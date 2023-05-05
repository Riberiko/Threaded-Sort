import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomListGen {

    public static Integer[] generateRandom(int size)
    {
        Integer[] data = new Random().ints(size, -500, 501).boxed().toArray(Integer[]::new);
        return data;
    }
}
