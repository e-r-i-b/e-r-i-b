package com.rssl.phizic.operations.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.payments.PersonalPaymentCard;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.billing.Billing;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessagingException;
import com.rssl.phizic.gate.payments.systems.recipients.PaymentRecipientGateService;

/**
 * @author Gainanov
 * @ created 22.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class GetPersonalPaymentCardOperation extends PersonalPaymentCardOperationBase
{
	private static final PaymentRecipientGateService paymentRecipientService
			= GateSingleton.getFactory().service(PaymentRecipientGateService.class);

	private Client cardOwner = null;

	/**
	 * �������������
	 * @param personId - ID ������������, ���� �������� �����
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(Long personId) throws BusinessException, BusinessLogicException
	{
		super.initialize(personId);

		readCardOwner();
	}

	/**
	 * �������������
	 * @param personId - ID ������������, ���� �������� �����
	 * @param cardId - ����� �����
	 * @param billingId - ID ����������� �������, ������ ������� �����
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(Long personId, String cardId, Long billingId)
			throws BusinessException, BusinessLogicException
	{
		super.initializeNew(personId, cardId, billingId);

		readCardOwner();
	}

	private void readCardOwner() throws BusinessException, BusinessLogicException
	{
		cardOwner = null;

		PersonalPaymentCard card = getCard();
		if (card == null)
			return;

		Billing billing = billingService.getById(card.getBillingId());
		if (billing == null)
			throw new BusinessException("�� ������ ������� �� ID " + card.getBillingId());

		try
		{
			cardOwner = paymentRecipientService.getCardOwner(card.getCardNumber(), billing);
		}
		catch(GateMessagingException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	/**
	 * @return ���������� � ��������� �����
	 * (can be null)
	 */
	public Client getCardOwner()
	{
		return cardOwner;
	}

	/**
	 * �������� �������������� ����� ������������
	 * @return true, ���� ����� ����������� ������������
	 */
	public boolean checkFIO()
	{
		String clientFIO = cardOwner.getFullName().replace(" ","");
		String personFIO = getPerson().getFullName().replace(" ","");
		return clientFIO.equalsIgnoreCase(personFIO);
	}
}
