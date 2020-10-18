/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg;

import static helper.HtmlGen.*;
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
public class patientedit extends HttpServlet {

    @EJB
    private PatientFacadeLocal patientFacade;

    
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
            Long id=Long.parseLong(request.getParameter("Id"));
            
            if (Vals.permission(session) > 1) {//admin
                String x = "";
                x += wrapTag(
                        wrapTag(
                                tagSeries("tr",
                                        //tagSeries("td", "Id:", "<Input type='number' name='Id'>"),
                                       
                                        tagSeries("td", "Active:", input("checkbox","active","active",strChecked(patientFacade.find(id).isActive()))),
                                        tagSeries("td", "Assigned Doctor:", input("number","docid",patientFacade.find(id).getCurrentDoc()+"")),
                                        tagSeries("td", "First Name:", input("text","firstname",patientFacade.find(id).getFirstName())),
                                        tagSeries("td", "Last Name:", input("text","lastname",patientFacade.find(id).getLastName())),
                                        tagSeries("td", "Username:", input("text","username",patientFacade.find(id).getUsername())),
                                        tagSeries("td", "Password:", input("text","password",patientFacade.find(id).getPassword())),
                                        wrapTag("<Input type='submit' value='Edit'", "td", at("colspan", "2"))
                                )+ input("hidden","Id",id+""),
                                "form", at2("action", "EditPat")+at2("method","post")
                        ),
                        "table");
                x+=btnLink("Go Back","patientlist");
                //System.out.println(x);
                out.print(template(session, x, "Edit Patient"));
            }else if(Vals.permission(session) == 1){
                String x = "";
                x += wrapTag(
                        wrapTag(
                                tagSeries("tr",
                                        //tagSeries("td", "Id:", "<Input type='number' name='Id'>"),
                                        //tagSeries("td", "Active:", input("checkbox","active","active",strChecked(patientFacade.find(id).isActive()))),
                                        tagSeries("td", "Active:", input("checkbox","active","active",strChecked(patientFacade.find(id).isActive()))),
                                        tagSeries("td", "First Name:", input("text","firstname",patientFacade.find(id).getFirstName())),
                                        tagSeries("td", "Last Name:", input("text","lastname",patientFacade.find(id).getLastName())),
                                        tagSeries("td", "Username:", input("text","username",patientFacade.find(id).getUsername())),
                                        tagSeries("td", "Password:", input("text","password",patientFacade.find(id).getPassword())),
                                        wrapTag("<Input type='submit' value='Edit'>", "td", at("colspan", "2"))
                                )+
                                        input("hidden","Id",id+"")+
                                        input("hidden","docid",patientFacade.find(id).getCurrentDoc()+""),
                                "form", at2("action", "EditPat")+at2("method","post")
                        ),
                        "table");
                x+=btnLink("Go Back","patientlist");
                //System.out.println(x);
                out.print(template(session, x, "Edit Patient"));
            }else {
                response.sendRedirect("patientlist");
            }
        }catch(Exception e){
            response.sendRedirect("patientlist");
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
