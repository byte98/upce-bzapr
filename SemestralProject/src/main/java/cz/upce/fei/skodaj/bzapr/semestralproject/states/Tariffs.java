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
import cz.upce.fei.skodaj.bzapr.semestralproject.data.Distances;
import cz.upce.fei.skodaj.bzapr.semestralproject.data.Station;
import cz.upce.fei.skodaj.bzapr.semestralproject.data.Stations;
import cz.upce.fei.skodaj.bzapr.semestralproject.data.Tariff;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.help.Help;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.help.HelpFactory;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.screens.HTMLTemplateScreen;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.screens.Screen;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Class representing tariffs menu
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Tariffs extends State {

    /**
     * Creates new tariffs menu
     * @param controller Controller of program
     */
    public Tariffs(Controller controller)
    {
        super(controller);
        this.commandPrefix = "/data/tariffs";
        this.screen = new HTMLTemplateScreen("tariffs", "tariffs.html");
        this.name = "tariffs";
        
        this.helps = new Help[4];
        this.helps[0] = HelpFactory.CreateSimpleHelp("stations", Color.YELLOW, "Rezim upravy stanice");
        this.helps[1] = HelpFactory.CreateSimpleHelp("distances", Color.YELLOW, "Rezim upravy vzdalenosti mezi stanicemi");
        this.helps[2] = HelpFactory.CreateSimpleHelp("tariffs", Color.YELLOW, "Rezim upravy tarifu");
        this.helps[3] = HelpFactory.CreateSimpleHelp("back", Color.MAGENTA, "Zpet");
    }

    @Override
    public Screen GetScreen()
    {
        Map<String, String> data = new HashMap<>();
        data.put("tariffs_tr", cz.upce.fei.skodaj.bzapr.semestralproject.data.Tariffs.GetInstance().GenerateTariffsTableRows());
        ((HTMLTemplateScreen)this.screen).SetContent(data);
        return this.screen;
    }
    
    @Override
    public void HandleInput(String input)
    {
        
    }

    
    
}
