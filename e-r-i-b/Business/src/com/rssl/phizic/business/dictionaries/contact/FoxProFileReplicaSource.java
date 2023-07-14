package com.rssl.phizic.business.dictionaries.contact;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFReader;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.ReplicaSource;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author Roshka
 * @ created 28.11.2006
 * @ $Author$
 * @ $Revision$
 */

public abstract class FoxProFileReplicaSource implements ReplicaSource
{

	protected Long convertStringToLongValue(Object o) throws GateException
	{
		try
		{
			return Long.parseLong(o.toString());
		}
		catch (NumberFormatException e)
		{
			throw new GateException(e);
		}
	}

	protected Long convertDoubleToLongValue(Object o) throws GateException
	{
		try
		{
			return ((Double) o).longValue();
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	protected <DR extends DictionaryRecord> Iterator<DR> dbfIterator(String dbfFileName, Comparator sortComparator)
			throws GateException
	{
		InputStream inputStream = null;
		List<ContactMember> list = null;

		try
		{
			inputStream = new FileInputStream(dbfFileName);

			DBFReader reader = new DBFReader(inputStream);
			reader.setCharactersetName("CP866");

			list = readList(reader);
		}
		catch (DBFException e)
		{
			throw new GateException(e);
		}
		catch (FileNotFoundException e)
		{
			throw new GateException("Не найден файл " + dbfFileName, e);
		}
		finally
		{
			try
			{
				if (inputStream != null)
					inputStream.close();
			}
			catch (IOException e)
			{
				inputStream = null;
				throw new GateException(e);
			}
		}
		if (!list.isEmpty())
		{
			Collections.sort(list, sortComparator);
		}

		return (Iterator<DR>) list.iterator();
	}

	private <DR extends DictionaryRecord> List<DR> readList(DBFReader reader)
			throws GateException, DBFException
	{
		List<DR> list = new ArrayList<DR>();
		Object[] rowObjects;

		while ((rowObjects = reader.nextRecord()) != null)
		{
			Map<String, Object> row = new HashMap<String, Object>();

			for (int i = 0; i < rowObjects.length; i++)
				row.put(reader.getField(i).getName(), rowObjects[i]);

			DictionaryRecord dr = map(row);

			validate(dr);

			list.add((DR) dr);
		}
		return list;
	}

	/**
	 * Валидация прочитаной записи.
	 * @param dr прочитанный объект
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	protected abstract void validate(DictionaryRecord dr) throws GateException;

	/**
	 * Сздание записи пользовательского типа
	 * @param row map - [имя поля : значение]
	 * @throws DBFException
	 * @throws GateException
	 */
	protected abstract DictionaryRecord map(Map row) throws DBFException, GateException;

	public void close()
	{
	}
}