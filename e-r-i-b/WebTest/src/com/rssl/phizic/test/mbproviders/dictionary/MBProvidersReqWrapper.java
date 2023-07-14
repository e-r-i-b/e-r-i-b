package com.rssl.phizic.test.mbproviders.dictionary;

import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.test.mbproviders.dictionary.generated.*;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLMessageWriter;
import org.apache.axis.client.Stub;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.xml.rpc.ServiceException;

/**
 * @author EgorovaA
 * @ created 12.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class MBProvidersReqWrapper
{
	private static final String SMS_SERVICE_ADDRESS = "http://localhost:8888/ErmbDictionaryListener/axis-services/ProvidersDictionaryServicePort";

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_WEB);

	private final ProvidersDictionaryServiceSoapBindingStub stub;

	public MBProvidersReqWrapper() throws ServiceException
	{
		ProvidersDictionaryServiceImplLocator locator = new ProvidersDictionaryServiceImplLocator();
		stub = (ProvidersDictionaryServiceSoapBindingStub) locator.getProvidersDictionaryServicePort();
		stub._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, SMS_SERVICE_ADDRESS);
	}

	/**
	 * Получить справочник поставщиков услуг Мобильного Банка
	 * @return ответ, содержащий справочник, UID и статус(0- запрос выполнен успешно, 1- сбой)
	 */
	public GetProvidersRsType getProviders()
	{
		String uid = new RandomGUID().getStringValue();
		GetProvidersRqType inputParams = new GetProvidersRqType(uid ,Calendar.getInstance());
		try
		{
			return stub.getProviders(inputParams);
		}
		catch (RemoteException e)
		{
			log.error("Ошибка при получении справочника поставшиков услуг Мобильного Банка", e);
			GetProvidersRsType errorResponse = new GetProvidersRsType();
			errorResponse.setRqUID(uid);
			errorResponse.setStatus(1);
			return errorResponse;
		}
	}

	/**
	 * Сформировать строку с ответом на запрос справчника поставщиков
	 * @param result
	 * @return строка в виде xml-документа
	 */
	public String buildMessageXml(GetProvidersRsType result)
	{
		XMLMessageWriter writer = new XMLMessageWriter("windows-1251");

		writer.startDocument();
		writer.startElement("GetMBServiceProvidersRs");
		writer.writeTextElement("rqUID", result.getRqUID());
		long status = result.getStatus();
		writer.writeTextElement("status", String.valueOf(status));
		PaymentType[] payments = result.getResponse();
		if (status == 0 && payments != null)
		{
			writer.startElement("response");
			for (PaymentType payment : payments)
			{
				List<String> missingParams = new ArrayList<String>();
				if (StringHelper.isEmpty(payment.getProviderSmsAlias()))
					missingParams.add("providerSmsAlias");
				if (StringHelper.isEmpty(payment.getServiceName()))
					missingParams.add("serviceName");

				if (missingParams.isEmpty())
				{
					writer.startElement("payment");
					writer.writeTextElement("billingId", payment.getBillingId());
					writer.writeTextElement("serviceId", payment.getServiceId());
					writer.writeTextElement("providerId", payment.getProviderId());
					writer.writeTextElement("serviceName", payment.getServiceName());
					writer.writeTextElement("providerName", payment.getProviderName());
					writer.writeTextElement("providerSmsAlias", payment.getProviderSmsAlias());

					// Если есть реквизиты, создаем элемент "paymentEntries" и заполняем реквизитами ( элемент "entry")
					EntryType[] entries = payment.getPaymentEntries();
					if (entries != null)
					{
						writer.startElement("paymentEntries");
						for (EntryType entry: entries)
						{
							writer.startElement("entry");
							writer.writeTextElement("title", entry.getTitle());
							if (entry.getDescription()!=null)
								writer.writeTextElement("description", entry.getDescription());
							if (entry.getHint()!=null)
								writer.writeTextElement("hint", entry.getHint());
							writer.endElement();
						}
						writer.endElement();
					}
					writer.endElement();
				}
				else
				{
					log.error("Ошибка при получении информации о поставщике с id = " + payment.getProviderId() + ". " +
							"Отсутствуют параметры: " + missingParams);
				}
			}
			writer.endElement();
		}
		writer.endElement();
		writer.endDocument();

		return writer.toString();
	}
}
