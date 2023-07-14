package com.rssl.phizic.web.configure.gate;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.gate.monitoring.MonitoringGateState;
import com.rssl.phizic.gate.monitoring.MonitoringServiceGateStateConfig;
import com.rssl.phizic.gate.monitoring.InactiveType;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.config.gate.EditMonitoringGateConfigOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;

import java.math.BigInteger;
import java.util.Map;

/**
 * @author akrenev
 * @ created 03.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class EditMonitoringGateConfigAction extends EditActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		MonitoringGateState state = MonitoringGateState.valueOf((String) frm.getField(EditMonitoringGateConfigForm.EDITING_STATE_FIELD_NAME));
		EditMonitoringGateConfigOperation operation;
		if (MonitoringGateState.DEGRADATION == state)
			operation = createOperation("EditMonitoringDegradationGateConfigOperation");
		else
			operation = createOperation("EditMonitoringInaccessibleGateConfigOperation");
		
		operation.initialize((String) frm.getField(EditMonitoringGateConfigForm.SERVICE_NAME_FIELD_NAME), state);
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditMonitoringGateConfigForm.EDIT_FORM;
	}

	private int getIntValue(Map<String, Object> data, String fieldName)
	{
		return ((BigInteger) data.get(fieldName)).intValue();
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		MonitoringServiceGateStateConfig stateConfig = (MonitoringServiceGateStateConfig) entity;
		stateConfig.setUseMonitoring((Boolean) data.get(EditMonitoringGateConfigForm.USE_FIELD_NAME));
		stateConfig.setMonitoringTime(getIntValue(data, EditMonitoringGateConfigForm.TIME_FIELD_NAME) * DateHelper.MILLISECONDS_IN_SECOND);
		stateConfig.setMonitoringCount(getIntValue(data, EditMonitoringGateConfigForm.COUNT_FIELD_NAME));
		stateConfig.setMonitoringErrorPercent(getIntValue(data, EditMonitoringGateConfigForm.PERCENT_FIELD_NAME));
		stateConfig.setTimeout(getIntValue(data, EditMonitoringGateConfigForm.TIMEOUT_FIELD_NAME) * DateHelper.MILLISECONDS_IN_SECOND);
		stateConfig.setMessageText((String) data.get(EditMonitoringGateConfigForm.MESSAGE_TEXT_FIELD_NAME));
		stateConfig.setRecoveryTime((Long) data.get(EditMonitoringGateConfigForm.RECOVERY_TIME_FIELD_NAME) * DateHelper.MILLISECONDS_IN_MINUTE);
		if (stateConfig.isAvailableChangeInactiveType())
			stateConfig.setInactiveType(InactiveType.valueOf((String) data.get(EditMonitoringGateConfigForm.INACTIVE_TYPE_FIELD_NAME)));
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		MonitoringServiceGateStateConfig stateConfig = (MonitoringServiceGateStateConfig) entity;
		frm.setField(EditMonitoringGateConfigForm.ID_FIELD_NAME, stateConfig.getId());
		frm.setField(EditMonitoringGateConfigForm.USE_FIELD_NAME, stateConfig.isUseMonitoring());
		frm.setField(EditMonitoringGateConfigForm.TIME_FIELD_NAME, stateConfig.getMonitoringTime() / DateHelper.MILLISECONDS_IN_SECOND);
		frm.setField(EditMonitoringGateConfigForm.COUNT_FIELD_NAME, stateConfig.getMonitoringCount());
		frm.setField(EditMonitoringGateConfigForm.PERCENT_FIELD_NAME, stateConfig.getMonitoringErrorPercent());
		frm.setField(EditMonitoringGateConfigForm.TIMEOUT_FIELD_NAME, stateConfig.getTimeout() / DateHelper.MILLISECONDS_IN_SECOND);
		frm.setField(EditMonitoringGateConfigForm.MESSAGE_TEXT_FIELD_NAME, stateConfig.getMessageText());
		frm.setField(EditMonitoringGateConfigForm.RECOVERY_TIME_FIELD_NAME, stateConfig.getRecoveryTime() / DateHelper.MILLISECONDS_IN_MINUTE);
		frm.setField(EditMonitoringGateConfigForm.AVAILABLE_CHANGE_INACTIVE_TYPE_FIELD_NAME, stateConfig.isAvailableChangeInactiveType());
		frm.setField(EditMonitoringGateConfigForm.INACTIVE_TYPE_FIELD_NAME, stateConfig.getInactiveType());
	}

	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		super.updateFormAdditionalData(frm, operation);
		EditMonitoringGateConfigOperation op = (EditMonitoringGateConfigOperation) operation;
		frm.setField(EditMonitoringGateConfigForm.STATE_FIELD_NAME, op.getCurrentState());
	}
}
