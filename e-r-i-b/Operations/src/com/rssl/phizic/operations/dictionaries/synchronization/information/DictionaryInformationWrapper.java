package com.rssl.phizic.operations.dictionaries.synchronization.information;

import com.rssl.phizic.business.dictionaries.synchronization.information.DictionaryInformation;
import com.rssl.phizic.business.dictionaries.synchronization.information.DictionaryState;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 04.02.2014
 * @ $Author$
 * @ $Revision$
 *
 * информация о состоянии справочников
 */

public class DictionaryInformationWrapper
{
	private String nodeName;
	private Calendar lastUpdateDate;
	private DictionaryState state;
	private String errorDetail;

	/**
	 * конструктор
	 * @param nodeName название блока
	 */
	public DictionaryInformationWrapper(String nodeName)
	{
		this.nodeName = nodeName;
	}

	/**
	 * конструктор
	 * @param nodeName название блока
	 * @param information информация о состоянии справочников
	 */
	public DictionaryInformationWrapper(String nodeName, DictionaryInformation information)
	{
		this(nodeName);
		this.lastUpdateDate = information.getLastUpdateDate();
		this.state = information.getState();
		this.errorDetail = information.getErrorDetail();
	}

	/**
	 * @return название блока
	 */
	public String getNodeName()
	{
		return nodeName;
	}

	/**
	 * @return дата последнего обновления
	 */
	public Calendar getLastUpdateDate()
	{
		return lastUpdateDate;
	}

	/**
	 * @return состояние справочников
	 */
	public DictionaryState getState()
	{
		return state;
	}

	/**
	 * @return описание ошибки
	 */
	public String getErrorDetail()
	{
		return errorDetail;
	}
}
