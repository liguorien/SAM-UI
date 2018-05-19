/*
 * ProjectClasspathEditor.java
 *
 * Created on August 13, 2005, 4:48 PM
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

package com.liguorien.samui.project;

import com.l2fprod.common.beans.editor.AbstractPropertyEditor;
import com.l2fprod.common.beans.editor.FixedButton;
import com.l2fprod.common.swing.LookAndFeelTweaks;
import com.liguorien.samui.MainUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.TransferHandler;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author Nicolas Désy
 */
public class ProjectClasspathEditor extends AbstractPropertyEditor {
    
    private ProjectClasspath _classpath;
    
    protected JTextField textfield;
    private JButton button;
    
    public ProjectClasspathEditor() {
        editor = new JPanel(new BorderLayout(0, 0));
        ((JPanel)editor).add("Center", textfield = new JTextField());
        ((JPanel)editor).add("East", button = new FixedButton());
        textfield.setBorder(LookAndFeelTweaks.EMPTY_BORDER);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectFile();
            }
        });
        
        textfield.setTransferHandler(new FileTransferHandler());
    }
    
    class FileTransferHandler extends TransferHandler {
        
        public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
            for (int i = 0, c = transferFlavors.length; i < c; i++) {
                if (transferFlavors[i].equals(DataFlavor.javaFileListFlavor)) {
                    return true;
                }
            }
            return false;
        }
        
        public boolean importData(JComponent comp, Transferable t) {
            try {                
                final List list = (List)t.getTransferData(DataFlavor.javaFileListFlavor);
                final int size = list.size();
                
                if(size > 0){
                    for(int i=0; i<size; i++){
                        _classpath.add((File)list.get(i));
                    }
                    textfield.setText(_classpath.toString());
                    firePropertyChange(_classpath, _classpath);
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }
    
    public Object getValue() {
        return _classpath;
    }
    
    public void setValue(Object value) {
        
        if (value instanceof ProjectClasspath) {
            _classpath = (ProjectClasspath) value;
        } else {
            _classpath = new ProjectClasspath();
        }
        textfield.setText(_classpath.toString());
    }
    
    protected void selectFile() {
        
        final ProjectClasspathDialog dialog = new ProjectClasspathDialog(_classpath);
        
        dialog.setVisible(true);
        
        if(dialog.isCommited()){
            final ProjectClasspath old = _classpath;
            _classpath = dialog.getClasspath();
            firePropertyChange(old, _classpath);
        }    
    } 
}