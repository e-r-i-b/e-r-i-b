package com.rssl.phizic.gate.templates.impl;

import com.rssl.phizic.gate.documents.attribute.ExtendedAttribute;
import com.rssl.phizic.gate.documents.attribute.Type;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.templates.attributable.Attributable;
import com.rssl.phizic.gate.templates.attributable.ExtendedAttributeFactory;
import com.rssl.phizic.utils.DateHelper;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.BooleanUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Базовый класс шаблонов документов
 *
 * @author khudyakov
 * @ created 17.04.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class AttributableTemplateBase implements Attributable
{
	private Map<String, ExtendedAttribute> extendedAttributes = new HashMap<String, ExtendedAttribute>();

	/**
	 * Вернуть мап доп. полей
	 * (для внутренних нужд ЕСУШ, для hibernate)
	 * @return мап. доп. полей шаблона платежа
	 */
	public Map<String, ExtendedAttribute> getHExtendedAttributes()
	{
		return extendedAttributes;
	}

	/**
	 * Установить мап доп. полей
	 * (для внутренних нужд ЕСУШ, для hibernate)
	 * @param extendedAttributes мап доп. полей
	 */
	public void setHExtendedAttributes(Map<String, ExtendedAttribute> extendedAttributes)
	{
	 	this.extendedAttributes = extendedAttributes;
	}

	/**
	 * Вернуть мап доп. полей
	 * (реализация гейтового интерфейса)
	 * @return мап. доп. полей шаблона платежа
	 */
	public Map<String, ExtendedAttribute> getExtendedAttributes() throws GateException
	{
		return Collections.unmodifiableMap(extendedAttributes);
	}

	/**
	 * Установить мап доп. полей
	 * (реализация гейтового интерфейса)
	 * @param destination мап доп. полей
	 * @throws Exception
	 */
	public void setExtendedAttributes(Map<String, ExtendedAttribute> destination) throws Exception
	{
		if (MapUtils.isEmpty(destination))
		{
			removeAllExtendedAttributes();
			return;
		}

		Map<String, ExtendedAttribute> previous = new HashMap<String, ExtendedAttribute>(extendedAttributes);

		for (Map.Entry<String, ExtendedAttribute> entry : destination.entrySet())
		{
			ExtendedAttribute value = entry.getValue();
			setAttributeValue(value.getName(), value.getType(), value.getValue());
			previous.remove(value.getName());
		}

		for (Map.Entry<String, ExtendedAttribute> entry : previous.entrySet())
		{
			extendedAttributes.remove(entry.getValue().getName());
		}
	}

	public ExtendedAttribute createAttribute(String name, Type type, Object value)
	{
		return ExtendedAttributeFactory.getInstance().create(name, type, value);
	}

	public ExtendedAttribute getAttribute(String name)
	{
		return extendedAttributes.get(name);
	}

	public void addAttribute(ExtendedAttribute attribute)
	{
		extendedAttributes.put(attribute.getName(), attribute);
	}

	public void removeAttribute(String name)
	{
		extendedAttributes.remove(name);
	}

	/**
	 * Получить значение атрибута
	 * @param name название атрибута
	 * @return значение
	 */
	protected Object getNullSaveAttributeValue(String name)
	{
		ExtendedAttribute extendedAttribute = getAttribute(name);
		if (extendedAttribute == null)
		{
			return null;
		}
		return extendedAttribute.getValue();
	}

	/**
	 * Получить значение атрибута (атрибут типа string)
	 * @param name название атрибута
	 * @return значение
	 */
	protected String getNullSaveAttributeStringValue(String name)
	{
		return (String) getNullSaveAttributeValue(name);
	}

	protected Calendar getNullSaveAttributeCalendarValue(String name)
	{
		return DateHelper.toCalendar((Date) getNullSaveAttributeValue(name));
	}

	protected boolean getNullSaveAttributeBooleanValue(String name)
	{
		return BooleanUtils.toBoolean((Boolean) getNullSaveAttributeValue(name));
	}

	protected BigDecimal getNullSaveAttributeDecimalValue(String name)
	{
		return (BigDecimal) getNullSaveAttributeValue(name);
	}

	protected Long getNullSaveAttributeLongValue(String name)
	{
		return (Long) getNullSaveAttributeValue(name);
	}

	protected void setNullSaveAttributeStringValue(String name, String value)
	{
		setAttributeValue(name, Type.STRING, value);
	}

	protected void setNullSaveAttributeBooleanValue(String name, Boolean value)
	{
		setAttributeValue(name, Type.BOOLEAN, value);
	}

	protected void setNullSaveAttributeDecimalValue(String name, BigDecimal value)
	{
		setAttributeValue(name, Type.DECIMAL, value);
	}

	protected void setNullSaveAttributeLongValue(String name, Long value)
	{
		setAttributeValue(name, Type.LONG, value);
	}

	protected void setNullSaveAttributeCalendarValue(String name, Calendar value)
	{
		setAttributeValue(name, Type.DATE, DateHelper.toDate(value));
	}

	/**
	 * Установить в атрибут значение
	 * если в качестве значения передан null, происходит удаление атрибута.
	 * @param name имя атрибута
	 * @param type тип атрибута
	 * @param value значение атрибута
	 */
	public void setAttributeValue(String name, Type type, Object value)
	{
		if (value == null)
		{
			//если значения нет, то удаляем атрибут
			removeAttribute(name);
			return;
		}

		ExtendedAttribute attribute = extendedAttributes.get(name);
		if (attribute == null)
		{
			//если атрибута нет, то добавляем
			addAttribute(createAttribute(name, type, value));
		}
		else
		{
			attribute.setChanged(!value.equals(attribute.getValue()));
			attribute.setValue(value);
		}
	}

	private void removeAllExtendedAttributes()
	{
		Iterator iterator = extendedAttributes.entrySet().iterator();
		while (iterator.hasNext())
		{
			iterator.remove();
		}
	}
}
