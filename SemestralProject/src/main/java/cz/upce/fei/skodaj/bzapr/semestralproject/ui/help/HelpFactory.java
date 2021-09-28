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
package cz.upce.fei.skodaj.bzapr.semestralproject.ui.help;

import java.awt.Color;

/**
 * Class which can create simple helps
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class HelpFactory {
    
    /**
     * Creates new help to the command
     * @param command Command to which help belongs to
     * @param color Color of the command
     * @param help Text of help to the command
     * @return New simple help to the command
     */
    public static SimpleHelp CreateSimpleHelp(String command, Color color, String help)
    {
        return new SimpleHelp(command, help, color);
    }
}
