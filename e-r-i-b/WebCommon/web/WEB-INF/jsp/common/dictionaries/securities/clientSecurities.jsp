
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:form action="/private/dictionary/clientSecurities">

    <tiles:importAttribute/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <tiles:insert definition="empty">
        <tiles:put name="data" type="string">
            <div id="dictionaryList">
                <div id="payment">
                    <tiles:insert definition="mainWorkspace" flush="false">
                        <tiles:put name="title" value="Справочник ценных бумаг" />
                        <tiles:put name="data">
                            <script type="text/javascript">
                                $(document).ready(function(){
                                    var windowWidth = 750;

                                    var windowHeight = $('#payment').height()+130;

                                    window.resizeTo(windowWidth, windowHeight);

                                    window.moveTo(screen.width/2 - windowWidth/2, screen.height/2 - windowHeight/2);
                                });

                                function sendSecurityData()
                                {
                                    var ids = document.getElementsByName("selectedIds");
                                    var key = getRadioValue(ids);
                                    for (var i = 0; i < ids.length; i++)
                                    {
                                        if (ids.item(i).checked)
                                        {
                                            var r = ids.item(i).parentNode.parentNode;
                                            var a = new Array(5);
                                            a['inside-code']         = trim(getElementTextContent(r.cells[2]));
                                            a['name']                = trim(getElementTextContent(r.cells[1]));
                                            a['registration-number'] = trim(getElementTextContent(r.cells[3]));
                                            a['nominal-amount']      = trim(getElementTextContent(r.cells[4]));
                                            a['nominal-currency']    = trim(getElementTextContent(r.cells[5]));
                                            window.opener.setSecuritiesInfo(a);
                                            window.close();
                                            return;
                                        }
                                    }
                                    alert("Выберите ценную бумагу.");
                                }

                            </script>

                            <tiles:insert definition="formHeader" flush="false">
                                <tiles:put name="image" value="${imagePath}/icon_securities.png"/>
                                <tiles:put name="alt" value="Справочник ценных бумаг"/>
                                <tiles:put name="description">
                                    Отметьте интересующую Вас ценную бумагу и нажмите на кнопку <b>Выбрать</b>.
                                </tiles:put>
                            </tiles:insert>
                           <div class="clear"></div>

                            <tiles:insert definition="simpleTableTemplate" flush="false">
                                <tiles:put name="grid">
                                    <sl:collection id="listElement" model="simple-pagination" property="securityList" collectionSize="10" needJsPagination="true">
                                        <sl:collectionParam id="selectType" value="radio"/>
                                        <sl:collectionParam id="selectName" value="selectedIds"/>
                                        <sl:collectionParam id="selectProperty" value="insideCode"/>

                                        <sl:collectionParam id="onRowClick" value="selectRow(this,'selectedIds');"/>
                                        <sl:collectionParam id="onRowDblClick" value="sendSecurityData();"/>
                                        <sl:collectionItem title="Наименование" property="name"/>
                                        <sl:collectionItem title="Внутренний код" property="insideCode"/>
                                        <sl:collectionItem title="Регистрационный номер" property="registrationNumber"/>
                                        <sl:collectionItem title="Номинал" property="nominal.decimal"/>
                                        <sl:collectionItem title="Валюта" property="nominal.currency.code"/>                                    
                                    </sl:collection>
                                </tiles:put>

                                <tiles:put name="isEmpty" value="${empty form.securityList}"/>
                                <tiles:put name="emptyMessage">
                                    <h3>В справочнике нет ценных бумаг, соответствующих заданным параметрам поиска. Попробуйте ввести другое значение.</h3>
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
                                <c:if test="${not empty form.securityList}">
                                    <tiles:insert definition="clientButton" flush="false">
                                       <tiles:put name="commandTextKey" value="button.choose"/>
                                       <tiles:put name="commandHelpKey" value="button.choose.help"/>
                                       <tiles:put name="bundle"         value="dictionaryBundle"/>
                                       <tiles:put name="onclick"        value="sendSecurityData();"/>
                                    </tiles:insert>
                                </c:if>
                            </div>
                            <div class="clear"></div>
                        </tiles:put>
                    </tiles:insert>
                </div>
            </div>
        </tiles:put>
    </tiles:insert>
</html:form>
