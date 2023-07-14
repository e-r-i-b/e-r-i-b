package com.rssl.phizic.business.payments.forms.meta.conditions;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.cardProduct.CardProduct;
import com.rssl.phizic.business.cardProduct.CardProductService;
import com.rssl.phizic.business.cardProduct.CardProductType;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.documents.payments.VirtualCardClaim;
import com.rssl.phizic.business.ext.sbrf.dictionaries.CASNSICardProduct;
import com.rssl.phizic.business.statemachine.StateObjectCondition;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.utils.DateHelper;

/**
 * @author Mescheryakova
 * @ created 13.10.2011
 * @ $Author$
 * @ $Revision$
 *
 * Проверка актуальности выбранной в заявке виртуальной карты
 */

public class VirtualCardClaimCondition implements StateObjectCondition
{
	private static final CardProductService cardProductService = new CardProductService();

	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		if (!(stateObject instanceof VirtualCardClaim))
			return false;

		boolean isCheckResult = checkVirtualCardClaim((VirtualCardClaim) stateObject);
		if (isCheckResult)
			((BusinessDocumentBase) stateObject).setRefusingReason("Условия по продукту изменились");  // сообщение для причины отказа аудите сотрудника
		
		return  isCheckResult;
	}

	private boolean checkVirtualCardClaim(VirtualCardClaim document) throws BusinessException
	{
		// получаем id кредитного продукта из документа
		Long cardProductId = document.getCardProductId();
		if (cardProductId == null)
			return false;

		// ищем кредитный продукт у нас в БД, проверяем, что он разрешен для просмотра и типа - вирт. карта и название такое же и дата не просрочена
		CardProduct cardProduct = cardProductService.findLocaledById(cardProductId);
		if (cardProduct == null)
			return true;

		boolean isOnline = cardProduct.getOnline();    // продукт доступен клиенту
		boolean isVirtualType = (cardProduct.getType() == CardProductType.VIRTUAL);  // это виртуальная карта
		boolean isNameEquals =  cardProduct.getName().equals(document.getNameCardProduct()); // имя продукта в БД и на форме одинаковые
		boolean isExpirationDate = (DateHelper.diff(cardProduct.getStopOpenDate(), DateHelper.getCurrentDate()) < 0); // дата закрытия вида карточного продукта наступила
		boolean isKindsProductExists = cardProduct.getKindOfProducts().isEmpty(); // у продукта нет видов карт

		if (!isOnline || !isVirtualType	|| !isNameEquals || isExpirationDate || isKindsProductExists)
			return true;

		boolean isKindWrong = true;
		for (CASNSICardProduct casnsiCardProduct : cardProduct.getKindOfProducts())
		{
			boolean isKindIdEquals = casnsiCardProduct.getProductId().equals(document.getKindCardProduct()); // виды продукта, выбранный на форме, равен виду из карточного продукта
			boolean isSubKindIdEqals = casnsiCardProduct.getProductSubId().equals(document.getSubKindCardProduct());  // подвиды с формы и из БД д. б. равны
			Currency currency = casnsiCardProduct.getCurrency();
			boolean isCurrencyCorrect = false;
			if (currency != null) {
				boolean isCurrencyNumberCorrect = currency.getNumber().equals(document.getCurrencyCodeCardProduct());
				boolean isCurrensyCodeCorrect = currency.getCode().equals(document.getCurrencyNameCardProduct());
				isCurrencyCorrect = isCurrencyNumberCorrect || isCurrensyCodeCorrect;
			}
			boolean isExpirationDateSubKind =  (DateHelper.diff(casnsiCardProduct.getStopOpenDeposit(), DateHelper.getCurrentDate()) > 0);   // дата закрытия вида карточного продукта еще не наступила

			// нашли нужный карточный вид и он впорядке, выходим
			if (isKindIdEquals	&&  isSubKindIdEqals && isCurrencyCorrect && isExpirationDateSubKind)
			{
				isKindWrong = false;
				break;
			}
		}
		return isKindWrong;
	}
}
