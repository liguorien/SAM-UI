/*
 * SWFViewer.java
 *
 * Created on August 12, 2005, 2:00 PM
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

package com.liguorien.samui.ui;


import com.liguorien.samui.ExceptionLogger;
import com.liguorien.samui.library.LibrarySymbolPanel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import org.jdesktop.jdic.browser.*;

/**
 *
 * @author Nicolas D�sy
 */
public class SWFViewer extends JAbstractTab {
    
    private WebBrowser _browser = null;
    private Dimension _minSize = new Dimension(10,10);
    private File _swf = null;
    
    public SWFViewer(File swf){
        _swf = swf;
        setLabel(swf.getName());
    }
    
    public Component getComponent() {
        if(_browser == null){
            try{                
                _browser = new WebBrowser(_swf.toURL()){
                    public Dimension getMinimumSize(){
                        return _minSize;
                    }
                };             
            }catch(Exception ex){
                ExceptionLogger.log(ex);
            }
        }
        return _browser;
    }
    
    public void setFile(File file){
        setLabel(file.getName());
        try{
            _browser.setURL(file.toURL());
        }catch(Exception ex){
            ExceptionLogger.log(ex);
        }
    }
    
    private static Icon _icon = null;
    static {
        try{
            _icon = new ImageIcon(LibrarySymbolPanel.class.getResource("/com/liguorien/samui/files/swf-icon.gif"));
        }catch(Exception ex){
            ExceptionLogger.log(ex);
        }
    }
    
    public Icon getIcon() {       
        return _icon;
    }
    
    public JPopupMenu getContextualMenu() {
        return new RightClickMenu();
    }
    
    class RightClickMenu extends JPopupMenu{
        
        private JMenuItem _close;
        
        public RightClickMenu(){
            super("");
            setLightWeightPopupEnabled(false);
            add(_close = new JMenuItem("Close"));
            
            _close.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent evt){                   
                    close();
                     _browser.setVisible(false);
                }
            });
        }
    }
}
