package addressbook;

import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class DataViewer 
{
    final static Charset ENCODING = StandardCharsets.UTF_8;
   
    private ArrayList<String[]> data;
    
    public DataViewer ()
    {
        data = new ArrayList<>();
    }
    
    public boolean readData (String passKey)
    {
        Path path = Paths.get ("information.txt");
        try (Scanner input = new Scanner (path, ENCODING.name()))
        {
            String line = null;
            
            if (input.hasNextLine()) // data file has entries (program has already been used)
            {
                while (input.hasNextLine())
                {
                    line = input.nextLine();
                    //line = Encrypter.xorMessage(line, passKey);
                    data.add(line.split(","));
                }
                
                return true;
            }
        }
        catch (IOException e)
        {
            System.err.println("File not read in or error decoding.");
        }
        return false;
    }
    
    public void writeData (String passKey)
    {
        Path path = Paths.get ("information.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(path, ENCODING))
        {
            String line = null, fName, lName, address, hPhone, mPhone;
            for (int i = 0; i < data.size(); i++)
            {
                fName = data.get(i)[0];
                lName = data.get(i)[1];
                address = data.get(i)[2];
                hPhone = data.get(i)[3];
                mPhone = data.get(i)[4];
                
                line = fName + "," + lName + "," + address + "," + hPhone + "," + mPhone;
                //line = Encrypter.xorMessage(line, passKey);
                //String encoded = Encrypter.base64encode(line);
                writer.write (line);
                writer.newLine();
            }
        }
        catch (IOException e)
        {
            System.err.println("Unable to write to file or error encoding.");
        }
    }
    
    public String[] grabEntry (String firstName, String lastName)
    {
        String entry[] = new String[5];
        
        for (int i = 0; i < data.size(); i++)
        {
            if (data.get(i)[0].equals(firstName) && data.get(i)[1].equals(lastName))
            {
                entry = data.get(i);
                break;
            }
        }
        
        return entry;
    }
    
    /*
    Used to populate list of names and
    populate user information window
    */
    public ArrayList<String[]> grabNames ()
    {
        return data;
    }
    
    public boolean userExists (String firstName, String lastName)
    {
        for (int i = 0; i < data.size(); i++)
        {
            if (data.get(i)[0].equals(firstName) && data.get(i)[1].equals(lastName))
            {
                return true;
            }
        }
        
        return false;
    }
    
    public int getIndex (String firstName, String lastName)
    {
        int index = 0;
        for (int i = 0; i < data.size(); i++)
        {
            if (data.get(i)[0].equals(firstName) && data.get(i)[1].equals(lastName))
            {
                index = i;
                break;
            }
        }
        
        return index;
    }
    
    public void removeEntry (int index)
    {
        data.remove(index);
    }
    
    public void addEntry (String [] newData)
    {
        data.add(newData);
    }
    
    public void updateEntry (String [] newData, int index)
    {
        data.remove(index);
        data.add(index, newData);
    }
}
