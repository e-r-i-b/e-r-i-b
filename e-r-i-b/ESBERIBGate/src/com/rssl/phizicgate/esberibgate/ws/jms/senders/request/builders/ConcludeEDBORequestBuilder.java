package com.rssl.phizicgate.esberibgate.ws.jms.senders.request.builders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.claims.sbnkd.ConcludeEDBODocument;
import com.rssl.phizic.gate.claims.sbnkd.FullAddress;
import com.rssl.phizic.gate.claims.sbnkd.impl.ConcludeEDBODocumentImpl;
import com.rssl.phizic.gate.clients.ClientSex;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.operations.context.OperationContext;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.claim.sbnkd.ConcludeEDBODocumentSender;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.*;
import org.apache.commons.lang.StringUtils;

/**
 * Запрос на подключение УДБО.
 *
 * @author bogdanov
 * @ created 17.12.14
 * @ $Author$
 * @ $Revision$
 */

public class ConcludeEDBORequestBuilder extends RequestBuilderBase<ConcludeEDBORq, ConcludeEDBODocument>
{
	private static final String REQUEST_TYPE = "ConcludeEDBORq";
	public static final String CONCLUDE_EDBO_OPER_NAME = "SrvConcludeEDBO";
	public static final String GENDER_F = "Female";
	public static final String GENDER_M = "Male";

	public ConcludeEDBORequestBuilder(GateFactory factory)
	{
		super(factory);
	}

	public ConcludeEDBORq makeRequest(ConcludeEDBODocument claim) throws GateException, GateLogicException
	{
		ConcludeEDBORq request = new ConcludeEDBORq();

		request.setOperName(CONCLUDE_EDBO_OPER_NAME);
		String uuid = new RandomGUID().getStringValue();
		while (!ConcludeEDBODocumentSender.checkUuid(uuid, claim))
		{
			uuid = new RandomGUID().getStringValue();
		}
		if (!(claim instanceof ConcludeEDBODocumentImpl))
		{
			uuid = uuid.substring(0, 8) + StringUtils.leftPad(Long.toHexString(claim.getId()), 24, '0');
		}
		request.setRqUID(uuid);
		request.setRqTm(generateRqTm());
		request.setOperUID(OperationContext.getCurrentOperUID());
		request.setSPName(SPNameType.BP_ERIB);

		BankInfoType bankInfo = new BankInfoType();
		bankInfo.setBranchId(claim.getEDBOBranchId());
		bankInfo.setAgencyId(claim.getEDBOAgencyId());
		bankInfo.setRbTbBrchId(claim.getConvertedRbTbBranchId());
		request.setBankInfo(bankInfo);

		CustRec custRec = new CustRec();

		CustInfoType custInfo = new CustInfoType();

		PersonInfoType personInfo = new PersonInfoType();
		personInfo.setBirthday(getStringDate(claim.getPersonBirthday()));
		personInfo.setBirthPlace(claim.getPersonBirthplace());
		personInfo.setTaxId(claim.getPersonTaxId());
		personInfo.setCitizenship(claim.getPersonCitizenship());
		if(claim.getPersonGender().length() == 1)
		{
			personInfo.setGender(claim.getPersonGender().equals(ClientSex.GENDER_FEMALE)? GENDER_F : GENDER_M);
		}
		else
		{
			personInfo.setGender(claim.getPersonGender());
		}
		personInfo.setResident(claim.isPersonResident());

		PersonName personName = new PersonName();
		personName.setLastName(claim.getPersonLastName());
		personName.setFirstName(claim.getPersonFirstName());
		personName.setMiddleName(claim.getPersonMiddleName());
		personInfo.setPersonName(personName);

		IdentityCardType identityCard = new IdentityCardType();
		identityCard.setIdType(claim.getIdentityCardType());
		identityCard.setIdSeries(claim.getIdentityCardSeries());
		identityCard.setIdNum(claim.getIdentityCardNumber());
		identityCard.setIssuedBy(claim.getIdentityCardIssuedBy());
		identityCard.setIssueDt(getStringDate(claim.getIdentityCardIssueDate()));
		identityCard.setExpDt(getStringDate(claim.getIdentityCardExpDate()));
		personInfo.setIdentityCard(identityCard);
		custInfo.setPersonInfo(personInfo);
		custRec.setCustInfo(custInfo);
		request.setCustRec(custRec);

		ContactInfoType infoType = new ContactInfoType();

		for (com.rssl.phizic.gate.claims.sbnkd.ContactData cd : claim.getContactData())
		{
			ContactData cdt = new ContactData();
			cdt.setContactType(cd.getContactType().getXmlName());
			cdt.setContactNum(cd.getContactNum());
			cdt.setPhoneOperName(cd.getPhoneOperName());
			infoType.getContactDatas().add(cdt);
		}

		for (FullAddress fa : claim.getAddress())
		{
			FullAddressType postAddrType = new FullAddressType();
			postAddrType.setAddrType(fa.isRegistrationAddress() ? "1" : "2");
			postAddrType.setAddr3(fa.toString());
			infoType.getPostAddrs().add(postAddrType);
		}
		personInfo.setContactInfo(infoType);

		EDBOContractType eDBOContract = new EDBOContractType();

		bankInfo = new BankInfoType();
		bankInfo.setBranchId(claim.getEDBOBranchId());
		bankInfo.setAgencyId(claim.getEDBOAgencyId());
		eDBOContract.setBankInfo(bankInfo);
		eDBOContract.setMobilePhone(PhoneNumberFormat.SIMPLE_NUMBER.translate(claim.getPhone()));
		eDBOContract.setMobileOperator(StringHelper.isEmpty(claim.getEDBOPhoneOperator()) ? "МТС": claim.getEDBOPhoneOperator());
		String email = claim.getEmail();
		eDBOContract.setEmail(StringHelper.isNotEmpty(email) ? email : "0");
		request.setEDBOContract(eDBOContract);
		request.setNotSigned(claim.isGuest());
		if (!claim.isGuest())
			request.setCardNumber(claim.getLastLogonCardNumber());

		return request;
	}

	public String getRequestId(ConcludeEDBORq request)
	{
		return request.getRqUID();
	}

	public String getRequestMessageType()
	{
		return REQUEST_TYPE;
	}
}
