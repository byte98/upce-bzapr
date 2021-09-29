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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class containing all stations
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Stations {
    
    /**
     * Instance of the class
     */
    private Stations instance = null;
    
    /**
     * List of all available stations
     */
    private List<Station> stations;
    
    /**
     * File containing all stations
     */
    private File stationsFile;
    
    /**
     * Creates new instance of stations
     */
    private Stations()
    {
        this.stations = new ArrayList<>();
        this.stationsFile = new File("resources/stations.csv");
        this.LoadStations();
    }
    
    /**
     * Loads stations from file
     */
    private void LoadStations()
    {
        if (this.stationsFile.exists())
        {
            Scanner sc;
            try
            {
                boolean first = true;
                sc = new Scanner(this.stationsFile);
                while (sc.hasNext())
                {
                    if (first == false)
                    {
                        String line = sc.nextLine();
                        String[] bits = line.split(",");
                        Station s = new Station(bits[1], bits[2]);
                        s.SetIdentifier(Integer.parseInt(bits[0]));
                        this.stations.add(s);
                    }
                    else
                    {
                        first = false;
                    }
                }
                sc.close();
            }
            catch (FileNotFoundException ex)
            {
                Logger.getLogger(Stations.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
    }
    
    /**
     * Saves stations to files
     */
    private void SaveStations()
    {
        try {
            FileWriter fw = new FileWriter(this.stationsFile);
            fw.write("id,abbr,name\n");
            Iterator<Station> it = this.stations.iterator();
            while (it.hasNext())
            {
                Station s = it.next();
                if (s != null)
                {
                    String output = s.GetIdentifier() + "," + s.GetAbbrevation() + "," + s.GetName() + "\n";
                    fw.append(output);
                }
            }
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Stations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * Checks, whether station identifier is free to use
     * @param id Identifier which will be checked
     * @return <code>TRUE</code> if identifier is free to use, <code>FALSE</code> otherwise
     */
    private boolean CheckFreeIdentifier(int id)
    {
        boolean reti = true;
        Iterator<Station> it = this.stations.iterator();
        while (it.hasNext())
        {
            Station s = it.next();
            if (s != null)
            {
                if (s.GetIdentifier() == id)
                {
                    reti = false;
                    break;
                }
            }
        }
        return reti;
    }
    
    /**
     * Gets station by its name in the system
     * @param name Name of the station
     * @return Station selected by its name or <code>null</code> if there is not such an station
     */
    private Station GetStationByName(String name)
    {
        Station reti = null;
        Iterator<Station> it = this.stations.iterator();
        while (it.hasNext())
        {
            Station s = it.next();
            if (s != null && s.GetName().toLowerCase() == name.toLowerCase())
            {
                reti = s;
                break;
            }
        }
        return reti;
    }
    
    /**
     * Gets station by its abbrevation in the system
     * @param abbr Abbrevation of the station
     * @return Station selected by its abbrevation or <code>null</null> if there is not such an station
     */
    private Station GetStationByAbbrevation(String abbr)
    {
        Station reti = null;
        Iterator<Station> it = this.stations.iterator();
        while (it.hasNext())
        {
            Station s = it.next();
            if (s != null && s.GetAbbrevation().toLowerCase() == abbr.toLowerCase())
            {
                reti = s;
                break;
            }
        }
        return reti;
    }
    
    /**
     * Selects station by its name or abbrevation
     * @param nameOrAbbr Name of the station or its abbrevation
     * @return Station selected by its name or abbrevation or <code>null</code> if there is not such an station
     */
    public Station GetStation(String nameOrAbbr)
    {
        Station reti = this.GetStationByName(nameOrAbbr);
        if (reti == null)
        {
            reti = this.GetStationByAbbrevation(nameOrAbbr);
        }
        return reti;
    }
    
    /**
     * Gets instance of class Stations
     * @return Instance of class Stations
     */
    public Stations GetInstance()
    {
        if (this.instance == null)
        {
            this.instance = new Stations();
        }
        return this.instance;
    }
    
    /**
     * Adds station to system
     * @param st Station which will be added to the system
     * @return Error message according to result:
     * <ul>
     *  <li><code>null</code> if station has been successfully added</li>
     *  <li><code>"zkratka je jiz obsazena</code> if abbrevation is already used</li>
     *  <li><code>"stanice jiz existuje</code> if name of station is already in the system</li>
     * </ul>
     */
    public String AddStation(Station st)
    {
        String reti = null;
        Station s = this.GetStationByName(st.GetName());
        if (s == null) // Unused name
        {
            s = this.GetStationByAbbrevation(st.GetAbbrevation());
            if (s == null) // Unused abbrevation
            {
                int newId;
                do
                {
                    newId = ThreadLocalRandom.current().nextInt(0,Integer.MAX_VALUE - 1);
                }
                while (this.CheckFreeIdentifier(newId) == false);
                st.SetIdentifier(newId);
                this.stations.add(st);
                this.SaveStations();
            }
            else
            {
                reti = "zkratka je jiz obsazena";
            }
        }
        else
        {
            reti = "stanice jiz existuje";
        }
        return reti;
    }
}
