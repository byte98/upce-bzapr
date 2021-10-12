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
 * Class representing tariff in system
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public abstract class Tariff {
    
    /**
     * Type of tariff
     */
    private TariffType type;
    
    /**
     * Name of tariff
     */
    private String name;
    
    /**
     * Abbreavation of tariff
     */
    private String abbr;
    
    /**
     * Creates new tariff
     * @param type Type of tariff
     * @param name Name of tariff
     * @param abbreavation Abbreavation of tariff
     */
    public Tariff(TariffType type, String name, String abbreavation)
    {
        this.type = type;
        this.name = name;
        this.abbr = abbreavation;
    }
    
    /**
     * Gets type of tariff
     * @return Type of tariff
     */
    public TariffType GetType()
    {
        return this.type;
    }
    
    /**
     * Gets name of tariff
     * @return Name of tariff
     */
    public String GetName()
    {
        return this.name;
    }
    
    /**
     * Gets abbreavation of tariff
     * @return Abbreavation of tariff
     */
    public String GetAbbr()
    {
        return this.abbr;
    }
    
    /**
     * Gets price between stations
     * @param origin Origin station
     * @param destination Destination station
     * @return Price between selected stations
     */
    public abstract int GetPrice(Station origin, Station destination);
    
    /**
     * Deletes tariff
     */
    public abstract void Delete();
}
