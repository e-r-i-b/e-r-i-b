package com.rssl.phizic.web.loans.claims;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.phizic.auth.GuestLoginImpl;
import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.business.*;
import com.rssl.phizic.business.clients.DefaultClientIdGenerator;
import com.rssl.phizic.business.clients.list.ClientInformation;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.GuestPerson;
import com.rssl.phizic.business.persons.PersonDocumentImpl;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.loanclaim.LoanClaimCreateOperation;
import com.rssl.phizic.operations.person.search.SearchPersonOperation;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.RandomHelper;
import com.rssl.phizic.web.persons.SearchPersonForm;
import com.rssl.phizic.web.persons.search.SearchPersonActionBase;
import org.apache.struts.action.*;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Nady
 * @ created 01.04.2015
 * @ $Author$
 * @ $Revision$
 */
/**
 * Экшн для поиска клиента для создания заявки на кредит в АРМ Сотрудника
 */
public class LoanClaimCreateAction extends SearchPersonActionBase
{
	private static final String FORWARD_SUCCESS = "Success";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.withGuest", "redirectWithGuest");
		map.put("button.preSearch", "preSearch");
		map.put("button.chooseClient", "chooseClient");
		return map;
	}

	@Override
	protected SearchPersonOperation createSearchOperation() throws BusinessException
	{
		return createOperation(LoanClaimCreateOperation.class);
	}

	@Override
	protected ActionForward getStartActionForward()
	{
		return getCurrentMapping().findForward(FORWARD_START);
	}

	@Override
	protected ActionForward getErrorActionForward()
	{
		return getCurrentMapping().findForward(FORWARD_START);
	}

	@Override
	protected ActionForward getNextActionForward(SearchPersonForm frm) throws BusinessLogicException, BusinessException
	{
		ActionRedirect redirect = new ActionRedirect(getCurrentMapping().findForward(FORWARD_SUCCESS).getPath());
		redirect.addParameter("form", "ExtendedLoanClaim");
		return redirect;
	}

	@Override
	protected UserVisitingMode getUserVisitingMode(SearchPersonForm frm)
	{
		return UserVisitingMode.EMPLOYEE_INPUT_BY_IDENTITY_DOCUMENT;
	}

	public ActionForward redirectWithGuest(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoanClaimCreateForm frm = (LoanClaimCreateForm) form;
		GuestPerson guestPerson = ((LoanClaimCreateOperation)getOperation(request)).getGuestPerson();
		PersonContext.getPersonDataProvider().setPersonData(new GuestPersonData(new GuestLoginImpl(null, Long.parseLong(RandomHelper.rand(7, RandomHelper.DIGITS))),guestPerson, false, null ,null, null));
		resetOperation(request);
		return getNextActionForward(frm);
	}

	public ActionForward preSearch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException, BusinessLogicException
	{
		LoanClaimCreateOperation operation = (LoanClaimCreateOperation) createSearchOperation();
		LoanClaimCreateForm frm = (LoanClaimCreateForm) form;
		addLogParameters(frm);
		try
		{
			FormProcessor<ActionMessages, ?> processor = getFormProcessor(frm);
			// если данных хватает, то пытаемся найти клиента
			if (processor.process())
			{
				Map<String, Object> params = processor.getResult();
				operation.initializePreSearch(params, getUserVisitingMode(frm));
				saveOperation(request, operation);
				List<ClientInformation> clientInformations = operation.getActivePersons();
				if (clientInformations.size()==1)
				{
					frm.setField("region", clientInformations.get(0).getTb());
					resetOperation(request);
					return search(mapping, form, request,response);
				} else if (clientInformations.size() == 0)
				{
					ActionRedirect redirect = new ActionRedirect(getCurrentMapping().getPath()+".do");

					redirect.addParameter("guestPerson", true);
					return redirect;
				} else if (clientInformations.size() > 1)
				{
					frm.setActivePersons(clientInformations);
				}
			}
			//не хватило данных -- сохраняем ошибку и рисуем страницу поиска
			saveErrors(request, processor.getErrors());
		}
		catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(request, e);
		}
		catch (BusinessLogicException e)
		{
			saveError(request,e);
		}
		catch (Exception e)
		{

		}
		return getStartActionForward();
	}

	public ActionForward chooseClient(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoanClaimCreateForm frm = (LoanClaimCreateForm) form;
		String clientId = frm.getClientId();
		for (ClientInformation clientInformation : ((LoanClaimCreateOperation)getOperation(request)).getActivePersons())
		{
			if (clientInformation.getId().equals(Long.parseLong(clientId)))
			{
				Map<String, Object> formData = ((LoanClaimCreateOperation)getOperation(request)).getFormData();
				frm.setField("firstName", formData.get("firstName"));
				frm.setField("patrName", formData.get("patrName"));
				frm.setField("surName", formData.get("surName"));
				frm.setField("documentType", formData.get("documentType"));
				frm.setField("documentSeries", formData.get("documentSeries"));
				frm.setField("documentNumber", formData.get("documentNumber"));
				frm.setField("birthDay", formData.get("birthDay"));
				frm.setField("region", clientInformation.getTb());
			}
		}
		resetOperation(request);
		return search(mapping, form, request, response);
	}
}
