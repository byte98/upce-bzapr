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
import java.util.ArrayList;
import java.util.List;

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
    
    
    
}
