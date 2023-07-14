package com.rssl.phizic.web.common.socialApi.cards;

import com.rssl.phizic.operations.card.GetCardIssuerInfoOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.actions.StrutsUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Для получения информации о банке-эмитенте карты по её номеру
 * @ author: Gololobov
 * @ created: 24.06.14
 * @ $Author$
 * @ $Revision$
 */
public class GetCardIssuerByNumberMobileAction extends OperationalActionBase
{
	private static final String INPUT_PARAMS_ERROR_MSG = "message.payment.operation.inputparams.error.message";

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		GetCardIssuerByNumberMobileForm frm = (GetCardIssuerByNumberMobileForm) form;
		GetCardIssuerInfoOperation operation = createOperation(GetCardIssuerInfoOperation.class);
		if (StringHelper.isEmpty(frm.getCardNumber()))
		{
			saveError(request, StrutsUtils.getMessage(INPUT_PARAMS_ERROR_MSG, "paymentsBundle"));
		}
		else
		{
			boolean isSBRFCardIssuerBank = operation.isSBRFCardIssuerBank(frm.getCardNumber());
			frm.setSbrfCardIssuer(isSBRFCardIssuerBank);
		}
		return mapping.findForward(FORWARD_START);
	}
}
