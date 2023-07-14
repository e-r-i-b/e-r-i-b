package com.rssl.phizicgate.mobilebank;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.exceptions.TechnicalException;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.mobilebank.ClientTariffInfo;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.utils.xml.jaxb.JAXBUtils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import javax.xml.bind.JAXBException;

/**
 * User: Moshenko
 * Date: 03.09.13
 * Time: 14:22
 * АС Мигратор при помощи вызываемой процедуры может возвратить в АС МБК Подключения, для которых ранее им был  запрошен COMMIT миграции
 */
public class ReverseMigrationJDBCAction extends JDBCActionBase<Void>
{
	//Нечего реверсировать. Все Подключения соответствующие переданному @MigrationID были удалены безвозвратно МБК – в Архиве Миграции нет информации для этого профиля клиента.
	private static final int SUCCESSFULLY = 1307290000;
	//АС МБК не известен переданный @MigrationID. АС МБК не будет предпринимать никаких действий .
	private static final int UNKNOWN_MIGRATION_ID = 1307291111;
	//миграция для переданного @MigrationID ещё не закончена и для неё не может быть сделан реверс. Повторите запрос позже.
	private static final int MIGRATION_NOT_END = 1307291113;
	//НЕ УСПЕШНО -  конфликт с операцией BEGIN MIGRATION
	private static final int BEGIN_CONFLICT = 1307292220;
	//Техническая ошибка АС МБК. Для реверса миграции клиента нужно повторить запрос
	private static final int TECHNICAL_ERR = 1307298888;
	//не найдены данные для одного или более клиентов по абонентской плате во входном параметре @Client
	private static final int CLIENT_NOT_FOUND = 1307293330;
	//ошибка формата во входном параметре @Client
	private static final int CLIENT_FORMAT_ERROR = 1307293331;

	private static final String ERMB_REVERSE_MIGRATION = "ERMB_ReverseMigration";

	private long migrationId;
	private String clientXml;

	/**
	 * Откат миграции МБК->ЕРМБ
	 * @param migrationId id миграционной транзакции
	 * @param client информация о состоянии абонентской платы клиента
	 */
	public ReverseMigrationJDBCAction(long migrationId, ClientTariffInfo client) throws GateException
	{
		this.migrationId = migrationId;
		this.clientXml = makeClientXml(client);
	}

	public Void doExecute(Connection con) throws SQLException, SystemException
	{
		LogThreadContext.setProcName(ERMB_REVERSE_MIGRATION);
		CallableStatement cstmt =
				con.prepareCall("{ ? = call " + ERMB_REVERSE_MIGRATION +
						"(@MigrationID = ?,\n" +
						"@Client = ?)}");
		try
		{
			cstmt.registerOutParameter(1, Types.INTEGER); // RetCode
			cstmt.setLong(2, migrationId);
			cstmt.setString(3, clientXml);
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
			case SUCCESSFULLY:
				throw new SQLException("Нечего реверсировать. Все Подключения соответствующие переданному @MigrationID: " + migrationId + " были удалены безвозвратно МБК – в Архиве Миграции нет информации для этого профиля клиента.");
			case UNKNOWN_MIGRATION_ID:
				throw new SQLException("АС МБК не известен переданный @MigrationID: " + migrationId + " АС МБК не будет предпринимать никаких действий . ");
			case MIGRATION_NOT_END:
				throw new SQLException("Миграция для переданного @MigrationID: " + migrationId + " ещё не закончена и для неё не может быть сделан реверс. Повторите запрос позже.");
			case BEGIN_CONFLICT:
				throw new SQLException("Одно из Подключений, аналогичных  одному из Подключений, которое собираемся реверсировать, НЕ находится в Реестре Миграции в статусе «Архивировано (мигрировано)», «Восстановлено» или «Удалено безвозвратно» . Повторите запрос позже.");
			case CLIENT_NOT_FOUND:
				throw new SQLException("Не найдены данные для одного или более клиентов по абонентской плате во входном параметре @Client: " + clientXml);
			case CLIENT_FORMAT_ERROR:
				throw new SQLException("ошибка формата во входном параметре @Client: " + clientXml);
			case TECHNICAL_ERR:
				throw new TechnicalException("Техническая ошибка АС МБК при выполнении отката миграции. Нужно повторить запрос.");
			default:
				throw new SQLException("ReverseMigrationJDBCAction вернула неизвестный код возврата: " + status);
		}
	}

	private String makeClientXml(ClientTariffInfo client) throws GateException
	{
		try
		{
			return JAXBUtils.marshalBean(client, null, false);
		}
		catch (JAXBException e)
		{
			throw new GateException("Ошибка сериализации поля @Client", e);
		}
	}
}
