package com.rssl.phizic.gate.fund;

import com.rssl.phizic.common.types.fund.Constants;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author osminin
 * @ created 30.12.14
 * @ $Author$
 * @ $Revision$
 *
 * ���������� �������� ������������� ������ �� ������ �� ���� �������
 */
public class ResponseExternalIdImpl extends ExternalIdImpl
{
	private static final int PROPERTIES_LENGTH = 3;

	private String senderPhone;

	/**
	 * ctor
	 * @param externalId ������� ������������� ������ �� ���� �������
	 * @throws GateException
	 */
	public ResponseExternalIdImpl(String externalId) throws GateException
	{
		super(externalId);
	}

	/**
	 * ������������� ������� ������������� ������ �� ������ �� ���� ������� ����
     * UID@����� �����@����� �������� ����������� �����
	 * @param senderPhone ����� �������� ����������� �����
	 * @return ������� ������������
	 */
	public static String generateExternalId(String senderPhone)
	{
		StringBuilder builder = ExternalIdImpl.getGenerateExternalIdBuilder();

		builder.append(Constants.EXTERNAL_ID_DELIMITER);
		builder.append(senderPhone);

		return builder.toString();
	}

	@Override
	protected int getPropertiesLength()
	{
		return PROPERTIES_LENGTH;
	}

	@Override
	protected void setParameters(String[] properties)
	{
		super.setParameters(properties);
		senderPhone = properties[2];
	}

	/**
	 * @return ����� �������� ����������� �����
	 */
	public String getSenderPhone()
	{
		return senderPhone;
	}
}
