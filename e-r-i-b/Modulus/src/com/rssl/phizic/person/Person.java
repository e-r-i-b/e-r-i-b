package com.rssl.phizic.person;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.common.types.security.SecurityType;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.messaging.TranslitMode;
import com.rssl.phizic.security.ConfirmableObject;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 15.09.2005
 * Time: 15:48:44
 */
public interface Person extends Serializable, ConfirmableObject
{
	/**�������� ������, ����� �������� � �������*/
	public static final String ACTIVE   = "A";

	/**���������*/
	public static final String DELETED  = "D";

	/**������ � �������� �����������*/
	public static final String TEMPLATE = "T";

	/**������ �����������*/
	public static final String ERROE_CANCELATION = "E";

	/**�� �����������*/
	public static final String WAIT_CANCELATION = "W";

	/**���������� ��������*/
	public static final String SIGN_AGREEMENT = "S";

	Long getId();

    void setId(Long id);

    String getClientId();

    void setClientId(String clientId);

    Login getLogin();

    void setLogin(Login login);

    String getFirstName();

    void setFirstName(String firstName);

    String getSurName();

    void setSurName(String surName);

    String getPatrName();

    void setPatrName(String patrName);

    Calendar getBirthDay();

    String getBirthPlace();

    void setBirthPlace(String birthPlace);

    void setBirthDay(Calendar birthDay);

	Address getRegistrationAddress();

	void setRegistrationAddress(Address registrationAddress);

    String getEmail();

    String getHomePhone();

    void setHomePhone(String homePhone);

    String getJobPhone();

    void setJobPhone(String jobPhone);

    String getMobilePhone();

    String getStatus();

    void setStatus(String status);

    public String getFullName();

    TranslitMode getSMSFormat();

    Long getTrustingPersonId();

    void setTrustingPersonId(Long trustingPersonId);

    Address getResidenceAddress();

	void setResidenceAddress(Address residenceAddress);

    String getMobileOperator();

    void setMobileOperator(String mobileOperator);

    String getAgreementNumber();

    void setAgreementNumber(String agreementNumber);

    Calendar getAgreementDate();

    void setAgreementDate(Calendar agreementDate);

    Calendar getServiceInsertionDate();

    void setServiceInsertionDate(Calendar serviceInsertionDate);

    String getGender();

    void setGender(String gender);

    String getCitizenship(); 

    void setCitizenship(String citizen);

    String getInn();

    Calendar getProlongationRejectionDate();

    void setProlongationRejectionDate(Calendar prolongationRejectionDate);

	String getDiscriminator();

	void setDiscriminator(String value);

	Long getDepartmentId();

	void setDepartmentId(Long depertmentId);

    String getContractCancellationCouse();

    void setContractCancellationCouse(String contractCancellationCouse);

	String getSecretWord();

	void setSecretWord(String secretWord);

	Boolean getIsResident();

	void setIsResident(Boolean isResident);

	Set<PersonDocument> getPersonDocuments();

	void setPersonDocuments(Set <PersonDocument> documents);

	void setDisplayClientId(String displayClientId);

	String getDisplayClientId();

	/**
	 * @return ��� �������� �������. �.�. ��� ��������, � ������� �������� ������ ��������� � �������.
	 */
	CreationType getCreationType();

	void setCreationType(CreationType creationType);

	/**
	 * @return ���� � ����� ���������� ��������� ������
	 */
	 Calendar getLastUpdateDate();

	 void setLastUpdateDate(Calendar lastUpdateDate);

	/**
	 * @return ������� ����������� � ����������� (true ���� ���������������)
	 */
	Boolean getIsRegisteredInDepo();

	void setIsRegisteredInDepo(Boolean registeredInDepo);

	/**
	 * @return ���������� �� �������� ������ � ���
	 */
	MDMState getMdmState();

	void setMdmState(MDMState mdmState);

	/**
	 * @return ����� = ��������� ����� ��������������� �������� ����� (���)
	 */
	String getSNILS();

	/**
	 * ������� ������������� ����������, ������� �������� ������ �� �������� �������.
	 * ������������� = ����� �������. �.�. ����� login.userId.
	 * @return ������������� ����������
	 */
	String getEmployeeId();

	void setEmployeeId(String employeeId);

	/**
	 * ����������, ������� �� ������ ����� ���������� ���������� ��� ���.
	 *
	 * @return
	 */
	boolean getIsERIBPerson();

	/**
	 * @return - ��� ��������, � �������� ������� �������
	 */
	SegmentCodeType getSegmentCodeType();

	void setSegmentCodeType(SegmentCodeType segmentCodeType);

	/**
	 * @return ��� ��������� �����
	 */
	String getTarifPlanCodeType();

	void setTarifPlanCodeType(String tarifPlanCodeType);

	/**
	 * @return ���� ����������� ��������� �����
	 */
	Calendar getTarifPlanConnectionDate();

	void setTarifPlanConnectionDate(Calendar tarifPlanConnectionDate);

	/**
	 * @return ����� ���������, �� ������� �������� ������
	 */
	String getManagerId();

	void setManagerId(String managerId);

	/**
	 * @return ������� ������������
	 */
	SecurityType getSecurityType();

	void setSecurityType(SecurityType securityType);

	/**
	 * ��������� ������� ������������ ��� ������������ �������� � ������������� ����� ������������
	 * @param storeSecurityType
	 */
	void saveStoreSecurityType (SecurityType storeSecurityType);

	/**
	 * @return ������� ������������, �������� ��� ������������ �������� � ������������� ����� ������������
	 */
	SecurityType getStoreSecurityType();

	void setStoreSecurityType(SecurityType securityType);

	/**
	 * �������� ������� ������������ �� ������, ���� � ����� ������� ������ ��� �� ������, ������������ �������� �� ��
	 * @return ������� ������������, �������� ��� ������������ �������� � ������������� ����� ������������
	 */
	SecurityType restoreSecurityType();

	/**
	 * @return ������� ���� (can be null)
	 */
	ErmbProfile getErmbProfile();

	void setErmbProfile(ErmbProfile ermbProfile);

	/**
	 * @return ���. ���� ����������� ���������
	 */
	String getManagerTB();

	void setManagerTB(String managerTB);

	/**
	 * @return ��� ����������� ���������
	 */
	String getManagerOSB();

	void setManagerOSB(String managerOSB);

	/**
	 * @return ��� ����������� ���������
	 */
	String getManagerVSP();

	void setManagerVSP(String managerVSP);

	ClientData asClientData() throws IKFLMessagingException;
}
