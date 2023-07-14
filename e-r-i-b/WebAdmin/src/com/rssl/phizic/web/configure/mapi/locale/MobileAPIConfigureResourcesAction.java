package com.rssl.phizic.web.configure.mapi.locale;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author koptyaev
 * @ created 09.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class MobileAPIConfigureResourcesAction extends EditPropertiesActionBase
{

	@Override
	protected EditPropertiesOperation getEditOperation() throws BusinessException
	{
		return createOperation("MobileApiSettingsOperation");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditFormBase frm = (EditFormBase) form;
		EditEntityOperation operation = createEditOperation(frm);
		FormProcessor<ActionMessages, ?> processor = getFormProcessor(frm, operation);
		try
		{
			if (processor.process())
			{
				Object entity = operation.getEntity();
				Map<String, Object> result = processor.getResult();
				updateEntity(entity, result);
				updateOperationAdditionalData(operation, frm, result);
				doSave(operation, mapping, frm);
			}
			else
			{
				saveErrors(request, processor.getErrors());
			}
			updateFormAdditionalData(frm, operation);
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
			updateFormAdditionalData(frm, createViewOperation(frm));

		}
		return getCurrentMapping().findForward(FORWARD_SUCCESS);
	}

	protected boolean isAjax()
	{
		return true;
	}
}
