package com.rssl.phizic.business.documents.templates;

import java.io.Serializable;

/**
 * Информация по возможным операциям с шаблоном документа
 *
 * @author khudyakov
 * @ created 11.11.14
 * @ $Author$
 * @ $Revision$
 */
public class ActivityInfo implements Serializable
{
	private boolean availableEdit;
	private boolean availableConfirm;
	private boolean availablePay;
	private boolean availableAutoPay;

	public ActivityInfo(boolean availableEdit, boolean availableConfirm, boolean availablePay, boolean availableAutoPay)
	{
		this.availableEdit = availableEdit;
		this.availableConfirm = availableConfirm;
		this.availablePay = availablePay;
		this.availableAutoPay = availableAutoPay;
	}

	public boolean isAvailableEdit()
	{
		return availableEdit;
	}

	public boolean isAvailableConfirm()
	{
		return availableConfirm;
	}

	public boolean isAvailablePay()
	{
		return availablePay;
	}

	public boolean isAvailableAutoPay()
	{
		return availableAutoPay;
	}
}
