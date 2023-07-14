package com.rssl.phizic.web.ext.sbrf.reports;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ext.sbrf.reports.ReportAbstract;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.ext.sbrf.reports.ReportsListOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.Map;

/**
 * @author Mescheryakova
 * @ created 04.06.2010
 * @ $Author$
 * @ $Revision$
 */

public class ReportsListAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ReportsListOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
	    ReportsListForm form = (ReportsListForm) frm;
		Query query = operation.createQuery(getQueryName(form));
		query.setParameter("id", form.getId());
		fillQuery(query, filterParams);
		form.setData(query.executeList());		
		form = getReportDate((ReportsListOperation) operation, form);  // получаем дату (период) формирования отчета
		updateFormAdditionalData(form, operation);
	}

	/**
	 * Заполняем поля даты формирования отчета в форме
	 * @param reportsOperation
	 * @param form
	 * @return
	 * @throws Exception
	 */

	protected ReportsListForm getReportDate(ReportsListOperation reportsOperation, ReportsListForm form)   throws Exception
	{
		reportsOperation.initialize(form.getId());
		ReportAbstract report = reportsOperation.getEntity();
		if (report != null)
		{
			form.setReportStartDate(report.getStartDate());
			form.setReportEndDate(report.getEndDate());
		}

		return form;
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ReportsListOperation.class);
	}

	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id) throws Exception
	{
		try
		{
			return super.doRemove(operation, id);
		}
		catch(BusinessLogicException e)
		{
			ActionMessages actionMessages = new ActionMessages();
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), id));
			return actionMessages;
		}
	}
}
