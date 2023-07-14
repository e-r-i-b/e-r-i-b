package com.rssl.phizic.business.ext.sbrf.reports;

import com.rssl.phizic.business.BusinessException;

import java.util.Map;
import java.util.List;

/**
 * ��������� ������ ��������������� ����� ��������, ��� ������� ���������� ���� ������� � ��
 * @author Mescheryakova
 * @ created 09.08.2010
 * @ $Author$
 * @ $Revision$
 */

public interface ReportAdditionalInfo
{
	/**
	 * �������� ����������� ������ �� ����������� ������� ��������
	 * @param obj - ������ ��������
	 * @param report - �����, �� ���. ��������� ����������� �����
	 * @return - ����������� �� ���������� ���������� �����
	 */
	public  Object buildReportByArrayOfObject(Object[] obj, ReportAbstract report) throws BusinessException;

	/**
	 * �������� ��� �����
	 * @param report - �����
	 * @return - ����������  �������� ����� ��� �������� ������� � �� ��� ��������� ������
	 */
	public String getQueryName(ReportAbstract report);

	/**
	 * �������� ��������������� ��������� ��� sql-�������
	 * @param report - �����
	 * @return ��� ���� "���� => ��������", ���. ����� ����� ������������� � sql
	 */
	public Map<String,Object> getAdditionalParams(ReportAbstract report);

	/**
	 * ������, �������� �� ����� �� id-�������������
	 * @return true - ��, false - ���
	 */
	public boolean isIds();

	/**
	 * ���������� ���������� �� sql-������� ������
	 * @param list ��������� sql-�������
	 */
	public List processData(List list);
}
