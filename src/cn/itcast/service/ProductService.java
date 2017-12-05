package cn.itcast.service;

import cn.itcast.pojo.PageBean;

public interface ProductService {

	public PageBean queryProductWithCondition(String queryString, 
			String catalog_name, 
			String price, 
			Integer page, 
			Integer rows, 
			String sort);
}
