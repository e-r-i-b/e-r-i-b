
package com.rssl.phizicgate.mdm.integration.mdm.generated;

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
 * <p>Java class for CardInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CardInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ProdType" minOccurs="0"/>
 *         &lt;element ref="{}CardType" minOccurs="0"/>
 *         &lt;element ref="{}CardNum"/>
 *         &lt;element ref="{}AdditionalCard" minOccurs="0"/>
 *         &lt;element name="StartDt" type="{}Date" minOccurs="0"/>
 *         &lt;element name="ExpDt" type="{}Date" minOccurs="0"/>
 *         &lt;element ref="{}CustId" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}AgreemtCustRole" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CardInfo_Type", propOrder = {
    "prodType",
    "cardType",
    "cardNum",
    "additionalCard",
    "startDt",
    "expDt",
    "custIds",
    "agreemtCustRoles"
})
@XmlRootElement(name = "CardInfo")
public class CardInfo {

    @XmlElement(name = "ProdType")
    protected String prodType;
    @XmlElement(name = "CardType")
    protected String cardType;
    @XmlElement(name = "CardNum", required = true)
    protected String cardNum;
    @XmlElement(name = "AdditionalCard")
    protected String additionalCard;
    @XmlElement(name = "StartDt", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar startDt;
    @XmlElement(name = "ExpDt", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar expDt;
    @XmlElement(name = "CustId")
    protected List<CustId> custIds;
    @XmlElement(name = "AgreemtCustRole")
    protected List<AgreemtCustRole> agreemtCustRoles;

    /**
     * Gets the value of the prodType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProdType() {
        return prodType;
    }

    /**
     * Sets the value of the prodType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProdType(String value) {
        this.prodType = value;
    }

    /**
     * Gets the value of the cardType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardType() {
        return cardType;
    }

    /**
     * Sets the value of the cardType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardType(String value) {
        this.cardType = value;
    }

    /**
     * Gets the value of the cardNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardNum() {
        return cardNum;
    }

    /**
     * Sets the value of the cardNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardNum(String value) {
        this.cardNum = value;
    }

    /**
     * Gets the value of the additionalCard property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalCard() {
        return additionalCard;
    }

    /**
     * Sets the value of the additionalCard property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalCard(String value) {
        this.additionalCard = value;
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
     * Gets the value of the expDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getExpDt() {
        return expDt;
    }

    /**
     * Sets the value of the expDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpDt(Calendar value) {
        this.expDt = value;
    }

    /**
     * Gets the value of the custIds property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the custIds property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCustIds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CustId }
     * 
     * 
     */
    public List<CustId> getCustIds() {
        if (custIds == null) {
            custIds = new ArrayList<CustId>();
        }
        return this.custIds;
    }

    /**
     * Gets the value of the agreemtCustRoles property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the agreemtCustRoles property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAgreemtCustRoles().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AgreemtCustRole }
     * 
     * 
     */
    public List<AgreemtCustRole> getAgreemtCustRoles() {
        if (agreemtCustRoles == null) {
            agreemtCustRoles = new ArrayList<AgreemtCustRole>();
        }
        return this.agreemtCustRoles;
    }

}
