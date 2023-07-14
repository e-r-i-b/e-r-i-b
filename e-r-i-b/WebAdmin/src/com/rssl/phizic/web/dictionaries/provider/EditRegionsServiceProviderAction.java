package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.provider.EditServiceProvidersOperation;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author khudyakov
 * @ created 23.01.2010
 * @ $Author$
 * @ $Revision$
 */

public class EditRegionsServiceProviderAction extends EditServiceProvidersActionBase
{
	protected Map<String, String>getAdditionalKeyMethodMap()
	{
		Map<String, String> map = super.getAdditionalKeyMethodMap();
		map.put("button.remove", "remove");
		return map;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{

	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{

	}

	protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation operation) throws Exception
	{
		super.updateFormAdditionalData(form, operation);
		EditRegionsServiceProviderForm frm = (EditRegionsServiceProviderForm) form;
		ServiceProviderBase provider = (ServiceProviderBase) operation.getEntity();
		frm.setRegions(provider.getRegions());
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase form, Map<String, Object> validationResult) throws Exception
	{
		EditRegionsServiceProviderForm frm = (EditRegionsServiceProviderForm) form;
		EditServiceProvidersOperation op = (EditServiceProvidersOperation) editOperation;
	    op.setRegions(StrutsUtils.parseIds(frm.getNewIds()));
	}

	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditRegionsServiceProviderForm frm = (EditRegionsServiceProviderForm) form;
		EditServiceProvidersOperation operation = createEditOperation(frm);
		try
		{
			operation.removeRegions(StrutsUtils.parseIds(frm.getSelectedIds()));
			operation.save();

			addLogParameters(new BeanLogParemetersReader("Данные редактируемой сущности", operation.getEntity()));
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
			return createStartActionForward(frm, mapping);
		}
		finally
		{
			updateFormAdditionalData(frm, operation);
		}
		return createStartActionForward(frm, mapping);
	}
}
