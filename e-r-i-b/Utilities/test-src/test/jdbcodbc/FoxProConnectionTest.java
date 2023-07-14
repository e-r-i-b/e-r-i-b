package test.jdbcodbc;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.rssl.phizic.utils.resources.ResourceHelper;
import junit.framework.TestCase;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Roshka
 * @ created 08.11.2006
 * @ $Author$
 * @ $Revision$
 */

public class FoxProConnectionTest extends TestCase
{
	public void testJavaBbf() throws ClassNotFoundException
	{
		try
		{
			// create a DBFReader object
			//
			InputStream inputStream = ResourceHelper.loadResourceAsStream("banks.dbf");
			DBFReader reader = new DBFReader(inputStream);
			reader.setCharactersetName("CP866");

			// get the field count if you want for some reasons like the following
			//
			int numberOfFields = reader.getFieldCount();

			// use this count to fetch all field information
			// if required
			//
			for (int i = 0; i < numberOfFields; i++)
			{
				DBFField field = reader.getField(i);

				// do something with it if you want
				// refer the JavaDoc API reference for more details
				//
				System.out.println(field.getName());
			}

			// Now, lets us start reading the rows
			//
			Object[] rowObjects;

			while ((rowObjects = reader.nextRecord()) != null)
			{
				for (int i = 0; i < rowObjects.length; i++)
				{
					System.out.println(reader.getField(i).getName() + "   " + rowObjects[i]);
				}
			}

			// By now, we have itereated through all of the rows
			inputStream.close();
		}

		catch (DBFException e)
		{
			System.out.println(e.getMessage());
		}

		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
	}
}