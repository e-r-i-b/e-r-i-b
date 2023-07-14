package com.rssl.phizicgate.esberibgate.ws.jms.senders.request.builders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.claims.sbnkd.*;
import com.rssl.phizic.gate.claims.sbnkd.CardInfo;
import com.rssl.phizic.gate.clients.ClientSex;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.operations.context.OperationContext;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.*;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.ContactData;

/**
 * «апрос на создание договора на получение карты.
 *
 * @author bogdanov
 * @ created 17.12.14
 * @ $Author$
 * @ $Revision$
 */

public class CreateCardContractRequestBuilder extends RequestBuilderBase<CreateCardContractRq, CardInfo>
{
	private static final String REQUEST_TYPE = "CreateCardContractRq";
	private static final String REGISTRATION_ADDRESS = "1";
	private static final String LIVING_ADDRESS = "2";

	public CreateCardContractRequestBuilder(GateFactory factory)
	{
		super(factory);
	}

	public CreateCardContractRq makeRequest(com.rssl.phizic.gate.claims.sbnkd.CardInfo cardClaim) throws GateException, GateLogicException
	{
		CreateCardContractDocument claim = cardClaim.getParent();
		CreateCardContractRq request = new CreateCardContractRq();

		request.setRqUID(cardClaim.getUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(OperationContext.getCurrentOperUID());
		request.setSPName(SPNameType.BP_ERIB);

		BankInfoType bankInfo = new BankInfoType();
		bankInfo.setBranchId(claim.getContractBranchId());
		bankInfo.setAgencyId(claim.getConvertedAgencyId());
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
			personInfo.setGender(claim.getPersonGender().equals(ClientSex.GENDER_FEMALE)? ConcludeEDBORequestBuilder.GENDER_F : ConcludeEDBORequestBuilder.GENDER_M);
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
		request.setNotSigned(!claim.isGuest());

		ContactInfoType infoType = new ContactInfoType();

		for (com.rssl.phizic.gate.claims.sbnkd.ContactData cd : claim.getContactData())
		{
			if (cd == null)
				continue;

			ContactData cdt = new ContactData();
			cdt.setContactType(cd.getContactType().getXmlName());
			cdt.setContactNum(cd.getContactNum());
			infoType.getContactDatas().add(cdt);
		}

		for (FullAddress fa : claim.getAddress())
		{
			if (fa == null)
				continue;

			FullAddressType postAddrType = new FullAddressType();
			postAddrType.setAddrType(fa.isRegistrationAddress() ? REGISTRATION_ADDRESS : LIVING_ADDRESS);
			postAddrType.setAddr3(StringHelper.isEmpty(fa.getCity()) ? fa.getAfterSityAdress() : fa.toString());
			infoType.getPostAddrs().add(postAddrType);
		}
		personInfo.setContactInfo(infoType);

		CardContractType cardContract = new CardContractType();
		cardContract.setCardType(cardClaim.getContractCardType());
		cardContract.setClientCategory(cardClaim.getContractClientCategory());
		cardContract.setCurrency(cardClaim.getContractCurrency());
		bankInfo = new BankInfoType();
		bankInfo.setBranchId(claim.getContractBranchId());
		bankInfo.setAgencyId(claim.getConvertedAgencyId());
		cardContract.setBankInfo(bankInfo);
		cardContract.setEmbossedText(cardClaim.getContractEmbossedText().replaceFirst(" ", "/").toUpperCase() + "/");
		if (!cardClaim.getBonusInfoCode().equals("0"))
			cardContract.setBonusProgram(BonusProgramType.valueOf("BPT_"+cardClaim.getBonusInfoCode()));
		cardContract.setServiceTarif(ServiceTarifType.STT_BANK_TARIF);
		cardContract.setIsInsider(false);
		cardContract.setHasBIOData(cardClaim.getContractBIOData());
		cardContract.setIsPINEnvelope(cardClaim.getContractIsPin());
		cardContract.setIsOwner(cardClaim.getContractIsOwner());
		request.setCardContract(cardContract);

		return request;
	}

	public String getRequestId(CreateCardContractRq request)
	{
		return request.getRqUID();
	}

	public String getRequestMessageType()
	{
		return REQUEST_TYPE;
	}
}
