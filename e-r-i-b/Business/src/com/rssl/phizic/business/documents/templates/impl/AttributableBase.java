package com.rssl.phizic.business.documents.templates.impl;

import com.rssl.phizic.business.documents.templates.Constants;
import com.rssl.phizic.business.documents.templates.attributes.*;
import com.rssl.phizic.gate.documents.attribute.ExtendedAttribute;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.commission.WriteDownOperation;
import com.rssl.phizic.gate.documents.attribute.Type;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.BooleanUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * ������� ����� ������ � ��������������� ���������� �������
 *
 * @author khudyakov
 * @ created 30.04.2013
 * @ $Author$
 * @ $Revision$
 */
public abstract class AttributableBase implements Attributable
{
	protected static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);

	private static final ExtendedAttributeFactory attributeFactory = new ExtendedAttributeFactoryImpl();

	private Map<String, ExtendedAttribute> extendedAttributes = new HashMap<String, ExtendedAttribute>();

	/**
	 * @return ���. ��������
	 */
	public Map<String, ExtendedAttribute> getExtendedAttributes()
	{
		return extendedAttributes;
	}

	public void setExtendedAttributes(Map<String, ExtendedAttribute> extendedAttributes)
	{
		this.extendedAttributes = extendedAttributes;
	}

	public ExtendedAttribute createAttribute(String name, Type type, Object value)
	{
		return attributeFactory.create(name, type, value);
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
		if (MapUtils.isEmpty(extendedAttributes))
		{
			return;
		}

		Iterator<Map.Entry<String, ExtendedAttribute>> iterator = extendedAttributes.entrySet().iterator();
		while (iterator.hasNext())
		{
			Map.Entry<String, ExtendedAttribute> entity = iterator.next();
			if (name.equals(entity.getKey()))
			{
				iterator.remove();
			}
		}
	}

	/**
	 * �������� �������� ��������
	 * @param name �������� ��������
	 * @return ��������
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
	 * �������� �������� �������� (������� ���� string)
	 * @param name �������� ��������
	 * @return ��������
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
	 * ���������� � ������� ��������
	 * ���� � �������� �������� ������� null, ���������� �������� ��������.
	 * @param name ��� ��������
	 * @param type ��� ��������
	 * @param value �������� ��������
	 */
	public void setAttributeValue(String name, Type type, Object value)
	{
		if (value == null)
		{
			//���� �������� ���, �� ������� �������
			removeAttribute(name);
			return;
		}

		ExtendedAttribute attribute = extendedAttributes.get(name);
		if (attribute == null)
		{
			//���� �������� ���, �� ���������
			addAttribute(createAttribute(name, type, value));
		}
		else
		{
			attribute.setChanged(!value.equals(attribute.getValue()));
			attribute.setValue(value);
		}
	}

	/**
	 * �������� �������� �������� � ������
	 * @param formData ������
	 * @param tagName �������� ��������
	 * @param value ��������
	 */
	public static void appendNullSaveString(Map<String, String> formData, String tagName, String value)
	{
		if (StringHelper.isEmpty(value))
			return;

		formData.put(tagName, value);
	}

	/**
	 * �������� �������� �������� � ������
	 * @param formData ������
	 * @param tagName �������� ��������
	 * @param value ��������
	 */
	public static void appendNullSaveBoolean(Map<String, String> formData, String tagName, Boolean value)
	{
		formData.put(tagName, String.valueOf(value));
	}

	/**
	 * �������� �������� �������� � ������
	 * @param formData ������
	 * @param tagName �������� ��������
	 * @param calendar ��������
	 */
	public static void appendNullSaveDate(Map<String, String> formData, String tagName, Calendar calendar)
	{
		if (calendar == null)
			return;

		appendNullSaveString(formData, tagName, new SimpleDateFormat(Constants.DATE_FORMAT).format(calendar.getTime()));
	}

	/**
	 * �������� �������� �������� � ������
	 * @param formData ������
	 * @param tagName �������� ��������
	 * @param calendar ��������
	 */
	public static void appendNullSaveTime(Map<String, String> formData, String tagName, Calendar calendar)
	{
		if (calendar == null)
			return;

		appendNullSaveString(formData, tagName, new SimpleDateFormat(Constants.TIME_FORMAT).format(calendar.getTime()));
	}

	/**
	 * �������� �������� �������� � ������
	 * @param formData ������
	 * @param tagName �������� ��������
	 * @param calendar ��������
	 */
	public static void appendNullSaveShortYear(Map<String, String> formData, String tagName, Calendar calendar)
	{
		if (calendar == null)
			return;

		appendNullSaveString(formData, tagName,  new SimpleDateFormat(Constants.SHORT_YEAR_FORMAT).format(calendar.getTime()));
	}

	/**
	 * �������� �������� �������� � ������
	 * @param formData ������
	 * @param tagName �������� ��������
	 * @param value ��������
	 */
	public static void appendNullSaveLong(Map<String, String> formData, String tagName, Long value)
	{
		if (value == null)
			return;

		formData.put(tagName, String.valueOf(value));
	}


	/**
	 * �������� �������� �������� � ������
	 * @param formData ������
	 * @param tagName �������� ��������
	 * @param money ��������
	 */
	public static void appendNullSaveMoney(Map<String, String> formData, String tagName, Money money)
	{
		if (money == null)
			return;

		appendNullSaveString(formData, tagName, money.getDecimal().toPlainString());
		appendNullSaveString(formData, tagName + Constants.CURRENCY_ATTRIBUTE_SUFFIX, money.getCurrency().getCode());
	}

	/**
	 * �������� �������� ���. ���������
	 * @param formData ������
	 * @param extendedAttributes ���. ��������
	 */
	public static void appendExtendedAttributes(Map<String, String> formData, Map<String, ExtendedAttribute> extendedAttributes)
	{
		for (Map.Entry<String, ExtendedAttribute> entry : extendedAttributes.entrySet())
		{
			ExtendedAttribute value = entry.getValue();
			formData.put(value.getName(), value.getStringValue());
		}
	}

	public List<WriteDownOperation> getWriteDownOperations()
	{
		return Collections.emptyList();
	}

	public void setWriteDownOperations(List<WriteDownOperation> writeDownOperations)
	{
		//��� �������� �� ��������� �������� ������������� ��������.
	}
}
