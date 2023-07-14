
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 *   Информация  по  карточному  контракту
 * 
 * <p>Java class for CardAcctInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CardAcctInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CardTypeProd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BonusCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SalaryAgreementId" type="{}AgreemtId_Type" minOccurs="0"/>
 *         &lt;element name="MainCardNum" type="{}CardNumType" minOccurs="0"/>
 *         &lt;element name="PinPack" type="{}PinPack_Type" minOccurs="0"/>
 *         &lt;element name="AutoPayInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MBCInfo" type="{}MBCInfo_Type" minOccurs="0"/>
 *         &lt;element name="Tariff" type="{}TarifUnionType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="DLCode" type="{}FNC10" minOccurs="0"/>
 *         &lt;element name="TAGCode" type="{}YesNoType" minOccurs="0"/>
 *         &lt;element name="EndDtForWay" type="{}String" minOccurs="0"/>
 *         &lt;element name="CardOrderNum" type="{}OrderNum_Type" minOccurs="0"/>
 *         &lt;element name="CardOrderDate" type="{}Date" minOccurs="0"/>
 *         &lt;element name="CardSubType" type="{}ClassifierCode_Type" minOccurs="0"/>
 *         &lt;element name="PersonalDesignRefNum" type="{}CardId_Type" minOccurs="0"/>
 *         &lt;element name="RiskFactor" type="{}C" minOccurs="0"/>
 *         &lt;element name="BonusInfo" type="{}BonusInfo_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CardAcctInfoType", propOrder = {
    "cardTypeProd",
    "bonusCode",
    "salaryAgreementId",
    "mainCardNum",
    "pinPack",
    "autoPayInfo",
    "mbcInfo",
    "tarifves",
    "dlCode",
    "tagCode",
    "endDtForWay",
    "cardOrderNum",
    "cardOrderDate",
    "cardSubType",
    "personalDesignRefNum",
    "riskFactor",
    "bonusInfo"
})
public class CardAcctInfoType {

    @XmlElement(name = "CardTypeProd")
    protected String cardTypeProd;
    @XmlElement(name = "BonusCode")
    protected String bonusCode;
    @XmlElement(name = "SalaryAgreementId")
    protected String salaryAgreementId;
    @XmlElement(name = "MainCardNum")
    protected String mainCardNum;
    @XmlElement(name = "PinPack")
    protected Long pinPack;
    @XmlElement(name = "AutoPayInfo")
    protected String autoPayInfo;
    @XmlElement(name = "MBCInfo")
    protected MBCInfoType mbcInfo;
    @XmlElement(name = "Tariff")
    protected List<TarifUnionType> tarifves;
    @XmlElement(name = "DLCode")
    protected Long dlCode;
    @XmlElement(name = "TAGCode")
    protected String tagCode;
    @XmlElement(name = "EndDtForWay")
    protected String endDtForWay;
    @XmlElement(name = "CardOrderNum")
    protected String cardOrderNum;
    @XmlElement(name = "CardOrderDate")
    protected String cardOrderDate;
    @XmlElement(name = "CardSubType")
    protected String cardSubType;
    @XmlElement(name = "PersonalDesignRefNum")
    protected String personalDesignRefNum;
    @XmlElement(name = "RiskFactor")
    protected String riskFactor;
    @XmlElement(name = "BonusInfo")
    protected BonusInfoType bonusInfo;

    /**
     * Gets the value of the cardTypeProd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardTypeProd() {
        return cardTypeProd;
    }

    /**
     * Sets the value of the cardTypeProd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardTypeProd(String value) {
        this.cardTypeProd = value;
    }

    /**
     * Gets the value of the bonusCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBonusCode() {
        return bonusCode;
    }

    /**
     * Sets the value of the bonusCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBonusCode(String value) {
        this.bonusCode = value;
    }

    /**
     * Gets the value of the salaryAgreementId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalaryAgreementId() {
        return salaryAgreementId;
    }

    /**
     * Sets the value of the salaryAgreementId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalaryAgreementId(String value) {
        this.salaryAgreementId = value;
    }

    /**
     * Gets the value of the mainCardNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMainCardNum() {
        return mainCardNum;
    }

    /**
     * Sets the value of the mainCardNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMainCardNum(String value) {
        this.mainCardNum = value;
    }

    /**
     * Gets the value of the pinPack property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPinPack() {
        return pinPack;
    }

    /**
     * Sets the value of the pinPack property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPinPack(Long value) {
        this.pinPack = value;
    }

    /**
     * Gets the value of the autoPayInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAutoPayInfo() {
        return autoPayInfo;
    }

    /**
     * Sets the value of the autoPayInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAutoPayInfo(String value) {
        this.autoPayInfo = value;
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
     * Gets the value of the tarifves property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tarifves property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTarifves().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TarifUnionType }
     * 
     * 
     */
    public List<TarifUnionType> getTarifves() {
        if (tarifves == null) {
            tarifves = new ArrayList<TarifUnionType>();
        }
        return this.tarifves;
    }

    /**
     * Gets the value of the dlCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getDLCode() {
        return dlCode;
    }

    /**
     * Sets the value of the dlCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setDLCode(Long value) {
        this.dlCode = value;
    }

    /**
     * Gets the value of the tagCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTAGCode() {
        return tagCode;
    }

    /**
     * Sets the value of the tagCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTAGCode(String value) {
        this.tagCode = value;
    }

    /**
     * Gets the value of the endDtForWay property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndDtForWay() {
        return endDtForWay;
    }

    /**
     * Sets the value of the endDtForWay property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndDtForWay(String value) {
        this.endDtForWay = value;
    }

    /**
     * Gets the value of the cardOrderNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardOrderNum() {
        return cardOrderNum;
    }

    /**
     * Sets the value of the cardOrderNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardOrderNum(String value) {
        this.cardOrderNum = value;
    }

    /**
     * Gets the value of the cardOrderDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardOrderDate() {
        return cardOrderDate;
    }

    /**
     * Sets the value of the cardOrderDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardOrderDate(String value) {
        this.cardOrderDate = value;
    }

    /**
     * Gets the value of the cardSubType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardSubType() {
        return cardSubType;
    }

    /**
     * Sets the value of the cardSubType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardSubType(String value) {
        this.cardSubType = value;
    }

    /**
     * Gets the value of the personalDesignRefNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonalDesignRefNum() {
        return personalDesignRefNum;
    }

    /**
     * Sets the value of the personalDesignRefNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersonalDesignRefNum(String value) {
        this.personalDesignRefNum = value;
    }

    /**
     * Gets the value of the riskFactor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiskFactor() {
        return riskFactor;
    }

    /**
     * Sets the value of the riskFactor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiskFactor(String value) {
        this.riskFactor = value;
    }

    /**
     * Gets the value of the bonusInfo property.
     * 
     * @return
     *     possible object is
     *     {@link BonusInfoType }
     *     
     */
    public BonusInfoType getBonusInfo() {
        return bonusInfo;
    }

    /**
     * Sets the value of the bonusInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BonusInfoType }
     *     
     */
    public void setBonusInfo(BonusInfoType value) {
        this.bonusInfo = value;
    }

}
