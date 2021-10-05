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
 * Class representing distance between stations
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Distance
{
    /**
     * Station from which distance will be meassurred
     */
    private Station station;
    
    /**
     * List of distances to other stations
     */
    private Map<Station, Integer> distances;
    
    /**
     * Creates new distance between stations
     * @param st Origin station
     */
    public Distance(Station st)
    {
        this.station = st;
        this.distances = new HashMap<>();
    }
    
    /**
     * Sets distance from origin station to selected one
     * @param to Selected station to which distance is set
     * @param distance Distance between stations (in kilometers)
     */
    public void SetDistance(Station to, int distance)
    {
        this.distances.put(to, distance);
    }
    
    /**
     * Gets distance from origin station to selected one
     * @param to Selected station to which distance will be returned
     * @return Distance between origin station and selected one
     */
    public int GetDistance(Station to)
    {
        int reti = 0;
        if (this.distances.get(to) != null)
        {
            reti = this.distances.get(to);
        }
        return reti;
    }
    
    /**
     * Gets all distances to other stations
     * @return All distances to other stations
     */
    public Map<Station, Integer> GetAllDistances()
    {
        return this.distances;
    }
}
