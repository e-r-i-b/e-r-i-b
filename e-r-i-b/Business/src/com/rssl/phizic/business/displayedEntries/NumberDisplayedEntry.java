package com.rssl.phizic.business.displayedEntries;

import java.io.Serializable;

/**
 * @author komarov
 * @ created 07.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class NumberDisplayedEntry implements Serializable
{
	private Long loginId;     // ������������� ������
	private String gridId;    // ������������� �����
	private Long recordCount; // ���������� ������������ ������� � �����

	public Long getLoginId()
	{
		return loginId;
	}

	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	public String getGridId()
	{
		return gridId;
	}

	public void setGridId(String gridId)
	{
		this.gridId = gridId;
	}

	public Long getRecordCount()
	{
		return recordCount;
	}

	public void setRecordCount(Long recordCount)
	{
		this.recordCount = recordCount;
	}
}
