/*
 * ClassTableModel.java
 *
 * Created on July 31, 2005, 6:00 PM
 *
 * Copyright 2005  Nicolas D�sy.  All rights reserved.
 *
 *   This file is part of SAM-UI
 *
 *   SAM-UI is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   Foobar is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with SAM-UI; if not, write to the Free Software
 *   Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package com.liguorien.samui.profiler;

import javax.swing.table.AbstractTableModel;
import java.util.*;
/**
 *
 * @author Nicolas D�sy - http://www.liguorien.com/blog/
 */
public class ClassTableModel extends AbstractTableModel {
    
    private List<ClassResult> _classes;
    
    /** Creates a new instance of ClassTableModel */
    public ClassTableModel(List<ClassResult> classes) {
        super();
        _classes = classes;
    }
    
    public int getColumnCount() {
        return 2;
    }
    
    public int getRowCount() {
        return _classes.size();
    }
    
    public Object getValueAt(int index, int column) {
        if(column==0) return _classes.get(index).getMaximumTime();
        return _classes.get(index).toString();
    }
    
    public String getColumnName(int column) {
        if(column==1) return "class name";
        return " ";
    }
    
    public List<ClassResult> getClasses(){
        return  _classes;
    }
    
    public ClassResult getResultAt(int index){
        if(index == -1) return null;
        return  _classes.get(index);
    }
    
    //  private final static Comparator<ClassResult>[] = {
    //      ProfilerStats.CLASS_MAXIMUM_COMPARATOR;
    //   };
    
    private int lastSortedIndex = -1;
    
    public void sortByColumnIndex(int index){
        try{
            synchronized(_classes){
                if(lastSortedIndex != index){
                    switch(index){
                        case 0 : Collections.sort(_classes, ProfilerStats.CLASS_MAXIMUM_COMPARATOR); break;
                        case 1 : Collections.sort(_classes, ProfilerStats.NAME_COMPARATOR); break;
                    }
                }else{
                    Collections.reverse(_classes);
                }
                lastSortedIndex = index;
            }
            fireTableDataChanged();
        }catch(NullPointerException ex){
            ex.printStackTrace();
        }
    }
}
