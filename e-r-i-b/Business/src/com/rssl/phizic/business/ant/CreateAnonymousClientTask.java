package com.rssl.phizic.business.ant;

import com.rssl.phizic.business.schemes.AccessScheme;
import com.rssl.phizic.business.schemes.AccessSchemesConfig;
import com.rssl.phizic.business.schemes.DbAccessSchemesConfig;
import com.rssl.phizic.business.security.AnonymousClientCreator;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.utils.test.SafeTaskBase;
import org.apache.tools.ant.BuildException;

/**
 * @author Evgrafov
 * @ created 13.08.2007
 * @ $Author: bogdanov $
 * @ $Revision: 57189 $
 */
@SuppressWarnings({"JavaDoc"})
public class CreateAnonymousClientTask extends SafeTaskBase
{
	private String  userName;
	private boolean ignoreNullUserName = true;
	private String  schemeKey;

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public boolean isIgnoreNullUserName()
	{
		return ignoreNullUserName;
	}

	public String getSchemeKey()
	{
		return schemeKey;
	}

	public void setSchemeKey(String schemeKey)
	{
		this.schemeKey = schemeKey;
	}

	public void setIgnoreNullUserName(boolean ignoreNullUserName)
	{
		this.ignoreNullUserName = ignoreNullUserName;
	}

	public void safeExecute() throws Exception
	{
		SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);
		AccessSchemesConfig schemesConfig = ConfigFactory.getConfig(DbAccessSchemesConfig.class);

		if (userName == null)
			userName = securityConfig.getAnonymousClientName();

		if(userName == null)
		{
			if(ignoreNullUserName)
			{
				log("»м€ пользовател€ анонимного клиента не задано, выполнение таска остановлено");
				return;
			}
			else
			{
				throw new BuildException("Ќе задан userName");
			}
		}

		AccessScheme scheme;

		if(schemeKey != null)
		{
			scheme = schemesConfig.getByCode(schemeKey);
		}
		else
		{
			scheme = schemesConfig.getAnonymousClientAccessScheme();
		}

		if (scheme == null)
		{
			throw new RuntimeException("Ќе установлена схема доступа " +
					"дл€ встроенной учетной записи анонимного клиента");
		}

		new AnonymousClientCreator(userName, scheme).create();
	}

	@Override public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
}