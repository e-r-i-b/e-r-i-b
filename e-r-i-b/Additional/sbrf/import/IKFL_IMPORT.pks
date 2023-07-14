CREATE OR REPLACE PACKAGE IKFL_IMPORT AS
                           
    PROCEDURE createDepartments;

    PROCEDURE createDepartment(osbVar in VARCHAR2,officeVar in VARCHAR2);
    
    PROCEDURE importClient (id IN VARCHAR2);
    
    PROCEDURE importAllClient;
    
END IKFL_IMPORT;
/