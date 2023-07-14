package com.rssl.phizic.business.cardProduct;

import com.rssl.phizic.business.ext.sbrf.dictionaries.CASNSICardProduct;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author gulov
 * @ created 29.09.2011
 * @ $Authors$
 * @ $Revision$
 */

/**
 * �����-�������� "��������� �������"
 */
@SuppressWarnings({"JavaDoc"})
public class CardProduct
{
	private Long id;
	private String name;
	private CardProductType type = CardProductType.VIRTUAL;
	private Boolean online;
	private Calendar stopOpenDate;
	private List<CASNSICardProduct> kindOfProducts;

	/**
	 * ���������� ��� ��������
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
	 * ������������ ��������
	 */
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * ��� ��������
	 */
	public CardProductType getType()
	{
		return type;
	}

	public void setType(CardProductType type)
	{
		this.type = type;
	}

	/**
	 * ������� ���������� ��������
	 */
	public Boolean isOnline()
	{
		return online;
	}

	/**
	 * ������� ���������� ��������
	 */
	public Boolean getOnline()
	{
		return online;
	}


	public void setOnline(Boolean online)
	{
		this.online = online;
	}

	/**
	 * ������������ ���� �������� ���� ���������� ��������
	 */
	public Calendar getStopOpenDate()
	{
		return stopOpenDate;
	}

	public void setStopOpenDate(Calendar stopOpenDate)
	{
		this.stopOpenDate = stopOpenDate;
	}

	/**
	 * ������ ����� � �������� ���������
	 */
	public List<CASNSICardProduct> getKindOfProducts()
	{
		return Collections.unmodifiableList(kindOfProducts);
	}

	public void setKindOfProducts(List<CASNSICardProduct> kindOfProducts)
	{
		this.kindOfProducts = new ArrayList<CASNSICardProduct>(kindOfProducts);
	}
}
