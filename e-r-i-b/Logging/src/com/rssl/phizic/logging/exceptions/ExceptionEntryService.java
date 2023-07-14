package com.rssl.phizic.logging.exceptions;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.ExceptionSystemLogEntry;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.hibernate.Session;

import java.sql.PreparedStatement;

import static com.rssl.phizic.logging.Constants.LOG_MODULE_CORE;

/**
 * @author osminin
 * @ created 18.03.2015
 * @ $Author$
 * @ $Revision$
 */
public class ExceptionEntryService
{
	private static final Log log = PhizICLogFactory.getLog(LOG_MODULE_CORE);

	/**
	 * Обновить информацию об исключении
	 * @param entry информация об ошибке
	 */
	public void update(final ExceptionSystemLogEntry entry)
	{
		try
		{
			HibernateExecutor.getInstance(Constants.DB_INSTANCE_NAME).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					PreparedStatement statement = null;

					try
					{
						statement = session.connection().prepareStatement("{call updateExceptionInformation( " +
								"?, " + // (1) exceptionKind
								"?, " + // (2) exceptionHash
								"?, " + // (3) exceptionOperation
								"?, " + // (4) exceptionApplication
								"?, " + // (5) exceptionDetail
								"?, " + // (6) exceptionSystem
								"?)}"   // (7) exceptionErrorCode);
						);

						Application application = entry.getApplication();

						statement.setString(1, entry.getKind());
						statement.setString(2, entry.getHash());
						statement.setString(3, entry.getOperation());
						statement.setString(4, application == null ? null : application.name());
						statement.setString(5, entry.getDetail());
						statement.setString(6, entry.getSystem());
						statement.setString(7, entry.getErrorCode());

						statement.execute();
					}
					catch (Exception e)
					{
						log.error("Ошибка обновления статистики исключений для " + entry.getHash(), e);
					}
					finally
					{
						if (statement != null)
						{
							statement.close();
						}
					}
					return null;
				}
			});
		}
		catch (Exception e)
		{
			log.error("Ошибка обновления статистики исключений для " + entry.getHash(), e);
		}
	}
}
