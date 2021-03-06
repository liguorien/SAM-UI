/*
 * AbstractResult.java
 *
 * Created on July 31, 2005, 1:07 PM
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
 *   SAM-UI is distributed in the hope that it will be useful,
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

/**
 *
 * @author Nicolas D�sy - http://www.liguorien.com/blog/
 */
public abstract class AbstractResult<T> {
    
    public int classID;
    public int methodID;
    
    protected final List<T> _results = new ArrayList<T>();
    
    public AbstractResult(int classID, int methodID){
        this.classID = classID;
        this.methodID = methodID;
    }
    
    public void addResult(T result){
        _results.add(result);
    }
    
    public List<T> getResults(){
        return _results;
    }
    
    public abstract int getMaximumTime();
    
    public abstract void addStackElement(StackElement el);
}
