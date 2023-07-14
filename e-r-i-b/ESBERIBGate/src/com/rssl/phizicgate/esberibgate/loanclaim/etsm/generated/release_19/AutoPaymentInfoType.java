
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Тип Информация об автоплатеже
 * 
 * <p>Java class for AutoPaymentInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AutoPaymentInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PaymentStatus" type="{}PaymentStatusASAP_Type"/>
 *         &lt;element name="PaymentStatusDesc" type="{}String"/>
 *         &lt;element ref="{}CurAmt" minOccurs="0"/>
 *         &lt;element name="Commission" type="{}Decimal" minOccurs="0"/>
 *         &lt;element name="MadeOperationId" type="{}String" minOccurs="0"/>
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
@XmlType(name = "AutoPaymentInfo_Type", propOrder = {
    "paymentStatus",
    "paymentStatusDesc",
    "curAmt",
    "commission",
    "madeOperationId",
    "execStatus"
})
public class AutoPaymentInfoType {

    @XmlElement(name = "PaymentStatus", required = true)
    protected PaymentStatusASAPType paymentStatus;
    @XmlElement(name = "PaymentStatusDesc", required = true)
    protected String paymentStatusDesc;
    @XmlElement(name = "CurAmt")
    protected BigDecimal curAmt;
    @XmlElement(name = "Commission")
    protected BigDecimal commission;
    @XmlElement(name = "MadeOperationId")
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
     * Сумма платежа в рублях
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCurAmt() {
        return curAmt;
    }

    /**
     * Sets the value of the curAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCurAmt(BigDecimal value) {
        this.curAmt = value;
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
