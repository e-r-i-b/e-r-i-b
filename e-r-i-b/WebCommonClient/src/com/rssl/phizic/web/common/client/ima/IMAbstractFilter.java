package com.rssl.phizic.web.common.client.ima;

import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * User: Balovtsev
 * Date: 21.11.2010
 * Time: 13:49:21
 */
public class IMAbstractFilter
{
	public static final String IMABSTRACT_FILTER_TYPE_FIELD  = "typeAbstract";
	public static final String IMABSTRACT_FILTER_BEGIN_FIELD = "fromAbstract";
	public static final String IMABSTRACT_FILTER_END_FIELD   = "toAbstract";

	private Calendar begin;
	private Calendar end;

	private IMAccountAbstractPeriods abstractType = IMAccountAbstractPeriods.month;
	/**
	 * 
	 * ������������� ���������� �������
	 *
	 * @param source ������ � ��� �� ����� ������ ����������� �������
	 */
	public IMAbstractFilter(Map<String, Object> source)
	{
		String filterType = source == null ? null : (String) source.get(IMABSTRACT_FILTER_TYPE_FIELD);
		
		if(filterType != null)
		{
			abstractType = IMAccountAbstractPeriods.valueOf(filterType);
		}
		end   = DateHelper.getCurrentDate();
		
		switch(abstractType)
		{
			case week:
			{
				begin = DateHelper.getPreviousWeek(DateHelper.getCurrentDate());
				break;
			}
			case period:
			{
				begin = DateHelper.toCalendar ((Date) source.get(IMABSTRACT_FILTER_BEGIN_FIELD));
				end   = DateHelper.toCalendar ((Date) source.get(IMABSTRACT_FILTER_END_FIELD));
				break;
			}
			default:
			{
				begin = DateHelper.getPreviousMonth(end);
				break;
			}
		}
	}

	/**
	 * ��������� ���� � ������� ����������� �������
	 * @return ���� ������ ������������ �������
	 */
	public Calendar getBeginAbstractDate()
	{
		return begin;
	}

	/**
	 * ��������� ���� �� ������� ����������� �������
	 * @return ���� �� ������� ����������� �������
	 */
	public Calendar getEndAbstractDate()
	{
		return end;	
	}

	/**
	 * ���������� ��� ������� �� �������� ����������� �������(������, ����� ��� ������ �������� �������������)
	 * @return IMAccountAbstractPeriods
	 */
	public IMAccountAbstractPeriods getAbstractType()
	{
		return abstractType;
	}
	
	/**
	 * ������������ �������� �� ������� ����� ������������� �������
	 */
	enum IMAccountAbstractPeriods
	{
		week("week"),
		month("month"),
		period("period");

		private String value;
		IMAccountAbstractPeriods(String value)
		{
			this.value = value;
		}

		@Override
		public String toString()
		{
			return this.value;
		}

		public boolean isCustomPeriod()
		{
			return this == period;
		}
	}
}
