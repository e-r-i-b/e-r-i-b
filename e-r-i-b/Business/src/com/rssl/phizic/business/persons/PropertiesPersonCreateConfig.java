package com.rssl.phizic.business.persons;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.config.build.BuildContextConfig;

/**
 * @author Omeliyanchuk
 * @ created 04.12.2006
 * @ $Author$
 * @ $Revision$
 */

public class PropertiesPersonCreateConfig extends PersonCreateConfig
{
	private Boolean registerChangesEnable;
	private Boolean agreementSignMandatory;
	private String agreementCreator;

	public PropertiesPersonCreateConfig(PropertyReader propertyReader)
	{
		super(propertyReader);
	}

	public Boolean getRegisterChangesEnable()
	{
		BuildContextConfig buildConfig= ConfigFactory.getConfig(BuildContextConfig.class);
		Boolean shadowDatabaseOn = buildConfig.isShadowDatabaseOn();
		if (!registerChangesEnable.equals(shadowDatabaseOn))
		{
			throw new RuntimeException("����������� ��������� �������. \"shadow.database.on\" � \"com.rssl.iccs.login.flow.register.changes.enable\" ������ ���� �����������.");
		}
		return registerChangesEnable;
	}

	public String getAgreementCreator()
	{
		return agreementCreator;
	}

	public Boolean getAgreementSignMandatory()
	{
		return agreementSignMandatory;
	}


	public void doRefresh()
	{
		registerChangesEnable           = getBoolProperty(PersonCreateConfig.REGISTER_CHANGES);
		agreementCreator                = getProperty(PersonCreateConfig.AGREMENT_NUMBER_CREATOR);
		agreementSignMandatory          = getBoolProperty(PersonCreateConfig.AGREMENT_SIGN_MANDATORY);

	}
}
