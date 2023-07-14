package com.rssl.phizic.business.dictionaries.pfp.targets;

import com.rssl.phizic.business.dictionaries.pfp.common.PFPDictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;

/**
 * @author akrenev
 * @ created 20.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class Target extends PFPDictionaryRecord
{
	private String name;         // �������� ����
	private Long imageId;        // ������������� ��������
	private boolean onlyOne;     // ����������� ��������� ������ ���� ���� ������� ����
	private boolean laterAll;    // ���� ���������� ���� ����� ���������
	private boolean laterLoans;  // ���� ���������� ���� ����� ���������� ������� �� �������

	/**
	 * @return �������� ����
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name - �������� ����
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	public Long getImageId()
	{
		return imageId;
	}

	public void setImageId(Long imageId)
	{
		this.imageId = imageId;
	}

	/**
	 * @return ����������� ��������� ������ ���� ���� ������� ����
	 */
	public boolean isOnlyOne()
	{
		return onlyOne;
	}

	/**
	 * @param onlyOne - ����������� ��������� ������ ���� ���� ������� ����
	 */
	public void setOnlyOne(boolean onlyOne)
	{
		this.onlyOne = onlyOne;
	}

	/**
	 * @return ���� ���������� ���� ����� ���������
	 */
	public boolean isLaterAll()
	{
		return laterAll;
	}

	/**
	 * @param laterAll - ���� ���������� ���� ����� ���������
	 */
	public void setLaterAll(boolean laterAll)
	{
		this.laterAll = laterAll;
	}

	/**
	 * @return - ���� ���������� ���� ����� ���������� ������� �� �������
	 */
	public boolean isLaterLoans()
	{
		return laterLoans;
	}

	/**
	 * @param laterLoans - ���� ���������� ���� ����� ���������� ������� �� �������
	 */
	public void setLaterLoans(boolean laterLoans)
	{
		this.laterLoans = laterLoans;
	}

	public Comparable getSynchKey()
	{
		return name;
	}

	public void updateFrom(DictionaryRecord that)
	{
		Target source = (Target) that;
		setName(source.getName());
		setImageId(source.getImageId());
	}
}
