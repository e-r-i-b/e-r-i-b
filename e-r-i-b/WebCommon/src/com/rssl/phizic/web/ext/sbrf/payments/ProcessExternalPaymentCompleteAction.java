package com.rssl.phizic.web.ext.sbrf.payments;

import com.rssl.phizic.auth.modes.AthenticationCompleteAction;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.messages.MessageConfig;
import com.rssl.phizic.business.operations.OperationFactory;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.externalPayment.ProcessExternalPaymentOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.StrutsOperationFactory;
import com.rssl.phizic.web.common.messages.StrutsMessageConfig;
import static com.rssl.phizic.einvoicing.EInvoicingConstants.WEBSHOP_REQ_ID;
import static com.rssl.phizic.einvoicing.EInvoicingConstants.FNS_PAY_INFO;
import static com.rssl.phizic.einvoicing.EInvoicingConstants.UEC_PAY_INFO;

import java.security.AccessControlException;

/**
 * @author Erkin
 * @ created 18.01.2011
 * @ $Author$
 * @ $Revision$
 * Экшен обработки оплаты внешних заказов и услуг (ФНС/Магазины)
 */

public class ProcessExternalPaymentCompleteAction implements AthenticationCompleteAction
{
	private static final String RESOURCE_BUNDLE = "commonBundle";

	/**
	 * Путь к странице с сообщением об ошибке в данных платежа
	 */
	private static final String INVALID_PAYMENT_PARAMS_PAGE_PATH = "/external/payments/system/end.do";

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final MessageConfig messageConfig = new StrutsMessageConfig();
	private static final OperationFactory operationFactory = new StrutsOperationFactory(messageConfig);

	public void execute(AuthenticationContext context) throws SecurityException
	{
		try
		{
            ProcessExternalPaymentOperation operation;
            //т.к. операция не spread, и может быть в любой услуге, то нужно проверить все возможные услуги
            if (operationFactory.checkAccess(ProcessExternalPaymentOperation.class, "ExternalProviderPayment"))
                operation = operationFactory.create(ProcessExternalPaymentOperation.class, "ExternalProviderPayment");
            else if (operationFactory.checkAccess(ProcessExternalPaymentOperation.class, "FNSPayment"))
                operation = operationFactory.create(ProcessExternalPaymentOperation.class, "FNSPayment");
            else if (operationFactory.checkAccess(ProcessExternalPaymentOperation.class, "AirlineReservationPayment"))
                operation = operationFactory.create(ProcessExternalPaymentOperation.class, "AirlineReservationPayment");
            else
                throw new AccessControlException("access denied " + ProcessExternalPaymentOperation.class.getSimpleName());

			String payInfo = context.getAuthenticationParameter(FNS_PAY_INFO);
			if (!StringHelper.isEmpty(payInfo))
			{
				operation.processFNSPayOrders(context);
				return;
			}

			String reqId = context.getAuthenticationParameter(WEBSHOP_REQ_ID);
			if (!StringHelper.isEmpty(reqId))
			{
				operation.processWebShopOrders(context);
				return;
			}

			String uecPayInfo = context.getAuthenticationParameter(UEC_PAY_INFO);
			if (!StringHelper.isEmpty(uecPayInfo))
			{
				operation.processUECPayOrders(context);
				return;
			}

			String returnTo = context.getAuthenticationParameter("returnTo");
			if (!StringHelper.isEmpty(returnTo))
				return;

			log.error("Ошибка в параметрах внешнего платежа: не указан ни PayInfo, ни ReqId, ни UECPayInfo");
			context.putMessage(getResourceMessage("message.invalid.payment.login.params"));
			context.setStartJobPagePath(INVALID_PAYMENT_PARAMS_PAGE_PATH);
		}
		catch (BusinessException ex)
		{
			throw new SecurityException(ex);
		}
		catch (BusinessLogicException ex)
		{
			context.putMessage(ex.getMessage());
			context.setStartJobPagePath(INVALID_PAYMENT_PARAMS_PAGE_PATH);
		}
	}

	private String getResourceMessage(String messageKey)
	{
		try
		{
			return messageConfig.message(RESOURCE_BUNDLE, messageKey);
		}
		catch (ConfigurationException ex)
		{
			log.warn(ex);
			return messageKey;
		}
	}
}
