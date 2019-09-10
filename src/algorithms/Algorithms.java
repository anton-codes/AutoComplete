package algorithms;

import java.util.*;

/**
 * This class contains implementations of different popular algorithms.
 * @author Anton Hrytsyk
 */
public final class Algorithms {


    /**
     * Generic wrapper around binarySearch for array.
     * @param key element to search for
     * @param list list to search
     * @param <K> Type of key
     * @param <T> Type of List
     * @return index of key or -1 if key isn't found.
     */
    public static <K extends Comparable<K>, T extends List<K>> int binarySearch(K key, T list ) {
        return binarySearch(key, (K[]) list.toArray(), 0, list.size() - 1);
    }

    /**
     * Recursive binary search algorithm.
     * Works with any list
     * @param key element to search for
     * @param list list to search
     * @param low starting index
     * @param high ending index
     * @param <K> Type of key
     * @param <T> Type of List
     * @return index of key or -1 if key not found.
     */
    public static <K extends Comparable<K>, T extends List<K>> int binarySearch(K key, T list, int low, int high ) {

          int middle = (low + high) / 2;

          if (high < low)
              return -1;
          if (key.compareTo(list.get(middle)) < 0)
              return binarySearch(key, list, low, middle - 1);
          if (key.compareTo(list.get(middle)) > 0)
              return binarySearch(key, list, middle + 1, high);
          if (key.compareTo(list.get(middle)) == 0)
              return middle;

        return -1;
    }

    /**
     * Recursive binary search.
     * Works with arrays.
     * @param key element to search for
     * @param arr array to search
     * @param low starting index
     * @param high ending index
     * @param <K> Type of key
     * @return index of key or -1 if key not found.
     */
    public static <K extends Comparable<K>> int binarySearch(K key, K arr[], int low, int high) {
        int middle = (low + high) / 2;

        if (high < low)
            return -1;

        if (key.compareTo(arr[middle]) < 0)
            return binarySearch(key, arr, low, middle - 1);
        if (key.compareTo(arr[middle]) > 0)
            return binarySearch(key, arr, middle + 1, high);
        if (key.compareTo(arr[middle]) == 0)
            return middle;

        return -1;
    }

    /**
     * Iterative selection sort algorithm with O^2 complexity.
     * Never makes more than O(n) swaps.
     *
     * @param arr array that will be sorted.
     * @param <T> type of elements of the array. Has to implement Comparable<T>
     * @return    sorted array
     */
    public static <T extends Comparable<T>> T[] selectionSort(T[] arr) {

        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i+1; j < arr.length; j++) {
                if (arr[minIndex].compareTo(arr[j]) > 0)
                    minIndex = j;
            }
            T temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
        return arr;

    }

    /**
     * Iterative insertion sort algorithm with O^2 complexity
     * @param arr array that will be sorted.
     * @param <T> type of elements of the array. Has to implement Comparable<T>.
     * @return    sorted array
     */
    public static <T extends Comparable<T>> T[] insertionSort(T[] arr) {

        for (int i = 0; i < arr.length - 1; i++) {

            int j = i + 1;
            if (arr[j].compareTo(arr[i]) < 0) {
                while (j-1 >= 0 && arr[j].compareTo( arr[j-1]) < 0) {
                    T temp = arr[j];
                    arr[j] = arr[j-1];
                    arr[j-1] = temp;
                    j--;
                }
            }

        }
        return arr;
    }

    /**
     * Recursive merge-sort algorithm n(log(n)) complexity.
     *
     * @param arr array that will be sorted
     * @return sorted array.
     */
    public static  Comparable[] mergeSort(Comparable[] arr) {

        if (arr.length <= 1)
            return arr;

        Comparable[] leftArr = new Comparable[arr.length/2];
        Comparable[] rightArr = new Comparable[arr.length - leftArr.length];

        for (int i = 0; i < arr.length ; i++) {
            if (i < arr.length/2)
                leftArr[i] = arr[i];
            else rightArr[i - arr.length/2] = arr[i];
        }

        mergeSort(leftArr);
        mergeSort(rightArr);

        // Declaring pointers into each of the arrays
        int i = 0, j = 0, k = 0;

        while (i < leftArr.length && j < rightArr.length){

            if (leftArr[i].compareTo(rightArr[j]) < 0 ) {
                arr[k] = leftArr[i];
                i++;
            } else {
                arr[k] = rightArr[j];
                j++;
            }
            k++;
        }
        return arr;
    }

}

