package com.rssl.auth.csa.front.business.tabs;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Session;

import java.util.List;

/**
 * @author osminin
 * @ created 15.11.2012
 * @ $Author$
 * @ $Revision$
 * Сервис для работы с вкладками/разделами
 */
public class TabsService
{
	private static final String CSA_INSTANCE_NAME = "CSA2";
	/**
	 * Получить все вкладоки/разделы
	 * @return List<Tabs>
	 * @throws Exception
	 */
	public List<Tabs> getAll() throws Exception
	{
		return HibernateExecutor.getInstance(CSA_INSTANCE_NAME).execute(new HibernateAction<List<Tabs>>()
		{
			public List<Tabs> run(Session session)
			{
				return session.createCriteria(Tabs.class).list();
			}
		});
	}
}
