package com.rssl.phizic.web.settings;

import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.persons.PersonCreateConfig;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.config.authService.AuthServiceConfig;
import com.rssl.phizic.config.csa.CSAConfig;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.gate.GateInfoService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

/**
 * @author Omeliyanchuk
 * @ created 24.07.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * Сервис для чтения общих настроек системы из веб, которые влияют на работу системы
 */
public class FlowControlService
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final GateInfoService gateInfoService = GateSingleton.getFactory().service(GateInfoService.class);
	private static final DepartmentService departmentService = new DepartmentService();

	/**
	 * обязательна ли печать договора
	 * @return
	 */
	public static Boolean isAgreementSignMandatory()
	{
		try
		{
			PersonCreateConfig flowConfig = ConfigFactory.getConfig(PersonCreateConfig.class);
			return flowConfig.getAgreementSignMandatory();
		}
		catch (Exception e)
		{
			log.error("Ошибка определения обязательности печати договора", e);
			return null;
		}
	}

	/**
	 * способ получения счетов (IMPORT - импорт, MANUAL - ввод вручную, OFF - счета непредусмотрены)
	 * @return
	 */
	public static String getAccountInputMode()
	{
		try
		{
			Department department = departmentService.findById(EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee().getDepartmentId());
			return gateInfoService.getAccountInputMode(department).toString();
		}
		catch(Exception ex)
		{
			log.error("Ошибка определения способа получения счетов", ex);
			return "OFF";
		}
	}

	/**
	 * способ получения карт (IMPORT - импорт, MANUAL - ввод вручную, OFF - карты непредусмотрены)
	 * @return
	 */
	public static String getCardInputMode()
	{
		try
		{
			Department department = departmentService.findById(EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee().getDepartmentId());
			return gateInfoService.getCardInputMode(department).toString();
		}
		catch(Exception ex)
		{
			log.error("Ошибка определения способа получения карт", ex);
			return "OFF";
		}
	}

	/**
	 * Нужно ли вручную списывать платы или она списывается бек-офисом
	 * @return true - списывается бизнесом, false - списывается гейтом
	 */
	public static Boolean isNeedChargeOff()
	{
		try
		{
			Department department = departmentService.findById(EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee().getDepartmentId());
			if (department.isService())
			{
				return !gateInfoService.isNeedChargeOff(department);
			}
			else
				return false;

		}
		catch (InactiveExternalSystemException e)
		{
			throw e;
		}
		catch(Exception ex)
		{
			log.error("Ошибка определения необходимости списания платы вручную", ex);
			return null;
		}
	}

	public static Boolean useOwnAuthentication()
	{
		return ConfigFactory.getConfig(AuthServiceConfig.class).isUseOwnAuth();
	}

	/**
	 * Нужно ли показывать сообщение клиентам на странице входа в систему
	 * @return true - надо, false - не надо
	 */
	public static Boolean showClientLoginPageMessage()
	{
		return ConfigFactory.getConfig(CSAConfig.class).isCsaModeTransition();
	}	
}
