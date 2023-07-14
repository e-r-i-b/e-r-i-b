package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author hudyakov
 * @ created 23.01.2010
 * @ $Author$
 * @ $Revision$
 */

public class EditComissionServiceProviderAction extends EditServiceProvidersActionBase
{
	protected Form getEditForm(EditFormBase frm)
	{
		return EditCommissionServiceProviderForm.COMISSION_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		BillingServiceProvider provider = (BillingServiceProvider) entity;
		provider.setMinComissionAmount((BigDecimal) data.get("minCommission"));
		provider.setMaxComissionAmount((BigDecimal) data.get("maxCommission"));
		provider.setComissionRate((BigDecimal) data.get("rate"));
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		BillingServiceProvider provider = (BillingServiceProvider) entity;

		frm.setField("minCommission", provider.getMinComissionAmount());
		frm.setField("maxCommission", provider.getMaxComissionAmount());
		frm.setField("rate", provider.getComissionRate());
	}
	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm)
	{
		saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("com.rssl.phizic.web.dictionaries.provider.save.success"), null);
		return super.createSaveActionForward(operation, frm);
	}
}
