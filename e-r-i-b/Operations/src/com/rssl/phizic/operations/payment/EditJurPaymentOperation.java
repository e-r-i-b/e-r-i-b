package com.rssl.phizic.operations.payment;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.BankDictionaryService;
import com.rssl.phizic.business.dictionaries.billing.Billing;
import com.rssl.phizic.business.dictionaries.billing.BillingService;
import com.rssl.phizic.business.dictionaries.providers.AccountType;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderState;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.NewDocumentSource;
import com.rssl.phizic.business.documents.payments.validators.DocumentValidator;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.business.documents.templates.TemplateHelper;
import com.rssl.phizic.business.documents.templates.validators.OwnerTemplateValidator;
import com.rssl.phizic.business.documents.templates.validators.StateTemplateValidator;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.business.statemachine.MachineState;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.PaymentRecipientGateService;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizic.business.regions.RegionHelper;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.*;

/**
 * @author krenev
 * @ created 06.03.2011
 * @ $Author$
 * @ $Revision$
 * �������� ��� ��������� ���������� � ��������� ������� ���� ������ �����
 * ������������� ���������� �� ��������� ����� ���������� � �������� ������ ������ ���������� ����(����� �������)
 */
public class EditJurPaymentOperation extends CreateFormPaymentOperation
{
	private static final SimpleService simpleService = new SimpleService();
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();
	private static final BankDictionaryService bankDictionaryService = new BankDictionaryService();
	private static final BillingService billingService = new BillingService();
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private final PaymentStateMachineService stateMachineService = new PaymentStateMachineService();
	private static final DocumentValidator validator = new IsOwnDocumentValidator();
	private String receiverAccount;
	private String receiverINN;
	private String receiverBIC;
	private String receiverCodePoint;
	private String operationCode;
	private boolean isEditPayment;
	private boolean isAutoPayment;
	private PaymentAbilityERL chargeOffResource;
	private Long templateId;
	protected List<BillingServiceProvider> serviceProviders;
	private Map<String, String[]> regions = new HashMap<String, String[]>();
	/**
	 * ������������������� ������� ������� �� ������ ������������� ���������
	 * @param fromResource �������� ��������, ���� ����� - ������������ ������ ��������� � ���������
	 * @param paymentId ������������ �������
	 * @param edit ������� ����, ��� ����������� �������������� ������������� ���������(�� �����) 
	 */
	public void initialize(String fromResource, Long paymentId, boolean edit, boolean isAutoPayment) throws BusinessException, BusinessLogicException
	{
		this.isEditPayment = edit;
		this.isAutoPayment = isAutoPayment;
		if (paymentId == null)
			throw new BusinessException("�� ����� ������������� �������");

		document = businessDocumentService.findById(paymentId);
		if (document == null)
		{
			throw new ResourceNotFoundBusinessException("�� ������ �������� � �������������� " + paymentId, BusinessDocument.class);
		}
		initialize(fromResource, document);
	}

	/**
	 * ������������������� ������� ������� �� ������ ������������� �������
	 * @param fromResource �������� ��������, ���� ����� - ������������ ������ ��������� � �������
	 * @param reminder ������� ������ �����������
	 * @param templateId ������������ �������
	 */
	public void initialize(String fromResource, Long templateId, boolean reminder) throws BusinessException, BusinessLogicException
	{
		this.templateId = templateId;
		if (templateId == null)
			throw new BusinessException("�� ����� ������������� �������");

		DocumentSource source = new NewDocumentSource(templateId, CreationType.internet, reminder, new OwnerTemplateValidator(), new StateTemplateValidator());
		initialize(fromResource, source.getDocument());
	}

	/**
	 * ������������������� ������� ������� �� ������ ������������� �������
	 * @param fromResource �������� �������� �� ���������
	 */
	public void initialize(String fromResource) throws BusinessException, BusinessLogicException
	{
		this.chargeOffResource = findFromResource(fromResource);
	}

	private PaymentAbilityERL findFromResource(String fromResource) throws BusinessException, BusinessLogicException
	{
		if (StringHelper.isEmpty(fromResource))
		{
			return null;
		}
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		ExternalResourceLink link = personData.getByCode(fromResource);
		if (!(link instanceof PaymentAbilityERL))
		{
			throw new BusinessException("������ � ������� " + fromResource + " �����������");
		}
		return (PaymentAbilityERL) link;
	}

	private void initialize(String fromResource, BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		if (document == null)
		{
			throw new IllegalArgumentException("document �� ����� ���� null");
		}
		if (!isAutoPayment && (isEditPayment && !editable(document)))
		{
			throw new BusinessLogicException("�������� �� ����� ���� ��������������");
		}
		if (!(document instanceof RurPayment))
		{
			throw new BusinessException("������������ ��� ��������� " + document.getId() + ". ��������� ��������� RurPayment");
		}
		this.document=document;
		RurPayment payment = (RurPayment) document;

		getDocumentValidator().validate(document);  //��������� �����
		//�������������� �������� ��������


		if (StringHelper.isEmpty(fromResource))
		{
			try
			{
				//���� �� ����� ����� �� ���������
				PaymentAbilityERL link = payment.getChargeOffResourceLink();
				if (link != null)
				{
					this.chargeOffResource = link;
				}
			}
			catch (DocumentException e)
			{
				throw new BusinessException(e);
			}
		}
		else
		{
			this.chargeOffResource = findFromResource(fromResource);
		}

		receiverAccount = payment.getReceiverAccount();
		receiverINN = payment.getReceiverINN();
		receiverBIC = payment.getReceiverBIC();
		operationCode = payment.getOperationCode();
		if (payment instanceof JurPayment)
		{
			JurPayment jurPayment = (JurPayment) payment;
			receiverCodePoint = jurPayment.getReceiverPointCode();
		}
	}

	/**
	 * ��������, �������� �� �������������� ���������
	 * @param document �������� ��� ��������.
	 * @return ��/���
	 */
	private boolean editable(BusinessDocument document)
	{
		MachineState state = stateMachineService.getStateMachineByFormName(document.getFormName()).getObjectMachineState(document);
		boolean isInitialAcceptable = state.isEventAcceptable(new ObjectEvent(DocumentEvent.INITIAL, ObjectEvent.CLIENT_EVENT_TYPE));
		boolean isEditAcceptable    = state.isEventAcceptable(new ObjectEvent(DocumentEvent.EDIT, ObjectEvent.CLIENT_EVENT_TYPE));

		return isInitialAcceptable || isEditAcceptable;
	}

	/**
	 * @return ���������� ��� ��������� ��������.
	 */
	public PaymentAbilityERL getChargeOffResource()
	{
		return chargeOffResource;
	}

	public List<PaymentAbilityERL> getChargeOffResources() throws BusinessException, BusinessLogicException
	{
		List<PaymentAbilityERL> result = new ArrayList<PaymentAbilityERL>();

		//���� ������������� ������������ ������ �� �����
		if (isFromAccountPaymentAllow())
		{
			List<AccountLink> list = PersonContext.getPersonDataProvider().getPersonData().getAccounts(new ActiveDebitRurAccountFilter());
			filterNotPermittedForFinancialTransactions(list);
			result.addAll(list);
		}

		if (isFromCardPaymentAllow())
		{
			result.addAll(PersonContext.getPersonDataProvider().getPersonData().getCards(new ActiveNotVirtualNotCreditOwnCardFilter()));
		}

		return result;
	}

	/**
	 * @return ���� ����������
	 */
	public String getReceiverAccount()
	{
		return receiverAccount;
	}

	/**
	 * @return ��� ����������
	 */
	public String getReceiverINN()
	{
		return receiverINN;
	}

	/**
	 * @return ��� ����� ����������
	 */
	public String getReceiverBIC()
	{
		return receiverBIC;
	}

	/**
	 * @return ������� ������������ ����������.
	 */
	public String getReceiverCodePoint()
	{
		return receiverCodePoint;
	}

	public void setChargeOffResource(PaymentAbilityERL chargeOffResource)
	{
		this.chargeOffResource = chargeOffResource;
	}

	public void setReceiverAccount(String receiverAccount)
	{
		this.receiverAccount = receiverAccount;
	}

	public void setReceiverINN(String receiverINN)
	{
		this.receiverINN = receiverINN;
	}

	public void setReceiverBIC(String receiverBIC)
	{
		this.receiverBIC = receiverBIC;
	}

	/**
	 * ����� ����������� � �������� �� ��������� (���� ����)
	 * ����� ������������ �� ���������� ReceiverAccount, ReceiverINN, ReceiverBIC, � ������ ���� ��������� �������� ChargeOffResource
	 * (��� ������ � ���� �� ����������� ���� � �������� �� ���������)
	 * @return ������ ����������� ��� ������ ������, ���� ���������� ������� ��� ����������� � ��� ���
	 */
	public List<Recipient> findDefaultBillingRecipients() throws BusinessException, BusinessLogicException
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
	 * �������� ������� �� ��������� ��� ������� ��������� ��������
	 * @return ������� �� ��������� ��� ������� ��������� ��������
	 * @throws BusinessException
	 */
	public Billing getDefaultBilling() throws BusinessException
	{
		return billingService.findDefaultBilling(chargeOffResource.getOffice());
	}

	/**
	 * �������� �� �������� �������������� ������������ ����������.
	 * @param externalProviderId ������� ������������ ����������.
	 * @return ������������
	 */
	public String getExtenalProviderName(String externalProviderId) throws BusinessException, BusinessLogicException
	{
		List<Recipient> billingRecipients = findDefaultBillingRecipients();
		for (Recipient billingRecipient : billingRecipients)
		{
			if (billingRecipient.getSynchKey().equals(externalProviderId))
			{
				return billingRecipient.getName();
			}
		}
		throw new BusinessException("�� ������� ��� ���������� � ��������������" + externalProviderId);
	}

	/**
	 * ����� ���������� �� ID � ����� ����
	 * @param id - id ����������
	 * @return  ���������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public BillingServiceProvider findRecipient(Long id)  throws BusinessException, BusinessLogicException
	{
		return (BillingServiceProvider) serviceProviderService.findById(id);
	}
	/**
	 * ����� ����������� � ����������� �����������
	 * ����� ������������ �� ���������� ReceiverAccount, ReceiverINN, ReceiverBIC
	 * � ������ ���� ��������� �������� � ��������� ��� ���� �����������.
	 */
	public void findRecipient() throws BusinessException, BusinessLogicException
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

	/**
	 * ����� ����������� � ����������� �����������
	 * ����� ������������ �� ���������� ReceiverAccount, ReceiverINN, ReceiverBIC
	 * � ������ ���� ��������� �������� � ��������� ��� ���� �����������.
	 * @return ������ ��������� �����������, ��������������� �� ��������, ��� null, ���� ����������� ��������������� �������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public List<Object[]> findRecipients() throws BusinessException, BusinessLogicException
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

	/**
	 * ����������� ����� ������ �����
	 * @return ������������ ��������������� �������. ����� ������������. ���� ������ �� ��������� � ����������.
	 */
	@Transactional
	public Long prepareJurPayment() throws BusinessException, BusinessLogicException
	{
		if (!isEditPayment)
		{
			//���� �� ���������� �������������� ������������� ��������� ������ ������ �� ���� - ����� ������ ����� ��������
			return null;
		}
		//������� - �������� �� ��� �������
		if (!isBillingPayment())
		{
			ResidentBank bank = bankDictionaryService.findByBIC(receiverBIC);
			if (bank == null)
			{
				throw new BusinessException("�� ������ ���� � ����������� � ����� " + receiverBIC);
			}
			//�� ��������  - ������ ��������� ��������� ����������.
			RurPayment payment = (RurPayment) document;
			payment.setReceiverBank(bank);
			payment.setReceiverAccount(receiverAccount);
			payment.setReceiverINN(receiverINN);
			businessDocumentService.addOrUpdate(payment);
			return payment.getId();
		}
		//�������� - ������� ������ ������
		removeOldDocument();
		//�������� ����� ����������� ������.
		return null;
	}

	/**
	 * ����������� ����������� ������ ��� ������ �������� � ����� �������� ����������.
	 * @param externalProviderId ������������ �������� ����������
	 * @return ������������ ��������������� �������. ����� ������������. ���� ������ �� ��������� � ����������.
	 */
	@Transactional
	public Long prepareExternalProviderPayment(String externalProviderId) throws BusinessException, BusinessLogicException
	{
		if (!isEditPayment)
		{
			//���� �� ���������� �������������� ������������� ��������� ������ ������ �� ���� - ����� ������� ����� ���������
			return null;
		}
		//������� - �������� �� ��� �������
		if (isBillingPayment())
		{
			//�� ��������  - ������� �������� �� ����������.
			JurPayment jurPayment = (JurPayment) document;
			if (externalProviderId != null && externalProviderId.equals(jurPayment.getReceiverPointCode()))
			{
				//�� �������� - ������ ������.
				return document.getId();
			}
		}
		//���� ���������� �������� ���� ��� ������� ��������
		//������� ������ ������
		removeOldDocument();
		//�������� ����� ����������� ������.
		return null;
	}

	private void removeOldDocument() throws BusinessException, BusinessLogicException
	{
		StateMachineExecutor executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(document.getFormName()));
		executor.initialize(document);
		executor.fireEvent(new ObjectEvent(DocumentEvent.DELETE, ObjectEvent.SYSTEM_EVENT_TYPE));
		businessDocumentService.addOrUpdate(document);
	}

	/**
	 * @return �������� �� ������ �� ������, �������� ��������� �������� ��� ������������� �����������
	 */
	public boolean isBillingPayment()
	{
		GateExecutableDocument gateDocument = (GateExecutableDocument) document;
		return AbstractPaymentSystemPayment.class.isAssignableFrom(gateDocument.getType());
	}

	/**
	 * ����������� ����������� ������ ��� ������ �������� � ����� ���������� �� �����������.
	 * @return ������������ ��������������� �������. ����� ������������. ���� ������ �� ��������� � ����������.
	 */
	@Transactional
	public Long prepareInternalProviderPayment(Long serviceProviderId) throws BusinessException, BusinessLogicException
	{
		if (!isEditPayment)
		{
			//���� �� ���������� �������������� ������������� ��������� ������ ������ �� ���� - ����� ������ ����� ��������
			return null;
		}
		//������� - �������� �� ��� �������
		if (isBillingPayment())
		{
			//�� ��������  - ������� �������� �� ����������.
			JurPayment jurPayment = (JurPayment) document;
			if (serviceProviderId.equals(jurPayment.getReceiverInternalId()))
			{
				//�� �������� - ������ ������.
				return document.getId();
			}
		}
		//���� ���������� �������� ���� ��� ������� ��������
		//������� ������ ������
		removeOldDocument();
		//�������� ����� ����������� ������.
		return null;
	}

	/**
	 * �������� ������������ ������� �� ������ �������� ������������� ������
	 * @return ������������ ������� ��� null, ���� �������������� ��������� ���������� ��� ������� �������.
	 */
	public Long getTemplateId()
	{
		return templateId;
	}

	public boolean isEditPayment()
	{
		return isEditPayment;
	}

	public boolean isCardsTransfer()
	{
		return chargeOffResource instanceof CardLink;
	}

	public String getOperationCode()
	{
		return operationCode;
	}

	protected DocumentValidator getDocumentValidator()
	{
		return validator;
	}

	protected boolean isFromCardPaymentAllow() throws BusinessException
	{
		//������ �� ������ �������� ���� ���������
		if (TemplateHelper.isByOldCPFLTemplate(document))
		{
			return false;
		}

		//��������� ���������� ������ � �����
		if (!DocumentHelper.isFromCardPaymentAllow(document))
		{
			return false;
		}

		if (document == null)
		{
			return true;
		}

		//��� ������ �� ������� ����� ������� �������� ���������
		return !(document.isByTemplate() && DocumentHelper.isFromAccountPaymentAllow(document));
	}

	protected boolean isFromAccountPaymentAllow() throws BusinessException
	{
		//��������� ���������� �� ������ �������� ���������� �� �����
		if (!DocumentHelper.isExternalJurAccountPaymentsAllowed())
		{
			return false;
		}

		//��������� ���������� ������ �� �����
		if (!DocumentHelper.isFromAccountPaymentAllow(document))
		{
			return false;
		}

		if (document == null)
		{
			return true;
		}

		//��� ������ �� ������� ����� ������� �������� ���������
		return !(document.isByTemplate() && DocumentHelper.isFromCardPaymentAllow(document));
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

	/**
	 * �������� �����������, ��������� � �������� ��������� ������� �������
	 * @param accountType ��������� ��������
	 * @param currentRegion "��������" ������
	 * @return ������ ����������� �� ���������� � ������ "���������" �������
	 * @throws BusinessException
	 */
	private List<Object[]> getProviderByRequisitesAndRegion(String[] accountType, Region currentRegion) throws BusinessException
	{
		Long currentRegionId = currentRegion == null ? null : currentRegion.getId();
		Long parentRegionId = currentRegion == null || currentRegion.getParent() == null ? null : currentRegion.getParent().getId();
		return serviceProviderService.getProviderByRequisitesAndRegion(receiverAccount,receiverINN, receiverBIC, accountType, parentRegionId, currentRegionId);
	}

	/**
	 * @param accountType ��������� ��������
	 * @return ������ ����������� �� ����������
	 * @throws BusinessException
	 */
	private List<Object[]> getProviderByRequisites(String[] accountType) throws BusinessException
	{
		return serviceProviderService.getProviderByRequisites(receiverAccount, receiverINN, receiverBIC, accountType);
	}

	/**
	 * �������� ������� ��� ����������� �� ����������� � ����������� ������
	 * @param providersList  - ������ �����������
	 * @throws BusinessException
	 */
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

	public Map<String, String[]> getRegions()
	{
		return regions;
	}
}