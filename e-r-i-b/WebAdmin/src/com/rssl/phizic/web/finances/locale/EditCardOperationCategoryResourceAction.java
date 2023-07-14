package com.rssl.phizic.web.finances.locale;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.finances.locale.CardOperationCategoryResources;
import com.rssl.phizic.locale.dynamic.resources.LanguageResources;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.finances.configuration.locale.EditCardOperationCategoryResourcesOperation;
import com.rssl.phizic.operations.locale.dynamic.resources.EditLanguageResourcesOperation;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseAction;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseForm;

import java.util.Map;

/**
 * @author komarov
 * @ created 02.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class EditCardOperationCategoryResourceAction extends EditLanguageResourcesBaseAction
{

	@Override
	protected void updateForm(EditLanguageResourcesBaseForm frm, LanguageResources entity) throws Exception
	{
		CardOperationCategoryResources ñardOperationCategory_res = (CardOperationCategoryResources)entity;
		frm.setField("name", ñardOperationCategory_res.getName());
	}

	@Override
	protected void updateFormOperationalData(EditLanguageResourcesBaseForm frm, EditLanguageResourcesOperation operation) throws Exception{}

	@Override
	protected void updateEntity(LanguageResources entity, Map<String, Object> data) throws Exception
	{
		CardOperationCategoryResources newsResources = (CardOperationCategoryResources)entity;
		newsResources.setName((String) data.get("name"));
	}

	@Override
	protected void updateEntityOperationalData(EditEntityOperation operation, Map<String, Object> data) throws Exception{}

	@Override
	protected Form getEditForm(EditLanguageResourcesBaseForm frm)
	{
		return EditCardOperationCategoryResourceForm.EDIT_LANGUAGE_FORM;
	}

	@Override
	protected EditLanguageResourcesOperation createEditOperation(EditLanguageResourcesBaseForm frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(EditCardOperationCategoryResourcesOperation.class);
	}
}
