#include <stdio.h>

void recursive(char c){
  c = getchar();
  if(c!='\n'){
    recursive(c);
    printf("%c",c);
  }
}

void iterative(&string str){
    int n = str.length();

    // Swap character
    for (int i = 0; i < n / 2; i++)
        swap(str[i], str[n - i - 1]);
}

int main() {
  printf("Input char: ");
  fflush(stdout);
  printf("Reversed: ");
  char c;
  recursive(c);

  char *str = scanf("Input char: \n");
  printf("Reversed: ");
  iterative(str);

  return 0;
}
