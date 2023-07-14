package com.rssl.phizic.web.client.userprofile.addressbook;

import com.rssl.phizic.business.profile.addressbook.Contact;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import org.apache.struts.action.ActionForm;

/**
 * Форма для получения информации о контакте
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
	 * @return Номер телефона
	 */
	public String getPhone()
	{
		return phone;
	}

	/**
	 * Установить номер телефона
	 * @param phone - Номер телефона
	 */
	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	/**
	 * @return контакт
	 */
	public Contact getContact()
	{
		return contact;
	}

	/**
	 * Установить контакт
	 * @param contact - контакт
	 */
	public void setContact(Contact contact)
	{
		this.contact = contact;
	}

	/**
	 * Установить информацию о контакте
	 * @param userInfo - информация о контакте
	 */
	public void setUserInfo(Pair<Client, Card> userInfo)
	{
		this.userInfo = userInfo;
	}

	/**
	 * @return информация о контакте
	 */
	public Pair<Client, Card> getUserInfo()
	{
		return userInfo;
	}

	/**
	 * @return идентификатор контакта
	 */
	public String getContactId()
	{
		return contactId;
	}

	/**
	 * Установить идентификатор контакта
	 * @param contactId - идентификатор контакта
	 */
	public void setContactId(String contactId)
	{
		this.contactId = contactId;
	}

	/**
	 * @return номер карты
	 */
	public String getCard()
	{
		return card;
	}

	/**
	 * Установить номер карты
	 * @param card - номер карты
	 */
	public void setCard(String card)
	{
		this.card = card;
	}

	/**
	 * @return превышен ли лимит запросов в МБК/ЕРМБ по номеру телефона
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
	 * @return тип платежа
	 */
	public String getPaymentType()
	{
		return paymentType;
	}

	/**
	 * Установить тип платежа
	 * @param paymentType - тип платежа
	 */
	public void setPaymentType(String paymentType)
	{
		this.paymentType = paymentType;
	}

	/**
	 * @return путь до аватара получателя платежа
	 */
	public String getReceiverAvatarPath()
	{
		return receiverAvatarPath;
	}

	/**
	 * Установить путь до аватара получателя платежа
	 * @param receiverAvatarPath - путь до аватара
	 */
	public void setReceiverAvatarPath(String receiverAvatarPath)
	{
		this.receiverAvatarPath = receiverAvatarPath;
	}

	/**
	 * Признак клиента сбербанка.
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
	 * @return тех. ошибка (техперерыв, Stand-In) МБК
	 */
	public boolean isExternalSystemError()
	{
		return externalSystemError;
	}

	/**
	 * @param externalSystemError тех. ошибка (техперерыв, Stand-In) МБК
	 */
	public void setExternalSystemError(boolean externalSystemError)
	{
		this.externalSystemError = externalSystemError;
	}
}
