package com.rssl.phizic.business.creditcards;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.cardProduct.CardProductService;
import com.rssl.phizic.business.cardProduct.CardProductType;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;

/**
 * @author gulov
 * @ created 19.10.2011
 * @ $Authors$
 * @ $Revision$
 */

/**
 * ћетоды дл€ работы с карточными продуктами
 */
public final class CardProductUtils
{
	private static final String UNDEFINED_CARD_PRODUCT = "ќшибка получени€ количества активных карточных продуктов";
	private static final String UNKNOWN_CARD_PRODUCT_TYPE = "Ќеизвестный тип карточного продукта";

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final CardProductService cardProductService = new CardProductService();

	/**
	 * ѕровер€ет существование активных карточных продуктов
	 * @param type - тип продукта
	 * @return - true - существует, false - нет
	 */
	public static boolean activeCardProductExists(final String type)
	{
		try
		{
			return cardProductService.activeCardProductExists(cardProductType(type), true, DateHelper.getCurrentDate());
		}
		catch (BusinessException e)
		{
			log.error(UNDEFINED_CARD_PRODUCT, e);
			return false;
		}
	}

	private static CardProductType cardProductType(String type) throws BusinessException
	{
		if (CardProductType.VIRTUAL.name().equals(type))
			return CardProductType.VIRTUAL;
		else
			throw new BusinessException(UNKNOWN_CARD_PRODUCT_TYPE);
	}
}
