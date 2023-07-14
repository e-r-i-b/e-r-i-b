
package com.rssl.phizgate.basket.generated;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Информация о платеже
 * 
 * <p>Java class for PaymentInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PaymentInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PaymentStatus" type="{}PaymentStatusASAP_Type"/>
 *         &lt;element name="PaymentStatusDesc" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Commission" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="MadeOperationId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ExecStatus" type="{}ExecStatus_Type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentInfo_Type", propOrder = {
    "paymentStatus",
    "paymentStatusDesc",
    "commission",
    "madeOperationId",
    "execStatus"
})
public class PaymentInfoType {

    @XmlElement(name = "PaymentStatus", required = true)
    protected PaymentStatusASAPType paymentStatus;
    @XmlElement(name = "PaymentStatusDesc", required = true)
    protected String paymentStatusDesc;
    @XmlElement(name = "Commission")
    protected BigDecimal commission;
    @XmlElement(name = "MadeOperationId", required = true)
    protected String madeOperationId;
    @XmlElement(name = "ExecStatus", required = true)
    protected ExecStatusType execStatus;

    /**
     * Gets the value of the paymentStatus property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentStatusASAPType }
     *     
     */
    public PaymentStatusASAPType getPaymentStatus() {
        return paymentStatus;
    }

    /**
     * Sets the value of the paymentStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentStatusASAPType }
     *     
     */
    public void setPaymentStatus(PaymentStatusASAPType value) {
        this.paymentStatus = value;
    }

    /**
     * Gets the value of the paymentStatusDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentStatusDesc() {
        return paymentStatusDesc;
    }

    /**
     * Sets the value of the paymentStatusDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentStatusDesc(String value) {
        this.paymentStatusDesc = value;
    }

    /**
     * Gets the value of the commission property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCommission() {
        return commission;
    }

    /**
     * Sets the value of the commission property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCommission(BigDecimal value) {
        this.commission = value;
    }

    /**
     * Gets the value of the madeOperationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMadeOperationId() {
        return madeOperationId;
    }

    /**
     * Sets the value of the madeOperationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMadeOperationId(String value) {
        this.madeOperationId = value;
    }

    /**
     * Gets the value of the execStatus property.
     * 
     * @return
     *     possible object is
     *     {@link ExecStatusType }
     *     
     */
    public ExecStatusType getExecStatus() {
        return execStatus;
    }

    /**
     * Sets the value of the execStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExecStatusType }
     *     
     */
    public void setExecStatus(ExecStatusType value) {
        this.execStatus = value;
    }

}
