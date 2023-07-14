package com.rssl.phizic.web.atm.common.payments;

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
 * ������� �������� �����������
 * @author sergunin
 * @ created 21.01.2014
 * @ $Author$
 * @ $Revision$
 */
public class MakeLongOfferATMAction extends ViewDocumentAction
{
	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return makeLongOffer(mapping, form, request, response);
	}

	protected CreationType getDocumentCreationType()
	{
		return CreationType.atm;
	}
}
