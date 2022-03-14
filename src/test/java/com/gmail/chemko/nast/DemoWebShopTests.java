package com.gmail.chemko.nast;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class DemoWebShopTests {

    @Test //Проверка добавления товара в Wishlist неавторизованным пользователем
    void addToWishlistAsNewUserTest() {

        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .body("addtocart_14.EnteredQuantity=1")
                .when()
                .post("http://demowebshop.tricentis.com/addproducttocart/details/14/2")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your " +
                        "<a href=\"/wishlist\">wishlist</a>"))
                .body("updatetopwishlistsectionhtml", is("(1)"));
    }

    @Test //Проверка добавления товара в корзину с куки
    void addToShoppingCartWithCookieTest() {
        Integer shoppingCartSize = 16;

        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie("Nop.customer=96727285-2ec3-4f36-a24d-96d4caf1529b;")
                .body("addtocart_14.EnteredQuantity=1")
                .when()
                .post("http://demowebshop.tricentis.com/addproducttocart/details/14/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your " +
                        "<a href=\"/cart\">shopping cart</a>"))
                .body("updatetopcartsectionhtml", is("(" + shoppingCartSize + ")"));
    }
}
