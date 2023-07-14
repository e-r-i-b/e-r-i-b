package com.rssl.phizic.operations.payment.internetShops;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.shop.ShopHelper;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.context.ClientInvoiceCounterHelper;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.einvoicing.OrderState;
import com.rssl.phizic.gate.einvoicing.ShopOrder;
import com.rssl.phizic.gate.einvoicing.ShopOrderService;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.OperationBase;
import org.hibernate.Session;

import java.util.Arrays;
import java.util.List;

/**
 * Операция отмены платежа.
 *
 * @author bogdanov
 * @ created 31.05.2013
 * @ $Author$
 * @ $Revision$
 */

public class CancelOrderOperation extends OperationBase
{
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private static final List<OrderState> permitRecallStates = Arrays.asList(OrderState.CREATED, OrderState.DELAYED, OrderState.RELATED, OrderState.PAYMENT);

	private final PaymentStateMachineService stateMachineService = new PaymentStateMachineService();
	protected static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	/**
	 * заказ.
	 */
	private ShopOrder order;
	/**
	 * необходимость перевести заказ в статус ОТКАЗАН.
	 */
	private boolean refuseBusinessDocument;

	public void initialize(String orderUuid, boolean refuseBusinessDocument) throws BusinessException, BusinessLogicException
	{
		this.refuseBusinessDocument = refuseBusinessDocument;
		order = ShopHelper.get().getShopOrder(orderUuid);

		if (!(ShopHelper.get().isSameClient(order.getProfile(), AuthenticationContext.getContext())))
			throw new BusinessException("Попытка отменить чужой документ");

		if(!permitRecallStates.contains(order.getState()))
			throw new BusinessException("Отмена заказа невозможна для статуса " + order.getState());
	}

	public void initialize(Long id, boolean refuseBusinessDocument) throws BusinessException, BusinessLogicException
	{
		this.refuseBusinessDocument = refuseBusinessDocument;
		initialize(ShopHelper.get().getOrderUuidByPayment(id), refuseBusinessDocument);
	}

	public void doCancel() throws BusinessException, BusinessLogicException
	{
        BusinessDocument businessDocument = DocumentHelper.getPaymentByOrder(order.getUuid());
		if (refuseBusinessDocument && businessDocument != null)
		{
			StateMachineExecutor executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(businessDocument.getFormName()));
			executor.setStateMachineEvent(getStateMachineEvent());
			executor.initialize(businessDocument);
			executor.fireEvent(new ObjectEvent(DocumentEvent.REFUSE, ObjectEvent.SYSTEM_EVENT_TYPE, "Данный заказ был отменен или изменен в системе поставщика."));
			businessDocumentService.addOrUpdate(businessDocument);
		}
		else
		{
			if (businessDocument != null)
				removeDocument(businessDocument);

			ShopHelper.get().changeOrderStatus(order.getUuid(), OrderState.CANCELED, order.getUtrrno(), null);
			try
			{
				GateSingleton.getFactory().service(ShopOrderService.class).markViewed(order.getUuid());
				ClientInvoiceCounterHelper.resetCounterValue();
			}
			catch (Exception e)
			{
				log.error(e.getMessage(), e);
			}
		}
	}

	public ShopOrder getOrder()
	{
		return order;
	}

	private void removeDocument(final BusinessDocument businessDocument) throws BusinessException, BusinessLogicException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Object>()
			{
				public Void run(Session session) throws Exception
				{
					StateMachineExecutor executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(businessDocument.getFormName()));
					executor.initialize(businessDocument);
					executor.fireEvent(new ObjectEvent(DocumentEvent.DELETE, ObjectEvent.CLIENT_EVENT_TYPE));

					businessDocumentService.addOrUpdate(businessDocument);
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
