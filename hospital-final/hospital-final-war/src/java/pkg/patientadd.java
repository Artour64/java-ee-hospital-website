/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg;

import static helper.HtmlGen.*;
import static helper.HtmlGen.at2;
import static helper.HtmlGen.tagSeries;
import static helper.HtmlGen.template;
import static helper.HtmlGen.wrapTag;
import helper.Vals;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author artour
 */
public class patientadd extends HttpServlet {

    //@EJB
    //private DoctorFacadeLocal doctorFacade;

    //@EJB
    //private PatientFacadeLocal patientFacade;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        Vals.myVals(request);
        try (PrintWriter out = response.getWriter()) {
            if(Vals.permission(session)<1){//patient or logged out
                response.sendRedirect("patientlist");
            }else if(Vals.permission(session)==1){//doctor
                String x = "";
                x += wrapTag(
                        wrapTag(
                                tagSeries("tr",
                                        //tagSeries("td", "Id:", "<Input type='number' name='Id'>"),
                                        wrapTag(wrapTag("Add Patient","h1"),"th",at("colspan","2")),
                                        tagSeries("td", "Active:", "<Input type='checkbox' name='active' value='active' checked>"),
                                        tagSeries("td", "First Name:", "<Input type='text' name='firstname'>"),
                                        tagSeries("td", "Last Name:", "<Input type='text' name='lastname'>"),
                                        tagSeries("td", "Username:", "<Input type='text' name='username'>"),
                                        tagSeries("td", "Password:", "<Input type='text' name='password'>"),
                                        wrapTag("<Input type='submit' value='Add'", "td", at("colspan", "2"))
                                )+input("hidden","docid",session.getAttribute("doctorid")+""),
                                "form", at2("action", "AddPat")+at2("method","post")
                        ),
                        "table");
                /*
                System.out.println(x);//*/
                out.print(template(session, x, "Add Patient"));
            }else if (Vals.permission(session) > 1) {//admin
                Object temp=session.getAttribute("doctorid");
                String id=null;
                if(temp!=null){
                    id=temp+"";
                }else{
                    id="";
                }
                String x = "";
                x += wrapTag(
                        wrapTag(
                                tagSeries("tr",
                                        //tagSeries("td", "Id:", "<Input type='number' name='Id'>"),
                                        wrapTag(wrapTag("Add Patient","h1"),"th",at("colspan","2")),
                                        tagSeries("td", "Active:", "<Input type='checkbox' name='active' value='active' checked>"),
                                        tagSeries("td", "Assigned Doctor:", input("number","docid",id)),
                                        tagSeries("td", "First Name:", "<Input type='text' name='firstname'>"),
                                        tagSeries("td", "Last Name:", "<Input type='text' name='lastname'>"),
                                        tagSeries("td", "Username:", "<Input type='text' name='username'>"),
                                        tagSeries("td", "Password:", "<Input type='text' name='password'>"),
                                        wrapTag("<Input type='submit' value='Add'", "td", at("colspan", "2"))
                                ),
                                "form", at2("action", "AddPat")+at2("method","post")
                        ),
                        "table");
                /*
                System.out.println(x);//*/
                out.print(template(session, x, "Add Patient"));
            } else {
                response.sendRedirect("patientlist");
            }
        }
    
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
