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

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
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
        String password = request.getParameter("password");
        String headIcon = request.getParameter("headicon");
        boolean type=false;//用于判断账号和密码是否与数据库中查询结果一致 
        String uId = "";
        response.setContentType("text/html; charset=UTF-8");  
        response.setCharacterEncoding("UTF-8");  
        PrintWriter out = response.getWriter();  
        try  
        {  
            Connection con=DBUtil.getConnection();  
            
        	
        	Statement stmt=con.createStatement();
        	
        	ResultSet checkExist = stmt.executeQuery("select * from daigo.user where phonenum=\'" + phoneNum + "\'");
            
        	//若未被注册
        	if (!checkExist.next()) {
        		type = true;
                 
        		PreparedStatement pstate = con.prepareStatement("INSERT INTO user (discode, phonenum, password, verify,"
        				+ "campuscode, headicon) VALUES(?,?,?,?,?,?)");
                pstate.setString(1, "86");
            	pstate.setString(2, phoneNum);
            	pstate.setString(3, password);
            	pstate.setInt(4, 0);
            	pstate.setInt(5, 0);
            	pstate.setString(6, headIcon);
            	
                int code = pstate.executeUpdate();
            	pstate.close();
        		
            	if (code > 0) {
            		 String sql="select * from daigo.user where phonenum="+phoneNum;
                     ResultSet rs=stmt.executeQuery(sql);  
                     rs.first();
             		uId = rs.getString("userid");
             		PreparedStatement pstate1 = con.prepareStatement("CREATE TABLE `user_" + uId + "`("
             				+ "`index` int NOT NULL AUTO_INCREMENT,"
             				+ "`orderid` text,"
             				+ "`type` int(1) NULL DEFAULT 0,"
             				+ "`addressed` int(1) NULL DEFAULT 0,"
             				+ "PRIMARY KEY (`index`)"
             				+ ")");
             		pstate1.executeUpdate();
             		pstate1 = con.prepareStatement("update daigo.user set nickname=? where userid=?");
             		pstate1.setString(1, "uid" + uId);
             		pstate1.setString(2, uId);
             		pstate1.executeUpdate();
             		pstate1.close();
            	}
            	
               
        	}
        }  
        catch(Exception ex)  
        {  
            ex.printStackTrace();  
        }  
        finally  
        {  
            DBUtil.Close();  
            if (type == true) {
            	out.print(uId);
            } else {
            	out.print(type);  
            }
            
            out.flush();  
            out.close();  
        }
	}

}
