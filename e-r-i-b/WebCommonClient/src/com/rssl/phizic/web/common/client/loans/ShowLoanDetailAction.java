package com.rssl.phizic.web.common.client.loans;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.auth.modes.ConfirmRequest;
import com.rssl.phizic.auth.modes.iPasSmsPasswordConfirmRequest;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.ErmbHelper;
import com.rssl.phizic.business.resources.external.AbstractStoredResource;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.business.resources.external.StoredResourceMessages;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.loans.LoanState;
import com.rssl.phizic.gate.loans.ScheduleAbstract;
import com.rssl.phizic.operations.account.EditExternalLinkOperation;
import com.rssl.phizic.operations.ermb.EditProductAliasOperation;
import com.rssl.phizic.operations.ext.sbrf.loans.GetLoanInfoOperation;
import com.rssl.phizic.operations.ext.sbrf.strategy.iPasCapConfirmRequest;
import com.rssl.phizic.operations.loans.loan.GetLoanAbstractOperation;
import com.rssl.phizic.operations.loans.loan.GetLoanListOperation;
import com.rssl.phizic.operations.loans.offert.AcceptCreditOffertOperation;
import com.rssl.phizic.utils.ClientConfig;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.common.client.Constants;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lepihina
 * @ created 07.10.2011
 * @ $Author$
 * @ $Reversion$
 */
public class ShowLoanDetailAction extends OperationalActionBase
{
	private static final String LOAN_SMS_ALIAS_FIELD = "clientSmsAlias";
	private static final String LOAN_AUTO_SMS_ALIAS_FIELD = "autoSmsAlias";
	private static final String LOAN_NAME_FIELD = "loanName";
    protected static final String FORWARD_SHOW_JMS = "ShowJMS";

	protected static final String BACK_ERROR_LOAN_MESSAGE  = "Операция временно недоступна. Повторите попытку позже.";

    private ClientConfig clientConfig = ConfigFactory.getConfig(ClientConfig.class);

	protected Map<String, String> getKeyMethodMap()
    {
	    Map<String, String> keyMap = new HashMap<String, String>();
	    keyMap.put("button.saveLoanName", "saveLoanName");
	    keyMap.put("button.saveClientSmsAlias", "saveClientSmsAlias");
        return keyMap;
    }

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)  throws Exception
    {
	    ShowLoanInfoForm frm = (ShowLoanInfoForm) form;
	    Long linkId = frm.getId();

	    GetLoanInfoOperation operation = createOperation(GetLoanInfoOperation.class);
	    operation.initialize(linkId);

	    LoanLink link = operation.getLoanLink();
	    frm.setLoanLink(link);
	    frm.setField(LOAN_NAME_FIELD,link.getName());
        frm.setLoanAccountInfo(operation.getLoanAccountsList());
	    frm.setEarlyLoanRepaymentAllowed(operation.isEarlyLoanRepaymentAllowed());
	    frm.setEarlyLoanRepaymentPossible(operation.isEarlyLoanRepaymentPossible());
	    frm.setApplicationNumber(operation.getApplicationNumber());
	    // если клиент имеет ЕРМБ-профиль, то надо установить значения смс-алиасов
	    if (ErmbHelper.isERMBConnectedPerson())
	    {
		    String autoSmsAlias = link.getAutoSmsAlias();
		    frm.setField(LOAN_AUTO_SMS_ALIAS_FIELD, autoSmsAlias);
		    frm.setField(LOAN_SMS_ALIAS_FIELD, StringUtils.defaultIfEmpty(link.getErmbSmsAlias(), autoSmsAlias));
	    }

	    if(checkAccess(GetLoanAbstractOperation.class))
	    {
	        GetLoanAbstractOperation abstractOperation = createOperation(GetLoanAbstractOperation.class);
	        abstractOperation.initialize(link);
		    Pair<Map<LoanLink, ScheduleAbstract>, Map<LoanLink, String>> scheduleAbstract = abstractOperation.getScheduleAbstract(-Constants.MAX_COUNT_OF_TRANSACTIONS, Constants.MAX_COUNT_OF_TRANSACTIONS, true, false);
			Map<LoanLink, ScheduleAbstract> abstractMap = scheduleAbstract.getFirst();
			frm.setScheduleAbstract(abstractMap.get(link));
	    }

	    if (operation.isUseStoredResource())
	    {
		    saveInactiveESMessage(currentRequest(), StoredResourceMessages.getUnreachableMessageSystem((AbstractStoredResource) link.getLoan()));
	    }

	    if (operation.isBackError())
		{
			saveMessage(currentRequest(), BACK_ERROR_LOAN_MESSAGE);
		}
	    setAnotherLoans(frm,link);
	    frm.setOneTimePassword(true);
		//Установка стратегии подтверждения просмотра текста оферты, если необходимо
	    if (StringHelper.isNotEmpty(frm.getApplicationNumber()))
        {
	        AcceptCreditOffertOperation acceptCreditOffertOperation = createOperation(AcceptCreditOffertOperation.class,"ExtendedLoanClaim");

            boolean isOneTimePassword = acceptCreditOffertOperation.isOneTimePassword();
	        frm.setOneTimePassword(isOneTimePassword);
	        frm.setConfirmEntered(isConfirmEnter(acceptCreditOffertOperation));

            if (! isOneTimePassword && frm.isConfirmEntered())
                frm.setOneTimePassword(true);

	        acceptCreditOffertOperation.resetConfirmStrategy();
            ConfirmationManager.sendRequest(acceptCreditOffertOperation);
	        frm.setConfirmStrategy(acceptCreditOffertOperation.getConfirmStrategy());
            frm.setConfirmableObject(acceptCreditOffertOperation.getConfirmableObject());
	        saveOperation(request, acceptCreditOffertOperation);
        }

        if (clientConfig.isJmsForLoanEnabled())
	        return mapping.findForward(FORWARD_SHOW_JMS);
	    return mapping.findForward(FORWARD_SHOW);
    }

	//подтверждали или нет вход на страницу просмотра оферты
	protected boolean isConfirmEnter(AcceptCreditOffertOperation op)  throws BusinessLogicException, BusinessException
	{
		if (ConfirmationManager.currentConfirmRequest(op.getConfirmableObject()) == null)
		{
			return  false;
		}

		ConfirmRequest confirmRequest = ConfirmationManager.currentConfirmRequest(op.getConfirmableObject());

		if (confirmRequest instanceof iPasSmsPasswordConfirmRequest)
			return ((iPasSmsPasswordConfirmRequest)confirmRequest).isConfirmEnter();
		else
			return ((iPasCapConfirmRequest)confirmRequest).isConfirmEnter();
	}

	/**
	 * Сохранение имени кредита
	 *
	 * @param mapping маппинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return Форвард
	 * @throws Exception
	 */
	public ActionForward saveLoanName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowLoanInfoForm frm = (ShowLoanInfoForm) form;
		Long linkId = frm.getId();
		EditExternalLinkOperation operation = createOperation(EditExternalLinkOperation.class);
		operation.initialize(linkId, LoanLink.class);

        FieldValuesSource valuesSource = getSaveLoanNameFieldValuesSource(frm);

		Form editForm = ShowLoanInfoForm.NAME_FORM;
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, editForm);

		if(processor.process())
		{
			Map<String, Object> result = processor.getResult();
            operation.saveLinkName((String) result.get(LOAN_NAME_FIELD));
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}
        return forwardSaveLoanName(mapping, form, request, response);
	}

    protected MapValuesSource getSaveLoanNameFieldValuesSource(ShowLoanInfoForm form)
    {
        return new MapValuesSource(form.getFields());
    }

    protected ActionForward forwardSaveLoanName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        return start(mapping, form, request, response);
    }

	public ActionForward saveClientSmsAlias(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowLoanInfoForm frm = (ShowLoanInfoForm) form;
		Long linkId = frm.getId();
		EditProductAliasOperation operation = createOperation(EditProductAliasOperation.class);
		operation.initialize(linkId, LoanLink.class);

		Map<String, Object> map = frm.getFields();
		FieldValuesSource valuesSource = new MapValuesSource(map);

		Form editForm = frm.createLoanAliasForm();
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, editForm);

		if(processor.process())
		{
			operation.saveProductSmsAlias((String)frm.getField(LOAN_SMS_ALIAS_FIELD));
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}
		return start(mapping, form, request, response);
	}

	/**
	 * Устанавливает остальные кредиты
	 *
	 * @param frm форма
	 * @param link ссылка на кредит
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	private void setAnotherLoans(ShowLoanInfoForm frm, LoanLink link) throws BusinessException, BusinessLogicException
	{
		GetLoanListOperation listOperation = createOperation(GetLoanListOperation.class);
	    List<LoanLink> anotherLoans = listOperation.getLoans();
	    anotherLoans.remove(link);

		Iterator<LoanLink> iterator = anotherLoans.iterator();
		while(iterator.hasNext())
		{
			if(iterator.next().getLoan().getState()== LoanState.closed)
				iterator.remove();
		}
	    frm.setAnotherLoans(anotherLoans);
	}
}
