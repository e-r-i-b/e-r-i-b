package com.rssl.phizicgate.mobilebank;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.exceptions.TechnicalException;
import com.rssl.phizic.logging.LogThreadContext;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

/**
 * User: Moshenko
 * Date: 03.09.13
 * Time: 11:26
 * Вызов процедуры отката миграции МБК,
 */
public class RollbackMigrationJDBCAction extends JDBCActionBase<Void>
{
	//АС МБК не известен переданный @MigrationID. АС МБК не будет предпринимать никаких действий . Запрос повторять не надо.
	private static final int UNKNOWN_ID = 1307280001;
	//АС Мигратор уже запрашивал ранее  COMMIT или РЕВЕРС миграции для переданного @MigrationID.
	//АС МБК не будет предпринимать никаких
	private static final int BUSY_OPERATION = 1307280002;
	//Техническая ошибка АС МБК.Для подтверждения миграции клиента нужно повторить запрос.
	private static final int TECHNICAL_ERR = 1307288888;

	private static final String ERMB_ROLLBACK_MIGRATION = "ERMB_RollbackMigration";

	private long migrationId;

	public RollbackMigrationJDBCAction(long migrationId)
	{
		this.migrationId = migrationId;
	}

	public Void doExecute(Connection con) throws SQLException, SystemException
	{
		LogThreadContext.setProcName(ERMB_ROLLBACK_MIGRATION);
		CallableStatement cstmt =
				con.prepareCall("{ ? = call " + ERMB_ROLLBACK_MIGRATION +
						"(@MigrationID = ?)}");
		try
		{
			cstmt.registerOutParameter(1, Types.INTEGER); // RetCode
			cstmt.setLong(2, migrationId);
			cstmt.execute();
			int status = cstmt.getInt(1);
			processStatus(status);
		}
		finally
		{
			if (cstmt != null)
				try
				{
					cstmt.close();
				}
				catch (SQLException ignored)
				{
				}
		}
		return null;
	}

	private void processStatus(int status) throws SQLException, TechnicalException
	{
		StandInUtils.checkStandInAndThrow(status);
		switch (status)
		{
			case 0:
				break;
			case UNKNOWN_ID:
				throw new SQLException("АС МБК не известен переданный @MigrationID: " + migrationId + " АС МБК не будет предпринимать попыток отката . Запрос повторять не надо.");
			case BUSY_OPERATION:
				throw new SQLException("АС Мигратор уже запрашивал ранее  COMMIT или РЕВЕРС миграции для переданного @MigrationID: " + migrationId + " АС МБК не будет предпринимать никаких");
			case TECHNICAL_ERR:
				throw new TechnicalException("Техническая ошибка АС МБК при выполнении отката миграции. Нужно повторить запрос.");
			default:
				throw new SQLException("ERMB_RollbackMigration вернула неизвестный код возврата: " + status);
		}
	}
}
