/*
 * JTab.java
 *
 * Created on July 24, 2005, 11:48 AM
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

package com.liguorien.samui.ui;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JPopupMenu;

/**
 *
 * @author Nicolas Désy - http://www.liguorien.com/blog/
 */
public interface JTab {
    
    public String getId();
    public void setId(String id);
    
    public String getLabel();
    public void setLabel(String label);
    
    public boolean isOpen();
    public void open();
    public void close();
    public void requestFocus();
    
    public Icon getIcon();
    public JPopupMenu getContextualMenu();
    public Component getComponent();   
    
    public void setTabbedPanel(JTabbedPanel panel);
    public JTabbedPanel getTabbedPanel();   
    
}
