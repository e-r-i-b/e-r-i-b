package com.rssl.phizic.operations.monitoring;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.employees.EmployeeService;
import com.rssl.phizic.business.monitoring.MonitoringReport;
import com.rssl.phizic.business.monitoring.MonitoringReportValuesService;
import com.rssl.phizic.business.monitoring.MonitoringThresholdService;
import com.rssl.phizic.business.monitoring.MonitoringThresholdValues;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.operations.OperationBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 01.03.2011
 * @ $Author$
 * @ $Revision$
 */

public class GetMonitoringReportValuesOperation extends OperationBase
{
	private MonitoringReportValuesService monitoringReportValuesService = new MonitoringReportValuesService();
	private DepartmentService departmentService = new DepartmentService();
	private EmployeeService employeeService = new EmployeeService();
		
	private Map<MonitoringReport,Long> monitoringReportValues;
	private List<MonitoringThresholdValues> thresholdValueses;
	private Department department;

	public void initialize(Long departmentId) throws BusinessException
	{
		department = chooseDepartment(departmentId);

		monitoringReportValues = new HashMap<MonitoringReport,Long>();
		thresholdValueses = new ArrayList<MonitoringThresholdValues>();

		if(department != null)//если есть подходящий департамент, то получаем данные по мониторингу
		{
			String TB = department.getCode().getFields().get("region");
			for(MonitoringReport report : MonitoringReport.values())
			{
				Long reportValue = monitoringReportValuesService.executeMonitoringReport(report.toString(), TB);
				monitoringReportValues.put(report,reportValue);
			}
			thresholdValueses = ConfigFactory.getConfig(MonitoringThresholdService.class).getByDepartment(department);
		}
	}

	private Department chooseDepartment(Long departmentId) throws BusinessException
	{
		EmployeeData employeeData = EmployeeContext.getEmployeeDataProvider().getEmployeeData();
		Employee employee = employeeData.getEmployee();
		Department localDepartment = null;
		//если передали департамент, который необходимо мониторить, то получаем его
		if(departmentId != null)
		{
			localDepartment = departmentService.findById(departmentId);
			//если полученный департамент не является тербанком, то его мониторить не будем
			if(!DepartmentService.isTB(localDepartment))
				return null;
		}
		//инача получаем тербанк к которому привязан клиент
		else
		{
			localDepartment = departmentService.getTB(departmentService.findById(employee.getDepartmentId()));
		}
		if(!AllowedDepartmentsUtil.isDepartmentAllowed(localDepartment))
			return null;

		return localDepartment;
	}

	public Map<MonitoringReport, Long> getMonitoringReportValues()
	{
		return monitoringReportValues;
	}

	public List<MonitoringThresholdValues> getThresholdValueses()
	{
		return thresholdValueses;
	}

	public Long getDepartmentId()
	{
		return department.getId();
	}
}
