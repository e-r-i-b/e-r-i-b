package com.rssl.phizic.operations.basket.invoice;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.basket.BasketHelper;
import com.rssl.phizic.business.basket.invoice.BusinessInvoiceService;
import com.rssl.phizic.business.basket.invoice.Invoice;
import com.rssl.phizic.business.basket.invoice.InvoiceService;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscriptionService;
import com.rssl.phizic.business.basket.invoiceSubscription.LightInvoiceSubscription;
import com.rssl.phizic.business.dictionaries.BankDictionaryService;
import com.rssl.phizic.business.operations.restrictions.InvoiceRestriction;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.context.ClientInvoiceCounterHelper;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.StringHelper;

import java.util.List;

/**
 * @author osminin
 * @ created 15.04.14
 * @ $Author$
 * @ $Revision$
 *
 * �������� ��������� ����� (�������)
 */
public class ViewInvoiceOperation extends OperationBase<InvoiceRestriction> implements ViewEntityOperation<Invoice>
{
	private static InvoiceService invoiceService = new InvoiceService();
	private static BankDictionaryService bankDictionaryService = new BankDictionaryService();
	private static InvoiceSubscriptionService invoiceSubscriptionService = new InvoiceSubscriptionService();

	private Invoice invoice;
	private ConfirmStrategyType confirmStrategyType;
	private String bankName;
	private String bankAccount;
	private String subscriptionName;
	private boolean availableOperations;
	private boolean accessRecoverAutoSub;

	/**
	 * ������������� ��������
	 * @param id ������������� �����
	 */
	public void initialize(Long id) throws BusinessException
	{
		if (id == null)
		{
			throw new IllegalArgumentException("������������� ����� �� ����� ���� null");
		}

		Pair<Invoice, ConfirmStrategyType > res = invoiceService.getInvoiceWithConfirmStrategyType(id);
		invoice = res.getFirst();
		confirmStrategyType = res.getSecond();

		if (invoice == null)
		{
			throw new BusinessException("����� c Id " + id + " �� ������.");
		}
		if (!getRestriction().accept(invoice))
		{
			throw new BusinessException("������������� � id=" + id + " �� ����������� �������.");
		}

		if(invoice.getIsNew() && ApplicationUtil.isClientApplication())
		{
			BusinessInvoiceService.markViewed(id);
			ClientInvoiceCounterHelper.decreaseInvoiceCounterNew(invoice);
		}
		updateBankInfo();
		updateSubscriptionInfo();
	}

	private void updateSubscriptionInfo() throws BusinessException
	{
		LightInvoiceSubscription subscription = invoiceSubscriptionService.getLightSubscriptionById(invoice.getInvoiceSubscriptionId());

		if (subscription == null)
		{
			throw new BusinessException("������ �� �������������� " + invoice.getInvoiceSubscriptionId() + " �� �������.");
		}

		subscriptionName = subscription.getName();
		accessRecoverAutoSub = StringHelper.isNotEmpty(subscription.getAutoSubExternalId());
		availableOperations = BasketHelper.isInvoicePayAvailable(subscription);
	}

	public Invoice getEntity() throws BusinessException, BusinessLogicException
	{
		return invoice;
	}

	/**
	 * @return true - �������� ���� ������������ ��������
	 */
	public boolean isConfirmSubscription()
	{
		return confirmStrategyType != null;
	}

	/**
	 * @return ������������ ������
	 */
	public String getInvoiceSubscriptionName()
	{
		return subscriptionName;
	}

	/**
	 * @return �������� �� ��������
	 */
	public boolean isAvailableOperations()
	{
		return availableOperations;
	}

	/**
	 * @return ������������ �����
	 */
	public String getBankName()
	{
		return bankName;
	}

	/**
	 * @return ���� �����
	 */
	public String getBankAccount()
	{
		return bankAccount;
	}

	private void updateBankInfo() throws BusinessException
	{
		ResidentBank bankInfo = bankDictionaryService.findByBIC(invoice.getRecBic());

		if (bankInfo == null)
		{
			throw new BusinessException("�� ������ ���� � ����������� � ����� " + invoice.getRecBic());
		}

		bankName = bankInfo.getName();
		bankAccount = bankInfo.getAccount();
	}

	/**
	 * @return ������ ��������� ���������� ��������
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public List<CardLink> getChargeOffResource() throws BusinessLogicException, BusinessException
	{
		return PersonContext.getPersonDataProvider().getPersonData().getCards();
	}

	/**
	 * @return true - ��������� ������� ������� ������ � �������������
	 */
	public boolean isAccessRecoverAutoSub()
	{
		return accessRecoverAutoSub;
	}
}
