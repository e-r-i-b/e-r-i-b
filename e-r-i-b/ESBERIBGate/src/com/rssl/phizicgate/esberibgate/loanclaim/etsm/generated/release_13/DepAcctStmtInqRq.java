
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateTimeAdapter;


/**
 * ��� ���������-������� ��� ���������� DAS - ������ ������� �� ��������� �����
 * 
 * <p>Java class for DepAcctStmtInqRq_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DepAcctStmtInqRq_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}RqUID"/>
 *         &lt;element ref="{}RqTm"/>
 *         &lt;element ref="{}OperUID"/>
 *         &lt;element ref="{}SPName"/>
 *         &lt;element ref="{}BankInfo"/>
 *         &lt;element name="DepAcctRec">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{}DepAcctId"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{}StmtType"/>
 *         &lt;element name="DateFrom" type="{}Date" minOccurs="0"/>
 *         &lt;element name="DateTo" type="{}Date" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DepAcctStmtInqRq_Type", propOrder = {
    "rqUID",
    "rqTm",
    "operUID",
    "spName",
    "bankInfo",
    "depAcctRec",
    "stmtType",
    "dateFrom",
    "dateTo"
})
@XmlRootElement(name = "DepAcctStmtInqRq")
public class DepAcctStmtInqRq {

    @XmlElement(name = "RqUID", required = true)
    protected String rqUID;
    @XmlElement(name = "RqTm", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeAdapter.class)
    protected Calendar rqTm;
    @XmlElement(name = "OperUID", required = true)
    protected String operUID;
    @XmlElement(name = "SPName", required = true)
    protected SPNameType spName;
    @XmlElement(name = "BankInfo", required = true)
    protected BankInfo bankInfo;
    @XmlElement(name = "DepAcctRec", required = true)
    protected DepAcctStmtInqRq.DepAcctRec depAcctRec;
    @XmlElement(name = "StmtType", required = true)
    protected StmtTypeType stmtType;
    @XmlElement(name = "DateFrom", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar dateFrom;
    @XmlElement(name = "DateTo", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar dateTo;

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
     * Gets the value of the depAcctRec property.
     * 
     * @return
     *     possible object is
     *     {@link DepAcctStmtInqRq.DepAcctRec }
     *     
     */
    public DepAcctStmtInqRq.DepAcctRec getDepAcctRec() {
        return depAcctRec;
    }

    /**
     * Sets the value of the depAcctRec property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepAcctStmtInqRq.DepAcctRec }
     *     
     */
    public void setDepAcctRec(DepAcctStmtInqRq.DepAcctRec value) {
        this.depAcctRec = value;
    }

    /**
     * Gets the value of the stmtType property.
     * 
     * @return
     *     possible object is
     *     {@link StmtTypeType }
     *     
     */
    public StmtTypeType getStmtType() {
        return stmtType;
    }

    /**
     * Sets the value of the stmtType property.
     * 
     * @param value
     *     allowed object is
     *     {@link StmtTypeType }
     *     
     */
    public void setStmtType(StmtTypeType value) {
        this.stmtType = value;
    }

    /**
     * Gets the value of the dateFrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getDateFrom() {
        return dateFrom;
    }

    /**
     * Sets the value of the dateFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateFrom(Calendar value) {
        this.dateFrom = value;
    }

    /**
     * Gets the value of the dateTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getDateTo() {
        return dateTo;
    }

    /**
     * Sets the value of the dateTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateTo(Calendar value) {
        this.dateTo = value;
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
     *         &lt;element ref="{}DepAcctId"/>
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
        "depAcctId"
    })
    public static class DepAcctRec {

        @XmlElement(name = "DepAcctId", required = true)
        protected DepAcctIdType depAcctId;

        /**
         * Gets the value of the depAcctId property.
         * 
         * @return
         *     possible object is
         *     {@link DepAcctIdType }
         *     
         */
        public DepAcctIdType getDepAcctId() {
            return depAcctId;
        }

        /**
         * Sets the value of the depAcctId property.
         * 
         * @param value
         *     allowed object is
         *     {@link DepAcctIdType }
         *     
         */
        public void setDepAcctId(DepAcctIdType value) {
            this.depAcctId = value;
        }

    }

}
