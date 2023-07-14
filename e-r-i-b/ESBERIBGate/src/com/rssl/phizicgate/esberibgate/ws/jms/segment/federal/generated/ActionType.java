
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Действие со счетом
 * 
 * <p>Java class for Action_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Action_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ActionName" type="{}ActionName_Type"/>
 *         &lt;element name="VariantInterestPayment" type="{}VariantInterestPayment_Type" minOccurs="0"/>
 *         &lt;element name="AlterMinRemainder" type="{}AlterMinRemainder_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Action_Type", propOrder = {
    "actionName",
    "variantInterestPayment",
    "alterMinRemainder"
})
public class ActionType {

    @XmlElement(name = "ActionName", required = true)
    protected ActionNameType actionName;
    @XmlElement(name = "VariantInterestPayment")
    protected VariantInterestPaymentType variantInterestPayment;
    @XmlElement(name = "AlterMinRemainder")
    protected AlterMinRemainderType alterMinRemainder;

    /**
     * Gets the value of the actionName property.
     * 
     * @return
     *     possible object is
     *     {@link ActionNameType }
     *     
     */
    public ActionNameType getActionName() {
        return actionName;
    }

    /**
     * Sets the value of the actionName property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActionNameType }
     *     
     */
    public void setActionName(ActionNameType value) {
        this.actionName = value;
    }

    /**
     * Gets the value of the variantInterestPayment property.
     * 
     * @return
     *     possible object is
     *     {@link VariantInterestPaymentType }
     *     
     */
    public VariantInterestPaymentType getVariantInterestPayment() {
        return variantInterestPayment;
    }

    /**
     * Sets the value of the variantInterestPayment property.
     * 
     * @param value
     *     allowed object is
     *     {@link VariantInterestPaymentType }
     *     
     */
    public void setVariantInterestPayment(VariantInterestPaymentType value) {
        this.variantInterestPayment = value;
    }

    /**
     * Gets the value of the alterMinRemainder property.
     * 
     * @return
     *     possible object is
     *     {@link AlterMinRemainderType }
     *     
     */
    public AlterMinRemainderType getAlterMinRemainder() {
        return alterMinRemainder;
    }

    /**
     * Sets the value of the alterMinRemainder property.
     * 
     * @param value
     *     allowed object is
     *     {@link AlterMinRemainderType }
     *     
     */
    public void setAlterMinRemainder(AlterMinRemainderType value) {
        this.alterMinRemainder = value;
    }

}
