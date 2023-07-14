package com.rssl.phizic.business.resources.external;

/**
 * @author Erkin
 * @ created 22.11.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���������� "�" ��� ����-���������
 */
public class AccountFilterConjunction extends CompositeAccountFilterBase
{
	/**
	 * default ctor
	 */
	public AccountFilterConjunction() {}

	/**
	 * ����������� �� ������� �������
	 * @param filters
	 */
	public AccountFilterConjunction(AccountFilter... filters)
	{
		super(filters);
	}

	public boolean accept(AccountLink accountLink)
	{
		for (AccountFilter filter : getFilters()) {
			if (!filter.accept(accountLink))
				return false;
		}
		return true;
	}
}
