/*
 * AntThread.java
 *
 * Created on July 1, 2005, 1:22 PM
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

import com.liguorien.samui.project.ProjectConfig;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;



/**
 *
 * @author Nicolas Désy - http://www.liguorien.com/blog/
 */
public class AntThread extends Thread {
    
    private ProjectConfig _project;
    private InputStream _input = null;
    private Process _ant;
    
    /**
     * Creates a new instance of AntThread 
     */
    public AntThread(Process ant, ProjectConfig project) {
        _input = ant.getInputStream();
        _ant = ant;
         _project = project;
    }
    
    public void run(){
        try{
            
            final AntOutput output = AntOutput.getInstance();
            
            output.clear();
            output.requestFocus();
            
            final BufferedReader input =  new BufferedReader(new InputStreamReader(_input));
            String line = null;
            while ((line = input.readLine()) != null) {
                EventQueue.invokeLater(new AppendLine(line));
            }
            input.close();
            
            if(_project.isUserWantToRunSWF() && _ant.exitValue() == 0){
                _project.runSWF();
            }
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    /**
     * Command that append a new line to ant console
     */
    class AppendLine implements Runnable {
        private String _line = null;
        public AppendLine(String line){
            _line = line;
        }
        public void run(){
            AntOutput.getInstance().append(_line);
        }
    }
}
