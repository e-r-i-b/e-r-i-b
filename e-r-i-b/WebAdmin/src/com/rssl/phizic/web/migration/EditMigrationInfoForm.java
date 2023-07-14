package com.rssl.phizic.web.migration;

import com.rssl.phizic.business.migration.TotalMigrationInfo;
import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author akrenev
 * @ created 26.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * ����� �������������� ��������� ��������
 */

public class EditMigrationInfoForm extends ActionFormBase
{
	private TotalMigrationInfo migrationInfo;
	private Long currentId;
	private Long batchSize;
	private long dataTimeout;

	/**
	 * @return ���������� � ��������
	 */
	@SuppressWarnings("UnusedDeclaration") //jsp
	public TotalMigrationInfo getMigrationInfo()
	{
		return migrationInfo;
	}

	/**
	 * ������ ���������� � ��������
	 * @param migrationInfo ����������
	 */
	public void setMigrationInfo(TotalMigrationInfo migrationInfo)
	{
		this.migrationInfo = migrationInfo;
	}

	/**
	 * @return ������������� ����������� ������
	 */
	public Long getCurrentId()
	{
		return currentId;
	}

	/**
	 * ������ ������������� ����������� ������
	 * @param currentId �������������
	 */
	public void setCurrentId(Long currentId)
	{
		this.currentId = currentId;
	}

	/**
	 * @return ������ ����� ����������� ��������
	 */
	public Long getBatchSize()
	{
		return batchSize;
	}

	/**
	 * ������ ������ ����� ����������� ��������
	 * @param batchSize ������
	 */
	@SuppressWarnings("UnusedDeclaration") //ActionServlet
	public void setBatchSize(Long batchSize)
	{
		this.batchSize = batchSize;
	}

	/**
	 * @return ����� ������������ ������
	 */
	@SuppressWarnings("UnusedDeclaration") // jsp
	public long getDataTimeout()
	{
		return dataTimeout;
	}

	/**
	 * ������ ����� ������������ ������
	 * @param dataTimeout ����� ������������ ������
	 */
	public void setDataTimeout(long dataTimeout)
	{
		this.dataTimeout = dataTimeout;
	}
}
