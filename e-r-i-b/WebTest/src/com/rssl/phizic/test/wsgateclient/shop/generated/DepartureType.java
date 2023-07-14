/**
 * DepartureType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.wsgateclient.shop.generated;

public class DepartureType  implements java.io.Serializable {
    private java.lang.String airport;

    private java.lang.String location;

    private java.lang.String terminal;

    private java.lang.String dateTime;

    private java.lang.String flight;

    public DepartureType() {
    }

    public DepartureType(
           java.lang.String airport,
           java.lang.String location,
           java.lang.String terminal,
           java.lang.String dateTime,
           java.lang.String flight) {
           this.airport = airport;
           this.location = location;
           this.terminal = terminal;
           this.dateTime = dateTime;
           this.flight = flight;
    }


    /**
     * Gets the airport value for this DepartureType.
     * 
     * @return airport
     */
    public java.lang.String getAirport() {
        return airport;
    }


    /**
     * Sets the airport value for this DepartureType.
     * 
     * @param airport
     */
    public void setAirport(java.lang.String airport) {
        this.airport = airport;
    }


    /**
     * Gets the location value for this DepartureType.
     * 
     * @return location
     */
    public java.lang.String getLocation() {
        return location;
    }


    /**
     * Sets the location value for this DepartureType.
     * 
     * @param location
     */
    public void setLocation(java.lang.String location) {
        this.location = location;
    }


    /**
     * Gets the terminal value for this DepartureType.
     * 
     * @return terminal
     */
    public java.lang.String getTerminal() {
        return terminal;
    }


    /**
     * Sets the terminal value for this DepartureType.
     * 
     * @param terminal
     */
    public void setTerminal(java.lang.String terminal) {
        this.terminal = terminal;
    }


    /**
     * Gets the dateTime value for this DepartureType.
     * 
     * @return dateTime
     */
    public java.lang.String getDateTime() {
        return dateTime;
    }


    /**
     * Sets the dateTime value for this DepartureType.
     * 
     * @param dateTime
     */
    public void setDateTime(java.lang.String dateTime) {
        this.dateTime = dateTime;
    }


    /**
     * Gets the flight value for this DepartureType.
     * 
     * @return flight
     */
    public java.lang.String getFlight() {
        return flight;
    }


    /**
     * Sets the flight value for this DepartureType.
     * 
     * @param flight
     */
    public void setFlight(java.lang.String flight) {
        this.flight = flight;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepartureType)) return false;
        DepartureType other = (DepartureType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.airport==null && other.getAirport()==null) || 
             (this.airport!=null &&
              this.airport.equals(other.getAirport()))) &&
            ((this.location==null && other.getLocation()==null) || 
             (this.location!=null &&
              this.location.equals(other.getLocation()))) &&
            ((this.terminal==null && other.getTerminal()==null) || 
             (this.terminal!=null &&
              this.terminal.equals(other.getTerminal()))) &&
            ((this.dateTime==null && other.getDateTime()==null) || 
             (this.dateTime!=null &&
              this.dateTime.equals(other.getDateTime()))) &&
            ((this.flight==null && other.getFlight()==null) || 
             (this.flight!=null &&
              this.flight.equals(other.getFlight())));
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
        if (getAirport() != null) {
            _hashCode += getAirport().hashCode();
        }
        if (getLocation() != null) {
            _hashCode += getLocation().hashCode();
        }
        if (getTerminal() != null) {
            _hashCode += getTerminal().hashCode();
        }
        if (getDateTime() != null) {
            _hashCode += getDateTime().hashCode();
        }
        if (getFlight() != null) {
            _hashCode += getFlight().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DepartureType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "DepartureType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("airport");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "Airport"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "StringType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("location");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "Location"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "StringType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("terminal");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "Terminal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "StringType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dateTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "DateTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flight");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "Flight"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "StringType"));
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
