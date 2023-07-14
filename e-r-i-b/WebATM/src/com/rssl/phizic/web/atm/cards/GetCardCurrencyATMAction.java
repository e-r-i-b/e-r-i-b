package com.rssl.phizic.web.atm.cards;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.validators.strategy.DefaultValidationStrategy;
import com.rssl.phizic.operations.card.GetExternalCardCurrencyOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Dorzhinov
 * @ created 30.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class GetCardCurrencyATMAction extends OperationalActionBase
{
	private static final String FORWARD_START = "Start";

	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		GetCardCurrencyATMForm frm = (GetCardCurrencyATMForm) form;

		FormProcessor<ActionMessages, ?> processor = createFormProcessor(getMapValuesSource(frm),
				GetCardCurrencyATMForm.CARD_CURRENCY_FORM,  DefaultValidationStrategy.getInstance());

		if(processor.process())
		{
			Map<String, Object> result = processor.getResult();
			String cardNumber = (String) result.get(GetCardCurrencyATMForm.CARD_NUMBER_FIELD_NAME);
			String phoneNumber = (String) result.get(GetCardCurrencyATMForm.PHONE_NUMBER_FIELD_NAME);

			GetExternalCardCurrencyOperation operation = createOperation("GetExternalCardCurrencyOperation", "ExternalCardCurrency");

			if(!StringHelper.isEmpty(cardNumber))
				operation.initialize(cardNumber);
			else if(!StringHelper.isEmpty(phoneNumber))
				operation.initializeByPhone(phoneNumber);

			frm.setCard(operation.getEntity());
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}
		return mapping.findForward(FORWARD_START);
	}

	private MapValuesSource getMapValuesSource(GetCardCurrencyATMForm frm)
	{
		Map<String,Object> filter = new HashMap<String,Object>();
		if(frm.getCardNumber() != null)
	        filter.put(GetCardCurrencyATMForm.CARD_NUMBER_FIELD_NAME, frm.getCardNumber());
		if(frm.getPhoneNumber() != null)
	        filter.put(GetCardCurrencyATMForm.PHONE_NUMBER_FIELD_NAME, frm.getPhoneNumber());
	    return new MapValuesSource(filter);
	}
}
