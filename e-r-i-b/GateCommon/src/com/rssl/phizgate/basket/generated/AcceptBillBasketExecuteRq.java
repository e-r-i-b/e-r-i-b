
package com.rssl.phizgate.basket.generated;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateTimeAdapter;


/**
 * <p>Java class for AcceptBillBasketExecuteRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AcceptBillBasketExecuteRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RqUID" type="{}UUID"/>
 *         &lt;element name="RqTm" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="OperUID" type="{}UUID"/>
 *         &lt;element name="SPName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BankInfo">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="RbTbBrchId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="AutoSubscriptionId" type="{}AutoSubscriptionId_Type"/>
 *         &lt;element name="PaymentId" type="{}PaymentId_Type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AcceptBillBasketExecuteRequestType", propOrder = {
    "rqUID",
    "rqTm",
    "operUID",
    "spName",
    "bankInfo",
    "autoSubscriptionId",
    "paymentId"
})
@XmlRootElement(name = "AcceptBillBasketExecuteRq")
public class AcceptBillBasketExecuteRq {

    @XmlElement(name = "RqUID", required = true)
    protected String rqUID;
    @XmlElement(name = "RqTm", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Calendar rqTm;
    @XmlElement(name = "OperUID", required = true)
    protected String operUID;
    @XmlElement(name = "SPName", required = true)
    protected String spName;
    @XmlElement(name = "BankInfo", required = true)
    protected AcceptBillBasketExecuteRq.BankInfo bankInfo;
    @XmlElement(name = "AutoSubscriptionId", required = true)
    protected AutoSubscriptionIdType autoSubscriptionId;
    @XmlElement(name = "PaymentId", required = true)
    protected PaymentIdType paymentId;

    /**
     * Gets the value of the rqUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRqUID() {
        return rqUID;
    }

    /**
     * Sets the value of the rqUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRqUID(String value) {
        this.rqUID = value;
    }

    /**
     * Gets the value of the rqTm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getRqTm() {
        return rqTm;
    }

    /**
     * Sets the value of the rqTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRqTm(Calendar value) {
        this.rqTm = value;
    }

    /**
     * Gets the value of the operUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperUID() {
        return operUID;
    }

    /**
     * Sets the value of the operUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperUID(String value) {
        this.operUID = value;
    }

    /**
     * Gets the value of the spName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSPName() {
        return spName;
    }

    /**
     * Sets the value of the spName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSPName(String value) {
        this.spName = value;
    }

    /**
     * Gets the value of the bankInfo property.
     * 
     * @return
     *     possible object is
     *     {@link AcceptBillBasketExecuteRq.BankInfo }
     *     
     */
    public AcceptBillBasketExecuteRq.BankInfo getBankInfo() {
        return bankInfo;
    }

    /**
     * Sets the value of the bankInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link AcceptBillBasketExecuteRq.BankInfo }
     *     
     */
    public void setBankInfo(AcceptBillBasketExecuteRq.BankInfo value) {
        this.bankInfo = value;
    }

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
     * Gets the value of the paymentId property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentIdType }
     *     
     */
    public PaymentIdType getPaymentId() {
        return paymentId;
    }

    /**
     * Sets the value of the paymentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentIdType }
     *     
     */
    public void setPaymentId(PaymentIdType value) {
        this.paymentId = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="RbTbBrchId" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "rbTbBrchId"
    })
    public static class BankInfo {

        @XmlElement(name = "RbTbBrchId", required = true)
        protected String rbTbBrchId;

        /**
         * Gets the value of the rbTbBrchId property.
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

    }

}
