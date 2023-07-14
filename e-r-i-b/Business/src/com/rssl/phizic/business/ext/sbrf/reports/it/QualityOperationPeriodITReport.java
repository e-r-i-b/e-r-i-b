package com.rssl.phizic.business.ext.sbrf.reports.it;

import com.rssl.phizic.business.ext.sbrf.reports.*;
import com.rssl.phizic.business.BusinessException;

import java.util.Map;
import java.util.HashMap;
import java.text.DecimalFormat;

/**
 * @author Mescheryakova
 * @ created 30.08.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * IT-����� '����� � �������� ���������� �������� �� ������'
 */

public class QualityOperationPeriodITReport  extends CountOperations
{
	private long tb_id;    //id ��
	private String tb_name;  // �������� ��
	private long countErrorOperations; // ���������� ��������� ��������

	private final static String SYSTEM_ERROR_PARAM_NAME = "SystemError";
	private final static String SYSTEM_ERROR_PARAM_VALUE = "(��������� ������)";

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

	public long getCountErrorOperations()
	{
		return countErrorOperations;
	}

	public void setCountErrorOperations(long countErrorOperations)
	{
		this.countErrorOperations = countErrorOperations;
	}

	/**
	 * ������� ������� ���������� ��������
	 * @return ������ ���� 0.00% 
	 */
	public String getPercentErrorOperations()
	{
		double percentErrors = (getCount() <= 0 ? 0 : (double) countErrorOperations * 100 / getCount());
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		return decimalFormat.format(percentErrors) + '%';
	}

	/**
	 * �������� ����������� ������ �� ����������� ������� ��������
	 * @param obj - ������ ��������
	 * @param report - �����, �� ���. ��������� ����������� �����
	 * @return - ����������� �� ���������� ���������� �����
	 */	
	public  Object buildReportByArrayOfObject(Object[] obj, ReportAbstract report)  throws BusinessException
	{
		this.setReport_id(report);
		this.setTb_id(Long.decode(obj[0].toString()));
		this.setTb_name(obj[1].toString());
		this.setCount(Long.decode(obj[2].toString()));
		this.setCountErrorOperations(Long.decode(obj[3].toString()));
		return this;
	}
	
	/**
	 * �������� ��� �����
	 * @return - ����������  �������� ����� ��� �������� ������� � �� ��� ��������� ������
	 */
	public String getQueryName(ReportAbstract report)
	{
		return "com.rssl.phizic.business.ext.sbrf.reports.getQualityOperatinsPeriodTB";
	}

	/**
	 * �������� ��������������� ��������� ��� sql-�������
	 * @param report - �����
	 * @return ��� ���� "���� => ��������", ���. ����� ����� ������������� � sql
	 */	
	public Map<String, Object> getAdditionalParams(ReportAbstract report)
	{
		Map<String, Object> mapAllParams = super.getAdditionalParams(report);  // �������� ��� ���������� ��� �������� ��� ������

		// ��������� � ���������� sql ��� ���� description ������� �������� ��� �������� ������
		Map<String, Object> systemError = new HashMap<String, Object>();
		for(Map.Entry<String, String> entry : sqlParamsDescription.entrySet())
			systemError.put(entry.getKey() + SYSTEM_ERROR_PARAM_NAME, entry.getValue() + SYSTEM_ERROR_PARAM_VALUE);

		mapAllParams.putAll(systemError);
		mapAllParams.putAll(sqlParamsDescription);

		return mapAllParams;
	}

	/**
	 * �������� ���������� �������� ��������
	 */
	public long getCountSuccessOperations()
	{
		return getCount() - countErrorOperations;
	}

	/**
	 * ������, �������� �� ����� �� id-�������������
	 * @return true - ��, false - ���
	 */
	public boolean isIds()
	{
		return true;
	}
}
