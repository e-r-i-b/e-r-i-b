package com.rssl.phizic.web.creditcards.products.locale;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.creditcards.products.locale.CreditCardProductResources;
import com.rssl.phizic.locale.dynamic.resources.LanguageResources;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.creditcards.products.locale.EditCreditCardProductResourceOperation;
import com.rssl.phizic.operations.locale.dynamic.resources.EditLanguageResourcesOperation;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseAction;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseForm;

import java.util.Map;

/**
 * Ёкшен редактировани€ мульти€зычных текстовок
 * @author komarov
 * @ created 22.05.2015
 * @ $Author$
 * @ $Revision$
 */
public class EditCreditCardProductResourcesAction extends EditLanguageResourcesBaseAction
{
	@Override
	protected void updateForm(EditLanguageResourcesBaseForm frm, LanguageResources entity) throws Exception
	{
		CreditCardProductResources resources = (CreditCardProductResources) entity;
	 	frm.setField(EditCreditCardProductResourcesForm.NAME_FIELD, resources.getName());
	 	frm.setField(EditCreditCardProductResourcesForm.ADDITIONAL_TERMS_FIELD, resources.getAdditionalTerms());
	}

	@Override
	protected void updateFormOperationalData(EditLanguageResourcesBaseForm frm, EditLanguageResourcesOperation operation) throws Exception {	}

	@Override
	protected void updateEntity(LanguageResources entity, Map<String, Object> data) throws Exception
	{
		CreditCardProductResources resources = (CreditCardProductResources) entity;
		resources.setName((String) data.get(EditCreditCardProductResourcesForm.NAME_FIELD));
		resources.setAdditionalTerms((String) data.get(EditCreditCardProductResourcesForm.ADDITIONAL_TERMS_FIELD));
	}

	@Override
	protected void updateEntityOperationalData(EditEntityOperation operation, Map<String, Object> data) throws Exception {	}

	@Override
	protected Form getEditForm(EditLanguageResourcesBaseForm frm)
	{
		return EditCreditCardProductResourcesForm.EDIT_FORM;
	}

	@Override
	protected EditLanguageResourcesOperation createEditOperation(EditLanguageResourcesBaseForm frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(EditCreditCardProductResourceOperation.class);
	}
}
