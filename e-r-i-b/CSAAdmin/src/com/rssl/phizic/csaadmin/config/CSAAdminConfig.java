package com.rssl.phizic.csaadmin.config;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author mihaylov
 * @ created 27.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Конфиг приложения ЦСА Админ
 */
public class CSAAdminConfig extends Config
{
	private static final String NODE_URL_PATTERN_KEY = "ikfl.node.login.url";
	private static final String AUTH_TOKEN_LIFE_TIME_KEY = "csa.admin.auth.token.lifeTime";
	private static final String DEPARTMENT_ADMINS_LIMIT_KEY = "csa.admin.departments.admins.limit";
	private static final String MULTI_NODE_REQUEST_TIME_OUT_KEY = "csa.admin.multinode.request.timeOut";

	private String nodeUrlPattern;
	private int authTokenLifeTime;
	private int departmentAdminsLimit;
	private int multiNodeRequestTimeOut;

	/**
	 * Конструктор
	 */
	public CSAAdminConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * @return шаблон страницы поставторизации в блоке
	 */
	public String getNodeUrlPattern()
	{
		return nodeUrlPattern;
	}

	/**
	 * @return время жизни токена(в секундах)
	 */
	public int getAuthTokenLifeTime()
	{
		return authTokenLifeTime;
	}

	/**
	 * @return лимит администраторов при привязке подразделения
	 */
	public int getDepartmentAdminsLimit()
	{
		return departmentAdminsLimit;
	}

	/**
	 * @return таймаут ожидания ответов от блоков при запросе в несколько блоков(в миллисекундах)
	 */
	public int getMultiNodeRequestTimeOut()
	{
		return multiNodeRequestTimeOut;
	}

	public void doRefresh() throws ConfigurationException
	{
		this.nodeUrlPattern = getProperty(NODE_URL_PATTERN_KEY);
		this.authTokenLifeTime = Integer.valueOf(getProperty(AUTH_TOKEN_LIFE_TIME_KEY));
		this.departmentAdminsLimit = Integer.valueOf(getProperty(DEPARTMENT_ADMINS_LIMIT_KEY));
		this.multiNodeRequestTimeOut = Integer.valueOf(getProperty(MULTI_NODE_REQUEST_TIME_OUT_KEY));
	}
}
