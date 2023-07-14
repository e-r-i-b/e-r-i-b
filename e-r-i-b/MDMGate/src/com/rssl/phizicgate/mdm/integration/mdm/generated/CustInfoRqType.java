
package com.rssl.phizicgate.mdm.integration.mdm.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CustInfoRq_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustInfoRq_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PersonInfo" type="{}PersonInfoRq_Type"/>
 *         &lt;element ref="{}IntegrationInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustInfoRq_Type", propOrder = {
    "personInfo",
    "integrationInfo"
})
public class CustInfoRqType {

    @XmlElement(name = "PersonInfo", required = true)
    protected PersonInfoRqType personInfo;
    @XmlElement(name = "IntegrationInfo")
    protected IntegrationInfo integrationInfo;

    /**
     * Gets the value of the personInfo property.
     * 
     * @return
     *     possible object is
     *     {@link PersonInfoRqType }
     *     
     */
    public PersonInfoRqType getPersonInfo() {
        return personInfo;
    }

    /**
     * Sets the value of the personInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonInfoRqType }
     *     
     */
    public void setPersonInfo(PersonInfoRqType value) {
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

}
