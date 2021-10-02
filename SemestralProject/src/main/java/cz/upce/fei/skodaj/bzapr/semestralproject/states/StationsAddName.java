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
 * Class representing add station form (with selected name option)
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class StationsAddName extends State
{

    /**
     * Creates new add station form (with selected name option)
     * @param controller Controller of program
     */
    public StationsAddName(Controller controller)
    {
        super(controller);
        this.commandPrefix = "/data/stations/add:name";
        this.screen = new HTMLTemplateScreen("stations-add-name", "stations-add-name.html");
        this.name = "stations-add-name";
        this.strict = false;
        
        this.helps = new Help[2];
        this.helps[0] = HelpFactory.CreateSimpleHelp("<nazev stanice>", Color.YELLOW, "Nazev stanice");
        this.helps[1] = HelpFactory.CreateSimpleHelp("cancel", Color.MAGENTA, "Zrusit");
        
    }

    @Override
    public void HandleInput(String input)
    {
        if ("cancel".equals(input.toLowerCase()))
        {
            this.controller.ChangeState("stations");   
        }
        else
        {
            if (cz.upce.fei.skodaj.bzapr.semestralproject.data.Stations.GetInstance().CheckFreeName(input))
            {
                Map<String, String> data = new HashMap<>();
                data.put("station_name", input);
                this.controller.ChangeState("stations-add-abbr", data);
            }
            else
            {
                this.controller.ShowError("Stanice '" + input + "' jiz existuje!");
            }
            
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
}
