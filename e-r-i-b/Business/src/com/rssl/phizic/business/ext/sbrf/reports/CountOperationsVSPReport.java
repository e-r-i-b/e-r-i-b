package com.rssl.phizic.business.ext.sbrf.reports;

/**
 * @author Mescheryakova
 * @ created 11.08.2010
 * @ $Author$
 * @ $Revision$
 */

/*
	����� '�������� � ���� �� ������ �� ���'
 */
public class CountOperationsVSPReport extends CountOperationsTBReport
{
	private Long osb_id;      // id ���
	private Long vsp_id;      // id ���

	public Long getOsb_id()
	{
		return osb_id;
	}

	public void setOsb_id(Long osb_id)
	{
		this.osb_id = osb_id;
	}

	public Long getVsp_id()
	{
		return vsp_id;
	}

	public void setVsp_id(Long vsp_id)
	{
		this.vsp_id = vsp_id;
	}

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
		this.setOsb_id( Long.decode(obj[2].toString()) );
		this.setVsp_id( Long.decode(obj[3].toString()) );

		this.setType( obj[4] == null ? " " : obj[4].toString() );
		this.setCount( Long.decode(obj[5].toString()) );

		return this;
	}
	
	/**
	 * �������� ��� �����
	 * @return - ����������  �������� ����� ��� �������� ������� � �� ��� ��������� ������
	 */
	public String getQueryName(ReportAbstract report)
	{
		return "com.rssl.phizic.business.ext.sbrf.reports.getOperationsVSP";
	}
}

