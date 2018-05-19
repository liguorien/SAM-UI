/*
 * JTabbedPanel.java
 *
 * Created on July 24, 2005, 11:39 AM
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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;

/**
 *
 * @author Nicolas Désy - http://www.liguorien.com/blog/
 */

public class JTabbedPanel extends JTabbedPane implements MouseListener {
    
    private final Map<Component, JTab> _tabs = new HashMap<Component, JTab>();
    
    /** Creates a new instance of OutputPanel */
    public JTabbedPanel() {
        super();
        addMouseListener(this);
    }
    
    public JTab addTab(JTab tab) {
        if(tab.getIcon() == null){
            super.addTab(tab.getLabel(), tab.getComponent());
        }else{
            super.addTab(tab.getLabel(), tab.getIcon(), tab.getComponent());
        }
        tab.setTabbedPanel(this);
        _tabs.put(tab.getComponent(), tab);
        tab.open();
        return tab;
    }
    
    public void mousePressed(MouseEvent evt) {
        handleRightClick(evt);
    }
    
    public void mouseReleased(MouseEvent evt) {
        handleRightClick(evt);
    }
    
    private void handleRightClick(MouseEvent evt){
        if (evt.isPopupTrigger()) {
            
            final int x = evt.getX();
            final int y = evt.getY();
            final int index = indexAtLocation(x,y);
            
            if(index != -1){
                final JPopupMenu menu =  _tabs.get(getComponentAt(index)).getContextualMenu();
                if(menu != null){
                    menu.show(this, x, y);
                }
            }
        }
    }
    
    public void mouseClicked(MouseEvent evt) {}
    public void mouseEntered(MouseEvent evt) {}
    public void mouseExited(MouseEvent evt) {}
}
