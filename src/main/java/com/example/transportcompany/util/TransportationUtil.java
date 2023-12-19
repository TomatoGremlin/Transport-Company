package com.example.transportcompany.util;

import com.example.transportcompany.model.Transportation;

import java.io.*;
import java.util.List;

public class TransportationUtil {
    public static void writeTransportations(String outputFile, List<Transportation> transportations){

        try(FileWriter fout = new FileWriter( new File( outputFile ), false)  ){
            for (Transportation transportation: transportations) {
                fout.append( transportation.toString() + System.lineSeparator() );
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readTransportations(String inputFile){
        StringBuilder transportationBuilder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader( new FileReader( inputFile) )) {
            String line;
            while ((line = reader.readLine()) != null) {
                transportationBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transportationBuilder.toString();
    }
}
