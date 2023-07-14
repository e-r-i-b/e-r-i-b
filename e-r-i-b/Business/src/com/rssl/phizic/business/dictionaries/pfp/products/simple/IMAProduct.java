package com.rssl.phizic.business.dictionaries.pfp.products.simple;

import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;

/**
 * @author akrenev
 * @ created 24.02.2012
 * @ $Author$
 * @ $Revision$
 * ���
 */
public class IMAProduct extends InvestmentProduct
{
	private Long imaId;             // ��� ����� �� �����������
	private Long imaAdditionalId;   // ������ ����� �� �����������

	/**
	 * @return ��� ����� �� �����������
	 */
	public Long getImaId()
	{
		return imaId;
	}

	/**
	 * @param imaId - ��� ����� �� �����������
	 */
	public void setImaId(Long imaId)
	{
		this.imaId = imaId;
	}

	/**
	 * @return ������ ����� �� �����������
	 */
	public Long getImaAdditionalId()
	{
		return imaAdditionalId;
	}

	/**
	 * ������ ������ ����� �� �����������
	 * @param imaAdditionalId ������ ����� �� �����������
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
