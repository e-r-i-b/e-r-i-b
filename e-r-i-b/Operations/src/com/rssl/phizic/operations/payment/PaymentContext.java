package com.rssl.phizic.operations.payment;

import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.phizic.utils.StringHelper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.rssl.common.forms.PaymentFieldKeys.*;

/**
 * @author Erkin
 * @ created 08.08.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Контекст платежа
 * Предоставляет данные для шагов редактирования платежа
 */
public class PaymentContext
{
	private boolean copy;

	private final Map<String, String> fields = new HashMap<String, String>();

	private final FieldValueReader fieldReader = new FieldValueReader(fields);

	private boolean personal = false;

	///////////////////////////////////////////////////////////////////////////

	public void clear()
	{
		fields.clear();
	}

	public boolean isCopy()
	{
		return copy;
	}

	public void setCopy(boolean copy)
	{
		this.copy = copy;
	}

	public void putFields(Map<String, String> fields)
	{
		this.fields.putAll(fields);
	}

	public Map<String, String> getFields()
	{
		return Collections.unmodifiableMap(fields);
	}

	public String getFormName()
	{
		return fieldReader.getString(FORM_NAME);
	}

	public void setFormName(String formName)
	{
		this.fields.put(FORM_NAME, formName);
	}

	public Long getPaymentId()
	{
		return fieldReader.getId(PAYMENT_ID_KEY);
	}

	public void setPaymentId(Long paymentId)
	{
		fields.put(PAYMENT_ID_KEY, paymentId.toString());
	}

	public Long getTemplateId()
	{
		return fieldReader.getId(TEMPLATE_ID_KEY);
	}

	public void setTemplateId(Long templateId)
	{
		fields.put(TEMPLATE_ID_KEY, templateId.toString());
	}

	public Long getServiceId()
	{
		return fieldReader.getId(SERVICE_KEY);
	}

	public void setServiceId(Long serviceId)
	{
		if (serviceId== null)
		{
			fields.remove(SERVICE_KEY);
		}
		else
		{
			fields.put(SERVICE_KEY, serviceId.toString());
		}
	}

	public Long getProviderId()
	{
		return fieldReader.getId(PROVIDER_KEY);
	}

	public void setProviderId(Long providerId)
	{
		if (providerId== null)
		{
			fields.remove(PROVIDER_KEY);
		}
		else
		{
			fields.put(PROVIDER_KEY, providerId.toString());
		}
	}

	public String getFromResource()
	{
		return fieldReader.getString(FROM_RESOURCE_KEY);
	}

	public void setFromResource(String fromResource)
	{
		fields.put(FROM_RESOURCE_KEY, fromResource);
	}

	public boolean isPersonal()
	{
		return personal;
	}

	public void setPersonal(boolean personal)
	{
		this.personal = personal;
	}

	public CreationSourceType getCreationSource()
	{
		return (StringHelper.isEmpty(fieldReader.getString(CREATION_SOURCE))) ? null :
				CreationSourceType.valueOf(fieldReader.getString(CREATION_SOURCE));
	}

	public void setCreationSource(CreationSourceType creationSource)
	{
		fields.put(CREATION_SOURCE, creationSource.toString());
	}
}
