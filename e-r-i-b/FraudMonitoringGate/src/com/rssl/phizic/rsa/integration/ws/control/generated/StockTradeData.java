/**
 * StockTradeData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;


/**
 * This defines the compostion for stock trade information
 */
public class StockTradeData  implements java.io.Serializable {
    private java.lang.Boolean allOrNone;

    private java.lang.Integer lowerChangeLimit;

    private com.rssl.phizic.rsa.integration.ws.control.generated.Amount lowerPrice;

    private java.lang.Integer numberOfShares;

    private com.rssl.phizic.rsa.integration.ws.control.generated.PriceType priceType;

    private com.rssl.phizic.rsa.integration.ws.control.generated.StockData stockData;

    private com.rssl.phizic.rsa.integration.ws.control.generated.TermType termType;

    private com.rssl.phizic.rsa.integration.ws.control.generated.TradeType tradeType;

    private java.lang.Integer upperChangeLimit;

    private com.rssl.phizic.rsa.integration.ws.control.generated.Amount upperPrice;

    public StockTradeData() {
    }

    public StockTradeData(
           java.lang.Boolean allOrNone,
           java.lang.Integer lowerChangeLimit,
           com.rssl.phizic.rsa.integration.ws.control.generated.Amount lowerPrice,
           java.lang.Integer numberOfShares,
           com.rssl.phizic.rsa.integration.ws.control.generated.PriceType priceType,
           com.rssl.phizic.rsa.integration.ws.control.generated.StockData stockData,
           com.rssl.phizic.rsa.integration.ws.control.generated.TermType termType,
           com.rssl.phizic.rsa.integration.ws.control.generated.TradeType tradeType,
           java.lang.Integer upperChangeLimit,
           com.rssl.phizic.rsa.integration.ws.control.generated.Amount upperPrice) {
           this.allOrNone = allOrNone;
           this.lowerChangeLimit = lowerChangeLimit;
           this.lowerPrice = lowerPrice;
           this.numberOfShares = numberOfShares;
           this.priceType = priceType;
           this.stockData = stockData;
           this.termType = termType;
           this.tradeType = tradeType;
           this.upperChangeLimit = upperChangeLimit;
           this.upperPrice = upperPrice;
    }


    /**
     * Gets the allOrNone value for this StockTradeData.
     * 
     * @return allOrNone
     */
    public java.lang.Boolean getAllOrNone() {
        return allOrNone;
    }


    /**
     * Sets the allOrNone value for this StockTradeData.
     * 
     * @param allOrNone
     */
    public void setAllOrNone(java.lang.Boolean allOrNone) {
        this.allOrNone = allOrNone;
    }


    /**
     * Gets the lowerChangeLimit value for this StockTradeData.
     * 
     * @return lowerChangeLimit
     */
    public java.lang.Integer getLowerChangeLimit() {
        return lowerChangeLimit;
    }


    /**
     * Sets the lowerChangeLimit value for this StockTradeData.
     * 
     * @param lowerChangeLimit
     */
    public void setLowerChangeLimit(java.lang.Integer lowerChangeLimit) {
        this.lowerChangeLimit = lowerChangeLimit;
    }


    /**
     * Gets the lowerPrice value for this StockTradeData.
     * 
     * @return lowerPrice
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.Amount getLowerPrice() {
        return lowerPrice;
    }


    /**
     * Sets the lowerPrice value for this StockTradeData.
     * 
     * @param lowerPrice
     */
    public void setLowerPrice(com.rssl.phizic.rsa.integration.ws.control.generated.Amount lowerPrice) {
        this.lowerPrice = lowerPrice;
    }


    /**
     * Gets the numberOfShares value for this StockTradeData.
     * 
     * @return numberOfShares
     */
    public java.lang.Integer getNumberOfShares() {
        return numberOfShares;
    }


    /**
     * Sets the numberOfShares value for this StockTradeData.
     * 
     * @param numberOfShares
     */
    public void setNumberOfShares(java.lang.Integer numberOfShares) {
        this.numberOfShares = numberOfShares;
    }


    /**
     * Gets the priceType value for this StockTradeData.
     * 
     * @return priceType
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.PriceType getPriceType() {
        return priceType;
    }


    /**
     * Sets the priceType value for this StockTradeData.
     * 
     * @param priceType
     */
    public void setPriceType(com.rssl.phizic.rsa.integration.ws.control.generated.PriceType priceType) {
        this.priceType = priceType;
    }


    /**
     * Gets the stockData value for this StockTradeData.
     * 
     * @return stockData
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.StockData getStockData() {
        return stockData;
    }


    /**
     * Sets the stockData value for this StockTradeData.
     * 
     * @param stockData
     */
    public void setStockData(com.rssl.phizic.rsa.integration.ws.control.generated.StockData stockData) {
        this.stockData = stockData;
    }


    /**
     * Gets the termType value for this StockTradeData.
     * 
     * @return termType
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.TermType getTermType() {
        return termType;
    }


    /**
     * Sets the termType value for this StockTradeData.
     * 
     * @param termType
     */
    public void setTermType(com.rssl.phizic.rsa.integration.ws.control.generated.TermType termType) {
        this.termType = termType;
    }


    /**
     * Gets the tradeType value for this StockTradeData.
     * 
     * @return tradeType
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.TradeType getTradeType() {
        return tradeType;
    }


    /**
     * Sets the tradeType value for this StockTradeData.
     * 
     * @param tradeType
     */
    public void setTradeType(com.rssl.phizic.rsa.integration.ws.control.generated.TradeType tradeType) {
        this.tradeType = tradeType;
    }


    /**
     * Gets the upperChangeLimit value for this StockTradeData.
     * 
     * @return upperChangeLimit
     */
    public java.lang.Integer getUpperChangeLimit() {
        return upperChangeLimit;
    }


    /**
     * Sets the upperChangeLimit value for this StockTradeData.
     * 
     * @param upperChangeLimit
     */
    public void setUpperChangeLimit(java.lang.Integer upperChangeLimit) {
        this.upperChangeLimit = upperChangeLimit;
    }


    /**
     * Gets the upperPrice value for this StockTradeData.
     * 
     * @return upperPrice
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.Amount getUpperPrice() {
        return upperPrice;
    }


    /**
     * Sets the upperPrice value for this StockTradeData.
     * 
     * @param upperPrice
     */
    public void setUpperPrice(com.rssl.phizic.rsa.integration.ws.control.generated.Amount upperPrice) {
        this.upperPrice = upperPrice;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StockTradeData)) return false;
        StockTradeData other = (StockTradeData) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.allOrNone==null && other.getAllOrNone()==null) || 
             (this.allOrNone!=null &&
              this.allOrNone.equals(other.getAllOrNone()))) &&
            ((this.lowerChangeLimit==null && other.getLowerChangeLimit()==null) || 
             (this.lowerChangeLimit!=null &&
              this.lowerChangeLimit.equals(other.getLowerChangeLimit()))) &&
            ((this.lowerPrice==null && other.getLowerPrice()==null) || 
             (this.lowerPrice!=null &&
              this.lowerPrice.equals(other.getLowerPrice()))) &&
            ((this.numberOfShares==null && other.getNumberOfShares()==null) || 
             (this.numberOfShares!=null &&
              this.numberOfShares.equals(other.getNumberOfShares()))) &&
            ((this.priceType==null && other.getPriceType()==null) || 
             (this.priceType!=null &&
              this.priceType.equals(other.getPriceType()))) &&
            ((this.stockData==null && other.getStockData()==null) || 
             (this.stockData!=null &&
              this.stockData.equals(other.getStockData()))) &&
            ((this.termType==null && other.getTermType()==null) || 
             (this.termType!=null &&
              this.termType.equals(other.getTermType()))) &&
            ((this.tradeType==null && other.getTradeType()==null) || 
             (this.tradeType!=null &&
              this.tradeType.equals(other.getTradeType()))) &&
            ((this.upperChangeLimit==null && other.getUpperChangeLimit()==null) || 
             (this.upperChangeLimit!=null &&
              this.upperChangeLimit.equals(other.getUpperChangeLimit()))) &&
            ((this.upperPrice==null && other.getUpperPrice()==null) || 
             (this.upperPrice!=null &&
              this.upperPrice.equals(other.getUpperPrice())));
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
        if (getAllOrNone() != null) {
            _hashCode += getAllOrNone().hashCode();
        }
        if (getLowerChangeLimit() != null) {
            _hashCode += getLowerChangeLimit().hashCode();
        }
        if (getLowerPrice() != null) {
            _hashCode += getLowerPrice().hashCode();
        }
        if (getNumberOfShares() != null) {
            _hashCode += getNumberOfShares().hashCode();
        }
        if (getPriceType() != null) {
            _hashCode += getPriceType().hashCode();
        }
        if (getStockData() != null) {
            _hashCode += getStockData().hashCode();
        }
        if (getTermType() != null) {
            _hashCode += getTermType().hashCode();
        }
        if (getTradeType() != null) {
            _hashCode += getTradeType().hashCode();
        }
        if (getUpperChangeLimit() != null) {
            _hashCode += getUpperChangeLimit().hashCode();
        }
        if (getUpperPrice() != null) {
            _hashCode += getUpperPrice().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StockTradeData.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "StockTradeData"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("allOrNone");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "allOrNone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lowerChangeLimit");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "lowerChangeLimit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lowerPrice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "lowerPrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "Amount"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numberOfShares");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "numberOfShares"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("priceType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "priceType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "PriceType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stockData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "stockData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "StockData"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("termType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "termType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "TermType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tradeType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "tradeType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "TradeType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("upperChangeLimit");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "upperChangeLimit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("upperPrice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "upperPrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "Amount"));
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
