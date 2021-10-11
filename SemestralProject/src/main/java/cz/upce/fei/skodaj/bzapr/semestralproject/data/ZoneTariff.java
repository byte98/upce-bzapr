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

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class representing tariff which calculates price based on zones of stations
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class ZoneTariff extends Tariff{
    
    /**
     * Map of zones of stations
     */
    private Map<Station, Integer> zones;
    
    /**
     * Path to file with zones of stations
     */
    private final String ZfilePath;

    /**
     * Creates new tariff which calculates price based on zones of stations
     * @param name Name of tariff
     * @param abbreavation Abbreavation of tariff
     */
    public ZoneTariff(String name, String abbreavation)
    {
        super(TariffType.ZONE, name, abbreavation);
        this.zones = new HashMap<>();
        for (Station s: cz.upce.fei.skodaj.bzapr.semestralproject.data.Stations.GetInstance().GetAllStations())
        {
            this.zones.put(s, null);
        }
        this.ZfilePath = "resources/" + abbreavation.toLowerCase() + ".jtz";
        this.LoadZones();
    }

    @Override
    public int GetPrice(Station origin, Station destination)
    {
        return 0;
    }
    
    /**
     * Saves zones to file
     */
    private void SaveZones()
    {
        // First, save all to one big array
        int idx = 0;
        int output[] = new int[(this.zones.size() * 2) + 1];
        output[idx] = this.zones.size();
        idx++;
        for (Station s: this.zones.keySet())
        {
            output[idx] = s.GetIdentifier();
            idx++;
        }
        for (int i = 0; i < this.zones.size(); i++)
        {
            Station s = Stations.GetInstance().GetStation(output[i + 1]);
            output[idx] = this.zones.get(s);
            idx++;
        }
        
        // Than, save it to file
        ByteArrayOutputStream baos = new ByteArrayOutputStream(output.length * Integer.BYTES);
        DataOutputStream dos = new DataOutputStream(baos);
        for (int val: output)
        {
            try
            {
                dos.writeInt(val);
            }
            catch (IOException ex)
            {
                Logger.getLogger(ZoneTariff.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try
        {
            FileOutputStream fos = new FileOutputStream(this.ZfilePath);
            baos.writeTo(fos);
            fos.flush();
            fos.close();
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(ZoneTariff.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            Logger.getLogger(ZoneTariff.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    /**
     * Loads tones from file
     */
    private void LoadZones()
    {
        File in = new File (this.ZfilePath);
        int [] data;
        if (in.exists())
        {
            try
            {
                FileInputStream fis = new FileInputStream(in);
                BufferedInputStream bis = new BufferedInputStream(fis);
                DataInputStream dis = new DataInputStream(bis);
                // First, load file into array
                int count = (int) (in.length() / Integer.BYTES);
                data = new int[count];
                for (int i = 0; i < count; i++)
                {
                    data[i] = dis.readInt();
                }
                int dataCount = data[0];
                for (int i = 0; i < dataCount; i++)
                {
                    this.zones.put(Stations.GetInstance().GetStation(data[i] + 1), data[i + 1 + dataCount]);
                }
            }
            catch (FileNotFoundException ex)
            {
                Logger.getLogger(ZoneTariff.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (IOException ex)
            {
                Logger.getLogger(ZoneTariff.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    /**
     * Sets zone to station
     * @param st Station to which zone ill be assigned to
     * @param zone Zone which will be assigned to station
     */
    public void SetZone(Station st, int zone)
    {
        this.zones.put(st, zone);
        this.SaveZones();
    }
    
    /**
     * Generates string with HTML table rows containing zone for each station
     * @return String with HTML table rows containing zone for each station
     */
    public String GenerateZonesTr()
    {
        String reti = new String();
        for (Station st: cz.upce.fei.skodaj.bzapr.semestralproject.data.Stations.GetInstance().GetAllStations())
        {
            reti += "<tr><td style='color: green;'>" + st.GetAbbrevation().toUpperCase() + "</td><td>" + st.GetName() + "</td><td style='color: white;'>" + (this.zones.get(st) == null ? "0" : this.zones.get(st)) + "</td></tr>";
        }
        return reti;
    }
}
