package com.rssl.phizicgate.mobilebank;

import com.rssl.phizgate.mobilebank.MobileBankConfig;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.jdbc.JDBCUtils;
import com.rssl.phizic.gate.mobilebank.AcceptInfo;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import javax.sql.rowset.CachedRowSet;

/**
 * Подтверждение интернет-заказа по смс.
 *
 * @author bogdanov
 * @ created 24.06.2013
 * @ $Author$
 * @ $Revision$
 */

public class OfferConfirmJDBCAction extends JDBCActionBase<List<AcceptInfo>>
{
	private static final String MB_OFFER_CONFIRM = "mb_Offer_Confirm";

	private static final int DATA_NOT_FOUND_RET_CODE = -100;

	private final String mbSystemId;

	private final JDBCMessageLogger messageLogger = new JDBCMessageLogger(System.mbk);

	public OfferConfirmJDBCAction(String mbSystemId)
	{
		this.mbSystemId = mbSystemId;
	}


	public List<AcceptInfo> doExecute(Connection con) throws SQLException, SystemException
	{
		int externalSystemId = StringHelper.isEmpty(mbSystemId) ? Integer.parseInt(ConfigFactory.getConfig(MobileBankConfig.class).getMbSystemId()) : Integer.parseInt(mbSystemId);

		messageLogger.startEntry("mb_Offer_Confirm");
		messageLogger.setSQLStatement("? = call mb_Offer_Confirm");
		messageLogger.addInputParam("ExternalSystemID", externalSystemId);

		CachedRowSet rs = null;
		CallableStatement cstmt = con.prepareCall("{? = call mb_Offer_Confirm(@ExternalSystemID = ?)}");
		try
		{
			cstmt.registerOutParameter(1, Types.INTEGER); // RetCode
			cstmt.setInt(2, externalSystemId);

			rs = JDBCUtils.executeStoredProcedureQuery(cstmt);
			messageLogger.setResultSet(rs);
			return processResult(cstmt, rs);
		}
		finally
		{
			messageLogger.finishEntry();
			if (rs != null)
				try { rs.close(); } catch (SQLException ignored) {}
			if (cstmt != null)
				try { cstmt.close(); } catch (SQLException ignored) {}
		}
	}

	private List<AcceptInfo> processResult(CallableStatement cstmt, ResultSet rs) throws SQLException, SystemException
	{
		int rc = cstmt.getInt(1);
		StandInUtils.registerMBOfflineEvent(rc);
		messageLogger.setResultCode(rc);
		StandInUtils.checkStandInAndThrow(rc);

		if (rc == DATA_NOT_FOUND_RET_CODE)
		{
			return null;
		}

		if (rc != 0)
		{
			throw new SystemException("Ошибка при выполнении парсинга результата " + MB_OFFER_CONFIRM);
		}

		List<AcceptInfo> infos = new LinkedList<AcceptInfo>();

		if (rs != null)
		{
			while (rs.next()) {
				AcceptInfo result = new AcceptInfo();
				result.setMessageId(rs.getLong(1));
				result.setReceiptTime(DateHelper.toCalendar(rs.getDate(2)));
				infos.add(result);
			}
		}

		return infos;
	}

	@Override
	public boolean isConnectionLogEnabled()
	{
		return false;
	}
}
