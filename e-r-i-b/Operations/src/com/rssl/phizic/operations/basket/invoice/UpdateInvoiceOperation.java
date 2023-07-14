 package com.rssl.phizic.operations.basket.invoice;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.basket.invoice.FakeInvoice;
import com.rssl.phizic.business.basket.invoice.Invoice;
import com.rssl.phizic.business.basket.invoice.InvoiceService;
import com.rssl.phizic.business.basket.invoice.InvoiceUpdateInfo;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscriptionService;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.basket.InvoiceStatus;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.operations.OperationBase;
import org.hibernate.Session;

import java.util.Calendar;

/**
 * @author vagin
 * @ created 10.06.14
 * @ $Author$
 * @ $Revision$
 * Операция изменения статуса инвойса.
 */
public class UpdateInvoiceOperation extends OperationBase
{
	private static final InvoiceService invoiceService = new InvoiceService();
	private static final InvoiceSubscriptionService invoiceSubscriptionService = new InvoiceSubscriptionService();
	private InvoiceUpdateInfo invoiceUpdateInfo;

	/**
	 * Инциализация операции
	 * @param id - идентификатор инрвойса.
	 * @param loginId - идентификатор логина клиента
	 * @throws BusinessException
	 */
	public void initialize(final Long id, Long loginId) throws BusinessException, BusinessLogicException
	{
		InvoiceUpdateInfo tempInfo = invoiceService.findInvoiceUpdateInfo(id);
		if(tempInfo == null)
			throw new BusinessException("Инвойса с id = " + id + " не существует");

		//проверка на принадлежность инвойса клиенту.
		if (!tempInfo.getLoginId().equals(loginId))
			throw new BusinessException("Счет с id=" + id + "не принадлежит клиенту с loginId=" + loginId);
		//проверка на допустимость изменения статуса.
		if (tempInfo.getStatus() == InvoiceStatus.PAID || tempInfo.getStatus() == InvoiceStatus.ERROR)
			throw new BusinessLogicException("Указанный вами счет находится в данный момент на обработке. Изменение статуса недопустимо.");

		this.invoiceUpdateInfo = tempInfo;
	}

	/**
	 * Отложить инвойс.
	 * @param delayDate - дата, на которую откладывается инвойс.
	 * @throws BusinessException
	 */
	public void delayInvoice(final Calendar delayDate) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					org.hibernate.Query query = session.getNamedQuery("com.rssl.phizic.business.basket.invoice.Invoice.setStateAndDelayDateById");
					query.setLong("id", invoiceUpdateInfo.getId());
					query.setCalendar("delayedPayDate", delayDate);
					query.setParameter("state", InvoiceStatus.DELAYED.name());
					query.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Удалить инвойс
	 * @throws BusinessException
	 */
	public void deleteInvoice() throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					org.hibernate.Query query = session.getNamedQuery("com.rssl.phizic.business.basket.invoice.Invoice.setStateAndDelayDateById");
					query.setLong("id", invoiceUpdateInfo.getId());
					query.setParameter("state", InvoiceStatus.INACTIVE.name());
					query.setCalendar("delayedPayDate", null);
					query.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public InvoiceUpdateInfo getInvoiceUpdateInfo()
	{
		return invoiceUpdateInfo;
	}
}
