import java.util.Arrays;
import java.util.Random;

/**
 * Timings of various sorting algorithms.
 * 
 * Sorry for the interface not being very intuitive... hopefully if you can read
 * the code, you can work out how to run it.
 * 
 * I suggest that you redirect the output to a file (with a .csv extension) and
 * open with a spreadsheet. Or plot in GnuPlot!
 * 
 * eg run with "java Sorts 6 > quicksort.csv"
 * 
 * You will probably have to kill the process (with ctrl-c) unless you fancy
 * waiting forever. Even if you decrease the max value of inputSize, you'll
 * still be waiting a long time, especially for bogosort and bozosort.
 * 
 * @author Alex Pinkney
 * @since 2013-06-04
 * 
 * 
 */
public class Sorts {

	private static Random r = new Random(System.currentTimeMillis());

	public static boolean isSorted(int[] nums) {
		for (int i = 0; i < nums.length - 1; i++) {
			if (nums[i] > nums[i + 1]) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {

		if (args.length == 0) {
			System.err.println("Error: need integer argument (0-11)");
			System.exit(-1);
		}

		int type = Integer.parseInt(args[0]);

		System.err.println("Running sort #" + type);

		for (int inputSize = 1; inputSize < 100000; inputSize++) {

			int totalRuns = 1000;

			System.out.print(inputSize);
			// System.err.println(inputSize);

			long totalruntime = 0;

			for (int run = 0; run < totalRuns; run++) {

				int[] nums = new int[inputSize];
				for (int i = 0; i < nums.length; i++) {
					nums[i] = r.nextInt(5 * inputSize);
				}

				long time = System.nanoTime();

				switch (type) {
					case 0:
						bubbleSort(nums);
						break;
					case 1:
						bubbleSortBetter(nums);
						break;
					case 2:
						bubbleSortBest(nums);
						break;
					case 3:
						cocktailSort(nums);
						break;
					case 4:
						selectionSort(nums);
						break;
					case 5:
						mergeSort(nums);
						break;
					case 6:
						quickSort(nums);
						break;
					case 7:
						heapSort(nums);
						break;
					case 8:
						Arrays.sort(nums); // Java's default sorting algorithm (Timsort?)
						break;
					case 9:
						bogoSort(nums);
						break;
					case 10:
						bozoSort(nums);
						break;
					case 11:
						quantumBogoSort(nums);
						break;
					default:
						System.err.printf("\nBad sort ID '%d'", type);
						System.exit(-2);
				}

				// For testing purposes
				// if (!isSorted(nums)) {
				// System.err.printf("Sort %d is broken:\n%s\n", type,
				// Arrays.toString(nums));
				// }

				totalruntime += System.nanoTime() - time;

			}

			System.out.println(", " + (totalruntime / totalRuns));

		}

	}

	// ***************************** Bubble Sorts *****************************

	// Checks all values on each pass
	public static void bubbleSort(int[] nums) {
		boolean swaps;
		do {
			swaps = false;
			for (int i = 0; i < nums.length - 1; i++) {
				if (nums[i] > nums[i + 1]) {
					int tmp = nums[i];
					nums[i] = nums[i + 1];
					nums[i + 1] = tmp;
					swaps = true;
				}
			}
		} while (swaps);
	}

	// Checks one fewer value each pass
	public static void bubbleSortBetter(int[] nums) {
		boolean swaps;
		int topsorted = 1;
		do {
			swaps = false;
			for (int i = 0; i < nums.length - topsorted; i++) {
				if (nums[i] > nums[i + 1]) {
					int tmp = nums[i];
					nums[i] = nums[i + 1];
					nums[i + 1] = tmp;
					swaps = true;
				}
			}
			topsorted++;
		} while (swaps);
	}

	// Doesn't check anything after the top swap on the last pass
	public static void bubbleSortBest(int[] nums) {
		int topswap = nums.length - 1;
		do {
			int newtopswap = 0;
			for (int i = 0; i < topswap; i++) {
				if (nums[i] > nums[i + 1]) {
					int tmp = nums[i];
					nums[i] = nums[i + 1];
					nums[i + 1] = tmp;
					newtopswap = i;
				}
			}
			topswap = newtopswap;
		} while (topswap > 0);
	}

	// Like the above, but does passes in alternate directions
	public static void cocktailSort(int[] nums) {
		int topswap = nums.length - 1;
		int botswap = 0;
		do {
			int newtopswap = 0;
			for (int i = botswap; i < topswap; i++) {
				if (nums[i] > nums[i + 1]) {
					int tmp = nums[i];
					nums[i] = nums[i + 1];
					nums[i + 1] = tmp;
					newtopswap = i;
				}
			}
			topswap = newtopswap;

			int newbotswap = topswap;
			for (int i = topswap - 1; i >= botswap; i--) {
				if (nums[i] > nums[i + 1]) {
					int tmp = nums[i];
					nums[i] = nums[i + 1];
					nums[i + 1] = tmp;
					newbotswap = i;
				}
			}
			botswap = newbotswap;
		} while (topswap > botswap);
	}

	// ***************************** Merge Sort *****************************

	public static void mergeSort(int[] nums) {

		if (nums.length <= 1) { // base case
			return;
		}

		// Split into 2
		int middle = nums.length / 2;
		int[] left = Arrays.copyOfRange(nums, 0, middle);
		int[] right = Arrays.copyOfRange(nums, middle, nums.length);

		// Recursive calls
		mergeSort(left);
		mergeSort(right);

		// Merge sub-arrays back together
		int l = 0;
		int r = 0;
		while (l + r < nums.length) {
			if (l < left.length && r < right.length) {
				if (left[l] < right[r]) {
					nums[l + r] = left[l++];
				} else {
					nums[l + r] = right[r++];
				}
			} else if (l < left.length) {
				nums[l + r] = left[l++];
			} else {
				nums[l + r] = right[r++];
			}
		}
	}

	// ***************************** Quicksort *****************************

	public static void quickSort(int[] nums) {
		quickSort(nums, 0, nums.length - 1);
	}

	private static void quickSort(int[] nums, int first, int last) {
		if (last > first) {
			int pivot = partition(nums, first, last);
			quickSort(nums, first, pivot - 1);
			quickSort(nums, pivot + 1, last);
		}
	}

	private static int partition(int[] nums, int first, int last) {
		int pivot = nums[first];
		int low = first + 1;
		int high = last;

		while (high > low) {
			while (low <= high && nums[low] <= pivot)
				low++;
			while (low <= high && nums[high] > pivot)
				high--;
			if (high > low) {
				int temp = nums[high];
				nums[high] = nums[low];
				nums[low] = temp;
			}
		}

		while (high > first && nums[high] >= pivot)
			high--;

		if (pivot > nums[high]) {
			nums[first] = nums[high];
			nums[high] = pivot;
			return high;
		} else {
			return first;
		}
	}

	// ***************************** Selection Sort ****************************

	public static void selectionSort(int[] nums) {
		int minindex;
		for (int i = 0; i < nums.length - 1; i++) {
			minindex = i;
			for (int j = i; j < nums.length; j++) {
				if (nums[j] < nums[minindex]) {
					minindex = j;
				}
			}
			if (minindex != i) {
				int tmp = nums[i];
				nums[i] = nums[minindex];
				nums[minindex] = tmp;
			}
		}
	}

	// ***************************** Heap Sort *****************************

	public static void heapSort(int[] nums) {
		heapify(nums);
		int end = nums.length - 1;

		while (end > 0) {
			int tmp = nums[end];
			nums[end] = nums[0];
			nums[0] = tmp;
			end--;
			siftDown(nums, 0, end);
		}
	}

	private static void heapify(int[] nums) {
		int start = (nums.length - 1) / 2;

		while (start >= 0) {
			siftDown(nums, start, nums.length - 1);
			start--;
		}
	}

	private static void siftDown(int[] nums, int start, int end) {

		int root = start;

		while (root * 2 + 1 <= end) {
			int child = root * 2 + 1;
			int swap = root;

			if (nums[swap] < nums[child]) {
				swap = child;
			}

			if (child + 1 <= end && nums[swap] < nums[child + 1]) {
				swap = child + 1;
			}

			if (swap != root) {
				int tmp = nums[root];
				nums[root] = nums[swap];
				nums[swap] = tmp;
				root = swap;
			} else {
				return;
			}
		}
	}

	public static void bogoSort(int[] nums) {
		while (!isSorted(nums)) {
			shuffle(nums);
		}
	}

	private static void shuffle(int[] nums) { // Knuth Shuffle
		int n, tmp;
		for (int i = nums.length - 1; i > 0; i--) {
			n = r.nextInt(i + 1);
			tmp = nums[i];
			nums[i] = nums[n];
			nums[n] = tmp;
		}
	}

	public static void bozoSort(int[] nums) {
		int n1, n2, tmp;
		while (!isSorted(nums)) {
			// swap 2 random elements
			n1 = r.nextInt(nums.length);
			n2 = r.nextInt(nums.length - 1);
			if (n2 >= n1) {
				n2++;
			}
			tmp = nums[n1];
			nums[n1] = nums[n2];
			nums[n2] = tmp;
		}
	}

	public static void quantumBogoSort(int[] nums) {
		shuffle(nums);
		if (!isSorted(nums)) {
			destroyUniverse();
		}
	}

	private static void destroyUniverse() {
		// TODO: Implement this...
		throw new UnsupportedOperationException(
				"The implementation of this method is left as an exercise to the reader");

	}

}
