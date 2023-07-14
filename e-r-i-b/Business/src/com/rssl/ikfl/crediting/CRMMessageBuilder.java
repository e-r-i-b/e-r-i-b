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
	//Маршалер для отправки в CRM новой заявки на кредит
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
			throw new GateException("Не удалось определить основной документ клиента.");

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
	 * @param srcRqUID Идентификатор исходного сообщения запрос согласия на оферту (ETSM-ЕРИБ);
	 * @param offerId Идентификатор заявки в ЕРИБ
	 * @param finalAmount Итоговая сумма кредита в рублях
	 * @param finalPeriodM Итоговый срок кредита в месяцах
	 * @param finalInterestRate Итоговая процентная ставка
	 * @param offerAgreeDate дата и время согласования оферты
	 * @param offerAccept	Признак подтверждения оферте. True-оферта принята. False-отказ от оферты
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
		String requestXML = loanApplicationRelease19MessageMarshaller.сonsumerProductOfferResultRq(request);
		return  new CRMMessage(request, requestXML);
	}

	CRMMessage makeOfferRequestMessage(ActivePerson person) throws Exception
	{
		CreditingConfig creditingConfig = ConfigFactory.getConfig(CreditingConfig.class);

		GetCampaignerInfoRq request = new GetCampaignerInfoRq();

		// 1. Данные запроса
		request.setRqUID(new RandomGUID().getStringValue());
		request.setRqTm(XMLDatatypeHelper.formatDateTimeWithoutTimeZone(Calendar.getInstance()));
		request.setSPName(SPNameType.BP_ERIB);
		request.setSystemId(creditingConfig.getCrmSystemID());

		// 2. Данные о клиенте
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

		throw new IllegalArgumentException("Не найден паспорт РФ у клиента LOGIN_ID=" + person.getLogin().getId());
	}

	private IdentityCardType makePersonDocument(PersonDocument document)
	{
		IdentityCardType identityCard = new IdentityCardType();

		// Серия+номер без пробелов
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
			throw new IllegalArgumentException("Не известен тербанк клиента");
		}

		tb = ConfigFactory.getConfig(TBSynonymsDictionary.class).getMainTBBySynonym(tb);

		bankInfo.setRegionId(StringHelper.addLeadingZeros(tb, 3));

		return bankInfo;
	}

	public CRMMessage makeFeedbackRequestMessage(Feedback feedback, String result) throws Exception
	{
		CreditingConfig creditingConfig = ConfigFactory.getConfig(CreditingConfig.class);

		RegisterRespondToMarketingProposeRq request = new RegisterRespondToMarketingProposeRq();

		// 1. Данные запроса
		request.setRqUID(new RandomGUID().getStringValue());
		request.setRqTm(XMLDatatypeHelper.formatDateTimeWithoutTimeZone(Calendar.getInstance()));
		request.setSPName(SPNameType.BP_ERIB);
		request.setSystemId(creditingConfig.getCrmSystemID());

		// 2. Данные по отклику
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
	 * Формирование сообщения для передачи заявки на обратный звонок в CRM
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
	 * Формирование сообщения на обзвон в CRM
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
	 * Формирование сообщения для передачи запроса на создание новой заявки в CRM
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
	 * Формирование сообщения для передачи изменения статуса заявки в CRM
	 * @param claim
	 * @return
	 * @throws Exception
	 */
	public CRMMessage makeUpdateClaimStatus(CRMStateType state, ExtendedLoanClaim claim) throws Exception
	{
		ERIBUpdApplStatusRq request = new ERIBUpdApplStatusRq();
		CreditingConfig creditingConfig = ConfigFactory.getConfig(CreditingConfig.class);
		//ID запроса
		request.setRqUID(new RandomGUID().getStringValue());
		//Дата и время запроса
		request.setRqTm(Calendar.getInstance());
		//ID системы, которая отправила сообщение
		request.setSPName(com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.SPNameType.BP_ERIB);
		//ID системы, которой направляется сообщение
		request.setSystemId(creditingConfig.getCrmSystemID());
		ErrorStatusType application = new ErrorStatusType();
		application.setStatusCRM(state.getCode().toString());
		application.setStatusDescCRM(state.getDescription());
		application.setNumber(claim.getOperationUID());
		request.setApplication(application);
		//Преобразование данных сообщения в строку
		String requestXML = newApplMessageMarshaller.marshalUpdateLoanClaimStatusRequest(request);
		return new CRMMessage(request, requestXML);
	}

	/**
	 * @param statusCode Код статуса. «-1» –  техническая ошибка доставки, «0» – успешная обработка обработка, «1» – бизнес-ошибка.
	 * @param errorMsg Ошибка
	 * @param rqUID исходного сообщения.
	 * @param rqTm исходного сообщения.
	 * @return Квитанция о доставке оферты от ETSM в ЕРИБ
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
	 * Формирование сообщения для передачи его в CRM
	 * @param application - данные по кредитной заявке для CRM
	 * @return
	 * @throws JAXBException
	 */
	private CRMMessage makeCRMMessage(ApplicationCRMType application) throws JAXBException
	{
		if (application == null)
			return null;
		CRMNewApplRq request = createCRMNewApplRq();
		//Данные по заявке
		request.setApplication(application);
		//Преобразование данных по заявке в строку
		String requestXML = newApplMessageMarshaller.marshalLoanClaimRequest(request);
		return new CRMMessage(request, requestXML);
	}

	/**
	 * Создание запроса на новую заявку или обратный зонок и заполнение её общими данными
	 * @return
	 */
	private CRMNewApplRq createCRMNewApplRq()
	{
		CreditingConfig creditingConfig = ConfigFactory.getConfig(CreditingConfig.class);

		CRMNewApplRq request = new CRMNewApplRq();
		//ID запроса
		request.setRqUID(new RandomGUID().getStringValue());
		//Дата и время запроса
		request.setRqTm(Calendar.getInstance());
		//ID системы, которая отправила сообщение
		request.setSPName(com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.SPNameType.BP_ERIB);
		//ID системы, которой направляется сообщение
		request.setSystemId(creditingConfig.getCrmSystemID());

		return request;
	}

	/**
	 * Данные по заявке при заказе обратного звонка
	 * @param claim
	 * @return
	 * @throws BusinessException
	 */
	private ApplicationCRMType makeApplicationForCallback(ExtendedLoanClaim claim) throws BusinessException
	{
		ApplicationCRMType applicationForCallback = makeApplicationCommon(claim);
		//Признак необходимости выполнения обратного звонка клиенту: 0 - звонок не требуется, 1 - звонок требуется
		applicationForCallback.setTMCallback("1");
		return applicationForCallback;
	}

	/**
	 * Заполнени общих данных по заявке при отправке сообщения в CRM
	 * @param claim
	 * @return
	 * @throws BusinessException
	 */
	private ApplicationCRMType makeApplicationCommon(ExtendedLoanClaim claim) throws BusinessException
	{
		ApplicationCRMType application = new ApplicationCRMType();
		//**************Идентификация заявки**************
		//Уникальный  номер заявки во внешней системе (номер заявки в ЕРИБ)
		application.setNumber(claim.getOperationUID());
		//Источник заявки, он же канал создания заявки
		application.setSource(claim.getCRMSourceChannel());
		//Тип заявки (для заявок отправляемых в ETSM и CRM параллельно = Full_ETSM, для заявок отправляемых в CRM на ВСП (без отправки в ETSM) = Prelim_CRM)
		application.setType(PRELIM_CRM_TYPE);

		//**************Данные по клиенту**************
		ActivePerson person = claim.getOwner().getPerson();
		application.setFirstName(person.getFirstName());
		application.setLastName(person.getSurName());
		application.setMiddleName(person.getPatrName());
		application.setBirthDate(person.getBirthDay());

		application.setPassportNum(claim.getOwnerIdCardSeries() + " " + claim.getOwnerIdCardNumber());
		//ID участника компании.  Заполняется в случае участия клиента в компании
		if (StringHelper.isNotEmpty(claim.getLoanOfferId()))
			application.setCampaingMemberId(claim.getCampaingMemberId());
		//Рабочий телефон
		if (StringHelper.isNotEmpty(claim.getFullJobPhoneNumber()))
		{
			PhoneNumber workPhone = PhoneNumber.fromString(claim.getFullJobPhoneNumber());
			if (workPhone != null)
				application.setWorkPhone("+" + PhoneNumberFormat.MOBILE_INTERANTIONAL.format(workPhone));
		}
		//Мобильный телефон
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
			throw new IllegalArgumentException("Не указан мобильный телефон клиента");
		//Дополнительный телефон
		if (StringHelper.isNotEmpty(claim.getFullResidencePhoneNumber()))
		{
			PhoneNumber addPhone = PhoneNumber.fromString(claim.getFullResidencePhoneNumber());
			if (addPhone != null)
				application.setAddPhone("+" + PhoneNumberFormat.MOBILE_INTERANTIONAL.format(addPhone));
		}

		//**************Данные по кредитной заявке**************
		//Наименование продукта из справочника кредитных продуктов ЕРИБ
		application.setProductName(claim.getProductName());
		// Код типа продукта
		application.setTargetProductType(claim.getLoanProductType());
		//Код продукта
		application.setTargetProduct(claim.getLoanProductCode());
		//Тип продукта заявки, самый верхний уровень – кредит (константа "Consumer Credit" с пробелом и без ковычек)
		application.setProdSubType(CONSUMER_CREDIT_PROD_SUB_TYPE);
		//Код субпродукта
		application.setTargetProductSub(claim.getLoanSubProductCode());
		Money amount = claim.getLoanAmount();
		if (amount != null)
		{
			//Символьный ISO код валюты
			application.setCreditCurrency(amount.getCurrency().getCode());
			// Запрашиваемая сумма кредита
			application.setCreditAmount(amount.getWholePart());
		}
		// Срок кредитования. Кол-во месяцев. Целое число в интервале 1-360
		Long loanPeriod = claim.getLoanPeriod();
		if (loanPeriod != null)
			application.setCreditPeriod(loanPeriod.intValue());
		//Процентная ставка, %.  Число с точностью до двух знаков после запятой в интервале от 0 до 100
		//взята максимальная процентня ставка по кредиту, или нужна минимальная?
		try
		{
			application.setCreditRate(claim.getLoanRate().getMaxLoanRate());
		} catch (IllegalArgumentException e){}
		//Подразделение банка из справочника «Подразделения банка» (Код ВСП) 11 цифр без пробелов: 2-код ТБ, 4-код ОСБ, 5-код ВСП.
		String divisionId = null;
		if (PersonHelper.isGuest())
			divisionId = StringHelper.addLeadingZeros(claim.getTb(), 2) +
						 StringHelper.addLeadingZeros(claim.getOsb(), 4) +
						 StringHelper.addLeadingZeros(claim.getVsp(), 5);
		else
			divisionId = PersonHelper.getPersonTbOsbVsp(person);

		application.setDivisionId(divisionId);
		//**************Данные по звонку и времени визита**************

		//Нет полей в заявке (по информации от аналитика сейчас пока не делаем)
		//*	Желаемое время звонка код из справочника «Интервалы звонка». Местное время клиента.
		//application.setPrefferedCallTime();
		//*	Желаемая дата перезвона, в формате MM/DD/YYYY
		//application.setPreferredCallDate(claim.get);
		return application;
	}
}
