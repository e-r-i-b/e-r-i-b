/**
 * MobileDevice.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated;

public class MobileDevice  extends com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.DeviceIdentifier  implements java.io.Serializable {
    private java.lang.String simId;

    private java.lang.String otherId;

    private java.lang.String hardwareId;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.GeoLocation geoLocation;

    private java.lang.String deviceModel;

    private java.lang.String deviceMultiTaskingSupported;

    private java.lang.String deviceName;

    private java.lang.String deviceSystemName;

    private java.lang.String deviceSystemVersion;

    private java.lang.String languages;

    private java.lang.String wiFiMacAddress;

    private com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.WiFiNetworkData wiFiNetworksData;

    private java.lang.String cellTowerID;

    private java.lang.String locationAreaCode;

    private java.lang.String screenSize;

    private java.lang.Integer numberOfAddressBookEntries;

    private java.lang.String rsaApplicationkey;

    private java.lang.String wapClientID;

    private java.lang.String vendorClientID;

    private java.lang.String mcc;

    private java.lang.String mnc;

    private java.lang.String osId;

    private java.lang.String mobileSdkData;

    public MobileDevice() {
    }

    public MobileDevice(
           java.lang.String simId,
           java.lang.String otherId,
           java.lang.String hardwareId,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.GeoLocation geoLocation,
           java.lang.String deviceModel,
           java.lang.String deviceMultiTaskingSupported,
           java.lang.String deviceName,
           java.lang.String deviceSystemName,
           java.lang.String deviceSystemVersion,
           java.lang.String languages,
           java.lang.String wiFiMacAddress,
           com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.WiFiNetworkData wiFiNetworksData,
           java.lang.String cellTowerID,
           java.lang.String locationAreaCode,
           java.lang.String screenSize,
           java.lang.Integer numberOfAddressBookEntries,
           java.lang.String rsaApplicationkey,
           java.lang.String wapClientID,
           java.lang.String vendorClientID,
           java.lang.String mcc,
           java.lang.String mnc,
           java.lang.String osId,
           java.lang.String mobileSdkData) {
        this.simId = simId;
        this.otherId = otherId;
        this.hardwareId = hardwareId;
        this.geoLocation = geoLocation;
        this.deviceModel = deviceModel;
        this.deviceMultiTaskingSupported = deviceMultiTaskingSupported;
        this.deviceName = deviceName;
        this.deviceSystemName = deviceSystemName;
        this.deviceSystemVersion = deviceSystemVersion;
        this.languages = languages;
        this.wiFiMacAddress = wiFiMacAddress;
        this.wiFiNetworksData = wiFiNetworksData;
        this.cellTowerID = cellTowerID;
        this.locationAreaCode = locationAreaCode;
        this.screenSize = screenSize;
        this.numberOfAddressBookEntries = numberOfAddressBookEntries;
        this.rsaApplicationkey = rsaApplicationkey;
        this.wapClientID = wapClientID;
        this.vendorClientID = vendorClientID;
        this.mcc = mcc;
        this.mnc = mnc;
        this.osId = osId;
        this.mobileSdkData = mobileSdkData;
    }


    /**
     * Gets the simId value for this MobileDevice.
     * 
     * @return simId
     */
    public java.lang.String getSimId() {
        return simId;
    }


    /**
     * Sets the simId value for this MobileDevice.
     * 
     * @param simId
     */
    public void setSimId(java.lang.String simId) {
        this.simId = simId;
    }


    /**
     * Gets the otherId value for this MobileDevice.
     * 
     * @return otherId
     */
    public java.lang.String getOtherId() {
        return otherId;
    }


    /**
     * Sets the otherId value for this MobileDevice.
     * 
     * @param otherId
     */
    public void setOtherId(java.lang.String otherId) {
        this.otherId = otherId;
    }


    /**
     * Gets the hardwareId value for this MobileDevice.
     * 
     * @return hardwareId
     */
    public java.lang.String getHardwareId() {
        return hardwareId;
    }


    /**
     * Sets the hardwareId value for this MobileDevice.
     * 
     * @param hardwareId
     */
    public void setHardwareId(java.lang.String hardwareId) {
        this.hardwareId = hardwareId;
    }


    /**
     * Gets the geoLocation value for this MobileDevice.
     * 
     * @return geoLocation
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.GeoLocation getGeoLocation() {
        return geoLocation;
    }


    /**
     * Sets the geoLocation value for this MobileDevice.
     * 
     * @param geoLocation
     */
    public void setGeoLocation(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }


    /**
     * Gets the deviceModel value for this MobileDevice.
     * 
     * @return deviceModel
     */
    public java.lang.String getDeviceModel() {
        return deviceModel;
    }


    /**
     * Sets the deviceModel value for this MobileDevice.
     * 
     * @param deviceModel
     */
    public void setDeviceModel(java.lang.String deviceModel) {
        this.deviceModel = deviceModel;
    }


    /**
     * Gets the deviceMultiTaskingSupported value for this MobileDevice.
     * 
     * @return deviceMultiTaskingSupported
     */
    public java.lang.String getDeviceMultiTaskingSupported() {
        return deviceMultiTaskingSupported;
    }


    /**
     * Sets the deviceMultiTaskingSupported value for this MobileDevice.
     * 
     * @param deviceMultiTaskingSupported
     */
    public void setDeviceMultiTaskingSupported(java.lang.String deviceMultiTaskingSupported) {
        this.deviceMultiTaskingSupported = deviceMultiTaskingSupported;
    }


    /**
     * Gets the deviceName value for this MobileDevice.
     * 
     * @return deviceName
     */
    public java.lang.String getDeviceName() {
        return deviceName;
    }


    /**
     * Sets the deviceName value for this MobileDevice.
     * 
     * @param deviceName
     */
    public void setDeviceName(java.lang.String deviceName) {
        this.deviceName = deviceName;
    }


    /**
     * Gets the deviceSystemName value for this MobileDevice.
     * 
     * @return deviceSystemName
     */
    public java.lang.String getDeviceSystemName() {
        return deviceSystemName;
    }


    /**
     * Sets the deviceSystemName value for this MobileDevice.
     * 
     * @param deviceSystemName
     */
    public void setDeviceSystemName(java.lang.String deviceSystemName) {
        this.deviceSystemName = deviceSystemName;
    }


    /**
     * Gets the deviceSystemVersion value for this MobileDevice.
     * 
     * @return deviceSystemVersion
     */
    public java.lang.String getDeviceSystemVersion() {
        return deviceSystemVersion;
    }


    /**
     * Sets the deviceSystemVersion value for this MobileDevice.
     * 
     * @param deviceSystemVersion
     */
    public void setDeviceSystemVersion(java.lang.String deviceSystemVersion) {
        this.deviceSystemVersion = deviceSystemVersion;
    }


    /**
     * Gets the languages value for this MobileDevice.
     * 
     * @return languages
     */
    public java.lang.String getLanguages() {
        return languages;
    }


    /**
     * Sets the languages value for this MobileDevice.
     * 
     * @param languages
     */
    public void setLanguages(java.lang.String languages) {
        this.languages = languages;
    }


    /**
     * Gets the wiFiMacAddress value for this MobileDevice.
     * 
     * @return wiFiMacAddress
     */
    public java.lang.String getWiFiMacAddress() {
        return wiFiMacAddress;
    }


    /**
     * Sets the wiFiMacAddress value for this MobileDevice.
     * 
     * @param wiFiMacAddress
     */
    public void setWiFiMacAddress(java.lang.String wiFiMacAddress) {
        this.wiFiMacAddress = wiFiMacAddress;
    }


    /**
     * Gets the wiFiNetworksData value for this MobileDevice.
     * 
     * @return wiFiNetworksData
     */
    public com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.WiFiNetworkData getWiFiNetworksData() {
        return wiFiNetworksData;
    }


    /**
     * Sets the wiFiNetworksData value for this MobileDevice.
     * 
     * @param wiFiNetworksData
     */
    public void setWiFiNetworksData(com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.WiFiNetworkData wiFiNetworksData) {
        this.wiFiNetworksData = wiFiNetworksData;
    }


    /**
     * Gets the cellTowerID value for this MobileDevice.
     * 
     * @return cellTowerID
     */
    public java.lang.String getCellTowerID() {
        return cellTowerID;
    }


    /**
     * Sets the cellTowerID value for this MobileDevice.
     * 
     * @param cellTowerID
     */
    public void setCellTowerID(java.lang.String cellTowerID) {
        this.cellTowerID = cellTowerID;
    }


    /**
     * Gets the locationAreaCode value for this MobileDevice.
     * 
     * @return locationAreaCode
     */
    public java.lang.String getLocationAreaCode() {
        return locationAreaCode;
    }


    /**
     * Sets the locationAreaCode value for this MobileDevice.
     * 
     * @param locationAreaCode
     */
    public void setLocationAreaCode(java.lang.String locationAreaCode) {
        this.locationAreaCode = locationAreaCode;
    }


    /**
     * Gets the screenSize value for this MobileDevice.
     * 
     * @return screenSize
     */
    public java.lang.String getScreenSize() {
        return screenSize;
    }


    /**
     * Sets the screenSize value for this MobileDevice.
     * 
     * @param screenSize
     */
    public void setScreenSize(java.lang.String screenSize) {
        this.screenSize = screenSize;
    }


    /**
     * Gets the numberOfAddressBookEntries value for this MobileDevice.
     * 
     * @return numberOfAddressBookEntries
     */
    public java.lang.Integer getNumberOfAddressBookEntries() {
        return numberOfAddressBookEntries;
    }


    /**
     * Sets the numberOfAddressBookEntries value for this MobileDevice.
     * 
     * @param numberOfAddressBookEntries
     */
    public void setNumberOfAddressBookEntries(java.lang.Integer numberOfAddressBookEntries) {
        this.numberOfAddressBookEntries = numberOfAddressBookEntries;
    }


    /**
     * Gets the rsaApplicationkey value for this MobileDevice.
     * 
     * @return rsaApplicationkey
     */
    public java.lang.String getRsaApplicationkey() {
        return rsaApplicationkey;
    }


    /**
     * Sets the rsaApplicationkey value for this MobileDevice.
     * 
     * @param rsaApplicationkey
     */
    public void setRsaApplicationkey(java.lang.String rsaApplicationkey) {
        this.rsaApplicationkey = rsaApplicationkey;
    }


    /**
     * Gets the wapClientID value for this MobileDevice.
     * 
     * @return wapClientID
     */
    public java.lang.String getWapClientID() {
        return wapClientID;
    }


    /**
     * Sets the wapClientID value for this MobileDevice.
     * 
     * @param wapClientID
     */
    public void setWapClientID(java.lang.String wapClientID) {
        this.wapClientID = wapClientID;
    }


    /**
     * Gets the vendorClientID value for this MobileDevice.
     * 
     * @return vendorClientID
     */
    public java.lang.String getVendorClientID() {
        return vendorClientID;
    }


    /**
     * Sets the vendorClientID value for this MobileDevice.
     * 
     * @param vendorClientID
     */
    public void setVendorClientID(java.lang.String vendorClientID) {
        this.vendorClientID = vendorClientID;
    }


    /**
     * Gets the mcc value for this MobileDevice.
     * 
     * @return mcc
     */
    public java.lang.String getMcc() {
        return mcc;
    }


    /**
     * Sets the mcc value for this MobileDevice.
     * 
     * @param mcc
     */
    public void setMcc(java.lang.String mcc) {
        this.mcc = mcc;
    }


    /**
     * Gets the mnc value for this MobileDevice.
     * 
     * @return mnc
     */
    public java.lang.String getMnc() {
        return mnc;
    }


    /**
     * Sets the mnc value for this MobileDevice.
     * 
     * @param mnc
     */
    public void setMnc(java.lang.String mnc) {
        this.mnc = mnc;
    }


    /**
     * Gets the osId value for this MobileDevice.
     * 
     * @return osId
     */
    public java.lang.String getOsId() {
        return osId;
    }


    /**
     * Sets the osId value for this MobileDevice.
     * 
     * @param osId
     */
    public void setOsId(java.lang.String osId) {
        this.osId = osId;
    }


    /**
     * Gets the mobileSdkData value for this MobileDevice.
     * 
     * @return mobileSdkData
     */
    public java.lang.String getMobileSdkData() {
        return mobileSdkData;
    }


    /**
     * Sets the mobileSdkData value for this MobileDevice.
     * 
     * @param mobileSdkData
     */
    public void setMobileSdkData(java.lang.String mobileSdkData) {
        this.mobileSdkData = mobileSdkData;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MobileDevice)) return false;
        MobileDevice other = (MobileDevice) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.simId==null && other.getSimId()==null) || 
             (this.simId!=null &&
              this.simId.equals(other.getSimId()))) &&
            ((this.otherId==null && other.getOtherId()==null) || 
             (this.otherId!=null &&
              this.otherId.equals(other.getOtherId()))) &&
            ((this.hardwareId==null && other.getHardwareId()==null) || 
             (this.hardwareId!=null &&
              this.hardwareId.equals(other.getHardwareId()))) &&
            ((this.geoLocation==null && other.getGeoLocation()==null) || 
             (this.geoLocation!=null &&
              this.geoLocation.equals(other.getGeoLocation()))) &&
            ((this.deviceModel==null && other.getDeviceModel()==null) || 
             (this.deviceModel!=null &&
              this.deviceModel.equals(other.getDeviceModel()))) &&
            ((this.deviceMultiTaskingSupported==null && other.getDeviceMultiTaskingSupported()==null) || 
             (this.deviceMultiTaskingSupported!=null &&
              this.deviceMultiTaskingSupported.equals(other.getDeviceMultiTaskingSupported()))) &&
            ((this.deviceName==null && other.getDeviceName()==null) || 
             (this.deviceName!=null &&
              this.deviceName.equals(other.getDeviceName()))) &&
            ((this.deviceSystemName==null && other.getDeviceSystemName()==null) || 
             (this.deviceSystemName!=null &&
              this.deviceSystemName.equals(other.getDeviceSystemName()))) &&
            ((this.deviceSystemVersion==null && other.getDeviceSystemVersion()==null) || 
             (this.deviceSystemVersion!=null &&
              this.deviceSystemVersion.equals(other.getDeviceSystemVersion()))) &&
            ((this.languages==null && other.getLanguages()==null) || 
             (this.languages!=null &&
              this.languages.equals(other.getLanguages()))) &&
            ((this.wiFiMacAddress==null && other.getWiFiMacAddress()==null) || 
             (this.wiFiMacAddress!=null &&
              this.wiFiMacAddress.equals(other.getWiFiMacAddress()))) &&
            ((this.wiFiNetworksData==null && other.getWiFiNetworksData()==null) || 
             (this.wiFiNetworksData!=null &&
              this.wiFiNetworksData.equals(other.getWiFiNetworksData()))) &&
            ((this.cellTowerID==null && other.getCellTowerID()==null) || 
             (this.cellTowerID!=null &&
              this.cellTowerID.equals(other.getCellTowerID()))) &&
            ((this.locationAreaCode==null && other.getLocationAreaCode()==null) || 
             (this.locationAreaCode!=null &&
              this.locationAreaCode.equals(other.getLocationAreaCode()))) &&
            ((this.screenSize==null && other.getScreenSize()==null) || 
             (this.screenSize!=null &&
              this.screenSize.equals(other.getScreenSize()))) &&
            ((this.numberOfAddressBookEntries==null && other.getNumberOfAddressBookEntries()==null) || 
             (this.numberOfAddressBookEntries!=null &&
              this.numberOfAddressBookEntries.equals(other.getNumberOfAddressBookEntries()))) &&
            ((this.rsaApplicationkey==null && other.getRsaApplicationkey()==null) || 
             (this.rsaApplicationkey!=null &&
              this.rsaApplicationkey.equals(other.getRsaApplicationkey()))) &&
            ((this.wapClientID==null && other.getWapClientID()==null) || 
             (this.wapClientID!=null &&
              this.wapClientID.equals(other.getWapClientID()))) &&
            ((this.vendorClientID==null && other.getVendorClientID()==null) || 
             (this.vendorClientID!=null &&
              this.vendorClientID.equals(other.getVendorClientID()))) &&
            ((this.mcc==null && other.getMcc()==null) || 
             (this.mcc!=null &&
              this.mcc.equals(other.getMcc()))) &&
            ((this.mnc==null && other.getMnc()==null) || 
             (this.mnc!=null &&
              this.mnc.equals(other.getMnc()))) &&
            ((this.osId==null && other.getOsId()==null) || 
             (this.osId!=null &&
              this.osId.equals(other.getOsId()))) &&
            ((this.mobileSdkData==null && other.getMobileSdkData()==null) || 
             (this.mobileSdkData!=null &&
              this.mobileSdkData.equals(other.getMobileSdkData())));
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
        if (getSimId() != null) {
            _hashCode += getSimId().hashCode();
        }
        if (getOtherId() != null) {
            _hashCode += getOtherId().hashCode();
        }
        if (getHardwareId() != null) {
            _hashCode += getHardwareId().hashCode();
        }
        if (getGeoLocation() != null) {
            _hashCode += getGeoLocation().hashCode();
        }
        if (getDeviceModel() != null) {
            _hashCode += getDeviceModel().hashCode();
        }
        if (getDeviceMultiTaskingSupported() != null) {
            _hashCode += getDeviceMultiTaskingSupported().hashCode();
        }
        if (getDeviceName() != null) {
            _hashCode += getDeviceName().hashCode();
        }
        if (getDeviceSystemName() != null) {
            _hashCode += getDeviceSystemName().hashCode();
        }
        if (getDeviceSystemVersion() != null) {
            _hashCode += getDeviceSystemVersion().hashCode();
        }
        if (getLanguages() != null) {
            _hashCode += getLanguages().hashCode();
        }
        if (getWiFiMacAddress() != null) {
            _hashCode += getWiFiMacAddress().hashCode();
        }
        if (getWiFiNetworksData() != null) {
            _hashCode += getWiFiNetworksData().hashCode();
        }
        if (getCellTowerID() != null) {
            _hashCode += getCellTowerID().hashCode();
        }
        if (getLocationAreaCode() != null) {
            _hashCode += getLocationAreaCode().hashCode();
        }
        if (getScreenSize() != null) {
            _hashCode += getScreenSize().hashCode();
        }
        if (getNumberOfAddressBookEntries() != null) {
            _hashCode += getNumberOfAddressBookEntries().hashCode();
        }
        if (getRsaApplicationkey() != null) {
            _hashCode += getRsaApplicationkey().hashCode();
        }
        if (getWapClientID() != null) {
            _hashCode += getWapClientID().hashCode();
        }
        if (getVendorClientID() != null) {
            _hashCode += getVendorClientID().hashCode();
        }
        if (getMcc() != null) {
            _hashCode += getMcc().hashCode();
        }
        if (getMnc() != null) {
            _hashCode += getMnc().hashCode();
        }
        if (getOsId() != null) {
            _hashCode += getOsId().hashCode();
        }
        if (getMobileSdkData() != null) {
            _hashCode += getMobileSdkData().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MobileDevice.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "MobileDevice"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("simId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "simId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("otherId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "otherId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hardwareId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "hardwareId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("geoLocation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "geoLocation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "GeoLocation"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deviceModel");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "deviceModel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deviceMultiTaskingSupported");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "deviceMultiTaskingSupported"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deviceName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "deviceName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deviceSystemName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "deviceSystemName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deviceSystemVersion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "deviceSystemVersion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("languages");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "languages"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wiFiMacAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "wiFiMacAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wiFiNetworksData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "wiFiNetworksData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "WiFiNetworkData"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cellTowerID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "cellTowerID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("locationAreaCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "locationAreaCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("screenSize");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "screenSize"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numberOfAddressBookEntries");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "numberOfAddressBookEntries"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rsaApplicationkey");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "rsaApplicationkey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wapClientID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "wapClientID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vendorClientID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "vendorClientID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mcc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "mcc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mnc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "mnc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("osId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "osId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mobileSdkData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "mobileSdkData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
