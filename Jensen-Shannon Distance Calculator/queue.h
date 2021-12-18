typedef struct queue_t{
  char **data;
  int max;
  int count;
  int head;
  int open;
  int num_of_threads;
  pthread_mutex_t lock;
  pthread_cond_t read_ready;
  pthread_cond_t thread_ready;
}queue_t;

int queue_create(struct queue_t *Q);
int enqueue(struct queue_t *Q, char *item);
int dequeue(queue_t *Q, char* path);
int destroy(queue_t *Q);
int queue_close(queue_t *Q);
int queue_threads(queue_t *Q, int threads);
