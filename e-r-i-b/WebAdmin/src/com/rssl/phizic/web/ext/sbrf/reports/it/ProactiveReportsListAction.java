package com.rssl.phizic.web.ext.sbrf.reports.it;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ext.sbrf.reports.ReportAbstract;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.ext.sbrf.reports.ReportsListAction;
import com.rssl.phizic.web.ext.sbrf.reports.ReportsListForm;
import com.rssl.phizic.operations.ext.sbrf.reports.ReportsListOperation;

/**
 * @author lukina
 * @ created 06.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class ProactiveReportsListAction extends ReportsListAction
{
	protected String getQueryName(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return "ProactiveMonitoring";
	}

	/**
	 * Заполняем поля даты формирования отчета и по всему банку или по ТБ строится отчет в форме
	 * @param reportsOperation
	 * @param form
	 * @return
	 * @throws Exception
	 */
	protected ProactiveReportsListForm getReportDate(ReportsListOperation reportsOperation, ReportsListForm form)   throws Exception
	{
		ProactiveReportsListForm frm = (ProactiveReportsListForm)  form;
		reportsOperation.initialize(form.getId());
		ReportAbstract report = reportsOperation.getEntity();
		if (report != null)
		{
			frm.setReportStartDate(report.getStartDate());
			frm.setReportEndDate(report.getEndDate());
			frm.setAllBank(report.getParams() == null);
		}
		return frm;
	}
}