package com.rssl.phizicgate.esberibgate.ws.jms.senders.request.builders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.claims.sbnkd.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.operations.context.OperationContext;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.*;
import com.rssl.phizicgate.esberibgate.claim.sbnkd.IssueCardClaimProcessor;

/**
 * «апрос на выполнение за€вки на выпуск карты.
 *
 * @author bogdanov
 * @ created 15.12.14
 * @ $Author$
 * @ $Revision$
 */

public class IssueCardClaimRequestBuilder extends RequestBuilderBase<IssueCardRq, com.rssl.phizic.gate.claims.sbnkd.CardInfo>
{
	private static final String REQUEST_TYPE = "IssueCardRq";
	private static final String OPERNAME = "PersonalCardESB";

	private static final String UN_GUEST = "guest";
	private static final String UN_CLIENT = "client";
	private static final String ADDRESS_TYPE = "I";
	private static final String TARIFF_TYPE_CODE = "CARD_PROD";
	private static final String TARIFF_TYPE_VID = "Card";

	public IssueCardClaimRequestBuilder(GateFactory factory)
	{
		super(factory);
	}

	public IssueCardRq makeRequest(com.rssl.phizic.gate.claims.sbnkd.CardInfo cardClaim) throws GateException, GateLogicException
	{

		IssueCardClaim claim = cardClaim.getParent();
		IssueCardRq request = new IssueCardRq();
		request.setRqUID(IssueCardClaimProcessor.getIt().buildUidForIssueCardRq(cardClaim.getUID()));
		request.setRqTm(generateRqTm());
		request.setOperUID(OperationContext.getCurrentOperUID());
		request.setSPName(SPNameType.BP_ERIB);
		request.setSystemId("urn:sbrfsystems:99-way");
		request.setUserName(claim.isGuest() ? UN_GUEST : UN_CLIENT);
		request.setOperName(OPERNAME);;

		BankInfoType bankInfo = new BankInfoType();
		bankInfo.setRegionId(claim.getConvertedRegionId());
		bankInfo.setAgencyId(claim.getConvertedAgencyId());
		bankInfo.setBranchId(claim.getConvertedBranchId());
		bankInfo.setRbTbBrchId(claim.getConvertedRbTbBranchId());

		request.setBankInfo(bankInfo);

		CardAcctIdType cardAcctId = new CardAcctIdType();

		CustInfoType custInfo = new CustInfoType();

		PersonInfoType personInfo = new PersonInfoType();
		PersonName personName = new PersonName();
		personName.setLastName(claim.getPersonLastName());
		personName.setFirstName(claim.getPersonFirstName());
		personName.setMiddleName(claim.getPersonMiddleName());
		personInfo.setPersonName(personName);

		IdentityCardType identityCard = new IdentityCardType();
		identityCard.setIdSeries(claim.getIdentityCardSeries());
		identityCard.setIdNum(claim.getIdentityCardNumber());
		personInfo.setIdentityCard(identityCard);

		ContactInfoType contactInfo = new ContactInfoType();

		for (FullAddress address : claim.getAddress())
		{
			if (address == null)
				continue;

			FullAddressIssueCardType fullAddressType = new FullAddressIssueCardType();
			fullAddressType.setAddressType(ADDRESS_TYPE);
			fullAddressType.setPostalCode(address.getPostalCode());
			fullAddressType.setCountry(address.getCountry());
			fullAddressType.setRegion(address.getRegion());
			fullAddressType.setCity(address.getCity());
			fullAddressType.setAfterCityAdress(address.getAfterSityAdress());

			contactInfo.getFullAddresses().add(fullAddressType);
		}

		personInfo.setContactInfo(contactInfo);
		custInfo.setPersonInfo(personInfo);
		cardAcctId.setCustInfo(custInfo);

		ContractType contract = new ContractType();
		contract.setProductCode(cardClaim.getContractProductCode());
		cardAcctId.setContract(contract);
		cardAcctId.setProductCode(cardClaim.getCardAcctProductCode());
		cardAcctId.setCreditLimit(cardClaim.getCardAcctCreditLimit());

		cardAcctId.setBankInfo(bankInfo);
		PlasticInfoType plasticInfo = new PlasticInfoType();
		String contractEmbossedText = claim.getContractEmbossedText();
		int i = contractEmbossedText.indexOf(" ");
		plasticInfo.setFirstName(contractEmbossedText.substring(0, i).trim());
		plasticInfo.setLastName(contractEmbossedText.substring(i, contractEmbossedText.length()).trim());

		cardAcctId.setPlasticInfo(plasticInfo);
		request.setCardAcctId(cardAcctId);

		CardAcctInfoType cardAcctInfo = new CardAcctInfoType();
		cardAcctInfo.setPinPack(cardClaim.getCardAcctPinPack());
		if (cardClaim.isFirstCard())
		{
			if(cardClaim.getParent().getCardAcctAutoPayInfo() != 0)
				cardAcctInfo.setAutoPayInfo(String.valueOf(cardClaim.getParent().getCardAcctAutoPayInfo()));
		}

		MBCInfoType mBCInfo = new MBCInfoType();
		mBCInfo.setStatus(cardClaim.getMBCStatus());
		mBCInfo.setContractType(cardClaim.getMBCContractType());

		PhoneNumType phoneNumber = new PhoneNumType();
		phoneNumber.setPhone(cardClaim.getMBCPhone());
		phoneNumber.setPhoneCode(cardClaim.getMBCPhoneCode());
		mBCInfo.setPhoneNum(phoneNumber);
		cardAcctInfo.setMBCInfo(mBCInfo);
		cardAcctInfo.setCardOrderNum(cardClaim.getCardNumber());
		cardAcctInfo.setCardOrderDate(cardClaim.getCardAcctCardOrderDate());
		cardAcctInfo.setRiskFactor(cardClaim.getCardAcctRiskFactor());

		TarifUnionType tariff = new TarifUnionType();
		TariffClassifierType tariffClassifierType = new TariffClassifierType();
		tariffClassifierType.setCode(TARIFF_TYPE_CODE);
		tariffClassifierType.setValue(cardClaim.getCardAcctProductCode());
		tariffClassifierType.setVid(TARIFF_TYPE_VID);
		tariff.getTariffClassifiers().add(tariffClassifierType);
		cardAcctInfo.getTarifves().add(tariff);

		BonusInfoType bonusInfo = new BonusInfoType();
		bonusInfo.setBonusCode(cardClaim.getBonusInfoCode());
		bonusInfo.setFieldValue(cardClaim.getBonusInfoValue());
		cardAcctInfo.setBonusInfo(bonusInfo);
		request.setCardAcctInfo(cardAcctInfo);

		return request;
	}

	public String getRequestId(IssueCardRq request)
	{
		return request.getRqUID();
	}

	public String getRequestMessageType()
	{
		return REQUEST_TYPE;
	}
}
