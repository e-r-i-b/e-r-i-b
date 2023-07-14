package com.rssl.phizic.web.client.promocodes;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.promoCodesDeposit.WrongPromoCodeLogicException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.promoCodesDeposit.PromoCodesDepositConfig;
import com.rssl.phizic.config.promoCodesDeposit.PromoCodesMessage;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.promo.InsertPromoCodesSystemOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.AsyncOperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Добавление промокода со страницы "Вклады"
 * @author Jatsky
 * @ created 23.12.14
 * @ $Author$
 * @ $Revision$
 */

public class AddPromoCodeAction extends AsyncOperationalActionBase
{
	private static final String OPEN_ACCOUNT_FORWARD = "OpenAccount";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> keyMethodMap = new HashMap<String, String>();
		keyMethodMap.put("button.save", "save");
		return keyMethodMap;
	}

	@Override public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return null;
	}

	/**
	 * сохраняет бюджет
	 * @param mapping мапинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return форвард
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AddPromoCodeForm frm = (AddPromoCodeForm) form;

		FieldValuesSource valuesSource = new MapValuesSource(frm.getFields());

		Form newCategoryForm = AddPromoCodeForm.ADD_PROMOCODE_FORM;

		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, newCategoryForm);
		if (processor.process())
		{
			InsertPromoCodesSystemOperation operation =  createOperation(InsertPromoCodesSystemOperation.class,  "ClientPromoCode");
			Map<String,Object> result = processor.getResult();
			PromoCodesDepositConfig promoCodesDepositConfig = ConfigFactory.getConfig(PromoCodesDepositConfig.class);
			try
			{
				operation.initializeInsert((String) result.get("promoCode"));
				operation.save();
			}
			catch (WrongPromoCodeLogicException e)
			{
				Calendar promoBlockUntil = PersonContext.getPersonDataProvider().getPersonData().getPromoBlockUntil();
				if (promoBlockUntil == null)
					frm.setErrorMessage(e.getPromoCodesMessage());
				else
				{
					String dateText = DateHelper.formatDateToStringOnPattern(promoBlockUntil, "HH:mm dd.MM.yyyy");
					PromoCodesMessage msg011 = promoCodesDepositConfig.getPromoCodesMessagesMap().get("MSG011").clone();
					msg011.setText(String.format(msg011.getText(), dateText));
					frm.setErrorMessage(msg011);
					PromoCodesMessage msg12 = promoCodesDepositConfig.getPromoCodesMessagesMap().get("MSG012").clone();
					msg12.setText(String.format(msg12.getText(), dateText));
					frm.setError12Message(msg12);
				}
			}
			catch (BusinessLogicException e)
			{
				   frm.setErrorMessage(promoCodesDepositConfig.getPromoCodesMessagesMap().get("MSG03"));
			}

			return mapping.findForward(FORWARD_START);
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}
		return mapping.findForward(OPEN_ACCOUNT_FORWARD);
	}
}
