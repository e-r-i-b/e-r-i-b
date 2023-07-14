package com.rssl.phizicgate.esberibgate.bankroll;

import com.rssl.phizgate.common.services.bankroll.ExtendedCodeGateImpl;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.CardType;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.clients.DocumentTypeComparator;
import com.rssl.phizic.gate.cms.claims.CardBlockingClaim;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.ReIssueCardClaim;
import com.rssl.phizic.gate.utils.EntityCompositeId;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.gate.config.ESBEribConfig;
import com.rssl.phizicgate.esberibgate.messaging.ClientRequestHelperBase;
import com.rssl.phizicgate.esberibgate.types.AccountStateActionType;
import com.rssl.phizicgate.esberibgate.types.BlockReasonWrapper;
import com.rssl.phizicgate.esberibgate.types.CardTypeWrapper;
import com.rssl.phizicgate.esberibgate.utils.CardOrAccountCompositeId;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.*;
import org.apache.commons.collections.CollectionUtils;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Хелпер для создания запросов к WAY, необходимых BankrollService.
 * @author egorova
 * @ created 28.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class BankrollRequestHelper extends ClientRequestHelperBase
{
	private static final String MESSAGE_DELIVERY_TYPE = "E";
	//Максимально WAY4 предоставляет выписку по 10 последним операциям. Если больше, обрезаем.
	private static final int MAXIMUM_ABSTRACT_COUNT = 10;

	public static final String SYSTEM_ID_IPS = "urn:sbrfsystems:99-ips";

	public BankrollRequestHelper(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * Создает структуру CardAcctDInqRq (запрос CRDWI получение детальной информации о карте)
	 * @param rbTbBrchId rbTbBrchId
	 * @param cardInfo - массив пар <номер карты, офис> по которому получаем информацию
	 * @return Запрос на получение детализированной информации по карте
	 */
	public IFXRq_Type createCardAcctDInqRq(String rbTbBrchId, Pair<String, Office> ... cardInfo) throws GateLogicException, GateException
	{
		ExternalSystemHelper.check(ConfigFactory.getConfig(ESBEribConfig.class).getEsbERIBCardSystemId99Way());

		IFXRq_Type ifxRq = new IFXRq_Type();

		CardAcctDInqRq_Type cardAcctDInqRq = new CardAcctDInqRq_Type();
		cardAcctDInqRq.setRqUID(generateUUID());
		cardAcctDInqRq.setRqTm(generateRqTm());
		cardAcctDInqRq.setOperUID(generateOUUID());
		cardAcctDInqRq.setSPName(getSPName());
		cardAcctDInqRq.setBankInfo(getBankInfo(rbTbBrchId, null));

		CardInfo_Type[] cardInfos = new CardInfo_Type[cardInfo.length];
		for(int i=0; i<cardInfo.length; i++)
		{
			CardInfo_Type cardInfoRq = new CardInfo_Type();
			CardAcctId_Type cardAcctId = new CardAcctId_Type();
			cardAcctId.setCardNum(cardInfo[i].getFirst());
			//systemId не заполянем - неизвестен и опционален 
			if(cardInfo[i].getSecond() != null)
				cardAcctId.setBankInfo(getBankInfo(cardInfo[i].getSecond(), rbTbBrchId, null));
			cardInfoRq.setCardAcctId(cardAcctId);
			cardInfos[i] = cardInfoRq;
		}
		cardAcctDInqRq.setCardInfo(cardInfos);

		ifxRq.setCardAcctDInqRq(cardAcctDInqRq);
		return ifxRq;
	}

	public IFXRq_Type createCardAcctDInqRq(String... cardId) throws GateLogicException, GateException
	{
		ExternalSystemHelper.check(ConfigFactory.getConfig(ESBEribConfig.class).getEsbERIBCardSystemId99Way());

		IFXRq_Type ifxRq = new IFXRq_Type();

		CardAcctDInqRq_Type cardAcctDInqRq = new CardAcctDInqRq_Type();
		cardAcctDInqRq.setRqUID(generateUUID());
		cardAcctDInqRq.setRqTm(generateRqTm());
		cardAcctDInqRq.setOperUID(generateOUUID());
		cardAcctDInqRq.setSPName(getSPName());
		cardAcctDInqRq.setBankInfo(getBankInfo(getRbTbBrch(EntityIdHelper.getCommonCompositeId(cardId[0])), null));

		CardInfo_Type[] cardInfos = new CardInfo_Type[cardId.length];
		for(int i=0; i<cardId.length; i++)
		{
			CardInfo_Type cardInfoRq = new CardInfo_Type();
			CardAcctId_Type cardAcctId = new CardAcctId_Type();
			EntityCompositeId compositeId = EntityIdHelper.getCommonCompositeId(cardId[i]);
			cardAcctId.setCardNum(compositeId.getEntityId());
			cardAcctId.setSystemId(compositeId.getSystemId());
			cardInfoRq.setCardAcctId(cardAcctId);
			cardInfos[i] = cardInfoRq;
		}
		cardAcctDInqRq.setCardInfo(cardInfos);

		ifxRq.setCardAcctDInqRq(cardAcctDInqRq);
		return ifxRq;
	}

	/**
	 * Получение выписки
	 * @param number - количество операций по карте, которое необход имо вернуть
	 * @param card - карта(ы)
	 * @return Запрос на получении расширенной выписки (в наших терминах "мини-выписки")
	 */
	public IFXRq_Type createCCAcctExtStmtInqRq(Long number, Card... card) throws GateLogicException, GateException
	{
		ExternalSystemHelper.check(ConfigFactory.getConfig(ESBEribConfig.class).getEsbERIBCardSystemId99Way());

		IFXRq_Type ifxRq = new IFXRq_Type();

		CCAcctExtStmtInqRq_Type cCAcctExtStmtInqRq = new CCAcctExtStmtInqRq_Type();
		cCAcctExtStmtInqRq.setRqUID(generateUUID());
		cCAcctExtStmtInqRq.setRqTm(generateRqTm());
		cCAcctExtStmtInqRq.setOperUID(generateOUUID());
		cCAcctExtStmtInqRq.setSPName(getSPName());
		cCAcctExtStmtInqRq.setBankInfo(getBankInfo(getRbTbBrch(EntityIdHelper.getCardCompositeId(card[0])), null));

		CardInfo_Type[] cardInfos = new CardInfo_Type[card.length];
		for (int i = 0; i < card.length; i++)
		{
			CardInfo_Type cardInfo = new CardInfo_Type();
			CardAcctId_Type cardAcctId = new CardAcctId_Type();
			cardAcctId.setCardNum(card[i].getNumber());
			EntityCompositeId compositeId = EntityIdHelper.getCardCompositeId(card[i]);
			cardAcctId.setSystemId(compositeId.getSystemId());
			BankInfo_Type bankInfo = new BankInfo_Type();
			bankInfo.setRbBrchId(compositeId.getRbBrchId());
			cardAcctId.setBankInfo(bankInfo);
			cardInfo.setCardAcctId(cardAcctId);
			cardInfos[i] = cardInfo;
		}
		cCAcctExtStmtInqRq.setCardInfo(cardInfos);
		cCAcctExtStmtInqRq.setOpCount((number > MAXIMUM_ABSTRACT_COUNT) ? MAXIMUM_ABSTRACT_COUNT : number);
		ifxRq.setCCAcctExtStmtInqRq(cCAcctExtStmtInqRq);
		return ifxRq;
	}

	/**
	 * отправка отчета по счету карты
	 * @param card - карта
	 * @param email - адрес электроннной почты, куда отправляем
	 * @param startDate - начальная дата, с которой запрашивается отчет
	 * @param endDate - конечная дата, до которой запрашивается отчет
	 * @param cbCode - код территориального банка, в котором открыт счет МБК, по которой клиент произвел идентификацию
	 * @return Запрос на отправку отчета по счету карты
	 */
	public IFXRq_Type createBankAcctStmtImgInqRq(Card card, String email, Calendar startDate, Calendar endDate, String cbCode) throws GateLogicException, GateException
	{
		ExternalSystemHelper.check(ConfigFactory.getConfig(ESBEribConfig.class).getEsbERIBCardSystemId99Way());

		IFXRq_Type ifxRq = new IFXRq_Type();

		BankAcctStmtImgInqRq_Type bankAcctStmtImgInqRq = new BankAcctStmtImgInqRq_Type();

		bankAcctStmtImgInqRq.setRqUID(generateUUID());
		bankAcctStmtImgInqRq.setRqTm(generateRqTm());
		bankAcctStmtImgInqRq.setOperUID(generateOUUID());
		bankAcctStmtImgInqRq.setSPName(getSPName());
		bankAcctStmtImgInqRq.setBankInfo(getBankInfo(cbCode, null));
		bankAcctStmtImgInqRq.setSystemId(SYSTEM_ID_IPS);

		SelRangeDt_Type selRangeDt = new SelRangeDt_Type();
		selRangeDt.setStartDate(getStringDate(startDate));
		selRangeDt.setEndDate(getStringDate(endDate));
		bankAcctStmtImgInqRq.setSelRangeDt(selRangeDt);

		CardAcctId_Type cardAcctId = new CardAcctId_Type();
		EntityCompositeId compositeId = EntityIdHelper.getCardCompositeId(card);
		cardAcctId.setSystemId(compositeId.getSystemId());
		cardAcctId.setCardNum(card.getNumber());
		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setRbBrchId(compositeId.getRbBrchId());
		cardAcctId.setBankInfo(bankInfo);
		bankAcctStmtImgInqRq.setCardAcctId(cardAcctId);

		ContactInfo_Type contactInfo = new ContactInfo_Type();
		contactInfo.setEmailAddr(email);
		contactInfo.setMessageDeliveryType(MessageDeliveryType_Type.fromString(MESSAGE_DELIVERY_TYPE));
		bankAcctStmtImgInqRq.setContactInfo(contactInfo);

		ifxRq.setBankAcctStmtImgInqRq(bankAcctStmtImgInqRq);
		return ifxRq;
	}

	/**
	 * запрос на блокировку карты
	 * @param card - карта
	 * @param claim - зявка на блокировку
	 * @param cbCode  - код территориального банка, в котором открыт счет МБК, по которой клиент произвел идентификацию
	 * @return запрос на блокировку карты
	 */
	public IFXRq_Type createCardBlockRq(Card card, CardBlockingClaim claim, String cbCode) throws GateLogicException, GateException
	{
		ExternalSystemHelper.check(ConfigFactory.getConfig(ESBEribConfig.class).getEsbERIBCardSystemId99Way());

		IFXRq_Type ifxRq = new IFXRq_Type();

		CardBlockRq_Type cardBlockRq = new CardBlockRq_Type();

		cardBlockRq.setRqUID(generateUUID());
		cardBlockRq.setRqTm(generateRqTm());
		cardBlockRq.setOperUID(generateOUUID());
		cardBlockRq.setSPName(getSPName());
		cardBlockRq.setBankInfo(getBankInfo(cbCode, null));

		CardAcctId_Type cardAcctId = new CardAcctId_Type();
		cardAcctId.setCardNum(card.getNumber());
		/*Если карта была зополнена из CRDWI, то SystemId будет null, т.к. по спецификации из CRDWI не приходит карточный SystemId.
		  На данный момент при блокировке карты информация по ней заполняется именно из CRDWI (BUG034623), по этому SystemId берем из заявки на блокировку*/
		EntityCompositeId cardCompositeId = EntityIdHelper.getCardOrAccountCompositeId(claim.getCardExternalId());
		EntityCompositeId compositeId = EntityIdHelper.getCardCompositeId(card);

		cardAcctId.setSystemId(cardCompositeId.getSystemId());
		BankInfo_Type bankInfo = new BankInfo_Type();		
		bankInfo.setRbBrchId(compositeId.getRbBrchId());
		cardAcctId.setBankInfo(bankInfo);
		cardBlockRq.setCardAcctId(cardAcctId);

		cardBlockRq.setBlockReason(BlockReasonType.fromString(BlockReasonWrapper.getBlockReason(claim.getBlockingReason())));

		ifxRq.setCardBlockRq(cardBlockRq);
		return ifxRq;
	}

	/**
	 * запрос на получение детальной информации по вкладу
	 * @param accountId - номер вклада
	 * @param office - офис в котором открыт вкад
	 * @return запрос получение детальной информации по вкладу
	 */
	public IFXRq_Type createAcctInfoRq(String accountId, Office office) throws GateLogicException, GateException
	{
		IFXRq_Type ifxRq = new IFXRq_Type();
		AcctInfoRq_Type acctInfoRq = new AcctInfoRq_Type();
		acctInfoRq.setRqUID(generateUUID());
		acctInfoRq.setRqTm(generateRqTm());
		acctInfoRq.setOperUID(generateOUUID());
		acctInfoRq.setSPName(getSPName());

		DepAcctRec_Type depAcctRec = new DepAcctRec_Type();
		DepAcctId_Type depAcctId = new DepAcctId_Type();

		CardOrAccountCompositeId compositeId = EntityIdHelper.getCardOrAccountCompositeId(accountId);
		depAcctId.setAcctId(compositeId.getEntityId());

		BankInfo_Type bankInfo;
		String cbCode = getRbTbBrch(compositeId);
		bankInfo = getBankInfo(compositeId, cbCode, true);

		acctInfoRq.setBankInfo(bankInfo);
		depAcctId.setBankInfo(bankInfo);

		depAcctId.setSystemId(compositeId.getSystemIdActiveSystem());

		depAcctRec.setDepAcctId(depAcctId);
		// На время пилота можно передать только один номер вклада
		DepAcctRec_Type[] depAcctRecs = new DepAcctRec_Type[]{depAcctRec};

		acctInfoRq.setDepAcctRec(depAcctRecs);
		ifxRq.setAcctInfoRq(acctInfoRq);
		return ifxRq;
	}

	/**
	 * запрос на получение выписки по вкладу
	 * @param abstractType - тип выписки Short10 – короткая выписка( 10 последних операций); Full – полная выписка за период
	 * @param dateFrom - если abstractType = Full, то указывается дата с которой получить выписку
	 * @param dateTo - если abstractType = Full, то указывается дата по которую получить выписку
	 * @param account  - список счетов
	 * @return запрос на получение выписки по вкладу
	 */
	public IFXRq_Type createDepAcctStmtInqRq(String abstractType, Calendar dateFrom, Calendar dateTo, Account account) throws GateLogicException, GateException
	{
		IFXRq_Type ifxRq = new IFXRq_Type();
		DepAcctStmtInqRq_Type depAcctStmtInqRq = new DepAcctStmtInqRq_Type();
		depAcctStmtInqRq.setRqUID(generateUUID());
		depAcctStmtInqRq.setRqTm(generateRqTm());
		depAcctStmtInqRq.setOperUID(generateOUUID());
		depAcctStmtInqRq.setSPName(getSPName());

		BankInfo_Type bankInfo = new BankInfo_Type();
		EntityCompositeId compositeId = EntityIdHelper.getAccountCompositeId(account);
		bankInfo.setRbTbBrchId(getRbTbBrch(compositeId));

		depAcctStmtInqRq.setBankInfo(bankInfo);

		DepAcctStmtInqRq_TypeDepAcctRec depAcctRec = new DepAcctStmtInqRq_TypeDepAcctRec();

		DepAcctId_Type depAcctId = new DepAcctId_Type();

		depAcctId.setSystemId(compositeId.getSystemIdActiveSystem());
		depAcctId.setAcctId(compositeId.getEntityId());
		bankInfo.setRbBrchId(compositeId.getRbBrchId());
		depAcctId.setBankInfo(bankInfo);

		depAcctRec.setDepAcctId(depAcctId);
		depAcctStmtInqRq.setDepAcctRec(depAcctRec);
		depAcctStmtInqRq.setStmtType(StmtType_Type.fromString(abstractType));
		if (dateFrom != null)
			depAcctStmtInqRq.setDateFrom(getStringDate(dateFrom));
		if (dateTo != null)
			depAcctStmtInqRq.setDateTo(getStringDate(dateTo));
		ifxRq.setDepAcctStmtInqRq(depAcctStmtInqRq);
		return ifxRq;
	}

	/**
	 * Запрос на получение дополнительных карт
	 * @param mainCards карты, для которых ищутся дополнительные
	 * @return Запрос на получение дополнительных карт
	 */
	public IFXRq_Type createCardAdditionalInfoRq(Card... mainCards) throws GateLogicException, GateException
	{
		IFXRq_Type ifxRq = new IFXRq_Type();
		CardAdditionalInfoRq_Type cardAdditionalInfoRq = new CardAdditionalInfoRq_Type();
		cardAdditionalInfoRq.setRqUID(generateUUID());
		cardAdditionalInfoRq.setRqTm(generateRqTm());
		cardAdditionalInfoRq.setOperUID(generateOUUID());
		cardAdditionalInfoRq.setSPName(getSPName());

		CardInfo_Type[] cardInfos = new CardInfo_Type[mainCards.length];
		for (int i = 0; i < mainCards.length; i++)
		{
			CardInfo_Type cardInfo = new CardInfo_Type();
			CardOrAccountCompositeId compositeId = EntityIdHelper.getCardCompositeId(mainCards[i]);
			cardInfo.setBankInfo(getBankInfo(getRbTbBrch(compositeId), null));

			CardAcctId_Type cardAcctId = new CardAcctId_Type();
			cardAcctId.setSystemId(compositeId.getSystemIdActiveSystem());
			cardAcctId.setCardNum(mainCards[i].getNumber());
			cardAcctId.setBankInfo(getBankInfo(null, compositeId.getRbBrchId()));
			cardInfo.setCardAcctId(cardAcctId);

			cardInfos[i] = cardInfo;
		}
		cardAdditionalInfoRq.setCardInfo(cardInfos);

		ifxRq.setCardAdditionalInfoRq(cardAdditionalInfoRq);
		return ifxRq;
	}

	/**
	 * Заявка на утерю сберкнижки (SACS)
	 * @param client - клиент
	 * @param account - счет утерянной сберкнижки
	 * @return заполненная данными заявка
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public IFXRq_Type createLossPassbookClaimRq(Client client, Account account)  throws GateLogicException, GateException
	{
		SetAccountStateRq_Type setAccountStateRq = new SetAccountStateRq_Type();
		setAccountStateRq.setRqUID(generateUUID());
		setAccountStateRq.setRqTm(generateRqTm());
		setAccountStateRq.setOperUID(generateOUUID());
		setAccountStateRq.setSPName(getSPName());

		//Информация о банке счета МБК, по которой клиент произвел идентификацию
		String cbCode = getRbTbBrch(client.getInternalOwnerId());
		setAccountStateRq.setBankInfo(getBankInfo(cbCode, null));

		//Информация о вкладном счете
		DepAcctId_Type depAcctId = new DepAcctId_Type();
		EntityCompositeId compositeId = EntityIdHelper.getAccountCompositeId(account);
		depAcctId.setSystemId(compositeId.getSystemIdActiveSystem());
		depAcctId.setAcctId(account.getNumber());
		BankInfo_Type bankInfo = getBankInfo(getRbTbBrch(compositeId), null);
		depAcctId.setBankInfo(bankInfo);
		setAccountStateRq.setDepAcctId(depAcctId);

		//удостоверение личности
		List<? extends ClientDocument> documents = client.getDocuments();
		if (CollectionUtils.isEmpty(documents))
			throw new GateException("Не найден документ клиента id=" + client.getId());
		//сортируем документы клиента
		Collections.sort(documents, new DocumentTypeComparator());
		ClientDocument document = documents.get(0);

		//Информация о клиенте
		PersonInfo_Type personInfo = getPersonInfo(client,document,false,false);
		CustInfo_Type custInfo = new CustInfo_Type();
		custInfo.setPersonInfo(personInfo);
		setAccountStateRq.setCustInfo(custInfo);

		//Действие метода. Признак утери сберкнижки по счету
		setAccountStateRq.setAccountStateAction(AccountStateActionType.STATE_SAVE_BOOK_LOST.name());

		IFXRq_Type ifxRq = new IFXRq_Type();
		ifxRq.setSetAccountStateRq(setAccountStateRq);
		return ifxRq;
	}

	/**
	 * Запрос на перевыпуск карты.
	 *
	 * @return заполненная данными заявка.
	 */
	public IFXRq_Type createCardReissuePlaceRq(ReIssueCardClaim claim) throws GateException
	{
		CardReissuePlaceRq_Type cardReissuePlaceRq = new CardReissuePlaceRq_Type();
		cardReissuePlaceRq.setRqUID(generateUUID());
		cardReissuePlaceRq.setRqTm(generateRqTm());
		cardReissuePlaceRq.setSPName(getSPName());

		ExtendedCodeGateImpl bankInfoCode    = new ExtendedCodeGateImpl(claim.getBankInfoCode());
		ExtendedCodeGateImpl destinationCode = new ExtendedCodeGateImpl(claim.getDestinationCode());

		cardReissuePlaceRq.setBankInfo(getBankInfoMod1(bankInfoCode));

		cardReissuePlaceRq.setCardNum(claim.getCardNumber());
		cardReissuePlaceRq.setCardType(CardTypeWrapper.getCardTypeForRequest(CardType.valueOf(claim.getCardType())));

		if (bankInfoCode.equals(destinationCode))
		{
			cardReissuePlaceRq.setDestination(getBankInfoLeadZero(destinationCode));
		}
		else
		{
			cardReissuePlaceRq.setDestination(getBankInfoLeadZero(claim.getConvertedDestinationCode()));
		}

		SourceId_Type sourceId = new SourceId_Type();
		sourceId.setSourceIdType(getSPName().getValue());
		cardReissuePlaceRq.setSource(sourceId);

		cardReissuePlaceRq.setReasonCode(claim.getReasonCode());
		cardReissuePlaceRq.setCommission(claim.getIsCommission());

		IFXRq_Type ifxrq = new IFXRq_Type();
		ifxrq.setCardReissuePlaceRq(cardReissuePlaceRq);
		return ifxrq;
	}

	private BankInfoMod1_Type getBankInfoMod1(Code officeCode)
	{
		BankInfoMod1_Type bankInfo = new BankInfoMod1_Type();

		ExtendedCodeGateImpl code = new ExtendedCodeGateImpl(officeCode);
		bankInfo.setBranchId(StringHelper.appendLeadingZeros(code.getOffice(), 4)); //Номер филиала
		bankInfo.setAgencyId(StringHelper.appendLeadingZeros(code.getBranch(), 4)); //Номер отделения
		bankInfo.setRegionId(StringHelper.appendLeadingZeros(code.getRegion(), 2)); //Номер тербанка

		return bankInfo;
	}

	private BankInfoLeadZero_Type getBankInfoLeadZero(Code officeCode)
	{
		BankInfoMod1_Type bankInfo = getBankInfoMod1(officeCode);

		return new BankInfoLeadZero_Type(
				bankInfo.getRegionId(), bankInfo.getAgencyId(), bankInfo.getBranchId()
		);
	}
}
