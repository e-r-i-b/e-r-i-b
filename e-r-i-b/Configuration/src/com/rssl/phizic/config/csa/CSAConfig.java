package com.rssl.phizic.config.csa;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.utils.StringHelper;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author Dorzhinov
 * @ created 05.03.2012
 * @ $Author$
 * @ $Revision$
 */
public class CSAConfig extends Config
{
	public static final String SHOW_CLIENT_LOGIN_PAGE_MESSAGE_PROPERTY = "csa.mode.transition";
	private static final String DATASOURCE_PROPERTY = "datasource.name";

	private Pattern loginPattern; //паттерн логина
    private Pattern aliasPattern; //паттерн алиаса
	private int passwordLifeTime;
	private boolean csaModeTransition;
	private String dataSourceName;

	public CSAConfig(PropertyReader reader)
	{
		super(reader);
	}

	public void doRefresh() throws ConfigurationException
	{
		loginPattern = readAndCompile(getProperty("csa.login.regexp"), "csa.login.regexp");
		aliasPattern = readAndCompile(getProperty("csa.alias.regexp"), "csa.alias.regexp");
		passwordLifeTime = getIntProperty("com.rssl.iccs.password.lifetime");
		csaModeTransition = getBoolProperty(SHOW_CLIENT_LOGIN_PAGE_MESSAGE_PROPERTY);
		dataSourceName = getProperty(DATASOURCE_PROPERTY);
	}

	private Pattern readAndCompile(String propertyValue, String key)
    {
        String regexp = propertyValue;

        if(StringHelper.isEmpty(regexp))
            throw new ConfigurationException("Не задан regexp " + key);

        try
        {
            return Pattern.compile(regexp);
        }
        catch (PatternSyntaxException e)
        {
            throw new ConfigurationException("Некорректный regexp " + key, e);
        }
    }

    public Pattern getLoginPattern()
    {
        if (loginPattern == null)
            throw new ConfigurationException("login pattern is null");
        return loginPattern;
    }

    public Pattern getAliasPattern()
    {
        if (aliasPattern == null)
            throw new ConfigurationException("alias pattern is null");
        return aliasPattern;
    }

	/**
	 * @return время жизни пароля в днях.
	 */
	public int getPasswordLifeTime()
	{
		return passwordLifeTime;
	}

	public boolean isCsaModeTransition()
	{
		return csaModeTransition;
	}

	public String getDataSourceName()
	{
		return dataSourceName;
	}
}
