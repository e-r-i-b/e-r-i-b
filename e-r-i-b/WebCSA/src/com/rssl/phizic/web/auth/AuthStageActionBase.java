package com.rssl.phizic.web.auth;

import com.rssl.auth.csa.front.exceptions.InterruptStageException;
import com.rssl.auth.csa.front.exceptions.ResetStageException;
import com.rssl.auth.csa.front.exceptions.ValidateException;
import com.rssl.auth.csa.front.operations.auth.OperationInfo;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.auth.csa.front.operations.Operation;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.utils.StringHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author niculichev
 * @ created 19.09.2012
 * @ $Author$
 * @ $Revision$
 */
public abstract class AuthStageActionBase extends SecurityActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.begin", "begin");
		map.put("button.next", "next");
		return map;
	}

	/**
	 * ����������� ��������� ��������� ��������
	 * @param info �������� ��������
	 * @param valueSource �������� �������� ��� ������������
	 * @throws Exception
	 */
	protected void nextStage(OperationInfo info, FieldValuesSource valueSource) throws Exception
	{
		// ��������������, ���� ���� ���� �����-�� ����������� ��� ������������ ���������
		synchronized (info)
		{
			// �������� ������� ���������
			Stage currentStage = info.getCurrentStage();
			// ���������� � ������������ � ��� ������
			Map<String, Object> formData =
					checkFormData(currentStage.getForm(info), valueSource);
			// ����� ��� ��������
			Operation operation = currentStage.getOperation(info, formData);
			// ���������
			operation.execute();
			// ����������� ���������
			info.nextStage();
		}
    }

	/**
	 * �������� ������ nextStage, ��� ����������� ��������� ������ �� next
	 * @param mapping �������
	 * @param frm �����
	 * @param request ������
	 * @param info �������� ��������
	 * @return ������� � ������ ������������ ������
	 * @throws Exception
	 */
	protected ActionForward doNextStage(ActionMapping mapping,  AuthStageFormBase frm, HttpServletRequest request, OperationInfo info) throws Exception
	{
		nextStage(info, getFieldValuesSource(frm));
		frm.setSuccessNextStage(true);

		return mapping.findForward(info.getCurrentName());
	}

	/**
	 * �������� ������ nextStage, ��� ����������� ��������� ������ �� begin
	 * @param mapping �������
	 * @param frm �����
	 * @param request ������
	 * @param info �������� ��������
	 * @return ������� � ������ ������������ ������
	 * @throws Exception
	 */
	protected ActionForward doBeginStage(ActionMapping mapping,  AuthStageFormBase frm, HttpServletRequest request, OperationInfo info) throws Exception
	{
		nextStage(info, getFieldValuesSource(frm));
		frm.setSuccessNextStage(true);

		return mapping.findForward(info.getCurrentName());
	}

	/**
	 * ��� ����� ��� ��������� ������� �� ������������� �� ���
	 * @param mapping �������
	 * @param form �����
	 * @param request ������
	 * @param response �����
	 * @return �������
	 * @throws Exception
	 */
	public ActionForward begin(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AuthStageFormBase frm = (AuthStageFormBase) form;
		OperationInfo operationInfo = getOperationInfo();

		ActionForward forward = null;

		try
		{
			// ����������� ���������
			forward = doBeginStage(mapping, frm, request,  operationInfo);
		}
		catch (ResetStageException e)
        {
	        // ��������� ��������� ������� ���� ����
	        if(StringHelper.isNotEmpty(e.getMessage()))
	            saveError(request, e.getMessage());
            forward = mapping.findForward(RESET_FORWARD);
        }
		catch(ValidateException e)
		{
			saveErrors(request, e.getMessages());
			forward = mapping.findForward(ERROR_FORWARD);
		}
		catch (FrontLogicException e)
		{
			saveError(request, e.getMessage());
			forward = mapping.findForward(ERROR_FORWARD);
		}

		// ��������� �� �����
		updateForm(operationInfo, frm, true);

		// ���� �� ���� ���������
		if(operationInfo.getCurrentStage() == null)
		{
			clearPayOrderData();
			return createCompleteForward(mapping, request);
		}

		// ��������� �������� �������� � ������ ������ ���� ������������ ������ �������
		if(frm.isSuccessNextStage())
			setOperationInfo(request, operationInfo);

		return forward;
	}

	/**
	 * ��� ����� ��� ��������� ������� �� ������������ ���������
	 * @param mapping �������
	 * @param form �����
	 * @param request ������
	 * @param response �����
	 * @return �������
	 * @throws Exception
	 */
    public ActionForward next(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws java.lang.Exception
    {
	    AuthStageFormBase frm = (AuthStageFormBase) form;
        OperationInfo operationInfo = getOperationInfo(request);

        if(operationInfo == null)
        {
	        saveError(request, DEFAULT_ERROR_MESSAGE);
	        return useFullErrorPage() ? mapping.findForward(ERROR_FULL_PAGE_FORWARD) : mapping.findForward(ERROR_FORWARD);
        }

	    ActionForward forward = null;

        try
        {
	        forward = doNextStage(mapping, frm, request,  operationInfo);
        }
        catch (ResetStageException e)
        {
	        resetOperationInfo(request);
	        // ��������� ��������� ������� ���� ����
	        if(StringHelper.isNotEmpty(e.getMessage()))
	            saveError(request, e.getMessage());

	        forward = mapping.findForward(RESET_FORWARD);
        }
        catch (InterruptStageException e)
        {
	        saveError(request, e.getMessage());
	        resetOperationInfo(request);
	        forward = mapping.findForward(ERROR_FORWARD);
        }
        catch(ValidateException e)
		{
			saveErrors(request, e.getMessages());
			forward = mapping.findForward(operationInfo.getCurrentName());
		}
        catch (FrontLogicException e)
        {
            saveError(request, e.getMessage());
	        forward = mapping.findForward(operationInfo.getCurrentName());
        }

	    // ��������� �����
		updateForm(operationInfo, frm, frm.isSuccessNextStage());

	    if(operationInfo.getCurrentStage() == null)
		{
			resetOperationInfo(request);
			clearPayOrderData();
			return createCompleteForward(mapping, request);
		}

	    // �������������� �������� �������� � ���������������� ����� ���� �� ��� �������
	    if(!isResetOperationInfo(request))
		    setOperationInfo(request, operationInfo);

	    return forward;
    }

	protected ActionForward createCompleteForward(ActionMapping mapping, HttpServletRequest request)
	{
		return mapping.findForward(COMPLETE_FORWARD);
	}

	protected void updateForm(OperationInfo operationInfo, AuthStageFormBase frm, boolean isFirstShowForm)
	{
		frm.setOperationInfo(operationInfo);
	}

	protected List<String> getCaptchaMethodNames()
	{
		return Arrays.asList("begin");
	}

	protected boolean processSpecificCaptha(ActionForm form, HttpServletRequest request, String operationName) throws Exception
	{
		return false;
	}

	public ActionForward specificCaptcha(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AuthStageFormBase frm = (AuthStageFormBase) form;
		OperationInfo operationInfo = getOperationInfo(request);

		// ��������� �������� ���, ������ �������
		if(operationInfo == null)
			return start(mapping, form, request, response);

		updateForm(operationInfo, frm, frm.isSuccessNextStage());
		// �������������� �����
		setOperationInfo(request, operationInfo);

		return mapping.findForward(operationInfo.getCurrentName());
	}

	/**
	 * @return ��������� �������� � ��������� ���������
	 */
	protected abstract OperationInfo getOperationInfo();

	/**
	 * ������� ������ �������, ��� ������ � ������� ������.
	 */
	protected void clearPayOrderData(){/*������ ��� ����� �������*/};
}
