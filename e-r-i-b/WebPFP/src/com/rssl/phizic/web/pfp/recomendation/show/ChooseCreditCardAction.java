package com.rssl.phizic.web.pfp.recomendation.show;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.products.card.recommendation.UseCreditCardRecommendation;
import com.rssl.phizic.operations.pfp.EditPfpOperationBase;
import com.rssl.phizic.operations.pfp.recommendation.show.ChooseCardOperation;
import com.rssl.phizic.web.pfp.PassingPFPFormInterface;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Выбор кредитной карты
 * @author komarov
 * @ created 07.08.13 
 * @ $Author$
 * @ $Revision$
 */

public class ChooseCreditCardAction  extends ShowRecommendationAction
{
	private static final String START = "Start";

	@Override
	protected EditPfpOperationBase getOperation(PassingPFPFormInterface form) throws BusinessException, BusinessLogicException
	{
		return getOperation(ChooseCardOperation.class, form);
	}

	@Override
	protected void updateForm(PassingPFPFormInterface form, EditPfpOperationBase operation) throws BusinessException, BusinessLogicException
	{
		super.updateForm(form, operation);
		ChooseCreditCardForm frm = (ChooseCreditCardForm)form;
		frm.setField("cardId", operation.getProfile().getCardId());
		updateFormAdditionalData(frm,operation);
	}

	//филды нужно обновлять и при сбое в валидации, поэтому выделяем отдельный метод
	private void updateFormAdditionalData(ChooseCreditCardForm frm, EditPfpOperationBase operation)
	{
		frm.setField("account", operation.getProfile().getUseAccount());
		if (operation.getProfile().getAccountValue() != null)
			frm.setField("accountIncome", operation.getProfile().getAccountValue());
		frm.setField("thanks",  operation.getProfile().getUseThanks());
		if (operation.getProfile().getThanksValue() != null)
			frm.setField("thanksPercent", operation.getProfile().getThanksValue());
		frm.setField("chooseCreditCard", operation.getProfile().getCardId() != null);
	}

	/**
	 * Перейти к следущему шагу планирования
	 * @param mapping mapping
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return форвард
	 * @throws Exception
	 */
	public ActionForward next(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ChooseCreditCardForm frm = (ChooseCreditCardForm) form;
		ChooseCardOperation operation = (ChooseCardOperation) getOperation(frm);
		UseCreditCardRecommendation recommendation = operation.getRecommendation();
		Form editForm = frm.createForm(recommendation.getAccountEfficacy(),recommendation.getThanksEfficacy());
		MapValuesSource mapValuesSource = new MapValuesSource(frm.getFields());

		FormProcessor<ActionMessages, ?> processor = createFormProcessor(mapValuesSource, editForm);
		Map<String, Object> result = new HashMap<String, Object>();
		if (processor.process())
		{
			result = processor.getResult();
			operation.setCardId((Long) result.get("cardId"));
			operation.getProfile().setUseAccount((Boolean) result.get("account"));
			operation.getProfile().setAccountValue((BigDecimal) result.get("accountIncome"));
			operation.getProfile().setUseThanks((Boolean) result.get("thanks"));
			operation.getProfile().setThanksValue((BigDecimal) result.get("thanksPercent"));
			operation.nextStep();
			return getRedirectForward(operation);
		}
		else
		{
			saveErrors(request, processor.getErrors());
			updateFormAdditionalData(frm,operation);
			return mapping.findForward(START);
		}
	}
}
