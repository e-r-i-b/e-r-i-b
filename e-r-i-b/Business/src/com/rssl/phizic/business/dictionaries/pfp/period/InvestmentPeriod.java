package com.rssl.phizic.business.dictionaries.pfp.period;

import com.rssl.phizic.business.dictionaries.pfp.common.PFPDictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;

/**
 * @author akrenev
 * @ created 15.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * "�������� ��������������"
 */

public class InvestmentPeriod extends PFPDictionaryRecord
{
	private Long id;
	private String period;

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
		return period;
	}

	/**
	 * �������� ���������� ������ �� ������
	 * @param that ������ �� ������� ���� ��������
	 */
	public void updateFrom(DictionaryRecord that)
	{
		setPeriod(((InvestmentPeriod)that).getPeriod());
	}

	/**
	 * @return ������
	 */
	public String getPeriod()
	{
		return period;
	}

	/**
	 * ������ ������
	 * @param period ������
	 */
	public void setPeriod(String period)
	{
		this.period = period;
	}
}
