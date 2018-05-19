/*
 * ClassResult.java
 *
 * Created on July 31, 2005, 1:07 PM
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

import java.util.Collections;

/**
 *
 * @author Nicolas Désy - http://www.liguorien.com/blog/
 */
public class ClassResult extends AbstractResult<MethodResult>{
    
    private ProfilerStats _stats;
    private String _string = null;
    private boolean _sorted = false;
    
    public ClassResult(ProfilerStats stats, int classID){
        super(classID, classID);
        _stats = stats;
    }
    
    public void addStackElement(StackElement el){
        MethodResult method = ProfilerStats.getResultByMethodID(_results, el.methodID);
        if(method == null){
            method = new MethodResult(_stats, classID, el.methodID);
            addResult(method);
            _stats.addMethodResult(method);
        }
        method.addStackElement(el);
    }
    
    public int getTotalMethodInvocation(){
        int total = 0;
        for(MethodResult m : _results){
            total += m.getInvocationNumber();
        }
        return total;
    }
    
    public int getMaximumTime(){
        if(!_sorted){
            _sorted = true;
            Collections.sort(_results, ProfilerStats.METHOD_MAXIMUM_COMPARATOR);
        }
        return _results.get(0).getMaximumTime();
    }
    
    private void _sort(){
        if(!_sorted){
            _sorted = true;
            Collections.sort(_results, ProfilerStats.METHOD_MAXIMUM_COMPARATOR);
        }
    }
    
    public String toString(){
        if(_string == null){
            _string = _stats.getLabel(classID);
        }
        return _string;
    }
}
