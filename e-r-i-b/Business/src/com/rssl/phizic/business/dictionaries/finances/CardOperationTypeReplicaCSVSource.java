package com.rssl.phizic.business.dictionaries.finances;

import com.csvreader.CsvReader;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.CsvReplicaSource;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import sun.nio.cs.StandardCharsets;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Для загрузки справочника типов операции из CSV-файла в базу
 * ---------------------------------------------
 * Структура данных в CSV-файле
 * 1 колонка - Код/тип операции
 * 2 колонка - тип операции: наличная (C) / безналичная (N)
 * ---------------------------------------------
 * @author Gololobov
 * @ created 02.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class CardOperationTypeReplicaCSVSource implements CsvReplicaSource, RecordConstructor
{
	//ридер для работы с csv-файлами
    private CsvReader reader;
	//Файл справочника категорий
	private static final String DEFAUL_FILE_NAME = "opertype_dictionaries.csv";
	//Разделитель
	private static final char DELIMITER = ';';

	public void initialize(GateFactory factory) throws GateException
	{
		InputStream readerStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(DEFAUL_FILE_NAME);
		if (readerStream == null)
			throw new GateException("Не обнаружен файл ресурса: "+DEFAUL_FILE_NAME);
		this.reader = new CsvReader(readerStream,DELIMITER,new StandardCharsets().charsetForName("windows-1251"));
	}

	/**
	 * Метод возвращает итератор на данные из CSV-файла
	 * @return Iterator - возвращает итератор на данные из CSV-файла
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public Iterator iterator() throws GateException, GateLogicException
	{
		try
		{
			return new CsvRecordIterator(this, reader);
		}
		catch (IOException e)
		{
			throw new RuntimeException("Ошибка ввода/вывода. Файл ресурса был закрыт другиим потоком.",e);
		}
	}

	/**
	 * Метод создает новый объект и заполняет его данными из CSV-файла
	 * @param csvRecordIterator - итератор по CSV-ридеру
	 * @return DictionaryRecordBase - объект тип операции "CardOperationType"
	 * @throws IOException
	 */
	public DictionaryRecordBase construct(CsvRecordIterator csvRecordIterator) throws IOException
	{
		try
		{
			CardOperationType cardOperationType = new CardOperationType();
			//Код/тип операции
			cardOperationType.setCode(Long.parseLong(csvRecordIterator.getColumByIndex(0)));
			//тип операции: наличная (C) / безналичная (N)
			cardOperationType.setCash(csvRecordIterator.getColumByIndex(1).compareToIgnoreCase("C") == 0);
			return cardOperationType;
		}
		catch (NumberFormatException e)
		{
			throw new RuntimeException("Неверный формат данных. В строке " + csvRecordIterator.getCurentRecordNum(), e);
		}
	}

	/**
	 * Освобождает CSV-ресурс
	 */
	public void close()
	{
		if (this.reader != null)
			this.reader.close();
	}

	public void setReader(CsvReader reader) throws GateException
	{
		this.reader = reader;
	}
}
