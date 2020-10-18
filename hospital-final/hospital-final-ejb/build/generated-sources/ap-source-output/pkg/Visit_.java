package pkg;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-04-20T15:01:58")
@StaticMetamodel(Visit.class)
public class Visit_ { 

    public static volatile SingularAttribute<Visit, Long> idDoctor;
    public static volatile SingularAttribute<Visit, Boolean> active;
    public static volatile SingularAttribute<Visit, Long> idPatient;
    public static volatile SingularAttribute<Visit, String> details;
    public static volatile SingularAttribute<Visit, Long> id;

}