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
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
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
    private static Stations instance = null;
    
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
        this.stationsFile = new File("resources/data/stations.csv");
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
                String content = new Scanner(new File(this.stationsFile.getAbsolutePath())).useDelimiter("\\Z").next();
                int ln = 0;
                for (String line: content.split("\n"))
                {
                    if (ln > 0)
                    {
                        String[] bits = line.split(",");
                        if (bits.length > 3)
                        {
                            for (int i = 3; i < bits.length; i++)
                            {
                                bits[2] += "," + bits[i];
                            }
                        }
                        Station s = new Station(bits[1], bits[2], Integer.parseInt(bits[0]));
                        this.stations.add(s);
                    }
                    ln++;
                    
                }
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
            if (s != null && s.GetName().toLowerCase().equals(name.toLowerCase()))
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
            if (s != null && s.GetAbbrevation().toLowerCase().equals(abbr.toLowerCase()))
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
     * Gets station by its identifier
     * @param identifier Identifier of station
     * @return Station selected by its identifier or <code>null</code>
     */
    public Station GetStation (int identifier)
    {
        Station reti = null;
        ListIterator<Station> it = this.stations.listIterator();
        while (it.hasNext())
        {
            Station s = it.next();
            if (s != null)
            {
                if (s.GetIdentifier() == identifier)
                {
                    reti = s;
                    break;
                }
            }
        }
        return reti;
    }
    
    /**
     * Gets instance of class Stations
     * @return Instance of class Stations
     */
    public static Stations GetInstance()
    {
        if (Stations.instance == null)
        {
            Stations.instance = new Stations();
        }
        return Stations.instance;
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
    
    /**
     * Gets all stations available to the system
     * @return Array with all available stations
     */
    public Station[] GetAllStations()
    {
        Station[] reti = new Station[this.stations.size()];
        ListIterator<Station> it = this.stations.listIterator();
        int idx = 0;
        while (it.hasNext())
        {
            Station s = it.next();
            if (s != null)
            {
                reti[idx] = s;
            }
            idx++;
        }
        Arrays.sort(reti, new Comparator<Station>(){
            @Override
            public int compare(Station s1, Station s2)
            {
                return s1.GetName().toLowerCase().compareTo(s2.GetName().toLowerCase());
            }            
        });
        return reti;
    }
    
    /**
     * Checks, whether stations abbreviation is free to use
     * @param abbr Abbreviation which will be checked
     * @return <code>TRUE</code> if stations abbreviation is free to use, <code>FALSE</code> otherwise
     */
    public boolean CheckFreeAbbr(String abbr)
    {
        boolean reti = true;
        Iterator<Station> it = this.stations.iterator();
        while (it.hasNext())
        {
            Station s = it.next();
            if (s != null)
            {
                if (s.GetAbbrevation().toLowerCase().equals(abbr.toLowerCase()))
                {
                    reti = false;
                    break;
                }
            }
        }
        return reti;
    }
    
    /**
     * Checks, whether stations name is free to use
     * @param name Name which will be checked
     * @return <code>TRUE</code> if name is free to use, <code>FALSE</code> otherwise
     */
    public boolean CheckFreeName(String name)
    {
        boolean reti = true;
        Iterator<Station> it = this.stations.iterator();
        while (it.hasNext())
        {
            Station s = it.next();
            if (s != null)
            {
                if (s.GetName().toLowerCase().equals(name.toLowerCase()))
                {
                    reti = false;
                    break;
                }
            }
        }
        return reti;
    }
    
    /**
     * Generates table rows with all stations
     * @return String containing HTML table rows with all stations
     */
    public String GenerateTableRows()
    {
        StringBuilder sb = new StringBuilder();
        for (Station s : this.GetAllStations())
        {
            sb.append("<tr><td style='color: green;'>");
            sb.append(s.GetAbbrevation().toUpperCase());
            sb.append("</td><td>");
            sb.append(s.GetName());
            sb.append("</td></tr>");
        }
        return sb.toString();
    }
    
    /**
     * Deletes station from system
     * @param s Station which will be deleted from system
     */
    public void DeleteStation(Station s)
    {
        if (this.stations.contains(s))
        {
            this.stations.remove(s);
            this.SaveStations();
        }
    }
    
    /**
     * Edits station in system
     * @param s Station which will be edited
     * @param newName New name of the station
     * @param newAbbr New abbrevation of station
     */
    public void EditStation(Station s, String newName, String newAbbr)
    {
        this.stations.remove(s);
        Station newS = new Station(newAbbr, newName);
        newS.SetIdentifier(s.GetIdentifier());
        this.stations.add(newS);
        this.SaveStations();        
    }
}
