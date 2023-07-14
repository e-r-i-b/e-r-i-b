
package com.rssl.phizicgate.mdm.integration.mdm.generated;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateTimeAdapter;


/**
 * <p>Java class for PostAddrType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PostAddrType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}AddrId" minOccurs="0"/>
 *         &lt;element ref="{}AddrType"/>
 *         &lt;element ref="{}Addr1" minOccurs="0"/>
 *         &lt;element ref="{}Addr2" minOccurs="0"/>
 *         &lt;element ref="{}Addr3" minOccurs="0"/>
 *         &lt;element ref="{}Addr4" minOccurs="0"/>
 *         &lt;element ref="{}HouseNum" minOccurs="0"/>
 *         &lt;element ref="{}HouseExt" minOccurs="0"/>
 *         &lt;element ref="{}Unit" minOccurs="0"/>
 *         &lt;element ref="{}UnitNum" minOccurs="0"/>
 *         &lt;element ref="{}Area" minOccurs="0"/>
 *         &lt;element ref="{}AreaCode" minOccurs="0"/>
 *         &lt;element ref="{}District" minOccurs="0"/>
 *         &lt;element ref="{}DistrictCode" minOccurs="0"/>
 *         &lt;element ref="{}City" minOccurs="0"/>
 *         &lt;element ref="{}CityCode" minOccurs="0"/>
 *         &lt;element ref="{}Region" minOccurs="0"/>
 *         &lt;element ref="{}RegionCode" minOccurs="0"/>
 *         &lt;element ref="{}PostalCode" minOccurs="0"/>
 *         &lt;element ref="{}Country" minOccurs="0"/>
 *         &lt;element ref="{}StartDt" minOccurs="0"/>
 *         &lt;element ref="{}EndDt" minOccurs="0"/>
 *         &lt;element ref="{}AddrStatus" minOccurs="0"/>
 *         &lt;element ref="{}EffDt" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PostAddrType", propOrder = {
    "addrId",
    "addrType",
    "addr1",
    "addr2",
    "addr3",
    "addr4",
    "houseNum",
    "houseExt",
    "unit",
    "unitNum",
    "area",
    "areaCode",
    "district",
    "districtCode",
    "city",
    "cityCode",
    "region",
    "regionCode",
    "postalCode",
    "country",
    "startDt",
    "endDt",
    "addrStatus",
    "effDt"
})
@XmlRootElement(name = "PostAddr")
public class PostAddr {

    @XmlElement(name = "AddrId")
    protected String addrId;
    @XmlElement(name = "AddrType", required = true)
    protected String addrType;
    @XmlElement(name = "Addr1")
    protected String addr1;
    @XmlElement(name = "Addr2")
    protected String addr2;
    @XmlElement(name = "Addr3")
    protected String addr3;
    @XmlElement(name = "Addr4")
    protected String addr4;
    @XmlElement(name = "HouseNum")
    protected String houseNum;
    @XmlElement(name = "HouseExt")
    protected String houseExt;
    @XmlElement(name = "Unit")
    protected String unit;
    @XmlElement(name = "UnitNum")
    protected String unitNum;
    @XmlElement(name = "Area")
    protected String area;
    @XmlElement(name = "AreaCode")
    protected String areaCode;
    @XmlElement(name = "District")
    protected String district;
    @XmlElement(name = "DistrictCode")
    protected String districtCode;
    @XmlElement(name = "City")
    protected String city;
    @XmlElement(name = "CityCode")
    protected String cityCode;
    @XmlElement(name = "Region")
    protected String region;
    @XmlElement(name = "RegionCode")
    protected String regionCode;
    @XmlElement(name = "PostalCode")
    protected String postalCode;
    @XmlElement(name = "Country")
    protected String country;
    @XmlElement(name = "StartDt", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeAdapter.class)
    protected Calendar startDt;
    @XmlElement(name = "EndDt", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeAdapter.class)
    protected Calendar endDt;
    @XmlElement(name = "AddrStatus")
    protected String addrStatus;
    @XmlElement(name = "EffDt", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeAdapter.class)
    protected Calendar effDt;

    /**
     * Gets the value of the addrId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddrId() {
        return addrId;
    }

    /**
     * Sets the value of the addrId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddrId(String value) {
        this.addrId = value;
    }

    /**
     * Gets the value of the addrType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddrType() {
        return addrType;
    }

    /**
     * Sets the value of the addrType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddrType(String value) {
        this.addrType = value;
    }

    /**
     * Gets the value of the addr1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddr1() {
        return addr1;
    }

    /**
     * Sets the value of the addr1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddr1(String value) {
        this.addr1 = value;
    }

    /**
     * Gets the value of the addr2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddr2() {
        return addr2;
    }

    /**
     * Sets the value of the addr2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddr2(String value) {
        this.addr2 = value;
    }

    /**
     * Gets the value of the addr3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddr3() {
        return addr3;
    }

    /**
     * Sets the value of the addr3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddr3(String value) {
        this.addr3 = value;
    }

    /**
     * Gets the value of the addr4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddr4() {
        return addr4;
    }

    /**
     * Sets the value of the addr4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddr4(String value) {
        this.addr4 = value;
    }

    /**
     * Gets the value of the houseNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHouseNum() {
        return houseNum;
    }

    /**
     * Sets the value of the houseNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHouseNum(String value) {
        this.houseNum = value;
    }

    /**
     * Gets the value of the houseExt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHouseExt() {
        return houseExt;
    }

    /**
     * Sets the value of the houseExt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHouseExt(String value) {
        this.houseExt = value;
    }

    /**
     * Gets the value of the unit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Sets the value of the unit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnit(String value) {
        this.unit = value;
    }

    /**
     * Gets the value of the unitNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitNum() {
        return unitNum;
    }

    /**
     * Sets the value of the unitNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitNum(String value) {
        this.unitNum = value;
    }

    /**
     * Gets the value of the area property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArea() {
        return area;
    }

    /**
     * Sets the value of the area property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArea(String value) {
        this.area = value;
    }

    /**
     * Gets the value of the areaCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * Sets the value of the areaCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAreaCode(String value) {
        this.areaCode = value;
    }

    /**
     * Gets the value of the district property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistrict() {
        return district;
    }

    /**
     * Sets the value of the district property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistrict(String value) {
        this.district = value;
    }

    /**
     * Gets the value of the districtCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistrictCode() {
        return districtCode;
    }

    /**
     * Sets the value of the districtCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistrictCode(String value) {
        this.districtCode = value;
    }

    /**
     * Gets the value of the city property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the value of the city property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCity(String value) {
        this.city = value;
    }

    /**
     * Gets the value of the cityCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCityCode() {
        return cityCode;
    }

    /**
     * Sets the value of the cityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCityCode(String value) {
        this.cityCode = value;
    }

    /**
     * Gets the value of the region property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegion() {
        return region;
    }

    /**
     * Sets the value of the region property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegion(String value) {
        this.region = value;
    }

    /**
     * Gets the value of the regionCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegionCode() {
        return regionCode;
    }

    /**
     * Sets the value of the regionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegionCode(String value) {
        this.regionCode = value;
    }

    /**
     * Gets the value of the postalCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the value of the postalCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostalCode(String value) {
        this.postalCode = value;
    }

    /**
     * Gets the value of the country property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountry(String value) {
        this.country = value;
    }

    /**
     * Gets the value of the startDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getStartDt() {
        return startDt;
    }

    /**
     * Sets the value of the startDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartDt(Calendar value) {
        this.startDt = value;
    }

    /**
     * Gets the value of the endDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getEndDt() {
        return endDt;
    }

    /**
     * Sets the value of the endDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndDt(Calendar value) {
        this.endDt = value;
    }

    /**
     * Gets the value of the addrStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddrStatus() {
        return addrStatus;
    }

    /**
     * Sets the value of the addrStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddrStatus(String value) {
        this.addrStatus = value;
    }

    /**
     * Gets the value of the effDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getEffDt() {
        return effDt;
    }

    /**
     * Sets the value of the effDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEffDt(Calendar value) {
        this.effDt = value;
    }

}
