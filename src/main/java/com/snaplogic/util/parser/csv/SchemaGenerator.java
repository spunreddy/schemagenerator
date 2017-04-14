package com.snaplogic.util.parser.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class SchemaGenerator {

	/**
	 * Java class to generate a schema file to supply to CSV Parser Snap for Schema Aware Spark mode.
	 * Arguments: Input-File, Delimiter and Output-Directory
	 * The file will be written to the output-directory as schema.json
	 * @param args
	 */
	public static void main(String[] args) {
		
		Options options = new Options();

        Option input = new Option("i", "input", true, "input file path");
        input.setRequired(true);
        options.addOption(input);

        Option delimiter = new Option("d", "delimiter", true, "input file delimiter");
        delimiter.setRequired(true);
        options.addOption(delimiter);

        Option output = new Option("o", "output", true, "output directory");
        output.setRequired(true);
        options.addOption(output);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
            return;
        }
		
		// The name of the file to open.
        String fileName = cmd.getOptionValue("input"); //args[0]; //"/Users/spunreddy/Documents/snaplogic/field/Customers/Cap1/pipelines/sample-ppdd_header_pipe_delimited.txt";
        String fileDelimiter = cmd.getOptionValue("delimiter"); //args[1];
        String outputDirectory = cmd.getOptionValue("output"); //args[2];

        // This will reference one line at a time
        String line = null;
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            // FileReader reads text files in the default encoding.
            fileReader =
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            bufferedReader =
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
              //String[] value_split = line.split("\\|");
              String[] value_split = line.split(fileDelimiter);
              
              Schema schema = new Schema();
              List<Field> fields = new ArrayList<Field>();
              for(int i=0; i<value_split.length; i++) {
            	  Field field = new Field();
            	  field.setName(value_split[i]);
            	  field.setType("String");
            	  field.setNullable(Boolean.TRUE);
            	  Metadata metadata = new Metadata();
            	  field.setMetadata(metadata);
            	  fields.add(field);
              }
              schema.setType("struct");
              schema.setFields(fields);
              
              ObjectMapper mapper = new ObjectMapper();
              mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
              String outputFileName = "sample-ppdd_header_pipe_delimited_schema_" + Calendar.getInstance().getTimeInMillis();
              //mapper.writeValue(new File("/Users/spunreddy/Documents/snaplogic/field/Customers/Cap1/pipelines/" + outputFileName + ".json"), schema);
              mapper.writeValue(new File(outputDirectory + "/" + outputFileName + ".json"), schema);
              System.out.println("The Schema has been successfully generated and written to: "+ outputDirectory + "/"  + outputFileName + ".json");
            }

        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" +
                fileName + "'");
            ex.printStackTrace();
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '"
                + fileName + "'");
            // Or we could just do this:
            ex.printStackTrace();
        }
        finally {
        	
            // Always close files.
            if(bufferedReader != null)
				try {
					bufferedReader.close();
				} catch (IOException e) {
					System.out.println(
			                "Error closing file '");
					e.printStackTrace();
				}
            if(fileReader != null )
				try {
					fileReader.close();
				} catch (IOException e) {
					System.out.println(
			                "Error closing file '");
					e.printStackTrace();
				}
        }
		
		

	}

}
