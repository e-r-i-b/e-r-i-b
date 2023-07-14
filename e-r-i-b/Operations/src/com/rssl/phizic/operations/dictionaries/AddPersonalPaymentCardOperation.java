package com.rssl.phizic.operations.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.billing.Billing;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.payments.PersonalPaymentCard;

/**
 * @author Erkin
 * @ created 23.08.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ƒобавление карты персональных платежей
 */
public class AddPersonalPaymentCardOperation extends PersonalPaymentCardOperationBase
{
	/**
	 * »нициализаци€
	 * @param personId - ID пользовател€, кому добавить карту
	 * @param cardNumber - номер карты
	 * @param billingId - ID биллинговой системы, откуда вз€лась карта
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(Long personId, String cardNumber, Long billingId)
			throws BusinessException, BusinessLogicException
	{
		super.initializeNew(personId, cardNumber, billingId);
	}

	/**
	 * —охранение изменений
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	@Transactional
	public void save() throws BusinessException, BusinessLogicException
	{
		PersonalPaymentCard card = getCard();
		if (card == null)
			throw new IllegalStateException("card cannot be null");

		Billing billing = billingService.getById(card.getBillingId());
		if (billing == null)
			throw new BusinessException("Ќе найден биллинг по ID " + card.getBillingId());

		requirePersonalPaymentsAvailable(billing);

		externalResourceService.addPaymentSystemIdLink(
				getPerson().getLogin(), card.getCardNumber(), billing, getInstanceName());
	}
}
