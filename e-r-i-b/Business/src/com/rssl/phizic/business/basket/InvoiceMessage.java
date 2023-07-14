package com.rssl.phizic.business.basket;

import com.rssl.phizgate.common.basket.RequisitesHelper;
import com.rssl.phizic.business.basket.invoice.Invoice;
import com.rssl.phizic.business.basket.invoice.InvoiceUpdateInfo;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.einvoicing.ShopOrder;
import com.rssl.phizic.gate.payments.template.TemplateInfo;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Сообщение о действиях над инвойсом
 * @author niculichev
 * @ created 25.11.14
 * @ $Author$
 * @ $Revision$
 */
public class InvoiceMessage
{
	public static final String INVOICE_PROCESS_MESSAGE_KEY = InvoiceMessage.class.getName();

	private String name;
	private BigDecimal amount;
	private Type type;
	private Long subscriptionId;
	private boolean additionMessage;

	public InvoiceMessage(String name, BigDecimal amount, Type type, boolean additionMessage, Long subscriptionId)
	{
		this.name = name;
		this.amount = amount;
		this.type = type;
		this.subscriptionId = subscriptionId;
		this.additionMessage = additionMessage;
	}

	public InvoiceMessage(String name, BigDecimal amount, Type type)
	{
		this(name, amount, type, false, null);
	}

	/**
	 * @return имя инвойса
	 */
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return сумма инвойса
	 */
	public BigDecimal getAmount()
	{
		return amount;
	}

	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	/**
	 * @return признак показа доп. сообщения
	 */
	public boolean isAdditionMessage()
	{
		return additionMessage;
	}

	public void setAdditionMessage(boolean additionMessage)
	{
		this.additionMessage = additionMessage;
	}

	/**
	 * @return тип действия над инвойсом
	 */
	public Type getType()
	{
		return type;
	}

	public void setType(Type type)
	{
		this.type = type;
	}

	/**
	 * @return идентификатор подписки на инвойсы
	 */
	public Long getSubscriptionId()
	{
		return subscriptionId;
	}

	/**
	 * сохранить сообщение об обработке инвойса по заказу
	 * @param order заказ
	 * @param type тип обработки
	 */
	public static void saveMessage(ShopOrder order, Type type)
	{
		saveMessage(new InvoiceMessage(
				"Оплата заказа " + order.getReceiverName(), order.getAmount().getDecimal(), type));
	}

	/**
	 * сохранить сообщение об обработке инвойса по напоминанию
	 * @param template напоминание
	 * @param type тип обработки
	 */
	public static void saveMessage(TemplateDocument template, Type type)
	{
		if(template == null)
			return;

		TemplateInfo templateInfo = template.getTemplateInfo();
		Money money = template.getExactAmount();

		saveMessage(new InvoiceMessage(
				templateInfo.getName(), money == null ? null : money.getDecimal(), type));
	}

	/**
	 * сохранить сообщение об обработке инвойса по подписке
	 * @param invoice инвойс
	 * @param type тип обработки
	 * @param additionMessage признак показа доп. сообщения
	 */
	public static void saveMessage(Invoice invoice, Type type, boolean additionMessage) throws Exception
	{
		saveMessage(new InvoiceMessage(
				invoice.getNameService(), RequisitesHelper.getAmount(invoice.getRequisites()), type, additionMessage, invoice.getInvoiceSubscriptionId()));
	}

	/**
	 * сохранить сообщение об обработке инвойса по подписке
	 * @param invoiceUpdateInfo информация по инвойсу
	 * @param type тип обработки
	 * @param additionMessage признак показа доп. сообщения
	 */
	public static void saveMessage(InvoiceUpdateInfo invoiceUpdateInfo, Type type, boolean additionMessage) throws Exception
	{
		saveMessage(new InvoiceMessage(
				invoiceUpdateInfo.getName(), invoiceUpdateInfo.getAmount(), type, additionMessage, invoiceUpdateInfo.getSubscriptionId()));
	}

	/**
	 * Сохранить сообщение об обработке инвойса
	 * @param invoiceMessage - информация об инвойсе
	 */
	private static void saveMessage(InvoiceMessage invoiceMessage)
	{
		Store store = StoreManager.getCurrentStore();
		//noinspection unchecked
		List<InvoiceMessage> messages = (List<InvoiceMessage>) store.restore(INVOICE_PROCESS_MESSAGE_KEY);
		if(messages == null)
		{
			messages = new ArrayList<InvoiceMessage>();
			store.save(INVOICE_PROCESS_MESSAGE_KEY, messages);
		}

		messages.add(invoiceMessage);
	}

	/**
	 * Удалить сообщения по инвойсам
	 * @return удаляемые сообщения
	 */
	public static List<InvoiceMessage> removeMessages()
	{
		Store store = StoreManager.getCurrentStore();
		//noinspection unchecked
		List<InvoiceMessage> messages = (List<InvoiceMessage>) store.restore(INVOICE_PROCESS_MESSAGE_KEY);
		// обрабатываем один раз
		store.remove(INVOICE_PROCESS_MESSAGE_KEY);

		return messages;
	}

	public static enum Type
	{
		/**
		 * удаление
		 */
		delete,

		/**
		 * оплата
		 */
		payment,

		/**
		 * откладывание
		 */
		delay
	}
}
