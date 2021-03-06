
/**
 *   Copyright 2005  Nicolas D�sy.  All rights reserved.
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
package com.liguorien.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Nicolas D�sy - http://www.liguorien.com/blog/
 */
public class IOUtil {
    
    private final static byte[] _buffer = new byte[51200];
    
    public static void copy(InputStream in, OutputStream out) throws IOException{        
        try {
            while (true) {
                synchronized(_buffer){
                    int amountRead = in.read(_buffer);
                    if (amountRead == -1) break;
                    out.write(_buffer, 0, amountRead);                    
                }                
            }
        }finally {
            if (in != null) in.close();
            if (out != null) out.close();
        }
    }   
}