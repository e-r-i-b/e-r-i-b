package com.rssl.phizic.gate.dictionaries;

import java.util.List;
import java.util.Set;

/**
 * –езультат загрузки справочника
 * @author Pankin
 * @ created 26.05.2011
 * @ $Author$
 * @ $Revision$
 */

public interface SynchronizeResult
{
	/**
	 * @return список общих сообщений дл€ данного справочника
	 */
	public Set<String> getMessages();

	/**
	 * ƒобавить общее сообщение (не прив€занное к конкретной записи)
	 * @param message текст сообщени€
	 */
	public void addMessage(String message);

	/**
	 * @return список общих ошибок загрузки дл€ данного справочника
	 */
	public List<String> getErrors();

	/**
	 * ƒобавить общую ошибку (не прив€занную к конкретной записи)
	 * @param error текст ошибки
	 */
	public void addError(String error);

	/**
	 * @return список результатов дл€ всех записей
	 */
	public List<SynchronizeResultRecord> getResultRecords();

	/**
	 * @return тип справочника
	 */
	public DictionaryType getDictionaryType();
}
