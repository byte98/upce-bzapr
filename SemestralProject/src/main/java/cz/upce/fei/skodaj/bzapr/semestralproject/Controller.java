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
import cz.upce.fei.skodaj.bzapr.semestralproject.states.State;
import cz.upce.fei.skodaj.bzapr.semestralproject.states.StateFactory;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.HelpWindow;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
     * List of all available states of program
     */
   private final List<State> states;
   
   /**
    * Main window of the program
    */
   private MainWindow mainWindow;
   
   /**
    * Window with help needed when selling tickets
    */
   private HelpWindow helpWindow;
   
   /**
    * Actual state of program
    */
   private State actualState = null;
   
   /**
    * Previous state of program
    */
   private State previousState = null;
    
    /**
     * Creates new instance of controller
     */
    private Controller()
    {
        this.states = new ArrayList<>();        
        this.AddStates();
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
                mainWindow.SetController(Controller.instance);
                ChangeState("welcome");
                
                helpWindow = new HelpWindow();
                helpWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                helpWindow.pack();
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
     * Adds available states of program
     */
    private void AddStates()
    {
        this.states.add(StateFactory.CreateState(this, "welcome"));
        this.states.add(StateFactory.CreateState(this, "exit"));
        this.states.add(StateFactory.CreateState(this, "data"));
        this.states.add(StateFactory.CreateState(this, "stations"));
        this.states.add(StateFactory.CreateState(this, "stations-add"));
        this.states.add(StateFactory.CreateState(this, "stations-add-name"));
        this.states.add(StateFactory.CreateState(this, "stations-add-abbr"));
        this.states.add(StateFactory.CreateState(this, "stations-edit"));
        this.states.add(StateFactory.CreateState(this, "stations-edit-name"));
        this.states.add(StateFactory.CreateState(this, "stations-edit-abbr"));
        this.states.add(StateFactory.CreateState(this, "stations-delete"));
        this.states.add(StateFactory.CreateState(this, "distances"));
        this.states.add(StateFactory.CreateState(this, "distances-create"));
        this.states.add(StateFactory.CreateState(this, "distances-view"));
        this.states.add(StateFactory.CreateState(this, "distances-view-station"));
        this.states.add(StateFactory.CreateState(this, "distances-set-from"));
        this.states.add(StateFactory.CreateState(this, "distances-set-to"));
        this.states.add(StateFactory.CreateState(this, "distances-set-distance"));
        this.states.add(StateFactory.CreateState(this, "distances-set"));
        this.states.add(StateFactory.CreateState(this, "tariffs"));
        this.states.add(StateFactory.CreateState(this, "tariffs-zone-name"));
        this.states.add(StateFactory.CreateState(this, "tariffs-zone-abbr"));
        this.states.add(StateFactory.CreateState(this, "tariffs-zone"));
        this.states.add(StateFactory.CreateState(this, "tariffs-zone-zones"));
        this.states.add(StateFactory.CreateState(this, "tariffs-zone-prices"));
        this.states.add(StateFactory.CreateState(this, "tariffs-zone-view"));
        this.states.add(StateFactory.CreateState(this, "tariffs-zone-delete"));
        this.states.add(StateFactory.CreateState(this, "tariffs-dist-name"));
        this.states.add(StateFactory.CreateState(this, "tariffs-dist-abbr"));
        this.states.add(StateFactory.CreateState(this, "tariffs-dist"));
        this.states.add(StateFactory.CreateState(this, "tariffs-dist-prices"));
        this.states.add(StateFactory.CreateState(this, "tariffs-dist-view"));
        this.states.add(StateFactory.CreateState(this, "tariffs-dist-delete"));
        this.states.add(StateFactory.CreateState(this, "ticket"));
    }
    
    /**
     * Method which handles commands
     * @param command Command which will be handled
     */
    public void HandleCommand(String command)
    {
        this.actualState.HandleInput(command);
    }
    
    /**
     * Changes size of string to required length
     * @param input Input string which size will be changed
     * @param length Required string length
     * @return String resized to required length
     */
    public static String TrimString(String input, int length)
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
     * Gets state of program by its name
     * @param name Name of state
     * @return State of program or <code>NULL</code>
     */
    private State GetState (String name)
    {
        State reti = null;
        Iterator<State> it = this.states.iterator();
        while (it.hasNext())
        {
            State s = it.next();
            if (s != null && s.GetName().toLowerCase() == name.toLowerCase())
            {
                reti = s;
                break;
            }
        }
        return reti;
    }
    
    /**
     * Changes state of program
     * @param nextState Name of state of program
     */
    public void ChangeState(String nextState)
    {
        State state = this.GetState(nextState);
        if (state != null)
        {
            this.previousState = this.actualState;
            this.actualState = state;
            this.actualState.Load();
            this.mainWindow.ShowScreen(this.actualState.GetScreen());
            this.mainWindow.SetStrict(this.actualState.GetStrict());
            this.mainWindow.SetCommandMode(this.actualState.GetCommandPrefix());
            this.mainWindow.ShowHelp(this.actualState.GetHelps());
            this.actualState.Control();
        }
    }
    
    /**
     * Changes state of program
     * @param nextState Name of state of program
     * @param data Data which will be displayed on screen
     */
    public void ChangeState(String nextState, Map<String, String> data)
    {
        State state = this.GetState(nextState);
        if (state != null)
        {
            this.previousState = this.actualState;
            this.actualState = state;
            this.actualState.Load();
            this.mainWindow.ShowScreen(this.actualState.GetScreen(data));
            this.mainWindow.SetStrict(this.actualState.GetStrict());
            this.mainWindow.SetCommandMode(this.actualState.GetCommandPrefix());
            this.mainWindow.ShowHelp(this.actualState.GetHelps());
            this.actualState.Control();
        }
    }
    
    /**
     * Changes state of program to previous one
     */
    public void ChangeToPreviousState()
    {
        if (this.previousState != null)
        {
            State s = this.actualState;
            this.actualState = this.previousState;
            this.previousState = s;
            this.ChangeState(this.actualState.GetName());
        }
    }
    
    /**
     * Changes state of program to previous one
     * @param data Data which will be displayed on screen
     */
    public void ChangeToPreviousState(Map<String, String> data)
    {
        if (this.previousState != null)
        {
            State s = this.actualState;
            this.actualState = this.previousState;
            this.previousState = s;
            this.ChangeState(this.actualState.GetName(), data);
        }
    }
    
    /**
     * Stops program
     */
    public void Stop()
    {
        this.mainWindow.dispatchEvent(new WindowEvent(this.mainWindow, WindowEvent.WINDOW_CLOSING));
    }
    
    /**
     * Shows error message in console
     * @param message Message which will be shown
     */
    public void ShowError(String message)
    {
        this.mainWindow.ShowInputError(message);
    }
    
    /**
     * Shows success message in console
     * @param message Message which will be shown
     */
    public void ShowSucess(String message)
    {
        this.mainWindow.ShowInputSuccess(message);
    }
    
    /**
     * Redraws actual screen
     */
    public void ReDraw()
    {
        this.mainWindow.ShowScreen(this.actualState.GetScreen());
        this.mainWindow.ShowHelp(this.actualState.GetHelps());
        this.mainWindow.SetCommandMode(this.actualState.GetCommandPrefix());
    }
    
    /**
     * Redraws actual screen
     * @param data data which will be displayed on the screen
     */
    public void ReDraw(Map<String, String> data)
    {
        this.mainWindow.ShowScreen(this.actualState.GetScreen(data));
        this.mainWindow.ShowHelp(this.actualState.GetHelps());
        this.mainWindow.SetCommandMode(this.actualState.GetCommandPrefix());
    }
    
    /**
     * Shows tariffs in help window
     */
    public void ShowTariffsHelp()
    {
        if (this.helpWindow.isVisible() == false)
        {
            this.helpWindow.setVisible(true);
        }
        this.helpWindow.setLocation(this.mainWindow.getX() + this.mainWindow.getWidth() + 5, this.mainWindow.getY());
        this.helpWindow.ShowTariffs();
        this.mainWindow.SetFocusOnCommandLine();
    }
    
    
    /**
     * Shows stations in help window
     */
    public void ShowStationsHelp()
    {
        if (this.helpWindow.isVisible() == false)
        {
            this.helpWindow.setVisible(true);
        }
        this.helpWindow.setLocation(this.mainWindow.getX() + this.mainWindow.getWidth() + 5, this.mainWindow.getY());
        this.helpWindow.ShowStations();
        this.mainWindow.SetFocusOnCommandLine();
    }
    
    /**
     * Hides help window
     */
    public void HideHelp()
    {
        this.helpWindow.setVisible(false);
    }
}
