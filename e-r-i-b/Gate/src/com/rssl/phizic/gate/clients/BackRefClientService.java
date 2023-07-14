package com.rssl.phizic.gate.clients;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 04.02.2010
 * @ $Author$
 * @ $Revision$
 */
public interface BackRefClientService extends Service
{
	/**
	 * Поиск клиента по id логина
	 * @param loginId - id логина
	 * @return клиент
	 */
	Client getClientById(Long loginId) throws GateLogicException, GateException;

	/**
	 * Получение клиента по ФИО, документу и дате рождения
	 * @param firstName - имя клиента
	 * @param lastName - фамилия клиента
	 * @param middleName - отчество клиента
	 * @param docSeries - серия документа клиента
	 * @param docNumber - номер документа клиента
	 * @param birthDate - дата рождения клиента
	 * @param tb - ТБ клиента
	 * @return Зарегистрированного у нас клиента
	 * @throws GateLogicException, GateException
	 */
	@SuppressWarnings("MethodWithTooManyParameters")
	Client getClientByFIOAndDoc(String firstName, String lastName, String middleName, String docSeries, String docNumber, Calendar birthDate, String tb) throws GateLogicException, GateException;

	/**
	 * Получить код подразделения, к которому привязана карта по которой пользователь вошел в систему
	 * @param loginId id логина клиента
	 * @return  строку вида  CB_CODE|ТБ карты последнего входа|ОСБ карты последнего входа|ВСП карты последнего входа
	 * где CB_CODE -  подразделения карты, по которой вошел клиент
	 */
	//todo запрос BUG025769
	//@Cachable(keyResolver = LongCacheKeyComposer.class, linkable = false)
	String getClientDepartmentCode(Long loginId) throws GateLogicException, GateException;

	/**
	 * Поиск типа клиента по id клиента
	 * @param clientId - id клиента
	 * @return тип клиента
	 * */
	String getClientCreationType(String clientId) throws GateLogicException, GateException;
}
