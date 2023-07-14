package com.rssl.phizic.business.dictionaries.loanclaim;

/**
 * @author Nady
 * @ created 25.05.2014
 * @ $Author$
 * @ $Revision$
 */

import com.csvreader.CsvReader;
import com.rssl.phizic.gate.loanclaim.dictionary.MultiWordDictionaryEntry;
import com.rssl.phizic.utils.StringHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Базовый класс для ридеров справочников адрессной информации
 */
public abstract class AbstractAddressDictionaryReader
{
	private static final String DEFAULT_CODE = "0000";
	private static final int MIN_POSTFIX_LENGTH = 3;    //@see liveSearchInput.js#minChars

	protected final CsvReader csvReader;
	protected boolean hasNext;
	private final String wordSeparators;

	protected AbstractAddressDictionaryReader(CsvReader csvReader, String wordSeparators) throws IOException
	{
		this.csvReader = csvReader;
		this.csvReader.readHeaders();
		this.hasNext = csvReader.readRecord();
		this.wordSeparators = wordSeparators;
	}

	/**
	 * Имеется ли следующая строка в файле
	 * @return
	 */
	public boolean hasNext()
	{
		return hasNext;
	}

	/**
	 * Метод для генерации кода справочника
	 * @param region - код региона
	 * @param area   - код района
	 * @param city   - код города
	 * @param settlement - код населенного пункта
	 * @param street     - код улицы
	 * @return - код справочника в общем формате
	 */
	protected String buildCodeDictionary(String region, String area, String city, String settlement, String street)
	{
		return  (region != null ? StringHelper.addLeadingZeros(region, 4): DEFAULT_CODE) +
				(area!=null ? StringHelper.addLeadingZeros(area, 4) : DEFAULT_CODE) +
				(city!=null ? StringHelper.addLeadingZeros(city, 4) : DEFAULT_CODE) +
				(settlement!=null ? StringHelper.addLeadingZeros(settlement, 4) : DEFAULT_CODE)+
				(street!=null ? StringHelper.addLeadingZeros(street, 4) : DEFAULT_CODE);
	}

	/**
	 * Читает запись из справочника и собирает из нее несколько сущностей для БД
	 * @see MultiWordDictionaryEntry#searchPostfix
	 * @return запись справочника, разбитая на постфиксы для поиска
	 */
	public List<MultiWordDictionaryEntry> readRecordWithPostfixes() throws IOException
	{
		MultiWordDictionaryEntry record = readRecord();

		List<String> postfixes = StringHelper.getPostfixes(record.getName(), wordSeparators, MIN_POSTFIX_LENGTH);
		List<MultiWordDictionaryEntry> result = new ArrayList<MultiWordDictionaryEntry>(postfixes.size() + 1);
		
		//само слово
		record.setSearchPostfix(record.getName().toLowerCase());
		result.add(record);
		//его постфиксы
		for (String postfix : postfixes)
		{
			MultiWordDictionaryEntry entry = (MultiWordDictionaryEntry) record.clone();
			entry.setSearchPostfix(postfix.toLowerCase());
			result.add(entry);
		}

		return result;
	}

	/**
	 * Прочитать запись
	 * @return сущность справочника
	 */
	protected abstract MultiWordDictionaryEntry readRecord() throws IOException;
}
