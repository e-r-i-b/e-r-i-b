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
	 * true - ����� �������� � ������ �������, ���������������� ���������
	 * @return
	 */
	public abstract Boolean getRegisterChangesEnable();

	/**
	 * ��������� ����� ������ ��� �������� ������ ��������
 	 * @return ��� ������
	 */
	public abstract String getAgreementCreator();

	/**
	 * ����������� �� ���������� �������� ����� ������������
	 * @return
	 */
	public abstract Boolean getAgreementSignMandatory();
}
