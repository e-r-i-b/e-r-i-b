
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Список задолженностей по АП
 * 
 * <p>Java class for PaymentStatusInfoList_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PaymentStatusInfoList_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AutoPaymentId" type="{}AutoPaymentId_Type"/>
 *         &lt;element name="Status" type="{}Status_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentStatusInfoList_Type", propOrder = {
    "autoPaymentId",
    "status"
})
public class PaymentStatusInfoListType {

    @XmlElement(name = "AutoPaymentId", required = true)
    protected AutoPaymentIdType autoPaymentId;
    @XmlElement(name = "Status")
    protected StatusType status;

    /**
     * Gets the value of the autoPaymentId property.
     * 
     * @return
     *     possible object is
     *     {@link AutoPaymentIdType }
     *     
     */
    public AutoPaymentIdType getAutoPaymentId() {
        return autoPaymentId;
    }

    /**
     * Sets the value of the autoPaymentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link AutoPaymentIdType }
     *     
     */
    public void setAutoPaymentId(AutoPaymentIdType value) {
        this.autoPaymentId = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link StatusType }
     *     
     */
    public StatusType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusType }
     *     
     */
    public void setStatus(StatusType value) {
        this.status = value;
    }

}
