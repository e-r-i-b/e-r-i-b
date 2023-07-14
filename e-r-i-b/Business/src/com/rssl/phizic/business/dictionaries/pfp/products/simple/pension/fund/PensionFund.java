package com.rssl.phizic.business.dictionaries.pfp.products.simple.pension.fund;

import com.rssl.phizic.business.dictionaries.pfp.common.PFPDictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;

/**
 * @author akrenev
 * @ created 17.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ����
 */

public class PensionFund extends PFPDictionaryRecord
{
	private String name;
	private Long imageId;

	/**
	 * @return ��������
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * ������ ��������
	 * @param name ��������
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return ������������� ������
	 */
	public Long getImageId()
	{
		return imageId;
	}

	/**
	 * ������ ������������� ������
	 * @param imageId ������������� ������
	 */
	public void setImageId(Long imageId)
	{
		this.imageId = imageId;
	}

	public Comparable getSynchKey()
	{
		return name;
	}

	public void updateFrom(DictionaryRecord that)
	{
		PensionFund otherFund = (PensionFund) that;
		setName(otherFund.getName());
		setImageId(otherFund.getImageId());
	}
}
