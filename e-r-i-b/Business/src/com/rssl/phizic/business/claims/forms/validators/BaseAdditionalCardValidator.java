package com.rssl.phizic.business.claims.forms.validators;

import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.BankrollServiceHelper;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;

import java.util.Map;
import java.util.List;

/**
 * Определяет является ли дополнительная карта дополнительной для основной
 * @author eMakarov
 * @ created 23.11.2007
 * @ $Author$
 * @ $Revision$
 */
public class BaseAdditionalCardValidator extends MultiFieldsValidatorBase
{
	public static final String FIELD_BASECARD = "baseCard";
	public static final String FIELD_ADDITCARD = "additCard";
	private static final String ERROR_RECIVE_CARD = "Ошибка при получении списка доп. карт по карте ";

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public boolean validate(Map values) throws TemporalDocumentException
	{
		BankrollServiceHelper bankrollHelper = new BankrollServiceHelper();

		Card baseCard = null;
		Card additCard = null;

		try
		{
			baseCard = getCard(FIELD_BASECARD, values);
			additCard = getCard(FIELD_ADDITCARD, values);
		}
		catch (BusinessLogicException e)
		{
			return false;
		}
		catch (BusinessException e)
		{
			return false;
		}

		try
		{
			//todo. ВРЕМЕННОЕ решение. После исправления шлюза к WAY4, вернуть на прямое получение карт из банкролла.			
			GroupResult<Card, List<Card>> cardsGroup = bankrollHelper.getAdditionalCards(baseCard);
			return GroupResultHelper.getOneResult(cardsGroup).contains(additCard);
		}
		catch (IKFLException ex)
		{
			String message1 = ERROR_RECIVE_CARD + baseCard.getNumber();
			log.error(message1,ex);
			throw new TemporalDocumentException(message1,ex);
		}
	}

	private Card getCard(String validatorField, Map values) throws BusinessException, BusinessLogicException
	{
		return (Card) retrieveFieldValue(validatorField, values);
	}
}
