#include <stdio.h>

void ralloc(char *myString, int *mallocSizePtr, int i){
  if(i = *mallocSizePtr-1){
    *mallocSizePtr = *mallocSizePtr * 2;
    realloc(myString, sizeof(char) * (*mallocSizePtr));
  }
}

void recursive(char c){
  if((c = getchar()) != '\n'){
    recursive(c);
    putchar(c);
  }
}

void iterative(char c){
  /* Initial memory allocation */
  int mallocSize = 10;
  int *mallocSizePtr = &mallocSize;
  char *myString = (char*) malloc(sizeof(char)*mallocSize);

  int i = 0; // counter

  /* collect chars, if array limit reached realloc double space */
  for(;;){
    c = getchar();
    if(c = '\n')
      break;
    myString[i] = c;
    ralloc(myString, mallocSizePtr, i++);
  }

  /* Print the string in reverse order */
  for(i; i > 0; i--){
    putchar(myString[i]);
  }

  /* Deallocate the memory */
  free(myString);
}

void flush(){
  fflush(stdout); //flushes output buffer of stdout stream
}

int main() {
  printf("Input string: ");
  flush();
  printf("Recursively Reversed: ");
  char c;
  recursive(c);

  printf("\nInput string: ");
  flush();
  printf("Iteratively Reversed: ");
  iterative(c);

  return 0;
}
