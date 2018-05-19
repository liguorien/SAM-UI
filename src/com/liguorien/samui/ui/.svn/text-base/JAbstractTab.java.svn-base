/*
 * JAbstractTab.java
 *
 * Created on July 25, 2005, 12:10 PM
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
import javax.swing.Icon;
import javax.swing.JPopupMenu;

/**
 *
 * @author Nicolas Désy - http://www.liguorien.com/blog/
 */
public abstract class JAbstractTab implements JTab {
    
    private boolean _opened = false;
    private String _label = null;
    private JTabbedPanel _panel = null;
    private String _id = null;
    
    public void close() {
        _opened = false;
         _panel.remove(getComponent());
    }

    public JPopupMenu getContextualMenu() {
        return null;
    }

    public Icon getIcon() {
        return null;
    }

    public String getId() {
        return _id;
    }

    public String getLabel() {
        return _label;
    }

    public JTabbedPanel getTabbedPanel() {
        return _panel;
    }

    public boolean isOpen() {
        return _opened;
    }

    public void open() {
        _opened = true;
        
    }

    public void requestFocus() {
        if(!_opened){
            _panel.addTab(this);
        }
        _panel.setSelectedComponent(getComponent());
    }

    public void setId(String id) {
        _id = id;
    }

    public void setLabel(String label) {
        _label = label;
        if(_panel != null){
            _panel.setTitleAt(_panel.indexOfComponent(getComponent()), label);
        }
    }

    public void setTabbedPanel(JTabbedPanel panel) {
        _panel = panel;
    }    
}
