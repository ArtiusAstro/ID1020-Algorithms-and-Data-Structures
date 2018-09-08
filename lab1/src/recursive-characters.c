/*#########################################################################
⡿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿      @Author: Ayub Atif
⣿⣿⣿⣿⣿⣿⣿⣿⡇⢀⢀⠍⠙⢿⡟⢿⣿⣿⣿⣿⣿⣿⣿⣿
⠹⣿⣿⣿⣿⣿⣿⣿⠁⠈⢀⡤⢲⣾⣗⠲⣿⣿⣿⣿⣿⣿⣟⠻      Title: recursive-characters.c
⡀⢙⣿⣿⣿⣿⣿⣿⢀⠰⠁⢰⣾⣿⣿⡇⢀⣿⣿⣿⣿⣿⣿⡄
⣇⢀⢀⠙⠷⣍⠛⠛⢀⢀⢀⢀⠙⠋⠉⢀⢀⢸⣿⣿⣿⣿⣿⣷      > Description
⡙⠆⢀⣀⠤⢀⢀⢀⢀⢀⢀⢀⢀⢀⢀⢀⢀⢸⣿⣿⣿⣿⣿⣿
⣷⣖⠋⠁⢀⢀⢀⢀⢀⢀⣀⣀⣄⢀⢀⢀⢀⢸⠏⣿⣿⣿⢿⣿      Reverse char input using both
⣿⣷⡀⢀⢀⢀⢀⢀⡒⠉⠉⢀⢀⢀⢀⢀⢀⢈⣴⣿⣿⡿⢀⡿      iterative and recursive methods
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
#include <string.h>

void ralloc(char *myString, char *myNewString, int *mallocSizePtr){
  /* If array limit reached malloc new array with double space */
  printf("performing ralloc\n");
  myNewString = (char*) malloc(sizeof(char) * (*mallocSizePtr) * 2);
  strncpy(myNewString, myString, *mallocSizePtr);
  free(myString);
  myString = myNewString;
  *mallocSizePtr = *mallocSizePtr * 2;
}

void iterative_malloc(char c){
  /* Initial memory allocation */
  int mallocSize = 200;
  char *myString = (char*) malloc(sizeof(char) * mallocSize);
  char *myNewString;

  int i = 0; // counter
  printf("fnxn start\n");

  while((c = getchar()) != EOF){
    myString[i++] = c;
    printf("In while loop with char: %c in index %d\n", myString[i], i);
    if(i = mallocSize){
      ralloc(myString, myNewString, &mallocSize);
    }
  }

  /* Print the string in reverse order */
  printf("\nIteratively Reversed: ");
  for(i; i > 0; i--){
    putchar(myString[i-1]);
  }

  /* Deallocate the memory */
  free(myString);
  free(myNewString);

}

void iterative(char c){
  /* Initial memory allocation */
  char myString[256];
  int i = 0; // counter

  while((c = getchar()) != EOF){
    myString[i++] = c;
  }

  /* Print the string in reverse order */
  printf("\nIteratively Reversed: ");
  while(i-- > 0){
    putchar(myString[i]);
  }
}

void recursive(char c){
  if((c = getchar()) != EOF){
    recursive(c);
    putchar(c);
  }
}

int main() {
  char c;
  printf("Input string: ");

  //printf("\nRecursively Reversed: ");
  //recursive(c);

  iterative(c);

  return 0;
}
