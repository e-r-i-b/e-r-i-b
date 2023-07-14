package com.rssl.phizic.einvoicing;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.einvoicing.ShopProvider;
import com.rssl.phizic.gate.exceptions.GateException;
import org.hibernate.Session;

/**
 * Сервис для получения поставщика из БД ЦСА Admin.
 *
 * @author bogdanov
 * @ created 14.02.14
 * @ $Author$
 * @ $Revision$
 */

public class ProviderService
{
	/**
	 * @param systemName имя поставщика услуг (codeRecipientSBOL).
	 * @return найденный поставщик.
	 */
	public ShopProvider findProvider(final String systemName) throws GateException
	{
		try
		{
		return HibernateExecutor.getInstance("CSAAdmin").execute(new HibernateAction<ShopProvider>()
		{
			public ShopProvider run(Session session) throws Exception
			{
				return (ShopProvider) session.getNamedQuery("com.rssl.phizic.einvoicing.ShopProviders.findProviderBySystemName")
						.setParameter("systemName", systemName)
						.uniqueResult();
			}
		});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
