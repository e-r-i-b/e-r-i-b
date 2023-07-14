package com.rssl.common.forms.validators.passwords;

import com.rssl.phizic.config.*;

/**
 * @author bogdanov
 * @ created 24.01.14
 * @ $Author$
 * @ $Revision$
 */

public class SmsPasswordConfig extends Config
{
	//default sms-password configs
	public static final String SMS_PASSWORD_LENGTH             = "com.rssl.iccs.sms.password.length";
	public static final String SMS_PASSWORD_CONFIRMATTEMTS     = "com.rssl.iccs.sms.password.confirmAttemts";
	public static final String SMS_PASSWORD_LIFETIME           = "com.rssl.iccs.sms.password.lifeTime";
	public static final String SMS_PASSWORD_ALLOWEDCHARS       = "com.rssl.iccs.sms.password.allowedChars";

	private String  smsPasswordAllowedChars;
	private int     smsPasswordLifeTime;
	private int     smsPasswordLength;
	private int     smsPasswordConfirmAttempts;

	public SmsPasswordConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		smsPasswordAllowedChars  = getProperty(SMS_PASSWORD_ALLOWEDCHARS);
		smsPasswordConfirmAttempts = getIntProperty(SMS_PASSWORD_CONFIRMATTEMTS);
		smsPasswordLength        = getIntProperty(SMS_PASSWORD_LENGTH);
		smsPasswordLifeTime      = getIntProperty(SMS_PASSWORD_LIFETIME);
	}

	public int getSmsPasswordLength()
	{
		return smsPasswordLength;
	}

	public int getSmsPasswordConfirmAttempts()
	{
		return smsPasswordConfirmAttempts;
	}

	public int getSmsPasswordLifeTime()
	{
		return smsPasswordLifeTime;
	}

	public String getSmsPasswordAllowedChars()
	{
		return smsPasswordAllowedChars;
	}
}
