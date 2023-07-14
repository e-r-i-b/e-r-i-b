package com.rssl.phizic.logging.quick.pay;

import com.rssl.phizic.dataaccess.hibernate.HibernateActionStateless;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.writers.QuickPaymentPanelLogWriter;
import org.hibernate.StatelessSession;

/**
 * @author lukina
 * @ created 04.08.2014
 * @ $Author$
 * @ $Revision$
 */
public class DatabaseQuickPaymentPanelLogWriter  implements QuickPaymentPanelLogWriter
{
	public void write(final QuickPaymentPanelLogEntry entry) throws Exception
	{
		HibernateExecutor.getInstance(Constants.DB_INSTANCE_NAME).execute(new HibernateActionStateless<Void>()
		{
			public Void run(StatelessSession session) throws Exception
			{
				session.insert(entry);
				return null;
			}
		});
	}
}

