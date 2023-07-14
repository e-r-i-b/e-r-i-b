package com.rssl.phizic.business.documents.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.LinkHelper;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.gate.longoffer.SumType;
import com.rssl.phizic.gate.payments.longoffer.CardPaymentSystemPaymentLongOffer;

import java.util.Calendar;

/**
 * Заявка на редактирование автоподписки
 * @author niculichev
 * @ created 10.02.2012
 * @ $Author$
 * @ $Revision$
 */
public abstract class AutoSubscriptionPaymentBase extends JurPayment
{
	public static final String AUTO_SUBSCRIPTION_NUMBER = "auto-sub-number";
	public static final String AUTO_SUBSCRIPTION_CREATE_DATE = "auto-sub-create-date";
	public static final String AUTO_SUBSCRIPTION_UPDATE_DATE = "auto-sub-update-date";
	public static final String FROM_RESOURCE = "from-resource";

	public static final String DEFAULT_GROUP_SERVICE = "99.99";

	protected AutoSubscriptionLink findAutoSubscriptionLink() throws DocumentException, DocumentLogicException
	{
		try
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			return personData.getAutoSubscriptionLink(Long.parseLong(getNullSaveAttributeStringValue(AUTO_SUBSCRIPTION_NUMBER)));
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new DocumentLogicException(e);
		}
	}

	/**
	 * Инициализация общих для всех заявок данных
	 * @param link линк на автоподписку
	 * @throws DocumentException
	 * @throws DocumentLogicException
	 */
	protected void storeAutoSubscriptionData(AutoSubscriptionLink link) throws DocumentException, DocumentLogicException
	{
		try
		{
			CardPaymentSystemPaymentLongOffer longOffer = link.getAutoSubscriptionInfo();
			BillingServiceProvider provider = link.getServiceProvider();

			if (provider != null)
			{
				setReceiverInternalId(provider.getId());
			}

			setChargeOffAccount(longOffer.getChargeOffCard());
			setNumber(longOffer.getNumber());
			setOffice(longOffer.getOffice());
			setChannelType(longOffer.getChannelType());
			setChargeOffResourceType(CardLink.class.getName());
			setLongOfferExternalId(link.getExternalId());
			setLongOffer(true);

			// данные поставщика
			setReceiverPointCode(longOffer.getReceiverPointCode());
			setReceiverName(longOffer.getReceiverName());
			setReceiverINN(longOffer.getReceiverINN());
			setNotVisibleBankDetails(longOffer.isNotVisibleBankDetails());
			setReceiverKPP(longOffer.getReceiverKPP());
			setReceiverAccount(longOffer.getReceiverAccount());
			setReceiverBank(longOffer.getReceiverBank());
			setService(longOffer.getService());
			setReceiverOfficeCode(longOffer.getReceiverOfficeCode());
			setExtendedFields(longOffer.getExtendedFields());
			setBillingCode(longOffer.getBillingCode());

			// данные автоподписки
			String groupService = getGroupService(longOffer.getGroupService(), provider == null ? null : provider.getId());
			setGroupService(groupService);

			setChannelType(longOffer.getChannelType());
			setNullSaveAttributeCalendarValue(AUTO_SUBSCRIPTION_CREATE_DATE, longOffer.getCreateDate());
			setNullSaveAttributeCalendarValue(AUTO_SUBSCRIPTION_UPDATE_DATE, longOffer.getUpdateDate());

			SumType sumType = longOffer.getSumType();
			setNullSaveAttributeEnumValue(LONG_OFFER_SUM_TYPE_ATTRIBUTE_NAME, sumType);
			setNullSaveAttributeStringValue(AUTOSUB_FRIENDLY_NAME, longOffer.getFriendlyName());

			ExecutionEventType eventType = longOffer.getExecutionEventType();
			setExecutionEventType(eventType);

			// если регулярный автоплатеж
			if (isRegular(eventType))
			{
				setNextPayDate(longOffer.getNextPayDate());
				// если автоплатеж на фиксированную сумму
				if(sumType == SumType.FIXED_SUMMA_IN_RECIP_CURR )
				{
					setNullSaveAttributeEnumValue(AUTOSUB_TYPE_ATTRIBUTE_NAME, AutoSubType.ALWAYS);
					setDestinationAmount(longOffer.getAmount());
				}
				// если автоплатеж по выставленному счету
				else if(sumType == SumType.BY_BILLING )
				{
					setNullSaveAttributeEnumValue(AUTOSUB_TYPE_ATTRIBUTE_NAME, AutoSubType.INVOICE);
					setMaxSumWritePerMonth(longOffer.getMaxSumWritePerMonth());
				}
			}
			// если пороговый платеж
			else if(SumType.FIXED_SUMMA_IN_RECIP_CURR == sumType && ExecutionEventType.ON_REMAIND == eventType)
			{
				setNullSaveAttributeEnumValue(AUTOSUB_TYPE_ATTRIBUTE_NAME, AutoSubType.THRESHOLD);
			}
			else
			{
				throw new DocumentException("Не известный тип автоподписки. sumType = " + sumType + " eventType = " + eventType);
			}
		}
		catch (BusinessLogicException e)
		{
			throw new DocumentLogicException(e);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
	}

	protected String getGroupService(String groupService, Long receiverId) throws DocumentException
	{
		//заполняется только для создания/редактирования подписки
		return null;
	}

	private boolean isRegular(ExecutionEventType eventType)
	{
		return eventType == ExecutionEventType.ONCE_IN_WEEK
						|| eventType == ExecutionEventType.ONCE_IN_MONTH
						|| eventType == ExecutionEventType.ONCE_IN_QUARTER
						|| eventType == ExecutionEventType.ONCE_IN_YEAR;
	}

	protected void storeFromResourceData(CardLink cardLink) throws DocumentLogicException
	{
		setNullSaveAttributeStringValue(FROM_RESOURCE, cardLink.getCode());
		setNullSaveAttributeStringValue(CHARGE_OFF_RESOURCE_CURRENCY_ATTRIBUTE_NAME, cardLink.getCurrency().getCode());
		setNullSaveAttributeStringValue(CHARGE_OFF_RESOURCE_NAME_ATTRIBUTE_NAME, cardLink.getName());
		if(!LinkHelper.isVisibleInChannel(cardLink))
			throw new DocumentLogicException("Вы не можете выполнить данную операцию. Для доступа измените настройки видимости Вашей карты в пункте меню «Настройки» - «Настройка безопасности» - «Настройка видимости продуктов»");
	}

	public Calendar getCreateDate()
	{
		return getNullSaveAttributeCalendarValue(AUTO_SUBSCRIPTION_CREATE_DATE);
	}

	public Calendar getUpdateDate()
	{
		return getNullSaveAttributeCalendarValue(AUTO_SUBSCRIPTION_UPDATE_DATE);
	}
}
