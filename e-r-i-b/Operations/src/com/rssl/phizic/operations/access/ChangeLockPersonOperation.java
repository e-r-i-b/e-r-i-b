package com.rssl.phizic.operations.access;

import com.rssl.phizic.auth.BlockingReasonType;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.LoginBlock;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.employees.EmployeeService;
import com.rssl.phizic.business.log.operations.LogParametersInfoBuilder;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.person.PersonOperationBase;
import com.rssl.phizic.security.SecurityDbException;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author Kidyaev
 * @ created 20.01.2006
 * @ $Author$
 * @ $Revision$
 *
 * операция блокировки и разблокировки клиента
 */

public class ChangeLockPersonOperation extends OperationBase<UserRestriction>
{
	private static final SecurityService securityService = new SecurityService();
	protected static final EmployeeService employeeService = new EmployeeService();

	private CommonLogin login;
	private StringBuilder logInfo = new StringBuilder();

	/**
	 * инициализация операции
	 * @param loginSource источник логина
	 * @throws BusinessException
	 */
	public void initialize(PersonLoginSource loginSource) throws BusinessException
	{
		ActivePerson person = loginSource.getPerson();
		PersonOperationBase.checkRestriction(getRestriction(), person);
		this.login = loginSource.getLogin();
	}

	/**
	 * Блокировка клиента или сотрудника вручную
	 * @param reason - причина блокировки (вводится вручную администратором или сотрудником)
	 * @param blockedFrom - дата начала блокировки
	 * @param blockedUntil - дата окончания блокировки, если нет - null
	 * @return блокировка
	 */
	@Transactional
	public LoginBlock lock(String reason, Date blockedFrom, Date blockedUntil) throws BusinessLogicException, BusinessException
	{
		try
		{
			CommonLogin employeeLogin = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee().getLogin();
			updateLogInfoForLock(reason, blockedFrom, blockedUntil, employeeLogin.getId());
			return securityService.lock(login, blockedFrom, blockedUntil, BlockingReasonType.employee, reason, employeeLogin.getId());
		}
		catch (SecurityDbException e)
		{
			throw new BusinessLogicException("Ошибка блокировки клиента с loginId=" + login.getId(), e);
		}
	}

	/**
	 * снятие блокировки с клиента
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	@Transactional
	public void unlock() throws BusinessException, BusinessLogicException
	{
		try
		{
			Date blockUntil = Calendar.getInstance().getTime();
			updateLogInfoForUnlock(blockUntil);
			securityService.unlock(login, false, null, blockUntil);
		}
		catch (SecurityDbException e)
		{
			throw new BusinessLogicException("Ошибка снятия блокировки с клиента с loginId=" + login.getId(), e);
		}
	}

	/**
	 * @return строка для лога
	 */
	public StringBuilder getLogInfo()
	{
		return logInfo;
	}

	private void updateLogInfoForLock(String reason, Date blockedFrom, Date blockedUntil, Long employeeLogin) throws BusinessException
	{
		LogParametersInfoBuilder logBuilder = new LogParametersInfoBuilder();
		logInfo.append(logBuilder.stringUserLoginInfo(login, "Информация о блокируемом"));
		logBuilder.appendBlockingInfo("Параметры блокировки", logInfo, BlockingReasonType.employee, reason, blockedFrom, blockedUntil, employeeLogin);
	}

	private void updateLogInfoForUnlock(Date blockUntil) throws BusinessException
	{
		LogParametersInfoBuilder logBuilder = new LogParametersInfoBuilder();
		logInfo.append(logBuilder.stringUserLoginInfo(login, "Информация о разблокируемом"));
		List<LoginBlock> blocks = securityService.getBlocksForLogin(login ,new GregorianCalendar().getTime(),null);
		if(blocks.isEmpty()) logInfo.append(logBuilder.stringBuilder("Снятые блокировки", "нет"));
		for (LoginBlock block: blocks)
		{
			Date until = block.getBlockedUntil();
			logBuilder.appendBlockingInfo("Параметры снятой блокировки", logInfo, block.getReasonType(), block.getReasonDescription(), block.getBlockedFrom(), until == null? blockUntil: until, block.getEmployee());
		}
	}
}
