package com.rssl.phizic.business.ext.sbrf.reports.it;

import com.rssl.phizic.business.ext.sbrf.reports.ReportAdditionalInfo;
import com.rssl.phizic.business.ext.sbrf.reports.ReportAbstract;

import java.util.Map;
import java.util.HashMap;
import java.util.Calendar;
import java.util.List;

/**
 * @author Mescheryakova
 * @ created 14.09.2010
 * @ $Author$
 * @ $Revision$
 */

public class CountUsersDayITReport  implements ReportAdditionalInfo
{
	private long id;
	private ReportAbstract report_id;
	private double countUsersDayAvg;
	private long countUsersDayMax;

	public static final boolean isIds = true;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public ReportAbstract getReport_id()
	{
		return report_id;
	}

	public void setReport_id(ReportAbstract report_id)
	{
		this.report_id = report_id;
	}

	public double getCountUsersDayAvg()
	{
		return countUsersDayAvg;
	}

	public void setCountUsersDayAvg(double countUsersDayAvg)
	{
		this.countUsersDayAvg = countUsersDayAvg;
	}

	public long getCountUsersDayMax()
	{
		return countUsersDayMax;
	}

	public void setCountUsersDayMax(long countUsersDayMax)
	{
		this.countUsersDayMax = countUsersDayMax;
	}

	/**
	 * �������� ��������������� ��������� ��� sql-�������
	 * @param report - �����
	 * @return ��� ���� "���� => ��������", ���. ����� ����� ������������� � sql
	 */
	public Map<String, Object> getAdditionalParams(ReportAbstract report)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		Calendar endDate = Calendar.getInstance();
		endDate.setTime( report.getEndDate().getTime() );
		endDate.add(Calendar.DATE, 1);     // + 1 ����, �.�. � ������� ������������ between

		map.put("endDate", endDate);
		map.put("description", "������� ���-�� ������ � ���������� PhizIC ����� ");
		map.put("beforeIP", "�� ������ � ������� ");
		map.put("application", "PhizIC");
		
		return map;
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
		this.setCountUsersDayAvg( obj[0] == null ? 0 : Double.valueOf(obj[0].toString().trim()).doubleValue() );
		this.setCountUsersDayMax( obj[1] == null ? 0 : Long.decode(obj[1].toString()) );
		return this;
	}

	/**
	 * �������� ��� �����
	 * @return - ����������  �������� ����� ��� �������� ������� � �� ��� ��������� ������
	 */
	public String getQueryName(ReportAbstract report)
	{
		return "com.rssl.phizic.business.ext.sbrf.reports.getBusinessParamsSBRF";
	}

	/**
	 * ������, �������� �� ����� �� id-�������������
	 * @return true - ��, false - ���
	 */
	public boolean isIds()
	{
		return false;
	}

	/**
	 * ���������� ���������� �� sql-������� ������
	 * @param list ��������� sql-�������
	 */
	public List processData(List list)
	{
		return list;
	}
}
