package com.rssl.phizic.gate.clients;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import org.w3c.dom.Document;

import java.util.Calendar;

/**
 * @author Roshka
 * @ created 08.09.2006
 * @ $Author$
 * @ $Revision$
 */


public interface RegistartionClientService extends Service
{
	/**
	 * Зарегистрировать клиента.
	 * @param office
	 * @param registerRequest
	 * @throws GateLogicException
	 * @throws GateException
	 */
	void register(Office office, Document registerRequest) throws GateLogicException, GateException;

	/**
	 * Обновить клиента во внешней системе.
	 * @param client - клиент, данные которого отсылаются во внешнюю систему
	 * @param lastUpdateDate - дата последнего обновления клиента
	 * @param isNew - является ли клиент новым. true - новый, false - старый.
	 * @param user - пользователь, который изменяет клиента (client)
	 * @throws GateLogicException
	 * @throws GateException
	 */
	void update(Client client, Calendar lastUpdateDate, boolean isNew, User user) throws GateLogicException, GateException;

	/**
	 * Измененить данные клиента
	 * @param office
	 * @param registerRequest
	 * @throws GateLogicException
	 * @throws GateException
	 */
	void update( Office office, Document registerRequest) throws GateLogicException, GateException;

	 /**
	 * расторжение договора
	 * @param client Клиент
	 * @param trustingClientId Внешний идентификатор клиента, по доверенности которого работал предстваитель.
	  * Если расторгаем с не придставителем, то null
	 * @param date дата расторжения
	 * @param type тип расторжения
	 * @param reason причина расторжения
	 * @return колбек или null если операция однофазная.
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public CancelationCallBack cancellation(Client client, String trustingClientId, Calendar date, CancelationType type, String reason) throws GateLogicException, GateException;

}
