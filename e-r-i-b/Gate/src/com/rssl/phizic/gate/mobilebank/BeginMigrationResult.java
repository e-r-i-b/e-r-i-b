package com.rssl.phizic.gate.mobilebank;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.List;

/**
 * ���������, ������������ �� ������� ������ �������� � ���.
 * @author Puzikov
 * @ created 18.02.14
 * @ $Author$
 * @ $Revision$
 */

public class BeginMigrationResult
{
	//������ ������ � ���
	private List<MbkConnectionInfo> mbkConnectionInfo;

	//���������� �� ������� ��������, ��������������� �� ���.
	private Long migrationId;

	public List<MbkConnectionInfo> getMbkConnectionInfo()
	{
		return mbkConnectionInfo;
	}

	public void setMbkConnectionInfo(List<MbkConnectionInfo> mbkConnectionInfo)
	{
		this.mbkConnectionInfo = mbkConnectionInfo;
	}

	public Long getMigrationId()
	{
		return migrationId;
	}

	public void setMigrationId(Long migrationId)
	{
		this.migrationId = migrationId;
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("mbkConnectionInfo", mbkConnectionInfo)
				.append("migrationId", migrationId)
				.toString();
	}
}
