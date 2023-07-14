package com.rssl.phizic.business.dictionaries.pfp.products.complex;

import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.AccountProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.ProductBase;

/**
 * @author akrenev
 * @ created 01.03.2012
 * @ $Author$
 * @ $Revision$
 *
 * базовый класс комплексного продукта
 */
public abstract class ComplexProductBase extends ProductBase
{
	private AccountProduct account;

	/**
	 * @return вклад, привязанный к продукту
	 */
	public AccountProduct getAccount()
	{
		return account;
	}

	/**
	 * задать вклад, привязанный к продукту
	 * @param account вклад, привязанный к продукту
	 */
	public void setAccount(AccountProduct account)
	{
		this.account = account;
	}

	public Comparable getSynchKey()
	{
		return getAccount().getSynchKey();
	}

	public void updateFrom(DictionaryRecord that)
	{
		super.updateFrom(that);
		ComplexProductBase source = (ComplexProductBase) that;
		setAccount(source.getAccount());
	}
}
