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

import cz.upce.fei.skodaj.bzapr.semestralproject.data.Station;
import cz.upce.fei.skodaj.bzapr.semestralproject.data.Stations;
import cz.upce.fei.skodaj.bzapr.semestralproject.data.Tariffs;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.MainWindow;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.help.Help;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.help.HelpFactory;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.screens.HTMLTemplateScreen;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.screens.Screen;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.screens.ScreenFactory;
import java.awt.Color;
import java.awt.event.WindowEvent;
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
     * Actual state of program
     */
    private String state;
    
    /**
     * Previous state of program
     */
    private String previousState;
    
    /**
     * Actual command prefix
     */
    private String commandPrefix;
    
    /**
     * Previously used command prefix
     */
    private String previousCommandPrefix;
    
    /**
     * Manager of stations in system
     */
    private Stations stationManager;
    
    /**
     * Manager of tariffs in the system
     */
    private Tariffs tariffManager;
    
    /**
     * Creates new instance of controller
     */
    private Controller()
    {
        this.screens = new HashMap<>();
        this.AddScreens();
        
        this.helps = new HashMap<>();
        this.AddHelps();
        
        this.commandPrefix = "";
        this.previousCommandPrefix = "";
        
        this.stationManager = Stations.GetInstance();
        this.tariffManager = Tariffs.GetInstance();
    }
    
    /**
     * Adds helps managed by controller
     */
    private void AddHelps()
    {
        // Welcome help
        Help[] wH = {
          HelpFactory.CreateSimpleHelp("sale", Color.CYAN, "Rezim prodeje"),
          HelpFactory.CreateSimpleHelp("tariffs", Color.CYAN, "Rezim upravy tarifu"),
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
        
        // Tariffs help
        Help[] tH = {
          HelpFactory.CreateSimpleHelp("stations", Color.YELLOW, "Rezim upravy stanic"),
          HelpFactory.CreateSimpleHelp("distances", Color.BLUE, "Rezim upravy vzdalenosti"),
          HelpFactory.CreateSimpleHelp("edit", Color.GREEN, "Rezim upravy tarifu"),
          HelpFactory.CreateSimpleHelp("back", Color.MAGENTA, "Zpet")
        };
        this.helps.put("tariffs", tH);
        
        // Stations help
        Help[] sH = {
          HelpFactory.CreateSimpleHelp("<zkratka nebo nazev>", Color.YELLOW, "Rezim upravy stanice"),
          HelpFactory.CreateSimpleHelp("add", Color.CYAN, "Rezim pridani stanice"),
          HelpFactory.CreateSimpleHelp("back", Color.MAGENTA, "Zpet")
        };
        this.helps.put("stations", sH);
    }
    
    /**
     * Adds screens managed by controller
     */
    private void AddScreens()
    {
        Screen screens[] = {
          ScreenFactory.CreateHTMLScreen("welcome", "welcome.html"),
          ScreenFactory.CreateHTMLScreen("welcome-no-tariff", "welcome-no-tariff.html"),
          ScreenFactory.CreateHTMLScreen("exit", "exit.html"),
          new HTMLTemplateScreen("tariffs", "tariffs.html"),
          new HTMLTemplateScreen("stations", "stations.html")

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
                state = "welcome";
                previousState = null;
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
        switch (command.toLowerCase())
        {
            case "exit":
                this.mainWindow.SetStrict(true);
                this.mainWindow.ShowScreen(this.GetScreen("exit"));
                this.mainWindow.ShowHelp(this.helps.get("exit"));
                this.previousState = this.state;
                this.state = "exit";
                this.previousCommandPrefix = this.commandPrefix;
                this.commandPrefix = "/exit?";
                this.mainWindow.SetCommandMode(this.commandPrefix);
                break;
            case "yes":
                if ("exit".equals(this.state)) // Exit confirmation
                {
                    this.mainWindow.dispatchEvent(new WindowEvent(this.mainWindow, WindowEvent.WINDOW_CLOSING));
                }
                break;
            case "no":
                if ("exit".equals(this.state))
                {
                    this.mainWindow.SetStrict(true);
                    this.state = this.previousState;
                    this.previousState = "exit";
                    this.mainWindow.ShowScreen(this.GetScreen(this.state));
                    this.mainWindow.ShowHelp(this.helps.get(this.state));                    
                    this.commandPrefix = this.previousCommandPrefix;
                    this.previousCommandPrefix = "/exit?";
                    this.mainWindow.SetCommandMode(this.commandPrefix);
                }
                break;
            case "tariffs":
                this.mainWindow.SetStrict(true);
                Map<String,String> stationData = new HashMap<>();
                Station[] stations = this.stationManager.GetAllStations();
                String stationsList = "";
                int idx = 0;
                for (Station s: stations)
                {
                    stationsList += s.GetName();
                    if (idx < stationData.size() - 1) stationsList += ",";
                }                
                stationData.put("station_list", this.TrimString(stationsList, 90));
                this.mainWindow.ShowScreen(this.SetScreensContent("tariffs", stationData));
                this.mainWindow.ShowHelp(this.helps.get("tariffs"));
                this.previousState = this.state;
                this.state = "tariffs";
                this.previousCommandPrefix = this.commandPrefix;
                this.commandPrefix = "/tariffs";
                this.mainWindow.SetCommandMode(this.commandPrefix);
                break;
            case "back":                
                this.mainWindow.SetStrict(true);
                if (this.previousState == "stations")
                {
                    this.mainWindow.ShowHelp(this.helps.get("welcome"));
                    this.mainWindow.ShowScreen(this.GetScreen("welcome"));
                    this.previousState = this.state;
                    this.state = "welcome";
                    this.previousCommandPrefix = this.commandPrefix;
                    this.commandPrefix = "";
                    this.mainWindow.SetCommandMode(this.commandPrefix);
                }
                else
                {
                    this.mainWindow.ShowScreen(this.GetScreen(this.previousState));
                    this.mainWindow.ShowHelp(this.helps.get(this.previousState));
                    String tmp = this.state;
                    this.state = this.previousState;
                    this.previousState = tmp;
                    tmp = this.commandPrefix;
                    this.commandPrefix = this.previousCommandPrefix;
                    this.previousCommandPrefix = tmp;
                    this.mainWindow.SetCommandMode(this.commandPrefix);
                }                
                break;
            case "stations":
                this.mainWindow.SetStrict(false);
                String stationsTr = " ";
                for (Station s: this.stationManager.GetAllStations())
                {
                    stationsTr += "<tr><td>" + s.GetAbbrevation() + "</td><td>" + s.GetName() + "</td></tr>";
                }
                Map<String, String> stationsTrData = new HashMap<>();
                stationsTrData.put("stations_tr", stationsTr);
                this.mainWindow.ShowScreen(this.SetScreensContent("stations", stationsTrData));
                this.mainWindow.ShowHelp(this.helps.get("stations"));                
                this.previousState = this.state;
                this.state = "stations";
                this.previousCommandPrefix = this.commandPrefix;
                this.commandPrefix = "/tariffs/stations";
                this.mainWindow.SetCommandMode(this.commandPrefix);
        }
    }
    
    /**
     * Changes size of string to required length
     * @param input Input string which size will be changed
     * @param length Required string length
     * @return String resized to required length
     */
    private String TrimString(String input, int length)
    {
        String reti = "";
        if (input.length() > length - 3)
        {
            for (int i = 0; i < length - 3; i++)
            {
                reti += input.charAt(i);
            }
            reti += "...";
        }
        else
        {
            reti = input;
        }
        return reti;
    }
    
    /**
     * Sets content to the screen
     * @param name Name of the screen
     * @param data Data of the screen
     * @return Screen with inserted data
     */
    private Screen SetScreensContent(String name, Map<String, String> data)
    {
        HTMLTemplateScreen sc = (HTMLTemplateScreen)this.GetScreen(name);
        sc.SetContent(data);
        return sc;
    }
}
