package com.rssl.phizic.utils.csv;

import com.csvreader.CsvReader;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * �������� ��� ������ � ������� CSV �������. �������� ����� �� ������� CSV-�����
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
	 * ����������� ��������� �� csv-����� 
	 * @param reader - CsvReader
	 * @throws IllegalArgumentException
	 */
	public CsvIterator (CsvReader reader) throws IllegalArgumentException, IOException
	{
		if (reader == null) {
			throw new IllegalArgumentException("�� ������ csv-������.");
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
	 * ����� �������� �������� ��������� ������. ��� �c���� �������� � � CsvReader.rawRecord
	 * @return - true ���� ������� �������� ��������� ������
	 * @throws RuntimeException
	 */
	public boolean hasNext()
	{
		return hasNextRecord;
	}

	/**
	 * ����� ���������� ������� ��������� ������ �� �������. ���� ���, �� ������ ������.
	 * @return String - ��������� ������
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
			NoSuchElementException ne = new NoSuchElementException("�� ������� �������� ��������� ������, ��� ��� ����-������ ������.");
			ne.initCause(e);
			throw ne;
		}
		return curRecord;
	}

	/**
	 * ����� �������� ���������� �������� �������. ������ �� ������� �� �������. 
	 * @throws UnsupportedOperationException
	 */
	public void remove() throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}	
}
