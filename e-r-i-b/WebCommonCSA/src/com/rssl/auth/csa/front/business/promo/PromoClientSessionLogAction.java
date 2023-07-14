package com.rssl.auth.csa.front.business.promo;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.dataaccess.jdbc.JDBCAction;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Логирование входа клиентов с участием промоутера
 *
 * @ author: Gololobov
 * @ created: 14.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class PromoClientSessionLogAction implements JDBCAction<String>
{
	private final String PROMO_CLIENT_SESSION_LOG_PROCEDURE = "SetPromoClientSessionLog";
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);
	private String connectorGuid;
	private String operatonOuid;
	private String promoterSessionId;

	public PromoClientSessionLogAction(String connectorGuid, String promoterSessionId, String operatonOuid)
	{
		this.connectorGuid = connectorGuid;
		this.promoterSessionId = promoterSessionId;
		this.operatonOuid = operatonOuid;
	}

	public String getConnectorGuid()
	{
		return connectorGuid;
	}

	public String getOperatonOuid()
	{
		return operatonOuid;
	}

	public String getPromoterSessionId()
	{
		return promoterSessionId;
	}

	public String execute(Connection con) throws SQLException, SystemException
	{
		CallableStatement statement =
				con.prepareCall("call CSA_CONNECTORS_PKG."+ PROMO_CLIENT_SESSION_LOG_PROCEDURE +"( " +
						"?, " + //pConnectorGUID - (IN) GUID коннектора клиента
						"?, " + //pOperationUID - (IN) OUID операции клиента
						"?, " + //pPromoterSessionId - (IN) ID активной сессии промоутера
						"?)"    //pPromoClientLogId - (OUT) ID записи в логе
				);

		LogThreadContext.setProcName(PROMO_CLIENT_SESSION_LOG_PROCEDURE);
		try
		{
			statement.setString(1, getConnectorGuid());
			statement.setString(2, getOperatonOuid());
			statement.setString(3, getPromoterSessionId());
			statement.registerOutParameter(4, Types.VARCHAR);

			statement.execute();
			return statement.getString(4);
		}
		catch (SQLException e)
		{
			log.error("Ошибка при логировании входа клиента с участием промоутера", e);
		}
		finally
		{
			if (statement != null)
				try { statement.close(); } catch (SQLException ignored) {}
		}

		return null;
	}

	public boolean isConnectionLogEnabled()
	{
		return true;
	}
}
