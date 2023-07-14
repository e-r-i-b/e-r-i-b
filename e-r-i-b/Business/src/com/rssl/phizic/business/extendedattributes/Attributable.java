package com.rssl.phizic.business.extendedattributes;

/**
 * @author Roshka
 * @ created 08.12.2005
 * @ $Author$
 * @ $Revision$
 */
public interface Attributable
{
    /**
     * ѕолучить атрибут по имени.
     * @param name им€ атрибута
     * @return
     */
    ExtendedAttribute getAttribute(String name);

    /**
     * ƒобавить атрибут
     * @param attr
     */
    void addAttribute(ExtendedAttribute attr);

    /**
     * ”далить атрибут
     * @param name
     */
    void removeAttribute(String name);

	/**
	 * —оздать атрибут (не добавл€ет)
	 * @param type тип
	 * @param name им€
	 * @param value значение
	 * @return новый атрибут
	 */
	ExtendedAttribute createAttribute(String type, String name, Object value);

	/**
	 * —оздать атрибут (не добавл€ет)
	 * @param type тип
	 * @param name им€
	 * @param value строковое значение
	 * @return новый атрибут
	 */
	ExtendedAttribute createAttribute(String type, String name, String value);

}
