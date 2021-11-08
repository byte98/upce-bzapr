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
 * Class representing add station form (with displayed confirmation)
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class StationsAdd extends State
{

    /**
     * Name of station
     */
    private String stationName;
    
    /**
     * Abbreavation of station
     */
    private String stationAbbr;
    
    /**
     * Creates new add station form (with displayed confirmation)
     * @param controller Controller of program
     */
    public StationsAdd(Controller controller)
    {
        super(controller);
        this.commandPrefix = "/data/stations/add?";
        this.screen = new HTMLTemplateScreen("stations-add", "stations-add.html");
        this.name = "stations-add";
        
        this.helps = new Help[2];
        this.helps[0] = HelpFactory.CreateSimpleHelp("yes", Color.GREEN, "Udaje jsou v poradku, pridat stanici");
        this.helps[1] = HelpFactory.CreateSimpleHelp("no", Color.RED, "Zrusit");
        
    }

    @Override
    public void HandleInput(String input)
    {
        if ("yes".equals(input))
        {
            Station st = new Station(this.stationAbbr, this.stationName);
            cz.upce.fei.skodaj.bzapr.semestralproject.data.Stations.GetInstance().AddStation(st);
            System.out.printf("New station (name: %s, abbreavation: %s) has been added\n", this.stationName, this.stationAbbr);
            this.controller.ShowSucess("Stanice '" + this.stationName + " (" + this.stationAbbr + ")' byla uspesne pridana!");
            this.controller.ChangeState("stations");
        }
        else
        {
            this.controller.ChangeState("stations");
        }
    }
    
    @Override
    public Screen GetScreen()
    {
        Map<String, String> data = new HashMap<>();
        data.put("stations_tr", cz.upce.fei.skodaj.bzapr.semestralproject.data.Stations.GetInstance().GenerateTableRows());
        ((HTMLTemplateScreen)this.screen).SetContent(data);
        return this.screen;
    }
    
    @Override
    public Screen GetScreen(Map<String, String> data)
    {
        data.put("stations_tr", cz.upce.fei.skodaj.bzapr.semestralproject.data.Stations.GetInstance().GenerateTableRows());
        ((HTMLTemplateScreen)this.screen).SetContent(data);
        this.stationAbbr = data.get("station_abbr");
        this.stationName = data.get("station_name");
        return this.screen;
    }
}
