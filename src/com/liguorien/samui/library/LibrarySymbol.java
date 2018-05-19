/*
 * LibrarySymbol.java
 *
 * Created on July 3, 2005, 10:26 AM
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

package com.liguorien.samui.library;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Nicolas D�sy - http://www.liguorien.com/blog/
 */
public class LibrarySymbol extends DefaultMutableTreeNode {
    
    private String _path = null;
    private String _class = null;
    
    /** Creates a new instance of LibraryItem */
    public LibrarySymbol(String linkage, String path, String className) {
        super(linkage);  
        _path = (path != null) ? path : "";
        _class = (className != null) ? className : "";
    }        
    
    public String getLinkage(){
        return (String) super.getUserObject();
    }
    
    public String getFilePath(){
        return _path;
    }
    
    public void setLinkage(String linkage){
        setUserObject(linkage);       
    }
    
    public void setFilePath(String path){
        _path = path;
        //wotkaround to notify changes to LibraryModel
      //  setLinkage(getLinkage());
    }
    
    public void setClassName(String name){
        _class = name;
        //setLinkage(getLinkage());
    }
    
    public String getClassName(){
        return _class;
    }
}
