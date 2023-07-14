package com.rssl.phizic.web.ext.sbrf.persons;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.migration.MigrationConfig;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.access.PersonLoginSource;
import com.rssl.phizic.operations.erkc.ERKCEmployeeOperation;
import com.rssl.phizic.operations.erkc.context.Functional;
import com.rssl.phizic.operations.person.ChangeClientExtendedLoggingOperation;
import com.rssl.phizic.operations.person.EditPersonOperation;
import com.rssl.phizic.operations.person.PersonExtendedInfoOperationBase;
import com.rssl.phizic.operations.person.mdm.SearchMDMInformationOperation;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.persons.EditPersonAction;
import com.rssl.phizic.web.persons.EditPersonForm;
import com.rssl.phizic.web.persons.PersonFormBuilder;
import com.rssl.phizic.web.util.ERKCEmployeeUtil;
import org.apache.struts.action.*;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Egorova
 * @ created 30.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class SBRFEditPersonAction extends EditPersonAction
{
	private static final PersonFormBuilder sbrfPersonFormBuilder = new SBRFPersonFormBuilder();

	private static final String TO_ERKC    = "ToERKC";   //переход к функционалу сотрудников ЕРКЦ

	protected Map<String,String> getAdditionalKeyMethodMap()
	{
	    Map<String,String> map = super.getAdditionalKeyMethodMap();
	    map.put("button.show.common-log",     "toCommonLog");
		map.put("button.show.operations-log", "toOperationsLog");
		map.put("button.show.system-log",     "toSystemLog");
		map.put("button.show.messages-log",   "toMessagesLog");
		map.put("button.show.audit-log",      "toAudit");
		map.put("button.show.entries-log",    "toEntriesLog");
		map.put("button.show.incoming-mail",  "toIncomingMail");
		map.put("button.show.outgoing-mail",  "toOutgoingMail");
		map.put("button.restore.password",    "restorePassword");
		map.put("button.extenededLog.on",     "extenededLogOn");
		map.put("button.extenededLog.off",    "extenededLogOff");
		map.put("button.search.in.mdm",       "searchMDMId");
		return map;
	}

	private void createERKCContext(ActivePerson person) throws BusinessException, BusinessLogicException
	{
		if (!checkAccess(ERKCEmployeeOperation.class))
			return;

		ERKCEmployeeOperation erkcEmployeeOperation = createOperation(ERKCEmployeeOperation.class);
		erkcEmployeeOperation.initialize(person);
		erkcEmployeeOperation.createERKCContext();
	}

	@Override
	protected EditEntityOperation createViewOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditPersonOperation operation = (EditPersonOperation) super.createViewOperation(frm);
		createERKCContext(operation.getPerson());
		return operation;
	}

	protected PersonFormBuilder getFormBuilder()
	{
		return sbrfPersonFormBuilder;
	}

	protected Form chooseEditForm(ActivePerson person)
	{
		if (person.getCreationType() == CreationType.UDBO || person.getCreationType() == CreationType.CARD)
			return getFormBuilder().getPartiallyFilledForm();

		return super.chooseEditForm(person);
	}

	protected boolean savePINForm(EditPersonForm frm, HttpServletRequest request) throws Exception
	{
		// УДБО и карточным клиентам никакие ПИНы не сохранять
		if (frm.getActivePerson().getCreationType() == CreationType.SBOL)
			return super.savePINForm(frm, request);
		return true;
	}

	public ActionForward toCommonLog(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return getERKCActionForward(mapping, form, Functional.commonLog);
	}

	public ActionForward toOperationsLog(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return getERKCActionForward(mapping, form, Functional.operationsLog);
	}

	public ActionForward toSystemLog(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return getERKCActionForward(mapping, form, Functional.systemLog);
	}

	public ActionForward toMessagesLog(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return getERKCActionForward(mapping, form, Functional.messagesLog);
	}

	public ActionForward toAudit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return getERKCActionForward(mapping, form, Functional.audit);
	}

	public ActionForward toEntriesLog(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return getERKCActionForward(mapping, form, Functional.entriesLog);
	}

	public ActionForward toIncomingMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return getERKCActionForward(mapping, form, Functional.incomingMail);
	}

	public ActionForward toOutgoingMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return getERKCActionForward(mapping, form, Functional.outgoingMail);
	}

	/**
	 * Включить расширенное логирование
	 */
	public ActionForward extenededLogOn(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditPersonForm frm = (EditPersonForm) form;
		ChangeClientExtendedLoggingOperation operation = createOperation(ChangeClientExtendedLoggingOperation.class);
		operation.initialize(new PersonLoginSource(frm.getEditedPerson()));
		if (PermissionUtil.impliesServiceRigid("ClientExtendedLoggingWithTimeManagement"))
		{
			MapValuesSource fieldsSource  = new MapValuesSource(frm.getFields());
			FormProcessor<ActionMessages, ?> processor = createFormProcessor(fieldsSource, EditPersonForm.EXTENDED_LOGGING_FORM);
			if (processor.process())
			{
				Map<String, Object> result = processor.getResult();

				if ((Boolean)result.get(EditPersonForm.TERMLESS_FIELD))
					operation.extendedLoggingOn(null);
				else
				{
					Date toDate = (Date) result.get(EditPersonForm.TO_DATE_FIELD);
					operation.extendedLoggingOn(DateHelper.toCalendar(toDate));
				}
			}
			else
				saveErrors(request, processor.getErrors());
		}
		else
		{

			Calendar endDate = Calendar.getInstance();
			endDate.add(Calendar.HOUR_OF_DAY, operation.getDefaultLoggingHours());
			operation.extendedLoggingOn(endDate);
		}

		stopLogParameters();
		return start(mapping, form, request, response);
	}

	/**
	 * Отключить расширенное логирование
	 */
	public ActionForward extenededLogOff(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ChangeClientExtendedLoggingOperation operation = createOperation(ChangeClientExtendedLoggingOperation.class);
		EditPersonForm frm = (EditPersonForm) form;
		operation.initialize(new PersonLoginSource(frm.getEditedPerson()));
		operation.extendedLoggingOff();

		stopLogParameters();
		return start(mapping, form, request, response);
	}

	private ActionForward getERKCActionForward(ActionMapping mapping, ActionForm form, Functional functional) throws Exception
	{
		ERKCEmployeeUtil.setCurrentFunctionalInfo(functional);
		return mapping.findForward(TO_ERKC);
	}

	@Override
	protected void doStart(EditEntityOperation operation, EditFormBase frm) throws Exception
	{
		super.doStart(operation, frm);
		EditPersonForm form = (EditPersonForm) frm;
		try
		{
			setMdmId(form);
		}
		catch (BusinessLogicException e)
		{
			saveMessage(currentRequest(), e.getMessage());
		}

	}

	private void setMdmId(EditPersonForm form) throws BusinessException, BusinessLogicException
	{
		if(checkAccess(SearchMDMInformationOperation.class))
		{
			SearchMDMInformationOperation searchInformation = createOperation(SearchMDMInformationOperation.class);
			searchInformation.initialize(form.getPerson());
			form.setMdmId(searchInformation.getEntity());
		}
	}

	/**
	 * Поиск идентификатора клиента в МДМ
	 */
	public ActionForward searchMDMId(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SearchMDMInformationOperation operation = createOperation(SearchMDMInformationOperation.class);
		EditPersonForm frm = (EditPersonForm) form;
		try
		{
			operation.search(frm.getPerson());
		}
		catch (BusinessLogicException e)
		{
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
			saveSessionMessages(currentRequest(), msgs);
		}
		frm.setMdmId(operation.getEntity());
		return start(mapping, form, request, response);
	}

	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws BusinessException, BusinessLogicException
	{
		super.updateFormAdditionalData(frm, operation);
		EditPersonForm form = (EditPersonForm) frm;
		PersonExtendedInfoOperationBase editPersonOperation = (PersonExtendedInfoOperationBase) operation;
		if (ConfigFactory.getConfig(MigrationConfig.class).isShowAdditionalClientStatus())
			form.setClientNodeState(editPersonOperation.getClientNodeState());
		if (checkAccess(ChangeClientExtendedLoggingOperation.class))
		{
			ChangeClientExtendedLoggingOperation extendedLoggingOperation = createOperation(ChangeClientExtendedLoggingOperation.class);
			extendedLoggingOperation.initialize(new PersonLoginSource(form.getEditedPerson()));
			form.setExtendedLoggingEntry(extendedLoggingOperation.getExtendedLoggingEntry());
		}
	}
}
