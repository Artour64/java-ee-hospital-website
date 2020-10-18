/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author artour
 */
public class Vals {
    //public static int permission=-1;
    
    //session.getAttribute("userid")
    
    public static int permission(HttpSession session){
        if(session.getAttribute("adminid")!=null){
            return 2;
        }
        else if(session.getAttribute("doctorid")!=null){
            return 1;
        }
        else if(session.getAttribute("patientid")!=null){
            return 0;
        }
        return -1;
    }
    
    public static void myVals(HttpServletRequest rq){//Called at every page. Doesn't do anything at the moment but provides opertunity for extensability through modularity.
        HttpSession session=rq.getSession();
        //add universal page logic here
    }
}
