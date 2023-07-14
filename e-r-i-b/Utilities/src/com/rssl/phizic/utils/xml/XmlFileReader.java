package com.rssl.phizic.utils.xml;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Kidyaev
 * @ created 05.05.2006
 * @ $Author$
 * @ $Revision$
 */
public class XmlFileReader
{
	private File file;

	public XmlFileReader(File file)
	{
		this.file = file;
	}

	/**
	 * Прочитать xml-файл в строку
	 *
	 * @return
	 * @throws java.io.IOException
	 * @throws org.xml.sax.SAXException
	 */
	public String readString() throws IOException, SAXException
	{
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		String result;

		try
		{
			readDocument();

			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			StringWriter stringWriter = new StringWriter();

			while (bufferedReader.ready())
			{
				stringWriter.write(bufferedReader.read());
			}

			StringBuffer buffer = stringWriter.getBuffer();
			result = buffer.toString();
		}
		finally
		{
			if (fileReader != null)
			{
				fileReader.close();
			}

			if (bufferedReader != null)
			{
				bufferedReader.close();
			}
		}

		return result;
	}

	/**
	 * Прочитать xml-файл в w3c.document
	 * @return
	 * @throws IOException
	 * @throws SAXException
	 */
	public Document readDocument() throws IOException, SAXException
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;

		try
		{
			documentBuilder = factory.newDocumentBuilder();
		}
		catch (ParserConfigurationException e)
		{
			throw new RuntimeException(e);
		}

		return documentBuilder.parse(file);
	}
}
