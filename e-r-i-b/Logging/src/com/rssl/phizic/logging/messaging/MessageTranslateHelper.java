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
 * ����� ��� ������ � ���������� MessageTranslate
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
			   ������� ������: AK_CODE_MESSAGE_
		       ��������� �������: "MESSAGE_TRANSLATE"."CODE"=:CODE
		       ��������������: 1
			*/
				DetachedCriteria criteria = DetachedCriteria.forClass(MessageTranslate.class).setProjection(Projections.property("logType")).add(Restrictions.eq("code", key));
				LogType logType = databaseService.findSingle(criteria, null, getInstanceName());
				return (logType != null) && logType.equals(LogType.financial);
			}
			catch (Exception e)
			{
				log.error("������ ����������� ���� ���������.", e);
				return false;
			}
		}
	}, 60L);


	/**
	 * �� �������� ��������� ��������� �������� �������� ���������� ��� ���
	 * @param code - �������� ���������
	 * @return ������� ���������� ��� ��� (���� �� ������� � ��, �� ������������)
	 */
	public static boolean isFinancial(String code)
	{
		try
		{
			return CACHE.getValue(code);
		}
		catch (Exception e)
		{
			log.error("���� �� ����������� ���� ���������", e);
			return false;
		}
	}

	/**
	 * ������������ �������� ���� ������
	 * @return - ��� ��������
	 */
	public static String getInstanceName()
	{
		MessageLogConfig messageLogConfig = ConfigFactory.getConfig(MessageLogConfig.class);
		String instanceName = messageLogConfig.getDbWriterInstanceName();
		//���� � ���������� log.properties ���� �� ������ � ����� ���� ������ - ����� �� ��������� � Log
		if(StringHelper.isEmpty(instanceName))
			return Constants.DB_INSTANCE_NAME;
		return instanceName;
	}
}
