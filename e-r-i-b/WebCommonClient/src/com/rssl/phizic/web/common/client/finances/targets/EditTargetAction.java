package com.rssl.phizic.web.common.client.finances.targets;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.BusinessLogicMessageException;
import com.rssl.phizic.business.documents.AccountOpeningClaim;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.ermb.MobileBankManager;
import com.rssl.phizic.business.finances.targets.AccountTarget;
import com.rssl.phizic.business.finances.targets.TargetType;
import com.rssl.phizic.business.resources.external.ActiveNotCreditMainCardFilter;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.business.resources.external.comparator.CardAmountComparator;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.ext.sbrf.payment.CreateMoneyBoxOperation;
import com.rssl.phizic.operations.finances.targets.CreateAccountTargetOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.http.UrlBuilder;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author lepihina
 * @ created 23.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class EditTargetAction extends EditActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase form) throws BusinessException
	{
		EditTargetForm frm = (EditTargetForm) form;
		CreateAccountTargetOperation operation = createOperation(CreateAccountTargetOperation.class);

		if (frm.getId() != null)
			operation.initializeById(frm.getId());
		else
			operation.initialize(TargetType.valueOf(frm.getTargetType()));
		frm.setHaveTariffTemplate(operation.haveTariffTemplate());
		return operation;
	}

	protected EditEntityOperation createViewOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		CreateAccountTargetOperation operation = (CreateAccountTargetOperation)createEditOperation(frm);
		if (operation.tooManyTargets())
			saveError(currentRequest(), new ActionMessage(operation.getTooManyTargetsMessage(), false));

		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditTargetForm.EDIT_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		Currency nationalCurrency = MoneyUtil.getNationalCurrency();
		AccountTarget target = (AccountTarget)entity;

		if(TargetType.OTHER == target.getDictionaryTarget())
			target.setName((String) data.get("targetName"));
		target.setNameComment((String) data.get("targetNameComment"));
		target.setAmount(new Money((BigDecimal) data.get("targetAmount"), nationalCurrency));
		target.setPlannedDate(DateHelper.toCalendar((Date) data.get("targetPlanedDate")));
	}

	protected void updateForm(EditFormBase form, Object entity) throws Exception
	{
		EditTargetForm frm = (EditTargetForm) form;
		AccountTarget target = (AccountTarget) entity;
		frm.setTargetType(target.getDictionaryTarget().name());
		frm.setField("dictionaryTarget", target.getDictionaryTarget());
		frm.setField("targetName", target.getName());
		frm.setField("targetNameComment", target.getNameComment());
		if (target.getAmount() != null)
			frm.setField("targetAmount", target.getAmount().getDecimal());

		if (target.getPlannedDate() != null)
			frm.setField("targetPlanedDate", DateHelper.toDate(target.getPlannedDate()));
		else
		{
			Calendar minDate = DateHelper.getCurrentDate();
			minDate.add(Calendar.DATE, 1);
			frm.setField("targetPlanedDate", DateHelper.toDate(minDate));
		}
	}

	protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation operation) throws Exception
	{
		if (checkAccess(CreateMoneyBoxOperation.class, "CreateMoneyBoxPayment"))
		{
			EditTargetForm frm = (EditTargetForm) form;
			List<CardLink> cardLinks = PersonContext.getPersonDataProvider().getPersonData().getCards(new ActiveNotCreditMainCardFilter());
			Collections.sort(cardLinks, new CardAmountComparator());
			frm.setMoneyBoxChargeOffResources(cardLinks);
			Map<String, Boolean> moneyBoxChargeOffResourcesMBConnections = new HashMap<String, Boolean>();
			for(CardLink link: cardLinks)
			{
				moneyBoxChargeOffResourcesMBConnections.put(link.getCode(), MobileBankManager.hasAnyMB(link.getNumber()));
			}
			frm.setMoneyBoxChargeOffResourcesMBConnect(moneyBoxChargeOffResourcesMBConnections);
			//обновляем данные по копилке
			CreateAccountTargetOperation op = (CreateAccountTargetOperation) operation;
			AccountOpeningClaim claim = op.findClaim();
			if (claim != null && claim.getCreateMoneyBox())
			{
				form.setField("createMoneyBox", claim.getCreateMoneyBox());
				form.setField("moneyBoxSumType", claim.getMoneyBoxSumType());
				form.setField("moneyBoxName", claim.getMoneyBoxName());

				String fromCard = claim.getMoneyBoxFromResourceCode();
				form.setField("moneyBoxFromResource", fromCard);
				if (claim.getMoneyBoxPercent() != null)
					form.setField("moneyBoxPercent", claim.getMoneyBoxPercent());
				form.setField("moneyBoxSellAmount", claim.getMoneyBoxSellAmount());

				form.setField("longOfferStartDate", claim.getMoneyBoxStartDate());
				form.setField("longOfferEventType", claim.getMoneyBoxEventType());
			}
			else
				form.setField("moneyBoxPercent", DocumentHelper.getMoneyBoxDefaultPercent());
		}
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		CreateAccountTargetOperation operation = (CreateAccountTargetOperation) editOperation;
		operation.setFormFieldsValues(validationResult);
	}

	protected ActionMessages validateAdditionalFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		ActionMessages msgs = new ActionMessages();
		CreateAccountTargetOperation op = (CreateAccountTargetOperation) operation;

		if (op.tooManyTargets())
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(op.getTooManyTargetsMessage(), false));

		return msgs;
	}

	@Override
	protected ActionForward doSave(EditEntityOperation operation, ActionMapping mapping, EditFormBase frm) throws Exception
	{
		try
		{
			return super.doSave(operation, mapping, frm);
		}
		catch (BusinessLogicMessageException e)
		{
			String message = StrutsUtils.getMessage(e.getMessage(), e.getBundle());
			saveError(currentRequest(), message);
			return createStartActionForward(frm, mapping);
		}
	}

	@Override
	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm) throws BusinessException
	{
		UrlBuilder urlBuilder = new UrlBuilder();
		urlBuilder.setUrl(super.createSaveActionForward(operation, frm).getPath());
		urlBuilder.addParameter("targetId", ((CreateAccountTargetOperation)operation).getEntity().getId().toString());
		return new ActionRedirect(urlBuilder.toString());
	}
}
