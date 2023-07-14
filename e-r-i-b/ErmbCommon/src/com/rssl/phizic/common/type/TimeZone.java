package com.rssl.phizic.common.type;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * @author EgorovaA
 * @ created 14.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class TimeZone
{
	private final static  List<TimeZone> LIST = new ArrayList<TimeZone>();

	public final static TimeZone KALININGRAD = createDefaultTimeZone(120, "(GMT+02:00)Калининград");
	public final static TimeZone MOSCOW = createDefaultTimeZone(180, "(GMT+03:00)Москва");
	public final static TimeZone SAMARA = createDefaultTimeZone(240, "(GMT+04:00)Самара");
	public final static TimeZone EKATERINBURG = createDefaultTimeZone(300, "(GMT+05:00)Екатеринбург");
	public final static TimeZone NOVOSIBIRSK = createDefaultTimeZone(360, "(GMT+06:00)Новосибирск");
	public final static TimeZone KRASNOYARSK = createDefaultTimeZone(420, "(GMT+07:00)Красноярск");
	public final static TimeZone IRKUTSK = createDefaultTimeZone(480, "(GMT+08:00)Иркутск");
	public final static TimeZone YAKUTSK = createDefaultTimeZone(540, "(GMT+09:00)Якутск");
	public final static TimeZone VLADIVOSTOK = createDefaultTimeZone(600, "(GMT+10:00)Владивосток");
	public final static TimeZone MAGADAN = createDefaultTimeZone(660, "(GMT+11:00)Магадан");
	public final static TimeZone KAMCHATKA = createDefaultTimeZone(720, "(GMT+12:00)Камчатка");

	private final long code;
	private final String text;

	private TimeZone(long code, String text)
	{
		this.code = code;
		this.text = text;
	}

	/**
	 * Создать список константных часовых поясов
	 * @param code - код
	 * @param text - описание
	 * @return
	 */
	private static TimeZone createDefaultTimeZone(long code, String text)
	{
		TimeZone timeZone = new TimeZone(code, text);
		LIST.add(timeZone);
		return timeZone;
	}

	/**
	 * Метод возвращает список константных часовых поясов
	 * @return
	 */
	public static List<TimeZone> getKnownTimeZones()
	{
		return Collections.unmodifiableList(LIST);
	}

	/**
	 * Код (смещение в минутах относительно GMT)
	 * @return
	 */
	public long getCode()
	{
		return code;
	}

	/**
	 * Описание часового пояса с указанием часовго пояса в формате GMT±hh:mm и названием
	 * @return
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * Получить часовой пояс по коду
	 * @param code - код
	 * @return константный(если есть значение с таким кодом) или кастомный часовой пояс
	 */
	public static TimeZone getTimeZoneByCode(long code)
	{
		TimeZone tz = getKnownTimeZone(code);
		if (tz == null)
			return createCustomTimeZone(code);
		return tz;
	}

	/**
	 * Получить константный часовой пояс по коду
	 * @param code - код
	 * @return часовой пояс (если есть в константных значение с таким кодом) или null
	 */
	public static TimeZone getKnownTimeZone(long code)
	{
		for (TimeZone timeZone: getKnownTimeZones())
		{
			if (timeZone.getCode() == code)
				return timeZone;
		}
		return null;
	}

	/**
	 * Создать кастомный часовой пояс по коду
	 * @param code - код
	 * @return  часовой пояс с переданным кодом и сгенерированным описанием
	 */
	public static TimeZone createCustomTimeZone(long code)
	{
		String newTimeZoneText = "("+ TimeZone.getFormattedTimeZone(code)+")Другой";
		return new TimeZone(code, newTimeZoneText);
	}

	/**
	 * Часовой пояс в формате GMT±hh:mm
	 * @param timeZone часовой пояс в минутах
	 * @return строка с часовым поясом в формате GMT±hh:mm
	 */
	public static String getFormattedTimeZone(long timeZone)
	{
		if (timeZone == 0)
			return "GMT";
		return String.format("GMT%+03d:%02d", timeZone/60, timeZone%60);
	}

	/**
	 * Сформировать список часовых поясов, доступных клиенту. Если часовой пояс клиента не совпадает
	 * ни с одним из "стандартного" списка, добавляем новый элемент
	 * @param code - часовой пояс (указан в минутах), указанный в ЕРМБ-профиле клиента
	 * @return список часовых поясов
	 */
	public static List<TimeZone> getTimeZoneList(long code)
	{
		if (getKnownTimeZone(code) != null)
			return getKnownTimeZones();
		//нет совпадений ни с одним из значений "стандартного" списка
		List<TimeZone> timeZoneList = new ArrayList<TimeZone>();
		timeZoneList.addAll(TimeZone.getKnownTimeZones());
		timeZoneList.add(0, createCustomTimeZone(code));
		return timeZoneList;

	}
}
