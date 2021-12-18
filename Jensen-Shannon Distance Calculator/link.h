typedef struct Node{
    double freq;
    int word_total;
    int file_total;
    char *word;
    struct Node *next;
}Node;

struct Node* createLinkedList(char arg[]);
struct Node *createNewNode(char *def, int running_total);
struct Node *insert(struct Node *head, char *def, int running_total);
int ifPresent(struct Node *head, char *def, int running_total);
char* cutDelims(char *def, char *mod);
char* lowerCase(char *def, char *mod);
void updateFreqs(struct Node *head, int running_total);
void printNodeInfo(struct Node* head);
void freeTheNodes(struct Node* initial);
