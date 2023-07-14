package com.rssl.phizic.web.monitoring.thresholdvalues;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.monitoring.MonitoringThresholdValues;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.monitoring.thresholdvalues.EditMonitoringThresholdValuesOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionForward;

import java.util.List;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 25.02.2011
 * @ $Author$
 * @ $Revision$
 *
 * Ёкшен редактировани€ параметров мониторинга
 */

public class EditMonitoringThresholdValuesAction extends EditActionBase
{
	@Override
	protected EditEntityOperation createEditOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EditMonitoringThresholdValuesForm frm = (EditMonitoringThresholdValuesForm) form;
		EditMonitoringThresholdValuesOperation operation = createOperation(EditMonitoringThresholdValuesOperation.class);
		operation.initialize(frm.getId());
		return operation;
	}

	@Override
	protected Form getEditForm(EditFormBase frm)
	{
		EditMonitoringThresholdValuesForm form = (EditMonitoringThresholdValuesForm) frm;
		return form.createForm();
	}

	@Override
	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		List<MonitoringThresholdValues> thresholdValues = (List<MonitoringThresholdValues>)entity;
		for(MonitoringThresholdValues value : thresholdValues)
		{
			String reportName = value.getReport().toString();
			value.setWarningThreshold((Long)data.get(reportName + EditMonitoringThresholdValuesForm.WARNING_THRESHOLD));
			value.setErrorThreshold((Long)data.get(reportName + EditMonitoringThresholdValuesForm.ERROR_THRESHOLD));
		}
	}

	@Override
	protected void updateForm(EditFormBase frm, Object entity){}

	@Override
	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		EditMonitoringThresholdValuesForm form = (EditMonitoringThresholdValuesForm) frm;
		List<MonitoringThresholdValues> thresholdValues = (List<MonitoringThresholdValues>)operation.getEntity();
		form.setValues(thresholdValues);
		for(MonitoringThresholdValues value : thresholdValues)
		{
			String reportName = value.getReport().toString();
			form.setField(reportName + EditMonitoringThresholdValuesForm.WARNING_THRESHOLD,value.getWarningThreshold());
			form.setField(reportName + EditMonitoringThresholdValuesForm.ERROR_THRESHOLD,value.getErrorThreshold());
		}
	}

	@Override
	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm) throws BusinessException
	{
		return createStartActionForward(frm, getCurrentMapping());
	}
}
