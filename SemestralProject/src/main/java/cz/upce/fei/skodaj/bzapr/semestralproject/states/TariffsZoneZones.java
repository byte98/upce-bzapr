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
import cz.upce.fei.skodaj.bzapr.semestralproject.data.ZoneTariff;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.help.Help;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.help.HelpFactory;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.screens.HTMLTemplateScreen;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.screens.Screen;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing creating new zone tariff (with setting zones to stations)
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class TariffsZoneZones extends State {

    /**
     * Tariff which will be edited
     */
    private ZoneTariff tariff;
    
    /**
     * Array with all available stations
     */
    private Station[] stations;
    
    /**
     * Index of actually selected station
     */
    private int stIdx;
    
    /**
     * Creates new dialog for creating new zone tariff (with setting zones to stations)
     * @param controller Controller of program
     */
    public TariffsZoneZones(Controller controller)
    {
        super(controller);
        this.commandPrefix = "/data/tariffs/";
        this.screen = new HTMLTemplateScreen("tariffs-zone-zones", "tariffs-zone-zones.html");
        this.name = "tariffs-zone-zones";
        this.strict = false;
        
        this.helps = new Help[2];
        this.helps[0] = HelpFactory.CreateSimpleHelp("<cele cislo>", Color.YELLOW, "Cislo zony pro stanici");
        this.helps[1] = HelpFactory.CreateSimpleHelp("cancel", Color.MAGENTA, "Zrusit");
    }

    @Override
    public void Load()
    {
        this.stations = cz.upce.fei.skodaj.bzapr.semestralproject.data.Stations.GetInstance().GetAllStations();
        this.stIdx = 0;
    }
    
    @Override
    public Screen GetScreen()
    {
        Map<String, String> data = new HashMap<>();
        data.put("tariff_zones", this.tariff.GenerateZonesTr());
        data.put("station_name", this.stations[this.stIdx].GetName());
        data.put("station_abbr", this.stations[this.stIdx].GetAbbrevation());
        ((HTMLTemplateScreen)this.screen).SetContent(data);
        return this.screen;
    }
    
    @Override
    public Screen GetScreen(Map<String, String> data)
    {
        this.tariff = new ZoneTariff(data.get("tariff_name"), data.get("tariff_abbr"));
        this.commandPrefix = "/data/tariffs/zone/" + data.get("tariff_abbr").toLowerCase();
        data.put("tariff_zones", this.tariff.GenerateZonesTr());
        data.put("station_name", this.stations[this.stIdx].GetName());
        data.put("station_abbr", this.stations[this.stIdx].GetAbbrevation());
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
            int zone = Integer.parseInt(input);
            if (zone > 0)
            {
                this.tariff.SetZone(this.stations[this.stIdx], zone);
                this.controller.ShowSucess("Zona pro stanici '" + this.stations[this.stIdx].GetName() + "' byla nastavene.");
                this.stIdx++;
                if (this.stIdx >= this.stations.length)
                {
                    cz.upce.fei.skodaj.bzapr.semestralproject.data.Tariffs.GetInstance().AddTariff(this.tariff);
                    this.controller.ShowSucess("Zony pro veschny stanice byly uspesne nastaveny!");
                    this.controller.ChangeState("tariffs");
                }
                else
                {
                    this.controller.ReDraw();
                }
            }
            else
            {
                this.controller.ShowError("Cislo zony musi byt kladne cislo!");
            }
        }
        else
        {
            this.controller.ShowError("Neznamy prikaz '" + input + "'!");
        }
    }
    
}
