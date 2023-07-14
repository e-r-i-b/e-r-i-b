package com.rssl.phizic.gate.claims.sbnkd.impl;

import com.rssl.phizic.gate.claims.sbnkd.*;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * @author bogdanov
 * @ created 17.12.14
 * @ $Author$
 * @ $Revision$
 */

public class ClientInfoDocumentImpl extends BaseCardClaim implements ClientInfoDocument
{
	private List<CardInfoImpl> cardInfos;
	private String tb;
	private String osb;
	private String vsp;
	private String convertedBranchId;
	private String convertedAgencyId;
	private String convertedRegionId;
	private String convertedRbTbBranchId;
	private boolean clientFound;
	private boolean verified;
	private Calendar personBirthday;
	private String personBirthplace;
	private String personCitizenship;
	private String personGender;
	private Boolean personResident;
	private String personFirstName;
	private String personLastName;
	private String personMiddleName;
	private String personTaxId;
	private String identityCardType;
	private String identityCardSeries;
	private String identityCardNumber;
	private String identityCardIssuedBy;
	private String identitycardIssuedCode;
	private Calendar identityCardIssueDate;
	private Calendar identityCardExpDate;
	private int cardCount;
	private String firstCardName;
	private String firstCardCurrency;
	private String allCardNames;

	private List<ContactData> contactDatas = new LinkedList<ContactData>();
	private List<FullAddress> fullAddresses = new LinkedList<FullAddress>();

	private boolean check(int index, String field)
	{
		if (StringHelper.isEmpty(field))
		{
			return false;
		}

		while (index >= contactDatas.size())
		{
			contactDatas.add(null);
		}

		if (contactDatas.get(index) == null)
		{
			contactDatas.set(index, new ContactData());
		}
		return true;
	}

	private boolean checkAddr(int index, String field)
	{
		if (StringHelper.isEmpty(field))
		{
			return false;
		}

		while (index >= fullAddresses.size())
		{
			fullAddresses.add(null);
		}

		if (fullAddresses.get(index) == null)
		{
			fullAddresses.set(index, new FullAddress());
			if (index == 1)
				fullAddresses.get(index).setRegistrationAddress(true);
		}
		return true;
	}

	private String getContactNumber(int index)
	{
		return index >= contactDatas.size() || contactDatas.get(index) == null ? null : contactDatas.get(index).getContactNum();
	}

	private void setContactNumber(int index, String contactNumber)
	{
		if (check(index, contactNumber))
			contactDatas.get(index).setContactNum(contactNumber);
	}

	private String getContactOperator(int index)
	{
		return index >= contactDatas.size() || contactDatas.get(index) == null ? null : contactDatas.get(index).getPhoneOperName();
	}

	private void setContactOperator(int index, String contactOperator)
	{
		if (check(index, contactOperator))
			contactDatas.get(index).setPhoneOperName(contactOperator);
	}

	private String getContactType(int index)
	{
		return index >= contactDatas.size() || contactDatas.get(index) == null ? null : contactDatas.get(index).getContactType().name();
	}

	private void setContactType(int index, String contactType)
	{
		if (check(index, contactType))
			contactDatas.get(index).setContactType(ContactData.ContactType.valueOf(contactType));
	}

	private String getAddressAfterCity(int index)
	{
		return index >= fullAddresses.size() || fullAddresses.get(index) == null ? null : fullAddresses.get(index).getAfterSityAdress();
	}

	public void setAddressAfterCity(int index, String addressAfterCity)
	{
		if (checkAddr(index, addressAfterCity))
			fullAddresses.get(index).setAfterSityAdress(addressAfterCity);
	}

	public String getAddressCity(int index)
	{
		return index >= fullAddresses.size() || fullAddresses.get(index) == null ? null : fullAddresses.get(index).getCity();
	}

	public void setAddressCity(int index, String addressCity)
	{
		if (checkAddr(index, addressCity))
			fullAddresses.get(index).setCity(addressCity);
	}

	public String getAddressCountry(int index)
	{
		return index >= fullAddresses.size() || fullAddresses.get(index) == null ? null : fullAddresses.get(index).getCountry();
	}

	public void setAddressCountry(int index, String addressCountry)
	{
		if (checkAddr(index, addressCountry))
			fullAddresses.get(index).setCountry(addressCountry);
	}

	public String getAddressPostalCode(int index)
	{
		return index >= fullAddresses.size() || fullAddresses.get(index) == null ? null : fullAddresses.get(index).getPostalCode();
	}

	public void setAddressPostalCode(int index, String addressPostalCode)
	{
		if (checkAddr(index, addressPostalCode))
			fullAddresses.get(index).setPostalCode(addressPostalCode);
	}

	public String getAddressRegion(int index)
	{
		return index >= fullAddresses.size() || fullAddresses.get(index) == null ? null : fullAddresses.get(index).getRegion();
	}

	public void setAddressRegion(int index, String addressRegion)
	{
		if (checkAddr(index, addressRegion))
			fullAddresses.get(index).setRegion(addressRegion);
	}

	public String getContactNumber0()
	{
		return getContactNumber(0);
	}

	public void setContactNumber0(String contactNumber0)
	{
		setContactNumber(0, contactNumber0);
	}

	public String getContactNumber1()
	{
		return getContactNumber(1);
	}

	public void setContactNumber1(String contactNumber1)
	{
		setContactNumber(1, contactNumber1);
	}

	public String getContactNumber2()
	{
		return getContactNumber(2);
	}

	public void setContactNumber2(String contactNumber2)
	{
		setContactNumber(2, contactNumber2);
	}

	public String getContactPhoneOperator0()
	{
		return getContactOperator(0);
	}

	public void setContactPhoneOperator0(String contactPhoneOperator0)
	{
		setContactOperator(0, contactPhoneOperator0);
	}

	public String getContactPhoneOperator1()
	{
		return getContactOperator(1);
	}

	public void setContactPhoneOperator1(String contactPhoneOperator1)
	{
		setContactOperator(1, contactPhoneOperator1);
	}

	public String getContactPhoneOperator2()
	{
		return getContactOperator(2);
	}

	public void setContactPhoneOperator2(String contactPhoneOperator2)
	{
		setContactOperator(2, contactPhoneOperator2);
	}

	public String getContactType0()
	{
		return getContactType(0);
	}

	public void setContactType0(String contactType0)
	{
		setContactType(0, contactType0);
	}

	public String getContactType1()
	{
		return getContactType(1);
	}

	public void setContactType1(String contactType1)
	{
		setContactType(1, contactType1);
	}

	public String getContactType2()
	{
		return getContactType(2);
	}

	public void setContactType2(String contactType2)
	{
		setContactType(2, contactType2);
	}

	public String getAddressAfterCity0()
	{
		return getAddressAfterCity(0);
	}

	public void setAddressAfterCity0(String addressAfterCity0)
	{
		setAddressAfterCity(0, addressAfterCity0);
	}

	public String getAddressAfterCity1()
	{
		return getAddressAfterCity(1);
	}

	public void setAddressAfterCity1(String addressAfterCity1)
	{
		setAddressAfterCity(1, addressAfterCity1);
	}

	public String getAddressCity0()
	{
		return getAddressCity(0);
	}

	public void setAddressCity0(String addressCity0)
	{
		setAddressCity(0, addressCity0);
	}

	public String getAddressCity1()
	{
		return getAddressCity(1);
	}

	public void setAddressCity1(String addressCity1)
	{
		setAddressCity(1, addressCity1);
	}

	public String getAddressCountry0()
	{
		return getAddressCountry(0);
	}

	public void setAddressCountry0(String addressCountry0)
	{
		setAddressCountry(0, addressCountry0);
	}

	public String getAddressCountry1()
	{
		return getAddressCountry(1);
	}

	public void setAddressCountry1(String addressCountry1)
	{
		setAddressCountry(1, addressCountry1);
	}

	public String getAddressPostalCode0()
	{
		return getAddressPostalCode(0);
	}

	public void setAddressPostalCode0(String addressPostalCode0)
	{
		setAddressPostalCode(0, addressPostalCode0);
	}

	public String getAddressPostalCode1()
	{
		return getAddressPostalCode(1);
	}

	public void setAddressPostalCode1(String addressPostalCode1)
	{
		setAddressPostalCode(1, addressPostalCode1);
	}

	public String getAddressRegion0()
	{
		return getAddressRegion(0);
	}

	public void setAddressRegion0(String addressRegion0)
	{
		setAddressRegion(0, addressRegion0);
	}

	public String getAddressRegion1()
	{
		return getAddressRegion(1);
	}

	public void setAddressRegion1(String addressRegion1)
	{
		setAddressRegion(1, addressRegion1);
	}

	public String getRbTbBranch()
	{
		return tb + StringUtils.leftPad(StringHelper.getEmptyIfNull(osb), 4, '0') + StringUtils.leftPad(StringHelper.getEmptyIfNull(vsp), 2, '0');
	}

	public List<CardInfoImpl> getCardInfos()
	{
		return cardInfos;
	}

	public void setCardInfos(List<CardInfoImpl> cardInfos)
	{
		this.cardInfos = cardInfos;
	}

	public void setClientFound(boolean found)
	{
		this.clientFound = found;
	}

	public boolean isClientFound()
	{
		return clientFound;
	}

	public String getOsb()
	{
		return osb;
	}

	public void setOsb(String osb)
	{
		this.osb = osb;
	}

	public String getTb()
	{
		return tb;
	}

	public void setTb(String tb)
	{
		this.tb = tb;
	}

	public String getVsp()
	{
		return vsp;
	}

	public void setVsp(String vsp)
	{
		this.vsp = vsp;
	}

	/**
	 * @param personBirthday Дата рождения клиента.
	 */
	public void setPersonBirthday(Calendar personBirthday)
	{
		this.personBirthday = personBirthday;
	}

	/**
	 * @return Дата рождения клиента.
	 */
	public Calendar getPersonBirthday()
	{
		return personBirthday;
	}

	/**
	 * @param personBirthplace Место рождения клиента.
	 */
	public void setPersonBirthplace(String personBirthplace)
	{
		this.personBirthplace = personBirthplace;
	}

	/**
	 * @return Место рождения клиента.
	 */
	public String getPersonBirthplace()
	{
		return personBirthplace;
	}

	/**
	 * @param personTaxId ИНН клиента.
	 */
	public void setPersonTaxId(String personTaxId)
	{
		this.personTaxId = personTaxId;
	}

	/**
	 * @return ИНН клиента.
	 */
	public String getPersonTaxId()
	{
		return personTaxId;
	}

	/**
	 * @param personCitizenship Гражданство клиента.
	 */
	public void setPersonCitizenship(String personCitizenship)
	{
		this.personCitizenship = personCitizenship;
	}

	/**
	 * @return Гражданство клиента.
	 */
	public String getPersonCitizenship()
	{
		return personCitizenship;
	}

	/**
	 * @param personGender Пол клиента.
	 */
	public void setPersonGender(String personGender)
	{
		this.personGender = personGender;
	}

	/**
	 * @return Пол клиента.
	 */
	public String getPersonGender()
	{
		return personGender;
	}

	/**
	 * @param personResident Клиент-резидент.
	 */
	public void setPersonResident(Boolean personResident)
	{
		this.personResident = personResident;
	}

	/**
	 * @return Клиент-резидент.
	 */
	public Boolean isPersonResident()
	{
		return personResident;
	}

	/**
	 * @param personLastName Фамилия клиента.
	 */
	public void setPersonLastName(String personLastName)
	{
		this.personLastName = personLastName;
	}

	/**
	 * @return Фамилия клиента.
	 */
	public String getPersonLastName()
	{
		return personLastName;
	}

	/**
	 * @param personFirstName Имя клиента.
	 */
	public void setPersonFirstName(String personFirstName)
	{
		this.personFirstName = personFirstName;
	}

	/**
	 * @return Имя клиента.
	 */
	public String getPersonFirstName()
	{
		return personFirstName;
	}

	/**
	 * @param personMiddleName Отчество клиента.
	 */
	public void setPersonMiddleName(String personMiddleName)
	{
		this.personMiddleName = personMiddleName;
	}

	/**
	 * @return Отчество клиента.
	 */
	public String getPersonMiddleName()
	{
		return personMiddleName;
	}

	/**
	 * @param identityCardType Тип документа удостоверяющего личность.
	 */
	public void setIdentityCardType(String identityCardType)
	{
		this.identityCardType = identityCardType;
	}

	/**
	 * @return Тип документа удостоверяющего личность.
	 */
	public String getIdentityCardType()
	{
		return identityCardType;
	}

	/**
	 * @param identityCardSeries Серия документа удостоверяющего личность.
	 */
	public void setIdentityCardSeries(String identityCardSeries)
	{
		this.identityCardSeries = identityCardSeries;
	}

	/**
	 * @return Серия документа удостоверяющего личность.
	 */
	public String getIdentityCardSeries()
	{
		return identityCardSeries;
	}

	/**
	 * @param identityCardNumber Номер документа удостоверяющего личность.
	 */
	public void setIdentityCardNumber(String identityCardNumber)
	{
		this.identityCardNumber = identityCardNumber;
	}

	/**
	 * @return Номер документа удостоверяющего личность.
	 */
	public String getIdentityCardNumber()
	{
		return identityCardNumber;
	}

	/**
	 * @param identityCardIssuedBy Кем выдан ДУЛ.
	 */
	public void setIdentityCardIssuedBy(String identityCardIssuedBy)
	{
		this.identityCardIssuedBy = identityCardIssuedBy;
	}

	/**
	 * @return Кем выдан ДУЛ.
	 */
	public String getIdentityCardIssuedBy()
	{
		return identityCardIssuedBy;
	}

	/**
	 * @return Кем выдан ДУЛ.
	 */
	public String getIdentityCardIssuedCode()
	{
		return identitycardIssuedCode;
	}

	/**
	 * @param identityCardIssuedCode - Кем выдан ДУЛ
	 */
	public void setIdentityCardIssuedCode(String identityCardIssuedCode)
	{
		this.identitycardIssuedCode = identityCardIssuedCode;
	}

	/**
	 * @param identityCardIssueDate Когда выдан ДУЛ.
	 */
	public void setIdentityCardIssueDate(Calendar identityCardIssueDate)
	{
		this.identityCardIssueDate = identityCardIssueDate;
	}

	/**
	 * @return Когда выдан ДУЛ.
	 */
	public Calendar getIdentityCardIssueDate()
	{
		return identityCardIssueDate;
	}

	/**
	 * @param identityCardExpDate Срок действия ДУЛ.
	 */
	public void setIdentityCardExpDate(Calendar identityCardExpDate)
	{
		this.identityCardExpDate = identityCardExpDate;
	}

	/**
	 * @return Срок действия ДУЛ.
	 */
	public Calendar getIdentityCardExpDate()
	{
		return identityCardExpDate;
	}

	/**
	 * @param verified Признак верифицированного клиента.
	 */
	public void setVerified(boolean verified)
	{
		this.verified = verified;
	}

	/**
	 * @return Признак верифицированного клиента.
	 */
	public boolean isVerified()
	{
		return verified;
	}

	/**
	 * @param contactData нетипизированная информация о котнакте.
	 */
	public void setContactData(ContactData[] contactData)
	{
		contactDatas.clear();
		for (ContactData cd : contactData)
		{
			contactDatas.add(cd);
		}
	}

	/**
	 * @return нетипизированная информация о котнакте.
	 */
	public ContactData[] getContactData()
	{
		return contactDatas.toArray(new ContactData[contactDatas.size()]);
	}

	/**
	 * @param address адреса.
	 */
	public void setAddress(FullAddress[] address)
	{
		fullAddresses.clear();
		for (FullAddress fa : address)
		{
			fullAddresses.add(fa);
		}
	}

	/**
	 * @return адреса.
	 */
	public FullAddress[] getAddress()
	{
		return fullAddresses.toArray(new FullAddress[fullAddresses.size()]);
	}

	/**
	 * @return количество карт в заявке
	 */
	public int getCardCount()
	{
		return cardCount;
	}

	/**
	 * Задать количество карт в заявке
	 * @param cardCount - количество
	 */
	public void setCardCount(int cardCount)
	{
		this.cardCount = cardCount;
	}

	/**
	 * @return имя первой карты
	 */
	public String getFirstCardName()
	{
		return firstCardName;
	}

	/**
	 * Задать имя первой карты
	 * @param firstCardName - имя
	 */
	public void setFirstCardName(String firstCardName)
	{
		this.firstCardName = firstCardName;
	}

	/**
	 * @return валюту первой карты
	 */
	public String getFirstCardCurrency()
	{
		return firstCardCurrency;
	}

	/**
	 * Задать валюту первой карты
	 * @param firstCardCurrency - валюта
	 */
	public void setFirstCardCurrency(String firstCardCurrency)
	{
		this.firstCardCurrency = firstCardCurrency;
	}

	public String getConvertedBranchId()
	{
		return convertedBranchId;
	}

	public void setConvertedBranchId(String convertedBranchId)
	{
		this.convertedBranchId = convertedBranchId;
	}

	public String getConvertedAgencyId()
	{
		return convertedAgencyId;
	}

	public void setConvertedAgencyId(String convertedAgencyId)
	{
		this.convertedAgencyId = convertedAgencyId;
	}

	public String getConvertedRegionId()
	{
		return convertedRegionId;
	}

	public void setConvertedRegionId(String convertedRegionId)
	{
		this.convertedRegionId = convertedRegionId;
	}

	public String getConvertedRbTbBranchId()
	{
		return convertedRbTbBranchId;
	}

	public void setConvertedRbTbBranchId(String convertedRbTbBranchId)
	{
		this.convertedRbTbBranchId = convertedRbTbBranchId;
	}

	/**
	 * @return имена всех карт заявки
	 */
	public String getAllCardNames()
	{
		return allCardNames;
	}

	/**
	 * Задать стороку имен всех карт заявки
	 * @param allCardNames - строка имен
	 */
	public void setAllCardNames(String allCardNames)
	{
		this.allCardNames = allCardNames;
	}
}
