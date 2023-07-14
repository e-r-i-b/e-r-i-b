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
	/**Активный клиент, может работать в системе*/
	public static final String ACTIVE   = "A";

	/**удаленный*/
	public static final String DELETED  = "D";

	/**Клиент в процессе регистрации*/
	public static final String TEMPLATE = "T";

	/**Ошибка расторжения*/
	public static final String ERROE_CANCELATION = "E";

	/**На расторжении*/
	public static final String WAIT_CANCELATION = "W";

	/**Подписание договора*/
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
	 * @return тип создания клиента. Т.е. тип договора, с помощью которого клиент подключен к системе.
	 */
	CreationType getCreationType();

	void setCreationType(CreationType creationType);

	/**
	 * @return Дата и время последнего изменения данных
	 */
	 Calendar getLastUpdateDate();

	 void setLastUpdateDate(Calendar lastUpdateDate);

	/**
	 * @return признак регистрации в депозитарии (true если зарегистрирован)
	 */
	Boolean getIsRegisteredInDepo();

	void setIsRegisteredInDepo(Boolean registeredInDepo);

	/**
	 * @return информация об отправке данных в МДМ
	 */
	MDMState getMdmState();

	void setMdmState(MDMState mdmState);

	/**
	 * @return СНИЛС = Страховой Номер Индивидуального Лицевого Счёта (ПФР)
	 */
	String getSNILS();

	/**
	 * Вернуть идентификатор сотрудника, который отправил заявку на удаление клиента.
	 * Идентификтаор = логин клиента. Т.е. равен login.userId.
	 * @return идентификатор сотрудника
	 */
	String getEmployeeId();

	void setEmployeeId(String employeeId);

	/**
	 * Определяет, заходил ли клиент через клиентское приложение или нет.
	 *
	 * @return
	 */
	boolean getIsERIBPerson();

	/**
	 * @return - Код сегмента, к которому относят клиента
	 */
	SegmentCodeType getSegmentCodeType();

	void setSegmentCodeType(SegmentCodeType segmentCodeType);

	/**
	 * @return Код тарифного плана
	 */
	String getTarifPlanCodeType();

	void setTarifPlanCodeType(String tarifPlanCodeType);

	/**
	 * @return Дата подключения тарифного плана
	 */
	Calendar getTarifPlanConnectionDate();

	void setTarifPlanConnectionDate(Calendar tarifPlanConnectionDate);

	/**
	 * @return Номер менеджера, за которым закреплён клиент
	 */
	String getManagerId();

	void setManagerId(String managerId);

	/**
	 * @return уровень безопасности
	 */
	SecurityType getSecurityType();

	void setSecurityType(SecurityType securityType);

	/**
	 * сохранить уровень безопасности для разруливания ситуации с переключением между приложениями
	 * @param storeSecurityType
	 */
	void saveStoreSecurityType (SecurityType storeSecurityType);

	/**
	 * @return уровень безопасности, хранимый для разруливания ситуации с переключением между приложениями
	 */
	SecurityType getStoreSecurityType();

	void setStoreSecurityType(SecurityType securityType);

	/**
	 * Получить уровень безопасности из сессии, если в сесси уровень пустой или не низкий, возвращается значение из БД
	 * @return уровень безопасности, хранимый для разруливания ситуации с переключением между приложениями
	 */
	SecurityType restoreSecurityType();

	/**
	 * @return профиль ЕРМБ (can be null)
	 */
	ErmbProfile getErmbProfile();

	void setErmbProfile(ErmbProfile ermbProfile);

	/**
	 * @return Тер. банк клиентского менеджера
	 */
	String getManagerTB();

	void setManagerTB(String managerTB);

	/**
	 * @return ОСБ клиентского менеджера
	 */
	String getManagerOSB();

	void setManagerOSB(String managerOSB);

	/**
	 * @return ВСП клиентского менеджера
	 */
	String getManagerVSP();

	void setManagerVSP(String managerVSP);

	ClientData asClientData() throws IKFLMessagingException;
}
