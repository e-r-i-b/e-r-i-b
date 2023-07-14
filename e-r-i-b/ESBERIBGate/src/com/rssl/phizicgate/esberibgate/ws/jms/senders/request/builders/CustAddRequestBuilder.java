package com.rssl.phizicgate.esberibgate.ws.jms.senders.request.builders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.claims.sbnkd.CustAddDocument;
import com.rssl.phizic.gate.claims.sbnkd.FullAddress;
import com.rssl.phizic.gate.clients.ClientSex;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.*;

/**
 * Запрос на подключение УДБО.
 *
 * @author bogdanov
 * @ created 17.12.14
 * @ $Author$
 * @ $Revision$
 */

public class CustAddRequestBuilder extends RequestBuilderBase<CustAddRq, CustAddDocument>
{
	private static final String REQUEST_TYPE = "CustAddRq";
	private static final String ADDRESS_TYPE_REGISTRATION = "Reg";
	private static final String ADDRESS_TYPE_LIVE = "Live";
	private static final String ADDRESS_TYPE_WORK = "Work";
	private static final String PHONE_TYPE_MOBILE = "Mobile";

	public CustAddRequestBuilder(GateFactory factory)
	{
		super(factory);
	}

	public CustAddRq makeRequest(CustAddDocument claim) throws GateException, GateLogicException
	{
		CustAddRq request = new CustAddRq();

		request.setRqUID(new RandomGUID().getStringValue());
		request.setRqTm(generateRqTm());
		request.setSPName(SPNameType.BP_ERIB);

		BankInfoType bankInfo = new BankInfoType();
		bankInfo.setRbTbBrchId(claim.getConvertedRbTbBranchId());
		bankInfo.setRegionId(claim.getConvertedRegionId());
		bankInfo.setAgencyId(claim.getConvertedAgencyId());
		bankInfo.setBranchId(claim.getConvertedBranchId());
		request.setBankInfo(bankInfo);

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
		request.setCustInfo(custInfo);

		ContactInfoType infoType = new ContactInfoType();

		PhoneNumType phone = new PhoneNumType();
		phone.setPhone(claim.getPhone());
		phone.setPhoneType(PHONE_TYPE_MOBILE);
		infoType.getPhoneNa().add(phone);

		int i = 0;
		for (FullAddress address : claim.getAddress())
		{
			FullAddressIssueCardType fullAddressType = new FullAddressIssueCardType();
			fullAddressType.setAddressType(getAddressType(i));
			fullAddressType.setPostalCode(address.getPostalCode());
			fullAddressType.setCountry(address.getCountry());
			fullAddressType.setRegion(address.getRegion());
			fullAddressType.setCity(address.getCity());
			fullAddressType.setAfterCityAdress(address.getAfterSityAdress());

			infoType.getFullAddresses().add(fullAddressType);
			i++;
		}
		infoType.setEmailAddr(claim.getEmail());
		personInfo.setContactInfo(infoType);

		return request;
	}

	public String getRequestId(CustAddRq request)
	{
		return request.getRqUID();
	}

	public String getRequestMessageType()
	{
		return REQUEST_TYPE;
	}

	/**
	 * Получить тип адреса по его порядковому номеру (на данный момент в заявке: 0 - адрес проживания, 1 - адрес регистрации
	 * @param number - индекс адреса в массиве адресов
	 * @return тип адреса
	 */
	private String getAddressType(int number)
	{
		if(number > 0)
			return number == 1 ? ADDRESS_TYPE_REGISTRATION : ADDRESS_TYPE_WORK;
	    else
			return ADDRESS_TYPE_LIVE;
	}
}
