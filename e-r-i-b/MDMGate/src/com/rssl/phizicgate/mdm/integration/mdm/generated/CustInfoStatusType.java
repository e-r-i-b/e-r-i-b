
package com.rssl.phizicgate.mdm.integration.mdm.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CustInfoStatus_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustInfoStatus_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}IntegrationInfo"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustInfoStatus_Type", propOrder = {
    "integrationInfo"
})
public class CustInfoStatusType {

    @XmlElement(name = "IntegrationInfo", required = true)
    protected IntegrationInfo integrationInfo;

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
