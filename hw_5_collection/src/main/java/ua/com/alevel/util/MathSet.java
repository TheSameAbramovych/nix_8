package ua.com.alevel.util;


public class MathSet<N extends Number> {

    private N[] numbers;
    private int pointer = 0;

    public MathSet() {
        this(10);
    }

    public MathSet(int capacity) {
        numbers = createNumbersArray(capacity);
    }

    public MathSet(N... numbers) {
        this(numbers.length);
        this.pointer = numbers.length;
        System.arraycopy(numbers, 0, this.numbers, 0, numbers.length);
    }

    public MathSet(N[]... numbers) {
    }

    public MathSet(MathSet numbers) {
    }

    public MathSet(MathSet... numbers) {
    }

    public void add(N n) {
        if (pointer == numbers.length) {
            copyToNewArray();
        }
        numbers[pointer] = n;
        pointer++;
    }

    public void add(N... numbers) {
        for (N n : numbers) {
            add(n);
        }
    }

    public void join(MathSet ms) {

    }

    public void join(MathSet... ms) {

    }

    public void intersection(MathSet ms) {

    }

    public void intersection(MathSet... ms) {

    }

    public void sortDesc() {

    }

    public void sortDesc(int firstIndex, int lastIndex) {

    }

    public void sortDesc(N value) {

    }

    public void sortAsc() {

    }

    public void sortAsc(int firstIndex, int lastIndex) {

    }

    public void sortAsc(N value) {

    }

    public N get(int index) {
        if (index >= pointer) {
            return null;
        }
        return numbers[index];
    }

    public N getMax() {
        return null;
    }

    public N getMin() {
        return null;
    }

    public N getAverage() {
        return null;
    }

    public N getMedian() {
        return null;
    }

    public N[] toArray() {
        return null;
    }

    public N[] toArray(int firstIndex, int lastIndex) {
        return null;
    }

    public MathSet cut(int firstIndex, int lastIndex) {
        return null;
    }

    public void clear() {

    }

    public void clear(N[] numbers) {

    }

    private void copyToNewArray() {
        N[] entitiesTmp = numbers;
        this.numbers = createNumbersArray((numbers.length * 3 / 2) + 1);
        System.arraycopy(entitiesTmp, 0, this.numbers, 0, pointer);
    }

    private N[] createNumbersArray(int capacity) {
        return (N[]) new Object[capacity];
    }
}
