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

	//springmvc�����ļ����Ѿ�����solrServer����
	@Autowired
	private SolrServer solrServer;
	
	public PageBean queryProductWithCondition(SolrQuery solrQuery) {
		//����һ����ҳ�������ڴ洢��ҳ��Ϣ
		PageBean pageBean = new PageBean();
		//����һ��product���͵�list�������ڷ�װ�Ӳ�ѯ�����ѯ����product����
		List<Product> pList = new ArrayList<>();
		
		try {
			//����solrserver���в�ѯ
			QueryResponse response = solrServer.query(solrQuery);
			//ͨ��getresult��ȡ����ѯ�����ݼ���
			SolrDocumentList results = response.getResults();
			//��ȡ�ĵ����ܼ�¼��
			Long numFound = results.getNumFound();
			//�����ĵ���¼���洢����ҳ��Ϣ��
			pageBean.setTotalRecord(numFound.intValue());
			
			//ѭ����ѯ���Ľ�������е����Ը�ֵҪ�õ��ֶ�
			for (SolrDocument docs : results) {
				//����һ��product����
				Product p = new Product();
				
				String id = (String) docs.get("id");
				p.setPid(id);
				
				String name = (String) docs.get("produc_name");
				//��ȡ����������
				Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
				Map<String, List<String>> map = highlighting.get(id);
				List<String> list = map.get("product_name");
				//�жϻ�ȡ�����Ƿ������ݽ��и�������
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
