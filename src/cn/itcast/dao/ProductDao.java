package cn.itcast.dao;

import org.apache.solr.client.solrj.SolrQuery;

import cn.itcast.pojo.PageBean;

public interface ProductDao {
	
	public PageBean queryProductWithCondition(SolrQuery solrQuery);

}
