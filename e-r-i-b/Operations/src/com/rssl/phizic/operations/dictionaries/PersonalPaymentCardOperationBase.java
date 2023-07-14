package com.rssl.phizic.operations.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.billing.Billing;
import com.rssl.phizic.business.dictionaries.billing.BillingService;
import com.rssl.phizic.business.payments.PersonalPaymentCard;
import com.rssl.phizic.business.resources.external.MultiInstanceExternalResourceService;
import com.rssl.phizic.business.resources.external.PaymentSystemIdLink;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.operations.person.PersonOperationBase;
import com.rssl.phizic.utils.StringHelper;

import java.util.List;

/**
 * @author Erkin
 * @ created 23.08.2010
 * @ $Author$
 * @ $Revision$
 */
public abstract class PersonalPaymentCardOperationBase extends PersonOperationBase
{
	protected static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	protected static final MultiInstanceExternalResourceService externalResourceService
			= new MultiInstanceExternalResourceService();

	protected static final BillingService billingService = new BillingService();

	private PersonalPaymentCard card = null;

	private PaymentSystemIdLink link = null;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ������������� ��� ���������� ����� �����
	 * @param personId - ID ������������, ���� �������� �����
	 * @param cardId - ����� �����
	 * @param billingId - ID ����������� �������, ������ ������� �����
	 */
	protected void initializeNew(Long personId, String cardId, Long billingId)
			throws BusinessException, BusinessLogicException
	{
		if (personId == null)
			throw new BusinessException("�� ������ ID ������������");
		if (StringHelper.isEmpty(cardId))
			throw new BusinessException("�� ������ ����� �����");
		if (billingId == null)
			throw new BusinessException("�� ������ ID ��������");

		setPersonId(personId);

		Billing billing = billingService.getById(billingId);
		if (billing == null)
			throw new BusinessException("�� ������ ������� �� ID " + billingId);

		card = new PersonalPaymentCard();
		card.setCardNumber(cardId);
		card.setBillingId(billing.getId());
		card.setBillingName(billing.getName());
	}

	/**
	 * ������������� ��� ��������������/�������� ������������ ����� �������
	 *  (���� � ������� ���� �����)
	 * @param personId - ������������� ������������ � ����
	 * @throws BusinessException
	 */
	protected void initialize(Long personId)
			throws BusinessException, BusinessLogicException
	{
		if (personId == null)
			throw new BusinessException("�� ������ ID ������������");

		setPersonId(personId);

		loadLink();

		if (link != null) {
			card = new PersonalPaymentCard();
			card.setId(link.getId());
			card.setCardNumber(link.getExternalId());
			card.setBillingId(link.getBilling().getId());
			card.setBillingName(link.getBilling().getName());
		}
	}

	protected void loadLink() throws BusinessException, BusinessLogicException
	{
		link = null;

		List<PaymentSystemIdLink> list = externalResourceService.getLinks(
				getPerson().getLogin(), PaymentSystemIdLink.class, getInstanceName());
		if (list.isEmpty())
			return;

		link = list.iterator().next();
		if (link.getBilling() == null)
			throw new BusinessException("������� ����� ������������ ��������, " +
					"�� ����������� � ��������. ID ����� " + link.getId());
	}

	/**
	 * @return ���� ����� � �� ����
	 * (null, ���� � ������� ����� ���)
	 */
	protected PaymentSystemIdLink getLink()
	{
		return link;
	}

	/**
	 * @return ����� ������������ ��������
	 * (null, ���� � ������� ����� ���)
	 */
	public PersonalPaymentCard getCard()
	{
		return card;
	}

	/**
	 * @return �������
	 * (null, ���� � ������� ����� ���)
	 */
	public Billing getBilling()
	{
		return link!=null ? link.getBilling() : null;
	}

	/**
	 * @param billingId - ID ��������
	 * @return true, ���� ������������ ������� �������� ���������� ��������
	 */
	public boolean isPersonalPaymentsAvailable(Long billingId)
			throws BusinessException, BusinessLogicException
	{
		if (billingId == null)
			throw new NullPointerException("Argument 'billingId' cannot be null");
		Billing billing = billingService.getById(billingId);
		return isPersonalPaymentsAvailable(billing);
	}

	private boolean isPersonalPaymentsAvailable(Billing billing)
			throws BusinessException, BusinessLogicException
	{
		try
		{
			return gateInfoService.isPersonalRecipientAvailable(billing);
		}
		catch (GateException ex)
		{
			throw new BusinessException(ex);
		}
		catch (GateLogicException ex)
		{
			throw new BusinessLogicException(ex);
		}
	}

	protected void requirePersonalPaymentsAvailable(Billing billing)
			throws BusinessException, BusinessLogicException
	{
		if (!isPersonalPaymentsAvailable(billing)) {
			throw new BusinessException("������������ ������� �� �������� " +
					"��� �������� " + billing.getName() + " " +
					"(ID=" + billing.getId() + ")");
		}

	}
}
