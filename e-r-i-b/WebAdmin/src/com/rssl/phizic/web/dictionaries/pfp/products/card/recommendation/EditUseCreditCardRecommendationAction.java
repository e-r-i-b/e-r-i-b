package com.rssl.phizic.web.dictionaries.pfp.products.card.recommendation;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.products.card.recommendation.ProductEfficacy;
import com.rssl.phizic.business.dictionaries.pfp.products.card.recommendation.UseCreditCardRecommendation;
import com.rssl.phizic.business.dictionaries.pfp.products.card.recommendation.UseCreditCardRecommendationStep;
import com.rssl.phizic.business.dictionaries.pfp.products.card.Card;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.pfp.products.card.recommendation.EditUseCreditCardRecommendationOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author akrenev
 * @ created 04.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Ёкшен редактировани€ настроек рекоммендаций
 */

public class EditUseCreditCardRecommendationAction extends EditActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditUseCreditCardRecommendationOperation operation = createOperation(EditUseCreditCardRecommendationOperation.class);
		operation.initialize();
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return ((EditUseCreditCardRecommendationForm) frm).createForm();
	}

	private void addEfficacyParameters(EditFormBase frm, ProductEfficacy efficacy, String productType)
	{
		frm.setField(productType.concat(EditUseCreditCardRecommendationForm.FROM_INCOME_FIELD_NAME_SUFFIX),    efficacy.getFromIncome());
		frm.setField(productType.concat(EditUseCreditCardRecommendationForm.TO_INCOME_FIELD_NAME_SUFFIX),      efficacy.getToIncome());
		frm.setField(productType.concat(EditUseCreditCardRecommendationForm.DEFAULT_INCOME_FIELD_NAME_SUFFIX), efficacy.getDefaultIncome());
		frm.setField(productType.concat(EditUseCreditCardRecommendationForm.DESCRIPTION_FIELD_NAME_SUFFIX),    efficacy.getDescription());
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		UseCreditCardRecommendation recommendation = (UseCreditCardRecommendation) entity;
		List<UseCreditCardRecommendationStep> steps = recommendation.getSteps();
		for (int i = 0; i < EditUseCreditCardRecommendationForm.getMaxRecommendationStepCount(); i++)
		{
			UseCreditCardRecommendationStep step = steps.get(i);
			String index = String.valueOf(i);
			frm.setField(EditUseCreditCardRecommendationForm.STEP_NAME_FIELD_NAME_PREFIX.concat(index),        step.getName());
			frm.setField(EditUseCreditCardRecommendationForm.STEP_DESCRIPTION_FIELD_NAME_PREFIX.concat(index), step.getDescription());
		}
		addEfficacyParameters(frm, recommendation.getAccountEfficacy(), EditUseCreditCardRecommendationForm.ACCOUNT_TYPE);
		addEfficacyParameters(frm, recommendation.getThanksEfficacy(),  EditUseCreditCardRecommendationForm.THANKS_TYPE);
		frm.setField(EditUseCreditCardRecommendationForm.RECOMMENDATION_FIELD_NAME, recommendation.getRecommendation());
		List<Card> cards = recommendation.getCards();
		Long[] ids = new Long[cards.size()];
		int i = 0;
		for (Card card: cards)
		{
			Long id = card.getId();
			ids[i++] = id;
			frm.setField(EditUseCreditCardRecommendationForm.CARD_NAME_FIELD_NAME_PREFIX.concat(String.valueOf(id)), card.getName());
		}
		((EditUseCreditCardRecommendationForm) frm).setCardProductIds(ids);
	}

	private void updateProductEfficacy(Map<String, Object> data, ProductEfficacy efficacy, String productType) throws Exception
	{
		efficacy.setFromIncome((BigDecimal) data.get(productType.concat(EditUseCreditCardRecommendationForm.FROM_INCOME_FIELD_NAME_SUFFIX)));
		efficacy.setToIncome((BigDecimal) data.get(productType.concat(EditUseCreditCardRecommendationForm.TO_INCOME_FIELD_NAME_SUFFIX)));
		efficacy.setDefaultIncome(((BigDecimal) data.get(productType.concat(EditUseCreditCardRecommendationForm.DEFAULT_INCOME_FIELD_NAME_SUFFIX))));
		efficacy.setDescription((String) data.get(productType.concat(EditUseCreditCardRecommendationForm.DESCRIPTION_FIELD_NAME_SUFFIX)));
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		UseCreditCardRecommendation recommendation = (UseCreditCardRecommendation) entity;
		List<UseCreditCardRecommendationStep> steps = recommendation.getSteps();
		for (int i = 0; i < EditUseCreditCardRecommendationForm.getMaxRecommendationStepCount(); i++)
		{
			UseCreditCardRecommendationStep step = steps.get(i);
			String index = String.valueOf(i);
			step.setName((String) data.get(EditUseCreditCardRecommendationForm.STEP_NAME_FIELD_NAME_PREFIX.concat(index)));
			step.setDescription((String) data.get(EditUseCreditCardRecommendationForm.STEP_DESCRIPTION_FIELD_NAME_PREFIX.concat(index)));
		}

		updateProductEfficacy(data, recommendation.getAccountEfficacy(), EditUseCreditCardRecommendationForm.ACCOUNT_TYPE);
		updateProductEfficacy(data, recommendation.getThanksEfficacy(), EditUseCreditCardRecommendationForm.THANKS_TYPE);
		recommendation.setRecommendation((String) data.get(EditUseCreditCardRecommendationForm.RECOMMENDATION_FIELD_NAME));
	}

	@Override
	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		super.updateOperationAdditionalData(editOperation, editForm, validationResult);
		EditUseCreditCardRecommendationOperation operation = (EditUseCreditCardRecommendationOperation) editOperation;
		EditUseCreditCardRecommendationForm form = (EditUseCreditCardRecommendationForm) editForm;
		operation.setCards(form.getCardProductIds());
	}
}
