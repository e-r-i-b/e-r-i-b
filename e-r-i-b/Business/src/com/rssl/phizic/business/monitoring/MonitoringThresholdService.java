package com.rssl.phizic.business.monitoring;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.config.*;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mihaylov
 * @ created 24.02.2011
 * @ $Author$
 * @ $Revision$
 */

public class MonitoringThresholdService extends Config
{
	private static final String PREFICS = "com.rssl.iccs.monitoring.threshold.";
	private static final String WARNING = ".warning";
	private static final String ERROR   = ".error";
	private static final String DEFAULT = "DEFAULT";

	private static final SimpleService simpleService = new SimpleService();

	public MonitoringThresholdService(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * Добавить или обновить настройку пороговых значений для мониторинга
	 * @param values - список пороговых значений
	 * @throws BusinessException
	 */
	public void addOrUpdate(List<MonitoringThresholdValues> values) throws BusinessException
	{
		for(MonitoringThresholdValues value: values)
			simpleService.addOrUpdate(value);
	}

	/**
	 * Поиск пороговых значений для мониторинга для указанного департамента
	 * @param department - департамент
	 * @return пороговых значений мониторинга для указанного департамента
	 * @throws BusinessException
	 */
	public List<MonitoringThresholdValues> getByDepartment(Department department) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(MonitoringThresholdValues.class);
		criteria.add(Expression.eq("department", department));
		criteria.addOrder(Order.asc("id"));
		List<MonitoringThresholdValues> thresholdValueses = simpleService.find(criteria);
		//если пороговых значений в базе нет, то возвращаем значения по умолчанию
		if(CollectionUtils.isEmpty(thresholdValueses))
			return getNewThresholdValues(department);
		return thresholdValueses;
	}

	/**
	 * @param department - департамент
	 * @return список пороговых значений по умолчанию для отчетов
	 */
	private List<MonitoringThresholdValues> getNewThresholdValues(Department department)
	{
		List<MonitoringThresholdValues> values = new ArrayList<MonitoringThresholdValues>();
		Long defaultWarningValue = Long.valueOf(getProperty(PREFICS + DEFAULT + WARNING));
		Long defaultErrorValue = Long.valueOf(getProperty(PREFICS + DEFAULT + ERROR));
		for(MonitoringReport report : MonitoringReport.values())
		{
			Long warningThreshold = defaultWarningValue;
			Long errorThreshold = defaultErrorValue;
			//получаем пороговые значения по умолчанию для данного отчета
			String warningValue = getProperty(PREFICS + report + WARNING);
			String errorValue = getProperty(PREFICS + report + ERROR);
			//если пороговые значения по умолчанию для данного отчета заданы, то используем их
			if(!StringHelper.isEmpty(warningValue))
				warningThreshold = Long.valueOf(warningValue);
			if(!StringHelper.isEmpty(errorValue))
				errorThreshold = Long.valueOf(errorValue);

			MonitoringThresholdValues value = new MonitoringThresholdValues();
			value.setDepartment(department);
			value.setReport(report);
			value.setWarningThreshold(warningThreshold);
			value.setErrorThreshold(errorThreshold);
			values.add(value);
		}
		return values;
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
	}
}
