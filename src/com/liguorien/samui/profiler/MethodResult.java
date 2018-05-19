/*
 * MethodResult.java
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

/**
 *
 * @author Nicolas Désy - http://www.liguorien.com/blog/
 */
public class MethodResult extends AbstractResult<StackElement>{
        
        private int minimumTime = Integer.MAX_VALUE;
        private int maximumTime = Integer.MIN_VALUE;
        private int averageTime = -1;
        private String _string = null;
        private ProfilerStats _stats;
        
        public MethodResult(ProfilerStats stats, int classID, int methodID){
            super(classID, methodID);
            _stats = stats;
        }
        
        public void addStackElement(StackElement el){
            addResult(el);
            if(el.time < minimumTime) minimumTime = el.time;
            if(el.time > maximumTime) maximumTime = el.time;
        }
        
        public int getMinimumTime(){
            return minimumTime;
        }
        
        public int getMaximumTime(){
            return maximumTime;
        }
        
        public int getAverageTime(){
            if(averageTime == -1){
                int temp = 0;
                for(StackElement el : _results){
                    temp += el.time;
                }
                averageTime = Math.round(temp/_results.size());
            }
            return averageTime;
        }
        
        public int getInvocationNumber(){
            return _results.size();
        }
        
        public String toString(){
            if(_string == null){
               _string =  _stats.getLabel(classID) + '.' + _stats.getLabel(methodID);
            }
            return _string;
        }
    }