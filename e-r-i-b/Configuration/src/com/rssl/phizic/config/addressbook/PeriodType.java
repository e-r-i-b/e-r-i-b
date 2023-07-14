package com.rssl.phizic.config.addressbook;

import java.util.Calendar;

/**
 * @author mihaylov
 * @ created 25.06.14
 * @ $Author$
 * @ $Revision$
 *
 * ��� ������� � �������� ���������� � ���������� ������ ������������� �������� �����
 */
public enum PeriodType
{
	DAY
	{
		@Override
		public Calendar getPrevious()
		{
			Calendar previous = Calendar.getInstance();
			previous.add(Calendar.DAY_OF_YEAR,-1);
			return previous;
		}
	},
	WEEK
	{
		@Override
		public Calendar getPrevious()
		{
			Calendar previous = Calendar.getInstance();
			previous.add(Calendar.WEEK_OF_MONTH,-1);
			return previous;
		}
	},
	MONTH
	{
		@Override
		public Calendar getPrevious()
		{
			Calendar previous = Calendar.getInstance();
			previous.add(Calendar.MONTH,-1);
			return previous;
		}
	};

	/**
	 * @return ������� ���� ����� ��� �������
	 */
	public abstract Calendar getPrevious();
}
