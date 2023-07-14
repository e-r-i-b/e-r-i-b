package com.rssl.phizic.ws.esberiblistener.mdm;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.login.LoginHelper;
import com.rssl.phizic.business.messaging.info.PersonalSubscriptionData;
import com.rssl.phizic.business.messaging.info.SubscriptionService;
import com.rssl.phizic.business.persons.*;
import com.rssl.phizic.business.userDocuments.DocumentType;
import com.rssl.phizic.business.userDocuments.UserDocumentService;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.utils.PhoneNumberUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.ws.esberiblistener.mdm.generated.*;
import com.rssl.phizic.ws.esberiblistener.mdm.types.ContactType;
import com.rssl.phizic.ws.esberiblistener.mdm.types.PassportTypeWrapper;
import com.rssl.phizic.person.Address;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.person.PersonDocument;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

/**
 * @author egorova
 * @ created 25.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class ClientResponseSerializer
{
	private static final PersonService personService = new PersonService();
	private static final DepartmentService departmentService = new DepartmentService();

	private static final String ERIB_ID = "urn:sbrfsystems:40-erib";

	/**
	 * ��������� �������� ��������� � ������� ���������� ������ �������
	 * @param client - ������, ��������� �� ��� ��� ����������
	 * @return ����� � ������� ���������� ������� � ����'�
	 */
	public IFXRs_Type updatePerson(MDMClientInfoUpdateRq_Type client)
	{

		MDMClientInfoUpdateRs_Type mdmRs = new MDMClientInfoUpdateRs_Type();
		//��������� ��������� ������ �������
		mdmRs.setRqUID(client.getRqUID());
		mdmRs.setRqTm(getRqTm());
		mdmRs.setOperUID(client.getOperUID());
		//���� ������� � ����
		CustRec_Type custRec = client.getCustRec();
		CustInfo_Type custInfo = custRec.getCustInfo();
		IntegrationInfo_TypeIntegrationID integrationInfoId = custInfo.getIntegrationInfo().getIntegrationID();
		if (!integrationInfoId.getISCode().equalsIgnoreCase(ERIB_ID))
		{
			return getResponse(-3, "������� �� ���������� ��� �������. ������� : " + integrationInfoId.getISCode() + ". ��������� " + ERIB_ID, mdmRs);
		}
		try
		{
			Long clientId = Long.parseLong(integrationInfoId.getISCustId());
			ActivePerson person = (ActivePerson) personService.findById(clientId);
			if (person == null)
			{
				return getResponse(-2, "������ c ID = " + clientId + " �� ���������������.", mdmRs);
			}

			//��������� �������
			updatePersonData(person, custRec);
		}
		catch (BusinessException e)
		{
			return getResponse(-1, e.getMessage(), mdmRs);
		}
		return getResponse(0, null, mdmRs);
	}

	private IFXRs_Type getResponse(long statusCode, String statusDesc, MDMClientInfoUpdateRs_Type mdmRs)
	{
		Status_Type status = new Status_Type();
		status.setStatusCode(statusCode);
		if (!StringHelper.isEmpty(statusDesc))
			status.setStatusDesc(StringHelper.truncate(statusDesc, 255));
		mdmRs.setStatus(status);
		//��������� ��������� ���������
		IFXRs_Type rs = new IFXRs_Type();
		rs.setMDMClientInfoUpdateRs(mdmRs);
		return rs;
	}

	private static Calendar parseDate(String date)
	{
		if (StringHelper.isEmpty(date))
			return null;
		return XMLDatatypeHelper.parseDate(date);
	}

	private static Calendar parseDateTime(String date)
	{
		if (StringHelper.isEmpty(date))
			return null;
		return XMLDatatypeHelper.parseDateTime(date);
	}

	/*
	��������� �������������� ������������� 
	 */
	protected Long getDepartmentId(BankInfo_Type bankInfo) throws BusinessException
	{
		if (bankInfo == null)
			return null;
		Code code = new ExtendedCodeImpl(bankInfo.getRegionId(), bankInfo.getAgencyId(), bankInfo.getBranchId());
		Department department = departmentService.findByCode(code);
		if (department == null)
			throw new BusinessException("�� ���������������� ������������� � ����� �� = " + bankInfo.getRegionId() +
									" ��� = " + bankInfo.getAgencyId() + " ��� = " + bankInfo.getBranchId());
		return department.getId();
	}

	private String getRqTm()
	{
		GregorianCalendar messageDate = new GregorianCalendar();
		return XMLDatatypeHelper.formatDateTimeWithoutTimeZone(messageDate);
	}

	/**
	 * ���������� ��������� ������������
	 * @param personDocuments - ��� ��������� ��������� ������������
	 * @param identityCard - ��������, ��������� �� ������� �������
	 * @return ����������� ��� ���������� �������� ������������
	 */
	private Set<PersonDocument> addOrUpdatePersonDocumentByType(Set<PersonDocument> personDocuments, IdentityCard_Type identityCard)
	{
		Set<PersonDocument> documents = new HashSet<PersonDocument>();
		PersonDocumentType documentType = PassportTypeWrapper.getPassportType(identityCard.getIdType());
		PersonDocument personDocument = new PersonDocumentImpl();

		for (PersonDocument personDocumentItem : personDocuments)
		{
			if (documentType.equals(personDocumentItem.getDocumentType()))
			{
				personDocument = personDocumentItem;
				break;
			}
		}

		personDocument.setDocumentSeries(identityCard.getIdSeries());
		personDocument.setDocumentNumber(identityCard.getIdNum());
		personDocument.setDocumentIssueBy(identityCard.getIssuedBy());
		personDocument.setDocumentIssueByCode(identityCard.getIssuedCode());
		personDocument.setDocumentIssueDate(parseDate(identityCard.getIssueDt()));
		personDocument.setDocumentTimeUpDate(parseDate(identityCard.getExpDt()));
		documents.add(personDocument);

		return documents;
	}

	/**
	 * ������� ��� �������� ������ �������
	 * @param person - ������
	 * @param addresses - ������
	 */
	private void addOrUpdateAddresses(ActivePerson person, FullAddress_Type[] addresses)
	{
		//���� ������ �� ������, ������
		if (addresses == null || addresses.length == 0)
		{
			person.setRegistrationAddress(null);
			person.setResidenceAddress(null);
			return;
		}
		for (FullAddress_Type address : addresses)
		{
			//����� �����������
			if (address.getAddrType().equals("1"))
			{
				person.setRegistrationAddress(addOrUpdatePerosnAddress(person.getRegistrationAddress(), address));
			}
			//����� ����������
			if (address.getAddrType().equals("2"))
			{
				person.setResidenceAddress(addOrUpdatePerosnAddress(person.getResidenceAddress(), address));
			}
		}
	}

	/**
	 * ��������� ���������� �����, ���������� �������
	 * @param personAddress - ����� �������, ���� ��� �� null
	 * @param address - ���������� �����. Null �� ����� ����!
	 * @return ���������� ��� ����� �����
	 */
	private Address addOrUpdatePerosnAddress(Address personAddress, FullAddress_Type address)
	{
		if (personAddress == null)
			personAddress = new Address();
		personAddress.setUnparseableAddress(address.getAddr3());
		//��� ������
		personAddress.setPostalCode(address.getPostalCode());
		personAddress.setProvince(address.getRegion());  //������ (�������)
		personAddress.setDistrict(address.getArea()); //�����
		personAddress.setCity(address.getCity());
		personAddress.setStreet(address.getAddr1());
		personAddress.setHouse(address.getHouseNum());
		personAddress.setBuilding(address.getHouseExt()); //������
		//�������� ���
		personAddress.setFlat(address.getUnitNum());
		return personAddress;
	}

	private void updateContactData(ActivePerson person, PersonalSubscriptionData subscriptionData, ContactData_Type[] contactDataArray)
	{
		//���� ������ �� ������, ������� ��� ������
		if (contactDataArray == null || contactDataArray.length == 0)
		{
			person.setJobPhone(null);
			person.setHomePhone(null);
			subscriptionData.setMobilePhone(null);
			subscriptionData.setEmailAddress(null);
			return;
		}
		for (ContactData_Type contactData : contactDataArray)
		{
			if (ContactType.HOME_PHONE.getDescription().equals(contactData.getContactType()))
			{
				person.setHomePhone(contactData.getContactNum());
			}
			if (ContactType.WORK_PHONE.getDescription().equals(contactData.getContactType()))
			{
				person.setJobPhone(contactData.getContactNum());
			}
			if (ContactType.MOBILE_PHONE.getDescription().equals(contactData.getContactType()))
			{
				subscriptionData.setMobilePhone(PhoneNumberUtil.getNormalizePhoneNumberString(contactData.getContactNum()));
				person.setMobileOperator(contactData.getPhoneOperName());
			}
			if (ContactType.EMAIL.getDescription().equals(contactData.getContactType()))
			{
				subscriptionData.setEmailAddress(contactData.getContactNum());
			}
		}
	}

	/**
	 * ���������� ������������.
	 * todo.clientId � displayClientId ��������� �� ����.
	 * @param person - ������������ ������� ��� ����������, ���� ����� ������� ����� �������� null
	 * @param custRec - ������, ��������� �� �������
	 */
	private void updatePersonData(ActivePerson person, CustRec_Type custRec) throws BusinessException
	{
		CustInfo_Type custInfo = custRec.getCustInfo();
		person.setLastUpdateDate(parseDateTime(custInfo.getEffDt()));
		PersonInfo_Type personInfo = custInfo.getPersonInfo();

		person.setBirthDay(parseDate(personInfo.getBirthday()));
		person.setBirthPlace(personInfo.getBirthPlace());
		UserDocumentService.get().resetUserDocument(person.getLogin(), DocumentType.INN, personInfo.getTaxId());
		person.setCitizenship(personInfo.getCitizenship());
		person.setGender(personInfo.getGender());
		person.setIsResident(personInfo.getResident());

		PersonName_Type name = personInfo.getPersonName();
		person.setSurName(name.getLastName());
		person.setFirstName(name.getFirstName());
		person.setPatrName(name.getMiddleName());

		person.setPersonDocuments(addOrUpdatePersonDocumentByType(person.getPersonDocuments(), personInfo.getIdentityCard()));
		ContactInfo_Type contactInfo = personInfo.getContactInfo();
		SubscriptionService subscriptionService = new SubscriptionService();
		PersonalSubscriptionData subscriptionData = subscriptionService.findPersonalData(person.getLogin());
		updateContactData(person, subscriptionData, contactInfo.getContactData());
		addOrUpdateAddresses(person, contactInfo.getPostAddr());

		ServiceInfo_Type serviceInfo = custRec.getServiceInfo();
		if (serviceInfo != null)
		{
			person.setAgreementNumber(serviceInfo.getAgreementNum());
			person.setAgreementDate(parseDate(serviceInfo.getStartDt()));
			person.setProlongationRejectionDate(parseDate(serviceInfo.getEndDt()));

			//������ ������ ��������� �������, ������ � ������, ���� ���� ������.
			person.setDepartmentId(getDepartmentId(serviceInfo.getBankInfo()));
		}
		personService.update(person);
		subscriptionService.changePersonalData(subscriptionData);
	}
}
