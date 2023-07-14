package com.rssl.phizic.business.ext.sbrf.reports;

import java.util.Map;

/**
 * @author Mescheryakova
 * @ created 17.08.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ����� ���������� ������� �� ������-���������, ��� ��������� ��� ������ ��� ��������, ������, �����
 */
public  abstract class CountOperationsReport extends CountOperations
{
	private String type;           // ��� (��������) ������-��������
	private String currency;       
	private float amount;

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getCurrency()
	{
		return currency;
	}

	public void setCurrency(String currency)
	{
		this.currency = currency;
	}

	public float getAmount()
	{
		return amount;
	}

	public void setAmount(float amount)
	{
		this.amount = amount;
	}

	/**
	 * ������, �������� �� ����� �� id-�������������
	 * @return true - ��, false - ���
	 */
	public boolean isIds()
	{
		return true;
	}

	/**
	 * �������� ��������������� ��������� ��� sql-�������
	 * @param report - �����
	 * @return ��� ���� "���� => ��������", ���. ����� ����� ������������� � sql
	 */
	public Map<String, Object> getAdditionalParams(ReportAbstract report)
	{
		Map<String, Object> map = super.getAdditionalParams(report);
		map.putAll(sqlParamsDescription);     // ��������� ��������� ��� ������ � userlog �� description 

		return map;
	}
}
