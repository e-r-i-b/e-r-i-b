package com.rssl.phizic.gate.confirmation;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.Service;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 10.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * Сервис работы с информации о подтверждении операций клиентом.
 */

public interface ConfirmationInfoService extends Service
{
	/**
	 * Получение информации о подтверждении операций клиентом.
	 * @param firstName - имя клиента
	 * @param lastName - фамилия клиента
	 * @param middleName - отчество клиента
	 * @param docNumber - номер документа клиента
	 * @param birthDate - дата рождения клиента
	 * @param tb - ТБ клиента
	 * @return информации о подтверждении
	 * @throws GateLogicException, GateException
	 */
	@SuppressWarnings({"MethodWithTooManyParameters"})
	public ConfirmationInfo getConfirmationInfo(String firstName, String lastName, String middleName, String docNumber, Calendar birthDate, String tb)
			throws GateException, GateLogicException;
}
