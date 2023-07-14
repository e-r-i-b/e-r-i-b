package com.rssl.auth.csa.front.business.blockingrules;

import com.rssl.phizic.config.CSAFrontConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

/**
 * Класс сущность. Правило блокировки.
 * @author komarov
 * @ created 10.04.2013 
 * @ $Author$
 * @ $Revision$
 */

public class BlockingRules
{
	private static final int AUTO_PROLONGATION_INTERVAL = 20; //интервал автопролонгации в мин
	private static final String TODAY_DATE_EXPIRE_FORMAT = "%1$tH:%1$tM";
	private static final String COMMON_DATE_EXPIRE_FORMAT = "%1$td.%1$tm.%1$tY %1$tH:%1$tM";

	private Long id;

	private String departments; //перечисленные через запятую коды подразделений или маски

	//настройки параметров уведомления
	private Calendar fromPublishDate;
	private Calendar toPublishDate;
	private Calendar fromRestrictionDate;
	private Calendar toRestrictionDate;	

	/**
	 * @return департаменты или маски
	 */
	public String getDepartments()
	{
		return departments;
	}

	/**
	 * @param departments департаменты или маски
	 */
	public void setDepartments(String departments)
	{
		this.departments = departments;
	}

	/**
	 * @return идентификатор
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id идентификатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}


	/**
	 *
	 * @return период публикации уведомления о блокировке c
	 */
	public Calendar getFromPublishDate()
	{
		return fromPublishDate;
	}

	/**
	 *
	 * @param fromPublishDate период публикации уведомления о блокировке c
	 */
	public void setFromPublishDate(Calendar fromPublishDate)
	{
		this.fromPublishDate = fromPublishDate;
	}

	/**
	 *
	 * @return период публикации уведомления о блокировке по
	 */
	public Calendar getToPublishDate()
	{
		return toPublishDate;
	}

	/**
	 *
	 * @param toPublishDate период публикации уведомления о блокировке по
	 */
	public void setToPublishDate(Calendar toPublishDate)
	{
		this.toPublishDate = toPublishDate;
	}

	/**
	 *
	 * @return период, отображаемый в уведомлении о блокировке с
	 */
	public Calendar getFromRestrictionDate()
	{
		return fromRestrictionDate;
	}

	/**
	 *
	 * @param fromRestrictionDate период, отображаемый в уведомлении о блокировке с
	 */
	public void setFromRestrictionDate(Calendar fromRestrictionDate)
	{
		this.fromRestrictionDate = fromRestrictionDate;
	}

	/**
	 *
	 * @return период, отображаемый в уведомлении о блокировке по
	 */
	public Calendar getToRestrictionDate()
	{
		return toRestrictionDate;
	}

	/**
	 * @param toRestrictionDate период, отображаемый в уведомлении о блокировке по
	 */
	public void setToRestrictionDate(Calendar toRestrictionDate)
	{
		this.toRestrictionDate = toRestrictionDate;
	}

	/**
	 * Сгенерировать сообщение об ограничение по правилу:
	 * Для Вашего региона услуга Сбербанк ОнЛ@йн будет временно недоступна с <время начало ограничения доступа> до <время окончания ограничения доступа>.»
	 *
	 * (дата отображается, только если время возобновления доступа приходится на другой календарный день).
	 * Если к указанному моменту доступ не будет открыт, то время восстановления доступа должно автоматически пролонгироваться на 20 минут.
	 * @return сообщение
	 */
	public String getMessage()
	{
		String RestrictionMessage;
		CSAFrontConfig properties = ConfigFactory.getConfig(CSAFrontConfig.class);
		RestrictionMessage = properties.getRestrictionMessage();

		Calendar fromDate = (Calendar) fromRestrictionDate.clone();
		Calendar toDate = (Calendar) toRestrictionDate.clone();
		Calendar currentDate = Calendar.getInstance();
		//Получаем разницу между текущи временем и восстановлением доступа в мс.
		long diff = DateHelper.diff(currentDate, toDate);
		if (diff > 0)
		{
			//Доступ не восстановили к назначенному времени. автоматически пролонгируем.
			int intervalInMilliseconds = AUTO_PROLONGATION_INTERVAL * DateHelper.MILLISECONDS_IN_MINUTE;
			int prolongationCount = (int) ((diff + intervalInMilliseconds - 1) / intervalInMilliseconds);// получаем сколько полных интервалов прошло с округлением в большую сторону
			toDate.add(Calendar.MINUTE, prolongationCount * AUTO_PROLONGATION_INTERVAL); //добавляем эти интервалы к ожидаемому времени восстановления.
		}
		String dateFormat = (DateHelper.daysDiff(currentDate, toDate) == 0) ? TODAY_DATE_EXPIRE_FORMAT : COMMON_DATE_EXPIRE_FORMAT;
		return String.format(RestrictionMessage, String.format(dateFormat, fromDate) ,String.format(dateFormat, toDate));
	}
}
