/*
 * >Description
 * A filter which replaces non-alphabeticals or \n with a blank
 * Uses the isalhpa() function to handle most of the work
 * The algorithm uses O(N) time complexity
 *
 * @Author: Ayub Atif
 */

#include <stdio.h>
#include <ctype.h>

/* filters */
int filter(FILE *input, FILE *output){
  int size = 0;
  int c;
  while((c=fgetc(input)) != EOF){
    if(isalpha(c) || c == '\n')  fputc(c,output);
    else  fputc(' ',output);
  }
  rewind(input);
  rewind(output);
  return size;
}

int size(FILE *file){
  int size = 0;
  while(fgetc(file) != EOF)  size++;
  return size;
}

/* Unit test */
int main() {
  printf("--------------------\nQ1\n--------------------\n");
  FILE *input = fopen("98-0.txt", "r");
  FILE *output = fopen("Q1-filtered.txt", "w+");

  filter(input, output);
  printf("Original txt size is: %d\n", size(input));
  printf("Filtered txt size is: %d", size(output));

  fclose(input);
  fclose(output);
  return(0);
}
