package com.rssl.phizic.business.pfr;

/**
 * ������� �� ���
 * @author Dorzhinov
 * @ created 03.02.2011
 * @ $Author $
 * @ $Revision $
 */
public class PFRStatement
{
	private Long id;              //id �������
	private Long claimId;         //id �������-������
	private String statementXml;  //xml-�������� �������
	private boolean valid;        //������� ���������� xml-���������

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getClaimId()
	{
		return claimId;
	}

	public void setClaimId(Long claimId)
	{
		this.claimId = claimId;
	}

	public String getStatementXml()
	{
		return statementXml;
	}

	public void setStatementXml(String statementXml)
	{
		this.statementXml = statementXml;
	}

	public boolean isValid()
	{
		return valid;
	}

	public void setValid(boolean valid)
	{
		this.valid = valid;
	}
}
