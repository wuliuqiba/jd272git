package cn.itcast.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.dao.ProductDao;
import cn.itcast.pojo.PageBean;
import cn.itcast.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductDao productDao;

	public PageBean queryProductWithCondition(String queryString, String catalog_name, String price, Integer page,
			Integer rows, String sort) {
		//创建一个solrquery对象用于封装条件
		SolrQuery solrQuery = new SolrQuery();
		//进行搜索框输入条件的判断
		if (queryString != null && !"".equals(queryString)) {
			solrQuery.setQuery(queryString);
		} else {
			solrQuery.setQuery("*:*");
		}
		
		//进行分类过滤查询
		if (catalog_name != null && !"".equals(catalog_name)) {
			solrQuery.addFilterQuery("product_catalog_name" + catalog_name);
		}
		
		//进行价格过滤
		if (price != null && !"".equals(price)) {
			String[] prices = price.split("-");
			//进行条件拼接
			solrQuery.addFilterQuery("product_price:["+ prices[0] +" TO " + prices[1]+"]");
		}
		
		//进行分页
		//计算页数
		int pageT = (page - 1) * rows;
		solrQuery.setStart(pageT);
		solrQuery.setRows(rows);
		
		//进行升降序
		if ("1".equals(sort)) {
			solrQuery.setSort("product_price", ORDER.asc);
		} else {
			solrQuery.setSort("product_price", ORDER.desc);
		}
		
		//设置默认域
		solrQuery.set("df", "product_keywords");
		
		//进行高亮设置
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("product_name");
		solrQuery.setHighlightSimplePre("<font color = 'red'>");
		solrQuery.setHighlightSimplePost("</font>");
		
		//调用dao进行查询
		PageBean result = productDao.queryProductWithCondition(solrQuery);
		
		//设置当前页
		result.setCurrentPage(page);
		//计算总页码
		Integer count = result.getTotalRecord();
		int pages = count/rows;
		if (count%rows > 0) {
			pages ++;
		}
		result.setTotalPage(pages);
		
		return result;
	}

}
