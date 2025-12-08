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
import jp.co.sss.spring_test.repository.SalesItemsRepository;

@Controller
public class PurchaseConfirmationController {
	@Autowired
	private CartsRepository cartsRepository;
	@Autowired
	private OrdersRepository ordersRepository;
	@Autowired
	private OrderItemsRepository orderItemsRepository;
	@Autowired
	private SalesItemsRepository salesItemsRepository;
	@GetMapping("/purchase/purchaseConfirmation/{cartId}")
	public String showPurchaseConfirmation(
		HttpSession session,
		Model model) {
		Integer cartId = (Integer) session.getAttribute("purchaseCartId");
		if (cartId == null) {
			return "redirect:/";
		}
		Carts cart = cartsRepository.findByIdWithUserAndProduct(cartId).orElse(null);
		if (cart == null) {
			return "redirect:/";
		}
		Users user = cart.getUser();
		Products product = cart.getProduct();
		Integer quantity = cart.getQuantity();
		String address = "";
		String building = "";
		if (user.getUser_address() != null) {
			String[] parts = user.getUser_address().split(" ", 2);
			address = parts[0];
			if (parts.length > 1) building = parts[1];
		}
		Integer totalPrice = product.getTax_price() * quantity;
		model.addAttribute("cart", cart);
		model.addAttribute("user", user);
		model.addAttribute("address", address);
		model.addAttribute("building", building);
		model.addAttribute("product", product);
		model.addAttribute("quantity", quantity);
		model.addAttribute("totalPrice", totalPrice);
		return "/purchase/purchaseConfirmation";
	}
	@GetMapping("/purchase/purchaseCompletion/{id}")
	public String purchaseComplete(
			@PathVariable("id") Integer cartId,
			HttpSession session) {
		Carts cart = cartsRepository.findByIdWithUserAndProduct(cartId).orElse(null);
		if (cart == null) {
			return "redirect:/";
		}
		Products product = cart.getProduct();
		Integer taxPrice = product.getTax_price();
		Integer quantity = cart.getQuantity();
		Integer totalPrice = taxPrice * quantity;
		Orders order = new Orders();
		order.setUsers(cart.getUser());
		order.setTotal_amopunt(totalPrice);
		order.setStatus("注文完了");
		order.setCreatedAt(LocalDateTime.now());
		order.setUpdatedAt(LocalDateTime.now());
		ordersRepository.save(order);
		OrderItems orderItem = new OrderItems();
		Integer price = product.getPrice();
		orderItem.setOrder(order);
		orderItem.setProduct(product);
		orderItem.setQuantity(quantity);
		orderItem.setPrice(price);
		orderItem.setCreatedAt(LocalDateTime.now());
		orderItem.setUpdatedAt(LocalDateTime.now());
		orderItemsRepository.save(orderItem);
		SalesItems salesItems = new SalesItems();
		salesItems.setProduct(product);
		salesItems.setCompany(product.getCompany());
		salesItems.setSale_name(product.getProduct_name());
		salesItems.setDescription("配送日時指定不可");
		salesItems.setDiscount_rate(0);
		salesItems.setSales_img_path("後で修正");
		LocalDateTime now = LocalDateTime.now();
		salesItems.setStart_month(now);
		salesItems.setEnd_month(now.plusMonths(3));
		salesItems.setCreatedAt(LocalDateTime.now());
		salesItems.setUpdatedAt(LocalDateTime.now());
		salesItemsRepository.save(salesItems);
		cartsRepository.delete(cart);
		session.removeAttribute("purchaseCartId");
		return "/purchase/purchaseCompletion";
	}
}