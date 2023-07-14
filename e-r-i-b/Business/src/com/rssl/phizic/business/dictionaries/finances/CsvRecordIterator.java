package com.rssl.phizic.business.dictionaries.finances;

import com.csvreader.CsvReader;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;
import com.rssl.phizic.utils.csv.CsvIterator;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Класс для итерации по записям справочника
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
	 * Итератор по CSV-файлу
	 */
	private final CsvIterator csvIterator;
	/**
	 * Конструктор
	 * @param recordConstructor - конструктор записи справочника
	 * @param csvReader - ридер по CSV-файлу
	 */
	CsvRecordIterator(RecordConstructor<T> recordConstructor, CsvReader csvReader) throws IOException
	{
		this.recordConstructor = recordConstructor;
		this.csvReader = csvReader;
		this.csvIterator = new CsvIterator(csvReader);
	}

	/**
	 * Метод определяет есть ли следующая запись относительно текущей в CSV-файле
	 * @return - true, если есть следующая запись
	 * @throws RuntimeException - если файл закрыт
	 */
	public boolean hasNext() throws RuntimeException
	{
		return csvIterator.hasNext();
	}

	/**
	 * Метод вовращает данные для текущей записи из колонки под номером "columnIndex"
	 * (нумерация начинается с "0") 
	 * @param columnIndex
	 * @return String - данные для текущей записи из "columnIndex" колонки
	 * @throws java.io.IOException - если файл с данными закрыт
	 */
	protected String getColumByIndex(int columnIndex) throws IOException
	{
		return csvIterator.getCsvReader().get(columnIndex).trim();
	}

	/**
	 * Метод возвращает номер текущей записи
	 * @return - long номер текущей записи
	 */
	protected long getCurentRecordNum()
	{
		return csvIterator.getCsvReader().getCurrentRecord();
	}

	/**
	 * Метод возвращает следующую запись
	 * @return - заполненный данными объект 
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
		//сдвигаем фокус на следующую запись в CSV-ридере 
		csvIterator.next();
		return dictionaryRecord;
	}

	/**
	 * Метод удаления последнего елемента ресурса. 
	 * @throws UnsupportedOperationException
	 */
	public void remove() throws UnsupportedOperationException
	{
		csvIterator.remove();
	}
}
