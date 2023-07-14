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
    <tiles:insert definition="dictionary" flush="false">
        <tiles:put name="pageTitle" type="string" value="Справочник кодов валютных операций"/>
        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle" value="dictionaryBundle"/>
                <tiles:put name="onclick" value="window.close();"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>
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
                <tiles:insert definition="tableTemplate" flush="false">
                    <tiles:put name="description" value="Отметьте интересующий Вас код валютной операции в списке и нажмите на кнопку «Выбрать»."/>
                    <tiles:put name="text" value="Коды валютных операций"/>
                    <tiles:put name="buttons">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.choose"/>
                            <tiles:put name="commandHelpKey" value="button.choose.help"/>
                            <tiles:put name="bundle" value="dictionaryBundle"/>
                            <tiles:put name="onclick" value="sendOperationTypeData();"/>
                        </tiles:insert>
                    </tiles:put>
                    <tiles:put name="grid">
                        <sl:collection id="listElement" model="list" property="data">
                            <sl:collectionParam id="selectType" value="radio"/>
                            <sl:collectionParam id="selectName" value="selectedIds"/>
                            <sl:collectionParam id="selectProperty" value="id"/>

                            <sl:collectionParam id="onRowClick" value="selectRow(this,'selectedIds');"/>
                            <sl:collectionParam id="onRowDblClick" value="sendOperationTypeData();"/>
                            <sl:collectionItem title="Код" width="50">
                                <fmt:formatNumber value="${listElement.code}" minIntegerDigits="5" pattern="#####"/>
                            </sl:collectionItem>

                            <sl:collectionItem title="Наименование" property="name"/>
                        </sl:collection>
                    </tiles:put>

                    <tiles:put name="isEmpty" value="${empty form.data}"/>
                    <tiles:put name="emptyMessage">
                        Не найдено ни одного вида валютных операций,<br>соответствующего заданному фильтру!
                    </tiles:put>
                </tiles:insert>
            </div>
        </tiles:put>
    </tiles:insert>
</html:form>