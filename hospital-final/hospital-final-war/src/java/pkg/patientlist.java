/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg;

import static helper.HtmlGen.*;
//import static helper.HtmlGen.input;
//import static helper.HtmlGen.tagSeries;
//import static helper.HtmlGen.tagSeriesAtt;
//import static helper.HtmlGen.template;
//import static helper.HtmlGen.wrapTag;
import helper.Vals;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
public class patientlist extends HttpServlet {

    @EJB
    private PatientFacadeLocal patientFacade;

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
            //x = "";

            //x += "<table>";
            //x += "<tr><th>First Name</th><th>Last Name</th></tr>";
            List<Patient> l = patientFacade.findAll();
            String rows = "";
            String[] colNames;
            ArrayList<String> t = new ArrayList<String>();
            String docid = null;
            Object temp = null;

            String firstname = null;
            temp = request.getParameter("firstname");
            //System.out.print(temp+"");
            if (temp != null) {
                if (!temp.equals("")) {
                    firstname = temp + "";
                }
            }
            String lastname = null;
            temp = request.getParameter("lastname");
            //System.out.print(temp+"");
            if (temp != null) {
                if (!temp.equals("")) {
                    lastname = temp + "";
                }
            }

            temp = session.getAttribute("doctorid");
            if (temp != null) {
                docid = temp + "";
            }
            if (Vals.permission(session) > 1) {//admin
                colNames = new String[]{"Id", "Active", "Assigned Doctor Id", "FirstName", "LastName", "Username", "Password"};
                /*
                for(int c=0;c<colNames.length;c++){
                    colNames[c]=wrapTag(colNames[c],"h2");
                }
                //*/
                t.add(tagSeries("th", colNames));
                for (Patient c : l) {
                    boolean fn = firstname == null;
                    if (!fn) {
                        fn = c.getFirstName().contains(firstname);
                    }
                    boolean ln = lastname == null;
                    if (!ln) {
                        ln = c.getLastName().contains(lastname);
                    }
                    //System.out.println(c.getFirstName());
                    //System.out.println(firstname);
                    //System.out.println(c.getLastName());
                    //System.out.println(lastname);
                    if (fn && ln) {
                        t.add(
                                tagSeriesAtt("td", "align='center' style='font-weight:normal'",
                                        c.getId() + "",
                                        c.isActive() + "",
                                        c.getCurrentDoc() + "",
                                        c.getFirstName(),
                                        c.getLastName(),
                                        c.getUsername(),
                                        c.getPassword(),
                                        wrapTag(
                                                input("hidden", "Id", c.getId() + "")
                                                + input("submit", null, "Edit"),
                                                "form", at2("action", "patientedit") + at2("method", "post")
                                        ),
                                        wrapTag(
                                                input("hidden", "Id", c.getId() + "")
                                                + input("submit", null, "Delete"),
                                                "form", at2("action", "DeletePat") + at2("method", "post")
                                        ),
                                        wrapTag(
                                                input("hidden", "patid", c.getId() + "")
                                                + input("hidden", "docid", docid)
                                                + input("submit", null, "Add Visit"),
                                                "form", at2("action", "visitadd") + at2("method", "post")
                                        )
                                )
                        );
                    }
                }
            } else if (Vals.permission(session) == 1) {//regular
                colNames = new String[]{"FirstName", "LastName"};
                t.add(tagSeries("th", colNames));
                for (Patient c : l) {
                    if (c.isActive()) {
                        if (c.getCurrentDoc() == Long.parseLong(docid)) {
                            boolean fn = firstname == null;
                            if (!fn) {
                                fn = c.getFirstName().contains(firstname);
                            }
                            boolean ln = lastname == null;
                            if (!ln) {
                                ln = c.getLastName().contains(lastname);
                            }
                            if (fn && ln) {
                                t.add(
                                        tagSeriesAtt("td", "align='center' style='font-weight:normal'",
                                                c.getFirstName(),
                                                c.getLastName(),
                                                wrapTag(
                                                        input("hidden", "patid", c.getId() + "")
                                                        + input("hidden", "docid", docid)
                                                        + input("submit", null, "Add Visit"),
                                                        "form", at2("action", "visitadd") + at2("method", "post")
                                                )
                                        )
                                );
                            }
                        }
                    }
                }
            } else {
                response.sendRedirect("home");
                return;
            }

            String[] c = new String[t.size()];
            c = (String[]) t.toArray(c);
            //x = "";

            x += wrapTag(
                    wrapTag(
                            tagSeries("tr",
                                    tagSeries("td",
                                            "First Name:",
                                            input("text", "firstname", firstname)
                                    ),
                                    tagSeries("td",
                                            "Last Name:",
                                            input("text", "lastname", lastname)
                                    ),
                                    wrapTag(input("submit", null, "Search"), "td", at("colspan", "2"))
                            ),
                            "table"),
                    "form", at2("action", "patientlist") + at2("method", "get"));
            x += wrapTag(
                    tagSeries("tr",
                            c
                    ),
                    "table");
            /*
                System.out.println(x);//*/
            out.print(template(session, x, "Patients"));

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
