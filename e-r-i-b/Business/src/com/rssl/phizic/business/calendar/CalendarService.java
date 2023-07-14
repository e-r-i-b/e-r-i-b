package com.rssl.phizic.business.calendar;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.receptiontimes.ReceptionTime;
import com.rssl.phizic.business.receptiontimes.ReceptionTimeService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.documents.GateDocument;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Gainanov
 * @ created 24.03.2009
 * @ $Author$
 * @ $Revision$
 */
public class CalendarService extends MultiInstanceCalendarService
{

	/**
	 *
	 * Определяет является ли проверяемая дата выходным днем или нет
	 *
	 * @param date проверяемая дата
	 * @param document документ
	 * @return boolean
	 * @throws BusinessException
	 */
	public boolean isHoliday(Calendar date, BusinessDocument document) throws BusinessException
	{
		ReceptionTimeService service = new ReceptionTimeService();
		ReceptionTime receptionTime = service.getRecepionTime((Department)document.getDepartment(), ((GateDocument)document).getType());
		return isHolidayInCalendar(date, receptionTime);
	}

	/**
	 *
	 * Определяет является ли проверяемая дата выходным днем или нет
	 *
	 * @param date проверяемая дата
	 * @param department департамент клиента
	 * @param paymentType тип платежа
	 * @return boolean
	 * @throws BusinessException
	 */
	public boolean isHoliday(Calendar date, Department department, Class paymentType) throws BusinessException
	{
		ReceptionTime receptionTime = new ReceptionTimeService().getRecepionTime(department, paymentType);
		return isHolidayInCalendar(date, receptionTime);
	}

	/**
	 *
	 * Определяет является ли проверяемая дата выходным днем или нет
	 *
	 * @param date проверяемая дата
	 * @param department департамент клиента
	 * @param paymentType тип платежа
	 * @return boolean
	 * @throws BusinessException
	 */
	public boolean isHoliday(Calendar date, Department department, String paymentType) throws BusinessException
	{
		ReceptionTime receptionTime = new ReceptionTimeService().getRecepionTime(department, paymentType);
		return isHolidayInCalendar(date, receptionTime);
	}

	private boolean isHolidayInCalendar(Calendar date, ReceptionTime receptionTime) throws BusinessException
	{
		//receptionTime = null, когда пользователь выбрал "стандартный" календарь
		WorkCalendar calendar = (receptionTime == null) ? null : receptionTime.getCalendar();

		if (calendar == null)
			return isStandardHoliday(date);
		WorkDay workDay = findInWorkCalendar(calendar, date);
		if (workDay != null)
			return !workDay.getIsWorkDay();
		else               // если наш день не встретился в перечне рабочих-выходных, отличающихся от стандартных
			return isStandardHoliday(date);
	}

	/**
	 *
	 * @param date дата
	 * @return является ли переданная дата стандартным выходным (субб. или воскр)
	 */
	public boolean isStandardHoliday(Calendar date)
	{
		return (date.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
				date.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
		);
	}

	/**
	 * находит переданную дату в переданном рабочем календаре
	 * @param calendar - раб. календарь
	 * @param date - дата.
	 * @return workDay
	 */
	public WorkDay findInWorkCalendar(final WorkCalendar calendar, final Calendar date) throws BusinessException
	{
		if (calendar == null)
			throw new NullPointerException("Аргумент 'calendar' не может быть null");
		if (date == null)
			throw new NullPointerException("Аргумент 'date' не может быть null");
		
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<WorkDay>()
			{
				public WorkDay run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.calendar.findWorkDay");
					query.setLong("calendarId", calendar.getId());
					query.setCalendar("extr_date", date);
					query.setMaxResults(1);
					return (WorkDay) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * находит даты в переданном временном промежутке и для заданного тб
	 * @param fromDate - с какой даты ищем
	 * @param toDate - до какой даты ищем
	 * @param tb - по какому тб ищем
	 * @return List<WorkDay>
	 * @throws BusinessException
	 */

	public List<WorkDay> findWorkDaysForPeriod(final Date fromDate, final Date toDate, final String tb) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<WorkDay>>()
			{
				public List<WorkDay> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.calendar.findWorkDaysForPeriod");
					query.setString("tb", tb);
					query.setDate("fromDate", fromDate);
					query.setDate("toDate", toDate);
					return (List<WorkDay>) query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить по дате и ТБ является ли день рабочим
	 * @param date дата
	 * @param tb ТБ
	 * @return true если этот день в указанном департаменте является рабочим
	 * @throws Exception
	 */
	public boolean getIsWorkDayByTB(final Calendar date, final String tb) throws Exception
	{
		WorkDay workDay = HibernateExecutor.getInstance().execute(new HibernateAction<WorkDay>()
		{
			public WorkDay run(Session session) throws Exception
			{
				Query query = session.getNamedQuery("com.rssl.phizic.business.calendar.findWorkDaysForPeriod");
				query.setLong("tb", Long.parseLong(tb));
				query.setCalendar("fromDate", date);
				query.setCalendar("toDate", date);
				query.setMaxResults(1);
				return (WorkDay) query.uniqueResult();
			}
		});
		if(workDay==null)
		{
			return !isStandardHoliday(date);
		}
		else
		{
			return workDay.getIsWorkDay();
		}
	}
}
