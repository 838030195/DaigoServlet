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
import com.Util.UInfo;
import com.Util.VerificationInfo;
import com.alibaba.fastjson.JSON;

/**
 * Servlet implementation class GetUserInfo
 */
@WebServlet("/GetUserInfo")
public class GetUserInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUserInfo() {
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
		String phoneNum = request.getParameter("phonenum");
		response.setContentType("text/html; charset=UTF-8");  
        response.setCharacterEncoding("UTF-8"); 
        PrintWriter out = response.getWriter();
        try {
        	Connection con = DBUtil.getConnection();
        	
        	
        		Statement st = con.createStatement();
        		ResultSet rs = st.executeQuery("select * from daigo.user where phonenum=" + phoneNum);
        		if (rs.next()) {
        			out.print("[");
        			out.print(JSON.toJSONString(uinfo(rs)));
        			out.print("]");
        		}
        		
        		st.close();
        		rs.close();
        	
        	
        } catch(Exception e) {
        	e.printStackTrace();
        } finally {
        	DBUtil.Close();
        	
        	out.flush();
        	out.close();
        }
        
	}
	
	
	private UInfo uinfo(ResultSet rs) {
		UInfo u = new UInfo();
		try {
			u.setIdCode(rs.getString("idcode"));
			u.setRealName(rs.getString("realname"));
			u.setNickName(rs.getString("nickname"));
			u.setPhoneNum(rs.getString("phonenum"));
			u.setUserId(rs.getString("userid"));
			u.setSchoolCode(rs.getString("campusidcode"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return u;
		
	}

}
