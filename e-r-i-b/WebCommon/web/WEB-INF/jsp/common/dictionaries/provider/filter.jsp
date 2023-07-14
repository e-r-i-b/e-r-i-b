<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<html:form action="/private/dictionaries/provider/filter">
    <tiles:insert definition="dictionary">

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id"  value="provider"/>
                <tiles:put name="alignTable" value="center"/>
                <tiles:put name="name"><bean:message bundle="providerBundle" key="filterform.name"/></tiles:put>
                <tiles:put name="description"><bean:message bundle="providerBundle" key="filterform.title"/></tiles:put>
                <tiles:put name="data">
                    <tr>
                        <td class="Width120 LabelAll" nowrap="true">
                            <bean:message bundle="providerBundle" key="filter.region"/>
                        </td>
                        <td>
                            <nobr>
                                <input type="text" id="regionName" value="" readonly="true" size="50"/>
                                <input type="hidden" id="regionId" value=""/>
                                <input type="button" class="buttWhite" onclick="openRegionsDictionary(setRegionInfo, 'filter');" value="..."/>
                                <script type="text/javascript">
                                   function setRegionInfo(result)
                                   {
                                       document.getElementById("regionId").value   = result['id'];
                                       document.getElementById("regionName").value = result['name'];
                                   }
                               </script>
                            </nobr>
                        </td>
                    </tr>
                    <tr>
                        <td class="Width120 LabelAll" nowrap="true">
                            <bean:message bundle="providerBundle" key="filter.paymentService"/>
                        </td>
                        <td>
                            <nobr>
                                <input type="text" id="paymentServiceName" value="" readonly="true" size="50"/>
                                <input type="hidden" id="paymentServiceId" value=""/>
                                <input type="button" class="buttWhite" onclick="openPaymentServicesDictionary(setPaymentServiceInfo);" value="..."/>
                                <script type="text/javascript">
                                   function setPaymentServiceInfo(result)
                                   {
                                       document.getElementById("paymentServiceId").value   = result['id'];
                                       document.getElementById("paymentServiceName").value = result['name'];
                                   }
                               </script>
                            </nobr>
                        </td>
                    </tr>
                    <tr>
                        <td class="Width120 LabelAll" nowrap="true">
                            <bean:message bundle="providerBundle" key="filter.name"/>
                        </td>
                        <td>
                            <nobr>
                                <input type="text" id="name" value="" size="50"/>
                            </nobr>
                        </td>
                    </tr>
                    <tr>
                        <td class="Width120 LabelAll" nowrap="true">
                            <bean:message bundle="providerBundle" key="filter.account"/>
                        </td>
                        <td>
                            <nobr>
                                <input type="text" id="account" value="" size="50"/>
                            </nobr>
                        </td>
                    </tr>
                    <tr>
                        <td class="Width120 LabelAll" nowrap="true">
                            <bean:message bundle="providerBundle" key="filter.INN"/>
                        </td>
                        <td>
                            <nobr>
                                <input type="text" id="INN" value="" size="50"/>
                            </nobr>
                        </td>
                    </tr>
                </tiles:put>
                <tiles:put name="buttons">
                    <script type="text/javascript">
                        <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/private/dictionaries/provider/list')}"/>

                        function applyFilter()
                        {
                            var regionId = document.getElementById("regionId").value;
                            var regionName = document.getElementById("regionName").value;
                            var paymentServiceId = document.getElementById("paymentServiceId").value;
                            var paymentServiceName = document.getElementById("paymentServiceName").value;
                            var name = document.getElementById("name").value;
                            var account = document.getElementById("account").value;
                            var INN = document.getElementById("INN").value;
                            var addr = '${url}' + '?action=getProviderInfo' + '&regionId=' + regionId + '&regionName=' + regionName +
                                       '&paymentServiceId=' + paymentServiceId + '&paymentServiceName=' + paymentServiceName +
                                       '&name=' + name + '&account=' + account + '&INN=' + INN;
                            window.location = addr;
                        }
                        
                        function clearFilter()
                        {
                            document.getElementById("regionId").value = '';
                            document.getElementById("regionName").value = '';
                            document.getElementById("paymentServiceId").value = '';
                            document.getElementById("paymentServiceName").value = '';
                            document.getElementById("name").value = '';
                            document.getElementById("account").value = '';
                            document.getElementById("INN").value = '';
                            return true;
                        }
                    </script>
                    <tiles:insert definition="clientButton" flush="false" operation="ViewListServiceProvidersOperation">
                        <tiles:put name="commandTextKey"     value="button.apply"/>
                        <tiles:put name="commandHelpKey" value="button.apply.help"/>
                        <tiles:put name="bundle"         value="providerBundle"/>
                        <tiles:put name="isDefault"        value="true"/>
                        <tiles:put name="onclick"        value="javascript:applyFilter(event)"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false">
                       <tiles:put name="commandTextKey" value="button.clear"/>
                       <tiles:put name="commandHelpKey" value="button.clear.help"/>
                       <tiles:put name="bundle"         value="providerBundle"/>
                       <tiles:put name="onclick"        value="javascript:clearFilter(event)"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false">
                       <tiles:put name="commandTextKey" value="button.filter.cancel"/>
                       <tiles:put name="commandHelpKey" value="button.filter.cancel.help"/>
                       <tiles:put name="bundle"         value="providerBundle"/>
                        <tiles:put name="onclick"       value="window.close();"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>

    </tiles:insert>

</html:form>