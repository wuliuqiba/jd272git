package cn.itcast.pojo;

import java.util.List;

public class PageBean {

	//��ǰҳ
	private Integer currentPage;
	//��ҳ��
	private Integer totalPage;
	//�ܼ�¼��
	private Integer totalRecord;
	//�ܼ�¼
	private List<Product> product;
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(Integer totalRecord) {
		this.totalRecord = totalRecord;
	}
	public List<Product> getProduct() {
		return product;
	}
	public void setProduct(List<Product> product) {
		this.product = product;
	}
}
