
package com.rssl.phizicgate.mdm.integration.mdm.generated;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateTimeAdapter;


/**
 * <p>Java class for PayrollInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PayrollInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}AgreemtNum" minOccurs="0"/>
 *         &lt;element ref="{}TaxId"/>
 *         &lt;element ref="{}LegalFormType"/>
 *         &lt;element ref="{}LegalName"/>
 *         &lt;element ref="{}StartDt" minOccurs="0"/>
 *         &lt;element ref="{}EndDt" minOccurs="0"/>
 *         &lt;element ref="{}CodeOfDisconnect" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PayrollInfo_Type", propOrder = {
    "agreemtNum",
    "taxId",
    "legalFormType",
    "legalName",
    "startDt",
    "endDt",
    "codeOfDisconnect"
})
@XmlRootElement(name = "PayrollInfo")
public class PayrollInfo {

    @XmlElement(name = "AgreemtNum")
    protected String agreemtNum;
    @XmlElement(name = "TaxId", required = true)
    protected String taxId;
    @XmlElement(name = "LegalFormType", required = true)
    protected String legalFormType;
    @XmlElement(name = "LegalName", required = true)
    protected String legalName;
    @XmlElement(name = "StartDt", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeAdapter.class)
    protected Calendar startDt;
    @XmlElement(name = "EndDt", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeAdapter.class)
    protected Calendar endDt;
    @XmlElement(name = "CodeOfDisconnect")
    protected Integer codeOfDisconnect;

    /**
     * Gets the value of the agreemtNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgreemtNum() {
        return agreemtNum;
    }

    /**
     * Sets the value of the agreemtNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgreemtNum(String value) {
        this.agreemtNum = value;
    }

    /**
     * Gets the value of the taxId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxId() {
        return taxId;
    }

    /**
     * Sets the value of the taxId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxId(String value) {
        this.taxId = value;
    }

    /**
     * Gets the value of the legalFormType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLegalFormType() {
        return legalFormType;
    }

    /**
     * Sets the value of the legalFormType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLegalFormType(String value) {
        this.legalFormType = value;
    }

    /**
     * Gets the value of the legalName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLegalName() {
        return legalName;
    }

    /**
     * Sets the value of the legalName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLegalName(String value) {
        this.legalName = value;
    }

    /**
     * Gets the value of the startDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getStartDt() {
        return startDt;
    }

    /**
     * Sets the value of the startDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartDt(Calendar value) {
        this.startDt = value;
    }

    /**
     * Gets the value of the endDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getEndDt() {
        return endDt;
    }

    /**
     * Sets the value of the endDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndDt(Calendar value) {
        this.endDt = value;
    }

    /**
     * Gets the value of the codeOfDisconnect property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodeOfDisconnect() {
        return codeOfDisconnect;
    }

    /**
     * Sets the value of the codeOfDisconnect property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodeOfDisconnect(Integer value) {
        this.codeOfDisconnect = value;
    }

}
