package com.rssl.phizic.utils.xml.jaxb;

import com.rssl.phizic.common.types.annotation.ThreadSafe;

import javax.xml.bind.JAXBException;

/**
 * @author Roshka
 * @ created 15.02.2007
 * @ $Author$
 * @ $Revision$
 */

@ThreadSafe
public class JAXBSerializer<B> extends JAXBSerializerBase
{
	private final Class<B> beanClass;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param beanClass - класс объектов, которые будем сериализовывать / десериализовывать
	 * @param charsetName - кодировка XML при сериализации / десериализации
	 */
	public JAXBSerializer(Class<B> beanClass, String charsetName)
	{
		super(new Class[]{beanClass}, charsetName);
		this.beanClass = beanClass;
	}

	/**
	 * Сериализация в строку
	 * @param bean - объект
	 * @return строка
	 */
	public String marshal(B bean) throws JAXBException
	{
		return super.marshalBean(bean);
	}

	/**
	 * Десериализация строки
	 * @param string строка
	 * @return объект
	 */
	public B unmarshal(String string) throws JAXBException
	{
		return super.unmarshalBean(string, beanClass);
	}
}