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
import cz.upce.fei.skodaj.bzapr.semestralproject.data.Tariff;
import cz.upce.fei.skodaj.bzapr.semestralproject.data.TariffType;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.help.Help;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.help.HelpFactory;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.screens.HTMLTemplateScreen;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.screens.Screen;
import java.awt.Color;
import java.util.HashMap;
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
        this.strict = false;
        
        this.helps = new Help[4];
        this.helps[0] = HelpFactory.CreateSimpleHelp("<jmeno nebo zkratka tarifu>", Color.YELLOW, "Rezim prohlizeni tarifu");
        this.helps[1] = HelpFactory.CreateSimpleHelp("zone", Color.YELLOW, "Vytvorit novy zonovy tarif");
        this.helps[2] = HelpFactory.CreateSimpleHelp("distance", Color.YELLOW, "Vytvorit novy vzdalenostni tarif");
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
        switch (input.toLowerCase())
        {
            case "back":
                this.controller.ChangeState("data");
                break;
            case "zone":
                this.controller.ChangeState("tariffs-zone-name");
                break;
            case "distance":
                this.controller.ChangeState("tariffs-dist-name");
                break;
            default:
                Tariff t = cz.upce.fei.skodaj.bzapr.semestralproject.data.Tariffs.GetInstance().GetTariff(input);
                if (input.equals(""))
                {
                    //pass
                }
                else if (t == null)
                {
                    this.controller.ShowError("Neznamy prikaz '" + input + "'!");
                }
                else
                {
                    Map<String, String> data = new HashMap<>();
                    data.put("tariff_abbr", t.GetAbbr());
                    if (t.GetType() == TariffType.ZONE)
                    {
                        this.controller.ChangeState("tariffs-zone-view", data);
                    }   
                    else if (t.GetType() == TariffType.DISTANCE)
                    {
                        this.controller.ChangeState("tariffs-dist-view", data);
                    }
                }
                break;
        }
    }

    
    
}
