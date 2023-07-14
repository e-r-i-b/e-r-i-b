
package com.rssl.phizicgate.mdm.integration.mdm.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CustInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}LegalStatus" minOccurs="0"/>
 *         &lt;element ref="{}PersonInfo"/>
 *         &lt;element ref="{}IntegrationInfo" minOccurs="0"/>
 *         &lt;element ref="{}CustStatus" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustInfo_Type", propOrder = {
    "legalStatus",
    "personInfo",
    "integrationInfo",
    "custStatus"
})
@XmlRootElement(name = "CustInfo")
public class CustInfo {

    @XmlElement(name = "LegalStatus")
    protected String legalStatus;
    @XmlElement(name = "PersonInfo", required = true)
    protected PersonInfo personInfo;
    @XmlElement(name = "IntegrationInfo")
    protected IntegrationInfo integrationInfo;
    @XmlElement(name = "CustStatus")
    protected CustStatus custStatus;

    /**
     * Gets the value of the legalStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLegalStatus() {
        return legalStatus;
    }

    /**
     * Sets the value of the legalStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLegalStatus(String value) {
        this.legalStatus = value;
    }

    /**
     * Gets the value of the personInfo property.
     * 
     * @return
     *     possible object is
     *     {@link PersonInfo }
     *     
     */
    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    /**
     * Sets the value of the personInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonInfo }
     *     
     */
    public void setPersonInfo(PersonInfo value) {
        this.personInfo = value;
    }

    /**
     * Gets the value of the integrationInfo property.
     * 
     * @return
     *     possible object is
     *     {@link IntegrationInfo }
     *     
     */
    public IntegrationInfo getIntegrationInfo() {
        return integrationInfo;
    }

    /**
     * Sets the value of the integrationInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link IntegrationInfo }
     *     
     */
    public void setIntegrationInfo(IntegrationInfo value) {
        this.integrationInfo = value;
    }

    /**
     * Gets the value of the custStatus property.
     * 
     * @return
     *     possible object is
     *     {@link CustStatus }
     *     
     */
    public CustStatus getCustStatus() {
        return custStatus;
    }

    /**
     * Sets the value of the custStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustStatus }
     *     
     */
    public void setCustStatus(CustStatus value) {
        this.custStatus = value;
    }

}
