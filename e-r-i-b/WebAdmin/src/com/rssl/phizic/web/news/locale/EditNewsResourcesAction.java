package com.rssl.phizic.web.news.locale;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

import com.rssl.phizic.business.news.locale.NewsResources;
import com.rssl.phizic.locale.dynamic.resources.LanguageResources;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.locale.dynamic.resources.EditLanguageResourcesOperation;
import com.rssl.phizic.operations.news.locale.EditNewsResourcesOperation;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseAction;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseForm;

import java.util.Map;

/**
 * @author koptyaev
 * @ created 30.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class EditNewsResourcesAction extends EditLanguageResourcesBaseAction
{
	@Override
	protected void updateForm(EditLanguageResourcesBaseForm frm, LanguageResources entity) throws Exception
	{
		NewsResources newsResources = (NewsResources)entity;
		frm.setField("title", newsResources.getTitle());
		frm.setField("text", newsResources.getText());
		frm.setField("shortText", newsResources.getShortText());
	}
	@Override
	protected void updateFormOperationalData(EditLanguageResourcesBaseForm frm, EditLanguageResourcesOperation operation) throws Exception {}

	@Override
	protected void updateEntity(LanguageResources entity, Map<String, Object> data) throws Exception
	{
		NewsResources newsResources = (NewsResources)entity;
		newsResources.setTitle((String)data.get("title"));
		newsResources.setText((String) data.get("text"));
		newsResources.setShortText((String) data.get("shortText"));
	}

	@Override
	protected void updateEntityOperationalData(EditEntityOperation operation, Map<String, Object> data) throws Exception {}

	@Override
	protected Form getEditForm(EditLanguageResourcesBaseForm frm)
	{
		return EditNewsResourcesForm.EDIT_LANGUAGE_FORM;
	}

	@Override
	protected EditLanguageResourcesOperation createEditOperation(EditLanguageResourcesBaseForm frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(EditNewsResourcesOperation.class);
	}
}
