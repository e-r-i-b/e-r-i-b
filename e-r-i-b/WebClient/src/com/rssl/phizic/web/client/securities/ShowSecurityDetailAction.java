package com.rssl.phizic.web.client.securities;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.resources.external.AbstractStoredResource;
import com.rssl.phizic.business.resources.external.StoredResourceMessages;
import com.rssl.phizic.business.resources.external.security.SecurityAccountLink;
import com.rssl.phizic.gate.security.SecurityAccount;
import com.rssl.phizic.operations.account.EditExternalLinkOperation;
import com.rssl.phizic.operations.securities.GetSecurityDetailOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lukina
 * @ created 05.09.13
 * @ $Author$
 * @ $Revision$
 */

public class ShowSecurityDetailAction extends OperationalActionBase
{
	private static final String SECURITY_NAME_FIELD = "securityName";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> keyMap = new HashMap<String, String>();
		keyMap.put("button.saveSecurityName", "saveSecurityName");
		return keyMap;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		GetSecurityDetailOperation operation = createOperation(GetSecurityDetailOperation.class);
		ShowSecurityDetailForm frm = (ShowSecurityDetailForm) form;
		operation.initialize(frm.getId());

		SecurityAccountLink link = operation.getEntity();
		frm.setLink(link);
		frm.setField(SECURITY_NAME_FIELD, link.getName());
		SecurityAccount securityAccount = operation.getSecurityAccount();
		if (operation.isBackError())
		{
			frm.setBackError(true);
			saveMessage(currentRequest(), "Информация по сберегательным сертификатам из АБС временно недоступна. Повторите операцию позже.");
		}
		if (operation.isUseStoredResource())
			saveInactiveESMessage(currentRequest(), StoredResourceMessages.getUnreachableMessageSystem((AbstractStoredResource) securityAccount));

		frm.setSecurityAccount(securityAccount);
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward saveSecurityName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowSecurityDetailForm frm = (ShowSecurityDetailForm) form;
		Long linkId = frm.getId();
		EditExternalLinkOperation operation = createOperation(EditExternalLinkOperation.class);
		operation.initialize(linkId, SecurityAccountLink.class);

		Map<String, Object> fields = frm.getFields();
		FieldValuesSource valuesSource = new MapValuesSource(fields);

		Form editForm = ShowSecurityDetailForm.EDIT_SECURITY_NAME_FORM;
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, editForm);

		if(processor.process())
		{
			operation.saveLinkName((String)frm.getField(SECURITY_NAME_FIELD));
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}
		return start(mapping, form, request, response);
	}
}
