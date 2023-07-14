package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.StoredResourceRefreshTimeoutConfig;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;

import java.io.Serializable;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 11.10.2005 Time: 17:26:40 */
public abstract class ExternalResourceLinkBase implements ExternalResourceLink, Serializable
{
	/**
	 * Разделителель в коде ссылки (см getCode)
	 */
	public static final String CODE_DELIMITER = ":";

	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

    private Long    id;
	private Long loginId;
	private String  externalId;

	protected ExternalResourceLinkBase()
    {
    }

	/**
	 * @return ID записи - суррогатый ключ
	 */
	public Long getId()
	{
	    return id;
	}

	/**
	 * @param id ID записи - суррогатый ключ
	 */
	public void setId(Long id)
	{
	    this.id = id;
	}

	/**
	 * @return id счета во внешней системе
	 */
	public String getExternalId()
	{
	    return externalId;
	}

	/**
	 * @param externalId Внешний ID ресурса
	 */
	public void setExternalId(String externalId)
	{
	    this.externalId = externalId;
	}

	public Long getLoginId()
	{
	    return loginId;
	}

	public void setLoginId(Long loginId)	{
	    this.loginId = loginId;
	}

	/**
	 * формируем код по правилу: "<префикс>:<id>"
	 * префикс определяют наследники (getCodePrefix)
	 * @return уникальный в разрезе всех (разнотипных) ссылок код
	 */
	public final String getCode()
	{
		return getCodePrefix() + CODE_DELIMITER + getId();
	}

	/**
	 * @return префикс кода ссылки (см getCode);
	 */
	public abstract String getCodePrefix();

	public String getName()
	{
		throw new UnsupportedOperationException();
	}

	public String getNumber()
	{
		throw new UnsupportedOperationException();
	}

	public Currency getCurrency()
	{
		throw new UnsupportedOperationException();
	}

	public String getDescription()
	{
		throw new UnsupportedOperationException();
	}

	public Class getResourceClass()
    {
        return this.getClass();
    }

	public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || !(o instanceof ExternalResourceLink)) return false;

        final ExternalResourceLinkBase that = (ExternalResourceLinkBase) o;

        if (!StringHelper.equalsNullIgnore(externalId, that.externalId))
	        return false;

        return true;
    }

    public int hashCode()
    {
	    return externalId.hashCode();
    }

	/**
	 * Получить из уникального кода ссылки идентфикатор ссылки(см метод getCode())
	 * @param code уникальный код
	 * @return идентфикатор ссылки
	 */
	public static long getIdFromCode(String code)
	{
		if (StringHelper.isEmpty(code))
		{
			throw new IllegalArgumentException("Параметр не может быть пустым");
		}
		int delimiterIndex = code.lastIndexOf(CODE_DELIMITER);
		if (delimiterIndex < 0)
		{
			throw new IllegalArgumentException("Код имеет некорректный формат "+code);
		}
		return Long.valueOf(code.substring(delimiterIndex + 1));
	}

	public Boolean getShowInSystem()
	{
		return true;
	}

	public Class getStoredResourceType()
	{
		return null;
	}

	/**
	 *
	 * Возвращает конфиг с помощью которого, определяется истекло ли время в течении которого оффлайн информация
	 * по продукту еще не устарела 
	 *
	 * @return StoredResourceRefreshTimeoutConfig
	 */
	protected StoredResourceRefreshTimeoutConfig getStoredResourceConfig()
	{
		return ConfigFactory.getConfig(StoredResourceRefreshTimeoutConfig.class);
	}
}
