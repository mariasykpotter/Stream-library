package ua.edu.ucu.stream;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AsIntStreamTest {
    private IntStream intStream;
    private IntStream intStream1;
    private IntStream intStream2;
    private int[] intArr = {-1, 0, 1, 2, 3};
    private int[] intArr1 = {1,4,9};
    private int[] intArr2 = {0, 1, 2, 3, 4, 5, 8, 9, 10};

    @Before
    public void setUp() {
        this.intStream = AsIntStream.of(intArr);
        this.intStream1 = AsIntStream.of(intArr1);
        this.intStream2 = AsIntStream.of(intArr2);
    }

    @Test
    public void testAverage() {
        double expectedValue = 1.0;
        assertEquals(expectedValue, this.intStream.average(), Integer.MIN_VALUE);
    }

    @Test
    public void testMax() {
        Integer expectedValue = 3;

        assertEquals(expectedValue, this.intStream.max());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMaxWithEmpty() {
        AsIntStream.of().max();
    }

    @Test
    public void testMin() {
        Integer expectedValue = -1;

        assertEquals(expectedValue, this.intStream.min());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMinWithEmptyStream() {
        AsIntStream.of().min();
    }

    @Test
    public void testCount() {
        long expectedValue = 5;

        assertEquals(expectedValue, this.intStream.count());
    }

    @Test
    public void testSum() {
        Integer expectedValue = 5;

        assertEquals(expectedValue, this.intStream.sum());
    }

    @Test
    public void testFilter() {
        int[] expectedValue = {1, 2, 3};

        assertArrayEquals(expectedValue, this.intStream.filter(x -> x > 0).toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFilterWithEmpty() {
        AsIntStream.of().filter(x -> x > 0);
    }

    @Test
    public void testMap() {
        int[] expectedValue = {1, 0, 1, 4, 9};

        assertArrayEquals(expectedValue, this.intStream.map(x -> x * x).toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMapWithEmpty() {
        AsIntStream.of().map(x -> x / 2 * x);
    }

    @Test
    public void testFlatMap() {
        int[] expectedValue = {-2, 0, -1, 1, 0, 2, 1, 3, 2, 4};

        assertArrayEquals(expectedValue, this.intStream.flatMap(x -> AsIntStream.of(x - 1, x + 1)).toArray());
    }
    @Test
    public void testFlatMap1() {
        int[] expectedValue = {0, 1, 2, 3, 4, 5, 8, 9, 10};

        assertArrayEquals(expectedValue, this.intStream1.flatMap(x -> AsIntStream.of(x - 1,x, x + 1)).toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFlatMapWithEmpty() {
        AsIntStream.of().flatMap(x -> AsIntStream.of(x * x));
    }

    @Test
    public void testReduce() {
        int expectedValue = 0;
        assertEquals(expectedValue, this.intStream.reduce(0, (sum, x) -> sum *= x));
    }
    @Test
    public void testReduce1() {
        int expectedValue = 14;
        assertEquals(expectedValue, this.intStream1.reduce(0, (sum, x) -> sum += x));
    }
    @Test
    public void testReduce2() {
        int expectedValue = 42;
        assertEquals(expectedValue, this.intStream2.reduce(0, (sum, x) -> sum += x));
    }

    @Test
    public void testtoArray() {
        int[] expectedValue = {-1, 0, 1, 2, 3};
        assertArrayEquals(expectedValue, this.intStream.toArray());

    }
}