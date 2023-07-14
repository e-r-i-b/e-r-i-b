package com.rssl.phizic.gate.loanclaim.dictionary;

import javax.xml.bind.annotation.*;

/**
 * @author Nady
 * @ created 25.12.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ����������� "������ �� ��������� ��������"
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "work-on-contract")
public class WorkOnContract extends AbstractDictionaryEntry
{
	/**
	 * ��� ���������� ������ �� ������ ��������� ������� "������ ������������ �������� /�����������"
	 */
	@XmlElement(name = "orgFullNameRequired", required = true)
	private boolean orgFullNameRequired;

	/**
	 * ��� ���������� ������ �� ������ ��������� ������� "��� ����������� ������������"
	 */
	@XmlElement(name = "orgINNRequired", required = true)
	private boolean orgINNRequired;

	/**
	 *  ������� "������� ��������"
	 */
	@XmlElement(name = "privatePractice", required = true)
	private boolean privatePractice;

	/**
	 *  ������� "���������"
	 */
	@XmlElement(name = "pensioner", required = true)
	private boolean pensioner;

	////////////////////////////////////////////////////////////////////////

	public boolean isOrgFullNameRequired()
	{
		return orgFullNameRequired;
	}

	public void setOrgFullNameRequired(boolean orgFullNameRequired)
	{
		this.orgFullNameRequired = orgFullNameRequired;
	}

	public boolean isOrgINNRequired()
	{
		return orgINNRequired;
	}

	public void setOrgINNRequired(boolean orgINNRequired)
	{
		this.orgINNRequired = orgINNRequired;
	}

	public boolean isPrivatePractice()
	{
		return privatePractice;
	}

	public void setPrivatePractice(boolean privatePractice)
	{
		this.privatePractice = privatePractice;
	}

	public boolean isPensioner()
	{
		return pensioner;
	}

	public void setPensioner(boolean pensioner)
	{
		this.pensioner = pensioner;
	}
}
