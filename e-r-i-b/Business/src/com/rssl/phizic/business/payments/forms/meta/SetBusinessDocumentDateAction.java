package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.TemporalBusinessException;
import com.rssl.phizic.business.documents.ConvertibleToGateDocumentAdapter;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.receptiontimes.ReceptionTime;
import com.rssl.phizic.business.receptiontimes.ReceptionTimeService;
import com.rssl.phizic.business.receptiontimes.TimeMatching;
import com.rssl.phizic.business.receptiontimes.WorkTimeInfo;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.utils.CalendarGateService;
import com.rssl.phizic.utils.DateHelper;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Roshka
 * @ created 01.02.2007
 * @ $Author$
 * @ $Revision$
 */

public class SetBusinessDocumentDateAction extends BusinessDocumentHandlerBase<BusinessDocument>
{
	private static final String DATE_PROPERTY_NAME          = "datePropertyName";
	private static final String DEFAULT_DATE_PROPERTY_NAME  = "date";
	private static final String DATE_PROPERTY_TYPE          = "datePropertyType";
	private static final String DATE_PROPERTY_TYPE_DATE     = Date.class.getName();   // �� ���������
	private static final String DATE_PROPERTY_TYPE_CALENDAR = Calendar.class.getName();


	public void process(BusinessDocument document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		Calendar date = calculateDate(document);

		String datePropertyType = getParameter(DATE_PROPERTY_TYPE);

		if ( datePropertyType == null || datePropertyType.equals(DATE_PROPERTY_TYPE_DATE) )
		{
			setDocumentProperty(document, date.getTime());
		}
		else if ( datePropertyType.equals(DATE_PROPERTY_TYPE_CALENDAR) )
		{
			setDocumentProperty(document, date);
		}
		else
		{
			throw new DocumentLogicException("������������ �������� �������� [" + DATE_PROPERTY_TYPE + "]: " + datePropertyType +
											 ". ��������� " + DATE_PROPERTY_TYPE_DATE + " ��� " + DATE_PROPERTY_TYPE_CALENDAR);
		}
	}

	public Calendar calculateDate(BusinessDocument document) throws DocumentLogicException, DocumentException
	{
		Calendar date = Calendar.getInstance();
		GateDocument doc = getGateDocument(document);
		WorkTimeInfo workTimeInfo = getWorkTimeInfo(document);

		// ���� ������ �� ������� �����
		if(workTimeInfo.isWorkTimeNow() != TimeMatching.RIGHT_NOW)
		{
		    try
			{
				// ������ ����� ������ ����������
				ReceptionTime receptionTime = workTimeInfo.getReceptionTime();

				// ���� ������� ����� ��� �����������, ���������� ��������� ������� ����
				if(workTimeInfo.isWorkTimeNow() == TimeMatching.TOO_LATE)
				{
					setNextWorkDay(date, doc);
				}

				// ������ ����� ������ ���������
				if(receptionTime==null)
				{
					throw new DocumentException("��� ������ ����� ������� � ������������� �� ������ ����� ������ ����������. " +
							"FORM=" + ((BusinessDocument) document).getFormName());
				}
				date.set(Calendar.HOUR_OF_DAY,receptionTime.getReceptionTimeStart().getHours());
				date.set(Calendar.MINUTE,receptionTime.getReceptionTimeStart().getMinutes());
				date.set(Calendar.SECOND,0);

				// ���� ������������ �������� ���� ���������� ��������� ������ ������� ����, ��� �������������
				// � ���, ��� � �������������, � ������� ������������ ������, ��� ��������� ����������� ����
				// (��-�� ������� ������� ������), � ����� ���������� ������� ��� �� ������. � ����� �������
				// ����� ���������� ������ ��������� ������� ����.
				if (date.before(Calendar.getInstance()))
					setNextWorkDay(date, doc);
			}
		    catch (GateException e)
			{
				throw new DocumentException(e);
			}
			catch (GateLogicException e)
			{
				throw new DocumentLogicException(e);
			}
		}
		return date;
	}

	protected GateDocument getGateDocument(BusinessDocument document) throws DocumentException
	{
		try
		{
			return new ConvertibleToGateDocumentAdapter(document).asGateDocument();
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	private void setNextWorkDay(Calendar date, GateDocument doc) throws GateLogicException, GateException
	{
		CalendarGateService calendarGateService = GateSingleton.getFactory().service(CalendarGateService.class);
		Calendar nextWorkDay = calendarGateService.getNextWorkDay(DateHelper.getCurrentDate(), doc);
		date.set(Calendar.YEAR, nextWorkDay.get(Calendar.YEAR));
		date.set(Calendar.MONTH, nextWorkDay.get(Calendar.MONTH));
		date.set(Calendar.DAY_OF_MONTH, nextWorkDay.get(Calendar.DAY_OF_MONTH));
	}

	private void setDocumentProperty(BusinessDocument document, Object date)
			throws DocumentLogicException
	{
		String datePropertyName = DEFAULT_DATE_PROPERTY_NAME;

		try
		{
			String propertyName = getParameter(DATE_PROPERTY_NAME);
			if (propertyName != null && propertyName.length() > 0)
				datePropertyName = propertyName;

			BeanUtils.setProperty(document, datePropertyName, date);
		}
		catch (IllegalAccessException e)
		{
			throw new DocumentLogicException("�������� ��� �������� [" + datePropertyName +
					"], ��������� " + DATE_PROPERTY_TYPE_DATE + " ��� " + DATE_PROPERTY_TYPE_CALENDAR, e);
		}
		catch (InvocationTargetException e)
		{
			throw new DocumentLogicException("� ��������� �� ������� �������� [" + datePropertyName + "]", e);
		}
	}

	protected WorkTimeInfo getWorkTimeInfo(BusinessDocument document) throws DocumentLogicException, DocumentException
	{
		try
		{
			ReceptionTimeService receptionTimeService = new ReceptionTimeService();
			return receptionTimeService.getWorkTimeInfo(document);
		}
		catch (BusinessLogicException e)
		{
			throw new DocumentLogicException(e);
		}
		catch (TemporalBusinessException e)
		{
			throw new TemporalDocumentException(e);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}

	}

	protected void recalculateDates(Calendar openingDate, Calendar newOpeningDate)
	{
		//������������� ����� ���� ��������
		openingDate.set(Calendar.YEAR, newOpeningDate.get(Calendar.YEAR));
		openingDate.set(Calendar.MONTH, newOpeningDate.get(Calendar.MONTH));
		openingDate.set(Calendar.DAY_OF_MONTH, newOpeningDate.get(Calendar.DAY_OF_MONTH));
	}

	protected boolean isOpeningDateLTNext(Calendar openingDate)
	{
		Calendar nextDate = getNextDate();
		return openingDate.before(nextDate);
	}

	protected Calendar getNextDate()
	{
		Calendar nextDate = DateHelper.getCurrentDate();
		nextDate.add(Calendar.DAY_OF_MONTH, 1);
		return nextDate;
	}

	/**
	 * ���������� ���� �������������� � ��������� � �������. ��� ��������� ����, ������ � ������� � ������ �� �������
	 *
	 * @param calendar ���� ��� ��������� � ������
	 * @return true ���� ���� �������������� � ��������� ����� �������, false � ��������� ������
	 */
	protected boolean isOpeningDateEQCurrent(Calendar calendar)
	{
		Calendar current = DateHelper.getCurrentDate();
		Calendar opening = DateHelper.clearTime(calendar);

		return current.compareTo(opening) == 0;
	}
}
