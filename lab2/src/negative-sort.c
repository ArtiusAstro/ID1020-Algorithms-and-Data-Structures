/*
 *
 * >Description
 * Implement a function in C which takes an array
 * of integers (both positive and negative) and orders the
 * elements in the array so that all negative elements come
 * before the positive. You are not allowed to sort the
 * array - only collect all negative values first.
 * The algorithm should only use O(1) extra memory
 *
 * Design a loop invariant which explains
 * how the method above works
 * (add it as part of the comments in the .c file).
 *
 * @Author: Ayub Atif
 */

#include <stdio.h>

/* Function to sort an array using insertion sort*/
void insertionX(int arr[], int n){
  int i, j, tmp;
  /*
  * At each step, for n negative integers in the array of size x,
  * the first n integers are negative integers and
  * the rest are non-negative integers.
  *
  */
  for (i = 0; i < n; i++){
    j = 0;
      while (arr[i] < 0 && j < n){
        if(arr[j]>0){
          tmp = arr[j];
          arr[j++] = arr[i];
          arr[i] = tmp;
        }
        else j++;
      }
  }
}

// A utility function to print an array of size n
void printArray(int arr[], int n)
{
   int i;
   for (i=0; i < n; i++){
     if(arr[i]<0) printf("%d ", arr[i]);
     else printf(" %d ", arr[i]);
     }
   printf("\n");
}

/* Driver program to test insertion sort */
int main() {
    int arr[] = {4, -2, 3, -1};
    int n = sizeof(arr)/sizeof(arr[0]);
    printf("Input:  ");
    printArray(arr, n);
    insertionX(arr, n);
    printf("Sorted: ");
    printArray(arr, n);

    return 0;
}
