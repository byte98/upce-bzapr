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

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class tf
{
    /**
     * Name of tariff
     */
    private String name;
    
    /**
     * Name of file containing tariffs data
     */
    private String file;
    
    /**
     * Pricelist of tariff
     */
    private Map<Integer, Integer> priceList;
    
    /**
     * Creates new tariff
     * @param name Name of tariff
     * @param file File containing tariffs data
     */
    public tf(String name, String file)
    {
        this.priceList = new HashMap<>();
        this.name = name;
        this.file = file;
        this.LoadTariff();
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
     * Gets price for distance
     * @param distance Distance for which will be price returned
     * @return Price for distance
     */
    public int GetPrice(int distance)
    {
        int reti = 0;
        if (this.priceList.containsKey(distance))
        {
            reti = this.priceList.get(distance);
        }
        return reti;
    }
    
    
    /**
     * Sets price for distance
     * @param distance Distance for which price will be set
     * @param price Price for distance
     */
    public void SetPrice(int distance, int price)
    {
        this.priceList.put(distance, price);
        this.SaveTariff();
    }
    
    /**
     * Saves tariff to file
     */
    private void SaveTariff()
    {
        // First, transform data into list
        List<Integer> dataList = new LinkedList<>();
        this.priceList.entrySet().stream().map(entry -> {
            dataList.add(entry.getKey());
            return entry;
        }).forEachOrdered(entry -> {
            dataList.add(entry.getValue());
        });
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream(dataList.size() * Integer.BYTES);
        DataOutputStream dos = new DataOutputStream(baos);
        Iterator<Integer> it = dataList.iterator();
        while (it.hasNext())
        {
            Integer next = it.next();
            if (next != null)
            {
                try
                {
                    dos.writeInt(next);
                }
                catch (IOException ex)
                {
                    Logger.getLogger(tf.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        try
        {
            FileOutputStream fos = new FileOutputStream(this.file);
            baos.writeTo(fos);
            fos.flush();
            fos.close();
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(tf.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            Logger.getLogger(tf.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * Loads tariff from file
     */
    private void LoadTariff()
    {
        try
        {
            List<Integer> data = new ArrayList<>();
            FileInputStream fis = new FileInputStream(this.file);
            DataInputStream dis = new DataInputStream(fis);
            try
            {
                while (true)
                {
                    data.add(dis.readInt());
                }
            }
            catch (EOFException ex)
            {
                // Transform data into array
                Iterator<Integer> it = data.iterator();
                int arr[] = new int[data.size()];
                int idx = 0;
                while (it.hasNext())
                {
                    arr[idx] = it.next();
                    idx++;
                }
                
                for (int i = 0; i < arr.length; i+=2)
                {
                    this.priceList.put(arr[i], arr[i+1]);
                }
                
            }
            catch (IOException ex)
            {
                Logger.getLogger(tf.class.getName()).log(Level.SEVERE, null, ex);
            }
            dis.close();
        }
        catch (FileNotFoundException ex)
        {            
            Logger.getLogger(tf.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            Logger.getLogger(tf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
