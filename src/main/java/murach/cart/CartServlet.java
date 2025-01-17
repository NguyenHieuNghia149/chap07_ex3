package murach.cart;

import java.io.*;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import murach.business.*;
import murach.data.*;
import murach.business.*;

import javax.sound.sampled.Line;

public class CartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        ServletContext sc = getServletContext();

        // get current action
        String action = request.getParameter("action");
        if (action == null) {
            action = "cart"; // default action
        }

        // perform action and set URL to appropriate page
        String url = "/index.html";
        if (action.equals("shop")) {
            url = "/index.html"; // the "index" page
        } else if (action.equals("cart")) {
            String productCode = request.getParameter("productCode");
            String quantityString = request.getParameter("quantity");
            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("cart");

            if (cart == null) {
                cart = new Cart();
            }

            // If the user enters a negative or invalid quantity, set to 1
            int quantity;
            try {
                quantity = Integer.parseInt(quantityString);
                if (quantity < 0) {
                    quantity = 1;
                }
            } catch (NumberFormatException nfe) {
                quantity = 1;
            }

            String path = sc.getRealPath("/WEB-INF/products.txt");
            Product product = ProductIO.getProduct(productCode, path);

            LineItem lineItem = new LineItem();
            lineItem.setProduct(product);
            lineItem.setQuantity(quantity);

            if (quantity > 0) {
                cart.addItem(lineItem);
            } else if (quantity == 0) {
                cart.removeItem(lineItem);
            }

            session.setAttribute("cart", cart);
            url = "/cart.jsp";
        }else if (action.equals("remove")) {
            // Handle remove action
            HttpSession session = request.getSession();
            String productCode = request.getParameter("productCode");
            Cart cart = (Cart) session.getAttribute("cart");
            // Remove the product from the cart
            cart.removeOneItem(productCode);  // Remove item based on productCode
            session.setAttribute("cart", cart);

            url = "/cart.jsp";

        } else if (action.equals("checkout")) {
            url = "/checkout.jsp";
        }
        sc.getRequestDispatcher(url).forward(request, response);
    }


    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
