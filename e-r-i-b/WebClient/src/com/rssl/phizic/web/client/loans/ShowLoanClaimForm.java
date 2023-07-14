package com.rssl.phizic.web.client.loans;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * Created by IntelliJ IDEA.
 * @author mihaylov
 * created 29.08.2008
 * @ $Author$
 * @ $Revision$
 */
public class ShowLoanClaimForm extends ActionFormBase
{
	private String firstName;       // ���
	private String surName;         // �������
    private String patrName;          // ��������
	private String number;         // ����� ������
	private BusinessDocument claim;           // ������
	private String stateMes;     // ��������� � �������
	private String guid;      // guid, ��������� � id ������

	public String getFirstName()
	{
		return firstName;
	}

	public String getSurName()
	{
		return surName;
	}

	public String getPatrName()
	{
		return patrName;
	}

	public String getNumber()
	{
		return number;
	}

	public BusinessDocument getClaim()
	{
		return claim;
	}

	public String getStateMes()
	{
		return stateMes;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public void setSurName(String surName)
	{
		this.surName = surName;
	}

	public void setPatrName(String patrName)
	{
		this.patrName = patrName;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public void setClaim(BusinessDocument claim)
	{
		this.claim = claim;
	}

	public void setStateMes(String stateMes)
	{
		this.stateMes = stateMes;
	}

	public String getGuid()
	{
		return guid;
	}

	public void setGuid(String guid)
	{
		this.guid = guid;
	}
}
