import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Quick Sort Threaded
 *
 * @param <T>
 * @author Riberiko Niyowmungere
 */
public class QuickSort <T extends Comparable<T>> {

    T[] data;

    private T[] actualSort(T[] data)
    {
        this.data = data;
        ForkJoinPool.commonPool().invoke(new DivideAndConquer(0, data.length-1));
       return data;
    }

    /**
     * Runs the quick sort algorithm using threads
     *
     * @param data  the data elements
     * @return  the arrays
     * @param <I>   the data Type must be comparable to its own type
     */
    public static <I extends Comparable<I>> I[] sort(I[] data)
    {
        return new QuickSort<I>().actualSort(data);
    }

    private class DivideAndConquer extends RecursiveAction
    {
        final int l;
        final int h;
        int p;

        boolean alternate;

        public DivideAndConquer(int l, int h)
        {
            this.l = l;
            this.h = h;
            p = (h+l)/2;

            alternate = false;
        }

        @Override
        public void compute()
        {
            if(l > h || h < l) return;

            int left = l;
            int right = h;

            //swap pivot out of the way
            T temp = data[right];
            data[right--] = data[p];
            data[p] = temp;
            p = right+1;

            while(left <= right && right != l && left != h-1)
            {
                if(alternate) {// this alternating allows for the algorithm to end up to the center as close as possible
                    while (left != h - 1 && data[left].compareTo(data[p]) <= 0) left++;
                    while (right != l - 1 && data[right].compareTo(data[p]) >= 0) right--;
                    alternate = false;
                }else
                {
                    while (right != l - 1 && data[right].compareTo(data[p]) >= 0) right--;
                    while (left != h - 1 && data[left].compareTo(data[p]) <= 0) left++;
                    alternate = true;
                }

                if(left != right && right >= l && data[left].compareTo(data[right]) == 0)
                {
                    left++;
                    right--;
                }
                else if(left < right && right >= l && data[left].compareTo(data[right]) > 0)
                {
                    temp = data[right];
                    data[right--] = data[left];
                    data[left++] = temp;
                }
            }

            if(data[left].compareTo(data[p]) >= 0)  //only swaps the pivot into place when the left is greater than or eqal
            {
                temp = data[p];
                data[p] = data[left];
                data[left] = temp;
                p = left;
            }

            if(h-l > 1) // continues forking while l great h
            {
                DivideAndConquer leftSub = new DivideAndConquer(l, right);
                DivideAndConquer rightSub = new DivideAndConquer(left+1, h);

                //initiates the sub processes
                leftSub.fork();
                rightSub.fork();

                //waits until they are completed
                leftSub.join();
                rightSub.join();
            }

        }

    }

}
