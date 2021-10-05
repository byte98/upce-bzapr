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
 * Class representing state of program which displays distances menu
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Distances extends State
{

    /**
     * Creates new state of program with distances menu
     * @param controller 
     */
    public Distances(Controller controller)
    {
        super(controller);
        this.commandPrefix = "/data/distances";
        this.screen = new HTMLTemplateScreen("distances", "distances.html");
        this.name = "distances";
        this.strict = true;
        
        this.helps = new Help[4];
        this.helps[0] = HelpFactory.CreateSimpleHelp("create", Color.YELLOW, "Rezim vytvareni tabulky vzdalenosti");
        this.helps[1] = HelpFactory.CreateSimpleHelp("set", Color.YELLOW, "Rezim upravy tabulky vzdalenosti");
        this.helps[2] = HelpFactory.CreateSimpleHelp("view", Color.YELLOW, "Rezim prohlizeni tabulky vzdalenosti");
        this.helps[3] = HelpFactory.CreateSimpleHelp("back", Color.MAGENTA, "Zpet");
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
            case "create": this.controller.ChangeState("distances-create"); break;
            
        }
    }
    
}
