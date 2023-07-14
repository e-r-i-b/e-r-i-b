package com.rssl.phizic.web.documents.templates;

import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author khudyakov
 * @ created 20.02.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class TemplateFormBase extends EditFormBase
{
	private TemplateDocument template;
	private Long templateId;
	private String templateName;
	private String html;
	private String category;
	private String formName;
	private String formDescription;
	private Metadata metadata;
	private String metadataPath;
	private String title;
	private String form;
	private Person owner;
	private String bankBIC;
	private String bankName;
	private boolean externalAccountPaymentAllowed;
	private String operationUID;
	private boolean avaliablePayment = true;
	private boolean fromFinanceCalendar = false;
	private String extractId;

	public TemplateDocument getTemplate()
	{
		return template;
	}

	public void setTemplate(TemplateDocument template)
	{
		this.template = template;
	}

	public Long getTemplateId()
	{
		return templateId;
	}

	public void setTemplateId(Long templateId)
	{
		this.templateId = templateId;
	}

	public String getTemplateName()
	{
		return templateName;
	}

	public void setTemplateName(String templateName)
	{
		this.templateName = templateName;
	}

	public String getHtml()
	{
		return html;
	}

	public void setHtml(String html)
	{
		this.html = html;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public void setFormName(String formName)
	{
		this.formName = formName;
	}

	public String getFormName()
	{
		return formName;
	}

	public void setFormDescription(String formDescription)
	{
		this.formDescription = formDescription;
	}

	public String getFormDescription()
	{
		return formDescription;
	}

	public void setMetadata(Metadata metadata)
	{
		this.metadata = metadata;
	}

	public Metadata getMetadata()
	{
		return metadata;
	}

	public String getMetadataPath()
	{
		return metadataPath;
	}

	public void setMetadataPath(String metadataPath)
	{
		this.metadataPath = metadataPath;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getTitle()
	{
		return title;
	}

	public void setForm(String form)
	{
		this.form = form;
	}

	public String getForm()
	{
		return form;
	}

	public void setOwner(Person owner)
	{
		this.owner = owner;
	}

	public Person getOwner()
	{
		return owner;
	}

	public void setBankBIC(String bankBIC)
	{
		this.bankBIC = bankBIC;
	}

	public String getBankBIC()
	{
		return bankBIC;
	}

	public void setBankName(String bankName)
	{
		this.bankName = bankName;
	}

	public String getBankName()
	{
		return bankName;
	}

	public void setExternalAccountPaymentAllowed(boolean externalAccountPaymentAllowed)
	{
		this.externalAccountPaymentAllowed = externalAccountPaymentAllowed;
	}

	public boolean isExternalAccountPaymentAllowed()
	{
		return externalAccountPaymentAllowed;
	}

	public String getOperationUID()
	{
		return operationUID;
	}

	public void setOperationUID(String operationUID)
	{
		this.operationUID = operationUID;
	}

	public boolean isAvaliablePayment()
	{
		return avaliablePayment;
	}

	public void setAvaliablePayment(boolean avaliablePayment)
	{
		this.avaliablePayment = avaliablePayment;
	}

	public boolean isFromFinanceCalendar()
	{
		return fromFinanceCalendar;
	}

	public void setFromFinanceCalendar(boolean fromFinanceCalendar)
	{
		this.fromFinanceCalendar = fromFinanceCalendar;
	}

	public String getExtractId()
	{
		return extractId;
	}

	public void setExtractId(String extractId)
	{
		this.extractId = extractId;
	}
}
