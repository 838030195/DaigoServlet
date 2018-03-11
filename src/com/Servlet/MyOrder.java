package com.Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DBTool.DBUtil;
import com.Util.Order;
import com.Util.User;
import com.alibaba.fastjson.JSON;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class MyOrder
 * 查询我的订单信息
 */
@WebServlet("/MyOrder")
public class MyOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyOrder() {
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
        
        /**
		 * type
		 * 0：（发单人）已发布订单
		 * 1：（发单人）完成
		 * 2：（接单人）接单
		 * 3：（接单人）完成
		 * 4：（发单人）取消
		 */
        int type = Integer.parseInt(request.getParameter("type"));
        String senderId = request.getParameter("senderid");
        String receiverId = request.getParameter("receiverid");

        try {
        	Connection con = DBUtil.getConnection();
        	
        	Statement st = con.createStatement();
        	ResultSet rs = null;
        	
        	List<String> myOrder = new ArrayList<>();
        	
        	if (type == 0) {
        		rs = st.executeQuery("select * from daigo.order where senderid=" + senderId
        				+ " and (state=" + Order.NORMAL + " or state="
        				+ Order.RECEIVED + ")");
        	} else if (type == 1) {
        		rs = st.executeQuery("select * from daigo.order where senderid=" + senderId
        				+ " and state=" + Order.COMPLETE);
        	} else if (type == 2) {
        		rs = st.executeQuery("select * from daigo.order where receiverid=" + receiverId
        				+ " and state=" + Order.RECEIVED);
        	} else if (type == 3) {
        		rs = st.executeQuery("select * from daigo.order where receiverid=" + receiverId
        				+ " and state=" + Order.COMPLETE);
        	} else if (type == 4) {
        		rs = st.executeQuery("select * from daigo.order where senderid=" + senderId
        				+ " and state=" + Order.INVALIDATE);
        	}
        	
        	while (rs.next()) {
    			Order JsonOrder = orderDAO(rs, con);
    			myOrder.add(JSON.toJSONString(JsonOrder));
    		}
        	
        	if (myOrder.size() > 0) {
            	out.print("[");
                for (int i = 0; i < myOrder.size() - 1; i++) {
                	out.print(myOrder.get(i));
                	out.print(",");
                }
                out.print(myOrder.get(myOrder.size() - 1));
                out.print("]");
            } else {
            	out.print("null");
            }
        	
        	rs.close();
        	st.close();
        	con.close();
        } catch(Exception e) {
        	e.printStackTrace();
        } finally {
        	DBUtil.Close();
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
		/*
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
		*/
			
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
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return order;
	}

}
