package com.rssl.phizic.web.client.userprofile.addressbook;

import com.rssl.phizic.business.profile.addressbook.Contact;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import org.apache.struts.action.ActionForm;

/**
 * ����� ��� ��������� ���������� � ��������
 * @author gladishev
 * @ created 09.10.2014
 * @ $Author$
 * @ $Revision$
 */

public class GetContactInfoForm extends ActionForm
{
	private String paymentType;
	private String contactId;
	private String phone;
	private String card;
	private String receiverAvatarPath;
	private Contact contact;
	private Pair<Client, Card> userInfo;
	private boolean limitExceeded = false;
	private boolean sberbankClient;
	private boolean externalSystemError;

	/**
	 * @return ����� ��������
	 */
	public String getPhone()
	{
		return phone;
	}

	/**
	 * ���������� ����� ��������
	 * @param phone - ����� ��������
	 */
	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	/**
	 * @return �������
	 */
	public Contact getContact()
	{
		return contact;
	}

	/**
	 * ���������� �������
	 * @param contact - �������
	 */
	public void setContact(Contact contact)
	{
		this.contact = contact;
	}

	/**
	 * ���������� ���������� � ��������
	 * @param userInfo - ���������� � ��������
	 */
	public void setUserInfo(Pair<Client, Card> userInfo)
	{
		this.userInfo = userInfo;
	}

	/**
	 * @return ���������� � ��������
	 */
	public Pair<Client, Card> getUserInfo()
	{
		return userInfo;
	}

	/**
	 * @return ������������� ��������
	 */
	public String getContactId()
	{
		return contactId;
	}

	/**
	 * ���������� ������������� ��������
	 * @param contactId - ������������� ��������
	 */
	public void setContactId(String contactId)
	{
		this.contactId = contactId;
	}

	/**
	 * @return ����� �����
	 */
	public String getCard()
	{
		return card;
	}

	/**
	 * ���������� ����� �����
	 * @param card - ����� �����
	 */
	public void setCard(String card)
	{
		this.card = card;
	}

	/**
	 * @return �������� �� ����� �������� � ���/���� �� ������ ��������
	 */
	public boolean isLimitExceeded()
	{
		return limitExceeded;
	}

	public void setLimitExceeded(boolean limitExceeded)
	{
		this.limitExceeded = limitExceeded;
	}

	/**
	 * @return ��� �������
	 */
	public String getPaymentType()
	{
		return paymentType;
	}

	/**
	 * ���������� ��� �������
	 * @param paymentType - ��� �������
	 */
	public void setPaymentType(String paymentType)
	{
		this.paymentType = paymentType;
	}

	/**
	 * @return ���� �� ������� ���������� �������
	 */
	public String getReceiverAvatarPath()
	{
		return receiverAvatarPath;
	}

	/**
	 * ���������� ���� �� ������� ���������� �������
	 * @param receiverAvatarPath - ���� �� �������
	 */
	public void setReceiverAvatarPath(String receiverAvatarPath)
	{
		this.receiverAvatarPath = receiverAvatarPath;
	}

	/**
	 * ������� ������� ���������.
	 * @param sberbankClient
	 */
	public void setSberbankClient(boolean sberbankClient)
	{
		this.sberbankClient = sberbankClient;
	}

	public boolean isSberbankClient()
	{
		return sberbankClient;
	}

	/**
	 * @return ���. ������ (����������, Stand-In) ���
	 */
	public boolean isExternalSystemError()
	{
		return externalSystemError;
	}

	/**
	 * @param externalSystemError ���. ������ (����������, Stand-In) ���
	 */
	public void setExternalSystemError(boolean externalSystemError)
	{
		this.externalSystemError = externalSystemError;
	}
}
