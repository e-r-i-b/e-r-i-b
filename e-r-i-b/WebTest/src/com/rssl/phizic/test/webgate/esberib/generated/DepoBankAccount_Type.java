/**
 * DepoBankAccount_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;


/**
 * Информации о рублевом счете
 */
public class DepoBankAccount_Type  implements java.io.Serializable {
    /* Номер рублевого счета */
    private java.lang.String accId;

    /* Вид пластиковой карты. Обязательно, если карт счет. */
    private java.lang.String cardType;

    /* Номер рублевого счета */
    private java.lang.String cardId;

    /* Наименование банка, где открыт рублевый счет */
    private java.lang.String bankName;

    /* БИК */
    private java.lang.String BIC;

    /* Наименование банка, где открыт корреспондентский счет */
    private java.lang.String corAccId;

    /* БИК */
    private java.lang.String corBankName;

    /* Назначение счета */
    private java.lang.String dest;

    public DepoBankAccount_Type() {
    }

    public DepoBankAccount_Type(
           java.lang.String accId,
           java.lang.String cardType,
           java.lang.String cardId,
           java.lang.String bankName,
           java.lang.String BIC,
           java.lang.String corAccId,
           java.lang.String corBankName,
           java.lang.String dest) {
           this.accId = accId;
           this.cardType = cardType;
           this.cardId = cardId;
           this.bankName = bankName;
           this.BIC = BIC;
           this.corAccId = corAccId;
           this.corBankName = corBankName;
           this.dest = dest;
    }


    /**
     * Gets the accId value for this DepoBankAccount_Type.
     * 
     * @return accId   * Номер рублевого счета
     */
    public java.lang.String getAccId() {
        return accId;
    }


    /**
     * Sets the accId value for this DepoBankAccount_Type.
     * 
     * @param accId   * Номер рублевого счета
     */
    public void setAccId(java.lang.String accId) {
        this.accId = accId;
    }


    /**
     * Gets the cardType value for this DepoBankAccount_Type.
     * 
     * @return cardType   * Вид пластиковой карты. Обязательно, если карт счет.
     */
    public java.lang.String getCardType() {
        return cardType;
    }


    /**
     * Sets the cardType value for this DepoBankAccount_Type.
     * 
     * @param cardType   * Вид пластиковой карты. Обязательно, если карт счет.
     */
    public void setCardType(java.lang.String cardType) {
        this.cardType = cardType;
    }


    /**
     * Gets the cardId value for this DepoBankAccount_Type.
     * 
     * @return cardId   * Номер рублевого счета
     */
    public java.lang.String getCardId() {
        return cardId;
    }


    /**
     * Sets the cardId value for this DepoBankAccount_Type.
     * 
     * @param cardId   * Номер рублевого счета
     */
    public void setCardId(java.lang.String cardId) {
        this.cardId = cardId;
    }


    /**
     * Gets the bankName value for this DepoBankAccount_Type.
     * 
     * @return bankName   * Наименование банка, где открыт рублевый счет
     */
    public java.lang.String getBankName() {
        return bankName;
    }


    /**
     * Sets the bankName value for this DepoBankAccount_Type.
     * 
     * @param bankName   * Наименование банка, где открыт рублевый счет
     */
    public void setBankName(java.lang.String bankName) {
        this.bankName = bankName;
    }


    /**
     * Gets the BIC value for this DepoBankAccount_Type.
     * 
     * @return BIC   * БИК
     */
    public java.lang.String getBIC() {
        return BIC;
    }


    /**
     * Sets the BIC value for this DepoBankAccount_Type.
     * 
     * @param BIC   * БИК
     */
    public void setBIC(java.lang.String BIC) {
        this.BIC = BIC;
    }


    /**
     * Gets the corAccId value for this DepoBankAccount_Type.
     * 
     * @return corAccId   * Наименование банка, где открыт корреспондентский счет
     */
    public java.lang.String getCorAccId() {
        return corAccId;
    }


    /**
     * Sets the corAccId value for this DepoBankAccount_Type.
     * 
     * @param corAccId   * Наименование банка, где открыт корреспондентский счет
     */
    public void setCorAccId(java.lang.String corAccId) {
        this.corAccId = corAccId;
    }


    /**
     * Gets the corBankName value for this DepoBankAccount_Type.
     * 
     * @return corBankName   * БИК
     */
    public java.lang.String getCorBankName() {
        return corBankName;
    }


    /**
     * Sets the corBankName value for this DepoBankAccount_Type.
     * 
     * @param corBankName   * БИК
     */
    public void setCorBankName(java.lang.String corBankName) {
        this.corBankName = corBankName;
    }


    /**
     * Gets the dest value for this DepoBankAccount_Type.
     * 
     * @return dest   * Назначение счета
     */
    public java.lang.String getDest() {
        return dest;
    }


    /**
     * Sets the dest value for this DepoBankAccount_Type.
     * 
     * @param dest   * Назначение счета
     */
    public void setDest(java.lang.String dest) {
        this.dest = dest;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepoBankAccount_Type)) return false;
        DepoBankAccount_Type other = (DepoBankAccount_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.accId==null && other.getAccId()==null) || 
             (this.accId!=null &&
              this.accId.equals(other.getAccId()))) &&
            ((this.cardType==null && other.getCardType()==null) || 
             (this.cardType!=null &&
              this.cardType.equals(other.getCardType()))) &&
            ((this.cardId==null && other.getCardId()==null) || 
             (this.cardId!=null &&
              this.cardId.equals(other.getCardId()))) &&
            ((this.bankName==null && other.getBankName()==null) || 
             (this.bankName!=null &&
              this.bankName.equals(other.getBankName()))) &&
            ((this.BIC==null && other.getBIC()==null) || 
             (this.BIC!=null &&
              this.BIC.equals(other.getBIC()))) &&
            ((this.corAccId==null && other.getCorAccId()==null) || 
             (this.corAccId!=null &&
              this.corAccId.equals(other.getCorAccId()))) &&
            ((this.corBankName==null && other.getCorBankName()==null) || 
             (this.corBankName!=null &&
              this.corBankName.equals(other.getCorBankName()))) &&
            ((this.dest==null && other.getDest()==null) || 
             (this.dest!=null &&
              this.dest.equals(other.getDest())));
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
        if (getAccId() != null) {
            _hashCode += getAccId().hashCode();
        }
        if (getCardType() != null) {
            _hashCode += getCardType().hashCode();
        }
        if (getCardId() != null) {
            _hashCode += getCardId().hashCode();
        }
        if (getBankName() != null) {
            _hashCode += getBankName().hashCode();
        }
        if (getBIC() != null) {
            _hashCode += getBIC().hashCode();
        }
        if (getCorAccId() != null) {
            _hashCode += getCorAccId().hashCode();
        }
        if (getCorBankName() != null) {
            _hashCode += getCorBankName().hashCode();
        }
        if (getDest() != null) {
            _hashCode += getDest().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DepoBankAccount_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoBankAccount_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AccId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BIC");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BIC"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("corAccId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CorAccId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("corBankName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CorBankName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Dest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
