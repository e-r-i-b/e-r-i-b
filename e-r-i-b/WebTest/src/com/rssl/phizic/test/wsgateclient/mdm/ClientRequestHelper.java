package com.rssl.phizic.test.wsgateclient.mdm;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.test.wsgateclient.mdm.generated.*;
import com.rssl.phizic.utils.RandomHelper;
import com.rssl.phizic.utils.PassportTypeWrapper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;

import java.util.Random;
import java.util.Set;

/**
 * @author egorova
 * @ created 18.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class ClientRequestHelper extends RequestHelperBase
{
	private static final String CONTACT_TYPE_MOBILE_PHONE = "мобильный телефон";
	private static final String ERIB_ID = "urn:sbrfsystems:40-erib";

	public MDMClientInfoUpdateRq_Type getClientInfo(ActivePerson person) throws GateException
	{
		try
		{
			CustRec_Type custRec = new CustRec_Type();
			//Здесь не оч понятно какой идентификатор			
			custRec.setCustId(person.getClientId());
			CustInfo_Type custInfo = new CustInfo_Type();
			custInfo.setEffDt(XMLDatatypeHelper.formatDateTimeWithoutTimeZone(person.getLastUpdateDate()));
			
			PersonInfo_Type personInfo = getPersonInfo(person);
			custInfo.setPersonInfo(personInfo);

			IntegrationInfo_Type integrationInfo = new IntegrationInfo_Type();
			integrationInfo.setIntegrationID(new IntegrationInfo_TypeIntegrationID(ERIB_ID, person.getId().toString()));
			custInfo.setIntegrationInfo(integrationInfo);

			custRec.setCustInfo(custInfo);

			ServiceInfo_Type serviceInfo = new ServiceInfo_Type();
			serviceInfo.setRegionNum("mock");
			serviceInfo.setBranchNum("mock");
			serviceInfo.setAgencyNum("mock");			
			serviceInfo.setProdType("COD");
			serviceInfo.setAgreementNum(person.getAgreementNumber());
			if (person.getAgreementDate() != null)
				serviceInfo.setStartDt(XMLDatatypeHelper.formatDateWithoutTimeZone(person.getAgreementDate()));
			if (person.getProlongationRejectionDate() != null)
				serviceInfo.setStartDt(XMLDatatypeHelper.formatDateWithoutTimeZone(person.getProlongationRejectionDate()));
			custRec.setServiceInfo(serviceInfo);

			return new MDMClientInfoUpdateRq_Type(generateUUID(),
							getRqTm(), generateOUUID(), SPName_Type.BP_ERIB,
							getBankInfo(null, person.getLogin().getLastLogonCardDepartment()), OperationType.CustModNf.name(), custRec);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	private PersonInfo_Type getPersonInfo(ActivePerson person) throws BusinessException
	{
		PersonInfo_Type personInfo = new PersonInfo_Type();

		personInfo.setBirthday(XMLDatatypeHelper.formatDateWithoutTimeZone(person.getBirthDay()));
		personInfo.setBirthPlace(person.getBirthPlace());
		personInfo.setTaxId(person.getInn());
		//Имитируем то, что данные изменились.		
		personInfo.setCitizenship(RandomHelper.rand(2));

		personInfo.setPersonName(getPersonName(person));
		personInfo.setIdentityCard(getIdentityCard(person));
		personInfo.setContactInfo(getContactInfo(person));
		return personInfo;
	}

	private PersonName_Type getPersonName(ActivePerson person)
	{
		PersonName_Type personName = new PersonName_Type();
		personName.setFirstName(person.getFirstName());
		personName.setLastName(person.getSurName());
		personName.setMiddleName(person.getPatrName());
		return personName;
	}

	private IdentityCard_Type getIdentityCard(ActivePerson person) throws BusinessException
	{
		IdentityCard_Type identityCard = new IdentityCard_Type();

		PersonDocument document = findIdentifyDocument(person.getPersonDocuments());
		identityCard.setIdType(PassportTypeWrapper.getPassportType(PersonDocumentType.valueFrom(document.getDocumentType())));
		identityCard.setIdSeries(document.getDocumentSeries());
		identityCard.setIdNum(document.getDocumentNumber());
		return identityCard;
	}

	private PersonDocument findIdentifyDocument(Set<PersonDocument> personDocuments) throws BusinessException
	{
		for (PersonDocument personDocument : personDocuments)
		{
			if (personDocument.isDocumentIdentify())
				return personDocument;
		}
		throw new BusinessException("Ошибка получения документов пользователя");
	}

	private ContactInfo_Type getContactInfo(ActivePerson person)
	{
		ContactInfo_Type contactInfo = new ContactInfo_Type();

		Random r = new Random(1);
		if (r.nextBoolean())
		{
			FullAddress_Type address = new FullAddress_Type();
			address.setAddrType("1"); //код адреса регистрации
			address.setAddr3("г.Москва, ул. Энгельса, д.12, корп.54, кв. " + RandomHelper.rand(3, RandomHelper.DIGITS));
			contactInfo.setPostAddr(new FullAddress_Type[]{address});
		}

		ContactData_Type contactData = new ContactData_Type();
		contactData.setContactType(CONTACT_TYPE_MOBILE_PHONE);
		//Если нет телефона, не возвращаем контактную информацию, иначе падает (на самом деле мобильный должен быть, но у нас не всегда так)
		if (person.getMobilePhone() == null)
			return contactInfo;

		contactData.setContactNum(person.getMobilePhone());
		contactData.setPhoneOperName(person.getMobileOperator());
		contactInfo.setContactData(new ContactData_Type[]{contactData});
		return contactInfo;
	}
}
