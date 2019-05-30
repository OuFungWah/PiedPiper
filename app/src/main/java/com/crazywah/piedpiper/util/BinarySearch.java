package com.crazywah.piedpiper.util;

import java.util.Comparator;
import java.util.List;

public abstract class BinarySearch<T> {

    public static final int EQUALS = 0;
    public static final int LESSTHAN = -1;
    public static final int BIGGERTHAN = 1;

    public int binarySearch(T target, List<T> list, int start, int end) {
        if (start == end) {
            return start;
        }
        int mid = start + (end - start) / 2;
        switch (compare(list.get(mid), target)) {
            case EQUALS:
                return mid;
            case BIGGERTHAN:
                return binarySearch(target, list, mid + 1, end);
            case LESSTHAN:
                return binarySearch(target, list, start, mid);
            default:
                return -1;
        }
    }

    public abstract int compare(T obj1, T obj2);

}
