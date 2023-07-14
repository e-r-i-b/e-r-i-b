package com.rssl.phizic.web.common.socialApi.ext.sbrf.userprofile;

import com.rssl.common.forms.Form;
import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author EgorovaA
 * @ created 14.08.13
 * @ $Author$
 * @ $Revision$
 *
 * Форма получения настроек оповещений
 */
public class NotificationSettingsForm extends ActionFormBase
{
	/**
	 * Тип настройки
	 */
	// оповещение о входе с в систему
	private String notificationType;
	// оповещение о получении письма из службы помощи
	private String mailNotificationType;
	// оповещение об исполнении операций
	private String deliveryNotificationType;

	/**
	 * Типы оповещений
	 */
	// по email
	private String email;
	// по телефону (номер телефона) - номера телефонов для оповещения о входе в систему
	private String phone;
	// push-уведомления (указание платформы устройств клиента)
	private String push;
	// по телефону (номер телефона) - номера телефонов для оповещений о письмах
	private String phoneForMail;

	public String getNotificationType()
	{
		return notificationType;
	}

	public void setNotificationType(String notificationType)
	{
		this.notificationType = notificationType;
	}

	public String getMailNotificationType()
	{
		return mailNotificationType;
	}

	public void setMailNotificationType(String mailNotificationType)
	{
		this.mailNotificationType = mailNotificationType;
	}

	public String getDeliveryNotificationType()
	{
		return deliveryNotificationType;
	}

	public void setDeliveryNotificationType(String deliveryNotificationType)
	{
		this.deliveryNotificationType = deliveryNotificationType;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getPush()
	{
		return push;
	}

	public void setPush(String push)
	{
		this.push = push;
	}

	public String getPhoneForMail()
	{
		return phoneForMail;
	}

	public void setPhoneForMail(String phoneForMail)
	{
		this.phoneForMail = phoneForMail;
	}
}
