package com.rssl.phizic.business.dictionaries.pfp.products.simple;

import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;

/**
 * @author akrenev
 * @ created 21.02.2012
 * @ $Author$
 * @ $Revision$
 *
 * ������
 */

public class AccountProduct extends Product
{
	private AdvisableSum advisableSum;
	private Long accountId;

	/**
	 * @return ������������� �����
	 */
	public AdvisableSum getAdvisableSum()
	{
		return advisableSum;
	}

	/**
	 * ������ ������������� �����
	 * @param advisableSum ������������� �����
	 */
	public void setAdvisableSum(AdvisableSum advisableSum)
	{
		this.advisableSum = advisableSum;
	}

	public void updateFrom(DictionaryRecord that)
	{
		super.updateFrom(that);
		setAdvisableSum(((AccountProduct) that).getAdvisableSum());
	}

	/**
	 * @return ������������� ������ ��� �������� � ����
	 */
	public Long getAccountId()
	{
		return accountId;
	}

	/**
	 * @param accountId - ������������� ������ ��� �������� � ����
	 */
	public void setAccountId(Long accountId)
	{
		this.accountId = accountId;
	}

	public DictionaryProductType getProductType()
	{
		return DictionaryProductType.ACCOUNT;
	}
}
