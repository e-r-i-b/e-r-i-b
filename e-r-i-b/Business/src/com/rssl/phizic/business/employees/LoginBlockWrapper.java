package com.rssl.phizic.business.employees;

import com.rssl.phizic.auth.BlockingReasonType;
import com.rssl.phizic.auth.LoginBlock;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 15.10.13
 * @ $Author$
 * @ $Revision$
 *
 * обертка блокировки логина
 */

public class LoginBlockWrapper implements com.rssl.phizic.gate.login.LoginBlock
{
	private final LoginBlock block;

	/**
	 * конструктор
	 */
	public LoginBlockWrapper()
	{
		block = new LoginBlock();
	}

	/**
	 * конструктор
	 * @param block блокировка
	 */
	public LoginBlockWrapper(LoginBlock block)
	{
		this.block = block;
	}

	public void setReasonType(String reasonType)
	{
		block.setReasonType(BlockingReasonType.valueOf(reasonType));
	}

	public String getReasonType()
	{
		return block.getReasonType().name();
	}

	public void setReasonDescription(String reasonDescription)
	{
		block.setReasonDescription(reasonDescription);
	}

	/**
	 * @return причина блокировки
	 */
	public String getReasonDescription()
	{
		return block.getReasonDescription();
	}

	public void setBlockedFrom(Calendar blockedFrom)
	{
		block.setBlockedFrom(DateHelper.toDate(blockedFrom));
	}

	public Calendar getBlockedFrom()
	{
		return DateHelper.toCalendar(block.getBlockedFrom());
	}

	public void setBlockedUntil(Calendar blockedUntil)
	{
		block.setBlockedUntil(DateHelper.toDate(blockedUntil));
	}

	public Calendar getBlockedUntil()
	{
		return DateHelper.toCalendar(block.getBlockedUntil());
	}
}
