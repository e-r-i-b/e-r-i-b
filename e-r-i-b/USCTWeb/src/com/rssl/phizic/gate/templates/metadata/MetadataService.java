package com.rssl.phizic.gate.templates.metadata;

import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.gate.templates.impl.*;

import java.util.HashMap;
import java.util.Map;


/**
 * Сервис для работы с метаданными шаблона документа.
 *
 * @author khudyakov
 * @ created 30.04.14
 * @ $Author$
 * @ $Revision$
 */
public class MetadataService
{
	private static final Map<String, Metadata> FORM_NAME_TO_CLASS_TYPE = new HashMap<String, Metadata>();
	private static final MetadataService INSTANCE = new MetadataService();

	static
	{
		//Важно: при расширении состава метаданных вынести хранение в БД
		FORM_NAME_TO_CLASS_TYPE.put(FormType.CONVERT_CURRENCY_TRANSFER.getName(),           new MetadataImpl(FormType.CONVERT_CURRENCY_TRANSFER.getName(), ConvertCurrencyTransferTemplate.class));
		FORM_NAME_TO_CLASS_TYPE.put(FormType.IMA_PAYMENT.getName(),                         new MetadataImpl(FormType.IMA_PAYMENT.getName(), IMATransferTemplate.class));
		FORM_NAME_TO_CLASS_TYPE.put(FormType.JURIDICAL_TRANSFER.getName(),                  new MetadataImpl(FormType.JURIDICAL_TRANSFER.getName(), IndividualTransferTemplate.class));
		FORM_NAME_TO_CLASS_TYPE.put(FormType.INDIVIDUAL_TRANSFER.getName(),                 new MetadataImpl(FormType.INDIVIDUAL_TRANSFER.getName(),IndividualTransferTemplate.class));
		FORM_NAME_TO_CLASS_TYPE.put(FormType.INDIVIDUAL_TRANSFER_NEW.getName(),             new MetadataImpl(FormType.INDIVIDUAL_TRANSFER_NEW.getName(),IndividualTransferTemplate.class));
		FORM_NAME_TO_CLASS_TYPE.put(FormType.INTERNAL_TRANSFER.getName(),                   new MetadataImpl(FormType.INTERNAL_TRANSFER.getName(), InternalTransferTemplate.class));
		FORM_NAME_TO_CLASS_TYPE.put(FormType.LOAN_PAYMENT.getName(),                        new MetadataImpl(FormType.LOAN_PAYMENT.getName(), LoanTransferTemplate.class));
		FORM_NAME_TO_CLASS_TYPE.put(FormType.EXTERNAL_PAYMENT_SYSTEM_TRANSFER.getName(),    new MetadataImpl(FormType.EXTERNAL_PAYMENT_SYSTEM_TRANSFER.getName(), PaymentSystemTransferTemplate.class));
		FORM_NAME_TO_CLASS_TYPE.put(FormType.INTERNAL_PAYMENT_SYSTEM_TRANSFER.getName(),    new MetadataImpl(FormType.INTERNAL_PAYMENT_SYSTEM_TRANSFER.getName(), PaymentSystemTransferTemplate.class));
		FORM_NAME_TO_CLASS_TYPE.put(FormType.SECURITIES_TRANSFER_CLAIM.getName(),           new MetadataImpl(FormType.SECURITIES_TRANSFER_CLAIM.getName(), SecuritiesTransferTemplate.class));
	}


	public static MetadataService getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Вернуть метаданные по названию формы
	 * @param formName название формы
	 * @return метаданные шаблона документа
	 */
	public Metadata findByFormName(String formName)
	{
		Metadata metadata = FORM_NAME_TO_CLASS_TYPE.get(formName);
		if (metadata == null)
		{
			throw new IllegalArgumentException("Не найдены метаданные шаблона платежа, formName " + formName);
		}
		return metadata;
	}
}
