package com.rssl.phizic.utils.csv;

import com.csvreader.CsvReader;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Итератор для работы с файлами CSV формата. Итератор бежит по записям CSV-файла
 * @author Gololobov
 * @ created 04.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class CsvIterator implements Iterator<String>
{
	private CsvReader csvReader;

	private boolean hasNextRecord = false;

	/**
	 * Конструктор итератора по csv-файлу 
	 * @param reader - CsvReader
	 * @throws IllegalArgumentException
	 */
	public CsvIterator (CsvReader reader) throws IllegalArgumentException, IOException
	{
		if (reader == null) {
			throw new IllegalArgumentException("Не указан csv-ресурс.");
		}
		this.csvReader = reader;

		synchronized (this.csvReader)
		{
			if (this.csvReader.getCurrentRecord() < 0 )
				hasNextRecord = this.csvReader.readRecord();
		}
	}

	public CsvReader getCsvReader()
	{
		return csvReader;
	}

	/**
	 * Метод пытается прочесть следующую запись. При уcпехе помещает её в CsvReader.rawRecord
	 * @return - true если удалось прочесть следующую запись
	 * @throws RuntimeException
	 */
	public boolean hasNext()
	{
		return hasNextRecord;
	}

	/**
	 * Метод возвращает строкой следующую запись из ресурса. Если нет, то пустую строку.
	 * @return String - следующая запись
	 */
	public String next() throws NoSuchElementException
	{
		String curRecord = null;
		try
		{
			curRecord = this.csvReader.getRawRecord();
			hasNextRecord = this.csvReader.readRecord();
		}
		catch (IOException e)
		{
			NoSuchElementException ne = new NoSuchElementException("Не удалось получить следующую запись, так как файл-ресурс закрыт.");
			ne.initCause(e);
			throw ne;
		}
		return curRecord;
	}

	/**
	 * Метод удаления последнего елемента ресурса. Ничего не удалять из ресурса. 
	 * @throws UnsupportedOperationException
	 */
	public void remove() throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}	
}
