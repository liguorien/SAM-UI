/*
 * StackElement.java
 *
 * Created on July 11, 2005, 7:58 PM
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

import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Nicolas Désy - http://www.liguorien.com/blog/
 */
public class StackElement extends DefaultMutableTreeNode implements Comparable<StackElement> {
    
    public static int nbInstance = 0;
    
    public int time = -1;
    public int classID;
    public int methodID;    
    public String className;
    public String methodName;    
    
    private int _subInvocations = 0;
    private StackElement _parent;
    private String _result = null;
    
    public boolean isOpen = true;
    
    public StackElement(StackElement parent){
        super();
        _parent = parent;
        if(_parent != null){
            _parent.add(this);
            _parent.notifyInvocation();
        }
        nbInstance++;
    }
    
    public StackElement getParentStack(){
        return _parent;
    }
    
    private void notifyInvocation(){
        _subInvocations++;
        if(_parent != null){
            _parent.notifyInvocation();
        }
    }
    
    public int getSubInvocation(){
        return _subInvocations;
    }
    
    public String toString(){
        if(_result == null){
            _result = className + '.' + methodName + " (" + time + "ms)";
        }
        return _result;
    }
    
    public int compareTo(StackElement el) {
        if(el.time < time) return 1; 
        if(el.time > time) return -1;
        return 0;
    }
}
