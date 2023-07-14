package com.rssl.phizicgate.esberibgate.messaging;

import com.rssl.phizgate.common.services.bankroll.ExtendedCodeGateImpl;
import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.clients.DocumentTypeComparator;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.payments.longoffer.*;
import com.rssl.phizic.gate.utils.EntityCompositeId;
import com.rssl.phizic.utils.PassportTypeWrapper;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizicgate.esberibgate.utils.CardOrAccountCompositeId;
import com.rssl.phizicgate.esberibgate.ws.generated.*;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 @author Pankin
 @ created 14.09.2010
 @ $Author$
 @ $Revision$
 */
public class RequestHelperBase extends AbstractService
{
	// � ������ ������� ���� "��� ������" ���������� 80 ���������
	// TODO: � ����� ������� ����������� �������� �� 255
	public static final int DOCUMENT_ISSUEBY_MAXLEN = 80;
	private static final Set<Class> autoSubClasses = new HashSet<Class>();

	private final BackRefInfoRequestHelper backRefInfoRequestHelper;

	static
	{
		autoSubClasses.add(EmployeeCardPaymentSystemPaymentLongOffer.class);
		autoSubClasses.add(EmployeeCloseCardPaymentSystemPaymentLongOffer.class);
		autoSubClasses.add(EmployeeDelayCardPaymentSystemPaymentLongOffer.class);
		autoSubClasses.add(EmployeeEditCardPaymentSystemPaymentLongOffer.class);
		autoSubClasses.add(EmployeeRecoveryCardPaymentSystemPaymentLongOffer.class);
	}

	public RequestHelperBase(GateFactory factory)
	{
		super(factory);
		backRefInfoRequestHelper = new BackRefInfoRequestHelper(factory);
	}

	/**
	 * �������� UUID ���������� �� ���� Narrow Character � ������������ ����� ����������������� ����� �  ������ 32 �������.
	 * �������� ������ ��������������� ����������� ��������� �[0-9a-fA-F]{32}�.  �������� �� ������ ��������� �������.
	 * ������: 1234567890abcdef1234567890abcdef
	 * @return UUID
	 */
	public static String generateUUID()
	{
		return new RandomGUID().getStringValue();
	}

	/**
	 * �������� OUUID ���������� �� ���� Narrow Character � ������������ ����� ����������������� ����� �  ������ 32 �������.
	 * �������� ������ ��������������� ����������� ��������� �[0-9a-fA-F]{32}�.  �������� �� ������ ��������� �������.
	 * ��� ���� �������� ������ ���������� � AA
	 * ������: AA34567890abcdef1234567890abcdef
	 * @return OUUID
	 */
	public static String generateOUUID()
	{
		return "AA" + new RandomGUID().getStringValue().substring(2);
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

	protected static String getStringDateTime(Calendar date)
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
	 * ���������� ��������� BankInfo
	 * @param office ���� ��� ���������� ������� �������, ���������, ��������
	 * @param bankCode ����� ���, �������� ���� �����, �� ������� ������������� ������
	 * @param rbBrchId ����� ���, �������� �������, ��� �������� ��������� BankInfo
	 * @return BankInfo_Type
	 */
	public BankInfo_Type getBankInfo(Office office, String bankCode, String rbBrchId)
	{
		BankInfo_Type bankInfo = new BankInfo_Type();
		if (office != null)
		{
			return getBankInfo(office.getCode(), bankCode, rbBrchId);
		}
		if (bankCode != null)
		{
			bankInfo.setRbTbBrchId(bankCode);
		}
		if (rbBrchId != null)
		{
			bankInfo.setRbBrchId(rbBrchId);
		}
		return bankInfo;
	}

	/**
	 * ���������� ��������� BankInfo
	 * @param officeCode ��� ����� ��� ���������� ������� �������, ���������, ��������
	 * @param bankCode ����� ���, �������� ���� �����, �� ������� ������������� ������
	 * @param rbBrchId ����� ���, �������� �������, ��� �������� ��������� BankInfo
	 * @return BankInfo_Type
	 */
	public BankInfo_Type getBankInfo(Code officeCode, String bankCode, String rbBrchId)
	{
		BankInfo_Type bankInfo = new BankInfo_Type();
		if (officeCode != null)
		{
			ExtendedCodeGateImpl code = new ExtendedCodeGateImpl(officeCode);
			//� ������ �������, WAY ���������� � ��� 4 ������� ���������� ����. ��������� ������������.
			bankInfo.setBranchId(StringHelper.appendLeadingZeros(code.getOffice(), 4)); //����� �������
			bankInfo.setAgencyId(StringHelper.appendLeadingZeros(code.getBranch(), 4)); //����� ���������
			bankInfo.setRegionId(StringHelper.appendLeadingZeros(code.getRegion(), 3)); //����� ��������
		}
		if (bankCode != null)
		{
			bankInfo.setRbTbBrchId(bankCode);
		}
		if (rbBrchId != null)
		{
			bankInfo.setRbBrchId(rbBrchId);
		}
		return bankInfo;
	}

	/**
	 * ���������� ��������� BankInfo
	 * @param bankCode ����� ���, �������� ���� �����, �� ������� ������������� ������
	 * @param rbBrchId ����� ���, �������� �������, ��� �������� ��������� BankInfo
	 * @return BankInfo_Type
	 */
	public static BankInfo_Type getBankInfo(String bankCode, String rbBrchId)
	{
		BankInfo_Type bankInfo = new BankInfo_Type();
		if (bankCode != null)
		{
			bankInfo.setRbTbBrchId(StringHelper.appendLeadingZeros(bankCode,8));
		}
		if (rbBrchId != null)
		{
			bankInfo.setRbBrchId(rbBrchId);
		}
		return bankInfo;
	}

	/**
	 * ���������� ��������� BankInfo
	 * @param compositeId ����������� ������������� ����� ��� �����
	 * @param bankCode ����� ���, �������� ���� �����, �� ������� ������������� ������
	 * @param needOffice ������� ����, ��� ����� ��������� ��������� ���������� � �������������, ������� ����� ��� ����
	 * @return BankInfo_Type 
	 */
	public BankInfo_Type getBankInfo(CardOrAccountCompositeId compositeId, String bankCode, boolean needOffice)
	{
		BankInfo_Type bankInfo = new BankInfo_Type();
		if (bankCode != null)
			bankInfo.setRbTbBrchId(bankCode);
		if (needOffice)
		{
			if (compositeId.getBranchId() != null)
				bankInfo.setBranchId(StringHelper.appendLeadingZeros(compositeId.getBranchId(), 4));
			if (compositeId.getRegionId() != null)
				bankInfo.setRegionId(StringHelper.appendLeadingZeros(compositeId.getRegionId(), 3));
			if (compositeId.getAgencyId() != null)
				bankInfo.setAgencyId(StringHelper.appendLeadingZeros(compositeId.getAgencyId(), 4));
		}
		if (compositeId.getRbBrchId() != null)
			bankInfo.setRbBrchId(compositeId.getRbBrchId());

		return bankInfo;
	}

	protected AcctType_Type[] getAcctType(List<BankProductType> productTypes)
	{
		if (CollectionUtils.isEmpty(productTypes))
			return null;

		int i = 0;
		AcctType_Type[] bankProductTypeValues = new AcctType_Type[productTypes.size()];
		for (BankProductType productType : productTypes)
		{
			String productName = BankProductType.CardWay == productType ? BankProductType.Card.name() : productType.name();
			bankProductTypeValues[i] = AcctType_Type.fromString(productName);
			i++;
		}
		return bankProductTypeValues;
	}

	protected AcctType_Type[] getAcctType(BankProductType... acctType)
	{
		if (acctType == null || acctType.length == 0)
			return null;
		AcctType_Type[] bankProductTypeValues = new AcctType_Type[acctType.length];
		for (int i=0; i< acctType.length; i++)
		{
			bankProductTypeValues[i] = AcctType_Type.fromString(acctType[i].toString());
		}
		return bankProductTypeValues;
	}

	/*
	���������� �������� ���������� ��� �������
	 */
	protected BankAcctInqRq_Type getMainBankAcctInqRq()
	{
		BankAcctInqRq_Type bankAcctInqRq = new BankAcctInqRq_Type();
		bankAcctInqRq.setRqUID(generateUUID());
		bankAcctInqRq.setRqTm(generateRqTm());
		bankAcctInqRq.setOperUID(generateOUUID());
		bankAcctInqRq.setSPName(getSPName());
		return bankAcctInqRq;
	}

	/*
		���������� ���������
	*/
	protected IdentityCard_Type getDocument(ClientDocument document, boolean isForCEDBO)
	{
		IdentityCard_Type identityCard = new IdentityCard_Type();
		identityCard.setIdType(PassportTypeWrapper.getPassportType(document.getDocumentType()));
		// ��� ���� �����������, ����� CEDBO, ����� �������� way ������ ������������ � ���� "�����" ��� ����
		if (document.getDocumentType() == ClientDocumentType.PASSPORT_WAY)
		{
			if (isForCEDBO)
            {
                if (document.getDocSeries() != null)
				    identityCard.setIdSeries(document.getDocSeries().replace(" ", ""));
            }
			else
				identityCard.setIdNum(StringHelper.getEmptyIfNull(document.getDocSeries()) + StringHelper.getEmptyIfNull(document.getDocNumber()));
		}
		else
		{
			//���� ��������. ���������� ����� �������� ����������� �������� � ��������.
			if ((document.getDocumentType() == ClientDocumentType.REGULAR_PASSPORT_RF) && document.getDocSeries() != null  && document.getDocSeries().matches("\\d{4}"))
			{
				identityCard.setIdSeries(document.getDocSeries().substring(0, 2) + " " + document.getDocSeries().substring(2, 4));
			}
			else
			{
				identityCard.setIdSeries(document.getDocSeries());
			}
			identityCard.setIdNum(document.getDocNumber());
		}
		//��� ��������� ���� ����������� ��� ����������
		identityCard.setIssuedBy(
				StringHelper.truncate(document.getDocIssueBy(), DOCUMENT_ISSUEBY_MAXLEN));
		identityCard.setIssueDt(document.getDocIssueDate() == null ? null : getStringDate(document.getDocIssueDate()));
		if (!StringHelper.isEmpty(document.getDocIssueByCode()))
			identityCard.setIssuedCode(document.getDocIssueByCode());
		identityCard.setExpDt(getStringDate(document.getDocTimeUpDate()));
		return identityCard;
	}

	protected SPName_Type getSPName()
	{
		return SPName_Type.BP_ERIB;
	}

	/**
	 * ��������� RbTbBrch ����� loginId �������
	 * @param loginId �������
	 * @return ������� ��� ������ ��� ������ ���������������� ����� � ������� RbTbBrch � ��� ��-�������� ��������� �������� ��� ��.
	 */
	public String getRbTbBrch(Long loginId) throws GateLogicException, GateException
	{
		return backRefInfoRequestHelper.getRbTbBrch(loginId);
	}

	/**
	 * ��������� RbTbBrch ����� ��������� ������������� ���������� ������������ ��������
	 * @param compositeId ��������� ������������� ���������� ������������ ��������
	 * @return ������� ��� ������ ��� ������ ���������������� ����� � ������� RbTbBrch � ��� ��-�������� ��������� �������� ��� ��.
	 */
	public String getRbTbBrch(EntityCompositeId compositeId) throws GateLogicException, GateException
	{
		return getRbTbBrch(compositeId.getLoginId());
	}

	/**
	 * ������������ �������� BankInfo c ����������� � �����, � ������� ������ ���� ���,
	 * �� ������� ������ ������ ��������������.
	 * @param loginId ������������ ������ �������
	 * @return BankInfo
	 */
	public BankInfo_Type createAuthBankInfo(Long loginId) throws GateLogicException, GateException
	{
		return createAuthBankInfo(getRbTbBrch(loginId));
	}

	/**
	 * ������������ �������� BankInfo c ����������� � �����, � ������� ������ ���� ���,
	 * �� ������� ������ ������ ��������������.
	 * @param rbTbBrch - rbTbBrch
	 * @return BankInfo
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public  BankInfo_Type createAuthBankInfo(String rbTbBrch) throws GateLogicException, GateException
	{
		return new BankInfo_Type(null, null, null, rbTbBrch, null);
	}

	public CustRec_Type createCustRec(Client client) throws GateException, GateLogicException
	{
		CustRec_Type custRec = new CustRec_Type();
		custRec.setCustInfo(createCustInfoType(client));
		return custRec;
	}

	public CustInfo_Type createCustInfoType(Client client) throws GateException
	{
		CustInfo_Type custInfo = new CustInfo_Type();

		PersonInfo_Type personInfo = new PersonInfo_Type();
		personInfo.setBirthday(getStringDate(client.getBirthDay()));
		PersonName_Type personName = new PersonName_Type();
		personName.setLastName(client.getSurName());
		personName.setFirstName(client.getFirstName());
		personName.setMiddleName(client.getPatrName());
		personInfo.setPersonName(personName);
		IdentityCard_Type identityCard = new IdentityCard_Type();

		List<? extends ClientDocument> documents = client.getDocuments();
		if (documents.isEmpty())
			throw new GateException("�� ������ �������� ������� id=" + client.getId());

		//��������� ��������� �������.
		Collections.sort(documents, new DocumentTypeComparator());
		ClientDocument document = documents.get(0);

		if (StringHelper.isEmpty(document.getDocNumber()))
			throw new GateException("������������ �������� ������� id=" + client.getId());

		identityCard.setIdSeries(document.getDocSeries());
		identityCard.setIdNum(document.getDocNumber());
		identityCard.setIdType(PassportTypeWrapper.getPassportType(document.getDocumentType()));
		identityCard.setIssuedBy(document.getDocIssueBy());
		identityCard.setIssuedCode(document.getDocIssueByCode());
		identityCard.setIssueDt(getStringDate(document.getDocIssueDate()));
		identityCard.setExpDt(getStringDate(document.getDocTimeUpDate()));

		personInfo.setIdentityCard(identityCard);
		custInfo.setPersonInfo(personInfo);
		return custInfo;
	}

	/**
	 * ������������� ���� ����
	 * ���� ������������ �� ��������� � ����(��������� ���� � �� ��������� �� ����) � ������� ���������� �� ��� ���������� �� ����,
	 * bankInfo ��������� �� ������������� ����������, ���������� ������.
	 * @param payment
	 */
	public BankInfo_Type generateBankInfo(GateDocument payment) throws GateLogicException, GateException
	{
		//���� rbTbBranch �� ������� �� ���������� ����� ������� � ������������ ������ �������� ������� �� ���������� ����������� �����������.
		if (getRbTbBrch(payment.getInternalOwnerId()) == null && autoSubClasses.contains(payment.getType()))
		{
			return new BankInfo_Type(null, null, null, generateRbTbBrchId(payment.getOffice()), null);
		}
		return createAuthBankInfo(payment.getInternalOwnerId());
	}

	/**
	 * ���������� RbTbBrchId �� ��
	 * @param office �������������
	 * @return RbTbBrchId
	 */
	public String generateRbTbBrchId(Office office)
	{
		if (office == null)
			return null;

		String rbTbBranch = (new ExtendedCodeGateImpl(office.getCode())).getRegion() + "000000";
		return StringHelper.appendLeadingZeros(rbTbBranch, 8);
	}

	/**
	 * �������� ��������� ��������� �� ������ ������� � �������(����� BackRefClientService)
	 * @param document ��������
	 * @return ��������
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public Client getBusinessOwner(GateDocument document) throws GateLogicException, GateException
	{
		return backRefInfoRequestHelper.getBusinessOwner(document);
	}

	/**
	 * �������� ����� �� ������
	 * @param client �������� ��� ��������� �������
	 * @param number ����� �����
	 * @param office ����
	 * @return �����
	 */
	public Card getCard(Client client, String number, Office office) throws GateException, GateLogicException
	{
		return backRefInfoRequestHelper.getCard(client, number, office);
	}

	/**
	 * �������� ���� �� ������
	 * @param number ����� �����
	 * @param office ����
	 * @return ����
	 */
	public Account getAccount(String number, Office office) throws GateException, GateLogicException
	{
		return backRefInfoRequestHelper.getAccount(number, office);
	}

	protected BackRefInfoRequestHelper getBackRefInfoRequestHelper()
	{
		return backRefInfoRequestHelper;
	}
}
