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
 * �� �������� ��� ������ ���������� ��������� ����� ���������� � �� ��� �����������, ��� ������� ����� �� ���  �������� COMMIT ��������
 */
public class ReverseMigrationJDBCAction extends JDBCActionBase<Void>
{
	//������ �������������. ��� ����������� ��������������� ����������� @MigrationID ���� ������� ������������ ��� � � ������ �������� ��� ���������� ��� ����� ������� �������.
	private static final int SUCCESSFULLY = 1307290000;
	//�� ��� �� �������� ���������� @MigrationID. �� ��� �� ����� ������������� ������� �������� .
	private static final int UNKNOWN_MIGRATION_ID = 1307291111;
	//�������� ��� ����������� @MigrationID ��� �� ��������� � ��� �� �� ����� ���� ������ ������. ��������� ������ �����.
	private static final int MIGRATION_NOT_END = 1307291113;
	//�� ������� -  �������� � ��������� BEGIN MIGRATION
	private static final int BEGIN_CONFLICT = 1307292220;
	//����������� ������ �� ���. ��� ������� �������� ������� ����� ��������� ������
	private static final int TECHNICAL_ERR = 1307298888;
	//�� ������� ������ ��� ������ ��� ����� �������� �� ����������� ����� �� ������� ��������� @Client
	private static final int CLIENT_NOT_FOUND = 1307293330;
	//������ ������� �� ������� ��������� @Client
	private static final int CLIENT_FORMAT_ERROR = 1307293331;

	private static final String ERMB_REVERSE_MIGRATION = "ERMB_ReverseMigration";

	private long migrationId;
	private String clientXml;

	/**
	 * ����� �������� ���->����
	 * @param migrationId id ������������ ����������
	 * @param client ���������� � ��������� ����������� ����� �������
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
				throw new SQLException("������ �������������. ��� ����������� ��������������� ����������� @MigrationID: " + migrationId + " ���� ������� ������������ ��� � � ������ �������� ��� ���������� ��� ����� ������� �������.");
			case UNKNOWN_MIGRATION_ID:
				throw new SQLException("�� ��� �� �������� ���������� @MigrationID: " + migrationId + " �� ��� �� ����� ������������� ������� �������� . ");
			case MIGRATION_NOT_END:
				throw new SQLException("�������� ��� ����������� @MigrationID: " + migrationId + " ��� �� ��������� � ��� �� �� ����� ���� ������ ������. ��������� ������ �����.");
			case BEGIN_CONFLICT:
				throw new SQLException("���� �� �����������, �����������  ������ �� �����������, ������� ���������� �������������, �� ��������� � ������� �������� � ������� ������������� (�����������)�, �������������� ��� �������� ������������ . ��������� ������ �����.");
			case CLIENT_NOT_FOUND:
				throw new SQLException("�� ������� ������ ��� ������ ��� ����� �������� �� ����������� ����� �� ������� ��������� @Client: " + clientXml);
			case CLIENT_FORMAT_ERROR:
				throw new SQLException("������ ������� �� ������� ��������� @Client: " + clientXml);
			case TECHNICAL_ERR:
				throw new TechnicalException("����������� ������ �� ��� ��� ���������� ������ ��������. ����� ��������� ������.");
			default:
				throw new SQLException("ReverseMigrationJDBCAction ������� ����������� ��� ��������: " + status);
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
			throw new GateException("������ ������������ ���� @Client", e);
		}
	}
}
