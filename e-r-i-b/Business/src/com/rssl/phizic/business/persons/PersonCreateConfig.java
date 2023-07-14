package com.rssl.phizic.business.persons;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author Omeliyanchuk
 * @ created 04.12.2006
 * @ $Author$
 * @ $Revision$
 */

public abstract class PersonCreateConfig extends Config
{
	public static final String REGISTER_CHANGES         = "com.rssl.iccs.login.flow.register.changes.enable";
	public static final String AGREMENT_NUMBER_CREATOR  = "com.rssl.iccs.login.agrement.numberCreator";
	public static final String AGREMENT_SIGN_MANDATORY  = "com.rssl.iccs.login.flow.agreement.sign.enable";

	protected PersonCreateConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * true - после изменеи€ в анкете клиента, зарегистрировать изменени€
	 * @return
	 */
	public abstract Boolean getRegisterChangesEnable();

	/**
	 * получение имени класса дл€ создани€ номера договора
 	 * @return им€ класса
	 */
	public abstract String getAgreementCreator();

	/**
	 * ќб€зательно ли подписание договора перед подключением
	 * @return
	 */
	public abstract Boolean getAgreementSignMandatory();
}
