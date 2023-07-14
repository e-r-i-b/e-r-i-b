package com.rssl.phizic.business.dictionaries.receivers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.config.Constants;
import com.rssl.phizic.business.dictionaries.config.DictionaryPathConfig;
import com.rssl.phizic.business.dictionaries.receivers.generated.PaymentDescriptor;
import com.rssl.phizic.business.dictionaries.receivers.generated.PaymentReceiverListType;
import com.rssl.phizic.business.dictionaries.receivers.generated.ReceiverDescriptor;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.resources.ResourceHelper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * @author Kidyaev
 * @ created 28.12.2006
 * @ $Author$
 * @ $Revision$
 */
public class PaymentReceiversDictionary
{
	private PaymentReceiverListType paymentReceiverList;

	/**
	 * ctor
	 * @throws BusinessException
	 */
	public PaymentReceiversDictionary() throws BusinessException
	{
		DictionaryPathConfig dictionaryPathConfig = ConfigFactory.getConfig(DictionaryPathConfig.class);
		String               fileName             = dictionaryPathConfig.getPath(Constants.CONTACT_RECEIVERS);

		if ( fileName == null )
			throw new BusinessException("Не найден путь к файлу справочника получателей системы \"Contact\" ["
										+ Constants.CONTACT_RECEIVERS + "]");

		try
		{
			ClassLoader classLoader   = Thread.currentThread().getContextClassLoader();
			JAXBContext context       = JAXBContext.newInstance("com.rssl.phizic.business.dictionaries.receivers.generated", classLoader);
			Unmarshaller unmarshaller = context.createUnmarshaller();

			InputStream inputStream  = ResourceHelper.loadResourceAsStream(fileName);
			paymentReceiverList      = (PaymentReceiverListType) unmarshaller.unmarshal(inputStream);
		}
		catch (JAXBException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param paymentAppointment имя платежа
	 * @return список дескрипторов получателей
	 */
	@SuppressWarnings({"unchecked"})
	public List<ReceiverDescriptor> getReceiverDescriptors(String paymentAppointment)
	{
		List<PaymentDescriptor>  paymentDescriptors  = paymentReceiverList.getPaymentDescriptors();
		List<ReceiverDescriptor> receiverDescriptors = new ArrayList<ReceiverDescriptor>();

		for ( PaymentDescriptor paymentDescriptor : paymentDescriptors )
		{
			if ( paymentDescriptor.getAppointment().equals(paymentAppointment) )
			{
				receiverDescriptors = paymentDescriptor.getReceiverDescriptors();
				break;
			}
		}
		
		return receiverDescriptors;
	}

	/**
	 * @param paymentAppointment имя платежа
	 * @param receiverKey ключ получателя
	 * @return дескриптор получателя
	 */
	@SuppressWarnings({"unchecked"})
	public ReceiverDescriptor getReceiverDescriptor(String paymentAppointment, String receiverKey)
	{
		List<PaymentDescriptor> paymentDescriptors = paymentReceiverList.getPaymentDescriptors();
		ReceiverDescriptor      result             = null;

		EXIT:
		for ( PaymentDescriptor paymentDescriptor : paymentDescriptors )
		{
			if ( !paymentDescriptor.getAppointment().equals(paymentAppointment) )
				continue;

			List<ReceiverDescriptor> receiverDescriptors = paymentDescriptor.getReceiverDescriptors();

			for ( ReceiverDescriptor receiverDescriptor : receiverDescriptors )
			{
				if ( receiverDescriptor.getKey().equals(receiverKey) )
				{
//TODO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//TODO!!!!!!!!! А RETURN receiverDescriptor НЕ РУЛИТ!!!!!!
					result = receiverDescriptor;
					break EXIT;
				}
			}
		}

		return result;
	}

	public List<PaymentDescriptor> getPaymentDescriptors()
	{
		//noinspection unchecked
		return paymentReceiverList.getPaymentDescriptors();
	}

	public PaymentDescriptor getPaymentDescription(String appointment)
	{
		Map<String,String> paymentDescriptions = new HashMap();
		List<PaymentDescriptor> paymentDescriptors = paymentReceiverList.getPaymentDescriptors();
		for ( PaymentDescriptor paymentDescriptor : paymentDescriptors )
		{
			if ( paymentDescriptor.getAppointment().equals(appointment) )
			{
				return paymentDescriptor;
			}
		}
		return null;
	}
}
