package com.rssl.phizic.operations.payment;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.accounts.AccountsHelper;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.common.types.RequisiteType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.atm.AtmApiConfig;
import com.rssl.phizic.config.mobile.MobileApiConfig;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Roshka
 * @ created 29.11.2005
 * @ $Author$
 * @ $Revision$
 */
public class CreateFormPaymentOperation extends EditDocumentOperationBase implements Serializable
{
	//ƒоп. поле означающее что поставщик услуг относитс€ к рискованным, и требуетс€ дополнительна€ проверка
	private static final String IS_RISK_RECIPIENT_FIELD = "_IsRiskRecipient";

	/**
	 *
	 * @return код источника списани€, или null/
	 */
	public String getFromResourceLink()
	{
		BusinessDocument document = getDocument();
		if  (document!=null && document instanceof RurPayment)
		{
			return ((RurPayment)document).getFromResourceLink();
		}
		else
			return null;
	}

	/**
	 *
	 * ƒл€ счетов с значени€ми полей вид/подвид равными 76/2, 77/41, 31/11 совершение финансовых операций запрещено.
	 *
	 * @param  list список счетов
	 *
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 *
	 */
	protected final void filterNotPermittedForFinancialTransactions(List<? extends PaymentAbilityERL> list) throws BusinessException, BusinessLogicException
	{
		ListIterator<? extends PaymentAbilityERL> i = list.listIterator();
		while (i.hasNext())
		{
			PaymentAbilityERL link = i.next();
			if (link.getResourceType() == ResourceType.ACCOUNT)
			{
				if (!AccountsHelper.isPermittedForFinancialTransactions((Account) link.getValue()))
				{
					i.remove();
				}
			}
		}
	}

	/**
	 * ƒл€ mAPI и atmAPI. »щет среди дополнительных полей рискованные и добавл€ет информационное сообщение в случае успеха
	 */
	public void fetchRiskFields() throws BusinessException
	{
		try
		{
			if (ApplicationUtil.isApi())
			{
				BusinessDocument document = getDocument();
				if (!(document instanceof JurPayment))
					return;

				JurPayment jurPayment = (JurPayment) document;
				List<Field> extendedFields = jurPayment.getExtendedFields();
				boolean neadAddRiskMessage = false;
				//ѕеребор доп. полей и поиск рискованных, дл€ отображени€ информации о рискованности платежа клиенту
				for (Field extendedField : extendedFields)
				{
					//≈сли пришла ошибка по полю, то не нужно лишней информации, пусть сначала еЄ исправит
					if (StringHelper.isNotEmpty(extendedField.getError()))
					{
						neadAddRiskMessage = false;
						break;
					}
					if (neadAddRiskMessage)	continue;

					if (extendedField.getExternalId().equalsIgnoreCase(IS_RISK_RECIPIENT_FIELD))
					{
						String isRiskRecipient = (String) extendedField.getValue();
						neadAddRiskMessage = Boolean.valueOf(isRiskRecipient);
					}
					if (neadAddRiskMessage)	continue;
					List<RequisiteType> requisiteTypeList =	extendedField.getRequisiteTypes();
					//если нет реквизитов пол€ или оно не рискованное, то шерстим дальше
					if (CollectionUtils.isEmpty(requisiteTypeList) ||
							(requisiteTypeList.indexOf(RequisiteType.IS_RISK_REQUISITE) < 0))
						continue;

					neadAddRiskMessage = true;
				}
				if (neadAddRiskMessage)
					addRiskInfoMessage();
			}
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ƒобавл€ет информационное сообщение о рискованности платежа и необходимости проверить реквизиты платежа
	 */
	private void addRiskInfoMessage()
	{
		String fieldRiskInfoMessage = null;
		if (ApplicationUtil.isATMApi())
		{
			AtmApiConfig atmApiConfig = ConfigFactory.getConfig(AtmApiConfig.class);
			fieldRiskInfoMessage = atmApiConfig.getFieldRiskInfoMessage();
		}
		else if (ApplicationUtil.isMobileApi())
		{
			MobileApiConfig mobileApiConfig = ConfigFactory.getConfig(MobileApiConfig.class);
			fieldRiskInfoMessage = mobileApiConfig.getFieldRiskInfoMessage();
		}
        else if (ApplicationUtil.isSocialApi())
		{
			MobileApiConfig mobileApiConfig = ConfigFactory.getConfig(MobileApiConfig.class);
			fieldRiskInfoMessage = mobileApiConfig.getFieldRiskInfoMessage();
		}

		Collection<String> errors = getStateMachineEvent().getMessageCollector().getErrors();
		//—ообщение добавл€етс€ только если нет ошибок и есть текст сообщени€ в проперт€х
		if (CollectionUtils.isNotEmpty(errors))
			return;

		if (StringHelper.isNotEmpty(fieldRiskInfoMessage))
		{
			getStateMachineEvent().addMessage(fieldRiskInfoMessage);
		}
		else
			log.error("¬ настройках не обнаружен текст сообщени€ о рискованном платеже.");
	}
}
