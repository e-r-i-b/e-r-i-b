package com.rssl.phizic.web.common.socialApi.cards;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.validators.strategy.DefaultValidationStrategy;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.card.GetExternalCardCurrencyOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
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
public class GetCardCurrencyMobileAction extends OperationalActionBase
{
	private static final String FORWARD_START = "Start";

	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		GetCardCurrencyMobileForm frm = (GetCardCurrencyMobileForm) form;

		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new RequestValuesSource(request), GetCardCurrencyMobileForm.CARD_CURRENCY_FORM);

		if(processor.process())
		{
			Map<String, Object> result = processor.getResult();
			String cardNumber = (String) result.get(GetCardCurrencyMobileForm.CARD_NUMBER_FIELD_NAME);
			String phoneNumber = (String) result.get(GetCardCurrencyMobileForm.PHONE_NUMBER_FIELD_NAME);
			Long dictFieldId = (Long) result.get(GetCardCurrencyMobileForm.DICT_FIELD_ID_FIELD_NAME);

			GetExternalCardCurrencyOperation operation = createOperation("GetExternalCardCurrencyOperation", "ExternalCardCurrency");

			try
			{
				if(!StringHelper.isEmpty(cardNumber))
					operation.initialize(cardNumber);
				else if(!StringHelper.isEmpty(phoneNumber))
					operation.initializeByPhone(phoneNumber);
				else if (dictFieldId != null)
					operation.initializeByDictId(dictFieldId);

				frm.setCard(operation.getEntity());
			}
			catch (BusinessLogicException e)
			{
				saveError(request, e.getMessage());
			}
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}
		return mapping.findForward(FORWARD_START);
	}
}
