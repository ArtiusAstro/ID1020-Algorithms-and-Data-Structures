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
    printf("%c",c);
  }
}

void iterative(char c){
  /* Initial memory allocation */
  int mallocSize = 10;
  char *myString = (char*) malloc(sizeof(char)*mallocSize);

  int i = 0; // counter

  /* collect chars, if array limit reached realloc double space */
  for(;;){
    c = getchar();
    if(c = '\n')
      break;
    ralloc(myString, &mallocSize, i++);
    myString[i++] = c;
  }

  /* Print the string in reverse order */
  for(i; i > 0; i--){
    printf("%c",myString[i]);
  }

  /* Deallocate the memory */
  free(myString);
}

void flush(){
  fflush(stdout);
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
