<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<html:form action="/private/contacts/list">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>

    <tiles:insert definition="empty" flush="false">
        <tiles:put name="data">
            <script type="text/javascript">
                function sendPhoneNumber()
                {
                    var ids = document.getElementsByName("selectedIds");
                    for (var i = 0; i < ids.length; i++)
                    {
                        if (ids.item(i).checked)
                        {
                            var r = ids.item(i).parentNode.parentNode;
                            var phone = trim(r.cells[2].innerHTML).substring(2);
                            window.opener.setPhoneNumber(phone);
                            window.close();
                            return;
                        }
                    }
                    $('#errors').show();
                }

                function closeChooseContactError()
                {
                    $('#errors').hide();
                }
            </script>
            <div id="reference">
                <tiles:insert definition="mainWorkspace" flush="false">
                    <tiles:put name="title">Справочник контактов</tiles:put>
                    <tiles:put name="data">
                        <tiles:insert definition="formHeader" flush="false">
                            <tiles:put name="image" value="${imagePath}/icon_reference.png"/>
                            <tiles:put name="width" value="64px"/>
                            <tiles:put name="height" value="64px"/>
                            <tiles:put name="description">
                               Выберите имя контакта и нажмите на кнопку «Выбрать».
                            </tiles:put>
                        </tiles:insert>
                        <%-- Фильтр --%>
                        <div class="paymentFilter">
                            <div class="filter">
                                <h1>Поиск</h1>
                                <div class="paymentSearch">
                                    <div class="searchForm" onkeydown="onEnterKey(event);">
                                        <div class="rightRound">
                                            <div class="cetnerSearch">
                                                <html:text property="filter(name)" value="${form.filters.name}" styleClass="searchEmpty customPlaceholder search" maxlength="100" size="95" title="введите имя контакта для поиска"/>

                                                <div id="search-button">
                                                    <tiles:insert definition="commandButton" flush="false">
                                                        <tiles:put name="commandKey" value="button.filter"/>
                                                        <tiles:put name="commandHelpKey" value="button.filter.help"/>
                                                        <tiles:put name="bundle" value="dictionaryBundle"/>
                                                        <tiles:put name="isDefault" value="true"/>
                                                    </tiles:insert>
                                                </div>
                                                <div class="clear"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="payments-legend">Поиск осуществляется по имени контакта</div>
                            </div>
                        </div>
                         <%-- /Фильтр --%>
                        <div class="clear"></div>
                        <br/>

                        <div id="errors" style="display: none;">
                            <tiles:insert definition="roundBorderLight" flush="false">
                                <tiles:put name="color" value="red"/>
                                <tiles:put name="data">
                                    <bean:message bundle="contactBundle" key="lable.choose.contact.error"/>

                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="button.closeError"/>
                                        <tiles:put name="commandHelpKey" value="button.closeError.help"/>
                                        <tiles:put name="bundle"         value="contactBundle"/>
                                        <tiles:put name="onclick"        value="closeChooseContactError();"/>
                                        <tiles:put name="viewType" value="buttonGrey"/>
                                    </tiles:insert>
                                    <div class="clear"></div>
                                </tiles:put>
                            </tiles:insert>
                        </div>

                        <tiles:insert definition="simpleTableTemplate" flush="false">
                            <tiles:put name="grid">
                                <sl:collection id="contact" model="simple-pagination" property="data">
                                    <sl:collectionParam id="selectType" value="radio"/>
                                    <sl:collectionParam id="selectName" value="selectedIds"/>
                                    <sl:collectionParam id="selectProperty" value="id"/>

                                    <sl:collectionItem title="Контакт" property="fullName" filter="true"/>
                                    <sl:collectionItem title="Номер телефона" value="+${contact.phone}"/>

                                    <sl:collectionParam id="onRowClick" value="selectRow(this,'selectedIds');"/>
                                    <sl:collectionParam id="onRowDblClick" value="sendPhoneNumber();"/>
                                </sl:collection>
                            </tiles:put>
                            <tiles:put name="isEmpty" value="${empty form.data}"/>
                            <tiles:put name="emptyMessage">
                                Не найдено ни одного контакта, соответствующего заданному фильтру
                            </tiles:put>
                        </tiles:insert>
                        <div class="buttonsArea">
                            <tiles:insert definition="clientButton" flush="false">
                               <tiles:put name="commandTextKey" value="button.cancel"/>
                               <tiles:put name="commandHelpKey" value="button.cancel"/>
                               <tiles:put name="bundle"         value="dictionaryBundle"/>
                               <tiles:put name="onclick"        value="window.close();"/>
                                <tiles:put name="viewType" value="buttonGrey"/>
                            </tiles:insert>
                            <c:if test="${not empty form.data}">
                                <tiles:insert definition="clientButton" flush="false">
                                   <tiles:put name="commandTextKey" value="button.choose"/>
                                   <tiles:put name="commandHelpKey" value="button.choose.help"/>
                                   <tiles:put name="bundle"         value="dictionaryBundle"/>
                                   <tiles:put name="onclick"        value="sendPhoneNumber();"/>
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