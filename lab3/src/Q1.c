/*
 * >Description
 * A filter which replaces non-alphabeticals except \n with a blank
 * Uses the isalhpa() function to handle most of the work
 * The algorithm uses O(N) time complexity
 *
 * @Author: Ayub Atif
 */

#include <stdio.h>
#include <ctype.h>

/* filters out non-alphas */
void filter(FILE *input, FILE *output){
  int c;
  while((c=fgetc(input)) != EOF){
    if(isalpha(c) || c == '\n')  fputc(c,output);
    else  fputc(' ',output);
	putc(c);
  }
  rewind(input);
  rewind(output);
}

/* return char count of file */
int size(FILE *file){
  int size = 0;
  while(fgetc(file) != EOF)  size++;
  return size;
}

/* Unit test */
int main(int argc, char *argv[]) {
  printf("--------------------\nQ1\n--------------------\n");
  if( argc == 3 ) {
      FILE *input = fopen(argv[1], "r");
	  FILE *output = fopen(argv[2], "w+");

	  filter(input, output);
	  printf("Original txt size is: %d\n", size(input));
	  printf("Filtered txt size is: %d", size(output));

	  fclose(input);
	  fclose(output);
   }
   else if( argc > 3 ) {
      printf("Too many arguments supplied.\n");
   }
   else {
      printf("Two arguments expected.\n");
   }
  
  return(0);
}
