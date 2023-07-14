package com.rssl.phizic.web.moneyBox;

import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.web.autopayments.PersonFormBase;

/**
 * @author osminin
 * @ created 14.10.14
 * @ $Author$
 * @ $Revision$
 * Форма для отображения списка копилок в АРМ сотрудника
 */
public class ListMoneyBoxForm extends PersonFormBase<AutoSubscriptionLink>
{
	private Long id;
	private String buttonName;

	/**
	 * @return идентификатор выбранной копилки
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id идентификатор выбранной копилки
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return наименование веб-метода, который будет вызываться после подтверждения картой
	 */
	public String getButtonName()
	{
		return buttonName;
	}

	/**
	 * @param buttonName наименование веб-метода, который будет вызываться после подтверждения картой
	 */
	public void setButtonName(String buttonName)
	{
		this.buttonName = buttonName;
	}
}
