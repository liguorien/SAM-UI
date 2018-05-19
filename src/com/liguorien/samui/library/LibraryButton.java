/*
 * LibraryButton.java
 *
 * Created on July 2, 2005, 2:07 PM
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

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author Nicolas D�sy - http://www.liguorien.com/blog/
 */
public class LibraryButton extends JButton implements MouseListener {
    
    private final static Dimension _dim = new Dimension(22, 22);
    
    /** Creates a new instance of LibraryButton */
    public LibraryButton(String icon, String tooltip) {
        super(new ImageIcon(LibraryButton.class.getResource(icon)));         
        setBorderPainted(false);
        setFocusable(false);
        setOpaque(false);
        setToolTipText(tooltip);
        setPreferredSize(_dim);
        setMaximumSize(_dim);
        addMouseListener(this);
    }    

    public void mouseEntered(MouseEvent mouseEvent) {
        setBorderPainted(true);
        validate();
    }

    public void mouseExited(MouseEvent mouseEvent) {
        setBorderPainted(false);
        validate();
    }

    public void mousePressed(MouseEvent mouseEvent) {}
    public void mouseReleased(MouseEvent mouseEvent) {}    
    public void mouseClicked(MouseEvent mouseEvent) {}  
}
