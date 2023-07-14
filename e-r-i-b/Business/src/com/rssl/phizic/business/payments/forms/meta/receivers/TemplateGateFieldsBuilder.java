package com.rssl.phizic.business.payments.forms.meta.receivers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.Field;
import com.rssl.common.forms.FormException;
import com.rssl.common.forms.types.SubType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizgate.common.payments.ExtendedFieldsHelper;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.payments.forms.TemplateFieldBuilderHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author khudyakov
 * @ created 04.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class TemplateGateFieldsBuilder implements FieldsBuilder
{
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();

	private List<? extends com.rssl.phizic.gate.payments.systems.recipients.Field> fields = new ArrayList<com.rssl.phizic.gate.payments.systems.recipients.Field>();
	private Map<String, String> formData = new HashMap<String, String>();
	private Long receiverInternalId;
	private boolean edit = false;
	private boolean isInitialLongOffer = false;
	private String billingCode;

	public TemplateGateFieldsBuilder(TemplateDocument template) throws BusinessException
	{
		try
		{
			fields = template.getExtendedFields();
			formData.putAll(template.getFormData());
			receiverInternalId = template.getReceiverInternalId();
			billingCode = template.getBillingCode();
			edit = true;

			if (CollectionUtils.isEmpty(fields) && receiverInternalId != null)
			{
				ServiceProviderBase provider = serviceProviderService.findById(receiverInternalId);
				if (provider != null)
				{
					fields = provider.getFieldDescriptions();
				}
			}
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	public TemplateGateFieldsBuilder(JurPayment document, TemplateDocument template, boolean isInitialLongOffer) throws BusinessException
	{
		try
		{
			//TODO уйти от двойственности при создании/редактировании шаблона/документа по шаблону (запрос)
			this.formData.putAll(template.getFormData());
			this.receiverInternalId = template.getReceiverInternalId();
			this.billingCode = template.getBillingCode();
			this.isInitialLongOffer = isInitialLongOffer;

			if (document == null || document.getExtendedFields() == null)
			{
				if (receiverInternalId != null)
				{
					ServiceProviderBase provider = serviceProviderService.findById(receiverInternalId);
					if (provider != null)
					{
						fields = provider.getFieldDescriptions();
					}
				}
				else
				{
					fields = new ArrayList<com.rssl.phizic.gate.payments.systems.recipients.Field>();
				}
			}
			else
			{
				fields = document.getExtendedFields();
			}
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	public List<Field> buildFields() throws BusinessException
	{
		return TemplateFieldBuilderHelper.adaptFields(fields, formData, getSubType(), receiverInternalId == null, billingCode);
	}

	public Element buildFieldsDictionary()
	{
		if (fields == null)
		{
			return null;
		}

		try
		{
			return XmlHelper.parse(ExtendedFieldsHelper.serialize(fields)).getDocumentElement();
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

	private SubType getSubType() throws BusinessException
	{
		if (edit || receiverInternalId == null)
		{
			return isInitialLongOffer ? SubType.STATIC : SubType.DINAMIC;
		}
		return SubType.fromValue(DocumentHelper.getEditMode(receiverInternalId));
	}
}
