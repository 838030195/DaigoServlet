package com.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DBTool.DBUtil;
import com.Util.VerificationInfo;
import com.alibaba.fastjson.JSON;

/**
 * Servlet implementation class GetVerification
 */
@WebServlet("/GetVerification")
public class GetVerification extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetVerification() {
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
		String userId = request.getParameter("userid");
		
		
		response.setContentType("text/html; charset=UTF-8");  
        response.setCharacterEncoding("UTF-8"); 
        PrintWriter out = response.getWriter();
        
        try {
        	Connection con = DBUtil.getConnection();
        	
        	if (userId != null && !userId.equals("")) {
        		int type = Integer.parseInt(request.getParameter("type"));
        		Statement st = con.createStatement();
        		ResultSet result = st.executeQuery("select * from daigo.verification "
        				+ "where userId=" + userId);
        		PreparedStatement ps = con.prepareStatement("delete from daigo.verification "
        				+ "where userId=" + userId);
        		//ps.setString(1, userId);
        		ps.executeUpdate();
        		
        		if (type == 1) {
        			ps = con.prepareStatement("update daigo.user set verify=?, idcode=?,"
        					+ "realname=?, campusidcode=? where userid=?");
            		ps.setInt(1, 1);
            		result.next();
            		ps.setString(2, result.getString("idcode"));
            		ps.setString(3, result.getString("realname"));
            		ps.setString(4, result.getString("schoolcode"));
            		ps.setString(5, userId);
            		ps.executeUpdate();
        		}
        	
        		ps.close();
        	} else {
        		Statement st = con.createStatement();
        		ResultSet rs = st.executeQuery("select * from daigo.verification");
        		if (rs.next()) {
        			out.print("[");
        			out.print(JSON.toJSONString(ver(rs)));
        			out.print("]");
        		}
        	}
        	
        } catch(Exception e) {
        	e.printStackTrace();
        } finally {
        	DBUtil.Close();
        	
        	out.flush();
        	out.close();
        }
        
	}
	
	
	private VerificationInfo ver(ResultSet rs) {
		VerificationInfo v = new VerificationInfo();
		try {
			v.setUserId(rs.getString("userId"));
			v.setIdCode(rs.getString("idcode"));
			v.setIddown(rs.getString("iddown"));
			v.setIdup(rs.getString("idup"));
			v.setRealName(rs.getString("realname"));
			v.setSchCard(rs.getString("schoolcard"));
			v.setSchCode(rs.getString("schoolcode"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return v;
		
	}

}
