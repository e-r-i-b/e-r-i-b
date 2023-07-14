
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 *   Информация   по  Тарифному    модулю
 * 
 * <p>Java class for TarifUnionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TarifUnionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TariffType" type="{}Long" minOccurs="0"/>
 *         &lt;element name="FeeByFirstYear" type="{}Long" minOccurs="0"/>
 *         &lt;element name="FeeByOtherYears" type="{}Long" minOccurs="0"/>
 *         &lt;element name="CodePlanTariff" type="{}TarifPlanCodeType" minOccurs="0"/>
 *         &lt;element name="TariffClassifier" type="{}TariffClassifier_Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TarifUnionType", propOrder = {
    "tariffType",
    "feeByFirstYear",
    "feeByOtherYears",
    "codePlanTariff",
    "tariffClassifiers"
})
public class TarifUnionType {

    @XmlElement(name = "TariffType")
    protected Long tariffType;
    @XmlElement(name = "FeeByFirstYear")
    protected Long feeByFirstYear;
    @XmlElement(name = "FeeByOtherYears")
    protected Long feeByOtherYears;
    @XmlElement(name = "CodePlanTariff")
    protected String codePlanTariff;
    @XmlElement(name = "TariffClassifier")
    protected List<TariffClassifierType> tariffClassifiers;

    /**
     * Gets the value of the tariffType property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTariffType() {
        return tariffType;
    }

    /**
     * Sets the value of the tariffType property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTariffType(Long value) {
        this.tariffType = value;
    }

    /**
     * Gets the value of the feeByFirstYear property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getFeeByFirstYear() {
        return feeByFirstYear;
    }

    /**
     * Sets the value of the feeByFirstYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setFeeByFirstYear(Long value) {
        this.feeByFirstYear = value;
    }

    /**
     * Gets the value of the feeByOtherYears property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getFeeByOtherYears() {
        return feeByOtherYears;
    }

    /**
     * Sets the value of the feeByOtherYears property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setFeeByOtherYears(Long value) {
        this.feeByOtherYears = value;
    }

    /**
     * Gets the value of the codePlanTariff property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodePlanTariff() {
        return codePlanTariff;
    }

    /**
     * Sets the value of the codePlanTariff property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodePlanTariff(String value) {
        this.codePlanTariff = value;
    }

    /**
     * Gets the value of the tariffClassifiers property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tariffClassifiers property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTariffClassifiers().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TariffClassifierType }
     * 
     * 
     */
    public List<TariffClassifierType> getTariffClassifiers() {
        if (tariffClassifiers == null) {
            tariffClassifiers = new ArrayList<TariffClassifierType>();
        }
        return this.tariffClassifiers;
    }

}
