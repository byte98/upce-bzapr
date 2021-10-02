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
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.help.Help;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.help.HelpFactory;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.screens.HTMLTemplateScreen;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.screens.Screen;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing edit station form
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class StationsEdit extends State
{
    
    /**
     * Abbreavation of station
     */
    private String stationAbbr = null;
    
    /**
     * Name of station
     */
    private String stationName = null;
    
    /**
     * New abbreavation of station
     */
    private String newStationAbbr = null;
    
    /**
     * New name of station
     */
    private String newStationName = null;

    /**
     * Creates new edit station form
     * @param controller Controller of program
     */
    public StationsEdit(Controller controller)
    {
        super(controller);
        this.commandPrefix = "/data/stations/edit?";
        this.screen = new HTMLTemplateScreen("stations-edit", "stations-edit.html");
        this.name = "stations-edit";
        this.strict = true;
        
        this.helps = new Help[2];
        this.helps[0] = HelpFactory.CreateSimpleHelp("yes", Color.GREEN, "Udaje jsou v poradku, zmenit stanici");
        this.helps[1] = HelpFactory.CreateSimpleHelp("no", Color.RED, "Zrusit");
        
    }

    @Override
    public void HandleInput(String input)
    {
        switch (input.toLowerCase())
        {
            case "no":
                this.controller.ChangeState("stations");
                break;
            case "yes":
                cz.upce.fei.skodaj.bzapr.semestralproject.data.Stations.GetInstance().EditStation(cz.upce.fei.skodaj.bzapr.semestralproject.data.Stations.GetInstance().GetStation(this.stationAbbr), this.newStationName, this.newStationAbbr);
                this.controller.ShowSucess("Stanice '" + this.stationName + " (" + this.stationAbbr + ")' byla uspesne zmenena.");
                this.controller.ChangeState("stations");
                break;
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
        this.stationAbbr = data.get("station_abbr");
        this.stationName = data.get("station_name");
        this.newStationAbbr = data.get("station_new_abbr");
        this.newStationName = data.get("station_new_name");
        ((HTMLTemplateScreen)this.screen).SetContent(data);
        return this.screen;
    }
}
