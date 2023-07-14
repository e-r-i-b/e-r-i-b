package com.rssl.phizic.operations.sberbankForEveryDay;

import com.rssl.phizgate.sbnkd.SBNKDLimitService;
import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.GuestLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.auth.modes.ConfirmStrategyProvider;
import com.rssl.phizic.auth.modes.SmsPasswordConfirmStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.cellOperator.CellOperator;
import com.rssl.phizic.business.dictionaries.departments.DepartmentsRecoding;
import com.rssl.phizic.business.dictionaries.departments.DepartmentsRecordingService;
import com.rssl.phizic.business.ermb.MobileBankManager;
import com.rssl.phizic.business.fraudMonitoring.FraudMonitoringSendersFactory;
import com.rssl.phizic.business.fraudMonitoring.exceptions.ProhibitionOperationFraudException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonDocumentTypeComparator;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.sbnkd.SberbankForEveryDayHelper;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.claims.sbnkd.*;
import com.rssl.phizic.gate.claims.sbnkd.impl.CardInfoImpl;
import com.rssl.phizic.gate.claims.sbnkd.impl.IssueCardDocumentImpl;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.mobilebank.MobileBankService;
import com.rssl.phizic.logging.operations.context.OperationContextUtil;
import com.rssl.phizic.messaging.MessageTemplateType;
import com.rssl.phizic.messaging.MessagingHelper;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.ext.sbrf.strategy.GuestStrategyProvider;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.rsa.InteractionType;
import com.rssl.phizic.rsa.PhaseType;
import com.rssl.phizic.rsa.exceptions.BlockClientOperationFraudException;
import com.rssl.phizic.rsa.exceptions.ProhibitionOperationFraudGateException;
import com.rssl.phizic.rsa.senders.FraudMonitoringSender;
import com.rssl.phizic.rsa.senders.initialization.PhaseInitializationData;
import com.rssl.phizic.rsa.senders.types.EventsType;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.PassportTypeWrapper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.claim.sbnkd.GetPrivateClientSender;
import com.rssl.phizicgate.esberibgate.claim.sbnkd.IssueCardClaimProcessor;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * Операция для работы с оформлением заявки "Сбербанк на каждый день"
 * @author shapin
 * @ created 11.12.14
 * @ $Author$
 * @ $Revision$
 */
public class CreateSberbankForEveryDayClaimOperation extends ConfirmableOperationBase implements EditEntityOperation
{
	private static final IssueCardService issueCardService = new IssueCardService();
	private static final SBNKDLimitService limitService = new SBNKDLimitService();
	private IssueCardDocumentImpl issueCardDoc;
	private boolean vipClient = false;

	public void save() throws BusinessException, BusinessLogicException
	{
		try
		{
			if (issueCardDoc.getId() != null && issueCardService.getClaim(issueCardDoc.getId()).getStatus() == IssueCardStatus.VIP_CLIENT)
			{
				vipClient = true;
				throw new BusinessLogicException("Клиент является VIP-клиентом");
			}
			boolean needGetPrivateClient = issueCardDoc.getId() == null && issueCardDoc.isGuest();

			if (StringHelper.isNotEmpty(issueCardDoc.getPhone()) && issueCardDoc.getContactData().length == 0)
			{
				issueCardDoc.setContactNumber0(issueCardDoc.getPhone());
				issueCardDoc.setContactType0(ContactData.ContactType.HOME.name());
			}
			issueCardDoc.setCardCount(issueCardDoc.getCardInfos().size());
			CardInfoImpl firstCard = issueCardDoc.getCardInfos().get(0);
			issueCardDoc.setFirstCardName(firstCard.getCardName());
			issueCardDoc.setFirstCardCurrency(firstCard.getContractCurrency());
			issueCardDoc.setAllCardNames(getAllCardNamesFromInfos(issueCardDoc.getCardInfos()));
			issueCardService.addOrUpdate(issueCardDoc);
			if (needGetPrivateClient)
				checkVip();
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
}

	private void checkVip() throws BusinessException, BusinessLogicException, GateException
	{
		boolean isError = false;
		try
		{
			Set<String> cardsByPhone = GateSingleton.getFactory().service(MobileBankService.class).getCardsByPhoneViaReportDB(issueCardDoc.getPhone());
			if (cardsByPhone == null || cardsByPhone.isEmpty())
			{
				isError = true;
				return;
			}
			String cardNumber = cardsByPhone.iterator().next();
			BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
			Card card = GroupResultHelper.getOneResult(bankrollService.getCard(cardNumber + "^null^" + issueCardDoc.getRbTbBranch() + "^" + issueCardDoc.getOwnerId()));
			if (card == null)
			{
				isError = true;
				return;
			}
			Client cardClient = card.getCardClient();

			issueCardDoc.setPersonBirthday(cardClient.getBirthDay());
			issueCardDoc.setPersonBirthplace(cardClient.getBirthPlace());

			List<? extends ClientDocument> documents = cardClient.getDocuments();
			ClientDocument clientDocument = documents.get(0);
			issueCardDoc.setIdentityCardType(PassportTypeWrapper.getPassportType(ClientDocumentType.PASSPORT_WAY));

			issueCardDoc.setIdentityCardSeries(clientDocument.getDocSeries());
			issueCardDoc.setIdentityCardNumber(clientDocument.getDocNumber());
			if(clientDocument.getDocIssueDate() != null)
			{
				issueCardDoc.setIdentityCardIssueDate(clientDocument.getDocIssueDate());
			}
			if(clientDocument.getDocTimeUpDate() != null)
			{
				issueCardDoc.setIdentityCardExpDate(clientDocument.getDocTimeUpDate());
			}

			issueCardDoc.setIdentityCardIssuedBy(clientDocument.getDocIssueBy());
			issueCardDoc.setIdentityCardIssuedCode(clientDocument.getDocIssueByCode());
			issueCardDoc.setPersonResident(cardClient.isResident());
			new GetPrivateClientSender(GateSingleton.getFactory()).send(issueCardDoc);
		}
		catch (LogicException e)
		{
			isError = true;
			log.error("Ошибка получения информации по карте клиента", e);
		}
		catch (SystemException e)
		{
			isError = true;
			log.error("Ошибка получения информации по карте клиента", e);
		}
		finally
		{
			if (isError)
			{
				issueCardDoc.setStatus(IssueCardStatus.INIT_NO_VIP);
				issueCardService.addOrUpdate(issueCardDoc);
			}
		}
	}

	public void initializeNewClaim() throws BusinessLogicException, BusinessException
	{
		issueCardDoc = new IssueCardDocumentImpl();

		OperationContextUtil.synchronizeObjectAndOperationContext(issueCardDoc);

		issueCardDoc.setStageNumber(1);
		issueCardDoc.setVerified(true);
		issueCardDoc.setCardInfos(new ArrayList<CardInfoImpl>(1));

		CardInfoImpl ci = new CardInfoImpl();
		issueCardDoc.getCardInfos().add(ci);
		Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		if (login instanceof GuestLogin)
		{
			//гость
			GuestLogin guestLogin = (GuestLogin)login;
			issueCardDoc.setOwnerId(guestLogin.getGuestCode());
			issueCardDoc.setPhone(guestLogin.getAuthPhone());
			issueCardDoc.setGuest(true);
			issueCardDoc.setStatus(IssueCardStatus.INIT);
			issueCardDoc.getCardInfos().get(0).setMBCPhone(guestLogin.getAuthPhone());
			issueCardDoc.setPersonResident(true);
			if (PersonContext.isAvailable())
			{
				String loginAlias = PersonContext.getPersonDataProvider().getPersonData().getGuestLoginAlias();
				if (StringHelper.isNotEmpty(loginAlias))
					issueCardDoc.setLogin(loginAlias);
			}

		}
		else
		{
			if(PersonContext.isAvailable())
			{
				//не гость
				PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
				ActivePerson person = personData.getPerson();
				issueCardDoc.setOwnerId(person.getLogin().getId());
				issueCardDoc.setTb(personData.getDepartment().getRegion());
				issueCardDoc.setOsb(personData.getDepartment().getOSB());
				issueCardDoc.setVsp(personData.getDepartment().getVSP());
				if (person.getSegmentCodeType() == SegmentCodeType.VIP)
				{
					issueCardDoc.setStatus(IssueCardStatus.VIP_CLIENT);
					vipClient = true;
					throw new BusinessLogicException("Клиент является VIP-клиентом");
				}
				else
				{
					issueCardDoc.setStatus(IssueCardStatus.INIT_NO_VIP);
				}
				issueCardDoc.setPersonGender(person.getGender());
				issueCardDoc.setPersonFirstName(person.getFirstName());
				issueCardDoc.setPersonLastName(person.getSurName());
				issueCardDoc.setPersonMiddleName(person.getPatrName());
				issueCardDoc.setPersonBirthday(person.getBirthDay());
				if(StringHelper.isNotEmpty(person.getBirthPlace()))
				{
					issueCardDoc.setPersonBirthplace(person.getBirthPlace());
				}
				issueCardDoc.setPersonCitizenship(person.getCitizenship());
				issueCardDoc.setPersonResident(person.getIsResident());
				issueCardDoc.setPersonTaxId(person.getInn());

				PersonDocument document = getPersonDocument(person);
				ClientDocumentType clientDoc = ClientDocumentType.valueOf(document.getDocumentType().toString());
				issueCardDoc.setIdentityCardType(PassportTypeWrapper.getPassportType(clientDoc));
				if(document.getDocumentSeries() != null)
				{
					issueCardDoc.setIdentityCardSeries(document.getDocumentSeries());
				}
				issueCardDoc.setIdentityCardNumber(document.getDocumentNumber());
				if(document.getDocumentIssueDate() != null)
				{
					issueCardDoc.setIdentityCardIssueDate(document.getDocumentIssueDate());
				}
				if(document.getDocumentTimeUpDate() != null)
				{
					issueCardDoc.setIdentityCardExpDate(document.getDocumentTimeUpDate());
				}
				if (document.getDocumentIssueBy() != null)
				{
					issueCardDoc.setIdentityCardIssuedBy(document.getDocumentIssueBy());
				}
				if(StringHelper.isNotEmpty(document.getDocumentIssueByCode()))
				{
					issueCardDoc.setIdentityCardIssuedCode(document.getDocumentIssueByCode());
				}

				FullAddress address[] = new FullAddress[2];
				if(person.getResidenceAddress() != null && (!person.getResidenceAddress().isEmpty() || StringHelper.isNotEmpty(person.getResidenceAddress().getUnparseableAddress())))
				{
					address[0] = new FullAddress();
					address[0].setPostalCode(person.getResidenceAddress().getPostalCode());
					address[0].setRegion(person.getResidenceAddress().getProvince());
					address[0].setCity(person.getResidenceAddress().getCity());
					address[0].setAfterSityAdress(SberbankForEveryDayHelper.getAfterCityAddress(person.getResidenceAddress()));
				}

				if(person.getRegistrationAddress() != null && (!person.getRegistrationAddress().isEmpty() || StringHelper.isNotEmpty(person.getRegistrationAddress().getUnparseableAddress())))
				{
					address[1] = new FullAddress();
					address[1].setPostalCode(person.getRegistrationAddress().getPostalCode());
					address[1].setRegion(person.getResidenceAddress().getProvince());
					address[1].setCity(person.getRegistrationAddress().getCity());
					address[1].setAfterSityAdress(SberbankForEveryDayHelper.getAfterCityAddress(person.getRegistrationAddress()));
					address[1].setRegistrationAddress(true);
				}
				issueCardDoc.setAddress(address);

				Collection<String> phones = MobileBankManager.getInfoPhones(person, true);
				String ph = phones.iterator().next();
				if(StringHelper.isNotEmpty(ph))
					issueCardDoc.getCardInfos().get(0).setMBCPhone(ph);
				issueCardDoc.setEmail(person.getEmail() != null ? person.getEmail() : "");

				issueCardDoc.setPhone(ph);
				issueCardDoc.setGuest(false);
				issueCardDoc.setLastLogonCardNumber(login.getLastLogonCardNumber());
			}
			else
			{
				throw new BusinessLogicException("Операция временно недоступна");
			}

		}

		CellOperator operator = SberbankForEveryDayHelper.findOperatorByPhone(issueCardDoc.getPhone());
		issueCardDoc.setAutopaymentIsAvailable(!operator.getFlAuto().equals(SberbankForEveryDayHelper.FALSE_VALUE));
		issueCardDoc.setBalanceLessThan(String.valueOf(operator.getBalance()));
		issueCardDoc.setMinAutopaymentSum(operator.getMinSumm());
		issueCardDoc.setMaxAutopaymentSum(operator.getMaxSumm());
	}

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		try
		{
			issueCardDoc = issueCardService.getClaim(id);

			OperationContextUtil.synchronizeObjectAndOperationContext(issueCardDoc);

			Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
			if(login instanceof GuestLogin)
			{
				GuestLogin guestLogin = (GuestLogin)login;
				if(!guestLogin.getGuestCode().equals(issueCardDoc.getOwnerId()))
					throw new BusinessException("Пользователь не является владельцем документа");
			}
			else
			{
				if(!PersonContext.getPersonDataProvider().getPersonData().getLogin().getId().equals(issueCardDoc.getOwnerId()))
					throw new BusinessException("Пользователь не является владельцем документа");
				PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
				ActivePerson person = personData.getPerson();
				//для клиента определяем сегмент при каждом обращении к заявке и, если он VIP, то заявку продолжить нельзя
				if (person.getSegmentCodeType() == SegmentCodeType.VIP)
				{
					issueCardDoc.setStatus(IssueCardStatus.VIP_CLIENT);
					vipClient = true;
					throw new BusinessLogicException("Клиент является VIP-клиентом");
				}
			}
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	public IssueCardDocumentImpl getEntity()
	{
		return issueCardDoc;
	}

	/**
	 * Получить актуальный документ из БД по идентификатору
	 * @return заявка СБНКД
	 * @throws BusinessException
	 */
	public IssueCardDocumentImpl getDocumentById() throws BusinessException
	{
		try
		{
			return issueCardService.getClaim(issueCardDoc.getId());
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	private PersonDocument getPersonDocument(ActivePerson owner)
	{
		List<PersonDocument> personDocuments = new ArrayList<PersonDocument>(owner.getPersonDocuments());
		Collections.sort(personDocuments, new PersonDocumentTypeComparator());
		return personDocuments.get(0);
	}

	@Override public ConfirmStrategyProvider getConfirmStrategyProvider()
	{
		return new GuestStrategyProvider(AuthModule.getAuthModule().getPrincipal());
	}

	/**
	 * Отправка запроса во ФМ
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public void doPreFraudControl() throws BusinessLogicException, BusinessException
	{
		if (PersonHelper.isGuest())
		{
			return;
		}

		try
		{
			//проверка на мошейничество
			FraudMonitoringSender sender = FraudMonitoringSendersFactory.getInstance().getSender(EventsType.REQUEST_NEW_CARD);
			//noinspection unchecked
			sender.initialize(new PhaseInitializationData(InteractionType.ASYNC, PhaseType.SENDING_REQUEST));
			sender.send();
		}
		catch (Exception e)
		{
			log.error(e);
		}
	}

	/**
	 * Проверка во ФМ после ввода смс пароля (ожидание ответа)
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public void doFraudControl() throws BusinessLogicException, BusinessException
	{
		if (PersonHelper.isGuest())
		{
			return;
		}

		try
		{
			//проверка на мошейничество
			FraudMonitoringSender sender = FraudMonitoringSendersFactory.getInstance().getSender(EventsType.REQUEST_NEW_CARD);
			//noinspection unchecked
			sender.initialize(new PhaseInitializationData(InteractionType.ASYNC, PhaseType.WAITING_FOR_RESPONSE));
			sender.send();
		}
		catch (ProhibitionOperationFraudGateException e)
		{
			MessagingHelper.sendMessage(PersonHelper.getContextPerson().getLogin().getId(), MessageTemplateType.DENY.name() + ".FM.SberBankForEveryDay");
			throw new ProhibitionOperationFraudException(e.getMessage(), e);
		}
		catch (BlockClientOperationFraudException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			log.error(e);
		}
	}

	protected void saveConfirm() throws BusinessException, BusinessLogicException, SecurityLogicException
	{
		doFraudControl();
		doProcess();
	}

	protected void doProcess() throws BusinessException, BusinessLogicException
	{
		//проверить статус
		try
		{
			issueCardDoc = issueCardService.getClaim(issueCardDoc.getId());
			DepartmentsRecoding code  = getConvertedDepartment(issueCardDoc.getContractRegionId(), issueCardDoc.getContractAgencyId(), issueCardDoc.getContractBranchId());
			issueCardDoc.setConvertedRegionId(StringHelper.addLeadingZeros(code.getDespatch().substring(0, 2), 3));
			issueCardDoc.setConvertedAgencyId(code.getOsbSpoobk2());
			issueCardDoc.setConvertedBranchId(code.getOfficeSpoobk2());
			issueCardDoc.setConvertedRbTbBranchId(code.getTbSpoobk2() + code.getDespatch().substring(0, 2) + code.getOsbSpoobk2());

			issueCardDoc.setEDBOBranchId(issueCardDoc.getContractBranchId());
			issueCardDoc.setEDBOAgencyId(issueCardDoc.getConvertedAgencyId());

			if (issueCardDoc.isGuest())
			{
				if (issueCardDoc.getStatus() == IssueCardStatus.VIP_CLIENT)
				{
					vipClient = true;
					throw new BusinessLogicException("Клиент является VIP-клиентом");
				}
				else if (issueCardDoc.getStatus() == IssueCardStatus.INIT)
				{
					issueCardDoc.setStatus(IssueCardStatus.CONFIRM);
					issueCardService.addOrUpdate(issueCardDoc);
				}
				else if (issueCardDoc.getStatus() == IssueCardStatus.INIT_NO_VIP)
				{
					issueCardDoc.setStatus(IssueCardStatus.CONFIRM_NO_VIP);
					issueCardService.addOrUpdate(issueCardDoc);
				}
			}
			else
			{
				issueCardDoc.setStatus(IssueCardStatus.CONFIRM_NO_VIP);
				issueCardService.addOrUpdate(issueCardDoc);
			}

			IssueCardClaimProcessor.getIt().processClaim(issueCardDoc, null);
		}
		catch (GateException e)
		{
			log.error("Ошибка отправки  GetPrivateClient", e);
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	private CardInfoImpl getFirstCard()  throws BusinessException
	{
		List<CardInfoImpl> cardInfos = issueCardDoc.getCardInfos();
		for (CardInfoImpl cardInfo : cardInfos)
		{
			if (cardInfo.isFirstCard())
				return cardInfo;
		}
		throw  new BusinessException("В заявке нет первой карты");
	}

	public boolean isVipClient()
	{
		return vipClient;
	}

	public ConfirmableObject getConfirmableObject()
	{
		return issueCardDoc;
	}

	public ConfirmStrategy getConfirmStrategy()
	{
		return new SmsPasswordConfirmStrategy();
	}

	public boolean checkLimit()
	{
		if (!issueCardDoc.isGuest())
			return true;

		try
		{
			return limitService.checkLimit(issueCardDoc);
		}
		catch (GateException e)
		{
			log.error("Ошибка обновления лимита для СБНКД", e);
		}

		return true;
	}

	public void removeCard(CardInfo cardInfo) throws GateException
	{
		issueCardService.removeCardInfo(cardInfo);
	}

	public DepartmentsRecoding getConvertedDepartment(String region, String branch, String office) throws GateException
	{
		try
		{
			DepartmentsRecordingService documentService = new DepartmentsRecordingService();
			return documentService.getDepartmentsRecodingByEribCodes(region, branch, office);
		}
		catch (Exception e)
		{
			log.error("Ошибка при поиске подразделения в справочнике перекодировки СПООБК2", e);
			return null;
		}
	}

	/**
	 * @param cardInfoList - список заказанных карт
	 * @return строку названий заказанных карт
	 */
	private String getAllCardNamesFromInfos(List<CardInfoImpl> cardInfoList)
	{
		if (CollectionUtils.isEmpty(cardInfoList))
			return "";
		StringBuilder names = new StringBuilder();
		boolean isFirst = true;
		for (CardInfoImpl info : cardInfoList)
		{
			if (!isFirst)
				names.append(", ");
			names.append(info.getCardName());
			isFirst = false;
		}
		return names.toString();
	}
}
