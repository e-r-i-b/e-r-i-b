package com.rssl.phizic.business.profile.addressbook;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.security.SecurityLogicException;

/**
 * ������� �������� �����
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
	 * ����� ��������.
	 */
	private String phone;
	/**
	 * ������������ ��������.
	 */
	private String fullName;

	/**
	 * ������� ������� ���������.
	 */
	private boolean sberbankClient = false;

	/**
	 * ������� "���������".
	 */
	private boolean incognito = false;

	/**
	 * ��� ������� ���������.
	 */
	private String fio;

	/**
	 * ���������������� ��� (�����)
	 */
	private String alias;

	/**
	 * �������� ���������������� ���
	 */
	private String cutAlias;

	/**
	 * ������.
	 */
	private String avatarPath;
	/**
	 * ����� ���������� �����.
	 */
	private String cardNumber;

	/**
	 * ��������� ��������.
	 */
	private ContactCategory category = ContactCategory.NONE;

	/**
	 * ������ ������������.
 	 */
	private boolean trusted = false;

	/**
	 * ����������� ��� P2P ���������.
	 */
	private int frequencypP2P = 0;

	/**
	 * ����������� ��� ������ �������� ��������.
	 */
	private int frequencyPay = 0;

	/**
	 * �������� ����������.
	 */
	private AddedType addedBy = AddedType.ERIB;

	/**
	 * ������ ��������.
	 */
	private ContactStatus status = ContactStatus.HIDE;

	/**
	 * �������� ��������.
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
	 * @return �������� ����������.
	 */
	public AddedType getAddedBy()
	{
		return addedBy;
	}

	/**
	 * @param addedBy �������� ����������.
	 */
	public void setAddedBy(AddedType addedBy)
	{
		this.addedBy = addedBy;
	}

	/**
	 * @return ���������������� ��� (�����).
	 */
	public String getAlias()
	{
		return alias;
	}

	/**
	 * @param alias ���������������� ��� (�����).
	 */
	public void setAlias(String alias)
	{
		this.alias = alias;
	}

	/**
	 * @return ������. (���� � �������).
	 */
	public String getAvatarPath()
	{
		return avatarPath;
	}

	/**
	 * @param avatarPath ������.
	 */
	public void setAvatarPath(String avatarPath)
	{
		this.avatarPath = avatarPath;
	}

	/**
	 * @return ����� ���������� �����.
	 */
	public String getCardNumber()
	{
		return cardNumber;
	}

	/**
	 * @param cardNumber ����� ���������� �����.
	 */
	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	/**
	 * @return ��������� ��������.
	 */
	public ContactCategory getCategory()
	{
		return category;
	}

	/**
	 * @param category ��������� ��������.
	 */
	public void setCategory(ContactCategory category)
	{
		this.category = category;
	}

	/**
	 * @return �������� ���������������� ���.
	 */
	public String getCutAlias()
	{
		return cutAlias;
	}

	/**
	 * @param cutAlias �������� ���������������� ���.
	 */
	public void setCutAlias(String cutAlias)
	{
		this.cutAlias = cutAlias;
	}

	/**
	 * @return ��� ������� ���������.
	 */
	public String getFio()
	{
		return fio;
	}

	/**
	 * @param fio ��� ������� ���������.
	 */
	public void setFio(String fio)
	{
		this.fio = fio;
	}

	/**
	 * @return ����������� ��� ������ �������� ��������.
	 */
	public int getFrequencyPay()
	{
		return frequencyPay;
	}

	/**
	 * @param frequencyPay ����������� ��� ������ �������� ��������.
	 */
	public void setFrequencyPay(int frequencyPay)
	{
		this.frequencyPay = frequencyPay;
	}

	/**
	 * @return ����������� ��� P2P ���������.
	 */
	public int getFrequencypP2P()
	{
		return frequencypP2P;
	}

	/**
	 * @param frequencypP2P ����������� ��� P2P ���������.
	 */
	public void setFrequencypP2P(int frequencypP2P)
	{
		this.frequencypP2P = frequencypP2P;
	}

	/**
	 * @return ������������ ��������.
	 */
	public String getFullName()
	{
		return fullName;
	}

	/**
	 * @param fullName ������������ ��������.
	 */
	public void setFullName(String fullName)
	{
		this.fullName = fullName;
	}

	/**
	 * @return ������� "���������".
	 */
	public boolean isIncognito()
	{
		return incognito;
	}

	/**
	 * @param incognito ������� "���������".
	 */
	public void setIncognito(boolean incognito)
	{
		this.incognito = incognito;
	}

	/**
	 * @return �������� ��������.
	 */
	public CommonLogin getOwner()
	{
		return owner;
	}

	/**
	 * @param owner �������� ��������.
	 */
	public void setOwner(CommonLogin owner)
	{
		this.owner = owner;
	}

	/**
	 * @return ����� ��������.
	 */
	public String getPhone()
	{
		return phone;
	}

	/**
	 * @param phone ����� ��������.
	 */
	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	/**
	 * @return ������� ������� ���������.
	 */
	public boolean isSberbankClient()
	{
		return sberbankClient;
	}

	/**
	 * @param sberbankClient ������� ������� ���������.
	 */
	public void setSberbankClient(boolean sberbankClient)
	{
		this.sberbankClient = sberbankClient;
	}

	/**
	 * @return ������ ��������.
	 */
	public ContactStatus getStatus()
	{
		return status;
	}

	/**
	 * @param status ������ ��������.
	 */
	public void setStatus(ContactStatus status)
	{
		this.status = status;
	}

	/**
	 * @return ������ ������������.
	 */
	public boolean isTrusted()
	{
		return trusted;
	}

	/**
	 * @param trusted ������ ������������.
	 */
	public void setTrusted(boolean trusted)
	{
		this.trusted = trusted;
	}

	public boolean isYandexContact()
	{
		return fullName.startsWith("������-�������");
	}
}
