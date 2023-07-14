package com.rssl.phizic.operations.documents.templates;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.ErrorsCollector;
import com.rssl.common.forms.validators.SimpleMessageHolder;
import com.rssl.common.forms.xslt.FormDataBuilder;
import com.rssl.common.forms.xslt.XmlConverter;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.metadata.source.SimpleFieldValueSource;
import com.rssl.phizic.business.documents.metadata.source.TemplateFieldValueSource;
import com.rssl.phizic.business.payments.forms.ExtendedFieldBuilderHelper;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.transform.dom.DOMResult;

/**
 * Базовый клас операции редактирования
 *
 * @author khudyakov
 * @ created 08.03.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class EditTemplateOperationBase extends ViewTemplateOperationBase implements EditTemplateDocumentOperation
{
	public void initialize(Long recipientId) throws BusinessException, BusinessLogicException
	{

	}

	public void update(Map<String, Object> formData) throws BusinessLogicException, BusinessException
	{
		XmlConverter converter = metadata.createConverter("xml");
		converter.setData(new FormDataBuilder().appentAllFileds(metadata.getForm(), formData).getFormData());

		Document document = XmlHelper.getDocumentBuilder().newDocument();;
		converter.convert(new DOMResult(document));

		template.setFormData(new SimpleFieldValueSource(metadata, document));
	}

	/**
	 * Получить ошибки редактирования
	 * @param errorsCollector errorsCollector
	 * @param <E>
	 * @return ошибки
	 * @throws BusinessException
	 */
	public <E> ErrorsCollector<E> getTemplateErrors(ErrorsCollector<E> errorsCollector) throws BusinessException
	{
		try
		{
			List<Field> extendedFields = template.getExtendedFields();
			if (CollectionUtils.isEmpty(extendedFields))
			{
				return errorsCollector;
			}

			for (Field field : extendedFields)
			{
				if (!StringHelper.isEmpty(field.getError()))
				{
					errorsCollector.add(StringHelper.getEmptyIfNull(field.getValue()), ExtendedFieldBuilderHelper.adaptField(field), new SimpleMessageHolder(field.getError()));
				}
			}
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
		return errorsCollector;
	}

	/**
	 * @return линк ресурса списания
	 * @throws BusinessException
	 */
	public PaymentAbilityERL getChargeOffResourceLink() throws BusinessException
	{
		return template.getChargeOffResourceLink();
	}

	public void setChargeOffResourceLink(PaymentAbilityERL chargeOffResourceLink)
	{
		return;
	}

	public Map<String, Object> getTemplateFieldValues() throws BusinessException
	{
		Map<String, Object> values = new HashMap<String, Object>();
		for (Map.Entry<String, String> entry : getFieldValuesSource().getAllValues().entrySet())
		{
			values.put(entry.getKey(), entry.getValue());
		}
		return values;
	}

	public TemplateFieldValueSource getFieldValuesSource() throws BusinessException
	{
		return new TemplateFieldValueSource(template);
	}
}
