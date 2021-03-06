package ua.edu.ucu.stream;

import ua.edu.ucu.function.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class AsIntStream implements IntStream {
    private Iterator<Integer> iterator;

    private AsIntStream(Iterator<Integer> iterator) {
        this.iterator = iterator;
    }

    public Iterable<Integer> toIterable() {
        return () -> iterator;
    }

    public static Iterator<Integer> addElem(int... values) {
        ArrayList<Integer> list = new ArrayList<>();
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
        int sum = 0;
        int counter = 0;
        for (int elem : toIterable()) {
            sum += elem;
            counter += 1;
        }
        return (double) sum / counter;
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
        int counter = 0;
        for (int i : this.toIterable()) {
            counter++;
        }
        return counter;
    }

    @Override
    public Integer sum() {
        check();
        int sum = 0;
        for (int i : this.toIterable()) {
            sum += i;
        }
        return sum;
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        check();
        ArrayList<Integer> list = new ArrayList<>();
        for (int el : this.toIterable()) {
            if (predicate.test(el)) {
                list.add(el);
            }
        }

        return new AsIntStream(list.iterator());
    }

    @Override
    public void forEach(IntConsumer action) {
        for (int i : this.toIterable()) {
            action.accept(i);
        }
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        check();
        return new AsIntStream(new MapIterator(mapper));
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        check();
        return new AsIntStream(new FlattenIterator(func));
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        check();
        while (this.iterator.hasNext()) {
            identity = op.apply(identity, this.iterator.next());
        }
        return identity;
    }

    @Override
    public int[] toArray() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int el : toIterable()) {
            list.add(el);
        }
        int[] intList = new int[list.size()];
        int i = 0;
        for (int elem : list) {
            intList[i] = elem;
            i++;
        }
        return intList;
    }

    public void check() {
        if (!iterator.hasNext()) {
            throw new IllegalArgumentException();
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

    private class FlattenIterator implements Iterator<Integer> {
        private IntToIntStreamFunction func;
        private ArrayList<Integer> list;
        private Iterator<Integer> iter;

        FlattenIterator(IntToIntStreamFunction func) {
            this.func = func;
            this.iter = Collections.emptyIterator();
            this.list = new ArrayList<Integer>();
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext() || this.iter.hasNext();
        }

        @Override
        public Integer next() {
            while (iterator.hasNext()) {
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

