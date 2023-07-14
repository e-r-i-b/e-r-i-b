package com.rssl.phizic.web.advertising.multilanguage;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.advertising.AdvertisingButton;
import com.rssl.phizic.business.advertising.locale.AdvertisingBlockResources;
import com.rssl.phizic.business.advertising.locale.AdvertisingButtonResources;
import com.rssl.phizic.locale.dynamic.resources.LanguageResources;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.advertising.language.EditLanguageOperation;
import com.rssl.phizic.operations.locale.dynamic.resources.EditLanguageResourcesOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseAction;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseForm;

import java.util.Map;

/**
 * @author komarov
 * @ created 23.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class EditLanguageAction extends EditLanguageResourcesBaseAction
{
	@Override
	protected void updateForm(EditLanguageResourcesBaseForm frm, LanguageResources entity) throws Exception
	{
		AdvertisingBlockResources advertising_res = (AdvertisingBlockResources)entity;
		frm.setField("title", advertising_res.getTitle());
		frm.setField("text", advertising_res.getText());
	}

	@Override
	protected void updateFormOperationalData(EditLanguageResourcesBaseForm frm, EditLanguageResourcesOperation operation) throws Exception
	{
		EditLanguageOperation op = (EditLanguageOperation)operation;
		Map<Long,AdvertisingButtonResources> buttons_res = op.getButtonsResMap();
		for(AdvertisingButton button : op.getButtons())
		{
			AdvertisingButtonResources button_res = buttons_res.get(button.getId());
			frm.setField("buttonTitle"+button.getOrderIndex(), button_res.getTitle());
		}
	}


	@Override
	protected void updateEntity(LanguageResources entity, Map<String, Object> data) throws Exception
	{
		AdvertisingBlockResources advertising_res = (AdvertisingBlockResources)entity;
		advertising_res.setTitle((String)data.get("title"));
		advertising_res.setText((String)data.get("text"));

	}

	@Override
	protected void updateEntityOperationalData(EditEntityOperation operation, Map<String, Object> data) throws Exception
	{
		EditLanguageOperation op = (EditLanguageOperation)operation;
		Map<Long,AdvertisingButtonResources> buttons_res = op.getButtonsResMap();
		for(AdvertisingButton button : op.getButtons())
		{
			AdvertisingButtonResources button_res = buttons_res.get(button.getId());
			if(StringHelper.isEmpty((String)data.get("buttonTitle"+button.getOrderIndex())))
				throw new BusinessLogicException("Заполните поле Кнопка "+(button.getOrderIndex() + 1)+".");

			button_res.setTitle((String) data.get("buttonTitle" + button.getOrderIndex()));
		}
	}

	@Override
	protected Form getEditForm(EditLanguageResourcesBaseForm frm)
	{
		return EditLanguageForm.EDIT_LANGUAGE_FORM;
	}

	@Override
	protected EditLanguageResourcesOperation createEditOperation(EditLanguageResourcesBaseForm frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(EditLanguageOperation.class);
	}
}
