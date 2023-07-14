package com.rssl.phizicgate.esberibgate.claim.sbnkd;

import com.rssl.phizgate.common.services.types.CodeImpl;
import com.rssl.phizgate.common.services.types.OfficeImpl;
import com.rssl.phizgate.sbnkd.limits.PhoneLimitType;
import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.remoteConnectionUDBO.RemoteConnectionUDBOConfig;
import com.rssl.phizic.config.sbnkd.SBNKDConfig;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.claims.sbnkd.CardInfo;
import com.rssl.phizic.gate.claims.sbnkd.*;
import com.rssl.phizic.gate.claims.sbnkd.ContactData;
import com.rssl.phizic.gate.claims.sbnkd.impl.CardInfoImpl;
import com.rssl.phizic.gate.claims.sbnkd.impl.IssueCardDocumentImpl;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientSex;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.mobilebank.MobileBankRegistration;
import com.rssl.phizic.gate.mobilebank.MobileBankService;
import com.rssl.phizic.gate.utils.ExternalSystemGateService;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.MessagingSingleton;
import com.rssl.phizic.userSettings.UserPropertyService;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.PhoneNumberUtil;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizicgate.esberibgate.clients.ClientRequestHelper;
import com.rssl.phizicgate.esberibgate.clients.ProductContainer;
import com.rssl.phizicgate.esberibgate.messaging.ClientResponseSerializer;
import com.rssl.phizicgate.esberibgate.types.ClientDocumentImpl;
import com.rssl.phizicgate.esberibgate.types.ClientImpl;
import com.rssl.phizicgate.esberibgate.ws.TransportProvider;
import com.rssl.phizicgate.esberibgate.ws.generated.CardAcctRec_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRq_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRs_Type;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.*;
import com.rssl.phizicgate.esberibgate.ws.jms.senders.request.builders.ConcludeEDBORequestBuilder;
import com.rssl.phizgate.sbnkd.SBNKDLimitService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author bogdanov
 * @ created 19.12.14
 * @ $Author$
 * @ $Revision$
 */

public class IssueCardClaimProcessor
{
	private static final IssueCardService issueCardService = new IssueCardService();
	private static final SBNKDLimitService limitService = new SBNKDLimitService();
	private static final String VIP_IDENT = "3";
	private static Log log = PhizICLogFactory.getLog(LogModule.Gate);

	private static IssueCardClaimProcessor it = new IssueCardClaimProcessor();

	public static IssueCardClaimProcessor getIt()
	{
		return it;
	}

	public void process(final String externalId, final Object response, final Object status) throws GateLogicException, GateException
	{
		try
		{
			if (response.getClass() == ConcludeEDBORs.class && ConcludeEDBODocumentSender.isBusinessDocument(((ConcludeEDBORs) response).getRqUID()))
			{
				concludeEDBOBusinessDoc((ConcludeEDBORs) response, issueCardService.getDocument(Long.parseLong(externalId.substring(8, 32), 16)), (StatusType)status);
				return;
			}

			if (response.getClass() == ConcludeEDBORs.class ||
					response.getClass() == GetPrivateClientRs.class ||
					response.getClass() == CustAddRs.class)
			{
				processClaim(externalId, response, (StatusType)status);
				return;
			}

			processCard(externalId, response, status, response.getClass() == IssueCardRs.class);
		}
		catch (GateException e)
		{
			throw e;
		}
		catch (GateLogicException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	private void fiilLogContext(ConcludeEDBODocument document)
	{
		LogThreadContext.setFirstName(document.getPersonFirstName());
		LogThreadContext.setSurName(document.getPersonLastName());
		LogThreadContext.setPatrName(document.getPersonMiddleName());
		LogThreadContext.setBirthday(document.getPersonBirthday());
		LogThreadContext.setDepartmentOSB(document.getOsb());
		LogThreadContext.setDepartmentVSP(document.getVsp());
		LogThreadContext.setDepartmentRegion(document.getTb());
		LogThreadContext.setSeries(document.getIdentityCardSeries());
		LogThreadContext.setNumber(document.getIdentityCardNumber());
		LogThreadContext.setSessionId(document.getUID());
		if (document.isGuest())
		{
			LogThreadContext.setGuestCode(document.getOwnerId());
			LogThreadContext.setGuestPhoneNumber(document.getPhone());
		}
		else
		{
			LogThreadContext.setLoginId(document.getOwnerId());
		}
	}

	private void processClaim(final String externalId, final Object response, final StatusType status) throws GateException, GateLogicException
	{
		IssueCardDocumentImpl document = issueCardService.getClaimByUuid(externalId);
		fiilLogContext(document);

		if (status.getStatusCode() != 0 && !(response instanceof GetPrivateClientRs && (status.getStatusCode() == -425L || status.getStatusCode() == -424L)))
		{
			resetDocumentStatus(document, IssueCardStatus.ERROR);
			log.info(status.getStatusDesc());
			issueCardService.addOrUpdate(document);
			SBNKDConfig config = ConfigFactory.getConfig(SBNKDConfig.class);
			if (config.isInformClientAboutSBNKDStatus())
				sendSms(document, config.getSmsMessageErrorExternalSystem());
			return;
		}

		try
		{
			processClaim(document, response);
		}
		catch (GateException e)
		{
			document.setStatus(IssueCardStatus.ERROR);
			log.info("Заявка на выпуск карт " + document.getId() + ":" + e.getMessage());
			throw e;
		}
	}

	public void processClaim(IssueCardDocumentImpl document, final Object response) throws GateException, GateLogicException
	{
		try
		{
			switch (document.getStatus())
			{
				case INIT:
				case CONFIRM:
				    processConfirm(document, (GetPrivateClientRs) response);
				    break;
			    case CONFIRM_NO_VIP:
				    //лимит на количество заявок СБНКД в месяц
				    if (document.isGuest())
					    limitService.updateLimit(document, 1, PhoneLimitType.LIMIT_CLAIME_PER_MONTH);
				    processConfirmNoVip(document);
				    break;
			    case CLIENT_GETTING:
				    processClientGetting(document,(GetPrivateClientRs) response);
				    break;
			    case CLIENT_GET:
				    processClientGet(document);
				    break;
			    case FIRST_CARD_CONTRACT_CREATED:
				    processConcludeEdbo(document, (ConcludeEDBORs) response);
				    break;
			    case EDBO_CONCLUDED:
				    processCustAdd(document, (CustAddRs) response);
				    break;
			}
		}
		catch (GateException e)
		{
			log.error("Ошибка отправки документа " + document.getId(), e);
			document.setStatus(IssueCardStatus.ERROR);
			throw e;
		}
		catch (GateLogicException e)
		{
			log.error("Ошибка отправки документа " + document.getId(), e);
			document.setStatus(IssueCardStatus.ERROR);
			throw e;
		}
		finally
		{
			issueCardService.addOrUpdate(document);
		}
	}

	/**
	 * Строит уникальный уид для запроса IssueCardRq.
	 * @param docUuid uid документа в базе.
	 * @return uid для IssueCardRq.
	 */
	public String buildUidForIssueCardRq(String docUuid)
	{
		char ch = docUuid.charAt(docUuid.length() - 1);
		int c = Integer.parseInt(Character.toString(ch), 16) + 1;
		return docUuid.substring(0, docUuid.length() - 1) + Integer.toString(c%16, 16);
	}

	/**
	 * @param rqUid uid из запроса IssueCardRs.
	 * @return реальный uuid документа.
	 */
	public String getRealUidForIssueCardRq(String rqUid)
	{
		char ch = rqUid.charAt(rqUid.length() - 1);
		int c = Integer.parseInt(Character.toString(ch), 16) + 16 - 1;
		return rqUid.substring(0, rqUid.length() - 1) + Integer.toString(c%16, 16);
	}

	private void processCard(final String externalId, final Object response, final Object status, boolean isIssueCardRs) throws GateException, GateLogicException
	{
		CardInfoImpl card = issueCardService.getCardInfoByUuid(isIssueCardRs ? getRealUidForIssueCardRq(externalId) : externalId);
		processCard(card, response, status, isIssueCardRs);
	}

	private void processCard(final CardInfoImpl card, final Object response, final Object status, boolean isIssueCardRs) throws GateException, GateLogicException
	{
		fiilLogContext(card.getParent());

		try
		{
			switch (card.getStatus())
			{
			    case NEW:
			    {
				    CreateCardContractRs rs = (CreateCardContractRs)response;
				    if (rs.getStatus().getStatusCode() != 0)
				    {
					    card.setStatus(CardInfoStatus.ERROR);
	                    log.info("Выпуск карты " + card.getUID() + ": " + rs.getStatus().getStatusDesc());
					    continueCardIssues(card);
				    }
				    else
				    {
				        processCardCreated(card, rs);
				    }
				    break;
			    }
			    case CONTRACTED:
				    card.setStatus(CardInfoStatus.CREATE_CRD);
				    new IssueCardDocumentSender(GateSingleton.getFactory()).send(card);
				    break;
			    case CREATE_CRD:
			    {
				    IssueCardRs rs = (IssueCardRs)response;
				    if (rs.getStatus().getStatusCode() != 0)
				    {
					    card.setStatus(CardInfoStatus.ERROR);
					    log.info("Выпуск карты " + card.getUID() + ": " + rs.getStatus().getStatusDesc());
					    issueCardService.addOrUpdate(card);
					    //лимит на количество карт в месяц (вычитаем, если по карте ошибка)
					    if(card.getParent().isGuest())
						    limitService.updateLimit(card.getParent(), -1, PhoneLimitType.LIMIT_CARD_PER_MONTH);
				    }
				    else
				    {
					    card.setStatus(CardInfoStatus.COMPLETE);
				    }
				    processCardCreating(card, rs);
				    break;
			    }
			}
		}
		catch (GateException e)
		{
			CardInfoStatus oldStatus = card.getStatus();
			card.setStatus(CardInfoStatus.ERROR);
			log.error("Выпуск карты " + card.getUID() + ": " + e.getMessage());
			issueCardService.addOrUpdate(card);
			//лимит на количество карт в месяц (вычитаем, если по карте ошибка);
			//карты в статусе NEW еще не учтены лимитом
			if(card.getParent().isGuest() && !oldStatus.equals(CardInfoStatus.NEW))
				limitService.updateLimit(card.getParent(), -1, PhoneLimitType.LIMIT_CARD_PER_MONTH);
			throw e;
		}
		catch (GateLogicException e)
		{
			CardInfoStatus oldStatus = card.getStatus();
			card.setStatus(CardInfoStatus.ERROR);
			log.error("Выпуск карты " + card.getUID() + ": " + e.getMessage());
			issueCardService.addOrUpdate(card);
			//лимит на количество карт в месяц (вычитаем, если по карте ошибка)
			//карты в статусе NEW еще не учтены лимитом
			if(card.getParent().isGuest() && !oldStatus.equals(CardInfoStatus.NEW))
				limitService.updateLimit(card.getParent(), -1, PhoneLimitType.LIMIT_CARD_PER_MONTH);
			throw e;
		}
	}

	private void resetDocumentStatus(ClientInfoDocument doc, IssueCardStatus nextStatus) throws GateException
	{
		if (doc.getStatus().canGoToState(nextStatus))
			doc.setStatus(nextStatus);
		else
			throw new GateException("Не возможно перевести документ из статуса " + doc.getStatus() + " в статус " + nextStatus);
	}

	private void processConfirm(IssueCardDocumentImpl document, GetPrivateClientRs rs) throws GateException, GateLogicException
	{
		if(rs == null)
		{
			return;
		}
		if (rs.getCustRec() != null)
		{
			 //если вип
            if (rs.getCustRec().getTarifPlanInfo() != null && VIP_IDENT.equals(rs.getCustRec().getTarifPlanInfo().getSegmentCode()))
            {
                resetDocumentStatus(document, IssueCardStatus.VIP_CLIENT);
                return;
            }
		}
		resetDocumentStatus(document, document.getStatus() == IssueCardStatus.INIT ? IssueCardStatus.INIT_NO_VIP : IssueCardStatus.CONFIRM_NO_VIP);
		issueCardService.addOrUpdate(document);
		StatusType type = new StatusType();
		type.setStatusCode(0L);
		processClaim(document, null);
	}

	private void processConfirmNoVip(IssueCardDocumentImpl document) throws GateException, GateLogicException
	{
		if (document.isGuest())
		{
			resetDocumentStatus(document, IssueCardStatus.CLIENT_GETTING);
			new GetPrivateClientSender(GateSingleton.getFactory()).send(document);
		}
		else
		{
			resetDocumentStatus(document, IssueCardStatus.CLIENT_GET);
			processClaim(document, null);
		}
	}

	private void processClientGetting(IssueCardDocumentImpl document, GetPrivateClientRs rs) throws GateException, GateLogicException
	{
		if (rs.getCustRec() != null)
		{
			//Клиент найден.
			fillDocumentByPersonInfo(document, rs);

			//если вип
			if (rs.getCustRec().getTarifPlanInfo() != null && "3".equals(rs.getCustRec().getTarifPlanInfo().getSegmentCode()))
			{
				resetDocumentStatus(document, IssueCardStatus.VIP_CLIENT);
				return;
			}

			resetDocumentStatus(document, IssueCardStatus.CLIENT_GET);
            processClaim(document, null);
		}
		else
		{
			//Клиент не найден.
			if (rs.getVerified() != null)
				document.setVerified(rs.getVerified());
			document.setClientFound(false);

			resetDocumentStatus(document, IssueCardStatus.CLIENT_GET);
			processClaim(document, null);
		}
	}

	private void fillDocumentByPersonInfo(IssueCardDocumentImpl document, GetPrivateClientRs rs) throws GateException
	{
		PersonInfoType pi = rs.getCustRec().getCustInfo().getPersonInfo();
		try
		{
			document.setPersonBirthday(DateHelper.toCalendar(DateHelper.parseXmlDateFormat(pi.getBirthday())));
		}
		catch (ParseException e)
		{
			throw new GateException(e);
		}
		document.setPersonBirthplace(pi.getBirthPlace());
		document.setPersonTaxId(pi.getTaxId());
		document.setPersonCitizenship(pi.getCitizenship());
		document.setPersonGender(pi.getGender().equals(ConcludeEDBORequestBuilder.GENDER_F) ? ClientSex.GENDER_FEMALE : ClientSex.GENDER_MALE);
		document.setPersonResident(pi.getResident());
		document.setPersonFirstName(pi.getPersonName().getFirstName());
		document.setPersonLastName(pi.getPersonName().getLastName());
		document.setPersonMiddleName(pi.getPersonName().getMiddleName());
		if (pi.getIdentityCard() != null)
		{
			document.setIdentityCardType(pi.getIdentityCard().getIdType());
			document.setIdentityCardSeries(pi.getIdentityCard().getIdSeries());
			document.setIdentityCardNumber(pi.getIdentityCard().getIdNum());
			document.setIdentityCardIssuedBy(pi.getIdentityCard().getIssuedBy());
			try
			{
				document.setIdentityCardIssueDate(DateHelper.toCalendar(DateHelper.parseXmlDateFormat(pi.getIdentityCard().getIssueDt())));
				document.setIdentityCardExpDate(DateHelper.toCalendar(DateHelper.parseXmlDateFormat(pi.getIdentityCard().getExpDt())));
			}
			catch (ParseException e)
			{
				throw new GateException(e);
			}
		}

		ContactInfoType contactInfo = pi.getContactInfo();
		ContactData[] cd = new ContactData[contactInfo.getContactDatas().size()];
		int i = 0;
		for (com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.ContactData cdd : contactInfo.getContactDatas())
		{
			cd[i] = new ContactData();
			cd[i].setContactType(ContactData.ContactType.from(cdd.getContactType()));
			cd[i].setContactNum(cdd.getContactNum());
			i++;
		}
		document.setContactData(cd);
		document.setVerified(rs.getVerified());
		document.setClientFound(true);
	}

	private void processClientGet(IssueCardDocumentImpl document) throws GateLogicException, GateException
	{
		for (CardInfo ci : document.getCardInfos())
        {
            if (ci.isFirstCard())
            {
                new CreateCardContractDocumentSender(GateSingleton.getFactory()).send(ci);
	            return;
            }
        }
	}

	private void processCardCreated(CardInfoImpl cardInfo, CreateCardContractRs rs) throws GateException, GateLogicException
	{
		cardInfo.setStatus(CardInfoStatus.CONTRACTED);
		//лимит на кол-во карточных заявок в месяц
		if(cardInfo.getParent().isGuest())
			limitService.updateLimit(cardInfo.getParent(), 1, PhoneLimitType.LIMIT_CARD_PER_MONTH);
		cardInfo.setCardNumber(Long.toString(rs.getCardContractNumber()));
		cardInfo.setCardAcctCardOrderDate(XMLDatatypeHelper.formatDateWithoutTimeZone(XMLDatatypeHelper.parseDateTime(rs.getRqTm())));

		if (cardInfo.isFirstCard())
		{
			resetDocumentStatus(cardInfo.getParent(), IssueCardStatus.FIRST_CARD_CONTRACT_CREATED);
		}

		//Для гостевого входа и первой обрабатываемой карты.
		if (cardInfo.getParent().isGuest() && cardInfo.isFirstCard())
		{
			//Если клиент найден.
			if (cardInfo.getParent().isClientFound())
			{
				//Отправим запрос УДБО.
				ExternalSystemGateService externalSystemGateService = GateSingleton.getFactory().service(ExternalSystemGateService.class);
				//перед отправкой запроса УДБО проверяем внешнюю систему на активность
				ExternalSystemHelper.check(externalSystemGateService.findByCodeTB(cardInfo.getParent().getRbTbBranch()));

				ClientRequestHelper requestHelper = new ClientRequestHelper(GateSingleton.getFactory());
				ClientResponseSerializer responseSerializer = new ClientResponseSerializer();
				IFXRq_Type ifxRq = requestHelper.createCustInqRq(cardInfo.getParent().getRbTbBranch(), cardInfo.getParent());
				IFXRs_Type ifxRs = TransportProvider.doRequest(ifxRq);
				Client temp = responseSerializer.fillClient(ifxRs, cardInfo.getParent().getRbTbBranch(), false);

				issueCardService.addOrUpdate(cardInfo);
				//Клиент не с УДБО?
				if (temp == null)
				{
					new ConcludeEDBODocumentSender(GateSingleton.getFactory()).send(cardInfo.getParent());
				}
				else
				{
					processCard(cardInfo, null, null, false);
				}
			}
			else
			{
				issueCardService.addOrUpdate(cardInfo);
				new ConcludeEDBODocumentSender(GateSingleton.getFactory()).send(cardInfo.getParent());
			}
		}
		else
		{
			issueCardService.addOrUpdate(cardInfo);
			processCard(cardInfo, null, null, false);
		}
	}

	private void concludeEDBOBusinessDoc(ConcludeEDBORs rs, ConcludeEDBODocument document, StatusType status) throws GateLogicException, GateException
	{
		fiilLogContext(document);
		if (status.getStatusCode() == 0)
		{
			document.setEDBOOrderNumber(rs.getSPDefField().getFieldNum());
			document.setEDBO_TB(Long.toString(rs.getSPDefField().getFieldData1()));

			//Просто заявка подключения УДБО
			document.setStatus(IssueCardStatus.EXECUTED);

			issueCardService.addOrUpdate((GateDocument) document);

			try
			{
				UserPropertyService userPropertyService = UserPropertyService.getIt();
				userPropertyService.setSetting(document.getOwnerId(), "com.rssl.phizic.userSettings.dateConnectionUDBO", String.valueOf(Calendar.getInstance().getTimeInMillis()));
			}
			catch (Exception e)
			{
				document.setStatus(IssueCardStatus.ERROR);
				issueCardService.addOrUpdate((GateDocument) document);
				throw new GateException(e);
			}

			try
			{
				sendSms(document, ConfigFactory.getConfig(RemoteConnectionUDBOConfig.class).getSmsMessageConnectionUDBOSuccess());
			}
			catch (Exception ignore)
			{
				log.error("Ошибка при отправке уведомления клиенту об подключении УДБО");
			}
		}
		else
		{
			document.setStatus(IssueCardStatus.ERROR);
			log.info("Заявка на подключение УДБО " + document.getId() + ":" + status.getStatusDesc());
			issueCardService.addOrUpdate((GateDocument) document);
		}
	}

	private void processConcludeEdbo(IssueCardDocumentImpl document, ConcludeEDBORs rs) throws GateLogicException, GateException
	{
		resetDocumentStatus(document, IssueCardStatus.EDBO_CONCLUDED);
		document.setEDBOOrderNumber(rs.getSPDefField().getFieldNum());
		document.setEDBO_TB(Long.toString(rs.getSPDefField().getFieldData1()));

		document.setClientFound(true);
	    //Отправим запрос CAD.
        new CustAddDocumentSender(GateSingleton.getFactory()).send(document);
	}

	private void processCustAdd(IssueCardDocumentImpl document, CustAddRs rs) throws GateException, GateLogicException
	{
		for (CardInfo ci : document.getCardInfos())
		{
			if (ci.isFirstCard())
				processCard((CardInfoImpl)ci, null, null, false);
		}
	}

	private void processCardCreating(CardInfoImpl document, IssueCardRs rs) throws GateLogicException, GateException
	{
		if (rs.getCardAcctId() != null)
		{
			document.setCardNumber(rs.getCardAcctId().getCardNum());
			document.setAccountNumber(rs.getCardAcctId().getAcctId());
			if (rs.getCardAcctId().getContract() != null)
				document.setContractNumber(rs.getCardAcctId().getContract().getContractNumber());
		}
		if (rs.getCardAcctInfo() != null)
		{
			try
			{
				document.setEndDtForWay(DateHelper.toCalendar(new SimpleDateFormat("yyMM").parse(rs.getCardAcctInfo().getEndDtForWay())));
			}
			catch (ParseException e)
			{
				throw new GateException(e);
			}
		}

		continueCardIssues(document);
	}

	private void continueCardIssues(CardInfoImpl document) throws GateLogicException, GateException
	{
		if (document.isFirstCard())
		{
			resetDocumentStatus(document.getParent(), IssueCardStatus.FIRST_CARD_CREATED);
		}
		issueCardService.addOrUpdate(document);

		for (CardInfo ci : document.getParent().getCardInfos())
		{
			if (ci.getStatus() != CardInfoStatus.NEW)
				continue;

			new CreateCardContractDocumentSender(GateSingleton.getFactory()).send(ci);
			return;
		}

		//Если по всем картам отправлены заявки.
		ClientInfoDocument doc = document.getParent();
		int errorCount = 0;
		for (CardInfo info : doc.getCardInfos())
		{
			if (info.getStatus() == CardInfoStatus.ERROR)
				errorCount++;
		}

		resetDocumentStatus(document.getParent(), errorCount == doc.getCardInfos().size() ? IssueCardStatus.ERROR : IssueCardStatus.EXECUTED);
		issueCardService.addOrUpdate(document.getParent());
		SBNKDConfig config = ConfigFactory.getConfig(SBNKDConfig.class);
		if (config.isInformClientAboutSBNKDStatus())
			sendSms(document.getParent(), errorCount == 0 ? config.getSmsMessageSuccess() : config.getSmsMessageErrorExternalSystem());
	}

	private void sendSms(ClientInfoDocument document, String text) throws GateException, GateLogicException
	{
		Set<String> phones = new HashSet<String>();
		phones.add(PhoneNumberUtil.getFullIKFLPhoneNumber(document.getPhone()));
		if (document.isClientFound() || !document.isGuest())
		{
			ClientImpl client = new ClientImpl();
			client.setFirstName(document.getPersonFirstName());
			client.setSurName(document.getPersonLastName());
			client.setPatrName(document.getPersonMiddleName());
			client.setResident(document.isPersonResident());
			client.setBirthDay(document.getPersonBirthday());
			client.setBirthPlace(document.getPersonBirthplace());
			client.setCitizenship(document.getPersonCitizenship());
			if (document instanceof ConcludeEDBODocument)
				client.setEmail(((ConcludeEDBODocument)document).getEmail());
			client.setGender(document.getPersonGender());
			client.setINN(document.getPersonTaxId());
			ClientDocumentImpl clDoc = new ClientDocumentImpl();
			clDoc.setDocIdentify(true);
			clDoc.setDocIssueBy(document.getIdentityCardIssuedBy());
			clDoc.setDocIssueByCode(document.getIdentityCardIssuedBy());
			clDoc.setDocIssueDate(document.getIdentityCardIssueDate());
			clDoc.setDocumentType(ClientDocumentType.REGULAR_PASSPORT_RF);
			clDoc.setDocSeries(document.getIdentityCardSeries());
			clDoc.setDocNumber(document.getIdentityCardNumber());
			client.setDocuments(Arrays.asList(clDoc));
			client.setOffice(new OfficeImpl(new CodeImpl(document.getTb(), document.getOsb(), document.getVsp())));

		    ClientRequestHelper rh = new ClientRequestHelper(GateSingleton.getFactory());
			ProductContainer pc = rh.createBankAcctInqRq(client, clDoc, BankProductType.Card);
			IFXRs_Type ifxRs = TransportProvider.doRequest(pc.getIfxRq_type());
			if (ifxRs.getBankAcctInqRs().getStatus().getStatusCode() == 0)
			{
				for (CardAcctRec_Type rec : ifxRs.getBankAcctInqRs().getCardAcctRec())
				{
					String cardNumber = rec.getCardAcctId().getCardNum();
					MobileBankService mobilebankService = GateSingleton.getFactory().service(MobileBankService.class);
					GroupResult<String, List<MobileBankRegistration>> result1 = mobilebankService.getRegistrations(false, cardNumber);

					IKFLException cardException = result1.getException(cardNumber);
					if (cardException != null) {
						log.warn("Сбой при получении регистраций по карте", cardException);
					}

					for (MobileBankRegistration mbr : result1.getResult(cardNumber))
					{
						phones.add(mbr.getMainCardInfo().getMobilePhoneNumber());
					}
				}
			}
		}

		String sendingText = text;
		if (document instanceof ConcludeEDBODocument)
		{
			String cardsType = " ";
			String cardsErrorType = " ";
			for (CardInfo info : document.getCardInfos())
			{
				if (info.getStatus() == CardInfoStatus.COMPLETE)
					cardsType = cardsType + info.getCardTypeString() + " ";
				if (info.getStatus() == CardInfoStatus.ERROR)
					cardsErrorType = cardsErrorType + info.getCardTypeString() + " ";
			}
			sendingText = text.replace("[udbo_number/]", (((ConcludeEDBODocument) document).getEDBOOrderNumber())+ "")
					.replace("[udbo_date/]", new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime()))
					.replace("[cards/]", cardsType).replace("[cardsError/]", cardsErrorType);
		}

		try
		{
			for (String phone : phones)
				MessagingSingleton.getInstance().getSmsTransportService(false, false).sendSms(phone, sendingText, sendingText, 0L);
		}
		catch (IKFLMessagingException e)
		{
			throw new GateException(e);
		}
	}
}
