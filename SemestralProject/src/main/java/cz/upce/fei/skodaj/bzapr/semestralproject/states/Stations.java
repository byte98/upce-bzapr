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
 * Class representing state of program which displays stations menu
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Stations extends State
{

    /**
     * Creates new state of program with stations menu
     * @param controller 
     */
    public Stations(Controller controller)
    {
        super(controller);
        this.commandPrefix = "/data/stations";
        this.screen = new HTMLTemplateScreen("stations", "stations.html");
        this.name = "stations";
        this.strict = false;
        
        this.helps = new Help[3];
        this.helps[0] = HelpFactory.CreateSimpleHelp("<nazev nebo zkratka stanice>", Color.YELLOW, "Vybrat stanici");
        this.helps[1] = HelpFactory.CreateSimpleHelp("add", Color.YELLOW, "Pridat novou stanici");
        this.helps[2] = HelpFactory.CreateSimpleHelp("back", Color.MAGENTA, "Zpet");
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
    public void HandleInput(String input)
    {
        switch (input.toLowerCase())
        {
            case "back": this.controller.ChangeState("data"); break;
            case "add": this.controller.ChangeState("stations-add-name"); break;
            default:
                Station st = cz.upce.fei.skodaj.bzapr.semestralproject.data.Stations.GetInstance().GetStation(input);
                if (st == null)
                {
                    this.controller.ShowError("Stanice '" + input + "' nenalezena!");
                }
                else
                {
                    Map<String, String> data = new HashMap<>();
                    data.put("station_name", st.GetName());
                    data.put("station_abbr", st.GetAbbrevation());
                    this.controller.ChangeState("stations-edit-name", data);
                }
                break;
        }
    }
    
}
