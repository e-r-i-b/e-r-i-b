package com.rssl.phizic.business.ant;

import com.rssl.phizic.business.dictionaries.payment.services.PaymentService;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentServiceLoader;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentServiceSynchronizer;
import com.rssl.phizic.utils.test.SafeTaskBase;

import java.util.List;

/**
 * @author gladishev
 * @ created 09.06.2010
 * @ $Author$
 * @ $Revision$
 */
public class PaymentServicesLoaderTask extends SafeTaskBase
{
	private String root;

	public void setRoot(String root)
	{
		this.root = root;
	}

	public void safeExecute() throws Exception
	{
		if (root == null || root.length() == 0)
			throw new Exception("Не установлен параметр 'root'");

		PaymentServiceLoader loader = new PaymentServiceLoader();
		loader.load();
		List<PaymentService> services = loader.getServices();

		PaymentServiceSynchronizer messagesSynchronizer = new PaymentServiceSynchronizer(services);

		messagesSynchronizer.update();

		log("Updating payment services processed.");
	}
}
