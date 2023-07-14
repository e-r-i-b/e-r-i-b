package com.rssl.phizic.web.passwordcards;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.auth.passwordcards.InvalidPasswordCardStateException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.passwordcards.ActivateUserPasswordCardOperation;
import com.rssl.phizic.operations.passwordcards.AssignUserPasswordCardOperation;
import com.rssl.phizic.operations.passwordcards.ChangeUserPasswordCardLockingOperation;
import com.rssl.phizic.operations.passwordcards.GetUserPasswordCardsOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import org.apache.struts.action.*;
import org.apache.struts.util.MessageResources;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Evgrafov
 * @ created 23.12.2005
 * @ $Author: gulov $
 * @ $Revision: 49166 $
 */

public class ListUserPasswordCardsAction extends ListActionBase
{
	private static final String FORWARD_UNBLOCK = "Unblock";

    protected Map getAditionalKeyMethodMap()
    {
        Map<String,String> map = new HashMap<String, String>();

        map.put("button.lock", "lock");
        map.put("button.unlock", "unlock");
	    map.put("button.activate", "activate");
        map.put("button.add", "add");

        return map;
    }

	protected ListEntitiesOperation createListOperation(ListFormBase form) throws BusinessException, BusinessLogicException
	{
		ListUserPassworCardsForm      frm       = (ListUserPassworCardsForm) form;
        GetUserPasswordCardsOperation operation = createOperation(GetUserPasswordCardsOperation.class);
	    operation.initialize(frm.getId());

		return operation;
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListUserPassworCardsForm.FILTER_FORM;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws BusinessException, BusinessLogicException, DataAccessException
	{
		GetUserPasswordCardsOperation op  = (GetUserPasswordCardsOperation) operation;
		Query query = operation.createQuery(getQueryName(frm));
		filterParams.put("login", op.getPerson().getLogin().getId());
		fillQuery(query, filterParams);
		frm.setData(query.executeList());
		frm.setFilters(filterParams);
		updateFormAdditionalData(frm, operation);
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
	    query.setParameter("passwordsCount", filterParams.get("passwordsCount"));
		query.setParameter("blockType", filterParams.get("blockType"));
		query.setParameter("fromDate", filterParams.get("fromDate"));
	    query.setParameter("toDate", filterParams.get("toDate"));
	    query.setParameter("number", filterParams.get("number"));
	    query.setParameter("state", filterParams.get("state"));
	    query.setParameter("login", filterParams.get("login"));
		query.setMaxResults(webPageConfig().getListLimit()+1);
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws BusinessException
	{
		ListUserPassworCardsForm      frm = (ListUserPassworCardsForm)      form;
		GetUserPasswordCardsOperation op  = (GetUserPasswordCardsOperation) operation;

		frm.setActivePerson(op.getPerson());
    }

    public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        ListUserPassworCardsForm         frm       = (ListUserPassworCardsForm) form;

	    AssignUserPasswordCardOperation operation = createOperation(AssignUserPasswordCardOperation.class);

        MapValuesSource mapValuesSource = new MapValuesSource( frm.getFields() );
        Form filterForm = ListUserPassworCardsForm.ADD_FORM;
        FormProcessor<ActionMessages, ?> formProcessor = createFormProcessor(mapValuesSource, filterForm);

        if( formProcessor.process() )
        {
			operation.initialize(frm.getPerson(), (String)frm.getField("cardNumber"));
	        operation.assign();

	        addLogParameters(new BeanLogParemetersReader("Добавляемая сущность", operation.getCardToAssign()));
        }
        else
        {
            saveErrors(request, formProcessor.getErrors());
        }

        return start(mapping, form, request, response);
    }

	public ActionForward lock ( ActionMapping mapping, ActionForm form, HttpServletRequest request,
	                            HttpServletResponse response ) throws Exception
	{
		ListUserPassworCardsForm         frm       = (ListUserPassworCardsForm)form;
		ChangeUserPasswordCardLockingOperation operation = createOperation(ChangeUserPasswordCardLockingOperation.class);
		String[]                         ids       = frm.getSelectedIds();
        ActionMessages msgs = new ActionMessages();

        if ( ids != null && ids.length != 0 )
		{
			MessageResources messageResources   = getResources(request, "personsBundle");
			final String     OPERATION_BLOCKING = messageResources.getMessage("com.rssl.phizic.web.persons.passwordcard.operation.blocking");
            String msgNum = "";
            String motive = messageResources.getMessage("com.rssl.phizic.web.persons.passwordcard.operation.blockingText");

            for (String id : ids)
			{
				try
				{
					operation.initialize(Long.decode(id));
					operation.setBlockReason( frm.getBlockReason() );
					operation.lock();

					addLogParameters(new BeanLogParemetersReader("Блокируемая сущность", operation.getCard()));
				}
				catch (InvalidPasswordCardStateException e)
				{
                    if ( msgNum.equals("") )
                         msgNum = "№: " + e.getCard().getNumber();
                    else msgNum += (", " + e.getCard().getNumber());
				}
			}
            if ( !msgNum.equals("") ) generateInvalidPasswordCardStateMessage(request, OPERATION_BLOCKING, msgs, msgNum, motive);
        }
		else
		{
			msgs.add( ActionMessages.GLOBAL_MESSAGE, new ActionMessage("com.rssl.phizic.web.security.error.noSelected.card") );
			saveErrors(request, msgs);
		}

		return start(mapping, form, request, response);
	}

	public ActionForward unlock ( ActionMapping mapping, ActionForm form, HttpServletRequest request,
	                              HttpServletResponse response ) throws Exception
	{
		ListUserPassworCardsForm         frm       = (ListUserPassworCardsForm)form;
		ChangeUserPasswordCardLockingOperation operation = createOperation(ChangeUserPasswordCardLockingOperation.class);
		String[]                         ids       = frm.getSelectedIds();
		ActionMessages msgs = new ActionMessages();

		if( ids == null || ids.length == 0 )
		{
			msgs.add( ActionMessages.GLOBAL_MESSAGE, new ActionMessage("com.rssl.phizic.web.security.error.noSelected.card") );
		}
		else if (ids.length > 1)
		{
			msgs.add( ActionMessages.GLOBAL_MESSAGE, new ActionMessage("com.rssl.phizic.web.security.error.moreSelected") );
		}
		else
		{
			operation.initialize(Long.parseLong(ids[0]));
			if (!operation.getCard().isBlocked())
			{
				msgs.add( ActionMessages.GLOBAL_MESSAGE, new ActionMessage("com.rssl.phizic.web.persons.passwordcard.operation.cardIsUnblocked", operation.getCard().getNumber()) );
			}
			else if (operation.getCard().getValidPasswordsCount() == 1)
			{
				msgs.add( ActionMessages.GLOBAL_MESSAGE, new ActionMessage("com.rssl.phizic.web.persons.passwordcard.operation.cantUnblockCard", operation.getCard().getNumber()) );
			}
		}

		if (msgs.size() > 0)
		{
			saveErrors(request, msgs);
			return start(mapping, form, request, response);
		}

		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getCard()));

		return new ActionForward( mapping.findForward(FORWARD_UNBLOCK).getPath() + "?person=" + frm.getId()
									+ "&card=" + ids[0],true);
	}

	public ActionForward activate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
	    ListUserPassworCardsForm frm = (ListUserPassworCardsForm) form;
		String[]                 ids = frm.getSelectedIds();
        MessageResources messageResources     = getResources(request, "personsBundle");

        if (ids.length > 1)
        {
            ActionMessages msgs = new ActionMessages();
			msgs.add( ActionMessages.GLOBAL_MESSAGE, new ActionMessage("com.rssl.phizic.web.security.error.moreSelected") );
			saveErrors(request, msgs);
        }
        else {
            if ( ids != null && ids.length != 0 )
		    {
			    ActivateUserPasswordCardOperation operation = createOperation(ActivateUserPasswordCardOperation.class);
			    operation.initialize(Long.valueOf(ids[0]));
                ActionMessages msgs = new ActionMessages();
                String           msgNum               = "";
                String           motive               = messageResources.getMessage("com.rssl.phizic.web.persons.passwordcard.operation.activationText");
                final String     OPERATION_ACTIVATION = messageResources.getMessage("com.rssl.phizic.web.persons.passwordcard.operation.activation");

                try
			    {
				    operation.activate();
				    addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getCard()));
			    }
			    catch (InvalidPasswordCardStateException  e)

                {

                    if (msgNum.equals("") ) msgNum = "№: " + e.getCard().getNumber();


			    }
                if (!msgNum.equals("") )  generateInvalidPasswordCardStateMessage(request, OPERATION_ACTIVATION, msgs, msgNum, motive);
            }
		    else
		    {
			    ActionMessages msgs = new ActionMessages();
			    msgs.add( ActionMessages.GLOBAL_MESSAGE, new ActionMessage("com.rssl.phizic.web.security.error.noSelected.card") );
			    saveErrors(request, msgs);
		    }
        }

        return start(mapping,form,request,response);
	}

	private void generateInvalidPasswordCardStateMessage(HttpServletRequest request, String operationName,ActionMessages msgs, String msgNum, String motive)
	{
		ActionMessage  msg  = new ActionMessage( "com.rssl.phizic.web.persons.invalidPasswordCardState", new String[] {operationName} );
        msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
        if (msgNum != null)
        {
            msg = new ActionMessage( "com.rssl.phizic.web.persons.numberCards", msgNum );
            msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
        }
        msg = new ActionMessage( "com.rssl.phizic.web.persons.numberCards", motive );
        msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);

        saveMessages(request, msgs);
	}
}
