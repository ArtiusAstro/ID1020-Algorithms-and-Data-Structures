#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

/*
 *  In a circular list, the next pointer of the last node points to the first node
 *
 * Following are the important operations supported by a circular list.
 * insert − Inserts an element at the start of the list.
 * delete − Deletes an element from the start of the list.
 * display − Displays the list.
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

int size() {
   int size = 0;
   Node *current = NULL;

   if(head == NULL) {
      return size;
   }

   current = head->next;

   while(current != NULL) {
      size++;
      current = current->next;
   }

   return size;
 }

/* function to insert a new_node in a list. Note that this
  function expects a pointer to head_ref as this can modify the
  head of the input linked list (similar to push())*/
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

//delete first item
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

Node* getFirst() {
  return head;
}

//display the list
void print_list() {
   Node *ptr = head;

   //start from the beginning
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

//display the list as a sentence
void print_list_tidy() {
   Node *ptr = head;

   //start from the beginning
   if(!isEmpty()) {
      while(!isTail(ptr)) {
        printf("%c ",ptr->data);
        ptr = ptr->next;
      }
      printf("%c",ptr->data);
   }
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

  printf("\n\nOriginal List: ");
  print_list();

  while(!isEmpty()) {
    Node *temp = getFirst();
    printf("\nList after deleting (%c): ",dequeue());
    print_list();
  }

  return 0;
}
