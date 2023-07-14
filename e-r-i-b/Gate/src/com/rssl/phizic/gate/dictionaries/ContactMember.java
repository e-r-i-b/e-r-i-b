package com.rssl.phizic.gate.dictionaries;

import java.io.Serializable;

/**
 * User: novikov_a
 * Date: 17.09.2009
 * Time: 16:37:52
 */
public class ContactMember extends DictionaryRecordBase implements Serializable
{
	private Long id;
	private String code;
	private String name;
	private String phone;
	private String address;
	private String city;
	private String countryId;
	private String regMask;
	private String comment;
	private boolean deleted;

	public Comparable getSynchKey()
	{
		return code;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	/**
	* @return ��� ������
	*/
	public String getCountryId()
	{
		return countryId;
	}
	/**
	* @param countryId ��� ������
	*/
	public void setCountryId(String countryId)
	{
		if (countryId==null)
		   this.countryId = "";
		else
		   this.countryId = countryId;
	}
	/**
	* @return ���������� ��������� ��� ��������� ��������
	*/
	public String getRegMask()
	{
		return regMask;
	}
	/**
	* ���������� ��������� ��� ��������� ��������
	* @param regMask ���������� ���������
	*/
	public void setRegMask(String regMask)
	{
		this.regMask = regMask;
	}
	/**
	* @return �������� ������� ��� ��������� ��������
	*/
	public String getComment()
	{
		return comment;
	}
	/**
	* @param comment �������� ������� ��� ��������� ��������
	*/
	public void setComment(String comment)
	{
		this.comment = comment;
	}
	/**
	* @return ������� ����� ��� ���
	*/
	public boolean isDeleted()
	{
		return deleted;
	}
	/**
	* @return ������� ����� ��� ���
	*/
	public boolean getDeleted()
	{
		return deleted;
	}
	/**
	* @param deleted ������� ����� ��� ���
	*/
	public void setDeleted(boolean deleted)
	{
		this.deleted = deleted;
	}
	/**
	* @param deleted ������� ����� ��� ���
	*/
	public void setDeleted(String deleted)
	{
		if ((deleted == null) || deleted.equals(""))
		   this.deleted = false;
		else
		   this.deleted = true;
	}
}
