package com.rssl.phizic.web.common.mobile.fund;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.fund.request.CloseFundRequestOperation;
import com.rssl.phizic.operations.fund.request.CreateFundRequestOperation;
import com.rssl.phizic.operations.fund.request.ViewFundRequestOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author usachev
 * @ created 09.12.14
 * @ $Author$
 * @ $Revision$
 *
 * Action для работы с исходящими запросами на сбор средств
 */
public class FundRequestAction extends OperationalActionBase
{
	private static final String START = "Start";
	private static final String SHOW_STATUS = "ShowStatus";
	private static final String CREATE_FAIL_FORWARD= "CreateFail";
	private static final String CREATE_SUCCESS_FORWARD= "CreateSuccess";


	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("close", "close");
		map.put("create", "create");
		return map;
	}

	/**
	 * Получение детальной информации по исходящему запросу
	 */
	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		FundRequestForm frm = (FundRequestForm) form;
		ViewFundRequestOperation operation = createOperation(ViewFundRequestOperation.class);
		operation.init(frm.getId());

		frm.setFundRequest(operation.getFundRequest());
		frm.setListFundInitiatorResponse(operation.getListResponse());
		frm.setContactsMap(operation.getContactsMap());
		frm.setProfileAvatarMap(operation.getProfileAvatarMap());
		frm.setAccumulatedSum(operation.getAccumulatedSum());

		return mapping.findForward(START);
	}

	/**
	 * Закрытие запроса на сбор средств по инициативе инициатора запроса
	 */
	public ActionForward close(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		FundRequestForm frm = (FundRequestForm)form;
		CloseFundRequestOperation operation = createOperation(CloseFundRequestOperation.class, "FundRequestManagment");
		operation.close(frm.getId());
		return mapping.findForward(SHOW_STATUS);
	}

	/**
	 * Создания запроса на сбор средств
	 */
	public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CreateFundRequestOperation operation = createOperation(CreateFundRequestOperation.class, "FundRequestManagment");
		try
		{
			FundRequestForm frm = (FundRequestForm) form;
			FormProcessor<ActionMessages, ?> processor = createFormProcessor(new RequestValuesSource(request), FundRequestForm.CREATE_FORM);
			if (!processor.process())
			{
				saveErrors(request, processor.getErrors());
				return mapping.findForward(CREATE_FAIL_FORWARD);
			}
			operation.fillOperationByFormData(processor.getResult());
			Integer availableLimit = operation.initLimitData();
			if (availableLimit < operation.getRequest().getSendersCount())
			{
				if (StringHelper.isEmpty(operation.getDate()))
				{
					saveError(request, "Вы можете включить в рассылку не больше, чем " + availableLimit + " отправителей.");
				}
				else
				{
					saveError(request, "Вы можете включить в рассылку не больше, чем " + availableLimit + " отправителей.  Для создания запроса с большим количеством отправителей повторите операцию " + operation.getDate());
				}
				frm.setAvailableLimit(availableLimit);
				return mapping.findForward(CREATE_FAIL_FORWARD);
			}
			operation.save();
		}

		catch (BusinessLogicException e)
		{
			String errorFieldKey = operation.getErrorFieldKey();
			if (errorFieldKey != null)
			{
				ActionMessage message = new ActionMessage(errorFieldKey, new ActionMessage(e.getMessage(), false));
				saveError(request, message);
			}
			else
			{
				saveError(request, e);
			}
			return mapping.findForward(CREATE_FAIL_FORWARD);
		}
		return mapping.findForward(CREATE_SUCCESS_FORWARD);
	}
}
