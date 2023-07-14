
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Тип Информация о шаблоне автоплатежа
 * 
 * <p>Java class for AutoPaymentTemplate_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AutoPaymentTemplate_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}SystemId" minOccurs="0"/>
 *         &lt;element ref="{}RecipientRec" minOccurs="0"/>
 *         &lt;element ref="{}BankAcctRec" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AutoPaymentTemplate_Type", propOrder = {
    "systemId",
    "recipientRec",
    "bankAcctRecs"
})
public class AutoPaymentTemplateType {

    @XmlElement(name = "SystemId")
    protected String systemId;
    @XmlElement(name = "RecipientRec")
    protected RecipientRec recipientRec;
    @XmlElement(name = "BankAcctRec")
    protected List<BankAcctRec> bankAcctRecs;

    /**
     * Gets the value of the systemId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSystemId() {
        return systemId;
    }

    /**
     * Sets the value of the systemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSystemId(String value) {
        this.systemId = value;
    }

    /**
     * Gets the value of the recipientRec property.
     * 
     * @return
     *     possible object is
     *     {@link RecipientRec }
     *     
     */
    public RecipientRec getRecipientRec() {
        return recipientRec;
    }

    /**
     * Sets the value of the recipientRec property.
     * 
     * @param value
     *     allowed object is
     *     {@link RecipientRec }
     *     
     */
    public void setRecipientRec(RecipientRec value) {
        this.recipientRec = value;
    }

    /**
     * Gets the value of the bankAcctRecs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bankAcctRecs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBankAcctRecs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BankAcctRec }
     * 
     * 
     */
    public List<BankAcctRec> getBankAcctRecs() {
        if (bankAcctRecs == null) {
            bankAcctRecs = new ArrayList<BankAcctRec>();
        }
        return this.bankAcctRecs;
    }

}
