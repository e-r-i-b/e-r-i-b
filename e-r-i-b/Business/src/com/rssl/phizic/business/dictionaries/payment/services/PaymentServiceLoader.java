package com.rssl.phizic.business.dictionaries.payment.services;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.system.SystemServiceConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author gladishev
 * @ created 10.06.2010
 * @ $Author$
 * @ $Revision$
 */
public class PaymentServiceLoader
{
	private static final String DELIMETER = "\\|";

	private List<PaymentService> paymentServices = new ArrayList<PaymentService>();

	public List<PaymentService> getServices()
	{
		return paymentServices;
	}

	public void load()
	{
		try
		{
			for ( Map.Entry entry : ConfigFactory.getConfig(SystemServiceConfig.class).getPaymentServices().entrySet() )
			{
				String value = (String) entry.getValue();

				String[] result = value.split(DELIMETER);

				PaymentService service = new PaymentService();
				service.setSynchKey(result[0]);
				service.setName(result[1]);
				service.setDefaultImage(result[2]);
				service.setSystem(true);
				service.setPopular(false);

				paymentServices.add(service);
			}

		}
		catch(Exception e)
		{
			throw new RuntimeException("Ошибка при загрузке сервиса", e);
		}
	}
}
