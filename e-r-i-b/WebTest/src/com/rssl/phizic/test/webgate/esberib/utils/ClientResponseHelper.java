package com.rssl.phizic.test.webgate.esberib.utils;

import com.rssl.phizgate.common.services.bankroll.ExtendedCodeGateImpl;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.test.webgate.esberib.generated.*;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.utils.PassportTypeWrapper;
import com.rssl.phizic.utils.RandomHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizicgate.esberibgate.types.ContactType;
import org.apache.commons.collections.CollectionUtils;

import java.rmi.RemoteException;
import java.util.*;

/**
 @author Pankin
 @ created 29.09.2010
 @ $Author$
 @ $Revision$
 */
public class ClientResponseHelper extends BaseResponseHelper
{
	private static final String EDBO = "EDBO";
	private static final Long CLIENT_NOT_FOUND_BY_CARD = -425L;

	private PersonService personService = new PersonService();
	private DepartmentService departmentService = new DepartmentService();

	/**
	 * @param parameters ��������� �������
	 * @return ����� (��������� ��� ������� � ���������� � ������� ������������ ����)
	 */
	public IFXRs_Type createCustInqRs(IFXRq_Type parameters) throws RemoteException
	{
		IFXRs_Type ifxRs = new IFXRs_Type();

		CustInqRq_Type custInqRq = parameters.getCustInqRq();
		CustInqRs_Type custInqRs = createCustInqRsMain(custInqRq);

		custInqRs.setRqTm(getRqTm());
		custInqRs.setOperUID(custInqRq.getOperUID());

		ActivePerson person = null;
		//���� ������������� �� �����
		if(custInqRq.getCardAcctId() != null)
		{
			Random rand = new Random();
			if(rand.nextInt(5) == 0 && !isNightBuildsEnabled())
			{   //��������� ������ �� ��������� �����
				custInqRs.setStatus(new Status_Type(CLIENT_NOT_FOUND_BY_CARD, "������ ������� ����������� ��� ������ �� ������", null, SERVER_STATUS_DESC));
			}
			String cardNumber = custInqRq.getCardAcctId().getCardNum();
			try
			{
				List<ActivePerson> activePersonList = personService.findByLasLogonCardNumber(cardNumber);
				if(CollectionUtils.isNotEmpty(activePersonList))
					person = activePersonList.get(0);
			}
			catch (BusinessException ignore)
			{
				//������ �� ������. ���� ���������� ��������, ��� ������������ �� �����.
			}
		}
		else
		{
			CustInfo_Type custInfo = custInqRq.getCustInfo();
			PersonInfo_Type personInfo = custInfo.getPersonInfo();
			PersonName_Type personName = personInfo.getPersonName();

			IdentityCard_Type identityCard = personInfo.getIdentityCard();

			String tb = custInqRq.getBankInfo().getRbTbBrchId().substring(0, 2);

			try
			{
				GregorianCalendar birthDate = null;
				if (!StringHelper.isEmpty(personInfo.getBirthday()))
					birthDate = XMLDatatypeHelper.parseDate(personInfo.getBirthday());
				List<ActivePerson> persons = personService.getByFIOAndDoc(personName.getLastName(),personName.getFirstName(),personName.getMiddleName(),identityCard.getIdSeries(),identityCard.getIdNum(), birthDate, tb);
				if(persons != null)
				{
					if(!persons.isEmpty())
						person = persons.get(0);
				}
			}
			catch (BusinessException e)
			{
				//������ �� ������. ���� ���������� ��������, ��� ������������ �� �����.
			}

		}
		try
		{
			//���������� ��������, ����� ������ ������, �������� ��� � ��� � �������, ������ �� ������������ �� ����
			if (person == null)
			{
				Random  random = new Random();
				if (random.nextInt(20) == 0 && !isNightBuildsEnabled())
				{
					//������� ����������� ��� ���������� ������� �����.
					custInqRs.setSPDefField(new SPDefField_Type(EDBO, false, null, null, null, null, State_Type.NO_CONTRACT));
					//��� ��������� �������� ��������� �������� ����������. �� �� ������������, �� ��� �������� �� ����� (BUG039212)
					createEmptyClient(custInqRs,CreationType.CARD);
				}
				else
				{
					//������� ����������� ��� ������� ���� �����.
					CustInfo_Type custInfo = custInqRq.getCustInfo();
					createNewClient(custInqRs, custInfo.getPersonInfo(), custInfo.getBankInfo());
				}
			}
			else
			{
				//���� ������ ��� ��������� �� ����, ������ ��������� ����� � ������������� ������� ���� true.
				//��� ��������� �������� ������ ���������� ������ ������� �������������� ����.
				//��� �������� SBOL �������� ���� ������������� ������� ���������� ���� true � ��������� �����
				//������� ��� ����������, � ��������� ������ ����� ���������� ������ ������� �������������� ����.
				if (person.getCreationType().equals(CreationType.CARD) || (person.getCreationType().equals(CreationType.SBOL) &&
						(StringHelper.isEmpty(person.getGender()) || person.getGender().equals("M"))))
				{
					custInqRs.setSPDefField(new SPDefField_Type(EDBO, false, null, null, null, null, State_Type.NO_CONTRACT));
					//��� ��������� �������� ��������� �������� ����������. �� �� ������������, �� ��� �������� �� ����� (BUG039212)
					createEmptyClient(custInqRs,person.getCreationType());
				}
				else
				{

					CustRec_Type custRec = new CustRec_Type();
					CustInfo_Type custInfo = new CustInfo_Type();

					custInfo.setPersonInfo(getPersonInfo(person));

					Office office = departmentService.findById(person.getDepartmentId());
					custInfo.setBankInfo(getBankInfo(new ExtendedCodeGateImpl(office.getCode())));
					custRec.setCustInfo(custInfo);
					//���������� ���������� � �������� ����� � ��������
					custRec.setTarifPlanInfo(getTarifPlanInfo());

					custInqRs.setCustRec(custRec);

					SPDefField_Type spDefField = new SPDefField_Type();
					spDefField.setFieldName(EDBO);
					//�������� ������� ����
					spDefField.setFieldValue(true);
					//���� ���������� ��������
					spDefField.setFieldDt(XMLDatatypeHelper.formatDateTime(person.getBirthDay()));
					//����� ��������
					spDefField.setFieldNum(RandomHelper.rand(12, RandomHelper.DIGITS));
					//������� ��������
					spDefField.setState(State_Type.ACTIVE_CONTRACT);

					//������ ������� ����. ��������������� ����� ����� ��� ������������
					//spDefField.setFieldValue(false);
					//���� �������� ����
					//Calendar cancellationDate = DateHelper.getPreviousDay(Calendar.getInstance());
					//spDefField.setFieldDt1(XMLDatatypeHelper.formatDateTime(cancellationDate));
					//��������� "������� ������"
					//spDefField.setState(State_Type.CLOSED_CONTRACT);

					custInqRs.setSPDefField(spDefField);
				}
			}

			ifxRs.setCustInqRs(custInqRs);
			return ifxRs;
		}
		catch (BusinessException e)
		{
			throw new RemoteException(e.getMessage(), e);
		}
	}

	private CustInqRs_Type createCustInqRsMain(CustInqRq_Type custInqRq)
	{
		CustInqRs_Type custInqRs = new CustInqRs_Type();
		custInqRs.setRqUID(custInqRq.getRqUID());
		custInqRs.setStatus(getStatus());
		return custInqRs;
	}

	private PersonInfo_Type getPersonInfo(ActivePerson person) throws BusinessException
	{
		PersonInfo_Type personInfo = new PersonInfo_Type();

		if (person == null)
		{
			Random rand = new Random();
			GregorianCalendar birthDay = new GregorianCalendar();
			birthDay.add(Calendar.YEAR, -1 * rand.nextInt(50));
			personInfo.setBirthday(XMLDatatypeHelper.formatDate(birthDay));
			personInfo.setBirthPlace("����� ��������: " + RandomHelper.rand(4));
			personInfo.setTaxId(RandomHelper.rand(12, RandomHelper.DIGITS));
			personInfo.setCitizenship("��");
			personInfo.setContactInfo(getContactInfo(null));

			personInfo.setPersonName(getPersonName(null));

			personInfo.setIdentityCard(getIdentityCard(null));
			return personInfo;
		}

		personInfo.setBirthday(XMLDatatypeHelper.formatDate(person.getBirthDay()));
		personInfo.setBirthPlace(person.getBirthPlace());
		personInfo.setTaxId(person.getInn());
		personInfo.setCitizenship(person.getCitizenship());

		personInfo.setPersonName(getPersonName(person));
		personInfo.setIdentityCard(getIdentityCard(person));
		personInfo.setContactInfo(getContactInfo(person));
		return personInfo;
	}

	private ContactInfo_Type getContactInfo(ActivePerson person)
	{
		ContactInfo_Type contactInfo = new ContactInfo_Type();

		Random r = new Random(1);
		if (r.nextBoolean())
		{
			FullAddress_Type address = new FullAddress_Type();
			address.setAddrType("1"); //��� ������ �����������
			address.setAddr3("�.������, ��. ��������, �.12345, ����.54, ��. " + RandomHelper.rand(3, RandomHelper.DIGITS));
			contactInfo.setPostAddr(new FullAddress_Type[]{address});
		}

		ContactData_Type contactData;
		if (person == null)
		{
			contactData = new ContactData_Type();
			contactData.setContactType(ContactType.MOBILE_PHONE.getDescription());
			contactData.setContactNum("+7(9" + RandomHelper.rand(2, RandomHelper.DIGITS)+ ")" + RandomHelper.rand(7, RandomHelper.DIGITS));
			contactData.setPhoneOperName("MTS");
			contactInfo.setContactData(new ContactData_Type[]{contactData});
			return contactInfo;
		}

		//���� ��� ��������, �� ���������� ���������� ����������, ����� ������ (�� ����� ���� ��������� ������ ����, �� � ��� �� ������ ���)
		if (person.getMobilePhone() == null)
			return contactInfo;

		List<ContactData_Type> contactDataList = new ArrayList<ContactData_Type>();
		createContact(contactDataList, ContactType.HOME_PHONE, person.getHomePhone(), null);
		createContact(contactDataList, ContactType.JOB_PHONE, person.getJobPhone(), null);
		createContact(contactDataList, ContactType.MOBILE_PHONE, person.getMobilePhone(), person.getMobileOperator());
		createContact(contactDataList, ContactType.EMAIL, person.getEmail(), null);

		ContactData_Type[] contactDataArray = new ContactData_Type[contactDataList.size()];
		contactInfo.setContactData(contactDataList.toArray(contactDataArray));
		return contactInfo;
	}

	private PersonName_Type getPersonName(ActivePerson person)
	{
		PersonName_Type personName = new PersonName_Type();
		if (person == null)
		{
			personName.setFirstName("��� �������: " + RandomHelper.rand(4));
			personName.setLastName("������� �������: " + RandomHelper.rand(4));
			personName.setMiddleName("�������� �������: " + RandomHelper.rand(4));
			return personName;
		}
		personName.setFirstName(person.getFirstName());
		personName.setLastName(person.getSurName());
		personName.setMiddleName(person.getPatrName());
		return personName;
	}

	private IdentityCard_Type getIdentityCard(ActivePerson person) throws BusinessException
	{
		IdentityCard_Type identityCard = new IdentityCard_Type();
		if (person == null)
		{
			//21 - ��������������� ������� ��
			identityCard.setIdType("21");
			identityCard.setIdSeries(RandomHelper.rand(4, RandomHelper.DIGITS));
			identityCard.setIdNum(RandomHelper.rand(6, RandomHelper.DIGITS));
			return identityCard;
		}
		PersonDocument document = findIdentifyDocument(person.getPersonDocuments());
		identityCard.setIdType(PassportTypeWrapper.getPassportType(PersonDocumentType.valueFrom(document.getDocumentType())));
		identityCard.setIdSeries(document.getDocumentSeries());
		identityCard.setIdNum(document.getDocumentNumber());
		identityCard.setIssuedBy(document.getDocumentIssueBy());
		identityCard.setIssuedCode(document.getDocumentIssueByCode());
		if (document.getDocumentIssueDate() != null)
		{
			identityCard.setIssueDt(XMLDatatypeHelper.formatDate(document.getDocumentIssueDate()));
		}
		return identityCard;
	}

	private PersonDocument findIdentifyDocument(Set<PersonDocument> personDocuments) throws BusinessException
	{
		for (PersonDocument personDocument : personDocuments)
		{
			if (personDocument.isDocumentIdentify())
				return personDocument;
		}
		throw new BusinessException("������ ��������� ���������� ������������");
	}

	/**
	 * ���������� ���������� � �������� ����� � ��������
	 * @return
	 */
	private TarifPlanInfo_Type getTarifPlanInfo()
	{
		TarifPlanInfo_Type tarifPlanInfo = new TarifPlanInfo_Type();
		//��� ��������� ����� (��������)
		//tarifPlanInfo.setCode(RandomHelper.rand(1,"0123"));
		tarifPlanInfo.setCode("0");
		//���� ����������� � ��������� ����� (��������)
		GregorianCalendar connectionDate = new GregorianCalendar();
		Random rand = new Random();
		connectionDate.add(Calendar.YEAR, -1 * rand.nextInt(50));
		tarifPlanInfo.setConnectionDate(XMLDatatypeHelper.formatDate(connectionDate));
		//tarifPlanInfo.setConnectionDate("10/08/2012");
		//��� ��������, � �������� ������� ������� (��������)
		tarifPlanInfo.setSegmentCode(RandomHelper.rand(1,"0234"));
		//����� ���������, �� ������� �������� ������ (��������)
		tarifPlanInfo.setManagerId(RandomHelper.rand(14,RandomHelper.DIGITS));
		tarifPlanInfo.setManagerTb("040");
		tarifPlanInfo.setManagerOsb("1556");
		tarifPlanInfo.setManagerFilial("71");
//		tarifPlanInfo.setManagerTb("050");
//		tarifPlanInfo.setManagerOsb("5000");
//		tarifPlanInfo.setManagerFilial("51");
		return tarifPlanInfo;
	}

	private void createNewClient(CustInqRs_Type custInqRs, PersonInfo_Type personInfo, BankInfo_Type bankInfo) throws BusinessException
	{
		CustRec_Type custRec = new CustRec_Type();
		CustInfo_Type custInfo = new CustInfo_Type();

		// 1. PersonInfo + IdentityCard
		PersonInfo_Type newPersonInfo = new PersonInfo_Type();
		BeanHelper.copyPropertiesFull(newPersonInfo, personInfo);
		IdentityCard_Type identityCard = makeCEDBOPersonIdentityCard(newPersonInfo);
		newPersonInfo.setIdentityCard(identityCard);
		custInfo.setPersonInfo(newPersonInfo);

		if (bankInfo == null)
			// noinspection AssignmentToMethodParameter
			bankInfo = getMockBankInfo();
		custInfo.setBankInfo(bankInfo);
		custRec.setCustInfo(custInfo);
		//���������� ���������� � �������� ����� � ��������
		custRec.setTarifPlanInfo(getTarifPlanInfo());
		
		custInqRs.setCustRec(custRec);

		SPDefField_Type spDefField = new SPDefField_Type();
		spDefField.setFieldName(EDBO);
		spDefField.setFieldValue(true);
		spDefField.setFieldDt(XMLDatatypeHelper.formatDateTime(new GregorianCalendar()));
		spDefField.setFieldNum(RandomHelper.rand(12, RandomHelper.DIGITS));
		spDefField.setState(State_Type.ACTIVE_CONTRACT);

		//������ ������� ����. ��������������� ����� ����� ��� ������������
		//spDefField.setFieldValue(false);
		//���� �������� ����
		//Calendar cancellationDate = DateHelper.getPreviousDay(Calendar.getInstance());
		//spDefField.setFieldDt1(XMLDatatypeHelper.formatDateTime(cancellationDate));
		//��������� "������� ������"
		//spDefField.setState(State_Type.CLOSED_CONTRACT);

		custInqRs.setSPDefField(spDefField);
	}

	/**
	 * ���������� ��� ��� CEDBO
	 * ���� � ������� ��� ���, ���������� ������� �� � ���������� ������ � �������
	 * ���� � ������� ������� WAY, ���������� ������� �� � ������ � ������� �� WAY
	 * ���� � ������� ������ ���, ���������� ���
	 * @param personInfo - ������ ������� �� ������� CEDBO (never null)
	 * @return ��� ��� ������ CEDBO
	 */
	private IdentityCard_Type makeCEDBOPersonIdentityCard(PersonInfo_Type personInfo)
	{
		IdentityCard_Type identityCard = personInfo.getIdentityCard();
		if (identityCard == null)
		{
			identityCard = new IdentityCard_Type();
			//21 - ��������������� ������� ��
			identityCard.setIdType("21");
			identityCard.setIdSeries(RandomHelper.rand(4, RandomHelper.DIGITS));
			identityCard.setIdNum(RandomHelper.rand(6, RandomHelper.DIGITS));
		}
		else
		{
			//300 - ������� WAY
			if (identityCard.getIdType().equals("300"))
			{
				String way = identityCard.getIdSeries();
				if (StringHelper.isEmpty(way))
					way = identityCard.getIdNum();
				if (StringHelper.isEmpty(way))
					way = RandomHelper.rand(10, RandomHelper.DIGITS);

				//21 - ��������������� ������� ��
				identityCard.setIdType("21");
				identityCard.setIdSeries(way.substring(0, 4));
				identityCard.setIdNum(way.substring(4, 10));
			}
		}
		return identityCard;
	}

	private void createEmptyClient(CustInqRs_Type custInqRs, CreationType clientType) throws BusinessException
	{
		//��� ��������� �������� ��������� �������� ����������. �� �� ������������, �� ��� �������� �� ����� (BUG039212)
		if (clientType.equals(CreationType.CARD)) {
			CustInfo_Type custInfo = new CustInfo_Type();

			PersonInfo_Type personInfo = new PersonInfo_Type();
			String emptyData = new String();
			//���� ��������
			personInfo.setBirthday(emptyData);
			//����� ��������
			personInfo.setBirthPlace(emptyData);
			//�����������
			personInfo.setCitizenship(emptyData);
			//��� ����������� ����
			PersonName_Type personName = new PersonName_Type();
			personName.setFirstName(emptyData);
			personName.setLastName(emptyData);
			personInfo.setPersonName(personName);
			//������������� ��������
			IdentityCard_Type identityCard = new IdentityCard_Type();
			identityCard.setIdType(emptyData);
			identityCard.setIdNum(emptyData);
			personInfo.setIdentityCard(identityCard);

			custInfo.setPersonInfo(personInfo);

			CustRec_Type custRec = new CustRec_Type();
			custRec.setCustInfo(custInfo);
			custInqRs.setCustRec(custRec);
			//����, ������������ ���������� �������� ����
			SPDefField_Type spDefField = new SPDefField_Type();
			spDefField.setFieldName(EDBO);
			spDefField.setFieldValue(false);//������� ���� �� ��������
			spDefField.setState(State_Type.NO_CONTRACT);
			custInqRs.setSPDefField(spDefField);
		}
	}

	private void createContact(List<ContactData_Type> contactDataList, ContactType type, String value, String param)
	{
		if (StringHelper.isEmpty(value))
			return;
		ContactData_Type contactData = new ContactData_Type();
		contactData.setContactType(type.getDescription());
		contactData.setContactNum(value);
		if (type == ContactType.MOBILE_PHONE)
			contactData.setPhoneOperName(param);
		contactDataList.add(contactData);
	}
}
