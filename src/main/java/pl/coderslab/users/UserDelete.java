package pl.coderslab.users;

import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/delete")
public class UserDelete extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if("Tak".equals(request.getParameter("choice"))){
            int id = Integer.parseInt(request.getParameter("id"));
            new UserDao().delete(id);
        }

        response.sendRedirect(request.getContextPath()+"/user/list");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        UserDao userDao = new UserDao();
        request.setAttribute("user",userDao.read(id));
        getServletContext()
                .getRequestDispatcher(request.getContextPath()+"/user/delete.jsp")
                .forward(request,response);
    }
}
