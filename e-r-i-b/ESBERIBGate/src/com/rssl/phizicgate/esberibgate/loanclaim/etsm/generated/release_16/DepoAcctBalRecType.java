
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16;

import java.math.BigDecimal;
import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;


/**
 * Разбивка задолженности ДЕПО
 * 
 * <p>Java class for DepoAcctBalRec_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DepoAcctBalRec_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RecNumber" type="{}String"/>
 *         &lt;element name="EffDt" type="{}Date"/>
 *         &lt;element name="CurAmt" type="{}Decimal"/>
 *         &lt;element name="CurAmtNDS" type="{}Decimal"/>
 *         &lt;element ref="{}AcctCur"/>
 *         &lt;element name="StrDt" type="{}Date"/>
 *         &lt;element name="EndDt" type="{}Date"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DepoAcctBalRec_Type", propOrder = {
    "recNumber",
    "effDt",
    "curAmt",
    "curAmtNDS",
    "acctCur",
    "strDt",
    "endDt"
})
public class DepoAcctBalRecType {

    @XmlElement(name = "RecNumber", required = true)
    protected String recNumber;
    @XmlElement(name = "EffDt", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar effDt;
    @XmlElement(name = "CurAmt", required = true)
    protected BigDecimal curAmt;
    @XmlElement(name = "CurAmtNDS", required = true)
    protected BigDecimal curAmtNDS;
    @XmlElement(name = "AcctCur", required = true)
    protected String acctCur;
    @XmlElement(name = "StrDt", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar strDt;
    @XmlElement(name = "EndDt", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar endDt;

    /**
     * Gets the value of the recNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecNumber() {
        return recNumber;
    }

    /**
     * Sets the value of the recNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecNumber(String value) {
        this.recNumber = value;
    }

    /**
     * Gets the value of the effDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getEffDt() {
        return effDt;
    }

    /**
     * Sets the value of the effDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEffDt(Calendar value) {
        this.effDt = value;
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
     * Gets the value of the curAmtNDS property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCurAmtNDS() {
        return curAmtNDS;
    }

    /**
     * Sets the value of the curAmtNDS property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCurAmtNDS(BigDecimal value) {
        this.curAmtNDS = value;
    }

    /**
     * Gets the value of the acctCur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcctCur() {
        return acctCur;
    }

    /**
     * Sets the value of the acctCur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcctCur(String value) {
        this.acctCur = value;
    }

    /**
     * Gets the value of the strDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getStrDt() {
        return strDt;
    }

    /**
     * Sets the value of the strDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrDt(Calendar value) {
        this.strDt = value;
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

}
