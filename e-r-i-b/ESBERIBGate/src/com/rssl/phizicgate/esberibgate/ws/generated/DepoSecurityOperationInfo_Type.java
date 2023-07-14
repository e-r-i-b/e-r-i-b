/**
 * DepoSecurityOperationInfo_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.esberibgate.ws.generated;


/**
 * Параметры операции с ценной бумагой (ДЕПО).
 */
public class DepoSecurityOperationInfo_Type  implements java.io.Serializable {
    /* Дата поручения */
    private java.lang.String documentDate;

    /* Номер поручения */
    private long documentNumber;

    /* Наименование ценной бумаги */
    private java.lang.String securityName;

    /* Регистрационный номер ценной бумаги */
    private java.lang.String securityNumber;

    /* Эмитент ценной бумаги */
    private java.lang.String issuer;

    private java.lang.String depositary;

    /* Идентификатор ценной бумаги */
    private java.lang.String insideCode;

    /* Планируемые операции над ценной бумагой */
    private com.rssl.phizicgate.esberibgate.ws.generated.DepoSecurityOperationList_Type[] operations;

    public DepoSecurityOperationInfo_Type() {
    }

    public DepoSecurityOperationInfo_Type(
           java.lang.String documentDate,
           long documentNumber,
           java.lang.String securityName,
           java.lang.String securityNumber,
           java.lang.String issuer,
           java.lang.String depositary,
           java.lang.String insideCode,
           com.rssl.phizicgate.esberibgate.ws.generated.DepoSecurityOperationList_Type[] operations) {
           this.documentDate = documentDate;
           this.documentNumber = documentNumber;
           this.securityName = securityName;
           this.securityNumber = securityNumber;
           this.issuer = issuer;
           this.depositary = depositary;
           this.insideCode = insideCode;
           this.operations = operations;
    }


    /**
     * Gets the documentDate value for this DepoSecurityOperationInfo_Type.
     * 
     * @return documentDate   * Дата поручения
     */
    public java.lang.String getDocumentDate() {
        return documentDate;
    }


    /**
     * Sets the documentDate value for this DepoSecurityOperationInfo_Type.
     * 
     * @param documentDate   * Дата поручения
     */
    public void setDocumentDate(java.lang.String documentDate) {
        this.documentDate = documentDate;
    }


    /**
     * Gets the documentNumber value for this DepoSecurityOperationInfo_Type.
     * 
     * @return documentNumber   * Номер поручения
     */
    public long getDocumentNumber() {
        return documentNumber;
    }


    /**
     * Sets the documentNumber value for this DepoSecurityOperationInfo_Type.
     * 
     * @param documentNumber   * Номер поручения
     */
    public void setDocumentNumber(long documentNumber) {
        this.documentNumber = documentNumber;
    }


    /**
     * Gets the securityName value for this DepoSecurityOperationInfo_Type.
     * 
     * @return securityName   * Наименование ценной бумаги
     */
    public java.lang.String getSecurityName() {
        return securityName;
    }


    /**
     * Sets the securityName value for this DepoSecurityOperationInfo_Type.
     * 
     * @param securityName   * Наименование ценной бумаги
     */
    public void setSecurityName(java.lang.String securityName) {
        this.securityName = securityName;
    }


    /**
     * Gets the securityNumber value for this DepoSecurityOperationInfo_Type.
     * 
     * @return securityNumber   * Регистрационный номер ценной бумаги
     */
    public java.lang.String getSecurityNumber() {
        return securityNumber;
    }


    /**
     * Sets the securityNumber value for this DepoSecurityOperationInfo_Type.
     * 
     * @param securityNumber   * Регистрационный номер ценной бумаги
     */
    public void setSecurityNumber(java.lang.String securityNumber) {
        this.securityNumber = securityNumber;
    }


    /**
     * Gets the issuer value for this DepoSecurityOperationInfo_Type.
     * 
     * @return issuer   * Эмитент ценной бумаги
     */
    public java.lang.String getIssuer() {
        return issuer;
    }


    /**
     * Sets the issuer value for this DepoSecurityOperationInfo_Type.
     * 
     * @param issuer   * Эмитент ценной бумаги
     */
    public void setIssuer(java.lang.String issuer) {
        this.issuer = issuer;
    }


    /**
     * Gets the depositary value for this DepoSecurityOperationInfo_Type.
     * 
     * @return depositary
     */
    public java.lang.String getDepositary() {
        return depositary;
    }


    /**
     * Sets the depositary value for this DepoSecurityOperationInfo_Type.
     * 
     * @param depositary
     */
    public void setDepositary(java.lang.String depositary) {
        this.depositary = depositary;
    }


    /**
     * Gets the insideCode value for this DepoSecurityOperationInfo_Type.
     * 
     * @return insideCode   * Идентификатор ценной бумаги
     */
    public java.lang.String getInsideCode() {
        return insideCode;
    }


    /**
     * Sets the insideCode value for this DepoSecurityOperationInfo_Type.
     * 
     * @param insideCode   * Идентификатор ценной бумаги
     */
    public void setInsideCode(java.lang.String insideCode) {
        this.insideCode = insideCode;
    }


    /**
     * Gets the operations value for this DepoSecurityOperationInfo_Type.
     * 
     * @return operations   * Планируемые операции над ценной бумагой
     */
    public com.rssl.phizicgate.esberibgate.ws.generated.DepoSecurityOperationList_Type[] getOperations() {
        return operations;
    }


    /**
     * Sets the operations value for this DepoSecurityOperationInfo_Type.
     * 
     * @param operations   * Планируемые операции над ценной бумагой
     */
    public void setOperations(com.rssl.phizicgate.esberibgate.ws.generated.DepoSecurityOperationList_Type[] operations) {
        this.operations = operations;
    }

    public com.rssl.phizicgate.esberibgate.ws.generated.DepoSecurityOperationList_Type getOperations(int i) {
        return this.operations[i];
    }

    public void setOperations(int i, com.rssl.phizicgate.esberibgate.ws.generated.DepoSecurityOperationList_Type _value) {
        this.operations[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepoSecurityOperationInfo_Type)) return false;
        DepoSecurityOperationInfo_Type other = (DepoSecurityOperationInfo_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.documentDate==null && other.getDocumentDate()==null) || 
             (this.documentDate!=null &&
              this.documentDate.equals(other.getDocumentDate()))) &&
            this.documentNumber == other.getDocumentNumber() &&
            ((this.securityName==null && other.getSecurityName()==null) || 
             (this.securityName!=null &&
              this.securityName.equals(other.getSecurityName()))) &&
            ((this.securityNumber==null && other.getSecurityNumber()==null) || 
             (this.securityNumber!=null &&
              this.securityNumber.equals(other.getSecurityNumber()))) &&
            ((this.issuer==null && other.getIssuer()==null) || 
             (this.issuer!=null &&
              this.issuer.equals(other.getIssuer()))) &&
            ((this.depositary==null && other.getDepositary()==null) || 
             (this.depositary!=null &&
              this.depositary.equals(other.getDepositary()))) &&
            ((this.insideCode==null && other.getInsideCode()==null) || 
             (this.insideCode!=null &&
              this.insideCode.equals(other.getInsideCode()))) &&
            ((this.operations==null && other.getOperations()==null) || 
             (this.operations!=null &&
              java.util.Arrays.equals(this.operations, other.getOperations())));
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
        if (getDocumentDate() != null) {
            _hashCode += getDocumentDate().hashCode();
        }
        _hashCode += new Long(getDocumentNumber()).hashCode();
        if (getSecurityName() != null) {
            _hashCode += getSecurityName().hashCode();
        }
        if (getSecurityNumber() != null) {
            _hashCode += getSecurityNumber().hashCode();
        }
        if (getIssuer() != null) {
            _hashCode += getIssuer().hashCode();
        }
        if (getDepositary() != null) {
            _hashCode += getDepositary().hashCode();
        }
        if (getInsideCode() != null) {
            _hashCode += getInsideCode().hashCode();
        }
        if (getOperations() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOperations());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOperations(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DepoSecurityOperationInfo_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoSecurityOperationInfo_Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DocumentDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DocumentNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("securityName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecurityName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("securityNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecurityNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("issuer");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Issuer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depositary");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Depositary"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("insideCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "InsideCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operations");
        elemField.setXmlName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Operations"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoSecurityOperationList_Type"));
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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
