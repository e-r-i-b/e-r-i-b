package com.rssl.phizicgate.mobilebank;

import com.rssl.phizgate.mobilebank.MobileBankConfig;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.utils.StringHelper;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

/**
 * ”ведомление об обработке интернет-заказа.
 *
 * @author bogdanov
 * @ created 24.06.2013
 * @ $Author$
 * @ $Revision$
 */

public class OfferQuitJDBCAction extends JDBCActionBase<Void>
{
	private static final String MB_OFFER_QUIT = "mb_Offer_Quit";

	private final String mbSystemId;
	private final Long messageId;

	public OfferQuitJDBCAction(String mbSystemId, Long messageId)
	{
		this.mbSystemId = mbSystemId;
		this.messageId = messageId;
	}

	public Void doExecute(Connection con) throws SQLException, SystemException
	{
		CallableStatement cstmt =
				con.prepareCall("{? = call " + MB_OFFER_QUIT +"(" +
						"@ExternalSystemID = ?, \n" +
						"@MessageID = ?)}"
				);

		LogThreadContext.setProcName(MB_OFFER_QUIT);
		try
		{
			cstmt.registerOutParameter(1, Types.INTEGER); // RetCode
			cstmt.setInt(2, StringHelper.isEmpty(mbSystemId) ? Integer.parseInt(ConfigFactory.getConfig(MobileBankConfig.class).getMbSystemId()) : Integer.parseInt(mbSystemId));
			cstmt.setLong(3, messageId);

			LogThreadContext.setProcName(MB_OFFER_QUIT);
			cstmt.execute();

			// анализ кода возврата
			int rc = cstmt.getInt(1);
			StandInUtils.registerMBOfflineEvent(rc);
			testReturnCode(rc);

			return null;
		}
		finally
		{
			if (cstmt != null)
				try { cstmt.close(); } catch (SQLException ignored) {}
		}
	}

	protected void testReturnCode(int rc) throws SQLException
	{
		StandInUtils.checkStandInAndThrow(rc);
		if (rc != 0)
			throw new SQLException("неизвестный код возврата (" + rc + ")");
	}
}
