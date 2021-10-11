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
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.help.Help;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.help.HelpFactory;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.screens.HTMLTemplateScreen;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.screens.Screen;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing creating new zone tariff (with abbreavation selected)
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class TariffsZoneAbbr extends State {

    /**
     * Name of tariff
     */
    private String tariffName;
    
    /**
     * Creates new dialog for creating new zone tariff (with abbreavation selected)
     * @param controller Controller of program
     */
    public TariffsZoneAbbr(Controller controller)
    {
        super(controller);
        this.commandPrefix = "/data/tariffs/zone:abbr";
        this.screen = new HTMLTemplateScreen("tariffs-zone-abbr", "tariffs-zone-abbr.html");
        this.name = "tariffs-zone-abbr";
        this.strict = false;
        
        this.helps = new Help[2];
        this.helps[0] = HelpFactory.CreateSimpleHelp("<zkratka tarifu>", Color.YELLOW, "Zkratka tarifu");
        this.helps[1] = HelpFactory.CreateSimpleHelp("cancel", Color.MAGENTA, "Zrusit");
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
    public Screen GetScreen(Map<String, String> data)
    {
        data.put("tariffs_tr", cz.upce.fei.skodaj.bzapr.semestralproject.data.Tariffs.GetInstance().GenerateTariffsTableRows());
        this.tariffName = data.get("tariff_name");
        ((HTMLTemplateScreen)this.screen).SetContent(data);
        return this.screen;
    }
    
    @Override
    public void HandleInput(String input)
    {
        if (input.toLowerCase().equals("cancel"))
        {
            this.controller.ChangeState("tariffs");
        }
        else
        {
            Tariff t = cz.upce.fei.skodaj.bzapr.semestralproject.data.Tariffs.GetInstance().GetTariffByAbbr(input);
            if (t != null)
            {
                this.controller.ShowError("Tarif '" + input + "' jiz existuje!");
            }
            else
            {
                Map<String, String> data = new HashMap<>();
                data.put("tariff_abbr", input);
                data.put("tariff_name", this.tariffName);
                this.controller.ChangeState("tariffs-zone", data);
            }
        }
    }

    
    
}
