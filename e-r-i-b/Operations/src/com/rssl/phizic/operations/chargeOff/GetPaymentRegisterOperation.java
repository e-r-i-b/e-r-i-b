package com.rssl.phizic.operations.chargeOff;

import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.person.PersonOperationBase;
import com.rssl.phizic.operations.access.PersonLoginSource;
import com.rssl.phizic.business.chargeoff.ChargeOffLogService;
import com.rssl.phizic.business.chargeoff.ChargeOffLog;
import com.rssl.phizic.business.chargeoff.ChargeOffState;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.business.operations.restrictions.Restriction;

import java.util.List;
import java.util.Calendar;

/**
 * @author: Pakhomova
 * @created: 05.03.2009
 * @ $Author$
 * @ $Revision$
 * операция для просмотра журнала оплаты
 */
public class GetPaymentRegisterOperation<R extends UserRestriction> extends OperationBase<R>
{
	private static ChargeOffLogService chargeOffLogService = new ChargeOffLogService();
	private static PersonService personService = new PersonService();

	private ActivePerson person;

	
	public void initialize(long personId)  throws BusinessException
	{
		//для проверки доступа
		PersonLoginSource seoure = new PersonLoginSource(personId);
		ActivePerson temp = seoure.getPerson();
		PersonOperationBase.checkRestriction(getRestriction(), temp);

		person = (ActivePerson) personService.findById(personId);
	}

	/**
	 *
	 * @return  текущий режим пользователя (shadow или основная база)
	 * @throws BusinessException
	 */
	public String getPersonInstanceName() throws BusinessException
	{
		return personService.getPersonInstanceName(person.getId());
	}

	/**
	 *
	 * @return  клиент
	 */
	public ActivePerson getPerson()
	{
		return person;
	}

	/**
	 *
	 * @param operationDate - дата совершения операции. Для долгов дата последней попытки
	 * @param state  статус платежа
	 * @param listLimit  ограничение на количество записей
	 * @return  список записей об оплате
	 * @throws BusinessException
	 */
	public List<ChargeOffLog> getChargeOffLogs(Calendar operationDate, ChargeOffState state, int listLimit) throws BusinessException
	{
		return chargeOffLogService.getChargeOffLogs(person.getLogin(), operationDate, state, null, listLimit);
	}

}
