
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;


/**
 * Тип - Паспорт
 * 
 * <p>Java class for Passport_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Passport_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}IdSeries"/>
 *         &lt;element ref="{}IdNum"/>
 *         &lt;element ref="{}IssuedBy"/>
 *         &lt;element name="IssuedCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element ref="{}IssueDt"/>
 *         &lt;element name="PrevPassportInfoFlag" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="PrevPassport" type="{}PrevPassport_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Passport_Type", propOrder = {
    "idSeries",
    "idNum",
    "issuedBy",
    "issuedCode",
    "issueDt",
    "prevPassportInfoFlag",
    "prevPassport"
})
public class PassportType {

    @XmlElement(name = "IdSeries", required = true)
    protected String idSeries;
    @XmlElement(name = "IdNum", required = true)
    protected String idNum;
    @XmlElement(name = "IssuedBy", required = true)
    protected String issuedBy;
    @XmlElement(name = "IssuedCode", required = true)
    protected String issuedCode;
    @XmlElement(name = "IssueDt", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar issueDt;
    @XmlElement(name = "PrevPassportInfoFlag")
    protected boolean prevPassportInfoFlag;
    @XmlElement(name = "PrevPassport")
    protected PrevPassportType prevPassport;

    /**
     * Серия документа удостоверяющего личность
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdSeries() {
        return idSeries;
    }

    /**
     * Sets the value of the idSeries property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdSeries(String value) {
        this.idSeries = value;
    }

    /**
     * Номер документа удостоверяющего личность
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdNum() {
        return idNum;
    }

    /**
     * Sets the value of the idNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdNum(String value) {
        this.idNum = value;
    }

    /**
     * Кем выдан.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssuedBy() {
        return issuedBy;
    }

    /**
     * Sets the value of the issuedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssuedBy(String value) {
        this.issuedBy = value;
    }

    /**
     * Gets the value of the issuedCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssuedCode() {
        return issuedCode;
    }

    /**
     * Sets the value of the issuedCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssuedCode(String value) {
        this.issuedCode = value;
    }

    /**
     * Дата выдачи.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getIssueDt() {
        return issueDt;
    }

    /**
     * Sets the value of the issueDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssueDt(Calendar value) {
        this.issueDt = value;
    }

    /**
     * Gets the value of the prevPassportInfoFlag property.
     * 
     */
    public boolean isPrevPassportInfoFlag() {
        return prevPassportInfoFlag;
    }

    /**
     * Sets the value of the prevPassportInfoFlag property.
     * 
     */
    public void setPrevPassportInfoFlag(boolean value) {
        this.prevPassportInfoFlag = value;
    }

    /**
     * Gets the value of the prevPassport property.
     * 
     * @return
     *     possible object is
     *     {@link PrevPassportType }
     *     
     */
    public PrevPassportType getPrevPassport() {
        return prevPassport;
    }

    /**
     * Sets the value of the prevPassport property.
     * 
     * @param value
     *     allowed object is
     *     {@link PrevPassportType }
     *     
     */
    public void setPrevPassport(PrevPassportType value) {
        this.prevPassport = value;
    }

}
