/*
 * LibrarySymbolPanel.java
 *
 * Created on July 3, 2005, 12:10 PM
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

import com.liguorien.utils.SpringUtil;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.liguorien.samui.*;
import com.liguorien.samui.ui.*;
import com.liguorien.samui.project.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

/**
 *
 * @author Nicolas Désy - http://www.liguorien.com/blog/
 */
public class LibrarySymbolPanel extends JAbstractTab implements ActionListener, Runnable {
    
    private JLabel _image = null;
    private JScrollPane _imagePane = null;
    private JPanel _infosPanel = null;
    private JTextField _linkageTxt = null;
    private JLabel _widthTxt = null;
    private JLabel _heightTxt = null;
    private JTextField _pathTxt = null;
    private JButton _pathBtn = null;
    private JPanel _panel = null;
    private JTextField _classTxt = null;
    private LibraryView _tree;
    private volatile LibrarySymbol _currentSymbol = null;
    
    
    
    
    /** Creates a new instance of LibrarySymbolPanel */
    public LibrarySymbolPanel(LibraryView tree) {
        super();
        setId("symbolViewer");
        setLabel("Symbol viewer");
        _tree = tree;
    }
    
    
    /**
     * This event handler is for the button that open the file chooser
     */
    public void actionPerformed(ActionEvent evt){
        final JFileChooser chooser = MainUI.getInstance().getFileChooser();
        
        synchronized(chooser.getTreeLock()){
            chooser.setDialogTitle("Choose symbol file");
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int returnVal = chooser.showOpenDialog(MainUI.getInstance());
            
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                _currentSymbol.setFilePath(chooser.getSelectedFile().getAbsolutePath());
                ProjectConfig.getInstance().getLibrary().getModel().nodeChanged(_currentSymbol);
                
                EventQueue.invokeLater(this);
            }
        }
    }
    
    private JLabel getBoldLabel(String txt){
        final JLabel label = new JLabel(txt);
        final Font font = label.getFont();
        label.setFont(font.deriveFont(font.getStyle() ^ Font.BOLD));
        return label;
    }
    
    public LibrarySymbol getCurrentSymbol(){
        return _currentSymbol;
    }
    
    public void setLibrarySymbol(LibrarySymbol symbol){
        requestFocus();
        _currentSymbol = symbol;
        EventQueue.invokeLater(this);
    }
    
    
    public void clear(){
        _currentSymbol = null;
        EventQueue.invokeLater(this);
    }
    
    
    public void run(){
        
        //if the user select a symbol in library
        if(_currentSymbol != null){
            
            //if user associated a file to this symbol
            if(!"".equals(_currentSymbol.getFilePath())){
                
                //we display all fields related to the file
                
                final ImageIcon icon = new ImageIcon(_currentSymbol.getFilePath());
                _image.setIcon(icon);
                _widthTxt.setText(String.valueOf(icon.getIconWidth()));
                _heightTxt.setText(String.valueOf(icon.getIconHeight()));
                _pathTxt.setText(_currentSymbol.getFilePath());
                
            }else{
                
                //we reset all fields related to the file
                
                _widthTxt.setText("");
                _heightTxt.setText("");
                _pathTxt.setText("");
                _image.setIcon(null);
            }
            
            //set 'always shown' fields
            _linkageTxt.setText(_currentSymbol.getLinkage());
            _classTxt.setText(_currentSymbol.getClassName());
            _infosPanel.setVisible(true);
            
        }else{
            //reset all field
            _image.setIcon(null);
            _linkageTxt.setText("");
            _widthTxt.setText("");
            _heightTxt.setText("");
            _pathTxt.setText("");
            _classTxt.setText("");
            _infosPanel.setVisible(false);
        }
    }
    
    
    /**
     * Get component that will be displayed in tabbed pane
     */
    public Component getComponent() {
        
        try{
            if(_panel == null){
                        
                _image = new JLabel();
                _image.setHorizontalAlignment(SwingConstants.CENTER);
                _image.setFocusable(false);
                _image.setHorizontalTextPosition(SwingConstants.CENTER);
                _image.setVerticalTextPosition(SwingConstants.BOTTOM);
                
                _imagePane = new JScrollPane(_image);
                
                
                _infosPanel = new JPanel();
                _infosPanel.setLayout(new SpringLayout());
                
                _infosPanel.add(new JLabel("linkage"));
                _infosPanel.add(_linkageTxt = new JTextField());
                
                _infosPanel.add(new JLabel("class"));
                _infosPanel.add(_classTxt = new JTextField());
                
                final JPanel filePanel = new JPanel();
                filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.X_AXIS));
                filePanel.add(_pathTxt = new JTextField());
                filePanel.add(_pathBtn = new JButton("..."));
                _infosPanel.add(new JLabel("file"));
                _infosPanel.add(filePanel);
                
                _infosPanel.add(new JLabel("width"));
                _infosPanel.add(_widthTxt = new JLabel());
                
                _infosPanel.add(new JLabel("height"));
                _infosPanel.add(_heightTxt = new JLabel());
                
                
                
                
                _pathBtn.addActionListener(this);
                
                _linkageTxt.getDocument().addDocumentListener(new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {handleEvent(e);}
                    public void removeUpdate(DocumentEvent e) {handleEvent(e);}
                    public void insertUpdate(DocumentEvent e) {handleEvent(e);}
                    
                    private void handleEvent(DocumentEvent e) {
                        try{
                            final Document doc = e.getDocument();
                            _currentSymbol.setLinkage(doc.getText(0,doc.getLength()));
                            _tree.updateSymbolUI(_currentSymbol);
                        }catch(Exception ex){}
                    }
                });
                
                _classTxt.getDocument().addDocumentListener(new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {handleEvent(e);}
                    public void removeUpdate(DocumentEvent e) {handleEvent(e);}
                    public void insertUpdate(DocumentEvent e) {handleEvent(e);}
                    
                    private void handleEvent(DocumentEvent e) {
                        try{
                            final Document doc = e.getDocument();
                            _currentSymbol.setClassName(doc.getText(0,doc.getLength()));
                        }catch(Exception ex){}
                    }
                });
                
                
                _pathTxt.addFocusListener(new FocusListener(){
                    public void focusLost(FocusEvent e) {
                        
                        _currentSymbol.setFilePath(_pathTxt.getText());
                        
                        //TODO: remove this crappy cibling
                        ProjectConfig.getInstance().getLibrary().getModel().nodeChanged(_currentSymbol);
                
                        EventQueue.invokeLater(LibrarySymbolPanel.this);
                    }

                    public void focusGained(FocusEvent e) {}                    
                });
                
                
                
                SpringUtil.makeCompactGrid(_infosPanel,
                        5, 2, //rows, cols
                        6, 6,        //initX, initY
                        6, 6);       //xPad, yPad
                
                
                _infosPanel.setVisible(false);
                
                _panel = new JPanel();
                _panel.setLayout(new BorderLayout());
                _panel.add(_infosPanel, BorderLayout.NORTH);
                _panel.add(_imagePane, BorderLayout.CENTER);
                
                
            }
            
        }catch(Exception ex){
            ExceptionLogger.log(ex);
        }
        
        return _panel;
    }
    
    public Icon getIcon() {
        
        Icon icon = null;
        
        try{
            icon = new ImageIcon(LibrarySymbolPanel.class.getResource("/com/liguorien/samui/files/library-symbol.gif"));
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return icon;
    }
}
