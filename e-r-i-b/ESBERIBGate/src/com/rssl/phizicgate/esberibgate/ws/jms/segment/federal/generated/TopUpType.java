
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Блок данных по TopUp кредитам
 * 
 * <p>Java class for TopUp_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TopUp_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TopUpLoanListCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TotalRepaymentSum" type="{}Amt_Type" minOccurs="0"/>
 *         &lt;element name="RepayLoan" type="{}RepayLoan_Type" maxOccurs="5"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TopUp_Type", propOrder = {
    "topUpLoanListCount",
    "totalRepaymentSum",
    "repayLoen"
})
public class TopUpType {

    @XmlElement(name = "TopUpLoanListCount")
    protected int topUpLoanListCount;
    @XmlElement(name = "TotalRepaymentSum")
    protected BigDecimal totalRepaymentSum;
    @XmlElement(name = "RepayLoan", required = true)
    protected List<RepayLoanType> repayLoen;

    /**
     * Gets the value of the topUpLoanListCount property.
     * 
     */
    public int getTopUpLoanListCount() {
        return topUpLoanListCount;
    }

    /**
     * Sets the value of the topUpLoanListCount property.
     * 
     */
    public void setTopUpLoanListCount(int value) {
        this.topUpLoanListCount = value;
    }

    /**
     * Gets the value of the totalRepaymentSum property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalRepaymentSum() {
        return totalRepaymentSum;
    }

    /**
     * Sets the value of the totalRepaymentSum property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalRepaymentSum(BigDecimal value) {
        this.totalRepaymentSum = value;
    }

    /**
     * Gets the value of the repayLoen property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the repayLoen property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRepayLoen().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RepayLoanType }
     * 
     * 
     */
    public List<RepayLoanType> getRepayLoen() {
        if (repayLoen == null) {
            repayLoen = new ArrayList<RepayLoanType>();
        }
        return this.repayLoen;
    }

}
