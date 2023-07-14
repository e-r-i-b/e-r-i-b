package com.rssl.phizic.utils.xml;

import java.io.*;

/**
 * @author Roshka
 * @ created 26.06.2006
 * @ $Author$
 * @ $Revision$
 */

public class TextFileReader
{
	protected File file;

	public TextFileReader(File file)
	{
		this.file = file;
	}

	/**
	 * Прочитать файл в строку
	 * @return
	 * @throws java.io.IOException
	 */
	public String readString() throws IOException
	{
	    FileReader fileReader = null;
	    BufferedReader bufferedReader = null;
	    String result;

	    try
	    {
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
	 * Прочитать файл в строку
	 * @return
	 * @throws java.io.IOException
	 */
//	public String readString() throws IOException
//	{
//		FileInputStream in = null;
//		Reader reader = null;
//		BufferedReader bufferedReader = null;
//		String result;
//
//		try
//		{
//			in = new FileInputStream(file);
//			reader = new InputStreamReader(in, "windows-1251");
//			bufferedReader = new BufferedReader(reader);
//
//			StringWriter stringWriter = new StringWriter();
//			String line;
//
//			while ( (line=bufferedReader.readLine()) != null )
//			{
//				stringWriter.write(line);
//			}
//
//			StringBuffer buffer = stringWriter.getBuffer();
//			result = buffer.toString();
//		}
//		finally
//		{
//			if (reader != null)
//				reader.close();
//
//			if (bufferedReader != null)
//				bufferedReader.close();
//
//			if (in != null)
//				in.close();
//		}
//
//		return result;
//	}
}