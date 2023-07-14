
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
 * <p>Java class for AddBillBasketInfoRqType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AddBillBasketInfoRqType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OperUID" type="{}UUID"/>
 *         &lt;element name="RqUID" type="{}UUID"/>
 *         &lt;element name="RqTm" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="SPName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ActionMode" type="{}ActionMode_Type" minOccurs="0"/>
 *         &lt;element name="AutoSubscriptionId" type="{}AutoSubscriptionId_Type"/>
 *         &lt;element name="Payment" type="{}Payment_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddBillBasketInfoRqType", propOrder = {
    "operUID",
    "rqUID",
    "rqTm",
    "spName",
    "actionMode",
    "autoSubscriptionId",
    "payment"
})
@XmlRootElement(name = "AddBillBasketInfoRq")
public class AddBillBasketInfoRq {

    @XmlElement(name = "OperUID", required = true)
    protected String operUID;
    @XmlElement(name = "RqUID", required = true)
    protected String rqUID;
    @XmlElement(name = "RqTm", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Calendar rqTm;
    @XmlElement(name = "SPName", required = true)
    protected String spName;
    @XmlElement(name = "ActionMode")
    protected ActionModeType actionMode;
    @XmlElement(name = "AutoSubscriptionId", required = true)
    protected AutoSubscriptionIdType autoSubscriptionId;
    @XmlElement(name = "Payment")
    protected PaymentType payment;

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
     * Gets the value of the actionMode property.
     * 
     * @return
     *     possible object is
     *     {@link ActionModeType }
     *     
     */
    public ActionModeType getActionMode() {
        return actionMode;
    }

    /**
     * Sets the value of the actionMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActionModeType }
     *     
     */
    public void setActionMode(ActionModeType value) {
        this.actionMode = value;
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
     * Gets the value of the payment property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentType }
     *     
     */
    public PaymentType getPayment() {
        return payment;
    }

    /**
     * Sets the value of the payment property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentType }
     *     
     */
    public void setPayment(PaymentType value) {
        this.payment = value;
    }

}
