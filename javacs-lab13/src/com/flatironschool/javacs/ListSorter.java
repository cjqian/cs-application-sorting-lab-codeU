/**
 * 
 */
package com.flatironschool.javacs;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Provides sorting algorithms.
 *
 */
public class ListSorter<T> {

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void insertionSort(List<T> list, Comparator<T> comparator) {
	
		for (int i=1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j-1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * Returns a list that might be new.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
        // FILL THIS IN!
		if (list.size() <= 1)
		{
			return list;
		}

		int mid = list.size()/2;
		List<T> first = new ArrayList(list.subList(0, mid));
		List<T> second = new ArrayList(list.subList(mid, list.size()));

		first= mergeSort(first, comparator);
		second = mergeSort(second, comparator);

		// Now, merge first and second.
		int iFirst = 0;
		int iSecond = 0;
		
		List<T> sorted = new ArrayList<T>();
		while (sorted.size() < list.size()){
			if (iFirst < first.size() && iSecond < second.size())
			{
				if (comparator.compare(first.get(iFirst), second.get(iSecond)) < 0)
				{
					sorted.add(first.get(iFirst));
					iFirst++;
				} else {
					sorted.add(second.get(iSecond));
					iSecond++;
				}
			}
			
			else if (iFirst < first.size()){
				sorted.add(first.get(iFirst));
				iFirst++;
			}
		
			else if (iSecond < second.size()){
				sorted.add(second.get(iSecond));
				iSecond++;
			}
		}

		return sorted;
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void heapSort(List<T> list, Comparator<T> comparator) {
        // FILL THIS IN!
		PriorityQueue<T> heap = getHeap(list, comparator);
		
		for (int i = 0; i < list.size(); i++){
			T next = heap.poll();
			list.set(i, next);
		}
	}
	
	private PriorityQueue<T> getHeap(List<T> list, Comparator<T> comparator){
		PriorityQueue<T> heap = new PriorityQueue<T>(list.size(), comparator);
		
		for (T element : list){
			heap.offer(element);
		}
		
		return heap;
	}

	
	/**
	 * Returns the largest `k` elements in `list` in ascending order.
	 * 
	 * @param k
	 * @param list
	 * @param comparator
	 * @return 
	 * @return
	 */
	public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
		PriorityQueue<T> heap = new PriorityQueue<T>();
		
		for (T element : list) 
		{
			if (heap.size() < k)
			{
				heap.offer(element);
			}
			else
			{
				T root = (T) heap.peek();
				if (comparator.compare(element, root) >= 0)
				{
					heap.poll();
					heap.offer(element);
				}
			}
		}
		
		List<T> sorted = new ArrayList<T>();
		
		for (int i = 0; i < k; i++){
			sorted.add(heap.poll());
		}
		
		return sorted;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer n, Integer m) {
				return n.compareTo(m);
			}
		};
		
		ListSorter<Integer> sorter = new ListSorter<Integer>();
		sorter.insertionSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		System.out.println(list);
	
		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);
	}
}
