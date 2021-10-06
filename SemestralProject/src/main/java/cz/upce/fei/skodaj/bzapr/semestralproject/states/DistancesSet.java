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
 * Class representing state of program which displays dialog for setting distance between station (with confirming dialog)
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class DistancesSet extends State
{

    /**
     * Origin station for setting distance
     */
    private Station origin;
    
    /**
     * Destination for setting distance
     */
    private Station destination;
    
    /**
     * Distance between stations
     */
    private int distance;
    
    /**
     * Creates new state of program which displays dialog for setting distance between station (with confirming dialog)
     * @param controller Controller of program
     */
    public DistancesSet(Controller controller)
    {
        super(controller);
        this.commandPrefix = "/data/distances/set?";
        this.screen = new HTMLTemplateScreen("distances-set", "distances-set.html");
        this.name = "distances-set";
        this.strict = true;
        
        this.helps = new Help[2];
        this.helps[0] = HelpFactory.CreateSimpleHelp("yes", Color.GREEN, "Udaje jsou v poradku, zmenit vzdalenost mezi stanicemi");
        this.helps[1] = HelpFactory.CreateSimpleHelp("no", Color.RED, "Zrusit");
    }
    
    @Override
    public Screen GetScreen(Map<String, String> data)
    {
        this.origin = cz.upce.fei.skodaj.bzapr.semestralproject.data.Stations.GetInstance().GetStation(data.get("station_from"));
        this.destination = cz.upce.fei.skodaj.bzapr.semestralproject.data.Stations.GetInstance().GetStation(data.get("station_to"));
        data.put("stations_distances_tr", cz.upce.fei.skodaj.bzapr.semestralproject.data.Distances.GetInstance().GenerateDistancesRows(
                this.origin
        ));
        data.put("station_from", this.origin.GetName() + " (" + this.origin.GetAbbrevation() + ")");
        data.put("station_to", this.destination.GetName() + " (" + this.destination.GetAbbrevation() + ")");
        this.distance = Integer.parseInt(data.get("distance"));
        ((HTMLTemplateScreen)this.screen).SetContent(data);
        return this.screen;
    }

    @Override
    public void HandleInput(String input)
    {
        switch(input.toLowerCase())
        {
            case "yes":
                cz.upce.fei.skodaj.bzapr.semestralproject.data.Distances.GetInstance().SetDistance(this.origin, this.destination, this.distance);
                this.controller.ShowSucess("Vzdalenost mezi stanicemi byla uspesne zmenena.");
                this.controller.ChangeState("distances");
                break;
            case "no":
                this.controller.ChangeState("distances");
                break;
        }
    }
    
}
