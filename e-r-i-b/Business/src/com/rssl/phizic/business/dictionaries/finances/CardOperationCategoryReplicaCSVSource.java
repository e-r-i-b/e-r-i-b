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
 * Для загрузки справочника категорий из CSV-файла в базу
 * ---------------------------------------------
 * Структура данных в CSV-файле
 * 1 колонка - Внешний ID категории
 * 2 колонка - Пользовательское название категории
 * 3 колонка - Тип операции (доход(I)/расход(O))
 * 4 колонка - Признак, характеризующий какие операции могут быть в этой категории
			   ("C"-наличные операции,"N"-безналичные операции,"B"-оба типа операций)
 * 5 колонка - Флаг допускается ли добавлять в категорию несовместимые с ней операции
               (1-допускается, 0-недопускается)
 * 6 колонка - Признак категории по умолчанию, категория в которую помещаются операции с MCCCode-ом отсутствующим в справочнике
               (D-Дефолтниая категория, всё остальное- не дефолтная категория)
 * ---------------------------------------------
 * @author Gololobov
 * @ created 29.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class CardOperationCategoryReplicaCSVSource  implements CsvReplicaSource, RecordConstructor
{
	//ридер для работы с csv-файлами
    private CsvReader reader;
	//Файл справочника категорий
	private static final String DEFAUL_FILE_NAME = "category_dictionaries.csv";
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
	 * @return DictionaryRecordBase - объект категория операции "CardOperationCategory"
	 * @throws IOException
	 */
	public DictionaryRecordBase construct(CsvRecordIterator csvRecordIterator) throws IOException
	{
		try
		{
			CardOperationCategory cardOperationCategory = new CardOperationCategory();
			//Внешний ID
			cardOperationCategory.setExternalId(csvRecordIterator.getColumByIndex(0));
			//Пользовательское название категории
			cardOperationCategory.setName(csvRecordIterator.getColumByIndex(1));
			//Тип операции (доходная(I)/расходная(O))
			cardOperationCategory.setIncome(csvRecordIterator.getColumByIndex(2).compareToIgnoreCase("I") == 0);
			//Признак, характеризующий какие операции могут быть в этой категории
			// ("C"-наличные операции,"N"-безналичные операции,"B"-оба типа операций)
			String operationType = csvRecordIterator.getColumByIndex(3); 
			boolean cash = operationType.compareToIgnoreCase("C") == 0 ||
					       operationType.compareToIgnoreCase("B") == 0;
			boolean cashless = operationType.compareToIgnoreCase("N") == 0 ||
					           operationType.compareToIgnoreCase("B") == 0;
			//Наличные
			cardOperationCategory.setCash(cash);
			//Безнал
			cardOperationCategory.setCashless(cashless);
			//Флаг 1-допускается добавлять в категорию несовместимые с ней операции, 0-недопускается
			cardOperationCategory.setIncompatibleOperationsAllowed(csvRecordIterator.getColumByIndex(4).compareTo("1") == 0);

			//особенность: D - категория по-умолчанию, T - перевод между своими счетами
			String feature = csvRecordIterator.getColumByIndex(5);
			cardOperationCategory.setIsDefault("D".equalsIgnoreCase(feature));
			cardOperationCategory.setForInternalOperations("T".equalsIgnoreCase(feature));

			return cardOperationCategory;
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
