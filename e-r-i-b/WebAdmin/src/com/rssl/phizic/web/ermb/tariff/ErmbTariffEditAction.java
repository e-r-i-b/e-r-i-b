package com.rssl.phizic.web.ermb.tariff;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.ErmbOperationStatus;
import com.rssl.phizic.business.ermb.ErmbTariff;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.ext.sbrf.mobilebank.ermb.tariff.ErmbTariffEditOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

/**
* @author Moshenko
* @ created 04.12.13
* @ $Author$
* @ $Revision$
* Экшен редактирование/создание тарифа ЕРМБ
*/
public class ErmbTariffEditAction extends EditActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		ErmbTariffEditOperation op = createOperation(ErmbTariffEditOperation.class);
		Long id = frm.getId();
		if ((id != null) && (id != 0))
			op.initialize(id);
		else
			op.initializeNew();
		return op;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return ErmbTariffEditForm.EDIT_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{

	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> data) throws Exception //2
	{
		ErmbTariffEditOperation op = (ErmbTariffEditOperation) editOperation;
		ErmbTariff tariff = (ErmbTariff)op.getEntity();
		tariff.setName((String)data.get(Constants.NAME));
		Money connectionCost = new Money((BigDecimal) data.get(Constants.CONNECTION_COST),op.getCurrency());
		tariff.setConnectionCost(connectionCost);
		Money graceClassFee = new Money((BigDecimal) data.get(Constants.GRACE_CLASS_FEE),op.getCurrency());
		tariff.setGraceClass(graceClassFee);
		Money premiumClassFee = new Money((BigDecimal) data.get(Constants.PREMIUM_CLASS_FEE),op.getCurrency());
		tariff.setPremiumClass(premiumClassFee);
		Money socialClassFee = new Money((BigDecimal) data.get(Constants.SOCIAL_CLASS_FEE),op.getCurrency());
		tariff.setSocialClass(socialClassFee);
		Money standardClassFee = new Money((BigDecimal) data.get(Constants.STANDARD_CLASS_FEE),op.getCurrency());
		tariff.setStandardClass(standardClassFee);
		tariff.setChargePeriod(((BigInteger)data.get(Constants.CHARGE_PERIOD)).intValue());
		tariff.setGracePeriod(((BigInteger)data.get(Constants.GRACE_PERIOD)).intValue());
		Money gracePeriodCost = new Money((BigDecimal) data.get(Constants.GRACE_PERIOD_COST),op.getCurrency());
		tariff.setGracePeriodCost(gracePeriodCost);
		tariff.setNoticeConsIncomCardOperation(ErmbOperationStatus.valueOf((String) data.get(Constants.NOTICE_CONS_INCOM_CARD_OP)));
		tariff.setNoticeConsIncomAccountOperation(ErmbOperationStatus.valueOf((String) data.get(Constants.NOTICE_CONS_INCOM_ACCOUNT_OP)));
		tariff.setCardInfoOperation(ErmbOperationStatus.valueOf((String) data.get(Constants.CARD_INFO_OP)));
		tariff.setAccountInfoOperation(ErmbOperationStatus.valueOf((String) data.get(Constants.ACCOUNT_INFO_OP)));
		tariff.setCardMiniInfoOperation(ErmbOperationStatus.valueOf((String) data.get(Constants.CARD_MINI_INFO_OP)));
		tariff.setAccountMiniInfoOperation(ErmbOperationStatus.valueOf((String) data.get(Constants.ACCOUNT_MINI_INFO_OP)));
		tariff.setReIssueCardOperation(ErmbOperationStatus.valueOf((String) data.get(Constants.RE_ISSUE_CARD_OP)));
		tariff.setJurPaymentOperation(ErmbOperationStatus.valueOf((String) data.get(Constants.JUR_PAYMENT_OP)));
		tariff.setTransfersToThirdPartiesOperation(ErmbOperationStatus.valueOf((String) data.get(Constants.TRANSFERS_TO_THIRD_PARTIES_OP)));
		tariff.setDescription((String) data.get(Constants.DESCRIPTION));
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{

		ErmbTariff tariff = (ErmbTariff) entity;
		if (tariff.getId() != null)
		{
			frm.setField(Constants.NAME, tariff.getName());
			Money connectionCost =  tariff.getConnectionCost();
			if (connectionCost != null)
				frm.setField(Constants.CONNECTION_COST, connectionCost.getDecimal());
			Money graceClass = tariff.getGraceClass();
			if (graceClass != null)
				frm.setField(Constants.GRACE_CLASS_FEE, graceClass.getDecimal());
			Money premiumClass = tariff.getPremiumClass();
			if (premiumClass != null)
				frm.setField(Constants.PREMIUM_CLASS_FEE, premiumClass.getDecimal());
			Money socialClass = tariff.getSocialClass();
			if (socialClass != null)
				frm.setField(Constants.SOCIAL_CLASS_FEE, socialClass.getDecimal());
			Money standardClass = tariff.getStandardClass();
			if (standardClass != null)
				frm.setField(Constants.STANDARD_CLASS_FEE, standardClass.getDecimal());
			frm.setField(Constants.CHARGE_PERIOD, tariff.getChargePeriod());
			frm.setField(Constants.GRACE_PERIOD, tariff.getGracePeriod());
			Money gracePeriodCost = tariff.getGracePeriodCost();
			if (gracePeriodCost != null)
				frm.setField(Constants.GRACE_PERIOD_COST, gracePeriodCost.getDecimal());
			frm.setField(Constants.NOTICE_CONS_INCOM_CARD_OP, tariff.getNoticeConsIncomCardOperation().toString());
			frm.setField(Constants.NOTICE_CONS_INCOM_ACCOUNT_OP, tariff.getNoticeConsIncomAccountOperation().toString());
			frm.setField(Constants.CARD_INFO_OP, tariff.getCardInfoOperation().toString());
			frm.setField(Constants.ACCOUNT_INFO_OP, tariff.getAccountInfoOperation().toString());
			frm.setField(Constants.CARD_MINI_INFO_OP, tariff.getCardMiniInfoOperation().toString());
			frm.setField(Constants.ACCOUNT_MINI_INFO_OP, tariff.getAccountMiniInfoOperation().toString());
			frm.setField(Constants.RE_ISSUE_CARD_OP, tariff.getReIssueCardOperation().toString());
			frm.setField(Constants.JUR_PAYMENT_OP, tariff.getJurPaymentOperation().toString());
			frm.setField(Constants.TRANSFERS_TO_THIRD_PARTIES_OP, tariff.getTransfersToThirdPartiesOperation().toString());
			frm.setField(Constants.DESCRIPTION, tariff.getDescription());
		}
		else
		{
			frm.setField(Constants.CHARGE_PERIOD, 1);
			frm.setField(Constants.GRACE_PERIOD, 0);
			frm.setField(Constants.GRACE_PERIOD_COST, 0);
			frm.setField(Constants.CONNECTION_COST, 0);
		}
	}
}
