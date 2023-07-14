<%--
  Created by IntelliJ IDEA.
  User: Egorova
  Date: 08.07.2008
  Time: 17:34:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:importAttribute/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="form" value="${ShowOperationCodesForm}"/>
<html:form action="/private/operationCodes">
    <tiles:insert definition="empty">
        <tiles:put name="data" type="string">
            <script type="text/javascript">
                $(document).ready(function()
                {
                    initReferenceSize();
                });

                function sendOperationTypeData(event)
                {
                    var ids = document.getElementsByName("selectedIds");
                    preventDefault(event);
                    var id = getRadioValue(ids);
                    for (var i = 0; i < ids.length; i++)
                    {
                        if (ids.item(i).checked)
                        {
                            var r = ids.item(i).parentNode.parentNode;
                            var a = new Array(4);
                            a['operationCode'] = trim(r.cells[1].innerHTML);
                            window.opener.setOperationCodeInfo(a);
                            window.close();
                            return true;
                        }
                    }
                    alert("Выберите операцию.");
                    return false;
                }
            </script>
            <div id="reference">
                <tiles:insert definition="mainWorkspace" flush="false">
                    <tiles:put name="title" value="Справочник кодов валютных операций"/>
                    <tiles:put name="data">
                        <tiles:insert definition="formHeader" flush="false">
                            <tiles:put name="image" value="${imagePath}/icon_reference.png"/>
                            <tiles:put name="alt" value="Список кодов валютных операций"/>
                            <tiles:put name="description">
                                Отметьте интересующий Вас код валютной операции в списке и нажмите на
                                кнопку
                                "Выбрать".
                            </tiles:put>
                        </tiles:insert>
                        <div class="clear"></div>
                        <tiles:insert definition="simpleTableTemplate" flush="false">
                            <tiles:put name="grid">
                                <sl:collection id="listElement" model="simple-pagination" property="data">
                                    <sl:collectionParam id="selectType" value="radio"/>
                                    <sl:collectionParam id="selectName" value="selectedIds"/>
                                    <sl:collectionParam id="selectProperty" value="id"/>

                                    <sl:collectionParam id="onRowClick"
                                                        value="selectRow(this,'selectedIds');"/>
                                    <sl:collectionParam id="onRowDblClick"
                                                        value="sendOperationTypeData();"/>
                                    <sl:collectionItem title="Код" width="50">
                                        <fmt:formatNumber value="${listElement.code}" minIntegerDigits="5"
                                                          pattern="#####"/>
                                    </sl:collectionItem>

                                    <sl:collectionItem title="Наименование" property="name"/>
                                </sl:collection>
                            </tiles:put>

                            <tiles:put name="isEmpty" value="${empty form.data}"/>
                            <tiles:put name="emptyMessage">
                                <h3>Не найдено ни одного вида валютных операций,<br>
                                    соответствующего заданному фильтру!</h3>
                            </tiles:put>
                        </tiles:insert>
                        <div class="buttonsArea">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.cancel"/>
                                <tiles:put name="commandHelpKey" value="button.cancel"/>
                                <tiles:put name="bundle" value="dictionaryBundle"/>
                                <tiles:put name="viewType" value="buttonGrey"/>
                                <tiles:put name="onclick" value="window.close();"/>
                            </tiles:insert>
                            <c:if test="${not empty form.data}">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.choose"/>
                                    <tiles:put name="commandHelpKey" value="button.choose.help"/>
                                    <tiles:put name="bundle" value="dictionaryBundle"/>
                                    <tiles:put name="onclick" value="sendOperationTypeData();"/>
                                </tiles:insert>
                            </c:if>
                        </div>
                        <div class="clear"></div>
                    </tiles:put>
                </tiles:insert>
            </div>
        </tiles:put>
    </tiles:insert>
</html:form>