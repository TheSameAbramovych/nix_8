package ua.com.alevel.util;

import java.util.Objects;

public class MathSet<N extends Number> {

    private N[] numbers;
    private int pointer = 0;

    public MathSet() {
        this(10);
    }

    public MathSet(int capacity) {
        numbers = createNumbersArray(capacity);
    }

    @SafeVarargs
    public MathSet(N... numbers) {
        this(numbers.length);
        this.pointer = numbers.length;
        System.arraycopy(numbers, 0, this.numbers, 0, numbers.length);
    }

    @SafeVarargs
    public MathSet(N[]... numbers) {
        for (N[] number : numbers) {
            add(number);
        }
    }

    public MathSet(MathSet<N> numbers) {
        join(numbers);
    }

    @SafeVarargs
    public MathSet(MathSet<N>... numbers) {
        for (MathSet<N> number : numbers) {
            join(number);
        }
    }

    public void add(N n) {
        if (pointer == numbers.length) {
            copyToNewArray();
        }
        numbers[pointer] = n;
        pointer++;
    }

    @SafeVarargs
    public final void add(N... numbers) {
        copyToNewArray(numbers.length + pointer);
        System.arraycopy(numbers, 0, this.numbers, pointer, numbers.length);
        pointer += numbers.length;
    }

    public void join(MathSet<N> ms) {
//        Number[] numbers1 = ms.toArray();
//
//        int capacity = numbers1.length + pointer;
//        N[] joinArray = createNumbersArray(capacity);
//
//        System.arraycopy(numbers,0, joinArray, 0, pointer);
//        System.arraycopy(numbers1, 0, joinArray, pointer, numbers1.length);
//        numbers = joinArray;
//        pointer = capacity;

        add((N[]) ms.toArray());
    }

    @SafeVarargs
    public final void join(MathSet<N>... ms) {
        for (MathSet<N> m : ms) {
            join(m);
        }
    }

    public void intersection(MathSet<N> ms) {
        clear((N[]) ms.toArray());
    }

    @SafeVarargs
    public final void intersection(MathSet<N>... ms) {
        for (MathSet<N> m : ms) {
            intersection(ms);
        }
    }

    public void sortDesc() {
        sortDesc(0, pointer);
    }

    public void sortDesc(int firstIndex, int lastIndex) {
        if (lastIndex >= pointer) {
            lastIndex = pointer - 1;
        }
        for (int i = firstIndex; i <= lastIndex; i++) {
            N max = numbers[i];
            int minId = i;
            for (int j = i + 1; j <= lastIndex; j++) {
                if (max.doubleValue() < numbers[j].doubleValue()) {
                    max = numbers[j];
                    minId = j;
                }
            }
            N temp = numbers[i];
            numbers[i] = max;
            numbers[minId] = temp;
        }
    }

    public void sortDesc(N value) {
        for (int i = 0; i < pointer; i++) {
            if (Objects.equals(value, numbers[i])) {
                sortDesc(i, pointer);
                break;
            }
        }
    }

    public void sortAsc() {
        sortAsc(0, pointer);
    }

    public void sortAsc(int firstIndex, int lastIndex) {
        if (lastIndex >= pointer) {
            lastIndex = pointer - 1;
        }
        for (int i = firstIndex; i <= lastIndex; i++) {
            N min = numbers[i];
            int minId = i;
            for (int j = i + 1; j <= lastIndex; j++) {
                if (min.doubleValue() > numbers[j].doubleValue()) {
                    min = numbers[j];
                    minId = j;
                }
            }
            N temp = numbers[i];
            numbers[i] = min;
            numbers[minId] = temp;
        }
    }

    public void sortAsc(N value) {
        for (int i = 0; i < pointer; i++) {
            if (Objects.equals(value, numbers[i])) {
                sortAsc(i, pointer);
                break;
            }
        }
    }

    public N get(int index) {
        if (index >= pointer) {
            return null;
        }
        return numbers[index];
    }

    public N getMax() {
        if (pointer <= 0) {
            return null;
        }
        N max = numbers[0];
        for (int i = 0; i < pointer; i++) {
            max = max.doubleValue() < numbers[i].doubleValue() ? numbers[i] : max;
        }
        return max;
    }

    public N getMin() {
        if (pointer <= 0) {
            return null;
        }
        N min = numbers[0];
        for (int i = 0; i < pointer; i++) {
            min = min.doubleValue() > numbers[i].doubleValue() ? numbers[i] : min;
        }
        return min;
    }

    public double getAverage() {
        double sum = 0;

        for (int i = 0; i < pointer; i++) {
            sum += numbers[i].doubleValue();

        }
        return sum / pointer;
    }

    public double getMedian() {
        if (pointer == 0) {
            return 0;
        }
        int index = pointer / 2;
        if (pointer % 2 == 0) {
            return (get(index).doubleValue() + get(index - 1).doubleValue()) / 2;
        }
        return get(index).doubleValue();
    }

    public Number[] toArray() {
        return toArray(0, pointer);
    }

    public Number[] toArray(int firstIndex, int lastIndex) {
        if (firstIndex < 0 || firstIndex > lastIndex) {
            firstIndex = 0;
        }
        if (lastIndex > pointer) {
            lastIndex = pointer;
        }

        var size = lastIndex - firstIndex;
        N[] numb = createNumbersArray(size);
        int p = 0;
        for (int i = firstIndex; i < lastIndex; i++) {
            numb[p] = numbers[i];
            p++;

        }
        return numb;
    }

    public MathSet<N> cut(int firstIndex, int lastIndex) {
        if (lastIndex > pointer) {
            lastIndex = pointer;
        }
        if (firstIndex < 0 || firstIndex > lastIndex) {
            firstIndex = 0;
        }

        N[] numb = createNumbersArray(numbers.length);
        int cutCapacity = lastIndex - firstIndex;
        N[] cutNumb = createNumbersArray(cutCapacity);

        System.arraycopy(this.numbers, firstIndex, cutNumb, 0, cutCapacity);

        System.arraycopy(this.numbers, 0, numb, 0, firstIndex);
        System.arraycopy(this.numbers, lastIndex, numb, firstIndex, pointer - firstIndex - cutCapacity);

        this.numbers = numb;
        pointer -= cutCapacity;

        return new MathSet<>(cutNumb);
    }

    public void clear() {
        numbers = createNumbersArray(numbers.length);
    }

    @SafeVarargs
    public final void clear(N... numbers) {
        for (N number : numbers) {
            for (int i = 0; i < pointer; i++) {
                if (Objects.equals(this.numbers[i], number)) {
                    N[] numb = createNumbersArray(pointer);
                    System.arraycopy(this.numbers, i + 1, numb, i, pointer - i - 1);
                    System.arraycopy(this.numbers, 0, numb, 0, i);
                    this.numbers = numb;
                    pointer--;
                    i--;
                }
            }
        }
    }

    private void copyToNewArray() {
        copyToNewArray((numbers.length * 3 / 2) + 1);
    }

    private void copyToNewArray(int capacity) {
        if (capacity > numbers.length) {
            N[] entitiesTmp = numbers;
            this.numbers = createNumbersArray(capacity);
            System.arraycopy(entitiesTmp, 0, this.numbers, 0, pointer);
        }
    }

    private N[] createNumbersArray(int capacity) {
        return (N[]) new Number[capacity];
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < pointer; i++) {
            stringBuilder.append(numbers[i]).append(", ");
        }
        return stringBuilder.toString();
    }
}
