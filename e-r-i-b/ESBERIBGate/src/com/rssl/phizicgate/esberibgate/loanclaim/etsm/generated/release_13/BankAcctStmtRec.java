
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
 * Информация по выписке по ОМС
 * 
 * <p>Java class for BankAcctStmtRec_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BankAcctStmtRec_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}EffDate"/>
 *         &lt;element ref="{}StmtSummAmt"/>
 *         &lt;element ref="{}AcctBal"/>
 *         &lt;element name="IMAOperConvInfo" type="{}IMAOperConvInfo_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BankAcctStmtRec_Type", propOrder = {
    "effDate",
    "stmtSummAmt",
    "acctBal",
    "imaOperConvInfo"
})
@XmlRootElement(name = "BankAcctStmtRec")
public class BankAcctStmtRec {

    @XmlElement(name = "EffDate", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar effDate;
    @XmlElement(name = "StmtSummAmt", required = true)
    protected StmtSummAmt stmtSummAmt;
    @XmlElement(name = "AcctBal", required = true)
    protected AcctBal acctBal;
    @XmlElement(name = "IMAOperConvInfo")
    protected IMAOperConvInfoType imaOperConvInfo;

    /**
     * Gets the value of the effDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getEffDate() {
        return effDate;
    }

    /**
     * Sets the value of the effDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEffDate(Calendar value) {
        this.effDate = value;
    }

    /**
     * Gets the value of the stmtSummAmt property.
     * 
     * @return
     *     possible object is
     *     {@link StmtSummAmt }
     *     
     */
    public StmtSummAmt getStmtSummAmt() {
        return stmtSummAmt;
    }

    /**
     * Sets the value of the stmtSummAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link StmtSummAmt }
     *     
     */
    public void setStmtSummAmt(StmtSummAmt value) {
        this.stmtSummAmt = value;
    }

    /**
     * Тип остатков BalType для ОМС: BalanceAfterOperation
     * 
     * @return
     *     possible object is
     *     {@link AcctBal }
     *     
     */
    public AcctBal getAcctBal() {
        return acctBal;
    }

    /**
     * Sets the value of the acctBal property.
     * 
     * @param value
     *     allowed object is
     *     {@link AcctBal }
     *     
     */
    public void setAcctBal(AcctBal value) {
        this.acctBal = value;
    }

    /**
     * Gets the value of the imaOperConvInfo property.
     * 
     * @return
     *     possible object is
     *     {@link IMAOperConvInfoType }
     *     
     */
    public IMAOperConvInfoType getIMAOperConvInfo() {
        return imaOperConvInfo;
    }

    /**
     * Sets the value of the imaOperConvInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link IMAOperConvInfoType }
     *     
     */
    public void setIMAOperConvInfo(IMAOperConvInfoType value) {
        this.imaOperConvInfo = value;
    }

}
