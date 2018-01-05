package com.cognizant.XMLCompare;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

public class XMLReader {
	
	private String xmlfilename;
	
	public XMLReader(String filename)
	{
		this.xmlfilename = filename;
	}

	public String getXmlfilename() {
		return xmlfilename;
	}

	public void setXmlfilename(String xmlfilename) {
		this.xmlfilename = xmlfilename;
	}
	
	public List<String> readXML()
	{
		XMLInputFactory factory = XMLInputFactory.newFactory();
		
		//path of the file
		String path = xmlfilename;
		List<String> listofnodes = new ArrayList<String>();
		//initialize a string which will keep on appending
		//as the xml is read through
		String text = "";
		int attr = 0;
		List<String> attributelist = new ArrayList<String>();
		try(InputStream input = Files.newInputStream(Paths.get(path)))
		{

			XMLStreamReader reader = factory.createXMLStreamReader(input);

//start the iterator/cursor
			
			while (reader.hasNext())
			{
				reader.next();

				//case 1 - if its a node
				if (reader.isStartElement())
				{



					text = text+"~Node - "+reader.getLocalName().trim()+",";

					//get the attributes if it has some and append it to the text
					if (reader.getAttributeCount()>0)
					{
						for (int i=0; i<reader.getAttributeCount(); i++)
						{
							
							if (reader.getAttributeValue(i).contains(","))
							{
								text = text+reader.getAttributeLocalName(i)+" - "+reader.getAttributeValue(i).replace(",", " ")+",";
							}
							else
							{
							text = text+reader.getAttributeLocalName(i)+" - "+reader.getAttributeValue(i)+",";
							}
							if (!attributelist.contains(reader.getAttributeLocalName(i)))
							{
							attributelist.add(reader.getAttributeLocalName(i));
							}
						}
						

						
						
					}
					
					
				}
				else{

					//if the element is a text get its corresponding text content
					if (reader.isCharacters())
					{
						if (!reader.getText().trim().equals(""))
						{
							if (reader.getText().contains(","))
							{
								text = text+"Text - "+reader.getText().trim().replace(",", " ")+",";
							}
							else{
							text = text+"Text - "+reader.getText().trim()+",";
							}
						}
					}
				}
			}

		}
		catch (Exception e)
		{
			System.out.println("XML could not be parsed");
		}
		
//		System.out.println(text);
		//split the text for nodes 
		//get each node as one separate line
		
		
//		System.out.println(array.length);
		
		String attributes = "";
		attributes+="Node~Text";
		
		for (String string : attributelist) {
			attributes+="~"+string;
		}

		listofnodes.add(attributes);
		String [] array = text.split("~");
		for (int j = 0; j < array.length; j++) {
			if (!array[j].equals(""))
			listofnodes.add(array[j]);
//			System.out.println(array[j]);
		}
//		System.out.println(attributelist.size());
//		System.out.println(attributes);
		
//		for (String string : listofnodes) 
//		{
//			System.out.println(string);
//			
//		}
		return listofnodes;

	}
}
