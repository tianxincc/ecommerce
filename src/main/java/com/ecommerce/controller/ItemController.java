package com.ecommerce.controller;

import com.ecommerce.controller.ViewObject.ItemView;
import com.ecommerce.error.BusinessException;
import com.ecommerce.response.CommonReturnType;
import com.ecommerce.service.ItemService;
import com.ecommerce.service.model.ItemModel;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Controller("item")
@RequestMapping("/item")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class ItemController extends BaseController {


    @Autowired
    private  ItemService itemService;

    //创建商品的controller
    @RequestMapping(value = "/create",method = {RequestMethod.POST},consumes = CONTENT_TYPE_FROMED)
    @ResponseBody
    public CommonReturnType createItem(@RequestParam(name="title")String title,
                                       @RequestParam(name="description")String description,
                                       @RequestParam(name="price") BigDecimal price,
                                       @RequestParam(name="imgUrl")String imgUrl,
                                       @RequestParam(name="stock")Integer stock) throws BusinessException {

        //封装service请求用来创建商品
        ItemModel itemModel=new ItemModel();
        itemModel.setTitle(title);
        itemModel.setDescription(description);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setImgUrl(imgUrl);

        ItemModel itemModel1FromRetuen= itemService.createItem(itemModel);

        ItemView itemView=converViewFromModel(itemModel1FromRetuen);

        return  CommonReturnType.create(itemView);
    }

    //商品详情页浏览
    @RequestMapping(value = "/getItem",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getItem(@RequestParam(name="id")Integer id){
        ItemModel itemModel=itemService.getItemById(id);
        ItemView itemView=converViewFromModel(itemModel);

        return  CommonReturnType.create(itemView);
    }

    //商品列表浏览
    @RequestMapping(value = "/list",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType listItem() {
        List<ItemModel> itemModelList=itemService.listItem();

        //使用stream api 将list 内itemModel 转换为ItemView
       List<ItemView> itemViewList= itemModelList.stream().map(itemModel -> {
            ItemView itemView= this.converViewFromModel(itemModel);
            return itemView;
        }).collect(Collectors.toList());
        return  CommonReturnType.create(itemViewList);
    }
        private  ItemView converViewFromModel(ItemModel itemModel){
        if(itemModel==null){
            return  null;
        }
        ItemView itemView=new ItemView();
        BeanUtils.copyProperties(itemModel,itemView);
        if(itemModel.getPromoModel()!=null){
            //有正在进行或者即将进行的秒杀活动
            itemView.setPromoStatus(itemModel.getPromoModel().getStatus());
            itemView.setPromoId(itemModel.getPromoModel().getId());
            itemView.setStartDate(itemModel.getPromoModel().getStartDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
            itemView.setPromoPrice(itemModel.getPromoModel().getPromoItemPrice());
        }else {
            itemView.setPromoStatus(0);
        }
        return itemView;
    }
}
