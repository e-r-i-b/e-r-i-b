package com.rssl.phizic.web.client.depo;

import com.rssl.phizic.business.depo.DepoAccountOwnerFormImpl;
import com.rssl.phizic.business.resources.external.DepoAccountLink;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author lukina
 * @ created 13.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowDepoAccountOwnerFormForm extends EditFormBase
{
	private DepoAccountOwnerFormImpl form;
	private DepoAccountLink account;
	private  Long linkId;

	/**
	 * @return  ������ ���������
	 */
	public DepoAccountOwnerFormImpl getForm()
	{
		return form;
	}

	public void setForm(DepoAccountOwnerFormImpl form)
	{
		this.form = form;
	}

	/**
	 * @return id ����� ����
	 */
	public Long getLinkId()
	{
		return linkId;
	}

	public void setLinkId(Long linkId)
	{
		this.linkId = linkId;
	}

	/**
	 * @return  ���� ����
	 */
	public DepoAccountLink getAccount()
	{
		return account;
	}

	public void setAccount(DepoAccountLink account)
	{
		this.account = account;
	}
}
