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
package cz.upce.fei.skodaj.bzapr.semestralproject.networking;

/**
 * Class representing Internet Protocol v4 address
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class IPv4Address {
    
    /**
     * Integer containing whole address
     */
    private int address;
    
    /**
     * Mask of address
     */
    private IPv4Address mask;
    
    /**
     * Creates new instance of Internet Protocol v4 address
     * @param firstOctet First octet of the address
     * @param secondOctet Second octet of the address
     * @param thirdOctet Third octet of the address
     * @param fourthOctet Fourth octet of the address
     * @param mask Mask of the address
     */
    public IPv4Address (byte firstOctet, byte secondOctet, byte thirdOctet, byte fourthOctet, byte mask)
    {
        this.address = (firstOctet << 24) | (secondOctet << 16) | (thirdOctet << 8) | fourthOctet;
    }
    
    /**
     * Creates new instance of Internet Protocol v4 address
     * @param firstOctet First octet of the address
     * @param secondOctet Second octet of the address
     * @param thirdOctet Third octet of the address
     * @param fourthOctet Fourth octet of the address
     * @param mask Mask of the address
     */
    public IPv4Address (byte firstOctet, byte secondOctet, byte thirdOctet, byte fourthOctet, IPv4Address mask)
    {
        
    }
    
    /**
     * Checks, whether address is mask
     * @return TRUE, if address is mask, FALSE otherwise
     */
    public boolean IsMask()
    {
        
    }
}
