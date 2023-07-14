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
 * ���������� ����� ������������ ��������
 */
public class AddPersonalPaymentCardOperation extends PersonalPaymentCardOperationBase
{
	/**
	 * �������������
	 * @param personId - ID ������������, ���� �������� �����
	 * @param cardNumber - ����� �����
	 * @param billingId - ID ����������� �������, ������ ������� �����
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(Long personId, String cardNumber, Long billingId)
			throws BusinessException, BusinessLogicException
	{
		super.initializeNew(personId, cardNumber, billingId);
	}

	/**
	 * ���������� ���������
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
			throw new BusinessException("�� ������ ������� �� ID " + card.getBillingId());

		requirePersonalPaymentsAvailable(billing);

		externalResourceService.addPaymentSystemIdLink(
				getPerson().getLogin(), card.getCardNumber(), billing, getInstanceName());
	}
}
