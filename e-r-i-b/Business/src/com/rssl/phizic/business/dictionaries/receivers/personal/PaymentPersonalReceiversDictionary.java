package com.rssl.phizic.business.dictionaries.receivers.personal;

import com.rssl.common.forms.types.Type;
import com.rssl.common.forms.types.TypesConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.PaymentReceiverBase;
import com.rssl.phizic.business.dictionaries.config.Constants;
import com.rssl.phizic.business.dictionaries.config.DictionaryPathConfig;
import com.rssl.phizic.business.dictionaries.receivers.personal.generated.FieldDescriptor;
import com.rssl.phizic.business.dictionaries.receivers.personal.generated.ReceiverDescriptor;
import com.rssl.phizic.business.dictionaries.receivers.personal.generated.ReceiversType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.resources.ResourceHelper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * @author Egorova
 * @ created 19.05.2008
 * @ $Author$
 * @ $Revision$
 */
public class PaymentPersonalReceiversDictionary
{
	private ReceiversType receivers;

	/**	 
	 * @throws BusinessException
	 */
	public PaymentPersonalReceiversDictionary() throws BusinessException
	{
		DictionaryPathConfig dictionaryPathConfig = ConfigFactory.getConfig(DictionaryPathConfig.class);
		String               fileName             = dictionaryPathConfig.getPath(Constants.RECEIVERS);

		if ( fileName == null )
			throw new BusinessException("Не найден путь к файлу справочника получателей [" + Constants.RECEIVERS + "]");

		try
		{
			ClassLoader classLoader   = Thread.currentThread().getContextClassLoader();
			JAXBContext context       = JAXBContext.newInstance("com.rssl.phizic.business.dictionaries.receivers.personal.generated", classLoader);
			Unmarshaller unmarshaller = context.createUnmarshaller();

			InputStream inputStream  = ResourceHelper.loadResourceAsStream(fileName);
			receivers      = (ReceiversType) unmarshaller.unmarshal(inputStream);
		}
		catch (JAXBException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получение ReceiverDescriptor по имени класса получателя
	 * @param kind - тип получетеля (1 буква)
	 * @return описакние получателя (ReceiverDescriptor)
	 * @throws BusinessException
	 */
	public ReceiverDescriptor getReceiverDescriptor(String kind) throws BusinessException
	{
		List<ReceiverDescriptor> receiverDescriptors = receivers.getReceiverDescriptors();

		for ( ReceiverDescriptor receiverDescriptor : receiverDescriptors )
		{
			if (receiverDescriptor.getKind().equals(kind))
					return receiverDescriptor;

		}
		throw new BusinessException("Не найден получатель [kind = " + kind + "]");
	}

	/**
	 * Получение ReceiverDescriptor по имени класса получателя
	 * @param className - имя класса получателя
	 * @return описакние получателя (ReceiverDescriptor)
	 * @throws BusinessException
	 */
	public ReceiverDescriptor getReceiverDescriptorByClassName(String className) throws BusinessException
	{
		List<ReceiverDescriptor> receiverDescriptors = receivers.getReceiverDescriptors();

		for ( ReceiverDescriptor receiverDescriptor : receiverDescriptors )
		{
			if (receiverDescriptor.getClassName().equals(className))
					return receiverDescriptor;

		}
		throw new BusinessException("Не найден получатель [className = " + className + "]");
	}

	public List<String> getAvaibleReceiverKinds()
	{
		List<ReceiverDescriptor> receiverDescriptors = receivers.getReceiverDescriptors();
		if(receiverDescriptors!=null)
		{
			List<String> kinds = new ArrayList<String>(receiverDescriptors.size());

			for ( ReceiverDescriptor receiverDescriptor : receiverDescriptors )
			{
				kinds.add(receiverDescriptor.getKind());
			}
			return kinds;
		}
		else
			return null;
	}

	/**
	 * @param receiver - получатель, чьи значения мы вытаскиваем
	 * @param e - описание поля (как в receivers.xml)
	 * @return Значение поля (свойства)
	 * @throws IllegalAccessException, NoSuchMethodException, InvocationTargetException
	 */
	public String calculateFieldValue(PaymentReceiverBase receiver, FieldDescriptor e) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException
		{
		if (e.getPath()==null)
			return BeanUtils.getProperty(receiver,e.getName());
		String[] properties = e.getPath().split("()*(\\.)()*"); //разбиваем строку на свойства, чтобы последовательно достать значения
		if (properties.length!=0)
		{
			BeanUtilsBean beanUtils = new BeanUtilsBean();
			Object value = receiver;
			for (String property: properties)
			{
				value = beanUtils.getPropertyUtils().getProperty(value, property);
				if (value == null)
					return null;
			}
			return value.toString();
		}
		return null;
	}

	/**
	 * @param receiver - заполняемый получатель
	 * @param e - описание поля
	 * @param value - значение поля
	 * @throws IllegalAccessException, NoSuchMethodException, InvocationTargetException
	 */
	public void setFieldValue(PaymentReceiverBase receiver, FieldDescriptor e, String value) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, BusinessException
	{
		Object objectValue = parse (e, value);
		if (e.getPath()==null)
		{
			BeanUtils.setProperty(receiver, e.getName(), objectValue);
			return;
		}
		String[] properties = e.getPath().split("()*(\\.)()*"); //разбиваем строку на свойства, чтобы последовательно достать значения
		if (properties.length!=0)
		{
			BeanUtilsBean beanUtils = new BeanUtilsBean();
			Object bean = receiver;
			for (int i=0; i<properties.length-1; i++)
			{
				bean = beanUtils.getPropertyUtils().getProperty(bean, properties[i]);
				if (bean == null)
					return;
			}
			BeanUtils.setProperty(bean, e.getName(), objectValue);
		}
	}

	/**
	 * Парсим поле и получем такой тип, который оперелен в receivers.xml, если не определен ничего не делаем
	 * @param e - описание поля
	 * @param value - значение поля
	 * @return Значение поля "правильного" типа
	 * @throws BusinessException
	 */
	private Object parse(FieldDescriptor e, String value) throws BusinessException
	{
		if (e.getType()== null)
			return value;
		TypesConfig typesConfig = ConfigFactory.getConfig(TypesConfig.class);
		Type type = typesConfig.getType(e.getType());
		try
		{
			return type.getDefaultParser().parse(value);
		}
		catch(ParseException ex)
		{
			throw new BusinessException("Ошибка при разборе поля "+e.getType()+". "+ex);
		}
	}
}
