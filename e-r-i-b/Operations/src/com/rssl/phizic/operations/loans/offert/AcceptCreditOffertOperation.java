package com.rssl.phizic.operations.loans.offert;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.ikfl.crediting.CRMMessageService;
import com.rssl.ikfl.crediting.ClaimNumberGenerator;
import com.rssl.phizgate.ext.sbrf.etsm.OfferOfficePrior;
import com.rssl.phizgate.ext.sbrf.etsm.OfferPrior;
import com.rssl.phizic.auth.modes.*;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.etsm.offer.*;
import com.rssl.phizic.business.etsm.offer.service.OfferPriorWebService;
import com.rssl.phizic.business.fraudMonitoring.FraudMonitoringSendersFactory;
import com.rssl.phizic.business.loanclaim.creditProduct.CreditProduct;
import com.rssl.phizic.business.loanclaim.creditProduct.CreditProductService;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductCondition;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductConditionService;
import com.rssl.phizic.business.loanclaim.creditProduct.type.CreditProductType;
import com.rssl.phizic.business.loanclaim.creditProduct.type.CreditProductTypeService;
import com.rssl.phizic.business.loanclaim.creditProduct.type.CreditSubProductType;
import com.rssl.phizic.business.offers.LoanOffer;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.business.template.offer.CreditOfferTemplate;
import com.rssl.phizic.business.template.offer.CreditOfferTemplateService;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.rsa.PhaseType;
import com.rssl.phizic.rsa.senders.FraudMonitoringSender;
import com.rssl.phizic.rsa.senders.types.EventsType;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author Moshenko
 * @ created 30.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AcceptCreditOffertOperation extends ConfirmableOperationBase
{
	private static final SimpleService simpleService = new SimpleService();
	private static final CreditOfferTemplateService templateService = new CreditOfferTemplateService("credit-offer-template-cache-name");
	private static final CreditProductConditionService conditionService = new CreditProductConditionService();
	private static final OfferPriorService officePriorService = new OfferPriorService();
	private static final OfferPriorWebService offerPriorWebService = new OfferPriorWebService();
	private static final AccessPolicyService accessService = new AccessPolicyService();
	private static final PersonService personService = new PersonService();
	private static final CreditProductService productService    = new CreditProductService();
	private static final CreditProductTypeService productTypeService    = new CreditProductTypeService();
	private static DepartmentService departmentService = new DepartmentService();
	private static final CRMMessageService crmMessageService = new CRMMessageService();
	private final static PaymentStateMachineService stateMachineService = new PaymentStateMachineService();

	private ConfirmableObject confirmableObject;
	//������ �� �������������
	private OfferConfirmed offerConfirmed;
	//������ �� ������� ����
	private List<OfferEribPrior> offerEribPrior = null;
	//������ �� ������� �� ����
	private List<OfferOfficePrior> offerOfficePrior = null;
	// �����������
	private Boolean ensuring;
	// ��� ���������� ��������
	private String productName;
	//���� ����������
	private String enrollAccount;
	//������ �������. ��������� ��������: 1� �� ��������� ����� 2 � �� ����� ����� 3 � �� ��������� ���������� ����� 4 � �� ����� ���������� ����� 5 � �� ��������� ������� ���� 6 � �� ����� ������� ����
	private String enrollAccountType;
	//���������� �� �����
	private Department claimDrawDepartment;
	//������ ������
	private CreditOfferTemplate offertTemplate;
	//������ ��� ������
	private CreditOfferTemplate pdpOfferTemplate;
	//������� ��� �������� ���
	private String phone;
	//�������������� �����������
	private LoanOffer loanOffer;
	//������ � ����
	private ExtendedLoanClaim claim;
	//������������� �����
	private String appNum;

	//����� ����� ������ � ����
	private final static int OFFER_LIFE_DAYS = 31;
	/**
	 * ������������� ��� ����� ����
	 * @param appNum ������������� ������
	 * @param claimId id ������ � ����
	 * @throws BusinessException
	 */
	public void initialize(String appNum, Long claimId) throws BusinessException
	{
		this.appNum = appNum;

		Date lifeDate = DateHelper.add(new Date(), 0, 0, OFFER_LIFE_DAYS);
		offerEribPrior = officePriorService.getEribOffers(appNum, DateHelper.toCalendar(lifeDate), "ACTIVE");
		if (offerEribPrior.isEmpty())
			throw new BusinessException("�� �������  ����� � ������� ACTIVE, ����� �������� ������ 31 ���  � � applicationNumber:" + appNum);

		boolean haveCliam = false;
		for (OfferEribPrior offer: offerEribPrior)
		{
			if (claimId.equals(offer.getClaimId()))
				haveCliam = true;
		}

		if (!haveCliam)
			throw new BusinessException("�� �������  ����� � applicationNumber:" + appNum + " ��������� � ������� � claimId:" + claimId);


		claim = simpleService.findById(ExtendedLoanClaim.class, claimId);
		if (claim == null)
			throw new BusinessException("�� ������� ����� ��������� ������ � id:" + claimId);

		if (claim.isUseLoanOffer())
		{
			loanOffer = claim.getLoanOffer();

			if (loanOffer != null  && loanOffer.getTopUps().size() > 0)
			{
				pdpOfferTemplate = new CreditOfferTemplate(templateService.findPdp());
				pdpOfferTemplate.setText(XmlHelper.getWithHtmlTag(pdpOfferTemplate.getText(), null));
			}
		}
		else
		{
			if (claim.getConditionId() == null)
			{
				throw new BusinessException("�� ������� ����� ��������� ������� � ������ � id:" + claimId);
			}

			CreditProductCondition condition = conditionService.findeById(claim.getConditionId());
			if (condition == null)
			{
				throw new BusinessException("�� ������� ����� ��������� ������� � ������ � id:" + claimId);
			}

			ensuring = condition.getCreditProduct().isEnsuring();
		}

		if (StringHelper.isNotEmpty(claim.getLoanIssueAccount()))
			enrollAccount = claim.getLoanIssueAccount();
		else if (StringHelper.isNotEmpty(claim.getLoanIssueCard()))
			enrollAccount = claim.getLoanIssueCard();
		else
			throw new BusinessException("� �������� ������ � id: " + claimId + "�� ������ �� ����� ����� ��� ������ �������, �� ����� ����� � ��������� ��� ������ �������");

		offertTemplate = templateService.getActiveTemplate();
		if (offertTemplate == null)
			throw new BusinessException("�� ������� ���������� �������� ������ ������");

		productName = claim.getProductName();
		phone = claim.getMobileNumber();
		claimDrawDepartment = claim.getClaimDrawDepartment();
		setPriority();
	}

	//������������� ������� ������������ ������
	private void setPriority()
	{
		BigDecimal curAmount = null ;
		if (claim != null)
		{//����
			OfferEribPrior curOffer = null;
			BigDecimal cliamAmount = claim.getLoanAmount().getDecimal();
			for (OfferEribPrior offer:offerEribPrior)
			{
				BigDecimal offerAmount = offer.getAltAmount();
				if (cliamAmount.compareTo(offerAmount) == 0)
					offer.setPriority(true);
				else
				{
					if (curAmount == null)
					{
						curAmount = offerAmount;
						curOffer = offer;
					}
					else
					{
						if (compareAmount(curAmount, offerAmount, cliamAmount));
						{
							curAmount = offerAmount;
							curOffer = offer;
						}
					}
				}
			}
			curOffer.setPriority(true);
		}
		else
		{//�� ����
			OfferOfficePrior curOffer = null;
			for (OfferOfficePrior offer:offerOfficePrior)
			{
				BigDecimal offerAmount = offer.getAltAmount();
				if (curAmount == null)
				{
					curAmount = offerAmount;
				}
				else
				{
					//TODO ���������� � ������ � ����� ������
					//compareAmount(curAmount, offerAmount, offerAmount)
					if (curAmount.compareTo(offerAmount) == -1)
						curOffer = offer;
				}

			}
			curOffer.setPriority(true);

		}
	}

	private boolean compareAmount(BigDecimal curAmount, BigDecimal offerAmount, BigDecimal cliamAmount)
	{  //���� ����� ������ ��� ����� ��� ���������� �� ������ ��� � ������
		return (curAmount.compareTo(offerAmount) == -1 || curAmount.compareTo(offerAmount) == 0) && (curAmount.compareTo(cliamAmount) == -1);
	}

	/**
	 * ������������� ��� ����� �� ����
	 * @param appNum ������������� ������
	 * @throws BusinessException
	 */
	public void initialize(String appNum) throws BusinessLogicException, BusinessException
	{
		this.appNum = appNum;
			offerOfficePrior = offerPriorWebService.getOfferOfficePrior(appNum);

		if (offerOfficePrior.isEmpty())
			throw new BusinessException("�� �������  ����� � applicationNumber:" + appNum);

		OfferOfficePrior offer = offerOfficePrior.get(0);

		//���. ���� (2 �����), ��� (4 �����), ��� (5 ����)
		String departmentStr = offerOfficePrior.get(0).getDepartment();
		if (StringHelper.isEmpty(departmentStr))
			throw new BusinessException("� ������ �� ������ ����������� (applicationNumber:" + appNum + ")");

		String tb = departmentStr.substring(0, 2);
		String osb = departmentStr.substring(2, 6);
		String vsp = departmentStr.substring(6, 11);
		Code code = new ExtendedCodeImpl(StringHelper.removeLeadingZeros(tb), StringHelper.removeLeadingZeros(osb), StringHelper.removeLeadingZeros(vsp));

		claimDrawDepartment = departmentService.findByCode(code);
		if (claimDrawDepartment == null)
			throw new BusinessException(String.format("�� ������� ������������� �� ������ �� ������: tb=%s,osb=%s,vsp=%s", tb, osb, vsp));

		String productCode = offer.getProductCode();
		if (StringHelper.isEmpty(productCode))
			throw new BusinessException("� ������ �� ������� ��� �������� (applicationNumber:" + appNum + ")");

		String subProductCode = offer.getSubProductCode();
		if (StringHelper.isEmpty(productCode))
			throw new BusinessException("� ������ �� ������� ��� ��� �������� (applicationNumber:" + appNum + ")");

		String currency = offer.getCurrency();
		if (StringHelper.isEmpty(productCode))
			throw new BusinessException("� ������ �� �������� ������ �������� (applicationNumber:" + appNum + ")");

		List<CreditProduct> products = productService.findByCode(productCode);
		if (products.isEmpty())
			throw new BusinessException("�� ���� " + productCode + " �� ������� ��������� ��������");

		offertTemplate = templateService.getActiveTemplate();
		if (offertTemplate == null)
			throw new BusinessException("�� ������� ���������� �������� ������ ������");

		//���������� ��������� �������
		CreditProduct ourProduct = null;
		for(CreditProduct product:products)
		{
			//���� �� ����� ��� ��� �������� + �� + ������
			for (CreditSubProductType subProdType:product.getCreditSubProductTypes())
			{
				String subCode = subProdType.getCode();
				if (StringHelper.isNotEmpty(subCode) && subCode.equals(subProductCode) &&
						StringHelper.equals(subProdType.getTerbank(), tb) &&
						StringHelper.equals(subProdType.getCurrency().getCode(), currency))
				{
					ourProduct = subProdType.getCreditProduct();
					productName = ourProduct.getName();
					ensuring = ourProduct.isEnsuring();
					break;
				}
			}
		}

		if (ourProduct == null)
		{//���� �� ����� �� ��� �������� + ��� ��� �������� + �� + ������, �� �������� ���������� �� ���� ���� ��������.

			String productTypeCode = offer.getProductTypeCode();
			if (StringHelper.isEmpty(productTypeCode))
				throw new BusinessException("�� �������� ���������� ������������ ���������� ��������  (applicationNumber:" + appNum + ")");
			CreditProductType productType = productTypeService.findeByCode(productTypeCode);
			if (productCode == null)
				throw new BusinessException("�� �������� ����� ��� ���������� �������� �� ����  (productTypeCode:" + productTypeCode + ")");

			productName = productType.getName();
			ensuring = null;
		}

		enrollAccount = offer.getAccountNumber();
		if (StringHelper.isEmpty(enrollAccount))
			throw new BusinessException("�� �������� ���������� ���� ���������� (applicationNumber:" + appNum + ")");
		enrollAccountType = offer.getTypeOfIssue();
		if (StringHelper.isEmpty(enrollAccountType))
			throw new BusinessException("�� �������� ���������� ��� ��������� ���������� (applicationNumber:" + appNum + ")");

		setPriority();
	}

	/**
	 * @param appNum ����� ������
	 * @param claimId id ������ � ����
	 * @throws BusinessException
	 */
	public void refuse(String appNum, Long claimId) throws BusinessException
	{
		claim = simpleService.findById(ExtendedLoanClaim.class, claimId);

		if (claim == null)
			throw new BusinessException("�� ������� ����� ��������� ������ � id:" + claimId);

        if (!claim.getOwner().getLogin().getId().equals(PersonContext.getPersonDataProvider().getPersonData().getLogin().getId()))
            throw new BusinessException("��������� ������ � id: " + claimId + " �� ����������� ���.");

        OfferOfficePrior officeOffer = officePriorService.disableOfficeOffer(appNum);
        OfferEribPrior eribOffer = officePriorService.disableEribOffer(appNum, claimId);

        try
        {
            if (officeOffer != null)
                crmMessageService.sendRefuseConsumerProductOfferResultRq(officeOffer.getRqUid(), claim.getOperationUID());
            if (eribOffer != null)
                crmMessageService.sendRefuseConsumerProductOfferResultRq(officeOffer.getRqUid(), claim.getOperationUID());
        }
        catch (Exception e)
        {
            throw new BusinessException("������ ��� �������� ������ � id:" + claimId, e);
        }

	}

	/**
	 * @return ������������ �� ����������� ������ ��� �����
	 * @throws BusinessException
	 */
	public boolean isOneTimePassword() throws BusinessException
	{
		AuthenticationContext authContext = AuthenticationContext.getContext();
		PersonDataProvider dataProvider = PersonContext.getPersonDataProvider();
		PersonData personData = dataProvider.getPersonData();
	    ActivePerson person = personData.getPerson();

		// ���������������� ��������� ������������ �� ��������
		AccessPolicy policy = authContext.getPolicy();
		Properties userProperties = null;
		try
		{
			AccessType accessType = policy.getAccessType();
			userProperties = accessService.getProperties(person.getLogin(), accessType);
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}

		if (userProperties == null)
			throw new BusinessException("�� ������� ���������������� �������� ������������� ��� ������� �������� �������");

		// ��� �������������
		String userConfirmValue = userProperties.getProperty("userOptionType");

		// ���� �� ������� - ����� �� client-authantication-modes.xml, ��������� ��� ���������
		if (userConfirmValue == null)
		{
			ConfirmStrategy result = getConfirmStrategyProvider().getStrategy();
			if (result instanceof CompositeConfirmStrategy)
			{
				CompositeConfirmStrategy strategy = (CompositeConfirmStrategy) result;
				userConfirmValue = strategy.getDefaultStrategy().toString();
			}
		}
		// ������ �����
		String authValue = userProperties.getProperty(policy.getAuthenticationChoice().getProperty());
		Map<String,Boolean> confirmAuthOptions = new HashMap<String,Boolean>();
		confirmAuthOptions.put("lp", false);
		confirmAuthOptions.put("smsp", true);
		return confirmAuthOptions.get(authValue);
	}

	public ConfirmableObject getConfirmableObject()
	{
		if (confirmableObject == null)
			confirmableObject = new AcceptCreditOffertConfirmableObj("/private/credit/offert/accept");
		return confirmableObject;
	}

	protected void saveConfirm() throws BusinessException, BusinessLogicException, SecurityLogicException
	{
		//1.�������� ������ �� ����
		try
		{
			doPostFraudControl();
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}

		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					//2. ���������� ������
					offerConfirmed.setOfferDate(Calendar.getInstance());
					officePriorService.addOrUpdate(offerConfirmed);

					//3. ��������� ������ �� ������ ����
					if (claim != null)
					{
						updateState(claim);
						claim.setConfirmedOfferId(offerConfirmed.getId());
						simpleService.addOrUpdate(claim);
					}
					//4. �������� ���������� �����
					if (offerEribPrior != null)
						officePriorService.delete(offerEribPrior);
					if (offerOfficePrior != null)
						 offerPriorWebService.deleteOfferOfficePrior(offerConfirmed.getApplicationNumber(), offerOfficePrior.get(0).getOfferDate());

					//5. �������� ������� ������� �� ������
					sendAgreeRq(claim != null ? claim.getOperationUID() : getOperUID());
					return null;
				}
			}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private void sendAgreeRq(String operUID) throws Exception
	{
			crmMessageService.sendAgreeConsumerProductOfferResultRq(offerConfirmed.getRqUid(),
					operUID,
					offerConfirmed.getAltAmount(),
					offerConfirmed.getAltPeriod(),
					offerConfirmed.getAltInterestRate(),
					offerConfirmed.getOfferDate());
	}

	private String getOperUID() throws BusinessException
	{
		ClaimNumberGenerator generator = new ClaimNumberGenerator();
		return  "00000" + generator.getInverseClaimNumber();
	}

	//�������� ������ �� ����. (� confirm)
	//����� �� ������ ���� ������� � Transactional
	private void doPostFraudControl() throws GateLogicException
	{
		FraudMonitoringSender sender = FraudMonitoringSendersFactory.getInstance().getSender(EventsType.REQUEST_CREDIT);
		//noinspection unchecked
		sender.initialize(new OfferToFraudInitializationData(offerConfirmed, PhaseType.WAITING_FOR_RESPONSE));
		sender.send();
	}

	//��������� ���� (� preConfirm)
	public void doPreFraudControl()
	{
		try
		{
			FraudMonitoringSender sender = FraudMonitoringSendersFactory.getInstance().getSender(EventsType.REQUEST_CREDIT);
			//noinspection unchecked
			sender.initialize(new OfferToFraudInitializationData(offerConfirmed, PhaseType.SENDING_REQUEST));
			sender.send();
		}
		catch (Exception e)
		{
			log.error(e);
		}
	}

	protected void updateState(ExtendedLoanClaim claim) throws BusinessException, BusinessLogicException
	{
		StateMachineExecutor executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(claim.getFormName()));
		executor.initialize(claim);
		executor.fireEvent(new ObjectEvent(DocumentEvent.CONFIRM, ObjectEvent.CLIENT_EVENT_TYPE));
	}


	/**
	 * ��������� ���������� ������ �� �������������
	 * @param selectedOfferId id ��������� ������
	 * @throws BusinessException
	 */
	public void updateOffer(Long selectedOfferId) throws BusinessException
	{
		offerConfirmed = new OfferConfirmed();
		OfferPrior offer = null;
		if (offerEribPrior != null)
		{
			for (OfferEribPrior eribOffer: offerEribPrior)
			if (selectedOfferId.equals(eribOffer.getId()))
			{
				Long loginId = eribOffer.getClientLoginId();
				ActivePerson person = personService.findByLoginId(loginId);
				offerConfirmed.setClaimId(claim.getId());
				offerConfirmed.setClientLoginId(person.getLogin().getId());
				offerConfirmed.setFirstName(person.getFirstName());
				offerConfirmed.setLastName(person.getSurName());
				offerConfirmed.setMiddleName(person.getPatrName());
				offerConfirmed.setIdNum(claim.getOwnerIdCardNumber());
				offerConfirmed.setIdSeries(claim.getOwnerIdCardSeries());
				offerConfirmed.setApplicationNumber(eribOffer.getApplicationNumber());
				offerConfirmed.setRegistrationAddress(OfferAgrimentHelper.getRegistrationStr(claim));
				offerConfirmed.setRqUid(eribOffer.getRqUid());
				String borrower =  person.getSurName() + " " + person.getFirstName();
				if (StringHelper.isNotEmpty(person.getPatrName()))
					borrower = borrower + " " + person.getPatrName();
				offerConfirmed.setBorrower(borrower);
				for(PersonDocument personDocument:person.getPersonDocuments())
				{
					if (StringHelper.equals(personDocument.getDocumentName(), claim.getOwnerIdCardNumber()) &&
							StringHelper.equals(personDocument.getDocumentSeries(), claim.getOwnerIdCardSeries()))
					{
						offerConfirmed.setIssueDt(personDocument.getDocumentIssueDate());
						offerConfirmed.setIssuedBy(personDocument.getDocumentIssueBy());
						offerConfirmed.setIdType(personDocument.getDocumentType().toValue());
					}
				}
				offer = eribOffer;
				break;
			}
		}
		else if (offerOfficePrior != null)
		{
			for (OfferOfficePrior officeOffer: offerOfficePrior)
				if (selectedOfferId.equals(officeOffer.getId()))
				{
					ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
					offerConfirmed.setClientLoginId(person.getLogin().getId());
					offerConfirmed.setFirstName(officeOffer.getFirstName());
					offerConfirmed.setLastName(officeOffer.getLastName());
					offerConfirmed.setMiddleName(officeOffer.getMiddleName());
					offerConfirmed.setIdType(officeOffer.getIdType());
					offerConfirmed.setIdSeries(officeOffer.getIdSeries());
					offerConfirmed.setIdNum(officeOffer.getIdNum());
					offerConfirmed.setIssuedBy(officeOffer.getIdIssueBy());
					offerConfirmed.setIssueDt(officeOffer.getIdIssueDate());
					String borrower =  officeOffer.getLastName() + " " + officeOffer.getFirstName();
					if (StringHelper.isNotEmpty(officeOffer.getMiddleName()))
						borrower = borrower + " " + officeOffer.getMiddleName();
					offerConfirmed.setBorrower(borrower);
					offerConfirmed.setRegistrationAddress("");
					offerConfirmed.setRqUid(officeOffer.getRqUid());
					offer = officeOffer;
					break;
				}
		}
		offerConfirmed.setTemplateId(offertTemplate.getId());
		offerConfirmed.setAccountNumber(enrollAccount);
		updateCommon(offer);
	}

	private void updateCommon(OfferPrior offer)
	{
		offerConfirmed.setApplicationNumber(offer.getApplicationNumber());
		offerConfirmed.setClientCategory(offer.getClientCategory());
		offerConfirmed.setAltPeriod(offer.getAltPeriod());
		offerConfirmed.setAltAmount(offer.getAltAmount());
		offerConfirmed.setAltInterestRate(offer.getAltInterestRate());
		offerConfirmed.setAltFullLoanCost(offer.getAltFullLoanCost());
		offerConfirmed.setAltAnnuityPayment(offer.getAltAnnuityPayment());
		offerConfirmed.setVisibilityCounter(offer.getVisibilityCounter());
		offerConfirmed.setCounterUpdated(offer.getCounterUpdated());
		offerConfirmed.setApplicationNumber(appNum);
		AcceptCreditOffertConfirmableObj confirmableObj = (AcceptCreditOffertConfirmableObj)confirmableObject;
		confirmableObj.setCurrency("RUB");
		confirmableObj.setLoanAmount(offer.getAltAmount());
		confirmableObj.setLoanPeriod(offer.getAltPeriod());
		DecimalFormat df = new DecimalFormat("0.##");
		confirmableObj.setLoanRate(df.format(offer.getAltPeriod()) + "%");
	}

	public Boolean isEnsuring()
	{
		return ensuring;
	}

	public List<OfferEribPrior> getOfferEribPrior()
	{
		return offerEribPrior;
	}

	public LoanOffer getLoanOffer()
	{
		return loanOffer;
	}

	public List<OfferOfficePrior> getOfferOfficePrior()
	{
		return offerOfficePrior;
	}

	public String getProductName()
	{
		return productName;
	}

	public String getEnrollAccount()
	{
		return enrollAccount;
	}

	public Department getClaimDrawDepartment()
	{
		return claimDrawDepartment;
	}

	public CreditOfferTemplate getOffertTemplate()
	{
		return offertTemplate;
	}

	public String getPhone()
	{
		return phone;
	}

	public String getEnrollAccountType()
	{
		return enrollAccountType;
	}

	public CreditOfferTemplate getPdpOfferTemplate()
	{
		return pdpOfferTemplate;
	}
}
