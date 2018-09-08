#include <stdio.h>
#include <stdlib.h>

#define FALSE  0
#define TRUE  1

typedef struct Node {
  struct Node* prev;
  char c;
} Node;

void init(Node** top) {
  /* set top of stack as empty */
  *top = NULL;
}

Node* push(Node* top, char c) {
  /* push node pointer to top of stack */
  Node* tmp = (Node*) malloc(sizeof(Node));

  tmp->c = c;
  tmp->prev = top;
  top = tmp;
  return top;
}

/*  Returns new TOS
* [In] TOS
* [Out] Data Node to pop
*/
Node* pop(Node *top, DATA *element) {
  Node* tmp = top;
  *element = top->data;
  top = top->prev;
  free(tmp);
  return top;
}

DATA* top_of_stack(Node *top) {
  return &top->data;
}

/*
* Return 1 if empty
*/
int is_empty(Node* top) {
  return (top == NULL) ? 1 : 0;
}
