import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class TestSorts {

    static Integer[] controlData;
    static Integer[] data;

    static final int numTests = 1;

    @Test
    void mergeTest()
    {
        for(int i = 0; i < numTests; i++)
        {
            data = RandomListGen.generateRandom(1000);
            controlData = Arrays.copyOf(data, data.length);
            Arrays.sort(controlData);
            Assertions.assertArrayEquals(controlData, MergeSort.sort(data));
        }

    }

    @Test
    void quickTest()
    {
        for(int i = 0; i < numTests; i++)
        {
            data = RandomListGen.generateRandom(1000);
            controlData = Arrays.copyOf(data, data.length);
            Arrays.sort(controlData);
            Assertions.assertArrayEquals(controlData, QuickSort.sort(data));
        }
    }
}
