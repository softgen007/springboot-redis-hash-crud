package demo.springboot.cache.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import demo.springboot.cache.redis.model.Item;
import demo.springboot.cache.redis.repo.ItemRepository;

import java.util.Map;

@RestController
public class ItemController {
    @Autowired
    ItemRepository itemRepo;

    @RequestMapping("/item")
    @ResponseBody
    public ResponseEntity<Map<Integer,Item>> getAllItems(){
        Map<Integer,Item> items =  itemRepo.getAllItems();
        return new ResponseEntity<Map<Integer,Item>>(items, HttpStatus.OK);
    }

    @GetMapping("/item/{itemId}")
    @ResponseBody
    public ResponseEntity<Item> getItem(@PathVariable int itemId){
        Item item = itemRepo.getItem(itemId);
        return new ResponseEntity<Item>(item, HttpStatus.OK);
    }

    @PostMapping(value = "/item")
    @ResponseBody
    public ResponseEntity<Item> addItem(@RequestBody Item item,UriComponentsBuilder builder){
        itemRepo.addItem(item);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/item/{id}").buildAndExpand(item.getId()).toUri());
        return new ResponseEntity<Item>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/item")
    @ResponseBody
    public ResponseEntity<Item> updateItem(@RequestBody Item item){
        if(item != null){
            itemRepo.updateItem(item);
        }
        return new ResponseEntity<Item>(item, HttpStatus.OK);
    }

    @DeleteMapping("/item/{itemId}")
    @ResponseBody
    public ResponseEntity<Void> deleteItem(@PathVariable int itemId){
        itemRepo.deleteItem(itemId);
        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }
}
