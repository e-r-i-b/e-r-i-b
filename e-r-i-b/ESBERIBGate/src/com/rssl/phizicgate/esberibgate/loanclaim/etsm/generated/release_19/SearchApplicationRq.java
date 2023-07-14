
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateTimeNoMillisecondsAdapter;


/**
 * Тип данных запроса на поиск существующих кредитных заявок в ETSM
 * 
 * <p>Java class for SearchApplicationRq_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SearchApplicationRq_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}RqUID"/>
 *         &lt;element ref="{}RqTm"/>
 *         &lt;element ref="{}SPName"/>
 *         &lt;element ref="{}PersonName"/>
 *         &lt;element name="Passport">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{}IdSeries"/>
 *                   &lt;element ref="{}IdNum"/>
 *                   &lt;element name="IssuedCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element ref="{}IssuedBy" minOccurs="0"/>
 *                   &lt;element ref="{}IssueDt" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchApplicationRq_Type", propOrder = {
    "rqUID",
    "rqTm",
    "spName",
    "personName",
    "passport"
})
@XmlRootElement(name = "SearchApplicationRq")
public class SearchApplicationRq {

    @XmlElement(name = "RqUID", required = true)
    protected String rqUID;
    @XmlElement(name = "RqTm", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeNoMillisecondsAdapter.class)
    protected Calendar rqTm;
    @XmlElement(name = "SPName", required = true)
    protected SPNameType spName;
    @XmlElement(name = "PersonName", required = true)
    protected PersonNameType personName;
    @XmlElement(name = "Passport", required = true)
    protected SearchApplicationRq.Passport passport;

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
     * Gets the value of the personName property.
     * 
     * @return
     *     possible object is
     *     {@link PersonNameType }
     *     
     */
    public PersonNameType getPersonName() {
        return personName;
    }

    /**
     * Sets the value of the personName property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonNameType }
     *     
     */
    public void setPersonName(PersonNameType value) {
        this.personName = value;
    }

    /**
     * Gets the value of the passport property.
     * 
     * @return
     *     possible object is
     *     {@link SearchApplicationRq.Passport }
     *     
     */
    public SearchApplicationRq.Passport getPassport() {
        return passport;
    }

    /**
     * Sets the value of the passport property.
     * 
     * @param value
     *     allowed object is
     *     {@link SearchApplicationRq.Passport }
     *     
     */
    public void setPassport(SearchApplicationRq.Passport value) {
        this.passport = value;
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
     *         &lt;element ref="{}IdSeries"/>
     *         &lt;element ref="{}IdNum"/>
     *         &lt;element name="IssuedCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element ref="{}IssuedBy" minOccurs="0"/>
     *         &lt;element ref="{}IssueDt" minOccurs="0"/>
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
        "idSeries",
        "idNum",
        "issuedCode",
        "issuedBy",
        "issueDt"
    })
    public static class Passport {

        @XmlElement(name = "IdSeries", required = true)
        protected String idSeries;
        @XmlElement(name = "IdNum", required = true)
        protected String idNum;
        @XmlElement(name = "IssuedCode")
        protected String issuedCode;
        @XmlElement(name = "IssuedBy")
        protected String issuedBy;
        @XmlElement(name = "IssueDt", type = String.class)
        @XmlJavaTypeAdapter(CalendarDateAdapter.class)
        protected Calendar issueDt;

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

    }

}
