package com.rssl.phizgate.common.services.types;

import com.rssl.phizic.gate.documents.CommissionOptions;
import com.rssl.phizic.gate.documents.CommissionTarget;

/**
 * User: novikov_a
 * Date: 23.09.2009
 * Time: 12:37:51
 */
public class CommissionOptionsImpl implements CommissionOptions
{
	private CommissionTarget target;
	private String           account;

	public CommissionOptionsImpl()
	{

	}

	/**
    * ������ ���������� ��������
    */
	public CommissionTarget getTarget()
    {
		return this.target;
    }

	public void setTarget(CommissionTarget target)
    {
		this.target = target;
    }

	public void setTarget(String target)
    {
		this.target = CommissionTarget.valueOf(target);
    }
   /**
    * ���� � �������� ���������� ��������. ���������� ��� ���������� � ������ getTarget == other.
    * � ��������� ������� �������� �������� ������������.
    */
	public String getAccount()
    {
		return this.account;
    }

	public void setAccount(String account)
    {
		this.account = account;
    }
}
