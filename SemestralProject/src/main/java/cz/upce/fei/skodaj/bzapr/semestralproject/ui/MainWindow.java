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
package cz.upce.fei.skodaj.bzapr.semestralproject.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Class representing main window of program
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class MainWindow extends JFrame
{
    /**
     * Text area representing output console
     */
    private JTextArea out;
    
    /**
     * Text area representing input console
     */
    private JTextArea in;
    
    /**
     * Panel containing input controls
     */
    private JPanel input;
    
    /**
     * Text area containing help
     */
    private JTextArea help;
    
    public MainWindow()
    {
        super("JTicket");
        this.setLayout(new GridLayout(2, 1));
        this.setPreferredSize(new Dimension(800, 600));
        
        this.out = new JTextArea();
        this.getContentPane().add(this.out);
        this.out.setBackground(Color.BLACK);
        this.out.setForeground(Color.LIGHT_GRAY);
        this.out.setFont(new Font("Lucida Console", Font.PLAIN, 16));
        this.out.setEditable(false);
        
        this.input = new JPanel(new GridLayout(2, 1));
        this.getContentPane().add(this.input);
        
        this.help = new JTextArea();
        this.input.add(this.help);
        this.help.setBackground(Color.BLACK);
        this.help.setForeground(Color.LIGHT_GRAY);
        this.help.setFont(new Font("Lucida Console", Font.PLAIN, 16));
        this.help.setEditable(false);
        
        this.in = new JTextArea();
        this.input.add(this.in);
        this.in.setBackground(Color.BLACK);
        this.in.setForeground(Color.LIGHT_GRAY);
        this.in.setFont(new Font("Lucida Console", Font.PLAIN, 16));
    }
}
