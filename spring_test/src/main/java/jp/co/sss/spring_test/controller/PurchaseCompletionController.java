package jp.co.sss.spring_test.controller;

import java.time.LocalDateTime;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jp.co.sss.spring_test.entity.Carts;
import jp.co.sss.spring_test.entity.OrderItems;
import jp.co.sss.spring_test.entity.Orders;
import jp.co.sss.spring_test.entity.Products;
import jp.co.sss.spring_test.entity.SalesItems;
import jp.co.sss.spring_test.entity.Users;
import jp.co.sss.spring_test.repository.CartsRepository;
import jp.co.sss.spring_test.repository.OrderItemsRepository;
import jp.co.sss.spring_test.repository.OrdersRepository;
import jp.co.sss.spring_test.repository.ProductDetailRepository;
import jp.co.sss.spring_test.repository.SalesItemsRepository;

@Controller
public class PurchaseCompletionController {
	@Autowired
	private CartsRepository cartsRepository;
	@Autowired
	private ProductDetailRepository productDetailRepository;
	@Autowired
	private OrdersRepository ordersRepository;
	@Autowired
	private OrderItemsRepository orderItemsRepository;
	@Autowired
	private SalesItemsRepository salesItemsRepository;

	@GetMapping("/purchase/purchaseCompletion/{cartId}")
	public String completeAndShow(
			@PathVariable("cartId") Integer cartId,
			HttpSession session,
			Model model) {
		Carts cart = cartsRepository.findByIdWithUserAndProduct(cartId).orElse(null);
		if(cart == null) return "redirect:/";
		Products product = cart.getProduct();
		Integer quantity = cart.getQuantity();
		Integer currentStock = product.getStock();
		if (currentStock != null) {
			int newStock = currentStock - quantity;
			if (newStock < 0) newStock = 0;
			product.setStock(newStock);
			productDetailRepository.save(product);
		}
		Integer taxPrice = product.getTax_price();
		Integer totalPrice = taxPrice * quantity;
		Orders order = new Orders();
		order.setUsers(cart.getUser());
		order.setTotal_amopunt(totalPrice);
		order.setStatus("注文完了");
		order.setCreatedAt(LocalDateTime.now());
		order.setUpdatedAt(LocalDateTime.now());
		ordersRepository.save(order);
		OrderItems orderItem = new OrderItems();
		orderItem.setOrder(order);
		orderItem.setProduct(product);
		orderItem.setQuantity(quantity);
		orderItem.setPrice(product.getPrice());
		orderItem.setCreatedAt(LocalDateTime.now());
		orderItem.setUpdatedAt(LocalDateTime.now());
		orderItemsRepository.save(orderItem);
		SalesItems salesItems = new SalesItems();
		salesItems.setProduct(product);
		salesItems.setCompany(product.getCompany());
		salesItems.setSale_name(product.getProduct_name());
		salesItems.setDescription("配送日時指定不可");
		salesItems.setDiscount_rate(0);
		salesItems.setSales_img_path(product.getImg_path());
		LocalDateTime now = LocalDateTime.now();
		salesItems.setStart_month(now);
		salesItems.setEnd_month(now.plusMonths(3));
		salesItems.setCreatedAt(LocalDateTime.now());
		salesItems.setUpdatedAt(LocalDateTime.now());
		salesItemsRepository.save(salesItems);
		cartsRepository.delete(cart);
		session.removeAttribute("purchaseCartId");
		Users user = cart.getUser();
		String address = "";
		String building = "";
		if(user.getUser_address() != null) {
			String[] parts = user.getUser_address().split(" ", 2);
			address = parts[0];
			if(parts.length > 1) building = parts[1];
		}
		model.addAttribute("productImg", product.getImg_path());
		model.addAttribute("productName", product.getProduct_name());
		model.addAttribute("product", product);
		model.addAttribute("quantity", quantity);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("address", address + " " + building);
		return "purchase/purchaseCompletion";
	}
}