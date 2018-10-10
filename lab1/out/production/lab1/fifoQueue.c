/*###################################################################################
⡿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿      @Author: Ayub Atif
⣿⣿⣿⣿⣿⣿⣿⣿⡇⢀⢀⠍⠙⢿⡟⢿⣿⣿⣿⣿⣿⣿⣿⣿
⠹⣿⣿⣿⣿⣿⣿⣿⠁⠈⢀⡤⢲⣾⣗⠲⣿⣿⣿⣿⣿⣿⣟⠻      Title: fifo-queue.c
⡀⢙⣿⣿⣿⣿⣿⣿⢀⠰⠁⢰⣾⣿⣿⡇⢀⣿⣿⣿⣿⣿⣿⡄      Execution: fifo-queue < input.txt
⣇⢀⢀⠙⠷⣍⠛⠛⢀⢀⢀⢀⠙⠋⠉⢀⢀⢸⣿⣿⣿⣿⣿⣷
⡙⠆⢀⣀⠤⢀⢀⢀⢀⢀⢀⢀⢀⢀⢀⢀⢀⢸⣿⣿⣿⣿⣿⣿      > Description
⣷⣖⠋⠁⢀⢀⢀⢀⢀⢀⣀⣀⣄⢀⢀⢀⢀⢸⠏⣿⣿⣿⢿⣿      char input using recursion
⣿⣷⡀⢀⢀⢀⢀⢀⡒⠉⠉⢀⢀⢀⢀⢀⢀⢈⣴⣿⣿⡿⢀⡿      is used to generate char single
⣿⣿⣷⣄⢀⢀⢀⢀⠐⠄⢀⢀⢀⠈⢀⣀⣴⣿⣿⣿⡿⠁⢀⣡      nodeed list queue
⠻⣿⣿⣿⣿⣆⠢⣤⣄⢀⢀⣀⠠⢴⣾⣿⣿⡿⢋⠟⢡⣿⣿⣿
⢀⠘⠿⣿⣿⣿⣦⣹⣿⣀⣀⣀⣀⠘⠛⠋⠁⡀⣄⣴⣿⣿⣿⣿
⢀⢀⢀⠈⠛⣽⣿⣿⣿⣿⣿⣿⠁⢀⢀⢀⣡⣾⣿⣿⣿⣿⣿⣿
⢀⢀⢀⢀⢰⣿⣿⣿⣿⣿⣿⣿⣦⣤⣶⣿⣿⣿⣿⣿⣿⣿⣿⣿
⢀⢀⢀⢀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⢀⢀⢀⢰⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
####################################################################################*/

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

typedef struct  node {
   char data;
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

//insert node at start
void enqueue(char data) {

   //create a node
   Node *node = (Node*) malloc(sizeof(Node));
   node->data = data;

   if(isEmpty()){
     head = node;
   }

   else {
     //point new node to old tail
     node->prev = tail;
     //point old tail to new node
     tail->next = node;
   }

   //point tail to new node
   tail = node;
}

//delete first item
void dequeue() {
  if(isEmpty()) {
    printf("Empty List, try calling enqueue() before removing\n");
    return;
  }

   if(head == tail) {
     head = tail = NULL;
   }

   else {
     head = head->next;
     head->prev = NULL;
   }
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
        printf("%c",ptr->data);
        ptr = ptr->next;
      }
      printf("%c",ptr->data);
   }
}

int main() {
  char c;
  while((c = getchar()) != EOF){
    enqueue(c);
  }

  //base test
  /*
  enqueue('a');
  enqueue('b');
  enqueue('c');

  printf("HEAD: %c\n", head->data);
  printf("HEAD->NEXT: %c\n", (head->next)->data);
  printf("TAIL->PREV: %c\n", (tail->prev)->data);
  printf("TAIL: %c\n", tail->data);
  */

  printf("Original List: ");
  //print list
  print_list();

  printf("\n\nOriginal String: ");
  //print list as a sentence
  print_list_tidy();
  printf("\n");

  while(!isEmpty()) {
    Node *temp = getFirst();
    dequeue();
    printf("\nString after deleting (%c): ",temp->data);
    print_list_tidy();
  }

  printf("\nList after deleting all items: ");
  print_list();

  return 0;
}
