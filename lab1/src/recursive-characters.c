/*###################################################################################
⡿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿      @Author: Ayub Atif
⣿⣿⣿⣿⣿⣿⣿⣿⡇⢀⢀⠍⠙⢿⡟⢿⣿⣿⣿⣿⣿⣿⣿⣿
⠹⣿⣿⣿⣿⣿⣿⣿⠁⠈⢀⡤⢲⣾⣗⠲⣿⣿⣿⣿⣿⣿⣟⠻      Title: recursive-characters.c
⡀⢙⣿⣿⣿⣿⣿⣿⢀⠰⠁⢰⣾⣿⣿⡇⢀⣿⣿⣿⣿⣿⣿⡄      Execution: recursive-characters.c < input.txt
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

void ralloc(char **myStringPtr, int *arraySizePtr){
  /* If array limit reached malloc new array with double space */
  char *copy = (char*) realloc(*myStringPtr, *(arraySizePtr) * 2);

  /* stack overflow exception handling */
  if(copy == NULL){
    printf("Not enough memory avaialable!");
    free(*myStringPtr);
  }

  /* update stuffs */
  else{
  *arraySizePtr = *arraySizePtr * 2;
  *myStringPtr = copy;
  }
}

void iterative(char c){
  /* Initial memory allocation */
  int ARRAY_SIZE = 10;
  char *myString = (char*) malloc(ARRAY_SIZE);
  int i = 0; // counter

  while((c = getchar()) != EOF){
    myString[i++] = c; // add char to string

    if(i == ARRAY_SIZE){
      /* current char capacity reached, realloc with double current capacity */
      ralloc(&myString, &ARRAY_SIZE);
      printf("String size doubled to %d\n", ARRAY_SIZE);
    }
  }

  printf("\nTotal chars: %d\n\nIteratively Reversed: ", i);
  /* Print the string in reverse order */
  while(i-- > 0){
    putchar(myString[i]);
  }

  free(myString);
}

void recursive(char c){
  /* basic recursion with getchar, chars printed in reverse order */
  if((c = getchar()) != EOF){
    recursive(c);
    putchar(c);
  }
}

int main() {
  char c;

  /* Print the raw input COMMENT OUT OTHERS */
  //while((c = getchar()) != EOF) putchar(c);

  /* Recursively print the input in reverse COMMENT OUT OTHERS */
  //printf("Recursively Reversed: "); recursive(c);

  /* Iteratively print the input in reverse COMMENT OUT OTHERS */
  iterative(c);

  return 0;
}
