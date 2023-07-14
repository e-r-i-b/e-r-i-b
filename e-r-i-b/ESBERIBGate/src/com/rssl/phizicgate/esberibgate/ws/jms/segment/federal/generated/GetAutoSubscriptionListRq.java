
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Тип сообщения-запроса для интерфейса GASL - получение списка подписок по платежным инструментам
 * 
 * <p>Java class for GetAutoSubscriptionListRq_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetAutoSubscriptionListRq_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}RqUID"/>
 *         &lt;element ref="{}RqTm"/>
 *         &lt;element ref="{}OperUID" minOccurs="0"/>
 *         &lt;element ref="{}SPName"/>
 *         &lt;element ref="{}BankInfo"/>
 *         &lt;element ref="{}BankAcctRec" maxOccurs="unbounded"/>
 *         &lt;element name="AutopayStatusList" type="{}AutopayStatusList_Type" minOccurs="0"/>
 *         &lt;element name="AutopayTypeList" type="{}AutopayTypeList_Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetAutoSubscriptionListRq_Type", propOrder = {
    "rqUID",
    "rqTm",
    "operUID",
    "spName",
    "bankInfo",
    "bankAcctRecs",
    "autopayStatusList",
    "autopayTypeLists"
})
@XmlRootElement(name = "GetAutoSubscriptionListRq")
public class GetAutoSubscriptionListRq {

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
    @XmlElement(name = "BankAcctRec", required = true)
    protected List<BankAcctRecType> bankAcctRecs;
    @XmlElement(name = "AutopayStatusList")
    protected AutopayStatusListType autopayStatusList;
    @XmlElement(name = "AutopayTypeList")
    protected List<AutopayTypeListType> autopayTypeLists;

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
     * Gets the value of the bankAcctRecs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bankAcctRecs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBankAcctRecs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BankAcctRecType }
     * 
     * 
     */
    public List<BankAcctRecType> getBankAcctRecs() {
        if (bankAcctRecs == null) {
            bankAcctRecs = new ArrayList<BankAcctRecType>();
        }
        return this.bankAcctRecs;
    }

    /**
     * Gets the value of the autopayStatusList property.
     * 
     * @return
     *     possible object is
     *     {@link AutopayStatusListType }
     *     
     */
    public AutopayStatusListType getAutopayStatusList() {
        return autopayStatusList;
    }

    /**
     * Sets the value of the autopayStatusList property.
     * 
     * @param value
     *     allowed object is
     *     {@link AutopayStatusListType }
     *     
     */
    public void setAutopayStatusList(AutopayStatusListType value) {
        this.autopayStatusList = value;
    }

    /**
     * Gets the value of the autopayTypeLists property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the autopayTypeLists property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAutopayTypeLists().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AutopayTypeListType }
     * 
     * 
     */
    public List<AutopayTypeListType> getAutopayTypeLists() {
        if (autopayTypeLists == null) {
            autopayTypeLists = new ArrayList<AutopayTypeListType>();
        }
        return this.autopayTypeLists;
    }

}
