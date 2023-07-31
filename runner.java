import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;


import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Jsoup;
import java.io.File;

public class runner {
    public static void main(String[] args) {
        Scanner httpInput = new Scanner(System.in);
        System.out.println("enter destination");
        String gohere = httpInput.nextLine();
        httpInput.close();
            try {
                String jsFilePath = "crawlerFileTyper.js";

                // Set up the ProcessBuilder
                ProcessBuilder processBuilder = new ProcessBuilder("node", jsFilePath,"\""  + gohere +"\"" );
                processBuilder.redirectErrorStream(true);

                // Start the process
                Process process = processBuilder.start();

                // Wait for the process to finish
                int exitCode = process.waitFor();

                // Check the exit code to see if the process completed successfully
                if(exitCode == 0){

                    try {
                        // Load the HTML file from the local file system
                        File input = new File("reactstorefront.html");

                        // Parse the HTML file using Jsoup
                        Document doc = Jsoup.parse(input, "UTF-8");

                        // Example: Extract and print the text from the <title> element
                        Element titleElement = doc.selectFirst("title");
                        String title = titleElement != null ? titleElement.text() : "No title found";
                        System.out.println("Title: " + title);

                        // Example: Extract and print all the text from <p> elements
                        Elements paragraphElements = doc.select("p");
                        for (Element paragraph : paragraphElements) {
                            System.out.println("paragraphs: " + paragraph.text());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                 else {
                    System.out.println("JavaScript execution failed with exit code: " + exitCode);
                }

                // Read and print the output of the process (optional for maintenance)
                /*BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }*/


            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
        }
    }
}