package com.rssl.auth.csa.back.integration.dasreda;

import com.rssl.auth.csa.back.integration.erib.types.Address;
import com.rssl.auth.csa.back.integration.erib.types.ClientInformation;
import com.rssl.auth.csa.back.integration.erib.types.Contact;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.phizgate.common.services.types.ClientDocumentImpl;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.logging.operations.context.OperationContext;
import com.rssl.phizic.utils.PhoneNumberUtil;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.gate.clients.DocumentTypeComparator;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;
import javax.xml.soap.*;

/**
 * Источник данных для отправки информации в деловую среду 
 *
 * @author akrenev
 * @ created 26.03.2013
 * @ $Author$
 * @ $Revision$
 *
 * в soap-сообщение добавляет информацию о клиенте:
 *
 * 	VerifyDSProfile_Input         [1]     Корневой элемент запроса в Деловую среду
 *		VerifyDSProfileRq         [1]     Корневой элемент запроса на верификацию в Деловую среду
 * 			RqUID                 [1]     Идентификатор запроса
 * 			RqTm                  [1]     Дата и время передачи сообщений
 * 			OperUID               [1]     Идентификатор операции
 * 			SystemId              [1]     Идентификатор системы
 * 			CustId                [1]     Идентификатор клиента
 * 			CustInfo              [1]     Идентификационные данные персоны
 * 				LastName          [1]     Фамилия
 * 				FirstName         [1]     Имя
 * 				MiddleName        [0-1]   Отчество (Поле не обязательно для клиентов без отчества)
 * 				Birthday          [1]     Дата рождения
 * 				IdentityCard      [1]     ДУЛ
 * 					IdType        [1]     Тип документа
 * 					IdSeries      [0-1]   Серия документа
 * 					IdNum         [1]     Номер документа
 * 					IssuedBy      [0-1]   Кем выдан
 * 					IssueDt       [0-1]   Дата выдачи
 * 					IssuedCode    [0-1]   Код подразделения.
 * 				PostAddr          [0-2]   Адрес клиента
 * 					AddrType      [1]     Тип адреса.
 * 					Addr          [1]     Ненормализованный адрес
 * 				ContactData       [1-n]   Средства связи
 * 					ContactType   [1]     Тип контакта
 * 					ContactNum    [1]     Значение контакта
 *				EDBOValue         [1]     Признак наличия заключенного договора УДБО (True – УДБО заключен, False – УДБО не заключен)
 */

public class BusinessEnvironmentDataSource
{
	private static final String SOAPENV_NAMESPACE_PREFIX = "soapenv";
	private static final String BAD_SOAPENV_NAMESPACE_PREFIX = "SOAP-ENV";

	private static final String DS_NAMESPACE = "http://siebel.com/CustomUI";
	private static final String NAMESPACE_PREFIX = "ns";

	private static final String VERIFY_DS_PROFILE_INPUT_TAG_NAME = "VerifyDSProfile_Input";
	private static final String VERIFY_DS_PROFILE_RQ_TAG_NAME = "VerifyDSProfileRq";
	private static final String RQ_UID_TAG_NAME = "RqUID";
	private static final String RQ_TM_TAG_NAME = "RqTm";
	private static final String OPER_UID_TAG_NAME = "OperUID";
	private static final String SYSTEM_ID_TAG_NAME = "SystemId";
	private static final String CUST_ID_TAG_NAME = "CustId";
	private static final String CUST_INFO_TAG_NAME = "CustInfo";
	private static final String LAST_NAME_TAG_NAME = "LastName";
	private static final String FIRST_NAME_TAG_NAME = "FirstName";
	private static final String MIDDLE_NAME_TAG_NAME = "MiddleName";
	private static final String BIRTHDAY_TAG_NAME = "Birthday";
	private static final String IDENTITY_CARD_TAG_NAME = "IdentityCard";
	private static final String ID_TYPE_TAG_NAME = "IdType";
	private static final String ID_SERIES_TAG_NAME = "IdSeries";
	private static final String ID_NUM_TAG_NAME = "IdNum";
	private static final String ISSUED_BY_TAG_NAME = "IssuedBy";
	private static final String ISSUE_DT_TAG_NAME = "IssueDt";
	private static final String ISSUED_CODE_TAG_NAME = "IssuedCode";
	private static final String POST_ADDR_TAG_NAME = "PostAddr";
	private static final String ADDR_TYPE_TAG_NAME = "AddrType";
	private static final String ADDR_TAG_NAME = "Addr";
	private static final String CONTACT_DATA_TAG_NAME = "ContactData";
	private static final String CONTACT_TYPE_TAG_NAME = "ContactType";
	private static final String CONTACT_NUM_TAG_NAME = "ContactNum";
	private static final String EDBO_VALUE_TAG_NAME = "EDBOValue";

	private static final Map<ClientDocumentType, String> clientDocumentTypes = new HashMap<ClientDocumentType, String>();
	private static final String SYSTEM_ID = "ERIB";
	private static final String MOBILE_TEL_CONTACT_TYPE = "MobileTel";
	private static final int MAX_ADDRESS_SIZE = 255;
	private static final String UNIVERSAL_PHONE_NUMBER_PREFIX = "7";
	private static final String FLAT_DECORATION = " кв.";
	private static final String BUILDING_DECORATION = " стр.";
	private static final String HOUSE_DECORATION = " д.";
	private static final String STREET_DECORATION = " ул.";
	private static final String CITY_DECORATION = " г.";
	private static final String DISTRICT_DECORATION = " р-н";
	private static final String PROVINCE_DECORATION = " обл.";
	private static final String POSTAL_CODE_DECORATION = "";
	private static final String SPACE = " ";

	static
	{
		clientDocumentTypes.put(ClientDocumentType.REGULAR_PASSPORT_USSR,   "1");
		clientDocumentTypes.put(ClientDocumentType.FOREIGN_PASSPORT_USSR,   "2");
		clientDocumentTypes.put(ClientDocumentType.BIRTH_CERTIFICATE,       "3");
		clientDocumentTypes.put(ClientDocumentType.OFFICER_IDCARD,          "4");
		clientDocumentTypes.put(ClientDocumentType.INQUIRY_ON_CLEARING,     "5");
		clientDocumentTypes.put(ClientDocumentType.PASSPORT_MINMORFLOT,     "6");
		clientDocumentTypes.put(ClientDocumentType.MILITARY_IDCARD,         "7");
		clientDocumentTypes.put(ClientDocumentType.DIPLOMATIC_PASSPORT_RF,  "9");
		clientDocumentTypes.put(ClientDocumentType.FOREIGN_PASSPORT,        "10");
		clientDocumentTypes.put(ClientDocumentType.IMMIGRANT_REGISTRATION,  "11");
		clientDocumentTypes.put(ClientDocumentType.RESIDENTIAL_PERMIT_RF,   "12");
		clientDocumentTypes.put(ClientDocumentType.REFUGEE_IDENTITY,        "13");
		clientDocumentTypes.put(ClientDocumentType.TIME_IDCARD_RF,          "14");
		clientDocumentTypes.put(ClientDocumentType.REGULAR_PASSPORT_RF,     "21");
		clientDocumentTypes.put(ClientDocumentType.FOREIGN_PASSPORT_RF,     "22");
		clientDocumentTypes.put(ClientDocumentType.SEAMEN_PASSPORT,         "26");
		clientDocumentTypes.put(ClientDocumentType.RESERVE_OFFICER_IDCARD,  "27");
		clientDocumentTypes.put(ClientDocumentType.MIGRATORY_CARD,          "39");
		clientDocumentTypes.put(ClientDocumentType.OTHER_DOCUMENT_MVD,      "91");
		clientDocumentTypes.put(ClientDocumentType.PASSPORT_WAY,            "300");
	}

	private final String custId;
	private final ClientInformation clientInformation;
	private final List<String> mobilePhones = new ArrayList<String>();

	/**
	 * конструктор сорса
	 * @param custId идентификатор клиента
	 * @param profile профиль клиента
	 * @param clientInformation информация о клиенте из ЕРИБа
	 * @param mobilePhones мобильные телефоны клиента
	 */
	public BusinessEnvironmentDataSource(String custId, Profile profile, ClientInformation clientInformation, Collection<String> mobilePhones)
	{
		this.custId = custId;
		this.clientInformation = clientInformation == null ? new ClientInformation(profile) : clientInformation;
		for (String phone : mobilePhones)
			this.mobilePhones.add(getFormatedPhone(phone));
	}

	/**
	 *  заполнить сообщение данными
	 */
	void buildExternalSystemRequest(SOAPMessage request) throws SOAPException
	{
		SOAPEnvelope envelope = request.getSOAPPart().getEnvelope();
		request.getSOAPHeader().detachNode();
		envelope.setPrefix(SOAPENV_NAMESPACE_PREFIX);
		envelope.removeNamespaceDeclaration(BAD_SOAPENV_NAMESPACE_PREFIX);
		buildRequestParameter(envelope);
	}

	private void buildSimpleElement(SOAPElement parent, String name, String value) throws SOAPException
	{
		SOAPElement element = parent.addChildElement(name);
		element.addTextNode(value);
	}

	private void buildNotEmptySimpleElement(SOAPElement parent, String name, String value) throws SOAPException
	{
		if (StringHelper.isNotEmpty(value))
			buildSimpleElement(parent, name, value);
	}

	private void buildRqUID(SOAPElement parent) throws SOAPException
	{
		buildSimpleElement(parent, RQ_UID_TAG_NAME, new RandomGUID().getStringValue());
	}

	private void buildRqTm(SOAPElement parent) throws SOAPException
	{
		buildSimpleElement(parent, RQ_TM_TAG_NAME, XMLDatatypeHelper.formatDateTimeWithoutTimeZone(Calendar.getInstance()));
	}

	private void buildOperUID(SOAPElement parent) throws SOAPException
	{
		buildSimpleElement(parent, OPER_UID_TAG_NAME, OperationContext.getCurrentOperUID());
	}

	private void buildSystemId(SOAPElement parent) throws SOAPException
	{
		buildSimpleElement(parent, SYSTEM_ID_TAG_NAME, SYSTEM_ID);
	}

	private void buildCustId(SOAPElement parent) throws SOAPException
	{
		buildSimpleElement(parent, CUST_ID_TAG_NAME, custId);
	}

	private void buildLastName(SOAPElement parent) throws SOAPException
	{
		buildSimpleElement(parent, LAST_NAME_TAG_NAME, clientInformation.getSurname());
	}

	private void buildFirstName(SOAPElement parent) throws SOAPException
	{
		buildSimpleElement(parent, FIRST_NAME_TAG_NAME, clientInformation.getFirstname());
	}

	private void buildMiddleName(SOAPElement parent) throws SOAPException
	{
		buildSimpleElement(parent, MIDDLE_NAME_TAG_NAME, clientInformation.getPatrname());
	}

	private void buildBirthday(SOAPElement parent) throws SOAPException
	{
		buildSimpleElement(parent, BIRTHDAY_TAG_NAME, format(clientInformation.getBirthdate()));
	}

	private void buildIdentityCard(SOAPElement parent) throws SOAPException
	{
		List<ClientDocumentImpl> documentList = new ArrayList<ClientDocumentImpl>(clientInformation.getDocuments());
		Collections.sort(documentList, new DocumentTypeComparator());
		ClientDocumentImpl document = documentList.get(0);

		SOAPElement identityCardElement = parent.addChildElement(IDENTITY_CARD_TAG_NAME);
		buildSimpleElement(identityCardElement, ID_TYPE_TAG_NAME, clientDocumentTypes.get(document.getDocumentType()));
		String docSeries = document.getDocSeries();
		String docNumber = document.getDocNumber();
		if (ClientDocumentType.PASSPORT_WAY == document.getDocumentType())
		{
			docSeries = document.getDocNumber();
			docNumber = document.getDocSeries();
		}
		buildNotEmptySimpleElement(identityCardElement, ID_SERIES_TAG_NAME, docSeries);
		buildSimpleElement(identityCardElement, ID_NUM_TAG_NAME, docNumber);
		buildNotEmptySimpleElement(identityCardElement, ISSUED_BY_TAG_NAME, document.getDocIssueBy());
		buildNotEmptySimpleElement(identityCardElement, ISSUE_DT_TAG_NAME, format(document.getDocIssueDate()));
		buildNotEmptySimpleElement(identityCardElement, ISSUED_CODE_TAG_NAME, document.getDocIssueByCode());
	}

	private void buildPostAddr(SOAPElement parent) throws SOAPException
	{
		if (CollectionUtils.isEmpty(clientInformation.getPostAddr()))
			return;

		for (Address address : clientInformation.getPostAddr())
		{
			String fullAddress = formatAddress(address).trim();
			if (StringHelper.isNotEmpty(fullAddress))
			{
				SOAPElement postAddrElement = parent.addChildElement(POST_ADDR_TAG_NAME);
				buildSimpleElement(postAddrElement, ADDR_TYPE_TAG_NAME, address.getType());
				buildSimpleElement(postAddrElement, ADDR_TAG_NAME, fullAddress);
			}
		}
	}

	private void buildContactData(SOAPElement parent) throws SOAPException
	{
		List<Contact> contacts = clientInformation.getContactData();
		if (CollectionUtils.isNotEmpty(contacts))
		{
			for (Contact contact : contacts)
			{
				SOAPElement contactDataElement = parent.addChildElement(CONTACT_DATA_TAG_NAME);
				String type = contact.getContactType();
				String num = contact.getContactNum();
				if (MOBILE_TEL_CONTACT_TYPE.equals(type))
				{
					num = getFormatedPhone(num);
					mobilePhones.remove(num);
				}
				buildSimpleElement(contactDataElement, CONTACT_TYPE_TAG_NAME, type);
				buildSimpleElement(contactDataElement, CONTACT_NUM_TAG_NAME, num);
			}
		}

		for (String phone : mobilePhones)
		{
			SOAPElement contactDataElement = parent.addChildElement(CONTACT_DATA_TAG_NAME);
			buildSimpleElement(contactDataElement, CONTACT_TYPE_TAG_NAME, MOBILE_TEL_CONTACT_TYPE);
			buildSimpleElement(contactDataElement, CONTACT_NUM_TAG_NAME, phone);
		}
	}

	private void buildUDBO(SOAPElement parent) throws SOAPException
	{
		buildSimpleElement(parent, EDBO_VALUE_TAG_NAME, String.valueOf(clientInformation.isUDBO()));
	}

	private void buildCustInfo(SOAPElement parent) throws SOAPException
	{
		SOAPElement custInfoElement = parent.addChildElement(CUST_INFO_TAG_NAME);
		buildLastName(custInfoElement);
		buildFirstName(custInfoElement);
		buildMiddleName(custInfoElement);
		buildBirthday(custInfoElement);
		buildIdentityCard(custInfoElement);
		buildPostAddr(custInfoElement);
		buildContactData(custInfoElement);
		buildUDBO(custInfoElement);
	}

	private void buildVerifyDSProfileRq(SOAPElement parent) throws SOAPException
	{
		SOAPElement verifyDSProfileRqElement = parent.addChildElement(VERIFY_DS_PROFILE_RQ_TAG_NAME);
		buildRqUID(verifyDSProfileRqElement);
		buildRqTm(verifyDSProfileRqElement);
		buildOperUID(verifyDSProfileRqElement);
		buildSystemId(verifyDSProfileRqElement);
		buildCustId(verifyDSProfileRqElement);
		buildCustInfo(verifyDSProfileRqElement);
	}

	private void buildRequestParameter(SOAPEnvelope envelope) throws SOAPException
	{
		SOAPBody soapBody = envelope.getBody();
		soapBody.setPrefix(SOAPENV_NAMESPACE_PREFIX);
		soapBody.removeNamespaceDeclaration(BAD_SOAPENV_NAMESPACE_PREFIX);
		Name bodyName = envelope.createName(VERIFY_DS_PROFILE_INPUT_TAG_NAME, NAMESPACE_PREFIX, DS_NAMESPACE);
		SOAPBodyElement soapBodyElement = soapBody.addBodyElement(bodyName);
		buildVerifyDSProfileRq(soapBodyElement);
	}


	private String decorateAddressElement(String decoration, String addressElement)
	{
		if (StringHelper.isEmpty(addressElement))
			return "";

		return decoration.concat(SPACE).concat(addressElement);
	}

	private void addIfSuitable(StringBuilder builder, String addressElement, String decoration)
	{
		String decoratedElement = decorateAddressElement(decoration, addressElement);
		if (StringHelper.isEmpty(decoratedElement))
			return;

		if (builder.length() + decoratedElement.length() < MAX_ADDRESS_SIZE)
			builder.insert(0, decoratedElement);
	}

	private String formatAddress(Address address)
	{
		if (StringHelper.isNotEmpty(address.getUnparseableAddress()))
			return address.getUnparseableAddress();

		StringBuilder fullAddress = new StringBuilder();
		addIfSuitable(fullAddress, address.getFlat(),       FLAT_DECORATION);
		addIfSuitable(fullAddress, address.getBuilding(),   BUILDING_DECORATION);
		addIfSuitable(fullAddress, address.getHouse(),      HOUSE_DECORATION);
		addIfSuitable(fullAddress, address.getStreet(),     STREET_DECORATION);
		addIfSuitable(fullAddress, address.getCity(),       CITY_DECORATION);
		addIfSuitable(fullAddress, address.getDistrict(),   DISTRICT_DECORATION);
		addIfSuitable(fullAddress, address.getProvince(),   PROVINCE_DECORATION);
		addIfSuitable(fullAddress, address.getPostalCode(), POSTAL_CODE_DECORATION);
		return fullAddress.toString();
	}

	private String getFormatedPhone(String phone)
	{
		return UNIVERSAL_PHONE_NUMBER_PREFIX.concat(PhoneNumberUtil.getNormalizePhoneNumberString(phone));
	}

	private String format(Calendar date)
	{
		if (date == null)
			return null;
		return XMLDatatypeHelper.formatDateWithoutTimeZone(date);
	}
}
