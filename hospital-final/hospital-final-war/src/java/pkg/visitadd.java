/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg;

import helper.*;
import static helper.HtmlGen.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author artour
 */
public class visitadd extends HttpServlet {

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
        String patid=null;
        String docid=null;
        Object temp;
        temp=request.getParameter("patid");
        if(temp!=null){
            patid=""+temp;
        }
        temp=session.getAttribute("doctorid");
        if(temp!=null){
            docid=""+temp;
        }
        
        try (PrintWriter out = response.getWriter()) {
            if(Vals.permission(session)<1){//patient or logged out
                response.sendRedirect("patientlist");
            }else if(Vals.permission(session)==1){//doctor
                String x = "";
                x += wrapTag(
                        wrapTag(
                                tagSeries("tr",
                                        //tagSeries("td", "Id:", "<Input type='number' name='Id'>"),
                                        wrapTag(wrapTag("Add Visit","h1"),"th",at("colspan","2")),
                                        tagSeries("td", "Active:", "<Input type='checkbox' name='active' value='active' checked>"),
                                        //tagSeries("td", "Doctor Id:", input("number","docid",docid)),
                                        tagSeries("td", "Patient Id:", input("number","patid",patid)),
                                        tagSeries("td", "Details:", input("text","details")),
                                        wrapTag("<Input type='submit' value='Add'", "td", at("colspan", "2"))
                                )+input("hidden","docid",docid),
                                "form", at2("action", "AddVisit")+at2("method","post")
                        ),
                        "table");
                /*
                System.out.println(x);//*/
                out.print(template(session, x, "Add Visit"));
            }else if (Vals.permission(session) > 1) {//admin
                String x = "";
                x += wrapTag(
                        wrapTag(
                                tagSeries("tr",
                                        //tagSeries("td", "Id:", "<Input type='number' name='Id'>"),
                                        wrapTag(wrapTag("Add Visit","h1"),"th",at("colspan","2")),
                                        tagSeries("td", "Active:", "<Input type='checkbox' name='active' value='active' checked>"),
                                        tagSeries("td", "Doctor Id:", input("number","docid",docid)),
                                        tagSeries("td", "Patient Id:", input("number","patid",patid)),
                                        tagSeries("td", "Details:", input("text","details")),
                                        wrapTag("<Input type='submit' value='Add'", "td", at("colspan", "2"))
                                ),
                                "form", at2("action", "AddVisit")+at2("method","post")
                        ),
                        "table");
                /*
                System.out.println(x);//*/
                out.print(template(session, x, "Add Visit"));
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
