<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/dictionary/kbk/replicate" enctype="multipart/form-data">
    <tiles:insert definition="listKBK">

        <tiles:put name="submenu" value="KBK"/>

        <tiles:put name="menu" type="string"/>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value=""/>
                <tiles:put name="name">
                    <bean:message bundle="kbkBundle" key="label.replic.name"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message bundle="kbkBundle" key="label.replic.title"/>
                </tiles:put>

                <tiles:put name="data">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="kbkBundle" key="label.file"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:file property="fileImage" size="50"/>
                        </tiles:put>
                    </tiles:insert>

                    <script type="text/javascript">
                        function doReplicate()
                        {
                            if (getElementValue("fileImage") != "")
                            {
                                return true;
                            }
                            return alert("Укажите файл");
                        }
                    </script>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.replicate"/>
                        <tiles:put name="commandHelpKey" value="button.replicate.help"/>
                        <tiles:put name="bundle"         value="kbkBundle"/>
                        <tiles:put name="validationFunction">
                            doReplicate();
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle"         value="kbkBundle"/>
                        <tiles:put name="action"         value="/private/dictionary/kbk/list.do"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>