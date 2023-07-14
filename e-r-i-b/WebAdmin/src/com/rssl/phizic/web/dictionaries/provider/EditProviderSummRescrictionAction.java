package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.SumRestrictions;
import com.rssl.phizic.web.common.EditFormBase;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author bogdanov
 * @ created 09.04.2012
 * @ $Author$
 * @ $Revision$
 *
 * редактирование ограничений на сумму операции для поставщика.
 */

public class EditProviderSummRescrictionAction extends EditServiceProvidersActionBase
{
	protected Form getEditForm(EditFormBase frm)
	{
		return EditProviderSummRestrictionForm.SUMM_RESTRICTION_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		ServiceProviderBase provider = (ServiceProviderBase) entity;
		
		SumRestrictions restrictions = new SumRestrictions();
		restrictions.setMinimumSum((BigDecimal)data.get(EditProviderSummRestrictionForm.MIN_SUM_RESTR));
		restrictions.setMaximumSum((BigDecimal)data.get(EditProviderSummRestrictionForm.MAX_SUM_RESTR));

		provider.setRestrictions(restrictions);
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		ServiceProviderBase provider = (ServiceProviderBase) entity;

		if (provider.getRestrictions() != null)
		{
			frm.setField(EditProviderSummRestrictionForm.MIN_SUM_RESTR, provider.getRestrictions().getMinimumSum());
			frm.setField(EditProviderSummRestrictionForm.MAX_SUM_RESTR, provider.getRestrictions().getMaximumSum());
		}
	}
}
