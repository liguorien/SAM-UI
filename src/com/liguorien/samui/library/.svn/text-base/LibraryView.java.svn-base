/*
 * LibraryTree.java
 *
 * Created on July 1, 2005, 7:24 PM
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
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import com.liguorien.samui.*;
import javax.swing.*;
/**
 *
 * @author Nicolas Désy - http://www.liguorien.com/blog/
 */
public class LibraryView extends JPanel {
    
    private RightClickMenu _popupMenu = null;
    
    private JTree _tree = null;
    
    public JTree getTree(){
        return _tree;
    }
    
    public RightClickMenu getPopupMenu(){
        return _popupMenu;
    }
    
    private LibrarySymbolPanel _symbolPanel = null;
    public LibrarySymbolPanel getSymbolPanel(){
        if(_symbolPanel == null){
            _symbolPanel = new LibrarySymbolPanel(this);
        }
        return _symbolPanel;
    }
    
    private TreePath _lastSelected;
    
    /** Creates a new instance of LibraryTree */
    public LibraryView(LibraryModel model) {
        super();
        
        setLayout(new BorderLayout());
        
        final JPanel iconPanel = new JPanel();
        iconPanel.setPreferredSize(new Dimension(300, 20));
        iconPanel.setLayout(new BoxLayout(iconPanel, BoxLayout.X_AXIS));
        
        
        final LibraryButton _newBtn = new LibraryButton("/com/liguorien/samui/files/newitem.gif", "New Symbol...");
        _newBtn.addActionListener(new AddSymbolActionListener());
        
        final LibraryButton _removeBtn = new LibraryButton("/com/liguorien/samui/files/trashcan.gif", "Delete");
        _removeBtn.addActionListener( new RemoveItemActionListener());
        
        final LibraryButton _folderBtn = new LibraryButton("/com/liguorien/samui/files/newfolder.gif", "New Folder");
        _folderBtn.addActionListener( new AddFolderActionListener());
        
        
        iconPanel.add(Box.createHorizontalGlue());
        iconPanel.add(_newBtn);
        iconPanel.add(_folderBtn);
        iconPanel.add(_removeBtn);
        
        final JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(new JLabel("LIBRARY"));
        
        
        add(new JScrollPane(_tree = new LibraryTree()),  BorderLayout.CENTER);
        add(iconPanel, BorderLayout.SOUTH);
        add(titlePanel, BorderLayout.NORTH);
        
        _tree.setModel(model);
        _tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        _tree.setEditable(true);
        _tree.setRootVisible(false);
        
        _popupMenu = new RightClickMenu();
        
        try{
            
            _tree.addMouseListener(new RightClickListener());
            
            _tree.addTreeSelectionListener(new TreeSelectionListener() {
                public void valueChanged(TreeSelectionEvent e) {
                    final Object node = _tree.getLastSelectedPathComponent();
                    if(node instanceof LibrarySymbol){
                        _lastSelected = new TreePath(node);
                        EventQueue.invokeLater(new Runnable(){
                            public void run(){
                                getSymbolPanel().setLibrarySymbol((LibrarySymbol) node);
                            }
                        });
                    }
                }
            });
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
    }
    
    
    public void updateSymbolUI(LibrarySymbol symbol){
        _tree.getModel().valueForPathChanged(_lastSelected, symbol.getLinkage());
    }
    
    class RemoveItemActionListener implements ActionListener {
        public void actionPerformed(ActionEvent evt){
            ((LibraryModel)LibraryView.this.getTree().getModel()).removeNodeFromParent((MutableTreeNode)LibraryView.this.getTree().getLastSelectedPathComponent());
            getSymbolPanel().clear();
        }
    }
    
    class AddSymbolActionListener implements ActionListener {
        public void actionPerformed(ActionEvent evt){
            
            final LibrarySymbol value = NewSymbolDialog.getNewSymbol();
            
            if(!"".equals(value.getLinkage())){
                
                final JTree tree = LibraryView.this.getTree();
                final LibraryModel model = (LibraryModel) tree.getModel();
                
                final DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
                LibraryFolder folder = null;
                
                if(node != null && node instanceof LibrarySymbol){
                    folder = (LibraryFolder)node.getParent();
                }else if(node == null){
                    folder = (LibraryFolder)model.getRoot();
                }else if(node instanceof LibraryFolder){
                    folder = (LibraryFolder) node;
                }
                
                if(folder == null){
                    return;
                }
                
                
                try{
                    model.insertNodeInto(value, folder, folder.getChildCount());
                    tree.setSelectionPath(new TreePath( value.getPath()));
                }catch(Exception ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(MainUI.getInstance(), "There is already a library item with this name.");
                }
                
            }
        }
    }
    
    class AddFolderActionListener implements ActionListener {
        public void actionPerformed(ActionEvent evt){
            
            final JTree tree = LibraryView.this.getTree();
            final LibraryModel model = (LibraryModel) tree.getModel();
            
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
            
            if(node != null && node instanceof LibrarySymbol){
                node = (DefaultMutableTreeNode)node.getParent();
            }else if(node == null){
                node = (DefaultMutableTreeNode)model.getRoot();
            }
            
            String name = "untitled folder";
            int cpt = 1;
            
            int length = node.getChildCount();
            for(int i=0; i<length; i++){
                if(name.equals(node.getChildAt(i).toString())){
                    name = "untitled folder (" + (cpt++) + ')';
                    i = 0;
                }
            }
            final LibraryFolder newNode = new LibraryFolder(name);
            
            node.add(newNode);
            
            model.insertNodeInto(newNode, node, node.getChildCount()-1);
            
            final TreePath path = new TreePath( newNode.getPath());
            tree.setSelectionPath(path);
            tree.startEditingAtPath(path);
        }
    }
    
    
    class RemoveSymbolActionListener implements ActionListener {
        public void actionPerformed(ActionEvent evt){
            
            final LibrarySymbol value = NewSymbolDialog.getNewSymbol();
            
            if(!"".equals(value.getLinkage()) && !"".equals(value.getFilePath())){
                
                final JTree tree = LibraryView.this.getTree();
                final LibraryModel model = (LibraryModel) tree.getModel();
                
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
                
                if(node != null && node instanceof LibrarySymbol){
                    node = (DefaultMutableTreeNode)node.getParent();
                }else if(node == null){
                    node = (DefaultMutableTreeNode)model.getRoot();
                }
                
                node.add(value);
                
                model.insertNodeInto(value, node, node.getChildCount()-1);
            }
        }
    }
    
    class RightClickMenu extends JPopupMenu{
        
        private JMenuItem _new = null;
        private JMenuItem _newFolder = null;
        private JMenuItem _rename = null;
        private JMenuItem _delete = null;
        
        
        
        public RightClickMenu(){
            super();
            
            add(_new = new JMenuItem("New Symbol"));
            add(_newFolder = new JMenuItem("New Folder"));
            add(_rename = new JMenuItem("Rename"));
            add(_delete = new JMenuItem("Delete"));
            
            _new.addActionListener(new AddSymbolActionListener());
            
            _newFolder.addActionListener(new AddFolderActionListener());
            
            _rename.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent evt){
                    getTree().startEditingAtPath(RightClickMenu.this.getCurrentPath());
                }
            });
            
            _delete.addActionListener(new RemoveItemActionListener());
        }
        
        /**
         * Holds value of property _currentPath.
         */
        private TreePath _currentPath;
        
        /**
         * Getter for property currentPath.
         * @return Value of property currentPath.
         */
        public TreePath getCurrentPath() {
            
            return _currentPath;
        }
        
        /**
         * Setter for property currentPath.
         * @param currentPath New value of property currentPath.
         */
        public void setCurrentPath(TreePath currentPath) {
            
            _currentPath = currentPath;
        }
        
    }
    
    class RightClickListener extends MouseAdapter {
        
        public void mousePressed(MouseEvent e) {
            handleEvent(e);
        }
        
        public void mouseReleased(MouseEvent e) {
            handleEvent(e);
        }
        
        private void handleEvent(MouseEvent e){
            if (e.isPopupTrigger()) {
                TreePath selPath = getTree().getPathForLocation(e.getX(), e.getY());
                getTree().setSelectionPath(selPath);
                getPopupMenu().show((Component)e.getSource(),e.getX(), e.getY());
                getPopupMenu().setCurrentPath(selPath);
            }
        }
    }
}
