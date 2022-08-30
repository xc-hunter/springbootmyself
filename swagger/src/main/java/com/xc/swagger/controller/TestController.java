package com.xc.swagger.controller;

import com.xc.swagger.entity.TestUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
// tags 组名称
@Api(tags = "测试控制器")
public class TestController {

    @ApiOperation("测试获取信息")
    @GetMapping("get/info")
    public String getTestInfo(@RequestParam("id")String id){
        return "test info return";
    }

    @ApiOperation(value = "查询用户", notes = "查询用户信息。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "查询的用户姓名", required = true),
            @ApiImplicitParam(name = "age", value = "查询的用户岁数", required = false)
    })
    @GetMapping(value = "/{name}")
    public void findUser(@PathVariable(value = "name", required = true) String name,
                                         @RequestParam(value = "age", required = false) Integer age) {
        // 创建模拟数据，然后返回数据
    }

    @ApiOperation(value = "增加用户")
    @PostMapping("user/insert")
    public void addUser(@RequestBody TestUser testUser){

    }

    @DeleteMapping(value = "/{name}")
    @ApiOperation(value = "删除用户", notes = "删除用户信息。")
    public ResponseEntity<String> deleteUser(@PathVariable(value = "name") String name) {
        return ResponseEntity.ok(name + " deleted");
    }
}
