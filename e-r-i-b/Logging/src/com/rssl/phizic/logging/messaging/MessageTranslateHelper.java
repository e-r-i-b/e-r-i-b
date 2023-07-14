package com.rssl.phizic.logging.messaging;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.DatabaseServiceBase;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.translateMessages.LogType;
import com.rssl.phizic.logging.translateMessages.MessageTranslate;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.cache.Cache;
import com.rssl.phizic.utils.cache.OnCacheOutOfDateListener;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;


/**
 * Класс для работы с сущностями MessageTranslate
 * User: miklyaev
 * Date: 22.09.14
 * Time: 16:09
 */
public class MessageTranslateHelper
{
	private static final DatabaseServiceBase databaseService = new DatabaseServiceBase();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final Cache<String, Boolean> CACHE = new Cache<String, Boolean>(new OnCacheOutOfDateListener<String, Boolean>() {

		public Boolean onRefresh(String key)
		{
			try
			{
			/*
			   Опорный объект: AK_CODE_MESSAGE_
		       Предикаты доступа: "MESSAGE_TRANSLATE"."CODE"=:CODE
		       Кардинальность: 1
			*/
				DetachedCriteria criteria = DetachedCriteria.forClass(MessageTranslate.class).setProjection(Projections.property("logType")).add(Restrictions.eq("code", key));
				LogType logType = databaseService.findSingle(criteria, null, getInstanceName());
				return (logType != null) && logType.equals(LogType.financial);
			}
			catch (Exception e)
			{
				log.error("Ошибка определения типа сообщения.", e);
				return false;
			}
		}
	}, 60L);


	/**
	 * По названию сообщения проверяет является операция финансовой или нет
	 * @param code - название сообщения
	 * @return признак финансовая или нет (если не найдена в бд, то нефинансовая)
	 */
	public static boolean isFinancial(String code)
	{
		try
		{
			return CACHE.getValue(code);
		}
		catch (Exception e)
		{
			log.error("Сбой на определении типа сообщения", e);
			return false;
		}
	}

	/**
	 * Опеределение инстанса базы дынных
	 * @return - имя инстанса
	 */
	public static String getInstanceName()
	{
		MessageLogConfig messageLogConfig = ConfigFactory.getConfig(MessageLogConfig.class);
		String instanceName = messageLogConfig.getDbWriterInstanceName();
		//если в настройках log.properties явно не задано в какую базу писать - пишем по умолчанию в Log
		if(StringHelper.isEmpty(instanceName))
			return Constants.DB_INSTANCE_NAME;
		return instanceName;
	}
}
