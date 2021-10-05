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
package cz.upce.fei.skodaj.bzapr.semestralproject.states;

import cz.upce.fei.skodaj.bzapr.semestralproject.Controller;
import cz.upce.fei.skodaj.bzapr.semestralproject.data.Station;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.help.Help;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.help.HelpFactory;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.screens.HTMLTemplateScreen;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.screens.Screen;
import java.awt.Color;

/**
 * Class representing state of program which displays creating of table of distances
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class DistancesCreate extends State
{

    /**
     * Array of all stations
     */
    private Station[] stations;
    
    /**
     * Index of stations array which selects actual origin
     */
    private int origin;
    
    /**
     * Index of stations array which selects actual destination
     */
    private int destination;
    
    /**
     * Creates new state of program with table of distances
     * @param controller 
     */
    public DistancesCreate(Controller controller)
    {
        super(controller);
        this.commandPrefix = "/data/distances/create";
        this.screen = new HTMLTemplateScreen("distances-create", "distances-create.html");
        this.name = "distances-create";
        this.strict = false;
        
        this.helps = new Help[2];
        this.helps[0] = HelpFactory.CreateSimpleHelp("<cele cislo>", Color.YELLOW, "Vzdalenost mezi stanicemi v kilometrech");
        this.helps[1] = HelpFactory.CreateSimpleHelp("cancel", Color.MAGENTA, "Zrusit");
    }
    
    
    /**
     * Checks, whether input contains only integer
     * @param input Input which will be checked
     * @return <code>TRUE</code> if input contains integer only, <code>FALSE</code> otherwise
     * @author Jonas K https://stackoverflow.com/questions/237159/whats-the-best-way-to-check-if-a-string-represents-an-integer-in-java
     */
    private boolean CheckInt(String input)
    {
        if (input == null)
        {
            return false;
        }
        int length = input.length();
        if (length == 0)
        {
            return false;
        }
        int i = 0;
        if (input.charAt(0) == '-')
        {
            if (length == 1)
            {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++)
        {
            char c = input.charAt(i);
            if (c < '0' || c > '9')
            {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public void Load()
    {
        this.stations = cz.upce.fei.skodaj.bzapr.semestralproject.data.Stations.GetInstance().GetAllStations();
        cz.upce.fei.skodaj.bzapr.semestralproject.data.Distances.GetInstance().SetAllZeroes();
    }
    
    @Override
    public void HandleInput(String input)
    {
        if (input.toLowerCase().equals("cancel"))
        {
            this.controller.ChangeState("distances");
        }
        else if (this.CheckInt(input) == true)
        {
            int value = Integer.parseInt(input);
            if (value < 0)
            {
                this.controller.ShowError("Zadane cislo nesmi byt mensi nez nula!");
            }
        }
        else
        {
            this.controller.ShowError("Neznamy prikaz '" + input + "'!");
        }
    }
    
}
