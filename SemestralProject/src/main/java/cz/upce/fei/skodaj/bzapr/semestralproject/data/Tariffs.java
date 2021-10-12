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
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class managing all tariffs
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Tariffs {
    
    /**
     * Instance of tariffs
     */
    private static Tariffs instance = null;
    
    /**
     * File containing all files
     */
    private File dataFile;
    
    /**
     * List of all available tariffs
     */
    private List<Tariff> tariffs;
    
    /**
     * Create new instance of tariff manager
     */
    private Tariffs()
    {
        this.dataFile = new File("resources/tariffs.csv");
        this.tariffs = new ArrayList<>();
        this.LoadTariffs();
    }
    
    /**
     * Gets instance of tariffs manager
     * @return Instance of tariffs manager
     */
    public static Tariffs GetInstance()
    {
        if (Tariffs.instance == null)
        {
            Tariffs.instance = new Tariffs();
        }
        return Tariffs.instance;
    }
    
    /**
     * Gets all tariffs available in system
     * @return List of all tariffs available in system
     */
    public Tariff[] GetAllTariffs()
    {
        Tariff reti[] = new Tariff[this.tariffs.size()];
        ListIterator<Tariff> it = this.tariffs.listIterator();
        int idx = 0;
        while (it.hasNext())
        {
            reti[idx] = it.next();
            idx++;
        }
        return reti;
    }
    
    
    /**
     * Generates HTML table rows to show all available tariffs
     * @return String containing HTML table rows with all available tariffs
     */
    public String GenerateTariffsTableRows()
    {
        String reti = new String();
        for (Tariff t: this.GetAllTariffs())
        {
            reti += "<tr><td style='color: blue;'>" + t.GetAbbr() + "</td><td>" + t.GetName() + "</td><td  style='color: green;'>";
            if (t.GetType() == TariffType.DISTANCE) reti += "VZDALENOSTNI";
            else if (t.GetType() == TariffType.ZONE) reti += "ZONOVY";
            reti += "</td></tr>";
                
        }
        return reti;
    }
    
    /**
     * Gets tariff by its name
     * @param name Name of tariff
     * @return Tariff selected by its name or <code>NULL</code> if there is not such an tariff
     */
    public Tariff GetTariffByName(String name)
    {
        Tariff reti = null;
        ListIterator<Tariff> it = this.tariffs.listIterator();
        while (it.hasNext())
        {
            Tariff t = it.next();
            if (t != null && t.GetName().toLowerCase().equals(name.toLowerCase()))
            {
                reti = t;
                break;
            }
        }
        return reti;
    }
    
    /**
     * Gets tariff by its abbreavation
     * @param abbr Abbreavation of tariff
     * @return Tariff selected by its abbreavation or <code>NULL</code> if there is not such an tariff
     */
    public Tariff GetTariffByAbbr(String abbr)
    {
        Tariff reti = null;
        ListIterator<Tariff> it = this.tariffs.listIterator();
        while (it.hasNext())
        {
            Tariff t = it.next();
            if (t != null && t.GetAbbr().toLowerCase().equals(abbr.toLowerCase()))
            {
                reti = t;
                break;
            }
        }
        return reti;
    }
    
    /**
     * Gets tariff by its name or abbreavation
     * @param nameOrAbbr Name or abbreavation of tariff
     * @return Selected tariff or <code>NULL</code> if there is not such a tariff
     */
    public Tariff GetTariff(String nameOrAbbr)
    {
        Tariff reti = this.GetTariffByAbbr(nameOrAbbr);
        if (reti == null)
        {
            reti = this.GetTariffByName(nameOrAbbr);
        }
        return reti;
    }
    
    /**
     * Saves tariffs to file
     */
    private void SaveTariffs()
    {
        String output = "abbr, name, type\n";
        ListIterator<Tariff> it = this.tariffs.listIterator();
        while (it.hasNext())
        {
            Tariff t = it.next();
            if (t != null)
            {
                output += t.GetAbbr() + "," + t.GetName() + "," + (t.GetType() == TariffType.ZONE ? "Z" : "D");
            }
        }
        try
        {
            FileWriter fw = new FileWriter(this.dataFile.getAbsolutePath());
            fw.write(output);
            fw.close();
        }
        catch (IOException ex)
        {
            Logger.getLogger(Tariffs.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * Loads tariffs from file
     */
    private void LoadTariffs()
    {
        if (this.dataFile.exists())
        {
            try
            {
                String content = new Scanner(this.dataFile).useDelimiter("\\Z").next();
                int lineNr = 0;
                for (String line: content.split("\n"))
                {
                    if (lineNr > 0)
                    {
                        String[] parts = line.split(",");
                        if (parts.length >= 3)
                        {
                            if (parts[2].toLowerCase().equals("z"))
                            {
                                this.tariffs.add(new ZoneTariff(parts[1], parts[0]));
                            }
                            else if (parts[2].toLowerCase().equals("d"))
                            {
                                // TODO Implement distance tariff
                            }
                        }
                    }
                    lineNr++;
                }
            }
            catch (FileNotFoundException ex)
            {
                Logger.getLogger(Tariffs.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Adds tariff to system
     * @param t Tariff which will be added to the system
     */
    public void AddTariff(Tariff t)
    {
        this.tariffs.add(t);
        this.SaveTariffs();
    }
    
    /**
     * Removes tariff from system
     * @param t Tariff which will be removed from system
     */
    public void RemoveTariff(Tariff t)
    {
        t.Delete();
        this.tariffs.remove(t);
        this.SaveTariffs();
    }
}
