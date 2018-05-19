/*
 * ClassTableRenderer.java
 *
 * Created on August 3, 2005, 7:54 PM
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
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Nicolas Désy - http://www.liguorien.com/blog/
 */
public class ExecutionTimeTableCell extends JComponent  implements TableCellRenderer {
    
    private int _time;
    
    public ExecutionTimeTableCell(){
        super();
    }
    
    public void paint(Graphics g) {
        g.setColor(StackElementIcon.getColor(_time));
        g.fillRect(0, 0, getWidth(), getHeight());        
    }
    
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        _time = ((Integer)value).intValue();
        return this;
    }
}


