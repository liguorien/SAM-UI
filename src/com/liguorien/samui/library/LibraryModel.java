/*
 * LibraryModel.java
 *
 * Created on July 2, 2005, 1:26 PM
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

import com.liguorien.samui.project.ProjectConfig;
import com.liguorien.utils.XmlUtil;
import com.sun.org.apache.xpath.internal.XPathAPI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.swing.tree.*;
import com.liguorien.samui.*;
import javax.swing.*;

/**
 *
 * @author Nicolas D�sy - http://www.liguorien.com/blog/
 */
public class LibraryModel extends DefaultTreeModel {
    
    
    private boolean _changed = true;
    
    /** Creates a new instance of LibraryModel */
    public LibraryModel() {
        super(new DefaultMutableTreeNode("Library"));
    }
    
    public boolean hasChange(){
        return _changed;
    }
    
    public void markChanges(){
        _changed = false;
    }
    
    public void readDOM(Document doc){
        
        try{
            final LibraryFolder root = new LibraryFolder("library");
            final NodeList list = XPathAPI.selectNodeList(doc,"/library/*");
            final int size = list.getLength();
            
            for(int i=0; i<size; i++){
                final Node item = list.item(i);
                if("folder".equals(item.getNodeName())){
                    final LibraryFolder folder = new LibraryFolder(XmlUtil.getAttribute(item,"name"));
                    root.add(folder);
                    parseFolder(folder,  item);
                }else{                   
                    root.add(new LibrarySymbol(
                            XmlUtil.getAttribute(item, "linkage"), 
                            XmlUtil.getAttribute(item, "file"), 
                            XmlUtil.getAttribute(item, "class")
                    ));
                }
            }
            
            setRoot(root);
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void parseFolder(DefaultMutableTreeNode treeNode, Node node){
        try{
            final NodeList list = XPathAPI.selectNodeList(node,"./*");
            final int size = list.getLength();
            
            for(int i=0; i<size; i++){
                final Node item = list.item(i);
                if("folder".equals(item.getNodeName())){
                    
                    final LibraryFolder folder = new LibraryFolder(XmlUtil.getAttribute(item,"name"));
                    treeNode.add(folder);
                    parseFolder(folder,  item);
                }else{               
                    treeNode.add(new LibrarySymbol(
                            XmlUtil.getAttribute(item, "linkage"), 
                            XmlUtil.getAttribute(item, "file"), 
                            XmlUtil.getAttribute(item, "class")
                    ));
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void insertNodeInto(MutableTreeNode newChild,  LibraryFolder folder, int index) throws Exception {
        final String childID = newChild.toString();
        if(childID == null) return;
        final int length = folder.getChildCount();
        for(int i=0; i<length; i++){
            if(childID.equals(folder.getChildAt(i).toString())  && newChild != folder.getChildAt(i)){
                throw new Exception("ID already exists !!!");
            }
        }
        folder.add(newChild);
        super.insertNodeInto(newChild, folder, index);
        _changed = true;
    }
    
    
    
    public void valueForPathChanged(TreePath path, Object value) {
        
        if(value == null) return;
        
        MutableTreeNode node = (MutableTreeNode)path.getLastPathComponent();
        
        final TreeNode parent = node.getParent();
        final int length = parent.getChildCount();
        for(int i=0; i<length; i++){
            if(value.equals(parent.getChildAt(i).toString()) && node != parent.getChildAt(i)){
                JOptionPane.showMessageDialog(MainUI.getInstance(), "There is already a library item with this name.");
                return;
            }
        }
        super.valueForPathChanged(path, value);
        _changed = true;
    }
    
    public String getXML(){
        final TreeNode root = (TreeNode)getRoot();
        
        final int length = root.getChildCount();
        final StringBuilder builder = new StringBuilder();
        
        builder.append("<library>");
        
        for(int i=0; i<length; i++){
            builder.append(_getXML(root.getChildAt(i)));
        }
        
        builder.append("</library>");
        
        return builder.toString();
    }
    
    public String _getXML(TreeNode node){
        
        if(node instanceof LibrarySymbol){
            final LibrarySymbol symbol = (LibrarySymbol) node;
            return "<item file=\""+ symbol.getFilePath() + "\" linkage=\""+symbol.toString()+"\" class=\""+symbol.getClassName()+"\"/>";
        }
        
        final LibraryFolder folder = (LibraryFolder)node;
        final int length = folder.getChildCount();
        final StringBuilder builder = new StringBuilder();
        
        builder.append("<folder name=\"").append(folder.toString()).append("\">");
        
        for(int i=0; i<length; i++){
            builder.append(_getXML(folder.getChildAt(i)));
        }
        builder.append("</folder>");
        
        return builder.toString();
    }
    
    private Document _dom = null;
    
    public Document getDOM(){
        if(_changed){
            try{
                _dom = XmlUtil.parse(getXML());
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return _dom;
    }
}
