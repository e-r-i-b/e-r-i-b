
 /**
 * ��������� ������� ActiveX �� ��� ProgID
 * @param progId - ����������� ActiveX. ��� ������� �������� �� ������ ����� � �������� ������(��������: SBRF.Server)
 * @return - ������ �� ActiveX ������.
 */
function createActvieXObject(progId)
{
    try
    {
        if (window.ActiveXObject)
        {
            return new ActiveXObject(progId)
        }
        else if (window.GeckoActiveXObject)
        {
            return new GeckoActiveXObject(progId)
        }
        else
        {
            alert("���� ������� �� ������������ �������� ActiveX ��������.\n"+
                 "���������� ����������� Internet Explorer ������ 6 � ����.");
        }
    }
    catch (err)
    {
        window.alert("������ ��� �������� ActiveX �������.\n\n"+ err.message);
        if (err.message.indexOf("80004005") >= 0)
        {
            window.alert("�������������� ��������� ������ �������� ���, "+
                              "����� ��������� ������c����� ��������� ���������� ActiveX.");
        }
    }
}