package com.rssl.phizic.operations.payment;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.documents.*;
import com.rssl.phizic.business.documents.payments.*;
import com.rssl.phizic.business.extendedattributes.ClientHistoryExtendedAttribute;
import com.rssl.phizic.business.extendedattributes.ExtendedAttribute;
import com.rssl.phizic.business.payments.forms.PaymentFormService;
import com.rssl.phizic.business.payments.forms.meta.MetadataBean;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.client.LoginType;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.documents.InputSumType;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.ListTransformer;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author Rtischeva
 * @ created 06.04.15
 * @ $Author$
 * @ $Revision$
 */
public abstract class HistoryListTransformer<Output, Input> implements ListTransformer<Output, Input>
{
	private final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private final DepartmentService departmentService = new DepartmentService();
	private final PaymentFormService paymentFormService = new PaymentFormService();

	private static final int BATCH_SIZE = 5;

	private List<Long> documentIds = new ArrayList<Long>();
	private List<Long> loanClaimIds = new ArrayList<Long>();
	private List<Long> loanCardClaimIds = new ArrayList<Long>();
	private List<Long> departmentIds = new ArrayList<Long>();
	private List<Long> formIds = new ArrayList<Long>();
	private Calendar minLoanCardClaimDate = null;
	private Calendar maxLoanCardClaimDate = null;
	private Calendar minLoanClaimDate = null;
	private Calendar maxLoanClaimDate = null;

	protected void collectIds(List<PaymentDocumentEntity> documentEntities)
	{
		// 1. Строим списки идентификаторов документов и департаментов. Параллельно вычисляем макс. и мин. даты создания кредитных заявок
		for (PaymentDocumentEntity documentEntity : documentEntities)
		{
			if (documentEntity.getDocumentId() != null)
			{
				if (documentEntity.getKind().equals("LCC"))
				{
					loanCardClaimIds.add(documentEntity.getDocumentId());
					Calendar claimDateCreated = documentEntity.getDateCreated();
					if (minLoanCardClaimDate == null || claimDateCreated.before(minLoanCardClaimDate))
						minLoanCardClaimDate = claimDateCreated;
					if (maxLoanCardClaimDate == null || claimDateCreated.after(maxLoanCardClaimDate))
						maxLoanCardClaimDate = claimDateCreated;
				}
				if (documentEntity.getKind().equals("EL"))
				{
					loanClaimIds.add(documentEntity.getDocumentId());
					Calendar claimDateCreated = documentEntity.getDateCreated();
					if (minLoanClaimDate == null || claimDateCreated.before(minLoanClaimDate))
						minLoanClaimDate = claimDateCreated;
					if (maxLoanClaimDate == null || claimDateCreated.after(maxLoanClaimDate))
						maxLoanClaimDate = claimDateCreated;
				}
				else
					documentIds.add(documentEntity.getDocumentId());
				Long departmentId = documentEntity.getDepartmentId();
				if (departmentId != null)
					departmentIds.add(departmentId);
				formIds.add(documentEntity.getFormId());
			}
		}
	}

	protected Map<Long, List<ExtendedAttribute>> buildExtendedAttributesMap() throws SystemException
	{
		Map<Long, List<ExtendedAttribute>> extendedAttributesMap = new HashMap<Long, List<ExtendedAttribute>>();

		// 2. Находим список всех расширенных полей для документов и раскладываем их в мапу (ключ - это идентификатор документа, значение - его расширенные поля)
		Collection<ClientHistoryExtendedAttribute> documentsExtendedAttributes = new ArrayList<ClientHistoryExtendedAttribute>();

		//2.1. Ищем расширенные поля для пяти документов за раз
		for(int i = 0; i < documentIds.size(); i+= BATCH_SIZE)
		{
			List<Long> docIds = buildIdsSubList(documentIds, i);
			documentsExtendedAttributes.addAll(businessDocumentService.findExtendedFields(docIds));
	    }

		// 2.2. Раскладываем поля в мапу
		collectClaimsAttributes(extendedAttributesMap, documentsExtendedAttributes);

		// 3. Находим список всех расширенных полей для кредитных заявок и раскладываем их в мапу (ключ - это идентификатор документа, значение - его расширенные поля)
		Collection<ClientHistoryExtendedAttribute> loanClaimsExtendedAttributes = new ArrayList<ClientHistoryExtendedAttribute>();

		//3.1. Ищем расширенные поля для пяти заявок за раз
		for(int i = 0; i < loanClaimIds.size(); i+= BATCH_SIZE)
		{
			List<Long> claimIds = buildIdsSubList(loanClaimIds, i);
			loanClaimsExtendedAttributes.addAll(businessDocumentService.findLoanClaimsExtendedFields(claimIds, minLoanClaimDate, maxLoanClaimDate));
	    }

		// 3.2. Раскладываем поля в ту же мапу, что и в 2.2
		collectClaimsAttributes(extendedAttributesMap, loanClaimsExtendedAttributes);

		Collection<ClientHistoryExtendedAttribute> loanCardClaimsExtendedAttributes = new ArrayList<ClientHistoryExtendedAttribute>();
		for(int i = 0; i < loanCardClaimIds.size(); i+= BATCH_SIZE)
		{
			List<Long> claimIds = buildIdsSubList(loanCardClaimIds, i);
			loanCardClaimsExtendedAttributes.addAll(businessDocumentService.findLoanCardClaimsExtendedFields(claimIds, minLoanCardClaimDate, maxLoanCardClaimDate));
		}

		// 3.3. Раскладываем поля в ту же мапу, что и в 2.2
		collectClaimsAttributes(extendedAttributesMap, loanCardClaimsExtendedAttributes);

		return extendedAttributesMap;
	}

	private void collectClaimsAttributes(Map<Long, List<ExtendedAttribute>> extendedAttributesMap, Collection<ClientHistoryExtendedAttribute> claimsExtendedAttributes)
	{
		for (ClientHistoryExtendedAttribute extendedAttribute : claimsExtendedAttributes)
		{
			Long paymentId = extendedAttribute.getPaymentId();
			List<ExtendedAttribute> attributes = extendedAttributesMap.get(paymentId);
			if (attributes == null)
			{
				attributes = new ArrayList<ExtendedAttribute>();
				extendedAttributesMap.put(paymentId, attributes);
			}

			attributes.add(extendedAttribute);
		}
	}

	protected Map<Long, String> buildFormNamesMap() throws SystemException
	{
		Map<Long, String> formNamesMap = new HashMap<Long, String>();
		List<MetadataBean> beans = paymentFormService.getAllFormsLight();

		for (Long formId : formIds)
		{
			for (MetadataBean bean : beans)
			{
				if (bean.getId().equals(formId))
					formNamesMap.put(formId, bean.getName());
			}
		}
		return formNamesMap;
	}

	protected Map<Long, Department> buildDepartmentsMap() throws SystemException
	{
		// Находим список департаментов и складываем их в мапу (id департамента - департамент)
		Map<Long, Department> departmentsMap = new HashMap<Long, Department>();
		List<Department> departments = departmentService.findByIds(departmentIds);

		for (Department department : departments)
		{
			departmentsMap.put(department.getId(), department);
		}
		return departmentsMap;
	}

	private List<Long> buildIdsSubList(List<Long> ids, int fromIndex)
	{
		if (fromIndex + BATCH_SIZE < ids.size())
		{
			return ids.subList(fromIndex, fromIndex + BATCH_SIZE);
		}
		else
		{
			List<Long> docIds = ids.subList(fromIndex, ids.size());
			for (int j = docIds.size(); j < BATCH_SIZE; j++)
			{
				docIds.add(null);
			}
			return docIds;
		}
	}

	protected BusinessDocumentBase transformDocumentEntity(PaymentDocumentEntity entity, BusinessDocumentOwner documentOwner) throws DocumentException
	{
		BusinessDocumentBase document = null;
		String documentKind = entity.getKind();

		if (documentKind.equals("EL"))
			document = setupGateExecutableDocument(new ExtendedLoanClaim(), entity, documentOwner);

		else if (documentKind.equals("LCC"))
			document = setupGateExecutableDocument(new LoanCardClaim(), entity, documentOwner);

		else if (documentKind.equals("A"))
			document = setupBusinessDocumentBase(new DefaultClaim(), entity, documentOwner);

		else if (documentKind.equals("B"))
			document = setupGateExecutableDocument(new DepositClosingClaim(), entity, documentOwner);

		else if (documentKind.equals("C"))
			document = setupAbstractPaymentDocument(new AccountClosingClaim(), entity, documentOwner);

		else if (documentKind.equals("D"))
			document = setupAbstractPaymentDocument(new ContactPayment(), entity, documentOwner);

		else if (documentKind.equals("E"))
			document = setupAbstractAccountsTransfer(new InternalTransfer(), entity, documentOwner);

		else if (documentKind.equals("F"))
			document = setupAbstractAccountsTransfer(new CurrencyPayment(), entity, documentOwner);

		else if (documentKind.equals("G"))
			document = setupAbstractAccountsTransfer(new AccountOpeningClaim(), entity, documentOwner);

		else if (documentKind.equals("H"))
			document = setupRurPayment(new RurPayment(), entity, documentOwner);

		else if (documentKind.equals("I"))
			document = setupGateExecutableDocument(new LoyaltyProgramRegistrationClaim(), entity, documentOwner);

		else if (documentKind.equals("J"))
			document = setupAbstractPaymentDocument(new GKHPayment(), entity, documentOwner);

		else if (documentKind.equals("K"))
			document = setupAbstractPaymentDocument(new GoodsAndServicesPayment(), entity, documentOwner);

		else if (documentKind.equals("L"))
			document = setupDepositOpeningClaim(new DepositOpeningClaim(), entity, documentOwner);

		else if (documentKind.equals("M"))
			document = setupGateExecutableDocument(new com.rssl.phizic.business.documents.LoanClaim(), entity, documentOwner);

		else if (documentKind.equals("N"))
			document = setupGateExecutableDocument(new RecallDocument(), entity, documentOwner);

		else if (documentKind.equals("O"))
			document = setupGateExecutableDocument(new LossPassbookApplication(), entity, documentOwner);

		else if (documentKind.equals("P"))
			document = setupJurPayment(new JurPayment(), entity, documentOwner);

		else if (documentKind.equals("Q"))
			document = setupGateExecutableDocument(new BlockingCardClaim(), entity, documentOwner);

		else if (documentKind.equals("R"))
			document = setupRefuseLongOfferClaim(new RefuseLongOfferClaim(), entity, documentOwner);

		else if (documentKind.equals("S"))
			document = setupAbstractPaymentDocument(new ChargeOffPaymentImpl(), entity, documentOwner);

		else if (documentKind.equals("T"))
			document = setupAbstractLongOfferDocument(new LoanPayment(), entity, documentOwner);

		else if (documentKind.equals("U"))
			document = setupGateExecutableDocument(new SecuritiesTransferClaim(), entity, documentOwner);

		else if (documentKind.equals("V"))
			document = setupGateExecutableDocument(new SecurityRegistrationClaim(), entity, documentOwner);

		else if (documentKind.equals("W"))
			document = setupGateExecutableDocument(new DepositorFormClaim(), entity, documentOwner);

		else if (documentKind.equals("X"))
			document = setupGateExecutableDocument(new RecallDepositaryClaim(), entity, documentOwner);

		else if (documentKind.equals("Y"))
			document = setupPFRStatementClaim(new PFRStatementClaim(), entity, documentOwner);

		else if (documentKind.equals("Z"))
			document = setupRurPayment(new CreateAutoPaymentImpl(), entity, documentOwner);

		else if (documentKind.equals("1"))
			document = setupAbstractAccountsTransfer(new AccountClosingPayment(), entity, documentOwner);

		else if (documentKind.equals("2"))
			document = setupRurPayment(new EditAutoPaymentImpl(), entity, documentOwner);

		else if (documentKind.equals("3"))
			document = setupRurPayment(new RefuseAutoPaymentImpl(), entity, documentOwner);

		else if (documentKind.equals("4"))
			document = setupAbstractPaymentDocument(new LoanOfferClaim(), entity, documentOwner);

		else if (documentKind.equals("5"))
			document = setupAbstractPaymentDocument(new LoanCardOfferClaim(), entity, documentOwner);

		else if (documentKind.equals("6"))
			document = setupAbstractPaymentDocument(new LoanProductClaim(), entity, documentOwner);

		else if (documentKind.equals("7"))
			document = setupAbstractPaymentDocument(new LoanCardProductClaim(), entity, documentOwner);

		else if (documentKind.equals("8"))
			document = setupAbstractPaymentDocument(new VirtualCardClaim(), entity, documentOwner);

		else if (documentKind.equals("9"))
			document = setupJurPayment(new EditAutoSubscriptionPayment(), entity, documentOwner);

		else if (documentKind.equals("0"))
			document = setupJurPayment(new DelayAutoSubscriptionPayment(), entity, documentOwner);

		else if (documentKind.equals(";"))
			document = setupJurPayment(new RecoveryAutoSubscriptionPayment(), entity, documentOwner);

		else if (documentKind.equals(":"))
			document = setupJurPayment(new CloseAutoSubscriptionPayment(), entity, documentOwner);

		else if (documentKind.equals("!"))
			document = setupAbstractAccountsTransfer(new IMAOpeningClaim(), entity, documentOwner);

		else if (documentKind.equals("RE"))
			document = setupGateExecutableDocument(new ReIssueCardClaim(), entity, documentOwner);

		else if (documentKind.equals("UD"))
			document = setupAbstractPaymentDocument(new RemoteConnectionUDBOClaim(), entity, documentOwner);

		else if (documentKind.equals("RO"))
			document = setupRollbackOrderClaim(new RollbackOrderClaim(), entity, documentOwner);

		else if (documentKind.equals("RG"))
			document = setupGateExecutableDocument(new RefundGoodsClaim(), entity, documentOwner);

		else if (documentKind.equals("CM"))
			document = setupGateExecutableDocument(new ChangeDepositMinimumBalanceClaim(), entity, documentOwner);

		else if (documentKind.equals("CI"))
			document = setupGateExecutableDocument(new AccountChangeInterestDestinationClaim(), entity, documentOwner);

		else if (documentKind.equals("EM"))
			document = setupGateExecutableDocument(new ShortLoanClaim(), entity, documentOwner);

		else if (documentKind.equals("PC"))
			document = setupAbstractPaymentDocument(new PreapprovedLoanCardClaim(), entity, documentOwner);

		else if (documentKind.equals("CS"))
			document = setupJurPayment(new CreateInvoiceSubscriptionPayment(), entity, documentOwner);

		else if (documentKind.equals("ES"))
			document = setupJurPayment(new EditInvoiceSubscriptionClaim(), entity, documentOwner);

		else if (documentKind.equals("DS"))
			document = setupJurPayment(new DelayInvoiceSubscriptionClaim(), entity, documentOwner);

		else if (documentKind.equals("RS"))
			document = setupJurPayment(new RecoveryInvoiceSubscriptionClaim(), entity, documentOwner);

		else if (documentKind.equals("RI"))
			document = setupJurPayment(new CloseInvoiceSubscriptionClaim(), entity, documentOwner);

		else if (documentKind.equals("GP"))
			document = setupGateExecutableDocument(new GetPrivateOperationScanClaim(), entity, documentOwner);

		else if (documentKind.equals("CD"))
			document = setupGateExecutableDocument(new CardReportDeliveryClaim(), entity, documentOwner);

		else if (documentKind.equals("IP"))
			document = setupJurPayment(new InvoicePayment(), entity, documentOwner);

		else if (documentKind.equals("CA"))
			document = setupAbstractAccountsTransfer(new CreateP2PAutoTransferClaim(), entity, documentOwner);

		else if (documentKind.equals("EA"))
			document = setupAbstractAccountsTransfer(new EditP2PAutoTransferClaim(), entity, documentOwner);

		else if (documentKind.equals("RC"))
			document = setupAbstractAccountsTransfer(new RecoveryP2PAutoTransferClaim(), entity, documentOwner);

		else if (documentKind.equals("DC"))
			document = setupAbstractAccountsTransfer(new DelayP2PAutoTransferClaim(), entity, documentOwner);

		else if (documentKind.equals("CC"))
			document = setupAbstractAccountsTransfer(new CloseP2PAutoTransferClaim(), entity, documentOwner);

		else if (documentKind.equals("MB"))
			document = setupAbstractAccountsTransfer(new CreateMoneyBoxPayment(), entity, documentOwner);

		else if (documentKind.equals("ME"))
			document = setupAbstractAccountsTransfer(new EditMoneyBoxClaim(), entity, documentOwner);

		else if (documentKind.equals("SB"))
			document = setupAbstractAccountsTransfer(new RefuseMoneyBoxPayment(), entity, documentOwner);

		else if (documentKind.equals("CB"))
			document = setupAbstractAccountsTransfer(new CloseMoneyBoxPayment(), entity, documentOwner);

		else if (documentKind.equals("RB"))
			document = setupAbstractAccountsTransfer(new RecoverMoneyBoxPayment(), entity, documentOwner);

		else if (documentKind.equals("CR"))
			document = setupJurPayment(new CreditReportPaymentImpl(), entity, documentOwner);

		else if (documentKind.equals("CE"))
			document = setupGateExecutableDocument(new ReportByCardClaim(), entity, documentOwner);
		else if (documentKind.equals("CL"))
			document = setupGateExecutableDocument(new ChangeCreditLimitClaim(), entity, documentOwner);
		else if (documentKind.equals("ER"))
			document = setupAbstractPaymentDocument(new EarlyLoanRepaymentClaimImpl(), entity, documentOwner);
		else
			throw new IllegalArgumentException("Неизвестен тип документа!");

		return document;
	}

	protected BusinessDocumentBase setupBusinessDocumentBase(BusinessDocumentBase document, PaymentDocumentEntity entity, BusinessDocumentOwner documentOwner) throws DocumentException
	{
		document.setId(entity.getDocumentId());

		try
		{
			document.setOwner(documentOwner);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}

		document.setDocumentNumber(entity.getDocumentNumber());
		document.setState(new State(entity.getStateCode(), entity.getStateDescription()));
		document.setSystemName(entity.getSystemName());

		String commissionCurrency = entity.getCommissionCurrency();
		BigDecimal commission = entity.getCommission();

		if (commission !=null & StringHelper.isNotEmpty(commissionCurrency))
		{
			try
			{
				CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
				Currency comCurrency = currencyService.findByAlphabeticCode(commissionCurrency);
				document.setCommission(new Money(commission, comCurrency));
			}
			catch (GateException e)
			{
				throw new DocumentException(e);
			}
		}

		document.setArchive(entity.getArchive());
		document.setCreationType(CreationType.fromValue(entity.getCreationType()));
		if (StringHelper.isNotEmpty(entity.getClientOperationChannel()))
		{
			document.setClientOperationChannel(CreationType.fromValue(entity.getClientOperationChannel()));
		}
		document.setRefusingReason(entity.getRefusingReason());
		document.setDateCreated(entity.getDateCreated());
		document.setOperationDate(entity.getOperationDate());
		document.setAdmissionDate(entity.getAdmissionDate());
		document.setExecutionDate(entity.getExecutionDate());
		document.setDocumentDate(entity.getDocumentDate());
		document.setStateMachineName(entity.getStateMachineName());
		document.setTemplateId(entity.getTemplateId());
		document.setCountError(entity.getCountError());
		document.setCreationSourceType(CreationSourceType.fromValue(entity.getCreationSourceType()));
		if (entity.getConfirmStrategyType() != null)
			document.setConfirmStrategyType(ConfirmStrategyType.valueOf(entity.getConfirmStrategyType()));
		document.setOperationUID(entity.getOperationUID());
		document.setSessionId(entity.getSessionId());
		document.setPromoCode(entity.getPromoCode());
		document.setCreatedEmployeeLoginId(entity.getCreatedEmployeeLoginId());
		document.setConfirmedEmployeeLoginId(entity.getConfirmedEmployeeLoginId());
		document.setCodeATM(entity.getCodeATM());
		if (StringHelper.isNotEmpty(entity.getAdditionalOperationChannel()))
		{
			document.setAdditionalOperationChannel(CreationType.fromValue(entity.getAdditionalOperationChannel()));
		}
		if (entity.getLoginType() != null)
			document.setLoginType(LoginType.valueOf(entity.getLoginType()));
		document.setPayerName(entity.getPayerName());
		return document;
	}

	protected BusinessDocumentBase setupGateExecutableDocument(GateExecutableDocument document, PaymentDocumentEntity entity, BusinessDocumentOwner documentOwner) throws DocumentException
	{
		setupBusinessDocumentBase(document, entity, documentOwner);
		document.setExternalId(entity.getExternalId());
		document.setExternalOwnerId(entity.getExternalOwnerId());
		return document;
	}

	protected BusinessDocumentBase setupAbstractPaymentDocument(AbstractPaymentDocument document, PaymentDocumentEntity entity, BusinessDocumentOwner documentOwner) throws DocumentException
	{
		setupGateExecutableDocument(document, entity, documentOwner);
		document.setChargeOffAccount(entity.getChargeOffAccount());
		document.setConfirmEmployee(entity.getConfirmEmployee());

		try
		{
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
			String chargeOffCurrencyStr = entity.getChargeOffCurrency();
			BigDecimal chargeOffAmount = entity.getChargeOffAmount();

			if (chargeOffAmount !=null & StringHelper.isNotEmpty(chargeOffCurrencyStr))
			{
				Currency chargeOffCurrency = currencyService.findByAlphabeticCode(chargeOffCurrencyStr);
				document.setChargeOffAmount(new Money(chargeOffAmount, chargeOffCurrency));
			}

			String destinationCurrencyStr = entity.getDestinationCurrency();
			BigDecimal destinationAmount = entity.getDestinationAmount();

			if (destinationAmount != null && StringHelper.isNotEmpty(destinationCurrencyStr))
			{
				Currency destinationCurrency = currencyService.findByAlphabeticCode(destinationCurrencyStr);
				document.setDestinationAmount(new Money(entity.getDestinationAmount(), destinationCurrency));
			}
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}

		String summType = entity.getSummType();
		if (StringHelper.isNotEmpty(summType))
			document.setInputSumType(InputSumType.fromValue(summType).toString());

		document.setGround(entity.getGround());
		document.setReceiverName(entity.getReceiverName());
		return document;
	}

	protected BusinessDocumentBase setupAbstractLongOfferDocument(AbstractLongOfferDocument document, PaymentDocumentEntity entity, BusinessDocumentOwner documentOwner) throws DocumentException
	{
		setupAbstractPaymentDocument(document, entity, documentOwner);
		document.setLongOffer(entity.getLongOffer());
		return document;
	}

	protected BusinessDocumentBase setupRefuseLongOfferClaim(RefuseLongOfferClaim document, PaymentDocumentEntity entity, BusinessDocumentOwner documentOwner) throws DocumentException
	{
		setupGateExecutableDocument(document, entity, documentOwner);
		document.setLongOffer(entity.getLongOffer());
		return document;
	}

	protected BusinessDocumentBase setupAbstractAccountsTransfer(AbstractAccountsTransfer document, PaymentDocumentEntity entity, BusinessDocumentOwner documentOwner) throws DocumentException
	{
		setupAbstractLongOfferDocument(document, entity, documentOwner);
		document.setReceiverAccount(entity.getReceiverAccount());
		return document;
	}

	protected BusinessDocumentBase setupRurPayment(RurPayment document, PaymentDocumentEntity entity, BusinessDocumentOwner documentOwner) throws DocumentException
	{
		setupAbstractAccountsTransfer(document, entity, documentOwner);
		document.setReceiverPointCode(entity.getProviderExternalId());
		document.setReceiverInternalId(entity.getReceiverInternalId());
		document.setBillingDocumentNumber(entity.getBillingDocumentNumber());
		return document;
	}

	protected BusinessDocumentBase setupDepositOpeningClaim(DepositOpeningClaim document, PaymentDocumentEntity entity, BusinessDocumentOwner documentOwner) throws DocumentException
	{
		setupAbstractPaymentDocument(document, entity, documentOwner);
		document.setFromAccount(entity.getPayerAccount());
		return document;
	}

	protected BusinessDocumentBase setupJurPayment(JurPayment document, PaymentDocumentEntity entity, BusinessDocumentOwner documentOwner) throws DocumentException
	{
		setupRurPayment(document, entity, documentOwner);
		document.setAutoPaySchemeAsString(entity.getAutoPaySchemeAsString());
		return document;
	}

	protected BusinessDocumentBase setupPFRStatementClaim(PFRStatementClaim document, PaymentDocumentEntity entity, BusinessDocumentOwner documentOwner) throws DocumentException
	{
		setupGateExecutableDocument(document, entity, documentOwner);
		document.setReceiverName(entity.getReceiverName());
		return document;
	}

	protected BusinessDocumentBase setupRollbackOrderClaim(RollbackOrderClaim document, PaymentDocumentEntity entity, BusinessDocumentOwner documentOwner) throws DocumentException
	{
		setupGateExecutableDocument(document, entity, documentOwner);
		String chargeOffCurrencyStr = entity.getChargeOffCurrency();
		BigDecimal chargeOffAmount = entity.getChargeOffAmount();

		if (chargeOffAmount !=null & StringHelper.isNotEmpty(chargeOffCurrencyStr))
		{
			try
			{
				CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
				Currency chargeOffCurrency = currencyService.findByAlphabeticCode(chargeOffCurrencyStr);
				document.setChargeOffAmount(new Money(chargeOffAmount, chargeOffCurrency));
			}
			catch (GateException e)
			{
				throw new DocumentException(e);
			}
		}
		return document;
	}
}
