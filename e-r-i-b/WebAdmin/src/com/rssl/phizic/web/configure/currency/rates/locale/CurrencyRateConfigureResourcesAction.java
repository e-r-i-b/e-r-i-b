package com.rssl.phizic.web.configure.currency.rates.locale;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.tariffPlan.locale.TariffPlanConfigResources;
import com.rssl.phizic.locale.dynamic.resources.LanguageResources;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.config.locale.CurrencyRateConfigureResourcesOperation;
import com.rssl.phizic.operations.locale.dynamic.resources.EditLanguageResourcesOperation;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseAction;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseForm;

import java.util.Map;

/**
 * Ёкшен редактировани€ много€зычных текстовок
 * @author komarov
 * @ created 08.06.2015
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyRateConfigureResourcesAction extends EditLanguageResourcesBaseAction
{
	@Override
	protected void updateForm(EditLanguageResourcesBaseForm frm, LanguageResources entity) throws Exception
	{
		TariffPlanConfigResources resource = (TariffPlanConfigResources)entity;
		frm.setField(CurrencyRateConfigureListResourcesForm.PRIVILEGED_RATE_MESSAGE, resource.getPrivilegedRateMessage());
	}

	@Override
	protected void updateFormOperationalData(EditLanguageResourcesBaseForm frm, EditLanguageResourcesOperation operation) throws Exception
	{

	}

	@Override
	protected void updateEntity(LanguageResources entity, Map<String, Object> data) throws Exception
	{
		TariffPlanConfigResources resource = (TariffPlanConfigResources)entity;
		resource.setPrivilegedRateMessage((String)data.get(CurrencyRateConfigureListResourcesForm.PRIVILEGED_RATE_MESSAGE));
	}

	@Override
	protected void updateEntityOperationalData(EditEntityOperation operation, Map<String, Object> data) throws Exception
	{

	}

	@Override
	protected Form getEditForm(EditLanguageResourcesBaseForm frm)
	{
		return CurrencyRateConfigureListResourcesForm.EDIT_FORM;
	}

	@Override
	protected EditLanguageResourcesOperation createEditOperation(EditLanguageResourcesBaseForm frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(CurrencyRateConfigureResourcesOperation.class);
	}
}
