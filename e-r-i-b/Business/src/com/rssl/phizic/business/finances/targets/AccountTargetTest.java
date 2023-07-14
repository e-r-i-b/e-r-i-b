package com.rssl.phizic.business.finances.targets;

import com.rssl.phizic.auth.CheckLoginTest;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.test.BusinessTestCaseBase;

import java.util.Calendar;

/**
 * “ест дл€ проверки работы сервиса с цел€ми
 * @author lepihina
 * @ created 20.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class AccountTargetTest extends BusinessTestCaseBase
{
	public void test() throws Exception
	{
		AccountTargetService targetService = new AccountTargetService();
		AccountTarget target = new AccountTarget();
		Login testLogin = CheckLoginTest.getTestLogin();

		target.setLoginId(testLogin.getId());
		target.setName(TargetType.OTHER.name());
		target.setNameComment("comment");
		target.setAmount(new Money(1028000L,0L, MoneyUtil.getNationalCurrency()));
		target.setPlannedDate(Calendar.getInstance());
		target.setAccountNum(null);

		targetService.addOrUpdate(target);
		target = targetService.findTargetById(target.getId());
		targetService.remove(target);
	}
}
