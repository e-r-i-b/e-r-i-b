package com.rssl.phizic.web.client.ext.sbrf.mobilebank;

import com.rssl.phizic.business.ext.sbrf.mobilebank.CardShortcut;
import com.rssl.phizic.business.ext.sbrf.mobilebank.RegistrationProfile;
import com.rssl.phizic.business.ext.sbrf.mobilebank.SmsCommand;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Erkin
 * @ created 07.05.2010
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"AbstractClassExtendsConcreteClass"})
public abstract class MobileBankFormBase extends ActionFormBase
{
	private String phoneCode;

	private String cardCode;

	/**
	 * Текущая регистрация
	 */
	private RegistrationProfile registration;

	/**
	 * Список всех регистраций
    */
	private List<RegistrationProfile> registrationProfiles;

	/**
	 * Текущая карта
	 */
	private CardShortcut card;

	/**
	 * Текущий список шаблонов SMS-запросов
	 */
	private List<SmsCommand> smsCommands;

	///////////////////////////////////////////////////////////////////////////

	public RegistrationProfile getRegistration()
	{
		return registration;
	}

	public void setRegistration(RegistrationProfile registration)
	{
		this.registration = registration;
	}

	public List<RegistrationProfile> getRegistrationProfiles()
	{
		return registrationProfiles;
	}

	public void setRegistrationProfiles(List<RegistrationProfile> registrationProfiles)
	{
		this.registrationProfiles = registrationProfiles;
	}

	public CardShortcut getCard()
	{
		return card;
	}

	public void setCard(CardShortcut card)
	{
		this.card = card;
	}

	public List<SmsCommand> getSmsCommands()
	{
		if (smsCommands == null)
			return null;
		return Collections.unmodifiableList(smsCommands);
	}

	public void setSmsCommands(List<SmsCommand> smsCommands)
	{
		if (smsCommands == null)
			this.smsCommands = null;
		else this.smsCommands = new ArrayList<SmsCommand>(smsCommands);
	}

	public String getPhoneCode()
	{
		return phoneCode;
	}

	public void setPhoneCode(String phoneCode)
	{
		this.phoneCode = phoneCode;
	}

	public String getCardCode()
	{
		return cardCode;
	}

	public void setCardCode(String cardCode)
	{
		this.cardCode = cardCode;
	}
}
