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
	 * «аполн€ем отчет параметрами
	 * @param dateStart - дата, на кот. формируетс€ отчет (начало периода, за кот. формируетс€ отчет)
	 * @param dateEnd   - дата, на кот. формируетс€ отчет (конец периода, за кот. формируетс€ отчет)
	 * @param ids      -  список, выбранных id департаментов
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void  initialize(Calendar dateStart, Calendar dateEnd, List<String> ids) throws BusinessException, BusinessLogicException
	{
	    report = getReportClass();                  // инициализаци€ репорта (бизнес или it)

		report.setStartDate( dateStart );          // дата на которую формируетс€ отчет или начало периода, на который формируетс€ отчет
	    report.setEndDate( dateEnd );              // окончание периода, на кот. формируетс€ отчет

	    report.setDate(Calendar.getInstance());    // дата начала формировани€ отчета
		report.setState(StateReport.SEND);         // ставим статус отчета: исполн€етс€
	    report.setType(getTypeReport());           // тип отчета

	    String idsString = StringUtils.join(ids, ';');
	    report.setParams(idsString);               // id департаментов через ;
	}

	/**
	 * получить период, за который хран€тс€ данные в userlog
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
