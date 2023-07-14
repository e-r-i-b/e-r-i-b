package com.rssl.phizic.gate.basket;

import com.rssl.phizic.common.types.basket.InvoiceStatus;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author vagin
 * @ created 28.04.14
 * @ $Author$
 * @ $Revision$
 * Базовый интерефейс инвойса
 */
public interface GateInvoice
{
	public long getId();

	public String getAutoPayId();

	public InvoiceStatus getState();

	public String getStateDesc();

	public BigDecimal getCommission();

	public Calendar getExecPaymentDate();

	public String getNonExecReasonCode();

	public String getNonExecReasonDesc();

	public String getCodeRecipientBs();

	public String getRecName();

	public String getCodeService();

	public String getNameService();

	public String getRecInn();

	public String getRecCorAccount();

	public String getRecKpp();

	public String getRecBic();

	public String getRecAccount();

	public String getRecNameOnBill();

	public String getRecPhoneNumber();

	public String getRecTb();

	public String getRequisites();

	/*
	Внешний идентификатор подписки.
	 */
	public String getAutoPaySubscriptionId();

	/**
	 * @return номер карты
	 */
	public String getCardNumber();
}
