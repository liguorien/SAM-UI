/*
 * OutputClient.java
 *
 * Created on July 4, 2005, 7:09 PM
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
import com.liguorien.samui.MainUI;
import com.liguorien.samui.profiler.ProfilerTab;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import com.liguorien.utils.*;
import org.w3c.dom.*;

/**
 * @author Nicolas Désy - http://www.liguorien.com/blog/
 */
public class OutputClient extends Thread {
    
    private Socket _socket;
    private OutputServer _server;
    private BufferedReader _in;
    private PrintWriter _out = null;
    private String _name = null;
    private ProfilerTab _profiler = null;
    
    
    public OutputClient(OutputServer server, Socket socket) {
        _server = server;
        _socket = socket;
        
        try {
            _in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch(Exception ex){
            kill();
        }
    }
    
    
    public void run() {
        try {
            
            final char buffer[] = new char[10240];
            
            StringBuilder msg = new StringBuilder();
            
            while (true) {
                                
                //read data from client
                final int nbReaded = _in.read(buffer);
                
                //check if client is still connected
                if (nbReaded == -1) break;
                
                //handle data
                int offset = 0;
                for(int i=0; i<nbReaded; i++){
                    if(buffer[i] == '\0'){
                        _handleMessage(msg.append(buffer, offset, i-offset).toString());
                        msg = new StringBuilder();
                        offset = i+1;
                    }
                }
                
                if(offset < nbReaded){
                    msg.append(buffer, offset, nbReaded-offset);
                }
            }
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } finally {
            kill();
        }
    }
    
    
    private void _handleMessage(String msg){
       
        try{
            
            final Document dom = XmlUtil.parse(msg);
            
            //if this is the first message
            if(_name == null){
                _name = msg;
                
                EventQueue.invokeLater(new Runnable(){
                
                    public void run(){
                        
                        //create an output console
                        if("output".equals(XmlUtil.getAttribute(dom.getFirstChild(), "type"))){
                            String name = XmlUtil.getAttribute(dom.getFirstChild(), "name");
                            _name =  OutputManager.getInstance().createNewTab(name).getId();
                        }
                        
                        //create an output console and a profiler tab
                        else if("profiler".equals(XmlUtil.getAttribute(dom.getFirstChild(), "type"))){
                            String name = XmlUtil.getAttribute(dom.getFirstChild(), "name");
                            OutputTab tab  =  OutputManager.getInstance().createNewTab(name);
                            _name = tab.getId();
                            _profiler = new ProfilerTab(tab.getLabel(), OutputClient.this);                            
                            MainUI.getInstance().getMainPanel().addTab(_profiler);
                          //  _profiler.requestFocus();
                        }                        
                    }
                });
            }else{
                
                final String packetName = dom.getFirstChild().getNodeName();
                
                //if this is an output message, send it to OutputManager
                if("m".equals(packetName)){
                    OutputManager.getInstance().output(_name, dom);
                }else
                //
                if("profiler".equals(packetName)){
                    _profiler.readDOM(dom);
                }                
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void send(String msg){
        if(_out == null){
            try{
                _out = new PrintWriter(_socket.getOutputStream());
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        _out.print(msg + '\0');
        _out.flush();
    }
    
    private void kill(){
        
        if(_profiler != null && !_profiler.isDataParsed()){
            _profiler.close();
        }
        
        OutputManager.getInstance().notifyDisconnect(_name);
        _server.removeOutputClient(this);
        try {
            if(_out != null){
                _out.close();
            }
            _in.close();
            _socket.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}

