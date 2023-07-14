package com.rssl.phizic.web.dictionaries.regions.locale;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.regions.locale.RegionResources;
import com.rssl.phizic.locale.dynamic.resources.LanguageResources;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.regions.locale.EditRegionResourcesOperation;
import com.rssl.phizic.operations.locale.dynamic.resources.EditLanguageResourcesOperation;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseAction;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseForm;

import java.util.Map;

/**
 * @author koptyaev
 * @ created 02.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class EditRegionResourcesAction extends EditLanguageResourcesBaseAction
{
	@Override
	protected void updateForm(EditLanguageResourcesBaseForm frm, LanguageResources entity) throws Exception
	{
		RegionResources resources = (RegionResources)entity;
		frm.setField("name", resources.getName());
	}

	@Override
	protected void updateFormOperationalData(EditLanguageResourcesBaseForm frm, EditLanguageResourcesOperation operation) throws Exception {}

	@Override
	protected void updateEntity(LanguageResources entity, Map<String, Object> data) throws Exception
	{
		RegionResources resources = (RegionResources)entity;
		resources.setName((String)data.get("name"));
	}

	@Override
	protected void updateEntityOperationalData(EditEntityOperation operation, Map<String, Object> data) throws Exception
	{}

	@Override
	protected Form getEditForm(EditLanguageResourcesBaseForm frm)
	{
		return EditRegionResourcesForm.EDIT_FORM;
	}

	@Override
	protected EditLanguageResourcesOperation createEditOperation(EditLanguageResourcesBaseForm frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(EditRegionResourcesOperation.class);
	}
}
