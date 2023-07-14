package com.rssl.phizic.web.configure.platform.locale;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.mobileDevices.locale.MobilePlatformResources;
import com.rssl.phizic.locale.dynamic.resources.LanguageResources;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.config.locale.MobilePlatformEditResourcesOperation;
import com.rssl.phizic.operations.locale.dynamic.resources.EditLanguageResourcesOperation;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseAction;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseForm;

import java.util.Map;

/**
 * @author koptyaev
 * @ created 13.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class MobilePlatformConfigEditResourcesAction extends EditLanguageResourcesBaseAction
{
	@Override
	protected void updateForm(EditLanguageResourcesBaseForm frm, LanguageResources entity) throws Exception
	{
		MobilePlatformConfigEditResourcesForm form = (MobilePlatformConfigEditResourcesForm)frm;
		MobilePlatformResources resources = (MobilePlatformResources) entity;
		form.setField("platformName", resources.getPlatformName());
		form.setField("errText", resources.getErrorText());
	}

	@Override protected void updateFormOperationalData(EditLanguageResourcesBaseForm frm, EditLanguageResourcesOperation operation) throws Exception {}

	@Override protected void updateEntity(LanguageResources entity, Map<String, Object> data) throws Exception
	{
		MobilePlatformResources resources = (MobilePlatformResources) entity;
		resources.setPlatformName((String)data.get("platformName"));
		resources.setErrorText((String)data.get("errText"));
	}

	@Override protected void updateEntityOperationalData(EditEntityOperation operation, Map<String, Object> data) throws Exception {}

	@Override
	protected Form getEditForm(EditLanguageResourcesBaseForm frm)
	{
		return MobilePlatformConfigEditResourcesForm.EDIT_FORM;
	}

	@Override protected EditLanguageResourcesOperation createEditOperation(EditLanguageResourcesBaseForm frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(MobilePlatformEditResourcesOperation.class);
	}
}
