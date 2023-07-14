package com.rssl.phizic.business.calendar;

import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.receptiontimes.ReceptionTime;
import com.rssl.phizic.business.receptiontimes.ReceptionTimeService;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.utils.CalendarGateService;
import com.rssl.phizic.utils.DateHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.Calendar;

/**
 * @author: Pakhomova
 * @created: 17.08.2009
 * @ $Author$
 * @ $Revision$
 */
public class CalendarGateServiceImpl extends AbstractService implements CalendarGateService
{
	private static final ReceptionTimeService receptionTimeService = new ReceptionTimeService();
	private static final CalendarService businessCalendarService = new CalendarService();
	private static final DepartmentService departmentService = new DepartmentService();

	public CalendarGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public Calendar getNextWorkDay(Calendar fromDate, GateDocument document) throws GateException, GateLogicException
	{
		ReceptionTime receptionTime = getReceptionTime((SynchronizableDocument) document);
		WorkCalendar calendar = (receptionTime == null) ? null : receptionTime.getCalendar();

		if (calendar == null || CollectionUtils.isEmpty(calendar.getWorkDays()))
		{
			//����������� ���������
			int weekday = fromDate.get(Calendar.DAY_OF_WEEK);
			int days = 1;
			if (weekday > Calendar.THURSDAY)
			{
				// �������, ������� ���� ����� ����������
				days = (Calendar.SATURDAY - weekday + 2) % 7;
			}
			fromDate.add(Calendar.DAY_OF_YEAR, days);
			return fromDate;
		}
		else
		{
			return findNextWorkDay(calendar, fromDate);
		}
	}

	/**
	 *
	 * @param date ���� ��� ��������
	 * @param document - ��������
	 * @return �������� �� ���������� ���� �������� �� �������� ��������� ��� �����
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public boolean isHoliday(Calendar date, GateDocument document) throws GateException, GateLogicException
	{
		ReceptionTime receptionTime = getReceptionTime((SynchronizableDocument) document);
		//receptionTime = null, ����� ������������ ������ "�����������" ���������
		WorkCalendar calendar = (receptionTime == null) ? null : receptionTime.getCalendar();

		try
		{
			if (calendar == null)
				return businessCalendarService.isStandardHoliday(date);
			WorkDay workDay = businessCalendarService.findInWorkCalendar(calendar, date);
			if (workDay != null)
				return !workDay.getIsWorkDay();
			else               // ���� ��� ���� �� ���������� � ������� �������-��������, ������������ �� �����������
				return businessCalendarService.isStandardHoliday(date);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	private ReceptionTime getReceptionTime(SynchronizableDocument document) throws GateException
	{
		try
		{
			if (document instanceof BusinessDocument)
			{
				return receptionTimeService.getRecepionTime((Department) ((BusinessDocument) document).getDepartment(), document.getType());
			}
			return receptionTimeService.getRecepionTime(departmentService.findByOffice(document.getOffice()), document.getType());
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 *
	 * @param calendar ������� ��������� (��������� �������)
	 * @param date  ����, ������������ ������� ���� ����� ����.���.����
	 * @return ����.���. ���� � ������ ����������� �������� � ����, �������� � ������� ���������
	 */
	private Calendar findNextWorkDay(WorkCalendar calendar, Calendar date) throws GateException
	{
		try
		{
			Calendar nextDate = DateHelper.toCalendar(DateHelper.add(DateHelper.toDate(date), new DateSpan(0, 0, 1)));
			//��������, ��� �� � ������� ��������� ���� ����
			WorkDay workDay = businessCalendarService.findInWorkCalendar(calendar, nextDate);

			if (workDay == null)
			{
				if (!businessCalendarService.isStandardHoliday(nextDate))
					return nextDate;
				else
					return findNextWorkDay(calendar, nextDate);
			}
			else
			{
				if (workDay.getIsWorkDay())
					return nextDate;
				else
					return findNextWorkDay(calendar, nextDate);
			}
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}
}
