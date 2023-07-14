/**
 * StockData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;


/**
 * This defines the composition for specific stock information
 */
public class StockData  implements java.io.Serializable {
    private java.lang.Boolean ETF;

    private java.lang.Boolean OTC;

    private java.lang.Boolean SP500;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount currentMarketPrice;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount last30DaysAveragePrice;

    private java.lang.Integer last30DaysAverageVolume;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount last30DaysHighPrice;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount last30DaysLowPrice;

    private java.lang.Integer percentSharesHeldByInstitution;

    private java.lang.Integer sharesFloating;

    private java.lang.Integer sharesOut;

    private java.lang.String symbol;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount todayHighPrice;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount todayLowPrice;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount todayOpenPrice;

    private java.lang.Integer todayVolume;

    public StockData() {
    }

    public StockData(
           java.lang.Boolean ETF,
           java.lang.Boolean OTC,
           java.lang.Boolean SP500,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount currentMarketPrice,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount last30DaysAveragePrice,
           java.lang.Integer last30DaysAverageVolume,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount last30DaysHighPrice,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount last30DaysLowPrice,
           java.lang.Integer percentSharesHeldByInstitution,
           java.lang.Integer sharesFloating,
           java.lang.Integer sharesOut,
           java.lang.String symbol,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount todayHighPrice,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount todayLowPrice,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount todayOpenPrice,
           java.lang.Integer todayVolume) {
           this.ETF = ETF;
           this.OTC = OTC;
           this.SP500 = SP500;
           this.currentMarketPrice = currentMarketPrice;
           this.last30DaysAveragePrice = last30DaysAveragePrice;
           this.last30DaysAverageVolume = last30DaysAverageVolume;
           this.last30DaysHighPrice = last30DaysHighPrice;
           this.last30DaysLowPrice = last30DaysLowPrice;
           this.percentSharesHeldByInstitution = percentSharesHeldByInstitution;
           this.sharesFloating = sharesFloating;
           this.sharesOut = sharesOut;
           this.symbol = symbol;
           this.todayHighPrice = todayHighPrice;
           this.todayLowPrice = todayLowPrice;
           this.todayOpenPrice = todayOpenPrice;
           this.todayVolume = todayVolume;
    }


    /**
     * Gets the ETF value for this StockData.
     * 
     * @return ETF
     */
    public java.lang.Boolean getETF() {
        return ETF;
    }


    /**
     * Sets the ETF value for this StockData.
     * 
     * @param ETF
     */
    public void setETF(java.lang.Boolean ETF) {
        this.ETF = ETF;
    }


    /**
     * Gets the OTC value for this StockData.
     * 
     * @return OTC
     */
    public java.lang.Boolean getOTC() {
        return OTC;
    }


    /**
     * Sets the OTC value for this StockData.
     * 
     * @param OTC
     */
    public void setOTC(java.lang.Boolean OTC) {
        this.OTC = OTC;
    }


    /**
     * Gets the SP500 value for this StockData.
     * 
     * @return SP500
     */
    public java.lang.Boolean getSP500() {
        return SP500;
    }


    /**
     * Sets the SP500 value for this StockData.
     * 
     * @param SP500
     */
    public void setSP500(java.lang.Boolean SP500) {
        this.SP500 = SP500;
    }


    /**
     * Gets the currentMarketPrice value for this StockData.
     * 
     * @return currentMarketPrice
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount getCurrentMarketPrice() {
        return currentMarketPrice;
    }


    /**
     * Sets the currentMarketPrice value for this StockData.
     * 
     * @param currentMarketPrice
     */
    public void setCurrentMarketPrice(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount currentMarketPrice) {
        this.currentMarketPrice = currentMarketPrice;
    }


    /**
     * Gets the last30DaysAveragePrice value for this StockData.
     * 
     * @return last30DaysAveragePrice
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount getLast30DaysAveragePrice() {
        return last30DaysAveragePrice;
    }


    /**
     * Sets the last30DaysAveragePrice value for this StockData.
     * 
     * @param last30DaysAveragePrice
     */
    public void setLast30DaysAveragePrice(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount last30DaysAveragePrice) {
        this.last30DaysAveragePrice = last30DaysAveragePrice;
    }


    /**
     * Gets the last30DaysAverageVolume value for this StockData.
     * 
     * @return last30DaysAverageVolume
     */
    public java.lang.Integer getLast30DaysAverageVolume() {
        return last30DaysAverageVolume;
    }


    /**
     * Sets the last30DaysAverageVolume value for this StockData.
     * 
     * @param last30DaysAverageVolume
     */
    public void setLast30DaysAverageVolume(java.lang.Integer last30DaysAverageVolume) {
        this.last30DaysAverageVolume = last30DaysAverageVolume;
    }


    /**
     * Gets the last30DaysHighPrice value for this StockData.
     * 
     * @return last30DaysHighPrice
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount getLast30DaysHighPrice() {
        return last30DaysHighPrice;
    }


    /**
     * Sets the last30DaysHighPrice value for this StockData.
     * 
     * @param last30DaysHighPrice
     */
    public void setLast30DaysHighPrice(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount last30DaysHighPrice) {
        this.last30DaysHighPrice = last30DaysHighPrice;
    }


    /**
     * Gets the last30DaysLowPrice value for this StockData.
     * 
     * @return last30DaysLowPrice
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount getLast30DaysLowPrice() {
        return last30DaysLowPrice;
    }


    /**
     * Sets the last30DaysLowPrice value for this StockData.
     * 
     * @param last30DaysLowPrice
     */
    public void setLast30DaysLowPrice(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount last30DaysLowPrice) {
        this.last30DaysLowPrice = last30DaysLowPrice;
    }


    /**
     * Gets the percentSharesHeldByInstitution value for this StockData.
     * 
     * @return percentSharesHeldByInstitution
     */
    public java.lang.Integer getPercentSharesHeldByInstitution() {
        return percentSharesHeldByInstitution;
    }


    /**
     * Sets the percentSharesHeldByInstitution value for this StockData.
     * 
     * @param percentSharesHeldByInstitution
     */
    public void setPercentSharesHeldByInstitution(java.lang.Integer percentSharesHeldByInstitution) {
        this.percentSharesHeldByInstitution = percentSharesHeldByInstitution;
    }


    /**
     * Gets the sharesFloating value for this StockData.
     * 
     * @return sharesFloating
     */
    public java.lang.Integer getSharesFloating() {
        return sharesFloating;
    }


    /**
     * Sets the sharesFloating value for this StockData.
     * 
     * @param sharesFloating
     */
    public void setSharesFloating(java.lang.Integer sharesFloating) {
        this.sharesFloating = sharesFloating;
    }


    /**
     * Gets the sharesOut value for this StockData.
     * 
     * @return sharesOut
     */
    public java.lang.Integer getSharesOut() {
        return sharesOut;
    }


    /**
     * Sets the sharesOut value for this StockData.
     * 
     * @param sharesOut
     */
    public void setSharesOut(java.lang.Integer sharesOut) {
        this.sharesOut = sharesOut;
    }


    /**
     * Gets the symbol value for this StockData.
     * 
     * @return symbol
     */
    public java.lang.String getSymbol() {
        return symbol;
    }


    /**
     * Sets the symbol value for this StockData.
     * 
     * @param symbol
     */
    public void setSymbol(java.lang.String symbol) {
        this.symbol = symbol;
    }


    /**
     * Gets the todayHighPrice value for this StockData.
     * 
     * @return todayHighPrice
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount getTodayHighPrice() {
        return todayHighPrice;
    }


    /**
     * Sets the todayHighPrice value for this StockData.
     * 
     * @param todayHighPrice
     */
    public void setTodayHighPrice(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount todayHighPrice) {
        this.todayHighPrice = todayHighPrice;
    }


    /**
     * Gets the todayLowPrice value for this StockData.
     * 
     * @return todayLowPrice
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount getTodayLowPrice() {
        return todayLowPrice;
    }


    /**
     * Sets the todayLowPrice value for this StockData.
     * 
     * @param todayLowPrice
     */
    public void setTodayLowPrice(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount todayLowPrice) {
        this.todayLowPrice = todayLowPrice;
    }


    /**
     * Gets the todayOpenPrice value for this StockData.
     * 
     * @return todayOpenPrice
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount getTodayOpenPrice() {
        return todayOpenPrice;
    }


    /**
     * Sets the todayOpenPrice value for this StockData.
     * 
     * @param todayOpenPrice
     */
    public void setTodayOpenPrice(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.Amount todayOpenPrice) {
        this.todayOpenPrice = todayOpenPrice;
    }


    /**
     * Gets the todayVolume value for this StockData.
     * 
     * @return todayVolume
     */
    public java.lang.Integer getTodayVolume() {
        return todayVolume;
    }


    /**
     * Sets the todayVolume value for this StockData.
     * 
     * @param todayVolume
     */
    public void setTodayVolume(java.lang.Integer todayVolume) {
        this.todayVolume = todayVolume;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StockData)) return false;
        StockData other = (StockData) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ETF==null && other.getETF()==null) || 
             (this.ETF!=null &&
              this.ETF.equals(other.getETF()))) &&
            ((this.OTC==null && other.getOTC()==null) || 
             (this.OTC!=null &&
              this.OTC.equals(other.getOTC()))) &&
            ((this.SP500==null && other.getSP500()==null) || 
             (this.SP500!=null &&
              this.SP500.equals(other.getSP500()))) &&
            ((this.currentMarketPrice==null && other.getCurrentMarketPrice()==null) || 
             (this.currentMarketPrice!=null &&
              this.currentMarketPrice.equals(other.getCurrentMarketPrice()))) &&
            ((this.last30DaysAveragePrice==null && other.getLast30DaysAveragePrice()==null) || 
             (this.last30DaysAveragePrice!=null &&
              this.last30DaysAveragePrice.equals(other.getLast30DaysAveragePrice()))) &&
            ((this.last30DaysAverageVolume==null && other.getLast30DaysAverageVolume()==null) || 
             (this.last30DaysAverageVolume!=null &&
              this.last30DaysAverageVolume.equals(other.getLast30DaysAverageVolume()))) &&
            ((this.last30DaysHighPrice==null && other.getLast30DaysHighPrice()==null) || 
             (this.last30DaysHighPrice!=null &&
              this.last30DaysHighPrice.equals(other.getLast30DaysHighPrice()))) &&
            ((this.last30DaysLowPrice==null && other.getLast30DaysLowPrice()==null) || 
             (this.last30DaysLowPrice!=null &&
              this.last30DaysLowPrice.equals(other.getLast30DaysLowPrice()))) &&
            ((this.percentSharesHeldByInstitution==null && other.getPercentSharesHeldByInstitution()==null) || 
             (this.percentSharesHeldByInstitution!=null &&
              this.percentSharesHeldByInstitution.equals(other.getPercentSharesHeldByInstitution()))) &&
            ((this.sharesFloating==null && other.getSharesFloating()==null) || 
             (this.sharesFloating!=null &&
              this.sharesFloating.equals(other.getSharesFloating()))) &&
            ((this.sharesOut==null && other.getSharesOut()==null) || 
             (this.sharesOut!=null &&
              this.sharesOut.equals(other.getSharesOut()))) &&
            ((this.symbol==null && other.getSymbol()==null) || 
             (this.symbol!=null &&
              this.symbol.equals(other.getSymbol()))) &&
            ((this.todayHighPrice==null && other.getTodayHighPrice()==null) || 
             (this.todayHighPrice!=null &&
              this.todayHighPrice.equals(other.getTodayHighPrice()))) &&
            ((this.todayLowPrice==null && other.getTodayLowPrice()==null) || 
             (this.todayLowPrice!=null &&
              this.todayLowPrice.equals(other.getTodayLowPrice()))) &&
            ((this.todayOpenPrice==null && other.getTodayOpenPrice()==null) || 
             (this.todayOpenPrice!=null &&
              this.todayOpenPrice.equals(other.getTodayOpenPrice()))) &&
            ((this.todayVolume==null && other.getTodayVolume()==null) || 
             (this.todayVolume!=null &&
              this.todayVolume.equals(other.getTodayVolume())));
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
        if (getETF() != null) {
            _hashCode += getETF().hashCode();
        }
        if (getOTC() != null) {
            _hashCode += getOTC().hashCode();
        }
        if (getSP500() != null) {
            _hashCode += getSP500().hashCode();
        }
        if (getCurrentMarketPrice() != null) {
            _hashCode += getCurrentMarketPrice().hashCode();
        }
        if (getLast30DaysAveragePrice() != null) {
            _hashCode += getLast30DaysAveragePrice().hashCode();
        }
        if (getLast30DaysAverageVolume() != null) {
            _hashCode += getLast30DaysAverageVolume().hashCode();
        }
        if (getLast30DaysHighPrice() != null) {
            _hashCode += getLast30DaysHighPrice().hashCode();
        }
        if (getLast30DaysLowPrice() != null) {
            _hashCode += getLast30DaysLowPrice().hashCode();
        }
        if (getPercentSharesHeldByInstitution() != null) {
            _hashCode += getPercentSharesHeldByInstitution().hashCode();
        }
        if (getSharesFloating() != null) {
            _hashCode += getSharesFloating().hashCode();
        }
        if (getSharesOut() != null) {
            _hashCode += getSharesOut().hashCode();
        }
        if (getSymbol() != null) {
            _hashCode += getSymbol().hashCode();
        }
        if (getTodayHighPrice() != null) {
            _hashCode += getTodayHighPrice().hashCode();
        }
        if (getTodayLowPrice() != null) {
            _hashCode += getTodayLowPrice().hashCode();
        }
        if (getTodayOpenPrice() != null) {
            _hashCode += getTodayOpenPrice().hashCode();
        }
        if (getTodayVolume() != null) {
            _hashCode += getTodayVolume().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StockData.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "StockData"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ETF");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ETF"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("OTC");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OTC"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SP500");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "SP500"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currentMarketPrice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "currentMarketPrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "Amount"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("last30DaysAveragePrice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "last30DaysAveragePrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "Amount"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("last30DaysAverageVolume");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "last30DaysAverageVolume"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("last30DaysHighPrice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "last30DaysHighPrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "Amount"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("last30DaysLowPrice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "last30DaysLowPrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "Amount"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("percentSharesHeldByInstitution");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "percentSharesHeldByInstitution"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sharesFloating");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "sharesFloating"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sharesOut");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "sharesOut"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("symbol");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "symbol"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("todayHighPrice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "todayHighPrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "Amount"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("todayLowPrice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "todayLowPrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "Amount"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("todayOpenPrice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "todayOpenPrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "Amount"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("todayVolume");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "todayVolume"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
