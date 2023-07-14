<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<html:form action="/ermb/rules/edit" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="ermbMain">
        <tiles:put name="submenu" type="string" value="ErmbBankrollProductRules"/>

        <tiles:put name="data" type="string">

            <script type="text/javascript">
                var addedDepartments = new Array();

                function setDepartmentInfo(resource)
                {
                    var ids   = new Array();
                    var names = new Array();
                    ids = resource['id'].split(',');
                    names = resource['name'].split(',');
                    for(var i = 0; i < ids.length; i++)
                    {
                        var id = ids[i];
                        var name = names[i];
                        if(document.getElementById('dep'+id) == null)
                        {
                            addedDepartments.push(id);
                            $("#lastTableElement").before(
                            '<tr id="dep'+ id +'">'+
                                 '<td width="20px">'+
                                      '<input type="checkbox" name="selectedTerbanks" value="'+id+'" class="departmentCheckbox"/>'+
                                      '<input type="hidden" name="selectedTerbankIds" value="'+id+'" style="border:none;"/>'+
                                 '</td>'+
                                 '<td>'+
                                      '<span id="'+id+'_id_name"></span>'+
                                 '</td>'+
                            '</tr>'
                            );
                            $("#"+id+"_id_name").text(name);
                        }
                    }
                }

                function deleteDepartments()
                {
                   checkIfOneItem("selectedPages");
                   if (!checkSelection("selectedTerbanks", "Выберите департамент!"))
                              return;
                   var ids = document.getElementsByName("selectedTerbanks");
                   var size = ids.length;
                   var c = 0;
                   for (var i = 0; i < size; i++)
                   {
                       if (ids.item(c).checked)
                       {
                           var id_id = ids.item(c).value;
                           $('#dep'+id_id).remove();
                       }
                       else
                       {
                           c++;
                       }
                   }
                   if (ids.length <= 0)
                   {
                       $("#pagesTable").css("display", "none");
                   }
                }
            </script>


            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name">
                    <bean:message bundle="ermbBundle" key="ermb.rule.edit"/>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle" value="commonBundle"/>
                        <tiles:put name="image" value=""/>
                        <tiles:put name="action" value="/ermb/rules/list.do"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save"/>
                        <tiles:put name="bundle" value="commonBundle"/>
                        <tiles:put name="image" value=""/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                </tiles:put>


                <c:choose>
                   <c:when test="${phiz:isCommonAttributeUseInProductsAvailable()}">
                       <c:set var="commonAttribute" value="true"/>
                       <c:set var="useInSmsBankTitle"><bean:message key="ermb.sms.useInSmsBank" bundle="ermbBundle"/></c:set>
                   </c:when>
                   <c:otherwise>
                       <c:set var="commonAttribute" value="false"/>
                       <c:set var="tableClass" value="notCommonAttribute"/>
                       <c:set var="ermbSmsOperationsTitle"> <bean:message key="ermb.sms.operations" bundle="ermbBundle"/> </c:set>
                       <c:set var="useSmsCommandsTitle"> <bean:message key="ermb.sms.useSmsCommands" bundle="ermbBundle"/> </c:set>
                       <c:set var="ermbSmsNotificationTitle"><bean:message key="ermb.sms.notification" bundle="ermbBundle"/></c:set>
                       <c:set var="ermbSmsNotifyTitle"><bean:message key="ermb.sms.notify" bundle="ermbBundle"/></c:set>
                   </c:otherwise>
                </c:choose>

                <tiles:put name="data" type="string">

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="ermb.rule.name" bundle="ermbBundle"/>
                    </tiles:put>
                    <tiles:put name="isNecessary" value="true"/>
                    <tiles:put name="data">
                        <html:text property="field(name)" name="form" styleClass="contactInput" maxlength="50"/>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="ermb.rule.state" bundle="ermbBundle"/>
                    </tiles:put>
                    <tiles:put name="data">
                        <html:select property="state" name="form" styleClass="select">
                            <html:option value="true"><bean:message key="ermb.rule.state.ACTIVE" bundle="ermbBundle"/></html:option>
                            <html:option value="false"><bean:message key="ermb.rule.state.NOT_ACTIVE" bundle="ermbBundle"/></html:option>
                        </html:select>
                    </tiles:put>
                </tiles:insert>

                <fieldset>
                    <legend>
                        <bean:message key="ermb.rule.condition" bundle="ermbBundle"/>
                    </legend>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="ermb.rule.tb" bundle="ermbBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.add"/>
                                <tiles:put name="commandHelpKey" value="button.add"/>
                                <tiles:put name="bundle" value="commonBundle"/>
                                <tiles:put name="viewType" value="buttonGrayNew"/>
                                <tiles:put name="onclick">
                                    openAllowedTBDictionary(setDepartmentInfo);
                                </tiles:put>
                            </tiles:insert>

                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.remove"/>
                                <tiles:put name="commandHelpKey" value="button.remove"/>
                                <tiles:put name="bundle" value="commonBundle"/>
                                <tiles:put name="onclick">
                                    deleteDepartments();
                                </tiles:put>
                                <tiles:put name="viewType" value="buttonGrayNew"/>
                            </tiles:insert>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">
                            <div class="selectedTerbanks">
                                <tiles:insert definition="tableTemplate" flush="false">
                                    <tiles:put name="id" value="selectedTerbanks"/>
                                    <tiles:put name="text" value="Выбранные террбанки"/>
                                    <tiles:put name="data">


                                        <c:if test="${not empty form.terbanks && form.terbanks != null}">

                                            <tr class="tblInfHeader">
                                                <th>
                                                    <input name="isSelectedAllTerbanks" type="checkbox" style="border:none" onclick="switchSelection('isSelectedAllTerbanks','selectedTerbanks')"/>
                                                </th>
                                                <th>
                                                    <bean:message key="ermb.rule.tbs" bundle="ermbBundle"/>
                                                </th>
                                            </tr>

                                            <c:forEach items="${form.terbanks}" var="terbank">
                                                <tr id="dep${terbank.id}">
                                                    <td width="20px">
                                                        <input type="checkbox" name="selectedTerbanks" value="${terbank.id}" class="departmentCheckbox"/>
                                                        <input type="hidden" name="selectedTerbankIds" value="${terbank.id}" style="border:none;"/>
                                                    </td>
                                                    <td>
                                                                                <span id="${terbank.id}_id_name">
                                                                                    <c:out value="${terbank.name}"/>
                                                                                </span>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </c:if>
                                        <tr id="lastTableElement" style="display:none;">
                                            <td></td>
                                            <td></td>
                                        </tr>

                                    </tiles:put>
                                </tiles:insert>
                            </div>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="ermb.rule.clientCategory" bundle="ermbBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:select property="fields(clientCategory)" styleClass="select" style="width:500px;">
                                <html:option value="UNIMPORTANT"><bean:message key="ermb.rule.clientCategory.UNIMPORTANT" bundle="ermbBundle"/></html:option>
                                <html:option value="STANDART"><bean:message key="ermb.rule.clientCategory.STANDART" bundle="ermbBundle"/></html:option>
                                <html:option value="VIP"><bean:message key="ermb.rule.clientCategory.VIP" bundle="ermbBundle"/></html:option>
                                <html:option value="MBC"><bean:message key="ermb.rule.clientCategory.MBC" bundle="ermbBundle"/></html:option>
                            </html:select>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="ermb.rule.age" bundle="ermbBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            c <html:text property="fields(ageFrom)" name="form" styleClass="contactInput" style="width: 40px;"/>
                            до <html:text property="fields(ageUntil)" name="form" styleClass="contactInput" style="width: 40px;"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="ermb.rule.productAvailability" bundle="ermbBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <tiles:insert definition="tableTemplate" flush="false">
                                <tiles:put name="id" value="productDefaults"/>
                                <tiles:put name="text" value="Клиентские продукты"/>
                                <tiles:put name="data">
                                    <tr class="tblInfHeader">
                                        <th>
                                            <bean:message key="ermb.rule.product" bundle="ermbBundle"/>
                                        </th>
                                        <th width="20px">
                                            <bean:message key="ermb.rule.product.state" bundle="ermbBundle"/>
                                        </th>
                                    </tr>
                                    <tr>
                                        <td>
                                            <bean:message key="ermb.rule.creditCards" bundle="ermbBundle"/>
                                        </td>
                                        <td>
                                            <html:select property="fields(creditCardsCriteria)" styleClass="select">
                                                <html:option value="UNIMPORTANT"><bean:message key="ermb.rule.product.UNIMPORTANT" bundle="ermbBundle"/></html:option>
                                                <html:option value="AVAILABLE"><bean:message key="ermb.rule.product.AVAILABLE" bundle="ermbBundle"/></html:option>
                                                <html:option value="UNAVAILABLE"><bean:message key="ermb.rule.product.UNAVAILABLE" bundle="ermbBundle"/></html:option>
                                            </html:select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <bean:message key="ermb.rule.deposits" bundle="ermbBundle"/>
                                        </td>
                                        <td>
                                            <html:select property="fields(depositsCriteria)" styleClass="select">
                                                <html:option value="UNIMPORTANT"><bean:message key="ermb.rule.product.UNIMPORTANT" bundle="ermbBundle"/></html:option>
                                                <html:option value="AVAILABLE"><bean:message key="ermb.rule.product.AVAILABLE" bundle="ermbBundle"/></html:option>
                                                <html:option value="UNAVAILABLE"><bean:message key="ermb.rule.product.UNAVAILABLE" bundle="ermbBundle"/></html:option>
                                            </html:select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <bean:message key="ermb.rule.loans" bundle="ermbBundle"/>
                                        </td>
                                        <td>
                                            <html:select property="fields(loansCriteria)" styleClass="select">
                                                <html:option value="UNIMPORTANT"><bean:message key="ermb.rule.product.UNIMPORTANT" bundle="ermbBundle"/></html:option>
                                                <html:option value="AVAILABLE"><bean:message key="ermb.rule.product.AVAILABLE" bundle="ermbBundle"/></html:option>
                                                <html:option value="UNAVAILABLE"><bean:message key="ermb.rule.product.UNAVAILABLE" bundle="ermbBundle"/></html:option>
                                            </html:select>
                                        </td>
                                    </tr>

                                </tiles:put>
                            </tiles:insert>
                        </tiles:put>
                    </tiles:insert>
                </fieldset>

                <fieldset>
                    <legend>
                        <bean:message key="ermb.rule.defaults" bundle="ermbBundle"/>
                    </legend>

                    <tiles:insert definition="tableTemplate" flush="false">
                        <tiles:put name="id" value="productDefaults"/>
                        <tiles:put name="text"><bean:message key="ermb.notification.settings" bundle="ermbBundle"/></tiles:put>
                        <tiles:put name="data">
                            <script>
                                function switchSelectionForNotification(flag, suffix)
                                {
                                    var list = ["cards","deposits","loans","newProducts"];
                                    flag = ensureElementByName(flag);
                                    for (var i=0; i<list.length; i++)
                                        turnSelection(flag.checked, list[i]+suffix);
                                }
                            </script>
                            <tr class="tblInfHeader">
                                <th width="90%">
                                    <bean:message key="ermb.client.products" bundle="ermbBundle"/>
                                </th>
                                <c:choose>
                                    <c:when test="${commonAttribute}">
                                        <th width="150px">
                                            <input name="isSelectedAllNotification" type="checkbox" style="border:none" onclick="switchSelectionForNotification('isSelectedAllNotification','Notification')"/>
                                            ${useInSmsBankTitle}
                                        </th>
                                    </c:when>
                                    <c:otherwise>
                                        <th class="smsOperationstitle">
                                            <input name="isSelectedAllVisibility" type="checkbox" style="border:none" onclick="switchSelectionForNotification('isSelectedAllVisibility','Visibility')"/>
                                            ${ermbSmsOperationsTitle}
                                        </th>
                                        <th class="smsNotificationsTitle">
                                            <input name="isSelectedAllNotification" type="checkbox" style="border:none" onclick="switchSelectionForNotification('isSelectedAllNotification','Notification')"/>
                                            ${ermbSmsNotificationTitle}
                                        </th>
                                    </c:otherwise>
                                </c:choose>
                            </tr>

                            <tr>
                               <td>
                                   <bean:message key="ermb.client.card" bundle="ermbBundle"/>
                               </td>
                                <c:choose>
                                   <c:when test="${commonAttribute}">
                                       <td>
                                           <html:checkbox property="cardsNotification"/>
                                           ${useInSmsBankTitle}
                                       </td>
                                   </c:when>
                                   <c:otherwise>
                                       <td>
                                           <html:checkbox property="cardsVisibility"/>
                                           ${useSmsCommandsTitle}
                                       </td>
                                       <td>
                                           <html:checkbox property="cardsNotification"/>
                                           ${ermbSmsNotifyTitle}
                                       </td>
                                   </c:otherwise>
                                </c:choose>
                            </tr>

                            <tr>
                                <td>
                                   <bean:message key="ermb.rule.deposits" bundle="ermbBundle"/>
                                </td>
                                <c:choose>
                                   <c:when test="${commonAttribute}">
                                       <td>
                                           <html:checkbox property="depositsNotification"/>
                                           ${useInSmsBankTitle}
                                       </td>
                                   </c:when>
                                   <c:otherwise>
                                       <td>
                                           <html:checkbox property="depositsVisibility"/>
                                           ${useSmsCommandsTitle}
                                       </td>
                                       <td>
                                           <html:checkbox property="depositsNotification"/>
                                           ${ermbSmsNotifyTitle}
                                       </td>
                                   </c:otherwise>
                                </c:choose>
                            </tr>

                            <tr>
                                <td>
                                   <bean:message key="ermb.rule.loans" bundle="ermbBundle"/>
                                </td>
                                <c:choose>
                                   <c:when test="${commonAttribute}">
                                       <td>
                                           <html:checkbox property="loansNotification"/>
                                           ${useInSmsBankTitle}
                                       </td>
                                   </c:when>
                                   <c:otherwise>
                                       <td>
                                           <html:checkbox property="loansVisibility"/>
                                           ${useSmsCommandsTitle}
                                       </td>
                                       <td>
                                           <html:checkbox property="loansNotification"/>
                                           ${ermbSmsNotifyTitle}
                                       </td>
                                   </c:otherwise>
                                </c:choose>
                            </tr>

                            <tr>
                                <td>
                                   <bean:message key="ermb.rule.newProducts" bundle="ermbBundle"/>
                                </td>
                                <c:choose>
                                   <c:when test="${commonAttribute}">
                                       <td>
                                           <html:checkbox property="newProductsNotification"/>
                                           ${useInSmsBankTitle}
                                       </td>
                                   </c:when>
                                   <c:otherwise>
                                       <td>
                                           <html:checkbox property="newProductsVisibility"/>
                                           ${useSmsCommandsTitle}
                                       </td>
                                       <td>
                                           <html:checkbox property="newProductsNotification"/>
                                           ${ermbSmsNotifyTitle}
                                       </td>
                                   </c:otherwise>
                                </c:choose>
                            </tr>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="ermb.rule.tariff" bundle="ermbBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:select property="selectedTariff" name="form">
                                <c:forEach items="${form.tariffs}" var="tariff">
                                    <html:option value="${tariff.id}">
                                        <c:out value="${tariff.name}"/>
                                    </html:option>
                                </c:forEach>
                            </html:select>
                        </tiles:put>
                    </tiles:insert>
                </fieldset>

                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>

 </html:form>