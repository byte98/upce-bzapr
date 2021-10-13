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
import cz.upce.fei.skodaj.bzapr.semestralproject.data.DistanceTariff;
import cz.upce.fei.skodaj.bzapr.semestralproject.data.Station;
import cz.upce.fei.skodaj.bzapr.semestralproject.data.Tariff;
import cz.upce.fei.skodaj.bzapr.semestralproject.data.ZoneTariff;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.help.Help;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.help.HelpFactory;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.screens.HTMLTemplateScreen;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.screens.Screen;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing tariff viewer for distance tariffs (with delete dialog)
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class TariffsDistDelete extends State {

    /**
     * Tariff which is displayed
     */
    private DistanceTariff tariff;
    
    /**
     * Creates new tariff viewer for distance tariffs (with delete dialog)
     * @param controller Controller of program
     */
    public TariffsDistDelete(Controller controller)
    {
        super(controller);
        this.commandPrefix = "/data/tariffs/";
        this.screen = new HTMLTemplateScreen("tariffs-dist-delete", "tariffs-dist-delete.html");
        this.name = "tariffs-dist-delete";
        this.strict = true;
        
        this.helps = new Help[2];
        this.helps[0] = HelpFactory.CreateSimpleHelp("no", Color.GREEN, "Zrusit");
        this.helps[1] = HelpFactory.CreateSimpleHelp("yes", Color.RED, "Smazat tarif");
    }
    
    @Override
    public Screen GetScreen(Map<String, String> data)
    {
        this.tariff =(DistanceTariff) cz.upce.fei.skodaj.bzapr.semestralproject.data.Tariffs.GetInstance().GetTariff(data.get("tariff_abbr"));
        if (tariff != null)
        {
            int min = -1, max = 1;
            for (Station from: cz.upce.fei.skodaj.bzapr.semestralproject.data.Stations.GetInstance().GetAllStations())
            {
                for (Station to: cz.upce.fei.skodaj.bzapr.semestralproject.data.Stations.GetInstance().GetAllStations())
                {
                    int dist = cz.upce.fei.skodaj.bzapr.semestralproject.data.Distances.GetInstance().GetDistance(from, to);
                    if (min == -1)
                    {
                        min = dist;
                    }
                    if (max == -1)
                    {
                        max = dist;
                    }
                    if (dist > max)
                    {
                        max = dist;
                    }
                    if (dist < min)
                    {
                        min = dist;
                    }
                }
            }
            data.put("tariff_name", this.tariff.GetName());
            data.put("tariff_prices", this.tariff.GeneratePriceListRows(min, max));
            this.commandPrefix = "/data/tariffs/" + this.tariff.GetAbbr().toLowerCase();
        }
        ((HTMLTemplateScreen)this.screen).SetContent(data);
        return this.screen;
    }
    
    @Override
    public void HandleInput(String input)
    {
        switch(input.toLowerCase())
        {
            case "no": 
                Map<String, String> data = new HashMap<>();
                data.put("tariff_abbr", this.tariff.GetAbbr());
                this.controller.ChangeState("tariffs-dist-view"); 
                break;
            case "yes":
                String tarifName = this.tariff.GetName();
                cz.upce.fei.skodaj.bzapr.semestralproject.data.Tariffs.GetInstance().RemoveTariff(this.tariff);
                this.controller.ShowSucess("Tarif '" + tarifName + "' byl uspesne odebran.");
                this.controller.ChangeState("tariffs");
                break;
        }
    }    
}
