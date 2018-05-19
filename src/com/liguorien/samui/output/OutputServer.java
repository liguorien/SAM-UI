/*
 * OutputServer.java
 *
 * Created on July 4, 2005, 6:51 PM
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author  Nicolas Désy - http://www.liguorien.com/blog/
 */
public class OutputServer extends Thread {
    
    private List<OutputClient> _clients = new ArrayList<OutputClient>();
    private ServerSocket _server;
    private int _port;
    
    public void startServer(int port){
        _port = port;
        start();
    }
    
    public void run(){        
        
        try {
            
            _server = new ServerSocket(_port);
            
            while(true) {
                
                final Socket socket = _server.accept();
                final OutputClient client = new OutputClient(this, socket);
                
                _clients.add(client);                
                client.start();         
            }
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } finally{
            try{            
                _server.close();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
    
    public void removeOutputClient(OutputClient client){
        _clients.remove(client);
    }
}

