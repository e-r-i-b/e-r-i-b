
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Информация о полном адресе
 * 
 * <p>Java class for FullAddress_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FullAddress_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AddrType" type="{}AddressType_Type"/>
 *         &lt;element name="Addr3" type="{}String" minOccurs="0"/>
 *         &lt;element name="Country" type="{}String" minOccurs="0"/>
 *         &lt;element name="PostalCode" type="{}PostalCode_Type" minOccurs="0"/>
 *         &lt;element name="Region" type="{}String" minOccurs="0"/>
 *         &lt;element name="Area" type="{}String" minOccurs="0"/>
 *         &lt;element name="City" type="{}String" minOccurs="0"/>
 *         &lt;element name="Settlement" type="{}String" minOccurs="0"/>
 *         &lt;element name="Addr1" type="{}String" minOccurs="0"/>
 *         &lt;element name="HouseNum" type="{}String" minOccurs="0"/>
 *         &lt;element name="HouseExt" type="{}String" minOccurs="0"/>
 *         &lt;element name="Unit" type="{}String" minOccurs="0"/>
 *         &lt;element name="UnitNum" type="{}String" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FullAddress_Type", propOrder = {
    "addrType",
    "addr3",
    "country",
    "postalCode",
    "region",
    "area",
    "city",
    "settlement",
    "addr1",
    "houseNum",
    "houseExt",
    "unit",
    "unitNum"
})
public class FullAddressType {

    @XmlElement(name = "AddrType", required = true)
    protected String addrType;
    @XmlElement(name = "Addr3")
    protected String addr3;
    @XmlElement(name = "Country")
    protected String country;
    @XmlElement(name = "PostalCode")
    protected String postalCode;
    @XmlElement(name = "Region")
    protected String region;
    @XmlElement(name = "Area")
    protected String area;
    @XmlElement(name = "City")
    protected String city;
    @XmlElement(name = "Settlement")
    protected String settlement;
    @XmlElement(name = "Addr1")
    protected String addr1;
    @XmlElement(name = "HouseNum")
    protected String houseNum;
    @XmlElement(name = "HouseExt")
    protected String houseExt;
    @XmlElement(name = "Unit")
    protected String unit;
    @XmlElement(name = "UnitNum")
    protected String unitNum;

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
     * Gets the value of the settlement property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSettlement() {
        return settlement;
    }

    /**
     * Sets the value of the settlement property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSettlement(String value) {
        this.settlement = value;
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

}
