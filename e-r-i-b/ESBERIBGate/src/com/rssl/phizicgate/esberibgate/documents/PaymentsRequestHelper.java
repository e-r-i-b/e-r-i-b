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
	 * ��������� ��p������ ���������� �� �����.
	 * @param account ����
	 * @param owner ��������
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
		//���� ������ ��� � � �������������� ����������� ����������, �� ��������� ���������� ��� ���
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
	 * ��������� ��p������ ���������� �� ����� ��������.
	 * @param account ����
	 * @param owner ��������
	 * @return DepAcctId_Type
	 */
	public DepAcctId_Type createDepAcctIdFrom(Account account, Client owner) throws GateLogicException, GateException
	{
		DepAcctId_Type result = createDepAcctId(account,owner);
		if (StringHelper.isEmpty(result.getSystemId()))
			throw new GateLogicException("�� �� ������ ��������� ������� � ����� �����. ����������, �������� ������ ���� ��������.");
		return result;
	}

	private BankInfo_Type getSrbBankInfoType(String rbBrchId) throws GateLogicException, GateException
	{
		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setRbBrchId(rbBrchId);
		return bankInfo;
	}

	/**
	 * ��������� ��p������ ���������� �� ����� �����.
	 * @param card �����
	 * @param owner ��������
	 * @param fillCustInfo ���������� ���������� � ��������� �������
	 * @param needOffice ������� ����, ��� ����� ��������� ��������� ���������� � �������������, ������� �����
	 * @param expireDate ���� ��������
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
			//���� ����� � ������ � CRWDI.. ���� �� ������ ... ���� � �� ������������, �������������� ����� ��������� GFL
			String cardExternalId = getFactory().service(BackRefBankrollService.class).findCardExternalId(owner.getInternalOwnerId(), cardNumber);
			if (cardExternalId == null)
			{
				throw new GateException("�� ������� ���������� � �� �� ����� " + cardNumber);
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
	 * ��������� ��p������ ���������� �� ���.
	 * @param imAccount ���
	 * @param owner ��������
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
	 * ��������� ��p������ ���������� �� ���.
	 * @param imAccount ���
	 * @param owner ��������
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
	 * ��������� ��������� ���������� � �������
	 * @param client ������
	 * @return PersonInfo_Type
	 */
	public PersonInfo_Type createPersonInfo(Client client)
	{
		PersonInfo_Type personInfo = new PersonInfo_Type();
		personInfo.setPersonName(new PersonName_Type(client.getSurName(), client.getFirstName(), client.getPatrName(), null));
		return personInfo;
	}

	/**
	 * ��������� ���������� �� �������
	 * @param externalId - id �������
	 * @param accountNumber - ����� �������� �����
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
	 * ��������� �������� Regular ����������� ������ �� ���������� ���������
	 * @param document ������ �� ��
	 * @return ����������� ���������.
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
	 * ��������� ��������� ���������� ����������� ������� ��� ��������
	 * (����� ���������� � ��� ������)
	 * @param client ������
	 * @return ContactInfo_Type
	 */
	public static ContactInfo_Type createClientRegistrationAddressContactInfo(Client client)
	{
		ContactInfo_Type contactInfo = new ContactInfo_Type();
		//�������� ������������ � ������� ������ ���� ���� ����� - ����� �����������
		FullAddress_Type addressType = new FullAddress_Type();
		addressType.setAddrType(AddressType.REGISTRATION.getType());

		Address address = client.getLegalAddress();
		addressType.setAddr3(address != null ? StringHelper.getEmptyIfNull(address.getUnparseableAddress()): "");
		//��������� ���������� ����������
		contactInfo.setPostAddr(new FullAddress_Type[]{addressType});
		return contactInfo;
	}

	protected static String getStringDateTime(Calendar date)
	{
		return date==null ? null : XMLDatatypeHelper.formatDateTimeWithoutTimeZone(date);
	}

	/**
	 * ������ ������ �� ��������� ������� �������� � ��� �� ���� �� ������ �� �������� ���
	 * @param claim - ������ �� �������� ���
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
