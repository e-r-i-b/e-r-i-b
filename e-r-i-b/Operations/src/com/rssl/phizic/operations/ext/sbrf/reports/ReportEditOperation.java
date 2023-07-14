package com.rssl.phizic.operations.ext.sbrf.reports;

import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ext.sbrf.reports.ReportServiceSBRF;
import com.rssl.phizic.business.ext.sbrf.reports.StateReport;
import com.rssl.phizic.business.ext.sbrf.reports.ReportAbstract;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Calendar;

/**
 * @author mescheryakova
 * @ created 09.06.2010
 * @ $Author$
 * @ $Revision$
 */

public abstract class ReportEditOperation extends OperationBase implements EditEntityOperation
{
	private static final ReportServiceSBRF reportServiceSBRF = new ReportServiceSBRF();
	private ReportAbstract report = null;


	public Object getEntity() throws BusinessException, BusinessLogicException
	{
		return report;
	}

	/**
	 * ��������� ����� �����������
	 * @param dateStart - ����, �� ���. ����������� ����� (������ �������, �� ���. ����������� �����)
	 * @param dateEnd   - ����, �� ���. ����������� ����� (����� �������, �� ���. ����������� �����)
	 * @param ids      -  ������, ��������� id �������������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void  initialize(Calendar dateStart, Calendar dateEnd, List<String> ids) throws BusinessException, BusinessLogicException
	{
	    report = getReportClass();                  // ������������� ������� (������ ��� it)

		report.setStartDate( dateStart );          // ���� �� ������� ����������� ����� ��� ������ �������, �� ������� ����������� �����
	    report.setEndDate( dateEnd );              // ��������� �������, �� ���. ����������� �����

	    report.setDate(Calendar.getInstance());    // ���� ������ ������������ ������
		report.setState(StateReport.SEND);         // ������ ������ ������: �����������
	    report.setType(getTypeReport());           // ��� ������

	    String idsString = StringUtils.join(ids, ';');
	    report.setParams(idsString);               // id ������������� ����� ;
	}

	/**
	 * �������� ������, �� ������� �������� ������ � userlog
	 * @return
	 */
	public Calendar[] getPeriodUserLog() throws BusinessException
	{
		return reportServiceSBRF.getPeriodUserLog(getInstanceName());
	}

	public String getInstanceName()
	{
		return "Report";
	}

	public abstract char getTypeReport();

	public abstract ReportAbstract getReportClass();

	public void save() throws BusinessException
	{
		reportServiceSBRF.add(report, getInstanceName());
	}
}
