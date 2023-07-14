package com.rssl.phizic.web.ermb;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.ErmbTariff;
import com.rssl.phizic.business.ermb.bankroll.config.BankrollProductAvailabilityType;
import com.rssl.phizic.business.ermb.bankroll.config.BankrollProductRule;
import com.rssl.phizic.business.ermb.bankroll.config.ClientCategoryType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ermb.ErmbConfig;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.ermb.BankrollProductRuleEditOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.Map;

/**
 * Экшн создания/редактирования правила включения видимости продуктов по умолчанию
 * @author Rtischeva
 * @ created 04.12.13
 * @ $Author$
 * @ $Revision$
 */
public class ErmbBankrollProductRuleEditAction extends EditActionBase
{
	@Override
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		BankrollProductRuleEditOperation operation = createOperation(BankrollProductRuleEditOperation.class);

		ErmbBankrollProductRuleEditForm form = (ErmbBankrollProductRuleEditForm) frm;

		Long ruleId = form.getId();
		if (ruleId != null)
			operation.initialize(ruleId);
		else
			operation.initializeNew();

		return operation;
	}

	@Override
	protected Form getEditForm(EditFormBase frm)
	{
		return ErmbBankrollProductRuleEditForm.FORM;
	}

	@Override
	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		BankrollProductRule rule = (BankrollProductRule) entity;
		rule.setName((String) data.get("name"));
		rule.setClientCategory(ClientCategoryType.fromValue((String) data.get("clientCategory")));
		rule.setCreditCardCriteria(BankrollProductAvailabilityType.fromValue((String) data.get("creditCardsCriteria")));
		rule.setDepositCriteria(BankrollProductAvailabilityType.fromValue((String) data.get("depositsCriteria")));
		rule.setLoanCriteria(BankrollProductAvailabilityType.fromValue((String) data.get("loansCriteria")));
        rule.setAgeFrom((Integer) data.get("ageFrom"));
        rule.setAgeUntil((Integer) data.get("ageUntil"));
	}

	@Override
	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		ErmbBankrollProductRuleEditForm form = (ErmbBankrollProductRuleEditForm) frm;
		BankrollProductRule rule = (BankrollProductRule) entity;
		form.setField("name", rule.getName());
		form.setField("clientCategory", rule.getClientCategory());
		form.setField("ageFrom", rule.getAgeFrom());
		form.setField("ageUntil", rule.getAgeUntil());
		form.setField("creditCardsCriteria", rule.getCreditCardCriteria());
		form.setField("depositsCriteria", rule.getDepositCriteria());
		form.setField("loansCriteria", rule.getLoanCriteria());

		form.setCardsVisibility(rule.isCardsVisibility());
		form.setCardsNotification(rule.isCardsNotification());
		form.setDepositsVisibility(rule.isDepositsVisibility());
		form.setDepositsNotification(rule.isDepositsNotification());
		form.setLoansVisibility(rule.isLoansVisibility());
		form.setLoansNotification(rule.isLoansNotification());
		form.setNewProductsVisibility(rule.isNewProductsVisibility());
		form.setNewProductsNotification(rule.isNewProductsNotification());

		ErmbTariff tariff = rule.getErmbTariff();
		if (tariff != null)
			form.setSelectedTariff(tariff.getId());

		form.setState(rule.isActive());
	}

	@Override
	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		ErmbBankrollProductRuleEditForm form = (ErmbBankrollProductRuleEditForm) editForm;
		BankrollProductRuleEditOperation op = (BankrollProductRuleEditOperation) editOperation;

		BankrollProductRule rule = op.getEntity();

		boolean commonAttribute = ConfigFactory.getConfig(ErmbConfig.class).getProductAvailabilityCommonAttribute();

		if (commonAttribute)
		{
			rule.setCardsVisibility(form.isCardsNotification());
			rule.setDepositsVisibility(form.isDepositsNotification());
			rule.setLoansVisibility(form.isLoansNotification());
			rule.setNewProductsVisibility(form.isNewProductsNotification());
		}
		else
		{
			rule.setCardsVisibility(form.isCardsVisibility());
			rule.setDepositsVisibility(form.isDepositsVisibility());
			rule.setLoansVisibility(form.isLoansVisibility());
			rule.setNewProductsVisibility(form.isNewProductsVisibility());
		}

		rule.setCardsNotification(form.isCardsNotification());
		rule.setDepositsNotification(form.isDepositsNotification());
		rule.setLoansNotification(form.isLoansNotification());
		rule.setNewProductsNotification(form.isNewProductsNotification());

		rule.setErmbTariff(op.getSelectedTariff(form.getSelectedTariff()));
		rule.setTerbankCodes(op.getTerbankCodes(form.getSelectedTerbankIds()));
		rule.setActive(form.isState());
	}

	@Override
	protected ActionMessages validateAdditionalFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		ActionMessages messages = super.validateAdditionalFormData(frm, operation);
		ErmbBankrollProductRuleEditForm form = (ErmbBankrollProductRuleEditForm) frm;
		if (ArrayUtils.isEmpty(form.getSelectedTerbankIds()))
		{
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Заполните хотя бы один тербанк", false));
			return messages;
		}
		return messages;
	}

	@Override
	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		ErmbBankrollProductRuleEditForm form = (ErmbBankrollProductRuleEditForm) frm;
		BankrollProductRuleEditOperation op = (BankrollProductRuleEditOperation) operation;

		if (!frm.isFromStart())
			form.setTerbanks(op.getTerbanks(form.getSelectedTerbankIds()));
		else
		{
			BankrollProductRule rule = op.getEntity();
			form.setTerbanks(op.getTerbanksByCodes(rule.getTerbankCodes()));
		}

		form.setTariffs(op.getTariffs());
	}
}
