package com.rssl.phizicgate.mobilebank;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.utils.xml.XMLStringWriter;
import com.rssl.phizic.utils.xml.XMLWriter;
import org.dom4j.io.OutputFormat;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Подтверждение получения сообщений унифицированного интерфейса МБК
 * @author Pankin
 * @ created 29.12.14
 * @ $Author$
 * @ $Revision$
 */
public class UESIConfirmMessagesJDBCAction extends JDBCActionBase<Void>
{
	private static final String CALL_NAME = "UESI_ConfirmMessages";

	private List<Long> externalIds;

	public UESIConfirmMessagesJDBCAction(List<Long> externalIds)
	{
		this.externalIds = externalIds;
	}

	protected Void doExecute(Connection con) throws SQLException, SystemException
	{
		CallableStatement cstmt = con.prepareCall("{call " + CALL_NAME + "(" +
				"@ExternalSystemID = 1," +
				"@Messages = ?)}");

		LogThreadContext.setProcName(CALL_NAME);

		try
		{
			cstmt.setString(1, prepareMessages());
			cstmt.execute();
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

	private String prepareMessages()
	{
		OutputFormat outputFormat = OutputFormat.createCompactFormat();
		outputFormat.setSuppressDeclaration(true);
		XMLWriter xmlWriter = new XMLStringWriter(outputFormat);

		xmlWriter.startElement("confirmed_msgs");
		for (Long externalId : externalIds)
		{
			xmlWriter.startElement("msg");
			{
				xmlWriter.writeTextElement("id", String.valueOf(externalId));
				xmlWriter.writeTextElement("err", "0");
				xmlWriter.writeTextElement("desc", "");
			}
			xmlWriter.endElement();
		}
		xmlWriter.endElement();

		return xmlWriter.toString();
	}
}
