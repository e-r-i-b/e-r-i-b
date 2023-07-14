package com.rssl.phizic.business.dictionaries.receivers;

import com.rssl.phizic.business.extendedattributes.ExtendedAttribute;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * @author Evgrafov
 * @ created 03.05.2007
 * @ $Author: khudyakov $
 * @ $Revision: 49377 $
 */

public class PaymentExtraAttributesHashModel implements TemplateHashModel
{
	private BusinessDocumentBase document;
	private BeansWrapper wrapper;

	/**
	 * ctor
	 * @param document платеж из доп полей которого берутся данные
	 * @throws TemplateModelException
	 */
	public PaymentExtraAttributesHashModel(BusinessDocument document) throws TemplateModelException
	{
		this.document = (BusinessDocumentBase) document;

		wrapper = new BeansWrapper();
		wrapper.setNullModel(wrapper.wrap(""));
	}

	/**
	 * @param key имя допполя
	 * @return если поле не установлено то пустая строка
	 * @throws TemplateModelException
	 */
	public TemplateModel get(String key) throws TemplateModelException
	{
		ExtendedAttribute extendedAttribute = document.getAttribute(key);

		Object value = extendedAttribute == null ? null : extendedAttribute.getValue();

		return wrapper.wrap(value);
	}

	public boolean isEmpty() throws TemplateModelException
	{
		return false;
	}
}