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

import java.util.HashMap;
import java.util.Map;

/**
 * Class managing distances between stations
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Distances
{
    /**
     * Instance of manager of distances between stations
     */
    private Distances instance = null;
    
    /**
     * List of distances from stations
     */
    private Map<Station, Distance> dist;
    
    /**
     * File with saved distances between stations
     */
    private final String filePath = "resources/distances.jtd";
    
    /**
     * Creates new manager of distances between stations
     */
    private Distances()
    {
        this.dist = new HashMap<>();
    }
    
    /**
     * Gets instance of manager of distances between stations
     */
    public Distances GetInstance()
    {
        if (this.instance == null)
        {
            this.instance = new Distances();
        }
        return this.instance;
    }
    
    /**
     * Saves distances to file
     */
    private void SaveDistances()
    {
        // First, get stations identifiers to array
        Station[] stations = Stations.GetInstance().GetAllStations();
        int[] stIds = new int[stations.length];
        for (int i = 0; i < stations.length; i++)
        {
            stIds[i] = stations[i].GetIdentifier();
        }
        
        // Then, get arrays of distances from each station
        int[][] data = new int[stations.length][stations.length];
        for (Station s: stations)
        {
            
        }
    }
    
    
    
    
}
