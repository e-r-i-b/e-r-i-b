/**
 * FullAddress_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Информация о полном адресе
 */
public class FullAddress_Type  implements java.io.Serializable {
    /* Тип адреса */
    private java.lang.String addrType;

    /* Ненормализованный адрес */
    private java.lang.String addr3;

    /* Страна */
    private java.lang.String country;

    /* Почтовый индекс */
    private java.lang.String postalCode;

    /* Регион(республика,область,край) */
    private java.lang.String region;

    /* Район */
    private java.lang.String area;

    /* Город */
    private java.lang.String city;

    /* Населенный пункт */
    private java.lang.String settlement;

    /* Улица */
    private java.lang.String addr1;

    /* Дом (владение) */
    private java.lang.String houseNum;

    /* Корпус */
    private java.lang.String houseExt;

    /* Строение */
    private java.lang.String unit;

    /* Квартира/офис */
    private java.lang.String unitNum;

    public FullAddress_Type() {
    }

    public FullAddress_Type(
           java.lang.String addrType,
           java.lang.String addr3,
           java.lang.String country,
           java.lang.String postalCode,
           java.lang.String region,
           java.lang.String area,
           java.lang.String city,
           java.lang.String settlement,
           java.lang.String addr1,
           java.lang.String houseNum,
           java.lang.String houseExt,
           java.lang.String unit,
           java.lang.String unitNum) {
           this.addrType = addrType;
           this.addr3 = addr3;
           this.country = country;
           this.postalCode = postalCode;
           this.region = region;
           this.area = area;
           this.city = city;
           this.settlement = settlement;
           this.addr1 = addr1;
           this.houseNum = houseNum;
           this.houseExt = houseExt;
           this.unit = unit;
           this.unitNum = unitNum;
    }


    /**
     * Gets the addrType value for this FullAddress_Type.
     * 
     * @return addrType   * Тип адреса
     */
    public java.lang.String getAddrType() {
        return addrType;
    }


    /**
     * Sets the addrType value for this FullAddress_Type.
     * 
     * @param addrType   * Тип адреса
     */
    public void setAddrType(java.lang.String addrType) {
        this.addrType = addrType;
    }


    /**
     * Gets the addr3 value for this FullAddress_Type.
     * 
     * @return addr3   * Ненормализованный адрес
     */
    public java.lang.String getAddr3() {
        return addr3;
    }


    /**
     * Sets the addr3 value for this FullAddress_Type.
     * 
     * @param addr3   * Ненормализованный адрес
     */
    public void setAddr3(java.lang.String addr3) {
        this.addr3 = addr3;
    }


    /**
     * Gets the country value for this FullAddress_Type.
     * 
     * @return country   * Страна
     */
    public java.lang.String getCountry() {
        return country;
    }


    /**
     * Sets the country value for this FullAddress_Type.
     * 
     * @param country   * Страна
     */
    public void setCountry(java.lang.String country) {
        this.country = country;
    }


    /**
     * Gets the postalCode value for this FullAddress_Type.
     * 
     * @return postalCode   * Почтовый индекс
     */
    public java.lang.String getPostalCode() {
        return postalCode;
    }


    /**
     * Sets the postalCode value for this FullAddress_Type.
     * 
     * @param postalCode   * Почтовый индекс
     */
    public void setPostalCode(java.lang.String postalCode) {
        this.postalCode = postalCode;
    }


    /**
     * Gets the region value for this FullAddress_Type.
     * 
     * @return region   * Регион(республика,область,край)
     */
    public java.lang.String getRegion() {
        return region;
    }


    /**
     * Sets the region value for this FullAddress_Type.
     * 
     * @param region   * Регион(республика,область,край)
     */
    public void setRegion(java.lang.String region) {
        this.region = region;
    }


    /**
     * Gets the area value for this FullAddress_Type.
     * 
     * @return area   * Район
     */
    public java.lang.String getArea() {
        return area;
    }


    /**
     * Sets the area value for this FullAddress_Type.
     * 
     * @param area   * Район
     */
    public void setArea(java.lang.String area) {
        this.area = area;
    }


    /**
     * Gets the city value for this FullAddress_Type.
     * 
     * @return city   * Город
     */
    public java.lang.String getCity() {
        return city;
    }


    /**
     * Sets the city value for this FullAddress_Type.
     * 
     * @param city   * Город
     */
    public void setCity(java.lang.String city) {
        this.city = city;
    }


    /**
     * Gets the settlement value for this FullAddress_Type.
     * 
     * @return settlement   * Населенный пункт
     */
    public java.lang.String getSettlement() {
        return settlement;
    }


    /**
     * Sets the settlement value for this FullAddress_Type.
     * 
     * @param settlement   * Населенный пункт
     */
    public void setSettlement(java.lang.String settlement) {
        this.settlement = settlement;
    }


    /**
     * Gets the addr1 value for this FullAddress_Type.
     * 
     * @return addr1   * Улица
     */
    public java.lang.String getAddr1() {
        return addr1;
    }


    /**
     * Sets the addr1 value for this FullAddress_Type.
     * 
     * @param addr1   * Улица
     */
    public void setAddr1(java.lang.String addr1) {
        this.addr1 = addr1;
    }


    /**
     * Gets the houseNum value for this FullAddress_Type.
     * 
     * @return houseNum   * Дом (владение)
     */
    public java.lang.String getHouseNum() {
        return houseNum;
    }


    /**
     * Sets the houseNum value for this FullAddress_Type.
     * 
     * @param houseNum   * Дом (владение)
     */
    public void setHouseNum(java.lang.String houseNum) {
        this.houseNum = houseNum;
    }


    /**
     * Gets the houseExt value for this FullAddress_Type.
     * 
     * @return houseExt   * Корпус
     */
    public java.lang.String getHouseExt() {
        return houseExt;
    }


    /**
     * Sets the houseExt value for this FullAddress_Type.
     * 
     * @param houseExt   * Корпус
     */
    public void setHouseExt(java.lang.String houseExt) {
        this.houseExt = houseExt;
    }


    /**
     * Gets the unit value for this FullAddress_Type.
     * 
     * @return unit   * Строение
     */
    public java.lang.String getUnit() {
        return unit;
    }


    /**
     * Sets the unit value for this FullAddress_Type.
     * 
     * @param unit   * Строение
     */
    public void setUnit(java.lang.String unit) {
        this.unit = unit;
    }


    /**
     * Gets the unitNum value for this FullAddress_Type.
     * 
     * @return unitNum   * Квартира/офис
     */
    public java.lang.String getUnitNum() {
        return unitNum;
    }


    /**
     * Sets the unitNum value for this FullAddress_Type.
     * 
     * @param unitNum   * Квартира/офис
     */
    public void setUnitNum(java.lang.String unitNum) {
        this.unitNum = unitNum;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FullAddress_Type)) return false;
        FullAddress_Type other = (FullAddress_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.addrType==null && other.getAddrType()==null) || 
             (this.addrType!=null &&
              this.addrType.equals(other.getAddrType()))) &&
            ((this.addr3==null && other.getAddr3()==null) || 
             (this.addr3!=null &&
              this.addr3.equals(other.getAddr3()))) &&
            ((this.country==null && other.getCountry()==null) || 
             (this.country!=null &&
              this.country.equals(other.getCountry()))) &&
            ((this.postalCode==null && other.getPostalCode()==null) || 
             (this.postalCode!=null &&
              this.postalCode.equals(other.getPostalCode()))) &&
            ((this.region==null && other.getRegion()==null) || 
             (this.region!=null &&
              this.region.equals(other.getRegion()))) &&
            ((this.area==null && other.getArea()==null) || 
             (this.area!=null &&
              this.area.equals(other.getArea()))) &&
            ((this.city==null && other.getCity()==null) || 
             (this.city!=null &&
              this.city.equals(other.getCity()))) &&
            ((this.settlement==null && other.getSettlement()==null) || 
             (this.settlement!=null &&
              this.settlement.equals(other.getSettlement()))) &&
            ((this.addr1==null && other.getAddr1()==null) || 
             (this.addr1!=null &&
              this.addr1.equals(other.getAddr1()))) &&
            ((this.houseNum==null && other.getHouseNum()==null) || 
             (this.houseNum!=null &&
              this.houseNum.equals(other.getHouseNum()))) &&
            ((this.houseExt==null && other.getHouseExt()==null) || 
             (this.houseExt!=null &&
              this.houseExt.equals(other.getHouseExt()))) &&
            ((this.unit==null && other.getUnit()==null) || 
             (this.unit!=null &&
              this.unit.equals(other.getUnit()))) &&
            ((this.unitNum==null && other.getUnitNum()==null) || 
             (this.unitNum!=null &&
              this.unitNum.equals(other.getUnitNum())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getAddrType() != null) {
            _hashCode += getAddrType().hashCode();
        }
        if (getAddr3() != null) {
            _hashCode += getAddr3().hashCode();
        }
        if (getCountry() != null) {
            _hashCode += getCountry().hashCode();
        }
        if (getPostalCode() != null) {
            _hashCode += getPostalCode().hashCode();
        }
        if (getRegion() != null) {
            _hashCode += getRegion().hashCode();
        }
        if (getArea() != null) {
            _hashCode += getArea().hashCode();
        }
        if (getCity() != null) {
            _hashCode += getCity().hashCode();
        }
        if (getSettlement() != null) {
            _hashCode += getSettlement().hashCode();
        }
        if (getAddr1() != null) {
            _hashCode += getAddr1().hashCode();
        }
        if (getHouseNum() != null) {
            _hashCode += getHouseNum().hashCode();
        }
        if (getHouseExt() != null) {
            _hashCode += getHouseExt().hashCode();
        }
        if (getUnit() != null) {
            _hashCode += getUnit().hashCode();
        }
        if (getUnitNum() != null) {
            _hashCode += getUnitNum().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FullAddress_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "FullAddress_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addrType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AddrType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addr3");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Addr3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("country");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Country"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("postalCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PostalCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("region");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Region"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("area");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Area"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("city");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "City"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("settlement");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Settlement"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addr1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Addr1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("houseNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "HouseNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("houseExt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "HouseExt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unit");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Unit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unitNum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "UnitNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
