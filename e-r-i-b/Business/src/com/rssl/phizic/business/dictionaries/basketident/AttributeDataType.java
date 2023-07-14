package com.rssl.phizic.business.dictionaries.basketident;

import com.rssl.phizic.utils.StringHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author bogdanov
 * @ created 07.11.14
 * @ $Author$
 * @ $Revision$
 */

public enum AttributeDataType
{
	TEXT("Текст")
			{
				@Override
				public String format(Object obj)
				{
					if (obj instanceof String)
						return (String) obj;
					else
						throw new ClassCastException(obj.getClass().getName());
				}

				@Override
				public Object parse(String str)
				{
					return str;
				}
			},
	DATE("Дата")
			{
				DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);

				@Override
				public String format(Object obj)
				{
					format.setLenient(false);
					if ((obj instanceof Calendar) || (obj instanceof Date))
					{

						if (obj instanceof Calendar)
							return format.format(((Calendar)obj).getTime());
						else
							return format.format((Date)obj);
					}
					else
						throw new ClassCastException(obj.getClass().getName());
				}

				@Override
				public Object parse(String str) throws ParseException
				{
					return StringHelper.isEmpty(str) ? null : format.parse(str);
				}
			},
	NUMBER("Число")
			{
				@Override
				public String format(Object obj)
				{
					return Integer.toString((Integer)obj);
				}

				@Override
				public Object parse(String str)
				{
					return StringHelper.isEmpty(str) ? null : Integer.parseInt(str);
				}
			},
	MONEY("Сумма и валюта")
			{
				@Override
				public String format(Object obj)
				{
					return NUMBER.format(obj);
				}

				@Override
				public Object parse(String str) throws ParseException
				{
					return StringHelper.isEmpty(str) ? null : NUMBER.parse(str);
				}
			};

    public abstract String format(Object obj);
	public abstract Object parse(String str) throws ParseException;
	private final String name;

	AttributeDataType(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}
}
