package com.rssl.phizic.web.common.dictionaries.locale;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.bank.locale.ResidentBankResources;
import com.rssl.phizic.locale.dynamic.resources.LanguageResources;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.bank.locale.EditBankResourcesOperation;
import com.rssl.phizic.operations.locale.dynamic.resources.EditLanguageResourcesOperation;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseAction;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseForm;

import java.util.Map;

/**
 * @author koptyaev
 * @ created 06.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class EditBankResourcesAction extends EditLanguageResourcesBaseAction
{
	@Override
	protected void updateForm(EditLanguageResourcesBaseForm frm, LanguageResources entity) throws Exception
	{
		ResidentBankResources bankResources = (ResidentBankResources)entity;
		frm.setField("name", bankResources.getName());
		frm.setField("place", bankResources.getPlace());
		frm.setField("shortName", bankResources.getShortName());
		frm.setField("address", bankResources.getAddress());

	}

	@Override protected void updateFormOperationalData(EditLanguageResourcesBaseForm frm, EditLanguageResourcesOperation operation) throws Exception {}

	@Override
	protected void updateEntity(LanguageResources entity, Map<String, Object> data) throws Exception
	{
		ResidentBankResources bankResources = (ResidentBankResources)entity;
		bankResources.setName((String)data.get("name"));
		bankResources.setPlace((String) data.get("place"));
		bankResources.setShortName((String) data.get("shortName"));
		bankResources.setAddress((String) data.get("address"));
	}

	@Override protected void updateEntityOperationalData(EditEntityOperation operation, Map<String, Object> data) throws Exception {}

	@Override
	protected Form getEditForm(EditLanguageResourcesBaseForm frm)
	{
		return EditBankResourcesForm.EDIT_FORM;
	}

	@Override protected EditLanguageResourcesOperation createEditOperation(EditLanguageResourcesBaseForm frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(EditBankResourcesOperation.class);
	}

	@Override
	protected void initializeOperation(EditLanguageResourcesOperation operation, EditLanguageResourcesBaseForm frm) throws BusinessLogicException, BusinessException
	{
		//noinspection unchecked
		operation.initialize(frm.getStringId(), frm.getLocaleId());
	}
}
