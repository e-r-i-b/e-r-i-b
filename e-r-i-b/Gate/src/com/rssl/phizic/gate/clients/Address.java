/***********************************************************************
 * Module:  Address.java
 * Author:  Omeliyanchuk
 * Purpose: Defines the Class Address
 ***********************************************************************/

package com.rssl.phizic.gate.clients;

import java.io.Serializable;

/**
 * ��� ������ ��� ������������� ������
 */
public interface Address extends Serializable
{
	/**
    * @return �������� ������
    */
   String getPostalCode();

   /**
    * @return �������
    */
   String getProvince();

   /**
    * @return �����
    */
   String getDistrict();

   /**
    * @return �����
    */
   String getCity();

	/**
    * @return ��������� �����
    */
   String getSettlement();

   /**
    * @return �����
    */
   String getStreet();

   /**
    * @return ����� ����
    */
   String getHouse();

   /**
    * @return ����� �������
    */
   String getBuilding();

   /**
    * @return ����� ��������
    */
   String getFlat();

   /**
    * @return �������� �������
    */
   String getHomePhone();

   /**
    * @return ������� �������
    */
   String getWorkPhone();

   /**
    * @return ��������� �������
    */
   String getMobilePhone();

   /**
    * @return ��������� ��������
    */
   String getMobileOperator();

	/**
	 * @return ������ ����� ����� �������. � ����� ����, ��������, ������������ �� ���� ��� ������� CEDBO
	 */
	String getUnparseableAddress();
}