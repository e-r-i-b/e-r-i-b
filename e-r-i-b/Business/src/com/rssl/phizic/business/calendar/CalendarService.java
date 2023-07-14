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
	 * ���������� �������� �� ����������� ���� �������� ���� ��� ���
	 *
	 * @param date ����������� ����
	 * @param document ��������
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
	 * ���������� �������� �� ����������� ���� �������� ���� ��� ���
	 *
	 * @param date ����������� ����
	 * @param department ����������� �������
	 * @param paymentType ��� �������
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
	 * ���������� �������� �� ����������� ���� �������� ���� ��� ���
	 *
	 * @param date ����������� ����
	 * @param department ����������� �������
	 * @param paymentType ��� �������
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
		//receptionTime = null, ����� ������������ ������ "�����������" ���������
		WorkCalendar calendar = (receptionTime == null) ? null : receptionTime.getCalendar();

		if (calendar == null)
			return isStandardHoliday(date);
		WorkDay workDay = findInWorkCalendar(calendar, date);
		if (workDay != null)
			return !workDay.getIsWorkDay();
		else               // ���� ��� ���� �� ���������� � ������� �������-��������, ������������ �� �����������
			return isStandardHoliday(date);
	}

	/**
	 *
	 * @param date ����
	 * @return �������� �� ���������� ���� ����������� �������� (����. ��� �����)
	 */
	public boolean isStandardHoliday(Calendar date)
	{
		return (date.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
				date.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
		);
	}

	/**
	 * ������� ���������� ���� � ���������� ������� ���������
	 * @param calendar - ���. ���������
	 * @param date - ����.
	 * @return workDay
	 */
	public WorkDay findInWorkCalendar(final WorkCalendar calendar, final Calendar date) throws BusinessException
	{
		if (calendar == null)
			throw new NullPointerException("�������� 'calendar' �� ����� ���� null");
		if (date == null)
			throw new NullPointerException("�������� 'date' �� ����� ���� null");
		
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
	 * ������� ���� � ���������� ��������� ���������� � ��� ��������� ��
	 * @param fromDate - � ����� ���� ����
	 * @param toDate - �� ����� ���� ����
	 * @param tb - �� ������ �� ����
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
	 * �������� �� ���� � �� �������� �� ���� �������
	 * @param date ����
	 * @param tb ��
	 * @return true ���� ���� ���� � ��������� ������������ �������� �������
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
