/*
 * LibrarySymbolRenderer.java
 *
 * Created on July 3, 2005, 2:04 PM
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

import java.awt.Component;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author Nicolas Désy - http://www.liguorien.com/blog/
 */
public class LibrarySymbolRenderer extends DefaultTreeCellRenderer {
    
    private ImageIcon _folderIcon = null;
    private ImageIcon _symbolIcon = null;
    
    /** Creates a new instance of LibrarySymbolRenderer */
    public LibrarySymbolRenderer() {
        super();
        
        try{
            _folderIcon = loadIcon("library-folder-close.gif");
            _symbolIcon = loadIcon("library-symbol.gif");
            
            setOpenIcon(loadIcon("library-folder-open.gif"));
            setClosedIcon(_folderIcon);
            setClosedIcon(loadIcon("library-folder-close.gif"));
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    private ImageIcon loadIcon(String name) throws IOException {
        return new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/com/liguorien/samui/files/" + name)));
    }
    
    
  
}
