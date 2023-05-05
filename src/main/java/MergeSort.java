import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Merge Sort Threaded
 *
 * @param <T> Tye array type to be sorted
 * @author Riberiko
 */
public class MergeSort <T extends Comparable<T>> {

    T[] data;
    private T[] actualSort(T[] data)
    {
        this.data = data;
        for(int i = 0; i < data.length; i++) this.data[i] = data[i];
        ForkJoinPool.commonPool().invoke(new Divide(0, data.length-1));
        return data;
    }

    /**
     * Runs the merge sort algorithm using threads
     *
     * @param data  the data elements
     * @return  the arrays
     * @param <I>   the data Type must be comparable to its own type
     */
    public static <I extends Comparable<I>> I[] sort(I[] data)
    {
        return new MergeSort<I>().actualSort(data);
    }

    //This process is responsible for the diving part of the solution
    private class Divide extends RecursiveAction
    {
        int l;
        int h;
        int m;

        public Divide(int l, int h)
        {
            this.l = l;
            this.h = h;
            m = (l+h)/2;
        }

        @Override
        public void compute() {
            if(l < h)
            {
                Divide left = new Divide(l,m);
                Divide right = new Divide(m+1, h);
                Conquer con = new Conquer(l,m, h);

                //runs both halfs at the same time
                left.fork();
                right.fork();

                //waits for both parts to done before it begins the conquering part
                left.join();
                right.join();
                ForkJoinPool.commonPool().invoke(con);
            }
        }
    }

    private class Conquer extends RecursiveAction
    {
        int left;
        int right;
        int left1;
        int right1;

        public Conquer(int left, int middle, int right)
        {
            this.left = left;
            this.right = middle;
            this.left1 = middle+1;
            right1 = right;
        }

        @Override
        public void compute() {

            //creates both halves for the sub problem
            T[] leftArr = Arrays.copyOfRange(data, left, right+1);
            T[] rightArr = Arrays.copyOfRange(data, left1, right1+1);
            int start = left;
            int end = right1;
            left = 0;
            left1 = 0;
            right = leftArr.length-1;
            right1 = rightArr.length-1;

            //puts the two arrays into the place of the data array in the correct order
            for(int i = start; i <= end; i++)
            {
                if(left <= right && left1 <= right1)    //when both sub arrays still have elements
                {
                    //deals with which one should be added
                    if(leftArr[left].compareTo(rightArr[left1]) < 0) data[i] = leftArr[left++];
                    else data[i] = rightArr[left1++];

                } else if(left <= right) data[i] = leftArr[left++]; //when only this one has elements to give
                else data[i] = rightArr[left1++];   //when only this one has elements to give
            }
        }
    }


}
