package com.rssl.phizicgate.sbrf.xbank;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.build.AppServerType;
import com.rssl.phizic.config.build.BuildContextConfig;
import com.rssl.phizic.dataaccess.jdbc.JDBCAction;
import com.rssl.phizic.dataaccess.jdbc.LazyConnection;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.messaging.impl.InnerSerializer;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.source.ProxyConnection;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import oracle.jdbc.OracleTypes;
import oracle.xdb.XMLType;
import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import java.io.StringWriter;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Gololobov
 * @ created 16.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class SBRFXBankAction implements JDBCAction<Document>
{
	private static final String WS_JDBC_UTIL_CLASS = "com.ibm.ws.rsadapter.jdbc.WSJdbcUtil";
	private static final String WS_JDBC_CONNECTION_CLASS = "com.ibm.ws.rsadapter.jdbc.WSJdbcConnection";
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);
	private final Account account;
	private final Calendar startDate;
	private final Calendar endDate;
	private final Boolean copying;

	private static final String ABONEBT_ESK = "ESK";
	private static final String CLIENT_ID = "0";
	private static final String REQUEST_TYPE = "billingDemand_q";
	private static final String RESPONCE_TYPE = "billing_a";

	private static final String XBANK_PRS_ACCOUNT_PRS_P = "XBANK_PRS.AccountPRS_P";

	public SBRFXBankAction(Account account, Calendar fromDate, Calendar toDate, Boolean copying)
	{
		this.account = account;
		this.startDate = fromDate;
		this.endDate = toDate;
		this.copying = copying;
	}

	public Document execute(Connection con) throws SQLException, GateException
	{
		String info = !copying ? "справки о состоянии вклада" : "выписки";
		String messageId = new RandomGUID().getStringValue();
		String xmlDocumentIn = getDocumentXMLString(messageId);
		String answerString = null;
		long begin = 0;
		Long executionTime = begin;
		CallableStatement statement =
				con.prepareCall("call "+ XBANK_PRS_ACCOUNT_PRS_P +"( " +
						"?, " + //iXMLDocument_IN
						"?)"    //iXMLDocument_OUT
				);

		LogThreadContext.setProcName(XBANK_PRS_ACCOUNT_PRS_P);
		try
		{
			//Передача XML-запроса на получение выписки
			XMLType xmlTypeObject;
			Connection origConnection = ((LazyConnection) (((ProxyConnection) con).getDelegateConnection())).getConnection();
			BuildContextConfig buildContextConfig = ConfigFactory.getConfig(BuildContextConfig.class);
			if (AppServerType.websphere == buildContextConfig.getApplicationServerType())
			{
				// коннект вебсферы не годится для получения XMLType, получаем нативный
				try
				{
					Class<Object> jdbcUtilClass = ClassHelper.loadClass(WS_JDBC_UTIL_CLASS);
					Class<Object> jdbcConnectionClass = ClassHelper.loadClass(WS_JDBC_CONNECTION_CLASS);
					Method getNativeConnectionMethod = jdbcUtilClass.getMethod("getNativeConnection", jdbcConnectionClass);
					Connection nativeConnection = (Connection) getNativeConnectionMethod.invoke(jdbcUtilClass, origConnection);
					xmlTypeObject = XMLType.createXML(nativeConnection, xmlDocumentIn);
				}
				catch (Exception e)
				{
					log.error("Ошибка при формированиии xmlTypeObject: ", e);
					return null;
				}
			}
			else
			{
				xmlTypeObject = XMLType.createXML(origConnection, xmlDocumentIn);
			}
			statement.setObject(1,xmlTypeObject);
			statement.registerOutParameter(2, OracleTypes.OPAQUE, "SYS.XMLTYPE");
			begin = new Date().getTime();
			statement.executeUpdate();
			executionTime = new Date().getTime() - begin;
			XMLType xmlDocumentOut = XMLType.createXML((oracle.sql.OPAQUE)statement.getObject(2));
			answerString = xmlDocumentOut.getStringVal();
			return xmlDocumentOut.getDOM();
		}
		catch (SQLException e)
		{
			log.error("Ошибка при получении "+info+" по счету: "+account.getNumber(), e);
		}
		finally
		{
			//Запись в журнал сообщений
			writeToLog(xmlDocumentIn,REQUEST_TYPE,messageId,answerString,RESPONCE_TYPE,executionTime);
			if (statement != null)
				try { statement.close(); } catch (SQLException ignored) {}
		}
		return null;
	}

	/**
	 * Формирование XML-запроса
	 * @return
	 */
	private String getDocumentXMLString(String messageId) throws GateException
	{
		try {
			String documentXMLString = null;
			if (account != null)
			{
				StringWriter writer = new StringWriter();
				InnerSerializer serializer = new InnerSerializer(writer);

				Attributes empty = new AttributesImpl();

				//message
				serializer.startDocument();
				serializer.startElement("", "", "message", empty);
					//messageId
					serializer.startElement("", "", "messageId", empty);
					writeText(serializer, messageId);
					serializer.endElement("", "", "messageId");
					// messageDate
					serializer.startElement("", "", "messageDate", empty);
					GregorianCalendar messageDate = new GregorianCalendar();
					writeText(serializer, XMLDatatypeHelper.formatDate(messageDate));
					serializer.endElement("", "", "messageDate");
					// fromAbonent
					serializer.startElement("", "", "fromAbonent", empty);
					writeText(serializer, ABONEBT_ESK);
					serializer.endElement("", "", "fromAbonent");
					//billingDemand_q
					serializer.startElement("", "", "billingDemand_q", empty);
						//clientId
						serializer.startElement("", "", "clientId", empty);
						writeText(serializer, CLIENT_ID);
						serializer.endElement("", "", "clientId");
						//copying
						serializer.startElement("", "", "copying", empty);
						writeText(serializer, copying.toString());
						serializer.endElement("", "", "copying");
						//account
						serializer.startElement("", "", "account", empty);
						writeText(serializer, account.getNumber());
						serializer.endElement("", "", "account");
						//startDate
						serializer.startElement("", "", "startDate", empty);
						writeText(serializer, DateHelper.toXMLDateFormat(startDate.getTime()));
						serializer.endElement("", "", "startDate");
						//endDate
						serializer.startElement("", "", "endDate", empty);
						writeText(serializer, DateHelper.toXMLDateFormat(endDate.getTime()));
						serializer.endElement("", "", "endDate");
					serializer.endElement("", "", "billingDemand_q");
				serializer.endElement("", "", "message");

				documentXMLString = writer.toString();
			}
			return documentXMLString;
		}
		catch (SAXException e)
		{
			throw new GateException(e);
		}
	}

	private void writeText(InnerSerializer  serializer, String text) throws SAXException
	{
		char[] arr = text.toCharArray();
		serializer.characters(arr, 0, arr.length);
	}

	/**
	 * Логирование взаимодействия с xBank в журнал сообщений
	 * @param request - XML-запрос
	 * @param requestType - тип запроса
	 * @param messageId - номер сообщения
	 * @param responce - XML-ответ
	 * @param responceType - тип ответа
	 * @param executionTime - время выполнения
	 */
	private void writeToLog(String request, String requestType, String messageId, String responce, String responceType, Long executionTime)
	{
		try
		{
			MessageLogWriter messageLogWriter = MessageLogService.getMessageLogWriter();
			MessagingLogEntry logEntry = MessageLogService.createLogEntry();

			logEntry.setMessageRequest(request);
			logEntry.setMessageRequestId(messageId);
			logEntry.setMessageType(requestType);

			logEntry.setMessageResponseId(messageId);
			logEntry.setMessageResponse(responce);

			logEntry.setExecutionTime(executionTime);
			logEntry.setSystem(System.xbank);

			messageLogWriter.write(logEntry);
		}
		catch (Exception e)
		{
			log.error("Ошибка записи сообщения в журнал", e);
		}
	}

	public boolean isConnectionLogEnabled()
	{
		return true;
	}
}
