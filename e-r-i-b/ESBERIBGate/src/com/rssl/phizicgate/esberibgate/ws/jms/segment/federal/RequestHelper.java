package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal;

import com.rssl.phizgate.common.services.bankroll.ExtendedCodeGateImpl;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.config.ESBEribConfig;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.utils.EntityCompositeId;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.logging.operations.context.OperationContext;
import com.rssl.phizic.utils.PassportTypeWrapper;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizicgate.esberibgate.messaging.BackRefInfoRequestHelper;
import com.rssl.phizicgate.esberibgate.messaging.RequestHelperBase;
import com.rssl.phizicgate.esberibgate.types.ExecutionEventTypeWrapper;
import com.rssl.phizicgate.esberibgate.types.SumTypeWrapper;
import com.rssl.phizicgate.esberibgate.utils.CardOrAccountCompositeId;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.utils.LongOfferCompositeId;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author akrenev
 * @ created 21.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * Хелпер сборки сообщений в шину
 */

public final class RequestHelper
{
	private static final int DOCUMENT_ISSUEBY_MAXLEN = RequestHelperBase.DOCUMENT_ISSUEBY_MAXLEN;

	private RequestHelper(){}

	/**
	 * преобразовать дату в строку в формате "2010-05-13T13:48:22"
	 * @param date дата
	 * @return строка
	 */
	public static String getStringDateTime(Calendar date)
	{
		return date==null ? null : XMLDatatypeHelper.formatDateTimeWithoutTimeZone(date);
	}

	/**
	 * Получить строкое значение для календаря, удовлетворяющее
	 * xsd:date	xmlns:xsd=http://www.w3.org/2001/XMLSchema
	 * @param date дата для преобразования
	 * @return xsd:date
	 */
	public static String getStringDate(Calendar date)
	{
		if (date == null)
			return null;
		return XMLDatatypeHelper.formatDateWithoutTimeZone(date);
	}

	/**
	 * распарсить дату
	 * @param date строка даты
	 * @return дата
	 */
	public static Calendar parseCalendar(String date)
	{
		if (StringHelper.isEmpty(date))
			return null;
		return XMLDatatypeHelper.parseDateTime(date);
	}

	/**
	 * Сенерировать RqUUID
	 * @return RqUUID
	 */
	public static String generateUUID()
	{
		return new RandomGUID().getStringValue();
	}

	/**
	 * Сенерировать OperUUID
	 * @return OperUUID
	 */
	public static String generateOUUID()
	{
		return OperationContext.getCurrentOperUID();
	}

	/**
	 * Сенерировать дату и время передачи сообщения
	 * @return RqTm
	 */
	public static String generateRqTm()
	{
		GregorianCalendar messageDate = new GregorianCalendar();
		return getStringDateTime(messageDate);
	}

	/**
	 * собрать BankInfo
	 * @param rbTbBranch информация о банке
	 * @return BankInfo
	 */
	public static BankInfoType makeBankInfo(String rbTbBranch)
	{
		BankInfoType info = new BankInfoType();
		info.setRbTbBrchId(StringHelper.appendLeadingZeros(rbTbBranch,8));
		return info;
	}

	/**
	 * Заполнение структуры BankInfo
	 * @param compositeId композитный идентификатор карты или счета
	 * @return BankInfoType
	 */
	public static BankInfoType getBankInfo(CardOrAccountCompositeId compositeId)
	{
		BankInfoType bankInfo = new BankInfoType();
		if (compositeId.getBranchId() != null)
			bankInfo.setBranchId(StringHelper.appendLeadingZeros(compositeId.getBranchId(), 4));
		if (compositeId.getRegionId() != null)
			bankInfo.setRegionId(StringHelper.appendLeadingZeros(compositeId.getRegionId(), 3));
		if (compositeId.getAgencyId() != null)
			bankInfo.setAgencyId(StringHelper.appendLeadingZeros(compositeId.getAgencyId(), 4));
		if (compositeId.getRbBrchId() != null)
			bankInfo.setRbBrchId(compositeId.getRbBrchId());
		return bankInfo;
	}

	/**
	 * Заполнение структуры BankInfo
	 * @param compositeId композитный идентификатор длительного поручения
	 * @return BankInfoType
	 */
	public static BankInfoType getBankInfo(LongOfferCompositeId compositeId)
	{
		BankInfoType bankInfo = new BankInfoType();
		bankInfo.setBranchId(compositeId.getBranchId());
		bankInfo.setRegionId(compositeId.getRegionId());
		bankInfo.setAgencyId(compositeId.getAgencyId());
		bankInfo.setRbBrchId(compositeId.getRbBrchId());
		return bankInfo;
	}

	/**
	 * получить ТБ по документу
	 * @param document документ
	 * @return ТБ
	 * @throws GateException
	 */
	public static String getTB(GateDocument document) throws GateException
	{
		return new ExtendedCodeGateImpl(document.getOffice().getCode()).getRegion();
	}

	private static IdentityCardType getDocument(ClientDocument document)
	{
		IdentityCardType identityCard = new IdentityCardType();
		identityCard.setIdType(PassportTypeWrapper.getPassportType(document.getDocumentType()));
		// Для всех интерфейсов, кроме CEDBO, серия паспорта way должна передаваться в теге "номер" как есть
		if (document.getDocumentType() == ClientDocumentType.PASSPORT_WAY)
		{
			identityCard.setIdNum(document.getDocSeries());
		}
		else
		{
			//мега заглушка. Необходимо серию паспорта обязательно прислать с пробелом.
			if ((document.getDocumentType() == ClientDocumentType.REGULAR_PASSPORT_RF) && document.getDocSeries() != null  && document.getDocSeries().matches("\\d{4}"))
				identityCard.setIdSeries(document.getDocSeries().substring(0, 2) + " " + document.getDocSeries().substring(2, 4));
			else
				identityCard.setIdSeries(document.getDocSeries());

			identityCard.setIdNum(document.getDocNumber());
		}
		//все требуемые поля обызательны для заполнения
		identityCard.setIssuedBy(StringHelper.truncate(document.getDocIssueBy(), DOCUMENT_ISSUEBY_MAXLEN));
		identityCard.setIssueDt(document.getDocIssueDate() == null ? null : getStringDate(document.getDocIssueDate()));
		if (!StringHelper.isEmpty(document.getDocIssueByCode()))
			identityCard.setIssuedCode(document.getDocIssueByCode());
		identityCard.setExpDt(getStringDate(document.getDocTimeUpDate()));
		return identityCard;
	}

	/**
	 * Заполнение информации о клиенте
	 * @param client - клиент
	 * @return Заполненного клиента
	 */
	public static PersonInfoType getPersonInfo(Client client)
	{
		return getPersonInfo(client.getSurName(), client.getFirstName(), client.getPatrName());
	}

	/**
	 * Заполнение информации о клиенте
	 * @param surName - фамилия
	 * @param firstName - имя
	 * @param patrName - отчество
	 * @return Заполненного клиента
	 */
	public static PersonInfoType getPersonInfo(String surName, String firstName, String patrName)
	{
		PersonInfoType personInfo = new PersonInfoType();

		PersonName personName = new PersonName();
		personName.setLastName(surName);
		personName.setFirstName(firstName);
		if (StringHelper.isNotEmpty(patrName))
			personName.setMiddleName(patrName);
		personInfo.setPersonName(personName);

		return personInfo;
	}

	/**
	 * Заполнение информации о клиенте с датой рождения
	 * @param client - клиент
	 * @return Заполненного клиента
	 */
	public static PersonInfoType getPersonInfoWithBirthday(Client client)
	{
		PersonInfoType personInfo = getPersonInfo(client);
		personInfo.setBirthday(getStringDate(client.getBirthDay()));
		return personInfo;
	}

	/**
	 * Заполнение информации о клиенте
	 * @param client - клиент
	 * @param document - документ клиента
	 * @return Заполненного клиента
	 */
	public static PersonInfoType getPersonInfo(Client client, ClientDocument document)
	{
		PersonInfoType personInfo = getPersonInfoWithBirthday(client);
		personInfo.setIdentityCard(getDocument(document));
		return personInfo;
	}

	/**
	 * Заполнить структуру информация по карте
	 * @param card карта
	 * @param owner владелец
	 * @param expireDate дата закрытия
	 * @param backRefInfoRequestHelper хелпер работы с уточнением инфы в шлюзе
	 * @return CardAcctId_Type
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 * @throws GateException
	 */
	public static CardAcctIdType createCardAcctId(Card card, Client owner, Calendar expireDate, BackRefInfoRequestHelper backRefInfoRequestHelper) throws GateLogicException, GateException
	{
		CardAcctIdType result = new CardAcctIdType();
		CardOrAccountCompositeId compositeId = getCardCompositeId(card, owner, backRefInfoRequestHelper);

		ESBEribConfig eribConfig = ConfigFactory.getConfig(ESBEribConfig.class);
		ExternalSystemHelper.check(eribConfig.getEsbERIBCardSystemId99Way());

		result.setSystemId(compositeId.getSystemId());
		result.setCardNum(card.getNumber());
		result.setEndDt(getStringDate(expireDate));

		CustInfoType custInfo = new CustInfoType();
		custInfo.setPersonInfo(getPersonInfo(owner));
		result.setCustInfo(custInfo);

		result.setBankInfo(getBankInfo(compositeId));
		return result;
	}

	/**
	 * Вернуть Объектное представление композитного идентификатора карты
	 * @param card карта
	 * @param owner собственник карты
	 * @param backRefInfoHelper хелпер обратной связи
	 * @return Объектное представление композитного идентификатора карты
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public static CardOrAccountCompositeId getCardCompositeId(Card card, Client owner, BackRefInfoRequestHelper backRefInfoHelper) throws GateException, GateLogicException
	{
		CardOrAccountCompositeId compositeId = EntityIdHelper.getCardCompositeId(card);
		if (StringHelper.isEmpty(compositeId.getSystemId()) || StringHelper.isEmpty(compositeId.getRbBrchId()))
		{
			//поля могут н придти в CRWDI.. если не пришли ... ищем в БД идентифкатор, сформированный после получения GFL
			String cardExternalId = backRefInfoHelper.getCardExternalId(owner, card.getNumber());
			if (cardExternalId == null)
				throw new GateException("Не найдена информация в БД по карте " + card.getNumber());

			compositeId = EntityIdHelper.getCardOrAccountCompositeId(cardExternalId);
		}

		return compositeId;
	}

	/**
	 * заполнить стpуктуру информация по счету.
	 * @param account счет
	 * @param owner владелец
	 * @param backRefInfoRequestHelper хелпер работы с уточнением инфы в шлюзе
	 * @return DepAcctIdType
	 */
	public static DepAcctIdType createDepAcctId(Account account, Client owner, BackRefInfoRequestHelper backRefInfoRequestHelper) throws GateLogicException, GateException
	{

		DepAcctIdType result = new DepAcctIdType();
		CardOrAccountCompositeId compositeId = EntityIdHelper.getCardOrAccountCompositeId(account.getId());
		result.setAcctId(account.getNumber());
		CustInfoType custInfo = new CustInfoType();
		custInfo.setPersonInfo(getPersonInfo(owner));
		result.setCustInfo(custInfo);
		//Если клиент СРБ и в идентификаторе отсутствует информация, то заполняем значениями для СРБ
		if (backRefInfoRequestHelper.isUseSRBValues(compositeId, owner))
		{
			result.setSystemId(backRefInfoRequestHelper.getSRBExternalSystemCode());
			result.setBankInfo(getSrbBankInfoType(backRefInfoRequestHelper.getSRBRbBrchId(account.getOffice())));
		}
		else
		{
			result.setSystemId(compositeId.getSystemIdActiveSystem());
			result.setBankInfo(getBankInfo(compositeId));
		}
		return result;
	}

	private static BankInfoType getSrbBankInfoType(String branch) throws GateLogicException, GateException
	{
		BankInfoType bankInfo = new BankInfoType();
		bankInfo.setRbBrchId(branch);
		return bankInfo;
	}

	/**
	 * заполнить стpуктуру информация по счету.
	 * @param account счет
	 * @param residentBank банк
	 * @return DepAcctIdType
	 */
	public static DepAcctIdType createDepAcctId(String account, ResidentBank residentBank) throws GateLogicException, GateException
	{
		DepAcctIdType result = new DepAcctIdType();
		result.setAcctId(account);
		result.setBIC(residentBank.getBIC());
		result.setCorrAcctId(residentBank.getAccount());
		return result;
	}

	/**
	 * заполнить стpуктуру информация по ОМС.
	 * @param imAccount ОМС
	 * @param owner владелец
	 * @return DepAcctId_Type
	 */
	public static DepAcctIdType createDepAcctId(IMAccount imAccount, Client owner) throws GateLogicException, GateException
	{
		DepAcctIdType result = new DepAcctIdType();
		EntityCompositeId compositeId = EntityIdHelper.getCommonCompositeId(imAccount.getId());
		result.setAcctId(imAccount.getNumber());
		CustInfoType custInfo = new CustInfoType();
		custInfo.setPersonInfo(getPersonInfo(owner));
		result.setCustInfo(custInfo);
		result.setSystemId(compositeId.getSystemIdActiveSystem());
		BankInfoType bankInfo = new BankInfoType();
		bankInfo.setRbBrchId(compositeId.getRbBrchId());
		result.setBankInfo(bankInfo);
		return result;
	}

	/**
	 * Заполнить струтуру Regular параметрами заявки на длительное поручение
	 * @param document заявка на ДП
	 * @return заполненная структура.
	 */
	public static Regular getRegular(LongOffer document)
	{
		Regular regular = new Regular();
		regular.setDateBegin(getStringDate(document.getStartDate()));
		regular.setDateEnd(getStringDate(document.getEndDate()));
		Regular.PayDay payDay = new Regular.PayDay();
		payDay.setDay(document.getPayDay());
		regular.setPayDay(payDay);
		regular.setPriority(document.getPriority());
		regular.setExeEventCode(ExecutionEventTypeWrapper.toESBValue(document.getExecutionEventType()));
		regular.setSummaKindCode(SumTypeWrapper.toESBValue(document.getSumType()));
		regular.setPercent(document.getPercent());
		return regular;
	}
}
