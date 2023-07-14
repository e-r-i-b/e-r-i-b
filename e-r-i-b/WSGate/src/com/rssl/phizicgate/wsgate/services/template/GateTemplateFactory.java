package com.rssl.phizicgate.wsgate.services.template;

import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizicgate.wsgate.services.template.builders.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Фабрика создания шаблона необходимого типа
 *
 * @author khudyakov
 * @ created 17.04.14
 * @ $Author$
 * @ $Revision$
 */
class GateTemplateFactory
{
	private static final GateTemplateFactory INSTANCE = new GateTemplateFactory();
	private static final Map<FormType, Class<? extends TemplateBuilder>> BUILDERS = new HashMap<FormType, Class<? extends TemplateBuilder>>();


	static
	{
		BUILDERS.put(FormType.CONVERT_CURRENCY_TRANSFER,                TransferTemplateBuilder.class);
		BUILDERS.put(FormType.INTERNAL_TRANSFER,                        TransferTemplateBuilder.class);
		BUILDERS.put(FormType.IMA_PAYMENT,                              TransferTemplateBuilder.class);
		BUILDERS.put(FormType.INDIVIDUAL_TRANSFER,                      PhizIndividualTransferTemplateBuilder.class);
		BUILDERS.put(FormType.INDIVIDUAL_TRANSFER_NEW,                  PhizIndividualTransferTemplateBuilder.class);
		BUILDERS.put(FormType.JURIDICAL_TRANSFER,                       IndividualTransferTemplateBuilder.class);
		BUILDERS.put(FormType.LOAN_PAYMENT,                             TransferTemplateBuilder.class);
		BUILDERS.put(FormType.SECURITIES_TRANSFER_CLAIM,                TransferTemplateBuilder.class);
		BUILDERS.put(FormType.EXTERNAL_PAYMENT_SYSTEM_TRANSFER,         ExternalPaymentSystemTransferTemplateBuilder.class);
		BUILDERS.put(FormType.INTERNAL_PAYMENT_SYSTEM_TRANSFER,         InternalPaymentSystemTransferTemplateBuilder.class);
	}

	private GateTemplateFactory()
	{}

	static GateTemplateFactory getInstance()
	{
		return INSTANCE;
	}

	public TemplateDocument create(Object o) throws GateException, GateLogicException
	{
		com.rssl.phizicgate.wsgate.services.template.generated.GateTemplate generated = (com.rssl.phizicgate.wsgate.services.template.generated.GateTemplate) o;

		Class<? extends TemplateBuilder> clazz = BUILDERS.get(FormType.valueOf(generated.getFormType()));
		return ClassHelper.newInstance(clazz).build(generated);
	}
}
