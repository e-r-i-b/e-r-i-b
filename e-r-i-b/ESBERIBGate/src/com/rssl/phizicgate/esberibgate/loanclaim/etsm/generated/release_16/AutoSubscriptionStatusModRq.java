
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16;

import com.rssl.phizic.utils.jaxb.CalendarDateTimeNoMillisecondsAdapter;

import java.util.Calendar;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Тип сообщения-запроса для интерфейса ASSM - приостановка/возобновление/закрытие подписки
 * 
 * <p>Java class for AutoSubscriptionStatusModRq_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AutoSubscriptionStatusModRq_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}RqUID"/>
 *         &lt;element ref="{}RqTm"/>
 *         &lt;element ref="{}OperUID" minOccurs="0"/>
 *         &lt;element ref="{}SPName"/>
 *         &lt;element ref="{}BankInfo"/>
 *         &lt;element ref="{}AutoSubscriptionRec"/>
 *         &lt;element name="ActionType" type="{}ChangeStatusAction_Type"/>
 *         &lt;element name="ChannelType" type="{}Channel_Type" minOccurs="0"/>
 *         &lt;element name="EmployeeOfTheVSP" type="{}EmployeeOfTheVSP_Type" minOccurs="0"/>
 *         &lt;element name="MBCInfo" type="{}MBCInfo_Type" minOccurs="0"/>
 *         &lt;element name="NeedConfirmation" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AutoSubscriptionStatusModRq_Type", propOrder = {
    "rqUID",
    "rqTm",
    "operUID",
    "spName",
    "bankInfo",
    "autoSubscriptionRec",
    "actionType",
    "channelType",
    "employeeOfTheVSP",
    "mbcInfo",
    "needConfirmation"
})
@XmlRootElement(name = "AutoSubscriptionStatusModRq")
public class AutoSubscriptionStatusModRq {

    @XmlElement(name = "RqUID", required = true)
    protected String rqUID;
    @XmlElement(name = "RqTm", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeNoMillisecondsAdapter.class)
    protected Calendar rqTm;
    @XmlElement(name = "OperUID")
    protected String operUID;
    @XmlElement(name = "SPName", required = true)
    protected SPNameType spName;
    @XmlElement(name = "BankInfo", required = true)
    protected BankInfo bankInfo;
    @XmlElement(name = "AutoSubscriptionRec", required = true)
    protected AutoSubscriptionRec autoSubscriptionRec;
    @XmlElement(name = "ActionType", required = true)
    protected ChangeStatusActionType actionType;
    @XmlElement(name = "ChannelType")
    protected ChannelType channelType;
    @XmlElement(name = "EmployeeOfTheVSP")
    protected EmployeeOfTheVSPType employeeOfTheVSP;
    @XmlElement(name = "MBCInfo")
    protected MBCInfoType mbcInfo;
    @XmlElement(name = "NeedConfirmation")
    protected boolean needConfirmation;

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

    /**
     * Gets the value of the autoSubscriptionRec property.
     * 
     * @return
     *     possible object is
     *     {@link AutoSubscriptionRec }
     *     
     */
    public AutoSubscriptionRec getAutoSubscriptionRec() {
        return autoSubscriptionRec;
    }

    /**
     * Sets the value of the autoSubscriptionRec property.
     * 
     * @param value
     *     allowed object is
     *     {@link AutoSubscriptionRec }
     *     
     */
    public void setAutoSubscriptionRec(AutoSubscriptionRec value) {
        this.autoSubscriptionRec = value;
    }

    /**
     * Gets the value of the actionType property.
     * 
     * @return
     *     possible object is
     *     {@link ChangeStatusActionType }
     *     
     */
    public ChangeStatusActionType getActionType() {
        return actionType;
    }

    /**
     * Sets the value of the actionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChangeStatusActionType }
     *     
     */
    public void setActionType(ChangeStatusActionType value) {
        this.actionType = value;
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
     * Gets the value of the employeeOfTheVSP property.
     * 
     * @return
     *     possible object is
     *     {@link EmployeeOfTheVSPType }
     *     
     */
    public EmployeeOfTheVSPType getEmployeeOfTheVSP() {
        return employeeOfTheVSP;
    }

    /**
     * Sets the value of the employeeOfTheVSP property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmployeeOfTheVSPType }
     *     
     */
    public void setEmployeeOfTheVSP(EmployeeOfTheVSPType value) {
        this.employeeOfTheVSP = value;
    }

    /**
     * Gets the value of the mbcInfo property.
     * 
     * @return
     *     possible object is
     *     {@link MBCInfoType }
     *     
     */
    public MBCInfoType getMBCInfo() {
        return mbcInfo;
    }

    /**
     * Sets the value of the mbcInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link MBCInfoType }
     *     
     */
    public void setMBCInfo(MBCInfoType value) {
        this.mbcInfo = value;
    }

    /**
     * Gets the value of the needConfirmation property.
     * 
     */
    public boolean isNeedConfirmation() {
        return needConfirmation;
    }

    /**
     * Sets the value of the needConfirmation property.
     * 
     */
    public void setNeedConfirmation(boolean value) {
        this.needConfirmation = value;
    }

}
