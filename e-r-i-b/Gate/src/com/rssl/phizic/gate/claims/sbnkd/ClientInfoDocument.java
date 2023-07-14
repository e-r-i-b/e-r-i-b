package com.rssl.phizic.gate.claims.sbnkd;

import com.rssl.phizic.gate.claims.sbnkd.impl.CardInfoImpl;

import java.util.Calendar;
import java.util.List;

/**
 * Информация о клиенте.
 *
 * @author bogdanov
 * @ created 17.12.14
 * @ $Author$
 * @ $Revision$
 */

public interface ClientInfoDocument
{
	/**
	 * @return rbTbBranch
	 */
	public String getRbTbBranch();

	/**
	 * @return дата создания.
	 */
	public Calendar getCreationDate();

	/**
	 * @param creationDate дата создания.
	 */
	public void setCreationDate(Calendar creationDate);

	/**
	 * @return ид.
	 */
	public Long getId();

	/**
	 * @param id ид.
	 */
	public void setId(Long id);

	/**
	 * @return гостевая заявка.
	 */
	public  boolean isGuest();

	/**
	 * @param guest гостевая заявка.
	 */
	public void setGuest(boolean guest);

	/**
	 * @return идентификатор владельца.
	 */
	public Long getOwnerId();

	/**
	 * @param ownerId идентификатор владельца.
	 */
	public void setOwnerId(Long ownerId);

	/**
	 * @return номер телефона.
	 */
	public String getPhone();

	/**
	 * @param phone номер телеофна.
	 */
	public void setPhone(String phone);

	/**
	 * @return внешний идентификатор.
	 */
	public String getUID();

	/**
	 * @param UID внешний идентификатор.
	 */
	public void setUID(String UID);

	/**
	 * @return информация по картам.
	 */
	public List<CardInfoImpl> getCardInfos();

	/**
	 * @param cardInfo информация по картам.
	 */
	public void setCardInfos(List<CardInfoImpl> cardInfo);

	/**
	 * @param found найден ли был клиент после запроса GetPrivateClient.
	 */
	public void setClientFound(boolean found);

	/**
	 * @return найден ли был клиент после запроса GetPrivateClient.
	 */
	public boolean isClientFound();

	/**
	 * @return ОСБ заявки.
	 */
	public String getOsb();

	/**
	 * @param osb ОСБ заявки.
	 */
	public void setOsb(String osb);

	/**
	 * @return ТБ заявки.
	 */
	public String getTb();

	/**
	 * @param tb ТБ заявки.
	 */
	public void setTb(String tb);

	/**
	 * @return ВСП заявки.
	 */
	public String getVsp();

	/**
	 * @param vsp ВСП заявки.
	 */
	public void setVsp(String vsp);

	/**
	 * @param personBirthday Дата рождения клиента.
	 */
	public void setPersonBirthday(Calendar personBirthday);

	/**
	 * @return Дата рождения клиента.
	 */
	public Calendar getPersonBirthday();

	/**
	 * @param personBirthplace Место рождения клиента.
	 */
	public void setPersonBirthplace(String personBirthplace);

	/**
	 * @return Место рождения клиента.
	 */
	public String getPersonBirthplace();

	/**
	 * @param personTaxId ИНН клиента.
	 */
	public void setPersonTaxId(String personTaxId);

	/**
	 * @return ИНН клиента.
	 */
	public String getPersonTaxId();

	/**
	 * @param personCitizenship Гражданство клиента.
	 */
	public void setPersonCitizenship(String personCitizenship);

	/**
	 * @return Гражданство клиента.
	 */
	public String getPersonCitizenship();

	/**
	 * @param personGender Пол клиента.
	 */
	public void setPersonGender(String personGender);

	/**
	 * @return Пол клиента.
	 */
	public String getPersonGender();

	/**
	 * @param personResident Клиент-резидент.
	 */
	public void setPersonResident(Boolean personResident);

	/**
	 * @return Клиент-резидент.
	 */
	public Boolean isPersonResident();

	/**
	 * @param personLastName Фамилия клиента.
	 */
	public void setPersonLastName(String personLastName);

	/**
	 * @return Фамилия клиента.
	 */
	public String getPersonLastName();

	/**
	 * @param personFirstName Имя клиента.
	 */
	public void setPersonFirstName(String personFirstName);

	/**
	 * @return Имя клиента.
	 */
	public String getPersonFirstName();

	/**
	 * @param personMiddleName Отчество клиента.
	 */
	public void setPersonMiddleName(String personMiddleName);

	/**
	 * @return Отчество клиента.
	 */
	public String getPersonMiddleName();

	/**
	 * @param identityCardType Тип документа удостоверяющего личность.
	 */
	public void setIdentityCardType(String identityCardType);

	/**
	 * @return Тип документа удостоверяющего личность.
	 */
	public String getIdentityCardType();

	/**
	 * @param identityCardSeries Серия документа удостоверяющего личность.
	 */
	public void setIdentityCardSeries(String identityCardSeries);

	/**
	 * @return Серия документа удостоверяющего личность.
	 */
	public String getIdentityCardSeries();

	/**
	 * @param identityCardNumber Номер документа удостоверяющего личность.
	 */
	public void setIdentityCardNumber(String identityCardNumber);

	/**
	 * @return Номер документа удостоверяющего личность.
	 */
	public String getIdentityCardNumber();

	/**
	 * @param identityCardIssuedBy Кем выдан ДУЛ.
	 */
	public void setIdentityCardIssuedBy(String identityCardIssuedBy);

	/**
	 * @return Кем выдан ДУЛ.
	 */
	public String getIdentityCardIssuedBy();

	/**
	 * @return - Кем выдан ДУЛ
	 */
	public String getIdentityCardIssuedCode();

	/**
	 * @param identityCardIssuedCode - Кем выдан ДУЛ
	 */
	public void setIdentityCardIssuedCode(String identityCardIssuedCode);

	/**
	 * @param identityCardIssueDate Когда выдан ДУЛ.
	 */
	public void setIdentityCardIssueDate(Calendar identityCardIssueDate);

	/**
	 * @return Когда выдан ДУЛ.
	 */
	public Calendar getIdentityCardIssueDate();

	/**
	 * @param identityCardExpDate Срок действия ДУЛ.
	 */
	public void setIdentityCardExpDate(Calendar identityCardExpDate);

	/**
	 * @return Срок действия ДУЛ.
	 */
	public Calendar getIdentityCardExpDate();

	/**
	 * @param verified Признак верифицированного клиента.
	 */
	public void setVerified(boolean verified);

	/**
	 * @return Признак верифицированного клиента.
	 */
	public boolean isVerified();

	/**
	 * @param contactData нетипизированная информация о котнакте.
	 */
	public void setContactData(ContactData[] contactData);

	/**
	 * @return нетипизированная информация о котнакте.
	 */
	public ContactData[] getContactData();

	/**
	 * @param address адреса.
	 */
	public void setAddress(FullAddress[] address);

	/**
	 * @return адреса.
	 */
	public FullAddress[] getAddress();

	/**
	 * @param state статус.
	 */
	void setStatus(IssueCardStatus state);

	/**
	 * @return статус.
	 */
	IssueCardStatus getStatus();

	/**
	 * @return  сконвертированный RbTbBranchId заявки
	 */
	public String getConvertedRbTbBranchId();

	/**
	 * @param convertedRbTbBranchId сконвертированный RbTbBranchId заявки
	 */

	public void setConvertedRbTbBranchId(String convertedRbTbBranchId);

	/**
	 * @return сконвретированный номер отделения
	 */
	public String getConvertedAgencyId();

	/**
	 * @return сконвретированный номер ТБ
	 */
	public String getConvertedRegionId();

	/**
	 * @param convertedRegionId cконвретированный номер ТБ
	 */
	public void setConvertedRegionId(String convertedRegionId);
	/**
	 * @param convertedBranchId сконвретированный номер филиала
	 */
	public void setConvertedBranchId(String convertedBranchId);

	/**
	 * @return сконвретированный номер филиала
	 */
	public String getConvertedBranchId();

	/**
	 * @param convertedAgencyId сконвретированный номер отделения
	 */
	public void setConvertedAgencyId(String convertedAgencyId);

}
