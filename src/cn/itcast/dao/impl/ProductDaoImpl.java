package cn.itcast.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.itcast.dao.ProductDao;
import cn.itcast.pojo.PageBean;
import cn.itcast.pojo.Product;

@Repository
public class ProductDaoImpl implements ProductDao {

	//springmvc配置文件中已经配置solrServer对象
	@Autowired
	private SolrServer solrServer;
	
	public PageBean queryProductWithCondition(SolrQuery solrQuery) {
		//创建一个分页对象用于存储分页信息
		PageBean pageBean = new PageBean();
		//创建一个product类型的list集合用于封装从查询结果查询到的product数据
		List<Product> pList = new ArrayList<>();
		
		try {
			//调用solrserver进行查询
			QueryResponse response = solrServer.query(solrQuery);
			//通过getresult获取到查询的数据集合
			SolrDocumentList results = response.getResults();
			//获取文档的总记录数
			Long numFound = results.getNumFound();
			//将总文档记录数存储到分页信息中
			pageBean.setTotalRecord(numFound.intValue());
			
			//循环查询到的结果将其中的属性赋值要用的字段
			for (SolrDocument docs : results) {
				//创建一个product对象
				Product p = new Product();
				
				String id = (String) docs.get("id");
				p.setPid(id);
				
				String name = (String) docs.get("produc_name");
				//获取高亮的内容
				Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
				Map<String, List<String>> map = highlighting.get(id);
				List<String> list = map.get("product_name");
				//判断获取到的是否有内容进行高亮设置
				if (list != null && list.size() > 0) {
					name = list.get(0);
				}
				
				p.setName(name);
				
				float price = (float) docs.get("product_price");
				p.setPrice(price );
				
				String picture = (String) docs.get("product_picture");
				p.setPicture(picture);
				
				pList.add(p);
			}
			
			pageBean.setProduct(pList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageBean;
	}

}
