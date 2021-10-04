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
            case             "welcome": reti = new Welcome(controller);          break;
            case                "exit": reti = new Exit(controller);             break; 
            case                "data": reti = new Data(controller);             break; 
            case            "stations": reti = new Stations(controller);         break;
            case        "stations-add": reti = new StationsAdd(controller);      break;
            case   "stations-add-name": reti = new StationsAddName(controller);  break;
            case   "stations-add-abbr": reti = new StationsAddAbbr(controller);  break;
            case  "stations-edit-name": reti = new StationsEditName(controller); break;
            case  "stations-edit-abbr": reti = new StationsEditAbbr(controller); break;
            case       "stations-edit": reti = new StationsEdit(controller);     break;
            case     "stations-delete": reti = new StationsDelete(controller);   break;
            case           "distances": reti = new Distances(controller);        break;
        }
        return reti;
    }
}
