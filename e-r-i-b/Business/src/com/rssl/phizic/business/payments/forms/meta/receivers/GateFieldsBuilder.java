package com.rssl.phizic.business.payments.forms.meta.receivers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.Field;
import com.rssl.common.forms.FormException;
import com.rssl.common.forms.types.SubType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProviderBase;
import com.rssl.phizgate.common.payments.ExtendedFieldsHelper;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.payments.forms.ExtendedFieldBuilderHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author Krenev
 * @ created 25.12.2009
 * @ $Author$
 * @ $Revision$
 * Билдер полей из гейтового представления.
 */
class GateFieldsBuilder implements FieldsBuilder
{
	private List<? extends com.rssl.phizic.gate.payments.systems.recipients.Field> fields;
	private Document fieldsAsDOM;
	private SubType subType;
	private String billingCode;


	GateFieldsBuilder(BillingServiceProviderBase provider)
	{
		fields = provider.getFieldDescriptions();
		fieldsAsDOM = null;
		billingCode = provider.getBilling().getCode();
	}

	GateFieldsBuilder(JurPayment payment) throws DocumentException
	{
		fields = payment.getExtendedFields();
		fieldsAsDOM = payment.getExtendedFieldsAsDOM();
		billingCode = payment.getBillingCode();
	}

	GateFieldsBuilder(List<com.rssl.phizic.gate.payments.systems.recipients.Field> fields, String billingCode)
	{
		this(fields, SubType.DINAMIC, billingCode);
	}

	GateFieldsBuilder(List<com.rssl.phizic.gate.payments.systems.recipients.Field> fields, SubType subType, String billingCode)
	{
		this.subType = subType;
		this.fields = fields;
		fieldsAsDOM = null;
		this.billingCode = billingCode;
	}

	public List<Field> buildFields() throws BusinessException
	{
		return ExtendedFieldBuilderHelper.adaptFields(this.fields, subType, billingCode);
	}

	public Element buildFieldsDictionary()
	{
		if(fields == null)
			return null;
		try
		{
			if(fieldsAsDOM == null)
			{
				fieldsAsDOM = XmlHelper.parse(ExtendedFieldsHelper.serialize(fields));
			}
			return fieldsAsDOM.getDocumentElement();
		}
		catch (ParserConfigurationException e)
		{
			throw new FormException(e);
		}
		catch (SAXException e)
		{
			throw new FormException(e);
		}
		catch (IOException e)
		{
			throw new FormException(e);
		}
		catch (DocumentException e)
		{
			throw new FormException(e);
		}
	}
}
