package jsp.controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import jsp.domain.URL;
import jsp.model.DBManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class URLController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ((action != null) && action.equals("insert")) {
            URL url = new URL(Integer.parseInt(request.getParameter("id")),
                    Integer.parseInt(request.getParameter("userid")),
                    request.getParameter("link"),
                    Integer.parseInt(request.getParameter("accessed")));
            DBManager dbManager = new DBManager();
            Boolean result = dbManager.insertURL(url);
            PrintWriter out = new PrintWriter(response.getOutputStream());
            if (result == true) {
                out.println("Inserted successfully");
            } else {
                out.println("Error inserting");
            }
            out.flush();
        } else if ((action != null) && action.equals("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            DBManager dbManager = new DBManager();
            Boolean result = dbManager.deleteURL(id);
            PrintWriter out = new PrintWriter(response.getOutputStream());
            if (result == true) {
                out.println("Deleted successfully");
            } else {
                out.println("Error deleting");
            }
            out.flush();
        } else if ((action != null) && action.equals("getTopN")) {
            int n = Integer.parseInt(request.getParameter("number"));
            response.setContentType("application/json");
            DBManager dbManager = new DBManager();
            ArrayList<URL> urls = dbManager.getTopURLs(n);
            JSONArray jsonURLs = new JSONArray();
            for (int i = 0; i < urls.size(); i++) {
                JSONObject jObj = new JSONObject();
                jObj.put("id", urls.get(i).getId());
                jObj.put("userid", urls.get(i).getUserid());
                jObj.put("link", urls.get(i).getLink());
                jObj.put("accessed", urls.get(i).getAccessed());
                jsonURLs.add(jObj);
            }
            PrintWriter out = new PrintWriter(response.getOutputStream());
            out.println(jsonURLs.toJSONString());
            out.flush();
        } else if ((action != null) && action.equals("getAll")) {
            int userid = Integer.parseInt(request.getParameter("userid"));
            response.setContentType("application/json");
            DBManager dbManager = new DBManager();
            ArrayList<URL> urls = dbManager.getUserURLs(userid);
            JSONArray jsonURLs = new JSONArray();
            for (int i = 0; i < urls.size(); i++) {
                JSONObject jObj = new JSONObject();
                jObj.put("id", urls.get(i).getId());
                jObj.put("userid", urls.get(i).getUserid());
                jObj.put("link", urls.get(i).getLink());
                jObj.put("accessed", urls.get(i).getAccessed());
                jsonURLs.add(jObj);
            }
            PrintWriter out = new PrintWriter(response.getOutputStream());
            out.println(jsonURLs.toJSONString());
            out.flush();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
