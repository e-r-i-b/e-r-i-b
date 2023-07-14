package com.rssl.phizic.business.mdm;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;


import java.util.Iterator;

/**
 * Сервис получения отчётов
 * @author komarov
 * @ created 21.07.15
 * @ $Author$
 * @ $Revision$
 */
public class MDMInfoService
{
	public static final String CARD_INFO_QUERY_NAME   = "com.rssl.phizic.business.mdm.MDMCardInfo.getCardInfo";
	public static final String PERSON_INFO_QUERY_NAME = "com.rssl.phizic.business.mdm.MDMPersonInfo.getPersonInfo";

	/**
	 * Выгрузка паспортных данных
	 * @param ids список идентификаторов логинов
	 * @return отчёт
	 * @throws BusinessException
	 */
	public Iterator<MDMCardInfo> getMDMCardInfo(String[] ids) throws BusinessException
	{
		return getClientInfo(ids, CARD_INFO_QUERY_NAME);
	}

	/**
	 * Выгрузка анкеты клиента
	 * @param ids список идентификаторов логинов
	 * @return отчёт
	 * @throws BusinessException
	 */
	public Iterator<MDMPersonInfo> getMDMPersonInfo(String[] ids) throws BusinessException
	{
		return getClientInfo(ids, PERSON_INFO_QUERY_NAME);
	}

	private <T> Iterator<T> getClientInfo(String[] ids, String queryName) throws BusinessException
	{
		try
		{
			ExecutorQuery query = new ExecutorQuery(HibernateExecutor.getInstance(), queryName);
			query.setParameterList("loginIds", ids);
			return query.executeIterator();
		}
		catch (Exception e)
		{
			throw new BusinessException("Не удалось получить информацию по клиентам", e);
		}
	}


}
