package com.rssl.phizic.web.ext.sbrf.reports.it;

import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.ext.sbrf.reports.ReportsListAction;
import com.rssl.phizic.web.ext.sbrf.reports.ReportsListForm;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.ext.sbrf.reports.ReportsListOperation;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.business.ext.sbrf.reports.ReportAdditionalInfo;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author Mescheryakova
 * @ created 17.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class BusinessOperationReportsListAction extends ReportsListAction
{

	protected List<String> getQueryNameList(ListFormBase form)
	{
		List<String> queryNames = new ArrayList<String>();
		queryNames.add("BusinessParamsReportTB");		
		queryNames.add("BusinessParamsReportSBRF");
		return queryNames;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
	    ReportsListForm form = (ReportsListForm) frm;

		List<String> queryNames = getQueryNameList(form);

		for (String queryName : queryNames)
		{
			Query query = operation.createQuery(queryName);
			query.setParameter("id", form.getId());
			List<ReportAdditionalInfo> queryResults = query.executeList();

			List< List<ReportAdditionalInfo> > reportAdditionalInfos = form.getData();

			if (reportAdditionalInfos == null)
			{
			    reportAdditionalInfos = new ArrayList< List<ReportAdditionalInfo> >();
				reportAdditionalInfos.add(0, queryResults);
			}
			else
			{
				reportAdditionalInfos.add(reportAdditionalInfos.size(), queryResults);


			}
			form.setData(reportAdditionalInfos);
			form = getReportDate((ReportsListOperation) operation, form);
		}
		updateFormAdditionalData(form, operation);
	}
}