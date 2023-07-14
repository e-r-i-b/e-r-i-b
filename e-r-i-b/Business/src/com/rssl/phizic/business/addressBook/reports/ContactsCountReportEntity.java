package com.rssl.phizic.business.addressBook.reports;

/**
 * @author akrenev
 * @ created 11.06.2014
 * @ $Author$
 * @ $Revision$
 *
 * �������� ������ "�� ���������� ���������"
 */

public class ContactsCountReportEntity extends ReportEntityBase
{
	private Long count;

	/**
	 * @return ���������� ���������
	 */
	public Long getCount()
	{
		return count;
	}

	/**
	 * ������ ���������� ���������
	 * @param count ���������� ���������
	 */
	public void setCount(Long count)
	{
		this.count = count;
	}
}
