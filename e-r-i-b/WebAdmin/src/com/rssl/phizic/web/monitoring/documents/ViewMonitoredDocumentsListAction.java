package com.rssl.phizic.web.monitoring.documents;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.monitoring.documents.GetMonitoredDocumentsOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Calendar;

/**
 * @author mihaylov
 * @ created 18.03.2011
 * @ $Author$
 * @ $Revision$
 */

public class ViewMonitoredDocumentsListAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		ViewMonitoredDocumentsListForm form = (ViewMonitoredDocumentsListForm) frm;
		GetMonitoredDocumentsOperation operation = createOperation(GetMonitoredDocumentsOperation.class);
		operation.initialize(form.getDepartmentId());
		return operation;
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected void updateFormAdditionalData(ListFormBase frm, ListEntitiesOperation operation) throws Exception
	{
		ViewMonitoredDocumentsListForm form = (ViewMonitoredDocumentsListForm) frm;
		form.setReportTime(Calendar.getInstance());
	}

	protected String getQueryName(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		ViewMonitoredDocumentsListForm form = (ViewMonitoredDocumentsListForm) frm;
		return form.getReport();
	}
}
