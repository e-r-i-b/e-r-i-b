
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Тип Запись о подписке автоплатежа
 * 
 * <p>Java class for AutoSubscriptionRec_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AutoSubscriptionRec_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AutoSubscriptionId" type="{}AutoSubscriptionId_Type" minOccurs="0"/>
 *         &lt;element name="AutoSubscriptionInfo" type="{}AutoSubscriptionInfo_Type" minOccurs="0"/>
 *         &lt;element name="AutoPaymentTemplate" type="{}AutoPaymentTemplate_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AutoSubscriptionRec_Type", propOrder = {
    "autoSubscriptionId",
    "autoSubscriptionInfo",
    "autoPaymentTemplate"
})
@XmlRootElement(name = "AutoSubscriptionRec")
public class AutoSubscriptionRec {

    @XmlElement(name = "AutoSubscriptionId")
    protected AutoSubscriptionIdType autoSubscriptionId;
    @XmlElement(name = "AutoSubscriptionInfo")
    protected AutoSubscriptionInfoType autoSubscriptionInfo;
    @XmlElement(name = "AutoPaymentTemplate")
    protected AutoPaymentTemplateType autoPaymentTemplate;

    /**
     * Gets the value of the autoSubscriptionId property.
     * 
     * @return
     *     possible object is
     *     {@link AutoSubscriptionIdType }
     *     
     */
    public AutoSubscriptionIdType getAutoSubscriptionId() {
        return autoSubscriptionId;
    }

    /**
     * Sets the value of the autoSubscriptionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link AutoSubscriptionIdType }
     *     
     */
    public void setAutoSubscriptionId(AutoSubscriptionIdType value) {
        this.autoSubscriptionId = value;
    }

    /**
     * Gets the value of the autoSubscriptionInfo property.
     * 
     * @return
     *     possible object is
     *     {@link AutoSubscriptionInfoType }
     *     
     */
    public AutoSubscriptionInfoType getAutoSubscriptionInfo() {
        return autoSubscriptionInfo;
    }

    /**
     * Sets the value of the autoSubscriptionInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link AutoSubscriptionInfoType }
     *     
     */
    public void setAutoSubscriptionInfo(AutoSubscriptionInfoType value) {
        this.autoSubscriptionInfo = value;
    }

    /**
     * Gets the value of the autoPaymentTemplate property.
     * 
     * @return
     *     possible object is
     *     {@link AutoPaymentTemplateType }
     *     
     */
    public AutoPaymentTemplateType getAutoPaymentTemplate() {
        return autoPaymentTemplate;
    }

    /**
     * Sets the value of the autoPaymentTemplate property.
     * 
     * @param value
     *     allowed object is
     *     {@link AutoPaymentTemplateType }
     *     
     */
    public void setAutoPaymentTemplate(AutoPaymentTemplateType value) {
        this.autoPaymentTemplate = value;
    }

}
