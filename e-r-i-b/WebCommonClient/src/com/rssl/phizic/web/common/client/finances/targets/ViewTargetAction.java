package com.rssl.phizic.web.common.client.finances.targets;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.AccountOpeningClaim;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.finances.targets.AccountTarget;
import com.rssl.phizic.business.finances.targets.AccountTargetService;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.ext.sbrf.payment.CreateMoneyBoxOperation;
import com.rssl.phizic.operations.finances.targets.ConfirmAccountTargetWithMoneyBoxOperation;
import com.rssl.phizic.operations.finances.targets.CreateAccountTargetOperation;
import com.rssl.phizic.operations.payment.ConfirmFormPaymentOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.http.UrlBuilder;
import com.rssl.phizic.web.common.FilterActionForm;
import com.rssl.phizic.web.common.client.ext.sbrf.payment.ConfirmClientDocumentAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akrenev
 * @ created 04.06.2013
 * @ $Author$
 * @ $Revision$
 *
 * Ёкшен подтверждени€ за€вки на открытие вклада дл€ цели
 */

public class ViewTargetAction extends ConfirmClientDocumentAction
{
	private static final String SUCCESS_FORWARD = "Success";
	private static final ActionForward EMPTY_ACTION_FORWARD = new ActionForward();
	private static final State SAVED_STATE = new State("SAVED");

	private void updateForm(FilterActionForm form, CreateAccountTargetOperation operation) throws BusinessException, ParseException
	{
		AccountTarget target = operation.getEntity();
		form.setField("createMoneyBox", false);
		if (checkAccess(CreateMoneyBoxOperation.class, "CreateMoneyBoxPayment"))
		{
			AccountOpeningClaim claim = operation.findClaim();
			if (claim != null && claim.getCreateMoneyBox())
			{
				form.setField("createMoneyBox", claim.getCreateMoneyBox());
				form.setField("moneyBoxSumType", claim.getMoneyBoxSumType());
				form.setField("moneyBoxName", claim.getMoneyBoxName());

				String fromCard = claim.getMoneyBoxFromResourceCode();
				form.setField("moneyBoxFromResource", PersonContext.getPersonDataProvider().getPersonData().getCard(Long.valueOf(fromCard.substring(CardLink.CODE_PREFIX.length() + 1))));
				form.setField("moneyBoxPercent", claim.getMoneyBoxPercent());
				form.setField("moneyBoxSellAmount", claim.getMoneyBoxSellAmount());

				form.setField("longOfferStartDate", claim.getMoneyBoxStartDate());
				form.setField("longOfferEventType", claim.getMoneyBoxEventType());
			}
		}

		form.setField("dictionaryTarget",  target.getDictionaryTarget());
		form.setField("targetName",        target.getName());
		form.setField("targetNameComment", target.getNameComment());
		form.setField("targetAmount",      target.getAmount().getDecimal());
		form.setField("targetPlanedDate",  DateHelper.toDate(target.getPlannedDate()));
		form.setField("depositTariffPlanCode", operation.getDepositTarifPlanCode());
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CreateAccountTargetOperation operation = createOperation(CreateAccountTargetOperation.class);
		ViewTargetForm frm = (ViewTargetForm) form;
		operation.initializeById(frm.getTargetId());
		frm.setId(operation.getEntity().getClaimId());
		updateForm(frm, operation);
		return super.start(mapping, form, request, response);
	}

	@Override
	public ActionForward doNextStage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward forward = super.doNextStage(mapping, form, request, response);
		if (!EMPTY_ACTION_FORWARD.equals(forward))
			return forward;

		Long targetId = ((ViewTargetForm) form).getTargetId();
		UrlBuilder urlBuilder = new UrlBuilder();
		urlBuilder.setUrl(getCurrentMapping().findForward(FORWARD_START).getPath());
		urlBuilder.addParameter("targetId", targetId.toString());
		return new ActionForward(urlBuilder.toString(), true);
	}

	@Override
	protected ActionForward createNextStageDocumentForward(ConfirmFormPaymentOperation operation, boolean backToEdit)
	{
		AccountOpeningClaim document = (AccountOpeningClaim) operation.getConfirmableObject();
		if (SAVED_STATE.equals(document.getState()))
			return EMPTY_ACTION_FORWARD;

		return getCurrentMapping().findForward(SUCCESS_FORWARD);

	}

	protected ConfirmFormPaymentOperation createConfirmOperation(ExistingSource source) throws BusinessException, BusinessLogicException
	{
		if(checkAccess(ConfirmAccountTargetWithMoneyBoxOperation.class, "CreateMoneyBoxPayment"))
			return createOperation(ConfirmAccountTargetWithMoneyBoxOperation.class, "CreateMoneyBoxPayment");
		else
			return createOperation(ConfirmFormPaymentOperation.class, getServiceName(source));
	}
}
