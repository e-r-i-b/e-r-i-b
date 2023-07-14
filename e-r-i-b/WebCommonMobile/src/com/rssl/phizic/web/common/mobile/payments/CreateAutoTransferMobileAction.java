package com.rssl.phizic.web.common.mobile.payments;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.web.actions.payments.forms.ViewDocumentAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Создание автоплатежа P2P
 * @author sergunin
 * @ created 13.02.2015
 * @ $Author$
 * @ $Revision$
 */
public class CreateAutoTransferMobileAction extends ViewDocumentAction
{
	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return makeAutoTransfer(mapping, form, request, response);
	}

	protected CreationType getDocumentCreationType()
	{
		return CreationType.mobile;
	}
}
