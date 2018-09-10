/*###################################################################################
⡿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿      @Author: Ayub Atif
⣿⣿⣿⣿⣿⣿⣿⣿⡇⢀⢀⠍⠙⢿⡟⢿⣿⣿⣿⣿⣿⣿⣿⣿
⠹⣿⣿⣿⣿⣿⣿⣿⠁⠈⢀⡤⢲⣾⣗⠲⣿⣿⣿⣿⣿⣿⣟⠻      Title: fifo-queue.c
⡀⢙⣿⣿⣿⣿⣿⣿⢀⠰⠁⢰⣾⣿⣿⡇⢀⣿⣿⣿⣿⣿⣿⡄      Execution: fifo-queue < input.txt
⣇⢀⢀⠙⠷⣍⠛⠛⢀⢀⢀⢀⠙⠋⠉⢀⢀⢸⣿⣿⣿⣿⣿⣷
⡙⠆⢀⣀⠤⢀⢀⢀⢀⢀⢀⢀⢀⢀⢀⢀⢀⢸⣿⣿⣿⣿⣿⣿      > Description
⣷⣖⠋⠁⢀⢀⢀⢀⢀⢀⣀⣀⣄⢀⢀⢀⢀⢸⠏⣿⣿⣿⢿⣿      char input using recursion
⣿⣷⡀⢀⢀⢀⢀⢀⡒⠉⠉⢀⢀⢀⢀⢀⢀⢈⣴⣿⣿⡿⢀⡿      is used to generate char single
⣿⣿⣷⣄⢀⢀⢀⢀⠐⠄⢀⢀⢀⠈⢀⣀⣴⣿⣿⣿⡿⠁⢀⣡      linked list queue
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
} Node;

Node *head = NULL;
Node *current = NULL;

bool isEmpty() {
   return head == NULL;
}

int size() {
   int size = 0;

   if(head == NULL) {
      return 0;
   }

   current = head->next;

   while(current != head) {
      size++;
      current = current->next;
   }

   return size;
}

//insert link at the first location
void enqueue(char data) {

   //create a link
   Node *link = (Node*) malloc(sizeof(Node));
   link->data = data;

   if (isEmpty()) {
      head = link;
      head->next = head;
   }
   else {
      //point it to old first node
      link->next = head;
      //point first to new first node
      head = link;
   }
}

//delete first item
void dequeue() {
  if(isEmpty()) {
    printf("Empty List, try calling enqueue() before removing\n");
    return;
  }

   if(head->next == head) {
     head = NULL;
   }

   else {
     head = head->next;
   }
}

Node* getFirst() {
  return head;
}

//display the list
void print_list() {
   Node *ptr = head;

   //start from the beginning
   if(head != NULL) {
      while(ptr->next != ptr) {
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
   if(head != NULL) {
      while(ptr->next != ptr) {
        printf("%c",ptr->data);
        ptr = ptr->next;
      }
      printf("%c",ptr->data);
   }
}

void recursive(char c){
  /* basic recursion with getchar, chars printed in reverse order */
  if((c = getchar()) != EOF){
    recursive(c);
    enqueue(c);
  }
}

void main() {
  char c;
  recursive(c);

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
}
