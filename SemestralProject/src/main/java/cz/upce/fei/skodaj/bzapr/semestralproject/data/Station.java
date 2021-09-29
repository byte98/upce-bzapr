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
package cz.upce.fei.skodaj.bzapr.semestralproject.data;

/**
 * Class representing station
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Station {
    
    /**
     * Name of the station
     */
    private String name;
    
    /**
     * Abbrevation for the station
     */
    private String abbrevation;
    
    /**
     * Identifier of the station
     */
    private int identifier;
    
    
    /**
     * Creates new station
     * @param abbrevation Abbrevation for the station
     * @param name Name of the station
     */
    public Station(String abbrevation, String name)
    {
        this.abbrevation = abbrevation;
        this.name = name;
    }
    
    /**
     * Gets name of the station
     * @return Name of the station
     */
    public String GetName()
    {
        return this.name;
    }
    
    /**
     * Gets abbrevation for the station
     * @return Abbrevation for the station
     */
    public String GetAbbrevation()
    {
        return this.abbrevation;
    }
    
    /**
     * Sets identifier of the station
     * @param id New identifier of the station
     */
    public void SetIdentifier(int id)
    {
        this.identifier = id;
    }
    
    /**
     * Gets identifier of the station
     * @return Identifier of the station
     */
    public int GetIdentifier()
    {
        return this.identifier;
    }
}
