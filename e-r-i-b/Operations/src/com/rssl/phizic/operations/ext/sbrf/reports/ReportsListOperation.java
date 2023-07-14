package com.rssl.phizic.operations.ext.sbrf.reports;

import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ext.sbrf.reports.ReportServiceSBRF;
import com.rssl.phizic.business.ext.sbrf.reports.StateReport;
import com.rssl.phizic.business.ext.sbrf.reports.ReportAbstract;

/**
 * @author Mescheryakova
 * @ created 04.06.2010
 * @ $Author$
 * @ $Revision$
 */

public class ReportsListOperation extends OperationBase implements ListEntitiesOperation, RemoveEntityOperation
{
	private ReportAbstract report;
	private static ReportServiceSBRF reportService = new ReportServiceSBRF();

	protected String getInstanceName()
	{
		return "Report";
	}

	/**
	 * Инициализация операции
	 * @param id идентификатор сущности для удаления.
	 */
	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		 report = reportService.findById(id, getInstanceName());
	}

	/**
	 *  Удалить сущность.
	 */
	public void remove() throws BusinessLogicException, BusinessException
	{
		if (report == null)
			return;
		if (report != null && report.getState() == StateReport.SEND)
			throw new BusinessLogicException("message.remove");	
		reportService.remove(report, getInstanceName());
	}

	/**
	 * @return удаляемую/удаленную сущность.
	 */
	public ReportAbstract getEntity()
	{
		return report;
	}
}
