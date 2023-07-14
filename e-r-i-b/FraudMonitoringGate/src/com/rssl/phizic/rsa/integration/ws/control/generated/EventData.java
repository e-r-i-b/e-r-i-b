/**
 * EventData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;


/**
 * This defines all relevant information about an event
 */
public class EventData  implements java.io.Serializable {
    private com.rssl.phizic.rsa.integration.ws.control.generated.AuthenticationLevel authenticationLevel;

    private com.rssl.phizic.rsa.integration.ws.control.generated.ClientDefinedFact[] clientDefinedAttributeList;

    private java.lang.String clientDefinedEventType;

    private java.lang.String eventDescription;

    private java.lang.String eventId;

    private java.lang.String eventReferenceId;

    private com.rssl.phizic.rsa.integration.ws.control.generated.EventType eventType;

    private com.rssl.phizic.rsa.integration.ws.control.generated.UserData newUserData;

    private com.rssl.phizic.rsa.integration.ws.control.generated.StockTradeData stockTradeData;

    private java.lang.String timeOfOccurrence;

    private com.rssl.phizic.rsa.integration.ws.control.generated.TransactionData transactionData;

    public EventData() {
    }

    public EventData(
           com.rssl.phizic.rsa.integration.ws.control.generated.AuthenticationLevel authenticationLevel,
           com.rssl.phizic.rsa.integration.ws.control.generated.ClientDefinedFact[] clientDefinedAttributeList,
           java.lang.String clientDefinedEventType,
           java.lang.String eventDescription,
           java.lang.String eventId,
           java.lang.String eventReferenceId,
           com.rssl.phizic.rsa.integration.ws.control.generated.EventType eventType,
           com.rssl.phizic.rsa.integration.ws.control.generated.UserData newUserData,
           com.rssl.phizic.rsa.integration.ws.control.generated.StockTradeData stockTradeData,
           java.lang.String timeOfOccurrence,
           com.rssl.phizic.rsa.integration.ws.control.generated.TransactionData transactionData) {
           this.authenticationLevel = authenticationLevel;
           this.clientDefinedAttributeList = clientDefinedAttributeList;
           this.clientDefinedEventType = clientDefinedEventType;
           this.eventDescription = eventDescription;
           this.eventId = eventId;
           this.eventReferenceId = eventReferenceId;
           this.eventType = eventType;
           this.newUserData = newUserData;
           this.stockTradeData = stockTradeData;
           this.timeOfOccurrence = timeOfOccurrence;
           this.transactionData = transactionData;
    }


    /**
     * Gets the authenticationLevel value for this EventData.
     * 
     * @return authenticationLevel
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.AuthenticationLevel getAuthenticationLevel() {
        return authenticationLevel;
    }


    /**
     * Sets the authenticationLevel value for this EventData.
     * 
     * @param authenticationLevel
     */
    public void setAuthenticationLevel(com.rssl.phizic.rsa.integration.ws.control.generated.AuthenticationLevel authenticationLevel) {
        this.authenticationLevel = authenticationLevel;
    }


    /**
     * Gets the clientDefinedAttributeList value for this EventData.
     * 
     * @return clientDefinedAttributeList
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.ClientDefinedFact[] getClientDefinedAttributeList() {
        return clientDefinedAttributeList;
    }


    /**
     * Sets the clientDefinedAttributeList value for this EventData.
     * 
     * @param clientDefinedAttributeList
     */
    public void setClientDefinedAttributeList(com.rssl.phizic.rsa.integration.ws.control.generated.ClientDefinedFact[] clientDefinedAttributeList) {
        this.clientDefinedAttributeList = clientDefinedAttributeList;
    }


    /**
     * Gets the clientDefinedEventType value for this EventData.
     * 
     * @return clientDefinedEventType
     */
    public java.lang.String getClientDefinedEventType() {
        return clientDefinedEventType;
    }


    /**
     * Sets the clientDefinedEventType value for this EventData.
     * 
     * @param clientDefinedEventType
     */
    public void setClientDefinedEventType(java.lang.String clientDefinedEventType) {
        this.clientDefinedEventType = clientDefinedEventType;
    }


    /**
     * Gets the eventDescription value for this EventData.
     * 
     * @return eventDescription
     */
    public java.lang.String getEventDescription() {
        return eventDescription;
    }


    /**
     * Sets the eventDescription value for this EventData.
     * 
     * @param eventDescription
     */
    public void setEventDescription(java.lang.String eventDescription) {
        this.eventDescription = eventDescription;
    }


    /**
     * Gets the eventId value for this EventData.
     * 
     * @return eventId
     */
    public java.lang.String getEventId() {
        return eventId;
    }


    /**
     * Sets the eventId value for this EventData.
     * 
     * @param eventId
     */
    public void setEventId(java.lang.String eventId) {
        this.eventId = eventId;
    }


    /**
     * Gets the eventReferenceId value for this EventData.
     * 
     * @return eventReferenceId
     */
    public java.lang.String getEventReferenceId() {
        return eventReferenceId;
    }


    /**
     * Sets the eventReferenceId value for this EventData.
     * 
     * @param eventReferenceId
     */
    public void setEventReferenceId(java.lang.String eventReferenceId) {
        this.eventReferenceId = eventReferenceId;
    }


    /**
     * Gets the eventType value for this EventData.
     * 
     * @return eventType
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.EventType getEventType() {
        return eventType;
    }


    /**
     * Sets the eventType value for this EventData.
     * 
     * @param eventType
     */
    public void setEventType(com.rssl.phizic.rsa.integration.ws.control.generated.EventType eventType) {
        this.eventType = eventType;
    }


    /**
     * Gets the newUserData value for this EventData.
     * 
     * @return newUserData
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.UserData getNewUserData() {
        return newUserData;
    }


    /**
     * Sets the newUserData value for this EventData.
     * 
     * @param newUserData
     */
    public void setNewUserData(com.rssl.phizic.rsa.integration.ws.control.generated.UserData newUserData) {
        this.newUserData = newUserData;
    }


    /**
     * Gets the stockTradeData value for this EventData.
     * 
     * @return stockTradeData
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.StockTradeData getStockTradeData() {
        return stockTradeData;
    }


    /**
     * Sets the stockTradeData value for this EventData.
     * 
     * @param stockTradeData
     */
    public void setStockTradeData(com.rssl.phizic.rsa.integration.ws.control.generated.StockTradeData stockTradeData) {
        this.stockTradeData = stockTradeData;
    }


    /**
     * Gets the timeOfOccurrence value for this EventData.
     * 
     * @return timeOfOccurrence
     */
    public java.lang.String getTimeOfOccurrence() {
        return timeOfOccurrence;
    }


    /**
     * Sets the timeOfOccurrence value for this EventData.
     * 
     * @param timeOfOccurrence
     */
    public void setTimeOfOccurrence(java.lang.String timeOfOccurrence) {
        this.timeOfOccurrence = timeOfOccurrence;
    }


    /**
     * Gets the transactionData value for this EventData.
     * 
     * @return transactionData
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.TransactionData getTransactionData() {
        return transactionData;
    }


    /**
     * Sets the transactionData value for this EventData.
     * 
     * @param transactionData
     */
    public void setTransactionData(com.rssl.phizic.rsa.integration.ws.control.generated.TransactionData transactionData) {
        this.transactionData = transactionData;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EventData)) return false;
        EventData other = (EventData) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.authenticationLevel==null && other.getAuthenticationLevel()==null) || 
             (this.authenticationLevel!=null &&
              this.authenticationLevel.equals(other.getAuthenticationLevel()))) &&
            ((this.clientDefinedAttributeList==null && other.getClientDefinedAttributeList()==null) || 
             (this.clientDefinedAttributeList!=null &&
              java.util.Arrays.equals(this.clientDefinedAttributeList, other.getClientDefinedAttributeList()))) &&
            ((this.clientDefinedEventType==null && other.getClientDefinedEventType()==null) || 
             (this.clientDefinedEventType!=null &&
              this.clientDefinedEventType.equals(other.getClientDefinedEventType()))) &&
            ((this.eventDescription==null && other.getEventDescription()==null) || 
             (this.eventDescription!=null &&
              this.eventDescription.equals(other.getEventDescription()))) &&
            ((this.eventId==null && other.getEventId()==null) || 
             (this.eventId!=null &&
              this.eventId.equals(other.getEventId()))) &&
            ((this.eventReferenceId==null && other.getEventReferenceId()==null) || 
             (this.eventReferenceId!=null &&
              this.eventReferenceId.equals(other.getEventReferenceId()))) &&
            ((this.eventType==null && other.getEventType()==null) || 
             (this.eventType!=null &&
              this.eventType.equals(other.getEventType()))) &&
            ((this.newUserData==null && other.getNewUserData()==null) || 
             (this.newUserData!=null &&
              this.newUserData.equals(other.getNewUserData()))) &&
            ((this.stockTradeData==null && other.getStockTradeData()==null) || 
             (this.stockTradeData!=null &&
              this.stockTradeData.equals(other.getStockTradeData()))) &&
            ((this.timeOfOccurrence==null && other.getTimeOfOccurrence()==null) || 
             (this.timeOfOccurrence!=null &&
              this.timeOfOccurrence.equals(other.getTimeOfOccurrence()))) &&
            ((this.transactionData==null && other.getTransactionData()==null) || 
             (this.transactionData!=null &&
              this.transactionData.equals(other.getTransactionData())));
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
        if (getAuthenticationLevel() != null) {
            _hashCode += getAuthenticationLevel().hashCode();
        }
        if (getClientDefinedAttributeList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getClientDefinedAttributeList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getClientDefinedAttributeList(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getClientDefinedEventType() != null) {
            _hashCode += getClientDefinedEventType().hashCode();
        }
        if (getEventDescription() != null) {
            _hashCode += getEventDescription().hashCode();
        }
        if (getEventId() != null) {
            _hashCode += getEventId().hashCode();
        }
        if (getEventReferenceId() != null) {
            _hashCode += getEventReferenceId().hashCode();
        }
        if (getEventType() != null) {
            _hashCode += getEventType().hashCode();
        }
        if (getNewUserData() != null) {
            _hashCode += getNewUserData().hashCode();
        }
        if (getStockTradeData() != null) {
            _hashCode += getStockTradeData().hashCode();
        }
        if (getTimeOfOccurrence() != null) {
            _hashCode += getTimeOfOccurrence().hashCode();
        }
        if (getTransactionData() != null) {
            _hashCode += getTransactionData().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EventData.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "EventData"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("authenticationLevel");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "authenticationLevel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AuthenticationLevel"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientDefinedAttributeList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "clientDefinedAttributeList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ClientDefinedFact"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "fact"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientDefinedEventType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "clientDefinedEventType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("eventDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "eventDescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("eventId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "eventId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("eventReferenceId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "eventReferenceId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("eventType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "eventType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "EventType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("newUserData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "newUserData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "UserData"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stockTradeData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "stockTradeData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "StockTradeData"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timeOfOccurrence");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "timeOfOccurrence"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transactionData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "transactionData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "TransactionData"));
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
