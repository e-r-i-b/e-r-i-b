package com.rssl.phizic.web.dictionaries.payment.services.locale;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.payment.services.locale.PaymentServiceResources;

import com.rssl.phizic.locale.dynamic.resources.LanguageResources;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.payment.services.locale.EditPaymentServicesResourcesOperation;
import com.rssl.phizic.operations.locale.dynamic.resources.EditLanguageResourcesOperation;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseAction;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseForm;

import java.util.Map;

/**
 * @author mihaylov
 * @ created 23.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Ёкшн редактировани€ локалезависмых данных услуги
 */
public class EditPaymentServiceResourcesAction extends EditLanguageResourcesBaseAction
{
	@Override
	protected void updateForm(EditLanguageResourcesBaseForm frm, LanguageResources entity) throws Exception
	{
		if(entity == null)
			return;
		PaymentServiceResources paymentServiceResources = (PaymentServiceResources) entity;
		frm.setField("localedName",paymentServiceResources.getName());
		frm.setField("localedDescription",paymentServiceResources.getDescription());
	}

	@Override
	protected void initializeOperation(EditLanguageResourcesOperation operation, EditLanguageResourcesBaseForm frm) throws BusinessLogicException, BusinessException
	{
		EditPaymentServicesResourcesOperation editOperation = (EditPaymentServicesResourcesOperation) operation;
		editOperation.initialize(frm.getStringId(), frm.getLocaleId());
	}

	@Override
	protected void updateFormOperationalData(EditLanguageResourcesBaseForm frm, EditLanguageResourcesOperation operation) throws Exception
	{
	}

	@Override
	protected void updateEntity(LanguageResources entity, Map<String, Object> data) throws Exception
	{
		PaymentServiceResources paymentServiceResources = (PaymentServiceResources) entity;
		paymentServiceResources.setName((String)data.get("localedName"));
		paymentServiceResources.setDescription((String) data.get("localedDescription"));
	}

	@Override
	protected void updateEntityOperationalData(EditEntityOperation operation, Map<String, Object> data) throws Exception
	{
	}

	@Override
	protected Form getEditForm(EditLanguageResourcesBaseForm frm)
	{
		return EditPaymentServiceResourcesForm.EDIT_FORM;
	}

	@Override
	protected EditLanguageResourcesOperation createEditOperation(EditLanguageResourcesBaseForm frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(EditPaymentServicesResourcesOperation.class);
	}
}
