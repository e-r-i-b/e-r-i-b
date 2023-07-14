package com.rssl.phizic.business.extendedattributes;

import org.w3c.dom.Element;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Roshka
 * @ created 08.12.2005
 * @ $Author$
 * @ $Revision$
 */
public abstract class AttributableBase implements Attributable
{
	private Map<String, ExtendedAttribute>  extendedAttributes = new HashMap<String, ExtendedAttribute>();

    public Map<String, ExtendedAttribute> getAttributes()
    {
        return extendedAttributes;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    private void setAttributes(Map<String, ExtendedAttribute> extendedProperties)
    {
        this.extendedAttributes=extendedProperties;
    }

    public ExtendedAttribute getAttribute(String name)
    {
        return extendedAttributes.get(name);
    }

    public void addAttribute(ExtendedAttribute attr)
    {
        extendedAttributes.put(attr.getName(), attr);
    }

	/**
	 * Добавить список новых атрибутов, со списком ограничений.
	 * @param newExtendedAttributes список
	 * @param restriction список полей, которые не должны скопироваться при копии.
	 */
	public void addAttributes(Collection<ExtendedAttribute> newExtendedAttributes, Set<String> restriction)
	{
		for (ExtendedAttribute attribute : newExtendedAttributes)
		{
			if (restriction.contains(attribute.getName()))
				continue;

			ExtendedAttribute newAttribute = createAttribute(attribute.getType(), attribute.getName(), attribute.getValue());
			addAttribute(newAttribute);
		}
	}

	/**
	 * Добавить список новых атрибутов
	 * @param newExtendedAttributes список
	 */
	public void addAttributes(Collection<ExtendedAttribute> newExtendedAttributes)
	{
		for (ExtendedAttribute attribute : newExtendedAttributes)
		{
			ExtendedAttribute newAttribute = createAttribute(attribute.getType(), attribute.getName(), attribute.getValue());
			addAttribute(newAttribute);
		}
	}

    public void removeAttribute(String name)
    {
        extendedAttributes.remove(name);
    }

	public ExtendedAttribute createAttribute(String type, String name, Object value)
	{
	    ExtendedAttribute attribute = new ExtendedAttribute(type, name);

	    attribute.setValue(value);

	    return attribute;
	}

	public ExtendedAttribute createAttribute(String type, String name, String value)
	{
	    ExtendedAttribute attribute = new ExtendedAttribute(type, name);

	    attribute.setValueByType(type, value);

	    return attribute;
	}

	/**
	 * Установить в атрибут значение
	 * @param element element
	 */
	public void setAttributeValue(Element element)
	{
		setAttributeValue(element.getAttribute("type"), element.getAttribute("name"), element.getAttribute("value"));
	}

	/**
	 * Установить в атрибут значение
	 * если в качестве значения передан null, происходит удаление атрибута.
	 * @param type тип атрибута
	 * @param name имя атрибута
	 * @param value значение атрибута
	 */
	public void setAttributeValue(String type, String name, Object value)
	{
		// если значения нет, то удаляем
		if (value == null)
		{
			removeAttribute(name);
			return;
		}

		ExtendedAttribute attribute = extendedAttributes.get(name);
		boolean isStringValue =  value instanceof String ;
		// атрибута нет, то добавляем
		if(attribute == null)
		{
			addAttribute(isStringValue ?
					createAttribute(type, name, (String) value) : createAttribute(type, name, value));
		}
		// иначе если значение ввиде строки, то сетим как строку
		else if(isStringValue)
		{
			Object oldValue = attribute.getValue();
			attribute.setStringValue((String) value);
			attribute.setIsChanged(attribute.getValue() != null ? !attribute.getValue().equals(oldValue) : oldValue != null);
		}
		else
		{
			attribute.setValue(value);
			attribute.setIsChanged(value.equals(attribute.getValue()));
		}
	}
}
