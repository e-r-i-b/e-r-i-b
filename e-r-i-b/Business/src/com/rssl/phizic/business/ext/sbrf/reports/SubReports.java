package com.rssl.phizic.business.ext.sbrf.reports;

import java.util.List;

/**
 * �����, ������������, ������� �� ����� �� ���������� (��������, it-����� �� ������-����������)
 * �������� ������������ ����� ����������� �����, ���������� � ����������� �������, �� ������������ � �����
 * ��� ����������� ������ �� ������������ �������� � ���� ������� �����, ��� �������� ��� ����������� �������
 * � ��
 * @author Mescheryakova
 * @ created 13.09.2010
 * @ $Author$
 * @ $Revision$
 */

public abstract class SubReports
{
	/**
	 * ������ ��� �������� ������ ������� ���������� ������� ������
	 * @return �� ��������� ���������� null - ��� ����������
	 */
 	public List<ReportAdditionalInfo> getSubReportsList(ReportAbstract report)
	{
		return null;
	}
}
