package com.rssl.phizic.web.security;

import com.rssl.phizic.business.StaticPersonData;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.operations.DefaultLogDataReader;
import com.rssl.phizic.logging.operations.LogWriter;
import com.rssl.phizic.logging.operations.OperationLogFactory;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.security.SecurityUtil;
import com.rssl.phizic.web.util.DepartmentViewUtil;

import java.util.*;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

/**
 * @author Omeliyanchuk
 * @ created 28.01.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Счетчик для подсчета кол-ва сессий.
 * Также см. AuthenticationManager.incrementSessionCounter
 */
public class SessionCountListener extends BaseCloseSessionListener
{
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);
	private static final Object CREATE_COUNTER_LOCKER = new Object();
	private static final Map<Application, UserCounter> counters = new HashMap<Application, UserCounter>();

	/**
	 * @return счетчики сессий
	 */
	public static Map<Application, UserCounter> getCounters()
	{
		return Collections.unmodifiableMap(counters);
	}

	/**
	 * @param application тип приложения
	 * @return счетчик сессий
	 */
	public static UserCounter getCounter(Application application)
	{
		UserCounter counter = counters.get(application);
		if (counter == null)
		{
			synchronized (CREATE_COUNTER_LOCKER)
			{
				counter = counters.get(application);
				if (counter == null)
				{
					counter = new UserCounter();
					counters.put(application, counter);
				}
			}
		}
		return counter;
	}

	public void sessionCreated(HttpSessionEvent httpSessionEvent){}

	@Override protected void doSessionDestroyed(HttpSessionEvent httpSessionEvent)
	{
		HttpSession session = httpSessionEvent.getSession();
		try
		{
			if( session!=null )
			{
				//получаем текущее приложение
				//смотрим, что удаляется сессия залогиненного пользователя.
				if(session.getAttribute(SecurityUtil.LOGIN_COMPLETE_KEY)!=null)
				{
					fillLogThreadContext(session, application);

					if (application != null)
					{
						//получаем для этого приложения счетчик, если нет - игнорируем.
						UserCounter counter = counters.get(application);
						if(counter!=null)
						{
							ApplicationConfig applicationConfig = ApplicationConfig.getIt();
							String fullAddr = applicationConfig.getApplicationInstanceName();

							Long terBank = DepartmentViewUtil.getCurrentDepartmentTBCode(session);
							long counterValue = counter.decrementAndGet(terBank);
							StringBuilder builder = new StringBuilder();
							builder.append("Количество сессий в приложении ");
							builder.append(application);
							builder.append(" уменьшилось до ");
							builder.append(counterValue);
							builder.append(" на машине с адресом ");
							builder.append(fullAddr);
							builder.append(".");
							builder.append(" Для тербанка ");
							builder.append(terBank);
							builder.append(".");

							// записываем в USERLOG
							LogWriter logWriter = OperationLogFactory.getLogWriter();
							DefaultLogDataReader reader = new DefaultLogDataReader(builder.toString());
							if (application==Application.PhizIA)
							{
								reader.setKey(com.rssl.phizic.logging.Constants.COUNT_ACTIVE_SESSION_KEY_ADMIN);
							}
							else
							{
								reader.setKey(com.rssl.phizic.logging.Constants.COUNT_ACTIVE_SESSION_KEY_CLIENT);
							}
							reader.setOperationKey(com.rssl.phizic.security.config.Constants.COUNT_ACTIVE_SESSION);
							logWriter.writeSecurityOperation(reader, Calendar.getInstance(), Calendar.getInstance());
						}
					}

					LogThreadContext.clear();
				}
			}
		}
		catch (Throwable ex)
		{
			//ошибку бросать не будем, т.к. это по сути дебажная информация
			log.debug("Ошибка при уменьшении значения счетчика активных сессий", ex);
		}
	}

	private void fillLogThreadContext(final HttpSession session, final Application application)
	{
		LogThreadContext.clear();
		LogThreadContext.setSessionId(session.getId());

		Long departmentId = 0L;
		switch (application)
		{
			case PhizIA:
			{
				EmployeeData employeeData = (EmployeeData) session.getAttribute(EmployeeData.class.getName());
				if (employeeData == null)
				{
					break;
				}

				Employee employee = employeeData.getEmployee();
				LogThreadContext.setFirstName(employee.getFirstName());
				LogThreadContext.setSurName(employee.getSurName());
				LogThreadContext.setPatrName(employee.getPatrName());
				LogThreadContext.setLoginId(employee.getLogin().getId());
				LogThreadContext.setUserId(employee.getLogin().getUserId());

				departmentId = employee.getDepartmentId();
				break;
			}

			default:
			{
				PersonData personData = (PersonData) session.getAttribute(StaticPersonData.class.getName());
				if (personData == null)
				{
					break;
				}

				ActivePerson person = personData.getPerson();
				LogThreadContext.setFirstName(person.getFirstName());
				LogThreadContext.setSurName(person.getSurName());
				LogThreadContext.setPatrName(person.getPatrName());
				LogThreadContext.setLoginId(personData.isGuest() ? null : person.getLogin().getId());
				LogThreadContext.setUserId(person.getLogin().getUserId());
				LogThreadContext.setBirthday(person.getBirthDay());

				PersonDocument document = PersonHelper.getPersonPassportWay(person);

				if (document != null)
				{
					LogThreadContext.setSeries(document.getDocumentSeries());
					LogThreadContext.setNumber(document.getDocumentNumber());
				}

				departmentId = person.getDepartmentId();
			}
		}

		if (departmentId == 0L)
		{
			return;
		}

		Department department = DepartmentViewUtil.getDepartmentById(departmentId);
		LogThreadContext.setDepartmentName(department.getName());
		LogThreadContext.setDepartmentCode(department.getCode().toString());
		LogThreadContext.setDepartmentRegion(department.getRegion());
		LogThreadContext.setDepartmentOSB(department.getOSB());
		LogThreadContext.setDepartmentVSP(department.getVSP());

	}
}
