/*
 * LibraryFolder.java
 *
 * Created on August 6, 2005, 11:38 AM
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

package com.liguorien.samui.library;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

/**
 *
 * @author Nicolas Désy - http://www.liguorien.com/blog/
 */
public class LibraryFolder  extends DefaultMutableTreeNode {
    
    /** Creates a new instance of LibraryFolder */
    public LibraryFolder(String name) {
        super(name);
    }

    public void add(MutableTreeNode newChild) {
        final String childID = newChild.toString();
        if(childID == null) return;
        final int length = getChildCount();
        for(int i=0; i<length; i++){
            if(childID.equals(getChildAt(i).toString())){                
                return;
            }
        }
    
        super.add(newChild);
    }
}
