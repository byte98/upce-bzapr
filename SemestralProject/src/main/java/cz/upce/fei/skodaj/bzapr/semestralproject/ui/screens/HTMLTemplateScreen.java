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
package cz.upce.fei.skodaj.bzapr.semestralproject.ui.screens;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class representing HTML screen with supported templates
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class HTMLTemplateScreen extends HTMLScreen {
    
    /**
     * Original content of HTML file with template
     */
    private String originalContent = null;
    
    /**
     * Creates new HTML screen with supported templates
     * @param name Name of screen
     * @param fileName File with screen content
     */
    public HTMLTemplateScreen(String name, String fileName)
    {
        super(name, fileName);
    }
    
    /**
     * Replaces content placeholders with data
     * @param data Data which will replace placeholders
     */
    public void SetContent(Map<String, String> data)
    {        
        if (this.content == null)
        {         
            try
            {
                File html = new File("resources/" + this.fileName);
                Scanner reader = new Scanner(html);
                while (reader.hasNextLine())
                {
                    this.originalContent += reader.nextLine();
                }
                reader.close();
            }
            catch (FileNotFoundException ex)
            {
                Logger.getLogger(HTMLScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.content = this.originalContent;
        data.entrySet().forEach(entry -> {
            this.content = this.content.replace("{$" + entry.getKey() + "}", entry.getValue());
        });
    }
    
    @Override
    public String GetContent()
    {
        if (this.originalContent == null) // Load content only once
        {
            try
            {
                File html = new File("resources/" + this.fileName);
                Scanner reader = new Scanner(html);
                while (reader.hasNextLine())
                {
                    this.originalContent += reader.nextLine();
                }
                reader.close();
                this.content = this.originalContent;
            }
            catch (FileNotFoundException ex)
            {
                Logger.getLogger(HTMLScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this.content;
    }
    
    
    
}
