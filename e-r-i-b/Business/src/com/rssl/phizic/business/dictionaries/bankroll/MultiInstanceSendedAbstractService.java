package com.rssl.phizic.business.dictionaries.bankroll;

import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import org.hibernate.Session;
import org.hibernate.Query;

/**
 * Сервис для работы с отправленными выписками
 * @author Pankin
 * @ created 08.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class MultiInstanceSendedAbstractService
{
	/**
	 * удалить все выписки, привязанные к линку
	 * @param link - кардлинк
	 * @param instanceName - имя экземпляра БД
	 */
	public void removeAll(final CardLink link, String instanceName) throws BusinessException
	{
		try
	    {
		   HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
		   {
			   public Void run(Session session) throws Exception
			   {
				   Query query = session.getNamedQuery("com.rssl.phizic.business.dictionaries.bankroll.SendedAbstract.removeByLink");
				   query.setParameter("cardLink", link);
				   query.executeUpdate();
				   return null;
			   }
		   });
	    }
	    catch(Exception ex)
	    {
			throw new BusinessException("Ошибка при удалении отпраленных выписок", ex);
	    }
	}
}
