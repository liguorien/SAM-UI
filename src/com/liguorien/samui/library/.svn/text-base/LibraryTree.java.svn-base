/*
 * LibraryTree.java
 *
 * Created on July 29, 2005, 11:04 AM
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


import com.liguorien.samui.library.LibrarySymbol;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.awt.image.*;
import com.liguorien.samui.library.*;
import java.util.List;
import java.io.*;
import com.liguorien.samui.*;
/**
 * This class represents library tree
 * @author Nicolas Désy - http://www.liguorien.com/blog/
 */
public class LibraryTree extends JTree implements DragSourceListener, DropTargetListener, DragGestureListener{
    
    
    /*
     *DataFlavor definition for drag and drop support
     */
    static DataFlavor localObjectFlavor;
    static {
        try{
            localObjectFlavor = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    static DataFlavor[] supportedFlavors = {localObjectFlavor};
    
    private final static int LINE_MARGIN = 50;
    private final static int BEFORE_ITEM = -1;
    private final static int INSIDE_FOLDER = 0;
    private final static int AFTER_ITEM = 1;
    
    private DragSource dragSource;
    private DropTarget dropTarget;
    private Point _lastPoint = new Point();
    private Rectangle2D  _raGhost = new Rectangle2D.Float();
    private TreePath _pathSource;
    private BufferedImage _imgGhost;
    private Point _ptOffset = new Point();
    private TreePath _lastPath = null;
    private Rectangle2D _line = new Rectangle2D.Float();
    
    
    /**
     * Creates a new instance of LibraryTree
     */
    public LibraryTree() {
        super();
        setCellRenderer(new DndTreeCellRenderer());
        dragSource = new DragSource();
        DragGestureRecognizer drg = dragSource.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_MOVE, this);
        dropTarget = new DropTarget(this, this);
    }
    
    
    /**
     * This event is invoked when user try to drag a TreeNode
     * it create a ghost copy of TreeNode component to enhance visual feedback
     */
    public void dragGestureRecognized(DragGestureEvent evt) {
        
        final Point origin = evt.getDragOrigin();
        final TreePath path = getPathForLocation(origin.x, origin.y);
        if (path == null){
            return;
        }
        
        // get the TreeNode bound
        final Rectangle bounds = getPathBounds(path);
        
        _ptOffset.setLocation(origin.x-bounds.x, origin.y-bounds.y);
        
        // Get the cell renderer (which is a JLabel) for the path being dragged
        final JLabel lbl = (JLabel) getCellRenderer().getTreeCellRendererComponent(this, path.getLastPathComponent(), false, isExpanded(path), getModel().isLeaf(path.getLastPathComponent()), 0, false);
        
        lbl.setSize(bounds.width, bounds.height);
        
        // Get a buffered image of the selection for dragging a ghost image
        _imgGhost = new BufferedImage(bounds.width, bounds.height, BufferedImage.TYPE_INT_ARGB_PRE);
        
        final Graphics2D g2 = _imgGhost.createGraphics();
        
        //set image alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 0.5f));
        
        // Ask the cell renderer to paint itself into the BufferedImage
        lbl.paint(g2);
        
        
        final Icon icon = lbl.getIcon();
        final int nStartOfText = (icon == null) ? 0 : icon.getIconWidth()+lbl.getIconTextGap();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_OVER, 0.5f));
        g2.setPaint(new GradientPaint(nStartOfText, 0, SystemColor.controlShadow, getWidth(), 0, new Color(255,255,255,0)));
        g2.fillRect(nStartOfText, 0, getWidth(), _imgGhost.getHeight());
        
        g2.dispose();
        
        setSelectionPath(_pathSource = path);
        
        //create tranferable drag and drop data
        final Transferable trans = new LibraryTransferable((TreeNode) path.getLastPathComponent());
        
        evt.startDrag(null, _imgGhost, new Point(5,5), trans, this);
    }
    
    
    /**
     * This event is invoked after drop, it reset tree display
     */
    public void dragDropEnd(DragSourceDropEvent evt) {
        repaint();
    }
    
    public void dragEnter(DragSourceDragEvent evt) {}
    public void dragExit(DragSourceEvent evt) {}
    public void dragOver(DragSourceDragEvent dragSourceDragEvent) {}
    public void dropActionChanged(DragSourceDragEvent dragSourceDragEvent) {}
    public void dragExit(DropTargetEvent evt) {}
    
    public void dragEnter(DropTargetDragEvent evt) {
        evt.acceptDrag(DnDConstants.ACTION_COPY_OR_MOVE);
    }
    
    
    /**
     * This event is invoked during d&d operation. It's called very often.
     */
    public void dragOver(DropTargetDragEvent evt) {
        
        //mouse position
        final Point pt = evt.getLocation();
        
        //check if it's same location as last event, we don't want to do useless stuff
        if (pt.equals(_lastPoint)) return;
        
        _lastPoint = pt;
        
        final Graphics2D g2 = (Graphics2D) getGraphics();
        
        //if current OS doesn't support image draging, draw it manually
        if (!DragSource.isDragImageSupported()) {
            paintImmediately(0,0,getWidth(), getHeight());
            g2.drawImage(_imgGhost, AffineTransform.getTranslateInstance(pt.x - _ptOffset.x, pt.y - _ptOffset.y), null);
        }else{
            paintImmediately(_line.getBounds());
        }
        
        //get path of the closest node
        TreePath path = getClosestPathForLocation(pt.x, pt.y);
        
        if(path == null) {
            path = new TreePath(getModel().getRoot());
        }
        
        if (!(path == _lastPath)) {
            _lastPath = path;
        }
        
        
        //the following code draw a cue line indicating where a drop will occur
        final Rectangle bounds = getPathBounds(path);
        final Object target = path.getLastPathComponent();
        
        // SYMBOL - if drop node is a symbol
        if(target instanceof LibrarySymbol){
            
            //NORTH - mouse is in the north area of node
            if(pt.y < (bounds.y + bounds.height / 2)) {
                _line.setRect(bounds.x,  bounds.y, bounds.width+LINE_MARGIN, 1);
            }
            
            //SOUTH - mouse is in the south area of node
            else {
                _line.setRect(bounds.x,  bounds.y + bounds.height, bounds.width +LINE_MARGIN, 1);
            }
        }
        
        
        // FOLDER - if drop node is a folder
        else if(target instanceof LibraryFolder){
            
            //NORTH - mouse is in the north area of node
            if(pt.y < (bounds.y + 5)) {
                _line.setRect(bounds.x,  bounds.y, bounds.width+LINE_MARGIN, 1);
            }
            
            //SOUTH - mouse is in the center area of node
            else if(pt.y > (bounds.y + bounds.height * 0.75f)) {
                _line.setRect(bounds.x,  bounds.y+(int)bounds.height, bounds.width+LINE_MARGIN, 1);
            }
            
            //CENTER - mouse is in the south area of node
            else {
                _line.setRect(bounds.x,  bounds.y, bounds.width +LINE_MARGIN, bounds.height);
            }
        }
        
        //draw cue line
        g2.setColor(Color.BLACK);
        g2.draw(_line);
    }
    
    
    
    /**
     * This event is invoked when a drop operation occur.
     */
    public void drop(DropTargetDropEvent evt) {
        
        boolean dropped = false;
        
        final LibraryModel model = (LibraryModel)getModel();
        
        try{
            
            evt.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
            
            final Point pt = evt.getLocation();
            TreePath path = getClosestPathForLocation(pt.x, pt.y);
            
            DefaultMutableTreeNode target = null;
            
            if(path == null){
                path = new TreePath(target = (DefaultMutableTreeNode)getModel().getRoot());
            }else{
                target = (DefaultMutableTreeNode) path.getLastPathComponent();
            }
            
            final Rectangle bounds = getPathBounds(path);
            LibraryFolder folder = null;
            int index = -1;
            
            //choose the good destination folder and index
            
            if(target == getModel().getRoot()){
                folder = (LibraryFolder) target;
                index = INSIDE_FOLDER;
            }
            
            
            // SYMBOL - if drop node is a symbol
            else if(target instanceof LibrarySymbol){
                
                folder = (LibraryFolder) target.getParent();
                
                //NORTH - mouse is in the north area of node
                if(pt.y < (bounds.y + bounds.height / 2)) {
                    index = BEFORE_ITEM;
                }
                
                //SOUTH - mouse is in the south area of node
                else {
                    index = AFTER_ITEM;
                }
            }
            
            
            // FOLDER - if drop node is a folder
            else if(target instanceof LibraryFolder){
                
                //NORTH - mouse is in the north area of node
                if(pt.y < (bounds.y + 5)) {
                    folder = (LibraryFolder) target.getParent();
                    index = BEFORE_ITEM;
                }
                
                //SOUTH - mouse is in the center area of node
                else if(pt.y > (bounds.y + bounds.height * 0.75f)) {
                    folder = (LibraryFolder) target.getParent();
                    index = AFTER_ITEM;
                }
                
                //CENTER - mouse is in the south area of node
                else {
                    folder = (LibraryFolder) target;
                    index = INSIDE_FOLDER;
                }
            }
            
            try{
                
                //try to get a transferable Java Object
                final Object droppedObject = evt.getTransferable().getTransferData(localObjectFlavor);
                
                //droppedObject must be a TreeNode
                if(!(droppedObject instanceof MutableTreeNode)){
                    evt.dropComplete(false);
                    return;
                }
                
                final MutableTreeNode droppedNode = (MutableTreeNode) droppedObject;
                
                //be sure there is no item with this same name on drop level
                if(droppedNode.getParent() != folder){
                    final int length = folder.getChildCount();
                    for(int i=0; i<length; i++){
                        if(folder.getChildAt(i).toString().equals(droppedNode.toString())){
                            throw new Exception("");
                        }
                    }
                }
                
                model.removeNodeFromParent(droppedNode);
                
                switch(index){
                    case BEFORE_ITEM : index = folder.getIndex(target); break;
                    case INSIDE_FOLDER : index = folder.getChildCount(); break;
                    case AFTER_ITEM : index = folder.getIndex(target)+1; break;
                }
                
                model.insertNodeInto(droppedNode, folder, index);
                
                dropped = true;
                
            }catch(UnsupportedFlavorException ufe){
                
                //user is dragging a list of external files in library
                
                try{
                    
                    final List files = (List)evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    final int length = files.size();
                    
                    for(int i=0; i<length; i++){
                        
                        final File file = (File) files.get(i);
                        final MutableTreeNode importedObject = (file.isDirectory()) ?  importFolder(file) :  importSymbol(file);
                        
                        switch(index){
                            case BEFORE_ITEM : model.insertNodeInto(importedObject, folder, folder.getIndex(target)); break;
                            case INSIDE_FOLDER : model.insertNodeInto(importedObject, folder, folder.getChildCount()); break;
                            case AFTER_ITEM : model.insertNodeInto(importedObject, folder, folder.getIndex(target)+1); break;
                        }
                    }
                    
                    if(getModel().getRoot() == target){
                        expandPath(path);
                    }
                    
                }catch(Exception ex2){
                    ex2.printStackTrace();
                }
            }
        }catch(Exception ex){
            
            ex.printStackTrace();
            dropped = false;
            JOptionPane.showMessageDialog(MainUI.getInstance(), "There is already a library item with this name in folder.");
        }
        
        evt.dropComplete(dropped);
    }
    
    
    /**
     *import symbol into library
     */
    private LibrarySymbol importSymbol(File file){
        //TODO: validate entry
        final LibrarySymbol importedSymbol = new LibrarySymbol(file.getName(),file.getAbsolutePath(), "");
        importedSymbol.setLinkage(file.getName());
        importedSymbol.setFilePath(file.getAbsolutePath());
        return importedSymbol;
    }
    
    /**
     *import filesystem folder into library
     */
    private LibraryFolder importFolder(File folder){
        
        final LibraryFolder importedFolder = new LibraryFolder(folder.getName());
        try{
            for(File file : folder.listFiles()){
                final MutableTreeNode importedObject = (file.isDirectory()) ?  importFolder(file) :  importSymbol(file);
                importedFolder.add(importedObject);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return importedFolder;
    }
    
    
    
    public void dropActionChanged(DropTargetDragEvent evt) { }
    
    
    class DndTreeCellRenderer extends DefaultTreeCellRenderer {
        
        private ImageIcon _folderIcon = null;
        private ImageIcon _symbolIcon = null;
        
        /** Creates a new instance of LibrarySymbolRenderer */
        public DndTreeCellRenderer() {
            super();
            
            try{
                _folderIcon = loadIcon("library-folder-close.gif");
                _symbolIcon = loadIcon("library-symbol.gif");
                
                setOpenIcon(loadIcon("library-folder-open.gif"));
                setClosedIcon(_folderIcon);
                setClosedIcon(loadIcon("library-folder-close.gif"));
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
        
        private ImageIcon loadIcon(String name) throws IOException {
            return new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/com/liguorien/samui/files/" + name)));
        }
        
        
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            
            super.getTreeCellRendererComponent(tree, value, sel,expanded, leaf, row,hasFocus);
            
            if (leaf){
                if(value instanceof LibrarySymbol) {
                    setIcon(_symbolIcon);
                    setToolTipText(((LibrarySymbol)value).getFilePath());
                } else {
                    setIcon(_folderIcon);
                    setToolTipText(null); //no tool tip
                }
            }
            
            return this;
        }
    }
    
    
    /**
     * Transferable data for library element
     */
    class LibraryTransferable implements Transferable {
        
        Object object;
        
        public LibraryTransferable(Object obj){
            object = obj;
        }
        
        public Object getTransferData(DataFlavor df) throws UnsupportedFlavorException, IOException {
            if(isDataFlavorSupported(df)){
                return object;
            }else{
                throw new UnsupportedFlavorException(df);
            }
        }
        
        public DataFlavor[] getTransferDataFlavors() {
            return supportedFlavors;
        }
        
        public boolean isDataFlavorSupported(DataFlavor df) {
            return (df.equals(localObjectFlavor));
        }
    }
}
