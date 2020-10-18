/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

/**
 *
 * @author artour
 */
public class Style {
    public static final Style DEFAULT=new Style();
    
    public String other="";
    
    public static String TABLE=""
            + "td,th,tr,table{"
            //+ "border:1px solid black;"
            + "margin:5px;"
            + "padding:5px;"
            + "margin-left:10px;margin-right:10px;"
            + "padding-left:10px;padding-right:10px;"
            + "};"
            
            + "td * {"
            + "font-weight:normal;"
            + "};"
            + "";
    
    public boolean table=true;
    
    public void clear(){
        other="";
        table=false;
    }
    
    @Override
    public String toString(){
        String x="";
        x+=other;
        if(table){
            x+=TABLE;
        }
        return x;
    }
}
