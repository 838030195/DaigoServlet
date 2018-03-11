package com.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DBTool.DBUtil;

/**
 * Servlet implementation class CheckVerification
 */
@WebServlet("/CheckVerification")
public class CheckVerification extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckVerification() {
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
        
        String userId = request.getParameter("userid");
        
        PrintWriter out = response.getWriter();
        
        boolean result = false;
        
        try {
        	
        	Connection con = DBUtil.getConnection();
        	
        	Statement st = con.createStatement();
        	String sql = "select verify from daigo.user where userid=" + userId;
        	
        	ResultSet rs = st.executeQuery(sql);
        	
        	if (rs.next()) {
        		result = rs.getInt("verify") == 1;
        	}
        	
        	st.close();
        	con.close();
        	
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
        	DBUtil.Close();
        	out.print(result);
        	out.flush();
        	out.close();
        }
	}

}
