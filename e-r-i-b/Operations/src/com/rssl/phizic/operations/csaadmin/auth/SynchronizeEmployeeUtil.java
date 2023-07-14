package com.rssl.phizic.operations.csaadmin.auth;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.pfp.channel.Channel;
import com.rssl.phizic.business.dictionaries.pfp.channel.ChannelService;
import com.rssl.phizic.gate.employee.Employee;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author akrenev
 * @ created 09.12.2013
 * @ $Author$
 * @ $Revision$
 *
 * Утилитный класс обновления сотрудника
 */

public final class SynchronizeEmployeeUtil
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final DepartmentService departmentService = new DepartmentService();
	private static final ChannelService channelService = new ChannelService();

	private SynchronizeEmployeeUtil(){}

	/**
	 * Обновление бизнесового сотрудника на основе гейтового сотрудника
	 * @param destination - бизнесовый сотрудник
	 * @param source - гейтовый сотрудник
	 */
	public static final void update(com.rssl.phizic.business.employees.Employee destination, Employee source)
	{
		destination.setFirstName(source.getFirstName());
		destination.setSurName(source.getSurName());
		destination.setPatrName(source.getPatrName());
		destination.setInfo(source.getInfo());
		destination.setEmail(source.getEmail());
		destination.setMobilePhone(source.getMobilePhone());
		destination.setSUDIRLogin(source.getSUDIRLogin());

		try
		{
			Department employeeDepartment = departmentService.findByCode(source.getDepartment().getCode());
			destination.setDepartmentId(employeeDepartment.getId());
		}
		catch (BusinessException e)
		{
			log.error("Ошибка получения департамента сотрудника.",e);
		}

		destination.setCAAdmin(source.isCAAdmin());
		destination.setVSPEmployee(source.isVSPEmployee());

		destination.setManagerId(source.getManagerId());
		destination.setManagerPhone(source.getManagerPhone());
		destination.setManagerEMail(source.getManagerEMail());
		destination.setManagerLeadEMail(source.getManagerLeadEMail());

		String managerChannel = source.getManagerChannel();
		if (StringHelper.isEmpty(managerChannel))
			return;

		try
		{
			Channel channel = channelService.getByName(managerChannel);
			Long channelId = channel == null ? null : channel.getId();
			destination.setChannelId(channelId);
		}
		catch (BusinessException e)
		{
			log.error("Ошибка получения канала сотрудника.",e);
		}
	}
}
