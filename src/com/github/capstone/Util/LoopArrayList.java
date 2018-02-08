package com.github.capstone.Util;

import java.util.ArrayList;

public class LoopArrayList<T>
{
    private ArrayList<T> list;
/**
@LoopArrayList
This method creates a new arraylist named list. 
@param none
@return none 
@throws none
*/ 

    public LoopArrayList()
    {
        list = new ArrayList<>();
    }
/**
@add
This method adds an entry to the list arraylist. 
@param t A T value
@return none 
@throws none
*/ 
    public void add(T t)
    {
        list.add(t);
    }
/**
@getNextOption
This method creates an index that corresponds to the current selection of the arraylist, and gathers the next option in the arraylist. Finally, it returns the information at the index created above. 
@param current A T value
@return list.get(index) The information at the index gathered. 
@throws none
*/ 
    public T getNextOption(T current)
    {
        int index = list.lastIndexOf(current);
        index = index + 1 >= list.size() ? 0 : index + 1;
        return list.get(index);
    }
/**
@remove
This method removes the index found at t. 
@param t An index of the list arraylist. 
@return none
@throws none
*/ 
    public void remove(T t)
    {
        list.remove(t);
    }
}
