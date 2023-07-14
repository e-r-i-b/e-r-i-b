package com.rssl.phizic.web.client.favourite;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.operations.payment.ListTemplatesOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.common.types.documents.FormType;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mihaylov
 * @ created 28.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class ListTemplatesAction extends OperationalActionBase
{
	protected final Map<String, String> getKeyMethodMap()
	{
		Map<String,String> map = new HashMap<String, String>();
		map.put("button.filter", "start");
		return map;
	}

	public final ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListTemplatesForm frm = (ListTemplatesForm) form;
		ListTemplatesOperation operation = createOperation(ListTemplatesOperation.class);

		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new RequestValuesSource(request), ListTemplatesForm.LIST_FORM);
		if (processor.process())
		{
			operation.initialize(FormType.valueOf((String) processor.getResult().get("formType")), CreationType.internet);
			frm.setTemplates(operation.getEntity());
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}

		return mapping.findForward(FORWARD_SHOW);
	}
}
