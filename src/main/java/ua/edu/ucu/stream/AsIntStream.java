package ua.edu.ucu.stream;

import ua.edu.ucu.function.*;

import java.util.ArrayList;
import java.util.Iterator;

public class AsIntStream implements IntStream {
    //    ArrayList<Integer> streamArr;
    private Iterator<Integer> iterator;

    private AsIntStream(Iterator<Integer> iterator) {
        this.iterator = iterator;
    }

    public Iterable<Integer> toIterable() {
        return () -> iterator;
    }

    public static Iterator addElem(int... values) {
        ArrayList<Integer> list = new ArrayList();
        for (int el : values) {
            list.add(el);
        }
        return list.iterator();
    }

    public static IntStream of(int... values) {
        return new AsIntStream(addElem(values));
    }

    @Override
    public Double average() {
        check();
        return (double) sum() / count();
    }

    @Override
    public Integer max() {
        check();
        int res = Integer.MIN_VALUE;
        for (int i : this.toIterable()) {
            if (i > res) {
                res = i;
            }
        }
        return res;
    }

    @Override
    public Integer min() {
        check();
        int res = Integer.MAX_VALUE;
        for (int i : this.toIterable()) {
            if (i < res) {
                res = i;
            }
        }
        return res;
    }

    @Override
    public long count() {
        int size = 0;
        for (int i : toIterable()) {
            size++;
        }
        return size;
    }

    @Override
    public Integer sum() {
        check();
        int suma = 0;
        for (int i : this.toIterable()) {
            suma += i;
        }
        return suma;
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        Iterable<Integer> filtered = () -> new FilterIterator(predicate);
        for (int el:filtered){
            System.out.println(el);
        }
        return new AsIntStream(filtered.iterator());
    }

    @Override
    public void forEach(IntConsumer action) {
        for (int i : this.toIterable()) {
            action.accept(i);
        }
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        Iterable<Integer> mapped = () -> new MapIterator(mapper);
        return new AsIntStream(mapped.iterator());
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        Iterable<Integer> flatted = () -> new FlattenIterator(func);
        return new AsIntStream(flatted.iterator());
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        int val = identity;
        for (int el : this.toIterable()) {
            val = op.apply(identity, el);
        }
        return val;
    }

    @Override
    public int[] toArray() {
        ArrayList<Integer> list = new ArrayList();
        for (int el : toIterable()) {
            list.add(el);
        }
        int[] intList = new int[list.size()];
        int i = 0;
        while (this.iterator.hasNext()) {
            intList[i] = this.iterator.next();
            i++;
        }
        return intList;
    }

    public void check() {
        if (iterator == null || !iterator.hasNext()) {
            throw new IllegalArgumentException("Stream is empty!");
        }
    }

    private class FilterIterator implements Iterator<Integer> {
        private IntPredicate predicate;

        FilterIterator(IntPredicate predicate) {
            this.predicate = predicate;
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public Integer next() {
            System.out.println(iterator.hasNext());
            while (iterator.hasNext()) {
                int value = iterator.next();
                if (predicate.test(value)) {
                    return value;
                }
            }
            return null;
        }
    }

    private class MapIterator implements Iterator<Integer> {
        private IntUnaryOperator action;

        MapIterator(IntUnaryOperator action) {
            this.action = action;
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public Integer next() {
            while (iterator.hasNext()) {
                return action.apply(iterator.next());
            }
            return null;
        }
    }

    private static class StreamIterator implements Iterator<Integer> {
        private int[] values;
        private int i = 0;

        public StreamIterator(int[] values) {
            this.values = values;
        }

        @Override
        public boolean hasNext() {
            return i <= this.values.length;
        }

        @Override
        public Integer next() {
            if (this.hasNext()) {
                i++;
                return this.values[i-1];
            }
            return null;
        }
    }

    private class FlattenIterator implements Iterator<Integer> {
        private IntToIntStreamFunction func;
        private ArrayList<Integer> list;
        private Iterator<Integer> iter;

        FlattenIterator(IntToIntStreamFunction func) {
            this.func = func;
            this.iter = iter;
            this.list = list;
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext() || this.iter.hasNext();
        }

        @Override
        public Integer next() {
            if (iterator.hasNext()) {
                IntStream Intstreamm = this.func.applyAsIntStream(iterator.next());
                for (int el : Intstreamm.toArray()) {
                    this.list.add(el);
                }
                this.iter = this.list.iterator();
            }
            return this.iter.next();
        }
    }

}

