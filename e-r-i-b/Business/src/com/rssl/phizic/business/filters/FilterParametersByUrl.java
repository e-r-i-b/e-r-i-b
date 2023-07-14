package com.rssl.phizic.business.filters;

import com.rssl.common.forms.parsers.*;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.forms.types.UserResourceParser;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author akrenev
 * @ created 06.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class FilterParametersByUrl
{
	private static final Map<FilterParametersType, FieldValueParser> parsers = new HashMap<FilterParametersType, FieldValueParser>();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	static
	{
		parsers.put(FilterParametersType.STRING,        NullParser.INSTANCE);
		parsers.put(FilterParametersType.BIGDECIMAL,    new BigDecimalParser());
		parsers.put(FilterParametersType.LONG,          new LongParser());
		parsers.put(FilterParametersType.RECOURCE,      new UserResourceParser());
		parsers.put(FilterParametersType.BOOLEAN,       new BooleanParser());
		parsers.put(FilterParametersType.DATE,          new DateParser("dd.MM.yyyy HH:mm:ss"));
	}

	private long id;                                                                                          // id сущности
	private String sessionId;                                                                                 // id сессии для которой сохранены параметры
	private Map<String, FilterParametersField> fields = new HashMap<String, FilterParametersField>();         // сохраненные поля
	private String url; // url страницы для которой сохранены параметры

	public long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getSessionId()
	{
		return sessionId;
	}

	public void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}

	/**
	 * @return параметры фильтрации
	 * @throws com.rssl.phizic.business.BusinessException ошибка парсинга
	 */
	public Map<String, Object> getParameters()
	{
		Map<String, FilterParametersField> fields = getFields();
		Map<String, Object> parameters = new HashMap<String, Object>();
		for (Map.Entry<String, FilterParametersField> entry : fields.entrySet())
		{
			FilterParametersField field = entry.getValue();
			try
			{
				parameters.put(entry.getKey(), parse(field.getType(), field.getValue()));
			}
			catch (BusinessException e)
			{
				log.error(e.getMessage(), e);
			}
		}
		return parameters;
	}

	/**
	 * преобразование параметров фильтрации в созраняемые сущности
	 * @param parameters параметры фильтрации
	 */
	public void setParameters(Map<String, Object> parameters)
	{
		for (Map.Entry<String, Object> entry : parameters.entrySet())
		{
			FilterParametersField filterParametersField = new FilterParametersField();
			String key = entry.getKey();
			Object value = entry.getValue();
			filterParametersField.setParameter(key);
			if (value != null)
			{
				if (value instanceof Date)
				{
					filterParametersField.setValue(DateHelper.toStringTime((Date) value));
					filterParametersField.setType(FilterParametersType.DATE);
				}
				else if (value instanceof ExternalResourceLink)
				{
					filterParametersField.setValue(((ExternalResourceLink) value).getCode());
					filterParametersField.setType(FilterParametersType.RECOURCE);
				}
				else if (value instanceof BigDecimal)
				{
					filterParametersField.setValue(value.toString());
					filterParametersField.setType(FilterParametersType.BIGDECIMAL);
				}
				else if (value instanceof Boolean)
				{
					filterParametersField.setValue(value.toString());
					filterParametersField.setType(FilterParametersType.BOOLEAN);
				}
				else if (value instanceof Long)
				{
					filterParametersField.setValue(value.toString());
					filterParametersField.setType(FilterParametersType.LONG);
				}
				else
				{
					filterParametersField.setValue(value.toString());
					filterParametersField.setType(FilterParametersType.STRING);
				}
			}
			addOrUpdateField(filterParametersField);
		}
	}

	/**
	 * @return сохраняемые поля
	 */
	public Map<String, FilterParametersField> getFields()
	{
		return fields;
	}

	/**
	 * задать сохраняемые поля
	 * @param fields сохраняемые поля
	 */
	public void setFields(Map<String, FilterParametersField> fields)
	{
		this.fields = fields;
	}

	/**
	 * @param type тип параметра
	 * @param value строковое представление значения параметра
	 * @return значение параметра
	 * @throws BusinessException ошибка парсинга
	 */
	private Object parse(FilterParametersType type, String value) throws BusinessException
	{
		FieldValueParser parser = parsers.get(type);
		if (parser == null)
			throw new BusinessException("Не задан парсер для типа параметра " + type);

		try
		{
			return parser.parse(value);
		}
		catch (ParseException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * добавить, удалить или обновить параметр
	 * @param field параметр
	 */
	public void addOrUpdateField(FilterParametersField field)
	{
		// если значения нет, то удаляем
		if (StringHelper.isEmpty(field.getValue()))
		{
			fields.remove(field.getParameter());
			return;
		}

		FilterParametersField existField = fields.get(field.getParameter());
		// поля нет, то добавляем
		if(existField == null)
		{
			fields.put(field.getParameter(), field);
			return;
		}

		existField.setValue(field.getValue());
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}
}
