package com.rssl.phizicgate.esberibgate.documents;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BackRefBankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Address;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.config.ESBEribConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.payments.AccountOrIMATransferBase;
import com.rssl.phizic.gate.utils.EntityCompositeId;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizicgate.esberibgate.messaging.RequestHelperBase;
import com.rssl.phizicgate.esberibgate.types.AddressType;
import com.rssl.phizicgate.esberibgate.types.ExecutionEventTypeWrapper;
import com.rssl.phizicgate.esberibgate.types.SumTypeWrapper;
import com.rssl.phizicgate.esberibgate.utils.CardOrAccountCompositeId;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.utils.LoanCompositeId;
import com.rssl.phizicgate.esberibgate.ws.generated.*;

import java.util.Calendar;

/**
 * @author krenev
 * @ created 16.07.2010
 * @ $Author$
 * @ $Revision$
 */
public class PaymentsRequestHelper extends RequestHelperBase
{
	public PaymentsRequestHelper(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * заполнить стpуктуру информация по счету.
	 * @param account счет
	 * @param owner владелец
	 * @return DepAcctId_Type
	 */
	public DepAcctId_Type createDepAcctId(Account account, Client owner) throws GateLogicException, GateException
	{

		DepAcctId_Type result = new DepAcctId_Type();
		CardOrAccountCompositeId compositeId = EntityIdHelper.getCardOrAccountCompositeId(account.getId());
		result.setAcctId(account.getNumber());
		CustInfo_Type custInfo = new CustInfo_Type();
		custInfo.setPersonInfo(createPersonInfo(owner));
		result.setCustInfo(custInfo);
		//Если клиент СРБ и в идентификаторе отсутствует информация, то заполняем значениями для СРБ
		if (getBackRefInfoRequestHelper().isUseSRBValues(compositeId, owner))
		{
			result.setSystemId(getBackRefInfoRequestHelper().getSRBExternalSystemCode());
			result.setBankInfo(getSrbBankInfoType(getBackRefInfoRequestHelper().getSRBRbBrchId(account.getOffice())));
		}
		else
		{
			result.setSystemId(compositeId.getSystemIdActiveSystem());
			result.setBankInfo(getBankInfo(compositeId, null, true));
		}
		return result;
	}

	/**
	 * заполнить стpуктуру информация по счету списания.
	 * @param account счет
	 * @param owner владелец
	 * @return DepAcctId_Type
	 */
	public DepAcctId_Type createDepAcctIdFrom(Account account, Client owner) throws GateLogicException, GateException
	{
		DepAcctId_Type result = createDepAcctId(account,owner);
		if (StringHelper.isEmpty(result.getSystemId()))
			throw new GateLogicException("Вы не можете выполнить перевод с этого счета. Пожалуйста, выберите другой счет списания.");
		return result;
	}

	private BankInfo_Type getSrbBankInfoType(String rbBrchId) throws GateLogicException, GateException
	{
		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setRbBrchId(rbBrchId);
		return bankInfo;
	}

	/**
	 * заполнить стpуктуру информация по СВОЕЙ карте.
	 * @param card карта
	 * @param owner владелец
	 * @param fillCustInfo заполнение информации о владельце запросе
	 * @param needOffice признак того, что нужно заполнить подробную информацию о подразделении, ведущем карту
	 * @param expireDate дата закрытия
	 * @return CardAcctId_Type
	 */
	public CardAcctId_Type createCardAcctId(Card card, Client owner, Calendar expireDate, boolean fillCustInfo, boolean needOffice)
			throws GateLogicException, GateException
	{
		String cardNumber = card.getNumber();
		CardAcctId_Type result = new CardAcctId_Type();
		CardOrAccountCompositeId compositeId = EntityIdHelper.getCardCompositeId(card);
		if (StringHelper.isEmpty(compositeId.getSystemId())|| StringHelper.isEmpty(compositeId.getRbBrchId()))
		{
			//поля могут н придти в CRWDI.. если не пришли ... ищем в БД идентифкатор, сформированный после получения GFL
			String cardExternalId = getFactory().service(BackRefBankrollService.class).findCardExternalId(owner.getInternalOwnerId(), cardNumber);
			if (cardExternalId == null)
			{
				throw new GateException("Не найдена информация в БД по карте " + cardNumber);
			}
			compositeId = EntityIdHelper.getCardOrAccountCompositeId(cardExternalId);
		}

		ESBEribConfig eribConfig = ConfigFactory.getConfig(ESBEribConfig.class);
		ExternalSystemHelper.check(eribConfig.getEsbERIBCardSystemId99Way());

		result.setSystemId(compositeId.getSystemId());
		result.setCardNum(card.getNumber());
		result.setEndDt(getStringDate(expireDate));
		if (fillCustInfo && owner != null)
		{
			CustInfo_Type custInfo = new CustInfo_Type();
			custInfo.setPersonInfo(createPersonInfo(owner));
			result.setCustInfo(custInfo);
		}
		result.setBankInfo(getBankInfo(compositeId, null, needOffice));
		return result;
	}

	/**
	 * заполнить стpуктуру информация по ОМС.
	 * @param imAccount ОМС
	 * @param owner владелец
	 * @return DepAcctId_Type
	 */
	public DepAcctId_Type createDepAcctId(IMAccount imAccount, Client owner) throws GateLogicException, GateException
	{

		DepAcctId_Type result = new DepAcctId_Type();
		EntityCompositeId compositeId = EntityIdHelper.getCommonCompositeId(imAccount.getId());
		result.setAcctId(imAccount.getNumber());
		CustInfo_Type custInfo = new CustInfo_Type();
		custInfo.setPersonInfo(createPersonInfo(owner));
		result.setCustInfo(custInfo);
		result.setSystemId(compositeId.getSystemIdActiveSystem());
		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setRbBrchId(compositeId.getRbBrchId());
		result.setBankInfo(bankInfo);
		return result;
	}

	/**
	 * заполнить стpуктуру информация по ОМС.
	 * @param imAccount ОМС
	 * @param owner владелец
	 * @return IMAAcctId_Type
	 */
	public IMAAcctId_Type createIMAAcctId(IMAccount imAccount, Client owner) throws GateLogicException, GateException
	{

		IMAAcctId_Type result = new IMAAcctId_Type();
		EntityCompositeId compositeId = EntityIdHelper.getCommonCompositeId(imAccount.getId());
		result.setAcctId(imAccount.getNumber());
		CustInfo_Type custInfo = new CustInfo_Type();
		custInfo.setPersonInfo(createPersonInfo(owner));
		result.setCustInfo(custInfo);
		result.setSystemId(compositeId.getSystemIdActiveSystem());
		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setRbBrchId(compositeId.getRbBrchId());
		result.setBankInfo(bankInfo);
		return result;
	}

	/**
	 * заполнить структуру информации о клиенте
	 * @param client клиент
	 * @return PersonInfo_Type
	 */
	public PersonInfo_Type createPersonInfo(Client client)
	{
		PersonInfo_Type personInfo = new PersonInfo_Type();
		personInfo.setPersonName(new PersonName_Type(client.getSurName(), client.getFirstName(), client.getPatrName(), null));
		return personInfo;
	}

	/**
	 * Заполнить информацию по кредиту
	 * @param externalId - id кредита
	 * @param accountNumber - Номер ссудного счета
	 * @return LoanAcctId_Type
	 */
	public LoanAcctId_Type createLoanAcctId(String externalId, String accountNumber, String agreementNumber) throws GateLogicException, GateException
	{
		LoanAcctId_Type loanAcctId = new LoanAcctId_Type();
		LoanCompositeId compositeId = EntityIdHelper.getLoanCompositeId(externalId);
		loanAcctId.setSystemId(compositeId.getSystemIdActiveSystem());
		loanAcctId.setAcctId(accountNumber);
		loanAcctId.setProdType(compositeId.getProductType());
		loanAcctId.setAgreemtNum(agreementNumber);
		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setRbBrchId(compositeId.getRbBrchId());
		loanAcctId.setBankInfo(bankInfo);

		return loanAcctId;
	}

	/**
	 * Заполнить струтуру Regular параметрами заявки на длительное поручение
	 * @param document заявка на ДП
	 * @return заполненная структура.
	 */
	public Regular_Type getRegular(LongOffer document)
	{
		Regular_Type regular = new Regular_Type();
		regular.setDateBegin(getStringDate(document.getStartDate()));
		regular.setDateEnd(getStringDate(document.getEndDate()));
		regular.setPayDay(new Regular_TypePayDay(document.getPayDay()));
		regular.setPriority(document.getPriority());
		regular.setExeEventCode(ExecutionEventTypeWrapper.toESBValue(document.getExecutionEventType()));
		regular.setSummaKindCode(SumTypeWrapper.toESBValue(document.getSumType()));
		regular.setPercent(document.getPercent());
		return regular;
	}

	/**
	 * Заполнить структуру контактной информацией клиента для платежей
	 * (адрес регисрации и тип адреса)
	 * @param client клиент
	 * @return ContactInfo_Type
	 */
	public static ContactInfo_Type createClientRegistrationAddressContactInfo(Client client)
	{
		ContactInfo_Type contactInfo = new ContactInfo_Type();
		//согласно спецификации в запросе должен быть один адрес - адрес регистрации
		FullAddress_Type addressType = new FullAddress_Type();
		addressType.setAddrType(AddressType.REGISTRATION.getType());

		Address address = client.getLegalAddress();
		addressType.setAddr3(address != null ? StringHelper.getEmptyIfNull(address.getUnparseableAddress()): "");
		//добавляем контактную информацию
		contactInfo.setPostAddr(new FullAddress_Type[]{addressType});
		return contactInfo;
	}

	protected static String getStringDateTime(Calendar date)
	{
		return date==null ? null : XMLDatatypeHelper.formatDateTimeWithoutTimeZone(date);
	}

	/**
	 * Создаёт запрос на уточнение статуса операций в АБС из ЕРИБ по заявки на открытие ОМС
	 * @param claim - заявка на открытие ОМС
	 * @return
	 */
	public static XferOperStatusInfoRq_Type createXferOperStatusInfoRq(AccountOrIMATransferBase claim)
	{
		XferOperStatusInfoRq_Type xferOperStatusInfo = new XferOperStatusInfoRq_Type();
		xferOperStatusInfo.setRqUID(generateUUID());
		xferOperStatusInfo.setRqTm(generateRqTm());
		xferOperStatusInfo.setOperUID(claim.getOperUId());
		xferOperStatusInfo.setOriginalRqTm(getStringDateTime(claim.getOperTime()));
		xferOperStatusInfo.setSPName(SPName_Type.BP_ERIB);
		
		return xferOperStatusInfo; 
	}
}
