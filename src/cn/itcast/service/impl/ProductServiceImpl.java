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
		//����һ��solrquery�������ڷ�װ����
		SolrQuery solrQuery = new SolrQuery();
		//���������������������ж�
		if (queryString != null && !"".equals(queryString)) {
			solrQuery.setQuery(queryString);
		} else {
			solrQuery.setQuery("*:*");
		}
		
		//���з�����˲�ѯ
		if (catalog_name != null && !"".equals(catalog_name)) {
			solrQuery.addFilterQuery("product_catalog_name" + catalog_name);
		}
		
		//���м۸����
		if (price != null && !"".equals(price)) {
			String[] prices = price.split("-");
			//��������ƴ��
			solrQuery.addFilterQuery("product_price:["+ prices[0] +" TO " + prices[1]+"]");
		}
		
		//���з�ҳ
		//����ҳ��
		int pageT = (page - 1) * rows;
		solrQuery.setStart(pageT);
		solrQuery.setRows(rows);
		
		//����������
		if ("1".equals(sort)) {
			solrQuery.setSort("product_price", ORDER.asc);
		} else {
			solrQuery.setSort("product_price", ORDER.desc);
		}
		
		//����Ĭ����
		solrQuery.set("df", "product_keywords");
		
		//���и�������
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("product_name");
		solrQuery.setHighlightSimplePre("<font color = 'red'>");
		solrQuery.setHighlightSimplePost("</font>");
		
		//����dao���в�ѯ
		PageBean result = productDao.queryProductWithCondition(solrQuery);
		
		//���õ�ǰҳ
		result.setCurrentPage(page);
		//������ҳ��
		Integer count = result.getTotalRecord();
		int pages = count/rows;
		if (count%rows > 0) {
			pages ++;
		}
		result.setTotalPage(pages);
		
		return result;
	}

}
