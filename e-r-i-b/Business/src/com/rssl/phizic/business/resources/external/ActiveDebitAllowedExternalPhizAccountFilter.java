package com.rssl.phizic.business.resources.external;

/**
 * ������ ��������� �������� ��������� ����������� ��� �������� ����������� ���� �����.
 *
 * @author bogdanov
 * @ created 18.10.2012
 * @ $Author$
 * @ $Revision$
 */

public class ActiveDebitAllowedExternalPhizAccountFilter extends ActiveDebitAccountFilter
{
	private AllowedExternalPhizAccountFilter allowedExternalPhizAccountFilter = new AllowedExternalPhizAccountFilter();

	@Override
	public boolean accept(AccountLink accountLink)
	{
		return super.accept(accountLink) && allowedExternalPhizAccountFilter.accept(accountLink);
	}
}
