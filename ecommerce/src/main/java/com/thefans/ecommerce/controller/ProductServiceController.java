package com.thefans.ecommerce.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.Collection;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.InputStreamResource;

import com.thefans.ecommerce.model.Product;
import com.thefans.ecommerce.service.ProductService;
import com.thefans.ecommerce.exception.ProductNotFoundException;

@RestController
public class ProductServiceController {
   private static Map<String, Product> productRepo = new HashMap<String, Product>();
   static {
      Product honey = new Product();
      honey.setId("1");
      honey.setName("Honey");
      productRepo.put(honey.getId(), honey);
      
      Product almond = new Product();
      almond.setId("2");
      almond.setName("Almond");
      productRepo.put(almond.getId(), almond);
      
      Product pizza = new Product();
      pizza.setId("3");
      pizza.setName("Pizza");
      productRepo.put(pizza.getId(), pizza);
   }

   @Autowired
   ProductService ps;

   @RequestMapping(value = "/products")
   @CrossOrigin(origins = "http://localhost:8081")
   public ResponseEntity<Collection<Product>> getProduct() {
      //return new ResponseEntity<Collection<Product>>(productRepo.values(), HttpStatus.OK);
      return new ResponseEntity<Collection<Product>>(ps.getProducts(), HttpStatus.OK);
   }

   @RequestMapping(value = "/products/{id}")
   public ResponseEntity<Product> getProduct(@PathVariable("id") String id) {
      //if (!productRepo.containsKey(id))
      if (!ps.hasProduct(id))
          throw new ProductNotFoundException();
      //return new ResponseEntity<Product>(productRepo.get(id), HttpStatus.OK);
      return new ResponseEntity<Product>(ps.getProduct(id), HttpStatus.OK);
   }

   @RequestMapping(value = "/products", method = RequestMethod.POST)
   @CrossOrigin(origins = "http://localhost:8081")
   public ResponseEntity<String> createProduct(@RequestBody Product product) {
      //productRepo.put(product.getId(), product);
      ps.createProduct(product);
      return new ResponseEntity<String>("Product is created successfully", HttpStatus.CREATED);
   }

   @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
   public ResponseEntity<String> updateProduct(@PathVariable("id") String id, @RequestBody Product product) { 
      //productRepo.remove(id);
      //product.setId(id);
      //productRepo.put(id, product);
      ps.updateProduct(id, product);
      return new ResponseEntity<String>("Product is updated successsfully", HttpStatus.OK);
   }

   @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
   public ResponseEntity<String> delete(@PathVariable("id") String id) { 
      //productRepo.remove(id);
      ps.deleteProduct(id);
      return new ResponseEntity<String>("Product is deleted successsfully", HttpStatus.OK);
   }

   // below code demonstrate how to consume RESTful APIs
   @Autowired
   RestTemplate restTemplate;

   @RequestMapping(value = "/consume/products")
   public ResponseEntity<Object> consumeGetProduct() {
      HttpHeaders headers = new HttpHeaders();
      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
      HttpEntity<Object> entity = new HttpEntity<Object>(headers);
      try {
         return restTemplate.exchange(
            "http://localhost:8080/products", 
            HttpMethod.GET,
            entity,
            Object.class
         );
      } catch (RestClientException e) {
         return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
      }
      //return new ResponseEntity<Object>(productRepo.values(), HttpStatus.OK);
   }

   @RequestMapping(value = "/consume/products/{id}")
   public ResponseEntity<Object> consumeGetProduct(@PathVariable("id") String id) {
      HttpHeaders headers = new HttpHeaders();
      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
      HttpEntity<Object> entity = new HttpEntity<Object>(headers);
      try {
         return restTemplate.exchange(
            "http://localhost:8080/products/" + id, 
            HttpMethod.GET,
            entity,
            Object.class
         );
      } catch (RestClientException e) {
         return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
      }

      //if (!productRepo.containsKey(id))
      //    throw new ProductNotFoundException();
      //return new ResponseEntity<Object>(productRepo.get(id), HttpStatus.OK);
   }

   @RequestMapping(value = "/consume/products", method = RequestMethod.POST)
   public ResponseEntity<String> consumeCreateProduct(@RequestBody Product product) {
      HttpHeaders headers = new HttpHeaders();
      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
      HttpEntity<Product> entity = new HttpEntity<Product>(product, headers);
      
      try {
         return restTemplate.exchange(
            "http://localhost:8080/products",
            HttpMethod.POST,
            entity,
            String.class
         );
      } catch (RestClientException e) {
         e.printStackTrace();
         return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
      }
      //productRepo.put(product.getId(), product);
      //return new ResponseEntity<String>("Product is created successfully", HttpStatus.CREATED);
   }

   @RequestMapping(value = "/consume/products/{id}", method = RequestMethod.PUT)
   public ResponseEntity<String> consumeUpdateProduct(@PathVariable("id") String id, @RequestBody Product product) { 
      HttpHeaders headers = new HttpHeaders();
      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
      HttpEntity<Product> entity = new HttpEntity<Product>(product, headers);
      
      try {
         return restTemplate.exchange(
            "http://localhost:8080/products/" + id,
            HttpMethod.PUT,
            entity,
            String.class
         );
      } catch (RestClientException e) {
         return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
      }
      //productRepo.remove(id);
      //product.setId(id);
      //productRepo.put(id, product);
      //return new ResponseEntity<String>("Product is updated successsfully", HttpStatus.OK);
   }

   @RequestMapping(value = "/consume/products/{id}", method = RequestMethod.DELETE)
   public ResponseEntity<String> consumeDelete(@PathVariable("id") String id) { 
      HttpHeaders headers = new HttpHeaders();
      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
      HttpEntity<Object> entity = new HttpEntity<Object>(headers);
      
      try {
         return restTemplate.exchange(
            "http://localhost:8080/products/" + id,
            HttpMethod.DELETE,
            entity,
            String.class
         );
      } catch (RestClientException e) {
         return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
      }
      //productRepo.remove(id);
      //return new ResponseEntity<String>("Product is deleted successsfully", HttpStatus.OK);
   }

   @RequestMapping(value = "/upload", method = RequestMethod.POST, 
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   public String fileUpload(@RequestParam("file") MultipartFile file) throws IOException {
      File convertFile = new File(file.getOriginalFilename());
      System.out.println("upload path=" + convertFile.getAbsolutePath());
      convertFile.createNewFile();
      FileOutputStream fout = new FileOutputStream(convertFile);
      fout.write(file.getBytes());
      fout.close();
      return "File is upload successfully";
   }

   @RequestMapping(value = "/download", method = RequestMethod.GET) 
   public ResponseEntity<Object> downloadFile() throws IOException {
      String filename = "springboot.png";
      File file = new File(filename);
      System.out.println("download path=" + file.getAbsolutePath());
      InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
      HttpHeaders headers = new HttpHeaders();
      
      headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
      headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
      headers.add("Pragma", "no-cache");
      headers.add("Expires", "0");
      
      ResponseEntity<Object> 
      responseEntity = ResponseEntity.ok().headers(headers).contentLength(
         file.length()).contentType(MediaType.parseMediaType("application/txt")).body(resource);
      
      return responseEntity;
   }

}
