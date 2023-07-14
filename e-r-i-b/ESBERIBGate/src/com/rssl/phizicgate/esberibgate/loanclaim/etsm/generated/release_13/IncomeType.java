
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Тип - доходы и расходы
 * 
 * <p>Java class for Income_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Income_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BasicIncome6M" type="{}Amount_10_2_Type"/>
 *         &lt;element name="AddIncome6M" type="{}Amount_10_2_Type"/>
 *         &lt;element name="FamilyIncome6M" type="{}Amount_10_2_Type"/>
 *         &lt;element name="Expenses6M" type="{}Amount_10_2_Type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Income_Type", propOrder = {
    "basicIncome6M",
    "addIncome6M",
    "familyIncome6M",
    "expenses6M"
})
public class IncomeType {

    @XmlElement(name = "BasicIncome6M", required = true)
    protected BigDecimal basicIncome6M;
    @XmlElement(name = "AddIncome6M", required = true)
    protected BigDecimal addIncome6M;
    @XmlElement(name = "FamilyIncome6M", required = true)
    protected BigDecimal familyIncome6M;
    @XmlElement(name = "Expenses6M", required = true)
    protected BigDecimal expenses6M;

    /**
     * Gets the value of the basicIncome6M property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBasicIncome6M() {
        return basicIncome6M;
    }

    /**
     * Sets the value of the basicIncome6M property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBasicIncome6M(BigDecimal value) {
        this.basicIncome6M = value;
    }

    /**
     * Gets the value of the addIncome6M property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAddIncome6M() {
        return addIncome6M;
    }

    /**
     * Sets the value of the addIncome6M property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAddIncome6M(BigDecimal value) {
        this.addIncome6M = value;
    }

    /**
     * Gets the value of the familyIncome6M property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getFamilyIncome6M() {
        return familyIncome6M;
    }

    /**
     * Sets the value of the familyIncome6M property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setFamilyIncome6M(BigDecimal value) {
        this.familyIncome6M = value;
    }

    /**
     * Gets the value of the expenses6M property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getExpenses6M() {
        return expenses6M;
    }

    /**
     * Sets the value of the expenses6M property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setExpenses6M(BigDecimal value) {
        this.expenses6M = value;
    }

}
