package com.rssl.phizic.business.resources.external;

/**
 * @author Kidyaev
 * @ created 14.11.2005
 * @ $Author$
 * @ $Revision$
 *
 * ��������� �������� �� ���� ��������
 */

public class ForeignCurrencyAccountFilter extends RURAccountFilter
{
    /**
     * �������� ��������� (�.�. �� ���������) �����
     */
    public boolean accept(AccountLink accountLink)
    {
	   return !super.accept(accountLink);
    }
}
