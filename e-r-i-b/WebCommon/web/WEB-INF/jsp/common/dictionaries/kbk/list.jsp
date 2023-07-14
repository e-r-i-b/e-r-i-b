<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="standalone" value="${empty param['action']}"/>

<c:choose>
    <c:when test="${standalone}">
         <c:set var="layoutDefinition" value="listKBK"/>
    </c:when>
    <c:otherwise>
        <c:set var="layoutDefinition" value="dictionary"/>
    </c:otherwise>
</c:choose>

<html:form action="/private/dictionary/kbk/list" onsubmit="return setEmptyAction(event);">
<c:choose>
<c:when test="${not standalone and param['action']=='taxKBK'}">
    <tiles:importAttribute/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <tiles:insert definition="empty">
        <tiles:put name="data" type="string">
            <div id="reference">
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="title" value="Справочник КБК"/>
                <tiles:put name="data">
                    <script type="text/javascript">

                        $(document).ready(function()
                        {
                            initReferenceSize();
                        });

                        function sendKBKData(event)
                        {
                            checkIfOneItem("selectedIds");
                            if (!checkOneSelection("selectedIds", "Выберите один КБК!"))
                                return;
                            var ids = document.getElementsByName("selectedIds");
                            for (var i = 0; i < ids.length; i++)
                            {
                                if (ids.item(i).checked)
                                {
                                    var row = ids.item(i).parentNode.parentNode;
                                    var res = new Array();
                                    res['id'] = ids.item(i).value;
                                    res['code'] = trim(row.cells[1].innerHTML);
                                    window.opener.setKBKInfo(res);
                                    window.close();
                                    return;
                                }
                            }
                            alert("Выберите КБК!");
                        }
                    </script>

                    <tiles:insert definition="formHeader" flush="false">
                        <tiles:put name="image" value="${imagePath}/icon_reference.png"/>
                        <tiles:put name="alt" value="Справочник КБК"/>
                        <tiles:put name="description">
                            Выберите интересующий Вас КБК в списке и нажмите на кнопку "Выбрать".
                        </tiles:put>
                    </tiles:insert>
                    <div class="clear"></div>
                    <tiles:insert definition="simpleTableTemplate" flush="false">
                        <tiles:put name="grid">
                            <sl:collection id="listElement" model="simple-pagination" property="data">
                                <sl:collectionParam id="selectType" value="radio"/>
                                <sl:collectionParam id="selectName" value="selectedIds"/>
                                <sl:collectionParam id="selectProperty" value="synchKey"/>

                                <sl:collectionParam id="onRowClick" value="selectRow(this,'selectedIds');"/>
                                <sl:collectionParam id="onRowDblClick" value="sendKBKData(event);"/>

                                <sl:collectionItem title="Код" property="code"/>
                                <sl:collectionItem title="Описание" property="description"/>
                            </sl:collection>
                        </tiles:put>

                        <tiles:put name="isEmpty" value="${empty form.data}"/>
                        <tiles:put name="emptyMessage">
                            <h3>Не найдено ни одной записи КБК</h3>
                        </tiles:put>
                    </tiles:insert>
                    <c:choose>
                        <c:when test="${not empty form.data}">
                            <div class="buttonsArea">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.cancel"/>
                                    <tiles:put name="commandHelpKey" value="button.cancel"/>
                                    <tiles:put name="bundle" value="dictionaryBundle"/>
                                    <tiles:put name="onclick" value="window.close();"/>
                                    <tiles:put name="viewType" value="buttonGrey"/>
                                </tiles:insert>
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.choose"/>
                                    <tiles:put name="commandHelpKey" value="button.choose.help"/>
                                    <tiles:put name="bundle" value="dictionaryBundle"/>
                                    <tiles:put name="onclick" value="sendKBKData(event);"/>
                                </tiles:insert>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="buttonsArea">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.cancel"/>
                                <tiles:put name="commandHelpKey" value="button.cancel"/>
                                <tiles:put name="bundle" value="dictionaryBundle"/>
                                <tiles:put name="onclick" value="window.close();"/>
                                <tiles:put name="viewType" value="buttonGrey"/>
                            </tiles:insert>
                        </c:otherwise>
                    </c:choose>

                    <div class="clear"></div>
                </tiles:put>
            </tiles:insert>
            </div>
        </tiles:put>
    </tiles:insert>
    </div>
</c:when>
    <c:otherwise>
        <tiles:insert definition="${layoutDefinition}">
            <tiles:put name="mainmenu" value="ServiceProviders"/>
            <tiles:put name="submenu" value="KBK" type="string"/>
            <tiles:put name="pageTitle" type="string"><bean:message bundle="kbkBundle" key="label.page.title"/></tiles:put>
            <tiles:put name="menu" type="string">
                <c:choose>
                    <c:when test="${standalone}">
                        <script type="text/javascript">
                            <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/private/dictionary/kbk/edit')}"/>
                            function doEdit()
                            {
                                checkIfOneItem("selectedIds");
                                if (!checkSelection("selectedIds", 'Укажите одну запись') || (!checkOneSelection("selectedIds", 'Укажите только одну запись')))
                                    return;
                                var id = getRadioValue(document.getElementsByName("selectedIds"));
                                window.location = '${url}?id=' + id;
                            }
                        </script>

                        <tiles:insert definition="clientButton" flush="false" operation="EditKBKOperation">
                            <tiles:put name="commandTextKey" value="button.add"/>
                            <tiles:put name="commandHelpKey" value="button.add.help"/>
                            <tiles:put name="bundle"         value="kbkBundle"/>
                            <tiles:put name="action"         value="/private/dictionary/kbk/edit.do"/>
                            <tiles:put name="viewType" value="blueBorder"/>
                        </tiles:insert>
                        <tiles:insert definition="clientButton" flush="false" operation="ReplicateKBKOperation">
                            <tiles:put name="commandTextKey" value="button.replicate"/>
                            <tiles:put name="commandHelpKey" value="button.replicate.help"/>
                            <tiles:put name="bundle"         value="kbkBundle"/>
                            <tiles:put name="action"         value="/private/dictionary/kbk/replicate.do"/>
                            <tiles:put name="viewType" value="blueBorder"/>
                        </tiles:insert>
                    </c:when>
                    <c:otherwise>
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.cancel"/>
                            <tiles:put name="commandHelpKey" value="button.cancel"/>
                            <tiles:put name="bundle" value="billingBundle"/>
                            <tiles:put name="onclick" value="window.close();"/>
                        </tiles:insert>
                    </c:otherwise>
                </c:choose>

            </tiles:put>

            <tiles:put name="filter" type="string">
                <tiles:insert definition="filterTextField" flush="false">
                    <tiles:put name="label"  value="label.code"/>
                    <tiles:put name="bundle" value="kbkBundle"/>
                    <tiles:put name="name"   value="code"/>
                </tiles:insert>
                <tiles:insert definition="filterEntryField" flush="false">
                    <tiles:put name="label" value="label.payment.type"/>
                    <tiles:put name="bundle" value="kbkBundle"/>
                    <tiles:put name="data">
                        <html:select property="filter(paymentType)" styleClass="filterSelect" style="width:200px">
                            <html:option value="TAX"><bean:message bundle="kbkBundle" key="label.tax"/></html:option>
                            <html:option value="BUDGET"><bean:message bundle="kbkBundle" key="label.budget"/></html:option>
                            <html:option value="OFF_BUDGET"><bean:message bundle="kbkBundle" key="label.off.budget"/></html:option>
                        </html:select>
                    </tiles:put>
                </tiles:insert>

            </tiles:put>

            <tiles:put name="data" type="string">

                <script type="text/javascript">

                        function sendKBKData(event)
                        {
                            checkIfOneItem("selectedIds");
                            if (!checkOneSelection("selectedIds", "Выберите один КБК!"))
                               return;
                            var ids = document.getElementsByName("selectedIds");
                            for (var i = 0; i < ids.length; i++)
                            {
                                if (ids.item(i).checked)
                                {
                                    var row = ids.item(i).parentNode.parentNode;
                                    var res = new Array();
                                    res['id']   = ids.item(i).value;
                                    res['code'] = trim(row.cells[1].innerHTML);
                                    window.opener.setKBKInfo(res);
                                    window.close();
                                    return;
                                }
                            }
                            alert("Выберите КБК!");
                        }

                        function doRemove()
                        {
                            checkIfOneItem("selectedIds");
                            if (checkSelection('selectedIds', 'Укажите хотя бы одну запись'))
                            {
                                return confirm("Вы действительно хотите удалить " + getSelectedQnt('selectedIds') + " записей из справочника?");
                            }
                            return false;
                        }
                </script>

                <tiles:insert definition="tableTemplate" flush="false">
                    <tiles:put name="id" value="KBKListTable"/>
                    <tiles:put name="image" value=""/>
                    <tiles:put name="buttons">
                        <c:choose>
                            <c:when test="${standalone}">
                                <tiles:insert definition="clientButton" flush="false" operation="EditKBKOperation">
                                    <tiles:put name="commandTextKey" value="button.edit"/>
                                    <tiles:put name="commandHelpKey" value="button.edit.help"/>
                                    <tiles:put name="bundle"         value="kbkBundle"/>
                                    <tiles:put name="onclick"        value="doEdit();"/>
                                </tiles:insert>

                                <tiles:insert definition="commandButton" flush="false" operation="RemoveKBKOperation">
                                    <tiles:put name="commandKey"     value="button.remove"/>
                                    <tiles:put name="commandHelpKey" value="button.remove.help"/>
                                    <tiles:put name="bundle"         value="kbkBundle"/>
                                    <tiles:put name="validationFunction">
                                        doRemove()
                                    </tiles:put>
                                </tiles:insert>
                            </c:when>
                            <c:otherwise>
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.send"/>
                                    <tiles:put name="commandHelpKey" value="button.send"/>
                                    <tiles:put name="bundle"         value="kbkBundle"/>
                                    <tiles:put name="onclick"        value="sendKBKData(event);"/>
                                </tiles:insert>
                            </c:otherwise>
                        </c:choose>
                    </tiles:put>
                    <tiles:put name="grid">
                        <sl:collection id="listElement" model="list" property="data" bundle="kbkBundle">
                            <sl:collectionParam id="selectName"     value="selectedIds"/>
                            <sl:collectionParam id="selectType"     value="checkbox" condition="${standalone}"/>
                            <sl:collectionParam id="selectType"     value="radio"    condition="${not standalone}"/>
                            <sl:collectionParam id="selectProperty" value="id"/>

                            <sl:collectionParam id="onRowClick"     value="selectRow(this, 'selectedIds');" condition="${not standalone}"/>
                            <sl:collectionParam id="onRowDblClick"  value="sendBillingData();"              condition="${not standalone}"/>

                            <sl:collectionItem title="label.code">
                                <sl:collectionItemParam id="action" value="/private/dictionary/kbk/edit.do?id=${listElement.id}"/>
                                ${listElement.code}
                            </sl:collectionItem>
                            <sl:collectionItem title="label.description">
                                <sl:collectionItemParam id="action" value="/private/dictionary/kbk/edit.do?id=${listElement.id}"/>
                                ${listElement.description}
                            </sl:collectionItem>
                        </sl:collection>
                    </tiles:put>

                    <tiles:put name="emptyMessage"><bean:message bundle="kbkBundle" key="empty.message"/></tiles:put>
                </tiles:insert>
            </tiles:put>
        </tiles:insert>
    </c:otherwise>
</c:choose>
</html:form>