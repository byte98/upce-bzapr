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

/**
 * Class which can create program states
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class StateFactory
{
    /**
     * Creates new state
     * @param controller Controller of program
     * @param name Name of state
     * @return New state of program
     */
    public static State CreateState(Controller controller, String name)
    {
        State reti = null;
        switch (name.toLowerCase())
        {
            case                "welcome": reti = new Welcome(controller);              break;
            case                   "exit": reti = new Exit(controller);                 break; 
            case                   "data": reti = new Data(controller);                 break; 
            case               "stations": reti = new Stations(controller);             break;
            case           "stations-add": reti = new StationsAdd(controller);          break;
            case      "stations-add-name": reti = new StationsAddName(controller);      break;
            case      "stations-add-abbr": reti = new StationsAddAbbr(controller);      break;
            case     "stations-edit-name": reti = new StationsEditName(controller);     break;
            case     "stations-edit-abbr": reti = new StationsEditAbbr(controller);     break;
            case          "stations-edit": reti = new StationsEdit(controller);         break;
            case        "stations-delete": reti = new StationsDelete(controller);       break;
            case              "distances": reti = new Distances(controller);            break;
            case       "distances-create": reti = new DistancesCreate(controller);      break;
            case         "distances-view": reti = new DistancesView(controller);        break;
            case "distances-view-station": reti = new DistancesViewStation(controller); break;
            case     "distances-set-from": reti = new DistancesSetFrom(controller);     break;
            case       "distances-set-to": reti = new DistancesSetTo(controller);       break;
            case "distances-set-distance": reti = new DistancesSetDistance(controller); break;
            case          "distances-set": reti = new DistancesSet(controller);         break;
            case                "tariffs": reti = new Tariffs(controller);              break;
            case      "tariffs-zone-name": reti = new TariffsZoneName(controller);      break;
            case      "tariffs-zone-abbr": reti = new TariffsZoneAbbr(controller);      break;
            case           "tariffs-zone": reti = new TariffsZone(controller);          break;
            case     "tariffs-zone-zones": reti = new TariffsZoneZones(controller);     break;
            case    "tariffs-zone-prices": reti = new TariffsZonePrices(controller);    break;
            case      "tariffs-zone-view": reti = new TariffsZoneView(controller);      break;
            case    "tariffs-zone-delete": reti = new TariffsZoneDelete(controller);    break;
        }
        return reti;
    }
}
