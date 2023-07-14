package com.rssl.phizic.business.profile.addressbook;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.security.SecurityLogicException;

/**
 * Контакт адресной книги
 *
 * @author bogdanov
 * @ created 18.09.14
 * @ $Author$
 * @ $Revision$
 */

public class Contact   implements ConfirmableObject
{
	private Long id;
	/**
	 * Номер телефона.
	 */
	private String phone;
	/**
	 * Наименование контакта.
	 */
	private String fullName;

	/**
	 * Признак «Клиент Сбербанка».
	 */
	private boolean sberbankClient = false;

	/**
	 * Признак "Инкогнито".
	 */
	private boolean incognito = false;

	/**
	 * ФИО клиента Сбербанка.
	 */
	private String fio;

	/**
	 * Пользовательское имя (алиас)
	 */
	private String alias;

	/**
	 * Короткое пользовательское имя
	 */
	private String cutAlias;

	/**
	 * Аватар.
	 */
	private String avatarPath;
	/**
	 * Номер банковской карты.
	 */
	private String cardNumber;

	/**
	 * Категория контакта.
	 */
	private ContactCategory category = ContactCategory.NONE;

	/**
	 * Статус доверенности.
 	 */
	private boolean trusted = false;

	/**
	 * Частотность для P2P переводов.
	 */
	private int frequencypP2P = 0;

	/**
	 * Частотность для оплаты телефона контакта.
	 */
	private int frequencyPay = 0;

	/**
	 * Источник добавления.
	 */
	private AddedType addedBy = AddedType.ERIB;

	/**
	 * Статус контакта.
	 */
	private ContactStatus status = ContactStatus.HIDE;

	/**
	 * Владелец контакта.
	 */
	private CommonLogin owner;

	public Long getId()
	{
		return id;
	}

	public byte[] getSignableObject() throws SecurityException, SecurityLogicException
	{
		return new byte[0];
	}

	public void setConfirmStrategyType(ConfirmStrategyType confirmStrategyType)
	{
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return Источник добавления.
	 */
	public AddedType getAddedBy()
	{
		return addedBy;
	}

	/**
	 * @param addedBy Источник добавления.
	 */
	public void setAddedBy(AddedType addedBy)
	{
		this.addedBy = addedBy;
	}

	/**
	 * @return Пользовательское имя (алиас).
	 */
	public String getAlias()
	{
		return alias;
	}

	/**
	 * @param alias Пользовательское имя (алиас).
	 */
	public void setAlias(String alias)
	{
		this.alias = alias;
	}

	/**
	 * @return Аватар. (путь к аватару).
	 */
	public String getAvatarPath()
	{
		return avatarPath;
	}

	/**
	 * @param avatarPath Аватар.
	 */
	public void setAvatarPath(String avatarPath)
	{
		this.avatarPath = avatarPath;
	}

	/**
	 * @return Номер банковской карты.
	 */
	public String getCardNumber()
	{
		return cardNumber;
	}

	/**
	 * @param cardNumber Номер банковской карты.
	 */
	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	/**
	 * @return Категория контакта.
	 */
	public ContactCategory getCategory()
	{
		return category;
	}

	/**
	 * @param category Категория контакта.
	 */
	public void setCategory(ContactCategory category)
	{
		this.category = category;
	}

	/**
	 * @return Короткое пользовательское имя.
	 */
	public String getCutAlias()
	{
		return cutAlias;
	}

	/**
	 * @param cutAlias Короткое пользовательское имя.
	 */
	public void setCutAlias(String cutAlias)
	{
		this.cutAlias = cutAlias;
	}

	/**
	 * @return ФИО клиента Сбербанка.
	 */
	public String getFio()
	{
		return fio;
	}

	/**
	 * @param fio ФИО клиента Сбербанка.
	 */
	public void setFio(String fio)
	{
		this.fio = fio;
	}

	/**
	 * @return Частотность для оплаты телефона контакта.
	 */
	public int getFrequencyPay()
	{
		return frequencyPay;
	}

	/**
	 * @param frequencyPay Частотность для оплаты телефона контакта.
	 */
	public void setFrequencyPay(int frequencyPay)
	{
		this.frequencyPay = frequencyPay;
	}

	/**
	 * @return Частотность для P2P переводов.
	 */
	public int getFrequencypP2P()
	{
		return frequencypP2P;
	}

	/**
	 * @param frequencypP2P Частотность для P2P переводов.
	 */
	public void setFrequencypP2P(int frequencypP2P)
	{
		this.frequencypP2P = frequencypP2P;
	}

	/**
	 * @return Наименование контакта.
	 */
	public String getFullName()
	{
		return fullName;
	}

	/**
	 * @param fullName Наименование контакта.
	 */
	public void setFullName(String fullName)
	{
		this.fullName = fullName;
	}

	/**
	 * @return Признак "Инкогнито".
	 */
	public boolean isIncognito()
	{
		return incognito;
	}

	/**
	 * @param incognito Признак "Инкогнито".
	 */
	public void setIncognito(boolean incognito)
	{
		this.incognito = incognito;
	}

	/**
	 * @return Владелец контакта.
	 */
	public CommonLogin getOwner()
	{
		return owner;
	}

	/**
	 * @param owner Владелец контакта.
	 */
	public void setOwner(CommonLogin owner)
	{
		this.owner = owner;
	}

	/**
	 * @return Номер телефона.
	 */
	public String getPhone()
	{
		return phone;
	}

	/**
	 * @param phone Номер телефона.
	 */
	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	/**
	 * @return Признак «Клиент Сбербанка».
	 */
	public boolean isSberbankClient()
	{
		return sberbankClient;
	}

	/**
	 * @param sberbankClient Признак «Клиент Сбербанка».
	 */
	public void setSberbankClient(boolean sberbankClient)
	{
		this.sberbankClient = sberbankClient;
	}

	/**
	 * @return Статус контакта.
	 */
	public ContactStatus getStatus()
	{
		return status;
	}

	/**
	 * @param status Статус контакта.
	 */
	public void setStatus(ContactStatus status)
	{
		this.status = status;
	}

	/**
	 * @return Статус доверенности.
	 */
	public boolean isTrusted()
	{
		return trusted;
	}

	/**
	 * @param trusted Статус доверенности.
	 */
	public void setTrusted(boolean trusted)
	{
		this.trusted = trusted;
	}

	public boolean isYandexContact()
	{
		return fullName.startsWith("Яндекс-контакт");
	}
}
