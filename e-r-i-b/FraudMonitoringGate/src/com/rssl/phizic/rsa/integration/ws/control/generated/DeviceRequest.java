/**
 * DeviceRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;


/**
 * Requests information about the device
 */
public class DeviceRequest  implements java.io.Serializable {
    private java.lang.String beaconId;

    private java.lang.String devicePrint;

    private java.lang.String deviceTokenCookie;

    private java.lang.String deviceTokenFSO;

    private java.lang.String httpAccept;

    private java.lang.String httpAcceptChars;

    private java.lang.String httpAcceptEncoding;

    private java.lang.String httpAcceptLanguage;

    private java.lang.String httpReferrer;

    private java.lang.String ipAddress;

    private java.lang.String userAgent;

    private java.lang.String geoLocation;

    private java.lang.String domElements;

    private java.lang.String jsEvents;

    private java.lang.String pageId;

    private com.rssl.phizic.rsa.integration.ws.control.generated.DeviceIdentifier[] deviceIdentifier;

    public DeviceRequest() {
    }

    public DeviceRequest(
           java.lang.String beaconId,
           java.lang.String devicePrint,
           java.lang.String deviceTokenCookie,
           java.lang.String deviceTokenFSO,
           java.lang.String httpAccept,
           java.lang.String httpAcceptChars,
           java.lang.String httpAcceptEncoding,
           java.lang.String httpAcceptLanguage,
           java.lang.String httpReferrer,
           java.lang.String ipAddress,
           java.lang.String userAgent,
           java.lang.String geoLocation,
           java.lang.String domElements,
           java.lang.String jsEvents,
           java.lang.String pageId,
           com.rssl.phizic.rsa.integration.ws.control.generated.DeviceIdentifier[] deviceIdentifier) {
           this.beaconId = beaconId;
           this.devicePrint = devicePrint;
           this.deviceTokenCookie = deviceTokenCookie;
           this.deviceTokenFSO = deviceTokenFSO;
           this.httpAccept = httpAccept;
           this.httpAcceptChars = httpAcceptChars;
           this.httpAcceptEncoding = httpAcceptEncoding;
           this.httpAcceptLanguage = httpAcceptLanguage;
           this.httpReferrer = httpReferrer;
           this.ipAddress = ipAddress;
           this.userAgent = userAgent;
           this.geoLocation = geoLocation;
           this.domElements = domElements;
           this.jsEvents = jsEvents;
           this.pageId = pageId;
           this.deviceIdentifier = deviceIdentifier;
    }


    /**
     * Gets the beaconId value for this DeviceRequest.
     * 
     * @return beaconId
     */
    public java.lang.String getBeaconId() {
        return beaconId;
    }


    /**
     * Sets the beaconId value for this DeviceRequest.
     * 
     * @param beaconId
     */
    public void setBeaconId(java.lang.String beaconId) {
        this.beaconId = beaconId;
    }


    /**
     * Gets the devicePrint value for this DeviceRequest.
     * 
     * @return devicePrint
     */
    public java.lang.String getDevicePrint() {
        return devicePrint;
    }


    /**
     * Sets the devicePrint value for this DeviceRequest.
     * 
     * @param devicePrint
     */
    public void setDevicePrint(java.lang.String devicePrint) {
        this.devicePrint = devicePrint;
    }


    /**
     * Gets the deviceTokenCookie value for this DeviceRequest.
     * 
     * @return deviceTokenCookie
     */
    public java.lang.String getDeviceTokenCookie() {
        return deviceTokenCookie;
    }


    /**
     * Sets the deviceTokenCookie value for this DeviceRequest.
     * 
     * @param deviceTokenCookie
     */
    public void setDeviceTokenCookie(java.lang.String deviceTokenCookie) {
        this.deviceTokenCookie = deviceTokenCookie;
    }


    /**
     * Gets the deviceTokenFSO value for this DeviceRequest.
     * 
     * @return deviceTokenFSO
     */
    public java.lang.String getDeviceTokenFSO() {
        return deviceTokenFSO;
    }


    /**
     * Sets the deviceTokenFSO value for this DeviceRequest.
     * 
     * @param deviceTokenFSO
     */
    public void setDeviceTokenFSO(java.lang.String deviceTokenFSO) {
        this.deviceTokenFSO = deviceTokenFSO;
    }


    /**
     * Gets the httpAccept value for this DeviceRequest.
     * 
     * @return httpAccept
     */
    public java.lang.String getHttpAccept() {
        return httpAccept;
    }


    /**
     * Sets the httpAccept value for this DeviceRequest.
     * 
     * @param httpAccept
     */
    public void setHttpAccept(java.lang.String httpAccept) {
        this.httpAccept = httpAccept;
    }


    /**
     * Gets the httpAcceptChars value for this DeviceRequest.
     * 
     * @return httpAcceptChars
     */
    public java.lang.String getHttpAcceptChars() {
        return httpAcceptChars;
    }


    /**
     * Sets the httpAcceptChars value for this DeviceRequest.
     * 
     * @param httpAcceptChars
     */
    public void setHttpAcceptChars(java.lang.String httpAcceptChars) {
        this.httpAcceptChars = httpAcceptChars;
    }


    /**
     * Gets the httpAcceptEncoding value for this DeviceRequest.
     * 
     * @return httpAcceptEncoding
     */
    public java.lang.String getHttpAcceptEncoding() {
        return httpAcceptEncoding;
    }


    /**
     * Sets the httpAcceptEncoding value for this DeviceRequest.
     * 
     * @param httpAcceptEncoding
     */
    public void setHttpAcceptEncoding(java.lang.String httpAcceptEncoding) {
        this.httpAcceptEncoding = httpAcceptEncoding;
    }


    /**
     * Gets the httpAcceptLanguage value for this DeviceRequest.
     * 
     * @return httpAcceptLanguage
     */
    public java.lang.String getHttpAcceptLanguage() {
        return httpAcceptLanguage;
    }


    /**
     * Sets the httpAcceptLanguage value for this DeviceRequest.
     * 
     * @param httpAcceptLanguage
     */
    public void setHttpAcceptLanguage(java.lang.String httpAcceptLanguage) {
        this.httpAcceptLanguage = httpAcceptLanguage;
    }


    /**
     * Gets the httpReferrer value for this DeviceRequest.
     * 
     * @return httpReferrer
     */
    public java.lang.String getHttpReferrer() {
        return httpReferrer;
    }


    /**
     * Sets the httpReferrer value for this DeviceRequest.
     * 
     * @param httpReferrer
     */
    public void setHttpReferrer(java.lang.String httpReferrer) {
        this.httpReferrer = httpReferrer;
    }


    /**
     * Gets the ipAddress value for this DeviceRequest.
     * 
     * @return ipAddress
     */
    public java.lang.String getIpAddress() {
        return ipAddress;
    }


    /**
     * Sets the ipAddress value for this DeviceRequest.
     * 
     * @param ipAddress
     */
    public void setIpAddress(java.lang.String ipAddress) {
        this.ipAddress = ipAddress;
    }


    /**
     * Gets the userAgent value for this DeviceRequest.
     * 
     * @return userAgent
     */
    public java.lang.String getUserAgent() {
        return userAgent;
    }


    /**
     * Sets the userAgent value for this DeviceRequest.
     * 
     * @param userAgent
     */
    public void setUserAgent(java.lang.String userAgent) {
        this.userAgent = userAgent;
    }


    /**
     * Gets the geoLocation value for this DeviceRequest.
     * 
     * @return geoLocation
     */
    public java.lang.String getGeoLocation() {
        return geoLocation;
    }


    /**
     * Sets the geoLocation value for this DeviceRequest.
     * 
     * @param geoLocation
     */
    public void setGeoLocation(java.lang.String geoLocation) {
        this.geoLocation = geoLocation;
    }


    /**
     * Gets the domElements value for this DeviceRequest.
     * 
     * @return domElements
     */
    public java.lang.String getDomElements() {
        return domElements;
    }


    /**
     * Sets the domElements value for this DeviceRequest.
     * 
     * @param domElements
     */
    public void setDomElements(java.lang.String domElements) {
        this.domElements = domElements;
    }


    /**
     * Gets the jsEvents value for this DeviceRequest.
     * 
     * @return jsEvents
     */
    public java.lang.String getJsEvents() {
        return jsEvents;
    }


    /**
     * Sets the jsEvents value for this DeviceRequest.
     * 
     * @param jsEvents
     */
    public void setJsEvents(java.lang.String jsEvents) {
        this.jsEvents = jsEvents;
    }


    /**
     * Gets the pageId value for this DeviceRequest.
     * 
     * @return pageId
     */
    public java.lang.String getPageId() {
        return pageId;
    }


    /**
     * Sets the pageId value for this DeviceRequest.
     * 
     * @param pageId
     */
    public void setPageId(java.lang.String pageId) {
        this.pageId = pageId;
    }


    /**
     * Gets the deviceIdentifier value for this DeviceRequest.
     * 
     * @return deviceIdentifier
     */
    public com.rssl.phizic.rsa.integration.ws.control.generated.DeviceIdentifier[] getDeviceIdentifier() {
        return deviceIdentifier;
    }


    /**
     * Sets the deviceIdentifier value for this DeviceRequest.
     * 
     * @param deviceIdentifier
     */
    public void setDeviceIdentifier(com.rssl.phizic.rsa.integration.ws.control.generated.DeviceIdentifier[] deviceIdentifier) {
        this.deviceIdentifier = deviceIdentifier;
    }

    public com.rssl.phizic.rsa.integration.ws.control.generated.DeviceIdentifier getDeviceIdentifier(int i) {
        return this.deviceIdentifier[i];
    }

    public void setDeviceIdentifier(int i, com.rssl.phizic.rsa.integration.ws.control.generated.DeviceIdentifier _value) {
        this.deviceIdentifier[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DeviceRequest)) return false;
        DeviceRequest other = (DeviceRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.beaconId==null && other.getBeaconId()==null) || 
             (this.beaconId!=null &&
              this.beaconId.equals(other.getBeaconId()))) &&
            ((this.devicePrint==null && other.getDevicePrint()==null) || 
             (this.devicePrint!=null &&
              this.devicePrint.equals(other.getDevicePrint()))) &&
            ((this.deviceTokenCookie==null && other.getDeviceTokenCookie()==null) || 
             (this.deviceTokenCookie!=null &&
              this.deviceTokenCookie.equals(other.getDeviceTokenCookie()))) &&
            ((this.deviceTokenFSO==null && other.getDeviceTokenFSO()==null) || 
             (this.deviceTokenFSO!=null &&
              this.deviceTokenFSO.equals(other.getDeviceTokenFSO()))) &&
            ((this.httpAccept==null && other.getHttpAccept()==null) || 
             (this.httpAccept!=null &&
              this.httpAccept.equals(other.getHttpAccept()))) &&
            ((this.httpAcceptChars==null && other.getHttpAcceptChars()==null) || 
             (this.httpAcceptChars!=null &&
              this.httpAcceptChars.equals(other.getHttpAcceptChars()))) &&
            ((this.httpAcceptEncoding==null && other.getHttpAcceptEncoding()==null) || 
             (this.httpAcceptEncoding!=null &&
              this.httpAcceptEncoding.equals(other.getHttpAcceptEncoding()))) &&
            ((this.httpAcceptLanguage==null && other.getHttpAcceptLanguage()==null) || 
             (this.httpAcceptLanguage!=null &&
              this.httpAcceptLanguage.equals(other.getHttpAcceptLanguage()))) &&
            ((this.httpReferrer==null && other.getHttpReferrer()==null) || 
             (this.httpReferrer!=null &&
              this.httpReferrer.equals(other.getHttpReferrer()))) &&
            ((this.ipAddress==null && other.getIpAddress()==null) || 
             (this.ipAddress!=null &&
              this.ipAddress.equals(other.getIpAddress()))) &&
            ((this.userAgent==null && other.getUserAgent()==null) || 
             (this.userAgent!=null &&
              this.userAgent.equals(other.getUserAgent()))) &&
            ((this.geoLocation==null && other.getGeoLocation()==null) || 
             (this.geoLocation!=null &&
              this.geoLocation.equals(other.getGeoLocation()))) &&
            ((this.domElements==null && other.getDomElements()==null) || 
             (this.domElements!=null &&
              this.domElements.equals(other.getDomElements()))) &&
            ((this.jsEvents==null && other.getJsEvents()==null) || 
             (this.jsEvents!=null &&
              this.jsEvents.equals(other.getJsEvents()))) &&
            ((this.pageId==null && other.getPageId()==null) || 
             (this.pageId!=null &&
              this.pageId.equals(other.getPageId()))) &&
            ((this.deviceIdentifier==null && other.getDeviceIdentifier()==null) || 
             (this.deviceIdentifier!=null &&
              java.util.Arrays.equals(this.deviceIdentifier, other.getDeviceIdentifier())));
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
        if (getBeaconId() != null) {
            _hashCode += getBeaconId().hashCode();
        }
        if (getDevicePrint() != null) {
            _hashCode += getDevicePrint().hashCode();
        }
        if (getDeviceTokenCookie() != null) {
            _hashCode += getDeviceTokenCookie().hashCode();
        }
        if (getDeviceTokenFSO() != null) {
            _hashCode += getDeviceTokenFSO().hashCode();
        }
        if (getHttpAccept() != null) {
            _hashCode += getHttpAccept().hashCode();
        }
        if (getHttpAcceptChars() != null) {
            _hashCode += getHttpAcceptChars().hashCode();
        }
        if (getHttpAcceptEncoding() != null) {
            _hashCode += getHttpAcceptEncoding().hashCode();
        }
        if (getHttpAcceptLanguage() != null) {
            _hashCode += getHttpAcceptLanguage().hashCode();
        }
        if (getHttpReferrer() != null) {
            _hashCode += getHttpReferrer().hashCode();
        }
        if (getIpAddress() != null) {
            _hashCode += getIpAddress().hashCode();
        }
        if (getUserAgent() != null) {
            _hashCode += getUserAgent().hashCode();
        }
        if (getGeoLocation() != null) {
            _hashCode += getGeoLocation().hashCode();
        }
        if (getDomElements() != null) {
            _hashCode += getDomElements().hashCode();
        }
        if (getJsEvents() != null) {
            _hashCode += getJsEvents().hashCode();
        }
        if (getPageId() != null) {
            _hashCode += getPageId().hashCode();
        }
        if (getDeviceIdentifier() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDeviceIdentifier());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDeviceIdentifier(), i);
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
        new org.apache.axis.description.TypeDesc(DeviceRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "DeviceRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("beaconId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "beaconId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("devicePrint");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "devicePrint"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deviceTokenCookie");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "deviceTokenCookie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deviceTokenFSO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "deviceTokenFSO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("httpAccept");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "httpAccept"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("httpAcceptChars");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "httpAcceptChars"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("httpAcceptEncoding");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "httpAcceptEncoding"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("httpAcceptLanguage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "httpAcceptLanguage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("httpReferrer");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "httpReferrer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ipAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ipAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userAgent");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "userAgent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("geoLocation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "geoLocation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("domElements");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "domElements"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("jsEvents");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "jsEvents"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pageId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "pageId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deviceIdentifier");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "deviceIdentifier"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "DeviceIdentifier"));
        elemField.setMinOccurs(0);
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
