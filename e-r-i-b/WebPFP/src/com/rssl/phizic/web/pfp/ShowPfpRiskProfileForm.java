package com.rssl.phizic.web.pfp;

import com.rssl.phizic.business.dictionaries.pfp.riskProfile.RiskProfile;
import com.rssl.phizic.business.pfp.risk.profile.PersonRiskProfile;
import com.rssl.phizic.common.types.Money;

/**
 * @author mihaylov
 * @ created 18.03.2012
 * @ $Author$
 * @ $Revision$
 */

public class ShowPfpRiskProfileForm extends EditPersonalFinanceProfileForm
{
	private RiskProfile riskProfile;
	private PersonRiskProfile personRiskProfile;
	private Money incomeMoney;      // ����������� �����
	private Money outcomeMoney;     // ����������� �������
	private Money freeMoney;        // ���������� ��������� �������
	private Boolean needBeCareful; // ���������� �� �������� ��������� � �������������� �� ������������

	public RiskProfile getRiskProfile()
	{
		return riskProfile;
	}

	public void setRiskProfile(RiskProfile riskProfile)
	{
		this.riskProfile = riskProfile;
	}

	/**
	 * @return ���� ������� �������
	 */
	public PersonRiskProfile getPersonRiskProfile()
	{
		return personRiskProfile;
	}

	/**
	 * @param personRiskProfile ���� ������� �������
	 */
	public void setPersonRiskProfile(PersonRiskProfile personRiskProfile)
	{
		this.personRiskProfile = personRiskProfile;
	}

	public Money getIncomeMoney()
	{
		return incomeMoney;
	}

	public void setIncomeMoney(Money incomeMoney)
	{
		this.incomeMoney = incomeMoney;
	}

	public Money getOutcomeMoney()
	{
		return outcomeMoney;
	}

	public void setOutcomeMoney(Money outcomeMoney)
	{
		this.outcomeMoney = outcomeMoney;
	}

	public Money getFreeMoney()
	{
		return freeMoney;
	}

	public void setFreeMoney(Money freeMoney)
	{
		this.freeMoney = freeMoney;
	}

	public Boolean getNeedBeCareful()
	{
		return needBeCareful;
	}

	public void setNeedBeCareful(Boolean needBeCareful)
	{
		this.needBeCareful = needBeCareful;
	}
}
