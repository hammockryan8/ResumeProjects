#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include <string.h>
#include "queue.h"

int queue_create(struct queue_t *Q){
  Q->data =(char **)calloc(256, sizeof(char*));
  for(int i = 0; i < 256; i++){
    Q->data[i] = (char *)calloc(256, sizeof(char));
  }
  Q->max = 256;
  Q->head = 0;
  Q->count = 0;
  Q->open = 1;
  Q->num_of_threads = 0;
  pthread_mutex_init(&Q->lock, NULL);
  pthread_cond_init(&Q->read_ready, NULL);
  pthread_cond_init(&Q->thread_ready, NULL);
  return 0;
}

int enqueue(struct queue_t *Q, char *item){
  if(pthread_mutex_lock(&Q->lock) != 0){
    perror("lock failed\n");
    abort();
  }
  if(Q->max == Q->count){
    Q->max *= 2;
    char **p = realloc(Q->data, sizeof(char*) * Q->max);
    Q->data = p;
  }
  int index = Q->head + Q->count;
  strncpy(Q->data[index], item, strlen(item));
  Q->count++;
  pthread_cond_signal(&Q->read_ready);
  if(pthread_mutex_unlock(&Q->lock) != 0){
    abort();
  }
  return 0;
}

int dequeue(queue_t *Q, char* path){
  if(pthread_mutex_lock(&Q->lock) != 0){
    perror("lock failed\n");
    abort();
  }
  if (Q->count == 0){
    Q->num_of_threads--;
    if (Q->num_of_threads == 0){
      pthread_mutex_unlock(&Q->lock);
      pthread_cond_broadcast(&Q->read_ready);
      return 1;
    }
    while ((Q->count == 0) && (Q->num_of_threads > 0) ){
      pthread_cond_wait(&Q->read_ready, &Q->lock);
    }
    if (Q->count == 0){
      pthread_mutex_unlock(&Q->lock);
      return 1;
    }
    Q->num_of_threads++;
  }
  Q->count--;
  strcpy(path, Q->data[Q->head++]);
  if(pthread_mutex_unlock(&Q->lock) != 0){
    perror("unlock failed\n");
    abort();
  }
  return 0;
}

int destroy(queue_t *Q){
  int i;
  for(i = 0; i < Q->max; i++){
      free(Q->data[i]);
  }
  free(Q->data);
  pthread_mutex_destroy(&Q->lock);
  pthread_cond_destroy(&Q->read_ready);
  pthread_cond_destroy(&Q->thread_ready);
  return 0;
}

int queue_close(queue_t *Q){
  if(pthread_mutex_lock(&Q->lock) != 0){
    perror("lock failed\n");
    abort();
  }
  pthread_cond_broadcast(&Q->read_ready);
  pthread_cond_broadcast(&Q->thread_ready);
  Q->open = 0;
  if(pthread_mutex_unlock(&Q->lock) != 0){
    perror("unlock failed\n");
    abort();
  }
  return 0;
}

int queue_threads(queue_t *Q, int threads){
  Q->num_of_threads++;
  return 0;
}
