package com.rssl.phizic.gate.templates.attributable;

import com.rssl.phizic.gate.documents.attribute.ExtendedAttribute;
import com.rssl.phizic.gate.documents.attribute.Type;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author khudyakov
 * @ created 30.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class ExtendedAttributeFactory
{
	private static final ExtendedAttributeFactory INSTANCE = new ExtendedAttributeFactory();

	public static ExtendedAttributeFactory getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Создать новый доп. атрибут шаблона документа
	 * @param name название
	 * @param type тип
	 * @param value значение
	 * @return дополнительный атрибут
	 */
	public ExtendedAttribute create(String name, Type type, Object value)
	{
		return create(null, name, type, value);
	}

	/**
	 * Создать новый доп. атрибут шаблона документа
	 * @param id идентификатор
	 * @param name название
	 * @param type тип
	 * @param value значение
	 * @return дополнительный атрибут
	 */
	public ExtendedAttribute create(Long id, String name, Type type, Object value)
	{
		if (StringHelper.isEmpty(name))
			throw new NullPointerException("наименование доп. атрибута не может быть null");

		if (type == null)
			throw new NullPointerException("тип доп. атрибута не может быть null");

		if (Type.STRING == type)
			return new StringAttribute(id, name, value);

		if (Type.BOOLEAN == type)
			return new BooleanAttribute(id, name, value);

		if (Type.DATE == type)
			return new DateAttribute(id, name, value);

		if (Type.DATE_TIME == type)
			return new DateTimeAttribute(id, name, value);

		if (Type.DECIMAL == type)
			return new DecimalAttribute(id, name, value);

		if (Type.INTEGER == type)
			return new IntegerAttribute(id, name, value);

		if (Type.LONG == type)
			return new LongAttribute(id, name, value);

		throw new IllegalArgumentException("некоректный тип доп. атрибута");
	}

	/**
	 * Создать новый доп. атрибут шаблона документа
	 * @param attribute исходный атрибут
	 * @return дополнительный атрибут
	 */
	public ExtendedAttribute create(com.rssl.phizic.gate.templates.services.generated.ExtendedAttribute attribute)
	{
		if (attribute == null)
		{
			throw new NullPointerException("наименование доп. атрибута не может быть null");
		}

		return create(attribute.getId(), attribute.getName(), Type.valueOf(attribute.getType()), attribute.getValue());
	}
}
