/*
 * ProjectToolBar.java
 *
 * Created on July 2, 2005, 11:33 AM
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

package com.liguorien.samui.project;

import com.liguorien.samui.library.LibraryButton;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

/**
 *
 * @author Nicolas D�sy - http://www.liguorien.com/blog/
 */
public class ProjectToolBar extends JPanel{
    
    private JButton _compileBtn = null;
    private JButton _playBtn = null;
    private JButton _popupBtn = null;
    private JButton _executeBtn = null;
    private JComboBox _targetCombo = null;
    
    
    /** Creates a new instance of ProjectToolBar */
    public ProjectToolBar() {
        super();
        
        setLayout(new FlowLayout(FlowLayout.LEFT));
        
        final Dimension dim = new Dimension(30, 30);
        
        _compileBtn = new LibraryButton("/com/liguorien/samui/files/build-project.gif", "Build Project");
        _compileBtn.addActionListener(new CompileProjectListener());  
        _compileBtn.setMaximumSize(dim);
        _compileBtn.setMinimumSize(dim);
        _compileBtn.setPreferredSize(dim);
        
        _playBtn = new LibraryButton("/com/liguorien/samui/files/run-project.gif", "Run Project");
        _playBtn.addActionListener(new CompileProjectListener(true));    
        _playBtn.setMaximumSize(dim);
        _playBtn.setMinimumSize(dim);
        _playBtn.setPreferredSize(dim);
        
        _popupBtn = new JButton("+");
        _popupBtn.addActionListener(new OpenCompilePopupListener());
        _popupBtn.setToolTipText("Open compiler shorcut (popup always on top)");
        
        _targetCombo = new JComboBox();
        
        _executeBtn = new JButton("execute");
        _executeBtn.addActionListener(new ExecuteAntTargetListener(_targetCombo));
        _executeBtn.setToolTipText("Execute selected ant target");
        
        
        add(_popupBtn);
        add(new JLabel("ant target : "));
        add(_targetCombo);
        add(_executeBtn);
        
        add(new JSeparator(JSeparator.VERTICAL));
        
        add(_compileBtn);
        add(_playBtn);
        
    }
    
    public JComboBox getTargetCombo(){
        return _targetCombo;
    }
}
