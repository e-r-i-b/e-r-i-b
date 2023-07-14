package com.rssl.phizic.gate.clients;

import com.rssl.phizic.auth.modes.UserRegistrationMode;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.Calendar;

/**
 * Сервис для получения дополнительной информации о клиентах
 * @author basharin
 * @ created 20.11.14
 * @ $Author$
 * @ $Revision$
 */

public interface AdditionalClientInfoService extends Service
{
	/**
	 * Получение информации о индивидуальном режиме самостоятельной регистрации .
	 * @param firstName - имя клиента
	 * @param lastName - фамилия клиента
	 * @param middleName - отчество клиента
	 * @param docNumber - номер документа клиента
	 * @param birthDate - дата рождения клиента
	 * @param tb - ТБ клиента
	 * @return индивидуальном режиме самостоятельной регистрации
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException, GateException
	 */
	@SuppressWarnings({"MethodWithTooManyParameters"})
	public UserRegistrationMode getUserRegistrationMode(String firstName, String lastName, String middleName, String docNumber, Calendar birthDate, String tb)
			throws GateException, GateLogicException;
}
