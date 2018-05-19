/*
 * CompileProjectListener.java
 *
 * Created on July 1, 2005, 7:19 PM
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

package com.liguorien.samui.project;

import com.liguorien.utils.XmlUtil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Nicolas Désy - http://www.liguorien.com/blog/
 */
public class CompileProjectListener implements ActionListener {    
    
    private boolean _userWantToRunSWF = false;
    
    public CompileProjectListener(){
        this(false);
    }
    
    public CompileProjectListener(boolean runSWF){
        _userWantToRunSWF = runSWF;
    }
    
    public void actionPerformed(ActionEvent evt){
        ProjectConfig.getInstance().compileProject(_userWantToRunSWF);
    }       
}
