package com.rssl.phizic.business.dictionaries.pfp.products.insurance;

import com.rssl.phizic.business.dictionaries.pfp.common.PFPDictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;

/**
 * @author akrenev
 * @ created 13.02.2012
 * @ $Author$
 * @ $Revision$
 *
 * ��� ���������� ��������
 */
public class InsuranceType extends PFPDictionaryRecord
{
	private String name;          // �������� ���� ��������
	private String description;   // ��������
	private Long imageId;         // ������������� ��������
	private InsuranceType parent; // ������������ ���

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Long getImageId()
	{
		return imageId;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setImageId(Long imageId)
	{
		this.imageId = imageId;
	}

	public InsuranceType getParent()
	{
		return parent;
	}

	public void setParent(InsuranceType parent)
	{
		this.parent = parent;
	}

	public Comparable getSynchKey()
	{
		String parentSynchKey = "";
		if (parent != null)
			parentSynchKey = (String) parent.getSynchKey();
		return parentSynchKey + getName();
	}

	public void updateFrom(DictionaryRecord that)
	{
		InsuranceType source = (InsuranceType) that;
		setName(source.getName());
		setDescription(source.getName());
		setParent(source.getParent());
		setImageId(source.getImageId());	
	}
}
