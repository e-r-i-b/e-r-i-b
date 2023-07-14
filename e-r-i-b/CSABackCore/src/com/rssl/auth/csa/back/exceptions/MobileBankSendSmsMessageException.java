package com.rssl.auth.csa.back.exceptions;

import com.rssl.phizic.common.types.exceptions.LogicException;

import java.util.Collection;
import java.util.Collections;

/**
 * ���������� ��� �������� ���
 * @author niculichev
 * @ created 02.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class MobileBankSendSmsMessageException extends LogicException
{
	private Collection<String> errorPhones = Collections.emptySet();

	public MobileBankSendSmsMessageException(String message)
	{
		super(message);
	}

	public MobileBankSendSmsMessageException(String message, Collection<String> errorPhones)
	{
		super(message);
		this.errorPhones = errorPhones;
	}

	/**
	 * @return ������ ������� ��������� �� ������� �� ������� ��������� ���-���������
	 */
	public Collection<String> getErrorPhones()
	{
		return errorPhones;
	}
}
