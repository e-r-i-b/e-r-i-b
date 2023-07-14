package com.rssl.phizic.operations.finances;

import java.math.BigDecimal;

/**
 * @author Erkin
 * @ created 18.08.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��� � ���������� ��������� ��������, ������� �������� �� ������������ ����� ��������������
 */
public class EditCardOperationForm
{
	private String description;

	private Long categoryId;

	private BigDecimal amount;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * @return �������� ����-��������, ������� ������� ������������
	 */
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return ID ��������� ��������, ������� ������ �� ������������
	 */
	public Long getCategoryId()
	{
		return categoryId;
	}

	public void setCategoryId(Long categoryId)
	{
		this.categoryId = categoryId;
	}

	/**
	 * @return �����, ������� ��� ������������ (� ������ ����� ��������)
	 */
	public BigDecimal getAmount()
	{
		return amount;
	}

	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}
}
