/*
 * Menu.java
 *
 * Created on June 22, 2005, 10:56 PM
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

import com.l2fprod.common.swing.plaf.LookAndFeelAddons;
import com.liguorien.samui.project.ProjectConfig;
import com.liguorien.samui.project.ProjectConfigView;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import com.liguorien.samui.MainUI;


/**
 *
 * @author Nicolas Désy - http://www.liguorien.com/blog/
 */
public final class Menu extends JMenuBar {
    
    private JMenu _fileMenu;
    private JMenuItem _newMenuItem;
    private JMenuItem _openMenuItem;
    private JMenuItem _exitMenuItem;
    
    
    /** Creates a new instance of Menu */
    public Menu() {
        super();
        
        _fileMenu = new JMenu();
        _fileMenu.setText("File");
        
        _newMenuItem = new JMenuItem("New...");
        _newMenuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                
                JFileChooser chooser = MainUI.getInstance().getFileChooser();
                
                synchronized(chooser.getTreeLock()){
                    chooser.setDialogTitle("Choose project directory");
                    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    int returnVal = chooser.showOpenDialog(MainUI.getInstance());
                    
                    if(returnVal == JFileChooser.APPROVE_OPTION) {
                        ProjectConfig.getInstance().initProject(chooser.getSelectedFile());
                    }
                }
            }
        });
        _openMenuItem = new JMenuItem("Open...");
        _openMenuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                
                JFileChooser chooser = MainUI.getInstance().getFileChooser();
                
                synchronized(chooser.getTreeLock()){
                    chooser.setDialogTitle("Choose project directory");
                    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    int returnVal = chooser.showOpenDialog(MainUI.getInstance());
                    
                    if(returnVal == JFileChooser.APPROVE_OPTION) {
                        ProjectConfig.getInstance().openProject(chooser.getSelectedFile());
                    }
                }
            }
        }); 
        
        _exitMenuItem = new JMenuItem("Exit...");
        _exitMenuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                System.exit(0);
            }
        });
        
        _fileMenu.add(_newMenuItem);
        _fileMenu.add(_openMenuItem);    
        _fileMenu.add(new JSeparator());
        _fileMenu.add(_exitMenuItem);
   
        
        add(_fileMenu);  
    }
}
