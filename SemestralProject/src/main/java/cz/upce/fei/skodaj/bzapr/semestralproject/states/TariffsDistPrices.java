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
import cz.upce.fei.skodaj.bzapr.semestralproject.data.ZoneTariff;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.help.Help;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.help.HelpFactory;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.screens.HTMLTemplateScreen;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.screens.Screen;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing creating new distance tariff (with setting prices to distances)
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class TariffsDistPrices extends State {

    /**
     * Tariff which will be edited
     */
    private DistanceTariff tariff;
    
    /**
     * Actually selected distance
     */
    private int actDistance = 0;
    
    /**
     * Maximal selected distance
     */
    private int maxDistance = 0;
    
    /**
     * Minimal selected distance
     */
    private int minDistance = 0;
    
    /**
     * Creates new dialog for creating new distance tariff (with setting prices to distances)
     * @param controller Controller of program
     */
    public TariffsDistPrices(Controller controller)
    {
        super(controller);
        this.commandPrefix = "/data/tariffs/";
        this.screen = new HTMLTemplateScreen("tariffs-dist-prices", "tariffs-dist-prices.html");
        this.name = "tariffs-dist-prices";
        this.strict = false;
        
        this.helps = new Help[2];
        this.helps[0] = HelpFactory.CreateSimpleHelp("<cele cislo>", Color.YELLOW, "Cena za projetou vzdalenost");
        this.helps[1] = HelpFactory.CreateSimpleHelp("cancel", Color.MAGENTA, "Zrusit");
    }
    
    @Override
    public Screen GetScreen()
    {
        Map<String, String> data = new HashMap<>();
        data.put("distance_act", Integer.toString(this.actDistance));
        data.put("tariff_name", this.tariff.GetName());
        data.put("distance_prices", this.tariff.GeneratePriceListRows(this.minDistance, this.maxDistance));
        ((HTMLTemplateScreen)this.screen).SetContent(data);
        return this.screen;
    }
    
    @Override
    public Screen GetScreen(Map<String, String> data)
    {
        if (this.tariff == null)
        {
            this.tariff = (DistanceTariff) cz.upce.fei.skodaj.bzapr.semestralproject.data.Tariffs.GetInstance().GetTariff(data.get("tariff_abbr"));
            this.commandPrefix = "/data/tariffs/zone/" + data.get("tariff_abbr").toLowerCase();
            int min = -1, max = -1;
            for (Station from: cz.upce.fei.skodaj.bzapr.semestralproject.data.Stations.GetInstance().GetAllStations())
            {
                for (Station to: cz.upce.fei.skodaj.bzapr.semestralproject.data.Stations.GetInstance().GetAllStations())
                {
                    int distance = cz.upce.fei.skodaj.bzapr.semestralproject.data.Distances.GetInstance().GetDistance(from, to);
                    if (min == -1)
                    {
                        min = distance;
                    }
                    if (max == -1)
                    {
                        max = distance;
                    }
                    if (distance < min)
                    {
                        min = distance;
                    }
                    if (distance > max)
                    {
                        max = distance;
                    }
                }
            }
            this.minDistance = min;
            this.maxDistance = max;
            this.actDistance = this.minDistance;
        }
        
        data.put("distance_act", Integer.toString(this.actDistance));
        data.put("tariff_name", this.tariff.GetName());
        data.put("distance_prices", this.tariff.GeneratePriceListRows(this.minDistance, this.maxDistance));
        ((HTMLTemplateScreen)this.screen).SetContent(data);
        return this.screen;
    }
    
    /**
     * Checks, whether input contains only integer
     * @param input Input which will be checked
     * @return <code>TRUE</code> if input contains integer only, <code>FALSE</code> otherwise
     * @author Jonas K https://stackoverflow.com/questions/237159/whats-the-best-way-to-check-if-a-string-represents-an-integer-in-java
     */
    private boolean CheckInt(String input)
    {
        if (input == null)
        {
            return false;
        }
        int length = input.length();
        if (length == 0)
        {
            return false;
        }
        int i = 0;
        if (input.charAt(0) == '-')
        {
            if (length == 1)
            {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++)
        {
            char c = input.charAt(i);
            if (c < '0' || c > '9')
            {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public void HandleInput(String input)
    {
        if (input.toLowerCase().equals("cancel"))
        {
            this.controller.ChangeState("tariffs");
        }
        else if (this.CheckInt(input))
        {
            int price = Integer.parseInt(input);
            if (price >= 0)
            {
                this.tariff.SetPrice(this.actDistance, Integer.parseInt(input));
                this.controller.ShowSucess("Cena pro vzdalenost " + this.actDistance + " km nastavena.");
                this.actDistance++;
                this.controller.ReDraw();
                if (this.actDistance > this.maxDistance)
                {
                    this.controller.ShowSucess("Cenik tarifu '" + this.tariff.GetName() + "' byl uspesne vytvoren.");
                    this.controller.ChangeState("tariffs");
                }
                
            }
            else
            {
                this.controller.ShowError("Cislo zony musi byt nezaporne cislo!");
            }
        }
        else
        {
            this.controller.ShowError("Neznamy prikaz '" + input + "'!");
        }
    }
}
