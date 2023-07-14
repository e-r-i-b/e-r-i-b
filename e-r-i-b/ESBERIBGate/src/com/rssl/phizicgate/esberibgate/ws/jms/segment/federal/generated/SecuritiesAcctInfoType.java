
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Информация о сертификатах
 * 
 * <p>Java class for SecuritiesAcctInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SecuritiesAcctInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Holder" type="{}CustInfo_Type"/>
 *         &lt;element name="SecuritiesRec" type="{}SecuritiesRecShort_Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SecuritiesAcctInfo_Type", propOrder = {
    "holder",
    "securitiesRecs"
})
public class SecuritiesAcctInfoType {

    @XmlElement(name = "Holder", required = true)
    protected CustInfoType holder;
    @XmlElement(name = "SecuritiesRec")
    protected List<SecuritiesRecShortType> securitiesRecs;

    /**
     * Gets the value of the holder property.
     * 
     * @return
     *     possible object is
     *     {@link CustInfoType }
     *     
     */
    public CustInfoType getHolder() {
        return holder;
    }

    /**
     * Sets the value of the holder property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustInfoType }
     *     
     */
    public void setHolder(CustInfoType value) {
        this.holder = value;
    }

    /**
     * Gets the value of the securitiesRecs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the securitiesRecs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSecuritiesRecs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SecuritiesRecShortType }
     * 
     * 
     */
    public List<SecuritiesRecShortType> getSecuritiesRecs() {
        if (securitiesRecs == null) {
            securitiesRecs = new ArrayList<SecuritiesRecShortType>();
        }
        return this.securitiesRecs;
    }

}
