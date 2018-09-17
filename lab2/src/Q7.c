#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

/*
 *  A linked list which holds int elements, it sorts its elements as they're added
 *
 * The following are the functions supported by this linked list:
 * enqueue − Inserts an element at its sorted position in the list.
 * dequeue − Deletes an element from the start of the list.
 * print_list − Displays the list.
 *
 */

typedef struct node {
   int data;
   struct node *next;
   struct node *prev;
} Node;

Node *head = NULL;
Node *tail = NULL;

bool isEmpty() {
   return head == NULL;
}

bool isTail(Node *node) {
  return node == tail;
}

/* Inserts a node at its sorted position according to its int element */
void enqueue(int data){
    Node *new_node = (Node*) malloc(sizeof(Node));
    new_node->data = data;

    if(isEmpty()){
      head = tail = new_node;
    }

    if(tail->data<data){
      tail->next = new_node;
      tail = new_node;
    }

    if(head->data>=data){
      new_node->next = head;
      head = new_node;
    }

    else{
        Node *current = head;
        while(current->next!=NULL && current->next->data<new_node->data){
            current = current->next;
        }
        new_node->next = current->next;
        current->next = new_node;
    }
}

/* delete first item */
int dequeue() {
  int item;
  if(isEmpty()) {
    printf("Empty List, try calling enqueue() before removing\n");
    return 0;
  }

  item = head->data;

   if(head == tail) {
     head = tail = NULL;
   }

   else {
     head = head->next;
     head->prev = NULL;
   }

   return item;
}

/* displays the list */
void print_list() {
   Node *ptr = head;

   if(!isEmpty()) {
      while(!isTail(ptr)) {
        printf("[%c], ",ptr->data);
        ptr = ptr->next;
      }
      printf("[%c",ptr->data);
   }
   else{
     printf("[");
   }
   printf("]");
}

int main() {
  char c;

  printf("Original List: ");
  print_list();

  while((c = getchar()) != EOF){
    if(c != '\n'){
      enqueue(c);
      printf("\nList after adding (%c): ",c);
      print_list();
    }
  }

  printf("\nFinal List: ");
  print_list();

  return 0;
}
