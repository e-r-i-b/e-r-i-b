<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>

<html:form action="/dictionaries/paymentService/edit" enctype="multipart/form-data">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="paymentServiceEdit">
        <tiles:put name="submenu" type="string" value="PaymentServices"/>

        <tiles:put name="menu" type="string"/>
        <tiles:put name="data" type="string">
            <html:hidden property="id"/>
            <html:hidden property="parentId"/>
            <c:set var="isEdit" value="${not empty param.id || not empty form.id}"/>
            <c:set var="isSystem" value="${EditPaymentServiceForm.fields.system}" />
            <script type="text/javascript">
                function setPaymentServiceInfo(data)
                {
                    if ($("#parentService" + data['id']).length == 1)
                    {
                        return 1;
                    }
                    if ($("#childService" + data['id']).length == 1)
                    {
                        return 2;
                    }
                    <c:if test="${not empty form.id}">
                        if (data['id'] == ${form.id})
                        {
                            return 3;
                        }
                    </c:if>
                    $(".hidableElement").show();
                    var line =  '<tr id="parentService' + data['id'] + '" class="ListLine0">' +
                                    '<td class="listItem">' +
                                        '<input type="checkbox" name="serviceSelectedIds" value="' + data['id'] + '">&nbsp;' +
                                        '<input type="hidden" checked name="parentServiceIds" value="' + data['id'] + '">' +
                                    '</td>' +
                                    '<td>' +
                                        '<input type="hidden" value="' + data['name'] +'" name="fields(parentServiceName' + data['id'] + ')">' +
                                        data['name'] +
                                    '</td>' +
                                '</tr>';
                    $("#parentServices > tbody").append(line);
                    return true;
                }

                function removeParentSevice()
                {
                    checkIfOneItem("serviceSelectedIds");
                    if (!checkSelection("serviceSelectedIds",
                                        'Выберите хотя бы одну группу услуг.'))
                        return false;

                    $(":checkbox[name=serviceSelectedIds]:checked").each(function(){$(this).parent().parent().remove();});
                    if ($("[name=serviceSelectedIds]").size() == 0)
                        $(".hidableElement").hide();
                    return true;
                }
                function openMCCDictionary()
                {
                    window.open(document.webRoot+'/finances/categories/list.do?action=getBankInfo', "", "width=1000,height=400,resizable=0,menubar=1,toolbar=0,scrollbars=1");
                    window.moveTo(screen.width / 2 - 410, screen.height / 2 - 225);
                }
                function setCategoryInfo(id, name)
                {
                    $('[name="field(cardOperationCategoryId)"]').val(id);
                    $('[name="field(cardOperationCategoryName)"]').val(name);
                }
                $(document).ready(function(){
                    $('#service').click(function(){
		                $('#parentRegions').show();
	                });
                    $('#category').click(function(){
		                $('#parentRegions').hide();
	                });
                });
            </script>
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="editPaymentService"/>
                <tiles:put name="name" type="string"><bean:message bundle="paymentServiceBundle" key="editform.name"/></tiles:put>
                <tiles:put name="description" type="string"><bean:message bundle="paymentServiceBundle" key="editform.title"/></tiles:put>
                <tiles:put name="data">

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="paymentServiceBundle" key="label.code"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="field(synchKey)" size="100" readonly="${(isSystem) && (param.id != null)}"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="paymentServiceBundle" key="label.name"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="field(name)" size="100"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.description" bundle="paymentServiceBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:textarea property="field(description)" cols="40" rows="5" styleClass="contactInput"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            Логотип
                        </tiles:put>
                        <tiles:put name="data">
                            <tiles:insert definition="imageInput" flush="false"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.show.popular" bundle="paymentServiceBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:radio property="field(popular)" styleId="popularYes" name="form" value="true" disabled="${(isSystem) && (param.id != null)}"/>
                            <label for="popularYes"><bean:message key="label.yes" bundle="paymentServiceBundle"/></label> <br/>
                            <html:radio property="field(popular)" styleId="popularNo" name="form" value="false" disabled="${(isSystem) && (param.id != null)}"/>
                            <label for="popularNo"><bean:message key="label.no" bundle="paymentServiceBundle"/></label>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="paymentServiceBundle" key="label.priority"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <c:set var="priority" value="${form.fields.priority}"/>
                            <tiles:insert definition="scrollTemplate2" flush="false">
                                <tiles:put name="id" value="income"/>
                                <tiles:put name="minValue" value="0"/>
                                <tiles:put name="maxValue" value="100"/>
                                <tiles:put name="currValue" value="${empty priority ? 0 : priority}"/>
                                <tiles:put name="fieldName" value="fields(priority)"/>
                                <tiles:put name="step" value="1"/>
                                <tiles:put name="round" value="0"/>
                                <tiles:put name="maxInputLength" value="3"/>
                            </tiles:insert>
                        </tiles:put>
                    </tiles:insert>

                    <fieldset>
                        <legend>
                            Настройки расположения услуги
                        </legend>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                Категория
                            </tiles:put>
                            <tiles:put name="data">
                                <html:radio property="field(isCategory)" name="form" styleId="category" value="true" /> <label for="category"> Категория верхнего уровня</label>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="data">
                                <html:radio property="field(isCategory)" name="form" styleId="service" value="false" /> <label for="service">указать родительскую категорию</label>
                            </tiles:put>
                        </tiles:insert>

                        <div id="parentRegions" <c:if test="${form.fields.isCategory == true}">style="display:none;"</c:if> >
                            <tiles:insert definition="simpleFormRow" flush="false">
                                <tiles:put name="data">
                                    <c:set var="buttonDisplayStyle" value=""/>
                                    <c:if test="${empty form.parentServiceIds}">
                                        <c:set var="buttonDisplayStyle" value=" display:none;"/>
                                    </c:if>

                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="button.add"/>
                                        <tiles:put name="commandHelpKey" value="button.add"/>
                                        <tiles:put name="bundle" value="paymentServiceBundle"/>
                                        <tiles:put name="onclick">openPaymentServicesDictionary(setPaymentServiceInfo);</tiles:put>
                                        <tiles:put name="viewType" value="buttonGrayNew"/>
                                    </tiles:insert>
                                    <div class="hidableElement" style="${buttonDisplayStyle}">
                                        <tiles:insert definition="clientButton" flush="false">
                                            <tiles:put name="commandTextKey" value="button.remove"/>
                                            <tiles:put name="commandHelpKey" value="button.remove"/>
                                            <tiles:put name="bundle" value="paymentServiceBundle"/>
                                            <tiles:put name="onclick">removeParentSevice();</tiles:put>
                                            <tiles:put name="viewType" value="buttonGrayNew"/>
                                        </tiles:insert>
                                    </div>
                                    <%--Таблица--%>
                                    <div class="smallDynamicGrid hidableElement" style="${buttonDisplayStyle}">
                                        <tiles:insert definition="tableTemplate" flush="false">
                                            <tiles:put name="id" value="parentServices"/>
                                            <tiles:put name="head">
                                                <th width="20px">
                                                    <input type="checkbox" onclick="switchSelection(this,'serviceSelectedIds');"/>
                                                </th>
                                                <th>
                                                    Родительские группы услуг
                                                </th>
                                            </tiles:put>
                                            <tiles:put name="data">
                                                <logic:iterate id="productId" name="form" property="parentServiceIds">
                                                    <c:set var="fieldName" value="parentServiceName${productId}"/>
                                                    <tr id="parentService${productId}" class="ListLine0">
                                                        <td class="listItem">
                                                            <input type="checkbox" value="${productId}" name="serviceSelectedIds">
                                                            <input type="hidden" value="${productId}" name="parentServiceIds" checked>
                                                        </td>
                                                        <td>
                                                            <html:hidden property="fields(${fieldName})"/>
                                                            ${form.fields[fieldName]}
                                                        </td>
                                                    </tr>
                                                </logic:iterate>
                                                <c:if test="${form.childrenServiceIds != null}">
                                                    <logic:iterate id="serviceId" name="form" property="childrenServiceIds">
                                                        <input type="hidden" id="childService${serviceId}"/>
                                                    </logic:iterate>
                                                </c:if>
                                                <div></div>
                                            </tiles:put>
                                        </tiles:insert>
                                    </div>
                                </tiles:put>
                            </tiles:insert>
                        </div>
                    </fieldset>

                    <fieldset>
                        <legend>Настройка видимости в каналах</legend>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                Интернет-банк
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:radio property="field(showInSystem)" name="form" styleId="showInSystem" value="true"/> <label for="showInSystem">в каталоге</label>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="data">
                                <html:radio property="field(showInSystem)" name="form" styleId="hideInSystem" value="false"/> <label for="hideInSystem">только в результатах поиска</label>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                mApi
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:radio property="field(showInMApi)" name="form" styleId="showInMApi" value="true"/> <label for="showInMApi">в каталоге</label>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="data">
                                <html:radio property="field(showInMApi)" name="form" styleId="hideInMApi" value="false"/> <label for="hideInMApi">только в результатах поиска</label>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                atmApi
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:radio property="field(showInAtmApi)" name="form" styleId="showInAtmApi" value="true"/> <label for="showInAtmApi">в каталоге</label>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="data">
                                <html:radio property="field(showInAtmApi)" name="form" styleId="hideInAtmApi" value="false"/> <label for="hideInAtmApi">только в результатах поиска</label>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                socialApi
                            </tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <html:radio property="field(showInSocialApi)" name="form" styleId="showInSocialApi" value="true"/> <label for="showInSocialApi">в каталоге</label>
                            </tiles:put>
                        </tiles:insert>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="data">
                                <html:radio property="field(showInSocialApi)" name="form" styleId="hideInSocialApi" value="false"/> <label for="hideInSocialApi">только в результатах поиска</label>
                            </tiles:put>
                        </tiles:insert>
                    </fieldset>

                    <fieldset>
                        <legend>
                            Настройка отображения в АЛФ
                        </legend>
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                Категория в АЛФ:
                            </tiles:put>
                            <tiles:put name="data">
                                <c:set var="cardOpCategoryId">
                                    <bean:write name="org.apache.struts.taglib.html.BEAN" property="field(cardOperationCategoryId)"/>
                                </c:set>
                                <html:hidden property="field(cardOperationCategoryId)" name="form"/>
                                <html:text property="field(cardOperationCategoryName)" name="form" size="40" readonly="true"/>
                                <input type="button" class="buttWhite" style="height:18px;width:18px;"
                                       onclick="openMCCDictionary();"
                                       value="..."/>
                            </tiles:put>
                        </tiles:insert>
                    </fieldset>

                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle"  value="paymentServiceBundle"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle"         value="paymentServiceBundle"/>
                        <tiles:put name="action"         value="/private/dictionaries/paymentService/list.do"/>
                    </tiles:insert>

                     <tiles:insert definition="languageSelectForEdit" flush="false">
                         <tiles:put name="selectId" value="chooseLocale"/>
                         <tiles:put name="entityId" value="${form.fields.synchKey}"/>
                         <tiles:put name="idName" value="stringId"/>
                         <tiles:put name="styleClass" value="float"/>
                         <tiles:put name="editLanguageURL" value="${phiz:calculateActionURL(pageContext, '/async/dictionaries/paymentService/resources/edit')}"/>
                     </tiles:insert>
                </tiles:put>
            </tiles:insert>
            <html:hidden property="field(popular)" disabled="${isEdit && !isSystem}"/>
        </tiles:put>

    </tiles:insert>

</html:form>