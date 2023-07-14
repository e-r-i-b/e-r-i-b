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
 * Быстрое создание автоплатежа
 * @author Dorzhinov
 * @ created 25.09.2013
 * @ $Author$
 * @ $Revision$
 */
public class MakeLongOfferMobileAction extends ViewDocumentAction
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
		return CreationType.mobile;
	}
}
