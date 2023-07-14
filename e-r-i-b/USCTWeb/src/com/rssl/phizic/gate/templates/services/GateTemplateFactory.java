package com.rssl.phizic.gate.templates.services;

import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.templates.impl.TemplateDocument;
import com.rssl.phizic.gate.templates.services.builders.IndividualTransferTemplateBuilder;
import com.rssl.phizic.gate.templates.services.builders.PaymentSystemTransferTemplateBuilder;
import com.rssl.phizic.gate.templates.services.builders.TemplateBuilder;
import com.rssl.phizic.gate.templates.services.builders.TransferTemplateBuilder;
import com.rssl.phizic.gate.templates.services.generated.GateTemplate;

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
public class GateTemplateFactory
{
	private static final GateTemplateFactory INSTANCE = new GateTemplateFactory();
	private static final Map<FormType, TemplateBuilder> BUILDERS = new HashMap<FormType, TemplateBuilder>();


	static
	{
		BUILDERS.put(FormType.CONVERT_CURRENCY_TRANSFER,                new TransferTemplateBuilder());
		BUILDERS.put(FormType.INTERNAL_TRANSFER,                        new TransferTemplateBuilder());
		BUILDERS.put(FormType.IMA_PAYMENT,                              new TransferTemplateBuilder());
		BUILDERS.put(FormType.INDIVIDUAL_TRANSFER,                      new IndividualTransferTemplateBuilder());
		BUILDERS.put(FormType.INDIVIDUAL_TRANSFER_NEW,                  new IndividualTransferTemplateBuilder());
		BUILDERS.put(FormType.JURIDICAL_TRANSFER,                       new IndividualTransferTemplateBuilder());
		BUILDERS.put(FormType.LOAN_PAYMENT,                             new TransferTemplateBuilder());
		BUILDERS.put(FormType.SECURITIES_TRANSFER_CLAIM,                new TransferTemplateBuilder());
		BUILDERS.put(FormType.EXTERNAL_PAYMENT_SYSTEM_TRANSFER,         new PaymentSystemTransferTemplateBuilder());
		BUILDERS.put(FormType.INTERNAL_PAYMENT_SYSTEM_TRANSFER,         new PaymentSystemTransferTemplateBuilder());
	}

	private GateTemplateFactory()
	{}

	public static GateTemplateFactory getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Создать шаблон документа
	 * @param generated объект
	 * @return шаблон
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public TemplateDocument build(GateTemplate generated) throws Exception
	{
		return getBuilder(generated).build(generated);
	}

	/**
	 * Построить шаблон документа
	 * @param template существующий шаблон
	 * @param generated объект
	 * @return шаблон
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public TemplateDocument build(TemplateDocument template, GateTemplate generated) throws Exception
	{
		//noinspection unchecked
		return getBuilder(generated).build(template, generated);
	}

	private TemplateBuilder getBuilder(GateTemplate generated)
	{
		return BUILDERS.get(FormType.valueOf(generated.getFormType()));
	}
}
