
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;


/**
 * Информация о платеже по кредиту
 * 
 * <p>Java class for LoanPaymentRec_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LoanPaymentRec_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LoanPaymentNumber" type="{}Long"/>
 *         &lt;element name="LoanPaymentStatus" type="{}String"/>
 *         &lt;element name="LoanPaymentDate" type="{}Date"/>
 *         &lt;element name="AcctBalOnDate" type="{}AcctBal_Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LoanPaymentRec_Type", propOrder = {
    "loanPaymentNumber",
    "loanPaymentStatus",
    "loanPaymentDate",
    "acctBalOnDates"
})
@XmlRootElement(name = "LoanPaymentRec")
public class LoanPaymentRec {

    @XmlElement(name = "LoanPaymentNumber")
    protected long loanPaymentNumber;
    @XmlElement(name = "LoanPaymentStatus", required = true)
    protected String loanPaymentStatus;
    @XmlElement(name = "LoanPaymentDate", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar loanPaymentDate;
    @XmlElement(name = "AcctBalOnDate")
    protected List<AcctBal> acctBalOnDates;

    /**
     * Gets the value of the loanPaymentNumber property.
     * 
     */
    public long getLoanPaymentNumber() {
        return loanPaymentNumber;
    }

    /**
     * Sets the value of the loanPaymentNumber property.
     * 
     */
    public void setLoanPaymentNumber(long value) {
        this.loanPaymentNumber = value;
    }

    /**
     * Gets the value of the loanPaymentStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoanPaymentStatus() {
        return loanPaymentStatus;
    }

    /**
     * Sets the value of the loanPaymentStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoanPaymentStatus(String value) {
        this.loanPaymentStatus = value;
    }

    /**
     * Gets the value of the loanPaymentDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getLoanPaymentDate() {
        return loanPaymentDate;
    }

    /**
     * Sets the value of the loanPaymentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoanPaymentDate(Calendar value) {
        this.loanPaymentDate = value;
    }

    /**
     * Gets the value of the acctBalOnDates property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the acctBalOnDates property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAcctBalOnDates().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AcctBal }
     * 
     * 
     */
    public List<AcctBal> getAcctBalOnDates() {
        if (acctBalOnDates == null) {
            acctBalOnDates = new ArrayList<AcctBal>();
        }
        return this.acctBalOnDates;
    }

}
