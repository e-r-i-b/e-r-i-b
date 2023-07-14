package com.rssl.phizic.business.dictionaries.pfp.products.simple;

import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;

/**
 * @author akrenev
 * @ created 24.02.2012
 * @ $Author$
 * @ $Revision$
 * ОМС
 */
public class IMAProduct extends InvestmentProduct
{
	private Long imaId;             // вид счета из справочника
	private Long imaAdditionalId;   // подвид счета из справочника

	/**
	 * @return вид счета из справочника
	 */
	public Long getImaId()
	{
		return imaId;
	}

	/**
	 * @param imaId - вид счета из справочника
	 */
	public void setImaId(Long imaId)
	{
		this.imaId = imaId;
	}

	/**
	 * @return подвид счета из справочника
	 */
	public Long getImaAdditionalId()
	{
		return imaAdditionalId;
	}

	/**
	 * задать подвид счета из справочника
	 * @param imaAdditionalId подвид счета из справочника
	 */
	public void setImaAdditionalId(Long imaAdditionalId)
	{
		this.imaAdditionalId = imaAdditionalId;
	}

	public DictionaryProductType getProductType()
	{
		return DictionaryProductType.IMA;
	}
}
