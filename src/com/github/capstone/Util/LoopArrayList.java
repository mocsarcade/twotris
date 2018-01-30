package com.github.capstone.Util;

import java.util.ArrayList;

public class LoopArrayList<T>
{
    private ArrayList<T> list;

    public LoopArrayList()
    {
        list = new ArrayList<>();
    }

    public void add(T t)
    {
        list.add(t);
    }

    public T getNextOption(T current)
    {
        int index = list.lastIndexOf(current);
        index = index + 1 >= list.size() ? 0 : index + 1;
        return list.get(index);
    }

    public void remove(T t)
    {
        list.remove(t);
    }
}
