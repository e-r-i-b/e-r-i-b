package com.rssl.phizic.business.dictionaries.pfp.products.simple;

import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;

/**
 * @author akrenev
 * @ created 21.02.2012
 * @ $Author$
 * @ $Revision$
 *
 * Вклады
 */

public class AccountProduct extends Product
{
	private AdvisableSum advisableSum;
	private Long accountId;

	/**
	 * @return Рекомендуемая сумма
	 */
	public AdvisableSum getAdvisableSum()
	{
		return advisableSum;
	}

	/**
	 * задать рекомендуемую сумму
	 * @param advisableSum Рекомендуемая сумма
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
	 * @return идентификатор вклада для открытия в СБОЛ
	 */
	public Long getAccountId()
	{
		return accountId;
	}

	/**
	 * @param accountId - идентификатор вклада для открытия в СБОЛ
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
