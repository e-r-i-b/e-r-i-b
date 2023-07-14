package com.rssl.phizic.web.client.certification;

import com.rssl.phizic.business.certification.CertDemand;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author Omeliyanchuk
 * @ created 21.05.2007
 * @ $Author$
 * @ $Revision$
 */

public class ConfirmCertDemandCertificationForm extends ActionFormBase
{
	private Long id;//id запроса на сертификат, для инициализации
	private ActivePerson person;//владелец запроса на сертификат
	private CertDemand demand;//сам запрос.

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public ActivePerson getPerson()
	{
		return person;
	}

	public void setPerson(ActivePerson person)
	{
		this.person = person;
	}

	public CertDemand getDemand()
	{
		return demand;
	}

	public void setDemand(CertDemand demand)
	{
		this.demand = demand;
	}
}
