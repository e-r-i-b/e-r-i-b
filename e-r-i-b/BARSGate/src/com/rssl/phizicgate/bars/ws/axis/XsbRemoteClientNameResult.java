/**
 * XsbRemoteClientNameResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.bars.ws.axis;

public class XsbRemoteClientNameResult  extends com.rssl.phizicgate.bars.ws.axis.XsbDocResults  implements java.io.Serializable {
    private java.lang.Object DOldNameBeg;

    private java.lang.Object DOldNameEnd;

    private java.lang.String SFName;

    private java.lang.String SFNameOld;

    private java.lang.String SInn;

    private java.lang.String SSName;

    private java.lang.String SSNameOld;

    public XsbRemoteClientNameResult() {
    }

    public XsbRemoteClientNameResult(
           com.rssl.phizicgate.bars.ws.axis.XsbDocID docID,
           com.rssl.phizicgate.bars.ws.axis.XsbExceptionItem[] exceptionItems,
           java.lang.Object DOldNameBeg,
           java.lang.Object DOldNameEnd,
           java.lang.String SFName,
           java.lang.String SFNameOld,
           java.lang.String SInn,
           java.lang.String SSName,
           java.lang.String SSNameOld) {
        super(
            docID,
            exceptionItems);
        this.DOldNameBeg = DOldNameBeg;
        this.DOldNameEnd = DOldNameEnd;
        this.SFName = SFName;
        this.SFNameOld = SFNameOld;
        this.SInn = SInn;
        this.SSName = SSName;
        this.SSNameOld = SSNameOld;
    }


    /**
     * Gets the DOldNameBeg value for this XsbRemoteClientNameResult.
     * 
     * @return DOldNameBeg
     */
    public java.lang.Object getDOldNameBeg() {
        return DOldNameBeg;
    }


    /**
     * Sets the DOldNameBeg value for this XsbRemoteClientNameResult.
     * 
     * @param DOldNameBeg
     */
    public void setDOldNameBeg(java.lang.Object DOldNameBeg) {
        this.DOldNameBeg = DOldNameBeg;
    }


    /**
     * Gets the DOldNameEnd value for this XsbRemoteClientNameResult.
     * 
     * @return DOldNameEnd
     */
    public java.lang.Object getDOldNameEnd() {
        return DOldNameEnd;
    }


    /**
     * Sets the DOldNameEnd value for this XsbRemoteClientNameResult.
     * 
     * @param DOldNameEnd
     */
    public void setDOldNameEnd(java.lang.Object DOldNameEnd) {
        this.DOldNameEnd = DOldNameEnd;
    }


    /**
     * Gets the SFName value for this XsbRemoteClientNameResult.
     * 
     * @return SFName
     */
    public java.lang.String getSFName() {
        return SFName;
    }


    /**
     * Sets the SFName value for this XsbRemoteClientNameResult.
     * 
     * @param SFName
     */
    public void setSFName(java.lang.String SFName) {
        this.SFName = SFName;
    }


    /**
     * Gets the SFNameOld value for this XsbRemoteClientNameResult.
     * 
     * @return SFNameOld
     */
    public java.lang.String getSFNameOld() {
        return SFNameOld;
    }


    /**
     * Sets the SFNameOld value for this XsbRemoteClientNameResult.
     * 
     * @param SFNameOld
     */
    public void setSFNameOld(java.lang.String SFNameOld) {
        this.SFNameOld = SFNameOld;
    }


    /**
     * Gets the SInn value for this XsbRemoteClientNameResult.
     * 
     * @return SInn
     */
    public java.lang.String getSInn() {
        return SInn;
    }


    /**
     * Sets the SInn value for this XsbRemoteClientNameResult.
     * 
     * @param SInn
     */
    public void setSInn(java.lang.String SInn) {
        this.SInn = SInn;
    }


    /**
     * Gets the SSName value for this XsbRemoteClientNameResult.
     * 
     * @return SSName
     */
    public java.lang.String getSSName() {
        return SSName;
    }


    /**
     * Sets the SSName value for this XsbRemoteClientNameResult.
     * 
     * @param SSName
     */
    public void setSSName(java.lang.String SSName) {
        this.SSName = SSName;
    }


    /**
     * Gets the SSNameOld value for this XsbRemoteClientNameResult.
     * 
     * @return SSNameOld
     */
    public java.lang.String getSSNameOld() {
        return SSNameOld;
    }


    /**
     * Sets the SSNameOld value for this XsbRemoteClientNameResult.
     * 
     * @param SSNameOld
     */
    public void setSSNameOld(java.lang.String SSNameOld) {
        this.SSNameOld = SSNameOld;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof XsbRemoteClientNameResult)) return false;
        XsbRemoteClientNameResult other = (XsbRemoteClientNameResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.DOldNameBeg==null && other.getDOldNameBeg()==null) || 
             (this.DOldNameBeg!=null &&
              this.DOldNameBeg.equals(other.getDOldNameBeg()))) &&
            ((this.DOldNameEnd==null && other.getDOldNameEnd()==null) || 
             (this.DOldNameEnd!=null &&
              this.DOldNameEnd.equals(other.getDOldNameEnd()))) &&
            ((this.SFName==null && other.getSFName()==null) || 
             (this.SFName!=null &&
              this.SFName.equals(other.getSFName()))) &&
            ((this.SFNameOld==null && other.getSFNameOld()==null) || 
             (this.SFNameOld!=null &&
              this.SFNameOld.equals(other.getSFNameOld()))) &&
            ((this.SInn==null && other.getSInn()==null) || 
             (this.SInn!=null &&
              this.SInn.equals(other.getSInn()))) &&
            ((this.SSName==null && other.getSSName()==null) || 
             (this.SSName!=null &&
              this.SSName.equals(other.getSSName()))) &&
            ((this.SSNameOld==null && other.getSSNameOld()==null) || 
             (this.SSNameOld!=null &&
              this.SSNameOld.equals(other.getSSNameOld())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getDOldNameBeg() != null) {
            _hashCode += getDOldNameBeg().hashCode();
        }
        if (getDOldNameEnd() != null) {
            _hashCode += getDOldNameEnd().hashCode();
        }
        if (getSFName() != null) {
            _hashCode += getSFName().hashCode();
        }
        if (getSFNameOld() != null) {
            _hashCode += getSFNameOld().hashCode();
        }
        if (getSInn() != null) {
            _hashCode += getSInn().hashCode();
        }
        if (getSSName() != null) {
            _hashCode += getSSName().hashCode();
        }
        if (getSSNameOld() != null) {
            _hashCode += getSSNameOld().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(XsbRemoteClientNameResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://checkNames.xsb.webservices.bars.sbrf", "XsbRemoteClientNameResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DOldNameBeg");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DOldNameBeg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DOldNameEnd");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DOldNameEnd"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SFName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SFName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SFNameOld");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SFNameOld"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SInn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SInn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SSName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SSName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SSNameOld");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SSNameOld"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
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
