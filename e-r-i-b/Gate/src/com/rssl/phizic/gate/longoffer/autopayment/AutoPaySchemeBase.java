package com.rssl.phizic.gate.longoffer.autopayment;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.xml.XmlEntityBuilder;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.text.ParseException;

/**
 * @author krenev
 * @ created 27.09.2011
 * @ $Author$
 * @ $Revision$
 */
public class AutoPaySchemeBase
{
	protected static final String CLIENT_HINT   = "clientHint";
	protected static final String FIELD         = "field";
	protected static final String ENTITY        = "entity";

	private Map<String, String> parameters = new HashMap<String, String>();
	private Long id;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getClientHint()
	{
		return getStringValue(CLIENT_HINT);
	}

	public void setClientHint(String clientHint)
	{
		setStringValue(CLIENT_HINT, clientHint);
	}

	/**
	 * установка параметров автоплатежа
	 * @param params параметры ввиде xml
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public void setParametersByXml(String params) throws GateLogicException, GateException
	{
		if(StringHelper.isEmpty(params))
			parameters.clear();

		parameters.putAll(new AutoPaymentSettingSAXSource(params).getSource());
	}

	/**
	 * @return параметры автоплатежа ввиде xml
	 */
	public String getParametersByXml()
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Получить значение параметра в виде String
	 * @param fieldName имя параметра автоплатежа
	 * @return значение
	 */
	protected String getStringValue(String fieldName)
	{
		return parameters.get(fieldName);
	}

	/**
	 * Установить значение параметра в виде String
	 * @param fieldName имя параметра
	 * @param value значение параметра(в случае null, удаляется из мапа)
	 */
	protected void setStringValue(String fieldName, String value)
	{
		if(value != null)
			parameters.put(fieldName, value);
		else
			parameters.remove(fieldName);
	}

	/**
	 * Получить значение параметра в виде Boolean
	 * @param fieldName имя параметра
	 * @return значение
	 */
	protected Boolean getBooleanValue(String fieldName)
	{
		String value = parameters.get(fieldName);
		if(StringHelper.isEmpty(value))
			return null;

		return Boolean.valueOf(value);
	}

	/**
	 * Установить значение ввиде Boolean
	 * @param fieldName имя параметра
	 * @param value значение(в случае null, удаляется из мапа)
	 */
	protected void setBooleanValue(String fieldName, Boolean value)
	{
		if(value != null)
			parameters.put(fieldName, BooleanUtils.toStringTrueFalse(value));
		else
			parameters.remove(fieldName);
	}

	/**
	 * Получить значение параметра в виде BigDecimal
	 * @param fieldName имя параметра
	 * @return значение
	 */
	protected BigDecimal getBigDecimalValue(String fieldName)
	{
		String value = parameters.get(fieldName);
		if(StringHelper.isEmpty(value))
			return null;

		try
		{
			return NumericUtil.parseBigDecimal(StringUtils.deleteWhitespace(value));
		}
		catch (ParseException e)
		{
			throw new IllegalArgumentException("Невозможно привести значение к BigDecimal", e);
		}
	}

	/**
	 * Получить значение параметра в виде енума
	 * @param enumType класс енума
	 * @param fieldName имя поля
	 * @param <T> название енума
	 * @return значение
	 */
	protected <T extends Enum<T>> T getEnumValue(Class<T> enumType, String fieldName)
	{
		String value = parameters.get(fieldName);
		if (StringHelper.isEmpty(value))
			return null;
		else
			return Enum.valueOf(enumType, value);
	}

	/**
	 * Установить значение енума
	 * @param fieldName имя поля
	 * @param value значение
	 * @param <T> название енума
	 */
	protected <T extends Enum<T>> void setEnumValue(String fieldName, T value)
	{
		if(value == null)
			parameters.remove(fieldName);
		else
			parameters.put(fieldName, value.name());
	}

	/**
	 * Установить значение параметра в виде BigDecimal
	 * @param fieldName имя параметра
	 * @param value значение(в случае null, удаляется из мапа)
	 */
	protected void setBigDecimalValue(String fieldName, BigDecimal value)
	{
		if(value != null)
			parameters.put(fieldName, value.toString());
		else
			parameters.remove(fieldName);
	}

	/**
	 * Построить строковое представление параметра
	 * @param nameField имя параметра
	 * @param value значение
	 * @return строковое представление параметра вида <field name="nameField">value</field>
	 * (пустая строка если значение null)
	 */
	protected String fieldBuilder(String nameField, Object value)
	{
		// если значения нет, то нет смысла записывать
		if(value == null)
			return StringUtils.EMPTY;

		XmlEntityBuilder builder = new XmlEntityBuilder();
		builder.createEntityTag(FIELD,
				value.toString(), Collections.singletonMap("name", nameField));
		return builder.toString();
	}
}
