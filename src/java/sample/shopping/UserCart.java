/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.shopping;

/**
 *
 * @author Lc_Tn
 */
public class UserCart {
    private String productImage;
    private String productId;
    private String productName;
    private double productPrice;
    private int cartQuantity;
    private double total;
    private int orderID;
    
    public UserCart() {
    }

    public UserCart(String productImage, String productId, String productName, double productPrice, int cartQuantity, double total, int orderID) {
        this.productImage = productImage;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.cartQuantity = cartQuantity;
        this.total = total;
        this.orderID = orderID;
    }

    

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(int cartQantity) {
        this.cartQuantity = cartQantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    
   
    
}
