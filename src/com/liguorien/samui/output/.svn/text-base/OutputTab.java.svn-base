/*
 * OutputTab.java
 *
 * Created on July 4, 2005, 9:39 PM
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

package com.liguorien.samui.output;

import com.liguorien.samui.ExceptionLogger;
import com.liguorien.samui.ui.JAbstractTab;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseMotionListener;
import java.util.regex.Pattern;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.*;
import org.jdesktop.jdic.desktop.Desktop;
import org.w3c.dom.NamedNodeMap;
import java.awt.event.*;
import java.awt.*;
import java.io.*;



/**
 *
 * @author Nicolas Désy - http://www.liguorien.com/blog/
 */
public class OutputTab extends JAbstractTab implements MouseMotionListener{
    
    private JScrollPane _scroll = null;
    private JTextPane _output = null;
    private RightClickMenu _menu = null;
    
    
    private final static Pattern classPattern = Pattern.compile(".*?\\.?(\\w+)::(\\w+)");
    
    private final static SimpleAttributeSet classStyle =
            new SimpleAttributeSet(StyleContext.getDefaultStyleContext().addAttribute(
            SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.decode("0x000077")));
    
    private final static SimpleAttributeSet textStyle =
            new SimpleAttributeSet(StyleContext.getDefaultStyleContext().addAttribute(
            SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.BLACK));
    
    
    /** Creates a new instance of MtascOutput */
    public OutputTab(String id, String label) {
        setId(id);
        setLabel(label);
    }
    
    public Component getComponent() {
        
        if(_scroll == null){
            _output = new JTextPane();
            _output.setEditorKit(new OutputEditorKit());
            _output.setEditable(false);
            _output.addMouseMotionListener(this);        
            _scroll =  new JScrollPane(_output);
        }
        
        return _scroll;
    }
    
    public void clear(){
        _output.setText("");
    }
    
    public void append(org.w3c.dom.Document dom) {
        
        try {
            
            final NamedNodeMap atts = dom.getFirstChild().getAttributes();
            final String fileName = atts.getNamedItem("f").getNodeValue() + " (Line " + atts.getNamedItem("l").getNodeValue() + ')' ;
            
            classStyle.addAttribute("filePath", atts.getNamedItem("f").getNodeValue());
            classStyle.addAttribute("fileName", fileName);
            textStyle.addAttribute("fileName", fileName);
            
            final Document doc = _output.getDocument();
            
            doc.insertString(doc.getLength(), '[' +
                    classPattern.matcher( atts.getNamedItem("c").getNodeValue() ).replaceAll("$1.$2")
                    + "] ",  classStyle);
            
            doc.insertString(doc.getLength(), dom.getFirstChild().getFirstChild().getNodeValue() + '\n', textStyle);
            _output.setCaretPosition(doc.getLength());
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public JPopupMenu getContextualMenu() {
        if(_menu == null){
            _menu =  new RightClickMenu() ;
        }
        return _menu;
    }
    
    public void mouseDragged(MouseEvent evt) {}
    
    public void mouseMoved(MouseEvent evt) {
        
        final Point pt = new Point(evt.getX(), evt.getY());
        final int index = _output.viewToModel(pt);
        
        if (index >= 0) {
            final Document doc = _output.getDocument();
            if (doc instanceof DefaultStyledDocument) {
                
                final DefaultStyledDocument hdoc = (DefaultStyledDocument) doc;
                final String href = (String) hdoc.getCharacterElement(index).getAttributes().getAttribute("fileName");
                
                if (href != null) {
                    _output.setToolTipText(href);
                } else {
                    _output.setToolTipText(null);
                }
            }
        } else {
            _output.setToolTipText(null);
        }
    }
    
    class RightClickMenu extends JPopupMenu{
        
        private JMenuItem _close;
        private JMenuItem _clear;
        
        public RightClickMenu(){
            super();
            
            add(_close = new JMenuItem("Close"));
            add(_clear = new JMenuItem("Clear"));
            
            _close.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent evt){
                    close();
                    clear();
                }
            });
            
            _clear.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent evt){
                    clear();
                }
            });
        }
    }
}
