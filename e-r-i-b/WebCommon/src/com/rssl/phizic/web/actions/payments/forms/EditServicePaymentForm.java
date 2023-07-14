package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.phizic.business.shop.Order;

import static com.rssl.phizic.business.ext.sbrf.payments.PaymentsSystemNameConstants.SYSTEM_NAME_UEC;

/**
 * @author krenev
 * @ created 14.01.2011
 * @ $Author$
 * @ $Revision$
 */
public class EditServicePaymentForm extends EditPaymentForm
{
	private Long originalPaymentId;
	private String operationUID;
	private Order order;
	private String barCode;
	private Boolean needSaveMessage = Boolean.TRUE;
	private boolean needSelectProvider = false;
	private Long documentNumber;

	/**
	 * @return идентификатор оригинального документа
	 */
	public Long getOriginalPaymentId()
	{
		return originalPaymentId;
	}

	/**
	 * задать идентификатор оригинального документа
	 * @param originalPaymentId идентификатор
	 */
	@SuppressWarnings("UnusedDeclaration") // requestProcessor
	public void setOriginalPaymentId(Long originalPaymentId)
	{
		this.originalPaymentId = originalPaymentId;
	}

	public String getOperationUID()
	{
		return operationUID;
	}

	public void setOperationUID(String operationUID)
	{
		this.operationUID = operationUID;
	}

	public Order getOrder()
	{
		return order;
	}

	public void setOrder(Order order)
	{
		this.order = order;
	}

	/**
	 * @return true, если оплата платёжного поручения УЭК (оплата с внешней ссылки)
	 */
	public boolean isUECPayOrder()
	{
		return order != null && order.getSystemName().equals(SYSTEM_NAME_UEC);
	}

	public String getBarCode()
	{
		return barCode;
	}

	public void setBarCode(String barCode)
	{
		this.barCode = barCode;
	}

	/**
	 * Проверка, нужно ли сохранять сообщение.
	 * @return Да, если нужно, нет в противном случае.
	 */
	public Boolean isNeedSaveMessage()
	{
		return needSaveMessage;
	}

	/**
	 * Установить флаг "нужно ли сохранять сообщение".
	 * @param needSaveMessage Флаг, сигнализирующий, нужно ли сохранять сообщение.
	 */
	public void setNeedSaveMessage(Boolean needSaveMessage)
	{
		this.needSaveMessage = needSaveMessage;
	}

	/**
	 * @return  нужно ли выбирать поставщика на первом шаге оплаты
	 */
	public boolean isNeedSelectProvider()
	{
		return needSelectProvider;
	}

	public void setNeedSelectProvider(boolean needSelectProvider)
	{
		this.needSelectProvider = needSelectProvider;
	}

	/**
	 * @return номер документа
	 */
	public Long getDocumentNumber()
	{
		return documentNumber;
	}

	/**
	 * @param documentNumber номер документа
	 */
	public void setDocumentNumber(Long documentNumber)
	{
		this.documentNumber = documentNumber;
	}
}
