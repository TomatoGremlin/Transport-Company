package com.example.transportcompany.util;

import com.example.transportcompany.model.Transportation;

import java.io.*;
import java.util.List;

public class TransportationUtil {
    /**
     * Writes a list of Transportation objects to a specified file.
     *
     * @param outputFile      The file path to write the Transportation objects to.
     * @param transportations The list of Transportation objects to write to the file.
     * @throws RuntimeException If an I/O error occurs while writing to the file.
     */
    public static void writeTransportations(String outputFile, List<Transportation> transportations){

        try(FileWriter fout = new FileWriter( new File( outputFile ), false)  ){
            for (Transportation transportation: transportations) {
                fout.append( transportation.toString() + System.lineSeparator() );
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing transportations to file: " + e.getMessage(), e);
        }
    }

    /**
     * Reads Transportation data from a specified input file and returns it as a string.
     *
     * @param inputFile The file path from which to read the Transportation data.
     * @return A string containing the read Transportation data.
     * @throws RuntimeException If an I/O error occurs while reading from the file or if the file is not found.
     */

    public static String readTransportations(String inputFile) {
        StringBuilder transportationBuilder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                transportationBuilder.append(line).append("\n");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException("Error reading transportations from file: " + e.getMessage(), e);
        }
        return transportationBuilder.toString();
    }
}
