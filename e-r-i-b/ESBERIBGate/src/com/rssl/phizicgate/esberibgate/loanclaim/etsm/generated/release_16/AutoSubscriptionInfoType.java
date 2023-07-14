
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16;

import com.rssl.phizic.utils.jaxb.CalendarDateTimeNoMillisecondsAdapter;

import java.math.BigDecimal;
import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Тип Информация о подписке
 * 
 * <p>Java class for AutoSubscriptionInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AutoSubscriptionInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RequestId" type="{}String" minOccurs="0"/>
 *         &lt;element name="AutopayName" type="{}String" minOccurs="0"/>
 *         &lt;element name="ExeEventCode" type="{}ExeEventCodeASAP_Type" minOccurs="0"/>
 *         &lt;element name="SummaKindCode" type="{}SummaKindCodeASAP_Type" minOccurs="0"/>
 *         &lt;element ref="{}AutopayStatus" minOccurs="0"/>
 *         &lt;element name="AutopayStatusDesc" type="{}String" minOccurs="0"/>
 *         &lt;element name="StartDate" type="{}DateTime" minOccurs="0"/>
 *         &lt;element name="UpdateDate" type="{}DateTime" minOccurs="0"/>
 *         &lt;element name="MaxSumWritePerMonth" type="{}Decimal" minOccurs="0"/>
 *         &lt;element name="IrreducibleAmt" type="{}Decimal" minOccurs="0"/>
 *         &lt;element name="CurAmt" type="{}Decimal" minOccurs="0"/>
 *         &lt;element name="NextPayDate" type="{}DateTime" minOccurs="0"/>
 *         &lt;element name="ChangeStatus" type="{}ChangeStatus_Type" minOccurs="0"/>
 *         &lt;element name="ChannelType" type="{}Channel_Type" minOccurs="0"/>
 *         &lt;element ref="{}SPNum" minOccurs="0"/>
 *         &lt;element ref="{}BankInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AutoSubscriptionInfo_Type", propOrder = {
    "requestId",
    "autopayName",
    "exeEventCode",
    "summaKindCode",
    "autopayStatus",
    "autopayStatusDesc",
    "startDate",
    "updateDate",
    "maxSumWritePerMonth",
    "irreducibleAmt",
    "curAmt",
    "nextPayDate",
    "changeStatus",
    "channelType",
    "spNum",
    "bankInfo"
})
public class AutoSubscriptionInfoType {

    @XmlElement(name = "RequestId")
    protected String requestId;
    @XmlElement(name = "AutopayName")
    protected String autopayName;
    @XmlElement(name = "ExeEventCode")
    protected ExeEventCodeASAPType exeEventCode;
    @XmlElement(name = "SummaKindCode")
    protected SummaKindCodeASAPType summaKindCode;
    @XmlElement(name = "AutopayStatus")
    protected AutopayStatusType autopayStatus;
    @XmlElement(name = "AutopayStatusDesc")
    protected String autopayStatusDesc;
    @XmlElement(name = "StartDate", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeNoMillisecondsAdapter.class)
    protected Calendar startDate;
    @XmlElement(name = "UpdateDate", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeNoMillisecondsAdapter.class)
    protected Calendar updateDate;
    @XmlElement(name = "MaxSumWritePerMonth")
    protected BigDecimal maxSumWritePerMonth;
    @XmlElement(name = "IrreducibleAmt")
    protected BigDecimal irreducibleAmt;
    @XmlElement(name = "CurAmt")
    protected BigDecimal curAmt;
    @XmlElement(name = "NextPayDate", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeNoMillisecondsAdapter.class)
    protected Calendar nextPayDate;
    @XmlElement(name = "ChangeStatus")
    protected ChangeStatusType changeStatus;
    @XmlElement(name = "ChannelType")
    protected ChannelType channelType;
    @XmlElement(name = "SPNum")
    protected String spNum;
    @XmlElement(name = "BankInfo")
    protected BankInfo bankInfo;

    /**
     * Gets the value of the requestId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Sets the value of the requestId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestId(String value) {
        this.requestId = value;
    }

    /**
     * Gets the value of the autopayName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAutopayName() {
        return autopayName;
    }

    /**
     * Sets the value of the autopayName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAutopayName(String value) {
        this.autopayName = value;
    }

    /**
     * Gets the value of the exeEventCode property.
     * 
     * @return
     *     possible object is
     *     {@link ExeEventCodeASAPType }
     *     
     */
    public ExeEventCodeASAPType getExeEventCode() {
        return exeEventCode;
    }

    /**
     * Sets the value of the exeEventCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExeEventCodeASAPType }
     *     
     */
    public void setExeEventCode(ExeEventCodeASAPType value) {
        this.exeEventCode = value;
    }

    /**
     * Gets the value of the summaKindCode property.
     * 
     * @return
     *     possible object is
     *     {@link SummaKindCodeASAPType }
     *     
     */
    public SummaKindCodeASAPType getSummaKindCode() {
        return summaKindCode;
    }

    /**
     * Sets the value of the summaKindCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link SummaKindCodeASAPType }
     *     
     */
    public void setSummaKindCode(SummaKindCodeASAPType value) {
        this.summaKindCode = value;
    }

    /**
     * Gets the value of the autopayStatus property.
     * 
     * @return
     *     possible object is
     *     {@link AutopayStatusType }
     *     
     */
    public AutopayStatusType getAutopayStatus() {
        return autopayStatus;
    }

    /**
     * Sets the value of the autopayStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link AutopayStatusType }
     *     
     */
    public void setAutopayStatus(AutopayStatusType value) {
        this.autopayStatus = value;
    }

    /**
     * Gets the value of the autopayStatusDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAutopayStatusDesc() {
        return autopayStatusDesc;
    }

    /**
     * Sets the value of the autopayStatusDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAutopayStatusDesc(String value) {
        this.autopayStatusDesc = value;
    }

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartDate(Calendar value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the updateDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getUpdateDate() {
        return updateDate;
    }

    /**
     * Sets the value of the updateDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpdateDate(Calendar value) {
        this.updateDate = value;
    }

    /**
     * Gets the value of the maxSumWritePerMonth property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMaxSumWritePerMonth() {
        return maxSumWritePerMonth;
    }

    /**
     * Sets the value of the maxSumWritePerMonth property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMaxSumWritePerMonth(BigDecimal value) {
        this.maxSumWritePerMonth = value;
    }

    /**
     * Gets the value of the irreducibleAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIrreducibleAmt() {
        return irreducibleAmt;
    }

    /**
     * Sets the value of the irreducibleAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIrreducibleAmt(BigDecimal value) {
        this.irreducibleAmt = value;
    }

    /**
     * Gets the value of the curAmt property.
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
     * Gets the value of the nextPayDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getNextPayDate() {
        return nextPayDate;
    }

    /**
     * Sets the value of the nextPayDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNextPayDate(Calendar value) {
        this.nextPayDate = value;
    }

    /**
     * Gets the value of the changeStatus property.
     * 
     * @return
     *     possible object is
     *     {@link ChangeStatusType }
     *     
     */
    public ChangeStatusType getChangeStatus() {
        return changeStatus;
    }

    /**
     * Sets the value of the changeStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChangeStatusType }
     *     
     */
    public void setChangeStatus(ChangeStatusType value) {
        this.changeStatus = value;
    }

    /**
     * Gets the value of the channelType property.
     * 
     * @return
     *     possible object is
     *     {@link ChannelType }
     *     
     */
    public ChannelType getChannelType() {
        return channelType;
    }

    /**
     * Sets the value of the channelType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChannelType }
     *     
     */
    public void setChannelType(ChannelType value) {
        this.channelType = value;
    }

    /**
     * Gets the value of the spNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSPNum() {
        return spNum;
    }

    /**
     * Sets the value of the spNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSPNum(String value) {
        this.spNum = value;
    }

    /**
     * Gets the value of the bankInfo property.
     * 
     * @return
     *     possible object is
     *     {@link BankInfo }
     *     
     */
    public BankInfo getBankInfo() {
        return bankInfo;
    }

    /**
     * Sets the value of the bankInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankInfo }
     *     
     */
    public void setBankInfo(BankInfo value) {
        this.bankInfo = value;
    }

}
