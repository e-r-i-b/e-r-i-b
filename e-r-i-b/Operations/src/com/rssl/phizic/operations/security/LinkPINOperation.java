package com.rssl.phizic.operations.security;

import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.auth.pin.PINEnvelope;
import com.rssl.phizic.auth.pin.PINService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.MultiInstancePersonService;
import com.rssl.phizic.business.operations.Transactional;

import com.rssl.phizic.operations.person.PersonOperationBase;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.SecurityLogicException;

/**
 * Операция привязки ПИН пользователю
 * Не работает с несколькими БД, считаем что пин-конверты ведуться только в основной БД.
 * @author Roshka
 * @ created 21.08.2006
 * @ $Author$
 * @ $Revision$
 */

public class LinkPINOperation extends PersonOperationBase
{
	private static final PINService pinService = new PINService();
	protected static final MultiInstancePersonService personService = new MultiInstancePersonService();

	private String pin;

	public String getPin()
	{
		return pin;
	}

	public void setPin(String pin)
	{
		this.pin = pin;
	}

	/**
	 * Проверка, что пин уже привязан.
	 */
	public boolean isPINLinked()
	{
		return pin.equals(getPerson().getLogin().getUserId());
	}

	/**
	 * Привязка ПИН к пользователю
	 * @throws BusinessException
	 * @throws SecurityLogicException Неверный статус PIN-конверта
	 */
	@Transactional
	public void link() throws BusinessException, SecurityLogicException
	{
		final PINEnvelope envelope = pinService.findEnvelope(pin);

		SecurityService securityService = new SecurityService();
		try
		{
			securityService.linkPinEvenlope(getPerson().getLogin(), envelope,  getInstanceName());
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить текущий pin конверт.
	 */
	@Transactional
	public PINEnvelope getCurrentLinkedPINEnvelope()
	{
		return pinService.findEnvelope(getPerson().getLogin().getUserId());
	}

}