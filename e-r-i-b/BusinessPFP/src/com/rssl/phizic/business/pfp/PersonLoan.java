package com.rssl.phizic.business.pfp;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.business.dictionaries.pfp.products.loan.LoanKindProduct;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;
import java.math.BigDecimal;

/**
 * @author mihaylov
 * @ created 22.07.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ �������
 */
public class PersonLoan
{
	private Long id;            //������������� �������
	private Long dictionaryLoan;//������������� ������� �� �����������
	private String name;        //��� �������
	private String comment;     //����������� � �������
	private Money amount;       //����� �������
	private Calendar startDate; //���� ���������� �������
	private Calendar endDate;   //���� ��������� �������� �� �������
	private BigDecimal rate;    //���������� ������
	private Long imageId;

	/**
	 * @return ������������� �������
	 */
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ������������� ������� �� �����������
	 */
	public Long getDictionaryLoan()
	{
		return dictionaryLoan;
	}

	public void setDictionaryLoan(Long dictionaryLoan)
	{
		this.dictionaryLoan = dictionaryLoan;
	}

	public void setDictionaryLoan(LoanKindProduct dictionaryLoan)
	{
		this.dictionaryLoan = dictionaryLoan.getId();
		this.name = dictionaryLoan.getName();
		this.imageId = dictionaryLoan.getImageId();
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public Money getAmount()
	{
		return amount;
	}

	public void setAmount(Money amount)
	{
		this.amount = amount;
	}

	/**
	 * @return ���� ���������� �������
	 */
	public Calendar getStartDate()
	{
		return startDate;
	}

	/**
	 * @param startDate ���� ���������� �������
	 */
	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	public Calendar getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Calendar endDate)
	{
		this.endDate = endDate;
	}

	public BigDecimal getRate()
	{
		return rate;
	}

	public void setRate(BigDecimal rate)
	{
		this.rate = rate;
	}

	/**
	 * @return ���� ������� � ���������
	 */
	public int getQuarterDuration()
	{
		return DateHelper.getDiffInQuarters(startDate,endDate);
	}

	public Long getImageId()
	{
		return imageId;
	}

	public void setImageId(Long imageId)
	{
		this.imageId = imageId;
	}
}
