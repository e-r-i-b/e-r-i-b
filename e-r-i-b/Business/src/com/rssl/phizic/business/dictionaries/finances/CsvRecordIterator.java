package com.rssl.phizic.business.dictionaries.finances;

import com.csvreader.CsvReader;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;
import com.rssl.phizic.utils.csv.CsvIterator;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * ����� ��� �������� �� ������� �����������
 * @author Gololobov
 * @ created 05.08.2011
 * @ $Author$
 * @ $Revision$
 */

class CsvRecordIterator<T extends DictionaryRecordBase> implements Iterator<T>
{
	private final RecordConstructor<T> recordConstructor;
	private final CsvReader csvReader;
	/**
	 * �������� �� CSV-�����
	 */
	private final CsvIterator csvIterator;
	/**
	 * �����������
	 * @param recordConstructor - ����������� ������ �����������
	 * @param csvReader - ����� �� CSV-�����
	 */
	CsvRecordIterator(RecordConstructor<T> recordConstructor, CsvReader csvReader) throws IOException
	{
		this.recordConstructor = recordConstructor;
		this.csvReader = csvReader;
		this.csvIterator = new CsvIterator(csvReader);
	}

	/**
	 * ����� ���������� ���� �� ��������� ������ ������������ ������� � CSV-�����
	 * @return - true, ���� ���� ��������� ������
	 * @throws RuntimeException - ���� ���� ������
	 */
	public boolean hasNext() throws RuntimeException
	{
		return csvIterator.hasNext();
	}

	/**
	 * ����� ��������� ������ ��� ������� ������ �� ������� ��� ������� "columnIndex"
	 * (��������� ���������� � "0") 
	 * @param columnIndex
	 * @return String - ������ ��� ������� ������ �� "columnIndex" �������
	 * @throws java.io.IOException - ���� ���� � ������� ������
	 */
	protected String getColumByIndex(int columnIndex) throws IOException
	{
		return csvIterator.getCsvReader().get(columnIndex).trim();
	}

	/**
	 * ����� ���������� ����� ������� ������
	 * @return - long ����� ������� ������
	 */
	protected long getCurentRecordNum()
	{
		return csvIterator.getCsvReader().getCurrentRecord();
	}

	/**
	 * ����� ���������� ��������� ������
	 * @return - ����������� ������� ������ 
	 */
	public T next() throws NoSuchElementException
	{
		T dictionaryRecord = null;
		try
		{
			dictionaryRecord = recordConstructor.construct(this);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		//�������� ����� �� ��������� ������ � CSV-������ 
		csvIterator.next();
		return dictionaryRecord;
	}

	/**
	 * ����� �������� ���������� �������� �������. 
	 * @throws UnsupportedOperationException
	 */
	public void remove() throws UnsupportedOperationException
	{
		csvIterator.remove();
	}
}
