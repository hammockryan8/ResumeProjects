#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <pthread.h>
#include <unistd.h>
#include <fcntl.h>
#include <ctype.h>
#include "link.h"

struct Node *createLinkedList(char arg[]){
      int file = open(arg, O_RDONLY);
      int write_size = 256;
      char* extra = (char *)calloc(write_size, sizeof(char));
      int space_detect = -1;
      size_t word_byte = 0;
      char buf [1];
      int i;
      int running_total = 0;
      char* ini = calloc(512, sizeof(char));
      struct Node *head = (struct Node *)malloc(sizeof(struct Node));
      head->freq = 0.0;
      head->word_total = 0;
      head->file_total = 0;
      head->word = ini;
      head->next = NULL;
      while((word_byte = read(file, buf, 1) > 0)){
      	   for(i = 0; i < 1; i++){
	       word_byte += 1;
	       if(word_byte == write_size){
		  extra = realloc(extra, (2*write_size));
		  write_size *= 2;
	       }
               if(buf[i] == ' '){
		   space_detect = 1;
	       }
	       else if(buf[i] == '\n'){
	           space_detect = 1;
	       }
	       else if(buf[i] == ';'){
		   
	       }
	       else if(buf[i] == ','){
		
	       }
	       else if(buf[i] == '\''){
		
	       }
	       else if(buf[i] == ':'){
			
	       }
	       else if(buf[i] == '?'){
		
	       }
	       else if(buf[i] == '.'){
		
	       }
	       else if(buf[i] == '!'){
		
	       }
	       else if(buf[i] == '\"'){
		
	       }
               else{
		  char result;
		  result = tolower(buf[i]);
		  strncat(extra, &result, 1);
	       }
               switch(space_detect){
		  case 1:
		     space_detect = 0;
	             
			switch(running_total){
			  case 0:
			      running_total++;
			      strcpy(head->word, extra);
			      head->word_total = 1;
			      head->file_total = 0;
			      head->freq = 1.0;
			      memset(extra, 0, 1);
			      break;
			  default:
			      switch(ifPresent(head, extra, running_total)){
				   case 1:
				      running_total++;
				      head = insert(head, extra, running_total);
				      updateFreqs(head, running_total);
				      break;
				   default:
				      running_total++;
				      updateFreqs(head, running_total);
				      break;
			      }
			      break;
		         }
		     
		     memset(extra, 0, 1);
		     break;
		  default:
		     break;
	       }
	       
	   }
      } 
      free(extra);
      close(file);
      updateFreqs(head, running_total);
      //      printNodeInfo(head);
      return head;
}

struct Node *createNewNode(char *def, int running_total){
    struct Node *newNode = (struct Node *)malloc(sizeof(struct Node));
    newNode->freq = (1.0 / (double)running_total);
    newNode->word_total = 1;
    newNode->file_total = 0;
    newNode->word = def;
    char* put = calloc(512, sizeof(char));
    strcpy(put, def);
    newNode->word = put;

    newNode->next = NULL;
    return newNode;
}

struct Node *insert(struct Node *head, char *def, int running_total){
    struct Node *prev = head;
    struct Node *place_holder = prev->next;
    
    while(place_holder != NULL){
	prev = place_holder;
	place_holder = place_holder->next;
    }
    struct Node *new_insert = createNewNode(def, running_total);
    prev->next = new_insert;
    return head;
}

int ifPresent(struct Node *head, char *def, int running_total){
    struct Node *temp = head;
    while(temp != NULL){
	if(strcmp(def, temp->word) == 0){
           temp->word_total++;
	   int num = temp->word_total;
	   temp->freq = ((double)num / (double)running_total);
	   return 0;
	}
	temp = temp->next;
    }
    return 1;
}

void updateFreqs(struct Node *head, int running_total){
    struct Node *temp = head;
    while(temp != NULL){
	int num = temp->word_total;
        temp->freq = ((double)num / (double)running_total);
	temp->file_total = running_total;
	temp = temp->next;
    }
}

void printNodeInfo(struct Node* head){
    struct Node *temp = head;
    while(temp != NULL){
	printf("%s ", temp->word);
	printf("%d ", temp->word_total);
	printf("%f", temp->freq);
	printf("->");
        temp = temp->next;
    }
    putchar('\n');
}

void freeTheNodes(struct Node* head){
    struct Node* temp;
    while(head != NULL){
	temp = head;
	head = head->next;
	free(temp->word);
	free(temp);
    }
}
