<%--
  Created by IntelliJ IDEA.
  User: gladishev
  Date: 17.06.2010
  Time: 9:19:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:form action="/private/dictionaries/provider/list">

    <tiles:importAttribute/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <tiles:insert definition="empty">
        <tiles:put name="data" type="string">
                <div id="reference">
                    <tiles:insert definition="mainWorkspace" flush="false">
                        <tiles:put name="title" value="Справочник получателей налогового платежа"/>
                        <tiles:put name="data">
                                <tiles:insert definition="formHeader" flush="false">
                                    <tiles:put name="image" value="${imagePath}/icon_reference.png"/>
                                    <tiles:put name="alt" value="Справочник налоговых получателей"/>
                                    <tiles:put name="description">
                                        Отметьте интересующего Вас получателя в списке и нажмите на кнопку "Выбрать".
                                    </tiles:put>
                                </tiles:insert>
                                <script type="text/javascript">

                                    $(document).ready(function(){
                                        initReferenceSize();
                                    });

                                    var providers = new Array();

                                    function sendProviderData(event, numb)
                                    {
                                        if (numb != null)
                                        {
                                            window.opener.setProviderInfo(providers[numb-1]);
                                            window.close();
                                            return;
                                        }

                                        var ids = document.getElementsByName("selectedIds");
                                        var res = new Array();
                                        for (var i = 0; i < ids.length; i++)
                                        {
                                            if (ids.item(i).checked)
                                            {
                                                window.opener.setProviderInfo(providers[i]);
                                                window.close();
                                                return;
                                            }
                                        }
                                        alert("Выберите получателя");
                                    }
                                </script>
                                <tiles:insert definition="simpleTableTemplate" flush="false" >
                                    <tiles:put name="grid">
                                        <sl:collection id="listElement" model="simple-pagination" property="data"  indexId="index" collectionSize="12">
                                            <sl:collectionParam id="selectType" value="radio"/>
                                            <sl:collectionParam id="selectName" value="selectedIds"/>
                                            <sl:collectionParam id="selectProperty" value="id"/>

                                            <sl:collectionParam id="onRowClick" value="selectRow(this,'selectedIds');"/>
                                            <sl:collectionParam id="onRowDblClick" value="sendProviderData(event);"/>

                                            <sl:collectionItem title="">
                                                ${index+1}.
                                            </sl:collectionItem>
                                            <sl:collectionItem title="">
                                                <a href="#" onclick="sendProviderData(event, '${index+1}'); return false;" class="simpleLink">
                                                    <u>${listElement.name}</u>
                                                </a>
                                                <div class="clear"></div>
                                                <c:if test="${not empty listElement.INN}"><<b>ИНН:</b>${listElement.INN}&nbsp;&nbsp;&nbsp;</c:if><c:if test="${not empty listElement.KPP}"><b>КПП:</b>&nbsp;${listElement.KPP}&nbsp;&nbsp;&nbsp;</c:if><c:if test="${not empty listElement.account}"><b>Расч.счет:</b>&nbsp;${listElement.account} </c:if>
                                                 <script type="text/javascript">
                                                    var provider = new Object();
                                                    provider.id = '${listElement.id}';
                                                    provider.name = '${listElement.name}';
                                                    provider.INN = '${listElement.INN}';
                                                    provider.KPP = '${listElement.KPP}';
                                                    provider.account = '${listElement.account}';
                                                    provider.BIC = '${listElement.BIC}';
                                                    provider.bankName = '${listElement.bankName}';
                                                    provider.corrAccount = '${listElement.corrAccount}';
                                                    providers[providers.length] = provider;
                                                </script>
                                            </sl:collectionItem>
                                        </sl:collection>
                                    </tiles:put>

                                    <tiles:put name="isEmpty" value="${empty form.data}"/>
                                    <tiles:put name="emptyMessage">
                                        <h3>Не найдено ни одного налогового получателя.</h3>
                                    </tiles:put>
                                </tiles:insert>
                                <html:hidden property="filter(kind)" value="T"/>
                                <html:hidden property="filter(STATE)" value="ACTIVE"/>
                            <div class="clear"></div>
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
                                       <tiles:put name="onclick"        value="sendProviderData(event);"/>
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