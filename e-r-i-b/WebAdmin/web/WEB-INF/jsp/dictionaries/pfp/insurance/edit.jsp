<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/pfp/insurance/productEdit" onsubmit="return setEmptyAction();" enctype="multipart/form-data">
	<tiles:insert definition="editPFPInsuranceProduct">
        <tiles:put name="submenu" type="string" value="insuranceEdit"/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
                <tiles:put name="id"  value="pfpInsurance"/>
                <tiles:put name="name">
                    <bean:message bundle="pfpInsuranceBundle" key="label.edit.name"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message bundle="pfpInsuranceBundle" key="label.edit.description"/>
                </tiles:put>
                <tiles:put name="data">
                    <script type="text/javascript">
                        var insuranceTypesArray = {};
                    </script>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpInsuranceBundle" key="label.edit.field.name"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="fields(name)" size="50" maxlength="250"/>
                            <div class="clear"></div>
                            <span class="notForComplex">
                                <html:checkbox property="fields(universal)" styleId="universal"/>
                                <label for="universal"><bean:message bundle="pfpProductBundle" key="label.universal.product"/></label>
                            </span>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpInsuranceBundle" key="label.edit.field.insuranceCompany"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:hidden property="fields(insuranceCompanyId)"/>
                            <html:text property="fields(insuranceCompanyName)" size="50" readonly="true"/>
                            <input type="button" class="buttWhite" onclick="openInsuranceCompanyDictionaries();" value="..."/>
                        </tiles:put>
                    </tiles:insert>

                    <div class="notForComplex">
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="pfpInsuranceBundle" key="label.edit.field.type.parent"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:select property="fields(typeParentId)" onchange="selectParentType();" styleClass="parentInsuranceTypes">
                                    <c:forEach var="type" items="${form.fields['insuranceTypeList']}">
                                        <c:if test="${type.parent == null}">
                                            <script type="text/javascript">
                                                insuranceTypesArray['${type.id}'] = new Array();
                                            </script>
                                            <html:option value="${type.id}">${type.name}</html:option>
                                        </c:if>
                                    </c:forEach>
                                </html:select>
                            </tiles:put>
                        </tiles:insert>
                    </div>

                    <div class="notForComplex">
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="pfpInsuranceBundle" key="label.edit.field.type"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:select property="fields(typeId)" styleClass="childInsuranceTypes">
                                    <c:forEach var="type" items="${form.fields['insuranceTypeList']}">
                                        <c:if test="${type.parent != null}">
                                            <html:option value="${type.id}" styleId="${type.id}" styleClass="insuranceTypeFor${type.parent.id}">${type.name}</html:option>
                                            <script type="text/javascript">
                                                insuranceTypesArray['${type.parent.id}'].push($('#' + ${type.id}));
                                            </script>
                                        </c:if>
                                    </c:forEach>
                                </html:select>
                            </tiles:put>
                        </tiles:insert>
                    </div>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="data">
                            <html:checkbox property="fields(forComplex)" styleId="fields(forComplex)" value="true" onclick="forComplexClick();"/>&nbsp;
                            <label for="fields(forComplex)"><bean:message bundle="pfpInsuranceBundle" key="label.edit.field.forComplex.true"/></label>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpProductBundle" key="product.label.income"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <b><bean:message bundle="pfpProductBundle" key="product.label.income.min.prefix"/>&nbsp;</b>
                            <html:text property="fields(minIncome)" size="10" maxlength="5"/>&nbsp;
                            <b><bean:message bundle="pfpProductBundle" key="product.label.income.max.prefix"/>&nbsp;</b>
                            <html:text property="fields(maxIncome)" size="10" maxlength="5"/>&nbsp;
                            <b><bean:message bundle="pfpProductBundle" key="product.label.income.postfix"/></b>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpProductBundle" key="product.label.income.default"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="fields(defaultIncome)" size="10" maxlength="5"/>&nbsp;
                            <b><bean:message bundle="pfpProductBundle" key="product.label.income.postfix"/></b>
                        </tiles:put>
                    </tiles:insert>

                    <div class="notForComplex">
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="pfpProductBundle" key="product.base.segment.field.name"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <jsp:include page="../products/setSegmentCodeTypeArea.jsp"/>
                            </tiles:put>
                        </tiles:insert>
                    </div>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpProductBundle" key="label.universal.enabled"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:radio property="fields(enabled)" styleId="enabledYes" value="true"/>
                            <label for="enabledYes"><bean:message bundle="pfpProductBundle" key="label.universal.enabled.yes"/></label>
                            <br/>
                            <html:radio property="fields(enabled)" styleId="enabledNo" value="false"/>
                            <label for="enabledNo"><bean:message bundle="pfpProductBundle" key="label.universal.enabled.no"/></label>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpInsuranceBundle" key="label.edit.field.image"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <tiles:insert definition="imageInput" flush="false"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpInsuranceBundle" key="label.edit.field.age"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            &nbsp;<bean:message bundle="pfpInsuranceBundle" key="label.edit.field.age.from"/>&nbsp;<html:text property="fields(minAge)" size="4" maxlength="3"/>
                            &nbsp;<bean:message bundle="pfpInsuranceBundle" key="label.edit.field.age.to"/>&nbsp;<html:text property="fields(maxAge)" size="4" maxlength="3"/>
                            &nbsp;<b><bean:message bundle="pfpInsuranceBundle" key="label.edit.field.age.unit"/></b>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpInsuranceBundle" key="label.edit.field.periodInformation.add.title"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <tiles:insert definition="clientButton" flush="false" operation="ListProductTypeParametersOperation">
                                <tiles:put name="commandTextKey"    value="label.edit.field.periodInformation.add"/>
                                <tiles:put name="commandHelpKey"    value="label.edit.field.periodInformation.add"/>
                                <tiles:put name="bundle"            value="pfpInsuranceBundle"/>
                                <tiles:put name="onclick"           value="addPeriodInfoRow();"/>
                                <tiles:put name="viewType"          value="buttonGrayNew"/>
                            </tiles:insert>
                        </tiles:put>
                    </tiles:insert>

                    <fieldset id="insurancePeriodTypesInfo">
                        <div id="insurancePeriodTypes">
                            <c:set var="periodInfoMaxRowIndex" value="0"/>
                            <c:set var="defaultRowIndex" value="${form.fields['defaultPeriod']}"/>
                            <c:forEach var="rowIndex" items="${form.lineNumbers}">
                                <c:set var="periodTypeFieldName" value="idPeriodType${rowIndex}"/>
                                <c:set var="periodFieldName" value="period${rowIndex}"/>
                                <c:set var="minSumFieldName" value="minSum${rowIndex}"/>
                                <c:set var="maxSumFieldName" value="maxSum${rowIndex}"/>

                                <div class="insurancePeriodInfo${rowIndex}">
                                    <tiles:insert definition="simpleFormRow" flush="false">
                                        <tiles:put name="data">
                                            <input type="hidden" name="lineNumbers" value="${rowIndex}"/>
                                            <input type="radio"
                                            <c:if test="${defaultRowIndex == rowIndex}">
                                                   checked
                                            </c:if>
                                                   name="fields(defaultPeriod)"
                                                   value="${rowIndex}">
                                            <bean:message bundle="pfpInsuranceBundle" key="label.edit.field.periodInformation.isDefault"/>
                                        </tiles:put>
                                    </tiles:insert>
                                </div>
                                <div class="insurancePeriodInfo${rowIndex}">
                                    <tiles:insert definition="simpleFormRow" flush="false">
                                        <tiles:put name="title">
                                            <bean:message bundle="pfpInsuranceBundle" key="label.edit.field.periodInformation.periodType"/>
                                        </tiles:put>
                                        <tiles:put name="isNecessary" value="true"/>
                                        <tiles:put name="data">
                                            <input type="hidden" name="periodTypeIds" id="periodTypeId${rowIndex}" value="${form.fields[periodTypeFieldName]}"/>
                                            <html:hidden property="fields(${periodTypeFieldName})"/>
                                            <html:text property="fields(namePeriodType${rowIndex})" size="58" readonly="true"/>
                                            <input type="button" class="buttWhite" onclick="openPeriodTypeDictionaries(${rowIndex});" value="..."/>
                                        </tiles:put>
                                    </tiles:insert>
                                </div>
                                <div class="insurancePeriodInfo${rowIndex}">
                                    <tiles:insert definition="simpleFormRow" flush="false">
                                        <tiles:put name="title">
                                            <bean:message bundle="pfpInsuranceBundle" key="label.edit.field.periodInformation.period"/>
                                        </tiles:put>
                                        <tiles:put name="isNecessary" value="true"/>
                                        <tiles:put name="data">
                                            <html:text property="fields(${periodFieldName})" size="20" maxlength="15"/>&nbsp;
                                            <b><bean:message bundle="pfpInsuranceBundle" key="label.edit.field.periodInformation.period.union"/></b>
                                        </tiles:put>
                                    </tiles:insert>
                                </div>
                                <div class="insurancePeriodInfo${rowIndex} notForComplex">
                                    <tiles:insert definition="simpleFormRow" flush="false">
                                        <tiles:put name="title">
                                            <bean:message bundle="pfpInsuranceBundle" key="label.edit.field.periodInformation.sum"/>
                                        </tiles:put>
                                        <tiles:put name="isNecessary" value="true"/>
                                        <tiles:put name="data">
                                            <bean:message bundle="pfpInsuranceBundle" key="label.edit.field.periodInformation.sum.min"/>&nbsp;
                                            <html:text property="fields(${minSumFieldName})" size="20" maxlength="15" styleClass="moneyField"/>&nbsp;
                                            <bean:message bundle="pfpInsuranceBundle" key="label.edit.field.periodInformation.sum.max"/>&nbsp;
                                            <html:text property="fields(${maxSumFieldName})" size="20" maxlength="15" styleClass="moneyField"/>&nbsp;
                                            <b><bean:message bundle="pfpInsuranceBundle" key="label.edit.field.periodInformation.sum.unit"/></b>
                                        </tiles:put>
                                    </tiles:insert>
                                </div>
                                <div class="insurancePeriodInfo${rowIndex}">
                                    <tiles:insert definition="simpleFormRow" flush="false">
                                        <tiles:put name="data">
                                            <input type="button"
                                                   class="buttonGrayNew"
                                                   onclick="removePeriodInformation(${rowIndex});"
                                                   value="<bean:message bundle="pfpInsuranceBundle" key="label.edit.field.periodInformation.remove"/>"/>
                                        </tiles:put>
                                    </tiles:insert>
                                </div>
                                <c:set var="periodInfoMaxRowIndex" value="${rowIndex}"/>
                            </c:forEach>
                        </div>
                    </fieldset>
                    <script type="text/javascript">
                       var periodInfoCurrentRow = 0;
                       var periodInfoMaxRowIndex = ${periodInfoMaxRowIndex};
                       var allChildSelectOptions = $('[class^=insuranceTypeFor]');

                       var childInsuranceTypesSelect = $("select.childInsuranceTypes");
                       var parentInsuranceTypes = $("select.parentInsuranceTypes");
                       var childInsuranceTypesSpan = $("span.childInsuranceTypes");

                       function selectParentType()
                       {
                           var parentId = parentInsuranceTypes.val();
                           allChildSelectOptions.remove();
                           var childs = insuranceTypesArray[''+parentId];
                           if(childs.length>0)
                           {
                               childs[0].attr("selected", true);
                               for(var i = 0; i < childs.length; i++)
                                   $("select.childInsuranceTypes").append(childs[i]);
                           }
                           childInsuranceTypesSpan.show();
                           if (childs.length != 0)
                           {
                               childInsuranceTypesSelect.attr("disabled", false);
                               return;
                           }


                           childInsuranceTypesSelect.attr("disabled", true);
                           childInsuranceTypesSpan.hide();
                       }

                       function openInsuranceCompanyDictionaries()
                       {
                           window.open("${phiz:calculateActionURL(pageContext, '/pfp/insurance/companiesList')}?action=dictionary",
                                       'companiesDictionary', "resizable=1,menubar=1,toolbar=1,scrollbars=1");
                       }

                       function setInsuranceCompany(data)
                       {
                           $("[name=fields(insuranceCompanyId)]").val(data['id']);
                           $("[name=fields(insuranceCompanyName)]").val(data['name']);
                       }

                       function openInsuranceTypeDictionaries()
                       {
                           window.open("${phiz:calculateActionURL(pageContext, '/pfp/insurance/typeList')}?action=dictionary",
                                   'typeDictionary', "resizable=1,menubar=1,toolbar=1,scrollbars=1");
                       }

                       function setInsuranceType(data)
                       {
                           $("[name=fields(typeId)]").val(data["id"]);
                           $("[name=fields(typeName)]").val(data["name"]);
                       }

                       function openPeriodTypeDictionaries(rowNum)
                       {
                           periodInfoCurrentRow = rowNum;
                           window.open("${phiz:calculateActionURL(pageContext, '/pfp/insurance/periodTypeList')}?action=dictionary",
                                   'periodTypeDictionary', "resizable=1,menubar=1,toolbar=1,scrollbars=1");
                       }

                       function addPeriodType(data)
                       {
                           $("[name=fields(idPeriodType" + periodInfoCurrentRow + ")]").val(data["id"]);
                           $("[name=fields(namePeriodType" + periodInfoCurrentRow + ")]").val(data["name"]);
                           $("#periodTypeId" + periodInfoCurrentRow).val(data["id"]);
                       }

                       function hasEmptyPeriod()
                       {
                           var lines = $("[name=fields(defaultPeriod)]");
                           for (var i = 0; i < lines.size(); i++)
                           {
                               var lineNumber = lines[i].value;
                               if ($("[name=fields(idPeriodType" + lineNumber + ")]").val().length == 0)
                                   return true;

                               if ($("[name=fields(period" + lineNumber + ")]").val().length == 0)
                                   return true;

                               if (!$("[name=fields(forComplex)]").attr("checked") &&
                                   $("[name=fields(minSum" + lineNumber + ")]").val().length == 0 &&
                                   $("[name=fields(maxSum" + lineNumber + ")]").val().length == 0)
                                   return true;


                           }
                           return false;
                       }

                       function addPeriodInfoRow()
                       {
                           $("#insurancePeriodTypesInfo").show();

                           if (hasEmptyPeriod())
                           {
                               alert("<bean:message bundle="pfpInsuranceBundle" key="label.edit.field.periodInformation.add.hasEmptyPeriod"/>");
                               return;
                           }

                           var periodInfoRowCount = ++periodInfoMaxRowIndex;
                           var line =  '<div class="insurancePeriodInfo' + periodInfoRowCount + '">' +
                                           '<div class="form-row">' +
                                           '<div class="paymentLabel">' +
                                               '&nbsp;' +
                                           '</div>' +
                                           '</div>' +
                                           '<div class="paymentValue">' +
                                               '<input type="hidden" name="lineNumbers" value="' + periodInfoRowCount + '"/>' +
                                               '<input type="radio" name="fields(defaultPeriod)" value="' + periodInfoRowCount + '">' +
                                               '<bean:message bundle="pfpInsuranceBundle" key="label.edit.field.periodInformation.isDefault"/>' +
                                           '</div>' +
                                           '<div class="clear"></div>' +
                                       '</div>' +
                                       '<div class="insurancePeriodInfo' + periodInfoRowCount + '">' +
                                           '<div class="form-row">' +
                                           '<div class="paymentLabel">' +
                                               '<bean:message bundle="pfpInsuranceBundle" key="label.edit.field.periodInformation.periodType"/>&nbsp;<span class="asterisk">*</span>&nbsp;' +
                                           '</div>' +
                                           '</div>' +
                                           '<div class="paymentValue">' +
                                               '<input type="hidden" name="periodTypeIds" id="periodTypeId' + periodInfoRowCount + '"/>' +
                                               '<html:hidden property="fields(idPeriodType' + periodInfoRowCount + ')"/>' +
                                               '<html:text property="fields(namePeriodType' + periodInfoRowCount + ')" size="58" readonly="true"/>' +
                                               '<input type="button" class="buttWhite" onclick="openPeriodTypeDictionaries(' + periodInfoRowCount + ');" value="..."/>' +
                                           '</div>' +
                                           '<div class="clear"></div>' +
                                       '</div>' +
                                       '<div class="insurancePeriodInfo' + periodInfoRowCount + '">' +
                                           '<div class="form-row">' +
                                           '<div class="paymentLabel">' +
                                               '<bean:message bundle="pfpInsuranceBundle" key="label.edit.field.periodInformation.period"/>&nbsp;<span class="asterisk">*</span>&nbsp;' +
                                           '</div>' +
                                           '</div>' +
                                           '<div class="paymentValue">' +
                                               '<html:text property="fields(period' + periodInfoRowCount + ')" size="20" maxlength="15"/>&nbsp;' +
                                               '<b><bean:message bundle="pfpInsuranceBundle" key="label.edit.field.periodInformation.period.union"/></b>' +
                                           '</div>' +
                                           '<div class="clear"></div>' +
                                        '</div>' +
                                        '<div class="insurancePeriodInfo' + periodInfoRowCount + ' notForComplex">' +
                                           '<div class="form-row">' +
                                           '<div class="paymentLabel">' +
                                                '<bean:message bundle="pfpInsuranceBundle" key="label.edit.field.periodInformation.sum"/>&nbsp;<span class="asterisk">*</span>&nbsp;' +
                                            '</div>' +
                                            '</div>' +
                                            '<div class="paymentValue">' +
                                                '<bean:message bundle="pfpInsuranceBundle" key="label.edit.field.periodInformation.sum.min"/>&nbsp;' +
                                                '<html:text property="fields(minSum' + periodInfoRowCount + ')" size="20" maxlength="15" styleClass="moneyField"/>&nbsp;' +
                                                '<bean:message bundle="pfpInsuranceBundle" key="label.edit.field.periodInformation.sum.max"/>&nbsp;' +
                                                '<html:text property="fields(maxSum' + periodInfoRowCount + ')" size="20" maxlength="15" styleClass="moneyField"/>&nbsp;' +
                                                '<b><bean:message bundle="pfpInsuranceBundle" key="label.edit.field.periodInformation.sum.unit"/></b>' +
                                            '</div>' +
                                            '<div class="clear"></div>' +
                                        '</div>' +
                                        '<div class="insurancePeriodInfo' + periodInfoRowCount + '">' +
                                           '<div class="form-row">' +
                                           '<div class="paymentLabel">' +
                                                '&nbsp;' +
                                            '</div>' +
                                            '</div>' +
                                            '<div class="paymentValue">' +
                                                '<input type="button"' +
                                                        'class="buttonGrayNew"' +
                                                        'onclick="removePeriodInformation(' + periodInfoRowCount + ');"' +
                                                        'value="<bean:message bundle="pfpInsuranceBundle" key="label.edit.field.periodInformation.remove"/>"/>' +
                                            '</div>' +
                                            '<div class="clear"></div>' +
                                        '</div>';
                            $("#insurancePeriodTypes").append(line);
                            hideOrShowForCommplexFields();
                        }

                        function removePeriodInformation(rowNum)
                        {
                            if ($("[class^=insurancePeriodInfo]").size() == 5)
                            {
                                $("#insurancePeriodTypes input[name^=fields]").val('');
                                $("[name=fields(defaultPeriod)]").val(rowNum);
                                return;
                            }

                            $('.insurancePeriodInfo'+rowNum).remove();
                        }

                        function forComplexClick()
                        {
                            hideOrShowForCommplexFields();
                            $("[name^=fields(minSum]").val("");
                            $("[name^=fields(maxSum]").val("");
                        }

                        function hideOrShowForCommplexFields()
                        {
                            if ($(":checkbox[name=fields(forComplex)]").attr("checked"))
                            {
                                $(".notForComplex").hide();
                                $(".forCommplexOnly").show();
                            }
                            else
                            {
                                $(".forCommplexOnly").hide();
                                $(".notForComplex").show();
                            }
                        }

                        function saveInsuranceProduct()
                        {
                            if ($("[class^=insurancePeriodInfo]").size() != 0 && getRadioValue($("[name=fields(defaultPeriod)]")) == null)
                            {
                                alert("Выберите период по умолчанию.");
                                clearLoadMessage();
                                return false;
                            }
                            clearLoadMessage();
                            return $(":checkbox[name=fields(forComplex)]").is(":checked") || checkSegment();
                        }

                        function initializeInsuranceProductForm()
                        {
                            hideOrShowForCommplexFields();
                            selectParentType();
                            if ($("[class^=insurancePeriodInfo]").size() < 5)
                                addPeriodInfoRow();
                        }

                        $(document).ready(function(){initializeInsuranceProductForm();});
                    </script>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpInsuranceBundle" key="label.edit.field.description"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:textarea property="fields(description)" cols="58" rows="3"/>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false" operation="ListInsuranceProductOperation">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle"         value="pfpInsuranceBundle"/>
                        <tiles:put name="action"         value="/pfp/insurance/productList.do"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="EditInsuranceProductOperation">
                        <tiles:put name="commandKey"         value="button.save"/>
                        <tiles:put name="commandHelpKey"     value="button.save.help"/>
                        <tiles:put name="bundle"             value="pfpInsuranceBundle"/>
                        <tiles:put name="validationFunction" value="saveInsuranceProduct();"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
