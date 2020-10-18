package pkg;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-04-20T15:01:58")
@StaticMetamodel(Patient.class)
public class Patient_ { 

    public static volatile SingularAttribute<Patient, String> firstName;
    public static volatile SingularAttribute<Patient, String> lastName;
    public static volatile SingularAttribute<Patient, String> password;
    public static volatile SingularAttribute<Patient, Long> currentDoc;
    public static volatile SingularAttribute<Patient, Boolean> active;
    public static volatile SingularAttribute<Patient, Long> id;
    public static volatile SingularAttribute<Patient, String> username;

}