package com.rssl.auth.csa.wsclient.exceptions;

/**
 * ���������� ��� ���������� ����������� � ��������� �����
 * @author niculichev
 * @ created 23.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class MobileBankRegistrationNotFoundException extends BackLogicException
{
	public MobileBankRegistrationNotFoundException()
    {
	    super();
    }

	public MobileBankRegistrationNotFoundException(String message)
	{
		super(message);
	}
}
