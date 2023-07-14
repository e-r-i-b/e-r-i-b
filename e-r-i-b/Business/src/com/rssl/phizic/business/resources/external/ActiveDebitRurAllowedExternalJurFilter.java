package com.rssl.phizic.business.resources.external;

/**
 * @author osminin
 * @ created 07.02.2013
 * @ $Author$
 * @ $Revision$
 *
 * ������ ��������� �������� ��������� �������� ����������� ��� �������� ��. ���� �����.
 */
public class ActiveDebitRurAllowedExternalJurFilter extends ActiveDebitRurAccountFilter
{
	private AllowedExternalJurAccountFilter filter = new AllowedExternalJurAccountFilter();

	public boolean accept(AccountLink accountLink)
	{
		return super.accept(accountLink) && filter.accept(accountLink); 
	}
}
