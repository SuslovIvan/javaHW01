package ru.otus;

import java.util.*;

public class DIYArrayList<T> implements List<T> {

    private static final int DEFAULT_EMPTY_SIZE = 25;

    private Object[] items;
    private int size = 0;

    public DIYArrayList() {
        this(DEFAULT_EMPTY_SIZE);
    }

    public DIYArrayList(int defaultSize) {
        items = new Object[defaultSize];
    }

    public DIYArrayList(Collection<? extends T> c) {
        if (c == null) {
            throw new NullPointerException();
        }
        items = c.toArray();
        size = c.size();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<T> iterator() {
        return new DIYIterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(items, size);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < size) {
            return (T1[]) Arrays.copyOf(items, size, a.getClass());
        }
        System.arraycopy(items, 0, a, 0, size);
        return a;
    }

    @Override
    public boolean add(T t) {
        if (size >= items.length || size <= 0) {
            items = expand();
        }

        items[size] = t;
        size++;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T get(int index) {
        Objects.checkIndex(index, size);
        return (T) items[index];
    }

    @Override
    public T set(int index, T element) {
        Objects.checkIndex(index, size);
        T oldElement = get(index);
        items[index] = element;
        return oldElement;
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T remove(int index) {
        Objects.checkIndex(index, size);

        final T e = get(index);
        final Object[] elementArray = items;
        if (size - 1 > index) {
            System.arraycopy(elementArray, index + 1, elementArray, index, size - index - 1);
        }
        size--;
        return e;
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new DIYIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    private Object[] expand() {
        return expand(items.length + items.length / 2);
    }

    private Object[] expand(int size) {
        return Arrays.copyOf(items, size);
    }

    private class DIYIterator implements ListIterator<T> {

        int cursor = 0;
        int lastReturned = -1;

        public DIYIterator() {}

        public DIYIterator(int index) {
            cursor = index;
        }

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public T next() {
            if (cursor >= size) {
                throw new NoSuchElementException();
            }

            T e = get(cursor);

            lastReturned = cursor;
            cursor++;

            return e;
        }

        @Override
        public boolean hasPrevious() {
            return cursor != 0;
        }

        @Override
        public T previous() {
            int index = cursor - 1;
            if (index < 0) {
                throw new NoSuchElementException();
            }
            if (index >= items.length) {
                throw new ConcurrentModificationException();
            }
            cursor = index;
            return (T) items[index];
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void remove() {
            if (lastReturned < 0) {
                throw new IllegalStateException();
            }
            DIYArrayList.this.remove(lastReturned);
            cursor = lastReturned;
            lastReturned--;
        }

        @Override
        public void set(T t) {
            if (lastReturned< 0) {
                throw new IllegalStateException();
            }

            DIYArrayList.this.set(lastReturned, t);
        }

        @Override
        public void add(T t) {
            throw new UnsupportedOperationException();
        }
    }
}
