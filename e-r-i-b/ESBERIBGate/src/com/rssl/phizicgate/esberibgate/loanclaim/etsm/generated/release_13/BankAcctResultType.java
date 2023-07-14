
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Результат изменения прав доступа для каждого счета в отдельности
 * 
 * <p>Java class for BankAcctResult_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BankAcctResult_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="DepAcctRec" type="{}DepAcctRec_Type" maxOccurs="unbounded"/>
 *           &lt;element name="CardAcctRec" type="{}CardAcctRec_Type" maxOccurs="unbounded"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BankAcctResult_Type", propOrder = {
    "cardAcctRecs",
    "depAcctRecs"
})
public class BankAcctResultType {

    @XmlElement(name = "CardAcctRec")
    protected List<CardAcctRec> cardAcctRecs;
    @XmlElement(name = "DepAcctRec")
    protected List<DepAcctRec> depAcctRecs;

    /**
     * Gets the value of the cardAcctRecs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cardAcctRecs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCardAcctRecs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CardAcctRec }
     * 
     * 
     */
    public List<CardAcctRec> getCardAcctRecs() {
        if (cardAcctRecs == null) {
            cardAcctRecs = new ArrayList<CardAcctRec>();
        }
        return this.cardAcctRecs;
    }

    /**
     * Gets the value of the depAcctRecs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the depAcctRecs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDepAcctRecs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DepAcctRec }
     * 
     * 
     */
    public List<DepAcctRec> getDepAcctRecs() {
        if (depAcctRecs == null) {
            depAcctRecs = new ArrayList<DepAcctRec>();
        }
        return this.depAcctRecs;
    }

}
