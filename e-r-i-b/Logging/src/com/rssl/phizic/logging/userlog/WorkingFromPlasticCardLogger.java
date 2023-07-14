package com.rssl.phizic.logging.userlog;

import com.rssl.phizic.logging.operations.LogWriter;
import com.rssl.phizic.logging.operations.OperationLogFactory;
import com.rssl.phizic.logging.operations.DefaultLogDataReader;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.common.types.exceptions.SystemException;

import java.util.Map;
import java.util.HashMap;
import java.util.Calendar;

/**
 * Записыватель лога, если клиент залогинился или выполнил операцию через АРМ сотрудника с помощью POS-терминала.
 *
 * @author bogdanov
 * @ created 01.10.2012
 * @ $Author$
 * @ $Revision$
 */

public class WorkingFromPlasticCardLogger
{
	private static final String USER_LOGINED_FROM_EMPLOYEE_KEY = "USER_LOGINED_FROM_EMPLOYEE_KEY";

	public static final String CARD_NUMBER_FIELD = "CardNumber";
	public static final String CARD_TYPE_FIELD = "CardType";
	public static final String TERMINAL_NUMBER_FIELD = "TerminalNumber";
	public static final String TRANSACTION_DATE_FIELD = "TransactionDate";
	public static final String TRANSACTION_TIME_FIELD = "TransactionTime";
	public static final String EMPLOYEE_LOGIN_ID_FIELD = "EmployeeLoginId";
	public static final String OPERATION_FIELD = "OperationType";
	public static final String SUCCESSFULL_FIELD = "Successfull";

	/**
	 * Таблица соответствия данных пришедших из Pos-терминала и типа карты.
	 */
	private static Map<String, String> cardTypeToStringCardType = new HashMap<String, String>();

	static
	{
		cardTypeToStringCardType.put("0", "Defined by employee");
		cardTypeToStringCardType.put("1", "VISA");
		cardTypeToStringCardType.put("2", "EUROCARD/MASTERCARD");
		cardTypeToStringCardType.put("3", "CIRRUS/MAESTRO");
		cardTypeToStringCardType.put("101", "Unternational card with processor");
	}

	/**
	 * Записывает данные о клиенте в лог.
	 * @param data данные для логирования.
	 */
	public static void writeLog(Map<String, String> data) throws SystemException
	{
		LogWriter logWriter = OperationLogFactory.getLogWriter();
		DefaultLogDataReader reader = new DefaultLogDataReader(data.get(OPERATION_FIELD));

		reader.setKey(USER_LOGINED_FROM_EMPLOYEE_KEY);
		reader.addParametersReader(new SimpleLogParametersReader("Статус", null, data.containsKey(SUCCESSFULL_FIELD) ? "успешно" : "неуспешно"));
		reader.addParametersReader(new SimpleLogParametersReader("Сотрудник", null, data.get(EMPLOYEE_LOGIN_ID_FIELD)));
		if (data.containsKey(CARD_NUMBER_FIELD))
		{
			reader.addParametersReader(new SimpleLogParametersReader("Карта клиента", null, MaskUtil.getCutCardNumberForLog(data.get(CARD_NUMBER_FIELD))));
			reader.addParametersReader(new SimpleLogParametersReader("Тип карты", null, getCardType(data.get(CARD_TYPE_FIELD))));
			reader.addParametersReader(new SimpleLogParametersReader("Дата и время операции", null, data.get(TRANSACTION_DATE_FIELD) + " " + data.get(TRANSACTION_TIME_FIELD)));
			reader.addParametersReader(new SimpleLogParametersReader("Номер терминала", null, data.get(TERMINAL_NUMBER_FIELD)));
		}
		reader.setOperationKey(USER_LOGINED_FROM_EMPLOYEE_KEY);
		try
		{
			logWriter.writeActiveOperation(reader, Calendar.getInstance(), Calendar.getInstance());
		}
		catch (Exception e)
		{
			throw new SystemException(e);
		}
	}

	/**
	 * @param value значение типа карты, пришедшее с pos-терминала.
	 * @return тип карты.
	 */
	private static String getCardType(String value)
	{
		if (cardTypeToStringCardType.containsKey(value))
			return cardTypeToStringCardType.get(value);

		return value;
	}
}
