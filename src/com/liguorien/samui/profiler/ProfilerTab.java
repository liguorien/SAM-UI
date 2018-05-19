/*
 * ProfilerTab.java
 *
 * Created on August 7, 2005, 12:41 AM
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

package com.liguorien.samui.profiler;

import com.liguorien.samui.ExceptionLogger;
import com.liguorien.samui.output.OutputClient;
import com.liguorien.samui.ui.*;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.TableColumn;
import javax.swing.tree.*;
import org.w3c.dom.Document;


/**
 *
 * @author Nicolas Désy - http://www.liguorien.com/blog/
 */
public class ProfilerTab extends JAbstractTab implements TreeSelectionListener {
    
    private OutputClient _client = null;
    private boolean _dataParsed = false;
    
    private JPanel _mainPanel = null;
    private JSplitPane _splitter;
    private JTree _tree;
    private JTable _classTable;
    private JTable _methodTable;
    private JTable _stackTable;
    
    /** Creates a new instance of ProfilerTab */
    public ProfilerTab(String title) {
        super();
        setLabel(title);
        setId(title);
    }
    
    public ProfilerTab(String title, OutputClient client) {
        this(title);
        _client = client;
    }
    
    
    
    public Component getComponent() {
        if(_mainPanel == null){
            
            _tree = new JTree(new DefaultTreeModel(null));
            _tree.setCellRenderer(new ProfilerStackRenderer());
            _tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
            _tree.addTreeSelectionListener(this);
            
            _classTable = new JTable();
            _classTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            _classTable.getSelectionModel().addListSelectionListener( new ListSelectionListener(){
                public void valueChanged(ListSelectionEvent e) {
                    if(!e.getValueIsAdjusting() && _methodTable.getModel() instanceof MethodTableModel) {
                        final ClassTableModel classesModel = (ClassTableModel)_classTable.getModel();
                        final ClassResult result = classesModel.getResultAt(_classTable.getSelectedRow());
                        if(result != null){
                            final MethodTableModel methodsModel = ((MethodTableModel)_methodTable.getModel());
                            EventQueue.invokeLater(new Runnable(){
                                public void run(){
                                    methodsModel.filterClass(result.classID);
                                }
                            });
                        }
                    }
                }
            });
            
            //Classes sorting
            _classTable.getTableHeader().addMouseListener( new  MouseAdapter() {
                public void mouseClicked(MouseEvent event) {
                    try{
                        ((ClassTableModel)_classTable.getModel()).sortByColumnIndex(_classTable.columnAtPoint(event.getPoint()));
                    }catch(Exception ex){
                        ExceptionLogger.log(ex);
                    }
                }
            });
            
            
            _methodTable = new JTable();
            _methodTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            _methodTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
                public void valueChanged(ListSelectionEvent e) {
                    if(!e.getValueIsAdjusting() && _stackTable.getModel() instanceof StackTableModel){
                        final MethodTableModel methodsModel = (MethodTableModel)_methodTable.getModel();
                        final int index = _methodTable.getSelectedRow();
                        if(index > -1){
                            final MethodResult result = methodsModel.getResultAt(index);
                            final StackTableModel stackModel = ((StackTableModel)_stackTable.getModel());
                            stackModel.filterStack(result.classID, result.methodID);
                        }
                    }
                }
            });
            
            //Methods sorting
            _methodTable.getTableHeader().addMouseListener( new  MouseAdapter() {
                public void mouseClicked(MouseEvent event) {
                    try{
                        ((MethodTableModel)_methodTable.getModel()).sortByColumnIndex(_methodTable.columnAtPoint(event.getPoint()));
                    }catch(Exception ex){
                        ExceptionLogger.log(ex);
                    }
                }
            });
            
            
            _stackTable = new JTable();
            _stackTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            _stackTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
                public void valueChanged(ListSelectionEvent e) {
                    if(e.getValueIsAdjusting()){
                        final int index = _stackTable.getSelectedRow();
                        if(index > -1){
                            final StackTableModel stackModel = ((StackTableModel)_stackTable.getModel());
                            final TreePath path = new TreePath( stackModel.getResultAt(index).getPath());
                            _tree.setSelectionPath(path);
                            _tree.scrollPathToVisible(path);
                        }
                    }
                }
            });
            //Methods call sorting
            _stackTable.getTableHeader().addMouseListener( new  MouseAdapter() {
                public void mouseClicked(MouseEvent event) {
                    try{
                        ((StackTableModel)_stackTable.getModel()).sortByColumnIndex(_stackTable.columnAtPoint(event.getPoint()));
                    }catch(Exception ex){
                        ExceptionLogger.log(ex);
                    }
                }
            });
            
            
            final JSplitPane bSplit = new JSplitPane();
            bSplit.setTopComponent(new TitledPanel("METHODS", new JScrollPane(_methodTable)));
            bSplit.setBottomComponent(new TitledPanel("METHOD CALLS", new JScrollPane(_stackTable)));
            bSplit.setDividerLocation(150);
            bSplit.setOrientation(JSplitPane.VERTICAL_SPLIT);
            
            final JSplitPane rSplit = new JSplitPane();
            rSplit.setTopComponent(new TitledPanel("CLASSES", new JScrollPane(_classTable)));
            rSplit.setBottomComponent(bSplit);
            rSplit.setDividerLocation(150);
            rSplit.setOrientation(JSplitPane.VERTICAL_SPLIT);
            
            _splitter = new JSplitPane();
            _splitter.setTopComponent(new TitledPanel("STACK HISTORY", new JScrollPane(_tree)));
            _splitter.setBottomComponent(rSplit);
            _splitter.setDividerLocation(400);
            
            _mainPanel = new JPanel();
            _mainPanel.setLayout(new BorderLayout());
            
            final JPanel btnPanel = new JPanel();
            
            if(_client != null) {
                final JButton btn = new JButton("take SWF snapshot");
                btn.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent evt){
                        try{
                            
                            _mainPanel.remove(btnPanel);
                            _mainPanel.paintImmediately(0, 0, _mainPanel.getWidth(), _mainPanel.getHeight());
                            
                            EventQueue.invokeLater(new Runnable(){
                                public void run(){
                                    _client.send("getStackHistory");
                                }
                            });
                            
                        }catch(Exception ex){
                            ExceptionLogger.log(ex);
                        }
                    }
                });
                
                btnPanel.add(btn);
                
            }
            
            _mainPanel.add(btnPanel, BorderLayout.NORTH);
            _mainPanel.add(_splitter, BorderLayout.CENTER);
            
        }
        return _mainPanel;
    }
    
    
    public void valueChanged(TreeSelectionEvent e) {
        
        if(e.getSource() == _tree){
            final StackElement node = (StackElement) _tree.getLastSelectedPathComponent();
            
            if(node != null){
                
                final ClassTableModel classesModel = (ClassTableModel)_classTable.getModel();
                final int length = classesModel.getRowCount();
                for(int i=0; i<length; i++){
                    if(classesModel.getResultAt(i).classID == node.classID){
                        _classTable.changeSelection(i,0,false,false);
                        break;
                    }
                }
                
                EventQueue.invokeLater(new Runnable(){
                    public void run(){
                        final MethodTableModel methodsModel = ((MethodTableModel)_methodTable.getModel());
                        final int length = methodsModel.getRowCount();
                        for(int i=0; i<length; i++){
                            if(methodsModel.getResultAt(i).methodID == node.methodID){
                                _methodTable.getSelectionModel().setLeadSelectionIndex(i);
                                _methodTable.changeSelection(i,0,false,false);
                                break;
                            }
                        }
                    }
                });
                
                EventQueue.invokeLater(new Runnable(){
                    public void run(){
                        final StackTableModel stackModel = ((StackTableModel)_stackTable.getModel());
                        final int length = stackModel.getRowCount();
                        for(int i=0; i<length; i++){
                            final StackElement el = stackModel.getResultAt(i);
                            if(el == node){
                                _stackTable.getSelectionModel().setLeadSelectionIndex(i);
                                _stackTable.changeSelection(i,0,false,false);
                                break;
                            }
                        }
                    }
                });
            }
        }
    }
    
    public boolean isDataParsed(){
        return _dataParsed;
    }
    
    public void readDOM(Document dom){
        _dataParsed = true;
        final ProfilerPacketParser parser = new ProfilerPacketParser();
        
        parser.readDOM(dom);
        
        EventQueue.invokeLater(new Runnable(){
            
            public void run(){
                
                _tree.setModel(new DefaultTreeModel(parser.getMainStack()));
                _tree.validate();
                
                TableColumn column = null;
                
                //CLASSES - init classes model
                final List<ClassResult> classes = parser.getStats().getClassResults();
                Collections.sort(classes, ProfilerStats.CLASS_MAXIMUM_COMPARATOR);
                
                _classTable.setModel(new ClassTableModel(classes));
                column = _classTable.getColumnModel().getColumn(0);
                column.setCellRenderer(new ExecutionTimeTableCell());
                column.setMaxWidth(20);
                column.setMinWidth(20);
                _classTable.revalidate();
                
                
                //METHODS - init methods model
                final List<MethodResult> methodes = parser.getStats().getMethodResults();
                Collections.sort(methodes, ProfilerStats.METHOD_MAXIMUM_COMPARATOR);
                
                _methodTable.setModel(new MethodTableModel(methodes));
                column = _methodTable.getColumnModel().getColumn(0);
                column.setCellRenderer(new ExecutionTimeTableCell());
                column.setMaxWidth(20);
                column.setMinWidth(20);
                
                
                //STACKS - init stacks model
                final List<StackElement> stacks = parser.getStats().getStackElements();
                Collections.sort(stacks, ProfilerStats.STACK_TIME_COMPARATOR);
                
                _stackTable.setModel(new StackTableModel(stacks));
                column = _stackTable.getColumnModel().getColumn(0);
                column.setCellRenderer(new ExecutionTimeTableCell());
                column.setMaxWidth(20);
                column.setMinWidth(20);
                
            }
        });
    }
    
    public Icon getIcon() {
        
        Icon icon = null;
        
        try{
            icon = new ImageIcon(ProfilerTab.class.getResource("/com/liguorien/samui/files/timer.gif"));
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        return icon;
    }
    
    public JPopupMenu getContextualMenu() {
        return new RightClickMenu();
    }
    
    class RightClickMenu extends JPopupMenu{
        
        private JMenuItem _close;
        
        public RightClickMenu(){
            super();
            
            add(_close = new JMenuItem("Close"));
            
            _close.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent evt){
                    close();
                }
            });
        }
    }
}
