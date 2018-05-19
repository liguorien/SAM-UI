/*
 * OutputEditorKit.java
 *
 * Created on July 10, 2005, 11:22 AM
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
import javax.swing.text.*;

public class OutputEditorKit extends StyledEditorKit {
    public ViewFactory getViewFactory() {
        return new StyledViewFactory();
    }
    
    static class StyledViewFactory implements ViewFactory {
        public View create(Element elem) {
            String kind = elem.getName();
            
            if (kind != null) {
                if (kind.equals(AbstractDocument.ContentElementName)) {
                    return new MyLabelView(elem);
                } else if (kind.equals(AbstractDocument.ParagraphElementName)) {
                    return new ParagraphView(elem);
                } else if (kind.equals(AbstractDocument.SectionElementName)) {
                    return new NoWrapBoxView(elem, View.Y_AXIS);
                } else if (kind.equals(StyleConstants.ComponentElementName)) {
                    return new ComponentView(elem);
                } else if (kind.equals(StyleConstants.IconElementName)) {
                    return new IconView(elem);
                }
            }
            
            return new LabelView(elem);
        }
    }
    
    static class NoWrapBoxView extends BoxView {
        public NoWrapBoxView(Element elem, int axis) {
            super(elem, axis);
        }
        
        public void layout(int width, int height) {
            super.layout(width, height);
        }
        
        public float getMinimumSpan(int axis) {
            return super.getPreferredSpan(axis);
        }
    }
    
    static class MyLabelView extends LabelView {
        public MyLabelView(Element elem) {
            super(elem);
        }
        
        public float getPreferredSpan(int axis) {
            if (axis == View.X_AXIS) {
                TabExpander ex = getTabExpander();
                
                if (ex == null) {
                    //paragraph implements TabExpander
                    ex =(TabExpander)this.getParent().getParent();
                    getTabbedSpan(0, ex);
                }
            }
            
            return super.getPreferredSpan(axis);
        }
    }
}