package com.rssl.phizic.gate.fund;

import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.common.types.fund.Constants;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author osminin
 * @ created 29.12.14
 * @ $Author$
 * @ $Revision$
 *
 * ���������� �������� ������������� ������� �� ���� �������
 */
public class ExternalIdImpl
{
	private static final int PROPERTIES_LENGTH = 2;

	private String uid;
	private Long nodeNumber;

	/**
	 * ctor
	 * @param externalId ������� �������������
	 */
	public ExternalIdImpl(String externalId) throws GateException
	{
		if (StringHelper.isEmpty(externalId))
		{
			throw new IllegalArgumentException("������� ������������� �� ����� ���� null.");
		}

		String[] properties = externalId.split(Constants.EXTERNAL_ID_DELIMITER);
		if (properties.length < getPropertiesLength())
		{
			throw new GateException("������� ������������� ������ ��������� " + getPropertiesLength() + " ��������� ����� �����������.");
		}
		setParameters(properties);
	}

	/**
	 * ������������� ������� ������������� ������� �� ���� ������� ����
	 * UID@����� �����
	 * @return ������� �������������
	 */
	public static String generateExternalId()
	{
		return getGenerateExternalIdBuilder().toString();
	}

	protected static StringBuilder getGenerateExternalIdBuilder()
	{
		StringBuilder builder = new StringBuilder();

		builder.append(new RandomGUID().getStringValue());
		builder.append(Constants.EXTERNAL_ID_DELIMITER);
		builder.append(ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber());

		return builder;
	}

	/**
	 * @return ���������� �������������
	 */
	public String getUid()
	{
		return uid;
	}

	/**
	 * @return ����� �����
	 */
	public Long getNodeNumber()
	{
		return nodeNumber;
	}

	protected int getPropertiesLength()
	{
		return PROPERTIES_LENGTH;
	}

	protected void setParameters(String[] properties)
	{
		uid = properties[0];
		nodeNumber = Long.valueOf(properties[1]);
	}
}
