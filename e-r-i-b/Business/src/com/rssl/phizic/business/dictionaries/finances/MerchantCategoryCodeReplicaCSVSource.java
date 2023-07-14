package com.rssl.phizic.business.dictionaries.finances;

import com.csvreader.CsvReader;
import com.rssl.phizic.business.locale.MultiLocaleQueryHelper;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.BeanQuery;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.CsvReplicaSource;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import sun.nio.cs.StandardCharsets;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Для загрузки справочника MCC-кодов из CSV-файла в базу
 * ---------------------------------------------
 * Структура данных в CSV-файле
 * 1 колонка - MCC код
 * 2 колонка - Внешний ID категории
 * ---------------------------------------------
 * @author Gololobov
 * @ created 02.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class MerchantCategoryCodeReplicaCSVSource implements CsvReplicaSource, RecordConstructor
{
	//ридер для работы с csv-файлами
    private CsvReader reader;
	//Категории операции загруженные ранее с owner is null
	private Map operationCategoriesWithOwnerNull = null;
	//Файл справочника категорий
	private static final String DEFAUL_FILE_NAME = "mcccode_dictionaries.csv";
	//Разделитель
	private static final char DELIMITER = ';';

	public void initialize(GateFactory factory) throws GateException
	{
		//Key - CardOperationCategory.externalId, Value - CardOperationCategory
		this.operationCategoriesWithOwnerNull = loadPublicCategories();
	}

	/**
	 *
	 * @param reader - CSV-ридер
	 */
	public void setReader(CsvReader reader)
	{
		this.reader = reader;
	}

	private CsvReader getDefaultReader() throws GateException
	{
		InputStream readerStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(DEFAUL_FILE_NAME);
		if (readerStream == null)
		{
			throw new GateException("Не обнаружен файл ресурса: "+DEFAUL_FILE_NAME);
		}

		return new CsvReader(readerStream,DELIMITER,new StandardCharsets().charsetForName("windows-1251"));
	}

	/**
	 * Метод возвращает итератор на данные из CSV-файла
	 * @return Iterator - возвращает итератор на данные из CSV-файла
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public Iterator iterator() throws GateException, GateLogicException
	{
		if (reader == null)
		{
			reader = getDefaultReader();
		}

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
	 * @return DictionaryRecordBase - объект категория операции "CardOperationCategory"
	 * @throws IOException
	 */
	public DictionaryRecordBase construct(CsvRecordIterator csvRecordIterator) throws IOException
	{
		try
		{
			MerchantCategoryCode merchantCategoryCode = new MerchantCategoryCode();
			//MCC код
			merchantCategoryCode.setCode(Long.parseLong(csvRecordIterator.getColumByIndex(0)));
			//Поиск категории по externalId категории из справочника категорий
			CardOperationCategory operationCategory1 = (CardOperationCategory)this.operationCategoriesWithOwnerNull.get(csvRecordIterator.getColumByIndex(1));
			CardOperationCategory operationCategory2 = (CardOperationCategory)this.operationCategoriesWithOwnerNull.get(csvRecordIterator.getColumByIndex(2));
			if (operationCategory1 != null)
				merchantCategoryCode.addOperationCategory(operationCategory1);
			if (operationCategory2 != null)
				merchantCategoryCode.addOperationCategory(operationCategory2);
			return merchantCategoryCode;
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

	/**
	 * Загрузка всех категорий операции которые были залиты при помощи "_Load3_Dictionaries"
	 * @throws GateException
	 */
	private Map<String,CardOperationCategory> loadPublicCategories() throws GateException
	{
		List<CardOperationCategory> list = null;
		try
		{

			BeanQuery query = MultiLocaleQueryHelper.getQuery("com.rssl.phizic.business.dictionaries.finances.getOperationCategoriesWithOwnerNull");
			list =  query.executeListInternal();

		}
		catch (Exception e)
		{
			throw new GateException(e);
		}

		//Map с категориями операций key- externalId операции, value- сама категория
		Map<String,CardOperationCategory> categoriesWithOwnerNull = new HashMap<String,CardOperationCategory>();
		if (!CollectionUtils.isEmpty(list))
		{
			for (CardOperationCategory cardOperationCategory : list)
			{
				categoriesWithOwnerNull.put(cardOperationCategory.getExternalId(), cardOperationCategory);
			}
		}
		
		return categoriesWithOwnerNull;
	}
}
