package com.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
 * Servlet implementation class OrderList
 */
@WebServlet("/OrderList")
public class OrderList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderList() {
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
		//String campusId = request.getParameter("campusid");
		String orderId = request.getParameter("orderid");
		response.setContentType("text/html; charset=UTF-8");  
        response.setCharacterEncoding("UTF-8");  
        PrintWriter out = response.getWriter();  
        
        List<String> orderList = new ArrayList<>();
        
        try  
        {  
            Connection con=DBUtil.getConnection();  
            Statement stmt=con.createStatement();  

            String sql = "select * from daigo.order where orderid=" + orderId;
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
            	Order JsonOrder = orderDAO(rs, con);
            	if (JsonOrder.getState() == Order.NORMAL) {
            		out.print("[");
            		out.print(JSON.toJSONString(JsonOrder));
            		out.print("]");
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
            out.flush();  
            out.close();  
        }
        
        
	}
	
	private Order orderDAO(ResultSet rs, Connection conn) {
		Order order = new Order();
		User sender = new User();
		
		try {
			sender.setUserId(rs.getString("senderid"));
			
			/**
			 * 获取用户信息
			 */
			Statement s = conn.createStatement();
			ResultSet senderRs = s.executeQuery("select * from daigo.user where"
					+ " userid=\'" + sender.getUserId() + "\'");
			if (senderRs.next()) {
				sender.setNickName(senderRs.getString("nickname"));
				sender.setHeadIcon(senderRs.getString("headicon"));
			}
			
			//////////////////////////////////////////////
			order.setSender(sender);
			order.setTitle(rs.getString("title"));
			order.setState(rs.getInt("state"));
			order.setRequestTime(rs.getString("requesttime"));
			order.setPublicContent(rs.getString("publiccontent"));
			order.setPrivateContent(rs.getString("privatecontent"));
			order.setContact(rs.getString("contact"));
			order.setPrice(rs.getString("price"));
			order.setOrderId(rs.getString("orderid"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return order;
	}

}
