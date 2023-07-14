package com.rssl.phizicgate.ips;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.source.LogableCallableStatement;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author akrenev
 * @ created 06.09.13
 * @ $Author$
 * @ $Revision$
 *
 * Парсер информации об операции по карте
 */

public class CardOperationsParser implements LogableCallableStatement.ObjectParser
{
	private static final String ID_PARAMETER_NAME = "id";
	private static final String OPERATION_TYPE_PARAMETER_NAME = "operationType";
	private static final String OPERATION_DATE_PARAMETER_NAME = "operationDate";
	private static final String CARD_ID_PARAMETER_NAME = "cardId";
	private static final String DESCRIPTION_PARAMETER_NAME = "description";
	private static final String ACC_SUM_PARAMETER_NAME = "accSum";
	private static final String CUR_ACC_SUM_PARAMETER_NAME = "curAccSum";
	private static final String SUM_PARAMETER_NAME = "sum";
	private static final String CUR_SUM_PARAMETER_NAME = "curSum";
	private static final String MCC_CODE_PARAMETER_NAME = "mccCode";
	private static final String CARD_ACCOUNT_PARAMETER_NAME = "cardAccount";
	private static final String DEVICE_ID_PARAMETER_NAME = "deviceId";
	private static final String PERSON_DOC_PARAMETER_NAME = "personDoc";
	private static final String AUTH_CODE_PARAMETER_NAME = "authCode";
	private static final String START_LOG_RECORD = "{";
	private static final String EQUAL = "=";
	private static final String PARAMETER_SEPARATOR = ",";
	private static final String END_LOG_RECORD = "}";

	private final CurrencyService currencyService;

	private final GetCardOperationsBatch executor;
	private final CallableStatement statement;
	private final int operationsBatchSize;
	private final Set<String> failedCardNumber = new HashSet<String>();
	private final Calendar startDate;

	private ResultSet cursor;

	/**
	 * конструктор
	 * @param statement указатель записи
	 * @param gateFactory гейтовая фабрика
	 * @param operationsBatchSize размер выборки
	 * @param startDate минимальная дата операции
	 */
	public CardOperationsParser(GetCardOperationsBatch executor, CallableStatement statement, GateFactory gateFactory, int operationsBatchSize, Calendar startDate)
	{
		currencyService = gateFactory.service(CurrencyService.class);
		this.statement = statement;
		this.operationsBatchSize = operationsBatchSize;
		this.executor = executor;
		this.startDate = startDate;
	}

	public Object getParam() throws SQLException
	{
		cursor = (ResultSet) statement.getObject(4);
		cursor.setFetchSize(operationsBatchSize);
		return cursor;
	}

	public String parse(Object object) throws SQLException
	{
		ResultSet resultSet = (ResultSet) object;
		StringBuilder logBuilder = new StringBuilder();
		List<CardOperationInfo> result = new ArrayList<CardOperationInfo>();
		while (resultSet.next())
		{
			CardOperationInfo cardOperationInfo = getOperationInfoRecord(resultSet);
			if (cardOperationInfo != null)
				result.add(cardOperationInfo);
			addInfoToLogBuilder(logBuilder, resultSet);
			if (operationsBatchSize <= result.size())
			{
				executor.save(result);
				result.clear();
			}
		}
		executor.save(result);
		return logBuilder.toString();
	}

	/**
	 * @return получить курсор
	 */
	public ResultSet getCursor()
	{
		return cursor;
	}

	private CardOperationInfo getOperationInfoRecord(ResultSet resultSet)
	{
		CardOperationInfo operationInfo = new CardOperationInfo();

		String cardNumber = getStringValue(resultSet, CARD_ID_PARAMETER_NAME, operationInfo, null);
		if (failedCardNumber.contains(cardNumber))
			return null;

		Calendar operationDate = getCalendarValue(resultSet, OPERATION_DATE_PARAMETER_NAME, operationInfo, cardNumber);
		if (startDate.after(operationDate))
			return null;

		operationInfo.setCardId(cardNumber);
		operationInfo.setOperationId(getStringValue(resultSet, ID_PARAMETER_NAME, operationInfo, cardNumber));
		operationInfo.setOperationType(getLongValue(resultSet, OPERATION_TYPE_PARAMETER_NAME, operationInfo, cardNumber));
		operationInfo.setOperationDate(operationDate);
		operationInfo.setDescription(getStringValue(resultSet, DESCRIPTION_PARAMETER_NAME, operationInfo, cardNumber));
		operationInfo.setAccountMoney(getMoney(resultSet, ACC_SUM_PARAMETER_NAME, CUR_ACC_SUM_PARAMETER_NAME, operationInfo, cardNumber));
		operationInfo.setOperationMoney(getMoney(resultSet, SUM_PARAMETER_NAME, CUR_SUM_PARAMETER_NAME, operationInfo, cardNumber));
		operationInfo.setMccCode(getLongValue(resultSet, MCC_CODE_PARAMETER_NAME, operationInfo, cardNumber));
		operationInfo.setDeviceId(getEmptyPossibleStringValue(resultSet, DEVICE_ID_PARAMETER_NAME, operationInfo, cardNumber));
		operationInfo.setAuthCode(getEmptyPossibleStringValue(resultSet, AUTH_CODE_PARAMETER_NAME, operationInfo, cardNumber));

		return operationInfo;
	}

	private void setError(String message, CardOperationInfo operationInfo, String cardNumber)
	{
		failedCardNumber.add(cardNumber);
		if (operationInfo.isBadOperation())
			return;

		operationInfo.setBadOperation(true);
		operationInfo.setErrorMessage(message);
	}

	private String getStringValue(ResultSet resultSet, String name, CardOperationInfo operationInfo, String cardNumber)
	{
		try
		{
			String string = resultSet.getString(name);
			if (StringHelper.isEmpty(string))
				setError( name + " не может быть null", operationInfo, cardNumber);
			return string;
		}
		catch (SQLException e)
		{
			setError(e.getMessage(), operationInfo, cardNumber);
			return null;
		}
	}

	private String getEmptyPossibleStringValue(ResultSet resultSet, String name, CardOperationInfo operationInfo, String cardNumber)
	{
		try
		{
			return resultSet.getString(name);
		}
		catch (SQLException e)
		{
			setError(e.getMessage(), operationInfo, cardNumber);
			return null;
		}
	}

	private Long getLongValue(ResultSet resultSet, String name, CardOperationInfo operationInfo, String cardNumber)
	{
		try
		{
			long value = resultSet.getLong(name);
			if (resultSet.wasNull())
			{
				setError( name + " не может быть null", operationInfo, cardNumber);
				return null;
			}
			return value;
		}
		catch (SQLException e)
		{
			setError(e.getMessage(), operationInfo, cardNumber);
			return null;
		}
	}

	private Calendar getCalendarValue(ResultSet resultSet, String name, CardOperationInfo operationInfo, String cardNumber)
	{
		try
		{
			Timestamp timestamp = resultSet.getTimestamp(name);
			if (timestamp == null)
			{
				setError( name + " не может быть null", operationInfo, cardNumber);
				return null;
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(timestamp.getTime());
			return calendar;
		}
		catch (SQLException e)
		{
			setError(e.getMessage(), operationInfo, cardNumber);
			return null;
		}
	}

	private BigDecimal getBigDecimalValue(ResultSet resultSet, String name, CardOperationInfo operationInfo, String cardNumber)
	{
		try
		{
			BigDecimal value = resultSet.getBigDecimal(name);
			if (value == null)
			{
				setError( name + " не может быть null", operationInfo, cardNumber);
				return null;
			}
			return value;
		}
		catch (SQLException e)
		{
			setError(e.getMessage(), operationInfo, cardNumber);
			return null;
		}
	}

	private Currency getCurrency(String currencyCode, CardOperationInfo operationInfo, String cardNumber)
	{
		try
		{
			return currencyService.findByNumericCode(currencyCode);
		}
		catch (GateException e)
		{
			setError(e.getMessage(), operationInfo, cardNumber);
			return null;
		}
	}

	private Money getMoney(ResultSet resultSet, String amountKey, String currencyKey, CardOperationInfo operationInfo, String cardNumber)
	{

		BigDecimal amount = getBigDecimalValue(resultSet, amountKey, operationInfo, cardNumber);
		String currencyCode = getStringValue(resultSet, currencyKey, operationInfo, cardNumber);

		if (amount == null || StringHelper.isEmpty(currencyCode))
			return null;

		Currency currency = getCurrency(currencyCode, operationInfo, cardNumber);
		if (currency == null)
		{
			setError("Не найдена валюта " + currencyCode, operationInfo, cardNumber);
			return null;
		}

		return new Money(amount, currency);
	}

	private String getLogStringValue(ResultSet resultSet, String name)
	{
		try
		{
			return resultSet.getString(name);
		}
		catch (SQLException ignore) // упали при попытке собрать запись для лога -- не беда, упадем дальше
		{
			return null;
		}
	}

	private void addInfoToLogBuilder(StringBuilder result, ResultSet resultSet)
	{
		result.append(START_LOG_RECORD);
		result.append(ID_PARAMETER_NAME).append(EQUAL).append(getLogStringValue(resultSet, ID_PARAMETER_NAME)).append(PARAMETER_SEPARATOR);
		result.append(OPERATION_TYPE_PARAMETER_NAME).append(EQUAL).append(getLogStringValue(resultSet, OPERATION_TYPE_PARAMETER_NAME)).append(PARAMETER_SEPARATOR);
		result.append(OPERATION_DATE_PARAMETER_NAME).append(EQUAL).append(getLogStringValue(resultSet, OPERATION_DATE_PARAMETER_NAME)).append(PARAMETER_SEPARATOR);
		result.append(CARD_ID_PARAMETER_NAME).append(EQUAL).append(getLogStringValue(resultSet, CARD_ID_PARAMETER_NAME)).append(PARAMETER_SEPARATOR);
		result.append(DESCRIPTION_PARAMETER_NAME).append(EQUAL).append(getLogStringValue(resultSet, DESCRIPTION_PARAMETER_NAME)).append(PARAMETER_SEPARATOR);
		result.append(ACC_SUM_PARAMETER_NAME).append(EQUAL).append(getLogStringValue(resultSet, ACC_SUM_PARAMETER_NAME)).append(PARAMETER_SEPARATOR);
		result.append(CUR_ACC_SUM_PARAMETER_NAME).append(EQUAL).append(getLogStringValue(resultSet, CUR_ACC_SUM_PARAMETER_NAME)).append(PARAMETER_SEPARATOR);
		result.append(SUM_PARAMETER_NAME).append(EQUAL).append(getLogStringValue(resultSet, SUM_PARAMETER_NAME)).append(PARAMETER_SEPARATOR);
		result.append(CUR_SUM_PARAMETER_NAME).append(EQUAL).append(getLogStringValue(resultSet, CUR_SUM_PARAMETER_NAME)).append(PARAMETER_SEPARATOR);
		result.append(MCC_CODE_PARAMETER_NAME).append(EQUAL).append(getLogStringValue(resultSet, MCC_CODE_PARAMETER_NAME)).append(PARAMETER_SEPARATOR);
		result.append(CARD_ACCOUNT_PARAMETER_NAME).append(EQUAL).append(getLogStringValue(resultSet, CARD_ACCOUNT_PARAMETER_NAME)).append(PARAMETER_SEPARATOR);
		result.append(DEVICE_ID_PARAMETER_NAME).append(EQUAL).append(getLogStringValue(resultSet, DEVICE_ID_PARAMETER_NAME)).append(PARAMETER_SEPARATOR);
		result.append(PERSON_DOC_PARAMETER_NAME).append(EQUAL).append(getLogStringValue(resultSet, PERSON_DOC_PARAMETER_NAME)).append(PARAMETER_SEPARATOR);
		result.append(AUTH_CODE_PARAMETER_NAME).append(EQUAL).append(getLogStringValue(resultSet, AUTH_CODE_PARAMETER_NAME));
		result.append(END_LOG_RECORD);
	}
}