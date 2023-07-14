package com.rssl.phizic.web.configure.gate.locale;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.gate.monitoring.locale.MonitoringServiceGateStateConfigResources;
import com.rssl.phizic.locale.dynamic.resources.LanguageResources;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.config.gate.locale.EditMonitoringGateConfigResourcesOperation;
import com.rssl.phizic.operations.locale.dynamic.resources.EditLanguageResourcesOperation;
import com.rssl.phizic.web.configure.gate.EditMonitoringGateConfigForm;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseAction;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseForm;

import java.util.Map;

/**
 * Ёкшен редактировани€ много€зычных текстовок дл€ MonitoringServiceGateStateConfig
 * @author komarov
 * @ created 29.05.2015
 * @ $Author$
 * @ $Revision$
 */
public class EditMonitoringGateConfigResourcesAction extends EditLanguageResourcesBaseAction
{
	@Override
	protected void updateForm(EditLanguageResourcesBaseForm frm, LanguageResources entity) throws Exception
	{
		MonitoringServiceGateStateConfigResources resource = (MonitoringServiceGateStateConfigResources)entity;
		frm.setField(EditMonitoringGateConfigForm.MESSAGE_TEXT_FIELD_NAME, resource.getMessageText());
	}

	@Override
	protected void updateFormOperationalData(EditLanguageResourcesBaseForm frm, EditLanguageResourcesOperation operation) throws Exception
	{
	}

	@Override
	protected void updateEntity(LanguageResources entity, Map<String, Object> data) throws Exception
	{
		MonitoringServiceGateStateConfigResources resource = (MonitoringServiceGateStateConfigResources)entity;
		resource.setMessageText((String) data.get(EditMonitoringGateConfigForm.MESSAGE_TEXT_FIELD_NAME));
	}

	@Override
	protected void updateEntityOperationalData(EditEntityOperation operation, Map<String, Object> data) throws Exception
	{
		((EditMonitoringGateConfigResourcesOperation) operation).setServiceName((String) data.get(EditMonitoringGateConfigForm.SERVICE_NAME_FIELD_NAME));
	}

	@Override
	protected Form getEditForm(EditLanguageResourcesBaseForm frm)
	{
		return EditMonitoringGateConfigResourcesForm.EDIT_FORM;
	}

	@Override
	protected EditLanguageResourcesOperation createEditOperation(EditLanguageResourcesBaseForm frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(EditMonitoringGateConfigResourcesOperation.class);
	}
}
