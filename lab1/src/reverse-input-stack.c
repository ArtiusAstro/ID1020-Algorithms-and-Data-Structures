/*#########################################################################
⡿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿      @Author: Ayub Atif
⣿⣿⣿⣿⣿⣿⣿⣿⡇⢀⢀⠍⠙⢿⡟⢿⣿⣿⣿⣿⣿⣿⣿⣿
⠹⣿⣿⣿⣿⣿⣿⣿⠁⠈⢀⡤⢲⣾⣗⠲⣿⣿⣿⣿⣿⣿⣟⠻      Title: stack.c
⡀⢙⣿⣿⣿⣿⣿⣿⢀⠰⠁⢰⣾⣿⣿⡇⢀⣿⣿⣿⣿⣿⣿⡄
⣇⢀⢀⠙⠷⣍⠛⠛⢀⢀⢀⢀⠙⠋⠉⢀⢀⢸⣿⣿⣿⣿⣿⣷      > Description
⡙⠆⢀⣀⠤⢀⢀⢀⢀⢀⢀⢀⢀⢀⢀⢀⢀⢸⣿⣿⣿⣿⣿⣿
⣷⣖⠋⠁⢀⢀⢀⢀⢀⢀⣀⣀⣄⢀⢀⢀⢀⢸⠏⣿⣿⣿⢿⣿      a char stack
⣿⣷⡀⢀⢀⢀⢀⢀⡒⠉⠉⢀⢀⢀⢀⢀⢀⢈⣴⣿⣿⡿⢀⡿
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
  char item;
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

Node* pop(Node *top, char *element) {
  Node* tmp = top;
  *element = top->item;
  top = top->prev;
  free(tmp);
  return top;
}

char* top_of_stack(Node *top) {
  return &top->item;
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
    char element;
    int i, size, counter = 0;

    /* stack size is dynamic and specified at runtime */
    printf("Enter stack size:");
    scanf("%d", &size);

    printf("Push elements to stack\n");
    init(&top);
    while (counter < size) {
        printf("push char %d into stack\n", element);
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
        printf("pop element %d from stack\n", element);
    }

    return 0;
}
