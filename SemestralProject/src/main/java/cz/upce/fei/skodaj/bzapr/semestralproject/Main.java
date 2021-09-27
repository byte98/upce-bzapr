/*
 * Copyright (C) 2021 Jiri Skoda <jiri.skoda@student.upce.cz>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package cz.upce.fei.skodaj.bzapr.semestralproject;

import cz.upce.fei.skodaj.bzapr.semestralproject.ui.gui.MainWindow;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Entry point of whole program
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Main {
    
    /**
     * Logger of the class
     */
    static Logger logger;
    
    /**
     * Main function of program
     * @param args Arguments of program
     */
    public static void main(String[] args) {
        Main.logger = Logger.getLogger("NetInspector");
        // Set windows look and feel
        try
        {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex)
        {
            Main.logger.log(Level.WARNING, ex.getLocalizedMessage());
        }
        
        // Just run GUI in separate thread
        SwingUtilities.invokeLater(new Runnable() {
             @Override
             public void run() {
                    MainWindow window;
                    window = new MainWindow("Net Inspector");
                    window.pack();
                    window.setVisible(true);
                  
             }
        });
    }
}
