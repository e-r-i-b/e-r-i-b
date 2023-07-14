package com.rssl.phizic.csaadmin.listeners;

import com.rssl.phizic.csaadmin.business.common.AdminException;
import com.rssl.phizic.csaadmin.business.common.AdminLogicException;
import com.rssl.phizic.csaadmin.business.login.Login;
import com.rssl.phizic.csaadmin.business.session.Session;
import com.rssl.phizic.csaadmin.business.session.SessionService;
import com.rssl.phizic.csaadmin.listeners.access.AccessSchemeServiceImpl;
import com.rssl.phizic.csaadmin.listeners.authentication.AuthenticationServiceImpl;
import com.rssl.phizic.csaadmin.listeners.employee.EmployeeServiceImpl;
import com.rssl.phizic.csaadmin.listeners.generated.*;
import com.rssl.phizic.csaadmin.listeners.mail.MailListService;
import com.rssl.phizic.csaadmin.listeners.mail.MailStatisticsService;
import com.rssl.phizic.csaadmin.log.InitializeContextHelper;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

/**
 * @author akrenev
 * @ created 19.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Веб сервис работы с ЦСА Админ
 */

public class CSAAdminService
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);
	private static final SessionService sessionService = new SessionService();
	private static final EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
	private static final AccessSchemeServiceImpl accessSchemeService = new AccessSchemeServiceImpl();
	private static final AuthenticationServiceImpl authenticationService = new AuthenticationServiceImpl();
	private static final MailListService mailListService = new MailListService();
	private static final MailStatisticsService mailStatisticsService = new MailStatisticsService();
	private static final long OPERATION_NOT_FOUND_STATUS_CODE = 404L;
	private static final String OPERATION_NOT_FOUND_STATUS_DESC = "Операция не найдена!";
	private static final long BAD_OPERATION_STATUS_CODE = 500L;

	private ResponseType createResponse(RequestType requestType)
	{
		ResponseType response = new ResponseType();
		response.setRqUID(requestType.getRqUID());
		response.setRqTm(requestType.getRqTm());
		response.setOperUID(requestType.getOperUID());
		response.setStatus(new StatusType(0, null));
		response.setData(new ResponseData());
		return response;
	}

	/**
	 * обработка запроса
	 * @param request запрос
	 * @return ответ
	 */
	public ResponseType exec(RequestType request)
	{
		RequestData data = request.getData();
		ResponseType response = createResponse(request);
		try
		{
			if(data.getGetAuthenticationParametersRq() != null)
			{
				AuthenticationParametersResultType result = authenticationService.getAuthenticationParameters(data.getGetAuthenticationParametersRq());
				response.getData().setGetAuthenticationParametersRs(result);
				return response;
			}
			else if (data.getGetEmployeeManagerInfoRq() != null)
			{
				response.getData().setGetEmployeeManagerInfoRs(new GetEmployeeManagerInfoResultType(employeeService.getManagerInfo(data.getGetEmployeeManagerInfoRq().getManagerId())));
				return response;
			}

			Session activeSession = sessionService.findActiveById(request.getSessionID());
			if (activeSession == null)
			{
				response.setStatus(new StatusType(BAD_OPERATION_STATUS_CODE, "Нет активной сессии."));
				return response;
			}

			InitializeContextHelper.initializeContext(activeSession);

			Login initiatorLogin = activeSession.getLogin();
			if (data.getGetEmployeeSynchronizationDataRq() != null)
			{
				GetEmployeeSynchronizationDataResultType result = employeeService.getGetEmployeeSynchronizationData(data.getGetEmployeeSynchronizationDataRq().getLoginId());
				response.getData().setGetEmployeeSynchronizationDataRs(result);
			}
			else if (data.getGetEmployeeListRq() != null)
			{
				response.getData().setGetEmployeeListRs(new GetEmployeeListResultType(employeeService.getList(data.getGetEmployeeListRq().getFilter())));
			}
			else if (data.getGetEmployeeMailManagerListRq() != null)
			{
				response.getData().setGetEmployeeMailManagerListRs(new GetEmployeeMailManagerListResultType(employeeService.getMailManagerList(data.getGetEmployeeMailManagerListRq().getFilter())));
			}
			else if (data.getGetEmployeeByIdRq() != null)
			{
				GetEmployeeByIdParametersType getEmployeeByIdRq = data.getGetEmployeeByIdRq();
				response.getData().setGetEmployeeByIdRs(new GetEmployeeByIdResultType(employeeService.getById(getEmployeeByIdRq.getExternalId(), initiatorLogin)));
			}
			else if (data.getGetCurrentEmployeeRq() != null)
			{
				response.getData().setGetCurrentEmployeeRs(new GetCurrentEmployeeResultType(employeeService.getByLogin(initiatorLogin)));
			}
			else if (data.getSaveEmployeeRq() != null)
			{
				response.getData().setSaveEmployeeRs(new SaveEmployeeResultType(employeeService.save(data.getSaveEmployeeRq().getEmployee(), initiatorLogin)));
			}
			else if (data.getAssignAccessSchemeEmployeeRq() != null)
			{
				AssignAccessSchemeEmployeeParametersType assignAccessSchemeEmployeeRq = data.getAssignAccessSchemeEmployeeRq();
				AccessSchemeType accessScheme = employeeService.assignAccessScheme(assignAccessSchemeEmployeeRq.getSchemeId(), assignAccessSchemeEmployeeRq.getEmployee(), initiatorLogin);
				response.getData().setAssignAccessSchemeEmployeeRs(new AssignAccessSchemeEmployeeResultType(accessScheme));
			}
			else if (data.getDeleteEmployeeRq() != null)
			{
				employeeService.delete(data.getDeleteEmployeeRq().getEmployee(), initiatorLogin);
				response.getData().setDeleteEmployeeRs(new DeleteEmployeeResultType());
			}
			else if (data.getLockEmployeeRq() != null)
			{
				employeeService.lock(data.getLockEmployeeRq().getLockLoginParameter(), data.getLockEmployeeRq().getLockBlockParameter(), initiatorLogin);
				response.getData().setLockEmployeeRs(new LockEmployeeResultType());
			}
			else if (data.getUnlockEmployeeRq() != null)
			{
				response.getData().setUnlockEmployeeRs(new UnlockEmployeeResultType(employeeService.unlock(data.getUnlockEmployeeRq().getLogin())));
			}
			else if (data.getChangeEmployeePasswordRq() != null)
			{
				response.getData().setChangeEmployeePasswordRs(new ChangeEmployeePasswordResultType(employeeService.changePassword(data.getChangeEmployeePasswordRq().getLogin())));
			}
			else if (data.getSelfChangePasswordRq() != null)
			{
				employeeService.selfChangePassword(activeSession,data.getSelfChangePasswordRq().getPassword());
				response.getData().setSelfChangePasswordRs(new VoidResultType());
			}
			else if (data.getGetAllowedDepartmentsRq() != null)
			{
				GetAllowedDepartmentsParametersType getAllowedDepartmentsRq = data.getGetAllowedDepartmentsRq();
				response.getData().setGetAllowedDepartmentsRs(new GetAllowedDepartmentsResultType(employeeService.getAllowedDepartments(getAllowedDepartmentsRq.getEmployee(), initiatorLogin)));
			}
			else if (data.getSaveAllowedDepartmentsRq() != null)
			{
				SaveAllowedDepartmentsParametersType saveAllowedDepartmentsRq = data.getSaveAllowedDepartmentsRq();
				employeeService.saveAllowedDepartments(saveAllowedDepartmentsRq.getAddDepartments(), saveAllowedDepartmentsRq.getDeleteDepartments(), saveAllowedDepartmentsRq.getEmployee(), initiatorLogin);
				response.getData().setSaveAllowedDepartmentsRs(new VoidResultType());
			}
			else if (data.getGetAccessSchemeListRq() != null)
			{
				response.getData().setGetAccessSchemeListRs(new GetAccessSchemeListResultType(accessSchemeService.getList(data.getGetAccessSchemeListRq().getFilter(), initiatorLogin)));
			}
			else if (data.getGetAccessSchemeByIdRq() != null)
			{
				response.getData().setGetAccessSchemeByIdRs(new GetAccessSchemeByIdResultType(accessSchemeService.getById(data.getGetAccessSchemeByIdRq().getExternalId())));
			}
			else if (data.getSaveAccessSchemeRq() != null)
			{
				response.getData().setSaveAccessSchemeRs(new SaveAccessSchemeResultType(accessSchemeService.save(data.getSaveAccessSchemeRq().getAccessScheme())));
			}
			else if (data.getDeleteAccessSchemeRq() != null)
			{
				accessSchemeService.delete(data.getDeleteAccessSchemeRq().getAccessScheme());
				response.getData().setDeleteAccessSchemeRs(new DeleteAccessSchemeResultType());
			}
			else if(data.getCloseSessionRq() != null)
			{
				VoidResultType result = authenticationService.closeSession(activeSession);
				response.getData().setCloseSessionRs(result);
			}
			else if(data.getChangeNodeRq() != null)
			{
				ChangeNodeResultType result = authenticationService.changeNode(activeSession, data.getChangeNodeRq());
				response.getData().setChangeNodeRs(result);
			}
			else if(data.getSaveOperationContextRq() != null)
			{
				VoidResultType result = authenticationService.saveOperationContext(activeSession, data.getSaveOperationContextRq());
				response.getData().setSaveOperationContextRs(result);
			}
			else if(data.getGetOperationContextRq() != null)
			{
				GetOperationContextResultType result = authenticationService.getOperationContext(activeSession);
				response.getData().setGetOperationContextRs(result);
			}
			else if (data.getGetCurrentEmployeeContextRq() != null)
			{
				response.getData().setGetCurrentEmployeeContextRs(authenticationService.getContextByLogin(initiatorLogin));
			}
			else if (data.getGetIncomeMailListRq() != null)
			{
				response.getData().setGetIncomeMailListRs(mailListService.getIncomeMailList(data.getGetIncomeMailListRq()));
			}
			else if (data.getGetOutcomeMailListRq() != null)
			{
				response.getData().setGetOutcomeMailListRs(mailListService.getOutcomeMailList(data.getGetOutcomeMailListRq()));
			}
			else if (data.getGetRemovedMailListRq() != null)
			{
				response.getData().setGetRemovedMailListRs(mailListService.getRemovedMailList(data.getGetRemovedMailListRq()));
			}
			else if (data.getGetMailStatisticsRq() != null)
			{
				response.getData().setGetMailStatisticsRs(mailStatisticsService.getMailStatistics(data.getGetMailStatisticsRq()));
			}
			else if (data.getGetMailEmployeeStatisticsRq() != null)
			{
				response.getData().setGetMailEmployeeStatisticsRs(mailStatisticsService.getEmployeeStatistics(data.getGetMailEmployeeStatisticsRq()));
			}
			else if (data.getGetMailAverageTimeRq() != null)
			{
				response.getData().setGetMailAverageTimeRs(mailStatisticsService.getMailAverageTime(data.getGetMailAverageTimeRq()));
			}
			else if (data.getGetFirstMailDateRq() != null)
			{
				response.getData().setGetFirstMailDateRs(mailStatisticsService.getFirstMailDate(data.getGetFirstMailDateRq()));
			}
			else
			{
				response.setStatus(new StatusType(OPERATION_NOT_FOUND_STATUS_CODE, OPERATION_NOT_FOUND_STATUS_DESC));
			}
		}
		catch (AdminLogicException e)
		{
			response.setStatus(new StatusType(e.getErrorCode(), e.getMessage()));
		}
		catch (AdminException e)
		{
			response.setStatus(new StatusType(BAD_OPERATION_STATUS_CODE, e.getMessage()));
			log.error("Ошибка обработки запроса в ЦСА Админ.", e);
		}
		catch (Exception e)
		{
			response.setStatus(new StatusType(BAD_OPERATION_STATUS_CODE, e.getMessage()));
			log.error("Ошибка обработки запроса в ЦСА Админ.", e);
		}
		return response;
	}
}
