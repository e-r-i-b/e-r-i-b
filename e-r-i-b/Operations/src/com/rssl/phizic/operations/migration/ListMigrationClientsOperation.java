package com.rssl.phizic.operations.migration;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.migration.MigrationConfig;
import com.rssl.phizic.business.migration.MigrationResult;
import com.rssl.phizic.business.migration.MigrationService;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.query.CustomExecutorQuery;
import com.rssl.phizic.dataaccess.query.CustomListExecutor;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akrenev
 * @ created 26.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * �������� ������ �������� ��� ��������
 */

public class ListMigrationClientsOperation extends OperationBase implements ListEntitiesOperation
{
	public static final String CLIENT_PARAMETER_NAME           = ClientForMigrationListExecutor.CLIENT_PARAMETER_NAME;
	public static final String DOCUMENT_PARAMETER_NAME         = ClientForMigrationListExecutor.DOCUMENT_PARAMETER_NAME;
	public static final String DEPARTMENT_PARAMETER_NAME       = ClientForMigrationListExecutor.DEPARTMENT_PARAMETER_NAME;
	public static final String BIRTHDAY_PARAMETER_NAME         = ClientForMigrationListExecutor.BIRTHDAY_PARAMETER_NAME;
	public static final String AGREEMENT_TYPE_PARAMETER_NAME   = ClientForMigrationListExecutor.AGREEMENT_TYPE_PARAMETER_NAME;
	public static final String AGREEMENT_NUMBER_PARAMETER_NAME = ClientForMigrationListExecutor.AGREEMENT_NUMBER_PARAMETER_NAME;

	private static final CustomListExecutor EXECUTOR = new ClientForMigrationListExecutor();
	private static final MigrationService migrationService = new MigrationService();

	/**
	 * @return ������ ������������� ��������� ��� ����������
	 */
	public List<Department> getDepartments() throws BusinessException
	{
		return AllowedDepartmentsUtil.getAllowedTerbanks();
	}

	/**
	 * @return ������ ����� ��������� ��������� ��� ����������
	 */
	public List<CreationType> getAgreementTypes()
	{
		List<CreationType> agreementTypes = new ArrayList<CreationType>();
		agreementTypes.add(CreationType.UDBO);
		agreementTypes.add(CreationType.CARD);
		return agreementTypes;
	}

	@Override
	public Query createQuery(String name)
	{
		return new CustomExecutorQuery(this, EXECUTOR);
	}

	private int getMigrationTimeout()
	{
		return ConfigFactory.getConfig(MigrationConfig.class).getMigrationThreadsCount();
	}

	/**
	 * ������������ �������
	 * @param id ������������� �������
	 * @return ��������� ��������
	 */
	public MigrationResult migrate(Long id) throws BusinessException
	{
		return migrationService.migrate(id, getMigrationTimeout());
	}
}
