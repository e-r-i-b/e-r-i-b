package com.rssl.phizic.web.common.mobile.cards;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.operations.card.GetCardIssuerInfoOperation;
import com.rssl.phizic.utils.MapUtil;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.Map;
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
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		GetCardIssuerByNumberMobileForm frm       = (GetCardIssuerByNumberMobileForm) form;
		GetCardIssuerInfoOperation      operation = createOperation(GetCardIssuerInfoOperation.class);

		Map valuesSource = MapUtil.getMap(GetCardIssuerByNumberMobileForm.CARD_NUMBER, frm.getCardNumber());
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(valuesSource), GetCardIssuerByNumberMobileForm.FORM);
		if (processor.process())
		{
			frm.setSbrfCardIssuer(operation.isSBRFCardIssuerBank(frm.getCardNumber()));
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}

		return mapping.findForward(FORWARD_START);
	}
}
