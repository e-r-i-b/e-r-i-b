package com.rssl.phizic.operations.migration;

import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.clients.list.ClientInformationService;
import com.rssl.phizic.business.migration.MigrationConfig;
import com.rssl.phizic.business.migration.MigrationInfoService;
import com.rssl.phizic.business.migration.TotalMigrationInfo;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author akrenev
 * @ created 26.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * �������� ������ � ���������� ��������
 */

public class EditMigrationInfoOperation extends OperationBase
{
	private static final MigrationInfoService migrationInfoService = new MigrationInfoService();
	private static final ClientInformationService clientInformationService = new ClientInformationService();

	private TotalMigrationInfo migrationInfo;
	private long batchSize;

	/**
	 * ���������������� ��������
	 */
	public void initialize() throws BusinessException
	{
		migrationInfo = migrationInfoService.getInfo();
	}

	/**
	 * ���������������� ��������
	 * @param currentId ������������� ������� ������
	 */
	public void initialize(Long currentId) throws BusinessException
	{
		migrationInfo = migrationInfoService.getInfo(currentId);
	}

	/**
	 * @return ���������� � ��������
	 */
	public TotalMigrationInfo getMigrationInfo() throws BusinessException
	{
		return migrationInfo;
	}

	/**
	 * ������ ������ ����� ����������� ��������
	 * @param batchSize ������
	 */
	public void setBatchSize(long batchSize)
	{
		this.batchSize = batchSize;
	}

	private int getTaskCount()
	{
		return ConfigFactory.getConfig(MigrationConfig.class).getMigrationThreadsCount();
	}

	/**
	 * ������������� �������� ��������
	 */
	public void initializeMigration() throws BusinessException, BusinessLogicException
	{
		Long nodeId = ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber();
		Long count = clientInformationService.getTemporaryNodeClientsCount(nodeId);
		initializeMigration(count);
	}


	private Long getMigrationInfoId()
	{
		return migrationInfo == null ? null : migrationInfo.getId();
	}
	/**
	 * ������������� �������� ��������
	 */
	private void initializeMigration(Long count) throws BusinessException, BusinessLogicException
	{
		migrationInfo = migrationInfoService.createMigrationTask(getMigrationInfoId(), count);
	}

	/**
	 * ��������� ������� ��������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void startMigration() throws BusinessException, BusinessLogicException
	{
		if (getMigrationInfoId() == null)
			initializeMigration(null);
		migrationInfo = migrationInfoService.startMigration(getMigrationInfoId() , batchSize, getTaskCount());
	}

	/**
	 * ���������� ������� ��������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void stopMigration() throws BusinessException, BusinessLogicException
	{
		migrationInfo = migrationInfoService.stopMigration(getMigrationInfoId());
	}
}
