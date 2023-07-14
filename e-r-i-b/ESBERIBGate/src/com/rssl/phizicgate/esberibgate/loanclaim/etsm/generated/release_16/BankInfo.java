
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Этот агрегат содержит дополнительную информацию для идентификации банка.
 * 
 * <p>Java class for BankInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BankInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}BranchId" minOccurs="0"/>
 *         &lt;element ref="{}AgencyId" minOccurs="0"/>
 *         &lt;element ref="{}RegionId" minOccurs="0"/>
 *         &lt;element ref="{}RbTbBrchId" minOccurs="0"/>
 *         &lt;element ref="{}RbBrchId" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BankInfo_Type", propOrder = {
    "branchId",
    "agencyId",
    "regionId",
    "rbTbBrchId",
    "rbBrchId"
})
@XmlRootElement(name = "BankInfo")
public class BankInfo {

    @XmlElement(name = "BranchId")
    protected String branchId;
    @XmlElement(name = "AgencyId")
    protected String agencyId;
    @XmlElement(name = "RegionId")
    protected String regionId;
    @XmlElement(name = "RbTbBrchId")
    protected String rbTbBrchId;
    @XmlElement(name = "RbBrchId")
    protected String rbBrchId;

    /**
     * Номер филиала.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBranchId() {
        return branchId;
    }

    /**
     * Sets the value of the branchId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBranchId(String value) {
        this.branchId = value;
    }

    /**
     * Номер отделения.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgencyId() {
        return agencyId;
    }

    /**
     * Sets the value of the agencyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgencyId(String value) {
        this.agencyId = value;
    }

    /**
     * Номер террбанка.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegionId() {
        return regionId;
    }

    /**
     * Sets the value of the regionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegionId(String value) {
        this.regionId = value;
    }

    /**
     *  8-ный код территориального банка, в котором открыт счет МБК, по которой клиент произвел идентификацию на УС
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRbTbBrchId() {
        return rbTbBrchId;
    }

    /**
     * Sets the value of the rbTbBrchId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRbTbBrchId(String value) {
        this.rbTbBrchId = value;
    }

    /**
     *  6-ный код территориального банка, в котором ведется продукт клиента (кредит, вклад, ОМС, карты)
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRbBrchId() {
        return rbBrchId;
    }

    /**
     * Sets the value of the rbBrchId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRbBrchId(String value) {
        this.rbBrchId = value;
    }

}
