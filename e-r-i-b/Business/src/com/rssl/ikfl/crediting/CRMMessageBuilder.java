package com.rssl.ikfl.crediting;

import com.rssl.phizic.TBSynonymsDictionary;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.ApplicationCRMType;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.CRMNewApplRq;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.ERIBUpdApplStatusRq;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.ErrorStatusType;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.ConsumerProductOfferResultRq;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.OfferTicket;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.PersonNameType;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.SearchApplicationRq;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.*;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.Calendar;
import javax.xml.bind.JAXBException;

/**
 * @author Erkin
 * @ created 29.12.2014
 * @ $Author$
 * @ $Revision$
 */
public class CRMMessageBuilder
{
	private static final String PRELIM_CRM_TYPE = "Prelim_CRM";
	private static final String CONSUMER_CREDIT_PROD_SUB_TYPE = "Consumer Credit";

	private final DepartmentService departmentService = new DepartmentService();

	private final CRMMessageMarshaller messageMarshaller = new CRMMessageMarshaller();
	//�������� ��� �������� � CRM ����� ������ �� ������
	private final CRMExtendedLoanClaimMessageMarshaller newApplMessageMarshaller = new CRMExtendedLoanClaimMessageMarshaller();
	private final LoanApplicationRelease19MessageMarshaller loanApplicationRelease19MessageMarshaller = new LoanApplicationRelease19MessageMarshaller();

	CRMMessage searchApplicationRequestMessage(Person person) throws Exception
	{
		SearchApplicationRq request = new SearchApplicationRq();

		request.setRqUID(new RandomGUID().getStringValue());
		request.setRqTm(Calendar.getInstance());
		request.setSPName(com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.SPNameType.BP_ERIB);

		PersonNameType personNameType = new PersonNameType();
		personNameType.setFirstName(person.getFirstName());
		personNameType.setLastName(person.getSurName());
		personNameType.setMiddleName(person.getPatrName());
		request.setPersonName(personNameType);

		PersonDocument mainDocument = null;
		for (PersonDocument document: person.getPersonDocuments())
			if (document.getDocumentMain())
				mainDocument = document;

		if (mainDocument == null)
			throw new GateException("�� ������� ���������� �������� �������� �������.");

		SearchApplicationRq.Passport passport = new SearchApplicationRq.Passport();
		passport.setIdSeries(mainDocument.getDocumentSeries());
		passport.setIdNum(mainDocument.getDocumentNumber());
		passport.setIssuedCode(mainDocument.getDocumentIssueByCode());
		passport.setIssuedBy(mainDocument.getDocumentIssueBy());
		passport.setIssueDt(mainDocument.getDocumentIssueDate());
		request.setPassport(passport);

		String requestXML = loanApplicationRelease19MessageMarshaller.marshalSearchApplicationRequest(request);
		return  new CRMMessage(request, requestXML);
	}

	/**
	 * @param srcRqUID ������������� ��������� ��������� ������ �������� �� ������ (ETSM-����);
	 * @param offerId ������������� ������ � ����
	 * @param finalAmount �������� ����� ������� � ������
	 * @param finalPeriodM �������� ���� ������� � �������
	 * @param finalInterestRate �������� ���������� ������
	 * @param offerAgreeDate ���� � ����� ������������ ������
	 * @param offerAccept	������� ������������� ������. True-������ �������. False-����� �� ������
	 * @return  CRMMessage
	 * @throws Exception
	 */
	CRMMessage consumerProductOfferResultRq(String srcRqUID,String offerId, BigDecimal finalAmount,
	                                        Long finalPeriodM, BigDecimal finalInterestRate, Calendar offerAgreeDate, boolean offerAccept) throws Exception
	{
		ConsumerProductOfferResultRq request = new ConsumerProductOfferResultRq();
		request.setRqUID(srcRqUID);
		request.setRqTm(Calendar.getInstance());
		request.setSPName(com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.SPNameType.BP_ERIB);
		ConsumerProductOfferResultRq.SrcRq srcRq = new ConsumerProductOfferResultRq.SrcRq();
		srcRq.setRqUID(srcRqUID);
		request.setSrcRq(srcRq);
		request.setOperUID(offerId);
		ConsumerProductOfferResultRq.Offer offer = new ConsumerProductOfferResultRq.Offer();
		offer.setOfferAccept(offerAccept);
		offer.setFinalAmount(finalAmount);
		offer.setFinalPeriodM(finalPeriodM);
		offer.setFinalInterestRate(finalInterestRate);
		offer.setOfferDate(offerAgreeDate);
		request.setOffer(offer);
		String requestXML = loanApplicationRelease19MessageMarshaller.�onsumerProductOfferResultRq(request);
		return  new CRMMessage(request, requestXML);
	}

	CRMMessage makeOfferRequestMessage(ActivePerson person) throws Exception
	{
		CreditingConfig creditingConfig = ConfigFactory.getConfig(CreditingConfig.class);

		GetCampaignerInfoRq request = new GetCampaignerInfoRq();

		// 1. ������ �������
		request.setRqUID(new RandomGUID().getStringValue());
		request.setRqTm(XMLDatatypeHelper.formatDateTimeWithoutTimeZone(Calendar.getInstance()));
		request.setSPName(SPNameType.BP_ERIB);
		request.setSystemId(creditingConfig.getCrmSystemID());

		// 2. ������ � �������
		request.setCampaignMember(makeCampaignMember(person));

		String requestXML = messageMarshaller.marshalOfferRequest(request);
		return new CRMMessage(request, requestXML);
	}

	private CampaignMember makeCampaignMember(ActivePerson person) throws BusinessException
	{
		CampaignMember campaignMember = new CampaignMember();

		campaignMember.setPersonInfo(makePersonInfo(person));
		campaignMember.setBankInfo(makeBankInfo(person));
		campaignMember.setTreatmentType(TreatmentTypeType.SBOL);

		return campaignMember;
	}

	private PersonInfoSecType makePersonInfo(ActivePerson person)
	{
		PersonInfoSecType personInfo = new PersonInfoSecType();

		personInfo.setPersonName(makePersonName(person));
		personInfo.setBirthDt(XMLDatatypeHelper.formatDateWithoutTimeZone(person.getBirthDay()));
		personInfo.setIdentityCards(makeIdentityCards(person));

		return personInfo;
	}

	private PersonName makePersonName(ActivePerson person)
	{
		PersonName personName = new PersonName();

		personName.setLastName(person.getSurName());
		personName.setFirstName(person.getFirstName());
		personName.setMiddleName(person.getPatrName());

		return personName;
	}

	private PersonInfoSecType.IdentityCards makeIdentityCards(ActivePerson person)
	{
		for (PersonDocument document : person.getPersonDocuments())
		{
			PersonInfoSecType.IdentityCards identityCards;
			switch (document.getDocumentType())
			{
				case REGULAR_PASSPORT_RF:
					identityCards = new PersonInfoSecType.IdentityCards();
					identityCards.getIdentityCards().add(makePersonDocument(document));
					return identityCards;

				case PASSPORT_WAY:
					if (PersonHelper.isGuest())
					{
						identityCards = new PersonInfoSecType.IdentityCards();
						identityCards.getIdentityCards().add(makePersonDocument(document));
						return identityCards;
					}
			}
		}

		throw new IllegalArgumentException("�� ������ ������� �� � ������� LOGIN_ID=" + person.getLogin().getId());
	}

	private IdentityCardType makePersonDocument(PersonDocument document)
	{
		IdentityCardType identityCard = new IdentityCardType();

		// �����+����� ��� ��������
		String series = StringHelper.getEmptyIfNull(document.getDocumentSeries());
		String number = StringHelper.getEmptyIfNull(document.getDocumentNumber());
		identityCard.setIdNum(StringUtils.deleteWhitespace(series + number));

		return identityCard;
	}

	private BankInfoType makeBankInfo(ActivePerson person) throws BusinessException
	{
		BankInfoType bankInfo = new BankInfoType();

		String tb;
		if (person.getDepartmentId() != null)
		{
			Department department = departmentService.findById(person.getDepartmentId());
			Code departmentCode = department.getCode();
			tb = departmentCode.getFields().get("region");
		}
		else if (PersonHelper.isGuest())
		{
			LoanClaimConfig loanClaimConfig = ConfigFactory.getConfig(LoanClaimConfig.class);
			tb = loanClaimConfig.getGuestLoanDepartmentTb();
		}
		else
		{
			throw new IllegalArgumentException("�� �������� ������� �������");
		}

		tb = ConfigFactory.getConfig(TBSynonymsDictionary.class).getMainTBBySynonym(tb);

		bankInfo.setRegionId(StringHelper.addLeadingZeros(tb, 3));

		return bankInfo;
	}

	public CRMMessage makeFeedbackRequestMessage(Feedback feedback, String result) throws Exception
	{
		CreditingConfig creditingConfig = ConfigFactory.getConfig(CreditingConfig.class);

		RegisterRespondToMarketingProposeRq request = new RegisterRespondToMarketingProposeRq();

		// 1. ������ �������
		request.setRqUID(new RandomGUID().getStringValue());
		request.setRqTm(XMLDatatypeHelper.formatDateTimeWithoutTimeZone(Calendar.getInstance()));
		request.setSPName(SPNameType.BP_ERIB);
		request.setSystemId(creditingConfig.getCrmSystemID());

		// 2. ������ �� �������
		request.getMarketingResponses().add(makeMarketingResponse(feedback, result));

		String requestXML = messageMarshaller.marshallFeedbackRequest(request);
		return new CRMMessage(request, requestXML);
	}

	private MarketingResponseType makeMarketingResponse(Feedback feedback, String result)
	{
		MarketingResponseType response = new MarketingResponseType();
		response.setSourceCode(feedback.sourceCode);
		response.setMethod(feedback.channel.crmCode);
		response.setResult(result);
		response.setDetailResult(result);
		response.setCampaingMemberId(feedback.campaignMemberId);
		response.setResponseDateTime(XMLDatatypeHelper.formatDateTimeWithoutTimeZone(feedback.feedbackTime));
		return response;
	}


	/**
	 * ������������ ��������� ��� �������� ������ �� �������� ������ � CRM
	 * @param claim
	 * @return
	 * @throws Exception
	 */
	public CRMMessage makePhoneCallback(ExtendedLoanClaim claim) throws Exception
	{
		ApplicationCRMType application = makeApplicationForCallback(claim);
		return makeCRMMessage(application);
	}

	/**
	 * ������������ ��������� �� ������ � CRM
	 *
	 * @param claim
	 * @param employee
	 * @return
	 * @throws Exception
	 */
	public CRMMessage makePhoneCall(ExtendedLoanClaim claim, Employee employee) throws Exception
	{
		ApplicationCRMType application = makeApplicationForCallback(claim);
		application.setCreatedByCCLogin(employee.getSUDIRLogin());
		application.setCreatedByCCFullName(employee.getFullName());
		return makeCRMMessage(application);
	}

	/**
	 * ������������ ��������� ��� �������� ������� �� �������� ����� ������ � CRM
	 * @param claim
	 * @return
	 * @throws Exception
	 */
	public CRMMessage makeNewClaim(ExtendedLoanClaim claim) throws Exception
	{
		ApplicationCRMType application = makeApplicationCommon(claim);
		return makeCRMMessage(application);
	}

	/**
	 * ������������ ��������� ��� �������� ��������� ������� ������ � CRM
	 * @param claim
	 * @return
	 * @throws Exception
	 */
	public CRMMessage makeUpdateClaimStatus(CRMStateType state, ExtendedLoanClaim claim) throws Exception
	{
		ERIBUpdApplStatusRq request = new ERIBUpdApplStatusRq();
		CreditingConfig creditingConfig = ConfigFactory.getConfig(CreditingConfig.class);
		//ID �������
		request.setRqUID(new RandomGUID().getStringValue());
		//���� � ����� �������
		request.setRqTm(Calendar.getInstance());
		//ID �������, ������� ��������� ���������
		request.setSPName(com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.SPNameType.BP_ERIB);
		//ID �������, ������� ������������ ���������
		request.setSystemId(creditingConfig.getCrmSystemID());
		ErrorStatusType application = new ErrorStatusType();
		application.setStatusCRM(state.getCode().toString());
		application.setStatusDescCRM(state.getDescription());
		application.setNumber(claim.getOperationUID());
		request.setApplication(application);
		//�������������� ������ ��������� � ������
		String requestXML = newApplMessageMarshaller.marshalUpdateLoanClaimStatusRequest(request);
		return new CRMMessage(request, requestXML);
	}

	/**
	 * @param statusCode ��� �������. �-1� �  ����������� ������ ��������, �0� � �������� ��������� ���������, �1� � ������-������.
	 * @param errorMsg ������
	 * @param rqUID ��������� ���������.
	 * @param rqTm ��������� ���������.
	 * @return ��������� � �������� ������ �� ETSM � ����
	 */
	public CRMMessage makeOfferTicket(String rqUID, Calendar rqTm, int statusCode, String errorMsg) throws JAXBException
	{
		OfferTicket ticket = new OfferTicket();
		ticket.setRqUID(rqUID);
		ticket.setRqTm(rqTm);
		ticket.setSPName(com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.SPNameType.BP_ERIB);
		OfferTicket.Status status = new OfferTicket.Status();
		if (StringHelper.isNotEmpty(errorMsg) && statusCode != 0)
		{
			OfferTicket.Status.Error error = new OfferTicket.Status.Error();
			error.setErrorCode(statusCode);
			error.setMessage(errorMsg);
			status.setError(error);
		}
		status.setStatusCode(statusCode);
		ticket.setStatus(status);
		String requestXML = loanApplicationRelease19MessageMarshaller.marshalOfferTicket(ticket);
		return new CRMMessage(ticket, requestXML);
	}

	/**
	 * ������������ ��������� ��� �������� ��� � CRM
	 * @param application - ������ �� ��������� ������ ��� CRM
	 * @return
	 * @throws JAXBException
	 */
	private CRMMessage makeCRMMessage(ApplicationCRMType application) throws JAXBException
	{
		if (application == null)
			return null;
		CRMNewApplRq request = createCRMNewApplRq();
		//������ �� ������
		request.setApplication(application);
		//�������������� ������ �� ������ � ������
		String requestXML = newApplMessageMarshaller.marshalLoanClaimRequest(request);
		return new CRMMessage(request, requestXML);
	}

	/**
	 * �������� ������� �� ����� ������ ��� �������� ����� � ���������� � ������ �������
	 * @return
	 */
	private CRMNewApplRq createCRMNewApplRq()
	{
		CreditingConfig creditingConfig = ConfigFactory.getConfig(CreditingConfig.class);

		CRMNewApplRq request = new CRMNewApplRq();
		//ID �������
		request.setRqUID(new RandomGUID().getStringValue());
		//���� � ����� �������
		request.setRqTm(Calendar.getInstance());
		//ID �������, ������� ��������� ���������
		request.setSPName(com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.SPNameType.BP_ERIB);
		//ID �������, ������� ������������ ���������
		request.setSystemId(creditingConfig.getCrmSystemID());

		return request;
	}

	/**
	 * ������ �� ������ ��� ������ ��������� ������
	 * @param claim
	 * @return
	 * @throws BusinessException
	 */
	private ApplicationCRMType makeApplicationForCallback(ExtendedLoanClaim claim) throws BusinessException
	{
		ApplicationCRMType applicationForCallback = makeApplicationCommon(claim);
		//������� ������������� ���������� ��������� ������ �������: 0 - ������ �� ���������, 1 - ������ ���������
		applicationForCallback.setTMCallback("1");
		return applicationForCallback;
	}

	/**
	 * ��������� ����� ������ �� ������ ��� �������� ��������� � CRM
	 * @param claim
	 * @return
	 * @throws BusinessException
	 */
	private ApplicationCRMType makeApplicationCommon(ExtendedLoanClaim claim) throws BusinessException
	{
		ApplicationCRMType application = new ApplicationCRMType();
		//**************������������� ������**************
		//����������  ����� ������ �� ������� ������� (����� ������ � ����)
		application.setNumber(claim.getOperationUID());
		//�������� ������, �� �� ����� �������� ������
		application.setSource(claim.getCRMSourceChannel());
		//��� ������ (��� ������ ������������ � ETSM � CRM ����������� = Full_ETSM, ��� ������ ������������ � CRM �� ��� (��� �������� � ETSM) = Prelim_CRM)
		application.setType(PRELIM_CRM_TYPE);

		//**************������ �� �������**************
		ActivePerson person = claim.getOwner().getPerson();
		application.setFirstName(person.getFirstName());
		application.setLastName(person.getSurName());
		application.setMiddleName(person.getPatrName());
		application.setBirthDate(person.getBirthDay());

		application.setPassportNum(claim.getOwnerIdCardSeries() + " " + claim.getOwnerIdCardNumber());
		//ID ��������� ��������.  ����������� � ������ ������� ������� � ��������
		if (StringHelper.isNotEmpty(claim.getLoanOfferId()))
			application.setCampaingMemberId(claim.getCampaingMemberId());
		//������� �������
		if (StringHelper.isNotEmpty(claim.getFullJobPhoneNumber()))
		{
			PhoneNumber workPhone = PhoneNumber.fromString(claim.getFullJobPhoneNumber());
			if (workPhone != null)
				application.setWorkPhone("+" + PhoneNumberFormat.MOBILE_INTERANTIONAL.format(workPhone));
		}
		//��������� �������
		String mobileNumber = claim.getFullMobileNumber();
		if (StringHelper.isNotEmpty(mobileNumber))
		{
			PhoneNumber mobilePhone = PhoneNumber.fromString(mobileNumber);
			application.setCellPhone("+" + PhoneNumberFormat.MOBILE_INTERANTIONAL.format(mobilePhone));
		}
		else if (StringHelper.isNotEmpty(claim.getOwner().getPerson().getMobilePhone()))
		{
			PhoneNumber mobilePhone = PhoneNumber.fromString(claim.getOwner().getPerson().getMobilePhone());
			application.setCellPhone("+" + PhoneNumberFormat.MOBILE_INTERANTIONAL.format(mobilePhone));
		} else
			throw new IllegalArgumentException("�� ������ ��������� ������� �������");
		//�������������� �������
		if (StringHelper.isNotEmpty(claim.getFullResidencePhoneNumber()))
		{
			PhoneNumber addPhone = PhoneNumber.fromString(claim.getFullResidencePhoneNumber());
			if (addPhone != null)
				application.setAddPhone("+" + PhoneNumberFormat.MOBILE_INTERANTIONAL.format(addPhone));
		}

		//**************������ �� ��������� ������**************
		//������������ �������� �� ����������� ��������� ��������� ����
		application.setProductName(claim.getProductName());
		// ��� ���� ��������
		application.setTargetProductType(claim.getLoanProductType());
		//��� ��������
		application.setTargetProduct(claim.getLoanProductCode());
		//��� �������� ������, ����� ������� ������� � ������ (��������� "Consumer Credit" � �������� � ��� �������)
		application.setProdSubType(CONSUMER_CREDIT_PROD_SUB_TYPE);
		//��� �����������
		application.setTargetProductSub(claim.getLoanSubProductCode());
		Money amount = claim.getLoanAmount();
		if (amount != null)
		{
			//���������� ISO ��� ������
			application.setCreditCurrency(amount.getCurrency().getCode());
			// ������������� ����� �������
			application.setCreditAmount(amount.getWholePart());
		}
		// ���� ������������. ���-�� �������. ����� ����� � ��������� 1-360
		Long loanPeriod = claim.getLoanPeriod();
		if (loanPeriod != null)
			application.setCreditPeriod(loanPeriod.intValue());
		//���������� ������, %.  ����� � ��������� �� ���� ������ ����� ������� � ��������� �� 0 �� 100
		//����� ������������ ��������� ������ �� �������, ��� ����� �����������?
		try
		{
			application.setCreditRate(claim.getLoanRate().getMaxLoanRate());
		} catch (IllegalArgumentException e){}
		//������������� ����� �� ����������� �������������� ����� (��� ���) 11 ���� ��� ��������: 2-��� ��, 4-��� ���, 5-��� ���.
		String divisionId = null;
		if (PersonHelper.isGuest())
			divisionId = StringHelper.addLeadingZeros(claim.getTb(), 2) +
						 StringHelper.addLeadingZeros(claim.getOsb(), 4) +
						 StringHelper.addLeadingZeros(claim.getVsp(), 5);
		else
			divisionId = PersonHelper.getPersonTbOsbVsp(person);

		application.setDivisionId(divisionId);
		//**************������ �� ������ � ������� ������**************

		//��� ����� � ������ (�� ���������� �� ��������� ������ ���� �� ������)
		//*	�������� ����� ������ ��� �� ����������� ���������� ������. ������� ����� �������.
		//application.setPrefferedCallTime();
		//*	�������� ���� ���������, � ������� MM/DD/YYYY
		//application.setPreferredCallDate(claim.get);
		return application;
	}
}
