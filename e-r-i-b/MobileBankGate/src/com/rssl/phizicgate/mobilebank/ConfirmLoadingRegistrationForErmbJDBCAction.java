package com.rssl.phizicgate.mobilebank;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.utils.xml.XMLMessageWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 * ����, ���������� �������� ��������� ������������� ���� ��������� ����������� �� ���
 * ������� �������� ��������� - ��������� id �������� �����������
 * @author Rtischeva
 * @ created 08.07.14
 * @ $Author$
 * @ $Revision$
 */
class ConfirmLoadingRegistrationForErmbJDBCAction extends JDBCActionBase<Void>
{
	private static final String MB_BATCH_ERMB_CONFIRM_REGISTRATION_LOADING = "mb_BATCH_ERMB_ConfirmRegistrationLoading";

	private static final int INCORRECT_FORMAT_RET_CODE = -1;
	private static final int ERROR_RET_CODE = -2;

	private String confirmedRegIdsXml;

	public ConfirmLoadingRegistrationForErmbJDBCAction(List<Long> regIds)
	{
		this.confirmedRegIdsXml = makeConfirmedRegIdsXml(regIds);
	}

	public Void doExecute(Connection con) throws SQLException, SystemException
	{
		CallableStatement cstmt = con.prepareCall("{? = call " + MB_BATCH_ERMB_CONFIRM_REGISTRATION_LOADING +
				"(@ID = ?)}");

		LogThreadContext.setProcName(MB_BATCH_ERMB_CONFIRM_REGISTRATION_LOADING);
		try
		{
			cstmt.registerOutParameter(1, Types.INTEGER); // RetCode
			cstmt.setString(2, confirmedRegIdsXml);
			cstmt.execute();

			processResult(cstmt);
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

	private void processResult(CallableStatement cstmt) throws SQLException, SystemException
	{
		int returnCode = cstmt.getInt(1);
		StandInUtils.registerMBOfflineEvent(returnCode);
		StandInUtils.checkStandInAndThrow(returnCode);
		if (returnCode == INCORRECT_FORMAT_RET_CODE)
			throw new SQLException("������������ ������ ��������� ��� ���-�� ����������� ��������� ���������� �������� ��� �������� ��������� ������������� ���� ��������� ����������� �� ���");
		if (returnCode == ERROR_RET_CODE)
			throw new SQLException("�������� ���������  ������������� ���� ��������� ����������� �� ��� ������� ������");
	}

	private String makeConfirmedRegIdsXml(List<Long> confirmedRegIds)
	{
		XMLMessageWriter writer = new XMLMessageWriter("UTF-8");

		writer.startDocument();
		writer.startElement("confirms");

		for (Long id : confirmedRegIds)
		{
			if (id == null)
				throw new IllegalArgumentException("id �� ����� ���� null!");
			writer.startElement("confirm", "id", String.valueOf(id)).endElement();
		}
		writer.endElement();

		return writer.toString();
	}
}
