
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Запись об остатке по счету
 * 
 * <p>Java class for AcctInqRec_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AcctInqRec_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}BankAcctRes" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}DepAcctRes" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}CardAcctRes" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AcctInqRec_Type", propOrder = {
    "bankAcctRes",
    "depAcctRes",
    "cardAcctRes"
})
@XmlRootElement(name = "AcctInqRec")
public class AcctInqRec {

    @XmlElement(name = "BankAcctRes")
    protected List<BankAcctRes> bankAcctRes;
    @XmlElement(name = "DepAcctRes")
    protected List<DepAcctRes> depAcctRes;
    @XmlElement(name = "CardAcctRes")
    protected List<CardAcctRes> cardAcctRes;

    /**
     * Gets the value of the bankAcctRes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bankAcctRes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBankAcctRes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BankAcctRes }
     * 
     * 
     */
    public List<BankAcctRes> getBankAcctRes() {
        if (bankAcctRes == null) {
            bankAcctRes = new ArrayList<BankAcctRes>();
        }
        return this.bankAcctRes;
    }

    /**
     * Gets the value of the depAcctRes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the depAcctRes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDepAcctRes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DepAcctRes }
     * 
     * 
     */
    public List<DepAcctRes> getDepAcctRes() {
        if (depAcctRes == null) {
            depAcctRes = new ArrayList<DepAcctRes>();
        }
        return this.depAcctRes;
    }

    /**
     * Gets the value of the cardAcctRes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cardAcctRes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCardAcctRes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CardAcctRes }
     * 
     * 
     */
    public List<CardAcctRes> getCardAcctRes() {
        if (cardAcctRes == null) {
            cardAcctRes = new ArrayList<CardAcctRes>();
        }
        return this.cardAcctRes;
    }

}
