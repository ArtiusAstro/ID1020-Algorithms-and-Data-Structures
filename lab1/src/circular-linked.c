/*###################################################################################
⡿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿      @Author: Ayub Atif
⣿⣿⣿⣿⣿⣿⣿⣿⡇⢀⢀⠍⠙⢿⡟⢿⣿⣿⣿⣿⣿⣿⣿⣿
⠹⣿⣿⣿⣿⣿⣿⣿⠁⠈⢀⡤⢲⣾⣗⠲⣿⣿⣿⣿⣿⣿⣟⠻      Title: circular-linked.c
⡀⢙⣿⣿⣿⣿⣿⣿⢀⠰⠁⢰⣾⣿⣿⡇⢀⣿⣿⣿⣿⣿⣿⡄      Execution: circular-linked < input.txt
⣇⢀⢀⠙⠷⣍⠛⠛⢀⢀⢀⢀⠙⠋⠉⢀⢀⢸⣿⣿⣿⣿⣿⣷
⡙⠆⢀⣀⠤⢀⢀⢀⢀⢀⢀⢀⢀⢀⢀⢀⢀⢸⣿⣿⣿⣿⣿⣿      > Description
⣷⣖⠋⠁⢀⢀⢀⢀⢀⢀⣀⣀⣄⢀⢀⢀⢀⢸⠏⣿⣿⣿⢿⣿      Reverse char input using both
⣿⣷⡀⢀⢀⢀⢀⢀⡒⠉⠉⢀⢀⢀⢀⢀⢀⢈⣴⣿⣿⡿⢀⡿      iterative and recursive methods.
⣿⣿⣷⣄⢀⢀⢀⢀⠐⠄⢀⢀⢀⠈⢀⣀⣴⣿⣿⣿⡿⠁⢀⣡      In main uncomment the method you'd
⠻⣿⣿⣿⣿⣆⠢⣤⣄⢀⢀⣀⠠⢴⣾⣿⣿⡿⢋⠟⢡⣿⣿⣿      like to test and comment out the
⢀⠘⠿⣿⣿⣿⣦⣹⣿⣀⣀⣀⣀⠘⠛⠋⠁⡀⣄⣴⣿⣿⣿⣿      other one.
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
   int data;
   struct node *next;
} Node;

node *head = NULL;
node *tail = NULL;
node *current = NULL;

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
void addFirst(int data) {

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
void removeFirst() {
  if(isEmpty()) {
    printf("Empty List, try calling addFirst() before removing\n");
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
void printList() {
   Node *ptr = head;

   //start from the beginning
   if(head != NULL) {
      while(ptr->next != ptr) {
        printf("[%d], ",ptr->data);
        ptr = ptr->next;
      }
      printf("[%d",ptr->data);
   }
   else{
     printf("[");
   }

   printf("]");
}

void main() {
   addFirst(10);
   addFirst(20);
   addFirst(30);
   addFirst(1);
   addFirst(40);
   addFirst(56);

   printf("Original List: ");

   //print list
   printList();

   while(!isEmpty()) {
      Node *temp = getFirst();
      removeFirst();
      printf("\nList after deleting (%d): ",temp->data);
      printList();
   }

   printf("\nList after deleting all items: ");
   printList();
}
