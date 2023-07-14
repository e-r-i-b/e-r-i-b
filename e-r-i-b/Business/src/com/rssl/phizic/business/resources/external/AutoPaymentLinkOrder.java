package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.auth.CommonLogin;

/**
 * ����� ��� ������ ������������ � �������� �� �����������
 * @ author gorshkov
 * @ created 26.09.13
 * @ $Author$
 * @ $Revision$
 */
public class AutoPaymentLinkOrder
{
	private Long    id;
	private Long loginId;
	private String  linkId;
	private int orderInd;

	/**
	 * @return ID ������
	 */
	public Long getId()
	{
	    return id;
	}

	/**
	 * @param id ID ������
	 */
	public void setId(Long id)
	{
	    this.id = id;
	}

	/**
	 * @return ������� id �����������
	 */
	public String getLinkId()
	{
	    return linkId;
	}

	/**
	 * @param linkId ������� id �����������
	 */
	public void setLinkId(String linkId)
	{
	    this.linkId = linkId;
	}

	/**
	 * @return ����� �������
	 */
	public Long getLoginId()
	{
	    return loginId;
	}

	/**
	 * @param loginId id ������ �������
	 */
	public void setLoginId(Long loginId)
	{
	    this.loginId = loginId;
	}

	/**
	 * @return ������� ����������� �����������
	 */
	public int getOrderInd()
	{
	    return orderInd;
	}

	/**
	 * @param orderInd ������� ����������� �����������
	 */
	public void setOrderInd(int orderInd)
	{
	    this.orderInd = orderInd;
	}


}
