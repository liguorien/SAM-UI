/**
 * $ $ License.
 *
 * Copyright $ L2FProd.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.l2fprod.common.swing.plaf.metal;

import com.l2fprod.common.swing.JTaskPaneGroup;
import com.l2fprod.common.swing.plaf.basic.BasicTaskPaneGroupUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;

/**
 * Metal implementation of the <code>JTaskPaneGroup</code> UI. <br>
 */
public class MetalTaskPaneGroupUI extends BasicTaskPaneGroupUI {

  public static ComponentUI createUI(JComponent c) {
    return new MetalTaskPaneGroupUI();
  }

  private static int TITLE_HEIGHT = 25;

  private static int ROUND_HEIGHT = 5;

  protected void installDefaults() {
    super.installDefaults();
    group.setOpaque(false);
  }

  protected int getTitleHeight() {
    return TITLE_HEIGHT;
  }

  protected Border createPaneBorder() {
    return new MetalPaneBorder();
  }

  /**
   * The border of the taskpane group paints the "text", the "icon",
   * the "expanded" status and the "special" type.
   *  
   */
  class MetalPaneBorder extends PaneBorder {

    protected void paintExpandedControls(JTaskPaneGroup group, Graphics g) {
      ((Graphics2D)g).setRenderingHint(
        RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);

      int ovalSize = TITLE_HEIGHT - 2 * ROUND_HEIGHT;

      Color paintColor;
      if (mouseOver) {
        if (group.isSpecial()) {
          paintColor = specialTitleOver;
        } else {
          paintColor = titleOver;
        }
      } else {
        if (group.isSpecial()) {
          paintColor = specialTitleForeground;
        } else {
          paintColor = titleForeground;
        }
      }

      if (mouseOver) {
        int x = group.getWidth() - TITLE_HEIGHT;
        int y = ROUND_HEIGHT - 1;
        int x2 = x + ovalSize;
        int y2 = y + ovalSize;
        g.setColor(paintColor);
        g.drawLine(x, y, x2, y);
        g.drawLine(x, y, x, y2);
        g.drawLine(x2, y, x2, y2);
        g.drawLine(x, y2, x2, y2);
      }

      ChevronIcon chevron;
      if (group.isExpanded()) {
        chevron = new ChevronIcon(true);
      } else {
        chevron = new ChevronIcon(false);
      }
      int chevronX =
        group.getWidth()
          - TITLE_HEIGHT
          + ovalSize / 2
          - chevron.getIconWidth() / 2;
      int chevronY =
        ROUND_HEIGHT + (ovalSize / 2 - chevron.getIconHeight()) - 1;
      g.setColor(paintColor);
      chevron.paintIcon(group, g, chevronX, chevronY);
      chevron.paintIcon(
        group,
        g,
        chevronX,
        chevronY + chevron.getIconHeight() + 1);

      ((Graphics2D)g).setRenderingHint(
        RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_OFF);
    }

  }

}