package com.rssl.phizic.business.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.FieldInitalValuesSource;
import com.rssl.common.forms.processing.CompositeFieldValuesSource;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.xslt.FormDataBuilder;
import com.rssl.common.forms.xslt.XmlConverter;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.source.TemplateFieldValueSource;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.dataaccess.common.counters.*;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

import javax.xml.transform.dom.DOMResult;

/**
 * @author Evgrafov
 * @ created 30.11.2006
 * @ $Author: saharnova $
 * @ $Revision: 75755 $
 */

public class DocumentService
{
	private static final Counter DOCUMENT_NUMBER = Counter.createExtendedCounter("DOCUMENT_NUMBER", Counters.MAX_DOCUMENT_NUMBER, CounterNameGenerator.EVERY_DAY);
	private static final CounterService counterService = new CounterService();

	/**
	 * Создать новый документ
	 *
	 * @param metadata метаданные
	 * @param initialValuesSource начальные значения
	 * @param messageCollector хранилище ошибок, может быть null
	 * @return новый документ
	 */
	public BusinessDocument createDocument(Metadata metadata, FieldValuesSource initialValuesSource, MessageCollector messageCollector) throws BusinessLogicException, BusinessException
	{
		FieldValuesSource initialXPathValuesSource = new FieldInitalValuesSource(metadata.getForm(), initialValuesSource.getAllValues());
		CompositeFieldValuesSource valuesSource = new CompositeFieldValuesSource(initialValuesSource, initialXPathValuesSource);

		FormDataBuilder dataBuilder = new FormDataBuilder();
		dataBuilder.appentAllFields(metadata.getForm(), valuesSource);

		BusinessDocument temp = metadata.createDocument();
		XmlConverter converter = metadata.createConverter("xml");
		converter.setData(dataBuilder.getFormData());
		Document document = XmlHelper.getDocumentBuilder().newDocument();
		converter.convert(new DOMResult(document));

		try
		{
			temp.initialize(document, messageCollector);
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
		catch (DocumentLogicException e)
		{
			throw new BusinessLogicException(e);
		}

		return temp;
	}

	/**
	 * Создать документ на основе шаблона документа
	 *
	 * @param metadata шаблон
	 * @param template шаблон документа
	 * @return документ
	 */
	public BusinessDocument createDocument(Metadata metadata, TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		try
		{
			FormDataBuilder dataBuilder = new FormDataBuilder();
			dataBuilder.appentAllFields(metadata.getForm(), new TemplateFieldValueSource(template));

			XmlConverter converter = metadata.createConverter("xml");
			converter.setData(dataBuilder.getFormData());

			Document document = XmlHelper.getDocumentBuilder().newDocument();
			converter.convert(new DOMResult(document));

			BusinessDocument temp = metadata.createDocument();
			temp.initialize(template);
			temp.initialize(document, null);

			return temp;
		}
		catch (DocumentLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Сгеренировать номер документа
	 * @return номер документа
	 * @throws DocumentException
	 */
	public String getNextDocumentNumber() throws DocumentException
	{
		try
		{
			for (int i = 0; i < 10; i++)
			{
				Long counterNumber = counterService.getNext(DOCUMENT_NUMBER);
				//по законодательству три последних разряда номера платежа должны быть отличны от «000».
				if (counterNumber % 1000 != 0)
					return counterNumber.toString();
			}
			throw new DocumentException("Защита от зацикливания. 10 раз подряд попался номер, кратный 1000");
		}
		catch (CounterException e)
		{
			throw new DocumentException(e);
		}
	}
}