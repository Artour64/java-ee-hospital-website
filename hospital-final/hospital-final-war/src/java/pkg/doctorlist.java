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
import java.util.ArrayList;
import java.util.List;
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
public class doctorlist extends HttpServlet {

    @EJB
    private DoctorFacadeLocal doctorFacade;

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
        String x = "";
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();
            Vals.myVals(request);
            x = "";

            //x += "<table>";
            //x += "<tr><th>First Name</th><th>Last Name</th></tr>";
            List<Doctor> docs = doctorFacade.findAll();
            String rows = "";
            String[] colNames;
            ArrayList<String> t = new ArrayList<String>();
            if (Vals.permission(session) > 1) {//admin
                colNames = new String[]{"Id", "Active", "FirstName", "LastName", "Username", "Password"};
                /*
                for(int c=0;c<colNames.length;c++){
                    colNames[c]=wrapTag(colNames[c],"h2");
                }
                //*/
                t.add(tagSeries("th", colNames));
                for (Doctor c : docs) {
                    t.add(
                            tagSeriesAtt("td", "align='center' style='font-weight:normal'",
                                    c.getId() + "",
                                    c.isActive() + "",
                                    c.getFirstName(),
                                    c.getLastName(),
                                    c.getUsername(),
                                    c.getPassword(),
                                    wrapTag(
                                            input("hidden", "Id", c.getId() + "")
                                            + input("submit", null, "Edit"),
                                            "form", at2("action", "doctoredit") + at2("method", "post")
                                    ),
                                    wrapTag(
                                            input("hidden", "Id", c.getId() + "")
                                            + input("submit", null, "Delete"),
                                            "form", at2("action", "DeleteDoc") + at2("method", "post")
                                    )
                            )
                    );

                }
            } else {//regular
                colNames = new String[]{"FirstName", "LastName"};
                t.add(tagSeries("th", colNames));
                for (Doctor c : docs) {
                    if(c.isActive()){
                        t.add(tagSeriesAtt("td", "align='center' style='font-weight:normal'", c.getFirstName(), c.getLastName()));
                    }
                }
            }
            x += "</table>";
            String[] c = new String[t.size()];
            c = (String[]) t.toArray(c);
            x = "";

            x += wrapTag(
                    tagSeries("tr",
                            c
                    ),
                    "table");
            /*
                System.out.println(x);//*/
            out.print(template(session, x, "Doctors"));

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
