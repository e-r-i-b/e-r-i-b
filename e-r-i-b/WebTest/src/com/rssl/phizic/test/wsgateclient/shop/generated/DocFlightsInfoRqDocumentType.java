/**
 * DocFlightsInfoRqDocumentType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.wsgateclient.shop.generated;

public class DocFlightsInfoRqDocumentType  implements java.io.Serializable {
    private java.lang.String ERIBUID;

    private long ticketsStatus;

    private java.lang.String[] ticketsList;

    private java.lang.String itineraryUrl;

    private java.lang.String statusDesc;

    private java.lang.String ticketsDesc;

    public DocFlightsInfoRqDocumentType() {
    }

    public DocFlightsInfoRqDocumentType(
           java.lang.String ERIBUID,
           long ticketsStatus,
           java.lang.String[] ticketsList,
           java.lang.String itineraryUrl,
           java.lang.String statusDesc,
           java.lang.String ticketsDesc) {
           this.ERIBUID = ERIBUID;
           this.ticketsStatus = ticketsStatus;
           this.ticketsList = ticketsList;
           this.itineraryUrl = itineraryUrl;
           this.statusDesc = statusDesc;
           this.ticketsDesc = ticketsDesc;
    }


    /**
     * Gets the ERIBUID value for this DocFlightsInfoRqDocumentType.
     * 
     * @return ERIBUID
     */
    public java.lang.String getERIBUID() {
        return ERIBUID;
    }


    /**
     * Sets the ERIBUID value for this DocFlightsInfoRqDocumentType.
     * 
     * @param ERIBUID
     */
    public void setERIBUID(java.lang.String ERIBUID) {
        this.ERIBUID = ERIBUID;
    }


    /**
     * Gets the ticketsStatus value for this DocFlightsInfoRqDocumentType.
     * 
     * @return ticketsStatus
     */
    public long getTicketsStatus() {
        return ticketsStatus;
    }


    /**
     * Sets the ticketsStatus value for this DocFlightsInfoRqDocumentType.
     * 
     * @param ticketsStatus
     */
    public void setTicketsStatus(long ticketsStatus) {
        this.ticketsStatus = ticketsStatus;
    }


    /**
     * Gets the ticketsList value for this DocFlightsInfoRqDocumentType.
     * 
     * @return ticketsList
     */
    public java.lang.String[] getTicketsList() {
        return ticketsList;
    }


    /**
     * Sets the ticketsList value for this DocFlightsInfoRqDocumentType.
     * 
     * @param ticketsList
     */
    public void setTicketsList(java.lang.String[] ticketsList) {
        this.ticketsList = ticketsList;
    }


    /**
     * Gets the itineraryUrl value for this DocFlightsInfoRqDocumentType.
     * 
     * @return itineraryUrl
     */
    public java.lang.String getItineraryUrl() {
        return itineraryUrl;
    }


    /**
     * Sets the itineraryUrl value for this DocFlightsInfoRqDocumentType.
     * 
     * @param itineraryUrl
     */
    public void setItineraryUrl(java.lang.String itineraryUrl) {
        this.itineraryUrl = itineraryUrl;
    }


    /**
     * Gets the statusDesc value for this DocFlightsInfoRqDocumentType.
     * 
     * @return statusDesc
     */
    public java.lang.String getStatusDesc() {
        return statusDesc;
    }


    /**
     * Sets the statusDesc value for this DocFlightsInfoRqDocumentType.
     * 
     * @param statusDesc
     */
    public void setStatusDesc(java.lang.String statusDesc) {
        this.statusDesc = statusDesc;
    }


    /**
     * Gets the ticketsDesc value for this DocFlightsInfoRqDocumentType.
     * 
     * @return ticketsDesc
     */
    public java.lang.String getTicketsDesc() {
        return ticketsDesc;
    }


    /**
     * Sets the ticketsDesc value for this DocFlightsInfoRqDocumentType.
     * 
     * @param ticketsDesc
     */
    public void setTicketsDesc(java.lang.String ticketsDesc) {
        this.ticketsDesc = ticketsDesc;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DocFlightsInfoRqDocumentType)) return false;
        DocFlightsInfoRqDocumentType other = (DocFlightsInfoRqDocumentType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ERIBUID==null && other.getERIBUID()==null) || 
             (this.ERIBUID!=null &&
              this.ERIBUID.equals(other.getERIBUID()))) &&
            this.ticketsStatus == other.getTicketsStatus() &&
            ((this.ticketsList==null && other.getTicketsList()==null) || 
             (this.ticketsList!=null &&
              java.util.Arrays.equals(this.ticketsList, other.getTicketsList()))) &&
            ((this.itineraryUrl==null && other.getItineraryUrl()==null) || 
             (this.itineraryUrl!=null &&
              this.itineraryUrl.equals(other.getItineraryUrl()))) &&
            ((this.statusDesc==null && other.getStatusDesc()==null) || 
             (this.statusDesc!=null &&
              this.statusDesc.equals(other.getStatusDesc()))) &&
            ((this.ticketsDesc==null && other.getTicketsDesc()==null) || 
             (this.ticketsDesc!=null &&
              this.ticketsDesc.equals(other.getTicketsDesc())));
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
        if (getERIBUID() != null) {
            _hashCode += getERIBUID().hashCode();
        }
        _hashCode += new Long(getTicketsStatus()).hashCode();
        if (getTicketsList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTicketsList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTicketsList(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getItineraryUrl() != null) {
            _hashCode += getItineraryUrl().hashCode();
        }
        if (getStatusDesc() != null) {
            _hashCode += getStatusDesc().hashCode();
        }
        if (getTicketsDesc() != null) {
            _hashCode += getTicketsDesc().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DocFlightsInfoRqDocumentType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "DocFlightsInfoRqDocumentType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ERIBUID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "ERIBUID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "UUIDType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ticketsStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "TicketsStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ticketsList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "TicketsList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "String15Type"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "TicketNumber"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("itineraryUrl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "ItineraryUrl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "StringType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statusDesc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "StatusDesc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "StringType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ticketsDesc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "TicketsDesc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://generated.shoplistener.phizic.rssl.com", "StringType"));
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
