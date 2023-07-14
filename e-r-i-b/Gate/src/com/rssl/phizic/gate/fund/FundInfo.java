package com.rssl.phizic.gate.fund;

import com.rssl.phizic.gate.clients.GUID;

/**
 * @author osminin
 * @ created 07.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Информация о сборе средств
 */
public interface FundInfo
{
	/**
	 * @return запрос на сборк средств
	 */
	Request getRequest();

	/**
	 * @return ФИО ДУЛ ДР ТБ инициатора
	 */
	GUID getInitiatorGuid();

	/**
	 * @return ФИО ДУЛ ДР ТБ отправителя
	 */
	GUID getSenderGuid();

	/**
	 * @return внешний идентификатор ответа в блоке инициатора
	 */
	String getExternalResponseId();

	/**
	 * @return телефоны иницатора через разделитель
	 */
	String getInitiatorPhones();
}
