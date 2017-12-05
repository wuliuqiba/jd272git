package cn.itcast.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.itcast.pojo.PageBean;
import cn.itcast.service.ProductService;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@RequestMapping("/list")
	public String getIndex(String queryString, 
			String catalog_name, 
			String price, 
			@RequestParam(defaultValue="1")Integer page,
			@RequestParam(defaultValue="50")Integer rows, 
			@RequestParam(defaultValue="1")String sort, 
			Model model) {
		PageBean result = productService.queryProductWithCondition(queryString, catalog_name, price, page, rows, sort);
		
		model.addAttribute("queryString", queryString);
		model.addAttribute("catalog_name", catalog_name);
		model.addAttribute("price", price);
		model.addAttribute("result", result);
		model.addAttribute("sort", sort);
		
		return "product_list";
	}
}
