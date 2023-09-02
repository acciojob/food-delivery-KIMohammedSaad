package com.driver.service.impl;

import com.driver.io.entity.FoodEntity;
import com.driver.io.repository.FoodRepository;
import com.driver.service.FoodService;
import com.driver.shared.dto.FoodDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class FoodServiceImpl implements FoodService{

    @Autowired
    private FoodRepository foodRepository;
    @Override
    public FoodDto createFood(FoodDto food) {
        FoodEntity foodEntity=FoodEntity.builder()
                .id(food.getId())
                .foodId(food.getFoodId())
                .foodCategory(food.getFoodCategory())
                .foodName(food.getFoodName())
                .foodPrice(food.getFoodPrice())
                .build();
        foodRepository.save(foodEntity);
        return food;
    }

    @Override
    public FoodDto getFoodById(String foodId) throws Exception {
        FoodEntity foodEntity=foodRepository.findByFoodId(foodId);
        if(foodEntity==null){
            throw new Exception(foodId);
        }
        FoodDto foodDto=FoodDto.builder()
                .id(foodEntity.getId())
                .foodId(foodEntity.getFoodId())
                .foodName(foodEntity.getFoodName())
                .foodCategory(foodEntity.getFoodCategory())
                .foodPrice(foodEntity.getFoodPrice())
                .build();

        return foodDto;
    }


    @Override
    public FoodDto updateFoodDetails(String foodId, FoodDto foodDetails) throws Exception {
        if(foodRepository.findByFoodId(foodId)==null){
            throw new Exception(foodId);
        }
        FoodEntity foodEntity= FoodEntity.builder()
                .id(foodDetails.getId())
                .foodId(foodId)
                .foodName(foodDetails.getFoodName())
                .foodCategory(foodDetails.getFoodCategory())
                .foodPrice(foodDetails.getFoodPrice())
                .build();
        foodRepository.save(foodEntity);
        return foodDetails;
    }


    @Override
    public void deleteFoodItem(String id) throws Exception {
        FoodEntity foodEntity=foodRepository.findByFoodId(id);
        if(foodEntity==null){
            throw new Exception(id);
        }
        foodRepository.delete(foodEntity);

    }


    @Override
    public List<FoodDto> getFoods() {
        List<FoodEntity>foodEntities= (List<FoodEntity>) foodRepository.findAll();
        List<FoodDto>foodDtoList=new ArrayList<>();
        for(FoodEntity foodEntity:foodEntities){
            FoodDto foodDto=FoodDto.builder()
                    .id(foodEntity.getId())
                    .foodId(foodEntity.getFoodId())
                    .foodName(foodEntity.getFoodName())
                    .foodCategory(foodEntity.getFoodCategory())
                    .foodPrice(foodEntity.getFoodPrice())
                    .build();
            foodDtoList.add(foodDto);
        }
        return foodDtoList;
    }
}