package com.rssl.phizic.business.payments.forms.meta.basket;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.Field;
import com.rssl.common.forms.FormException;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.types.SubType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.basket.BasketHelper;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.InvoicePayment;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.payments.forms.ExtendedFieldBuilderHelper;
import com.rssl.phizic.business.payments.forms.meta.receivers.ExtendedMetadataLoaderBase;
import org.w3c.dom.Element;

import java.util.List;
import java.util.Map;

/**
 * @author osminin
 * @ created 10.06.14
 * @ $Author$
 * @ $Revision$
 *
 * Загрузчик расширенных метаданных для оплаты инвойса
 */
public class CreateInvoicePaymentMetadataLoader extends ExtendedMetadataLoaderBase
{
	public Metadata load(Metadata metadata, FieldValuesSource fieldSource) throws BusinessException, BusinessLogicException
	{
		throw new UnsupportedOperationException("Операция загрузки меты по источнику значений полей не поддерживается");
	}

	public Metadata load(Metadata metadata, BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		try
		{
			InvoicePayment payment = (InvoicePayment) document;

			List<Field> fields = ExtendedFieldBuilderHelper.adaptFields(payment.getExtendedFields(), SubType.STATIC, payment.getBillingCode());
			Map<String, Element> dictionary = BasketHelper.createDictionary(payment.getExtendedFieldsAsDOM());
			fields.addAll(metadata.getForm().getFields());

			return load(metadata, fields, dictionary, payment.getServiceName());
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	public Metadata load(Metadata metadata, TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		throw new UnsupportedOperationException("Операция загрузки меты для создания шаблона не поддерживается");
	}

	public Metadata load(Metadata metadata, BusinessDocument document, TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		throw new UnsupportedOperationException("Операция загрузки меты по шаблону не поддерживается");
	}

	public String getExtendedMetadataKey(FieldValuesSource fieldSource) throws FormException
	{
		return null;
	}
}
