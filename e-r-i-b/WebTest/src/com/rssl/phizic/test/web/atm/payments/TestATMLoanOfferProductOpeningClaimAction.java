package com.rssl.phizic.test.web.atm.payments;

import com.rssl.phizic.business.test.atm.generated.ResponseElement;
import com.rssl.phizic.utils.StringHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Moshenko
 * Date: 16.03.12
 * Time: 12:06
 * Тэстилка оформления заявок на открытие вклада
 */
public class TestATMLoanOfferProductOpeningClaimAction extends TestATMDocumentAction
{
	protected static final String FORWARD_INIT = "Init";
	protected static final String FORWARD_STAGE = "Stage";
	protected static final String FORWARD_CONDITIONS_AND_REGISTRATION = "ConditionsAndRegistration";

	public ActionForward execute(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		TestATMDocumentForm form = (TestATMDocumentForm) frm;
		String documentStatus = form.getDocumentStatus();
		String operation = form.getOperation();

		if (StringHelper.isEmpty(documentStatus))
			documentStatus = "INITIAL";

		if (documentStatus.equals("INITIAL"))
		{
			if (StringHelper.isEmpty(operation))
				return mapping.findForward(FORWARD_INIT);

			return submitInit(mapping, form);
		}

		return null;
	}

	protected ActionForward submitInit(ActionMapping mapping, TestATMDocumentForm form)
	{
		int stateCode = send(form);
		if (stateCode == 0 || stateCode == 1)
		{
			if (form.getResponse() instanceof ResponseElement)
			{
				ResponseElement	respMob4 = (ResponseElement) form.getResponse();
				if (respMob4.getLoanOfferStage()!=null)
					return mapping.findForward(FORWARD_STAGE);
				else if (respMob4.getInitialData() != null && respMob4.getInitialData().getLoanOffer()!=null)
					return mapping.findForward(FORWARD_CONDITIONS_AND_REGISTRATION);
				else if (respMob4.getDocument() != null && respMob4.getDocument().getLoanOfferDocument() != null)
					return mapping.findForward(FORWARD_CONDITIONS_AND_REGISTRATION);
			}
		}
		return  mapping.findForward(FORWARD_INIT);
	}

}