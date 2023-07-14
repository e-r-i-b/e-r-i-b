package com.rssl.phizic.business.dictionaries.receivers;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.extendedattributes.Attributable;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Вычисляет основание полатежа по строке форматирования
 * @author Evgrafov
 * @ created 04.05.2007
 * @ $Author: khudyakov $
 * @ $Revision: 49377 $
 */

public class PaymentRecieversGroundEvaluator
{

	private final Configuration configuration;

	/**
	 * ctor
	 */
	public PaymentRecieversGroundEvaluator()
	{
		configuration = createConfiguration();
	}

	/**
	 * Вычислить основание для платежа
	 * @param payment платеж
	 * @return основание
	 * @throws BusinessException
	 */
	public String evaluate(Attributable payment) throws BusinessException
	{
		try
		{
			String appointment = payment.getAttribute("appointment").getStringValue();
			String receiverKey = payment.getAttribute("receiverKey").getStringValue();
			Template template = configuration.getTemplate(appointment + "#" + receiverKey);

			PaymentExtraAttributesHashModel hashModel = new PaymentExtraAttributesHashModel((BusinessDocument) payment);
			StringWriter writer = new StringWriter();

			template.process(hashModel, writer);

			return writer.getBuffer().toString();
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
		catch (TemplateException e)
		{
			throw new BusinessException(e);
		}
	}

	private static Configuration createConfiguration()
	{
		Configuration configuration = new Configuration();

		configuration.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
		configuration.setLocalizedLookup(false);
		configuration.setTemplateLoader(new PaymentRecieversGroundTemplateLoader());

		return configuration;
	}
}