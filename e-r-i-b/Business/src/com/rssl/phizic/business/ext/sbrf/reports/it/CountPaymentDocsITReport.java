package com.rssl.phizic.business.ext.sbrf.reports.it;

import com.rssl.phizic.business.ext.sbrf.reports.CountOperationsReport;
import com.rssl.phizic.business.ext.sbrf.reports.CountOperationsSBRFReport;
import com.rssl.phizic.business.ext.sbrf.reports.ReportAbstract;
import com.rssl.phizic.business.ext.sbrf.reports.ReportAdditionalInfo;

import java.util.Map;
import java.util.List;

/**
 * @author Mescheryakova
 * @ created 13.09.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������� '���������� ��������� ���������� � ����' ��� ������ �� ������-����������
 */
public class CountPaymentDocsITReport  implements ReportAdditionalInfo
{
	private long id;
	private long tb_id;
	private String tb_name;
	private double countDocsDayAvg;        // ������� ���-�� ��������� ���������� � ����
	private long countDocsDayMax;          // ������������ ���-�� ��������� ���������� � ����
	private long countOperationsSecondMax;  // ���-�� ������-�������� � �������
	private ReportAbstract report_id;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public long getTb_id()
	{
		return tb_id;
	}

	public void setTb_id(long tb_id)
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

	public double getCountDocsDayAvg()
	{
		return countDocsDayAvg;
	}

	public void setCountDocsDayAvg(double countDocsDayAvg)
	{
		this.countDocsDayAvg = countDocsDayAvg;
	}

	public long getCountDocsDayMax()
	{
		return countDocsDayMax;
	}

	public void setCountDocsDayMax(long countDocsDayMax)
	{
		this.countDocsDayMax = countDocsDayMax;
	}

	public long getCountOperationsSecondMax()
	{
		return countOperationsSecondMax;
	}

	public void setCountOperationsSecondMax(long countOperationsSecondMax)
	{
		this.countOperationsSecondMax = countOperationsSecondMax;
	}

	public ReportAbstract getReport_id()
	{
		return report_id;
	}

	public void setReport_id(ReportAbstract report_id)
	{
		this.report_id = report_id;
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
		this.setCountDocsDayAvg( obj[2] == null ? 0 : Double.valueOf(obj[2].toString().trim()).doubleValue() );
		this.setCountDocsDayMax( obj[3] == null ? 0 : Long.decode(obj[3].toString()) );
		this.setCountOperationsSecondMax( obj[4] == null ? 0 : Long.decode(obj[4].toString()) );
		return this;
	}

	/**
	 * �������� ��� �����
	 * @return - ����������  �������� ����� ��� �������� ������� � �� ��� ��������� ������
	 */
	public String getQueryName(ReportAbstract report)
	{
		return "com.rssl.phizic.business.ext.sbrf.reports.getBusinessParamsTB";
	}

	/**
	 * �������� ��������������� ��������� ��� sql-�������
	 * @param report - �����
	 * @return ��� ���� "���� => ��������", ���. ����� ����� ������������� � sql
	 */
	public Map<String, Object> getAdditionalParams(ReportAbstract report)
	{
		CountOperationsReport  countOperationsReport = new CountOperationsSBRFReport();
		Map<String, Object> map = countOperationsReport.getAdditionalParams(report);       // ������ ����� �� ��������� ��� � � ������� �� ���������, ����� ������������� ������������ �������� ��� ������� �� ������ ������-��������
		return map;
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
	 * ���������� ���������� �� sql-������� ������
	 * @param list ��������� sql-�������
	 */
	public List processData(List list)
	{
		return list;
	}
}
