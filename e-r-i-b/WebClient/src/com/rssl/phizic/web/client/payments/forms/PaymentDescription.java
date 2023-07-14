package com.rssl.phizic.web.client.payments.forms;

import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.MetadataCache;
import com.rssl.phizic.business.dictionaries.receivers.PaymentReceiversDictionary;
import com.rssl.phizic.business.dictionaries.receivers.generated.PaymentDescriptor;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Egorova
 * @ created 01.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class PaymentDescription
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * ѕолучение описани€ платежа из метаданных по названию
	 * @param paymentName - название платежа
	 * @param appointment - в ќплате товаров и услуг назначение платежа
	 * @return название и описание платежа
	 */
	public static Map getPaymentDescription(String paymentName, String appointment)
	{
		try
	    {
		    Map result = new HashMap();

		    if (appointment==null || appointment.length()==0)
		    {
				Metadata metadata = MetadataCache.getBasicMetadata(paymentName);
				result.put("description", metadata.getForm().getDescription());
				result.put("detailedDescription", metadata.getForm().getDetailedDescription());
		    }
		    else
		    {
				PaymentReceiversDictionary dictionary = new PaymentReceiversDictionary();
				PaymentDescriptor paymentDescriptions = dictionary.getPaymentDescription(appointment);    			    
			    result.put("description", paymentDescriptions.getDescription());
			    result.put("detailedDescription", paymentDescriptions.getDetailedDescription());
		    }

			return result;
	    }
		catch (Exception e)
        {
            log.error("ќшибка получени€ метаданных платежа. ",e);
	        return null;
        }
	}
}
