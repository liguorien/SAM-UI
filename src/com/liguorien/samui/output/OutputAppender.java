/*
 * OutputAppender.java
 *
 * Created on July 4, 2005, 7:11 PM
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

package com.liguorien.samui.output;

import org.w3c.dom.Document;

/**
 *
 * @author Nicolas Désy - http://www.liguorien.com/blog/
 */
public class OutputAppender implements Runnable{
    
    private Document _msg;
    private OutputTab _tab;
    
    public OutputAppender(OutputTab tab, Document msg){
        _msg = msg;
        _tab = tab;
    }
    
    public void run(){    
        _tab.append(_msg);
    }    
}
