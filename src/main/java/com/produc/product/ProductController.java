/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.produc.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.produc.product.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpEntity;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ASUS A412
 */
@RestController
@CrossOrigin
public class ProductController {
    
    Product product = new Product();
    ProductJpaController ctrl = new ProductJpaController();
    List<Product> array = new ArrayList<>();
    
    @ResponseBody
    @RequestMapping("/del/{id}")
    public String deleteData(@PathVariable("id") int id){
        try {
            ctrl.destroy(id);
            return id + "deleted";
        }catch (NonexistentEntityException e){return "fail";}
	}
	
    @ResponseBody
    @RequestMapping("/get")
    public List<Product> getTable(){
        try {array = ctrl.findProductEntities();
        } catch (Exception e){}
        return array;
	}
	
    @ResponseBody
    @RequestMapping(value = "/post", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    
    public String postData(HttpEntity<String> data) throws Exception {
        @SuppressWarnings("UnusedAssignment")
        String message="No Action";
        @SuppressWarnings("UnusedAssignment")
        Product bundData = new Product();
        
        ObjectMapper mapper = new ObjectMapper();
        bundData = mapper.readValue(data.getBody(), Product.class);
        try {
            ctrl.create(bundData);
            message = "Data saved";
        } catch (Exception e){
            message = "Error";
        }
        return message;
	}
	
    @ResponseBody
    @RequestMapping(value = "/put", method = RequestMethod.PUT, consumes = APPLICATION_JSON_VALUE)
    
    public String putData(HttpEntity<String> data) throws Exception {
        @SuppressWarnings("UnusedAssignment")
        String message="No Action";
        @SuppressWarnings("UnusedAssignment")
        Product bundData = new Product();
        
        ObjectMapper mapper = new ObjectMapper();
        bundData = mapper.readValue(data.getBody(), Product.class);
        
        try {
            ctrl.edit(bundData);
            message = "Data saved";
        } catch (Exception e){
            message = e.toString();
        }
            return message;
    }
}
