package com.myshop.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.myshop.dto.ItemFormDto;
import com.myshop.dto.ItemSearchDto;
import com.myshop.entity.Item;
import com.myshop.service.ItemService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ItemController {
	private final ItemService itemService;

	// 상품 등록 페이지를 보여줌
	@GetMapping(value = "/admin/item/new")
	public String itemForm(Model model) {
		model.addAttribute("itemFormDto", new ItemFormDto());
		return "item/itemForm";
	}

	// 상품등록
	@PostMapping(value = "/admin/item/new")
	public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult, Model model,
			@RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {
		if (bindingResult.hasErrors()) {
			return "item/itemForm";
		}

		// 첫번째 이미지가 있는지 검사(첫번째 이미지는 필수 입력값이기 때문에)
		if (itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null) {
			model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값입니다.");
			return "item/itemForm";
		}

		try {
			itemService.saveItem(itemFormDto, itemImgFileList);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "상품 등록 중 에러가 발생했습니다.");
			return "item/itemForm";
		}

		return "redirect:/";
	}

	// 상품 수정 페이지 보기
	@GetMapping(value = "/admin/item/{itemId}") // 아직 파라메터가 뭐가 들어올지 모르니까 {itemId}로 적어준 다음
	public String itemDtl(@PathVariable("itemId") Long itemId, Model model) { // @PathVariable 어노테이션을 붙여준다. 그러면 {itemId}
																				// 여기애 값을 넣어줌 (주소에 있는 값을 인식해서 넣어줌)
		try {
			ItemFormDto itemFormDto = itemService.getItemDtl(itemId); // 매개변수로 itemID (Path에서 온 값)
			model.addAttribute(itemFormDto); // 모델에 담아주면 끝!
		} catch (EntityNotFoundException e) {
			model.addAttribute("errorMessage", "존재하지 않는 상품입니다.");
			model.addAttribute("itemFormDto", new ItemFormDto()); // 등록과 수정을 같이 사용하고 있기 때문에 빈 객체를 넣어줘야 object에서 인식을 할 수
																	// 있다.
			return "item/itemForm";
		}
		return "item/itemForm";
	}

	// 상품 수정
	@PostMapping(value = "/admin/item/{itemId}")
	public String itemUpdate(@Valid ItemFormDto itemFormDto, BindingResult bindingResult, Model model,
			@RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {
		
		// 첫번째 이미지가 있는지 검사(첫번째 이미지는 필수 입력값이기 때문에)
		if (itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null) {
			model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값입니다.");
			return "item/itemForm";
		}
		
		try {
			 itemService.updateItem(itemFormDto, itemImgFileList);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
		} 
		return "redirect:/";
	}
	
	@GetMapping(value = {"/admin/items", "/admin/items/{page}"}) // 페이지 번호가 없는 경우와 있는 경우 2가지를 매핑 
	public String itemManage(ItemSearchDto itemSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {
		// url 경로에 페이지가 있으면 해당 페이지를 조회하도록 하고 페이지 번호가 없으면 0 페이지를 조회하도록 한다. 
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3); // of(조회할 페이지의 번호, 한 페이지당 조회할 데이터의 개수)
		
		Page<Item> items = itemService.getAdminItemPage(itemSearchDto, pageable);
		
		model.addAttribute("items", items);
		model.addAttribute("itemSearchDto", itemSearchDto);
		model.addAttribute("maxPage", 5);
		
		return "item/itemMng";
	}

}
