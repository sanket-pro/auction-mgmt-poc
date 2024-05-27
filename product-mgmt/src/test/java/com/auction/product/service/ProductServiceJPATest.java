package com.auction.product.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.web.client.RestTemplate;

import com.auction.product.constant.Availability;
import com.auction.product.dao.entity.ProductEntity;
import com.auction.product.dao.repo.ProductRepo;
import com.auction.product.exception.ProductMgmtException;
import com.auction.product.model.ProductDetails;
import com.auction.product.model.Response;

@DataJpaTest(properties = {"spring.datasource.url=jdbc:h2:mem:mydb",
"spring.jpa.hibernate.ddl-auto=create-drop"})
public class ProductServiceJPATest {
	
	@SpyBean
	private ProductService prodService;
	
	@SpyBean
	RestTemplate restTemplate;
	
	@Autowired
	private ProductRepo prodRepo;
	
	private ProductEntity product1 = new ProductEntity();
	private Integer pid1;
	private ProductEntity product2 = new ProductEntity();
	private Integer pid2;
	
	@BeforeEach
	public void addDataInProductTable() {
		
		product1.setPname("laptop");
		product1.setPrice(500);
		product1.setSellerId(1);
		product1.setAvailability(Availability.YES.toString());
		pid1 = prodRepo.save(product1).getPid();
		
		
		product2.setPname("cellphone");
		product2.setPrice(1000);
		product2.setSellerId(2);
		product2.setAvailability(Availability.YES.toString());
		pid2 = prodRepo.save(product2).getPid();
	}
	
	@AfterEach
	public void deleteDateaInProductTable(){
		prodRepo.deleteAll();
		prodRepo.flush();
	}
	
	@Test
	public void testfetchProduct() throws ProductMgmtException {
		
		Response resp = prodService.fetchProduct(pid1);
		ProductDetails prodDetail = (ProductDetails)resp.getData();
		
		assertEquals("Product found!", resp.getMessage());
		assertEquals(prodDetail.getPid(), pid1);
		assertEquals(prodDetail.getProductName(), product1.getPname());
		assertEquals(prodDetail.getPrice(), product1.getPrice());
		assertEquals(prodDetail.getAvailability(), product1.getAvailability());
	}
	
	@Test
	public void testfetchAllProduct() throws ProductMgmtException {
		
		Response resp = prodService.fetchAllProduct();
		List<ProductEntity>  resplist = (List<ProductEntity>) resp.getData();
		
		assertEquals("Available Products", resp.getMessage());
		assertNotNull(resplist);
		assertEquals(2, resplist.size());
		assertTrue(resplist.contains(product1));
		assertTrue(resplist.contains(product2));
		
	}

}
