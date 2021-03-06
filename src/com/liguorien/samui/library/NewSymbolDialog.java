/*
 * NewSymbolDialog.java
 *
 * Created on July 3, 2005, 2:19 PM
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

import com.liguorien.utils.SpringUtil;
import com.liguorien.samui.MainUI;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;


/**
 *
 * @author Nicolas D�sy - http://www.liguorien.com/blog/
 */
public class NewSymbolDialog extends JDialog implements ActionListener {
    
    private JLabel  _linkageLbl = null;
    private JTextField  _linkageTxt = null;
    private JLabel  _classLbl = null;
    private JTextField  _classTxt = null;
    private JTextField  _fileTxt = null;
    private JLabel  _fileLbl = null;
    private JButton  _fileBtn = null;
    private JButton     _okBtn = null;
    private JButton     _cancelBtn = null;
    
    
    private NewSymbolDialog() {
        super();
        setTitle("New Symbol...");
        setModal(true);
        setAlwaysOnTop(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        final JPanel mainPanel = new JPanel();
        
        _linkageTxt = new JTextField();
        _linkageLbl = new JLabel("linkage : ");
        _linkageLbl.setLabelFor(_linkageTxt);
        
 
         _classTxt = new JTextField();
        _classLbl = new JLabel("class : ");
        _classLbl.setLabelFor(_classTxt);
        
        
        final JPanel filePanel = new JPanel();
        filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.X_AXIS));
        filePanel.add(_fileTxt = new JTextField());
        filePanel.add(_fileBtn = new JButton("..."));
        
        _fileLbl = new JLabel("file : ");
        _fileLbl.setLabelFor(_fileTxt);
        
        
        
        mainPanel.add(_linkageLbl);
        mainPanel.add(_linkageTxt);        
        
         mainPanel.add(_classLbl);
        mainPanel.add(_classTxt);  
        
        mainPanel.add(_fileLbl);
        mainPanel.add(filePanel);
        
        _fileBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                final JFileChooser chooser = MainUI.getInstance().getFileChooser();
                
                synchronized(chooser.getTreeLock()){
                    chooser.setDialogTitle("Choose symbol file");
                    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    int returnVal = chooser.showOpenDialog(NewSymbolDialog.this);
                    if(returnVal == JFileChooser.APPROVE_OPTION) {
                        _fileTxt.setText(chooser.getSelectedFile().getAbsolutePath());
                    }
                }
            }
        });
        
        /*
         
         */
        
        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 2));
        buttonPanel.add(_okBtn = new JButton("OK"));
        buttonPanel.add(_cancelBtn = new JButton("Cancel"));
        
        mainPanel.add(new JLabel(""));
        mainPanel.add(buttonPanel);
       
        
        
        mainPanel.setLayout(new SpringLayout());
        
        SpringUtil.makeCompactGrid(mainPanel,
                4, 2, //rows, cols
                2, 2,        //initX, initY
                6, 6);
        
        add(mainPanel, BorderLayout.NORTH);
        add(new JPanel(), BorderLayout.CENTER);
        
        _okBtn.addActionListener(this);
        _cancelBtn.addActionListener(this);
        
        
        //add(buttonPanel, BorderLayout.SOUTH);
            pack();
        
       
        
        setSize(400,150);
        setResizable(false);
        
        setLocationRelativeTo(MainUI.getInstance());
    }
    
    public void actionPerformed(ActionEvent e) {
        if( e.getSource() == _okBtn ) {
            //value = inputField.getText();
        }
        dispose();
    }
    
    public static LibrarySymbol getNewSymbol() {
        final NewSymbolDialog dialog = new NewSymbolDialog();
        dialog.setVisible(true);      
        return new LibrarySymbol(dialog.getLinkage(), dialog.getFilePath(), dialog.getClassName());
    }
    
    
    
    /**
     * Holds value of property _linkage.
     */
    private String _linkage;
    
    /**
     * Getter for property linkage.
     * @return Value of property linkage.
     */
    public String getLinkage() {
        return _linkageTxt.getText();
    }
    
    /**
     * Holds value of property _filePath.
     */
    private String _filePath;
    
    /**
     * Getter for property filePath.
     * @return Value of property filePath.
     */
    public String getFilePath() {
        return _fileTxt.getText();
    }
    
    public String getClassName(){
        return _classTxt.getText();
    }
}
