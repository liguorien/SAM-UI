/*
 * StackTabelModel.java
 *
 * Created on August 5, 2005, 1:25 AM
 *
 * Copyright 2005  Nicolas Désy.  All rights reserved.
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
import java.util.*;
import javax.swing.table.*;
/**
 *
 * @author Nicolas Désy - http://www.liguorien.com/blog/
 */
public class StackTableModel  extends AbstractTableModel {
    
    private List<StackElement> _stacks;
    private List<StackElement> _results;
    
    private int _currentClassID = -1;
    private int _currentMethodID = -1;
    
    /** Creates a new instance of ClassTableModel */
    public StackTableModel(List<StackElement> m) {
        super();
        _results = _stacks = m;
    }
    
    public int getColumnCount() {
        return 4;
    }
    
    public int getRowCount() {
        return _results.size();
    }
    
    public Object getValueAt(int index, int column) {
        if(column==0 || column==2) return _results.get(index).time;
        if(column==3) return _results.get(index).getSubInvocation();
        return _results.get(index).toString();
    }
    
    public String getColumnName(int column) {
        switch(column){
            case 0 : return " ";
            case 1 : return "method name";
            case 2 : return "time";
            case 3 : return "sub invocation";
        }
        return " ";
    }
    
    public synchronized void filterStack(int classID, int methodID){
        if(_currentMethodID != methodID || _currentClassID != classID){
            _results = new ArrayList<StackElement>();
            
            for(StackElement result : _stacks){
                if(result.methodID == methodID && result.classID == classID){
                    _results.add(result);
                }
            }
            _currentMethodID = methodID;
            _currentClassID = classID;
            fireTableDataChanged();
        }
    }
    
    public StackElement getResultAt(int index){
        return  _results.get(index);
    }
    
    private int lastSortedIndex = -1;
    
    public void sortByColumnIndex(int index){
        synchronized(_results){
            if(lastSortedIndex != index){
                switch(index){
                    case 0 : Collections.sort(_results, ProfilerStats.STACK_TIME_COMPARATOR); break;
                    case 1 : Collections.sort(_results, ProfilerStats.STACK_NAME_COMPARATOR); break;
                    case 2 : Collections.sort(_results, ProfilerStats.STACK_TIME_COMPARATOR); break;
                    case 3 : Collections.sort(_results, ProfilerStats.STACK_INVOCATION_COMPARATOR); break;
                }
            }else{
                Collections.reverse(_results);
            }            
            lastSortedIndex = index;
        }
        fireTableDataChanged();
    }
}
