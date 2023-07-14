package com.rssl.phizic.business.dictionaries.pfp.risk;

import com.rssl.phizic.business.dictionaries.pfp.common.PFPDictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;

/**
 * @author akrenev
 * @ created 15.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * �������� �����
 */

public class Risk extends PFPDictionaryRecord
{
	private Long id;
	private String name;

	/**
	 * @return �������������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ������ �������������
	 * @param id �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * ��������� ������ ��� ����������
	 * @return ���� ������
	 */
	@Override public Comparable getSynchKey()
	{
		return name;
	}

	/**
	 * �������� ���������� ������ �� ������
	 * @param that ������ �� ������� ���� ��������
	 */
	public void updateFrom(DictionaryRecord that)
	{
		setName(((Risk)that).getName());
	}

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
}
