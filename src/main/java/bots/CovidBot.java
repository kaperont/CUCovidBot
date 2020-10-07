/*
 * @author Kyle Peront
 * @version 1.0
 * File: CovidBot.java
 * Created: September 2020
 * All rights reserved.
 * Summary of Modifications:
 *      30 September 2020 - Implemented and released CovidBot to the CS Discord with the functions "!ping" and "!covid"
        07 October   2020 - Implemented new functions "!covid -a" "!binaryCount" and "!binaryCount -a" as well as added
                            new features including on and off campus counts and the percentage of positive students.
 * 
 * Description: This Bot downloads the COVID-19 report for (c)Cedarville University and sends the report with modified format
                    to the Computer Science Discord Server for Cedarville University.
 */

package bots;

import java.io.*; 
import java.net.URL;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.math.RoundingMode;
import org.javacord.api.*;
import java.text.DecimalFormat;

public class CovidBot {

    static String ON_CAMPUS = "";
    static String OFF_CAMPUS = "";  
    static String TOTAL = "";
    static String PERCENTAGE = "";
    
    public static void main(String args[]) { 
        
        // Login to Discord Bot
        String token = "Inser your Discord Bot Token Here";
        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();
        
        // Add a listener which answers with "Pong!" if someone writes "!ping"
        api.addMessageCreateListener(event -> {
            if (event.getMessageContent().equalsIgnoreCase("!ping")) {
                event.getChannel().sendMessage("Pong!");
            }
        });
            
        // Add a listener which answers "!covid" with the count
        api.addMessageCreateListener(event -> {
            if (event.getMessageContent().equalsIgnoreCase("!covid")) {
                try {
                    FileWriter fstream = new FileWriter("./data/log.txt", true);
                    BufferedWriter log = new BufferedWriter(fstream);
                    log.write(event.getMessageAuthor() + " ::: !covid          ::: " + new java.util.Date() + "\n");
                    log.close();
                    
                    count();
                    
                    final String finTOTAL = TOTAL;
                    
                    event.getChannel().sendMessage(
                        "Last Updated: " + new java.util.Date() + "\n" +
                        "Total Active Confirmed Cases: **" + finTOTAL + "**"
                    );
                }
                catch(IOException e){
                    System.out.println("IOException raised");
                }
            }
        });  
        
        // Add a listener which answers "!covid -a" with the total counts
        api.addMessageCreateListener(event -> {
            if (event.getMessageContent().equalsIgnoreCase("!covid -a")) {
                try {
                    count();
                    FileWriter fstream = new FileWriter("./data/log.txt", true);
                    BufferedWriter log = new BufferedWriter(fstream);
                    log.write(event.getMessageAuthor() + " ::: !covid -a       ::: " + new java.util.Date() + "\n");
                    log.close();
                    
                    final String finON = ON_CAMPUS;
                    final String finOFF = OFF_CAMPUS;
                    final String finTOTAL = TOTAL;
                    double percentage = Double.valueOf(TOTAL);
                        
                    percentage = (percentage/3676)*(100);
                    DecimalFormat df = new DecimalFormat("###.##");
                    df.setRoundingMode(RoundingMode.CEILING);
                    
                    event.getChannel().sendMessage(
                        "Last Updated: " + new java.util.Date() + "\n" +
                        "Isolating On Campus: **" + finON + "**" + "\n" +
                        "Isolating Off Campus: **" + finOFF + "**" + "\n" +
                        "Total Active Confirmed Cases: **" + finTOTAL + "**" + "\n" +
                        "**" + df.format(percentage) + "%** of Undergraduate and Graduate Students Enrolled in a Campus-Based Program (" + PERCENTAGE + ")"
                    );
                }
                catch(IOException e){
                    System.out.println("IOException raised");
                }
            }
        }); 
        
        // Add a listener which answers "!binaryCount" with the count in binary
        api.addMessageCreateListener(event -> {
            if (event.getMessageContent().equalsIgnoreCase("!binaryCount")) {
                try {
                    count();
                    FileWriter fstream = new FileWriter("./data/log.txt", true);
                    BufferedWriter log = new BufferedWriter(fstream);
                    log.write(event.getMessageAuthor() + " ::: !binaryCount    ::: " + new java.util.Date() + "\n");
                    log.close();
                    
                    int totalAsInt = Integer.parseInt(TOTAL);
                    final String finTOTAL = Integer.toBinaryString(totalAsInt);
                    
                    event.getChannel().sendMessage(
                        "Last Updated: " + new java.util.Date() + "\n" +
                        "Total Active Confirmed Cases: **" + finTOTAL + "**"
                    );
                }
                catch(IOException e){
                    System.out.println("IOException raised");
                }
            }
        });
        
                // Add a listener which answers "!binaryCount" with the count in binary
        api.addMessageCreateListener(event -> {
            if (event.getMessageContent().equalsIgnoreCase("!binaryCount -a")) {
                try {
                    count();
                    FileWriter fstream = new FileWriter("./data/log.txt", true);
                    BufferedWriter log = new BufferedWriter(fstream);
                    log.write(event.getMessageAuthor() + " ::: !binaryCount -a ::: " + new java.util.Date() + "\n");
                    log.close();
                    
                    int onAsInt = Integer.parseInt(ON_CAMPUS);
                    int offAsInt = Integer.parseInt(OFF_CAMPUS);
                    int totalAsInt = Integer.parseInt(TOTAL);
                    
                    final String finON = Integer.toBinaryString(onAsInt);
                    final String finOFF = Integer.toBinaryString(offAsInt);
                    final String finTOTAL = Integer.toBinaryString(totalAsInt);
                    double percentage = Double.valueOf(TOTAL);
                        
                    percentage = (percentage/3676)*(100);
                    DecimalFormat df = new DecimalFormat("###.##");
                    df.setRoundingMode(RoundingMode.CEILING);
                    
                    event.getChannel().sendMessage(
                        "Last Updated: " + new java.util.Date() + "\n" +
                        "Isolating On Campus: **" + finON + "**" + "\n" +
                        "Isolating Off Campus: **" + finOFF + "**" + "\n" +
                        "Total Active Confirmed Cases: **" + finTOTAL + "**" + "\n" +
                        "**" + df.format(percentage) + "%** of Undergraduate and Graduate Students Enrolled in a Campus-Based Program (" + PERCENTAGE +")"
                    );
                }
                catch(IOException e){
                    System.out.println("IOException raised");
                }
            }
        });
        
        
        // Add a listener which answers "!covid -a" with the total counts
        api.addMessageCreateListener(event -> {
            if (event.getMessageContent().equalsIgnoreCase("!test")) {
                try {
                    
                    String ID = event.getMessageAuthor().getIdAsString();
                    if(ID.matches("Developer's User ID")){
                        
                        count();

                        final String finON = ON_CAMPUS;
                        final String finOFF = OFF_CAMPUS;
                        final String finTOTAL = TOTAL;
                        double percentage = Double.valueOf(TOTAL);
                        
                        percentage = (percentage/3676)*(100);
                        DecimalFormat df = new DecimalFormat("###.##");
                        df.setRoundingMode(RoundingMode.CEILING);
                        

                        event.getChannel().sendMessage(
                            "Last Updated: " + new java.util.Date() + "\n" +
                            "Isolating On Campus: **" + finON + "**" + "\n" +
                            "Isolating Off Campus: **" + finOFF + "**" + "\n" +
                            "Total Active Confirmed Cases: **" + finTOTAL + "**" + "\n" +
                            "**" + df.format(percentage) + "%** of Undergraduate and Graduate Students Enrolled in a Campus-Based Program (" + PERCENTAGE + ")"
                        );
                    }
                }
                catch(IOException e){
                    System.out.println("IOException raised");
                }
            }
        }); 
        
        
    }
    
    public static void count() throws IOException 
    {
        
        String bigString = "";
        URL url = new URL("https://www.cedarville.edu/caring-well-staying-well/covid-19-reporting");
        Scanner scnr = new Scanner(url.openStream());
        StringBuffer sb = new StringBuffer();
        
        while(scnr.hasNext()){
            sb.append(scnr.next());
        }
        
        bigString = sb.toString();
                    
        Scanner stringSCNR = new Scanner(bigString);

        // Define strings to be pushed
        String onCampus = "0";
        String offCampus = "0";
        String total = "0";
        String percentage = "0";
                    
        for(int i = 100; i > 0; i--){
            onCampus = stringSCNR.findInLine("Campus:<br>" + String.valueOf(i));
            if(onCampus != null){
                break;
            }
        }
                    
        for(int i = 100; i > 0; i--){
            offCampus = stringSCNR.findInLine("Campus:<br>" + String.valueOf(i));
            if(offCampus != null){
                break;
            }
        }
        
        for(int i = 100; i > 0; i--){
            total = stringSCNR.findInLine("Cases:" + String.valueOf(i));
            if(total != null){
                break;
            }
        }
                    
        onCampus = onCampus.replace("Campus:<br>", "");
        offCampus = offCampus.replace("Campus:<br>", "");
        total = total.replace("Cases:", "");
        percentage = total + "/3676";
        
        System.out.println("Successfully Downloaded Covid Report: " + new java.util.Date());
        System.out.println( "Isolating On Campus: " + onCampus + "\n" + 
                             "Isolating On Campus: " + offCampus + "\n" +
                             "Total Active Confirmed Cases: " + total + "\n"
        );
        
        ON_CAMPUS = onCampus;
        OFF_CAMPUS = offCampus;
        TOTAL = total;
        PERCENTAGE = percentage;
        
    }
    
}