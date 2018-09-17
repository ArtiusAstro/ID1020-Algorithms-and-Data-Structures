/*
 *
 * >Description
 * Implemented a function in C which takes an array
 * of integers (both positive and negative) and orders the
 * elements in the array so that all negative elements come
 * before the positive.
 * The algorithm uses only O(1) auxillary memory
 *
 * @Author: Ayub Atif
 */

#include <stdio.h>

/* Function to sort an array using insertion sort*/
void insertionX(int arr[], int n){
  int i, j, tmp;

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

/* prints an array of size n */
void printArray(int arr[], int n){
   int i;
   for (i=0; i < n; i++){
     if(arr[i]<0) printf("%d ", arr[i]);
     else printf(" %d ", arr[i]);
     }
   printf("\n");
}

/* Unit test the insertion sort */
int main() {
    int arr[] = {4, -2, 3, -1, -9, 13};
    int n = sizeof(arr)/sizeof(arr[0]);
    printf("Input:  ");
    printArray(arr, n);
    insertionX(arr, n);
    printf("Sorted: ");
    printArray(arr, n);

    return 0;
}
