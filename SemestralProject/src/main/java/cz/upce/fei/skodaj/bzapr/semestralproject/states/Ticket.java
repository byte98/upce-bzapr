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
import cz.upce.fei.skodaj.bzapr.semestralproject.data.Station;
import cz.upce.fei.skodaj.bzapr.semestralproject.data.Tariff;
import cz.upce.fei.skodaj.bzapr.semestralproject.data.TariffType;
import cz.upce.fei.skodaj.bzapr.semestralproject.data.ZoneTariff;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.help.Help;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.help.HelpFactory;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.screens.HTMLTemplateScreen;
import cz.upce.fei.skodaj.bzapr.semestralproject.ui.screens.Screen;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 * Class representing ticket selling state of program
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Ticket extends State{

    /**
     * Progress in filling form to sell ticket
     */
    private byte progress = 0;
    
    /**
     * Information about actual ticket
     */
    private final Map<String, String> ticketData;
    
    /**
     * Path to directory containing all generated tickets
     */
    private final String ticketsPath = "resources/tickets/";
    
    /**
     * Array with all needed screens to fill ticket data
     */
    private final HTMLTemplateScreen[] screens;
    
    /**
     * Array with helps to each state of filling ticket data
     */
    private Help[][] helps;
    
    /**
     * Tariff which calculates tickets price
     */
    private Tariff tariff;
    
    /**
     * Origin station of ticket
     */
    private Station origin;
    
    /**
     * Destination of ticket
     */
    private Station destination;
    
    /**
     * VAT rate (in percents)
     */
    private final int VAT = 10;
    
    /**
     * Creates new state of ticket selling
     * @param controller Controller of program
     */
    public Ticket(Controller controller)
    {
        super(controller);
        this.ticketData = new HashMap<>();
        this.strict = false;
        this.name = "ticket";
        this.commandPrefix = "/ticket:tariff";
        this.helps = new Help[4][];
        
        this.screens = new HTMLTemplateScreen[4];
        this.screens[0] = new HTMLTemplateScreen("ticket-tariff", "ticket-tariff.html");
        this.screens[1] = new HTMLTemplateScreen("ticket-from", "ticket-from.html");
        this.screens[2] = new HTMLTemplateScreen("ticket-to", "ticket-to.html");
        this.screens[3] = new HTMLTemplateScreen("ticket", "ticket.html");
        
        this.helps[0] = new Help[2];
        this.helps[0][0] = HelpFactory.CreateSimpleHelp("<jmeno nebo zkratka tarifu>", Color.YELLOW, "Jmeno nebo zkratka tarifu, podle ktereho se vypocita cena");
        this.helps[0][1] = HelpFactory.CreateSimpleHelp("cancel", Color.MAGENTA, "Zrusit");
        
        this.helps[1] = new Help[2];
        this.helps[1][0] = HelpFactory.CreateSimpleHelp("<jmeno nebo zkratka stanice>", Color.YELLOW, "Jmeno nebo zkratka vychozi stanice");
        this.helps[1][1] = HelpFactory.CreateSimpleHelp("cancel", Color.MAGENTA, "Zrusit");
        
        this.helps[2] = new Help[2];
        this.helps[2][0] = HelpFactory.CreateSimpleHelp("<jmeno nebo zkratka stanice>", Color.YELLOW, "Jmeno nebo zkratka cilove stanice");
        this.helps[2][1] = HelpFactory.CreateSimpleHelp("cancel", Color.MAGENTA, "Zrusit");
        
        this.helps[3] = new Help[2];
        this.helps[3][0] = HelpFactory.CreateSimpleHelp("yes", Color.GREEN, "Ano, zadane udaje jsou v poradku");
        this.helps[3][1] = HelpFactory.CreateSimpleHelp("no", Color.RED, "Zrusit");
    }
    
    @Override
    public void Load()
    {
        // Fill information about tickets with white spaces
        this.ticketData.put("ticket_tariff", " ");
        this.ticketData.put("ticket_id", this.GenerateTicketId());
        this.ticketData.put("ticket_from", " ");
        this.ticketData.put("ticket_to", " ");
        Date date = new Date();
        String issued = new String();
        issued += (date.getDate() < 10 ? "0" + date.getDate(): date.getDate());
        issued += ".";
        issued += (date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1): (date.getMonth() + 1));
        issued += ".";
        issued += date.getYear() + 1900;
        this.ticketData.put("ticket_issued", issued);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        date = c.getTime();
        String valid = new String();
        valid += (date.getDate() < 10 ? "0" + date.getDate(): date.getDate());
        valid += ".";
        valid += (date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1): (date.getMonth() + 1));
        valid += ".";
        valid += date.getYear() + 1900;
        this.ticketData.put("ticket_validity", valid);
        this.ticketData.put("ticket_price", " ");
        this.ticketData.put("ticket_distance", " ");
        this.commandPrefix = "/ticket:tariff";
        this.progress = 0;
    }
    
    @Override
    public Help[] GetHelps()
    {
        return this.helps[this.progress];
    }
    
    @Override
    public Screen GetScreen()
    {
        if (this.progress == 0) this.controller.ShowTariffsHelp();
        else this.controller.ShowStationsHelp();
        HTMLTemplateScreen reti = this.screens[this.progress];
        reti.SetContent(this.ticketData);
        return reti;
    }
    
    @Override
    public Screen GetScreen(Map<String, String> data)
    {
        if (this.progress == 0) this.controller.ShowTariffsHelp();
        else this.controller.ShowStationsHelp();
        HTMLTemplateScreen reti = this.screens[this.progress];
        this.ticketData.keySet().forEach(tD -> {
            data.put(tD, this.ticketData.get(tD));
        });
        reti.SetContent(data);
        return reti;
    }
    
    /**
     * Generates new unused identifier for ticket
     * @return New unique identifier for ticket
     */
    private String GenerateTicketId()
    {
        String reti = new String();
        int number = 0;
        do
        {
            number++;
            reti = String.format("JT%012d", number);
        }
        while (new File(this.ticketsPath + reti + ".pdf").exists());
        return reti;
    }

    @Override
    public void HandleInput(String input)
    {
        if (input.length() > 0)
        {
            if (progress < 3 && input.toLowerCase().equals("cancel"))
            {
                this.controller.HideHelp();
                this.controller.ChangeState("welcome");
            }
            else
            {
                switch (this.progress)
                {
                    case 0:
                        Tariff t = cz.upce.fei.skodaj.bzapr.semestralproject.data.Tariffs.GetInstance().GetTariff(input);
                        if (t == null)
                        {
                            this.controller.ShowError("Neznamy tarif '" + input + "'!");
                        }
                        else
                        {
                            this.tariff = t;
                            this.ticketData.put("ticket_tariff", t.GetName());
                            this.commandPrefix = "/ticket:from";
                            this.progress++;
                            this.controller.ReDraw();
                        }
                        break;
                    case 1:
                        Station s = cz.upce.fei.skodaj.bzapr.semestralproject.data.Stations.GetInstance().GetStation(input);
                        if (s == null)
                        {
                            this.controller.ShowError("Neznama stanice '" + input + "'!");
                        }
                        else
                        {
                            this.origin = s;
                            if (this.tariff.GetType() == TariffType.ZONE)
                            {
                                ZoneTariff zt = (ZoneTariff) this.tariff;
                                this.ticketData.put("ticket_from", s.GetName() + " (" + zt.GetZone(s) + ")");
                            }
                            else this.ticketData.put("ticket_from", s.GetName());
                            this.commandPrefix = "/ticket:to";
                            this.progress++;
                            this.controller.ReDraw();
                        }
                        break;
                    case 2:
                        Station to = cz.upce.fei.skodaj.bzapr.semestralproject.data.Stations.GetInstance().GetStation(input);
                        if (to == null)
                        {
                            this.controller.ShowError("Neznama stanice '" + input + "'!");
                        }
                        else
                        {
                            this.destination = to;
                            String distance = cz.upce.fei.skodaj.bzapr.semestralproject.data.Distances.GetInstance().GetDistance(this.origin, this.destination) + " km";
                            if (this.tariff.GetType() == TariffType.ZONE)
                            {
                                ZoneTariff zt = (ZoneTariff) this.tariff;
                                this.ticketData.put("ticket_to", to.GetName() + " (" + zt.GetZone(to) + ")");
                                distance += " (pocet zon: " + (Math.abs(zt.GetZone(this.origin) - zt.GetZone(this.destination)) + 1) + ")";
                            }
                            else this.ticketData.put("ticket_to", to.GetName());
                            this.ticketData.put("ticket_distance", distance);
                            this.ticketData.put("ticket_price", String.valueOf(this.tariff.GetPrice(this.origin, this.destination)));
                            this.commandPrefix = "/ticket?";
                            this.progress++;
                            this.controller.ReDraw();
                        }
                        break;
                    case 3:
                        if (input.toLowerCase().equals("no"))
                        {
                            this.controller.HideHelp();
                            this.controller.ChangeState("welcome");
                        }
                        else if (input.toLowerCase().equals("yes"))
                        {
                            this.GeneratePdf();
                            this.controller.ShowSucess("Jizdenka s cislem " + this.ticketData.get("ticket_id") + " byla uspesne vytvorena.");
                            this.controller.HideHelp();
                            this.controller.ChangeState("welcome");
                            this.controller.ChangeState("ticket");
                        }
                        else
                        {
                            this.controller.ShowError("Neznamy prikaz '" + input + "'!");
                        }
                        break;
                }
            }
            
        }
    }
    
    /**
     * Generates PDF with ticket data
     */
    private void GeneratePdf()
    {
        String path = this.ticketsPath + this.ticketData.get("ticket_id") + ".pdf";
        PDDocument doc = new PDDocument();
        float POINTS_PER_INCH = 72f;
        PDPage page = new PDPage(new PDRectangle(5.8f * POINTS_PER_INCH, 8.2f * POINTS_PER_INCH));
        
        // Add background image
        try
        {
            
            PDPageContentStream stream = new PDPageContentStream(doc, page, false, true);
            PDImageXObject image = PDImageXObject.createFromFile("resources/template.bmp", doc);
            stream.saveGraphicsState();
            stream.drawImage(image, 0, 0);
            stream.restoreGraphicsState();
            stream.close();
        }
        catch (IOException ex)
        {
            Logger.getLogger(Ticket.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Add ticket info
        this.WriteTextToPage(doc, page, this.ticketData.get("ticket_issued"), PDType1Font.COURIER_BOLD, 24, 500, 10, Integer.MAX_VALUE);
        this.WriteTextToPage(doc, page, this.ticketData.get("ticket_id"), PDType1Font.COURIER_BOLD, 24, 500, 210, Integer.MAX_VALUE);
        this.WriteTextToPage(doc, page, this.ticketData.get("ticket_tariff"), PDType1Font.COURIER, 18, 450, 5, Integer.MAX_VALUE);
        this.WriteTextToPage(doc, page, this.ticketData.get("ticket_from"), PDType1Font.COURIER_BOLD, 24, 390, 52, 24);
        this.WriteTextToPage(doc, page, this.ticketData.get("ticket_to"), PDType1Font.COURIER_BOLD, 24, 235, 52, 24);
        this.WriteTextToPage(doc, page, this.ticketData.get("ticket_validity"), PDType1Font.COURIER, 24, 160, 85, Integer.MAX_VALUE);
        this.WriteTextToPage(doc, page, this.ticketData.get("ticket_distance"), PDType1Font.COURIER, 24, 110, 85, Integer.MAX_VALUE);
        this.WriteTextToPage(doc, page, this.ticketData.get("ticket_price"), PDType1Font.COURIER_BOLD, 48, 73, 280, Integer.MAX_VALUE);
        this.WriteTextToPage(doc, page, "Cena bez DPH", PDType1Font.COURIER, 12, 90, 5, Integer.MAX_VALUE);
        this.WriteTextToPage(doc, page, "Price without VAT", PDType1Font.COURIER, 12, 78, 5, Integer.MAX_VALUE);
        this.WriteTextToPage(doc, page, "CZK " + String.format("%.2f", (float)Integer.parseInt(this.ticketData.get("ticket_price")) - ((float)Integer.parseInt(this.ticketData.get("ticket_price")) * (float)((float)this.VAT / (float)100))), PDType1Font.COURIER, 18, 85, 140, Integer.MAX_VALUE);
        this.WriteTextToPage(doc, page, "DPH", PDType1Font.COURIER, 12, 66, 5, Integer.MAX_VALUE);
        this.WriteTextToPage(doc, page, "VAT", PDType1Font.COURIER, 12, 54, 5, Integer.MAX_VALUE);        
        this.WriteTextToPage(doc, page, this.VAT + " %", PDType1Font.COURIER, 18, 61, 70, Integer.MAX_VALUE);
        this.WriteTextToPage(doc, page, "CZK " + String.format("%.2f", (float)Integer.parseInt(this.ticketData.get("ticket_price")) * (float)((float)this.VAT / 100f)), PDType1Font.COURIER, 18, 61, 140, Integer.MAX_VALUE);
        
        doc.addPage(page);
        try
        {
            doc.save(path);
            doc.close();
        }
        catch (IOException ex)
        {
            Logger.getLogger(Ticket.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * Writes text to PDF document
     * @param document Document to which text will be written
     * @param page Page to which text will be written
     * @param text Text which will be written to page
     * @param fontFamily Font defining appearance of text which will be written to page
     * @param fontSize Size of font of text which will be written to page
     * @param top Top position of text
     * @param left Left position of text
     * @param maxLength Maximal length of row
     */
    private void WriteTextToPage(PDDocument document, PDPage page, String text, PDType1Font fontFamily, int fontSize, int top, int left, int maxLength)
    {
        if (text.length() > maxLength)
        {
            // If longer than expected line length, split to multiline
            String parts[] = text.split(" ");
            List<String> newLines = new ArrayList<>();
            String line = new String();
            for (String part: parts)
            {
                if (part.length() > 1)
                {
                    if (line.length() + part.length() > maxLength)
                    {
                        newLines.add(line);
                        line = part;
                    }
                    else
                    {
                        line += part;
                    }
                }
                else
                {
                    line += part;
                }
                line += " ";
            }
            newLines.add(line);
            ListIterator<String> it = newLines.listIterator();
            int idx = 0;
            while (it.hasNext())
            {
                this.WriteTextToPage(document, page, it.next(), fontFamily, fontSize, top - (idx * fontSize), left, Integer.MAX_VALUE);
                idx++;
            }
        }
        else
        {
            try
            {
                PDPageContentStream stream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND,true,true);        
                stream.setFont(fontFamily, fontSize);
                stream.beginText();
                stream.newLineAtOffset(left, top);
                stream.showText(text);
                stream.endText();
                stream.close();
            }
            catch (IOException ex)
            {
                Logger.getLogger(Ticket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
