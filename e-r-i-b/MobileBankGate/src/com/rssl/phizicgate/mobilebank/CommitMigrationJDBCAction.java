package com.rssl.phizicgate.mobilebank;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.exceptions.TechnicalException;
import com.rssl.phizic.dataaccess.jdbc.JDBCUtils;
import com.rssl.phizic.gate.mobilebank.ClientTariffInfo;
import com.rssl.phizic.gate.mobilebank.CommitMigrationResult;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.utils.DateHelper;
import org.apache.commons.lang.StringUtils;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import javax.sql.rowset.CachedRowSet;

/**
 * User: Moshenko
 * Date: 30.08.13
 * Time: 10:51
 * АС Мигратор при помощи этой процедуры уведомляет АС МБК, что заблокированные при операции BEGIN,
 * при помощи Миграционной Блокировки, Подключения могут быть удалены из АС МБК и перенесены в Архив.
 */
public class CommitMigrationJDBCAction extends JDBCActionBase<List<CommitMigrationResult>>
{
	//АС МБК не известен переданный @MigrationID (т.е. АС Мигратор не вызывал BEGIN MIGRATION).
	//АС МБК не будет предпринимать никаких действий. Перед тем как делать COMMIT, АС Мигратор должен сделать BEGIN миграции.
	private static final int UNKNOWN_MIGRATION_ID = 1307271111;
	//АС Мигратор уже запрашивал ранее COMMIT, ROLLBACK или РЕВЕРС для переданного @MigrationID.
	//АС МБК не будет предпринимать никаких дополнительных действий. Запрос повторять не надо.
	private static final int OPERATION_REQUESTED = 1307271112;
	//Техническая ошибка АС МБК при подтверждении необходимости миграции.
	//Для подтверждения миграции клиента нужно повторить запрос.
	private static final int TECHNICAL_ERR = 1307278888;

	private final JDBCMessageLogger messageLogger = new JDBCMessageLogger(System.mbk);

	private final long migrationId;

	/**
	 * коммит миграции МБК-ЕРМБ
	 * @param migrationId id миграционной транзакции
	 */
	public CommitMigrationJDBCAction(long migrationId)
	{
		this.migrationId = migrationId;
	}

	public List<CommitMigrationResult> doExecute(Connection con) throws SQLException, SystemException
	{
		messageLogger.startEntry("ERMB_CommitMigration");
		messageLogger.setSQLStatement("? = call ERMB_CommitMigration");
		messageLogger.addInputParam("MigrationID", String.valueOf(migrationId));

		CachedRowSet rs = null;
		CallableStatement cstmt =
				con.prepareCall("{ ? = call ERMB_CommitMigration" +
						"(@MigrationID = ?)}");
		try
		{
			cstmt.registerOutParameter(1, Types.INTEGER); // RetCode
			cstmt.setLong(2, migrationId);

			rs = JDBCUtils.executeStoredProcedureQuery(cstmt);
			int resultCode = cstmt.getInt(1);
			messageLogger.setResultCode(String.valueOf(resultCode));
			messageLogger.setResultSet(rs);

			processStatus(resultCode);
			return processResult(rs);
		}
		finally
		{
			messageLogger.finishEntry();
			if (cstmt != null)
				try { cstmt.close(); } catch (SQLException ignored) {}
			if (rs != null)
				try { rs.close();} catch (SQLException ignored) {}
		}
	}

	private List<CommitMigrationResult> processResult(ResultSet rs) throws TechnicalException, SQLException
	{
		DateFormat mbkDateFormat = new SimpleDateFormat("MMyy");
		List<CommitMigrationResult> entityList = new LinkedList<CommitMigrationResult>();
		if (rs != null)
		{
			while (rs.next())
			{
				CommitMigrationResult result = new CommitMigrationResult();
				result.setLinkId(rs.getString(1));
				result.setPhoneNumber(rs.getString(2));
				result.setPaymentCard(rs.getString(3));
				ClientTariffInfo clientTariffInfo = new ClientTariffInfo();
				clientTariffInfo.setLinkTariff(rs.getInt(4));
				clientTariffInfo.setLinkPaymentBlockID(rs.getInt(5));
				try
				{
					clientTariffInfo.setFirstRegistrationDate(DateHelper.toCalendar(rs.getDate(6)));
					String nextPaidPeriod = rs.getString(7);
					if (StringUtils.isNotEmpty(nextPaidPeriod))
						clientTariffInfo.setNextPaidPeriod(DateHelper.toCalendar(mbkDateFormat.parse(nextPaidPeriod)));
				}
				catch (ParseException e)
				{
					//noinspection ThrowInsideCatchBlockWhichIgnoresCaughtException
					throw new SQLException("Неверный формат даты " + e.getMessage());
				}
				clientTariffInfo.setPayDate(Integer.parseInt(rs.getString(8)));
				result.setTariffInfo(clientTariffInfo);
				entityList.add(result);
			}
		}
		return entityList;
	}

	private void processStatus(int resultCode) throws SQLException, TechnicalException
	{
		StandInUtils.checkStandInAndThrow(resultCode);
		switch (resultCode)
		{
			case 0:
				break;
			case UNKNOWN_MIGRATION_ID:
				throw new SQLException("АС МБК не известен переданный @MigrationID. Миграция приостановлена");
			case OPERATION_REQUESTED:
				throw new SQLException("АС Мигратор уже запрашивал ранее COMMIT, ROLLBACK или РЕВЕРС для переданного @MigrationID.");
			case TECHNICAL_ERR:
				throw new TechnicalException("Техническая ошибка АС МБК при подтверждении необходимости миграции. Нужно повторить запрос.");
			default:
				throw new SQLException("ERMB_CommitMigration вернула неизвестный код возврата: " + resultCode);
		}
	}

	@Override
	public boolean isConnectionLogEnabled()
	{
		return false;
	}
}
