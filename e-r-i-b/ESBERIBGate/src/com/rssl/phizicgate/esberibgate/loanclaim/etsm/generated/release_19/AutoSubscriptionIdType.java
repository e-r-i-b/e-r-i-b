
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Тип Идентификационная информация о подписке.
 * 
 * <p>Java class for AutoSubscriptionId_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AutoSubscriptionId_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}SystemId"/>
 *         &lt;element name="AutopayId" type="{}Long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AutoSubscriptionId_Type", propOrder = {
    "systemId",
    "autopayId"
})
public class AutoSubscriptionIdType {

    @XmlElement(name = "SystemId", required = true)
    protected String systemId;
    @XmlElement(name = "AutopayId")
    protected long autopayId;

    /**
     * Gets the value of the systemId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSystemId() {
        return systemId;
    }

    /**
     * Sets the value of the systemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSystemId(String value) {
        this.systemId = value;
    }

    /**
     * Gets the value of the autopayId property.
     * 
     */
    public long getAutopayId() {
        return autopayId;
    }

    /**
     * Sets the value of the autopayId property.
     * 
     */
    public void setAutopayId(long value) {
        this.autopayId = value;
    }

}
