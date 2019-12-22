package ua.edu.ucu.stream;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AsIntStreamTest {
    private IntStream intStream;
    private IntStream intStream1;
    private IntStream intStream2;
    private IntStream intStream3;
    private int[] intArr = {-1, 0, 1, 2, 3};
    private int[] intArr1 = {1, 4, 9};
    private int[] intArr2 = {0, 1, 2, 3, 4, 5, 8, 9, 10};
    private int[] intArr3 = {1, 2, 3};

    @Before
    public void setUp() {
        this.intStream = AsIntStream.of(intArr);
        this.intStream1 = AsIntStream.of(intArr1);
        this.intStream2 = AsIntStream.of(intArr2);
        this.intStream3 = AsIntStream.of(intArr3);
    }

    @Test
    public void testAverage() {
        double expectedValue = 1.0;
        assertEquals(expectedValue, this.intStream.average(), Integer.MIN_VALUE);
    }
    @Test
    public void testAverage1() {
        double expectedValue = 4.666666666666667;
        assertEquals(expectedValue, this.intStream1.average(), Integer.MIN_VALUE);
    }
    @Test
    public void testAverage2() {
        double expectedValue = 4.666666666666667;
        assertEquals(expectedValue, this.intStream2.average(), Integer.MIN_VALUE);
    }
    @Test
    public void testAverage3() {
        double expectedValue = 2.0;
        assertEquals(expectedValue, this.intStream3.average(), Integer.MIN_VALUE);
    }

    @Test
    public void testMax() {
        Integer expectedValue = 3;

        assertEquals(expectedValue, this.intStream.max());
    }
    @Test
    public void testMax1() {
        Integer expectedValue = 9;

        assertEquals(expectedValue, this.intStream1.max());
    }
    @Test
    public void testMax2() {
        Integer expectedValue = 10;

        assertEquals(expectedValue, this.intStream2.max());
    }
    @Test
    public void testMax3() {
        Integer expectedValue = 3;

        assertEquals(expectedValue, this.intStream3.max());
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
    @Test
    public void testMin1() {
        Integer expectedValue = 1;

        assertEquals(expectedValue, this.intStream1.min());
    }
    @Test
    public void testMin2() {
        Integer expectedValue = 0;

        assertEquals(expectedValue, this.intStream2.min());
    }
    @Test
    public void testMin3() {
        Integer expectedValue = 1;

        assertEquals(expectedValue, this.intStream3.min());
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
    public void testCount1() {
        long expectedValue = 3;

        assertEquals(expectedValue, this.intStream1.count());
    }
    @Test
    public void testCount2() {
        long expectedValue = 9;

        assertEquals(expectedValue, this.intStream2.count());
    }
    @Test
    public void testCount3() {
        long expectedValue = 3;

        assertEquals(expectedValue, this.intStream3.count());
    }

    @Test
    public void testSum() {
        Integer expectedValue = 5;

        assertEquals(expectedValue, this.intStream.sum());
    }
    @Test
    public void testSum1() {
        Integer expectedValue = 14;

        assertEquals(expectedValue, this.intStream1.sum());
    }
    @Test
    public void testSum2() {
        Integer expectedValue = 42;

        assertEquals(expectedValue, this.intStream2.sum());
    }
    @Test
    public void testSum3() {
        Integer expectedValue = 6;

        assertEquals(expectedValue, this.intStream3.sum());
    }

    @Test
    public void testFilter() {
        int[] expectedValue = {1, 2, 3};

        assertArrayEquals(expectedValue, this.intStream.filter(x -> x > 0).toArray());
    }
    @Test
    public void testFilter1() {
        int[] expectedValue = {4,9};

        assertArrayEquals(expectedValue, this.intStream1.filter(x -> x >= 4).toArray());
    }
    @Test
    public void testFilter2() {
        int[] expectedValue = {10};

        assertArrayEquals(expectedValue, this.intStream2.filter(x -> x >= 10).toArray());
    }    @Test
    public void testFilter3() {
        int[] expectedValue = {};

        assertArrayEquals(expectedValue, this.intStream3.filter(x -> x < 1).toArray());
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
    @Test
    public void testMap1() {
        int[] expectedValue = {1,16,81};

        assertArrayEquals(expectedValue, this.intStream1.map(x -> x * x).toArray());
    }
    @Test
    public void testMap2() {
        int[] expectedValue = {0, 1, 4, 9, 16, 25, 64, 81, 100};

        assertArrayEquals(expectedValue, this.intStream2.map(x -> x * x).toArray());
    }
    @Test
    public void testMap3() {
        int[] expectedValue = {1, 4,9};

        assertArrayEquals(expectedValue, this.intStream3.map(x -> x * x).toArray());
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

        assertArrayEquals(expectedValue, this.intStream1.flatMap(x -> AsIntStream.of(x - 1, x, x + 1)).toArray());
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

    @Test
    public void testMultiple() {
        int expectedValue = 42;
        assertEquals(expectedValue, this.intStream3.filter(x -> x > 0).map(x -> x * x).flatMap(x -> AsIntStream.of(x - 1, x, x + 1)).reduce(0, (sum, x) -> sum += x));
    }
}