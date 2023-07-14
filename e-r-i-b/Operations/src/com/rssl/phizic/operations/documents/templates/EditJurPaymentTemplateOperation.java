package com.rssl.phizic.operations.documents.templates;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.billing.Billing;
import com.rssl.phizic.business.dictionaries.billing.BillingService;
import com.rssl.phizic.business.dictionaries.providers.AccountType;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderState;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.templates.TemplateHelper;
import com.rssl.phizic.business.regions.RegionHelper;
import com.rssl.phizic.business.resources.external.ActiveNotVirtualNotCreditOwnCardFilter;
import com.rssl.phizic.business.resources.external.CardFilter;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.PaymentRecipientGateService;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.*;

/**
 * �������� �������������� ������� ���� ������ �� ������������ ����������
 *
 * @author khudyakov
 * @ created 08.03.14
 * @ $Author$
 * @ $Revision$
 */
public class EditJurPaymentTemplateOperation extends EditServicePaymentTemplateOperation
{
	private static final BillingService billingService = new BillingService();
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();
	private static final SimpleService simpleService = new SimpleService();

	private Map<String, String[]> regions = new HashMap<String, String[]>();

	private String fromResource;
	private String receiverAccount;
	private String receiverINN;
	private String receiverBIC;
	private String operationCode;
	private String receiverCodePoint;
	private String externalReceiverId;
	private PaymentAbilityERL chargeOffResourceLink;
	private List<BillingServiceProvider> serviceProviders;


	public void initialize(String fromResource, String receiverAccount, String receiverINN, String receiverBIC) throws BusinessException, BusinessLogicException
	{
	 	this.fromResource = fromResource;
		this.receiverAccount = receiverAccount;
		this.receiverINN = receiverINN;
		this.receiverBIC = receiverBIC;
	}

	public String getFromResource()
	{
		return fromResource;
	}

	public void setFromResource(String fromResource)
	{
		this.fromResource = fromResource;
	}

	public String getReceiverAccount()
	{
		return receiverAccount;
	}

	public void setReceiverAccount(String receiverAccount)
	{
		this.receiverAccount = receiverAccount;
	}

	public String getReceiverINN()
	{
		return receiverINN;
	}

	public void setReceiverINN(String receiverINN)
	{
		this.receiverINN = receiverINN;
	}

	public String getReceiverBIC()
	{
		return receiverBIC;
	}

	public void setReceiverBIC(String receiverBIC)
	{
		this.receiverBIC = receiverBIC;
	}

	public String getOperationCode()
	{
		return operationCode;
	}

	public void setOperationCode(String operationCode)
	{
		this.operationCode = operationCode;
	}

	public String getReceiverCodePoint()
	{
		return receiverCodePoint;
	}

	public void setReceiverCodePoint(String receiverCodePoint)
	{
		this.receiverCodePoint = receiverCodePoint;
	}

	public void setExternalReceiverId(String externalReceiverId)
	{
		this.externalReceiverId = externalReceiverId;
	}

	public String getExternalReceiverId()
	{
		return externalReceiverId;
	}

	public void findRecipient() throws BusinessException
	{
		//����������� �� ���� ��������� �������� ���������� ������ ���� �����������
		//���� ������ ��������� �� �����, ��������� ����� ������������ �� ������������� ������� �������� �� ������
		AccountType[] accountTypes = (AccountType[]) getAccountType(false);

		// ���� ��������� ����������� ��� ������������� ������� ���, �� ����� ������� ����� ������� ������� �����.
		if (accountTypes == null)
		{
			serviceProviders = null;
			return;
		}

		DetachedCriteria criteria = DetachedCriteria.forClass(BillingServiceProvider.class);
		criteria.add(Expression.eq("state", ServiceProviderState.ACTIVE));
		criteria.add(Expression.eq("account", receiverAccount));
		criteria.add(Expression.eq("INN", receiverINN));
		criteria.add(Expression.eq("BIC", receiverBIC));

		if (ApplicationUtil.isMobileApi())
			criteria.add(Expression.eq("availablePaymentsForMApi", true));

		criteria.add(Expression.in("accountType", accountTypes));

		serviceProviders = simpleService.find(criteria);

	}

	public List<BillingServiceProvider> getServiceProviders()
	{
		return serviceProviders;
	}

	public List<Recipient> findDefaultBillingRecipients() throws BusinessLogicException, BusinessException
	{
		if (isCardsTransfer())
		{
			return Collections.emptyList(); //��� ���� �� ���� � ������� �� ���������.
		}

		//�������� ������� �� ��������� ��� �� ����� ��������
		Billing billing = getDefaultBilling();
		if (billing == null)
		{
			return Collections.emptyList();//�������� �� ��������� ��� - ������ ������.
		}
		//������� ����� - ���� ����������� � ���.
		PaymentRecipientGateService service = GateSingleton.getFactory().service(PaymentRecipientGateService.class);
		try
		{
			return service.getRecipientList(receiverAccount, receiverBIC, receiverINN, billing);
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
	 * @return ���� ������� ��������
	 */
	public PaymentAbilityERL getChargeOffResourceLink()
	{
		return chargeOffResourceLink;
	}

	public void setChargeOffResourceLink(PaymentAbilityERL chargeOffResourceLink)
	{
		this.chargeOffResourceLink = chargeOffResourceLink;
	}

	public String getExternalProviderName() throws BusinessException, BusinessLogicException
	{
		if (StringHelper.isEmpty(getExternalReceiverId()))
		{
			return null;
		}

		List<Recipient> billingRecipients = findDefaultBillingRecipients();
		for (Recipient billingRecipient : billingRecipients)
		{
			if (billingRecipient.getSynchKey().equals(getExternalReceiverId()))
			{
				return billingRecipient.getName();
			}
		}
		throw new BusinessException("�� ������� ��� ���������� � ��������������" + getExternalReceiverId());
	}

	public Billing getDefaultBilling() throws BusinessException
	{
		return billingService.findDefaultBilling(chargeOffResourceLink.getOffice());
	}

	/**
	 * @return �������� �� ������ �� ������, �������� ��������� �������� ��� ������������� �����������
	 */
	public boolean isBillingPayment()
	{
		return AbstractPaymentSystemPayment.class.isAssignableFrom(template.getType());
	}

	public List<Object[]> findRecipients() throws BusinessLogicException, BusinessException
	{
		//����� � ����������� �� ���� ��������� �������� ���������� ������ ���� �����������
		//���� ������ ��������� �� �����, ��������� ����� ������������ �� ������������� ������� �������� �� ������
		String[]  accountType = (String[]) getAccountType(true);

		// ���� ��������� ����������� ��� ������������� ������� ���, �� ����� ������� ����� ������� ������� �����.
		if (accountType == null)
		{
			return Collections.emptyList();
		}

		List<Object[]> result = new ArrayList<Object[]>();
		// ��������� ����������� �� �������� ��������� �������
		result.addAll(getProviderByRequisitesAndRegion(accountType, RegionHelper.getCurrentRegion()));
		// ��������� ����������� �� ���� ��������� ��������
		result.addAll(getProviderByRequisites(accountType));
		if (!result.isEmpty())
			updateRegionsList(result);

		return result;
	}

	public Map<String, String[]> getRegions()
	{
		return regions;
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
		return true;
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
		return true;
	}

	protected CardFilter getCardFilter() throws BusinessException
	{
		return new ActiveNotVirtualNotCreditOwnCardFilter();
	}

	/**
	 * @param isStringType ������� �� ��������� ������
	 * @return ��������� ��������
	 * @throws BusinessException
	 */
	private Object[] getAccountType(boolean isStringType) throws BusinessException
	{
		if (isCardsTransfer())
		{
			if (isStringType)
				return new String[]{AccountType.ALL.toString(), AccountType.CARD.toString()};

			return new AccountType[]{AccountType.ALL, AccountType.CARD};
		}
		else
		{
			//��� �������� �� ����� �������� ���������� � ����� "�����" � "�����/����"
			if (isStringType)
				return new String[]{AccountType.ALL.toString(), AccountType.DEPOSIT.toString()};

			return new AccountType[]{AccountType.ALL, AccountType.DEPOSIT};
		}
	}

	private List<Object[]> getProviderByRequisitesAndRegion(String[] accountType, Region currentRegion) throws BusinessException
	{
		Long currentRegionId = currentRegion == null ? null : currentRegion.getId();
		Long parentRegionId = currentRegion == null || currentRegion.getParent() == null ? null : currentRegion.getParent().getId();
		return serviceProviderService.getProviderByRequisitesAndRegion(receiverAccount,receiverINN, receiverBIC, accountType, parentRegionId, currentRegionId);
	}

	private List<Object[]> getProviderByRequisites(String[] accountType) throws BusinessException
	{
		return serviceProviderService.getProviderByRequisites(receiverAccount, receiverINN, receiverBIC, accountType);
	}

	private void updateRegionsList(List<Object[]> providersList) throws BusinessException
	{
		List<String> providers = new ArrayList<String>();
		for (Object[] res : providersList){
			if (!providers.contains((String) res[6]))
				providers.add((String) res[6]);
		}

		List<Object[]> resultList = serviceProviderService.getRegionsForProviders(providers);
		//���� �������� �� ������� �������
		if (CollectionUtils.isEmpty(resultList))
			return;

		StringBuilder  nameRegions = new StringBuilder();
		String providerId = "";
		String firstRegion = ""; //������, ������� ������������ � ����� �������
		boolean needComma = false; //����� �� ����������� ����� ���������
		for (Object[] result : resultList)
		{
			String currProviderId = (String) result[0];
			if (providerId.equals(""))
				providerId = currProviderId;
			if (!providerId.equals(currProviderId))
			{
				regions.put(providerId, new String[]{firstRegion, nameRegions.toString()});
				providerId = currProviderId;
				nameRegions =  new StringBuilder();
				needComma = false;
				firstRegion = "";
			}
			if (needComma)
				nameRegions.append(", ");
			if (firstRegion.equals(""))
			{
				firstRegion = (String) result[1];
			}
			else
			{
				nameRegions.append("<a>"+result[1]+"</a>");
				needComma = true;
			}
		}
		regions.put(providerId, new String[]{firstRegion, nameRegions.toString()});
	}
}
