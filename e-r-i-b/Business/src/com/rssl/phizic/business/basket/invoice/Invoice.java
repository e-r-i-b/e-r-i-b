package com.rssl.phizic.business.basket.invoice;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.basket.RequisitesHelper;
import com.rssl.phizic.common.types.basket.InvoiceStatus;
import com.rssl.phizic.gate.basket.GateInvoice;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author osminin
 * @ created 15.04.14
 * @ $Author$
 * @ $Revision$
 *
 * Сущность - счет (инвойс)
 */
public class Invoice implements GateInvoice
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private long id;
	private long loginId;
	private String autoPayId;
	private String autoPaySubscriptionId;
	private long invoiceSubscriptionId;
	private InvoiceStatus state;
	private String stateDesc;
	private BigDecimal commission;
	private Calendar execPaymentDate;
	private Calendar delayedPayDate;
	private Calendar creatingDate;
	private String nonExecReasonCode;
	private String nonExecReasonDesc;
	private String codeRecipientBs;
	private String recName;
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
	private String requisites;
	private Long paymentId;
	private String cardNumber;
	private Boolean isNew = true;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getAutoPayId()
	{
		return autoPayId;
	}

	public void setAutoPayId(String autoPayId)
	{
		this.autoPayId = autoPayId;
	}

	public InvoiceStatus getState()
	{
		return state;
	}

	public void setState(InvoiceStatus state)
	{
		this.state = state;
	}

	public String getStateDesc()
	{
		return stateDesc;
	}

	public void setStateDesc(String stateDesc)
	{
		this.stateDesc = stateDesc;
	}

	public BigDecimal getCommission()
	{
		return commission;
	}

	public void setCommission(BigDecimal commission)
	{
		this.commission = commission;
	}

	public Calendar getExecPaymentDate()
	{
		return execPaymentDate;
	}

	public void setExecPaymentDate(Calendar execPaymentDate)
	{
		this.execPaymentDate = execPaymentDate;
	}

	public String getNonExecReasonCode()
	{
		return nonExecReasonCode;
	}

	public void setNonExecReasonCode(String nonExecReasonCode)
	{
		this.nonExecReasonCode = nonExecReasonCode;
	}

	public String getNonExecReasonDesc()
	{
		return nonExecReasonDesc;
	}

	public void setNonExecReasonDesc(String nonExecReasonDesc)
	{
		this.nonExecReasonDesc = nonExecReasonDesc;
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

	public String getAutoPaySubscriptionId()
	{
		return autoPaySubscriptionId;
	}

	public void setAutoPaySubscriptionId(String autoPaySubscriptionId)
	{
		this.autoPaySubscriptionId = autoPaySubscriptionId;
	}

	public Calendar getDelayedPayDate()
	{
		return delayedPayDate;
	}

	public void setDelayedPayDate(Calendar delayedPayDate)
	{
		this.delayedPayDate = delayedPayDate;
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
			return Collections.EMPTY_LIST;
		}
	}

	public long getLoginId()
	{
		return loginId;
	}

	public void setLoginId(long loginId)
	{
		this.loginId = loginId;
	}

	public long getInvoiceSubscriptionId()
	{
		return invoiceSubscriptionId;
	}

	public void setInvoiceSubscriptionId(long invoiceSubscriptionId)
	{
		this.invoiceSubscriptionId = invoiceSubscriptionId;
	}

	public Calendar getCreatingDate()
	{
		return creatingDate;
	}

	public void setCreatingDate(Calendar creatingDate)
	{
		this.creatingDate = creatingDate;
	}
	public Long getPaymentId()
	{
		return paymentId;
	}

	public void setPaymentId(Long paymentId)
	{
		this.paymentId = paymentId;
	}

	public String getCardNumber()
	{
		return cardNumber;
	}

	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	public Boolean getIsNew()
	{
		return isNew;
	}

	public void setIsNew(Boolean aNew)
	{
		isNew = aNew;
	}
}
