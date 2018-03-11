package com.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DBTool.DBUtil;
import com.Util.User;
import com.alibaba.fastjson.JSON;

/**
 * Servlet implementation class CheckIDPassword
 */
@WebServlet("/CheckIDPassword")
public class CheckIDPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckIDPassword() {
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
		String userId = request.getParameter("userid"); //用于接收前段输入的ID值，此处参数须和input控件的name值一致  
        String password = request.getParameter("password");//用于接收前段输入的PW值，此处参数须和input控件的name值一致  
        boolean type=false;//用于判断账号和密码是否与数据库中查询结果一致 
        String uId = "";
        response.setContentType("text/html; charset=UTF-8");  
        response.setCharacterEncoding("UTF-8");  
        PrintWriter out = response.getWriter();
        User dao = new User();
        try  
        {  
            Connection con=DBUtil.getConnection();  
            Statement stmt=con.createStatement();  
            String sql="select * from daigo.user where userid=\'"+userId+"\'"+" and password=\'"+password+"\'";  
            ResultSet rs=stmt.executeQuery(sql);  
        	
            while(rs.next())  
            {  
            	type=true;
            	dao = userDAO(rs);
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
            	String userJson = JSON.toJSONString(dao);
            	out.print(userJson.toString());
            } else {
            	out.print(type);  
            }
            
            out.flush();  
            out.close();  
        }
	}
	
	private User userDAO(ResultSet rs) {
		User user = new User();
		try {
			user.setPassword(rs.getString("password"));
			user.setNickName(rs.getString("nickname"));
			user.setCampusCode(rs.getInt("campuscode"));
			user.setCampusIdCode(rs.getString("campusidcode"));
			user.setRealName(rs.getString("realname"));
			user.setDefaultAddress(rs.getString("address"));
			user.setHeadIcon(rs.getString("headicon"));
			user.setIDCode(rs.getString("idcode"));
			user.setPhoneNum(rs.getString("phonenum"));
			user.setIsVerified(rs.getInt("verify") == 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

}
