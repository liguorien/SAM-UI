/*
 * ProjectClasspathDialog.java
 *
 * Created on August 14, 2005, 1:12 PM
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

import com.liguorien.samui.MainUI;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Nicolas Désy
 */


class ProjectClasspathDialog extends JDialog {
    
    private JList _list;
    private JButton _add;
    private JButton _remove;
    private JButton _ok;
    private JButton _cancel;
    private List<File> _classpath;
    
    private boolean _commited = false;
    
    public ProjectClasspathDialog(List<File> classpath){
        super(MainUI.getInstance());
        setTitle("Project classpath");
        setModal(true);     
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationByPlatform(true);
        
        _classpath = classpath;
        
        final DefaultListModel model = new DefaultListModel();
        
        _list = new JList(model);
        Box fixedrow = Box.createHorizontalBox();
        fixedrow.add(new JScrollPane(_list));
        fixedrow.add(Box.createHorizontalStrut(5));
        
        final Box listMargin = Box.createVerticalBox();
        
        listMargin.add(fixedrow);
        listMargin.add(Box.createVerticalStrut(5));
        
        for(File path : _classpath){
            model.addElement(path);
        }
        
        _list.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent e) {
                _remove.setEnabled(true);
            }
        });
        
        
        final JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(_add = new JButton("add"));
        rightPanel.add(Box.createVerticalStrut(5));
        rightPanel.add(_remove = new JButton("remove"));
        rightPanel.add(Box.createVerticalGlue());
        
        _add.setMaximumSize(new Dimension(200, 50));
        
        _remove.setEnabled(false);
        
        _remove.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                Object[] items = _list.getSelectedValues();
                DefaultListModel model = (DefaultListModel) _list.getModel();
                for(int i=0; i<items.length; i++){
                    model.removeElement(items[i]);
                }
                _remove.setEnabled(false);
            }
        });
        
        _add.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                JFileChooser chooser = MainUI.getInstance().getFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setDialogTitle("Add folder to classpath");
                chooser.setApproveButtonText("Select");
                
                if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(MainUI.getInstance())) {
                    model.addElement(chooser.getSelectedFile());
                }
            }
        });
        
        final JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.add(Box.createHorizontalGlue());
        bottomPanel.add(_cancel = new JButton("cancel"));
        bottomPanel.add(Box.createHorizontalStrut(5));
        bottomPanel.add(_ok = new JButton("ok"));
        
        
        _cancel.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                dispose();
            }
        });
        
        _ok.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                _commited = true;
                dispose();
            }
        });
        
        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        
        mainPanel.add(listMargin, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel,  BorderLayout.CENTER);
        add(Box.createVerticalStrut(5), BorderLayout.NORTH);
        add(Box.createVerticalStrut(5), BorderLayout.SOUTH);
        add(Box.createHorizontalStrut(5), BorderLayout.WEST);
        add(Box.createHorizontalStrut(5), BorderLayout.EAST);
        
        setSize(450,300);
    }
    
    public boolean isCommited(){
        return _commited;
    }
    
    public ProjectClasspath getClasspath(){
        final ProjectClasspath classpath = new ProjectClasspath();
        final ListModel model = _list.getModel();
        final int length = model.getSize();
        for(int i=0; i<length; i++){
            classpath.add((File)model.getElementAt(i));
        }
        return classpath;
    }
}