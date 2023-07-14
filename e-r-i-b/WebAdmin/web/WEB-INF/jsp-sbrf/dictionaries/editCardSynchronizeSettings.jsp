<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>

<html:form action="/dictionaries/synchronize/card_settings/edit" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="dictionary">
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="CardSynchronizeSettings"/>
                <tiles:put name="name" value="�������� ��� (������) ������ ��� �������� ����"/>
                <tiles:put name="description" value="��� ���������� ���� ������ ������� ��� ���� � ��� �������� ������ � ������� �� ������ �����������."/>
                <tiles:put name="data">
                    <script type="text/javascript">
                        doOnLoad(function() {
                            init();
                        });

                        function init()
                        {
                            updateEditingNumber();
                            updateFormTextValues();
                        }

                        function updateFormTextValues()
                        {
                            if(opener.getPageTitle == undefined)
                                return;
                            $('div.pageTitle').eq(1).html(opener.getPageTitle());
                            $('td.pmntTitleText').eq(0).find('span').eq(0).html(opener.getPageTitle());
                            if (opener.getHelpInfo == undefined)
                                return;
                            $('div.pmntTitleText').html(opener.getHelpInfo());
                            if (opener.getLabel1 == undefined)
                                return;
                            $('div.paymentLabel').eq(0).html(opener.getLabel1());
                            if (opener.getLabel2 == undefined)
                                return;
                            $('div.paymentLabel').eq(1).html(opener.getLabel2());
                        }

                        //��� � ���������
                        function number(kind, subkinds)
                        {
                            this.kind = kind;
                            this.subkinds = subkinds;
                        }

                        var editingNumber; //������������� (�����������) ���

                        function updateEditingNumber()
                        {
                            editingNumber = window.opener.getEditingNumber();
                            if(editingNumber == null) return;
                            //��������� ���� ������� �� ���������� ����
                            ensureElement("kind").value = editingNumber.kind;
                            ensureElement("subkinds").value = editingNumber.subkinds;
                        }
                        
                        function sendNumber()
                        {
                            var kind = trim(ensureElement("kind").value);
                            var subkinds = trim(ensureElement("subkinds").value);

                            var regexKind = new RegExp(/^\s*\d{1,3}\s*$/);
                            var regexSubkinds = new RegExp(/^((\s*\d{1,19}\s*)(,\s*\d{1,19}\s*)*)?$/);

                            if(!regexKind.test(kind))
                            {
                                if (opener.isIMAforLoading == undefined)
                                    alert("����������, ������� ��� ���� ������, ���������� �� 1 �� 3 ����.");
                                else
                                    alert("����������, ������� ��� ���� ���, ���������� �� 1 �� 3 ����.");
                                return;
                            }
                            if(!regexSubkinds.test(subkinds))
                            {
                                if (opener.isIMAforLoading == undefined)
                                    alert("������� ���������� �������� ����� �������� ������ - �����, ����������� ��������.");
                                else
                                    alert("������� ���������� �������� ����� �������� ��� - �����, ����������� ��������.");
                                return;
                            }

                            editingNumber = new number(kind, subkinds);
                            var res = window.opener.addOrUpdateNumber(editingNumber, ${param['update']});
                            var wrongNumberMessage = opener.wrongNumberMessage;
                            if(wrongNumberMessage == undefined)
                                wrongNumberMessage = "��������� ���� ��� �� ������������� ���������� ���� ������. ����������, ������� ������ ���.";
                            switch(res)
                            {
                                case -1:
                                    alert(wrongNumberMessage);
                                    break;
                                case -2:
                                    if (opener.isIMAforLoading == undefined)
                                        alert("������ ��� ������ ��� ���������� � �������. ����������, ������� ������ ����� ���� ������.");
                                    else
                                        alert("������ ��� ��� ��� ���������� � �������. ����������, ������� ������ ����� ���� ���.");
                                    break;
                                case 0:
                                    window.close();
                            }
                        }

                        function cancel()
                        {
                            window.close();
                        }
                    </script>

                    <input type="hidden" id="conditionId"/>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            ��� ���� ������
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <input type="text" id="kind" size="3" maxlength="3" ${param['update'] ? 'readonly' : ''}/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            ��� ������� ������
                        </tiles:put>
                        <tiles:put name="data">
                            <input type="text" id="subkinds" size="20" maxlength="100"/>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>

                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save"/>
                        <tiles:put name="bundle" value="dictionariesBundle"/>
                        <tiles:put name="onclick">sendNumber();</tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel"/>
                        <tiles:put name="bundle" value="dictionariesBundle"/>
                        <tiles:put name="onclick">cancel();</tiles:put>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>