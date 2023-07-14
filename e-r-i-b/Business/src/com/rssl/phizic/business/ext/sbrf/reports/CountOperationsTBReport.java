package com.rssl.phizic.business.ext.sbrf.reports;

/**
 * @author Mescheryakova
 * @ created 11.08.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ����� '���������� �������� �� �� �� ������'
 */

public class CountOperationsTBReport extends  CountOperationsReport
{
	private Long tb_id;
	private String tb_name;

	
	/**
	 * �������� ����������� ������ �� ����������� ������� ��������
	 * @param obj - ������ ��������
	 * @param report - �����, �� ���. ��������� ����������� �����
	 * @return - ����������� �� ���������� ���������� �����
	 */

	public  Object buildReportByArrayOfObject(Object[] obj, ReportAbstract report)
	{
		this.setReport_id(report);
		this.setTb_id( Long.decode(obj[0].toString()) );
		this.setTb_name( obj[1].toString() );
		this.setType( obj[2] == null ? " " : obj[2].toString() );
		this.setCurrency( obj[3] == null ? " " : obj[3].toString() );
		this.setCount( Long.decode(obj[4].toString()) );
		this.setAmount( obj[5] == null ? 0 : Float.valueOf(obj[5].toString().trim()).floatValue() );
		return this;
	}

	/**
	 * �������� ��� �����
	 * @return - ����������  �������� ����� ��� �������� ������� � �� ��� ��������� ������
	 */
	public String getQueryName(ReportAbstract report)
	{
		return "com.rssl.phizic.business.ext.sbrf.reports.getOperationsTB";
	}

	public Long getTb_id()
	{
		return tb_id;
	}

	public void setTb_id(Long tb_id)
	{
		this.tb_id = tb_id;
	}

	public String getTb_name()
	{
		return tb_name;
	}

	public void setTb_name(String tb_name)
	{
		this.tb_name = tb_name;
	}
}

