/*
 * StackElementIcon.java
 *
 * Created on July 25, 2005, 12:49 PM
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

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;

/**
 *
 * @author Nicolas Désy - http://www.liguorien.com/blog/
 */
public class StackElementIcon implements Icon{
    
    private final static int WIDTH = 18;
    private final static int HEIGHT = 18;
    private final static int PADDING = 4;
    private Color _color = Color.GREEN;
    
    private StackElementIcon(Color color){
        _color = color;
    }
    
    public void setColor(Color c){
        _color = c;
    }
    
    public int getIconHeight() {
        return HEIGHT;
    }
    
    public int getIconWidth() {
        return WIDTH;
    }
    
    public void paintIcon(Component component, Graphics g, int x, int y) {
        g.setColor(_color);
        g.fillRect(x+PADDING, y+PADDING, WIDTH-PADDING*2, HEIGHT-PADDING*2);
    }
    
    
    private final static Color[] _colors = new Color[]{
        Color.decode("0x0EF113"),
        Color.decode("0x6FF20D"),
        Color.decode("0xBAF30C"),
        Color.decode("0xDBF10E"),
        Color.decode("0xF1E00E"),
        Color.decode("0xF2A80D"),
        Color.decode("0xFF0D00")
    };
    
    private final static Icon[] _icons;
    static {
        final int size = _colors.length;
        _icons = new Icon[size];
        for(int i=0 ;i<size; i++){
            _icons[i] = new StackElementIcon(_colors[i]);
        }      
    }
    
    private final static int[] _limit = new int[]{ 5, 10, 20, 50, 100, 150 };
    
    public static Icon getIcon(int time){
        for(int i=0; i<_limit.length; i++){
            if(time < _limit[i]) return _icons[i];
        }
        return _icons[_icons.length-1];
    }
    
    public static Color getColor(int time){
        for(int i=0; i<_limit.length; i++){
            if(time < _limit[i]) return _colors[i];
        }
        return _colors[_colors.length-1];
    }
}
