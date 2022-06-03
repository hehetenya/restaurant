package com.epam.koval.restaurant.tags;

import com.epam.koval.restaurant.database.entity.Dish;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Map;

public class CountTotalTag extends TagSupport {

    private static final Logger log = LogManager.getLogger(CountTotalTag.class);

    private Map<Dish, Integer> cart;

    private int total;

    public void setCart(Map<Dish, Integer> cart) {
        log.debug("cart ==>" + cart);
        this.cart = cart;
    }

    @Override
    public int doStartTag() throws JspException {
        total = 0;
        for (Dish dish:
                cart.keySet()) {
            total += dish.getPrice()*cart.get(dish);
        }
        try {
            pageContext.getOut().print(total);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }
}
