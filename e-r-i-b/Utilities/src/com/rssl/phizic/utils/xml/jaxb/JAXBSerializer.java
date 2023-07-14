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
	 * @param beanClass - ����� ��������, ������� ����� ��������������� / �����������������
	 * @param charsetName - ��������� XML ��� ������������ / ��������������
	 */
	public JAXBSerializer(Class<B> beanClass, String charsetName)
	{
		super(new Class[]{beanClass}, charsetName);
		this.beanClass = beanClass;
	}

	/**
	 * ������������ � ������
	 * @param bean - ������
	 * @return ������
	 */
	public String marshal(B bean) throws JAXBException
	{
		return super.marshalBean(bean);
	}

	/**
	 * �������������� ������
	 * @param string ������
	 * @return ������
	 */
	public B unmarshal(String string) throws JAXBException
	{
		return super.unmarshalBean(string, beanClass);
	}
}