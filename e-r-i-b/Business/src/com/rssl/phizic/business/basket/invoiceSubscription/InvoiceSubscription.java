package com.rssl.phizic.business.basket.invoiceSubscription;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizgate.common.basket.RequisitesHelper;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.basket.InvoiceSubscriptionErrorType;
import com.rssl.phizic.common.types.basket.InvoiceSubscriptionState;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.security.SecurityLogicException;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author osminin
 * @ created 11.04.14
 * @ $Author$
 * @ $Revision$
 *
 * Сущность - Подписка на инвойсы(автоподписка)
 */
public class InvoiceSubscription implements Comparable, ConfirmableObject
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final int NEXT_STATE_POS     = 1;
	private static final int PREVIOUS_STATE_POS = 0;

	private Long id;                                        //ид автоподписки
	private long loginId;                                   //ид логина, к которому данная автоподписка привязана
	private Long accountingEntityId;                        //ид объекта учёта, к которому дання автоподписка привязана
	private String name;                                    //имя услуги
	private String autoPayId;
	private String requestId;
	private Calendar startDate;
	private ChannelType channelType;
	private String tb;
	private ExecutionEventType executionEventType;
	private Calendar payDate;
	private String chargeOffCard;
	private String codeRecipientBs;
	private String recName;                                //отображаемое имя поставщика услуг
	private String codeService;
	private String nameService;
	private String recInn;
	private String recCorAccount;
	private String recKpp;
	private String recBic;
	private String recAccount;
	private String recNameOnBill;
	private String recPhoneNumber;
	private String recTb;
	private String requisites;                             //реквизиты поставщика услуг
	private Long recipientId;                              //ид соответствующего поставщика услуг
	private String errorDesc;                              //текст ошибки, отображаемый пользователю, если автоподписка находится в соответствующем состоянии
	private InvoiceSubscriptionErrorType errorType;        //тип ошибки, если таковая имеется
	private Long numberOfNotPaidInvoices;                  //число неоплаченных инвойсов данной автоподписки
	private Long numberOfDelayedInvoices;                  //число отложенных инвойсов данной автоподписки
	private Calendar delayDate;                            //дата ближайшего отложенного инвойса
	private String billingCode;                            //код биллинга поставщика услуг
	private String documentStatus;                          //Статус заявки на создание подписки (В текущей ревизии необходимо для визуализации CreateInvoiceSubscriptionPayment) //TODO: Вынести в отдельную сущность для визуализации
	private CreationType creationType;                      // тип создания(ручной, автоматический)
	private ConfirmStrategyType confirmStrategyType;        // стратегия подтверждения подписки
	private String baseState;
	private String autoSubExternalId;

	public Long getId()
	{
		return id;
	}

	public byte[] getSignableObject() throws SecurityException, SecurityLogicException
	{
		return new byte[0];
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public long getLoginId()
	{
		return loginId;
	}

	public void setLoginId(long loginId)
	{
		this.loginId = loginId;
	}

	public Long getAccountingEntityId()
	{
		return accountingEntityId;
	}

	public void setAccountingEntityId(Long accountingEntityId)
	{
		this.accountingEntityId = accountingEntityId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getAutoPayId()
	{
		return autoPayId;
	}

	public void setAutoPayId(String autoPayId)
	{
		this.autoPayId = autoPayId;
	}

	public String getRequestId()
	{
		return requestId;
	}

	public void setRequestId(String requestId)
	{
		this.requestId = requestId;
	}

	public Calendar getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	public ChannelType getChannelType()
	{
		return channelType;
	}

	public void setChannelType(ChannelType channelType)
	{
		this.channelType = channelType;
	}

	public String getTb()
	{
		return tb;
	}

	public void setTb(String tb)
	{
		this.tb = tb;
	}

	public ExecutionEventType getExecutionEventType()
	{
		return executionEventType;
	}

	public void setExecutionEventType(ExecutionEventType executionEventType)
	{
		this.executionEventType = executionEventType;
	}

	public String getChargeOffCard()
	{
		return chargeOffCard;
	}

	public void setChargeOffCard(String chargeOffCard)
	{
		this.chargeOffCard = chargeOffCard;
	}

	public InvoiceSubscriptionState getState()
	{
		if (baseState.contains(com.rssl.phizic.common.types.basket.Constants.STATE_DELIMITER))
		{
			return InvoiceSubscriptionState.WAIT;
		}
		return InvoiceSubscriptionState.valueOf(baseState);
	}

	public String getCodeRecipientBs()
	{
		return codeRecipientBs;
	}

	public void setCodeRecipientBs(String codeRecipientBs)
	{
		this.codeRecipientBs = codeRecipientBs;
	}

	public String getRecName()
	{
		return recName;
	}

	public void setRecName(String recName)
	{
		this.recName = recName;
	}

	public String getCodeService()
	{
		return codeService;
	}

	public void setCodeService(String codeService)
	{
		this.codeService = codeService;
	}

	public String getNameService()
	{
		return nameService;
	}

	public void setNameService(String nameService)
	{
		this.nameService = nameService;
	}

	public String getRecInn()
	{
		return recInn;
	}

	public void setRecInn(String recInn)
	{
		this.recInn = recInn;
	}

	public String getRecCorAccount()
	{
		return recCorAccount;
	}

	public void setRecCorAccount(String recCorAccount)
	{
		this.recCorAccount = recCorAccount;
	}

	public String getRecKpp()
	{
		return recKpp;
	}

	public void setRecKpp(String recKpp)
	{
		this.recKpp = recKpp;
	}

	public String getRecBic()
	{
		return recBic;
	}

	public void setRecBic(String recBic)
	{
		this.recBic = recBic;
	}

	public String getRecAccount()
	{
		return recAccount;
	}

	public void setRecAccount(String recAccount)
	{
		this.recAccount = recAccount;
	}

	public String getRecNameOnBill()
	{
		return recNameOnBill;
	}

	public void setRecNameOnBill(String recNameOnBill)
	{
		this.recNameOnBill = recNameOnBill;
	}

	public String getRecPhoneNumber()
	{
		return recPhoneNumber;
	}

	public void setRecPhoneNumber(String recPhoneNumber)
	{
		this.recPhoneNumber = recPhoneNumber;
	}

	public String getRecTb()
	{
		return recTb;
	}

	public void setRecTb(String recTb)
	{
		this.recTb = recTb;
	}

	public String getRequisites()
	{
		return requisites;
	}

	public void setRequisites(String requisites)
	{
		this.requisites = requisites;
	}

	public Long getRecipientId()
	{
		return recipientId;
	}

	public void setRecipientId(Long recipientId)
	{
		this.recipientId = recipientId;
	}

	/**
	 * @return внутреннее представление ошибки
	 * @deprecated используем getErrorInfo
	 */
	@Deprecated
	public String getErrorDesc()
	{
		return errorDesc;
	}

	/**
	 * @return информация об ошибке
	 */
	public ErrorInfo getErrorInfo()
	{
		return ErrorInfo.buildErrorInfo(this.errorDesc);
	}

	/**
	 * Установить внутреннее описание ошибки
	 * @param errorDesc внутреннее описание
	 * @deprecated используем setErrorInfo
	 */
	@Deprecated
	public void setErrorDesc(String errorDesc)
	{
		this.errorDesc = errorDesc;
	}

	/**
	 * Установить инфомацию об ошибке
	 * @param errorInfo инфомация об ошибке
	 */
	public void setErrorInfo(ErrorInfo errorInfo)
	{
		this.errorDesc = ErrorInfo.buildErrorDesc(errorInfo);
	}

	public InvoiceSubscriptionErrorType getErrorType()
	{
		return errorType;
	}

	public void setErrorType(InvoiceSubscriptionErrorType errorType)
	{
		this.errorType = errorType;
	}

	public Long getNumberOfNotPaidInvoices()
	{
		return numberOfNotPaidInvoices;
	}

	public void setNumberOfNotPaidInvoices(Long numberOfNotPaidInvoices)
	{
		this.numberOfNotPaidInvoices = numberOfNotPaidInvoices;
	}

	public Long getNumberOfDelayedInvoices()
	{
		return numberOfDelayedInvoices;
	}

	public void setNumberOfDelayedInvoices(Long numberOfDelayedInvoices)
	{
		this.numberOfDelayedInvoices = numberOfDelayedInvoices;
	}

	public Calendar getDelayDate()
	{
		return delayDate;
	}

	public void setDelayDate(Calendar date)
	{
		this.delayDate = date;
	}

	public int compareTo(Object o)
	{
		if (o.getClass() != InvoiceSubscription.class)
		{
			return 1;
		}
		InvoiceSubscription second = (InvoiceSubscription) o;
		if (this.getId() > second.getId())
			return 1;
		else if (this.getId() < second.getId())
			return -1;
		return 0;
	}

	/**
	 * @return реквизиты ввиде списка
	 */
	public List<Field> getRequisitesAsList()
	{
		try
		{
			return RequisitesHelper.deserialize(getRequisites());
		}
		catch (DocumentException e)
		{
			log.error("Ошибка приведения реквизитов к списку.", e);
			return Collections.emptyList();
		}
	}

	public Calendar getPayDate()
	{
		return payDate;
	}

	public void setPayDate(Calendar payDate)
	{
		this.payDate = payDate;
	}

	public String getBillingCode()
	{
		return billingCode;
	}

	public void setBillingCode(String billingCode)
	{
		this.billingCode = billingCode;
	}

	public String getDocumentStatus()
	{
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus)
	{
		this.documentStatus = documentStatus;
	}

	public CreationType getCreationType()
	{
		return creationType;
	}

	public void setCreationType(CreationType creationType)
	{
		this.creationType = creationType;
	}

	public ConfirmStrategyType getConfirmStrategyType()
	{
		return confirmStrategyType;
	}

	public void setConfirmStrategyType(ConfirmStrategyType confirmStrategyType)
	{
		this.confirmStrategyType = confirmStrategyType;
	}

	public InvoiceSubscriptionState getPreviousState() throws BusinessException
	{
		return getPreviousState(baseState);
	}

	public InvoiceSubscriptionState getNextState() throws BusinessException
	{
		return getNextState(baseState);
	}

	public String getBaseState()
	{
		return baseState;
	}

	public void setBaseState(String baseState)
	{
		this.baseState = baseState;
	}

	static InvoiceSubscriptionState getPreviousState(String baseState) throws BusinessException
	{
		return getCalculatedState(baseState, PREVIOUS_STATE_POS);
	}

	static InvoiceSubscriptionState getNextState(String baseState) throws BusinessException
	{
		return getCalculatedState(baseState, NEXT_STATE_POS);
	}

	private static InvoiceSubscriptionState getCalculatedState(String baseState, int position) throws BusinessException
	{
		String[] states = baseState.split(com.rssl.phizic.common.types.basket.Constants.STATE_DELIMITER);

		if (states.length == 1)
		{
			return null;
		}

		return InvoiceSubscriptionState.valueOf(states[position]);
	}

	/**
	 * @return внешний идентификатор автоплатежа, по которому создана подписка
	 */
	public String getAutoSubExternalId()
	{
		return autoSubExternalId;
	}

	/**
	 * Установить внешний идентикатор автоплатежа, по которому создана подписка
	 * @param autoSubExternalId внешний идентификатор автоплатежа
	 */
	public void setAutoSubExternalId(String autoSubExternalId)
	{
		this.autoSubExternalId = autoSubExternalId;
	}
}
