
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * ¬алидаторы (контроли), используемые дл€ дополнительного атрибута. Ќеприменимо дл€ типов set и list.
 * 
 * <p>Java class for Validators_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Validators_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Validator" type="{}Validator_Type" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Validators_Type", propOrder = {
    "validators"
})
public class ValidatorsType {

    @XmlElement(name = "Validator", required = true)
    protected List<ValidatorType> validators;

    /**
     * Gets the value of the validators property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the validators property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValidators().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ValidatorType }
     * 
     * 
     */
    public List<ValidatorType> getValidators() {
        if (validators == null) {
            validators = new ArrayList<ValidatorType>();
        }
        return this.validators;
    }

}
