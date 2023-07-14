package com.rssl.phizicgate.mdm.integration.mdm.processors;

import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.logging.operations.context.OperationContext;
import com.rssl.phizic.utils.PassportTypeWrapper;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizicgate.mdm.business.profiles.Gender;
import com.rssl.phizicgate.mdm.integration.mdm.generated.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author akrenev
 * @ created 14.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * ������ �������� � ���
 */

public final class RequestHelper
{
	private static final String ERIB_SYSTEM_NAME = SPNameType.URN_SBRFSYSTEMS_99_ERIB.value();
	private static final String MDM_SYSTEM_NAME = SPNameType.MDM.value();

	private RequestHelper() {}

	/**
	 * ������������ RqUUID
	 * @return RqUUID
	 */
	public static String generateUUID()
	{
		return new RandomGUID().getStringValue();
	}

	/**
	 * ������������ OperUUID
	 * @return OperUUID
	 */
	public static String generateOUUID()
	{
		return OperationContext.getCurrentOperUID();
	}

	/**
	 * @return �������� ������� ����
	 */
	public static String getEribSystemName()
	{
		return ERIB_SYSTEM_NAME;
	}

	/**
	 * @return �������� ������� ���
	 */
	public static String getMdmSystemName()
	{
		return MDM_SYSTEM_NAME;
	}

	/**
	 * @return SPName �������
	 */
	public static SPNameType getRequestSPName()
	{
		return SPNameType.URN_SBRFSYSTEMS_99_ERIB;
	}

	/**
	 * ������������ CustId ������� ����
	 * @param id ������������� � ����
	 * @return CustId
	 */
	public static CustIdRqType getEribCustId(Long id)
	{
		CustIdRqType custId = new CustIdRqType();
		custId.setSPName(SPNameType.URN_SBRFSYSTEMS_99_ERIB);
		custId.setCustPermId(String.valueOf(id));
		return custId;
	}

	/**
	 * ������������ CustId ������� MDM
	 * @param id ������������� � MDM
	 * @return CustId
	 */
	public static CustIdRqType getMDMCustId(String id)
	{
		CustIdRqType custId = new CustIdRqType();
		custId.setSPName(SPNameType.MDM);
		custId.setCustPermId(id);
		return custId;
	}

	/**
	 * ������������ PersonName
	 * @param lastName �������
	 * @param firstName ���
	 * @param middleName ��������
	 * @return PersonName
	 */
	public static PersonName getPersonName(String lastName, String firstName, String middleName)
	{
		PersonName personName = new PersonName();
		personName.setLastName(lastName);
		personName.setFirstName(firstName);
		personName.setMiddleName(middleName);
		return personName;
	}

	/**
	 * ������������ IdentityCard
	 * @param type ���
	 * @param series �����
	 * @param number �����
	 * @return IdentityCard
	 */
	public static IdentityCardRqType getIdentityCard(ClientDocumentType type, String series, String number)
	{
		IdentityCardRqType identityCard = new IdentityCardRqType();
		identityCard.setIdType(PassportTypeWrapper.getPassportType(type));
		identityCard.setIdSeries(series);
		identityCard.setIdNum(number);
		return identityCard;
	}

	/**
	 * ������� ���������� �� �������������� ����
	 * @param id ������������� ����
	 * @return ����������
	 */
	public static IntegrationId createEribIntegrationId(Long id)
	{
		IntegrationId integrationInfo = new IntegrationId();
		integrationInfo.setISCode(getEribSystemName());
		integrationInfo.setISCustId(String.valueOf(id));
		return integrationInfo;
	}

	/**
	 * �������� ������������� ����
	 * @param integrationInfo ���������� �� ���������������
	 * @return �������������
	 */
	public static Long getEribIntegrationId(IntegrationInfo integrationInfo)
	{
		for (IntegrationId integrationId : integrationInfo.getIntegrationIds())
		{
			if (getEribSystemName().equals(integrationId.getISCode()))
				return Long.valueOf(integrationId.getISCustId());
		}
		return null;
	}

	/**
	 * ������� ���������� �� �������������� ���
	 * @param id ������������� ���
	 * @return ����������
	 */
	public static IntegrationId createMDMIntegrationId(String id)
	{
		IntegrationId integrationInfo = new IntegrationId();
		integrationInfo.setISCode(getMdmSystemName());
		integrationInfo.setISCustId(id);
		return integrationInfo;
	}

	/**
	 * �������� ������������� ���
	 * @param integrationInfo ���������� �� ���������������
	 * @return �������������
	 */
	public static String getMDMIntegrationId(IntegrationInfo integrationInfo)
	{
		for (IntegrationId integrationId : integrationInfo.getIntegrationIds())
		{
			if (getMdmSystemName().equals(integrationId.getISCode()))
				return integrationId.getISCustId();
		}
		return null;
	}

	/**
	 * ������������ Status
	 * @param code        ���
	 * @param description ��������
	 * @return Status
	 */
	public static Status getStatus(long code, String description)
	{
		Status status = new Status();
		status.setStatusCode(code);
		status.setStatusDesc(description);
		return status;
	}

	/**
	 * ���������� �������� ���� "boolean"
	 * @param value ��������
	 * @return ���������
	 */
	public static boolean parseBoolean(String value)
	{
		return "1".equals(value);
	}

	private static final Map<String, Gender> genderMapping = new HashMap<String, Gender>(2);

	static
	{
		genderMapping.put("F", Gender.FEMALE);
		genderMapping.put("M", Gender.MALE);
	}

	/**
	 * ���������� �������� ���� "���"
	 * @param value ��������
	 * @return ���������
	 */
	public static Gender parseGender(String value)
	{
		Gender gender = genderMapping.get(value);
		return gender == null ? Gender.UNKNOWN : gender;
	}
}
