package com.rssl.phizic.web.dictionaries.provider.locale;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.locale.ServiceProviderResources;
import com.rssl.phizic.locale.dynamic.resources.LanguageResources;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.provider.locale.EditServiceProvidersLanguageOperation;
import com.rssl.phizic.operations.locale.dynamic.resources.EditLanguageResourcesOperation;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseAction;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseForm;

import java.util.Map;

/**
 * @author komarov
 * @ created 06.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class EditServiceProviderLanguageResourcesAction extends EditLanguageResourcesBaseAction
{
	@Override
	protected void updateForm(EditLanguageResourcesBaseForm frm, LanguageResources entity) throws Exception
	{
		ServiceProviderResources resource = (ServiceProviderResources) entity;
		frm.setField("name", resource.getName());
		frm.setField("legalName", resource.getLegalName());
		frm.setField("alias", resource.getAlias());
		frm.setField("bankName", resource.getBankName());
		frm.setField("description", resource.getDescription());
		frm.setField("tip", resource.getTipOfProvider());
		frm.setField("commissionMessage", resource.getCommissionMessage());
		frm.setField("nameOnBill", resource.getNameOnBill());
		frm.setField("nameService", resource.getNameService());
	}

	@Override
	protected void updateFormOperationalData(EditLanguageResourcesBaseForm frm, EditLanguageResourcesOperation operation) throws Exception {}

	@Override
	protected void updateEntity(LanguageResources entity, Map<String, Object> data) throws Exception
	{
		ServiceProviderResources resource = (ServiceProviderResources) entity;
		resource.setName((String) data.get("name"));
		resource.setLegalName((String) data.get("legalName"));
		resource.setAlias((String) data.get("alias"));
		resource.setBankName((String) data.get("bankName"));
		resource.setDescription((String) data.get("description"));
		resource.setTipOfProvider((String) data.get("tip"));
		resource.setCommissionMessage((String) data.get("commissionMessage"));
		resource.setNameOnBill((String) data.get("nameOnBill"));
		resource.setNameService((String) data.get("nameService"));
	}

	@Override
	protected void updateEntityOperationalData(EditEntityOperation operation, Map<String, Object> data) throws Exception {}

	@Override
	protected Form getEditForm(EditLanguageResourcesBaseForm frm)
	{
		return EditServiceProviderLanguageResourcesForm.EDIT_FORM;
	}

	@Override
	protected EditLanguageResourcesOperation createEditOperation(EditLanguageResourcesBaseForm frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(EditServiceProvidersLanguageOperation.class);
	}
}
