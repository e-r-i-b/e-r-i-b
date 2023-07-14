package com.rssl.phizic.business.dictionaries.receivers;

import com.rssl.phizic.business.dictionaries.receivers.generated.PaymentDescriptor;
import com.rssl.phizic.business.dictionaries.receivers.generated.ReceiverDescriptor;
import com.rssl.phizic.business.documents.payments.GoodsAndServicesPayment;
import com.rssl.phizic.test.BusinessTestCaseBase;
import freemarker.template.*;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

/**
 * @author Evgrafov
 * @ created 03.05.2007
 * @ $Author: krenev $
 * @ $Revision: 8953 $
 */

@SuppressWarnings({"JavaDoc"})
public class PaymentRecieversGroundTemplateLoaderTest extends BusinessTestCaseBase
{
	public void testPaymentRecieversGroundTemplateLoader() throws Exception
	{
		Configuration configuration = createConfiguration();

		PaymentReceiversDictionary dictionary = new PaymentReceiversDictionary();

		GoodsAndServicesPayment payment = new GoodsAndServicesPayment();
		TemplateModel model = new PaymentExtraAttributesHashModel(payment);

		System.out.println(new Date());
		test(dictionary, configuration, model);
		System.out.println(new Date());
		test(dictionary, configuration, model);
		System.out.println(new Date());

	}

	private void test(PaymentReceiversDictionary dictionary, Configuration configuration, TemplateModel model) throws IOException, TemplateException
	{
		List<PaymentDescriptor> pds = dictionary.getPaymentDescriptors();

		for (PaymentDescriptor pd : pds)
		{
			List<ReceiverDescriptor> rds = dictionary.getReceiverDescriptors(pd.getAppointment());

			for (ReceiverDescriptor rd : rds)
			{
				String templateName = pd.getAppointment() + "#" + rd.getKey();
				Template template = configuration.getTemplate(templateName);
				System.out.print(templateName);
				System.out.print(" ");
				System.out.print(template);
				System.out.println();
				StringWriter writer = new StringWriter();
				template.process(model, writer);
				assertNotNull(writer.getBuffer().toString());
			}
		}
	}

	private static Configuration createConfiguration()
			throws IOException
	{
		Configuration configuration = new Configuration();

//		// - Set an error handler that prints errors so they are readable with a HTML browser.
//		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
		// - Use beans wrapper (recommmended for most applications)
		configuration.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
//		// - Set the default charset of the template files
//		configuration.setDefaultEncoding("windows-1251");
//		// - Set the charset of the output. This is actually just a hint, that
//		//   templates may require for URL encoding and for generating META element
//		//   that uses http-equiv="Content-type".
//		configuration.setOutputEncoding("windows-1251");
		// - Set the default locale
//		configuration.setLocale(new Locale("ru", "RU"));
		configuration.setLocalizedLookup(false);
		// - Set the directory of the template files
		configuration.setTemplateLoader(new PaymentRecieversGroundTemplateLoader());
		return configuration;
	}
}
