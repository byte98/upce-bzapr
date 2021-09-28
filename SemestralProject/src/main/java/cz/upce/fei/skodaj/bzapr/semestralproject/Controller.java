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

import cz.upce.fei.skodaj.bzapr.semestralproject.ui.MainWindow;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.help.Help;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.help.HelpFactory;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.screens.Screen;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.screens.ScreenFactory;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Controller of whole program
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Controller {
    /**
     * Instance of controller class
     */
    private static Controller instance = null;
    
    /**
     * List of all available screens
     */
    private final Map<String, Screen> screens;
    
    /**
     * List of all available helps to commands
     */
    private final Map<String, Help[]> helps;
    
    /**
     * Main window of the program
     */
    private MainWindow mainWindow;
    
    /**
     * Creates new instance of controller
     */
    private Controller()
    {
        this.screens = new HashMap<>();
        this.AddScreens();
        
        this.helps = new HashMap<>();
        this.AddHelps();
    }
    
    /**
     * Adds helps managed by controller
     */
    private void AddHelps()
    {
        // Welcome help
        Help[] wH = {
          HelpFactory.CreateSimpleHelp("sale", Color.CYAN, "Rezim prodeje"),
          HelpFactory.CreateSimpleHelp("tariff", Color.CYAN, "Rezim upravy tarifu"),
          HelpFactory.CreateSimpleHelp("exit", Color.MAGENTA, "Ukoncit program")
        };
        this.helps.put("welcome", wH);
        this.helps.put("welcome-no-tariff", wH);
        
        // Exit help
        Help[] ex = {
          HelpFactory.CreateSimpleHelp("yes", Color.YELLOW, "Ukoncit program"),
          HelpFactory.CreateSimpleHelp("no", Color.YELLOW, "Zrusit")
        };
        this.helps.put("exit", ex);
        
    }
    
    /**
     * Adds screens managed by controller
     */
    private void AddScreens()
    {
        Screen screens[] = {
          ScreenFactory.CreateHTMLScreen("welcome", "welcome.html"),
          ScreenFactory.CreateHTMLScreen("welcome-no-tariff", "welcome-no-tariff.html"),
          ScreenFactory.CreateHTMLScreen("exit", "exit.html")
        };
        
        for (Screen screen : screens) {
            this.AddScreen(screen);
        }
   }
    
    /**
     * Adds screen to controller
     * @param screen Screen which will be added
     */
    private void AddScreen(Screen screen)
    {
        this.screens.put(screen.GetName(), screen);
    }
    
    /**
     * Gets screen by its name
     * @param name Name of screen
     * @return Screen selected by its name
     */
    private Screen GetScreen(String name)
    {
        return this.screens.get(name);
    }
    
    /**
     * Starts program
     */
    public void Start()
    {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run()
            {
                mainWindow = new MainWindow();
                mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainWindow.pack();
                mainWindow.setVisible(true);
                mainWindow.ShowScreen(GetScreen("welcome"));
                mainWindow.ShowHelp(helps.get("welcome"));
                mainWindow.SetController(Controller.instance);
                mainWindow.SetStrict(true);
            }
        });
    }
    
    /**
     * Gets controller of program
     * @return Controller of program
     */
    public static Controller GetController()
    {
        Controller reti = Controller.instance;
        if (Controller.instance == null)
        {
            Controller.instance = new Controller();
            reti = Controller.instance;
        }
        return reti;
    }
    
    /**
     * Method which handles commands
     * @param command Command which will be handled
     */
    public void HandleCommand(String command)
    {
        if ("exit".equals(command.toLowerCase()))
        {
            this.mainWindow.ShowScreen(this.GetScreen("exit"));
            this.mainWindow.ShowHelp(this.helps.get("exit"));
        }
    }
}
