package com.rssl.phizic.business.documents;

/**
 * @author osminin
 * @ created 07.02.2013
 * @ $Author$
 * @ $Revision$
 *
 * ��������� �� ��������, ����������� ���������� ������� �������� ���������� �� ����� ��. ����.
 */
public class AllowedExternalJurAccountPaymentValidator extends AllowedExternalAccountPaymentValidatorBase
{
	public AllowedExternalJurAccountPaymentValidator()
	{
		super();
	}

	protected boolean isExternalEccountPaymentAllowed()
	{
		return DocumentHelper.isExternalJurAccountPaymentsAllowed();
	}
}
