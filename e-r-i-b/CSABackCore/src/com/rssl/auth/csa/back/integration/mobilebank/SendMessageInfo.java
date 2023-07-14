package com.rssl.auth.csa.back.integration.mobilebank;

import com.rssl.phizic.gate.mobilebank.GetRegistrationMode;
import com.rssl.phizic.gate.mobilebank.MessageInfo;

import java.util.Set;

/**
 * @author osminin
 * @ created 30.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Информация для отправки сообщения
 */
public class SendMessageInfo
{
	private Long profileId;
	private String cardNumber;
	private MessageInfo messageInfo;
	private boolean isCheckIMSI;
	private Set<String> phones;
	private GetRegistrationMode mode;
	private Set<String> excludedPhones;

	/**
	 * ctor
	 * @param cardNumber номер карты
	 * @param messageInfo информация о сообщении
	 * @param isCheckIMSI делать ли проверку ИМСИ
	 * @param mode режим получения регистрации
	 */
	public SendMessageInfo(String cardNumber, MessageInfo messageInfo, boolean isCheckIMSI, GetRegistrationMode mode)
	{
		this.cardNumber = cardNumber;
		this.messageInfo = messageInfo;
		this.isCheckIMSI = isCheckIMSI;
		this.mode = mode;
	}

	/**
	 * ctor
	 * @param messageInfo информация о сообщении
	 * @param isCheckIMSI делать ли проверку ИМСИ
	 * @param mode режим получения регистрации
	 */
	public SendMessageInfo(Set<String> phones, MessageInfo messageInfo, boolean isCheckIMSI, GetRegistrationMode mode)
	{
		this.phones = phones;
		this.messageInfo = messageInfo;
		this.isCheckIMSI = isCheckIMSI;
		this.mode = mode;
	}

	/**
	 * ctor
	 * @param profileId идентификатор профиля
	 * @param cardNumber номер карты
     * @param messageInfo информация о сообщении
     * @param isCheckIMSI делать ли проверку ИМСИ
     * @param mode режим получения регистрации
	 */
	public SendMessageInfo(Long profileId, String cardNumber, MessageInfo messageInfo, boolean isCheckIMSI, GetRegistrationMode mode)
	{
		this(cardNumber, messageInfo, isCheckIMSI, mode);
		this.profileId = profileId;
	}

	/**
	 * ctor
	 * @param cardNumber номер карты
	 * @param messageInfo информация о сообщении
	 * @param isCheckIMSI делать ли проверку ИМСИ
	 * @param mode режим получения регистрации
	 * @param excludedPhones список телефонов, на которые не нужно отсылать сообщение
	 */
	public SendMessageInfo(String cardNumber, MessageInfo messageInfo, boolean isCheckIMSI, GetRegistrationMode mode, Set<String> excludedPhones)
	{
		this(cardNumber, messageInfo, isCheckIMSI, mode);
		this.excludedPhones = excludedPhones;
	}

	/**
	 * ctor
	 * @param cardNumber номер карты
	 * @param messageInfo информация о сообщении
	 * @param isCheckIMSI делать ли проверку ИМСИ
	 * @param mode режим получения регистрации
	 * @param excludedPhones список телефонов, на которые не нужно отсылать сообщение
	 */
	public SendMessageInfo(Long profileId, String cardNumber, MessageInfo messageInfo, boolean isCheckIMSI, GetRegistrationMode mode, Set<String> excludedPhones)
	{
		this(profileId, cardNumber, messageInfo, isCheckIMSI, mode);
		this.excludedPhones = excludedPhones;
	}

	/**
	 * @return идентификатор профиля
	 */
	public Long getProfileId()
	{
		return profileId;
	}

	/**
	 * @param profileId идентификатор профиля
	 */
	public void setProfileId(Long profileId)
	{
		this.profileId = profileId;
	}

	/**
	 * @return номер карты
	 */
	public String getCardNumber()
	{
		return cardNumber;
	}

	/**
	 * @param cardNumber номер карты
	 */
	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	/**
	 * @return информация о сообщении
	 */
	public MessageInfo getMessageInfo()
	{
		return messageInfo;
	}

	/**
	 * @param messageInfo информация о сообщении
	 */
	public void setMessageInfo(MessageInfo messageInfo)
	{
		this.messageInfo = messageInfo;
	}

	/**
	 * @return делать ли проверку ИМСИ
	 */
	public boolean isCheckIMSI()
	{
		return isCheckIMSI;
	}

	/**
	 * @param checkIMSI делать ли проверку ИМСИ
	 */
	public void setCheckIMSI(boolean checkIMSI)
	{
		isCheckIMSI = checkIMSI;
	}

	/**
	 * @return режим получения регистрации
	 */
	public GetRegistrationMode getMode()
	{
		return mode;
	}

	/**
	 * @param mode режим получения регистрации
	 */
	public void setMode(GetRegistrationMode mode)
	{
		this.mode = mode;
	}

	/**
	 * @return множество номеров телефонов
	 */
	public Set<String> getPhones()
	{
		return phones;
	}

	/**
	 * Установить множество номеров телефонов
	 * @param phones номера телефонов
	 */
	public void setPhones(Set<String> phones)
	{
		this.phones = phones;
	}

	public Set<String> getExcludedPhones()
	{
		return excludedPhones;
	}

	public void setExcludedPhones(Set<String> excludedPhones)
	{
		this.excludedPhones = excludedPhones;
	}
}
