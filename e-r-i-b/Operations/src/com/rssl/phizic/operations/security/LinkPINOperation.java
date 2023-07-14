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
 * �������� �������� ��� ������������
 * �� �������� � ����������� ��, ������� ��� ���-�������� �������� ������ � �������� ��.
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
	 * ��������, ��� ��� ��� ��������.
	 */
	public boolean isPINLinked()
	{
		return pin.equals(getPerson().getLogin().getUserId());
	}

	/**
	 * �������� ��� � ������������
	 * @throws BusinessException
	 * @throws SecurityLogicException �������� ������ PIN-��������
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
	 * �������� ������� pin �������.
	 */
	@Transactional
	public PINEnvelope getCurrentLinkedPINEnvelope()
	{
		return pinService.findEnvelope(getPerson().getLogin().getUserId());
	}

}