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
import cz.upce.fei.skodaj.bzapr.semestralproject.data.ZoneTariff;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.help.Help;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.help.HelpFactory;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.screens.HTMLTemplateScreen;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.screens.Screen;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing creating new zone tariff (with setting prices to zones)
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class TariffsZonePrices extends State {

    /**
     * Tariff which will be edited
     */
    private ZoneTariff tariff;
    
    /**
     * Actually selected zone
     */
    private int actZone = 0;
    
    /**
     * Maximal selected zone
     */
    private int maxZone = 0;
    
    /**
     * Creates new dialog for creating new zone tariff (with setting prices to zones)
     * @param controller Controller of program
     */
    public TariffsZonePrices(Controller controller)
    {
        super(controller);
        this.commandPrefix = "/data/tariffs/";
        this.screen = new HTMLTemplateScreen("tariffs-zone-prices", "tariffs-zone-prices.html");
        this.name = "tariffs-zone-prices";
        this.strict = false;
        
        this.helps = new Help[2];
        this.helps[0] = HelpFactory.CreateSimpleHelp("<cele cislo>", Color.YELLOW, "Cena za projete zony");
        this.helps[1] = HelpFactory.CreateSimpleHelp("cancel", Color.MAGENTA, "Zrusit");
    }
    
    @Override
    public Screen GetScreen()
    {
        Map<String, String> data = new HashMap<>();
        data.put("zones_count", Integer.toString(this.actZone));
        data.put("tariff_name", this.tariff.GetName());
        data.put("zones_prices", this.GetZonePrices());
        ((HTMLTemplateScreen)this.screen).SetContent(data);
        return this.screen;
    }
    
    @Override
    public Screen GetScreen(Map<String, String> data)
    {
        if (this.tariff == null)
        {
            this.tariff = (ZoneTariff) cz.upce.fei.skodaj.bzapr.semestralproject.data.Tariffs.GetInstance().GetTariff(data.get("tariff_abbr"));
            this.commandPrefix = "/data/tariffs/zone/" + data.get("tariff_abbr").toLowerCase();
            int min = -1, max = -1;
            for (int zone: this.tariff.GetAllZones().values())
            {
                if (min == -1)
                {
                    min = zone;
                }
                if (max == -1)
                {
                    max = zone;
                }
                if (zone < min)
                {
                    min = zone;
                }
                if (zone > max)
                {
                    max = zone;
                }
            }
        this.maxZone = max - min;
        }
        
        data.put("zones_count", Integer.toString(this.actZone));
        data.put("tariff_name", this.tariff.GetName());
        data.put("zones_prices", this.GetZonePrices());
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
                this.tariff.SetPrice(this.actZone, price);
                this.controller.ShowSucess("Cena pro " + this.actZone + " zony byla nastavena.");
                this.actZone++;
                if (this.actZone > this.maxZone)
                {
                    this.controller.ShowSucess("Ceny pro vsechny zony byly uspesne nastaveny!");
                    this.controller.ChangeState("tariffs");
                }
                else
                {
                    this.controller.ReDraw();
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
    
    /**
     * Gets HTML table rows with zone prices
     * @return String with HTML table rows with zone prices
     */
    private String GetZonePrices()
    {
        String reti = new String();
        
        for (int i = 0; i <= this.maxZone; i++)
        {
            reti += "<tr><td>" + i + "</td><td style='color: white;'>";
            reti += (this.tariff.GetAllPrices().get(i) == null ? " " : this.tariff.GetAllPrices().get(i) + " Kc");
            reti += "</td></tr>";
        }
        return reti;
    }
}
