/*#########################################################################
⡿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿      @Author: Ayub Atif
⣿⣿⣿⣿⣿⣿⣿⣿⡇⢀⢀⠍⠙⢿⡟⢿⣿⣿⣿⣿⣿⣿⣿⣿
⠹⣿⣿⣿⣿⣿⣿⣿⠁⠈⢀⡤⢲⣾⣗⠲⣿⣿⣿⣿⣿⣿⣟⠻      Title: reverse-input-stack.c
⡀⢙⣿⣿⣿⣿⣿⣿⢀⠰⠁⢰⣾⣿⣿⡇⢀⣿⣿⣿⣿⣿⣿⡄
⣇⢀⢀⠙⠷⣍⠛⠛⢀⢀⢀⢀⠙⠋⠉⢀⢀⢸⣿⣿⣿⣿⣿⣷      > Description
⡙⠆⢀⣀⠤⢀⢀⢀⢀⢀⢀⢀⢀⢀⢀⢀⢀⢸⣿⣿⣿⣿⣿⣿
⣷⣖⠋⠁⢀⢀⢀⢀⢀⢀⣀⣀⣄⢀⢀⢀⢀⢸⠏⣿⣿⣿⢿⣿      Reverse char input using a stack
⣿⣷⡀⢀⢀⢀⢀⢀⡒⠉⠉⢀⢀⢀⢀⢀⢀⢈⣴⣿⣿⡿⢀⡿      with an iterative approach
⣿⣿⣷⣄⢀⢀⢀⢀⠐⠄⢀⢀⢀⠈⢀⣀⣴⣿⣿⣿⡿⠁⢀⣡
⠻⣿⣿⣿⣿⣆⠢⣤⣄⢀⢀⣀⠠⢴⣾⣿⣿⡿⢋⠟⢡⣿⣿⣿
⢀⠘⠿⣿⣿⣿⣦⣹⣿⣀⣀⣀⣀⠘⠛⠋⠁⡀⣄⣴⣿⣿⣿⣿
⢀⢀⢀⠈⠛⣽⣿⣿⣿⣿⣿⣿⠁⢀⢀⢀⣡⣾⣿⣿⣿⣿⣿⣿
⢀⢀⢀⢀⢰⣿⣿⣿⣿⣿⣿⣿⣦⣤⣶⣿⣿⣿⣿⣿⣿⣿⣿⣿
⢀⢀⢀⢀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⢀⢀⢀⢰⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
##########################################################################*/

#include <stdio.h>
#include <stdlib.h>

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

int main() {
    NODE * top, * second_stack;
    NODE * arr[4];
    DATA element;
    int i, size, counter = 0;

    /* stack size is dynamic and specified at runtime */
    printf("Enter stack size:");
    scanf("%d", &size);

    printf("Push elements to stack\n");
    init(&top);
    while (counter < size) {
        element.info = rand();
        printf("push element %d into stack\n", element.info);
        top = push(top, element);
        counter++;
    }
    printf("\nTop Of stack %d\n", top_of_stack(top)->info);

    init(&second_stack);
    element.info = 333333;
    second_stack = push(second_stack, element);
    top = push_from_other_stack(top, second_stack);

    printf("Pop elements from stack\n");
    while (0 == is_empty(top)) {
        top = pop(top, &element);
        printf("pop element %d from stack\n", element.info);
    }

    return (EXIT_SUCCESS);
}
