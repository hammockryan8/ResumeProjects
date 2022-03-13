#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <string.h>
#include <fcntl.h>
#include <dirent.h>
#include "link.h"
#include "queue.h"
#include <pthread.h>
#include <math.h>

typedef struct WFD_Repo{
  char* name;
  int total_words;
  struct Node* list;
  pthread_mutex_t lock;
}WFD_Repo;

typedef struct Anal_List{
  struct WFD_Repo *deposit1;
  struct WFD_Repo *deposit2;
  double KLD1;
  double KLD2;
  double JSD;
  pthread_mutex_t lock;
}Anal_List;

int num_files = 0;
int fail = 0;

int isDir(char* path);
void* Dir_Traverse(void* args);
void loopDirQ(void *args, int dir_threads, int fil_threads);
void* unitEmpty(void *args);
void* unitEmptyF(void *args);
int repo_create(struct WFD_Repo *repo);
int repo_add(struct WFD_Repo *repo, struct Node *node, char *name);
int repo_destroy(struct WFD_Repo *repo);
void *avg_freq(void* args);

/*Struct specifically for Directory Thread. To use Dir_Traverse with threading we need to pass (void* args)
since  (void*args) can be anything, i will be passing a struct which contains the arguments needed for Dir_Traverse*/

typedef struct freq_list{
  double freq;
  char *file1;
  char *file2;
}freq_list;

typedef struct thread_args{
  char* path;
  queue_t* dir_q;
  queue_t* fil_q;
  struct WFD_Repo *wfd;
  char* suffix;
}thread_args;

typedef struct anal_args{
  int start;
  int end;
  struct Anal_List *pair;
}anal_args;

int sort_func(const void *x ,const void *y){
  struct Anal_List *orderX = (Anal_List *)x;
  struct Anal_List *orderY = (Anal_List *)y;
  if (orderX->JSD > orderY->JSD){
    return 1;
  }
  else if (orderX->JSD < orderY->JSD){
    return -1;
  }
  else{
    return 0;
  }
}

int main(int argc, char **argv){
	if(argc < 2){
	   fprintf(stderr, "need aguments: <file1> <file2> ... <-dN> <-fN> <-aN> <-sS>\n");
	   exit(1);
	}
        int dir_threads = 1;
 	int file_threads = 1;
        int anal_threads = 1;
	char* suffix = calloc(5, sizeof(char));
	strcpy(suffix, ".txt");
	int i;
	char* num = calloc(256, sizeof(char));
        int j;
	struct queue_t dir_q;
        queue_create(&dir_q);
        struct queue_t fil_q;
        queue_create(&fil_q);
        for(i = 1; i < argc; i++){
	    if(strncmp(argv[i], "-", 1) == 0){
	      if(strncmp(argv[i], "-d", 2) == 0){
		for(j = 2; j < strlen(argv[i]); j++){
		  strncat(num, &(argv[i][j]), 1);
		} 
		dir_threads = atoi(num);
		memset(num, 0 , 1);
	      }
	      else if(strncmp(argv[i], "-f", 2) == 0){
		for(j = 2; j < strlen(argv[i]); j++){
		  strncat(num, &(argv[i][j]), 1);
		} 
		file_threads = atoi(num);
		memset(num, 0 , 1);
	      }
	      else if(strncmp(argv[i], "-a", 2) == 0){
	       for(j = 2; j < strlen(argv[i]); j++){
		 strncat(num, &(argv[i][j]), 1);
	       } 
	       anal_threads = atoi(num);
	       memset(num, 0 , 1);
	      }
	      else if(strncmp(argv[i], "-s", 2) == 0){
		for(j = 2; j < strlen(argv[i]); j++){
		  strncat(num, &(argv[i][j]), 1);
		} 
		memset(suffix, 0, 1);
		suffix = realloc(suffix, ( (strlen(argv[i])+1)  * sizeof(char) ));
		strcpy(suffix, num);
		memset(num, 0 , 1);
	      }
	      else{
		fprintf(stderr, "invalid option: %s\n", argv[i]);
		free(num);
		free(suffix);
		destroy(&dir_q);
		destroy(&fil_q);
		return EXIT_FAILURE;
	      }
	    }
	    else{
	        memset(num, 0 , 1);
		switch(isDir(argv[i])){
		     case 1:
		        enqueue(&dir_q, argv[i]);
		        break;
		     case 2:
			enqueue(&fil_q, argv[i]);
		        break;
		     default:
		        fprintf(stderr, "No such file or directory found: %s\n", argv[i]);
			break;
	        }
	    }
        }
	free(num);
	char *str = calloc(256, sizeof(char));
	//Directory Threads
	pthread_t *tids;
	struct thread_args *argsd = calloc(dir_threads, sizeof(struct thread_args));
	if (dir_threads > file_threads)
	  tids = calloc(dir_threads, sizeof(pthread_t));
	else
	  tids = calloc(file_threads, sizeof(pthread_t));
	for(i = 0; i < dir_threads; i++){
	  argsd[i].path = calloc(256, sizeof(char)); 
	  strcpy(argsd[i].path, str);
	  argsd[i].dir_q = &dir_q;
	  argsd[i].fil_q = &fil_q;
	  argsd[i].suffix = calloc(strlen(suffix), sizeof(char) + 1);
	  strncpy(argsd[i].suffix, suffix, strlen(suffix));
	  queue_threads(&dir_q, dir_threads);
	  if(pthread_create(&tids[i], NULL, unitEmpty, &argsd[i]) != 0){
	    perror("create thread failed");
	    abort();
	  }
	}
	while(dir_q.num_of_threads > 0){
	}
	//Creates WFD Repository
	struct WFD_Repo *repo = NULL;
	int num_of_files = fil_q.count;
	repo = (struct WFD_Repo *) calloc(num_of_files, sizeof(struct WFD_Repo));
	for(int i = 0; i < num_of_files; i++){
	  repo[i].name = calloc(256, sizeof(char));
	  repo[i].total_words = 0;
	  repo[i].list = NULL;
	}
	//File Threads
	struct thread_args *args = calloc(file_threads, sizeof(struct thread_args));
	for(i = 0; i < file_threads; i++){
	  args[i].path = calloc(256, sizeof(char)); 
	  strcpy(args[i].path, str);
	  args[i].fil_q = &fil_q;
	  args[i].dir_q = &fil_q;
	  args[i].wfd = repo;
          queue_threads(&fil_q, file_threads);
	  if(pthread_create(&tids[i], NULL, unitEmptyF, &args[i]) != 0){
	    perror("create thread failed");
	    abort();
	  }
	}
	while(fil_q.num_of_threads){
	}
	//Free Directory and File Threads
	for(i = 0; i < dir_threads; i++){
	  free(argsd[i].path);
	  free(argsd[i].suffix);
	}
	for(i = 0; i < file_threads; i++){
	  free(args[i].path);
	}
	queue_close(&dir_q);
	queue_close(&fil_q);
	for(i = 0; i < dir_threads; i++){
	    pthread_join(tids[i], NULL);
	}
	//Creating Analysis list
	double d_pairing_num = ((double)num_files / 2.0) * ((double)num_files - 1.0);
        int pairing_num = (int)d_pairing_num;
        int push = 0;
        struct Anal_List *pairs = NULL;
	pairs = (struct Anal_List *) calloc(pairing_num, sizeof(struct Anal_List));
	for(i = 0; i < pairing_num; i++){
	  pairs[i].deposit1 = NULL;
          pairs[i].deposit2 = NULL;
          pairs[i].KLD1 = 0.0;
	  pairs[i].KLD2 = 0.0;
	  pairs[i].JSD = 0.0;
	  pthread_mutex_init(&pairs[i].lock, NULL);
	}
	for(i = 0 ; i < num_of_files; i++){
	    if(repo[i].list != NULL){
	       for(j = i + 1; j < num_of_files; j++){
		   if(repo[j].list != NULL){
	              pairs[push].deposit1 = &repo[i];
		      pairs[push].deposit2 = &repo[j];
		      push++;
	           }
	       }
	    }
	}
	int **per_anal_thread = malloc(anal_threads * sizeof(int*));
	for(i = 0; i < anal_threads; i++){
	  per_anal_thread[i] = malloc(2 * sizeof(int));
	}
	int cnt = 0;
	for(i = 0; i < anal_threads; i++){
	  if(i == 0){
	    per_anal_thread[0][0] = 0;
	    per_anal_thread[0][1] = (pairing_num/anal_threads);
	    cnt += (pairing_num/anal_threads);
	  }
	  else if(i == anal_threads - 1){
	      per_anal_thread[anal_threads - 1][0] = cnt;
	      per_anal_thread[anal_threads - 1][1] = pairing_num;
	  }
	  else{
	    per_anal_thread[i][0] = cnt;
	    per_anal_thread[i][1] = cnt + (pairing_num/anal_threads);
	    cnt += (pairing_num/anal_threads);
	  }
	}
	//Analysis Threads
	pthread_t *anal_tids = calloc(anal_threads, sizeof(pthread_t));
	struct anal_args *a_args = calloc(anal_threads, sizeof(struct anal_args));
	for(i = 0; i < anal_threads; i++){
	  a_args[i].start = per_anal_thread[i][0]; //when it starts
	  a_args[i].end = per_anal_thread[i][1]; //when it ends
	  a_args[i].pair = pairs;
	  if(pthread_create(&anal_tids[i], NULL, avg_freq, &a_args[i]) != 0){
	    perror("create thread failed");
	    abort();
	  }
        }
	for(i = 0; i < anal_threads; i++){
	    pthread_join(anal_tids[i], NULL);
	}
	//sorting the JSD
	qsort(pairs, pairing_num, sizeof(struct Anal_List), sort_func);
	for( i = 0; i < pairing_num; i++){
	  printf("%f %s %s\n", pairs[i].JSD, pairs[i].deposit1->name, pairs[i].deposit2->name);
	}
	//Destroying Analysis List
	for(i = 0; i < pairing_num; i++){
            pthread_mutex_destroy(&pairs[i].lock);
	}
	//Destory the Repository	
	for(int k = 0; k < num_of_files; k++){
	  free(repo[k].name);
	  if(repo[k].list != NULL){
	    freeTheNodes(repo[k].list);
	  }
	}
	pthread_mutex_destroy(&repo->lock);
	//Free up EVERYTHING
	for(i = 0; i < anal_threads; i++){
	  free(per_anal_thread[i]);
	}
	free(suffix);
	free(per_anal_thread);
	free(pairs);
	free(repo);
	free(tids);
	free(anal_tids);
	free(str);
	free(a_args);
	free(argsd);
	free(args);
        destroy(&dir_q);
        destroy(&fil_q);
	if(fail == 1) return EXIT_FAILURE;
	return EXIT_SUCCESS;
}

int isDir(char *name){
        struct stat data;
        int err = stat(name, &data);
        if (err){
                return 0;
        }
	//returns 1 if directory or returns 2 if a regular file
        if(S_ISDIR(data.st_mode)){
          	return 1;
        }
	if(S_ISREG(data.st_mode)){
	  	return 2;
	}
        return 0;
}

void* Dir_Traverse(void* args){
  	struct thread_args *thisArg = args;
	char* dirPath = calloc(256, sizeof(char));
	DIR *dirp = opendir(thisArg->path);
	struct dirent* de;
	while ((de = readdir(dirp))) {
	  	strcpy(dirPath, thisArg->path);
		/*add slash if the last character is not a slash. this guarentees that 
		 when adding the file name, it is attached to the directory path*/
		if(dirPath[strlen(dirPath)-1] == '/'){
			strcat(dirPath, de->d_name);
		}
		else{
		  	strcat(dirPath, "/");
			strcat(dirPath, de->d_name);
		}
		if (isDir(dirPath) == 0){
		  	perror("Invalid Directory/File");
			fail = 1;
		}
		else if (isDir(dirPath) == 1){
			if (strncmp(de->d_name, ".", 1) == 0){
				continue;
			}
			enqueue(thisArg->dir_q, dirPath);
		}
		else if(isDir(dirPath) == 2){
		  	int path_size = strlen(dirPath);
			int suffix_len = strlen(thisArg->suffix);
			//Checks if the file has same suffix given
			if(strncmp(dirPath + path_size - suffix_len, thisArg->suffix, suffix_len) == 0){
			  enqueue(thisArg->fil_q, dirPath);
			}
		}
	}
	closedir(dirp);
	free(dirPath);
	return 0;
}

void* unitEmpty(void *args){
	struct thread_args *this_args = args;
	while(this_args->dir_q->num_of_threads > 0){
	    if(dequeue(this_args->dir_q, this_args->path) == 0){
	       Dir_Traverse(this_args);
	    }
	}
        return 0;
}

void* unitEmptyF(void *args){
    struct thread_args *this_args = args;
    while(this_args->fil_q->num_of_threads > 0){
	if(dequeue(this_args->fil_q, this_args->path) == 0){
      	struct Node *head = createLinkedList(this_args->path);
      	repo_add(this_args->wfd, head, this_args->path);
      }
    }
    return 0;
}

int repo_add(struct WFD_Repo *repo, struct Node *node, char *name){
  if(pthread_mutex_lock(&repo->lock) != 0){
    abort();
  }
  num_files++;
  int i = 0;
  while(repo[i].list != NULL){
    i++;
  }
  repo[i].list = node;
  strcpy(repo[i].name, name);
  repo[i].total_words = node->file_total;
  if(pthread_mutex_unlock(&repo->lock) != 0){
    abort();
  }
  return 0;
}

void *avg_freq(void* args){
  struct anal_args *a_args = args;
  struct Node *temp1 = NULL;
  struct Node *temp2 = NULL;
  for(int i = a_args->start; i < a_args->end; i++){
    temp1 = a_args->pair[i].deposit1->list;
    temp2 = a_args->pair[i].deposit2->list;
    //Compares every word in file1 with every word in file 2
    while(temp1 != NULL){
      while(temp2 != NULL){
	//If a word in both files are the same
	if(strcmp(temp1->word, temp2->word) == 0){
	  double trouble = ((temp1->freq + temp2->freq) / 2.0);
          a_args->pair[i].KLD1 += ((temp1->freq) * (log2((temp1->freq)/(trouble))));
          a_args->pair[i].KLD2 += ((temp2->freq) * (log2((temp2->freq)/(trouble))));
	  break;
	}
	temp2 = temp2->next;
      }
      //If file1 has a unique word
      if(temp2 == NULL){
	double trouble = (temp1->freq / 2.0);
	a_args->pair[i].KLD1 += ((temp1->freq) * (log2((temp1->freq)/(trouble))));
      }
      temp1 = temp1->next;
      temp2 = a_args->pair[i].deposit2->list;
    }
    temp2 = a_args->pair[i].deposit2->list;
    temp1 = a_args->pair[i].deposit1->list;
    //Iterates through file2 to find unique words in file2
    while(temp2 != NULL){
	while(temp1 != NULL){
	    if(strcmp(temp1->word, temp2->word) == 0){
	       break;
	    }
            temp1 = temp1->next;
	}
	if(temp1 == NULL){
	   double trouble = (temp2->freq / 2.0);
           a_args->pair[i].KLD2 += ((temp2->freq) * (log2((temp2->freq)/(trouble))));
	}
	temp2 = temp2->next;
	temp1 = a_args->pair[i].deposit1->list;
    }
    temp1 = a_args->pair[i].deposit1->list;
    temp2 = a_args->pair[i].deposit2->list;
    a_args->pair[i].JSD = sqrt((0.5 * a_args->pair[i].KLD1) + (0.5 * a_args->pair[i].KLD2));
  }
  
  return 0;
}
