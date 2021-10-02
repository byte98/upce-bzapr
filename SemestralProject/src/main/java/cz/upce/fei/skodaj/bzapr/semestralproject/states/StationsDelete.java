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
 * Class representing delete station dialog
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class StationsDelete extends State
{
    /**
     * Abbrevation of station
     */
    private String stationAbbr;
    
    /**
     * Name of the station
     */
    private String stationName;
    
    /**
     * Creates new delete station dialog
     * @param controller Controller of program
     */
    public StationsDelete(Controller controller)
    {
        super(controller);
        this.commandPrefix = "/data/stations/delete?";
        this.screen = new HTMLTemplateScreen("stations-delete", "stations-delete.html");
        this.name = "stations-delete";
        
        this.helps = new Help[2];
        this.helps[0] = HelpFactory.CreateSimpleHelp("no", Color.GREEN, "Zrusit");
        this.helps[1] = HelpFactory.CreateSimpleHelp("yes", Color.RED, "Smazat stanici");
        
    }

    @Override
    public void HandleInput(String input)
    {
        switch (input.toLowerCase())
        {
            
            case "no": 
                Map<String, String> data = new HashMap<>();
                data.put("station_name", this.stationName);
                data.put("station_abbr", this.stationAbbr);
                this.controller.ChangeState("stations-edit-name", data);
                break;
            case "yes":
                cz.upce.fei.skodaj.bzapr.semestralproject.data.Stations.GetInstance().DeleteStation(cz.upce.fei.skodaj.bzapr.semestralproject.data.Stations.GetInstance().GetStation(this.stationAbbr));
                this.controller.ShowSucess("Stanice '" + this.stationName + "' byla uspesne vymazana.");
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
        this.stationAbbr = data.get("station_abbr");
        this.stationName = data.get("station_name");
        data.put("stations_tr", cz.upce.fei.skodaj.bzapr.semestralproject.data.Stations.GetInstance().GenerateTableRows());
        ((HTMLTemplateScreen)this.screen).SetContent(data);
        return this.screen;
    }
}
