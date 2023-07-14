package com.rssl.phizic.config.fund;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author osminin
 * @ created 01.10.14
 * @ $Author$
 * @ $Revision$
 *
 * ������ ��� ������ � ��������������
 */
public class FundConfig extends Config
{
	private static final String BROADCAST_COMMIT_PACK_SIZE_PROPERTY = "com.rssl.iccs.fund.broadcast.commit.pack.size";
	private static final String ACCUMULATED_PACK_SIZE_PROPERTY      = "com.rssl.iccs.fund.accumulated.pack.size";

	private int broadcastCommitPackSize;
	private int accumulatedPackSize;

	/**
	 * ����� ������ ������ ����������� ������ �����������.
	 * @param reader �����.
	 */
	public FundConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		broadcastCommitPackSize = getIntProperty(BROADCAST_COMMIT_PACK_SIZE_PROPERTY);
		accumulatedPackSize = getIntProperty(ACCUMULATED_PACK_SIZE_PROPERTY);
	}

	/**
	 * @return ���������� �������, ������� ���������� �� ��� ��� ���������
	 */
	public int getBroadcastCommitPackSize()
	{
		return broadcastCommitPackSize;
	}

	/**
	 * @return ������ ����� ��� ��������� �������, ����������� ����� ����������� �����
	 */
	public int getAccumulatedPackSize()
	{
		return accumulatedPackSize;
	}
}
