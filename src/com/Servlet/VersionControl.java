package com.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DBTool.DBUtil;
import com.Util.Order;
import com.alibaba.fastjson.JSON;

/**
 * Servlet implementation class VersionControl
 */
@WebServlet("/VersionControl")
public class VersionControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VersionControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		response.setContentType("text/html; charset=UTF-8");  
        response.setCharacterEncoding("UTF-8");  
        PrintWriter out = response.getWriter();
        
        String type = request.getParameter("type");
        
        try {  
            Connection con=DBUtil.getConnection();
            if (type.equals("update")) {
            	String filename = request.getParameter("filename");
            	int vercode = Integer.parseInt(request.getParameter("vercode"));
            	String logContent = request.getParameter("logcontent");
            	
            	PreparedStatement ps = con.prepareStatement("update daigo.update_info "
            			+ "set `filename`=?, `versioncode`=?, logcontent=? where `index`=0");
            	ps.setString(1, filename);
            	ps.setInt(2, vercode);
            	ps.setString(3, logContent);
            	ps.executeUpdate();
            	
            	ps.close();
            } else {
            	Statement stmt=con.createStatement();  
                String sql = "select * from daigo.update_info where `index`=0";
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()) {
                	if (type.equals("vercode")) {
                    	out.print(rs.getInt("versioncode"));
                    } else if (type.equals("filename")) {
                    	out.print(rs.getString("filename"));
                    } else if (type.equals("logcontent")) {
                    	out.print(rs.getString("logcontent"));
                    }
                }
                rs.close();
                stmt.close();
            }
            

        }  
        catch(Exception ex) {  
            ex.printStackTrace();  
        }  
        finally {  
            DBUtil.Close();
            out.flush();  
            out.close();  
        }
	}

}
