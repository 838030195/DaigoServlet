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
import com.Util.Order;
import com.Util.User;
import com.alibaba.fastjson.JSON;

/**
 * Servlet implementation class OrderDetail
 */
@WebServlet("/OrderDetail")
public class OrderDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderDetail() {
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
		
		String orderId = request.getParameter("orderid");
		
		
		boolean type=false;
        
        response.setContentType("text/html; charset=UTF-8");  
        response.setCharacterEncoding("UTF-8");  
        PrintWriter out = response.getWriter();  
        
        String orderDetail = "";
        
        try  
        {  
            Connection con=DBUtil.getConnection();  
            Statement stmt=con.createStatement();  
            String sql="select * from daigo.order where orderid=\'"+orderId+"\'";  
            ResultSet rs=stmt.executeQuery(sql);  
        	
            while(rs.next())  
            {  
            	type = true;
            	orderDetail = JSON.toJSONString(orderDAO(rs, con));
            	out.print(orderDetail);
            }
        }  
        catch(Exception ex)  
        {  
            ex.printStackTrace();  
        }  
        finally  
        {  
            DBUtil.Close();  
            if (!type) {
            	out.print(type);
            }
            out.flush();  
            out.close();  
        }
	}
	
	private Order orderDAO(ResultSet rs, Connection conn) {
		Order order = new Order();
		User sender = new User();
		User receiver = new User();
		try {
			sender.setUserId(rs.getString("senderid"));
			receiver.setUserId(rs.getString("receiverid"));
			/**
			 * 获取用户信息
			 */
			Statement s = conn.createStatement();
			ResultSet senderRs = s.executeQuery("select * from daigo.user where"
					+ " userid=\'" + sender.getUserId() + "\'");
			if (senderRs.next()) {
				sender.setNickName(senderRs.getString("nickname"));
				sender.setHeadIcon(senderRs.getString("headicon"));
				sender.setPhoneNum(senderRs.getString("phonenum"));
			}
			
			
			ResultSet receiverRs = s.executeQuery("select * from daigo.user where"
					+ " userid=\'" + receiver.getUserId() + "\'");
			if (receiverRs.next()) {
				receiver.setNickName(receiverRs.getString("nickname"));
				receiver.setHeadIcon(receiverRs.getString("headicon"));
				receiver.setPhoneNum(receiverRs.getString("phonenum"));
			}
				
			
			//////////////////////////////////////////////
			order.setSender(sender);
			order.setReceiver(receiver);
			order.setTitle(rs.getString("title"));
			order.setState(rs.getInt("state"));
			order.setRequestTime(rs.getString("requesttime"));
			order.setPublicContent(rs.getString("publiccontent"));
			order.setPrivateContent(rs.getString("privatecontent"));
			order.setContact(rs.getString("contact"));
			order.setPrice(rs.getString("price"));
			order.setOrderId(rs.getString("orderid"));
			order.setAcceptTime(rs.getString("accepttime"));
			order.setCompleteTime(rs.getString("completetime"));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return order;
	}

}
