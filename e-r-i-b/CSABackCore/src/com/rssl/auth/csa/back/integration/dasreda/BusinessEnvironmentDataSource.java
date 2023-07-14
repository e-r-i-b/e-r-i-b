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
 * �������� ������ ��� �������� ���������� � ������� ����� 
 *
 * @author akrenev
 * @ created 26.03.2013
 * @ $Author$
 * @ $Revision$
 *
 * � soap-��������� ��������� ���������� � �������:
 *
 * 	VerifyDSProfile_Input         [1]     �������� ������� ������� � ������� �����
 *		VerifyDSProfileRq         [1]     �������� ������� ������� �� ����������� � ������� �����
 * 			RqUID                 [1]     ������������� �������
 * 			RqTm                  [1]     ���� � ����� �������� ���������
 * 			OperUID               [1]     ������������� ��������
 * 			SystemId              [1]     ������������� �������
 * 			CustId                [1]     ������������� �������
 * 			CustInfo              [1]     ����������������� ������ �������
 * 				LastName          [1]     �������
 * 				FirstName         [1]     ���
 * 				MiddleName        [0-1]   �������� (���� �� ����������� ��� �������� ��� ��������)
 * 				Birthday          [1]     ���� ��������
 * 				IdentityCard      [1]     ���
 * 					IdType        [1]     ��� ���������
 * 					IdSeries      [0-1]   ����� ���������
 * 					IdNum         [1]     ����� ���������
 * 					IssuedBy      [0-1]   ��� �����
 * 					IssueDt       [0-1]   ���� ������
 * 					IssuedCode    [0-1]   ��� �������������.
 * 				PostAddr          [0-2]   ����� �������
 * 					AddrType      [1]     ��� ������.
 * 					Addr          [1]     ����������������� �����
 * 				ContactData       [1-n]   �������� �����
 * 					ContactType   [1]     ��� ��������
 * 					ContactNum    [1]     �������� ��������
 *				EDBOValue         [1]     ������� ������� ������������ �������� ���� (True � ���� ��������, False � ���� �� ��������)
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
	private static final String FLAT_DECORATION = " ��.";
	private static final String BUILDING_DECORATION = " ���.";
	private static final String HOUSE_DECORATION = " �.";
	private static final String STREET_DECORATION = " ��.";
	private static final String CITY_DECORATION = " �.";
	private static final String DISTRICT_DECORATION = " �-�";
	private static final String PROVINCE_DECORATION = " ���.";
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
	 * ����������� �����
	 * @param custId ������������� �������
	 * @param profile ������� �������
	 * @param clientInformation ���������� � ������� �� �����
	 * @param mobilePhones ��������� �������� �������
	 */
	public BusinessEnvironmentDataSource(String custId, Profile profile, ClientInformation clientInformation, Collection<String> mobilePhones)
	{
		this.custId = custId;
		this.clientInformation = clientInformation == null ? new ClientInformation(profile) : clientInformation;
		for (String phone : mobilePhones)
			this.mobilePhones.add(getFormatedPhone(phone));
	}

	/**
	 *  ��������� ��������� �������
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
