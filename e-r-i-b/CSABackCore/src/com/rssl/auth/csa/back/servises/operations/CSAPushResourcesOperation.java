package com.rssl.auth.csa.back.servises.operations;

import com.rssl.auth.csa.back.servises.Operation;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.Query;
import org.hibernate.Session;

import java.text.MessageFormat;

/**
 * операция для загрузки push сообщений
 * @author basharin
 * @ created 06.10.14
 * @ $Author$
 * @ $Revision$
 */

public class CSAPushResourcesOperation extends Operation
{
	/**
	 *
	 * Возвращает форматированный текст смс
	 *
	 * @param key  ключ искомого ресурса смс
	 * @param args
	 *
	 * @return String
	 * @throws Exception
	 */
	public static String getFormattedSmsResourcesText(final String key, final Object... args) throws Exception
	{
		String message = null;

		try
		{
			message = getHibernateExecutor().execute(new HibernateAction<String>()
			{
				public String run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.auth.csa.back.messages.CSAPushResourcesOperation.getText");
					query.setParameter("key", key);
					return (String) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			log.trace("Ошибка при получении текста смс сообщения с ключом " + key, e);
		}

		if (message == null)
		{
			return null;
		}

		return new MessageFormat(StringHelper.fixLineTransfer(message)).format(args);
	}

	/**
	 *
	 * Возвращает текст короткого сообщения
	 *
	 * @param key  ключ искомого ресурса
	 * @return String
	 * @throws Exception
	 */
	public static Object[] getShortText(final String key) throws Exception
	{
		Object[] message = null;

		try
		{
			message = getHibernateExecutor().execute(new HibernateAction<Object[]>()
			{
				public Object[] run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.auth.csa.back.messages.CSAPushResourcesOperation.getParams");
					query.setParameter("key", key);
					return (Object[]) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			log.trace("Ошибка при получении текста смс сообщения с ключом " + key, e);
		}

		return message;
	}
}
