package com.rssl.phizicgate.esberibgate.ws.jms.senders.request.builders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.claims.sbnkd.*;
import com.rssl.phizic.gate.clients.ClientSex;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.operations.context.OperationContext;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.*;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.ContactData;

/**
 * Создатель запроса на получение информации по клиенту.
 *
 * @author bogdanov
 * @ created 17.12.14
 * @ $Author$
 * @ $Revision$
 */

public class GetPrivateClientRequestBuilder extends RequestBuilderBase<GetPrivateClientRq, ClientInfoDocument>
{
	private static final String REQUEST_TYPE = "GetPrivateClientRq";
	private static final String REGISTRATION_ADDRESS = "1";
	private static final String LIVING_ADDRESS = "2";

	public GetPrivateClientRequestBuilder(GateFactory factory)
	{
		super(factory);
	}

	public GetPrivateClientRq makeRequest(ClientInfoDocument claim) throws GateException, GateLogicException
	{
		GetPrivateClientRq request = new GetPrivateClientRq();

		request.setRqUID(new RandomGUID().getStringValue());
		request.setRqTm(generateRqTm());
		request.setOperUID(OperationContext.getCurrentOperUID());
		request.setSPName(SPNameType.BP_ERIB);

		BankInfoType bankInfoType = new BankInfoType();
		bankInfoType.setRbTbBrchId(claim.getRbTbBranch());
		request.setBankInfo(bankInfoType);

		CustRec custRec = new CustRec();

		CustInfoType custInfo = new CustInfoType();

		PersonInfoType personInfoType = new PersonInfoType();
		personInfoType.setBirthday(getStringDate(claim.getPersonBirthday()));
		personInfoType.setBirthPlace(claim.getPersonBirthplace());
		personInfoType.setTaxId(claim.getPersonTaxId());
		personInfoType.setCitizenship(claim.getPersonCitizenship());
		if(claim.getPersonGender().length() == 1)
		{
			personInfoType.setGender(claim.getPersonGender().equals(ClientSex.GENDER_FEMALE)? ConcludeEDBORequestBuilder.GENDER_F : ConcludeEDBORequestBuilder.GENDER_M);
		}
		else
		{
			personInfoType.setGender(claim.getPersonGender());
		}
		personInfoType.setResident(claim.isPersonResident());

		PersonName personNameType = new PersonName();
		personNameType.setLastName(claim.getPersonLastName());
		personNameType.setFirstName(claim.getPersonFirstName());
		personNameType.setMiddleName(claim.getPersonMiddleName());
		personInfoType.setPersonName(personNameType);

		IdentityCardType identityCardType = new IdentityCardType();
		identityCardType.setIdType(claim.getIdentityCardType());
		identityCardType.setIdSeries(claim.getIdentityCardSeries());
		identityCardType.setIdNum(claim.getIdentityCardNumber());
		identityCardType.setIssuedBy(claim.getIdentityCardIssuedBy());
		identityCardType.setIssuedCode(claim.getIdentityCardIssuedCode());
		identityCardType.setIssueDt(getStringDate(claim.getIdentityCardIssueDate()));
		identityCardType.setExpDt(getStringDate(claim.getIdentityCardExpDate()));
		personInfoType.setIdentityCard(identityCardType);
		custInfo.setPersonInfo(personInfoType);
		custRec.setCustInfo(custInfo);
		request.setCustRec(custRec);
		request.setVerified(claim.isVerified());

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
			postAddrType.setAddrType(fa.isRegistrationAddress() ? REGISTRATION_ADDRESS : LIVING_ADDRESS);
			postAddrType.setAddr3(fa.toString());
			infoType.getPostAddrs().add(postAddrType);
		}
		personInfoType.setContactInfo(infoType);

		return request;
	}

	public String getRequestId(GetPrivateClientRq request)
	{
		return request.getRqUID();
	}

	public String getRequestMessageType()
	{
		return REQUEST_TYPE;
	}
}
