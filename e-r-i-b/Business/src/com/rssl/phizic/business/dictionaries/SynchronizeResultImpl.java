package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.gate.dictionaries.DictionaryType;
import com.rssl.phizic.gate.dictionaries.SynchronizeResult;
import com.rssl.phizic.gate.dictionaries.SynchronizeResultRecord;

import java.util.*;

/**
 * @author Pankin
 * @ created 26.05.2011
 * @ $Author$
 * @ $Revision$
 */

public class SynchronizeResultImpl implements SynchronizeResult
{
	private Set<String> messages;
	private List<String> errors;
	private List<SynchronizeResultRecord> recordList;
	private DictionaryType dictionaryType;

	/**
	 * конструктор
	 */
	public SynchronizeResultImpl()
	{
		messages = new HashSet<String>();
		errors = new ArrayList<String>();
		recordList = new ArrayList<SynchronizeResultRecord>();
	}

	public Set<String> getMessages()
	{
		return Collections.unmodifiableSet(messages);
	}

	public void addMessage(String message)
	{
		messages.add(message);
	}

	public List<String> getErrors()
	{
		return Collections.unmodifiableList(errors);
	}

	public void addError(String error)
	{
		errors.add(error);
	}

	public List<SynchronizeResultRecord> getResultRecords()
	{
		return Collections.unmodifiableList(recordList);
	}

	/**
	 * Добавить результат загрузки для записи
	 * @param resultRecord результат
	 */
	public void addResultRecord(SynchronizeResultRecord resultRecord)
	{
		recordList.add(resultRecord);
	}

	public DictionaryType getDictionaryType()
	{
		return dictionaryType;
	}

	/**
	 * Установить тип загружаемого справочника
	 * @param dictionaryType тип справочника
	 */
	public void setDictionaryType(DictionaryType dictionaryType)
	{
		this.dictionaryType = dictionaryType;
	}
}
