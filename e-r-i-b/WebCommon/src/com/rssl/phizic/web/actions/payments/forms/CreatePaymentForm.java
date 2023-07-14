package com.rssl.phizic.web.actions.payments.forms;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 08.11.2005
 * Time: 14:01:59
 */
public class CreatePaymentForm extends EditDocumentForm
{
	private String       category;
    private String       html;
    private Long         template;
	private boolean      markReminder;
	private String       guid;
    private Boolean      copying =false;
	private String       sessionData;
	private String       type;
	private String       orderId;   // uuid заказа из внешеней системы
	private String       operationUID; // uid операции
	private int          documentStatus;

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public String getHtml()
    {
        return html;
    }

    public void setHtml(String html)
    {
        this.html = html;
    }

    /**
     * ID шаблона
     */
    public Long getTemplate()
    {
        return template;
    }

    /**
     * ID шаблона
     */
    public void setTemplate(Long template)
    {
        this.template = template;
    }

    public Boolean getCopying() {
        return copying;
    }

    public void setCopying(Boolean copying) {
        this.copying = copying;
    }

	public String getGuid()
	{
		return guid;
	}

	public void setGuid(String guid)
	{
		this.guid = guid;
	}

	/**
	 * @return id записи с данными протухшей сессии
	 */
	public String getSessionData()
	{
		return sessionData;
	}

	public void setSessionData(String sessionData)
	{
		this.sessionData = sessionData;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}


	public String getOrderId()
	{
		return orderId;
	}

	public void setOrderId(String orderId)
	{
		this.orderId = orderId;
	}

	public String getOperationUID()
	{
		return operationUID;
	}

	public void setOperationUID(String operationUID)
	{
		this.operationUID = operationUID;
	}

	public boolean isMarkReminder()
	{
		return markReminder;
	}

	public void setMarkReminder(boolean markReminder)
	{
		this.markReminder = markReminder;
	}

	/**
	 * —татус документа при ошибке.  (дл€ мјпи)
	 *
	 * @return статус.
	 */
	public int getDocumentStatus()
	{
		return documentStatus;
	}

	/**
	 * @param documentStatus статус документа при ошибке дл€ мјпи.
	 */
	public void setDocumentStatus(int documentStatus)
	{
		this.documentStatus = documentStatus;
	}
}
