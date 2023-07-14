package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.business.fraudMonitoring.FraudMonitoringSendersFactory;
import com.rssl.phizic.business.fraudMonitoring.exceptions.ProhibitionOperationFraudException;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.statemachine.MachineState;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachine;
import com.rssl.phizic.business.statemachine.forms.Form;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.rsa.exceptions.BlockClientOperationFraudException;
import com.rssl.phizic.rsa.exceptions.ProhibitionOperationFraudGateException;
import com.rssl.phizic.rsa.senders.types.EventsType;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.http.UrlBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Evgrafov
 * @ created 05.12.2006
 * @ $Author: khudyakov $
 * @ $Revision: 85552 $
 */
public class DefaultDocumentAction extends Action
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final String GUEST_LINK    = "/guest";
	private static final String PRIVATE_LINK  = "/private";
	private static final String EXTERNAL_LINK = "/external";

	private final PaymentStateMachineService stateMachineService = new PaymentStateMachineService();


	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String objectFormName = request.getParameter("objectFormName");
		String stateCode = request.getParameter("stateCode");

		MachineState machineState = getStateObjectMachine(objectFormName).getMachineStateByObjectStateCode(stateCode);
		if (machineState == null)
		{
			throw new IllegalArgumentException("Недопустимый статус для объекта с идентификатором " + request.getParameter("id"));
		}

		if (ApplicationConfig.getIt().getApplicationInfo().isPhizIC() && machineState.isViewState())
		{
			doFraudControl();
		}

		UrlBuilder urlBuilder = new UrlBuilder(createStateUrl(machineState));
		additionalParameters(request, urlBuilder);

		return new ActionForward(urlBuilder.getUrl(), true);
	}

	private void doFraudControl() throws ProhibitionOperationFraudException
	{
		try
		{
			FraudMonitoringSendersFactory.getInstance().getSender(EventsType.VIEW_STATEMENT).send();
		}
		catch (ProhibitionOperationFraudGateException e)
		{
			throw new ProhibitionOperationFraudException(e.getMessage(), e);
		}
		catch (BlockClientOperationFraudException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			log.error(e);
		}
	}

	protected void additionalParameters(HttpServletRequest request, UrlBuilder urlBuilder)
	{
		String id = request.getParameter("id");
		String formName = request.getParameter("form");
		String history = request.getParameter("history");
		String internetOrder = request.getParameter("internetOrder");

		if (StringHelper.isNotEmpty(formName))
		{
			urlBuilder.addParameter("form", formName);
			urlBuilder.addParameter("parentId", id);
		}
		else
		{
			urlBuilder.addParameter("id", id);
		}

		if (!StringHelper.isEmpty(history))
		{
			urlBuilder.addParameter("history", history);
		}

		if (StringHelper.isNotEmpty(internetOrder))
		{
			urlBuilder.addParameter("internetOrder", internetOrder);
		}
	}

	protected StateMachine getStateObjectMachine(String formName)
	{
		return stateMachineService.getStateMachineByFormName(formName);
	}

	/**
	 * Создать переход документа (шаблона) на редактирование
	 * @param id идентификатор сущности
	 * @param machineState текущее состояние МС
	 * @return переход
	 */
	public static ActionForward createDefaultEditForward(Long id, MachineState machineState)
	{
		UrlBuilder urlBuilder = new UrlBuilder(createStateUrl(machineState));
		urlBuilder.addParameter("id", StringHelper.getEmptyIfNull(id));
		return new ActionForward(urlBuilder.toString(), true);
	}

	/**
	 * Создать переход документа (шаблона) на редактирование
	 * @param stateObject идентификатор сущности
	 * @param machineState текущее состояние МС
	 * @return переход
	 */
	public static ActionForward createDefaultEditForward(StateObject stateObject, MachineState machineState)
	{
		UrlBuilder urlBuilder = new UrlBuilder(createStateUrl(stateObject, machineState));
		urlBuilder.addParameter("id", StringHelper.getEmptyIfNull(stateObject.getId()));
		return new ActionForward(urlBuilder.toString(), true);
	}

	/**
	 * Сформировать url перехода
	 *
	 * @param machineState состояние машины состояний
	 * @return переход
	 */
	public static String createStateUrl(MachineState machineState)
	{
		return createStateUrl(null, machineState);
	}

	/**
	 * Сформировать url перехода
	 *
	 * @param stateObject объект
	 * @param machineState состояние машины состояний
	 * @return переход
	 */
	public static String createStateUrl(StateObject stateObject, MachineState machineState)
	{
		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
		Application application = applicationInfo.getApplication();

		if (Application.PhizIC == application || applicationInfo.isMobileApi() || applicationInfo.isATM() || applicationInfo.isSocialApi())
		{
			//арм клиента, mAPI, atmAPI
			return createClientApplicationUrl(stateObject, machineState, application);
		}

		if (Application.PhizIA == application)
		{
			//арм сотрудника
			return createEmployeeApplicationUrl(stateObject, machineState, application);
		}

		throw new IllegalStateException("Некорректный тип приложения, переход платежа невозможен.");
	}

	private static String createClientApplicationUrl(StateObject stateObject, MachineState machineState, Application application)
	{
		String url = machineState.getClientForm();
		if (stateObject != null)
		{
			String formUrl = getStateFormUrl(stateObject, machineState, application);
			if (StringHelper.isNotEmpty(formUrl))
			{
				url = formUrl;
			}
		}

		if (UserVisitingMode.BASIC == ConfirmationManager.getUserVisitingMode())
		{
			return url;
		}

		if (PersonHelper.isGuest())
		{
			return url.replaceFirst(PRIVATE_LINK, GUEST_LINK);
		}
		/*
			Если мы находимся в режиме оплаты платёжных поручений (ФНС/OZON),
			то нам нужно подменить ссылку дальнейшего редиректа из state-machine.xml
			на ссылку для опалаты с внешних источников
		*/
		return url.replaceFirst(PRIVATE_LINK, EXTERNAL_LINK);
	}

	private static String createEmployeeApplicationUrl(StateObject stateObject, MachineState machineState, Application application)
	{
		String url = machineState.getEmployeeForm();
		if (stateObject != null)
		{
			String formUrl = getStateFormUrl(stateObject, machineState, application);
			if (StringHelper.isNotEmpty(formUrl))
			{
				return formUrl;
			}
		}
		return url;
	}

	private static String getStateFormUrl(StateObject stateObject, MachineState machineState, Application application)
	{
		List<Form> forms = machineState.getForms();
		if (CollectionUtils.isEmpty(forms) || application == null)
		{
			return null;
		}

		for (Form form: forms)
		{
			if (form.isActive(stateObject, application))
			{
				return form.getUrl();
			}
		}
		return null;
	}
}