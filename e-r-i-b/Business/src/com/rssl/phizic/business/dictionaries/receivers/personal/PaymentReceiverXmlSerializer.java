package com.rssl.phizic.business.dictionaries.receivers.personal;

import org.w3c.dom.Element;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.business.dictionaries.PaymentReceiverBase;
import com.rssl.phizic.business.dictionaries.receivers.personal.generated.FieldDescriptor;
import com.rssl.phizic.business.dictionaries.receivers.personal.generated.ReceiverDescriptor;
import com.rssl.phizic.business.BusinessException;

import java.util.List;
import javax.xml.transform.TransformerException;

/**
 * @author Egorova
 * @ created 19.11.2008
 * @ $Author$
 * @ $Revision$
 */
public class PaymentReceiverXmlSerializer
{
	private PaymentReceiverBase receiver;

	/**
	 * @param receiver - получатель для преобразования
	 */
	public PaymentReceiverXmlSerializer(PaymentReceiverBase receiver)
    {
        this.receiver = receiver;
    }

	/**
	 * Построить xml преобразование получателя. Формат <названиеПоля>значениеПоля</названиеПоля>
	 * @param root - родительский тег
	 */
	public void buildXmlRepresentation(Element root) throws BusinessException
	{
		try
		{
			PaymentPersonalReceiversDictionary dictionary = new PaymentPersonalReceiversDictionary();
			ReceiverDescriptor receiverDescriptor = dictionary.getReceiverDescriptor(receiver.getKind());
			List<FieldDescriptor> fieldDescriptors = receiverDescriptor.getFieldDescriptors();
			for (FieldDescriptor e: fieldDescriptors){
				XmlHelper.appendSimpleElement(root, e.getName(), calculateFieldValue(dictionary, e));
			}
			XmlHelper.appendSimpleElement(root, "kind", receiver.getKind());
		}
		catch(Exception e)
		{
			throw new BusinessException("Ошибка преобразования получателя "+receiver.getAlias()+" в xml. "+e);
		}
	}

	/**
	 * Получаем значение поля
	 */
	private String calculateFieldValue(PaymentPersonalReceiversDictionary dictionary, FieldDescriptor e) throws BusinessException
	{
		try
		{
			return dictionary.calculateFieldValue(receiver, e);
		}
		catch(Exception ex)
		{
			throw new BusinessException("Ошибка при получении значения поля "+e.getName()+". "+e);
		}
	}

	/**
	 * Заполняем получателя из xml
	 * @param root - текущий элемент
	 * @return получателя
	 * @throws BusinessException
	 */
	public PaymentReceiverBase fillFromXml(Element root)  throws BusinessException
	{
		try
		{
			PaymentPersonalReceiversDictionary dictionary = new PaymentPersonalReceiversDictionary();
			ReceiverDescriptor receiverDescriptor = dictionary.getReceiverDescriptorByClassName(receiver.getClass().getName());
			List<FieldDescriptor> fieldDescriptors = receiverDescriptor.getFieldDescriptors();
			for (FieldDescriptor e: fieldDescriptors){
				 dictionary.setFieldValue(receiver, e, getElementValueByPath(root,e.getName()));
			}
			return receiver;
		}
		catch(Exception e)
		{
			throw new BusinessException("Ошибка преобразования получателя из xml. "+e);
		}
	}

	private String getElementValueByPath(Element rootNow, String pathNow) throws BusinessException
	{
		try
		{
			return XmlHelper.getElementValueByPath(rootNow, pathNow);
		}
		catch(TransformerException ex)
		{
			throw new BusinessException("Ошибка преобразования",ex);
		}
	}
}
