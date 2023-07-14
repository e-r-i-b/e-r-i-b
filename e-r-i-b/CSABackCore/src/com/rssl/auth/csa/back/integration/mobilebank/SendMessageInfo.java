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
 * ���������� ��� �������� ���������
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
	 * @param cardNumber ����� �����
	 * @param messageInfo ���������� � ���������
	 * @param isCheckIMSI ������ �� �������� ����
	 * @param mode ����� ��������� �����������
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
	 * @param messageInfo ���������� � ���������
	 * @param isCheckIMSI ������ �� �������� ����
	 * @param mode ����� ��������� �����������
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
	 * @param profileId ������������� �������
	 * @param cardNumber ����� �����
     * @param messageInfo ���������� � ���������
     * @param isCheckIMSI ������ �� �������� ����
     * @param mode ����� ��������� �����������
	 */
	public SendMessageInfo(Long profileId, String cardNumber, MessageInfo messageInfo, boolean isCheckIMSI, GetRegistrationMode mode)
	{
		this(cardNumber, messageInfo, isCheckIMSI, mode);
		this.profileId = profileId;
	}

	/**
	 * ctor
	 * @param cardNumber ����� �����
	 * @param messageInfo ���������� � ���������
	 * @param isCheckIMSI ������ �� �������� ����
	 * @param mode ����� ��������� �����������
	 * @param excludedPhones ������ ���������, �� ������� �� ����� �������� ���������
	 */
	public SendMessageInfo(String cardNumber, MessageInfo messageInfo, boolean isCheckIMSI, GetRegistrationMode mode, Set<String> excludedPhones)
	{
		this(cardNumber, messageInfo, isCheckIMSI, mode);
		this.excludedPhones = excludedPhones;
	}

	/**
	 * ctor
	 * @param cardNumber ����� �����
	 * @param messageInfo ���������� � ���������
	 * @param isCheckIMSI ������ �� �������� ����
	 * @param mode ����� ��������� �����������
	 * @param excludedPhones ������ ���������, �� ������� �� ����� �������� ���������
	 */
	public SendMessageInfo(Long profileId, String cardNumber, MessageInfo messageInfo, boolean isCheckIMSI, GetRegistrationMode mode, Set<String> excludedPhones)
	{
		this(profileId, cardNumber, messageInfo, isCheckIMSI, mode);
		this.excludedPhones = excludedPhones;
	}

	/**
	 * @return ������������� �������
	 */
	public Long getProfileId()
	{
		return profileId;
	}

	/**
	 * @param profileId ������������� �������
	 */
	public void setProfileId(Long profileId)
	{
		this.profileId = profileId;
	}

	/**
	 * @return ����� �����
	 */
	public String getCardNumber()
	{
		return cardNumber;
	}

	/**
	 * @param cardNumber ����� �����
	 */
	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	/**
	 * @return ���������� � ���������
	 */
	public MessageInfo getMessageInfo()
	{
		return messageInfo;
	}

	/**
	 * @param messageInfo ���������� � ���������
	 */
	public void setMessageInfo(MessageInfo messageInfo)
	{
		this.messageInfo = messageInfo;
	}

	/**
	 * @return ������ �� �������� ����
	 */
	public boolean isCheckIMSI()
	{
		return isCheckIMSI;
	}

	/**
	 * @param checkIMSI ������ �� �������� ����
	 */
	public void setCheckIMSI(boolean checkIMSI)
	{
		isCheckIMSI = checkIMSI;
	}

	/**
	 * @return ����� ��������� �����������
	 */
	public GetRegistrationMode getMode()
	{
		return mode;
	}

	/**
	 * @param mode ����� ��������� �����������
	 */
	public void setMode(GetRegistrationMode mode)
	{
		this.mode = mode;
	}

	/**
	 * @return ��������� ������� ���������
	 */
	public Set<String> getPhones()
	{
		return phones;
	}

	/**
	 * ���������� ��������� ������� ���������
	 * @param phones ������ ���������
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
