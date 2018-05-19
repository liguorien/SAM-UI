/*
 * TitledPanel.java
 *
 * Created on July 31, 2005, 4:37 PM
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

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Nicolas Désy - http://www.liguorien.com/blog/
 */
public class TitledPanel extends JPanel{
    
    
    
    /** Creates a new instance of TitledPanel */
    public TitledPanel(String title, JComponent content) {
        super();
        setLayout(new BorderLayout());
        
        JLabel lbl = new JLabel(' ' + title);
        
        lbl.setPreferredSize(new Dimension(40,24));
        
        add(lbl, BorderLayout.NORTH);
        add(content);        
    }    
}
