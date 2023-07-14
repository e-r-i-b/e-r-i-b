package com.rssl.phizic.web.persons;

import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.FilterActionForm;

/**
 * Форма просмотра интернет-заказа в корзине
 * @author niculichev
 * @ created 21.08.15
 * @ $Author$
 * @ $Revision$
 */
public class ViewShopOrderForm extends EditFormBase
{
	private String html;
	private Long personId;
	private String orderId;
	private String formName;
	private String providerName;

	/**
	 * @return идентификатор персоны
	 */
	public Long getPersonId()
	{
		return personId;
	}

	/**
	 * Установить идентификатор персоны
	 * @param personId id
	 */
	public void setPersonId(Long personId)
	{
		this.personId = personId;
	}

	/**
	 * @return сгенерированный по заказу html-код
	 */
	public String getHtml()
	{
		return html;
	}

	/**
	 * Установить сгенерированный по заказу html-код
	 * @param html код
	 */
	public void setHtml(String html)
	{
		this.html = html;
	}

	/**
	 * @return внешний идентификатор заказа
	 */
	public String getOrderId()
	{
		return orderId;
	}

	/**
	 * установить внешний идентификатор заказа
	 * @param orderId идентификатор заказа
	 */
	public void setOrderId(String orderId)
	{
		this.orderId = orderId;
	}

	/**
	 * @return название поставщика
	 */
	public String getProviderName()
	{
		return providerName;
	}

	/**
	 * Установить название поставщика
	 * @param providerName название поставщика
	 */
	public void setProviderName(String providerName)
	{
		this.providerName = providerName;
	}

	/**
	 * @return название формы
	 */
	public String getFormName()
	{
		return formName;
	}

	/**
	 * Установить название формы
	 * @param formName имя формы
	 */
	public void setFormName(String formName)
	{
		this.formName = formName;
	}
}
