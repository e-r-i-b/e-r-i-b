/**
 * ItemType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.shopclient.generated;

public class ItemType  implements java.io.Serializable {
    private java.lang.String itemDesc;

    private java.lang.String name;

    private java.math.BigDecimal price;

    private com.rssl.phizicgate.shopclient.generated.CurrencyType priceCur;

    private java.lang.Long count;

    public ItemType() {
    }

    public ItemType(
           java.lang.String itemDesc,
           java.lang.String name,
           java.math.BigDecimal price,
           com.rssl.phizicgate.shopclient.generated.CurrencyType priceCur,
           java.lang.Long count) {
           this.itemDesc = itemDesc;
           this.name = name;
           this.price = price;
           this.priceCur = priceCur;
           this.count = count;
    }


    /**
     * Gets the itemDesc value for this ItemType.
     * 
     * @return itemDesc
     */
    public java.lang.String getItemDesc() {
        return itemDesc;
    }


    /**
     * Sets the itemDesc value for this ItemType.
     * 
     * @param itemDesc
     */
    public void setItemDesc(java.lang.String itemDesc) {
        this.itemDesc = itemDesc;
    }


    /**
     * Gets the name value for this ItemType.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this ItemType.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the price value for this ItemType.
     * 
     * @return price
     */
    public java.math.BigDecimal getPrice() {
        return price;
    }


    /**
     * Sets the price value for this ItemType.
     * 
     * @param price
     */
    public void setPrice(java.math.BigDecimal price) {
        this.price = price;
    }


    /**
     * Gets the priceCur value for this ItemType.
     * 
     * @return priceCur
     */
    public com.rssl.phizicgate.shopclient.generated.CurrencyType getPriceCur() {
        return priceCur;
    }


    /**
     * Sets the priceCur value for this ItemType.
     * 
     * @param priceCur
     */
    public void setPriceCur(com.rssl.phizicgate.shopclient.generated.CurrencyType priceCur) {
        this.priceCur = priceCur;
    }


    /**
     * Gets the count value for this ItemType.
     * 
     * @return count
     */
    public java.lang.Long getCount() {
        return count;
    }


    /**
     * Sets the count value for this ItemType.
     * 
     * @param count
     */
    public void setCount(java.lang.Long count) {
        this.count = count;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ItemType)) return false;
        ItemType other = (ItemType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.itemDesc==null && other.getItemDesc()==null) || 
             (this.itemDesc!=null &&
              this.itemDesc.equals(other.getItemDesc()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.price==null && other.getPrice()==null) || 
             (this.price!=null &&
              this.price.equals(other.getPrice()))) &&
            ((this.priceCur==null && other.getPriceCur()==null) || 
             (this.priceCur!=null &&
              this.priceCur.equals(other.getPriceCur()))) &&
            ((this.count==null && other.getCount()==null) || 
             (this.count!=null &&
              this.count.equals(other.getCount())));
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
        if (getItemDesc() != null) {
            _hashCode += getItemDesc().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getPrice() != null) {
            _hashCode += getPrice().hashCode();
        }
        if (getPriceCur() != null) {
            _hashCode += getPriceCur().hashCode();
        }
        if (getCount() != null) {
            _hashCode += getCount().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ItemType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "ItemType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("itemDesc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "ItemDesc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "String255Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "Name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "String255Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("price");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "Price"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "DecimalType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("priceCur");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "PriceCur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "CurrencyType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("count");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "Count"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shop.webgate.test.phizic.rssl.com", "LongType"));
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
