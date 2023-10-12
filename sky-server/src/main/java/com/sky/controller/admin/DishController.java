package com.sky.controller.admin;


import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "菜品相关接口")
@RestController
public class DishController {

   @Autowired
   private DishService dishService;

   @Autowired
   private RedisTemplate redisTemplate;

   @PostMapping
   @ApiOperation("新增菜品")
   public Result save(@RequestBody DishDTO dishDTO) {

      log.info("新增菜品", dishDTO);
      dishService.saveWithFlavor(dishDTO);

      // 清理缓存数据
      String key = "dish_" + dishDTO.getCategoryId();
      redisTemplate.delete(key);

      return Result.success();
   }

   @ApiOperation("菜品查询")
   @GetMapping("/page")
   public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
      PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
      return Result.success(pageResult);
   }

   @DeleteMapping
   @ApiOperation("菜品批量删除")
   public Result delect( @RequestParam List<Long> ids){
      log.info("菜品批量删除：{}",ids);
      dishService.delectBatch(ids);
      //将所有菜品缓存清掉
      Set keys = redisTemplate.keys("dish_*");
      redisTemplate.delete(keys);

      return Result.success();
   }

   /**
    * 根据id查询菜品
    * @param id
    * @return
    */
   @GetMapping("/{id}")
   @ApiOperation("根据id查询菜品")
 public Result<DishVO> getById(@PathVariable Long id){

      DishVO dishVO = dishService.getByIdWithFlavor(id);
      return Result.success(dishVO);

   }

   /**
    * 修改菜品接口
    * @param dishDTO
    * @return
    */

   @ApiOperation("修改菜品数据")
@PutMapping
   public Result update(@RequestBody DishDTO dishDTO){
       log.info("修改菜品：{}",dishDTO);
      dishService.updateWithFlavor(dishDTO);
      //将所有菜品缓存清掉
      Set keys = redisTemplate.keys("dish_*");
      redisTemplate.delete(keys);
      return Result.success();
   }






}
