package com.rssl.phizic.test.web.credit.crm;

import org.apache.struts.action.ActionForm;

/**
 * User: Moshenko
 * Date: 16.01.15
 * Time: 13:24
 * �������������� � "���������" CRM
 */
public class CRMResponseForm  extends ActionForm
{
	/**
	 * ���������� �������������� ��������� GetCampaignerInfo
	 */
	private String getCampaignerInfoRqUID;
	/**
	 * �������� GetCampaignerInfoRs
	 */
	private String getCampaignerInfoRsXML;

	public String getGetCampaignerInfoRqUID()
	{
		return getCampaignerInfoRqUID;
	}

	public void setGetCampaignerInfoRqUID(String getCampaignerInfoRqUID)
	{
		this.getCampaignerInfoRqUID = getCampaignerInfoRqUID;
	}

	public String getGetCampaignerInfoRsXML()
	{
		return getCampaignerInfoRsXML;
	}

	public void setGetCampaignerInfoRsXML(String getCampaignerInfoRsXML)
	{
		this.getCampaignerInfoRsXML = getCampaignerInfoRsXML;
	}
}
