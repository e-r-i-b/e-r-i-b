
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;


/**
 * Удостоверение личности <IdentityCard>
 * 
 * <p>Java class for IdentityCard_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IdentityCard_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}IdType" minOccurs="0"/>
 *         &lt;element ref="{}IdSeries" minOccurs="0"/>
 *         &lt;element ref="{}IdNum" minOccurs="0"/>
 *         &lt;element ref="{}IssuedBy" minOccurs="0"/>
 *         &lt;element name="IssuedCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element ref="{}IssueDt" minOccurs="0"/>
 *         &lt;element ref="{}ExpDt" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdentityCard_Type", propOrder = {
    "idType",
    "idSeries",
    "idNum",
    "issuedBy",
    "issuedCode",
    "issueDt",
    "expDt"
})
@XmlRootElement(name = "IdentityCard")
public class IdentityCard {

    @XmlElement(name = "IdType")
    protected String idType;
    @XmlElement(name = "IdSeries")
    protected String idSeries;
    @XmlElement(name = "IdNum")
    protected String idNum;
    @XmlElement(name = "IssuedBy")
    protected String issuedBy;
    @XmlElement(name = "IssuedCode")
    protected String issuedCode;
    @XmlElement(name = "IssueDt", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar issueDt;
    @XmlElement(name = "ExpDt", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar expDt;

    /**
     * Тип документа удостоверяющего личность
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdType() {
        return idType;
    }

    /**
     * Sets the value of the idType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdType(String value) {
        this.idType = value;
    }

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
     * Срок действия/окончания
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getExpDt() {
        return expDt;
    }

    /**
     * Sets the value of the expDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpDt(Calendar value) {
        this.expDt = value;
    }

}
