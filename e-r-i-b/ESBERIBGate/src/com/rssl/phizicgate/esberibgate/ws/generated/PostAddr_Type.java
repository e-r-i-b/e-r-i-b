/**
 * PostAddr_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Почтовый адрес
 */
public class PostAddr_Type  implements java.io.Serializable {
    /* Тип адреса. Предопределенные значения:
     * Seasonal – сезонный
     * Primary – основной
     * Secondary – второй
     * Business – рабочий
     * Home – домашний */
    private java.lang.String addrType;

    /* Строка адреса 1 */
    private java.lang.String addr1;

    /* Город */
    private java.lang.String city;

    /* Код района */
    private java.lang.String areaCode;

    /* Индекс */
    private java.lang.String postalCode;

    /* Страна */
    private java.lang.String country;

    /* Код страны согласно ISO */
    private java.lang.String countryCode;

    /* Код района города. */
    private java.lang.String districtCode;

    /* Район города */
    private java.lang.String district;

    /* Код населенног пункта */
    private java.lang.String placeCode;

    /* Населенный пункт */
    private java.lang.String place;

    /* Код улицы */
    private java.lang.String streetCode;

    /* Наименование улицы */
    private java.lang.String street;

    /* Номер дома */
    private java.lang.String house;

    /* Корпус */
    private java.lang.String corpus;

    /* Стоение */
    private java.lang.String building;

    /* Номер квартиры */
    private java.lang.String flat;

    public PostAddr_Type() {
    }

    public PostAddr_Type(
           java.lang.String addrType,
           java.lang.String addr1,
           java.lang.String city,
           java.lang.String areaCode,
           java.lang.String postalCode,
           java.lang.String country,
           java.lang.String countryCode,
           java.lang.String districtCode,
           java.lang.String district,
           java.lang.String placeCode,
           java.lang.String place,
           java.lang.String streetCode,
           java.lang.String street,
           java.lang.String house,
           java.lang.String corpus,
           java.lang.String building,
           java.lang.String flat) {
           this.addrType = addrType;
           this.addr1 = addr1;
           this.city = city;
           this.areaCode = areaCode;
           this.postalCode = postalCode;
           this.country = country;
           this.countryCode = countryCode;
           this.districtCode = districtCode;
           this.district = district;
           this.placeCode = placeCode;
           this.place = place;
           this.streetCode = streetCode;
           this.street = street;
           this.house = house;
           this.corpus = corpus;
           this.building = building;
           this.flat = flat;
    }


    /**
     * Gets the addrType value for this PostAddr_Type.
     * 
     * @return addrType   * Тип адреса. Предопределенные значения:
     * Seasonal – сезонный
     * Primary – основной
     * Secondary – второй
     * Business – рабочий
     * Home – домашний
     */
    public java.lang.String getAddrType() {
        return addrType;
    }


    /**
     * Sets the addrType value for this PostAddr_Type.
     * 
     * @param addrType   * Тип адреса. Предопределенные значения:
     * Seasonal – сезонный
     * Primary – основной
     * Secondary – второй
     * Business – рабочий
     * Home – домашний
     */
    public void setAddrType(java.lang.String addrType) {
        this.addrType = addrType;
    }


    /**
     * Gets the addr1 value for this PostAddr_Type.
     * 
     * @return addr1   * Строка адреса 1
     */
    public java.lang.String getAddr1() {
        return addr1;
    }


    /**
     * Sets the addr1 value for this PostAddr_Type.
     * 
     * @param addr1   * Строка адреса 1
     */
    public void setAddr1(java.lang.String addr1) {
        this.addr1 = addr1;
    }


    /**
     * Gets the city value for this PostAddr_Type.
     * 
     * @return city   * Город
     */
    public java.lang.String getCity() {
        return city;
    }


    /**
     * Sets the city value for this PostAddr_Type.
     * 
     * @param city   * Город
     */
    public void setCity(java.lang.String city) {
        this.city = city;
    }


    /**
     * Gets the areaCode value for this PostAddr_Type.
     * 
     * @return areaCode   * Код района
     */
    public java.lang.String getAreaCode() {
        return areaCode;
    }


    /**
     * Sets the areaCode value for this PostAddr_Type.
     * 
     * @param areaCode   * Код района
     */
    public void setAreaCode(java.lang.String areaCode) {
        this.areaCode = areaCode;
    }


    /**
     * Gets the postalCode value for this PostAddr_Type.
     * 
     * @return postalCode   * Индекс
     */
    public java.lang.String getPostalCode() {
        return postalCode;
    }


    /**
     * Sets the postalCode value for this PostAddr_Type.
     * 
     * @param postalCode   * Индекс
     */
    public void setPostalCode(java.lang.String postalCode) {
        this.postalCode = postalCode;
    }


    /**
     * Gets the country value for this PostAddr_Type.
     * 
     * @return country   * Страна
     */
    public java.lang.String getCountry() {
        return country;
    }


    /**
     * Sets the country value for this PostAddr_Type.
     * 
     * @param country   * Страна
     */
    public void setCountry(java.lang.String country) {
        this.country = country;
    }


    /**
     * Gets the countryCode value for this PostAddr_Type.
     * 
     * @return countryCode   * Код страны согласно ISO
     */
    public java.lang.String getCountryCode() {
        return countryCode;
    }


    /**
     * Sets the countryCode value for this PostAddr_Type.
     * 
     * @param countryCode   * Код страны согласно ISO
     */
    public void setCountryCode(java.lang.String countryCode) {
        this.countryCode = countryCode;
    }


    /**
     * Gets the districtCode value for this PostAddr_Type.
     * 
     * @return districtCode   * Код района города.
     */
    public java.lang.String getDistrictCode() {
        return districtCode;
    }


    /**
     * Sets the districtCode value for this PostAddr_Type.
     * 
     * @param districtCode   * Код района города.
     */
    public void setDistrictCode(java.lang.String districtCode) {
        this.districtCode = districtCode;
    }


    /**
     * Gets the district value for this PostAddr_Type.
     * 
     * @return district   * Район города
     */
    public java.lang.String getDistrict() {
        return district;
    }


    /**
     * Sets the district value for this PostAddr_Type.
     * 
     * @param district   * Район города
     */
    public void setDistrict(java.lang.String district) {
        this.district = district;
    }


    /**
     * Gets the placeCode value for this PostAddr_Type.
     * 
     * @return placeCode   * Код населенног пункта
     */
    public java.lang.String getPlaceCode() {
        return placeCode;
    }


    /**
     * Sets the placeCode value for this PostAddr_Type.
     * 
     * @param placeCode   * Код населенног пункта
     */
    public void setPlaceCode(java.lang.String placeCode) {
        this.placeCode = placeCode;
    }


    /**
     * Gets the place value for this PostAddr_Type.
     * 
     * @return place   * Населенный пункт
     */
    public java.lang.String getPlace() {
        return place;
    }


    /**
     * Sets the place value for this PostAddr_Type.
     * 
     * @param place   * Населенный пункт
     */
    public void setPlace(java.lang.String place) {
        this.place = place;
    }


    /**
     * Gets the streetCode value for this PostAddr_Type.
     * 
     * @return streetCode   * Код улицы
     */
    public java.lang.String getStreetCode() {
        return streetCode;
    }


    /**
     * Sets the streetCode value for this PostAddr_Type.
     * 
     * @param streetCode   * Код улицы
     */
    public void setStreetCode(java.lang.String streetCode) {
        this.streetCode = streetCode;
    }


    /**
     * Gets the street value for this PostAddr_Type.
     * 
     * @return street   * Наименование улицы
     */
    public java.lang.String getStreet() {
        return street;
    }


    /**
     * Sets the street value for this PostAddr_Type.
     * 
     * @param street   * Наименование улицы
     */
    public void setStreet(java.lang.String street) {
        this.street = street;
    }


    /**
     * Gets the house value for this PostAddr_Type.
     * 
     * @return house   * Номер дома
     */
    public java.lang.String getHouse() {
        return house;
    }


    /**
     * Sets the house value for this PostAddr_Type.
     * 
     * @param house   * Номер дома
     */
    public void setHouse(java.lang.String house) {
        this.house = house;
    }


    /**
     * Gets the corpus value for this PostAddr_Type.
     * 
     * @return corpus   * Корпус
     */
    public java.lang.String getCorpus() {
        return corpus;
    }


    /**
     * Sets the corpus value for this PostAddr_Type.
     * 
     * @param corpus   * Корпус
     */
    public void setCorpus(java.lang.String corpus) {
        this.corpus = corpus;
    }


    /**
     * Gets the building value for this PostAddr_Type.
     * 
     * @return building   * Стоение
     */
    public java.lang.String getBuilding() {
        return building;
    }


    /**
     * Sets the building value for this PostAddr_Type.
     * 
     * @param building   * Стоение
     */
    public void setBuilding(java.lang.String building) {
        this.building = building;
    }


    /**
     * Gets the flat value for this PostAddr_Type.
     * 
     * @return flat   * Номер квартиры
     */
    public java.lang.String getFlat() {
        return flat;
    }


    /**
     * Sets the flat value for this PostAddr_Type.
     * 
     * @param flat   * Номер квартиры
     */
    public void setFlat(java.lang.String flat) {
        this.flat = flat;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PostAddr_Type)) return false;
        PostAddr_Type other = (PostAddr_Type) obj;
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
            ((this.addr1==null && other.getAddr1()==null) || 
             (this.addr1!=null &&
              this.addr1.equals(other.getAddr1()))) &&
            ((this.city==null && other.getCity()==null) || 
             (this.city!=null &&
              this.city.equals(other.getCity()))) &&
            ((this.areaCode==null && other.getAreaCode()==null) || 
             (this.areaCode!=null &&
              this.areaCode.equals(other.getAreaCode()))) &&
            ((this.postalCode==null && other.getPostalCode()==null) || 
             (this.postalCode!=null &&
              this.postalCode.equals(other.getPostalCode()))) &&
            ((this.country==null && other.getCountry()==null) || 
             (this.country!=null &&
              this.country.equals(other.getCountry()))) &&
            ((this.countryCode==null && other.getCountryCode()==null) || 
             (this.countryCode!=null &&
              this.countryCode.equals(other.getCountryCode()))) &&
            ((this.districtCode==null && other.getDistrictCode()==null) || 
             (this.districtCode!=null &&
              this.districtCode.equals(other.getDistrictCode()))) &&
            ((this.district==null && other.getDistrict()==null) || 
             (this.district!=null &&
              this.district.equals(other.getDistrict()))) &&
            ((this.placeCode==null && other.getPlaceCode()==null) || 
             (this.placeCode!=null &&
              this.placeCode.equals(other.getPlaceCode()))) &&
            ((this.place==null && other.getPlace()==null) || 
             (this.place!=null &&
              this.place.equals(other.getPlace()))) &&
            ((this.streetCode==null && other.getStreetCode()==null) || 
             (this.streetCode!=null &&
              this.streetCode.equals(other.getStreetCode()))) &&
            ((this.street==null && other.getStreet()==null) || 
             (this.street!=null &&
              this.street.equals(other.getStreet()))) &&
            ((this.house==null && other.getHouse()==null) || 
             (this.house!=null &&
              this.house.equals(other.getHouse()))) &&
            ((this.corpus==null && other.getCorpus()==null) || 
             (this.corpus!=null &&
              this.corpus.equals(other.getCorpus()))) &&
            ((this.building==null && other.getBuilding()==null) || 
             (this.building!=null &&
              this.building.equals(other.getBuilding()))) &&
            ((this.flat==null && other.getFlat()==null) || 
             (this.flat!=null &&
              this.flat.equals(other.getFlat())));
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
        if (getAddr1() != null) {
            _hashCode += getAddr1().hashCode();
        }
        if (getCity() != null) {
            _hashCode += getCity().hashCode();
        }
        if (getAreaCode() != null) {
            _hashCode += getAreaCode().hashCode();
        }
        if (getPostalCode() != null) {
            _hashCode += getPostalCode().hashCode();
        }
        if (getCountry() != null) {
            _hashCode += getCountry().hashCode();
        }
        if (getCountryCode() != null) {
            _hashCode += getCountryCode().hashCode();
        }
        if (getDistrictCode() != null) {
            _hashCode += getDistrictCode().hashCode();
        }
        if (getDistrict() != null) {
            _hashCode += getDistrict().hashCode();
        }
        if (getPlaceCode() != null) {
            _hashCode += getPlaceCode().hashCode();
        }
        if (getPlace() != null) {
            _hashCode += getPlace().hashCode();
        }
        if (getStreetCode() != null) {
            _hashCode += getStreetCode().hashCode();
        }
        if (getStreet() != null) {
            _hashCode += getStreet().hashCode();
        }
        if (getHouse() != null) {
            _hashCode += getHouse().hashCode();
        }
        if (getCorpus() != null) {
            _hashCode += getCorpus().hashCode();
        }
        if (getBuilding() != null) {
            _hashCode += getBuilding().hashCode();
        }
        if (getFlat() != null) {
            _hashCode += getFlat().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PostAddr_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PostAddr_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addrType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AddrType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OpenEnum"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addr1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Addr1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("city");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "City"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("areaCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AreaCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
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
        elemField.setFieldName("country");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Country"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "NC"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("countryCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CountryCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "NC"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("districtCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DistrictCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("district");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "District"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("placeCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PlaceCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("place");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Place"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("streetCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StreetCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("street");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Street"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("house");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "House"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("corpus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Corpus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("building");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Building"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flat");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Flat"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C"));
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
