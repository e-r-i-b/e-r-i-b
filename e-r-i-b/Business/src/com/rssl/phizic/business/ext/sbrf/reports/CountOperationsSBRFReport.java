package com.rssl.phizic.business.ext.sbrf.reports;

/**
 * @author Mescheryakova
 * @ created 10.08.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ����� "���������� �������� �� ���� �� ������"
 */

public class CountOperationsSBRFReport  extends CountOperationsReport
{
	/**
	 * �������� ����������� ������ �� ����������� ������� ��������
	 * @param obj - ������ ��������
	 * @param report - �����, �� ���. ��������� ����������� �����
	 * @return - ����������� �� ���������� ���������� �����
	 */
	public  Object buildReportByArrayOfObject(Object[] obj, ReportAbstract report)
	{
		this.setReport_id(report);
		this.setType( obj[0] == null ? " " : obj[0].toString() );
		this.setCurrency( obj[1] == null ? " " : obj[1].toString() );
		this.setCount( Long.decode(obj[2].toString()) );
		this.setAmount( obj[3] == null ? 0 : Float.valueOf(obj[3].toString().trim()).floatValue() );
		return this;
	}

	/**
	 * �������� ��� �����
	 * @return - ����������  �������� ����� ��� �������� ������� � �� ��� ��������� ������
	 */
	public String getQueryName(ReportAbstract report)
	{
		return "com.rssl.phizic.business.ext.sbrf.reports.getOperationsSBRF";
	}

	/**
	 * ������, �������� �� ����� �� id-�������������
	 * @return true - ��, false - ���
	 */	
	public boolean isIds()
	{
		return false;
	}
}
