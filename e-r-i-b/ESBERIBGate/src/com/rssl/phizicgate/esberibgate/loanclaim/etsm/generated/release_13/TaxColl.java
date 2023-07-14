
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;


/**
 * Налоговые реквизиты
 * 
 * <p>Java class for TaxColl_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TaxColl_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SRCstatus" type="{}String" minOccurs="0"/>
 *         &lt;element name="TaxRegCodeTo" type="{}String" minOccurs="0"/>
 *         &lt;element name="BudgetCode" type="{}String" minOccurs="0"/>
 *         &lt;element name="OKATO" type="{}String" minOccurs="0"/>
 *         &lt;element name="TaxBase" type="{}String" minOccurs="0"/>
 *         &lt;element name="TaxPeriod" type="{}String" minOccurs="0"/>
 *         &lt;element name="TaxNum" type="{}String" minOccurs="0"/>
 *         &lt;element name="TaxDt" type="{}Date" minOccurs="0"/>
 *         &lt;element name="TaxType" type="{}String" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxColl_Type", propOrder = {
    "srCstatus",
    "taxRegCodeTo",
    "budgetCode",
    "okato",
    "taxBase",
    "taxPeriod",
    "taxNum",
    "taxDt",
    "taxType"
})
@XmlRootElement(name = "TaxColl")
public class TaxColl {

    @XmlElement(name = "SRCstatus")
    protected String srCstatus;
    @XmlElement(name = "TaxRegCodeTo")
    protected String taxRegCodeTo;
    @XmlElement(name = "BudgetCode")
    protected String budgetCode;
    @XmlElement(name = "OKATO")
    protected String okato;
    @XmlElement(name = "TaxBase")
    protected String taxBase;
    @XmlElement(name = "TaxPeriod")
    protected String taxPeriod;
    @XmlElement(name = "TaxNum")
    protected String taxNum;
    @XmlElement(name = "TaxDt", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar taxDt;
    @XmlElement(name = "TaxType")
    protected String taxType;

    /**
     * Gets the value of the srCstatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSRCstatus() {
        return srCstatus;
    }

    /**
     * Sets the value of the srCstatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSRCstatus(String value) {
        this.srCstatus = value;
    }

    /**
     * Gets the value of the taxRegCodeTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxRegCodeTo() {
        return taxRegCodeTo;
    }

    /**
     * Sets the value of the taxRegCodeTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxRegCodeTo(String value) {
        this.taxRegCodeTo = value;
    }

    /**
     * Gets the value of the budgetCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBudgetCode() {
        return budgetCode;
    }

    /**
     * Sets the value of the budgetCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBudgetCode(String value) {
        this.budgetCode = value;
    }

    /**
     * Gets the value of the okato property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOKATO() {
        return okato;
    }

    /**
     * Sets the value of the okato property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOKATO(String value) {
        this.okato = value;
    }

    /**
     * Gets the value of the taxBase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxBase() {
        return taxBase;
    }

    /**
     * Sets the value of the taxBase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxBase(String value) {
        this.taxBase = value;
    }

    /**
     * Gets the value of the taxPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxPeriod() {
        return taxPeriod;
    }

    /**
     * Sets the value of the taxPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxPeriod(String value) {
        this.taxPeriod = value;
    }

    /**
     * Gets the value of the taxNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxNum() {
        return taxNum;
    }

    /**
     * Sets the value of the taxNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxNum(String value) {
        this.taxNum = value;
    }

    /**
     * Gets the value of the taxDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getTaxDt() {
        return taxDt;
    }

    /**
     * Sets the value of the taxDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxDt(Calendar value) {
        this.taxDt = value;
    }

    /**
     * Gets the value of the taxType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxType() {
        return taxType;
    }

    /**
     * Sets the value of the taxType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxType(String value) {
        this.taxType = value;
    }

}
