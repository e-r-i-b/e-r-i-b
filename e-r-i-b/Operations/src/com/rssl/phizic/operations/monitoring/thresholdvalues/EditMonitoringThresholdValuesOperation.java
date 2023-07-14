package com.rssl.phizic.operations.monitoring.thresholdvalues;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.monitoring.MonitoringThresholdService;
import com.rssl.phizic.business.monitoring.MonitoringThresholdValues;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;

import java.util.List;

/**
 * @author mihaylov
 * @ created 24.02.2011
 * @ $Author$
 * @ $Revision$
 *
 * Операция редактирования параметров мониторинга
 */

public class EditMonitoringThresholdValuesOperation extends OperationBase implements EditEntityOperation
{
	private List<MonitoringThresholdValues> thresholdValues;

	/**
	 * инициализация операции
	 * @param departmentId идентификатор подраздления
	 * @throws BusinessException
	 */
	public void initialize(Long departmentId) throws BusinessException
	{
		Department department = MultiBlockModeDictionaryHelper.getLocalDepartment(departmentId);
		thresholdValues = ConfigFactory.getConfig(MonitoringThresholdService.class).getByDepartment(department);
	}

	public void save() throws BusinessException
	{
		ConfigFactory.getConfig(MonitoringThresholdService.class).addOrUpdate(thresholdValues);
	}

	public List<MonitoringThresholdValues> getEntity()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return thresholdValues;
	}
}
