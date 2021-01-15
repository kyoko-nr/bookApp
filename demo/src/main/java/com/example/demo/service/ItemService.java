package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Item;
import com.example.demo.mapper.ItemMapper;

/**
 * アイテムサービス.
 * @author kyokokitagawa
 */
@Service
public class ItemService {

	/**
	 * アイテムマッパー
	 */
	@Autowired
	private ItemMapper itemMapper;
	
	/**
	 * キーワード検索.
	 * @param str キーワード
	 * @param str2 キーワード
	 * @return アイテム
	 */
	@Transactional
	public List<Item> findByKeyword(String str, String str2) {
		return itemMapper.findByKeyword(str, str2);
	}

	
	/**
	 * 一件検索.
	 * @param id ID
	 * @return アイテム
	 */
	@Transactional
	public Item findOne(Long id) {
		return itemMapper.findOne(id);
	}

}
