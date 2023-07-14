package com.rssl.phizic.business.hibernate;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.build.BuildContextConfig;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Mescheryakova
 * @ created 10.12.2009
 * @ $Author$
 * @ $Revision$
 */

public class DataBaseTypeQueryHelper
{

	public static String getDBType()
	{
		Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
		BuildContextConfig config = ConfigFactory.getConfig(BuildContextConfig.class);
		switch (config.getDatabaseType())
		{
			case oracle:
				return "oracle";
			case mssql:
				return "ms-sql";
			default:
			{
				log.warn("Не удалось найти текущий тип БД");
				return new String();
			}
		}
	}

	public static Query getNamedQuery(Session session, String namedQuery)
	{
		try
		{
			return session.getNamedQuery(namedQuery);
		}
		catch(Exception e)
		{
			return session.getNamedQuery(namedQuery + "." + getDBType());
		}
	}

}
