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
package cz.upce.fei.skodaj.bzapr.semestralproject.ui.gui;

import cz.upce.fei.skodaj.bzapr.semestralproject.utils.IconLoader;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Class representing main window of program
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class MainWindow extends JFrame {
    
    /**
     * Panel which contains starting controls
     */
    private JPanel startControls;
    
    /**
     * Button which starts scanning
     */
    private JButton startButton;
    
    /**
     * Panel containing main information
     */
    private JPanel mainPanel;
    
    /**
     * Label containing title information
     */
    private JLabel titleImg;
    
    /**
     * Creates new instance of main window
     * @param title Title of window
     */
    public MainWindow(String title)
    {
        this.setTitle(title);
        this.setLayout(new BorderLayout());
        
        this.startControls = new JPanel(new GridLayout());
        this.getContentPane().add(this.startControls, BorderLayout.NORTH);
        
        this.startButton = new JButton("Start scan", IconLoader.GetIcon("play_32.png"));
        this.startControls.add(this.startButton);
        
        this.mainPanel = new JPanel(new GridLayout());
        this.getContentPane().add(this.mainPanel, BorderLayout.CENTER);
        
        this.titleImg = new JLabel(IconLoader.GetIcon("title.png"));
        this.mainPanel.add(this.titleImg);
        
    }
}
