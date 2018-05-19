/*
 * SwingUtil.java
 *
 * Created on July 31, 2005, 6:32 PM
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

package com.liguorien.utils;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Nicolas Désy
 */
public class SwingUtil {
    
    public final static void adjustColumnToPreferredWidth(JTable table){
        
        final int rowLength = table.getRowCount();
        final int columnLength = table.getColumnCount();
        final TableColumnModel model = table.getColumnModel();
        
        for(int column=0; column<columnLength; column++){
            int max = 0;
            for(int row=0; row<rowLength; row++){
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Object value = table.getValueAt(row, column);
            }
        }
    }
    
}
