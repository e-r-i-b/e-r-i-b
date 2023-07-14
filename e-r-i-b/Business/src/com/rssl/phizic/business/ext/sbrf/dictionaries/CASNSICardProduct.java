package com.rssl.phizic.business.ext.sbrf.dictionaries;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.utils.BeanHelper;

import java.util.Calendar;

/**
 * @author Mescheryakova
 * @ created 27.09.2011
 * @ $Author$
 * @ $Revision$
 *
 * ��������, ����������� ����������� �� ����������� ��� ��� �����
 */

public class CASNSICardProduct extends DictionaryRecordBase
{
	private Long id;                    // �������������
	private String name;                // �������� ���������� �������� �� �����������
	private Long productId;             // ���
	private Long productSubId;          // ������
	private Calendar stopOpenDeposit;   // ���� ����������� �������� �������
	private Currency currency;          // ������
	private Calendar lastUpdateDate;    // ��������� ���� ��������� � ��

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Long getProductId()
	{
		return productId;
	}

	public void setProductId(Long productId)
	{
		this.productId = productId;
	}

	public Long getProductSubId()
	{
		return productSubId;
	}

	public void setProductSubId(Long productSubId)
	{
		this.productSubId = productSubId;
	}

	public Calendar getStopOpenDeposit()
	{
		return stopOpenDeposit;
	}

	public void setStopOpenDeposit(Calendar stopOpenDeposit)
	{
		this.stopOpenDeposit = stopOpenDeposit;
	}

	public Currency getCurrency()
	{
		return currency;
	}

	public void setCurrency(Currency currency)
	{
		this.currency = currency;
	}

	public Calendar getLastUpdateDate()
	{
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Calendar lastUpdateDate)
	{
		this.lastUpdateDate = lastUpdateDate;
	}

	/**
	 * ���� ��� ��������� ��������: �������� � ���� productId-productSubId-��� ������
	 * @return ���� �������������
	 */
    public Comparable getSynchKey()
	{
		class CASNSICSynchKey implements Comparable<CASNSICSynchKey>
		{
			   private CASNSICSynchKey() {}

			   public  int compareTo(CASNSICSynchKey o)
			   {
				   String synchKey = this.getSynchKey();
				   if (synchKey.equals(o.getSynchKey()))
					   return 0;
				   return -1;
			   }

			   public String toString()
			   {
				   return getSynchKey();
			   }

			   private String getSynchKey()
			   {
				   return productId + "-" + productSubId + "-" + currency.getCode();
			   }
		}

		return new CASNSICSynchKey();
	}
}
