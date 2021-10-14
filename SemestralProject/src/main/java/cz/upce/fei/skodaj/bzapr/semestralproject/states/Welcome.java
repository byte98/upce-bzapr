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
package cz.upce.fei.skodaj.bzapr.semestralproject.states;

import cz.upce.fei.skodaj.bzapr.semestralproject.Controller;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.help.Help;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.help.HelpFactory;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.screens.ScreenFactory;
import java.awt.Color;

/**
 * Class representing welcome state of program
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Welcome extends State
{

    /**
     * Creates new welcome state of program
     * @param controller Controller of whole program
     */
    public Welcome(Controller controller)
    {
        super(controller);
        this.commandPrefix = "";
        this.screen = ScreenFactory.CreateHTMLScreen("welcome", "welcome.html");
        this.name = "welcome";
        
        this.helps = new Help[3];
        this.helps[0] = HelpFactory.CreateSimpleHelp("sale", Color.YELLOW, "Rezim prodeje");
        this.helps[1] = HelpFactory.CreateSimpleHelp("data", Color.YELLOW, "Rezim upravy dat");
        this.helps[2] = HelpFactory.CreateSimpleHelp("exit", Color.MAGENTA, "Ukoncit program");
    }
    
    

    @Override
    public void HandleInput(String input)
    {
        switch (input.toLowerCase())
        {
            case "exit": this.controller.ChangeState("exit"); break;
            case "data": this.controller.ChangeState("data"); break;
            case "sale": this.controller.ShowTariffsHelp(); break;
        }
    }
    
}
