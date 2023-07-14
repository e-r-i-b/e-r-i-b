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
	 * ������������� � ���� ������ (�� getCode)
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
	 * @return ID ������ - ���������� ����
	 */
	public Long getId()
	{
	    return id;
	}

	/**
	 * @param id ID ������ - ���������� ����
	 */
	public void setId(Long id)
	{
	    this.id = id;
	}

	/**
	 * @return id ����� �� ������� �������
	 */
	public String getExternalId()
	{
	    return externalId;
	}

	/**
	 * @param externalId ������� ID �������
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
	 * ��������� ��� �� �������: "<�������>:<id>"
	 * ������� ���������� ���������� (getCodePrefix)
	 * @return ���������� � ������� ���� (�����������) ������ ���
	 */
	public final String getCode()
	{
		return getCodePrefix() + CODE_DELIMITER + getId();
	}

	/**
	 * @return ������� ���� ������ (�� getCode);
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
	 * �������� �� ����������� ���� ������ ������������ ������(�� ����� getCode())
	 * @param code ���������� ���
	 * @return ������������ ������
	 */
	public static long getIdFromCode(String code)
	{
		if (StringHelper.isEmpty(code))
		{
			throw new IllegalArgumentException("�������� �� ����� ���� ������");
		}
		int delimiterIndex = code.lastIndexOf(CODE_DELIMITER);
		if (delimiterIndex < 0)
		{
			throw new IllegalArgumentException("��� ����� ������������ ������ "+code);
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
	 * ���������� ������ � ������� ��������, ������������ ������� �� ����� � ������� �������� ������� ����������
	 * �� �������� ��� �� �������� 
	 *
	 * @return StoredResourceRefreshTimeoutConfig
	 */
	protected StoredResourceRefreshTimeoutConfig getStoredResourceConfig()
	{
		return ConfigFactory.getConfig(StoredResourceRefreshTimeoutConfig.class);
	}
}
