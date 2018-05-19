/*
 * Library.java
 *
 * Created on July 2, 2005, 1:26 PM
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

/**
 *
 * @author Nicolas Désy - http://www.liguorien.com/blog/
 */
public class Library {
    
    /**
     *singleton
     */
/*    private static Library _instance = null;
    public static Library getInstance(){
        if(_instance == null){
            _instance = new Library();
        }
        return _instance;
    }*/
    
    
        
    
    /**
     * Holds value of property _model.
     */
    private LibraryModel _model;
    
    /**
     * Holds value of property _view.
     */
    private LibraryView _view;
    
    /** Creates a new instance of Library */
    public Library() {
        _model = new LibraryModel();
    }
    
    
    
    /**
     * Getter for property view.
     * @return Value of property view.
     */
    public LibraryView getView() {
        if(_view == null){
            _view = new LibraryView(getModel());
        }      
        return _view;
    }
    
    /**
     * Getter for property model.
     * @return Value of property model.
     */
    public LibraryModel getModel() {
        if(_model == null){
            _model = new LibraryModel();
        }      
        return _model;
    }  
}
