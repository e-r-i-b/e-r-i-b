package com.rssl.phizic.web.client.payments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.payment.GetPaymentStateOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Ajax-action �������� ������� ������� �������
 * @author Dorzhinov
 * @ created 28.05.2012
 * @ $Author$
 * @ $Revision$
 */
public class AsyncGetTicketsStateAction extends OperationalActionBase
{
    private static final Log LOG = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
    private static final String NOT_ASSIGNED_KEY = "ASYNC_GET_TICKETS_STATE_NOT_ASSIGNED";
    private static final String ASSIGNED_KEY = "ASYNC_GET_TICKETS_STATE_ASSIGNED";
    private static final String ERROR_KEY = "ASYNC_GET_TICKETS_STATE_ACTION_ERROR";

	protected boolean isAjax()
	{
		return true;
	}

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            GetPaymentStateOperation operation = createOperation(GetPaymentStateOperation.class, "AirlineReservationPayment");
            String id = request.getParameter("id");
	        operation.initialize(id);
            response.getWriter().write("EXECUTED".equals(operation.getPaymentState()) ? ASSIGNED_KEY : NOT_ASSIGNED_KEY);
        }
        catch (BusinessException e)
        {
            LOG.error("������ ��� ��������� ������� ������� �������.", e);
            try
            {
                response.getWriter().write(ERROR_KEY);
            }
            catch (IOException ignore) {}
        }
        catch (BusinessLogicException e)
        {
			LOG.error("������ ��� ��������� ������� ������� �������.", e);
			try
			{
				response.getWriter().write(ERROR_KEY);
			}
			catch (IOException ignore) {}
        }
        catch (IOException ignore) {}

        return null;
    }
}