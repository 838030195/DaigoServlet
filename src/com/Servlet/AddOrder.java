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

/**
 * Servlet implementation class AddOrder
 */
@WebServlet("/AddOrder")
public class AddOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddOrder() {
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
		String orderId = request.getParameter("orderid"); //用于接收前段输入的ID值，此处参数须和input控件的name值一致  
        String senderId = request.getParameter("senderid");//用于接收前段输入的PW值，此处参数须和input控件的name值一致  
        int campusId = Integer.parseInt(request.getParameter("campusid"));
        
        String title = request.getParameter("title");
        String requestTime = request.getParameter("requesttime");
        String publicContent = request.getParameter("publiccontent");
        String privateContent = request.getParameter("privatecontent");
        String contact = request.getParameter("contact");
        String price = request.getParameter("price");
        
        boolean type=false;
        
        response.setContentType("text/html; charset=UTF-8");  
        response.setCharacterEncoding("UTF-8");  
        PrintWriter out = response.getWriter();  
        try  
        {  
            Connection con=DBUtil.getConnection();  
            
        	
        	Statement stmt=con.createStatement();

        		PreparedStatement pstate = con.prepareStatement("insert into `order` (orderid, senderid, state,"
        				+ " title, requesttime, publiccontent, privatecontent,"
        				+ " contact, price, campusid) VALUES(?,?,?,?,?,?,?,?,?,?)");
                pstate.setString(1, orderId);
            	pstate.setInt(2, Integer.parseInt(senderId));
            	pstate.setInt(3, Order.NORMAL);
            	pstate.setString(4, title);
            	pstate.setString(5, requestTime);
            	pstate.setString(6, publicContent);
            	pstate.setString(7, privateContent);
            	pstate.setString(8, contact);
            	pstate.setString(9, price);
            	pstate.setInt(10, campusId);
            	
                int code = pstate.executeUpdate();
            	pstate.close();
        		
            	if (code > 0) {
            		type = true;
            		//更新校区最新订单信息
            		 String sql="update orderids set orderid=? where campusid=?";
                     
                     PreparedStatement ps = con.prepareStatement(sql);
                     ps.setString(1, orderId);
                     ps.setInt(2, campusId);
             		 ps.executeUpdate();
             		 ps.close();
             		 
//             		PreparedStatement pstate1 = con.prepareStatement("insert into user_" + senderId + " ("
//             				+ "orderid) VALUES(?)");
//             		pstate1.setString(1, orderId);
//             		pstate1.executeUpdate();
//             		pstate1.close();
            	}
            	
               con.close();
        	
        }  
        catch(Exception ex)  
        {  
            ex.printStackTrace();  
        }  
        finally  
        {  
            DBUtil.Close();  
            out.print(type);
            
            out.flush();  
            out.close();  
        }
	}

}
