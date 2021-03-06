/*
 * MainUI.java
 *
 * Created on June 22, 2005, 10:51 PM
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

package com.liguorien.samui;

import com.liguorien.samui.library.Library;
import com.liguorien.samui.output.OutputManager;
import com.liguorien.samui.project.ProjectConfig;
import com.liguorien.samui.project.ProjectToolBar;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import com.liguorien.samui.ui.*;



/**
 *
 * SAM-UI main user interface
 *
 * @author Nicolas D�sy - http://www.liguorien.com/blog/
 */
public class MainUI extends JFrame{
    
    private JSplitPane _mainSplitter;
    private JSplitPane _rightSplitter;
    private JSplitPane _leftSplitter;
    private JFileChooser _fileChooser;  
    private ProjectToolBar _toolbar;
    private boolean _inited = false;
    
    
    /**
     * Create initial UI (frame's icon and menubar)
     */
    private MainUI() {
        super("SAM-UI");
        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try{
            setIconImage(ImageIO.read(getClass().getResourceAsStream("/com/liguorien/samui/files/sam-ui.gif")));
        }catch(Exception ex){
            ex.printStackTrace();
        }
        setSize(800,600);
        setJMenuBar(new Menu());
    }
    
    public boolean isDoubleBuffered(){
        return true;
    }
    
    
    /**
     * Return a reference to the main panel
     */
    public JTabbedPanel _mainPanel = null;
    public JTabbedPanel getMainPanel(){
        return _mainPanel;
    }
    
    /**
     * init  components
     */
    public void initComponents(){
        synchronized(getTreeLock()){
            if(!_inited){
                _inited = true;
                
                final ProjectConfig project = ProjectConfig.getInstance();
                final Library library = project.getLibrary();
                
                _leftSplitter = new JSplitPane();
                _leftSplitter.setOrientation(JSplitPane.VERTICAL_SPLIT);
                _leftSplitter.setDividerLocation(300);
                _leftSplitter.setLeftComponent(project.getView());
                _leftSplitter.setOneTouchExpandable(true);
                _leftSplitter.setRightComponent(library.getView());
       
                
                _mainPanel = new JTabbedPanel();
                _mainPanel.addTab(library.getView().getSymbolPanel());
                
                _rightSplitter = new JSplitPane();
                _rightSplitter.setOrientation(JSplitPane.VERTICAL_SPLIT);
                _rightSplitter.setDividerLocation(450);
                _rightSplitter.setOneTouchExpandable(true);
                _rightSplitter.setLeftComponent(_mainPanel);
                _rightSplitter.setRightComponent(OutputManager.getInstance().getOutputPanel());
                _rightSplitter.setContinuousLayout(true);
                
                
                
                _mainSplitter = new JSplitPane();
                _mainSplitter.setOneTouchExpandable(true);
                _mainSplitter.setLeftComponent(_leftSplitter);
                _mainSplitter.setRightComponent(_rightSplitter);
                 _mainSplitter.setContinuousLayout(true);
                
                add(_mainSplitter);
                add(_toolbar = new ProjectToolBar(), BorderLayout.NORTH);
            }
        }
    }
    
    
    
    public static void main(String args[]) {
        
        if(args.length == 1 && "-installed-look".equals(args[0])){
            try{
                final LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();
                System.out.println("\nInstalled LookAndFeels : ");
                System.out.println("------------------------------ ");
                for(int i=0; i<infos.length; i++){
                    System.out.println(" - " + infos[i].getClassName());
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }finally{
                System.exit(0);
            }
        }
        
        try{
            
            final String customSkin = System.getProperty("samui.lookandfeel");
            if(customSkin != null){
                UIManager.setLookAndFeel(customSkin);
            }else{
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
           //         UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
            }
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                MainUI.getInstance().setVisible(true);
            }
        });
    }
    
    
    public ProjectToolBar getToolBar(){
        return _toolbar;
    }
    
    public JFileChooser getFileChooser(){
        if(_fileChooser == null){
            _fileChooser = new JFileChooser();
        }
        return _fileChooser;
    }
    
    private static MainUI _instance = null;
    public static MainUI getInstance(){
        if(_instance == null){
            _instance = new MainUI();
        }
        return _instance;
    }
}
