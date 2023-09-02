package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.model.request.FoodDetailsRequestModel;
import com.driver.model.response.FoodDetailsResponse;
import com.driver.model.response.OperationStatusModel;
import com.driver.service.FoodService;
import com.driver.shared.dto.FoodDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foods")
public class FoodController {

	@Autowired
	private FoodService foodService;
	@GetMapping(path="/{id}")
	public FoodDetailsResponse getFood(@PathVariable String id) throws Exception{
		FoodDto foodDto=foodService.getFoodById(id);
		if(foodDto==null){
			throw new Exception(id);
		}
		FoodDetailsResponse foodDetailsResponse= FoodDetailsResponse.builder()
				.foodId(foodDto.getFoodId())
				.foodCategory(foodDto.getFoodCategory())
				.foodName(foodDto.getFoodName())
				.foodPrice(foodDto.getFoodPrice()).build();
		return foodDetailsResponse;
	}

	@PostMapping("/create")
	public FoodDetailsResponse createFood(@RequestBody FoodDetailsRequestModel foodDetails) {

		FoodDto foodDto= FoodDto.builder()
				.foodName(foodDetails.getFoodName())
				.foodCategory(foodDetails.getFoodCategory())
				.foodPrice(foodDetails.getFoodPrice()).build();
		foodService.createFood(foodDto);
		FoodDetailsResponse foodDetailsResponse= FoodDetailsResponse.builder()
				.foodName(foodDetails.getFoodName())
				.foodCategory(foodDetails.getFoodCategory())
				.foodPrice(foodDetails.getFoodPrice()).build();
		return foodDetailsResponse;
	}

	@PutMapping(path="/{id}")
	public FoodDetailsResponse updateFood(@PathVariable String id, @RequestBody FoodDetailsRequestModel foodDetails) throws Exception{
		FoodDto foodDto= FoodDto.builder()
				.foodId(id)
				.foodName(foodDetails.getFoodName())
				.foodCategory(foodDetails.getFoodCategory())
				.foodPrice(foodDetails.getFoodPrice())
				.build();
		try{
			foodService.updateFoodDetails(id,foodDto);
		}catch(Exception e){
			throw new Exception(id);
		}
		return FoodDetailsResponse.builder()
				.foodId(id)
				.foodName(foodDetails.getFoodName())
				.foodCategory(foodDetails.getFoodCategory())
				.foodPrice(foodDetails.getFoodPrice()).build();
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteFood(@PathVariable String id) throws Exception{
		try{
			foodService.deleteFoodItem(id);
		}catch (Exception e){
			throw new Exception(id);
		}

		return new OperationStatusModel("SUCCESS","Delete Food");
	}

	@GetMapping()
	public List<FoodDetailsResponse> getFoods() {
		List<FoodDto>foodDtoList=foodService.getFoods();
		List<FoodDetailsResponse>foodDetailsResponseList=new ArrayList<>();
		for(FoodDto foodDto:foodDtoList){
			FoodDetailsResponse foodDetailsResponse=FoodDetailsResponse.builder()
					.foodId(foodDto.getFoodId())
					.foodName(foodDto.getFoodName())
					.foodCategory(foodDto.getFoodCategory())
					.foodPrice(foodDto.getFoodPrice()).build();
			foodDetailsResponseList.add(foodDetailsResponse);
		}
		return foodDetailsResponseList;
	}
}