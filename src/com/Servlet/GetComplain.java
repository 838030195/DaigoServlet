package com.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DBTool.DBUtil;
import com.Util.ComplainDAO;
import com.alibaba.fastjson.JSON;
import java.sql.PreparedStatement;

/**
 * Servlet implementation class ModifyComplain
 */
@WebServlet("/GetComplain")
public class GetComplain extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetComplain() {
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
        
        int type = Integer.parseInt(request.getParameter("type"));
        Connection con = DBUtil.getConnection();
        
        if (type == 0) {
        	//获取投诉信息
        	try {
        		String orderId = request.getParameter("orderId");
				ResultSet rs = con.createStatement().executeQuery("select * from daigo.complain");
				List<ComplainDAO> list = new ArrayList<>();
				while (rs.next()) {
					if (rs.getInt("addressed") == 0) {
						list.add(comp(rs));
					}
				}
				out.print("[");
				for (int i = 0; i < list.size() - 1; i++) {
					out.print(JSON.toJSONString(list.get(i)));
					out.print(",");
				}
				out.print(JSON.toJSONString(list.get(list.size() - 1)));
				out.print("]");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } else {
        	//更新投诉信息
        	String orderId = request.getParameter("orderid");
        	PreparedStatement ps;
			try {
				ps = con.prepareStatement("update daigo.complain set "
						+ "addressed=1 where orderid=?");
				ps.setString(1, orderId);
				ps.executeUpdate();
	        	
	        	ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }
        
        DBUtil.Close();
        out.flush();
        out.close();
        
	}
	
	private ComplainDAO comp(ResultSet rs) {
		ComplainDAO c = new ComplainDAO();
		try {
			c.setContent(rs.getString("content"));
			c.setOrderId(rs.getString("orderid"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return c;
	}

}
