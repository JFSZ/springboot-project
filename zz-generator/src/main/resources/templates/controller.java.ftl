package ${package.Controller};

import java.util.Arrays;
import java.util.Map;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.zz.springbootproject.utils.ServerResponse;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
import com.zz.springbootproject.utils.PageUtil;

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * ${table.comment!} 前端控制器
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${(table.entityPath)}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>
    @Autowired
    private ${table.serviceName} ${(table.serviceName)?uncap_first};

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("${package.ModuleName}:${table.entityPath}:list")
    public PageUtil list(@RequestParam Map<String, Object> params){
        PageUtil page = ${(table.serviceName)?uncap_first}.queryPage(params);
        return page;
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("${package.ModuleName}:${table.entityPath}:info")
    public ServerResponse info(@PathVariable("id") Long id){
        ${entity} ${table.name} = ${(table.serviceName)?uncap_first}.getById(id);
        return ServerResponse.ok().put("${table.name}", ${table.name});
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("${package.ModuleName}:${table.entityPath}:save")
    public ServerResponse save(@RequestBody ${entity} ${table.name}){
        ${(table.serviceName)?uncap_first}.save(${table.name});
        return ServerResponse.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("${package.ModuleName}:${table.entityPath}:update")
    public ServerResponse update(@RequestBody ${entity} ${table.name}){
        ${(table.serviceName)?uncap_first}.updateById(${table.name});
        return ServerResponse.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("${package.ModuleName}:${table.entityPath}:delete")
    public ServerResponse delete(@RequestBody Long[] ids){
        ${(table.serviceName)?uncap_first}.removeByIds(Arrays.asList(ids));
        return ServerResponse.ok();
    }

}
</#if>
