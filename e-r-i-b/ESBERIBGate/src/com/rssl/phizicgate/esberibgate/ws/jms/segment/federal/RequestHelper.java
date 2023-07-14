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
 * ������ ������ ��������� � ����
 */

public final class RequestHelper
{
	private static final int DOCUMENT_ISSUEBY_MAXLEN = RequestHelperBase.DOCUMENT_ISSUEBY_MAXLEN;

	private RequestHelper(){}

	/**
	 * ������������� ���� � ������ � ������� "2010-05-13T13:48:22"
	 * @param date ����
	 * @return ������
	 */
	public static String getStringDateTime(Calendar date)
	{
		return date==null ? null : XMLDatatypeHelper.formatDateTimeWithoutTimeZone(date);
	}

	/**
	 * �������� ������� �������� ��� ���������, ���������������
	 * xsd:date	xmlns:xsd=http://www.w3.org/2001/XMLSchema
	 * @param date ���� ��� ��������������
	 * @return xsd:date
	 */
	public static String getStringDate(Calendar date)
	{
		if (date == null)
			return null;
		return XMLDatatypeHelper.formatDateWithoutTimeZone(date);
	}

	/**
	 * ���������� ����
	 * @param date ������ ����
	 * @return ����
	 */
	public static Calendar parseCalendar(String date)
	{
		if (StringHelper.isEmpty(date))
			return null;
		return XMLDatatypeHelper.parseDateTime(date);
	}

	/**
	 * ������������ RqUUID
	 * @return RqUUID
	 */
	public static String generateUUID()
	{
		return new RandomGUID().getStringValue();
	}

	/**
	 * ������������ OperUUID
	 * @return OperUUID
	 */
	public static String generateOUUID()
	{
		return OperationContext.getCurrentOperUID();
	}

	/**
	 * ������������ ���� � ����� �������� ���������
	 * @return RqTm
	 */
	public static String generateRqTm()
	{
		GregorianCalendar messageDate = new GregorianCalendar();
		return getStringDateTime(messageDate);
	}

	/**
	 * ������� BankInfo
	 * @param rbTbBranch ���������� � �����
	 * @return BankInfo
	 */
	public static BankInfoType makeBankInfo(String rbTbBranch)
	{
		BankInfoType info = new BankInfoType();
		info.setRbTbBrchId(StringHelper.appendLeadingZeros(rbTbBranch,8));
		return info;
	}

	/**
	 * ���������� ��������� BankInfo
	 * @param compositeId ����������� ������������� ����� ��� �����
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
	 * ���������� ��������� BankInfo
	 * @param compositeId ����������� ������������� ����������� ���������
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
	 * �������� �� �� ���������
	 * @param document ��������
	 * @return ��
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
		// ��� ���� �����������, ����� CEDBO, ����� �������� way ������ ������������ � ���� "�����" ��� ����
		if (document.getDocumentType() == ClientDocumentType.PASSPORT_WAY)
		{
			identityCard.setIdNum(document.getDocSeries());
		}
		else
		{
			//���� ��������. ���������� ����� �������� ����������� �������� � ��������.
			if ((document.getDocumentType() == ClientDocumentType.REGULAR_PASSPORT_RF) && document.getDocSeries() != null  && document.getDocSeries().matches("\\d{4}"))
				identityCard.setIdSeries(document.getDocSeries().substring(0, 2) + " " + document.getDocSeries().substring(2, 4));
			else
				identityCard.setIdSeries(document.getDocSeries());

			identityCard.setIdNum(document.getDocNumber());
		}
		//��� ��������� ���� ����������� ��� ����������
		identityCard.setIssuedBy(StringHelper.truncate(document.getDocIssueBy(), DOCUMENT_ISSUEBY_MAXLEN));
		identityCard.setIssueDt(document.getDocIssueDate() == null ? null : getStringDate(document.getDocIssueDate()));
		if (!StringHelper.isEmpty(document.getDocIssueByCode()))
			identityCard.setIssuedCode(document.getDocIssueByCode());
		identityCard.setExpDt(getStringDate(document.getDocTimeUpDate()));
		return identityCard;
	}

	/**
	 * ���������� ���������� � �������
	 * @param client - ������
	 * @return ������������ �������
	 */
	public static PersonInfoType getPersonInfo(Client client)
	{
		return getPersonInfo(client.getSurName(), client.getFirstName(), client.getPatrName());
	}

	/**
	 * ���������� ���������� � �������
	 * @param surName - �������
	 * @param firstName - ���
	 * @param patrName - ��������
	 * @return ������������ �������
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
	 * ���������� ���������� � ������� � ����� ��������
	 * @param client - ������
	 * @return ������������ �������
	 */
	public static PersonInfoType getPersonInfoWithBirthday(Client client)
	{
		PersonInfoType personInfo = getPersonInfo(client);
		personInfo.setBirthday(getStringDate(client.getBirthDay()));
		return personInfo;
	}

	/**
	 * ���������� ���������� � �������
	 * @param client - ������
	 * @param document - �������� �������
	 * @return ������������ �������
	 */
	public static PersonInfoType getPersonInfo(Client client, ClientDocument document)
	{
		PersonInfoType personInfo = getPersonInfoWithBirthday(client);
		personInfo.setIdentityCard(getDocument(document));
		return personInfo;
	}

	/**
	 * ��������� ��������� ���������� �� �����
	 * @param card �����
	 * @param owner ��������
	 * @param expireDate ���� ��������
	 * @param backRefInfoRequestHelper ������ ������ � ���������� ���� � �����
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
	 * ������� ��������� ������������� ������������ �������������� �����
	 * @param card �����
	 * @param owner ����������� �����
	 * @param backRefInfoHelper ������ �������� �����
	 * @return ��������� ������������� ������������ �������������� �����
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public static CardOrAccountCompositeId getCardCompositeId(Card card, Client owner, BackRefInfoRequestHelper backRefInfoHelper) throws GateException, GateLogicException
	{
		CardOrAccountCompositeId compositeId = EntityIdHelper.getCardCompositeId(card);
		if (StringHelper.isEmpty(compositeId.getSystemId()) || StringHelper.isEmpty(compositeId.getRbBrchId()))
		{
			//���� ����� � ������ � CRWDI.. ���� �� ������ ... ���� � �� ������������, �������������� ����� ��������� GFL
			String cardExternalId = backRefInfoHelper.getCardExternalId(owner, card.getNumber());
			if (cardExternalId == null)
				throw new GateException("�� ������� ���������� � �� �� ����� " + card.getNumber());

			compositeId = EntityIdHelper.getCardOrAccountCompositeId(cardExternalId);
		}

		return compositeId;
	}

	/**
	 * ��������� ��p������ ���������� �� �����.
	 * @param account ����
	 * @param owner ��������
	 * @param backRefInfoRequestHelper ������ ������ � ���������� ���� � �����
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
		//���� ������ ��� � � �������������� ����������� ����������, �� ��������� ���������� ��� ���
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
	 * ��������� ��p������ ���������� �� �����.
	 * @param account ����
	 * @param residentBank ����
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
	 * ��������� ��p������ ���������� �� ���.
	 * @param imAccount ���
	 * @param owner ��������
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
	 * ��������� �������� Regular ����������� ������ �� ���������� ���������
	 * @param document ������ �� ��
	 * @return ����������� ���������.
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
