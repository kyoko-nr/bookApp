package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.ShopStock;
import com.example.demo.domain.Stock;
import com.example.demo.form.StockArriveForm;
import com.example.demo.service.StockService;
import com.example.demo.utils.TaxCalculator;

/**
 * 在庫コントローラー.
 * @author kyokokitagawa
 *
 */
@Controller
@RequestMapping("/stock")
public class StockController {
	
	/** 在庫サービス. */
	@Autowired
	private StockService stockService;

	/**
	 * 商品入荷ページ表示.
	 * @param itemId 商品ID
	 * @param shopId 店舗ID
	 * @param model モデル
	 * @return テンプレート名
	 */
	@GetMapping("arrive/{itemId}/{shopId}")
	public String arrive(@PathVariable Long itemId, @PathVariable Long shopId, Model model) {
		final ShopStock shopStock = stockService.findByItemIdAndShopId(itemId, shopId);
		StockArriveForm stockArriveForm = new StockArriveForm();
		stockArriveForm.setItemId(shopStock.getItemId());
		stockArriveForm.setPrice(shopStock.getPrice());
		stockArriveForm.setShopId(shopStock.getShopId());
		stockArriveForm.setShopName(shopStock.getShopName());
		stockArriveForm.setCurrentAmount(shopStock.getAmount());
		stockArriveForm.setTaxPrice(TaxCalculator.calcTaxIncluded(shopStock.getPrice()));
		stockArriveForm.setBookName(shopStock.getBookName());
		model.addAttribute("stockArriveForm", stockArriveForm);
		return "arrive";
	}
	
	/**
	 * 入荷情報登録.
	 * @param stockArriveForm 在庫入荷フォーム
	 * @param result BindingResult
	 * @param model モデル
	 * @return テンプレート名
	 */
	@PostMapping("arrive/submit")
	public String arrivalSubmit(@ModelAttribute @Validated StockArriveForm stockArriveForm, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "arrive";
		}
		Stock stock = new Stock();
		stock.setItemId(stockArriveForm.getItemId());
		stock.setShopId(stockArriveForm.getShopId());
		stock.setAmount(stockArriveForm.getInputAmount());
		stockService.saveStock(stock);
		
		return "redirect:/items/"+ stock.getItemId();
	}
	
	@GetMapping("sell/{itemId}/{shopId}")
	public String sell(@PathVariable Long itemId, @PathVariable Long shopId, Model model) {
		return null;
	}
	
	@PostMapping("sell/submit")
	public String sellSubmit(@ModelAttribute @Validated StockArriveForm stockArriveForm, BindingResult result, Model model) {
		return null;
	}
}
