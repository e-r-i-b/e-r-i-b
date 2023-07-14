
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Тип сообщения-запроса для интерфейса GAPL - получение списка платежей по подписке
 * 
 * <p>Java class for GetAutoPaymentListRq_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetAutoPaymentListRq_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}RqUID"/>
 *         &lt;element ref="{}RqTm"/>
 *         &lt;element ref="{}OperUID" minOccurs="0"/>
 *         &lt;element ref="{}SPName"/>
 *         &lt;element ref="{}BankInfo"/>
 *         &lt;element name="AutoSubscriptionId" type="{}AutoSubscriptionId_Type"/>
 *         &lt;element name="PaymentStatusList" type="{}PaymentStatusList_Type" minOccurs="0"/>
 *         &lt;element name="SelRangeDtTm" type="{}SelRangeDtTm_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetAutoPaymentListRq_Type", propOrder = {
    "rqUID",
    "rqTm",
    "operUID",
    "spName",
    "bankInfo",
    "autoSubscriptionId",
    "paymentStatusList",
    "selRangeDtTm"
})
@XmlRootElement(name = "GetAutoPaymentListRq")
public class GetAutoPaymentListRq {

    @XmlElement(name = "RqUID", required = true)
    protected String rqUID;
    @XmlElement(name = "RqTm", required = true)
    protected String rqTm;
    @XmlElement(name = "OperUID")
    protected String operUID;
    @XmlElement(name = "SPName", required = true)
    protected SPNameType spName;
    @XmlElement(name = "BankInfo", required = true)
    protected BankInfoType bankInfo;
    @XmlElement(name = "AutoSubscriptionId", required = true)
    protected AutoSubscriptionIdType autoSubscriptionId;
    @XmlElement(name = "PaymentStatusList")
    protected PaymentStatusListType paymentStatusList;
    @XmlElement(name = "SelRangeDtTm")
    protected SelRangeDtTmType selRangeDtTm;

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
    public String getRqTm() {
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
    public void setRqTm(String value) {
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
     *     {@link SPNameType }
     *     
     */
    public SPNameType getSPName() {
        return spName;
    }

    /**
     * Sets the value of the spName property.
     * 
     * @param value
     *     allowed object is
     *     {@link SPNameType }
     *     
     */
    public void setSPName(SPNameType value) {
        this.spName = value;
    }

    /**
     * Gets the value of the bankInfo property.
     * 
     * @return
     *     possible object is
     *     {@link BankInfoType }
     *     
     */
    public BankInfoType getBankInfo() {
        return bankInfo;
    }

    /**
     * Sets the value of the bankInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankInfoType }
     *     
     */
    public void setBankInfo(BankInfoType value) {
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
     * Gets the value of the paymentStatusList property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentStatusListType }
     *     
     */
    public PaymentStatusListType getPaymentStatusList() {
        return paymentStatusList;
    }

    /**
     * Sets the value of the paymentStatusList property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentStatusListType }
     *     
     */
    public void setPaymentStatusList(PaymentStatusListType value) {
        this.paymentStatusList = value;
    }

    /**
     * Gets the value of the selRangeDtTm property.
     * 
     * @return
     *     possible object is
     *     {@link SelRangeDtTmType }
     *     
     */
    public SelRangeDtTmType getSelRangeDtTm() {
        return selRangeDtTm;
    }

    /**
     * Sets the value of the selRangeDtTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link SelRangeDtTmType }
     *     
     */
    public void setSelRangeDtTm(SelRangeDtTmType value) {
        this.selRangeDtTm = value;
    }

}
