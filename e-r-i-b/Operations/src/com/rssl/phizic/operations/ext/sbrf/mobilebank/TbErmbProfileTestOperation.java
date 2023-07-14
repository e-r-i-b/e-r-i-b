package com.rssl.phizic.operations.ext.sbrf.mobilebank;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.ErmbHelper;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.StringHelper;

/**
 * User: Moshenko
 * Date: 17.07.14
 * Time: 12:22
 * Операция инкапсулирует логику проверки наличия ермб профиля в другом тербанке.
 */
public class TbErmbProfileTestOperation  extends OperationBase
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private ActivePerson person;

	public void initialize()
	{
		this.person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
	}
	/**
	 * @return Есть ли профиль ЕМРБ в другом тербанке.
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public boolean isErmbProfileInOtherTb() throws BusinessLogicException, BusinessException
	{
		return ErmbHelper.hasErmbProfileByPerson(person);
	}

	/**
	 * @return Фамилия отчество клиента, или фамилия
	 */
	public String getPersonName()
	{
		String patrName = person.getPatrName();
		if (StringHelper.isNotEmpty(patrName))
			return person.getFirstName() + " " + patrName;
		else
			return person.getFirstName();
	}

}
