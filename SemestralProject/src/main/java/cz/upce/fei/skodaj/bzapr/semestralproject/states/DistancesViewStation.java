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
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing state of program which displays table of distances from station
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class DistancesViewStation extends State
{

    /**
     * Origin station from which distances will be displayed
     */
    private Station origin;
    
    /**
     * Creates new state of program with table of distances from station
     * @param controller 
     */
    public DistancesViewStation(Controller controller)
    {
        super(controller);
        this.commandPrefix = "/data/distances/view/";
        this.screen = new HTMLTemplateScreen("distances-view-station", "distances-view-station.html");
        this.name = "distances-view-station";
        this.strict = true;
        
        this.helps = new Help[1];
        this.helps[0] = HelpFactory.CreateSimpleHelp("back", Color.MAGENTA, "Zpet");
    }
    
    @Override
    public Screen GetScreen(Map<String, String> data)
    {
       Station s = cz.upce.fei.skodaj.bzapr.semestralproject.data.Stations.GetInstance().GetStation(data.get("station"));
       if (s != null)
       {
           data.put("station_from", s.GetName() + " (" + s.GetAbbrevation() + ")");
           data.put("stations_distances_tr", cz.upce.fei.skodaj.bzapr.semestralproject.data.Distances.GetInstance().GenerateDistancesRows(s));
           this.origin = s;
           this.commandPrefix = "/data/distances/view/" + s.GetAbbrevation().toLowerCase();
           ((HTMLTemplateScreen) this.screen).SetContent(data);           
       }
       return this.screen;
    }
    
    
    @Override
    public void HandleInput(String input)
    {
        if (input.toLowerCase().equals("back"))
        {
            this.controller.ChangeState("distances-view");
        }
    }
    
    
}
