SBRF.dll ���������� ������ � UPOS-����������, ��� ����������� ��������� ������������� ����� ����������� ����� �������.

����� ������� ����������� ���������� ���������������� dll � ������� �������
regsvr32 "����\���.dll" 

������������ ����� ����� 3008619728719560, ���� ����� ������ ����� �����
�� ����� �������� ���� card_number.txt c ������� ����� � ������ ����� D

������ �������������(JavaScript):

var activeXObject;
try
{
   activeXObject = createActvieXObject("SBRF.Server");
   activeXObject.Clear();
   if (activeXObject.NFun(5004) == "0")
   {
      getElement("field(cardNumber)").value = activeXObject.GParamString("ClientCard");
      activeXObject.Clear();
      createCommandButton('button.search', '�����').click('', false);
   }
   else
   {
      activeXObject.Clear();
      alert("");
   }
}
finally
{
                        
}
