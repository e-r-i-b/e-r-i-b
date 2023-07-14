package com.rssl.phizic.web.configure.gate;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.gate.monitoring.MonitoringParameters;
import com.rssl.phizic.gate.monitoring.MonitoringStateParameters;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.config.gate.RunGateMonitoringOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author akrenev
 * @ created 22.01.2013
 * @ $Author$
 * @ $Revision$
 */

public class RunGateMonitoringAction extends EditActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation("RunGateMonitoringOperation", "RunGateMonitoringService");
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return RunGateMonitoringForm.getForm();
	}

	@Override
	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		super.updateOperationAdditionalData(editOperation, editForm, validationResult);
		RunGateMonitoringForm form = (RunGateMonitoringForm) editForm;
		RunGateMonitoringOperation operation = (RunGateMonitoringOperation) editOperation;
		operation.setGateUrls(form.getGateUrls());
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		MonitoringParameters parameters = (MonitoringParameters) entity;
		parameters.setServiceName((String) data.get(RunGateMonitoringForm.SERVICE_NAME_FIELD_NAME));
		List<MonitoringStateParameters> stateParameters = new ArrayList<MonitoringStateParameters>();
		parameters.setAllStateConfig(stateParameters);
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{}
}
