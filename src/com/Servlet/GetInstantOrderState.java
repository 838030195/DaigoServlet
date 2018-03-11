package com.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import com.Util.InstantOrderInfo;
import com.alibaba.fastjson.JSON;

/**
 * Servlet implementation class GetInstantOrderState
 */
@WebServlet("/GetInstantOrderState")
public class GetInstantOrderState extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetInstantOrderState() {
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
        
        String userId = request.getParameter("userid");
        try {  
            Connection con=DBUtil.getConnection();  
            Statement stmt=con.createStatement();  
            String sql="select * from daigo.order where senderid=\'"+ userId + "\' and "
            		+ "addressed=1";  
            ResultSet rs=stmt.executeQuery(sql);
            
            PreparedStatement ps = con.prepareStatement("update `order` set addressed=0 where "
            		+ "senderid=?");
            ps.setString(1, userId);
            ps.executeUpdate();
            ps.close();
            
            List<String> info = new ArrayList<>();
            
            while (rs.next()) {
            	info.add(JSON.toJSONString(orderInfo(rs)));
            }
            
            printRes(info, out);
            
            rs.close();
            con.close();
            stmt.close();
        
        }  
        catch(Exception ex) {  
            ex.printStackTrace();  
        }  
        finally {  
            DBUtil.Close();  
            
            out.flush();  
            out.close();  
        }
	}
	
	private void printRes(List<String> list, PrintWriter out) {
		if (list.size() == 0) {
			out.print("false");
		} else {
			out.print("[");
			for (int i = 0; i < list.size() - 1; i++) {
				out.print(list.get(i));
				out.print(",");
			}
			out.print(list.get(list.size() - 1));
			out.print("]");
		}
	}
	
	private InstantOrderInfo orderInfo(ResultSet rs) {
		InstantOrderInfo o = new InstantOrderInfo();
		try {
			o.setTitle(rs.getString("title"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}

}
