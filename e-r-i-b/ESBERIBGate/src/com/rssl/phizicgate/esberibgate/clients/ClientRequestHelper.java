package com.rssl.phizicgate.esberibgate.clients;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.claims.sbnkd.ClientInfoDocument;
import com.rssl.phizic.gate.claims.sbnkd.ConcludeEDBODocument;
import com.rssl.phizic.gate.claims.sbnkd.FullAddress;
import com.rssl.phizic.gate.clients.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.messaging.ClientRequestHelperBase;
import com.rssl.phizic.gate.clients.DocumentTypeComparator;
import com.rssl.phizicgate.esberibgate.types.MDMOperationType;
import com.rssl.phizicgate.esberibgate.ws.generated.*;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 @author Pankin
 @ created 29.09.2010
 @ $Author$
 @ $Revision$
 */
public class ClientRequestHelper extends ClientRequestHelperBase
{
	public static final String DELIMITER = "|";

	/**
	 * �����������
	 * @param factory - �������
	 */
	public ClientRequestHelper(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * �������� ������� ��� �������� ������� � ������� ������������ ����
	 * �  ��������� ��� ������� �� ������ ����� ������� (���)
	 * @param rbTbBranchId - ��� ���������������� �����, � ������� ������ ���� ���, �� ������� ������ �������� �������������
	 * @param cardNumber - ����� �����
	 * @return ������
	 */
	public IFXRq_Type createCustInqRq(String rbTbBranchId, String cardNumber)
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		CustInqRq_Type custInqRq = new CustInqRq_Type();
		custInqRq.setRqUID(generateUUID());
		custInqRq.setRqTm(generateRqTm());
		custInqRq.setOperUID(generateOUUID());
		custInqRq.setSPName(getSPName());
		custInqRq.setBankInfo(getBankInfo(rbTbBranchId, null));

		CardAcctId_Type cardAcctId = new CardAcctId_Type();
		cardAcctId.setCardNum(cardNumber);
		custInqRq.setCardAcctId(cardAcctId);

		ifxRq.setCustInqRq(custInqRq);
		return ifxRq;
	}

	/**
	 * �������� ������� ��� �������� ������� � ������� ������������ ����
	 * �  ��������� ��� ������� �� ���, ��� � ��� �������� �������
	 * @param rbTbBranchId - ��� ���������������� �����, � ������� ������ ���� ���, �� ������� ������ �������� �������������
	 * @param client - ������ �������
	 * @return ������
	 */
	public IFXRq_Type createCustInqRq(String rbTbBranchId, Client client)
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		CustInqRq_Type custInqRq = new CustInqRq_Type();
		custInqRq.setRqUID(generateUUID());
		custInqRq.setRqTm(generateRqTm());
		custInqRq.setOperUID(generateOUUID());
		custInqRq.setSPName(getSPName());
		custInqRq.setBankInfo(getBankInfo(rbTbBranchId, null));

		CustInfo_Type custInfo = new CustInfo_Type();

		List<? extends ClientDocument> documents = client.getDocuments();
		Collections.sort(documents, new DocumentTypeComparator());
		ClientDocument document =  documents.get(0);

		PersonInfo_Type personInfo = getPersonInfo(client, document, false, true);
		custInfo.setPersonInfo(personInfo);

		
		custInqRq.setCustInfo(custInfo);

		ifxRq.setCustInqRq(custInqRq);
		return ifxRq;
	}

	/**
	 * �������� ������� ��� �������� ������� � ������� ������������ ����
	 * �  ��������� ��� ������� �� ���, ��� � ��� �������� �������
	 * @param rbTbBranchId - ��� ���������������� �����, � ������� ������ ���� ���, �� ������� ������ �������� �������������
	 * @param document - ����� ������ �� ����������� ����.
	 * @return ������
	 */
	public IFXRq_Type createCustInqRq(String rbTbBranchId, ConcludeEDBODocument document)
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		CustInqRq_Type custInqRq = new CustInqRq_Type();
		custInqRq.setRqUID(generateUUID());
		custInqRq.setRqTm(generateRqTm());
		custInqRq.setOperUID(generateOUUID());
		custInqRq.setSPName(getSPName());
		custInqRq.setBankInfo(getBankInfo(rbTbBranchId, null));

		CustInfo_Type custInfo = new CustInfo_Type();

		//������� ��������� �������� ���������� � �������, ������� ����� �� ������ ����������
		PersonInfo_Type personInfo = new PersonInfo_Type();
		personInfo.setBirthday(getStringDate(document.getPersonBirthday()));

		PersonName_Type personName = new PersonName_Type();
		personName.setLastName(document.getPersonLastName());
		personName.setFirstName(document.getPersonFirstName());
		if (!StringHelper.isEmpty(document.getPersonMiddleName()))
			personName.setMiddleName(document.getPersonMiddleName());
		personInfo.setPersonName(personName);

		IdentityCard_Type identityCard = new IdentityCard_Type();
		identityCard.setIdType(document.getIdentityCardType());
	    identityCard.setIdNum(document.getIdentityCardSeries() + document.getIdentityCardNumber());

		//��� ��������� ���� ����������� ��� ����������
		identityCard.setIssuedBy(StringHelper.truncate(document.getIdentityCardIssuedBy(), 80));
		identityCard.setIssueDt(getStringDate(document.getIdentityCardIssueDate()));
		if (!StringHelper.isEmpty(document.getIdentityCardIssuedBy()))
			identityCard.setIssuedCode(document.getIdentityCardIssuedBy());
		identityCard.setExpDt(getStringDate(document.getIdentityCardExpDate()));

		personInfo.setIdentityCard(identityCard);

		personInfo.setBirthPlace(StringHelper.getEmptyIfNull(document.getPersonBirthplace()));
		personInfo.setTaxId(StringHelper.getEmptyIfNull(document.getPersonTaxId()));
		personInfo.setCitizenship(document.getPersonCitizenship());
		if (!StringHelper.isEmpty(document.getPersonGender()))
			personInfo.setGender(document.getPersonGender());
		personInfo.setResident(document.isPersonResident());

		personInfo.setContactInfo(null);

		custInfo.setPersonInfo(personInfo);

		custInqRq.setCustInfo(custInfo);

		ifxRq.setCustInqRq(custInqRq);
		return ifxRq;
	}

}
