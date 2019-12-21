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
        return  (double)sum() / count();
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
        check();
        return new AsIntStream(new FilterIterator(predicate));
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

    private class FilterIterator implements Iterator<Integer> {
        private IntPredicate predicate;
        private int val;

        FilterIterator(IntPredicate predicate) {
            this.predicate = predicate;
        }

        @Override
        public boolean hasNext() {
            while (iterator.hasNext()) {
                val = iterator.next();
                if (predicate.test(val)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public Integer next() {
            return val;
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

