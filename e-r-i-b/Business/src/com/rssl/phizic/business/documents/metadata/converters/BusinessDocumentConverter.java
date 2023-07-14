package com.rssl.phizic.business.documents.metadata.converters;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.xslt.FormDataBuilder;
import com.rssl.common.forms.xslt.XmlConverter;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import org.w3c.dom.Document;

import java.util.Map;
import javax.xml.transform.dom.DOMResult;

/**
 *  овертирует данные формы в платеж
 * @author Evgrafov
 * @ created 05.12.2005
 * @ $Author: saharnova $
 * @ $Revision: 75755 $
 */
public class BusinessDocumentConverter
{
    private BusinessDocument businessDocument;
    private Map<String, Object> formData;
	private Metadata metadata;


	/**
	 * ќбновить документ введенными данными
	 * @param metadata метаданные
	 * @param businessDocument документ дл€ обовлени€
	 * @param formData введенные данные
	 */
	@SuppressWarnings({"AssignmentToCollectionOrArrayFieldFromParameter"}) //класс с коротким временем жизни
	public BusinessDocumentConverter(Metadata metadata, BusinessDocument businessDocument, Map<String, Object> formData)
	{
		this.metadata = metadata;
		this.businessDocument = businessDocument;
		this.formData = formData;
	}

    /**
     * ѕереобразовать данные формы в XML представление и обновить платеж
     */
    public void update() throws DocumentLogicException, DocumentException
    {
	    FormDataBuilder dataBuilder = new FormDataBuilder();
	    dataBuilder.appentAllFileds(metadata.getForm(), formData);

	    XmlConverter converter = metadata.createConverter("xml");

	    converter.setData(dataBuilder.getFormData());
	    DOMResult documentResult = new DOMResult();
	    converter.convert(documentResult);

        businessDocument.readFromDom((Document) documentResult.getNode(), null);
    }
}
