package com.gstore.api.product.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gstore.api.product.model.ProductCart;
import com.gstore.api.product.model.ProductList;
import com.gstore.api.product.repository.ProductRepository;
import com.gstore.api.product.util.JwtUtil;

@RestController
@RequestMapping(path="/product") 
public class ProductController {
	
	@Autowired
	ProductRepository prodRep;
	
	@Autowired 
	JwtUtil jwtUtils;
	
	@RequestMapping(method = RequestMethod.GET)
	public Iterable<ProductList> getallproducts(HttpServletRequest request){
		System.out.println("header >>"+request.getHeader("authorization"));
		return prodRep.findAll();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getproductsById(@PathVariable int id,HttpServletRequest request){
		System.out.println(""+id);
		
		//return "y";
		String value ="N";
		Optional<ProductList>  data = prodRep.findById(id);
		
		System.out.println("datadata>>"+data.empty());
		if(data.empty() != null )
		{
			value ="Y";
		}
		return value;
		
	}


	
	
}
