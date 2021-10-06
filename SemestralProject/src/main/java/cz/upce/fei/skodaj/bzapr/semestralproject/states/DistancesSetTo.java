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
 * Class representing state of program which displays dialog for setting distance between station (with destination selected)
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class DistancesSetTo extends State
{

    /**
     * Origin station for setting distance
     */
    private Station origin;
    
    /**
     * Creates new state of program which displays dialog for setting distance between station (with destination selected)
     * @param controller Controller of program
     */
    public DistancesSetTo(Controller controller)
    {
        super(controller);
        this.commandPrefix = "/data/distances/set:to";
        this.screen = new HTMLTemplateScreen("distances-set-to", "distances-set-to.html");
        this.name = "distances-set-to";
        this.strict = false;
        
        this.helps = new Help[2];
        this.helps[0] = HelpFactory.CreateSimpleHelp("<nazev nebo zkratka stanice>", Color.YELLOW, "Cilova stanice pro nastaveni vzdalenosti");
        this.helps[1] = HelpFactory.CreateSimpleHelp("cancel", Color.MAGENTA, "Zrusit");
    }
    
    @Override
    public Screen GetScreen(Map<String, String> data)
    {
        this.origin = cz.upce.fei.skodaj.bzapr.semestralproject.data.Stations.GetInstance().GetStation(data.get("station_from"));
        data.put("stations_distances_tr", cz.upce.fei.skodaj.bzapr.semestralproject.data.Distances.GetInstance().GenerateDistancesRows(
                this.origin
        ));
        data.put("station_from", this.origin.GetName() + " (" + this.origin.GetAbbrevation() + ")");
        ((HTMLTemplateScreen)this.screen).SetContent(data);
        return this.screen;
    }

    @Override
    public void HandleInput(String input)
    {
        if (input.toLowerCase().equals("cancel"))
        {
            this.controller.ChangeState("distances");
        }
        else
        {
            Station s = cz.upce.fei.skodaj.bzapr.semestralproject.data.Stations.GetInstance().GetStation(input);
            if (s == null)
            {
                this.controller.ShowError("Neznama stanice '" + input + "'!");
            }
            else
            {
                Map<String, String> data = new HashMap<>();
                data.put("station_from", this.origin.GetAbbrevation());
                data.put("station_to", s.GetAbbrevation());
                this.controller.ChangeState("distances-set-distance", data);
            }
        }
    }
    
}
