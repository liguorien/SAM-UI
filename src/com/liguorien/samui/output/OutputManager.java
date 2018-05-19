/*
 * OutputManager.java
 *
 * Created on July 4, 2005, 9:46 PM
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

package com.liguorien.samui.output;

import java.awt.Component;
import java.awt.EventQueue;
import java.util.HashMap;
import java.util.Map;
import com.liguorien.samui.ui.*;
import org.w3c.dom.*;

/**
 *
 * @author Nicolas Désy - http://www.liguorien.com/blog/
 */
public class OutputManager {
        
    
    /**
     * Output tabbed panel
     */
    private JTabbedPanel _outputPanel = null;
    public JTabbedPanel getOutputPanel(){
        if(_outputPanel == null)  _outputPanel = new JTabbedPanel();
        return _outputPanel;
    }
    
    
    /**
     * output console map
     */
    private Map<String,OutputTab> _tabs = new HashMap<String,OutputTab>();
    
    
    /**
     * disconnect client
     */    
    private Map<String,String> _disconnected = new HashMap<String,String>();
    
    
    /**
     * Notify manager that a client is disconnected
     * @param name client's ID
     */
    public void notifyDisconnect(String name){
        _disconnected.put(name,name);
    }
    
    /**
     * Check if the client mapped to this console is still connected. If not, console will be disposed
     * @param tab Output console
     */
    public void validConnection(OutputTab tab){
        if(_disconnected.get(tab.getId()) != null){
            _tabs.remove(tab.getId());
            _disconnected.remove(tab.getId());
        }
    }
    
    
    private OutputServer _server = null;
    
    private OutputManager() {
        _server = new OutputServer();
        _server.startServer(12000);
    }
    
    /**
     *
     * @param name
     * @return
     */
    public String clear(String name){
        if(_tabs.get(name) != null){
            _tabs.get(name).clear();
        }
        return name;
    }
    
    /**
     *
     * @param name
     * @return
     */
    public OutputTab createNewTab(String name){
        
        name = name.replaceAll("\\\\", "/");
        
        OutputTab tab = _tabs.get(name);
        
        if(tab == null){
            final String[] temp = name.split("/");
            tab = new OutputTab(name, temp[temp.length-1]);
            getOutputPanel().addTab(tab);
            _tabs.put(name, tab);
        }else{
            tab.clear();
        }
        
        tab.requestFocus();
        
        return tab;
    }
    
    
    /**
     * redirect message to the good console
     *@param name console's name (id)
     *@msg xml DOM that contains output message
     */
    public void output(String name, Document msg){
        final OutputTab tab = _tabs.get(name);
        if(tab != null){
            EventQueue.invokeLater(new OutputAppender(tab, msg));
        }
    }
    
    
    /**
     * there's only one manager, so it's a singleton
     */
    private static OutputManager _instance = null;
    public static OutputManager getInstance(){
        if(_instance == null)  _instance = new OutputManager();
        return _instance;
    }
}
