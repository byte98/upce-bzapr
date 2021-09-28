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
package cz.upce.fei.skodaj.bzapr.semestralproject.ui.screens;

/**
 * Class creating new screens
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class ScreenFactory {
    /**
     * Creates new HTML screen
     * @param name Name of screen
     * @param fileName File containing content of the screen
     * @return Screen with selected name and content
     */
    public static HTMLScreen CreateHTMLScreen(String name, String fileName)
    {
        return new HTMLScreen(name, fileName);
    }
}
