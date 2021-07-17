package com.thefans.ecommerce;

import org.springframework.util.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.thefans.ecommerce.controller.ProductServiceController;
import com.thefans.ecommerce.model.Product;

public class ProductServiceControllerTest extends AbstractTest {
    @Override // this will clear @BeforeEach on super class's setUp()
    @BeforeEach // use @Before doesn't work
    public void setUp() {
        super.setUp();
    }

    @Autowired
    private ProductServiceController controller;

    @Test
    public void contextLoads() throws Exception {
        Assert.notNull(controller, "ProductServiceController is not autowired.");
    }

    @Test
    public void getOneProduct() throws Exception {
        String uri = "/products/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.isTrue(200 == status, "Status isn't 200!");
        String content = mvcResult.getResponse().getContentAsString();
        Product product = super.mapFromJson(content, Product.class);
        Assert.isTrue(product != null, "No product is found!");
        Assert.isTrue("1".equals(product.getId()), "Product id isn't 1!");
        Assert.isTrue("Honey".equals(product.getName()), "Product name isn't Honey!");
    }

    @Test
    public void getProductsList() throws Exception {
        String uri = "/products";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.isTrue(200 == status, "Status isn't 200!");
        String content = mvcResult.getResponse().getContentAsString();
        Product[] productlist = super.mapFromJson(content, Product[].class);
        Assert.isTrue(productlist.length == 3, "Number of product isn't 3!");
    }

    @Test
    public void createProduct() throws Exception {
        String uri = "/products";
        Product product = new Product();
        product.setId("4");
        product.setName("Ginger");
        String inputJson = super.mapToJson(product);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.isTrue(201 == status, "Status isn't 201!");
        String content = mvcResult.getResponse().getContentAsString();
        Assert.isTrue("Product is created successfully".equals(content), "Returned content doesn't match!");
    }
    @Test

    public void updateProduct() throws Exception {
        String uri = "/products/2";
        Product product = new Product();
        product.setName("Lemon");
        String inputJson = super.mapToJson(product);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.isTrue(200 == status, "Status isn't 200!");
        String content = mvcResult.getResponse().getContentAsString();
        Assert.isTrue("Product is updated successfully".equals(content), "Returned content doesn't match!");
    }

    @Test
    public void deleteProduct() throws Exception {
        String uri = "/products/2";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.isTrue(200 == status, "Status isn't 200!");
        String content = mvcResult.getResponse().getContentAsString();
        Assert.isTrue("Product is deleted successfully".equals(content), "Returned content doesn't match!");
    }
}