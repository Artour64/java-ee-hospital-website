/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import javax.servlet.http.HttpSession;

/**
 *
 * @author artour
 */
public class HtmlGen {

    public boolean missingQuote(String att){
        return (att.length() - att.replace("'", "").length()) % 2 != 0;
    }
    
    public String fixQuote(String x){
        if(missingQuote(x)){
            x+="'";
        }
        return x;
    }
    
    public static String strChecked(boolean x) {
        if (x) {
            return " checked ";
        }
        return "";
    }
    
    public static String activeN(boolean x) {
        if (x) {
            return "active";
        }
        return null;
    }

    public static String input(String type, String name) {
        return input(type, name, null);
    }

    public static String input(String type, String name, String value) {
        return input(type, name, value, null);
    }

    public static String input(String type, String name, String value, String att) {
        String x = "";
        x += "<input type='" + type + "'";
        if (name != null) {
            x += " name='" + name + "'";
        }
        if (value != null) {
            x += " value='" + value + "'";
        }
        if (att != null) {
            if (att.length() > 0) {
                x += " " + att;
                if ((att.length() - att.replace("'", "").length()) % 2 != 0) {//prevent breaking code with missing "'" character. if odd number of quotes add another
                    x += "'";
                }
            }
        }
        x += ">";
        return x;
    }

    public static String at(String name, String value) {//attribute
        return name + "='" + value + "'";
    }

    public static String at2(String name, String value) {//attribute with 1 space padding
        return " " + at(name, value) + " ";
    }

    public static String wrapTags(String content, String... tag) {
        return wrapTags(content, true, tag);
    }

    public static String wrapTags(String content, boolean inward, String... tag) {
        String x = content;
        if (inward) {
            for (int c = tag.length - 1; c > -1; c--) {
                x = wrapTag(x, tag[c]);
            }
        } else {
            for (String c : tag) {
                x = "<" + c + ">" + content + "</" + c + ">";
            }
        }
        return x;
    }

    public static String wrapTag(String content, String tag, String attributes) {
        String x = "";
        x += "<" + tag;
        if (attributes.length() > 0) {
            x += " " + attributes;
            if ((attributes.length() - attributes.replace("'", "").length()) % 2 != 0) {//prevent breaking code with missing "'" character. if odd number of quotes add another
                x += "'";
            }
        }
        x += ">";
        x += content;
        x += "</" + tag + ">";
        return x;
    }

    public static String wrapTag(String content, String tag) {
        return wrapTag(content, tag, "");
    }

    //public static String wrapTag(String content){return content;}
    //public static String wrapTag(){return "";}
    public static String btnLink(String text, String url) {
        //return "<a href'" + url + "'><button>" + text + "</button></a>";
        String x = wrapTag(text, "button");
        return wrapTag(x, "a", "href='" + url + "'");
    }

    public static String tagSeriesAtt(String tag, String attributes, String... content) {
        String x = "";
        for (String c : content) {
            x += wrapTag(c, tag, attributes);
        }
        return x;
    }

    public static String tagSeries(String tag, String... content) {
        String x = "";
        for (String c : content) {
            x += wrapTag(c, tag);
        }
        return x;
    }

    public static String navBar(HttpSession session) {
        String x = "";

        String[] regPages = {"home", "doctorlist"};
        String[] patientPages ={};
        String[] docPages = {"patientlist","patientadd","visitlist"};
        String[] adminPages = {"doctoradd"};

        String[] regControl = {"Account"};
        String[] patientControl = {};
        String[] docControl = {};
        String[] adminControl = {};

        x += "<table style='width:100%;background-color:grey;'>";

        //all users
        x += "<tr><td align='left'>";
        for (String c : regPages) {
            x += btnLink(c, c);
        }
        x += "</td><td align='right'>";
        for (String c : regControl) {
            x += btnLink(c, c);
        }
        x += "</td></tr>";

        if (Vals.permission(session) > -1) {//patient
            x += "<tr><td align='left'>";
            for (String c : patientPages) {
                x += btnLink(c, c);
            }
            x += "</td><td align='right'>";
            for (String c : patientControl) {
                x += btnLink(c, c);
            }
            x += "</td></tr>";
        }
        
        //*
        if (Vals.permission(session) > 0) {//doc
            x += "<tr><td align='left'>";
            for (String c : docPages) {
                x += btnLink(c, c);
            }
            x += "</td><td align='right'>";
            for (String c : docControl) {
                x += btnLink(c, c);
            }
            x += "</td></tr>";
        }
        //*/

        if (Vals.permission(session) > 1) {//admin
            x += "<tr><td align='left'>";
            for (String c : adminPages) {
                x += btnLink(c, c);
            }
            x += "</td><td align='right'>";
            for (String c : adminControl) {
                x += btnLink(c, c);
            }
            x += "</td></tr>";
        }

        x += "</table>";
        return x;
    }

    public static String template(HttpSession session, String content, String title) {
        String x = "";
        x += "<!DOCTYPE html>";
        x += "<html>";
        x += "<head>";
        //x += "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>";
        x += wrapTag(title, "title");
        x += wrapTag(Style.DEFAULT + "", "style");
        x += "</head>";
        x += "<body>";

        x += navBar(session);

        content = wrapTags(content, "tr", "th");
        x += wrapTag(content, "table", "style='margin-left:auto;margin-right:auto'");

        x += "</body>";
        x += "</html>";
        //System.out.println(x);
        return x;
    }
}
