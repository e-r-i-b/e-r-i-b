package com.rssl.phizic.operations.documents.templates;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.accounts.AccountsHelper;
import com.rssl.phizic.business.dictionaries.providers.*;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.OperationMessagesConfig;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.templates.Constants;
import com.rssl.phizic.business.documents.templates.TemplateHelper;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.documents.attribute.ExtendedAttribute;
import com.rssl.phizic.gate.exceptions.GateException;

import java.util.*;

/**
 * �������� �������������� ������� ���� ������ ���������� �����
 *
 * @author khudyakov
 * @ created 17.02.14
 * @ $Author$
 * @ $Revision$
 */
public class EditServicePaymentTemplateOperation extends EditTemplateOperation
{
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();

	private BillingServiceProviderBase provider;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		Long recipientId = getRecipientId(id);

		BillingServiceProviderBase temp = (BillingServiceProviderBase) serviceProviderService.findById(recipientId);
		checkProvider(temp);

		this.provider = temp;

		try
		{
			Map<String, ExtendedAttribute> attributes = template.getExtendedAttributes();
			if (attributes.get(Constants.EDIT_EVENT_ATTRIBUTE_NAME) == null)
			{
				//�������� ������������ ������� � ��������. �� ���� ��������� ������ ������������
				template.setIdFromPaymentSystem(null);
				template.setExtendedFields(null);

				setProviderInfo(temp);
			}
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ��������� �������� ������� ���������
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public void saveAsDraft() throws BusinessLogicException, BusinessException
	{
		executor.fireEvent(new ObjectEvent(DocumentEvent.SAVEASTEMPLATE, "client"));
		TemplateDocumentService.getInstance().addOrUpdate(template);
	}

	/**
	 * @return ��������� �����
	 */
	public BillingServiceProviderBase getProvider()
	{
		return provider;
	}

	/**
	 * �������� ������ "�����" ����������(�� ������ � �������� ����� ��� ����������� �������� �� view)
	 * ������ ���������� - ��� ��������� ���������� � ����������� CodeRecipientSBOL.
	 * �� ����� ���� ��� 1 ��������� � ����� �������(1 ����) �� ��������������� ������ ������ � ��������(Service)
	 * ��������, ��������� �������  �������������� 2 ��������:
	 * 1) �������� ������� � ����������� ������� "������� �����"
	 * 2) �������� ������� � ����������� ������� "��������"
	 * @return ������ "�����" (�����������).
	 */
	public List<ServiceProviderShort> getProviderAllServices() throws BusinessException
	{
		return serviceProviderService.getProviderAllServices(provider, true);
	}

	/**
	 * �������� ������ ���� ����� ���������(��) ��� ���� ��� ����������� �����.
	 * @return ������������ �������������� ���� ����������� ���������� � ������� ������ ������ �������������� ��
	 * FieldDescription#getHolderId.
	 */
	public List<FieldDescription> getProviderAllServicesFields() throws BusinessException
	{
		return serviceProviderService.getProviderAllServicesFields(provider);
	}

	protected void checkProvider(ServiceProviderBase provider) throws BusinessLogicException
	{
		if (provider == null)
		{
			throw new BusinessLogicException(ConfigFactory.getConfig(OperationMessagesConfig.class).getOperationMessage(OperationMessagesConfig.NOT_ACTIVE_PROVIDER_MESSAGE));
		}

		if (ServiceProviderState.ACTIVE != provider.getState())
		{
			throw new BusinessLogicException(ConfigFactory.getConfig(OperationMessagesConfig.class).getOperationMessage(OperationMessagesConfig.NOT_ACTIVE_PROVIDER_MESSAGE));
		}

		if (!isAvailableForApplication(provider))
		{
			throw new BusinessLogicException(ConfigFactory.getConfig(OperationMessagesConfig.class).getOperationMessage(OperationMessagesConfig.NOT_ACTIVE_PROVIDER_MESSAGE));
		}
	}

	/**
	 * @return ������ ��������
	 */
	public List<PaymentAbilityERL> getProviderChargeOffResources() throws BusinessLogicException, BusinessException
	{
		switch (provider.getAccountType())
		{
			case ALL:
			{
				List<PaymentAbilityERL> list = new ArrayList<PaymentAbilityERL>();
				for (PaymentAbilityERL link : getChargeOffResources())
				{
					ResourceType type = link.getResourceType();
					if (ResourceType.CARD == type || ResourceType.ACCOUNT == type)
					{
						list.add(link);
					}
				}
				return list;
			}
			case CARD:
			{
				List<PaymentAbilityERL> list = new ArrayList<PaymentAbilityERL>();
				for (PaymentAbilityERL link : getChargeOffResources())
				{
					if (link.getResourceType() == ResourceType.CARD)
					{
						list.add(link);
					}
				}
				return list;
			}
			case DEPOSIT:
			{
				List<PaymentAbilityERL> list = new ArrayList<PaymentAbilityERL>();
				for (PaymentAbilityERL link : getChargeOffResources())
				{
					if (link.getResourceType() == ResourceType.ACCOUNT)
					{
						list.add(link);
					}
				}
				return list;
			}
			default: throw new RuntimeException("����������� ����� ��������� ��������");
		}
	}

	/**
	 * @return ������ ��������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public List<PaymentAbilityERL> getChargeOffResources() throws BusinessException, BusinessLogicException
	{
		List<PaymentAbilityERL> result = new ArrayList<PaymentAbilityERL>();

		try
		{
			if (isFromAccountPaymentAllow())
			{
				List<? extends PaymentAbilityERL> list = PersonContext.getPersonDataProvider().getPersonData().getAccounts(new ActiveDebitRurAccountFilter());
				filterStoredResource(list);
				filterNotPermittedForFinancialTransactions(list);
				result.addAll(list);
			}
		}
		catch (InactiveExternalSystemException ex)
		{
			log.error("�� �������� �������� ���������� �� ������", ex);
			getMessageCollector().addInactiveSystemError("���� ������ � ����� �������� ����������. " + ex.getMessage());
		}

		try
		{
			if (isFromCardPaymentAllow())
			{
				List<? extends PaymentAbilityERL> list = PersonContext.getPersonDataProvider().getPersonData().getCards(getCardFilter());
				filterStoredResource(list);
				result.addAll(list);
			}
		}
		catch (InactiveExternalSystemException ex)
		{
			log.error("�� �������� �������� ���������� �� ������", ex);
			getMessageCollector().addInactiveSystemError("���� ����� �������� ����������. " + ex.getMessage());
		}
		return result;
	}

	protected boolean isFromAccountPaymentAllow() throws BusinessException
	{
		//��������� ���������� �� ������ �������� ���������� �� �����
		if (!DocumentHelper.isExternalJurAccountPaymentsAllowed())
		{
			return false;
		}

		//��������� ���������� ������ �� �����
		if (!TemplateHelper.isFromAccountPaymentAllow(template))
		{
			return false;
		}

		return AccountType.ALL == provider.getAccountType() || AccountType.DEPOSIT == provider.getAccountType();
	}

	protected boolean isFromCardPaymentAllow() throws BusinessException
	{
		//������ �� ������ �������� ���� ���������
		if (TemplateHelper.isOldCPFLTemplate(template))
		{
			return false;
		}

		//��������� ���������� ������ � �����
		if (!TemplateHelper.isFromCardPaymentAllow(template))
		{
			return false;
		}

		return AccountType.ALL == provider.getAccountType() || AccountType.CARD == provider.getAccountType();
	}


	/**
	 *
	 * ������������� ������� ��������� ����������� ������ ��� ����� ������ ����� (����� ����������), �
	 * ��������� ������� ����� ���������� ������� �������� ��������� �� ������
	 *
	 * @param  links ������ ���� ��� ������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	protected void filterStoredResource(List<? extends PaymentAbilityERL> links) throws BusinessException, BusinessLogicException
	{
		Iterator<? extends PaymentAbilityERL> i = links.listIterator();
		while (i.hasNext())
		{
			PaymentAbilityERL link = i.next();
			if (link.getValue() instanceof AbstractStoredResource)
			{
				if (FormType.isPaymentSystemPayment(template.getFormType()))
				{
					String resource = link.getResourceType() == ResourceType.CARD ? "������" : "������";
					getMessageCollector().addInactiveSystemError("���������� �� ����� " + resource + " ����� ���� ������������.");
					break;
				}
				else
				{
					i.remove();
				}
			}
		}
	}

	/**
	 *
	 * ��� ������ � ���������� ����� ���/������ ������� 76/2, 77/41, 31/11 ���������� ���������� �������� ���������.
	 *
	 * @param  list ������ ������
	 *
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 *
	 */
	protected final void filterNotPermittedForFinancialTransactions(List<? extends PaymentAbilityERL> list) throws BusinessException, BusinessLogicException
	{
		ListIterator<? extends PaymentAbilityERL> i = list.listIterator();
		while (i.hasNext())
		{
			PaymentAbilityERL link = i.next();
			if (link.getResourceType() == ResourceType.ACCOUNT)
			{
				if (!AccountsHelper.isPermittedForFinancialTransactions((Account) link.getValue()))
				{
					i.remove();
				}
			}
		}
	}

	protected CardFilter getCardFilter() throws BusinessException
	{
		return getProvider().isCreditCardSupported() ?
				new CardFilterConjunction(new ActiveNotVirtualCardsFilter(), new CardOwnFilter()) : new ActiveNotVirtualNotCreditOwnCardFilter();
	}

	private boolean isAvailableForApplication(ServiceProviderBase provider)
	{
		ApplicationInfo info = ApplicationConfig.getIt().getApplicationInfo();
		if (info.isWeb())
		{
			return provider.isAvailablePaymentsForInternetBank();
		}
		if (info.isMobileApi())
		{
			return provider.isAvailablePaymentsForMApi();
		}
		if (info.isATM())
		{
			return provider.isAvailablePaymentsForAtmApi();
		}
		return provider.isAvailablePaymentsForErmb();
	}

	private void setProviderInfo(BillingServiceProviderBase provider) throws BusinessException
	{
		try
		{
			this.template.setExtendedFields((List) provider.getFieldDescriptions());
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������� ��� �������� �� �������� �������
	 * @return true- ������� � �����
	 */
	public boolean isCardsTransfer() throws BusinessException
	{
		PaymentAbilityERL link = getChargeOffResourceLink();
		if (link == null)
		{
			return false;
		}
		return getChargeOffResourceLink() instanceof CardLink;
	}

	private Long getRecipientId(Long formId) throws BusinessException
	{
		//id ���������� � �����
		if (formId != null)
		{
			return formId;
		}

		//id ���������� �� �������
		Long templateId = template.getReceiverInternalId();
		if (templateId != null)
		{
			return templateId;
		}

		throw new BusinessException("�� ����� ���������� ������� �������.");
	}
}
