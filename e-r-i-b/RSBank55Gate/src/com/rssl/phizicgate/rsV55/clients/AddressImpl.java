package com.rssl.phizicgate.rsV55.clients;

import com.rssl.phizic.gate.clients.Address;

/**
 * @author Danilov
 * @ created 12.12.2007
 * @ $Author$
 * @ $Revision$
 */
public class AddressImpl implements Address
{
	private static final String COMMA = ", ";
	private static final String PROVINCE = " ���. ";
	private static final String DISTRICT = " �-�. ";
	private static final String CITY = "�. ";
	private static final String STREET = "��. ";
	private static final String HOUSE = "�. ";
	private static final String BUILDING = "����. ";
	private static final String FLAT = "��. ";

	private String id;
	private String postalCode;
	private String province;
	private String district;
	private String city;
	private String street;
	private String house;
	private String building;
	private String flat;
	private String homePhone;
	private String workPhone;
	private String mobilePhone;
	private String mobileOperator;
	private String unparseableAddress;

	/**
    * �������� ������
    */
   public String getPostalCode() {
      return postalCode;
   }

   /**
    * �������
    */
   public String getProvince() {
      return province;
   }

   /**
    * �����
    */
   public String getDistrict() {
      return district;
   }

   /**
    * �����
    */
   public String getCity() {
      return city;
   }

	/**
    * ���������� �����
    */
	public String getSettlement()
	{
		return null;
	}

   /**
    * �����
    */
   public String getStreet() {
      return street;
   }

   /**
    * ����� ����
    */
   public String getHouse() {
      return house;
   }

   /**
    * ����� �������
    */
   public String getBuilding() {
      return building;
   }

   /**
    * ����� ��������
    */
   public String getFlat() {
      return flat;
   }

   /**
    * �������� �������
    */
   public String getHomePhone() {
      return homePhone;
   }

   /**
    * ������� �������
    */
   public String getWorkPhone() {
      return workPhone;
   }

   /**
    * ��������� �������
    */
   public String getMobilePhone() {
      return mobilePhone;
   }

	public String getMobileOperator()
	{
		return mobileOperator;
	}

	public void setMobileOperator(String mobileOperator)
	{
		this.mobileOperator = mobileOperator;
	}

	public void setPostalCode(String postalCode)
	{
		this.postalCode = postalCode;
	}

	public void setProvince(String province)
	{
		this.province = province;
	}

	public void setDistrict(String district)
	{
		this.district = district;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public void setStreet(String street)
	{
		this.street = street;
	}

	public void setHouse(String house)
	{
		this.house = house;
	}

	public void setBuilding(String building)
	{
		this.building = building;
	}

	public void setFlat(String flat)
	{
		this.flat = flat;
	}

	public void setHomePhone(String homePhone)
	{
		this.homePhone = homePhone;
	}

	public void setWorkPhone(String workPhone)
	{
		this.workPhone = workPhone;
	}

	public void setMobilePhone(String mobilePhone)
	{
		this.mobilePhone = mobilePhone;
	}

	public String getUnparseableAddress()
	{
		return unparseableAddress;
	}

	public void setUnparseableAddress(String unparseableAddress)
	{
		this.unparseableAddress = unparseableAddress;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String toString()
    {
	    StringBuilder sb = new StringBuilder();
        if (getPostalCode() != null)
	        sb.append(getPostalCode()).append(COMMA);
        if (getProvince() != null)
	        sb.append(getProvince()).append(PROVINCE).append(COMMA);
        if (getDistrict() != null)
	        sb.append(DISTRICT).append(getDistrict()).append(COMMA);
        if (getCity() != null)
	        sb.append(CITY).append(getCity()).append(COMMA);
	    if (getStreet() != null)
	        sb.append(STREET).append(getStreet()).append(COMMA);
        if (getHouse() != null)
	        sb.append(HOUSE).append(getHouse()).append(COMMA);
	    if (getBuilding() != null)
	        sb.append(BUILDING).append(getBuilding()).append(COMMA);
        if (getFlat() != null)
	        sb.append(FLAT).append(getFlat());
        return sb.toString();
    }
}
